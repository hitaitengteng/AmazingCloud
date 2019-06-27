package com.whut.analyst.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;


/**
 * Created by 杨赟 on 2018-07-16.
 */
public class HBaseConfig {

    //表
    public static final String TABLE_NAME = "fbg";
    public static final String FAMILY1 = "tmp";
    public static final String FAMILY2 = "str";
    public static final String FAMILY3 = "acc";

    private static final String ROW_KEY = "%01d%01d%01d%01d%10d";
    private static final String QUALIFIER = "%01d%01d";

    private static Connection connection = null;
    private static Admin admin = null;
    static {
        try {
            //获得Configuration实例并进行相关设置
            Configuration conf = HBaseConfiguration.create();
//            conf.set("hbase.rootdir","hdfs://localhost:9000/hbase");
            //获得Connection实例
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
    public static Admin getAdmin() {
        return admin;
    }

    public static void close(){
        if(admin!=null)
        {
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(connection != null)
        {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getRowKey(int clamp, int frequency, int pressure, double openingDegree, long timestamp){
        return String.format(
                ROW_KEY,
                clamp,
                frequency == 5 ? 0 : 1,
                pressure == 21 ? 0 : 1,
                openingDegree == 5 ? 0 : (openingDegree == 10 ? 2 : 1),
                timestamp
        );
    }
    public static String getQualifier(int channel, int point){
        return String.format(QUALIFIER, channel, point);
    }
}
