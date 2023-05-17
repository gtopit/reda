package com.gtop.reda.test;

import com.gtop.reda.core.annotation.IsMe;
import lombok.Data;

/**
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-17 17:42
 */
@Data
public class CountRes {
    @IsMe(id = "1")
    private Integer count1;
    @IsMe(id = "2")
    private Integer count2;

    private Integer total;

}
