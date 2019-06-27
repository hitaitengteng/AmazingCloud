package com.whut.preprocessor.util;

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
    public static String timestamp2DateTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date(timestamp));
    }

    /**
     * @since 18-12-7 下午2:45
     * @author 杨赟
     * @describe 日期转时间戳
     */
    public static long dateTime2Timestamp(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(timestamp2DateTime(System.currentTimeMillis()));
    }
}
