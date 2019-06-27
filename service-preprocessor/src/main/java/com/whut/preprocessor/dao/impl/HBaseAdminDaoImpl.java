package com.whut.preprocessor.dao.impl;

import com.whut.preprocessor.config.HBaseConfig;
import com.whut.preprocessor.dao.IHBaseAdminDao;
import com.whut.preprocessor.util.HBaseDaoUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by 杨赟 on 2018-07-12.
 */
@Component
public class HBaseAdminDaoImpl implements IHBaseAdminDao {

    private static final Admin admin = HBaseConfig.getAdmin();

    static {
        new HBaseAdminDaoImpl().createTable(HBaseConfig.TABLE_NAME, HBaseConfig.SPILT_KEYS, HBaseConfig.FAMILY);
    }
    /**
     * 创建表
     * @param tableName 表名
     * @param familyNames 列族名
     * */
    @Override
    public void createTable(String tableName, String[] spiltKeys, String... familyNames){
        try {
            if (admin.tableExists(TableName.valueOf(tableName))) {
                return;
            }
            //通过HTableDescriptor类来描述一个表，HColumnDescriptor描述一个列族
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            for (String familyName : familyNames) {
                tableDescriptor.addFamily(new HColumnDescriptor(familyName));
            }
            byte[][] bytes = new byte[spiltKeys.length][];
            for (int i = 0; i < spiltKeys.length; ++i)
            {
                bytes[i] = spiltKeys[i].getBytes();
            }
            admin.createTable(tableDescriptor,bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 删除表
     * @param tableName 表名
     * */
    @Override
    public void dropTable(String tableName){
        //删除之前要将表disable
        try {
            if (!admin.isTableDisabled(TableName.valueOf(tableName))) {
                admin.disableTable(TableName.valueOf(tableName));
                admin.deleteTable(TableName.valueOf(tableName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * 修改列属性[也可在创建时设置]
     *
     * @param tableName
     * @param family
     * @param maxVersions
     */
    @Override
    public void modifyFamilyVersion(String tableName, String family, int maxVersions){
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(family)) {
            return;
        }
        TableName tn = TableName.valueOf(tableName);
        HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(family);
        hColumnDescriptor.setMaxVersions(maxVersions);
        try {
            admin.disableTable(tn);
            admin.modifyColumn(tn, hColumnDescriptor);
            admin.enableTable(tn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 添加列
     *
     * @param tableName
     * @param family
     * @param maxVersions
     */
    @Override
    public void addFamily(String tableName, String family, int maxVersions){
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(family)) {
            return;
        }
        TableName tn = TableName.valueOf(tableName);
        HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(family);
        hColumnDescriptor.setMaxVersions(maxVersions);
        try {
            admin.disableTable(tn);
            admin.addColumn(tn, hColumnDescriptor);
            admin.enableTable(tn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
