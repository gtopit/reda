package com.gtop.reda.core.support;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.gtop.reda.core.RedaResultHandler;
import com.gtop.reda.core.RedaCallHandler;
import com.gtop.reda.core.annotation.IsMe;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 默认使用
 * @author Javy Hong
 * @Date 2023-05-16 16:44
 */
public final class DefaultRedaCallHandler extends ThreadPoolExecutor implements RedaCallHandler, RedaResultHandler {

    public DefaultRedaCallHandler() {
        super(Runtime.getRuntime().availableProcessors() * 2, Runtime.getRuntime().availableProcessors() * 4, 60_000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(32), new ThreadFactory() {
            private volatile AtomicInteger threadNo = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Reda-remoteCall-" + threadNo.getAndIncrement());
            }
        });
    }

    @Override
    public Object invokeGet(RedaDefinition info) {
        List<CompletableFuture<RedaResult>> futureList = doHttp(up -> {
            RedaUri redaUri = up.redaUri;
            String data;
            if (up.parameters.size() > 0) {
                data = HttpUtil.get(redaUri.getUri(), up.parameters);
            } else {
                data = HttpUtil.get(redaUri.getUri());
            }
            return RedaResult.builder().meId(redaUri.getMeId()).data(data).build();
        }, info);
        List<RedaResult> redaResults = getResultFromFuture(futureList);
        return mapRedaResult(redaResults, info);
    }

    private Object mapRedaResult(List<RedaResult> redaResults, RedaDefinition info) {
        return handleResult(redaResults, info);
    }


    @Override
    public Object invokePost(RedaDefinition info) {
        List<CompletableFuture<RedaResult>> futureList = doHttp(up -> {
            String data = HttpUtil.post(up.redaUri.getUri(), up.parameters);
            return RedaResult.builder().meId(up.redaUri.getMeId()).data(data).build();
        }, info);
        List<RedaResult> redaResults = getResultFromFuture(futureList);
        return mapRedaResult(redaResults, info);
    }

    private List<RedaResult> getResultFromFuture(List<CompletableFuture<RedaResult>> futures) {

        List<RedaResult> results = new ArrayList<>();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        futures.forEach(e -> {
            try {
                results.add(e.get());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return results;

    }

    private List<CompletableFuture<RedaResult>> doHttp(Function<UriAndParameters, RedaResult> callback, RedaDefinition info) {
        return Arrays.stream(info.getRedaUris()).map(uri -> {
            CompletableFuture<RedaResult> future = CompletableFuture.completedFuture(uri)
                    .thenApplyAsync(u -> {
                        Map<String, Object> parameters = info.getRedaParameters();
                        UriAndParameters up = new UriAndParameters(u, parameters);
                        return callback.apply(up);
                    }, this);

            return future;
        }).collect(Collectors.toList());
    }

    @Override
    public Object handleResult(List<RedaResult> redaResults, RedaDefinition definition) {
        Object result = null;
        try {
            result = definition.getFinalResultClass().newInstance();
            Map<String, Field> fields = getIsMeFields(definition.getFinalResultClass());
            if (redaResults.size() > 1) {
                for (RedaResult redaResult : redaResults) {
                    Field dataField = fields.get(redaResult.getMeId());
                    dataField.setAccessible(true);
                    if (definition.getResultTemplate().getData(redaResult.getData()).getClass().getClassLoader() != null) {
                        dataField.set(result, JSONUtil.toBean((String) definition.getResultTemplate().getData(redaResult.getData()), dataField.getType()));
                    } else {
                        dataField.set(result, definition.getResultTemplate().getData(redaResult.getData()));
                    }
                }
            } else if (redaResults.size() == 1) {
                Field dataField = fields.entrySet().iterator().next().getValue();
                dataField.setAccessible(true);
                if (definition.getResultTemplate().getData(redaResults.get(0).getData()).getClass().getClassLoader() != null) {
                    dataField.set(result, JSONUtil.toBean((String) definition.getResultTemplate().getData(redaResults.get(0).getData()), dataField.getType()));
                } else {
                    dataField.set(result, definition.getResultTemplate().getData(redaResults.get(0).getData()));
                }
            } else {
                if (definition.getResultTemplate().getData(redaResults.get(0).getData()).getClass().getClassLoader() != null) {
                    result = JSONUtil.toBean((String) definition.getResultTemplate().getData(redaResults.get(0).getData()), definition.getFinalResultClass());
                } else {
                    result = definition.getResultTemplate().getData(redaResults.get(0).getData());
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    private Map<String, Field> getIsMeFields(Class<?> targetClass) {

        Map<String, Field> map = new HashMap<>();

        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            IsMe isMe = declaredField.getAnnotation(IsMe.class);
            if (isMe != null) {
                map.put(isMe.id(), declaredField);
            }
        }

        if (targetClass != null && !targetClass.getSuperclass().equals(Object.class)) {
            Map<String, Field> subMap = getIsMeFields(targetClass.getSuperclass());
            map.putAll(subMap);
        }

        return map;
    }

    static class UriAndParameters {
        RedaUri redaUri;
        Map<String, Object> parameters;
        UriAndParameters(RedaUri redaUri, Map<String, Object> parameters) {
            this.redaUri = redaUri;
            this.parameters = parameters;
        }
    }

}
