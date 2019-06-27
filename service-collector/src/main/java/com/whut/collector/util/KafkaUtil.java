package com.whut.collector.util;

import bjzr.util.rtdb.DynamicData;
import bjzr.util.rtdb.Point;
import com.alibaba.fastjson.JSONObject;
import com.whut.collector.config.CollectorConfig;
import com.whut.collector.config.KafkaConfig;
import com.whut.collector.dao.impl.PSpaceDaoImpl;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.logging.Logger;

/**
 * @author 杨赟
 * @describe
 * @since 18-7-24 上午9:37
 */
public class KafkaUtil {

    private static final Logger logger = Logger.getLogger(KafkaUtil.class.getName());

    private static Producer<String,String> producer = KafkaConfig.getProducer();

    private static JSONObject parseData(long[] tagIds, DynamicData[] dynamicData)
    {
        JSONObject result = new JSONObject();
        JSONObject valueObject = new JSONObject();
        if (dynamicData.length == 0) return null;
        result.put("time",dynamicData[0].time().getTime());
        for (int i = 0; i < dynamicData.length; i++) {
            Point point = PSpaceDaoImpl.oIConnect.getPointInfo(tagIds[i]);
            String name = point.getName();
            Object value = dynamicData[i].value();
            valueObject.put(name,value);
        }
        result.put("value",valueObject);
        return result;
    }
    public static void sendData(long[] tagIds, DynamicData[] dynamicData)
    {
        JSONObject result = parseData(tagIds,dynamicData);
        if (result == null) return;
        String time = String.valueOf(result.getLong("time"));
        String valueObject = result.getJSONObject("value").toJSONString();
        producer.send(new ProducerRecord<>(CollectorConfig.TOPIC, time, valueObject));
    }

    public static void sendData(long timestamp, JSONObject tagValue)
    {
        String time = String.valueOf(timestamp);
        String valueObject = tagValue.toJSONString();
        producer.send(new ProducerRecord<>(CollectorConfig.TOPIC, time, valueObject));
    }
}
