package com.whut.zuul.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 拦截器
     *
     * @param registry 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new WebInterceptor()).addPathPatterns("/**").excludePathPatterns("/toLogin", "/toRegister",
                "/haslogin", "/quit");
        super.addInterceptors(registry);
    }
}
