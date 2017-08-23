package com.htxa.wecare.task;

import com.htxa.wecare.model.TaskEntity;
import com.htxa.wecare.util.AppConfig;
import com.htxa.wecare.util.DetailQuartzJobBean;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Description:
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time:2017/8/10 11:56
 */
@Component
public class ServerTask {

    @Resource
    private AppConfig appConfig;

    @Bean(name = "stdScheduler")
    public SchedulerFactoryBean schedulerFactoryBean()
            throws Exception {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // this allows to update triggers in DB when updating settings in config file:
        //用于quartz集群,QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        factory.setStartupDelay(10);

        factory.setAutoStartup(true);
        factory.setApplicationContextSchedulerContextKey("ctx");

        //use config
//        factory.setTriggers(getTriggers());

        //load quartz.properties
        ResourceLoader loader = new DefaultResourceLoader();
        factory.setConfigLocation(loader.getResource("classpath:quartz.properties"));
//        factory.getScheduler().

        return factory;
    }


    /**
     *
     * @return
     * @throws Exception
     */
    private Trigger[] getTriggers() throws Exception {
        List<CronTrigger> triggers=new ArrayList<>();

        List<TaskEntity> taskEntities=appConfig.getList();

        for(TaskEntity item:taskEntities){
            JobDataMap map = new JobDataMap();
            map.put("targetObject", item.getTaskService());
            map.put("targetMethod", "execute");
            JobDetail jobDetail = newJob(DetailQuartzJobBean.class).withIdentity(item.getTaskService())
                    .usingJobData(map).storeDurably().requestRecovery().build();

            JobDataMap cronJobMap = new JobDataMap();
            cronJobMap.put("jobDetail", jobDetail);

            CronTrigger cronTrigger=newTrigger().forJob(jobDetail).withIdentity(item.getTaskService(), Scheduler.DEFAULT_GROUP)
                    .usingJobData(cronJobMap).withSchedule(cronSchedule(item.getCronExpression()))
                    .build();

            triggers.add(cronTrigger);
        }

        return  triggers.toArray(new CronTriggerImpl[0]);
    }

}
