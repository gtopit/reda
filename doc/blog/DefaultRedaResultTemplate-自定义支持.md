# 功能作用
    对远程调用返回的json格式进行解析，自定义自己的解析模板。
# 使用示例
### 1）实现RedaResultTemplate接口

```java

import com.gtop.reda.core.support.RedaResultTemplate;

// MyData参考 IsMe注解.md（去掉了@IsMe注解）
public class MyTemplate implements RedaResultTemplate {
    @Override
    public Object getData(Object result) {
        JSONObject jsonObject = JSONUtil.parseObj(result);
        Object data = jsonObject.get("message");
        if (data.getClass().getClassLoader() == null) {
            // jdk的类直接返回
            return data;
        }
        // 自定义的类转成json返回
        return JSONUtil.toJsonStr(data);
    }
}
```
### 2）@RemoteData注解参数赋值

```java
import com.gtop.reda.core.annotation.RemoteData;

@RestController
@RequestMapping("/rd")
public class TestController {

    @RemoteData(resultTemplate = MyTemplate.class, uris = "http://localhost:8080/getCatAndDog")
    @GetMapping("/get")
    public Result<MyData> test(ParamDTO dto) {
        return Result.success(new MyData());
    }

}
```
