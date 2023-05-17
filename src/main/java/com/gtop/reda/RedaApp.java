package com.gtop.reda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

/**
 * @author hongzw@citycloud.com.cn
 * @Date 2023-05-07 23:53
 */
@SpringBootApplication
public class RedaApp {

    private static final Logger log = Logger.getLogger(RedaApp.class .getName());

    public static void main(String[] args) {
        SpringApplication.run(RedaApp.class, args);
        log.info(">>> " + RedaApp.class.getSimpleName() + " is success!");
    }

}
