package com.whut.zuul.Service.hbaseservice;

import com.whut.zuul.dao.HBaseRestServiceDao;
import org.springframework.stereotype.Component;

/**
 * @Author Th.FAR
 * @Date 19-1-11
 * @Desc
 */
@Component
public class HBaseRestHystrixService implements HBaseRestServiceDao {
    @Override
    public String findSingleOnlineDataByTags(String currtime, String tags) {
        return "请求超时";
    }
}
