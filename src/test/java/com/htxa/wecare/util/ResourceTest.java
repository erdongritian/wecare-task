package com.htxa.wecare.util;

import org.junit.Test;
import org.springframework.core.io.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Description:
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time:2017/8/11 16:06
 */
public class ResourceTest {

    @Test
    public void testResourceTest(){
        System.out.println("ddd");
    }

    @Test
    public void testResourceLoader() {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("http://www.google.com.hk");
        System.out.println(resource instanceof UrlResource); //true
        //注意这里前缀不能使用“classpath*:”，这样不能真正访问到对应的资源，exists()返回false
        resource = loader.getResource("classpath:quartz.properties");
        System.out.println("#####"+resource.exists());
        System.out.println(resource instanceof ClassPathResource); //true
        resource = loader.getResource("1quartz.properties");
        System.out.println(resource.exists());
        System.out.println(resource instanceof ClassPathResource); //true
    }

    @Test
    public void testSchedulerFactoryBean(){
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // this allows to update triggers in DB when updating settings in config file:
        //用于quartz集群,QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        //用于quartz集群,加载quartz数据源
        //factory.setDataSource(dataSource);
        //QuartzScheduler 延时启动，应用启动完10秒后 QuartzScheduler 再启动
        factory.setStartupDelay(10);
        //用于quartz集群,加载quartz数据源配置
//        factory.setQuartzProperties(quartzProperties());
        factory.setAutoStartup(true);
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        //注册触发器
        factory.setTriggers();           //直接使用配置文件

        ResourceLoader loader = new DefaultResourceLoader();
        factory.setConfigLocation(loader.getResource("classpath:quartz.properties"));

//        factory.setConfigLocation(new FileSystemResource(this.getClass().getResource("/quartz.properties").getPath()));
    }
}
