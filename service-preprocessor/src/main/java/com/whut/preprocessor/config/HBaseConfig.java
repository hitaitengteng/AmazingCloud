package com.whut.preprocessor.config;

import com.whut.preprocessor.util.HBaseDaoUtil;
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

    public static final String TABLE_NAME = "DCS-TABLE";
    public static final String FAMILY = "f";
    public static final String[] SPILT_KEYS = HBaseDaoUtil.getSpiltKeys(3);
    private static Connection connection = null;
    private static Admin admin = null;
    static {
        try {
            Configuration conf = HBaseConfiguration.create();
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
        if(admin!=null) {
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
}
