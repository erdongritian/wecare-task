package com.htxa.wecare.util;


import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.time.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description:Job excute instantce
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time:2017/8/11 16:48
 */
public class DetailQuartzJobBean extends QuartzJobBean {

    protected final Log logger = LogFactory.getLog(getClass());

    //prevent execute the same task at one time
    static ConcurrentHashMap<String, Boolean> targetMap = new ConcurrentHashMap<>();
    static Lock lock = new ReentrantLock();

    @Setter
    private String targetObject;
    @Setter
    private String targetMethod;
    @Setter
    private ApplicationContext ctx;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        lock.lock();
        Boolean isRun = targetMap.get(targetObject);
        if (isRun == null || !isRun) {
            targetMap.put(targetObject, true);
            lock.unlock();
            executeJob(context);
            targetMap.put(targetObject, false);
        }else {
            lock.unlock();
        }

    }

    private void executeJob(JobExecutionContext context){
        try {
            logger.info("begin execute task:["+targetObject+"]");
            Instant start=Instant.now();
            Object otargetObject = ctx.getBean(targetObject);
            try {
                Method m = otargetObject.getClass().getMethod(targetMethod, new Class[] {JobExecutionContext.class});
                m.invoke(otargetObject,new Object[] {context});
                Duration duration=Duration.between(start,Instant.now());
                logger.info("Task finish:["+targetObject+"]"+",consuming time："+DateUtil.formatTime(duration.toMillis()));
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("Task error:["+targetObject+"],message:"+e.getMessage());
            e.printStackTrace();
        }
    }
}
