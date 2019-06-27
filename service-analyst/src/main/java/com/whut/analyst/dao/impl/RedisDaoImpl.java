package com.whut.analyst.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whut.analyst.config.RedisConfig;
import com.whut.analyst.dao.IRedisDao;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
public class RedisDaoImpl implements IRedisDao {

    /**
     * @since 18-11-10 下午2:59
     * @author 杨赟
     * @describe kafka存到Redis
     * @param key：Kafka的key
     * @param value：Kafka的value
     * @return void
     */
    @Override
    public void addData(String key, String value){

        //日期,作为Redis的key值
        String date = new SimpleDateFormat("yyyyMMddHHmmss").
                format(new Date(Long.valueOf(key.substring(4,14)+"000")));
        //年月日
        String key1 = date.substring(0,8);
        //时分秒
        String key2 = date.substring(8,14);
        //通道测点和值
        JSONArray array = JSON.parseArray(value);
        //libsvm格式
        StringBuilder line = new StringBuilder(key.substring(0, 1));
        for (int i = 0; i!=array.size();++i){
            JSONObject object =  array.getJSONObject(i);
            String c = object.getString("c");
            String p = object.getString("p");
            String v = object.getString("v");
            line.append(" ").append(c).append(p).append(":").append(v);
        }
        //存储到Redis
        Jedis jedis = RedisConfig.getJedis();
        Objects.requireNonNull(jedis).hset(key1,key2,String.valueOf(line));
        RedisConfig.returnResource(jedis);
    }

    /**
     * @since 18-11-10 下午4:26
     * @author 杨赟
     * @describe 获取一天的标准数据
     * @param year:年
     * @param month:月
     * @param day: 日
     * @return key是时分秒,value是libsvm格式的数据
     */
    @Override
    public Map<String,String> getDayData(int year, int month, int day){

        String dateString = String.format("%04d%02d%02d", year,month,day);
        Jedis jedis = RedisConfig.getJedis();
        assert jedis != null;
        Map<String,String> data = jedis.hgetAll(dateString);
        RedisConfig.returnResource(jedis);
        return data;
    }
}