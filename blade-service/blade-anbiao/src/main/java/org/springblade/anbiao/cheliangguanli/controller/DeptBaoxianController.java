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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.anbiao.cheliangguanli.entity.DeptBaoxianInfo;
import org.springblade.anbiao.cheliangguanli.entity.DeptBaoxianMingxi;
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxian;
import org.springblade.anbiao.cheliangguanli.service.IDeptBaoxianMingxiService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.feign.IOrganizationsClient;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.common.tool.FuncUtil;
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
import org.springblade.anbiao.cheliangguanli.entity.DeptBaoxian;
import org.springblade.anbiao.cheliangguanli.vo.DeptBaoxianVO;
import org.springblade.anbiao.cheliangguanli.service.IDeptBaoxianService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
		if(StringUtil.isNotBlank(baoxian.getAvbInsuredIds())) {		//被保险人
//			JiaShiYuan jiaShiYuan = jiaShiYuanService.selectByIds(baoxian.getAvbInsuredIds());
//			if(jiaShiYuan != null) {
//				baoxian.setAvbInsuredName(jiaShiYuan.getJiashiyuanxingming());
//				baoxian.setAvbInsuredContactNumber(jiaShiYuan.getShoujihaoma());
//				baoxian.setAvbInsuredContactAddress(jiaShiYuan.getJiatingzhuzhi());
//				baoxian.setAvbInsuredContacts(jiaShiYuan.getJiashiyuanxingming());
//				baoxian.setAvbDeptIds(jiaShiYuan.getDeptId()+"");
//			}
			Dept dept = iSysClient.selectById(baoxian.getAvbInsuredIds());
			if(dept != null) {
				baoxian.setAvbInsuredName(dept.getDeptName());
				baoxian.setAvbInsuredContactNumber("");
				baoxian.setAvbInsuredContactAddress("");
				baoxian.setAvbInsuredContacts(dept.getDeptName());
				baoxian.setAvbCertificateNumber(dept.getTenantCode());
			}
		}
		if(StringUtil.isNotBlank(baoxian.getAvbInsureIds())) {
			Dept dept = iSysClient.getDept(Integer.parseInt(baoxian.getAvbInsureIds()));
			if(dept != null) {
				baoxian.setAvbInsureName(dept.getDeptName());
				baoxian.setAvbInsureContactNumber("");
				baoxian.setAvbInsureContactAddress("");
				baoxian.setAvbInsureContacts(dept.getDeptName());
			}
		}
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
		if(StringUtil.isNotBlank(baoxian.getAvbInsuredIds())) {		//被保险人
//			JiaShiYuan jiaShiYuan = jiaShiYuanService.selectByIds(baoxian.getAvbInsuredIds());
//			if(jiaShiYuan != null) {
//				baoxian.setAvbInsuredName(jiaShiYuan.getJiashiyuanxingming());
//				baoxian.setAvbInsuredContactNumber(jiaShiYuan.getShoujihaoma());
//				baoxian.setAvbInsuredContactAddress(jiaShiYuan.getJiatingzhuzhi());
//				baoxian.setAvbInsuredContacts(jiaShiYuan.getJiashiyuanxingming());
//				baoxian.setAvbDeptIds(jiaShiYuan.getDeptId()+"");
//			}
			Dept dept = iSysClient.selectById(baoxian.getAvbInsuredIds());
			if(dept != null) {
				baoxian.setAvbInsuredName(dept.getDeptName());
				baoxian.setAvbInsuredContactNumber("");
				baoxian.setAvbInsuredContactAddress("");
				baoxian.setAvbInsuredContacts(dept.getDeptName());
				baoxian.setAvbCertificateNumber(dept.getTenantCode());
			}
		}
		if(StringUtil.isNotBlank(baoxian.getAvbInsureIds())) {
			Dept dept = iSysClient.getDept(Integer.parseInt(baoxian.getAvbInsureIds()));
			if(dept != null) {
				baoxian.setAvbInsureName(dept.getDeptName());
				baoxian.setAvbInsureContactNumber("");
				baoxian.setAvbInsureContactAddress("");
				baoxian.setAvbInsureContacts(dept.getDeptName());
			}
		}
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
