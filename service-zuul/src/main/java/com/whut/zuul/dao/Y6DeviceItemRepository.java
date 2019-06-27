package com.whut.zuul.dao;

import com.whut.zuul.domain.Y6DeviceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Y6DeviceItemRepository extends JpaRepository<Y6DeviceItem,Integer> {
    List<Y6DeviceItem> findBySectionAndContentindex(String section, String contentindex);
}
