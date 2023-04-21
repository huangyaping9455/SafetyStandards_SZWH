/**
 * FileName: XinXiJiaoHuZhuServiceImpl
 */
package org.springblade.anbiao.zhengfu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.zhengfu.entity.*;
import org.springblade.anbiao.zhengfu.mapper.ZhengFuBaoJingTongJiMapper;
import org.springblade.anbiao.zhengfu.page.ZhengFuBaoJingTongJiJieSuanPage;
import org.springblade.anbiao.zhengfu.page.ZhengFuIntoAreaPage;
import org.springblade.anbiao.zhengfu.page.ZhengFuRiskRankingPage;
import org.springblade.anbiao.zhengfu.service.IZhengFuBaoJingTongJiService;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.tool.GpsToBaiduUtil;
import org.springblade.common.tool.LatLotForLocation;
import org.springblade.doc.safetyproductionfile.entity.AnbiaoSafetyproductionfileNum;
import org.springblade.doc.safetyproductionfile.page.SafetyproductionfileNumPage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author 呵呵哒
 */
@Service
@AllArgsConstructor
public class ZhengFuBaoJingTongJiServiceImpl extends ServiceImpl<ZhengFuBaoJingTongJiMapper, ZhengFuBaoJingTongJi> implements IZhengFuBaoJingTongJiService {

	private ZhengFuBaoJingTongJiMapper zhengFuBaoJingTongJiMapper;

	private AlarmServer alarmServer;

	@Override
	public List<ZhengFuBaoJingTongJi> selectGetZFBaoJing(String deptId) {
		return zhengFuBaoJingTongJiMapper.selectGetZFBaoJing(deptId);
	}

	@Override
	public List<ZhengFuBaoJingTongJi> selectGetZFBaoJing_XiaJi(String xiaJiDeptId) {
		return zhengFuBaoJingTongJiMapper.selectGetZFBaoJing_XiaJi(xiaJiDeptId);
	}

	@Override
	public ZhengFuBaoJingTongJi selectGetZFBaoJingYear(String deptId) {
		return zhengFuBaoJingTongJiMapper.selectGetZFBaoJingYear(deptId);
	}

	@Override
	public List<ZhengFuBaoJingTongJi> selectGetZFBaoJingYear_XiaJi(String xiaJiDeptId) {
		return zhengFuBaoJingTongJiMapper.selectGetZFBaoJingYear_XiaJi(xiaJiDeptId);
	}
	@Override
	public ZhengFuBaoJingTongJi selectGetZFBaoJingMonth(String deptId,String date) {
		return zhengFuBaoJingTongJiMapper.selectGetZFBaoJingMonth(deptId,date);
	}

	@Override
	public List<ZhengFuBaoJingTongJi> selectGetZFBaoJingMonth_XiaJi(String xiaJiDeptId,String date) {
		return zhengFuBaoJingTongJiMapper.selectGetZFBaoJingMonth_XiaJi(xiaJiDeptId, date);
	}

	@Override
	public List<ZhengFuBaoJingTongJi> selectGetZFBaoJingQuShi(String deptId, String firstDate, String endDate) {
		return zhengFuBaoJingTongJiMapper.selectGetZFBaoJingQuShi(deptId,firstDate,endDate);
	}

	@Override
	public List<ZhengFuBaoJingTongJi> selectGetZFBaoJingQuShi_XiaJi(String xiaJiDeptId, String firstDate, String endDate) {
		return zhengFuBaoJingTongJiMapper.selectGetZFBaoJingQuShi_XiaJi(xiaJiDeptId,firstDate,endDate);
	}

	@Override
	public List<ZhengFuBaoJingTongJi> selectGetZFQYBaoJingPaiMing(String deptId) {
		return zhengFuBaoJingTongJiMapper.selectGetZFQYBaoJingPaiMing(deptId);
	}

	@Override
	public List<ZhengFuBaoJingTongJi> selectGetZFQYBaoJingPaiMing_XiaJi(String xiaJiDeptId) {
		return zhengFuBaoJingTongJiMapper.selectGetZFQYBaoJingPaiMing_XiaJi(xiaJiDeptId);
	}

	@Override
	public List<ZhengFuBaoJingTongJi> selectGetZFDQBaoJingPaiMing(String deptId) {
		return zhengFuBaoJingTongJiMapper.selectGetZFDQBaoJingPaiMing(deptId);
	}

	@Override
	public List<ZhengFuBaoJingTongJi> selectGetZFDQBaoJingPaiMing_XiaJi(String xiaJiDeptId) {
		return zhengFuBaoJingTongJiMapper.selectGetZFDQBaoJingPaiMing_XiaJi(xiaJiDeptId);
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage selectGetBJTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {

		Integer total = zhengFuBaoJingTongJiMapper.selectGetBJTJTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				return zhengFuBaoJingTongJiJieSuanPage;
			}else {
				List<ZhengFuBaoJingTongJiJieSuan> zhengFuBaoJingTongJiList = zhengFuBaoJingTongJiMapper.selectGetBJTJ(zhengFuBaoJingTongJiJieSuanPage);
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
			List<ZhengFuBaoJingTongJiJieSuan> zhengFuBaoJingTongJiList = zhengFuBaoJingTongJiMapper.selectGetBJTJ(zhengFuBaoJingTongJiJieSuanPage);
			zhengFuBaoJingTongJiJieSuanPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage selectGetDqLvTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
//		List<ZhengFuBaoJingTongJi> zhengFuBaoJingTongJi = zhengFuBaoJingTongJiMapper.selectGetZFYG(zhengFuBaoJingTongJiJieSuanPage.getDeptId());
//		if(zhengFuBaoJingTongJi.size() < 1){
//			zhengFuBaoJingTongJiJieSuanPage.setXiaJiDeptId(zhengFuBaoJingTongJiJieSuanPage.getDeptId());
//		}
		Integer total = zhengFuBaoJingTongJiMapper.selectGetDqLvTJTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			List<ZhengFuBaoJingTongJiLv> zhengFuBaoJingTongJiList = zhengFuBaoJingTongJiMapper.selectGetDqLvTJ(zhengFuBaoJingTongJiJieSuanPage);
			//List<ZhengFuBaoJingTongJiLv> zhengFuBaoJingTongJiLvs = zhengFuBaoJingTongJiMapper.selectGetZFBJCLS(zhengFuBaoJingTongJiJieSuanPage);

			zhengFuBaoJingTongJiJieSuanPage.setRecords(zhengFuBaoJingTongJiList);
			return zhengFuBaoJingTongJiJieSuanPage;
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
			List<ZhengFuBaoJingTongJiLv> zhengFuBaoJingTongJiList = zhengFuBaoJingTongJiMapper.selectGetDqLvTJ(zhengFuBaoJingTongJiJieSuanPage);
			//List<ZhengFuBaoJingTongJiLv> zhengFuBaoJingTongJiLvs = zhengFuBaoJingTongJiMapper.selectGetZFBJCLS(zhengFuBaoJingTongJiJieSuanPage);

			zhengFuBaoJingTongJiJieSuanPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage selectGetQYLvTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectGetQYLvTJTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				return zhengFuBaoJingTongJiJieSuanPage;
			}else {
				List<ZhengFuBaoJingTongJiLv> zhengFuBaoJingTongJiList = zhengFuBaoJingTongJiMapper.selectGetQYLvTJ(zhengFuBaoJingTongJiJieSuanPage);
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
			List<ZhengFuBaoJingTongJiLv> zhengFuBaoJingTongJiList = zhengFuBaoJingTongJiMapper.selectGetQYLvTJ(zhengFuBaoJingTongJiJieSuanPage);
			zhengFuBaoJingTongJiJieSuanPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage selectGetGPSMXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		if("未申诉".equals(zhengFuBaoJingTongJiJieSuanPage.getShifoushenshu()) && "已处理".equals(zhengFuBaoJingTongJiJieSuanPage.getShifouchuli())){
			zhengFuBaoJingTongJiJieSuanPage.setShifoushenshu("");
		}else if("已申诉".equals(zhengFuBaoJingTongJiJieSuanPage.getShifoushenshu()) && "未处理".equals(zhengFuBaoJingTongJiJieSuanPage.getShifouchuli())){
			zhengFuBaoJingTongJiJieSuanPage.setShifouchuli("");
		}else if("已申诉".equals(zhengFuBaoJingTongJiJieSuanPage.getShifoushenshu()) && "已处理".equals(zhengFuBaoJingTongJiJieSuanPage.getShifouchuli())){

		}
		Integer total = zhengFuBaoJingTongJiMapper.selectGetGPSMXTJTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(total==0){
			return zhengFuBaoJingTongJiJieSuanPage;
		}
		//导出
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			List<ZhengFuBaoJingMingXi> list = zhengFuBaoJingTongJiMapper.selectGetGPSMXTJ(zhengFuBaoJingTongJiJieSuanPage);
			list.forEach(item->{
				if(item.getRemark()==null){
					item.setShensuzhuangtai("未申诉");
					item.setChulizhuangtai("未处理");
					item.setChulimiaoshu("");
					item.setChulixingshi("");
				}
//				else if(item.getRemark()==1){
//					EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
//					item.setChulizhuangtai(byValue.desc);
//					item.setShensuzhuangtai("未申诉");
//				}else{
//					EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
//					item.setShensuzhuangtai(byValue.desc);
//					item.setChulizhuangtai("未处理");
//					item.setShensumiaoshu(item.getChulimiaoshu());
//					item.setShensuxingshi(item.getChulixingshi());
//					item.setChulimiaoshu("");
//					item.setChulixingshi("");
//				}
				if(StringUtils.isBlank(item.getRoadName())){
					double lat = Double.parseDouble(item.getLatitude());
					double lon = Double.parseDouble(item.getLongitude());
					double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
					item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
					item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
					String LocalName= LatLotForLocation.getProvince(item.getLatitude(),item.getLongitude(),alarmServer.getAddressAk());
					item.setRoadName(LocalName);
				}
			});
			zhengFuBaoJingTongJiJieSuanPage.setRecords(list);
			return zhengFuBaoJingTongJiJieSuanPage;
		}
		//分页查询
		Integer pagetotal = 0;
		if (total > 0) {
			pagetotal = total / zhengFuBaoJingTongJiJieSuanPage.getSize() + 1;
		}
		if (pagetotal >= zhengFuBaoJingTongJiJieSuanPage.getCurrent() && pagetotal > 0) {
			zhengFuBaoJingTongJiJieSuanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (zhengFuBaoJingTongJiJieSuanPage.getCurrent() > 1) {
				offsetNo = zhengFuBaoJingTongJiJieSuanPage.getSize() * (zhengFuBaoJingTongJiJieSuanPage.getCurrent() - 1);
			}
			zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			zhengFuBaoJingTongJiJieSuanPage.setOffsetNo(offsetNo);
			List<ZhengFuBaoJingMingXi> rows = zhengFuBaoJingTongJiMapper.selectGetGPSMXTJ(zhengFuBaoJingTongJiJieSuanPage);
			rows.forEach(item->{
				if(item.getRemark()==null){
					item.setShensuzhuangtai("未申诉");
					item.setChulizhuangtai("未处理");
					item.setChulimiaoshu("");
					item.setChulixingshi("");
				}
//				else if(item.getRemark()==1){
//					EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
//					item.setChulizhuangtai(byValue.desc);
//					item.setShensuzhuangtai("未申诉");
//				}else{
//					EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
//					item.setShensuzhuangtai(byValue.desc);
//					item.setChulizhuangtai("未处理");
//					item.setShensumiaoshu(item.getChulimiaoshu());
//					item.setShensuxingshi(item.getChulixingshi());
//					item.setChulimiaoshu("");
//					item.setChulixingshi("");
//				}
				if(StringUtils.isBlank(item.getRoadName())){
					double lat = Double.parseDouble(item.getLatitude());
					double lon = Double.parseDouble(item.getLongitude());
					double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
					item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
					item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
					String LocalName= LatLotForLocation.getProvince(item.getLatitude(),item.getLongitude(),alarmServer.getAddressAk());
					item.setRoadName(LocalName);
				}
			});
			zhengFuBaoJingTongJiJieSuanPage.setRecords(rows);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage selectGetDMSMXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectGetDMSMXTJTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			List<ZhengFuBaoJingMingXi> list=zhengFuBaoJingTongJiMapper.selectGetDMSMXTJ(zhengFuBaoJingTongJiJieSuanPage);
			list.forEach(item->{
				if(item.getRemark()==null){
					item.setShensuzhuangtai("未申诉");
					item.setChulizhuangtai("未处理");
					item.setChulimiaoshu("");
					item.setChulixingshi("");
				}
//				else if(item.getRemark()==1){
//					EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
//					item.setChulizhuangtai(byValue.desc);
//					item.setShensuzhuangtai("未申诉");
//				}else{
//					EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
//					item.setShensuzhuangtai(byValue.desc);
//					item.setChulizhuangtai("未处理");
//					item.setShensumiaoshu(item.getChulimiaoshu());
//					item.setShensuxingshi(item.getChulixingshi());
//					item.setChulimiaoshu("");
//					item.setChulixingshi("");
//				}
				if(StringUtils.isBlank(item.getRoadName())){
					double lat = Double.parseDouble(item.getLatitude());
					double lon = Double.parseDouble(item.getLongitude());
					double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
					item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
					item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
					String LocalName= LatLotForLocation.getProvince(item.getLatitude(),item.getLongitude(),alarmServer.getAddressAk());
					item.setRoadName(LocalName);
				}
			});
			zhengFuBaoJingTongJiJieSuanPage.setRecords(list);
			return zhengFuBaoJingTongJiJieSuanPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			pagetotal = total / zhengFuBaoJingTongJiJieSuanPage.getSize() + 1;
		}
		if (pagetotal >= zhengFuBaoJingTongJiJieSuanPage.getCurrent() && pagetotal > 0) {
			zhengFuBaoJingTongJiJieSuanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (zhengFuBaoJingTongJiJieSuanPage.getCurrent() > 1) {
				offsetNo = zhengFuBaoJingTongJiJieSuanPage.getSize() * (zhengFuBaoJingTongJiJieSuanPage.getCurrent() - 1);
			}
			zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			zhengFuBaoJingTongJiJieSuanPage.setOffsetNo(offsetNo);
			List<ZhengFuBaoJingMingXi> list = zhengFuBaoJingTongJiMapper.selectGetDMSMXTJ(zhengFuBaoJingTongJiJieSuanPage);
			list.forEach(item->{
				if(item.getRemark()==null){
					item.setShensuzhuangtai("未申诉");
					item.setChulizhuangtai("未处理");
					item.setChulimiaoshu("");
					item.setChulixingshi("");
				}
//				else if(item.getRemark()==1){
//					EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
//					item.setChulizhuangtai(byValue.desc);
//					item.setShensuzhuangtai("未申诉");
//				}else{
//					EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
//					item.setShensuzhuangtai(byValue.desc);
//					item.setChulizhuangtai("未处理");
//					item.setShensumiaoshu(item.getChulimiaoshu());
//					item.setShensuxingshi(item.getChulixingshi());
//					item.setChulimiaoshu("");
//					item.setChulixingshi("");
//				}
				if(StringUtils.isBlank(item.getRoadName())){
					double lat = Double.parseDouble(item.getLatitude());
					double lon = Double.parseDouble(item.getLongitude());
					double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
					item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
					item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
					String LocalName= LatLotForLocation.getProvince(item.getLatitude(),item.getLongitude(),alarmServer.getAddressAk());
					item.setRoadName(LocalName);
				}
			});
			zhengFuBaoJingTongJiJieSuanPage.setRecords(list);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage selectGetVehicleMXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectGetVehicleMXTJTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				return zhengFuBaoJingTongJiJieSuanPage;
			}else {
				List<ZhengFuBaoJingVehicle> zhengFuBaoJingTongJiList = zhengFuBaoJingTongJiMapper.selectGetVehicleMXTJ(zhengFuBaoJingTongJiJieSuanPage);
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
			List<ZhengFuBaoJingVehicle> zhengFuBaoJingTongJiList = zhengFuBaoJingTongJiMapper.selectGetVehicleMXTJ(zhengFuBaoJingTongJiJieSuanPage);
			zhengFuBaoJingTongJiJieSuanPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage selectGetAllMXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectGetAllMXTJTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			List<ZhengFuBaoJingMingXi> list=zhengFuBaoJingTongJiMapper.selectGetAllMXTJ(zhengFuBaoJingTongJiJieSuanPage);
			list.forEach(item->{
				if(item.getRemark()==null){
					item.setShensuzhuangtai("未申诉");
					item.setChulizhuangtai("未处理");
					item.setChulimiaoshu("");
					item.setChulixingshi("");
				}
//				else if(item.getRemark()==1){
//					EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
//					item.setChulizhuangtai(byValue.desc);
//					item.setShensuzhuangtai("未申诉");
//				}else{
//					EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
//					item.setShensuzhuangtai(byValue.desc);
//					item.setChulizhuangtai("未处理");
//					item.setShensumiaoshu(item.getChulimiaoshu());
//					item.setShensuxingshi(item.getChulixingshi());
//					item.setChulimiaoshu("");
//					item.setChulixingshi("");
//				}
				if(StringUtils.isBlank(item.getRoadName())){
					double lat = Double.parseDouble(item.getLatitude());
					double lon = Double.parseDouble(item.getLongitude());
					double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
					item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
					item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
					String LocalName= LatLotForLocation.getProvince(item.getLatitude(),item.getLongitude(),alarmServer.getAddressAk());
					item.setRoadName(LocalName);
				}
			});
			zhengFuBaoJingTongJiJieSuanPage.setRecords(list);
			return zhengFuBaoJingTongJiJieSuanPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			pagetotal = total / zhengFuBaoJingTongJiJieSuanPage.getSize() + 1;
		}
		if (pagetotal >= zhengFuBaoJingTongJiJieSuanPage.getCurrent() && pagetotal > 0) {
			zhengFuBaoJingTongJiJieSuanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (zhengFuBaoJingTongJiJieSuanPage.getCurrent() > 1) {
				offsetNo = zhengFuBaoJingTongJiJieSuanPage.getSize() * (zhengFuBaoJingTongJiJieSuanPage.getCurrent() - 1);
			}
			zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			zhengFuBaoJingTongJiJieSuanPage.setOffsetNo(offsetNo);
			List<ZhengFuBaoJingMingXi> list = zhengFuBaoJingTongJiMapper.selectGetAllMXTJ(zhengFuBaoJingTongJiJieSuanPage);
			list.forEach(item->{
				if(item.getRemark()==null){
					item.setShensuzhuangtai("未申诉");
					item.setChulizhuangtai("未处理");
					item.setChulimiaoshu("");
					item.setChulixingshi("");
				}
//				else if(item.getRemark()==1){
//					EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
//					item.setChulizhuangtai(byValue.desc);
//					item.setShensuzhuangtai("未申诉");
//				}else{
//					EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
//					item.setShensuzhuangtai(byValue.desc);
//					item.setChulizhuangtai("未处理");
//					item.setShensumiaoshu(item.getChulimiaoshu());
//					item.setShensuxingshi(item.getChulixingshi());
//					item.setChulimiaoshu("");
//					item.setChulixingshi("");
//				}
				if(StringUtils.isBlank(item.getRoadName())){
					double lat = Double.parseDouble(item.getLatitude());
					double lon = Double.parseDouble(item.getLongitude());
					double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
					item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
					item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
					String LocalName= LatLotForLocation.getProvince(item.getLatitude(),item.getLongitude(),alarmServer.getAddressAk());
					item.setRoadName(LocalName);
				}
			});
			zhengFuBaoJingTongJiJieSuanPage.setRecords(list);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage selectGetDQTJPMTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectDQTJPMTotal(zhengFuBaoJingTongJiJieSuanPage);

		List<ZhengFuBaoJingTongJiJieSuan> organizationList;
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				return zhengFuBaoJingTongJiJieSuanPage;
			}else {
				organizationList = zhengFuBaoJingTongJiMapper.selectGetDQTJPMTJ(zhengFuBaoJingTongJiJieSuanPage);
//			for (int i = 0;i<organizationList.size();i++){
//				List<ZhengFuBaoJingTongJiJieSuan> zhengFuBaoJingTongJiJieSuans = zhengFuBaoJingTongJiMapper.seletGetVehicleCount(zhengFuBaoJingTongJiJieSuanPage);
//				for(int j = 0;j<=i;j++){
//					if(zhengFuBaoJingTongJiJieSuans.get(j).getZhengfuid().equals(organizationList.get(i).getZhengfuid())){
//						organizationList.get(i).setCheliangshu(zhengFuBaoJingTongJiJieSuans.get(j).getCheliangshu());
//					}
//				}
//			}
				zhengFuBaoJingTongJiJieSuanPage.setRecords(organizationList);
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
			organizationList = zhengFuBaoJingTongJiMapper.selectGetDQTJPMTJ(zhengFuBaoJingTongJiJieSuanPage);
//			for (int i = 0;i<organizationList.size();i++){
//				List<ZhengFuBaoJingTongJiJieSuan> zhengFuBaoJingTongJiJieSuans = zhengFuBaoJingTongJiMapper.seletGetVehicleCount(zhengFuBaoJingTongJiJieSuanPage);
//				for(int j = 0;j<=i;j++){
//					if(zhengFuBaoJingTongJiJieSuans.get(j).getZhengfuid().equals(organizationList.get(i).getZhengfuid())){
//						organizationList.get(i).setCheliangshu(zhengFuBaoJingTongJiJieSuans.get(j).getCheliangshu());
//					}
//				}
//			}
			zhengFuBaoJingTongJiJieSuanPage.setRecords(organizationList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage selectGetCLTJPMTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectCLTJPMTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			List<ZhengFuBaoJingTongJiJieSuan> organizationList = zhengFuBaoJingTongJiMapper.selectGetCLTJPMTJ(zhengFuBaoJingTongJiJieSuanPage);
			zhengFuBaoJingTongJiJieSuanPage.setRecords(organizationList);
			return zhengFuBaoJingTongJiJieSuanPage;
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
			List<ZhengFuBaoJingTongJiJieSuan> organizationList = zhengFuBaoJingTongJiMapper.selectGetCLTJPMTJ(zhengFuBaoJingTongJiJieSuanPage);
			zhengFuBaoJingTongJiJieSuanPage.setRecords(organizationList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public List<TtsMessageInfo> selectByAlarmGuId(String alarmGuId) {
		return zhengFuBaoJingTongJiMapper.selectByAlarmGuId(alarmGuId);
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage<ZhengFuRiYunXingTongJi> selectGetCLRYXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectCLRYXTJTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				return zhengFuBaoJingTongJiJieSuanPage;
			}else {
				List<ZhengFuRiYunXingTongJi> organizationList = zhengFuBaoJingTongJiMapper.selectGetCLRYXTJ(zhengFuBaoJingTongJiJieSuanPage);
				zhengFuBaoJingTongJiJieSuanPage.setRecords(organizationList);
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
			List<ZhengFuRiYunXingTongJi> organizationList = zhengFuBaoJingTongJiMapper.selectGetCLRYXTJ(zhengFuBaoJingTongJiJieSuanPage);
			zhengFuBaoJingTongJiJieSuanPage.setRecords(organizationList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage<ZhengFuRiYunXingTongJi> selectGetQYRYXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectQYRYXTJTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				return zhengFuBaoJingTongJiJieSuanPage;
			}else {
				List<ZhengFuRiYunXingTongJi> zhengFuRiYunXingTongJiList = zhengFuBaoJingTongJiMapper.selectGetQYRYXTJ(zhengFuBaoJingTongJiJieSuanPage);
				zhengFuBaoJingTongJiJieSuanPage.setRecords(zhengFuRiYunXingTongJiList);
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
			List<ZhengFuRiYunXingTongJi> zhengFuRiYunXingTongJiList = zhengFuBaoJingTongJiMapper.selectGetQYRYXTJ(zhengFuBaoJingTongJiJieSuanPage);
			zhengFuBaoJingTongJiJieSuanPage.setRecords(zhengFuRiYunXingTongJiList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public List<ZhengFuBaoJingTongJiJieSuan> seletGetVehicleCount(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		return zhengFuBaoJingTongJiMapper.seletGetVehicleCount(zhengFuBaoJingTongJiJieSuanPage);
	}

	@Override
	public ZhengFuRiskRankingPage<ZhengFuRiskRanking> selectGetDQRiskPMTJ(ZhengFuRiskRankingPage zhengFuRiskRankingPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectDQRiskPMTotal(zhengFuRiskRankingPage);
		if(zhengFuRiskRankingPage.getSize()==0){
			if(zhengFuRiskRankingPage.getTotal()==0){
				zhengFuRiskRankingPage.setTotal(total);
			}
			if(zhengFuRiskRankingPage.getTotal()==0){
				return zhengFuRiskRankingPage;
			}else {
				List<ZhengFuRiskRanking> zhengFuRiskRankingList = zhengFuBaoJingTongJiMapper.selectGetDQRiskPMTJ(zhengFuRiskRankingPage);
				zhengFuRiskRankingPage.setRecords(zhengFuRiskRankingList);
				return zhengFuRiskRankingPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%zhengFuRiskRankingPage.getSize()==0){
				pagetotal = total / zhengFuRiskRankingPage.getSize();
			}else {
				pagetotal = total / zhengFuRiskRankingPage.getSize() + 1;
			}
		}
		if (pagetotal >= zhengFuRiskRankingPage.getCurrent()) {
			zhengFuRiskRankingPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (zhengFuRiskRankingPage.getCurrent() > 1) {
				offsetNo = zhengFuRiskRankingPage.getSize() * (zhengFuRiskRankingPage.getCurrent() - 1);
			}
			zhengFuRiskRankingPage.setTotal(total);
			zhengFuRiskRankingPage.setOffsetNo(offsetNo);
			List<ZhengFuRiskRanking> zhengFuRiskRankingList = zhengFuBaoJingTongJiMapper.selectGetDQRiskPMTJ(zhengFuRiskRankingPage);
			zhengFuRiskRankingPage.setRecords(zhengFuRiskRankingList);
		}
		return zhengFuRiskRankingPage;
	}

	@Override
	public ZhengFuIntoAreaPage<ZhengFuIntoArea> selectDQIntoAreaPMTJ(ZhengFuIntoAreaPage zhengFuIntoAreaPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectDQIntoAreaPMTotal(zhengFuIntoAreaPage);
		if(zhengFuIntoAreaPage.getSize()==0){
			if(zhengFuIntoAreaPage.getTotal()==0){
				zhengFuIntoAreaPage.setTotal(total);
			}
			if(zhengFuIntoAreaPage.getTotal()==0){
				return zhengFuIntoAreaPage;
			}else {
				List<ZhengFuIntoArea> zhengFuIntoAreaList = zhengFuBaoJingTongJiMapper.selectDQIntoAreaPMTJ(zhengFuIntoAreaPage);
				List<ZhengFuIntoArea> zhengFuIntoAreas = zhengFuBaoJingTongJiMapper.selectDQVehCount(zhengFuIntoAreaPage);
				zhengFuIntoAreaList.forEach(item-> {
					zhengFuIntoAreas.forEach(items-> {
						if(item.getZhengfuid().equals(items.getZhengfuid())){
							item.setCheliangshu(items.getCheliangshu());
						}
					});
				});
				zhengFuIntoAreaPage.setRecords(zhengFuIntoAreaList);
				return zhengFuIntoAreaPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%zhengFuIntoAreaPage.getSize()==0){
				pagetotal = total / zhengFuIntoAreaPage.getSize();
			}else {
				pagetotal = total / zhengFuIntoAreaPage.getSize() + 1;
			}
		}
		if (pagetotal >= zhengFuIntoAreaPage.getCurrent()) {
			zhengFuIntoAreaPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (zhengFuIntoAreaPage.getCurrent() > 1) {
				offsetNo = zhengFuIntoAreaPage.getSize() * (zhengFuIntoAreaPage.getCurrent() - 1);
			}
			zhengFuIntoAreaPage.setTotal(total);
			zhengFuIntoAreaPage.setOffsetNo(offsetNo);
			List<ZhengFuIntoArea> zhengFuIntoAreaList = zhengFuBaoJingTongJiMapper.selectDQIntoAreaPMTJ(zhengFuIntoAreaPage);
			List<ZhengFuIntoArea> zhengFuIntoAreas = zhengFuBaoJingTongJiMapper.selectDQVehCount(zhengFuIntoAreaPage);
			zhengFuIntoAreaList.forEach(item-> {
				zhengFuIntoAreas.forEach(items-> {
					if(item.getZhengfuid().equals(items.getZhengfuid())){
						item.setCheliangshu(items.getCheliangshu());
					}
				});
			});
			zhengFuIntoAreaPage.setRecords(zhengFuIntoAreaList);
		}
		return zhengFuIntoAreaPage;
	}

	@Override
	public ZhengFuIntoAreaPage<ZhengFuIntoArea> selectDeptIntoAreaPMTJ(ZhengFuIntoAreaPage zhengFuIntoAreaPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectDeptIntoAreaPMTotal(zhengFuIntoAreaPage);
		if(zhengFuIntoAreaPage.getSize()==0){
			if(zhengFuIntoAreaPage.getTotal()==0){
				zhengFuIntoAreaPage.setTotal(total);
			}
			if(zhengFuIntoAreaPage.getTotal()==0){
				return zhengFuIntoAreaPage;
			}else {
				List<ZhengFuIntoArea> zhengFuIntoAreaList = zhengFuBaoJingTongJiMapper.selectDeptIntoAreaPMTJ(zhengFuIntoAreaPage);
				List<ZhengFuIntoArea> zhengFuIntoAreas = zhengFuBaoJingTongJiMapper.selectDeptVehCount(zhengFuIntoAreaPage);
				zhengFuIntoAreaList.forEach(item-> {
					zhengFuIntoAreas.forEach(items-> {
						if(item.getQiyeid().equals(items.getQiyeid())){
							item.setCheliangshu(items.getCheliangshu());
						}
					});
				});
				zhengFuIntoAreaPage.setRecords(zhengFuIntoAreaList);
				return zhengFuIntoAreaPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%zhengFuIntoAreaPage.getSize()==0){
				pagetotal = total / zhengFuIntoAreaPage.getSize();
			}else {
				pagetotal = total / zhengFuIntoAreaPage.getSize() + 1;
			}
		}
		if (pagetotal >= zhengFuIntoAreaPage.getCurrent()) {
			zhengFuIntoAreaPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (zhengFuIntoAreaPage.getCurrent() > 1) {
				offsetNo = zhengFuIntoAreaPage.getSize() * (zhengFuIntoAreaPage.getCurrent() - 1);
			}
			zhengFuIntoAreaPage.setTotal(total);
			zhengFuIntoAreaPage.setOffsetNo(offsetNo);
			List<ZhengFuIntoArea> zhengFuIntoAreaList = zhengFuBaoJingTongJiMapper.selectDeptIntoAreaPMTJ(zhengFuIntoAreaPage);
			List<ZhengFuIntoArea> zhengFuIntoAreas = zhengFuBaoJingTongJiMapper.selectDeptVehCount(zhengFuIntoAreaPage);
			zhengFuIntoAreaList.forEach(item-> {
				zhengFuIntoAreas.forEach(items-> {
					if(item.getQiyeid().equals(items.getQiyeid())){
						item.setCheliangshu(items.getCheliangshu());
					}
				});
			});
			zhengFuIntoAreaPage.setRecords(zhengFuIntoAreaList);
		}
		return zhengFuIntoAreaPage;
	}

	@Override
	public ZhengFuIntoAreaPage<ZhengFuIntoArea> selectDeptCLIntoAreaPMTJ(ZhengFuIntoAreaPage zhengFuIntoAreaPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectDeptCLIntoAreaPMTotal(zhengFuIntoAreaPage);
		if(zhengFuIntoAreaPage.getSize()==0){
			if(zhengFuIntoAreaPage.getTotal()==0){
				zhengFuIntoAreaPage.setTotal(total);
			}
			if(zhengFuIntoAreaPage.getTotal()==0){
				return zhengFuIntoAreaPage;
			}else {
				List<ZhengFuIntoArea> zhengFuIntoAreaList = zhengFuBaoJingTongJiMapper.selectDeptCLIntoAreaPMTJ(zhengFuIntoAreaPage);
				List<ZhengFuIntoArea> zhengFuIntoAreas = zhengFuBaoJingTongJiMapper.selectDeptVehCount(zhengFuIntoAreaPage);
				zhengFuIntoAreaList.forEach(item-> {
					zhengFuIntoAreas.forEach(items-> {
						if(item.getQiyeid().equals(items.getQiyeid())){
							item.setCheliangshu(items.getCheliangshu());
						}
					});
				});
				zhengFuIntoAreaPage.setRecords(zhengFuIntoAreaList);
				return zhengFuIntoAreaPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%zhengFuIntoAreaPage.getSize()==0){
				pagetotal = total / zhengFuIntoAreaPage.getSize();
			}else {
				pagetotal = total / zhengFuIntoAreaPage.getSize() + 1;
			}
		}
		if (pagetotal >= zhengFuIntoAreaPage.getCurrent()) {
			zhengFuIntoAreaPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (zhengFuIntoAreaPage.getCurrent() > 1) {
				offsetNo = zhengFuIntoAreaPage.getSize() * (zhengFuIntoAreaPage.getCurrent() - 1);
			}
			zhengFuIntoAreaPage.setTotal(total);
			zhengFuIntoAreaPage.setOffsetNo(offsetNo);
			List<ZhengFuIntoArea> zhengFuIntoAreaList = zhengFuBaoJingTongJiMapper.selectDeptCLIntoAreaPMTJ(zhengFuIntoAreaPage);
			List<ZhengFuIntoArea> zhengFuIntoAreas = zhengFuBaoJingTongJiMapper.selectDeptVehCount(zhengFuIntoAreaPage);
			zhengFuIntoAreaList.forEach(item-> {
				zhengFuIntoAreas.forEach(items-> {
					if(item.getQiyeid().equals(items.getQiyeid())){
						item.setCheliangshu(items.getCheliangshu());
					}
				});
			});
			zhengFuIntoAreaPage.setRecords(zhengFuIntoAreaList);
		}
		return zhengFuIntoAreaPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage<ZhengFuOperatorMonitorTongJi> selectYYSRYXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectYYSRYXTJTotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				return zhengFuBaoJingTongJiJieSuanPage;
			}else {
				List<ZhengFuOperatorMonitorTongJi> zhengFuOperatorMonitorTongJiList = zhengFuBaoJingTongJiMapper.selectYYSRYXTJ(zhengFuBaoJingTongJiJieSuanPage);
				zhengFuBaoJingTongJiJieSuanPage.setRecords(zhengFuOperatorMonitorTongJiList);
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
			List<ZhengFuOperatorMonitorTongJi> zhengFuOperatorMonitorTongJiList = zhengFuBaoJingTongJiMapper.selectYYSRYXTJ(zhengFuBaoJingTongJiJieSuanPage);
			zhengFuBaoJingTongJiJieSuanPage.setRecords(zhengFuOperatorMonitorTongJiList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage<QiYeLWLKTongJi> selectLWLKTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectLWLKTJotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				return zhengFuBaoJingTongJiJieSuanPage;
			}else {
				List<QiYeLWLKTongJi> qiYeLWLKTongJiList = zhengFuBaoJingTongJiMapper.selectLWLKTJ(zhengFuBaoJingTongJiJieSuanPage);
				zhengFuBaoJingTongJiJieSuanPage.setRecords(qiYeLWLKTongJiList);
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
			List<QiYeLWLKTongJi> qiYeLWLKTongJiList = zhengFuBaoJingTongJiMapper.selectLWLKTJ(zhengFuBaoJingTongJiJieSuanPage);
			zhengFuBaoJingTongJiJieSuanPage.setRecords(qiYeLWLKTongJiList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public ZhengFuBaoJingTongJiJieSuanPage<QiYeLWLKTongJi> selectFWSLWLKTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectFWSLWLKTJotal(zhengFuBaoJingTongJiJieSuanPage);
		if(zhengFuBaoJingTongJiJieSuanPage.getSize()==0){
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				zhengFuBaoJingTongJiJieSuanPage.setTotal(total);
			}
			if(zhengFuBaoJingTongJiJieSuanPage.getTotal()==0){
				return zhengFuBaoJingTongJiJieSuanPage;
			}else {
				List<QiYeLWLKTongJi> qiYeLWLKTongJiList = zhengFuBaoJingTongJiMapper.selectFWSLWLKTJ(zhengFuBaoJingTongJiJieSuanPage);
				zhengFuBaoJingTongJiJieSuanPage.setRecords(qiYeLWLKTongJiList);
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
			List<QiYeLWLKTongJi> qiYeLWLKTongJiList = zhengFuBaoJingTongJiMapper.selectFWSLWLKTJ(zhengFuBaoJingTongJiJieSuanPage);
			zhengFuBaoJingTongJiJieSuanPage.setRecords(qiYeLWLKTongJiList);
		}
		return zhengFuBaoJingTongJiJieSuanPage;
	}

	@Override
	public ZhengFuDongTaiJianKong selectGetZFJk(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		return zhengFuBaoJingTongJiMapper.selectGetZFJk(zhengFuBaoJingTongJiJieSuanPage);
	}

	@Override
	public SafetyproductionfileNumPage<AnbiaoSafetyproductionfileNum> selectTJ(SafetyproductionfileNumPage safetyproductionfileNumPage) {
		Integer total = zhengFuBaoJingTongJiMapper.selectTotal(safetyproductionfileNumPage);
		if(safetyproductionfileNumPage.getSize()==0){
			if(safetyproductionfileNumPage.getTotal()==0){
				safetyproductionfileNumPage.setTotal(total);
			}
			List<AnbiaoSafetyproductionfileNum> safetyproductionfileNumList = zhengFuBaoJingTongJiMapper.selectTJ(safetyproductionfileNumPage);
			safetyproductionfileNumList.forEach(item->{
				double num1 = item.getFinshNum();
				double num2 = item.getUploadedNum();
				double ratio = num1*1.0 / num2;
				if(item.getFinshNum() == 0){
					item.setFinshRatio("0.00%");
				}else {
					DecimalFormat df = new DecimalFormat("#.##");
					String formattedRatio = df.format(ratio)+"%";
					item.setFinshRatio(formattedRatio);
				}
			});
			safetyproductionfileNumPage.setRecords(safetyproductionfileNumList);
			return safetyproductionfileNumPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%safetyproductionfileNumPage.getSize()==0){
				pagetotal = total / safetyproductionfileNumPage.getSize();
			}else {
				pagetotal = total / safetyproductionfileNumPage.getSize() + 1;
			}
		}
		if (pagetotal >= safetyproductionfileNumPage.getCurrent()) {
			safetyproductionfileNumPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (safetyproductionfileNumPage.getCurrent() > 1) {
				offsetNo = safetyproductionfileNumPage.getSize() * (safetyproductionfileNumPage.getCurrent() - 1);
			}
			safetyproductionfileNumPage.setTotal(total);
			safetyproductionfileNumPage.setOffsetNo(offsetNo);
			List<AnbiaoSafetyproductionfileNum> safetyproductionfileNumList = zhengFuBaoJingTongJiMapper.selectTJ(safetyproductionfileNumPage);
			safetyproductionfileNumList.forEach(item->{
				double num1 = item.getFinshNum();
				double num2 = item.getUploadedNum();
				double ratio = num1*1.0 / num2;
				if(item.getFinshNum() == 0){
					item.setFinshRatio("0.00%");
				}else {
					DecimalFormat df = new DecimalFormat("#.##");
					String formattedRatio = df.format(ratio)+"%";
					item.setFinshRatio(formattedRatio);
				}
			});
			safetyproductionfileNumPage.setRecords(safetyproductionfileNumList);
		}
		return safetyproductionfileNumPage;
	}

}
