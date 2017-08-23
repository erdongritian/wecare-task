package com.htxa.wecare.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time:2017/8/14 10:33
 */
@Getter
@Setter
public class TaskEntity {
    private String taskService;
    private String cronExpression;
    private String description;
}
