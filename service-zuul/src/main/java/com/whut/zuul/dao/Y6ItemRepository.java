package com.whut.zuul.dao;

import com.whut.zuul.domain.Y6Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Y6ItemRepository extends JpaRepository<Y6Item,Integer> {
    List<Y6Item> findByFactory(String factory);
    Y6Item findByTag(String tag);

}
