package com.htxa.wecare.util;

import java.time.Duration;
import java.time.Instant;

/**
 * Description:
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time:2017/8/17 15:34
 */
public class TestUtil {
    public static void main(String[] args) throws Exception {
        Instant start= Instant.now();
        Thread.sleep(2123);
        Instant end=Instant.now();

        Duration duration=Duration.between(start,end);
        System.out.println(duration.toMillis());
        System.out.println(DateUtil.formatTime(duration.toMillis()));
        System.out.println(start);
    }
}
