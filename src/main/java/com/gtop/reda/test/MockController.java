package com.gtop.reda.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Javy Hong
 * @Date 2023-05-17 17:25
 */
@RestController
@RequestMapping("/mock")
public class MockController {

    @GetMapping("/get1")
    public Result<Integer> getCount1(ParamDTO dto) {
        return Result.success(dto.getId() + 100);
    }

    @GetMapping("/get2")
    public Result<Integer> getCount2(ParamDTO dto) {
        return Result.success(dto.getId() + 101);
    }

    @PostMapping("/post1")
    public Result<Integer> postCount1(ParamDTO dto) {
        return Result.success(dto.getId() + 100);
    }

    @PostMapping("/post2")
    public Result<Integer> postCount2(ParamDTO dto) {
        return Result.success(dto.getId() + 101);
    }

}
