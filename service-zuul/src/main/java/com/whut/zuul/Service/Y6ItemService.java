package com.whut.zuul.Service;

import org.springframework.stereotype.Component;

@Component
public interface Y6ItemService {
    String findByY6Item(String factory);
    public String findByTag(String tags);
}
