package com.whut.zuul.controller;

import com.whut.zuul.Service.impl.UserServiceImpl;
import com.whut.zuul.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserServiceImpl userService;

    /**
     * 前往登陆界面
     * @return
     */
    @RequestMapping(value = "toLogin",method = RequestMethod.GET)
    public String toLogin(){
        return "login/login";
    }



    /**
     * 开始登陆，保存登陆用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = "haslogin",method = RequestMethod.POST)
    @ResponseBody
    public String hasLogin(@RequestBody User user, HttpServletRequest request){
        User u = userService.findByUsername(user);
        if(!(u == null)){
            /*获取Session保存用户信息*/
            HttpSession session = request.getSession();
            session.setAttribute("user",u);
            return "SUCCESS";
        } else {
            return "用户不存在";
        }
    }

    /**
     * 现在登陆到首页
     * @return
     */
    @RequestMapping(value = "haslogin2",method = RequestMethod.GET)
    public String hasLogin2(HttpServletRequest request){
        String page = "home/main";
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        String role = user.getRole();
        boolean flag = role.equals("admin") || role.equals("normmal") || role.equals("tourist");
        if(!flag){
           page = "login/login";
        }
        return page;
    }

    @RequestMapping(value = "requestquit",method = RequestMethod.GET)
    @ResponseBody
    public String requestLogin(HttpServletRequest request){
        return "SUCCESS";
    }

    @RequestMapping(value = "quit",method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request){
        request.getSession().invalidate();
        return "login/login";
    }
}
