package org.springblade.anbiao.yinhuanpaicha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoHiddenDanger;
import org.springblade.anbiao.yinhuanpaicha.mapper.AnbiaoHiddenDangerMapper;
import org.springblade.anbiao.yinhuanpaicha.page.AnbiaoHiddenDangerPage;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoHiddenDangerService;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoHiddenDangerVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 隐患排查信息 服务实现类
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
@Service
@AllArgsConstructor
public class AnbiaoHiddenDangerServiceImpl extends ServiceImpl<AnbiaoHiddenDangerMapper, AnbiaoHiddenDanger> implements IAnbiaoHiddenDangerService {

	private AnbiaoHiddenDangerMapper mapper;

	@Override
	public AnbiaoHiddenDangerPage<AnbiaoHiddenDangerVO> selectPage(AnbiaoHiddenDangerPage anbiaoHiddenDangerPage) {
		Integer total = mapper.selectTotal(anbiaoHiddenDangerPage);
		Integer pagetotal = 0;
		if(anbiaoHiddenDangerPage.getSize()==0){
			if(anbiaoHiddenDangerPage.getTotal()==0){
				anbiaoHiddenDangerPage.setTotal(total);
			}
			if(anbiaoHiddenDangerPage.getTotal()==0){
				return anbiaoHiddenDangerPage;
			}else {
				List<AnbiaoHiddenDangerVO> hiddenDangerVOList = mapper.selectPage(anbiaoHiddenDangerPage);
				anbiaoHiddenDangerPage.setRecords(hiddenDangerVOList);
				return anbiaoHiddenDangerPage;
			}
		}
		if (total > 0) {
			if(total%anbiaoHiddenDangerPage.getSize()==0){
				pagetotal = total / anbiaoHiddenDangerPage.getSize();
			}else {
				pagetotal = total / anbiaoHiddenDangerPage.getSize() + 1;
			}
		}
		if (pagetotal < anbiaoHiddenDangerPage.getCurrent()) {
			return anbiaoHiddenDangerPage;
		} else {
			anbiaoHiddenDangerPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoHiddenDangerPage.getCurrent() > 1) {
				offsetNo = anbiaoHiddenDangerPage.getSize() * (anbiaoHiddenDangerPage.getCurrent() - 1);
			}
			anbiaoHiddenDangerPage.setTotal(total);
			anbiaoHiddenDangerPage.setOffsetNo(offsetNo);
			List<AnbiaoHiddenDangerVO> hiddenDangerVOList = mapper.selectPage(anbiaoHiddenDangerPage);
			anbiaoHiddenDangerPage.setRecords(hiddenDangerVOList);
			return anbiaoHiddenDangerPage;
		}
	}



}
