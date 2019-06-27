package com.whut.analyst.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by YY on 2018-03-25.
 */
public class TimeFormatUtil {

    public static String getCurrentMsTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
    }
    public static String getCurrentSTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    public static String getCurrentDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
    public static String getLastDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return sdf.format(date);
    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToTime(long ts,String format){
        return new SimpleDateFormat(format).format( new Date(ts));
    }
    /*
     * Date 转换为时间
     */
    public static String dateToTime(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static long utcStr2Stamp(String dateTime,String format)
    {
        dateTime = dateTime.replace("Z", " UTC");//注意是空格+UTC
        SimpleDateFormat f = new SimpleDateFormat(format);//注意格式化的表达式
        Date d = null;
        try {
            d = f.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert d != null;
        return d.getTime();
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的时间戳
     * @param format
     * @return
     */
    public static String timestamp2Date(long seconds,String format) {

        if(format == null || format.isEmpty()){
            format = "yyyy/MM/dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }
    /**
     * 日期格式字符串转换成时间戳
     * @param date 字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long date2Timestamp(String date,String format){

        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime()/1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
