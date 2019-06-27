package com.whut.zuul.Service.impl;

import com.whut.zuul.domain.ExcelItem;
import org.json.JSONArray;
import org.json.JSONObject;
import com.whut.zuul.Service.ExcelExportService;
import com.whut.zuul.dao.ExcelExportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {
    private final
    ExcelExportRepository excelExportRepository;

    @Autowired
    public ExcelExportServiceImpl(ExcelExportRepository excelExportRepository) {
        this.excelExportRepository = excelExportRepository;
    }

    @Override
    public String findByItem(String item) {
        List<ExcelItem> byItem = excelExportRepository.findByItem(item);
        JSONArray jsonArray = new JSONArray();
        for (ExcelItem eitem:byItem){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tag",eitem.getTag());
            jsonObject.put("desc",eitem.getDesc());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }
}
