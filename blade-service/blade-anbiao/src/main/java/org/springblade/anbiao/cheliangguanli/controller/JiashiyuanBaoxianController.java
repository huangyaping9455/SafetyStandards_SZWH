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

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.tools.zip.ZipOutputStream;
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxian;
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxianInfo;
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxianMingxi;
import org.springblade.anbiao.cheliangguanli.service.IJiashiyuanBaoxianMingxiService;
import org.springblade.anbiao.cheliangguanli.service.IJiashiyuanBaoxianService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.vo.JiashiyuanBaoxianVO;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanRuzhi;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanLedgerPage;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanRuzhiService;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanLedgerVO;
import org.springblade.anbiao.labor.VO.LaborledgerVO;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.labor.page.laborledgerPage;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.ApacheZipUtils;
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
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 驾驶员保险信息主表 控制器
 *
 * @author Blade
 * @since 2022-10-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/jiashiyuanbaoxian")
@Api(value = "驾驶员保险信息主表", tags = "驾驶员保险信息主表接口")
public class JiashiyuanBaoxianController extends BladeController {

	private IJiashiyuanBaoxianService jiashiyuanBaoxianService;
	private IJiashiyuanBaoxianMingxiService mingxiService;
	private IJiaShiYuanService jiaShiYuanService;
	private IVehicleService vehicleService;
	private ISysClient iSysClient;
	private IAnbiaoJiashiyuanRuzhiService ruzhiService;
	private FileServer fileServer;
	private IFileUploadClient fileUploadClient;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入jiashiyuanBaoxian")
	public R<JiashiyuanBaoxianInfo> detail(String ajbId) {
//		JiashiyuanBaoxian detail = jiashiyuanBaoxianService.getOne(Condition.getQueryWrapper(jiashiyuanBaoxian));
		JiashiyuanBaoxianInfo detail = jiashiyuanBaoxianService.queryDetail(ajbId);
		return R.data(detail);
	}

	@GetMapping("/queryByDriver")
	@ApiOperation(value = "根据被保险人ID查询保险详情", notes = "根据被保险人ID查询保险详情")
	public R<JiashiyuanBaoxianInfo> queryByDept(String driverId) {
		R r = new R();
		JiashiyuanBaoxian deptBaoxian = new JiashiyuanBaoxian();
		deptBaoxian.setAjbInsureIds(driverId);
		deptBaoxian.setAjbDelete("0");
		JiashiyuanBaoxian baoxian = jiashiyuanBaoxianService.getOne(Condition.getQueryWrapper(deptBaoxian));
		if(baoxian != null) {
			JiashiyuanBaoxianInfo detail = jiashiyuanBaoxianService.queryDetail(baoxian.getAjbIds());
			return R.data(detail);
		} else {
			r.setCode(500);
			r.setMsg("未查询到人员保险信息！");
			return r;
		}
	}

	@GetMapping("/queryByMax")
	@ApiOperation(value = "根据被保险人ID查询上次保险记录", notes = "根据被保险人ID查询上次保险记录")
	public R<JiashiyuanBaoxian> queryByMax(String driverId) {
		return R.data(jiashiyuanBaoxianService.queryByMax(driverId));
	}

	@PostMapping("/insuranceImport")
	@ApiLog("企业端-驾驶员保险导入")
	@ApiOperation(value = "企业端-驾驶员保险导入", notes = "file")
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

		List<JiashiyuanBaoxianMingxi> insurance = new ArrayList<>();
		JiashiyuanBaoxianInfo baoxianInfo = new JiashiyuanBaoxianInfo();
		JiashiyuanBaoxian baoxian = new JiashiyuanBaoxian();
		for(Map<String ,Object> mmap: readAll) {
			boolean isFail = false;
			Dept avbInsureDept = new Dept();
			Dept avbInsuredDept = new Dept();

			String avbInsureName = String.valueOf(mmap.get("投保单位")).trim();		//投保单位
			String avbInsuredName = String.valueOf(mmap.get("保险单位")).trim();
			String avbInsuranceCompany = String.valueOf(mmap.get("保险公司")).trim();
			String avbInsuredContacts = String.valueOf(mmap.get("被保险人")).trim();
			String avbPolicyNo = String.valueOf(mmap.get("保险单号")).trim();
			String avbInsureContacts = String.valueOf(mmap.get("投保人")).trim();
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
						baoxian.setAjbInsureIds(avbInsureDept.getId()+"");
						baoxian.setAjbInsureName(avbInsureName);
						baoxian.setAjbInsureDeptid(avbInsureDept.getId()+"");
						//投保人
						List<User> users = jiashiyuanBaoxianService.getDeptUser(avbInsureDept.getId().toString());
						if(users.size() > 0){
							users.forEach(item-> {
								if("主要负责人".equals(item.getPostName())){
									baoxian.setAjbInsureContacts(item.getName());
									baoxian.setAjbInsureContactNumber(item.getPhone());
									baoxian.setAjbInsureContactAddress(item.getAddress());
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

				//被保险人所属企业
				if(StringUtil.isNotBlank(avbInsuredName) && avbInsuredName != "null") {
					avbInsuredDept = iSysClient.getDeptByName(avbInsuredName);
					if(avbInsuredDept != null) {
						baoxian.setAjbDeptIds(new Long(avbInsuredDept.getId()));		//被保险人所属企业
					} else {
						errorStr += "没有查询到被保险单位！";
					}
				} else {
					isFail=true;
					errorStr += "被保车辆不能为空！";
				}
				//被保险人
				if(StringUtil.isNotBlank(avbInsureContacts)) {
					JiaShiYuan jsy = new JiaShiYuan();
					jsy.setJiashiyuanxingming(avbInsureContacts);
					jsy.setIsdelete(0);
					JiaShiYuan driver = jiaShiYuanService.getOne(Condition.getQueryWrapper(jsy));
					if(driver != null) {
						baoxian.setAjbInsuredIds(driver.getId());
						baoxian.setAjbInsuredName(driver.getJiashiyuanxingming());
						baoxian.setAjbInsuredContacts(driver.getJiashiyuanxingming());
						baoxian.setAjbInsuredContactNumber(driver.getShoujihaoma());
						baoxian.setAjbCertificateNumber(driver.getShenfenzhenghao());
						QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
						ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, driver.getId());
						ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
						AnbiaoJiashiyuanRuzhi ruzhiInfo = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
						if (ruzhiInfo != null) {
							baoxian.setAjbInsuredContactAddress(ruzhiInfo.getAjrAddress());
						}
					} else {
						errorStr += "没有查询到被保险人驾驶员！";
					}
				} else {
					isFail=true;
					errorStr += "被保驾驶员不能为空！";
				}
				if(StringUtil.isNotBlank(avbInsuranceCompany)) {
					baoxian.setAjbInsuranceCompany(avbInsuranceCompany);
				}
				baoxian.setAjbPolicyNo(avbPolicyNo);
				baoxian.setAjbInsuranceDays(Integer.parseInt(daysRemaining));
				baoxian.setAjbDelete("0");
				if(StringUtil.isNotBlank(avbInsurancePeriodStart)) {
					baoxian.setAjbInsurancePeriodStart(dateFormat2.parse(avbInsurancePeriodStart).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				}
				if(StringUtil.isNotBlank(avbInsurancePeriodEnd)) {
					baoxian.setAjbInsurancePeriodEnd(dateFormat2.parse(avbInsurancePeriodEnd).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				}
				if(StringUtil.isNotBlank(avbInsurancePeriodStart) && StringUtil.isNotBlank(avbInsurancePeriodEnd)) {
					Date startTime = dateFormat2.parse(avbInsurancePeriodStart);
					Date endTime = dateFormat2.parse(avbInsurancePeriodEnd);
					long s = endTime.getTime() - startTime.getTime();
					TimeUnit time = TimeUnit.DAYS;
					long days = time.convert(s,TimeUnit.MICROSECONDS);
					baoxian.setAjbInsuranceDays((int)days);
				}
				baoxianInfo.setBaoxian(baoxian);

//			}
			JiashiyuanBaoxianMingxi mingxi = new JiashiyuanBaoxianMingxi();
			mingxi.setAjbmRisk(avbmRisk);
			mingxi.setAjbmName(avbmName);
			mingxi.setAjbmBasicPremium(new BigDecimal(avbmBasicPremium));
			mingxi.setAjbmCompanyAmount(new BigDecimal(avbmInsuranceAmount));
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
			baoxian.setAjbCreateTime(LocalDateTime.now());
			baoxian.setAjbCreateByIds(user.getUserId().toString());
			baoxian.setAjbCreateByName(user.getUserName());
			boolean isSave = jiashiyuanBaoxianService.save(baoxian);
			for (JiashiyuanBaoxianMingxi baoxianMingxi: insurance) {
				baoxianMingxi.setAjbmAvbIds(baoxian.getAjbIds());
				if(StringUtil.isEmpty(baoxianMingxi.getAjbmName())){
					baoxianMingxi.setAjbmName("人员");
				}
				mingxiService.save(baoxianMingxi);
			}
			return R.status(isSave);
		}
	}


//	/**
//	 * 分页 驾驶员保险信息主表
//	 */
//	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "传入jiashiyuanBaoxian")
//	public R<IPage<JiashiyuanBaoxian>> list(JiashiyuanBaoxian jiashiyuanBaoxian, Query query) {
//		IPage<JiashiyuanBaoxian> pages = jiashiyuanBaoxianService.page(Condition.getPage(query), Condition.getQueryWrapper(jiashiyuanBaoxian));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 驾驶员保险信息主表
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入jiashiyuanBaoxian")
	public R<IPage<JiashiyuanBaoxianVO>> page(JiashiyuanBaoxianVO jiashiyuanBaoxian, Query query,String ajbInsuredIds) {
		IPage<JiashiyuanBaoxianVO> pages = jiashiyuanBaoxianService.selectJiashiyuanBaoxianPage(Condition.getPage(query), jiashiyuanBaoxian,ajbInsuredIds);
		return R.data(pages);
	}

	/**
	 * 新增 驾驶员保险信息主表
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入jiashiyuanBaoxian")
	public R save(@Valid @RequestBody JiashiyuanBaoxianInfo jiashiyuanBaoxian, BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setCode(401);
			r.setMsg("未授权，请重新登录！");
			return r;
		}
		JiashiyuanBaoxian baoxian = jiashiyuanBaoxian.getBaoxian();
		baoxian.setAjbApprove("0");
		baoxian.setAjbDelete("0");
//		if(StringUtil.isNotBlank(baoxian.getAjbInsuredIds())) {		//被保险人
//			JiaShiYuan jiaShiYuan = jiaShiYuanService.selectByIds(baoxian.getAjbInsuredIds());
//			if(jiaShiYuan != null) {
//				baoxian.setAjbInsuredName(jiaShiYuan.getJiashiyuanxingming());
//				baoxian.setAjbInsuredContactNumber(jiaShiYuan.getShoujihaoma());
//				baoxian.setAjbInsuredContactAddress(jiaShiYuan.getJiatingzhuzhi());
//				baoxian.setAjbInsuredContacts(jiaShiYuan.getJiashiyuanxingming());
//				baoxian.setAjbDeptIds(new Long(jiaShiYuan.getDeptId()));
//				baoxian.setAjbCertificateNumber(jiaShiYuan.getShenfenzhenghao());
//				baoxian.setAjbDeptIds(new Long(jiaShiYuan.getDeptId()));
//			}
//		}
//		if(StringUtil.isNotBlank(baoxian.getAjbInsureIds())) {
//			Dept dept = iSysClient.getDept(Integer.parseInt(baoxian.getAjbInsureIds()));
//			if(dept != null) {
//				baoxian.setAjbInsureName(dept.getDeptName());
//				baoxian.setAjbInsureContactNumber("");
//				baoxian.setAjbInsureContactAddress("");
//				baoxian.setAjbInsureContacts(dept.getDeptName());
//			}
//		}
		baoxian.setAjbCreateByIds(user.getUserId()+"");
		baoxian.setAjbCreateByName(user.getUserName());
		baoxian.setAjbCreateTime(LocalDateTime.now());
		boolean isSave = jiashiyuanBaoxianService.save(baoxian);
		if(jiashiyuanBaoxian.getBaoxianMingxis() != null && jiashiyuanBaoxian.getBaoxianMingxis().size() > 0) {
			for (JiashiyuanBaoxianMingxi baoxianMingxi: jiashiyuanBaoxian.getBaoxianMingxis()) {
				baoxianMingxi.setAjbmAvbIds(jiashiyuanBaoxian.getBaoxian().getAjbIds());
				mingxiService.save(baoxianMingxi);
			}
		}
		return R.status(isSave);
	}

	/**
	 * 修改 驾驶员保险信息主表
	 */
	@PostMapping("/update")
	public R update(@Valid @RequestBody JiashiyuanBaoxianInfo jiashiyuanBaoxian,BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setCode(401);
			r.setMsg("未授权，请重新登录！");
			return r;
		}
		JiashiyuanBaoxian baoxian = jiashiyuanBaoxian.getBaoxian();
		baoxian.setAjbApprove("0");
		baoxian.setAjbDelete("0");
//		if(StringUtil.isNotBlank(baoxian.getAjbInsuredIds())) {		//被保险人
//			JiaShiYuan jiaShiYuan = jiaShiYuanService.selectByIds(baoxian.getAjbInsuredIds());
//			if(jiaShiYuan != null) {
//				baoxian.setAjbInsuredName(jiaShiYuan.getJiashiyuanxingming());
//				baoxian.setAjbInsuredContactNumber(jiaShiYuan.getShoujihaoma());
//				baoxian.setAjbInsuredContactAddress(jiaShiYuan.getJiatingzhuzhi());
//				baoxian.setAjbInsuredContacts(jiaShiYuan.getJiashiyuanxingming());
//				baoxian.setAjbDeptIds(new Long(jiaShiYuan.getDeptId()));
//				baoxian.setAjbCertificateNumber(jiaShiYuan.getShenfenzhenghao());
//				baoxian.setAjbDeptIds(new Long(jiaShiYuan.getDeptId()));
//			}
//		}
//		if(StringUtil.isNotBlank(baoxian.getAjbInsureIds())) {
//			Dept dept = iSysClient.getDept(Integer.parseInt(baoxian.getAjbInsureIds()));
//			if(dept != null) {
//				baoxian.setAjbInsureName(dept.getDeptName());
//				baoxian.setAjbInsureContactNumber("");
//				baoxian.setAjbInsureContactAddress("");
//				baoxian.setAjbInsureContacts(dept.getDeptName());
//			}
//		}
		baoxian.setAjbUpdateByIds(user.getUserId()+"");
		baoxian.setAjbUpdateByName(user.getUserName());
		baoxian.setAjbUpdateTime(LocalDateTime.now());
		boolean isUpdate = jiashiyuanBaoxianService.updateById(baoxian);
		if(jiashiyuanBaoxian.getBaoxianMingxis() != null && jiashiyuanBaoxian.getBaoxianMingxis().size() > 0) {
			for (JiashiyuanBaoxianMingxi baoxianMingxi: jiashiyuanBaoxian.getBaoxianMingxis()) {
				QueryWrapper<JiashiyuanBaoxianMingxi> baoxianMingxiQueryWrapper = new QueryWrapper<JiashiyuanBaoxianMingxi>();
				baoxianMingxiQueryWrapper.lambda().eq(JiashiyuanBaoxianMingxi::getAjbmAvbIds,baoxian.getAjbIds());
				baoxianMingxiQueryWrapper.lambda().eq(JiashiyuanBaoxianMingxi::getAjbmRisk,baoxianMingxi.getAjbmRisk());
				baoxianMingxiQueryWrapper.lambda().eq(JiashiyuanBaoxianMingxi::getAjbmName,baoxianMingxi.getAjbmName());
				JiashiyuanBaoxianMingxi deail = mingxiService.getBaseMapper().selectOne(baoxianMingxiQueryWrapper);
				if(deail == null || deail.getAjbmIds() == null){
					baoxianMingxi.setAjbmAvbIds(baoxian.getAjbIds());
					mingxiService.save(baoxianMingxi);
				}else{
					mingxiService.updateById(baoxianMingxi);
				}
			}
		}
		return R.status(isUpdate);
	}

	/**
	 * 新增或修改 驾驶员保险信息主表
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入jiashiyuanBaoxian")
	public R submit(@Valid @RequestBody JiashiyuanBaoxian jiashiyuanBaoxian) {
		return R.status(jiashiyuanBaoxianService.saveOrUpdate(jiashiyuanBaoxian));
	}


	/**
	 * 删除 驾驶员保险信息主表
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		String[] idsss = ids.split(",");
		List<JiashiyuanBaoxian> deptBaoxians = new ArrayList<>();
		for(String id:idsss) {
			JiashiyuanBaoxian baoxian = new JiashiyuanBaoxian();
			baoxian.setAjbIds(id);
			baoxian.setAjbDelete("1");
			baoxian.setAjbUpdateTime(LocalDateTime.now());
			deptBaoxians.add(baoxian);
		}
		return R.status(jiashiyuanBaoxianService.updateBatchById(deptBaoxians));
	}

	@GetMapping("/getMoveJSYList")
	@ApiOperation(value = "根据企业ID获取已离职的驾驶员信息", notes = "传入deptId")
	public R getMoveJSYList(String deptId,BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setCode(401);
			r.setMsg("未授权，请重新登录！");
			return r;
		}
		QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getDeptId,deptId);
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getStatus,1);
		List<JiaShiYuan> deail = jiaShiYuanService.getBaseMapper().selectList(jiaShiYuanQueryWrapper);
		if (deail.size() < 1){
			r.setCode(200);
			r.setMsg("暂无数据");
			r.setSuccess(false);
			return r;
		}else{
			r.setCode(200);
			r.setMsg("获取成功");
			r.setSuccess(true);
			r.setData(deail);
			return r;
		}
	}

	@GetMapping("/move")
	@ApiOperation(value = "保险异动", notes = "传入保险数据ID，异动的驾驶员ID")
	public R move(String id,String jsyid,BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setCode(401);
			r.setMsg("未授权，请重新登录！");
			return r;
		}
		QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId,jsyid);
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getStatus,1);
		JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
		if (deail == null){
			r.setCode(500);
			r.setMsg("该驾驶员未离职，不能进行保险异动！");
			r.setSuccess(false);
			return r;
		}
		JiashiyuanBaoxian baoxian = new JiashiyuanBaoxian();
		baoxian.setAjbIds(id);
		baoxian.setAjbApprove("0");
		baoxian.setAjbDelete("0");
		baoxian.setAjbUpdateByIds(user.getUserId()+"");
		baoxian.setAjbUpdateByName(user.getUserName());
		baoxian.setAjbUpdateTime(LocalDateTime.now());
		baoxian.setAjbCertificateNumber(deail.getShenfenzhenghao());
		baoxian.setAjbInsuredName(deail.getJiashiyuanxingming());
		baoxian.setAjbInsuredContacts(deail.getJiashiyuanxingming());
		baoxian.setAjbInsuredContactAddress(deail.getJiatingzhuzhi());
		boolean i = jiashiyuanBaoxianService.updateById(baoxian);
		if(i){
			r.setCode(200);
			r.setMsg("异动成功");
			r.setSuccess(true);
			return r;
		}else {
			r.setCode(500);
			r.setMsg("异动失败");
			r.setSuccess(false);
			return r;
		}
	}

	@PostMapping("/getledgerPage")
	@ApiLog("分页 列表-保险台账")
	@ApiOperation(value = "分页 列表-保险台账", notes = "传入JiaShiYuanLedgerPage", position = 2)
	public R getLedgerPage(@RequestBody JiaShiYuanLedgerPage jiaShiYuanLedgerPage, BladeUser us) {
		R rs = new R();
		JiaShiYuanLedgerPage list = jiashiyuanBaoxianService.selectLedgerList(jiaShiYuanLedgerPage);
		return R.data(list);
	}

	@GetMapping("/goExport_Excel")
	@ApiLog("保险信息-导出")
	@ApiOperation(value = "保险信息-导出", notes = "传入JiaShiYuanLedgerPage", position = 22)
	public R goExport_HiddenDanger_Excel(HttpServletRequest request, HttpServletResponse response, String deptId , String date, BladeUser user) throws IOException {
		int a=1;
		R rs = new R();
		List<String> urlList = new ArrayList<>();
		JiaShiYuanLedgerPage jiaShiYuanLedgerPage = new JiaShiYuanLedgerPage();
		jiaShiYuanLedgerPage.setDeptId(deptId);
		jiaShiYuanLedgerPage.setDate(date);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath =fileServer.getPathPrefix()+"muban\\"+"Baoxian.xlsx";
		String [] nyr= DateUtil.today().split("-");
		String[] idsss = jiaShiYuanLedgerPage.getDeptId().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss= listid.toArray(new String[1]);
		for(int j = 0;j< idss.length;j++){
			jiaShiYuanLedgerPage.setDeptName("");
			jiaShiYuanLedgerPage.setSize(0);
			jiaShiYuanLedgerPage.setCurrent(0);
			jiaShiYuanLedgerPage.setDeptId(idss[j]);
			jiashiyuanBaoxianService.selectLedgerList(jiaShiYuanLedgerPage);
			List<JiaShiYuanLedgerVO> jiaShiYuanLedgerVOS = jiaShiYuanLedgerPage.getRecords();
			//Excel中的结果集ListData
//			List<LaborledgerVO> ListData = new ArrayList<>();
			if(jiaShiYuanLedgerVOS.size()==0){

			}else if(jiaShiYuanLedgerVOS.size()>3000){
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			}else{
				for(int i = 0; i < jiaShiYuanLedgerVOS.size() ; i++) {
					List<LaborledgerVO> ListData = new ArrayList<>();
					Map<String, Object> map = new HashMap<>();
					String templateFile = templatePath;
					// 渲染文本
					JiaShiYuanLedgerVO t = jiaShiYuanLedgerVOS.get(i);
					map.put("deptName", t.getDeptName());
					if(StrUtil.isNotEmpty(t.getJiashiyuanrenshu())){
						map.put("jiashiyuanrenshu", t.getJiashiyuanrenshu());
					}else {
						map.put("jiashiyuanrenshu", "0");
					}
					if(StrUtil.isNotEmpty(t.getCheliangshuliang())){
						map.put("cheliangshuliang", t.getCheliangshuliang());
					}else {
						map.put("cheliangshuliang", "0");
					}
					if(StrUtil.isNotEmpty(t.getYiwaixianzongbaoxianjine())){
						map.put("yiwaixianzongbaoxianjine", t.getYiwaixianzongbaoxianjine());
					}else {
						map.put("yiwaixianzongbaoxianjine", "0");
					}
					if(StrUtil.isNotEmpty(t.getYiwaixianzongbaofeijine())){
						map.put("yiwaixianzongbaofeijine", t.getYiwaixianzongbaofeijine());
					}else {
						map.put("yiwaixianzongbaofeijine", "0");
					}
					if(StrUtil.isNotEmpty(t.getQitazongbaoxianjine())){
						map.put("qitazongbaoxianjine", t.getQitazongbaoxianjine());
					}else {
						map.put("qitazongbaoxianjine", "0");
					}
					if(StrUtil.isNotEmpty(t.getQitazongbaofeijine())){
						map.put("qitazongbaofeijine", t.getQitazongbaofeijine());
					}else {
						map.put("qitazongbaofeijine", "0");
					}
					if(StrUtil.isNotEmpty(t.getChexianzongbaoxianjine())){
						map.put("chexianzongbaoxianjine", t.getChexianzongbaoxianjine());
					}else {
						map.put("chexianzongbaoxianjine", "0");
					}
					if(StrUtil.isNotEmpty(t.getChexianzongbaofeijine())){
						map.put("chexianzongbaofeijine", t.getChexianzongbaofeijine());
					}else {
						map.put("chexianzongbaofeijine", "0");
					}
					if(StrUtil.isNotEmpty(t.getJiaoqiangxianzongjine())){
						map.put("jiaoqiangxianzongjine", t.getJiaoqiangxianzongjine());
					}else {
						map.put("jiaoqiangxianzongjine", "0");
					}
					if(StrUtil.isNotEmpty(t.getJiaoqiangxianzongbaofeijine())){
						map.put("jiaoqiangxianzongbaofeijine", t.getJiaoqiangxianzongbaofeijine());
					}else {
						map.put("jiaoqiangxianzongbaofeijine", "0");
					}
					if(StrUtil.isNotEmpty(t.getHuoguizongbaoxianjine())){
						map.put("huoguizongbaoxianjine", t.getHuoguizongbaoxianjine());
					}else {
						map.put("huoguizongbaoxianjine", "0");
					}
					if(StrUtil.isNotEmpty(t.getHuoguizongbaofeijine())){
						map.put("huoguizongbaofeijine", t.getHuoguizongbaofeijine());
					}else {
						map.put("huoguizongbaofeijine", "0");
					}
					if(StrUtil.isNotEmpty(t.getAnzezongbaoxianjine())){
						map.put("anzezongbaoxianjine", t.getAnzezongbaoxianjine());
					}else {
						map.put("anzezongbaoxianjine", "0");
					}
					if(StrUtil.isNotEmpty(t.getAnzezongbaofeijine())){
						map.put("anzezongbaofeijine", t.getAnzezongbaofeijine());
					}else {
						map.put("anzezongbaofeijine", "0");
					}
					if(StrUtil.isNotEmpty(t.getQitazongbaoxianjineqiye())){
						map.put("qitazongbaoxianjineqiye", t.getQitazongbaoxianjineqiye());
					}else {
						map.put("qitazongbaoxianjineqiye", "0");
					}
					if(StrUtil.isNotEmpty(t.getQitazongbaofeijineqiye())){
						map.put("qitazongbaofeijineqiye", t.getQitazongbaofeijineqiye());
					}else {
						map.put("qitazongbaofeijineqiye", "0");
					}
					if(StrUtil.isNotEmpty(t.getTotalTmount())){
						map.put("totalTmount", t.getTotalTmount());
					}else {
						map.put("totalTmount", "0");
					}

					// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
					// {} 代表普通变量 {.} 代表是list的变量
					// 这里模板 删除了list以后的数据，也就是统计的这一行
					String templateFileName = templateFile;
					//alarmServer.getTemplateUrl()+
//					String fileName = "D:\\ExcelTest\\"+t.getDeptName()+"-保险台账"+a+".xlsx";
//					String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2]+"/"+t.getDeptName()+"-保险台账"+".xlsx";

					String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2];
					File newFile = new File(fileName);
					//判断目标文件所在目录是否存在
					if(!newFile.exists()){
						//如果目标文件所在的目录不存在，则创建父目录
						newFile.mkdirs();
					}
					fileName = fileName+"/"+t.getDeptName()+"-保险台账.xlsx";


					ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
					WriteSheet writeSheet = EasyExcel.writerSheet().build();
					// 写入list之前的数据
					excelWriter.fill(map, writeSheet);
					// 直接写入数据
					excelWriter.fill(ListData, writeSheet);
					excelWriter.finish();
					urlList.add(fileName);
					a++;
				}
			}
		}
		String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+"保险台账.zip";
		ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream(fileName));
		ApacheZipUtils.doCompress1(urlList, bizOut);
		//不要忘记调用
		bizOut.close();

		rs.setMsg("下载成功");
		rs.setCode(200);
		rs.setData(fileName);
		rs.setSuccess(true);
		return rs;
	}

}
