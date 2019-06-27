package com.whut.zuul.controller;

import com.whut.zuul.Service.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ExcelExportController {
    private final ExcelExportService excelExportService;

    @Autowired
    public ExcelExportController(ExcelExportService excelExportService){

        this.excelExportService=excelExportService;
    }

    @RequestMapping(value = "/findSectionByItemIndex", method = RequestMethod.POST)
    @ResponseBody
    public String findItemTag(HttpServletRequest request){
        String item = request.getParameter("itemIndex");
        String byItem = excelExportService.findByItem(item);
        return byItem;
    }
}
