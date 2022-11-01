package org.springblade.anbiao.chuchejiancha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfo;
import org.springblade.anbiao.chuchejiancha.mapper.AnbiaoCarExamineInfoMapper;
import org.springblade.anbiao.chuchejiancha.page.AnbiaoCarExamineInfoPage;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@Service
@AllArgsConstructor
public class AnbiaoCarExamineInfoServiceImpl extends ServiceImpl<AnbiaoCarExamineInfoMapper, AnbiaoCarExamineInfo> implements IAnbiaoCarExamineInfoService {

	private AnbiaoCarExamineInfoMapper mapper;

	@Override
	public AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfo> selectCarExamineInfoPage(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage) {
		Integer total = mapper.selectCarExamineInfoTotal(anbiaoCarExamineInfoPage);
		if(anbiaoCarExamineInfoPage.getSize()==0){
			if(anbiaoCarExamineInfoPage.getTotal()==0){
				anbiaoCarExamineInfoPage.setTotal(total);
			}
			List<AnbiaoCarExamineInfo> infoList = mapper.selectCarExamineInfoPage(anbiaoCarExamineInfoPage);
			anbiaoCarExamineInfoPage.setRecords(infoList);
			return anbiaoCarExamineInfoPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoCarExamineInfoPage.getSize()==0){
				pagetotal = total / anbiaoCarExamineInfoPage.getSize();
			}else {
				pagetotal = total / anbiaoCarExamineInfoPage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoCarExamineInfoPage.getCurrent()) {
			anbiaoCarExamineInfoPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoCarExamineInfoPage.getCurrent() > 1) {
				offsetNo = anbiaoCarExamineInfoPage.getSize() * (anbiaoCarExamineInfoPage.getCurrent() - 1);
			}
			anbiaoCarExamineInfoPage.setTotal(total);
			anbiaoCarExamineInfoPage.setOffsetNo(offsetNo);
			List<AnbiaoCarExamineInfo> infoList = mapper.selectCarExamineInfoPage(anbiaoCarExamineInfoPage);
			anbiaoCarExamineInfoPage.setRecords(infoList);
		}
		return anbiaoCarExamineInfoPage;
	}



}
