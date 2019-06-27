package com.whut.zuul.controller;



import com.whut.zuul.domain.User;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Controller
public class OnlineController {

@RequestMapping(value = "/toLink3D",method = RequestMethod.GET)
public String linkTo3D(HttpServletRequest request){
        String page = "/dataquery/onlinequery/3demulation";
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        String role = user.getRole();
        boolean flag = role.equals("admin") || role.equals("normmal") || role.equals("tourist");
        if(!flag){
        page = "login/login";
        }
        return page;
        }

@RequestMapping(value = "/ajax")
@ResponseBody
public Object backText(String callback,HttpServletRequest request){
        String section = request.getParameter("tag");
        System.out.println(callback);
        String message="{'name':'盖伦','age':'30'}";
        //MappingJacksonValue在spring4.1后出的，它将数据封装，并可以设置sjonp返回函数。
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(message);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
}

}
