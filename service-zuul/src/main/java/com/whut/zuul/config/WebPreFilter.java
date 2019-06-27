package com.whut.zuul.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.whut.zuul.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 在转发到其他微服务之前对请求进行验证，判断是否登陆
 */
@Component
public class WebPreFilter extends ZuulFilter {
    private static final Logger log = LoggerFactory.getLogger(WebPreFilter.class);

    @Override
    public Object run() {
        System.out.println("==============================1.先进行过滤===================");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        HttpSession session = request.getSession();
        String url = request.getRequestURL().toString();
        log.info(String.format("%s UserLoginFilter request to %s", request.getMethod(), url));
        User user = (User)session.getAttribute("user");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return null;
        }
        if(user !=null){
            System.out.println("==============================先进行过滤============================");
            ctx.setSendZuulResponse(true);// 对该请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);// 设值，可以在多个过滤器时使用
            return null;
        }else {
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(404);// 返回错误码
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
            ctx.setResponseBody("{\"result\":\"请您先登陆\"}");// 返回错误内容
            ctx.set("isSuccess", false);
        }
        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
}
