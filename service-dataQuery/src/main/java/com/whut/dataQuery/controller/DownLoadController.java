package com.whut.dataQuery.controller;

import com.alibaba.fastjson.JSONObject;
import com.whut.dataQuery.dao.impl.HBaseDaoImpl;
import com.whut.dataQuery.service.IHBaseService;
import com.whut.dataQuery.service.impl.HBaseServiceImpl;
import com.whut.dataQuery.util.HttpCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yy
 * @describe
 * @time 18-12-22 下午11:29
 */
@RestController
public class DownLoadController {

    @Autowired
    private final IHBaseService hBaseService;

    public DownLoadController(IHBaseService hBaseService) {
        this.hBaseService = hBaseService;
    }

    @PostMapping(value = "/toCSV")
    @ResponseBody
    public JSONObject toCSV(HttpServletRequest request){

        JSONObject result = new JSONObject();
        String startTime = request.getParameter("startTime");
        String stopTime = request.getParameter("stopTime");
        String tags = request.getParameter("tags");
        if (!HttpCheckUtil.check(startTime,stopTime,tags)) {
            result.put("success",false);
            result.put("message", "参数错误");
            result.put("data","");
            return result;
        }
        String filename = hBaseService.toCSV(startTime,stopTime,tags.split(","));
        if (filename == null)
        {
            result.put("success",false);
            result.put("message", "参数错误");
            result.put("data","");
            return result;
        }
        result.put("success",true);
        result.put("message", "正在导出文件,请耐心等候");
        result.put("data","/service-dataQuery/file/download/"+filename.replaceAll("/tmp/",""));
        return result;
    }

    public static void main(String[] args) {
        HBaseServiceImpl hBaseService = new HBaseServiceImpl(new HBaseDaoImpl());
        String filename = hBaseService.toCSV("2019-06-23 14:00:00", "2019-06-23 16:00:00",new String[]{"y6_ai01","y6_ai02","y6_ai03"});

        System.out.println(filename);
    }
}
