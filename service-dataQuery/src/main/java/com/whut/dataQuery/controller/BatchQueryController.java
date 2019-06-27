package com.whut.dataQuery.controller;

import com.alibaba.fastjson.JSONObject;
import com.whut.dataQuery.service.IHBaseService;
import com.whut.dataQuery.util.HttpCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yy
 * @describe
 * @time 18-12-22 下午11:51
 */
@RestController
public class BatchQueryController {

    private final
    IHBaseService ihBaseService;

    @Autowired
    public BatchQueryController(IHBaseService ihBaseService) {
        this.ihBaseService = ihBaseService;
    }

    /**
     * @since 2018-07-18
     * @author 杨赟
     * @describe 查询一个控制项目
     * @return 查询结果
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject find(HttpServletRequest request)
    {
        JSONObject result = new JSONObject();
        String startTime = request.getParameter("startTime");
        String stopTime = request.getParameter("stopTime");
        String tag = request.getParameter("tag");
        if (!HttpCheckUtil.check(startTime,stopTime,tag)) {
            result.put("success",false);
            result.put("message", "参数错误");
            result.put("data","");
            return result;
        }
        JSONObject data = ihBaseService.find(startTime, stopTime,tag);
        result.put("success",true);
        result.put("message", "查询成功");
        result.put("data",data);
        return result;
    }

    /**
     * @since 2018-07-18
     * @author 杨赟
     * @describe 查询N个控制项目
     * @return 查询结果
     */
    @RequestMapping(value = "/finds", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject finds(HttpServletRequest request)
    {
        JSONObject result = new JSONObject();
        String startTime = request.getParameter("startTime");
        String stopTime = request.getParameter("stopTime");
        String tags = request.getParameter("tags");
        if (!HttpCheckUtil.check(startTime,stopTime,tags))
        {
            result.put("success",false);
            result.put("message","参数错误");
            result.put("data","");
            return result;
        }
        JSONObject data = ihBaseService.finds(startTime, stopTime,tags.split(","));
        result.put("success",true);
        result.put("message", "查询成功");
        result.put("data",data);
        return result;
    }
}
