package com.whut.zuul.controller;


import com.whut.zuul.Service.impl.SectionAndContentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SectionAndContent {

    @Autowired
    SectionAndContentImpl sectionAndContent;


    /*选择工段*/
    @RequestMapping(value = "/section",method = RequestMethod.POST)
    public String findSection(){
        String allSectionToJson = sectionAndContent.findAllSectionToJson();
        return allSectionToJson;
    }


    /*选择工段对应的内容*/
    @RequestMapping(value = "/content",method = RequestMethod.POST)
    public String findContent(@RequestBody String id){
        String[] split = id.split("&");
        System.out.println("选择的工段："+split);
        String contentToJson = sectionAndContent.findContentToJson(split);
        return contentToJson;
    }
}
