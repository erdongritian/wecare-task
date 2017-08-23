package com.htxa.wecare.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time: 2017/8/22 14:31
 */
public class CommonUtil {

    public static Map<String, Object> commonResponse(Boolean status, String message){
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", message);
        return map;
    }
}
