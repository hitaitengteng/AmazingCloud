package com.whut.zuul.controller;


import com.whut.zuul.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ServiceController{

    @RequestMapping("/link")
    public String save(HttpServletRequest request) {
        String page = "login/login";
        User user = (User) request.getSession().getAttribute("user");
        String role = user.getRole();
        boolean flag = role.equals("admin") || role.equals("normmal") || role.equals("tourist");
        if(flag){
            page = "dataquery/query_admin";
        }
        return page;
    }

    @RequestMapping("/analysis_admin")
    public String linkToDataAnalysis(HttpServletRequest request){

        return "dataanalysis/analysis_admin";
    }

    @RequestMapping("/query_admin")
    public String linkToDataQuery(HttpServletRequest request){
        return "dataquery/query_admin";
    }

    @RequestMapping("/quality_admin")
    public String linkToQualityPredicition(HttpServletRequest request){
        return "qualitypredicition/quality_admin";
    }
}
