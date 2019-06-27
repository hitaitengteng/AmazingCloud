package com.whut.collector.dao.impl;

import bjzr.util.rtdb.*;
import com.whut.collector.config.CollectorConfig;
import com.whut.collector.dao.PSPaceDao;
import psclient.*;

import java.util.GregorianCalendar;
import java.util.logging.Logger;

//@Component
public class PSpaceDaoImpl implements PSPaceDao{

    private static final Logger logger = Logger.getLogger("PSpaceDaoImpl");

    public static final IConnect oIConnect =
            DbFactoryImpl.getInstance().getConnection(
                    CollectorConfig.pSpace_host,
                    CollectorConfig.pSpace_port,
                    CollectorConfig.pSpace_username,
                    CollectorConfig.pSpace_password);

    @Override
    public long[] getAllPointID() {
        logger.info("准备获取实时库所有测点");
        return oIConnect.getAllPointID();

    }

    @Override
    public long[] getPointIDbyFullName(String[] fullName) {
        logger.info("getPointIDbyFullName");
        return oIConnect.getPointIdsByFullName(fullName);
    }

    @Override
    public long[] getPointIdsByQueryFilter(PsTagQueryFilter filter) {
        return oIConnect.getPointIdsByQueryFilter(filter, new PsStatus());
    }

    @Override
    public ISubscribe subscribeByIds(long[] tags, DynamicData[] firstData) {

        logger.info("准备订阅实时库测点");

        PsStatus[] results = new PsStatus[tags.length];
        // 订阅器
        ISubscribe subscribe = new IRealSubImpl();
        // 实时数据回调接口
        IRealCallBack realCallBack = new IRealCallBackImpl();

        PsStatus ret = oIConnect.newRealSubscrbeAndRead(tags, realCallBack,
                subscribe, firstData, results);

        if (!ret.isOk() && PsStatus.StatusCode.PSERR_FAIL_IN_BATCH != ret.getStatusCode()) {
            return null;
        }

        if (ret.isOk()) {
            logger.info("测点实时订阅成功");
        } else {
            logger.warning("测点实时订阅部分失败");
        }
        for (int i = 0; i < tags.length; i++) {
            if (results[i].isOk()) {
                logger.info("TagId:" + tags[i] + "订阅成功");
            }else {
                logger.warning("TagId:" + tags[i] + "订阅失败,失败原因:" + results[i].toString());
            }
        }
        return subscribe;
    }

    @Override
    public ISubscribe subscribeByNames(String[] fullNames, DynamicData[] firstData) {
        long[] tags = oIConnect.getPointIdsByFullName(fullNames);
        return subscribeByIds(tags,firstData);
    }

    @Override
    public void delSubscribe(ISubscribe subscribe) {
        PsStatus ret = oIConnect.deleSubscribe(subscribe);
        if (ret.isOk()) {
            logger.info("删除订阅成功");
        } else {
            logger.warning("删除订阅失败: " + ret.toString());
        }
    }

    @Override
    public HistoryData[] getHistoryDataByIds(long[] tags, GregorianCalendar start, GregorianCalendar end) {
        PsStatus[] results = new PsStatus[tags.length];
        GregorianCalendar s = new GregorianCalendar();
        return oIConnect.getHistoryData(tags, start.getTime(), end.getTime(), 30000, results);
    }

    @Override
    public HistoryData[] getHistoryDataByNames(String[] fullNames, GregorianCalendar start, GregorianCalendar end) {
        long[] tags = oIConnect.getPointIdsByFullName(fullNames);
        return getHistoryDataByIds(tags,start,end);
    }
}
