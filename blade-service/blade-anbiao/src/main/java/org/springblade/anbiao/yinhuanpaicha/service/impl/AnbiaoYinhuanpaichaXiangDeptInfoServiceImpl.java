package org.springblade.anbiao.yinhuanpaicha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiangDeptInfo;
import org.springblade.anbiao.yinhuanpaicha.mapper.AnbiaoYinhuanpaichaXiangDeptInfoMapper;
import org.springblade.anbiao.yinhuanpaicha.page.YinhuanpaichaXiangPage;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoYinhuanpaichaXiangDeptInfoService;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoYinhuanpaichaXiangDeptInfoVO;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoYinhuanpaichaXiangDeptVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyp
 * @since 2022-09-14
 */
@Service
@AllArgsConstructor
public class AnbiaoYinhuanpaichaXiangDeptInfoServiceImpl extends ServiceImpl<AnbiaoYinhuanpaichaXiangDeptInfoMapper, AnbiaoYinhuanpaichaXiangDeptInfo> implements IAnbiaoYinhuanpaichaXiangDeptInfoService {

	private AnbiaoYinhuanpaichaXiangDeptInfoMapper deptInfoMapper;

	@Override
	public YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptVO> selectYinhuanpaichaDeptPlanPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage) {
		Integer total = deptInfoMapper.selectYinhuanpaichaDeptPlanTotal(yinhuanpaichaXiangPage);
		if(yinhuanpaichaXiangPage.getSize()==0){
			if(yinhuanpaichaXiangPage.getTotal()==0){
				yinhuanpaichaXiangPage.setTotal(total);
			}
			List<AnbiaoYinhuanpaichaXiangDeptVO> infoVOList = deptInfoMapper.selectYinhuanpaichaDeptPlanPage(yinhuanpaichaXiangPage);
			yinhuanpaichaXiangPage.setRecords(infoVOList);
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
			List<AnbiaoYinhuanpaichaXiangDeptVO> infoVOList = deptInfoMapper.selectYinhuanpaichaDeptPlanPage(yinhuanpaichaXiangPage);
			yinhuanpaichaXiangPage.setRecords(infoVOList);
		}
		return yinhuanpaichaXiangPage;
	}

	@Override
	public List<AnbiaoYinhuanpaichaXiangDeptInfoVO> selectDeptDriverPlan(Integer deptId, String jsyid, String vehid) {
		return deptInfoMapper.selectDeptDriverPlan(deptId, jsyid, vehid);
	}

	@Override
	public YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptVO> selectDeptYHPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage) {
		Integer total = deptInfoMapper.selectDeptYHTotal(yinhuanpaichaXiangPage);
		if(yinhuanpaichaXiangPage.getSize()==0){
			if(yinhuanpaichaXiangPage.getTotal()==0){
				yinhuanpaichaXiangPage.setTotal(total);
			}
			List<AnbiaoYinhuanpaichaXiangDeptVO> infoVOList = deptInfoMapper.selectDeptYHPage(yinhuanpaichaXiangPage);
			yinhuanpaichaXiangPage.setRecords(infoVOList);
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
			List<AnbiaoYinhuanpaichaXiangDeptVO> infoVOList = deptInfoMapper.selectDeptYHPage(yinhuanpaichaXiangPage);
			yinhuanpaichaXiangPage.setRecords(infoVOList);
		}
		return yinhuanpaichaXiangPage;
	}

	@Override
	public YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptInfoVO> selectYinhuanpaichaDeptPlanRemarkPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage) {
		Integer total = deptInfoMapper.selectYinhuanpaichaDeptPlanRemarkTotal(yinhuanpaichaXiangPage);
		if(yinhuanpaichaXiangPage.getSize()==0){
			if(yinhuanpaichaXiangPage.getTotal()==0){
				yinhuanpaichaXiangPage.setTotal(total);
			}
			List<AnbiaoYinhuanpaichaXiangDeptInfoVO> infoVOList = deptInfoMapper.selectYinhuanpaichaDeptPlanRemarkPage(yinhuanpaichaXiangPage);
			yinhuanpaichaXiangPage.setRecords(infoVOList);
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
			List<AnbiaoYinhuanpaichaXiangDeptInfoVO> infoVOList = deptInfoMapper.selectYinhuanpaichaDeptPlanRemarkPage(yinhuanpaichaXiangPage);
			yinhuanpaichaXiangPage.setRecords(infoVOList);
		}
		return yinhuanpaichaXiangPage;
	}
}
