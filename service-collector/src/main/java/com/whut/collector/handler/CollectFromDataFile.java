package com.whut.collector.handler;

import com.alibaba.fastjson.JSONObject;
import com.whut.collector.config.CollectorConfig;
import com.whut.collector.util.KafkaUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

/**
 * @author yy
 * @describe used for test when we don't have PSpace database
 * @time 18-11-30 下午2:28
 *
 */
@Service
@EnableScheduling
public class CollectFromDataFile {

    private static final Logger logger = Logger.getLogger(CollectFromDataFile.class.getName());

    private static String[] keys;
    private static List<String[]> list = new ArrayList<>();
    private int j = 0;

    @PostConstruct
    private void init() throws IOException {
        if (CollectorConfig.loadConfigFile(null)) return;
        CollectorConfig.showConfig();
        loadData();
    }

    private static void loadData() throws IOException
    {
        File file = new File("data.csv");
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        keys = line.split(",");
        while ((line = bufferedReader.readLine()) !=null)
        {
            String[] values = line.split(",");
            list.add(values);
        }
    }

    @Scheduled(fixedRate = 5000)
    public void sendData()
    {
        JSONObject object = new JSONObject();
        if (j == list.size())
        {
            j = 0;
        }
        for (int i =0;i!=keys.length;++i)
        {
            String[] values = list.get(j);
            object.put("y6_"+keys[i],values[i]);
        }
        j++;
        KafkaUtil.sendData(System.currentTimeMillis(),object);
        logger.info("send data to topic(DCS-TOPIC) successfully, offset:" + j);
    }
}
