package com.whut.zuul.controller;



import com.whut.zuul.Service.Y6ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Y6ItemController {
    private final Y6ItemService factoryService;
    @Autowired
    public Y6ItemController(Y6ItemService factoryService) {
        this.factoryService = factoryService;
    }

    @RequestMapping(value = "/findAllFactory", method = RequestMethod.POST)
    @ResponseBody
    public String findByY6Item(HttpServletRequest request){
        String section = request.getParameter("section");
        String byY6Item = factoryService.findByY6Item(section);
        return byY6Item;
    }

    @RequestMapping(value = "findAllContentByTags")
    @ResponseBody
    public String queryByTags(HttpServletRequest request){
        String tags = request.getParameter("tags");
        String byTag = factoryService.findByTag(tags);
        return byTag;
    }
}
