package com.whut.zuul.Service;

import org.springframework.stereotype.Component;

@Component
public interface ExcelExportService {
    public String findByItem(String item);
}
