package com.whut.zuul.Service.impl;

import com.alibaba.fastjson.JSON;
import com.whut.zuul.Service.SectionAndContent;
import com.whut.zuul.dao.WorkSectionDao;
import com.whut.zuul.domain.Worksection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectionAndContentImpl implements SectionAndContent {

    @Autowired
    WorkSectionDao workSectionDao;

    @Override
    public String findAllSectionToJson() {
        List<Worksection> byAll = workSectionDao.findAll();
        List<Worksection> newByAll =new ArrayList<>();
        Worksection worksection = new Worksection("全选/反选", "", "0");
        newByAll.add(worksection);
        for(int i=0;i<byAll.size();i++){
            newByAll.add(byAll.get(i));
        }

        String s = JSON.toJSONString(newByAll);
        return s;
    }

    @Override
    public String findContentToJson(String[] id) {

        return null;
    }
}
