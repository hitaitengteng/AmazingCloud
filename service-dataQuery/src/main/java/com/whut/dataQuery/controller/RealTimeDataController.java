package com.whut.dataQuery.controller;

import com.alibaba.fastjson.JSONObject;
import com.whut.dataQuery.config.RedisConfig;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yy
 * @describe
 * @time 18-12-25 下午1:53
 * 提供获取实时数据的接口
 */
@RestController
public class RealTimeDataController {

    /**
     * @since 2018-07-18
     * @author 杨赟
     * @describe 查询一个控制项目
     * @return 查询结果
     */
    @RequestMapping(value = "/currentValue", method = RequestMethod.GET)
    @ResponseBody
    public Object currentValue(String callback, HttpServletRequest request)
    {
        JSONObject result = new JSONObject();
        String tag = request.getParameter("tag");
        Jedis jedis = RedisConfig.getJedis();
        String data;
        if (jedis == null)
        {
            data = "null";
        }
        else
        {
            data = jedis.get(tag);
            result.put("data",data);
        }
        RedisConfig.returnResource(jedis);
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

    /**
     * @since 2018-07-18
     * @author 杨赟
     * @describe 查询一个控制项目
     * @return 查询结果
     */
    @RequestMapping(value = "/currentValues", method = RequestMethod.POST)
    @ResponseBody
    public Object currentValues(HttpServletRequest request)
    {
        JSONObject result = new JSONObject();
        String[] tags = request.getParameter("tags").split(",");
        JSONObject values = new JSONObject();
        Jedis jedis = RedisConfig.getJedis();
        assert jedis != null;
        String data;
        for (String tag : tags)
        {
            data = jedis.get(tag);
            values.put(tag,data);
        }
        RedisConfig.returnResource(jedis);
        result.put("success",true);
        result.put("message","");
        result.put("data",values);
        return result;
    }
}
