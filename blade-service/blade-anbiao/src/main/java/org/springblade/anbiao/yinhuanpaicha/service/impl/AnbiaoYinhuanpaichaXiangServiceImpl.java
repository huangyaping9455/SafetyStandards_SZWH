package org.springblade.anbiao.yinhuanpaicha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiang;
import org.springblade.anbiao.yinhuanpaicha.mapper.AnbiaoYinhuanpaichaXiangMapper;
import org.springblade.anbiao.yinhuanpaicha.page.YinhuanpaichaXiangPage;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoYinhuanpaichaXiangService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyp
 * @since 2022-09-08
 */
@Service
@AllArgsConstructor
public class AnbiaoYinhuanpaichaXiangServiceImpl extends ServiceImpl<AnbiaoYinhuanpaichaXiangMapper, AnbiaoYinhuanpaichaXiang> implements IAnbiaoYinhuanpaichaXiangService {

	private AnbiaoYinhuanpaichaXiangMapper anbiaoYinhuanpaichaXiangMapper;

	@Override
	public YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiang> selectYinhuanpaichaXiangPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage) {
		Integer total = anbiaoYinhuanpaichaXiangMapper.selectYinhuanpaichaXiangTotal(yinhuanpaichaXiangPage);
		if(yinhuanpaichaXiangPage.getSize()==0){
			if(yinhuanpaichaXiangPage.getTotal()==0){
				yinhuanpaichaXiangPage.setTotal(total);
			}
			List<AnbiaoYinhuanpaichaXiang> yinhuanpaichaXiangList = anbiaoYinhuanpaichaXiangMapper.selectYinhuanpaichaXiangPage(yinhuanpaichaXiangPage);
			yinhuanpaichaXiangPage.setRecords(yinhuanpaichaXiangList);
			return yinhuanpaichaXiangPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%yinhuanpaichaXiangPage.getSize()==0){
				pagetotal = total / yinhuanpaichaXiangPage.getSize();
			}else {
				pagetotal = total / yinhuanpaichaXiangPage.getSize() + 1;
			}
		}
		if (pagetotal >= yinhuanpaichaXiangPage.getCurrent()) {
			yinhuanpaichaXiangPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (yinhuanpaichaXiangPage.getCurrent() > 1) {
				offsetNo = yinhuanpaichaXiangPage.getSize() * (yinhuanpaichaXiangPage.getCurrent() - 1);
			}
			yinhuanpaichaXiangPage.setTotal(total);
			yinhuanpaichaXiangPage.setOffsetNo(offsetNo);
			List<AnbiaoYinhuanpaichaXiang> yinhuanpaichaXiangList = anbiaoYinhuanpaichaXiangMapper.selectYinhuanpaichaXiangPage(yinhuanpaichaXiangPage);
			yinhuanpaichaXiangPage.setRecords(yinhuanpaichaXiangList);
		}
		return yinhuanpaichaXiangPage;
	}
}
