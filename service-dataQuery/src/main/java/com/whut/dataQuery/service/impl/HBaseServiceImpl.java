package com.whut.dataQuery.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whut.dataQuery.config.HBaseConfig;
import com.whut.dataQuery.enumerate.DCSPoint;
import com.whut.dataQuery.service.IHBaseService;
import com.whut.dataQuery.dao.IHBaseDao;
import com.whut.dataQuery.util.HBaseDaoUtil;
import com.whut.dataQuery.util.TimeUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hbase.thirdparty.org.apache.commons.collections4.map.HashedMap;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

/**
 * Created by 杨赟 on 2018-07-16.
 */
@Service
public class HBaseServiceImpl implements IHBaseService {

    private static final Logger logger = Logger.getLogger(HBaseServiceImpl.class.getName());

    private final IHBaseDao hBaseDao;
    @Autowired
    public HBaseServiceImpl(IHBaseDao hBaseDao) {
        this.hBaseDao = hBaseDao;
    }

    /**
     * @since 18-12-7 下午1:54
     * @author 杨赟
     * @describe 按标签名查询
     * @param tag 标签名(y6_ai08)
     * @return 查询结果
     */
    @Override
    public JSONObject find(String startTime, String stopTime, String tag)
    {
        JSONObject data = new JSONObject();
        JSONArray times = new JSONArray();
        JSONArray values = new JSONArray();
        String startHBaeTime = TimeUtil.time2HBaseTime(startTime);
        String stopHBaseTime = TimeUtil.time2HBaseTime(stopTime);
        String startRow = HBaseDaoUtil.hashName(tag) + startHBaeTime.substring(0,10);
        String stopRow = HBaseDaoUtil.hashName(tag) + stopHBaseTime.substring(0,10);
        Scan scan = new Scan();
        scan = HBaseDaoUtil.rowStart(scan,startRow);
        scan = HBaseDaoUtil.rowStop(scan,stopRow);
        for (Result result : hBaseDao.scan(HBaseConfig.TABLE_NAME, scan)) {

            final String ymdh = Bytes.toString(result.getRow()).substring(6,16);
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                String ms = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                String datetime = TimeUtil.hBaseTime2Time(ymdh, ms);
                if (datetime.compareTo(startTime) < 0)
                {
                    continue;
                }
                if (datetime.compareTo(stopTime) >= 0)
                {
                    break;
                }
                String v = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                times.add(datetime);
                values.add(v);
            }
        }
        data.put("times", times);
        data.put("values", values);
        return data;
    }

    @Override
    public JSONObject finds(String startTime, String stopTime, String[] tags) {
        JSONObject data = new JSONObject();
        for (String tag : tags) {
            data.put(tag, find(startTime,stopTime,tag));
        }
        return data;
    }


    /**
     * 非结构化数据转结构化数据
     * @author yy
     * @param findsResult the output of finds function in HBaseServiceImpl
     * @return filename
     */
    @Override
    public Map<String,String[]> structuring(JSONObject findsResult){
        if (findsResult.isEmpty())
        {
            return null;
        }
        int i, j;
        Set<String> timeSet = new TreeSet<>();
        for (String tag : findsResult.keySet())
        {
            JSONArray times = findsResult.getJSONObject(tag).getJSONArray("times");
            for (i = 0; i < times.size(); ++i){
                timeSet.add(times.getString(i));
            }
        }
        Map<String,String[]> result = new HashedMap<>();
        String[] timeArray = new String[timeSet.size()];
        timeSet.toArray(timeArray);
        String[] valueArray = new String[timeSet.size()];
        for (String tag : findsResult.keySet())
        {
            JSONArray times = findsResult.getJSONObject(tag).getJSONArray("times");
            JSONArray values = findsResult.getJSONObject(tag).getJSONArray("values");
            i = 0; j = 0;
            while (i < timeArray.length && j < times.size())
            {
                if (timeArray[i].compareTo(times.getString(j)) > 0)
                {
                    ++j;
                    continue;
                }
                if (timeArray[i].compareTo(times.getString(j)) < 0)
                {
                    valueArray[i++] = values.getString(j == 0 ? 0 : j-1);
                }
                else
                {
                    valueArray[i++] = values.getString(j++);
                }
            }
            result.put(tag, valueArray);
        }
        result.put("times",timeArray);
        return result;
    }

    @Override
    public String toCSV(String startTime, String stopTime, String[] tags) {

        long start = TimeUtil.date2Timestamp(startTime);
        long stop = TimeUtil.date2Timestamp(stopTime);
        int threads = (int) ((stop-start) / 3600000);
        if (threads >= 2)
        {
            return toCSV(startTime,stopTime,tags,threads> 5 ? 5 : threads);
        }
        else
        {
            return toCSV(startTime,stopTime,tags,true);
        }
    }

    private String toCSV(String startTime, String stopTime, String[] tags, boolean printHead) {
        //文件名
        String filename = "/tmp/"+ DigestUtils.md5DigestAsHex((startTime+stopTime+Arrays.toString(tags)).getBytes())+".csv";
        //打开文件
        BufferedWriter writer = null;
        //系统换行符
        String separator = "\r\n";
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            //打印表头
            StringBuilder builder = new StringBuilder("Time");
            if (printHead)
            {
                for (String tag : tags) {
                    builder.append(",").append(tag);
                }
                builder.append(separator);
                builder.append("时间");
                for (String tag : tags) {
                    builder.append(",").append(DCSPoint.valueOf(tag.toUpperCase()).getDescription());
                }
                builder.append(separator);
                writer.write(builder.toString());
                writer.flush();
            }
            //设置查询条件
            JSONObject data = finds(startTime,stopTime,tags);
            Map<String,String[]> structuredMap = structuring(data);
            String[] times = structuredMap.get("times");
            for (int i = 0; i < times.length; ++i) {
                builder = new StringBuilder(times[i]);
                for (String tag : tags)
                {
                    builder.append(",").append(structuredMap.get(tag)[i]);
                }
                builder.append(separator);
                writer.write(builder.toString());
            }
            writer.flush();
            writer.close();
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    private String toCSV(String startTime, String stopTime, String[] tags, int threads) {
        long start = TimeUtil.date2Timestamp(startTime);
        long stop = TimeUtil.date2Timestamp(stopTime);
        long internal = (stop - start) / threads;
        String[] spiltKeys = new String[threads+1];
        CountDownLatch countDownLatch = new CountDownLatch(threads);
        int i;
        for (i = 0; i < spiltKeys.length; ++i)
        {
            spiltKeys[i] = TimeUtil.timestamp2Date(start + i * internal);
        }
        Map<Integer, String> fileMap = Collections.synchronizedSortedMap(new TreeMap<>()) ;
        for (i = 0; i < threads; ++i)
        {
            final Integer id = i;
            new Thread(()->{
                String filename;
                if (id == 0)
                {
                    filename = toCSV(spiltKeys[id],spiltKeys[id+1],tags,true);
                }
                else
                {
                    filename = toCSV(spiltKeys[id],spiltKeys[id+1],tags,false);
                }
                fileMap.put(id,filename);
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //
        String[] paths  = new String[fileMap.size()];
        fileMap.values().toArray(paths);
        //文件名
        String filename = "/tmp/"+ DigestUtils.md5DigestAsHex((startTime+stopTime+Arrays.toString(tags)).getBytes())+".csv";
        if (mergeFiles(paths,filename))
        {
            return filename;
        }
        return null;
    }

    private boolean mergeFiles(String[] paths, String resultPath)
    {
        if (paths == null || paths.length < 1 || TextUtils.isEmpty(resultPath)) {
            return false;
        }
        if (paths.length == 1) {
            return new File(paths[0]).renameTo(new File(resultPath));
        }

        File[] files = new File[paths.length];
        for (int i = 0; i < paths.length; i ++) {
            files[i] = new File(paths[i]);
            if (TextUtils.isEmpty(paths[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try {
            FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();
            for (int i = 0; i < paths.length; i ++) {
                FileChannel blk = new FileInputStream(files[i]).getChannel();
                resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                blk.close();
            }
            resultFileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        for (int i = 0; i < paths.length; i ++) {
            files[i].delete();
        }
        return true;
    }
}
