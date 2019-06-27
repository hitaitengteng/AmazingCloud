package com.whut.analyst.dao;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yy
 * @describe
 * @time 18-11-10 下午4:28
 */
@Component
public interface IRedisDao {
    void addData(String key, String value);
    Map<String,String> getDayData(int year, int month, int day);
}
