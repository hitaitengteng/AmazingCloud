package com.whut.dataQuery.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author yy
 * @describe
 * @time 18-12-25 下午4:36
 */
public class HttpCheckUtil {

    public static boolean check(String startTime, String stopTime, String tags)
    {
        if (startTime == null || stopTime == null || tags == null)
        {
            return false;
        }
        if (TimeUtil.date2Timestamp(startTime) > TimeUtil.date2Timestamp(stopTime))
        {
            return false;
        }
        String[] spilt = tags.split(",");
        for (String tag : spilt)
        {
            if (tag.split("_").length != 2) return false;
        }
        return true;
    }
}
