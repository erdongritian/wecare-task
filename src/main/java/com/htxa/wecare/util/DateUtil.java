package com.htxa.wecare.util;

/**
 * Description:
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time:2017/8/17 15:39
 */
public class DateUtil {

    /**
     *
     * @param millsecond
     * @return
     */
    public static String formatTime(long millsecond) {
        long ms = millsecond % 1000;
        long second = millsecond / 1000 % 60;
        long minute = millsecond / 1000 / 60 % 60;
        long hour = millsecond / 1000 / 60 / 60;
        //		return "" + hour + " h" + minute + " min" + second + " s" + ms + " ms";
        return "["+minute + " min " + second + " s " + ms + " ms]";
    }

}
