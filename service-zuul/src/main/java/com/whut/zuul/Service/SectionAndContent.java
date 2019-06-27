package com.whut.zuul.Service;


public interface SectionAndContent {

    /*===============================将查询到的工段全部转换成jsond的格式返回*=====================*/
    public String findAllSectionToJson();

    public String findContentToJson(String[] id);
}
