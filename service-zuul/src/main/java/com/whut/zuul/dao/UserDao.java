package com.whut.zuul.dao;

import com.whut.zuul.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User,Long> {
   /*====================================注册==========================*/

    /*==================================查询用户========================*/
    List<User> findByUsername(String username);

    /*=================================删除用户=======================*/


    /*================================修改用户角色=======================*/


}
