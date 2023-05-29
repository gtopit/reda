package com.gtop.reda;

import com.gtop.reda.core.annotation.EnableReda;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

/**
 * @author Javy Hong
 * @Date 2023-05-07 23:53
 */
@SpringBootApplication
@EnableReda
public class RedaApp {

    private static final Logger log = Logger.getLogger(RedaApp.class .getName());

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RedaApp.class);
        application.run(args);
        log.info(">>> " + RedaApp.class.getSimpleName() + " is success!");
    }

}
