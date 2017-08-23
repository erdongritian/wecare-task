package com.htxa.wecare.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * Description:
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time:2017/8/14 9:59
 */
@Service("demoService2")
public class DemoService2 implements Job {
    Logger logger= LoggerFactory.getLogger(DemoService2.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("DemoService2 is run:"+ LocalTime.now());
    }
}
