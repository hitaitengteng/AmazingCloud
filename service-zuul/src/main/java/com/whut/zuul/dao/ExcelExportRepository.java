package com.whut.zuul.dao;

import com.whut.zuul.domain.ExcelItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcelExportRepository extends JpaRepository<ExcelItem,Integer> {
    List<ExcelItem> findByItem(String item);
}
