package org.springblade.train.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.train.entity.TradeKind;
import org.springblade.train.mapper.TradeKindMapper;
import org.springblade.train.service.ITradeKindService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TradeKindServiceImpl extends ServiceImpl<TradeKindMapper, TradeKind> implements ITradeKindService {

	private TradeKindMapper tradeKindMapper;
	
	/**
	 * 行业类型下拉框查询
	 * @param name 行业名称
	 * @return
	 */
	@Override
	public List<TradeKind> selectTradeKindList(String name) throws Exception {
		QueryWrapper<TradeKind> queryWrapper = new QueryWrapper<TradeKind>();
		queryWrapper.lambda().like(StringUtils.isNotEmpty(name), TradeKind::getName, name);
		return tradeKindMapper.selectList(queryWrapper);
	}

}
