package com.gtop.reda.test;

import com.gtop.reda.core.annotation.RemoteData;
import com.gtop.reda.core.support.RedaMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-17 17:25
 */
@RestController
@RequestMapping("/rd")
public class TestController {

    @GetMapping("/test")
    @RemoteData(uris = {"meId@1@http://localhost:8080/mock/get1"}, method = RedaMethod.GET)
    public Result<CountRes> test(ParamDTO dto) {
        CountRes res = new CountRes();
        res.setCount1(111);
        res.setCount2(222);
        return Result.success(res);
    }

    @GetMapping("/testGet")
    @RemoteData(uris = {"meId@1@http://localhost:8080/mock/get1", "meId@2@http://localhost:8080/mock/get2"}, method = RedaMethod.GET, resultDecorator = MyDecorator.class)
    public Result<CountRes> testGet(ParamDTO dto) {
        CountRes res = new CountRes();
        res.setCount1(111);
        res.setCount2(222);
        return Result.success(res);
    }

    @GetMapping("/testPost")
    @RemoteData(uris = {"meId@1@http://localhost:8080/mock/post1", "meId@2@http://localhost:8080/mock/post2"}, method = RedaMethod.POST)
    public Result<CountRes> testPost(ParamDTO dto) {
        CountRes res = new CountRes();
        res.setCount1(111);
        res.setCount2(222);
        return Result.success(res);
    }

}
