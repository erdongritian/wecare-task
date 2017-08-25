package com.htxa.wecare.util;

import com.htxa.wecare.StartWebApplication;
import com.htxa.wecare.model.CronTriggerWithState;
import com.htxa.wecare.model.TaskEntity;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Description:
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time: 2017/8/18 10:56
 */
@Component
public class TaskUtil {

    @Resource
    private StdScheduler stdScheduler;

    public String addTask(TaskEntity taskEntity){
        try {
            JobDataMap map = new JobDataMap();
            map.put("targetObject", taskEntity.getTaskService());
            map.put("targetMethod", "execute");
            JobDetail jobDetail = newJob(DetailQuartzJobBean.class).withIdentity(taskEntity.getTaskService())
                    .usingJobData(map).storeDurably().requestRecovery().build();

            CronTrigger cronTrigger=newTrigger().forJob(jobDetail).withIdentity(taskEntity.getTaskService(), Scheduler.DEFAULT_GROUP)
                    .withDescription(taskEntity.getDescription())
                    .withSchedule(cronSchedule(taskEntity.getCronExpression())).build();
            stdScheduler.scheduleJob(jobDetail,cronTrigger);
            return "SUCCESS";
        } catch (ObjectAlreadyExistsException e) {
            e.printStackTrace();
            return e.getMessage();
        }catch (RuntimeException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (SchedulerException e) {
            e.printStackTrace();
            return e.getMessage();
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }

    }

    public Boolean deleteTask(String triggerName){
        try {
            TriggerKey triggerKey=new TriggerKey(triggerName, null);
            JobKey jobKey=new JobKey(triggerName,null);
            stdScheduler.unscheduleJob(triggerKey);
            stdScheduler.deleteJob(jobKey);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String modifySchedule(TaskEntity taskEntity){
        try {
            JobKey jobKey=new JobKey(taskEntity.getTaskService(),null);
            JobDetail jobDetail=stdScheduler.getJobDetail(jobKey);

            CronTrigger cronTrigger=newTrigger().forJob(jobDetail).withIdentity(taskEntity.getTaskService(), Scheduler.DEFAULT_GROUP)
                    .withDescription(taskEntity.getDescription())
                    .withSchedule(cronSchedule(taskEntity.getCronExpression())).build();

            TriggerKey triggerKey=new TriggerKey(taskEntity.getTaskService(), null);
            stdScheduler.rescheduleJob(triggerKey,cronTrigger);
            return "SUCCESS";
        } catch (ObjectAlreadyExistsException e) {
            e.printStackTrace();
            return e.getMessage();
        }catch (RuntimeException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (SchedulerException e) {
            e.printStackTrace();
            return e.getMessage();
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public Boolean pauseTase(String triggerName){
        try {
            TriggerKey triggerKey=new TriggerKey(triggerName, null);
            stdScheduler.pauseTrigger(triggerKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean resumeTask(String triggerName){
        try {
            TriggerKey triggerKey=new TriggerKey(triggerName, null);
            stdScheduler.resumeTrigger(triggerKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CronTriggerWithState> getAllTask(){
        List<CronTriggerWithState> list=new ArrayList<>();
        try {
            Set<TriggerKey> triggerKeys=stdScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup());
            for(TriggerKey key:triggerKeys){
                CronTriggerWithState trigger=new CronTriggerWithState();
                trigger.setCronTrigger((CronTrigger) stdScheduler.getTrigger(key));
                trigger.setStateName(stdScheduler.getTriggerState(key).name());
                list.add(trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Boolean testTask(String taskService){
        try {
            Job job=(Job) StartWebApplication.applicationContext.getBean(taskService);
            job.execute(null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
