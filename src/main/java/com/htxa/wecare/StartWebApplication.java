package com.htxa.wecare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Description:
 *
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time:2017/8/9 16:45
 */
@SpringBootApplication
public class StartWebApplication {
    public static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(StartWebApplication.class);
    }
}
