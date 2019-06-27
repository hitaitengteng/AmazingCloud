package com.whut.zuul.Service.impl;

import com.whut.zuul.Service.Y6DeviceItemService;
import com.whut.zuul.dao.Y6DeviceItemRepository;
import com.whut.zuul.domain.Y6DeviceItem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Y6DeviceItemServiceImpl implements Y6DeviceItemService {
    private final
    Y6DeviceItemRepository deviceItemRepository;

    @Autowired
    public Y6DeviceItemServiceImpl(Y6DeviceItemRepository deviceItemRepository) {
        this.deviceItemRepository = deviceItemRepository;
    }

    @Override
    public String findBySectionAndContentIndex(String section, String contentindex) {
        List<Y6DeviceItem> bySectionAndContentindex = deviceItemRepository.findBySectionAndContentindex(section, contentindex);
        JSONArray jsonArray = new JSONArray();
        for (Y6DeviceItem item:bySectionAndContentindex){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",item.getTag());
            jsonObject.put("desc",item.getDesc());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }
}
