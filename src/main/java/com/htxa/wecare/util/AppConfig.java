package com.htxa.wecare.util;

import com.htxa.wecare.model.TaskEntity;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time:2017/8/14 11:22
 */
@Getter
@Component
@ConfigurationProperties(prefix="task")
public class AppConfig {
    private final List<TaskEntity> list = new ArrayList<>();
}
