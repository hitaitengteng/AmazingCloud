package com.whut.analyst.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConfig {
    private static final String HOST = "192.168.1.2";
    private static final int PORT = 27017;
    private static final String DB_NAME = "dbcarbon";
    private static MongoDatabase connection = null;
    public static final String CORR_COLLECTION = "corr_collection";

    static {
        // 连接到MongoDB
        MongoClient mongo = new MongoClient(HOST, PORT);
        // 打开数据库
        connection = mongo.getDatabase(DB_NAME);
    }

    public static MongoDatabase getConnection() {
        return connection;
    }
}
