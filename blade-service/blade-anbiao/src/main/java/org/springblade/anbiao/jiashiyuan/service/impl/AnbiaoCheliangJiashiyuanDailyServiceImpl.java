package org.springblade.anbiao.jiashiyuan.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuanDaily;
import org.springblade.anbiao.jiashiyuan.mapper.AnbiaoCheliangJiashiyuanDailyMapper;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoCheliangJiashiyuanDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lmh
 * @since 2022-11-10
 */
@Service
@AllArgsConstructor
public class AnbiaoCheliangJiashiyuanDailyServiceImpl extends ServiceImpl<AnbiaoCheliangJiashiyuanDailyMapper, AnbiaoCheliangJiashiyuanDaily> implements IAnbiaoCheliangJiashiyuanDailyService {

	private AnbiaoCheliangJiashiyuanDailyMapper mapper;

	@Override
	public AnbiaoCheliangJiashiyuanDaily SelectByID(String shiyongxingzhi,String jiashiyuanid) {
		return mapper.SelectByID(shiyongxingzhi,jiashiyuanid);
	}
}
