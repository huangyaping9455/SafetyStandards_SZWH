/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.anbiao.jinritixing.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.Baoxianxinxi;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.mapper.BaoxianxinxiMapper;
import org.springblade.anbiao.cheliangguanli.mapper.VehicleMapper;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.mapper.JiaShiYuanMapper;
import org.springblade.anbiao.jinritixing.entity.*;
import org.springblade.anbiao.jinritixing.mapper.JinritixingMapper;
import org.springblade.anbiao.jinritixing.page.JinritixingPage;
import org.springblade.anbiao.jinritixing.service.IJinritixingService;
import org.springblade.anbiao.jinritixing.vo.FyyhTiXingInfoVO;
import org.springblade.anbiao.jinritixing.vo.FyyhTiXingVO;
import org.springblade.anbiao.jinritixing.vo.JinritixingVO;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiang;
import org.springblade.anbiao.yinhuanpaicha.mapper.AnbiaoYinhuanpaichaXiangMapper;
import org.springblade.common.tool.DateUtils;
import org.springblade.common.tool.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 *  服务实现类
 */
@Service
@AllArgsConstructor
public class JinritixingServiceImpl extends ServiceImpl<JinritixingMapper, Jinritixing> implements IJinritixingService {

	private JinritixingMapper mapper;

	private VehicleMapper vehicleMapper;

	private JiaShiYuanMapper jiaShiYuanMapper;

	private AnbiaoYinhuanpaichaXiangMapper anbiaoYinhuanpaichaXiangMapper;

	private BaoxianxinxiMapper baoxianxinxiMapper;

	@Override
	public IPage<JinritixingVO> selectJinritixingPage(IPage<JinritixingVO> page, JinritixingVO jinritixing) {
		return page.setRecords(baseMapper.selectJinritixingPage(page, jinritixing));
	}

	@Override
	public boolean updateDel(String id) {
		return mapper.updateDel(id);
	}

	@Override
	public JinritixingPage<JinritixingVO> selectPageList(JinritixingPage page) {
		Integer total = mapper.selectTotal(page);
		if(page.getSize()==0){
			if(page.getTotal()==0){
				page.setTotal(total);
			}
			List<JinritixingVO> yuJingList = baseMapper.selectPageList(page);
			page.setRecords(yuJingList);
			return page;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%page.getSize()==0){
				pagetotal = total / page.getSize();
			}else {
				pagetotal = total / page.getSize() + 1;
			}
		}
		if (pagetotal >= page.getCurrent()) {
			page.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (page.getCurrent() > 1) {
				offsetNo = page.getSize() * (page.getCurrent() - 1);
			}
			page.setTotal(total);
			page.setOffsetNo(offsetNo);
			List<JinritixingVO> yuJingList = baseMapper.selectPageList(page);
			page.setRecords(yuJingList);
		}
		return page;
	}

	@Override
	public JinritixingVO selectByIds(String id) {
		return mapper.selectByIds(id);
	}

	@Override
	public JinritixingPage<JinritixingVO> selectLists(JinritixingPage Page) {
		Integer total = mapper.selectListTotal(Page);
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%Page.getSize()==0){
				pagetotal = total / Page.getSize();
			}else {
				pagetotal = total / Page.getSize() + 1;
			}
		}
		if (pagetotal < Page.getCurrent()) {
			return Page;
		} else {
			Page.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (Page.getCurrent() > 1) {
				offsetNo = Page.getSize() * (Page.getCurrent() - 1);
			}
			Page.setTotal(total);
			Page.setOffsetNo(offsetNo);
			List<JinritixingVO> vehlist = mapper.selectLists(Page);
			return (JinritixingPage<JinritixingVO>) Page.setRecords(vehlist);
		}
	}

	@Override
	public int selectNum(JinritixingPage Page) {
		return mapper.selectNum(Page);
	}

	@Override
	public JinritixingPage<FyyhTiXingVO> selectFYYHPageList(JinritixingPage jinritixingPage) {
		Integer total = mapper.selectFYYHTotal(jinritixingPage);
		if(jinritixingPage.getSize()==0){
			if(jinritixingPage.getTotal()==0){
				jinritixingPage.setTotal(total);
			}
			List<FyyhTiXingVO> fyyhTiXingVOList = mapper.selectFYYHPageList(jinritixingPage);
			jinritixingPage.setRecords(fyyhTiXingVOList);
			return jinritixingPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%jinritixingPage.getSize()==0){
				pagetotal = total / jinritixingPage.getSize();
			}else {
				pagetotal = total / jinritixingPage.getSize() + 1;
			}
		}
		if (pagetotal >= jinritixingPage.getCurrent()) {
			jinritixingPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (jinritixingPage.getCurrent() > 1) {
				offsetNo = jinritixingPage.getSize() * (jinritixingPage.getCurrent() - 1);
			}
			jinritixingPage.setTotal(total);
			jinritixingPage.setOffsetNo(offsetNo);
			List<FyyhTiXingVO> fyyhTiXingVOList = mapper.selectFYYHPageList(jinritixingPage);
			jinritixingPage.setRecords(fyyhTiXingVOList);
		}
		return jinritixingPage;
	}

	@Override
	public List<FyyhTiXingVO> selectCountScore(Integer deptId) {
		String begdate = DateUtils.getPastDate(30);
		String enddate = DateUtil.format(new Date(),"yyyy-MM-dd");
		return mapper.selectCountScore(deptId,begdate,enddate);
	}

	@Override
	public List<FyyhTiXingVO> selectFinshCountScore(Integer deptId) {
		String begdate = DateUtils.getPastDate(30);
		String enddate = DateUtil.format(new Date(),"yyyy-MM-dd");
		return mapper.selectFinshCountScore(deptId,begdate,enddate);
	}

	@Override
	public JinritixingPage<FyyhTiXingInfoVO> selectYHFLPageList(JinritixingPage jinritixingPage) {
		String begdate = DateUtils.getPastDate(30);
		String enddate = DateUtil.format(new Date(),"yyyy-MM-dd");
		jinritixingPage.setBegdate(begdate);
		jinritixingPage.setEnddate(enddate);
		Integer total = mapper.selectYHFLTotal(jinritixingPage);
		if(jinritixingPage.getSize()==0){
			if(jinritixingPage.getTotal()==0){
				jinritixingPage.setTotal(total);
			}
			List<FyyhTiXingInfoVO> yhflPageList = mapper.selectYHFLPageList(jinritixingPage);
			if(yhflPageList != null){
				yhflPageList.forEach(item->{
					if(StringUtils.isNotBlank(item.getCheliangid())){
						Vehicle vehicle = vehicleMapper.selectByKey(item.getCheliangid());
						if(vehicle != null){
							try {
								if(!"缺项".equals(item.getTixingleixing())){
									//获取class类的实例
									Class<?> c1 = Vehicle.class;
									//获取指定属性的值
									Field field = c1.getDeclaredField(item.getBiaoid());
									//打开私有访问
									field.setAccessible(true);
									//获取属性值
									String subjectType = (String) field.get(vehicle);
									System.out.println(subjectType);
									if(subjectType != null){
										item.setYouxiaoqi(subjectType);
									}
								}
							}catch (Exception e) {

							}
						}
					}

					if(StringUtils.isNotBlank(item.getJiashiyuanid())){
						JiaShiYuan jiaShiYuan = jiaShiYuanMapper.selectByIds(item.getJiashiyuanid());
						if(jiaShiYuan != null){
							try {
								if(!"缺项".equals(item.getTixingleixing())){
									//获取class类的实例
									Class<?> c1 = JiaShiYuan.class;
									//获取指定属性的值
									Field field = c1.getDeclaredField(item.getBiaoid());
									//打开私有访问
									field.setAccessible(true);
									//获取属性值
									String subjectType = (String) field.get(jiaShiYuan);
									System.out.println(subjectType);
									if(subjectType != null){
										item.setYouxiaoqi(subjectType);
									}
								}
							}catch (Exception e) {

							}
						}
					}
				});
			}
			jinritixingPage.setRecords(yhflPageList);
			return jinritixingPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%jinritixingPage.getSize()==0){
				pagetotal = total / jinritixingPage.getSize();
			}else {
				pagetotal = total / jinritixingPage.getSize() + 1;
			}
		}
		if (pagetotal >= jinritixingPage.getCurrent()) {
			jinritixingPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (jinritixingPage.getCurrent() > 1) {
				offsetNo = jinritixingPage.getSize() * (jinritixingPage.getCurrent() - 1);
			}
			jinritixingPage.setTotal(total);
			jinritixingPage.setOffsetNo(offsetNo);
			List<FyyhTiXingInfoVO> yhflPageList = mapper.selectYHFLPageList(jinritixingPage);
			if(yhflPageList != null){
				yhflPageList.forEach(item->{
					if(StringUtils.isNotBlank(item.getCheliangid())){
						Vehicle vehicle = vehicleMapper.selectByKey(item.getCheliangid());
						if(vehicle != null){
							try {
								QueryWrapper<AnbiaoYinhuanpaichaXiang> queryWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiang>();
								queryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getId, item.getTixingxiangqingid());
								queryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getIsdelete, 0);
								AnbiaoYinhuanpaichaXiang deailInfo = anbiaoYinhuanpaichaXiangMapper.selectOne(queryWrapper);
								if (deailInfo != null) {
									if(deailInfo.getName().equals("车辆交强险到期日期")
										|| deailInfo.getName().equals("车辆超赔险到期日期")
										|| deailInfo.getName().equals("车辆雇主险到期日期")){
										Baoxianxinxi baoxianxinxi = baoxianxinxiMapper.selectByVehIds(item.getCheliangid());
										if(!"缺项".equals(item.getTixingleixing())) {
											//获取class类的实例
											Class<?> c1 = Baoxianxinxi.class;
											//获取指定属性的值
											Field field = c1.getDeclaredField(item.getBiaoid());
											//打开私有访问
											field.setAccessible(true);
											//获取属性值
											String subjectType = (String) field.get(baoxianxinxi);
											if (subjectType != null) {
												item.setYouxiaoqi(subjectType.substring(0, 10));
											}
										}
									}else{
										if(!"缺项".equals(item.getTixingleixing())){
											//获取class类的实例
											Class<?> c1 = Vehicle.class;
											//获取指定属性的值
											Field field = c1.getDeclaredField(item.getBiaoid());
											//打开私有访问
											field.setAccessible(true);
											//获取属性值
											String subjectType = (String) field.get(vehicle);
											System.out.println(subjectType);
											if(subjectType != null){
												item.setYouxiaoqi(subjectType);
											}
										}
									}
								}
							}catch (Exception e) {

							}
						}
					}

					if(StringUtils.isNotBlank(item.getJiashiyuanid())){
						JiaShiYuan jiaShiYuan = jiaShiYuanMapper.selectByIds(item.getJiashiyuanid());
						if(jiaShiYuan != null){
							try {
								if(!"缺项".equals(item.getTixingleixing())){
									//获取class类的实例
									Class<?> c1 = JiaShiYuan.class;
									//获取指定属性的值
									Field field = c1.getDeclaredField(item.getBiaoid());
									//打开私有访问
									field.setAccessible(true);
									//获取属性值
									String subjectType = (String) field.get(jiaShiYuan);
									System.out.println(subjectType);
									if(subjectType != null){
										item.setYouxiaoqi(subjectType);
									}
								}
							}catch (Exception e) {

							}
						}
					}
				});
			}
			jinritixingPage.setRecords(yhflPageList);
		}
		return jinritixingPage;
	}

	@Override
	public List<FyyhTiXingInfoVO> selectJinritixingByDeptId(Integer deptId, Integer tixingxiangqingid) {
		String begdate = DateUtils.getPastDate(30);
		String enddate = DateUtil.format(new Date(),"yyyy-MM-dd");
		List<FyyhTiXingInfoVO> jinritixingList = mapper.selectJinritixingByDeptId(deptId, tixingxiangqingid, begdate, enddate);

		if(jinritixingList != null){
			jinritixingList.forEach(item->{
				if(StringUtils.isNotBlank(item.getCheliangid())){
//					QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
//					vehicleQueryWrapper.lambda().eq(Vehicle::getId, item.getCheliangid());
//					vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
					Vehicle vehicle = vehicleMapper.selectByKey(item.getCheliangid());
					if(vehicle != null){
						item.setCheliangid(vehicle.getId());
						item.setCheliangpaizhao(vehicle.getCheliangpaizhao());
						item.setChepaiyanse(vehicle.getChepaiyanse());
						item.setShiyongxingzhi(vehicle.getShiyongxingzhi());
					}
				}

				if(StringUtils.isNotBlank(item.getJiashiyuanid())){
//					QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
//					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, item.getJiashiyuanid());
//					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
					JiaShiYuan jiaShiYuan = jiaShiYuanMapper.selectByIds(item.getJiashiyuanid());
					if(jiaShiYuan != null){
						item.setJiashiyuanid(jiaShiYuan.getId());
						item.setJiashiyuanxingming(jiaShiYuan.getJiashiyuanxingming());
						item.setShoujihaoma(jiaShiYuan.getShoujihaoma());
						item.setCongyeliebie(jiaShiYuan.getCongyerenyuanleixing());
					}
				}
			});
		}
		return jinritixingList;
	}

	@Override
	public List<YinHuanVehicle> selectYHXDept() {
		return mapper.selectYHXDept();
	}

	@Override
	public List<YinHuanVehicle> selectVehQZBFSJ(Integer deptId, String type) {
		return mapper.selectVehQZBFSJ(deptId, type);
	}

	@Override
	public List<YinHuanVehicle> selectVehXCNSSJ(Integer deptId, String type) {
		return mapper.selectVehXCNSSJ(deptId, type);
	}

	@Override
	public List<YinHuanVehicle> selectVehXCNJSJ(Integer deptId, String type) {
		return mapper.selectVehXCNJSJ(deptId, type);
	}

	@Override
	public List<YinHuanVehicle> selectVehDLYSZYXQSJ(Integer deptId, String type) {
		return mapper.selectVehDLYSZYXQSJ(deptId, type);
	}

	@Override
	public List<YinHuanVehicle> selectVehXCJSPDQSJ(Integer deptId, String type) {
		return mapper.selectVehXCJSPDQSJ(deptId, type);
	}

	@Override
	public List<YinHuanVehicle> selectVehBXSJ(Integer deptId, Integer toubaoleixing, String type) {
		return mapper.selectVehBXSJ(deptId, toubaoleixing, type);
	}

	@Override
	public List<YinHuanDriver> selectDriverSJ(Integer deptId, String leixing, String type) {
		return mapper.selectDriverSJ(deptId, leixing, type);
	}

	@Override
	public List<YinHuanDept> selectDeptSJ(Integer deptId, String leixing, String type) {
		return mapper.selectDeptSJ(deptId, leixing, type);
	}

	@Override
	public YinHuanRate selectDeptRate(Integer deptId) {
		return mapper.selectDeptRate(deptId);
	}

}
