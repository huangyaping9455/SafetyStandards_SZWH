package org.springblade.anbiao.jiaoyupeixun.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTraining;
import org.springblade.anbiao.jiaoyupeixun.mapper.AnbiaoSafetyTrainingMapper;
import org.springblade.anbiao.jiaoyupeixun.page.AnbiaoSafetyTrainingPage;
import org.springblade.anbiao.jiaoyupeixun.service.IAnbiaoSafetyTrainingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.anbiao.jiaoyupeixun.vo.AnbiaoSafetyTrainingVO;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 安全生产培训(线下) 服务实现类
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
@Service
@AllArgsConstructor
public class AnbiaoSafetyTrainingServiceImpl extends ServiceImpl<AnbiaoSafetyTrainingMapper, AnbiaoSafetyTraining> implements IAnbiaoSafetyTrainingService {

	private AnbiaoSafetyTrainingMapper mapper;

	@Override
	public AnbiaoSafetyTrainingPage<AnbiaoSafetyTrainingVO> selectPage(AnbiaoSafetyTrainingPage anbiaoSafetyTrainingPage) {
		Integer total = mapper.selectTotal(anbiaoSafetyTrainingPage);
		Integer pagetotal = 0;
		if(anbiaoSafetyTrainingPage.getSize()==0){
			if(anbiaoSafetyTrainingPage.getTotal()==0){
				anbiaoSafetyTrainingPage.setTotal(total);
			}
			if(anbiaoSafetyTrainingPage.getTotal()==0){
				return anbiaoSafetyTrainingPage;
			}else {
				List<AnbiaoSafetyTrainingVO> safetyTrainingList = mapper.selectPage(anbiaoSafetyTrainingPage);
				anbiaoSafetyTrainingPage.setRecords(safetyTrainingList);
				return anbiaoSafetyTrainingPage;
			}
		}
		if (total > 0) {
			if(total%anbiaoSafetyTrainingPage.getSize()==0){
				pagetotal = total / anbiaoSafetyTrainingPage.getSize();
			}else {
				pagetotal = total / anbiaoSafetyTrainingPage.getSize() + 1;
			}
		}
		if (pagetotal < anbiaoSafetyTrainingPage.getCurrent()) {
			return anbiaoSafetyTrainingPage;
		} else {
			anbiaoSafetyTrainingPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoSafetyTrainingPage.getCurrent() > 1) {
				offsetNo = anbiaoSafetyTrainingPage.getSize() * (anbiaoSafetyTrainingPage.getCurrent() - 1);
			}
			anbiaoSafetyTrainingPage.setTotal(total);
			anbiaoSafetyTrainingPage.setOffsetNo(offsetNo);
			List<AnbiaoSafetyTrainingVO> safetyTrainingList = mapper.selectPage(anbiaoSafetyTrainingPage);
			anbiaoSafetyTrainingPage.setRecords(safetyTrainingList);
			return anbiaoSafetyTrainingPage;
		}
	}
}
