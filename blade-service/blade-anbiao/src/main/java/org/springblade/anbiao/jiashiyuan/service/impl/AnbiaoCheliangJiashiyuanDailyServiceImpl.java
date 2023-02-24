package org.springblade.anbiao.jiashiyuan.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuanDaily;
import org.springblade.anbiao.jiashiyuan.mapper.AnbiaoCheliangJiashiyuanDailyMapper;
import org.springblade.anbiao.jiashiyuan.page.AnbiaoCheliangJiashiyuanDailyPage;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanVehiclePage;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoCheliangJiashiyuanDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.anbiao.jiashiyuan.vo.CheliangJiashiyuanVO;
import org.springframework.stereotype.Service;

import java.util.List;

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

	@Override
	public AnbiaoCheliangJiashiyuanDailyPage<AnbiaoCheliangJiashiyuanDaily> selectPageList(AnbiaoCheliangJiashiyuanDailyPage page) {
		Integer total = mapper.selectTotal(page);
		Integer pagetotal = 0;
		if (page.getSize() == 0) {
			if (page.getTotal() == 0) {
				page.setTotal(total);
			}
			if (page.getTotal() == 0) {
				return page;
			} else {
				List<AnbiaoCheliangJiashiyuanDaily> vehlist = mapper.selectPageList(page);
				page.setRecords(vehlist);
				return page;
			}
		}
		if (total > 0) {
			if (total % page.getSize() == 0) {
				pagetotal = total / page.getSize();
			} else {
				pagetotal = total / page.getSize() + 1;
			}
		}
		if (pagetotal < page.getCurrent()) {
			return page;
		} else {
			page.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (page.getCurrent() > 1) {
				offsetNo = page.getSize() * (page.getCurrent() - 1);
			}
			page.setTotal(total);
			page.setOffsetNo(offsetNo);
			List<AnbiaoCheliangJiashiyuanDaily> vehlist = mapper.selectPageList(page);
			page.setRecords(vehlist);
			return page;
		}
	}

	@Override
	public boolean unbundleVeh(AnbiaoCheliangJiashiyuanDaily anbiaoCheliangJiashiyuanDaily) {
		return mapper.unbundleVeh(anbiaoCheliangJiashiyuanDaily);
	}
}
