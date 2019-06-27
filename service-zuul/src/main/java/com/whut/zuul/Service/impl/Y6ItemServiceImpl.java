package com.whut.zuul.Service.impl;

import com.whut.zuul.Service.Y6ItemService;
import com.whut.zuul.dao.HBaseRestServiceDao;
import com.whut.zuul.dao.Y6ItemRepository;
import com.whut.zuul.domain.Y6Item;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Y6ItemServiceImpl implements Y6ItemService {
    private final
    Y6ItemRepository factoryRepository;

    @Autowired
    private  HBaseRestServiceDao hBaseRestServiceDao;
    @Autowired
    public Y6ItemServiceImpl(Y6ItemRepository factoryRepository) {
        this.factoryRepository = factoryRepository;
    }

    @Override
    public String findByY6Item(String factory) {
        List<Y6Item> byFactory = factoryRepository.findByFactory(factory);
        JSONObject array = new JSONObject();
        JSONArray data = new JSONArray();
        for (Y6Item item:byFactory){
            JSONObject object = new JSONObject();
            object.put("tag",item.getTag());
            object.put("desc",item.getDesc());
            data.put(object);
        }
        array.put("data",data);
        return array.toString();
    }

    @Override
    public String findByTag(String tags) {
        //hBaseRestServiceDao.findSingleOnlineDataByTags(tags,currtime);
        String[] split = tags.split(",");
        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String ss:split){
            JSONObject jsonObject = new JSONObject();
            Y6Item byTag = factoryRepository.findByTag(ss);
            if(byTag!=null){
                jsonObject.put("tag",byTag.getTag());
                jsonObject.put("desc",byTag.getDesc());
                jsonObject.put("address",byTag.getAddress());
                jsonObject.put("type",byTag.getType());
                jsonArray.put(jsonObject);
            }else {
                jsonObject.put("tag","null");
                jsonObject.put("desc","null");
                jsonObject.put("address","null");
                jsonObject.put("type","null");
                jsonArray.put(jsonObject);
            }
        }
        object.put("content",jsonArray);
        return object.toString();
    }
}
