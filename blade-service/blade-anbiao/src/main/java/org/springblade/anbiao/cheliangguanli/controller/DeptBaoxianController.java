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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.service.IDeptBaoxianMingxiService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.feign.IOrganizationsClient;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.common.tool.FuncUtil;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.cheliangguanli.vo.DeptBaoxianVO;
import org.springblade.anbiao.cheliangguanli.service.IDeptBaoxianService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springframework.web.multipart.MultipartFile;

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
 * 企业保险信息主表 控制器
 *
 * @author Blade
 * @since 2022-10-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/deptbaoxian")
@Api(value = "企业保险信息主表", tags = "企业保险信息主表接口")
public class DeptBaoxianController extends BladeController {

	private IDeptBaoxianService deptBaoxianService;
	private IDeptBaoxianMingxiService deptBaoxianMingxiService;
	private IOrganizationsClient orrganizationsClient;
	private IJiaShiYuanService jiaShiYuanService;
	private IVehicleService vehicleService;
	private ISysClient iSysClient;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入deptBaoxian")
	public R<DeptBaoxianInfo> detail(String avbId) {
		DeptBaoxianInfo detail = deptBaoxianService.queryDetail(avbId);
		return R.data(detail);
	}

	@GetMapping("/queryByDept")
	@ApiOperation(value = "根据被保险企业ID查询保险详情", notes = "根据被保险企业ID查询保险详情")
	public R<DeptBaoxianInfo> queryByDept(String deptId) {
		R r = new R();
		DeptBaoxian deptBaoxian = new DeptBaoxian();
		deptBaoxian.setAvbInsureIds(deptId);
		deptBaoxian.setAvbDelete("0");
		DeptBaoxian baoxian =deptBaoxianService.getOne(Condition.getQueryWrapper(deptBaoxian));
		if(baoxian != null) {
			DeptBaoxianInfo detail = deptBaoxianService.queryDetail(baoxian.getAvbIds());
			return R.data(detail);
		} else {
			r.setCode(500);
			r.setMsg("未查询到企业保险信息！");
			return r;
		}
	}

	@GetMapping("/queryByMax")
	@ApiOperation(value = "根据被保险企业ID查询上次保险记录", notes = "根据被保险企业ID查询上次保险记录")
	public R<DeptBaoxian> queryByMax(String avbInsuredIds) {
		return R.data(deptBaoxianService.queryByMax(avbInsuredIds));
	}


	@PostMapping("/insuranceImport")
	@ApiLog("企业端-企业保险导入")
	@ApiOperation(value = "企业端-企业保险导入", notes = "file")
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
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
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

		List<DeptBaoxianMingxi> insurance = new ArrayList<>();
		DeptBaoxianInfo baoxianInfo = new DeptBaoxianInfo();
		DeptBaoxian baoxian = new DeptBaoxian();
		for(Map<String ,Object> mmap: readAll) {
			boolean isFail = false;
			Dept avbInsureDept = new Dept();
			Dept avbInsuredDept = new Dept();

			String avbInsureName = String.valueOf(mmap.get("投保单位")).trim();		//投保单位
			String avbInsuredName = String.valueOf(mmap.get("被保险单位")).trim();
			String avbInsuredContacts = String.valueOf(mmap.get("被保险人")).trim();
			String avbPolicyNo = String.valueOf(mmap.get("保单号")).trim();
			String avbInsureContacts = String.valueOf(mmap.get("投保联系人")).trim();
			String avbInsureContactNumber = String.valueOf(mmap.get("投保人联系电话")).trim();
			String avbmRisk = String.valueOf(mmap.get("保险类别")).trim();
			String avbmName = String.valueOf(mmap.get("保险名称")).trim();
			String avbInsurancePeriodStart = String.valueOf(mmap.get("投保开始时间")).trim();
			String avbInsurancePeriodEnd = String.valueOf(mmap.get("投保结束时间")).trim();
			String daysRemaining = String.valueOf(mmap.get("剩余天数")).trim();
			String avbmInsuranceAmount = String.valueOf(mmap.get("保额")).trim();
			String avbmBasicPremium = String.valueOf(mmap.get("保费")).trim();

			if(baoxian == null) {
				if(StringUtil.isNotBlank(avbInsureName)) {
					avbInsureDept = iSysClient.getDeptByName(avbInsureName);
					baoxian.setAvbInsureIds(avbInsureDept.getId()+"");
					baoxian.setAvbInsureName(avbInsureName);
				} else {
					isFail=true;
					errorStr += "投保企业不能为空！";
				}

				//被保险人所属企业
				if(StringUtil.isNotBlank(avbInsuredName)) {
					avbInsuredDept = iSysClient.getDeptByName(avbInsuredName);
					if(avbInsuredDept != null) {
						baoxian.setAvbDeptIds(avbInsuredDept.getId()+"");		//被保险人所属企业
					} else {
						errorStr += "没有查询到被保险单位！";
					}
				} else {
					isFail=true;
					errorStr += "被保车辆不能为空！";
				}
				//被保险人
				if(StringUtil.isNotBlank(avbInsuredContacts)) {
					Dept d = iSysClient.getDeptByName(avbInsuredContacts);
					if(d != null) {
						baoxian.setAvbInsuredIds(d.getId()+"");
						baoxian.setAvbInsuredName(d.getDeptName());
//						baoxian.setAvbInsuredContacts(driver.getJiashiyuanxingming());
//						baoxian.setAvbInsuredContactNumber(driver.getShoujihaoma());
//						baoxian.setAvbInsuredContactAddress(driver.getJiatingzhuzhi());
					} else {
						errorStr += "没有查询到被保险人驾驶员！";
					}
				} else {
					isFail=true;
					errorStr += "被保驾驶员不能为空！";
				}
				baoxian.setAvbInsureContacts(avbInsureContacts);
				baoxian.setAvbInsureContactNumber(avbInsureContactNumber);
				baoxian.setAvbDelete("0");
				if(StringUtil.isNotBlank(avbInsurancePeriodStart)) {
					baoxian.setAvbInsurancePeriodStart(dateFormat2.parse(avbInsurancePeriodStart).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				}
				if(StringUtil.isNotBlank(avbInsurancePeriodEnd)) {
					baoxian.setAvbInsurancePeriodEnd(dateFormat2.parse(avbInsurancePeriodEnd).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				}
				if(StringUtil.isNotBlank(avbInsurancePeriodStart) && StringUtil.isNotBlank(avbInsurancePeriodEnd)) {
					Date startTime = dateFormat2.parse(avbInsurancePeriodStart);
					Date endTime = dateFormat2.parse(avbInsurancePeriodEnd);
					long s = endTime.getTime() - startTime.getTime();
					TimeUnit time = TimeUnit.DAYS;
					long days = time.convert(s,TimeUnit.MICROSECONDS);
					baoxian.setAvbInsuranceDays((int)days);
				}
				baoxian.setAvbCreateByIds(user.getUserId()+"");
				baoxian.setAvbCreateByName(user.getUserName());
				baoxian.setAvbCreateTime(LocalDateTime.now());
				baoxianInfo.setBaoxian(baoxian);

			}
			DeptBaoxianMingxi mingxi = new DeptBaoxianMingxi();
			mingxi.setAvbmRisk(avbmRisk);
			mingxi.setAvbmName(avbmName);
			mingxi.setAvbmBasicPremium(new BigDecimal(avbmBasicPremium));
			mingxi.setAvbmBasicPremium(new BigDecimal(avbmInsuranceAmount));
			insurance.add(mingxi);
			if(isFail) {
				failNum++;
			} else {
				successNum++;
			}
		}
		baoxianInfo.setMingxiList(insurance);
		if(failNum > 0) {
			r.setCode(500);
			r.setMsg(errorStr);
			r.setSuccess(false);
			r.setData(null);
			return r;
		} else {
			boolean isSave = deptBaoxianService.save(baoxian);
			for (DeptBaoxianMingxi baoxianMingxi: insurance) {
				baoxianMingxi.setAvbmAvbIds(baoxian.getAvbIds());
				deptBaoxianMingxiService.save(baoxianMingxi);
			}
			return R.status(isSave);
		}
	}

//	/**
//	 * 分页 企业保险信息主表
//	 */
//	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "传入deptBaoxian")
//	public R<IPage<DeptBaoxian>> list(DeptBaoxian deptBaoxian, Query query) {
//		IPage<DeptBaoxian> pages = deptBaoxianService.page(Condition.getPage(query), Condition.getQueryWrapper(deptBaoxian));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 企业保险信息主表
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入deptBaoxian")
	public R<IPage<DeptBaoxianVO>> page(DeptBaoxianVO deptBaoxian, Query query) {
		IPage<DeptBaoxianVO> pages = deptBaoxianService.selectDeptBaoxianPage(Condition.getPage(query), deptBaoxian);
		return R.data(pages);
	}

	/**
	 * 新增 企业保险信息主表
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入deptBaoxian")
	public R save(@Valid @RequestBody DeptBaoxianInfo deptBaoxian, BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setCode(401);
			r.setMsg("未授权，请重新登录！");
			return r;
		}
		DeptBaoxian baoxian = deptBaoxian.getBaoxian();
		baoxian.setAvbApprove("0");
		baoxian.setAvbDelete("0");
//		if(StringUtil.isNotBlank(baoxian.getAvbInsuredIds())) {		//被保险人
////			JiaShiYuan jiaShiYuan = jiaShiYuanService.selectByIds(baoxian.getAvbInsuredIds());
////			if(jiaShiYuan != null) {
////				baoxian.setAvbInsuredName(jiaShiYuan.getJiashiyuanxingming());
////				baoxian.setAvbInsuredContactNumber(jiaShiYuan.getShoujihaoma());
////				baoxian.setAvbInsuredContactAddress(jiaShiYuan.getJiatingzhuzhi());
////				baoxian.setAvbInsuredContacts(jiaShiYuan.getJiashiyuanxingming());
////				baoxian.setAvbDeptIds(jiaShiYuan.getDeptId()+"");
////			}
//			Dept dept = iSysClient.selectById(baoxian.getAvbInsuredIds());
//			if(dept != null) {
//				baoxian.setAvbInsuredName(dept.getDeptName());
//				baoxian.setAvbInsuredContactNumber("");
//				baoxian.setAvbInsuredContactAddress("");
//				baoxian.setAvbInsuredContacts(dept.getDeptName());
//				baoxian.setAvbCertificateNumber(dept.getTenantCode());
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
		boolean isSave = deptBaoxianService.save(deptBaoxian.getBaoxian());
		if(deptBaoxian.getMingxiList() != null && deptBaoxian.getMingxiList().size() > 0) {
			for(DeptBaoxianMingxi mingxi:deptBaoxian.getMingxiList()) {
				mingxi.setAvbmAvbIds(deptBaoxian.getBaoxian().getAvbIds());
				deptBaoxianMingxiService.save(mingxi);
			}
		}
		return R.status(isSave);
	}

	/**
	 * 修改 企业保险信息主表
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入deptBaoxian")
	public R update(@Valid @RequestBody DeptBaoxianInfo deptBaoxian,BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setCode(401);
			r.setMsg("未授权，请重新登录！");
			return r;
		}
		DeptBaoxian baoxian = deptBaoxian.getBaoxian();
		baoxian.setAvbApprove("0");
		baoxian.setAvbDelete("0");
//		if(StringUtil.isNotBlank(baoxian.getAvbInsuredIds())) {		//被保险人
////			JiaShiYuan jiaShiYuan = jiaShiYuanService.selectByIds(baoxian.getAvbInsuredIds());
////			if(jiaShiYuan != null) {
////				baoxian.setAvbInsuredName(jiaShiYuan.getJiashiyuanxingming());
////				baoxian.setAvbInsuredContactNumber(jiaShiYuan.getShoujihaoma());
////				baoxian.setAvbInsuredContactAddress(jiaShiYuan.getJiatingzhuzhi());
////				baoxian.setAvbInsuredContacts(jiaShiYuan.getJiashiyuanxingming());
////				baoxian.setAvbDeptIds(jiaShiYuan.getDeptId()+"");
////			}
//			Dept dept = iSysClient.selectById(baoxian.getAvbInsuredIds());
//			if(dept != null) {
//				baoxian.setAvbInsuredName(dept.getDeptName());
//				baoxian.setAvbInsuredContactNumber("");
//				baoxian.setAvbInsuredContactAddress("");
//				baoxian.setAvbInsuredContacts(dept.getDeptName());
//				baoxian.setAvbCertificateNumber(dept.getTenantCode());
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
		boolean isUpdate = deptBaoxianService.updateById(deptBaoxian.getBaoxian());
		if(deptBaoxian.getMingxiList() != null && deptBaoxian.getMingxiList().size() > 0) {
			for(DeptBaoxianMingxi mingxi:deptBaoxian.getMingxiList()) {
				deptBaoxianMingxiService.updateById(mingxi);
			}
		}
		return R.status(isUpdate);
	}

	/**
	 * 新增或修改 企业保险信息主表
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入deptBaoxian")
	public R submit(@Valid @RequestBody DeptBaoxian deptBaoxian) {
		return R.status(deptBaoxianService.saveOrUpdate(deptBaoxian));
	}


	/**
	 * 删除 企业保险信息主表
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		String[] idsss = ids.split(",");
		List<DeptBaoxian> deptBaoxians = new ArrayList<>();
		for(String id:idsss) {
			DeptBaoxian baoxian = new DeptBaoxian();
			baoxian.setAvbIds(id);
			baoxian.setAvbDelete("1");
			baoxian.setAvbUpdateTime(LocalDateTime.now());
			deptBaoxians.add(baoxian);
		}
		return R.status(deptBaoxianService.updateBatchById(deptBaoxians));
	}

}
