package com.whut.collector.dao;

import bjzr.util.rtdb.DynamicData;
import bjzr.util.rtdb.HistoryData;
import bjzr.util.rtdb.ISubscribe;
import psclient.PsTagQueryFilter;

import java.util.GregorianCalendar;

public interface PSPaceDao {

    long[] getAllPointID();
    long[] getPointIDbyFullName(String[] fullName);
    long[] getPointIdsByQueryFilter(PsTagQueryFilter filter);

    //回调
    ISubscribe subscribeByIds(long[] tags, DynamicData[] firstDat);
    ISubscribe subscribeByNames(String[] fullNames, DynamicData[] firstDat);
    void delSubscribe(ISubscribe subscribe);

    //主动查询
    HistoryData[] getHistoryDataByIds(long[] tags, GregorianCalendar start, GregorianCalendar end);
    HistoryData[] getHistoryDataByNames(String[] fullNames, GregorianCalendar start, GregorianCalendar end);
}
