package com.whut.zuul.dao;

import com.whut.zuul.domain.Worksection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkSectionDao extends JpaRepository<Worksection,Long> {
    /*=======================查询所有的工段=====================*/
    List<Worksection> findAll();
    /*=======================根据工段内容查询===================*/
    Worksection findById(String id);
}
