package com.whut.zuul.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author     ：ThunderFAR.
 * @ Date       ：Created in @create: 2018-07-25 08:12
 * @ Description：关联度分析
 * @ Modified By：
 * @Version: 1.0
 */


@RestController
public class CAcontroller {

    /**
    * @Description: This is a Function
    * @Author: ThunderFAR
    * @Date: 2018/7/25 8:13
    * @Param:
    * @return:
    */

    @RequestMapping(value = "/scatter",method = RequestMethod.POST)
    public String scatter(){
        JSONArray array = new JSONArray();


        for(int j =0 ;j<3;j++){
            JSONArray array1 = new JSONArray();
            JSONArray array2 = new JSONArray();
            JSONObject object = new JSONObject();
            for (int i=0;i<3;i++){
                array1.add(String.valueOf(i));
                array2.add(i);
            }
            object.put("key1",array1);
            object.put("value",array2);
            array.add(object);
        }

        return array.toString();
    }
}