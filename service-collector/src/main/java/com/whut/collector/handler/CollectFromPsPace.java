package com.whut.collector.handler;

import bjzr.util.rtdb.DynamicData;
import bjzr.util.rtdb.HistoryData;
import bjzr.util.rtdb.Point;
import com.alibaba.fastjson.JSONObject;
import com.whut.collector.config.CollectorConfig;
import com.whut.collector.dao.PSPaceDao;
import com.whut.collector.enumerate.DCSPoint;
import com.whut.collector.util.KafkaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

/**
 * @author yy
 * @describe 从实时库获取数据
 * @time 18-11-30 下午2:28
 */
//@Service
//@EnableScheduling
public class CollectFromPsPace {

    private static final Logger logger = Logger.getLogger(CollectFromPsPace.class.getName());

    private final PSPaceDao psPaceDao;

    private final String[] fullNames;

    @Autowired
    public CollectFromPsPace(PSPaceDao psPaceDao) {
        this.psPaceDao = psPaceDao;
        DCSPoint[] points = DCSPoint.values();
        fullNames = new String[points.length];
        for (int i = 0; i < points.length; ++i)
        {
            fullNames[i] = points[i].getFullName();
        }
    }

    @PostConstruct
    private void init()
    {
        if (CollectorConfig.loadConfigFile("collector-site.xml")) return;
        CollectorConfig.showConfig();
        subscribe();
    }

    private void subscribe()
    {
        long[] tags = psPaceDao.getPointIDbyFullName(fullNames);
        if (tags == null)
        {
            logger.warning("测点表的测点不存在！！！");
            return;
        }
        DynamicData[] dynamicData = new DynamicData[tags.length];
        psPaceDao.subscribeByIds(tags,dynamicData);
    }

    @PostConstruct
    @Scheduled(cron = "0 0 * * * ?")
    private void requestAll() throws InterruptedException {
        GregorianCalendar start = new GregorianCalendar();
        GregorianCalendar end = new GregorianCalendar();
        start.setTime(new Date());
        Thread.sleep(1);
        end.setTime(new Date());
        JSONObject object = new JSONObject();
        long currentTime = System.currentTimeMillis();
        HistoryData[] historyData = psPaceDao.getHistoryDataByNames(fullNames,start,end);
        for (int i=0;i!=historyData.length;++i){
            String tag = fullNames[i].substring(fullNames[i].lastIndexOf("\\")+1);
            DynamicData[] dynamicData = historyData[i].getDataValues();
            object.put(tag,dynamicData[0] == null ? null : dynamicData[0].value());
        }
        KafkaUtil.sendData(currentTime,object);
        logger.info("Successfully acquired all points data");
    }
}
