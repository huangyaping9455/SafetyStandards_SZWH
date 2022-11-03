package org.springblade.anbiao.anquanhuiyi.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.mapper.AnbiaoAnquanhuiyiMapper;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 安全会议记录表 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2022-11-01
 */
@Service
@AllArgsConstructor
public class AnbiaoAnquanhuiyiServiceImpl extends ServiceImpl<AnbiaoAnquanhuiyiMapper, AnbiaoAnquanhuiyi> implements IAnbiaoAnquanhuiyiService {

	AnbiaoAnquanhuiyiMapper mapper;

	@Override
	public AnQuanHuiYiPage<AnbiaoAnquanhuiyi> selectPage(AnQuanHuiYiPage anQuanHuiYiPage) {
		Integer total = mapper.selectTotal(anQuanHuiYiPage);
		Integer pagetotal = 0;
		if(anQuanHuiYiPage.getSize()==0){
			if(anQuanHuiYiPage.getTotal()==0){
				anQuanHuiYiPage.setTotal(total);
			}
			if(anQuanHuiYiPage.getTotal()==0){
				return anQuanHuiYiPage;
			}else {
				List<AnbiaoAnquanhuiyi> safetyTrainingList = mapper.selectPage(anQuanHuiYiPage);
				anQuanHuiYiPage.setRecords(safetyTrainingList);
				return anQuanHuiYiPage;
			}
		}
		if (total > 0) {
			if(total%anQuanHuiYiPage.getSize()==0){
				pagetotal = total / anQuanHuiYiPage.getSize();
			}else {
				pagetotal = total / anQuanHuiYiPage.getSize() + 1;
			}
		}
		if (pagetotal < anQuanHuiYiPage.getCurrent()) {
			return anQuanHuiYiPage;
		} else {
			anQuanHuiYiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anQuanHuiYiPage.getCurrent() > 1) {
				offsetNo = anQuanHuiYiPage.getSize() * (anQuanHuiYiPage.getCurrent() - 1);
			}
			anQuanHuiYiPage.setTotal(total);
			anQuanHuiYiPage.setOffsetNo(offsetNo);
			List<AnbiaoAnquanhuiyi> safetyTrainingList = mapper.selectPage(anQuanHuiYiPage);
			anQuanHuiYiPage.setRecords(safetyTrainingList);
			return anQuanHuiYiPage;
		}
	}
}
