package com.whut.analyst.dao.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoIterable;
import com.whut.analyst.dao.IMongoDBDao;
import com.whut.analyst.config.MongoDBConfig;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 杨赟 on 2018-07-27.
 */
@Component
public class MongoDBDaoImpl implements IMongoDBDao {

    /**
     * @since 18-7-27
     * @author 杨赟
     * @describe
     * @param collection 集合名
     * @param key 键
     * @param value 值
     * @return 找到的第一份文档
     */
    @Override
    public Document findDocument(String collection, String key, Object value){
        return  MongoDBConfig.getConnection().//数据库连接
                getCollection(collection).//集合
                find(new Document(key,value)).//文档迭代器
                first();//第一份
    }

    @Override
    public FindIterable<Document> findDocumentByPage(String collection,int skip, int limit){
        return  MongoDBConfig.getConnection().//数据库连接
                getCollection(collection).//集合
                find().//文档迭代器
                skip(skip).//跳过
                limit(limit);//查询上限
    }

    @Override
    public FindIterable<Document> findDocumentByRange(String collection,String key, Object start, Object end){
        Document range = new Document();
        range.put("$gte",start);
        range.put("$lt",end);
        return MongoDBConfig.getConnection().
                getCollection(collection).
                find(new Document(key,range));
    }

    @Override
    public FindIterable<Document> findDocumentByMinId(String collection,String id, int limit) {
        return findDocumentByMinId(collection,id).limit(limit);
    }
    @Override
    public FindIterable<Document> findDocumentByMinId(String collection,String id) {
        Document min = new Document();
        min.put("$gte",new ObjectId(id));
        return MongoDBConfig.getConnection().
                getCollection(collection).
                find(new Document("_id",min));
    }

    @Override
    public FindIterable<Document> findAllDocument(String collection) {
        return MongoDBConfig.getConnection().
                getCollection(collection).
                find();
    }

    @Override
    public void insertDocument(String collection,Document document){
        MongoDBConfig.getConnection().
                getCollection(collection).
                insertOne(document);
    }

    @Override
    public void insertMultiDocument(String collection,List<Document> documents){
        MongoDBConfig.getConnection().
                getCollection(collection).
                insertMany(documents);
    }

    @Override
    public long countDocument(String collection){
        return MongoDBConfig.getConnection().getCollection(collection).count();
    }

    @Override
    public void createCollection(String name) {
        MongoDBConfig.getConnection().createCollection(name);
    }

    @Override
    public void deleteCollection(String name) {
        MongoDBConfig.getConnection().getCollection(name).drop();
    }

    @Override
    public boolean isCollectionExist(String name) {
        MongoIterable<String> iterable = MongoDBConfig.getConnection().
                listCollectionNames();
        for(String string: iterable){
            if(string.equals(name)){
                return true;
            }
        }
        return false;
    }
}
