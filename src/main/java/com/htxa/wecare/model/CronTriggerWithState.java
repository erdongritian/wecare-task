package com.htxa.wecare.model;

import lombok.Getter;
import lombok.Setter;
import org.quartz.CronTrigger;

/**
 * Description:
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time: 2017/8/21 17:32
 */
@Setter
@Getter
public class CronTriggerWithState{
    private String stateName;
    private CronTrigger cronTrigger;
}
