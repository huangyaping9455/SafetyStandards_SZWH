package org.springblade.train.service;

import org.springblade.train.entity.TradeKind;

import java.util.List;

public interface ITradeKindService {

	/**
	 * 行业类型下拉框查询
	 * @param name 行业名称
	 * @return
	 */
	List<TradeKind> selectTradeKindList(String name) throws Exception;

}
