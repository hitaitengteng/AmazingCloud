package com.whut.dataQuery.service;


import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by 杨赟 on 2018-07-18.
 */
public interface IHBaseService {

    JSONObject find(String startTime, String stopTime, String tag);
    JSONObject finds(String startTime, String stopTime, String[] tags);
    Map<String,String[]> structuring(JSONObject findsResult);
    String toCSV(String startTime, String stopTime, String[] tags);

}
