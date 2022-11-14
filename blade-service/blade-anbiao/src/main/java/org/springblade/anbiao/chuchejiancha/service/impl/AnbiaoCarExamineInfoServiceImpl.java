package org.springblade.anbiao.chuchejiancha.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfo;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfoRemark;
import org.springblade.anbiao.chuchejiancha.mapper.AnbiaoCarExamineInfoMapper;
import org.springblade.anbiao.chuchejiancha.mapper.AnbiaoCarExamineInfoRemarkMapper;
import org.springblade.anbiao.chuchejiancha.page.AnBiaoCheckCarPage;
import org.springblade.anbiao.chuchejiancha.page.AnbiaoCarExamineInfoPage;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineInfoService;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineInfoVO;
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

	private AnbiaoCarExamineInfoRemarkMapper remarkMapper;

	@Override
	public AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfoVO> selectCarExamineInfoPage(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage) {
		Integer total = mapper.selectCarExamineInfoTotal(anbiaoCarExamineInfoPage);
		if(anbiaoCarExamineInfoPage.getSize()==0){
			if(anbiaoCarExamineInfoPage.getTotal()==0){
				anbiaoCarExamineInfoPage.setTotal(total);
			}
			List<AnbiaoCarExamineInfoVO> infoList = mapper.selectCarExamineInfoPage(anbiaoCarExamineInfoPage);
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
			List<AnbiaoCarExamineInfoVO> infoList = mapper.selectCarExamineInfoPage(anbiaoCarExamineInfoPage);
			anbiaoCarExamineInfoPage.setRecords(infoList);
		}
		return anbiaoCarExamineInfoPage;
	}

	@Override
	public List<AnbiaoCarExamineInfo> selectAnBiaoCheckCarALLPage(AnBiaoCheckCarPage anbiaoCarExamineInfoPage) {
		List<AnbiaoCarExamineInfo> infoList = mapper.selectAnBiaoCheckCarALLPage(anbiaoCarExamineInfoPage);
		if(infoList.size()>0){
			infoList.forEach(item-> {
				QueryWrapper<AnbiaoCarExamineInfoRemark> remarkQueryWrapper = new QueryWrapper<AnbiaoCarExamineInfoRemark>();
				remarkQueryWrapper.lambda().eq(AnbiaoCarExamineInfoRemark::getExamid,item.getId());
				List<AnbiaoCarExamineInfoRemark> examineInfoRemarkList = remarkMapper.selectList(remarkQueryWrapper);
				item.setAnbiaoCarExamineInfoRemarkList(examineInfoRemarkList);
			});
		}
		return infoList;
	}


}
