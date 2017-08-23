package com.htxa.wecare.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Description:
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time: 2017/8/17 17:19
 */
@Entity(name = "QRTZ_TRIGGERS")
@Getter
@Setter
public class QTriggers {

    @Id
    @Column(name = "TRIGGER_NAME", length = 200)
    private String triggerName;

    @Column(name = "SCHED_NAME", length = 120)
    private String schedName;

    @Column(name = "TRIGGER_GROUP", length = 200)
    private String triggerGroup;

    @Column(name = "JOB_NAME", length = 200)
    private String jobName;

    @Column(name = "JOB_GROUP", length = 200)
    private String jobGroup;

    @Column(name = "DESCRIPTION", length = 250)
    private String description;

    @Column(name = "NEXT_FIRE_TIME", length = 13)
    private long nextFireTime;

    @Column(name = "PREV_FIRE_TIME", length = 13)
    private long prevFireTime;

    @Column(name = "PRIORITY", length = 2)
    private int priority;

    @Column(name = "TRIGGER_STATE", length = 16)
    private String triggerState;

    @Column(name = "TRIGGER_TYPE", length = 8)
    private String triggerType;

    @Column(name = "START_TIME", length = 13)
    private long start_time;

    @Column(name = "END_TIME", length = 13)
    private long endTime;

    @Column(name = "CALENDAR_NAME", length = 200)
    private String calendarName;

    @Column(name = "MISFIRE_INSTR", length = 2)
    private int misfireInstr;

    @Column(name = "USER_NAME")
    private byte[] jobData;
}
