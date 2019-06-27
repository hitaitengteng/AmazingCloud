package com.whut.zuul.controller;


import com.whut.zuul.Service.Y6DeviceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Y6DeviceController {
    private final Y6DeviceItemService deviceItemService;

    @Autowired
    public Y6DeviceController(Y6DeviceItemService deviceItemService){
        this.deviceItemService=deviceItemService;
    }

    @RequestMapping(value = "/findDeviceBySectionAndContentIndex", method = RequestMethod.POST)
    @ResponseBody
    public String findDeviceBySectionAndContentIndex(HttpServletRequest request){
        String section = request.getParameter("section");
        String contentIndex = request.getParameter("contentIndex");
        return deviceItemService.findBySectionAndContentIndex(section, contentIndex);
    }

}
