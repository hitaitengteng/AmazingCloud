package com.whut.zuul.Service;

import org.springframework.stereotype.Component;

@Component
public interface Y6DeviceItemService {
    public String findBySectionAndContentIndex(String section, String contentindex);

}
