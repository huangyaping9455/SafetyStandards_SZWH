package org.springblade.anbiao.yunyingshang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.yunyingshang.entity.TsOperatorInfo;
import org.springblade.anbiao.yunyingshang.mapper.TsOperatorInfoMapper;
import org.springblade.anbiao.yunyingshang.page.TsOperatorInfoPage;
import org.springblade.anbiao.yunyingshang.service.ITsOperatorInfoService;
import org.springblade.anbiao.yunyingshang.vo.TsOperatorInfoVo;
import org.springblade.anbiao.zhengfu.entity.ZhengFuBaoJingTongJi;
import org.springblade.anbiao.zhengfu.entity.ZhengFuBaoJingTongJiJieSuan;
import org.springblade.anbiao.zhengfu.entity.ZhengFuRiYunXingTongJi;
import org.springblade.anbiao.zhengfu.page.ZhengFuBaoJingTongJiJieSuanPage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyp
 * @since 2022-11-18
 */
@Service
@AllArgsConstructor
public class TsOperatorInfoServiceImpl extends ServiceImpl<TsOperatorInfoMapper, TsOperatorInfo> implements ITsOperatorInfoService {

	private TsOperatorInfoMapper mapper;


	@Override
	public TsOperatorInfoPage<TsOperatorInfo> selectGetAll(TsOperatorInfoPage tsOperatorInfoPage) {
		Integer total = mapper.selectGetAllTotal(tsOperatorInfoPage);
		Integer tsOperatorInfoPagetotal = 0;
		if (tsOperatorInfoPage.getSize() == 0) {
			if (tsOperatorInfoPage.getTotal() == 0) {
				tsOperatorInfoPage.setTotal(total);
			}
			if (tsOperatorInfoPage.getTotal() == 0) {
				return tsOperatorInfoPage;
			} else {
				List<TsOperatorInfo> operatorInfoList = mapper.selectGetAll(tsOperatorInfoPage);
				tsOperatorInfoPage.setRecords(operatorInfoList);
				return tsOperatorInfoPage;
			}
		}
		if (total > 0) {
			if (total % tsOperatorInfoPage.getSize() == 0) {
				tsOperatorInfoPagetotal = total / tsOperatorInfoPage.getSize();
			} else {
				tsOperatorInfoPagetotal = total / tsOperatorInfoPage.getSize() + 1;
			}
		}
		if (tsOperatorInfoPagetotal < tsOperatorInfoPage.getCurrent()) {
			return tsOperatorInfoPage;
		} else {
			tsOperatorInfoPage.setPageTotal(tsOperatorInfoPagetotal);
			Integer offsetNo = 0;
			if (tsOperatorInfoPage.getCurrent() > 1) {
				offsetNo = tsOperatorInfoPage.getSize() * (tsOperatorInfoPage.getCurrent() - 1);
			}
			tsOperatorInfoPage.setTotal(total);
			tsOperatorInfoPage.setOffsetNo(offsetNo);
			List<TsOperatorInfo> operatorInfoList = mapper.selectGetAll(tsOperatorInfoPage);
			tsOperatorInfoPage.setRecords(operatorInfoList);
			return tsOperatorInfoPage;
		}
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage<ZhengFuRiYunXingTongJi> selectGetYYSRYXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = mapper.selectYYSRYXTJTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				return zhengFuBaoJingTongJiJieSuanPage;
			}else {
				List<ZhengFuRiYunXingTongJi> riYunXingTongJiList = mapper.selectGetYYSRYXTJ(zhengFuBaoJingTongJiJieSuanPage);
				zhengFuBaoJingTongJiJieSuanPage.setRecords(riYunXingTongJiList);
				return zhengFuBaoJingTongJiJieSuanPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
				pagetotal = total / zhengFuBaoJingTongJiJieSuanPage.getSize();
			}else {
				pagetotal = total / zhengFuBaoJingTongJiJieSuanPage.getSize() + 1;
			}
		}
		if (pagetotal >= zhengFuBaoJingTongJiJieSuanPage.getCurrent()) {
			zhengFuBaoJingTongJiJieSuanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (zhengFuBaoJingTongJiJieSuanPage.getCurrent() > 1) {
				offsetNo = zhengFuBaoJingTongJiJieSuanPage.getSize() * (zhengFuBaoJingTongJiJieSuanPage.getCurrent() - 1);
			}
			zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			zhengFuBaoJingTongJiJieSuanPage.setOffsetNo(offsetNo);
			List<ZhengFuRiYunXingTongJi> riYunXingTongJiList = mapper.selectGetYYSRYXTJ(zhengFuBaoJingTongJiJieSuanPage);
			zhengFuBaoJingTongJiJieSuanPage.setRecords(riYunXingTongJiList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public List<ZhengFuBaoJingTongJi> selectGetZFYYSBaoJingPaiMing(String deptId,String xiaJiDeptId) {
		return mapper.selectGetZFYYSBaoJingPaiMing(deptId,xiaJiDeptId);
	}

	@Override
	public TsOperatorInfoPage<TsOperatorInfoVo> selectZFYYSPage(TsOperatorInfoPage tsOperatorInfoPage) {
		Integer total = mapper.selectZFYYSTotal(tsOperatorInfoPage);
		if(tsOperatorInfoPage.getSize()==0){
			if(tsOperatorInfoPage.getTotal()==0){
				tsOperatorInfoPage.setTotal(total);
			}
			if(tsOperatorInfoPage.getTotal()==0){
				return tsOperatorInfoPage;
			}else {
				List<TsOperatorInfoVo> tsOperatorInfoList = mapper.selectZFYYSPage(tsOperatorInfoPage);
				tsOperatorInfoPage.setRecords(tsOperatorInfoList);
				return tsOperatorInfoPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%tsOperatorInfoPage.getSize()==0){
				pagetotal = total / tsOperatorInfoPage.getSize();
			}else {
				pagetotal = total / tsOperatorInfoPage.getSize() + 1;
			}
		}
		if (pagetotal >= tsOperatorInfoPage.getCurrent()) {
			tsOperatorInfoPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (tsOperatorInfoPage.getCurrent() > 1) {
				offsetNo = tsOperatorInfoPage.getSize() * (tsOperatorInfoPage.getCurrent() - 1);
			}
			tsOperatorInfoPage.setTotal(total);
			tsOperatorInfoPage.setOffsetNo(offsetNo);
			List<TsOperatorInfoVo> tsOperatorInfoList = mapper.selectZFYYSPage(tsOperatorInfoPage);
			tsOperatorInfoPage.setRecords(tsOperatorInfoList);
		}
		return tsOperatorInfoPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage<ZhengFuBaoJingTongJiJieSuan> selectGetBJTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = mapper.selectGetBJTJTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				return zhengFuBaoJingTongJiJieSuanPage;
			}else {
				List<ZhengFuBaoJingTongJiJieSuan> zhengFuBaoJingTongJiList = mapper.selectGetBJTJ(zhengFuBaoJingTongJiJieSuanPage);
				zhengFuBaoJingTongJiJieSuanPage.setRecords(zhengFuBaoJingTongJiList);
				return zhengFuBaoJingTongJiJieSuanPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
				pagetotal = total / zhengFuBaoJingTongJiJieSuanPage.getSize();
			}else {
				pagetotal = total / zhengFuBaoJingTongJiJieSuanPage.getSize() + 1;
			}
		}
		if (pagetotal >= zhengFuBaoJingTongJiJieSuanPage.getCurrent()) {
			zhengFuBaoJingTongJiJieSuanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (zhengFuBaoJingTongJiJieSuanPage.getCurrent() > 1) {
				offsetNo = zhengFuBaoJingTongJiJieSuanPage.getSize() * (zhengFuBaoJingTongJiJieSuanPage.getCurrent() - 1);
			}
			zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			zhengFuBaoJingTongJiJieSuanPage.setOffsetNo(offsetNo);
			List<ZhengFuBaoJingTongJiJieSuan> zhengFuBaoJingTongJiList = mapper.selectGetBJTJ(zhengFuBaoJingTongJiJieSuanPage);
			zhengFuBaoJingTongJiJieSuanPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public List<TsOperatorInfo> selectOperatorInfo(String deptId, String opCode) {
		return mapper.selectOperatorInfo(deptId,opCode);
	}

}
