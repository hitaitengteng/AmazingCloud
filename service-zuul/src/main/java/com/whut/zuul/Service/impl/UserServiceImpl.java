package com.whut.zuul.Service.impl;

import com.whut.zuul.Service.UserService;
import com.whut.zuul.dao.UserDao;
import com.whut.zuul.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    /*=============================查询用户======================*/
    public User findByUsername(User user){
        List<User> username = userDao.findByUsername(user.getUsername());
        for(User u:username){
            if(u.getUsername().equals(user.getUsername())&&u.getPassword().equals(user.getPassword())){
                return u;
            }
        }
        return null;
    }

}
