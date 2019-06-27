package com.whut.zuul.dao;

import com.whut.zuul.Service.hbaseservice.HBaseRestHystrixService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Th.FAR
 * @Date 19-1-11
 * @Desc
 */

@Component
@FeignClient(value = "hbase-server",fallback = HBaseRestHystrixService.class)
public interface HBaseRestServiceDao {
    @RequestMapping(value = "/findSingleOnlineDataByTags", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String findSingleOnlineDataByTags(@RequestParam( value = "currtime") String currtime,
                                @RequestParam(value = "tags") String tags);
}
