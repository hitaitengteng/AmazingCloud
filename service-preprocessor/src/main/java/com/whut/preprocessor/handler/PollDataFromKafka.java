package com.whut.preprocessor.handler;

import com.alibaba.fastjson.JSONObject;
import com.whut.preprocessor.config.HBaseConfig;
import com.whut.preprocessor.config.KafkaConfig;
import com.whut.preprocessor.config.RedisConfig;
import com.whut.preprocessor.dao.IHBaseDao;
import com.whut.preprocessor.enumerate.DCSPoint;
import com.whut.preprocessor.enumerate.DataType;
import com.whut.preprocessor.enumerate.Section;
import com.whut.preprocessor.util.HBaseDaoUtil;
import com.whut.preprocessor.util.TimeUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

/**
 * Created by 杨赟 on 18-7-27
 */
@Service
@EnableScheduling
public class PollDataFromKafka {

    private static final Logger logger = Logger.getLogger(PollDataFromKafka.class.getName());

    private static final
    KafkaConsumer<String, String> consumer;

    static {
        consumer = KafkaConfig.getConsumer();
        consumer.subscribe(Collections.singletonList(KafkaConfig.topic));
    }

    private final IHBaseDao hBaseDao;

    @Autowired
    public PollDataFromKafka(IHBaseDao hBaseDao) {
        this.hBaseDao = hBaseDao;
    }

    /**
     * @author 杨赟
     * @describe 预处理任务
     * @since 18-7-26 下午5:43
     */
    @Scheduled(fixedRate = 5000)
    public void processing()
    {
        ConsumerRecords<String, String> records = consumer.poll(2000);
        if (records.count() == 0)
        {
            return;
        }
        Iterator<ConsumerRecord<String, String>> iterator = records.iterator();
        CountDownLatch countDownLatch = new CountDownLatch(records.count());
        ConsumerRecord array[] = new ConsumerRecord[records.count()];
        int i;
        for (i = 0; i < records.count(); ++i)
        {
            array[i] = iterator.next();
        }
        for (i = 0; i < records.count(); ++i){
            final int id = i;
            if (id < records.count() - 1)
            {
                new Thread(()-> {
                    disposeOneRecord(array[id],false);
                    countDownLatch.countDown();
                }).start();
            }
            else
            {
                new Thread(()-> {
                    disposeOneRecord(array[id],true);
                    countDownLatch.countDown();
                }).start();
            }
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumer.commitAsync();
        logger.info(String.format("Successfully processing %d records", records.count()));
    }

    private void disposeOneRecord(ConsumerRecord<String, String> record, boolean saveInRedis)
    {
        DCSPoint point;
        String dateTime, rowKey, qualifier, value;
        List<Put> putList = new ArrayList<>();
        Map<String,Object> tagAndValue = JSONObject.parseObject(record.value()).getInnerMap();
        for (String tag : tagAndValue.keySet()) {
            point = DCSPoint.valueOf(tag.toUpperCase());
            if (point.getSection() == Section.UNKNOWN || point.getDataType() != DataType.REAL)
            {
                continue;
            }
            dateTime = TimeUtil.timestamp2DateTime(Long.parseLong(record.key()));
            rowKey= HBaseDaoUtil.hashName(tag) + dateTime.substring(0,10);
            qualifier = dateTime.substring(10,14);
            value = tagAndValue.get(tag).toString();
            if (saveInRedis)
            {
                RedisConfig.masterRedis.set(tag, value);
            }
            putList.add(HBaseDaoUtil.cellPut(rowKey, HBaseConfig.FAMILY, qualifier,value));
        }
        hBaseDao.adds(HBaseConfig.TABLE_NAME, putList);
    }
}

