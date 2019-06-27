package com.whut.preprocessor.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

/**
 * @author yy
 * @describe
 * @time 18-10-13 下午3:44
 */
public class KafkaConfig {

    private static Properties props = new Properties();

    public static final String topic = "DCS-TOPIC";

    static {
        props.put("bootstrap.servers", "59.69.101.206:49092,59.69.101.206:49093,59.69.101.206:49094");
        props.put("group.id", "com.lou");
        //设置手动提交偏移量
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "5000");
        props.put("max.poll.records", 5);
        props.put("max.poll.interval.ms", "30000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }
    public static KafkaConsumer<String, String> getConsumer(){
        return new KafkaConsumer<String,String>(props);
    }
    public static void returnConsumer(KafkaConsumer<String, String> consumer){
        consumer.close();
    }
}
