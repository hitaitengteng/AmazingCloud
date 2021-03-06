package com.whut.dataQuery.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by 杨赟 on 2018-06-12.
 */
public class TimeUtil {

    /**
     * @since 18-12-7 下午2:44
     * @author 杨赟
     * @describe 时间戳转日期
     */
    public static String timestamp2Date(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }
    public static String timestamp2HBaseTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date(timestamp));
    }

    /**
     * @since 18-12-7 下午2:45
     * @author 杨赟
     * @describe 日期转时间戳
     */
    public static long date2Timestamp(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static long dayStartTime(long nowTime){
        return nowTime - (nowTime + TimeZone.getDefault().getRawOffset())% (1000*3600*24);
    }


    public static String time2HBaseTime(String dataTime){
        return dataTime.replaceAll("-","").replaceAll(":","").replaceAll(" ","");
    }
    public static String hBaseTime2Time(String ymdh, String ms){
        return String.format("%s-%s-%s %s:%s:%s", ymdh.substring(0,4),
                ymdh.substring(4,6),
                ymdh.substring(6,8),
                ymdh.substring(8,10),
                ms.substring(0,2),
                ms.substring(2,4));
    }
}
