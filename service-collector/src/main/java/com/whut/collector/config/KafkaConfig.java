package com.whut.collector.config;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class KafkaConfig {

    public static KafkaProducer<String, String> getProducer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", CollectorConfig.SERVERS);
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<String,String>(properties);
    }

    public static void returnProducer(KafkaProducer<String, String> producer){
        producer.close();
    }
}
