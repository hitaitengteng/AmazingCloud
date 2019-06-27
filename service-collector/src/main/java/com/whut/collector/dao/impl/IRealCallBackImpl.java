package com.whut.collector.dao.impl;

import bjzr.util.rtdb.*;
import com.whut.collector.util.KafkaUtil;
import psjniinterface.PsDataValueBuffer;


public class IRealCallBackImpl implements IRealCallBack {

	@Override
	public void update(long[] tagIds, PsDataValueBuffer dataValues) {
		DynamicData[] dynamicData = new DynamicData[dataValues.getDataValues().length];
		PspaceDataAdapter.PsDataValueToDynamicData(dataValues.getDataValues(), dynamicData);
		KafkaUtil.sendData(tagIds,dynamicData);
	}
}

