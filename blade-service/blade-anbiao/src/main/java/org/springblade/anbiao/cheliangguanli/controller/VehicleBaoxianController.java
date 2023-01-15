/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.anbiao.cheliangguanli.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.entity.VehicleBaoxian;
import org.springblade.anbiao.cheliangguanli.entity.VehicleBaoxianInfo;
import org.springblade.anbiao.cheliangguanli.entity.VehicleBaoxianMingxi;
import org.springblade.anbiao.cheliangguanli.service.IJiashiyuanBaoxianService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleBaoxianMingxiService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleBaoxianService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.vo.VehicleBaoxianVO;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanRuzhi;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanRuzhiService;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springblade.system.user.entity.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 车辆保险信息主表 控制器
 *
 * @author Blade
 * @since 2022-10-28
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/anbiao/vehiclebaoxian")
@Api(value = "车辆保险信息主表", tags = "车辆保险信息主表接口")
public class VehicleBaoxianController extends BladeController {

	private IVehicleBaoxianService vehicleBaoxianService;
	private IVehicleBaoxianMingxiService vehicleBaoxianMingxiService;
	private IVehicleService vehicleService;
	private IJiaShiYuanService jiaShiYuanService;
	private ISysClient iSysClient;
	private IJiashiyuanBaoxianService jiashiyuanBaoxianService;
	private IAnbiaoJiashiyuanRuzhiService ruzhiService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入vehicleBaoxian")
	public R<VehicleBaoxianInfo> detail(String avbId) {
//		VehicleBaoxian detail = vehicleBaoxianService.getOne(Condition.getQueryWrapper(vehicleBaoxian));
		VehicleBaoxianInfo detail = vehicleBaoxianService.queryDetail(avbId);
		return R.data(detail);
	}

	@GetMapping("/queryByVehicle")
	@ApiOperation(value = "根据被保险车辆ID查询保险详情", notes = "根据被保险车辆ID查询保险详情")
	public R<List<VehicleBaoxian>> queryByDept(String vehicleId) {
		R r = new R();
		VehicleBaoxian vehicleBaoxian = new VehicleBaoxian();
		vehicleBaoxian.setAvbAvIds(vehicleId);
		vehicleBaoxian.setAvbDelete(0);
		List<VehicleBaoxian> baoxian = vehicleBaoxianService.getBaseMapper().selectList(Condition.getQueryWrapper(vehicleBaoxian));
		if(baoxian != null) {
			baoxian.forEach(item-> {
				VehicleBaoxianMingxi mingxi = new VehicleBaoxianMingxi();
				mingxi.setAvbmAvbIds(item.getAvbIds());
				List<VehicleBaoxianMingxi> mingxiList = vehicleBaoxianMingxiService.getBaseMapper().selectList(Condition.getQueryWrapper(mingxi));
				item.setBaoxianMingxis(mingxiList);
			});
//			VehicleBaoxianInfo detail = vehicleBaoxianService.queryDetail(baoxian.getAvbIds());
			return R.data(baoxian);
		} else {
			r.setCode(500);
			r.setMsg("未查询到车辆保险信息！");
			return r;
		}
	}

	@GetMapping("/queryByMax")
	@ApiOperation(value = "根据被保险人车辆ID查询上次保险记录", notes = "根据被保险车辆ID查询保险详情")
	public R<VehicleBaoxian> queryByMax(String vehicleId) {
		return R.data(vehicleBaoxianService.queryByMax(vehicleId));
	}

	/**
	 * 自定义分页 车辆保险信息主表
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入vehicleBaoxian")
	public R<IPage<VehicleBaoxianVO>> page(VehicleBaoxianVO vehicleBaoxian, Query query,String avbAvIds) {
		IPage<VehicleBaoxianVO> pages = vehicleBaoxianService.selectVehicleBaoxianPage(Condition.getPage(query), vehicleBaoxian,avbAvIds);
		return R.data(pages);
	}

	@PostMapping("/insuranceImport")
	@ApiLog("企业端-车辆保险导入")
	@ApiOperation(value = "企业端-车辆保险导入", notes = "file")
	public R insuranceImport(@RequestParam(value = "file") MultipartFile file, BladeUser user) throws ParseException {
		R r = new R();
		if (user == null) {
			r.setCode(401);
			r.setMsg("用户权限验证失败");
			r.setData(null);
			r.setSuccess(false);
			return r;
		}
		ExcelReader reader = null;
		try {
			reader = ExcelUtil.getReader(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//时间默认格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//验证数据成功条数
		int successNum = 0;
		//验证数据错误条数
		int failNum = 0;
		//全局变量，只要一条数据不对，就为false
		boolean isDataValidity = true;
		//错误信息
		String errorStr = "";

		List<Map<String, Object>> readAll = reader.readAll();
		if(readAll.size() > 2000) {
			errorStr +="导入数据超过2000条，无法导入";
			r.setMsg(errorStr);
			r.setCode(400);
			return  r;
		}

		List<VehicleBaoxianMingxi> insurance = new ArrayList<>();
		VehicleBaoxianInfo baoxianInfo = new VehicleBaoxianInfo();
		VehicleBaoxian baoxian = new VehicleBaoxian();
		for(Map<String ,Object> mmap: readAll) {
			boolean isFail = false;
			Dept avbInsureDept = new Dept();
			Dept avbInsuredDept = new Dept();
			Vehicle avbInsuredVehicle = new Vehicle();
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

			String avbInsureName = String.valueOf(mmap.get("投保单位")).trim();		//投保单位
			String avbInsuredName = String.valueOf(mmap.get("保险单位")).trim();
			String avbInsuredContacts = String.valueOf(mmap.get("被保险人")).trim();
			String avbPolicyNo = String.valueOf(mmap.get("保险单号")).trim();
			String avbInsureContacts = String.valueOf(mmap.get("投保车辆")).trim();
			String avbInsureContactNumber = String.valueOf(mmap.get("投保联系人电话")).trim();
			String avbmRisk = String.valueOf(mmap.get("保险种类")).trim();
			String avbmName = String.valueOf(mmap.get("保险名称")).trim();
			String avbInsurancePeriodStart = String.valueOf(mmap.get("投保开始时间")).trim();
			String avbInsurancePeriodEnd = String.valueOf(mmap.get("投保结束时间")).trim();
			String daysRemaining = String.valueOf(mmap.get("投保剩余有效期")).trim();
			String avbmInsuranceAmount = String.valueOf(mmap.get("保险金额")).trim();
			String avbmBasicPremium = String.valueOf(mmap.get("保险费用")).trim();

//			if(baoxian == null) {
				if(StringUtil.isNotBlank(avbInsureName)) {
					avbInsureDept = iSysClient.getDeptByName(avbInsureName);
					if (avbInsureDept != null){
						baoxian.setAvbInsureIds(avbInsureDept.getId()+"");
						baoxian.setAvbInsureName(avbInsureName);
						baoxian.setAjbInsureDeptid(avbInsureDept.getId()+"");
						//投保人
						List<User> users = jiashiyuanBaoxianService.getDeptUser(avbInsureDept.getId().toString());
						if(users.size() > 0){
							users.forEach(item-> {
								if("主要负责人".equals(item.getPostName())){
									baoxian.setAvbInsureContacts(item.getName());
									baoxian.setAvbInsureContactNumber(item.getPhone());
									baoxian.setAvbInsureContactAddress(item.getAddress());
								}
							});
						}
					}else{
						isFail=true;
						errorStr += "投保企业不存在！";
					}
				} else {
					isFail=true;
					errorStr += "投保企业不能为空！";
				}

				//被保车辆所属单位
				if(StringUtil.isNotBlank(avbInsuredName)) {
					avbInsuredDept = iSysClient.getDeptByName(avbInsuredName);
					if(avbInsuredDept != null) {
						baoxian.setAvbDeptIds(new Long(avbInsuredDept.getId()));		//车辆所属企业
					} else {
						errorStr += "没有查询到被保险单位！";
					}
				} else {
					isFail=true;
					errorStr += "被保车辆不能为空！";
				}

				if(StringUtil.isNotBlank(avbInsureContacts) && avbInsureContacts != "null" ) {
					Vehicle v = new Vehicle();
					v.setCheliangpaizhao(avbInsureContacts);
					v.setIsdel(0);
					avbInsuredVehicle = vehicleService.getOne(Condition.getQueryWrapper(v));
					if(avbInsuredVehicle != null) {
						baoxian.setAvbAvIds(avbInsuredVehicle.getId());		//被保的车辆
						baoxian.setAvbInsuredIds(avbInsuredVehicle.getJiashiyuanid());
						JiaShiYuan driver = jiaShiYuanService.selectByIds(avbInsuredVehicle.getJiashiyuanid());
						if(driver != null) {
							baoxian.setAvbInsuredName(driver.getJiashiyuanxingming());
							baoxian.setAvbInsuredContacts(driver.getJiashiyuanxingming());
							baoxian.setAvbInsuredContactNumber(driver.getShoujihaoma());
							QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
							ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, driver.getId());
							ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
							AnbiaoJiashiyuanRuzhi ruzhiInfo = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
							if (ruzhiInfo != null) {
								baoxian.setAvbInsuredContactAddress(ruzhiInfo.getAjrAddress());
							}
						} else {
							errorStr += "没有查询到"+avbInsuredVehicle.getCheliangpaizhao()+"的车主信息！";
						}
					} else {
						errorStr += "没有查询到被保车辆，请查证后重新导入！";
					}
				}
				baoxian.setAvbPolicyNo(avbPolicyNo);
				baoxian.setAvbInsuranceDays(Integer.parseInt(daysRemaining));
				baoxian.setAvbDelete(0);
				if(StringUtil.isNotBlank(avbInsurancePeriodStart)) {
					baoxian.setAvbInsurancePeriodStart(dateFormat2.parse(avbInsurancePeriodStart).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				}
				if(StringUtil.isNotBlank(avbInsurancePeriodEnd)) {
					baoxian.setAvbInsurancePeriodEnd(dateFormat2.parse(avbInsurancePeriodEnd).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				}
//				if(StringUtil.isNotBlank(avbInsurancePeriodStart) && StringUtil.isNotBlank(avbInsurancePeriodEnd)) {
//					Date startTime = dateFormat2.parse(avbInsurancePeriodStart);
//					Date endTime = dateFormat2.parse(avbInsurancePeriodEnd);
//					long s = endTime.getTime() - startTime.getTime();
//					TimeUnit time = TimeUnit.DAYS;
//					long days = time.convert(s,TimeUnit.MICROSECONDS);
//					baoxian.setAvbInsuranceDays((int)days);
//				}
				baoxian.setAvbCreateByIds(user.getUserId()+"");
				baoxian.setAvbCreateByName(user.getUserName());
				baoxian.setAvbCreateTime(LocalDateTime.now());
				baoxianInfo.setBaoxian(baoxian);

//			}
			VehicleBaoxianMingxi mingxi = new VehicleBaoxianMingxi();
			mingxi.setAvbmRisk(avbmRisk);
			mingxi.setAvbmName(avbmName);
			mingxi.setAvbmBasicPremium(new BigDecimal(avbmBasicPremium));
			mingxi.setAvbmCompanyAmount(new BigDecimal(avbmInsuranceAmount));
			insurance.add(mingxi);
			if(isFail) {
				failNum++;
			} else {
				successNum++;
			}
		}
		baoxianInfo.setBaoxianMingxis(insurance);
		if(failNum > 0) {
			r.setCode(500);
			r.setMsg(errorStr);
			r.setSuccess(false);
			r.setData(null);
			return r;
		} else {
			boolean isSave = vehicleBaoxianService.save(baoxian);
			for (VehicleBaoxianMingxi baoxianMingxi: insurance) {
				baoxianMingxi.setAvbmAvbIds(baoxian.getAvbIds());
				if(StringUtil.isEmpty(baoxianMingxi.getAvbmName())){
					baoxianMingxi.setAvbmName("车辆");
				}
				vehicleBaoxianMingxiService.save(baoxianMingxi);
			}
			return R.status(isSave);
		}
	}

	/**
	 * 新增 车辆保险信息主表
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入vehicleBaoxian")
	public R save(@Valid @RequestBody VehicleBaoxianInfo vehicleBaoxian, BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setCode(401);
			r.setMsg("未授权，请重新登录！");
			return r;
		}
		VehicleBaoxian baoxian = vehicleBaoxian.getBaoxian();
		baoxian.setAvbApprove("0");
		baoxian.setAvbDelete(0);
//		if(StringUtil.isNotBlank(baoxian.getAvbInsuredIds())) {		//被保险人
//			JiaShiYuan jiaShiYuan = jiaShiYuanService.selectByIds(baoxian.getAvbInsuredIds());
//			if(jiaShiYuan != null) {
//				baoxian.setAvbInsuredName(jiaShiYuan.getJiashiyuanxingming());
//				baoxian.setAvbInsuredContactNumber(jiaShiYuan.getShoujihaoma());
//				baoxian.setAvbInsuredContactAddress(jiaShiYuan.getJiatingzhuzhi());
//				baoxian.setAvbInsuredContacts(jiaShiYuan.getJiashiyuanxingming());
//				baoxian.setAvbDeptIds(new Long(jiaShiYuan.getDeptId()));
//				baoxian.setAvbCertificateNumber(jiaShiYuan.getShenfenzhenghao());
//				baoxian.setAvbDeptIds(new Long(jiaShiYuan.getDeptId()));
//			}
//		}
//		if(StringUtil.isNotBlank(baoxian.getAvbInsureIds())) {
//			Dept dept = iSysClient.getDept(Integer.parseInt(baoxian.getAvbInsureIds()));
//			if(dept != null) {
//				baoxian.setAvbInsureName(dept.getDeptName());
//				baoxian.setAvbInsureContactNumber("");
//				baoxian.setAvbInsureContactAddress("");
//				baoxian.setAvbInsureContacts(dept.getDeptName());
//			}
//		}
		baoxian.setAvbCreateByIds(user.getUserId()+"");
		baoxian.setAvbCreateByName(user.getUserName());
		baoxian.setAvbCreateTime(LocalDateTime.now());
		boolean isSave = vehicleBaoxianService.save(baoxian);
		if(vehicleBaoxian.getBaoxianMingxis() != null && vehicleBaoxian.getBaoxianMingxis().size() > 0) {
			for (VehicleBaoxianMingxi baoxianMingxi: vehicleBaoxian.getBaoxianMingxis()) {
				baoxianMingxi.setAvbmAvbIds(vehicleBaoxian.getBaoxian().getAvbIds());
				vehicleBaoxianMingxiService.save(baoxianMingxi);
			}
		}
		return R.status(isSave);
	}

	/**
	 * 修改 车辆保险信息主表
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入vehicleBaoxian")
	public R update(@Valid @RequestBody VehicleBaoxianInfo vehicleBaoxian, BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setCode(401);
			r.setMsg("未授权，请重新登录！");
			return r;
		}
		VehicleBaoxian baoxian = vehicleBaoxian.getBaoxian();
		baoxian.setAvbApprove("0");
		baoxian.setAvbDelete(0);
//		if(StringUtil.isNotBlank(baoxian.getAvbInsuredIds())) {		//被保险人
//			JiaShiYuan jiaShiYuan = jiaShiYuanService.selectByIds(baoxian.getAvbInsuredIds());
//			if(jiaShiYuan != null) {
//				baoxian.setAvbInsuredName(jiaShiYuan.getJiashiyuanxingming());
//				baoxian.setAvbInsuredContactNumber(jiaShiYuan.getShoujihaoma());
//				baoxian.setAvbInsuredContactAddress(jiaShiYuan.getJiatingzhuzhi());
//				baoxian.setAvbInsuredContacts(jiaShiYuan.getJiashiyuanxingming());
//				baoxian.setAvbDeptIds(new Long(jiaShiYuan.getDeptId()));
//				baoxian.setAvbCertificateNumber(jiaShiYuan.getShenfenzhenghao());
//				baoxian.setAvbDeptIds(new Long(jiaShiYuan.getDeptId()));
//			}
//		}
//		if(StringUtil.isNotBlank(baoxian.getAvbInsureIds())) {
//			Dept dept = iSysClient.getDept(Integer.parseInt(baoxian.getAvbInsureIds()));
//			if(dept != null) {
//				baoxian.setAvbInsureName(dept.getDeptName());
//				baoxian.setAvbInsureContactNumber("");
//				baoxian.setAvbInsureContactAddress("");
//				baoxian.setAvbInsureContacts(dept.getDeptName());
//			}
//		}
		baoxian.setAvbUpdateByIds(user.getUserId()+"");
		baoxian.setAvbUpdateByName(user.getUserName());
		baoxian.setAvbUpdateTime(LocalDateTime.now());

		boolean isUpdate = vehicleBaoxianService.updateById(baoxian);
		if(vehicleBaoxian.getBaoxianMingxis() != null && vehicleBaoxian.getBaoxianMingxis().size() > 0) {
			for (VehicleBaoxianMingxi baoxianMingxi: vehicleBaoxian.getBaoxianMingxis()) {
				QueryWrapper<VehicleBaoxianMingxi> baoxianMingxiQueryWrapper = new QueryWrapper<VehicleBaoxianMingxi>();
				baoxianMingxiQueryWrapper.lambda().eq(VehicleBaoxianMingxi::getAvbmAvbIds,baoxian.getAvbIds());
				baoxianMingxiQueryWrapper.lambda().eq(VehicleBaoxianMingxi::getAvbmRisk,baoxianMingxi.getAvbmRisk());
				baoxianMingxiQueryWrapper.lambda().eq(VehicleBaoxianMingxi::getAvbmName,baoxianMingxi.getAvbmName());
				VehicleBaoxianMingxi deail = vehicleBaoxianMingxiService.getBaseMapper().selectOne(baoxianMingxiQueryWrapper);
				if(deail == null || deail.getAvbmIds() == null){
					baoxianMingxi.setAvbmAvbIds(baoxian.getAvbIds());
					vehicleBaoxianMingxiService.save(baoxianMingxi);
				}else{
					vehicleBaoxianMingxiService.updateById(baoxianMingxi);
				}
			}
		}
		return R.status(isUpdate);
	}

//	/**
//	 * 新增或修改 车辆保险信息主表
//	 */
//	@PostMapping("/submit")
//	@ApiOperation(value = "新增或修改", notes = "传入vehicleBaoxian")
//	public R submit(@Valid @RequestBody VehicleBaoxian vehicleBaoxian) {
//		return R.status(vehicleBaoxianService.saveOrUpdate(vehicleBaoxian));
//	}


	/**
	 * 删除 车辆保险信息主表
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		String[] idsss = ids.split(",");
		List<VehicleBaoxian> vehicleBaoxians = new ArrayList<>();
		for(String id:idsss) {
			VehicleBaoxian baoxian = new VehicleBaoxian();
			baoxian.setAvbIds(id);
			baoxian.setAvbDelete(1);
			baoxian.setAvbUpdateTime(LocalDateTime.now());
			vehicleBaoxians.add(baoxian);
		}
		return R.status(vehicleBaoxianService.updateBatchById(vehicleBaoxians));
	}


}
