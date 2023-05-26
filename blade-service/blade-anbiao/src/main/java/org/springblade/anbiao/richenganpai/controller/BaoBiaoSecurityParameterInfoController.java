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
package org.springblade.anbiao.richenganpai.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.richenganpai.entity.BaoBiaoSecurityParameterInfo;
import org.springblade.anbiao.richenganpai.entity.BaoBiaoSecurityParameterInfoRemark;
import org.springblade.anbiao.richenganpai.entity.BaoBiaoSecurityParameterInfoTime;
import org.springblade.anbiao.richenganpai.entity.Richenganpai;
import org.springblade.anbiao.richenganpai.page.RiChengAnPaiPage;
import org.springblade.anbiao.richenganpai.service.IBaoBiaoSecurityParameterInfoService;
import org.springblade.anbiao.richenganpai.service.IRichenganpaiService;
import org.springblade.common.tool.DateUtils;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.doc.safetyproductionfile.vo.SafetyProductionFileVO;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.feign.IUserClient;
import org.springblade.system.user.page.UserPage;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *  控制器
 * @author 呵呵哒
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/baoBiaoSecurityParameterInfo")
@Api(value = "安全台账配置", tags = "安全台账配置接口")
public class BaoBiaoSecurityParameterInfoController extends BladeController {

	private IBaoBiaoSecurityParameterInfoService iBaoBiaoSecurityParameterInfoService;

	private IRichenganpaiService richenganpaiService;

	private ISysClient iSysClient;

	private IUserClient iUserClient;

	/**
	 * 根据ID获取安全台账配置详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "根据ID获取安全台账配置详情", notes = "传入Id", position = 1)
	public R<BaoBiaoSecurityParameterInfo> detail(Integer Id) {
		R r = new R();
		List<BaoBiaoSecurityParameterInfo> detail = iBaoBiaoSecurityParameterInfoService.selectByIds(Id,null);
		if(detail != null){
			r.setMsg("获取成功");
			r.setData(detail);
			r.setCode(200);
		}else{
			r.setMsg("获取成功,暂无数据");
			r.setCode(200);
		}
		return r;
	}

	/**
	 * 新增安全台账配置
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增安全台账配置", notes = "传入baoBiaoSecurityParameterInfo", position = 2)
	public R submit(@RequestBody BaoBiaoSecurityParameterInfo baoBiaoSecurityParameterInfo, BladeUser bladeUser) {
		R rs = new R();
		String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		baoBiaoSecurityParameterInfo.setUpdatetime(updateTime);
		if(bladeUser != null){
			baoBiaoSecurityParameterInfo.setUpdateuser(bladeUser.getUserId());
		}
		List<BaoBiaoSecurityParameterInfo> detail = iBaoBiaoSecurityParameterInfoService.selectByIds(null,baoBiaoSecurityParameterInfo.getName());
		if(detail.size() > 0){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("该项已存在配置记录");
		}else{
			boolean ss = iBaoBiaoSecurityParameterInfoService.insertSelective(baoBiaoSecurityParameterInfo);
			if(ss){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("新增安全台账配置信息成功");
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("新增安全台账配置信息失败");
			}
		}
		return rs;
	}

	/**
	 * 修改安全台账配置信息
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改安全台账配置信息", notes = "传入baoBiaoSecurityParameterInfo", position = 3)
	public R update(@RequestBody BaoBiaoSecurityParameterInfo baoBiaoSecurityParameterInfo, BladeUser bladeUser) {
		R rs = new R();
		String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		baoBiaoSecurityParameterInfo.setUpdatetime(updateTime);
		if(bladeUser != null){
			baoBiaoSecurityParameterInfo.setUpdateuser(bladeUser.getUserId());
		}
		boolean ss = iBaoBiaoSecurityParameterInfoService.updateSelective(baoBiaoSecurityParameterInfo);
		if(ss){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("编辑安全台账配置信息成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("编辑安全台账配置信息失败");
		}
		return rs;
	}

	/**
	 * 根据ID删除安全台账配置信息
	 */
	@GetMapping("/remove")
	@ApiLog("根据ID删除安全台账配置信息")
	@ApiOperation(value = "根据ID删除安全台账配置信息", notes = "传入安全台账配置信息ID、用户ID", position = 4)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Id", value = "日程安排ID", required = true)
	})
	public R remove(@RequestParam Integer Id, BladeUser bladeUser) {
		R rs = new R();
		String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Integer userId = 0;
		if(bladeUser != null){
			userId = bladeUser.getUserId();
		}
		boolean ss = iBaoBiaoSecurityParameterInfoService.deleteBind(updateTime,userId,Id);
		if(ss){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("删除安全台账配置信息成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("删除安全台账配置信息失败");
		}
		return rs;
	}

	@PostMapping(value = "/getBaoBiaoSecurityParameterInfoList")
	@ApiLog("获取安全台账配置信息列表")
	@ApiOperation(value = "获取安全台账配置信息列表", notes = "传入riChengAnPaiPage", position = 3)
	public R<RiChengAnPaiPage<BaoBiaoSecurityParameterInfo>> getBaoBiaoSecurityParameterInfoList(@RequestBody RiChengAnPaiPage riChengAnPaiPage) {
		R r = new R();
		RiChengAnPaiPage<BaoBiaoSecurityParameterInfo> pages = iBaoBiaoSecurityParameterInfoService.getAll(riChengAnPaiPage);
		if(pages != null){
			r.setMsg("获取成功");
			r.setData(pages);
			r.setCode(200);
		}else{
			r.setMsg("获取成功,暂无数据");
			r.setCode(200);
		}
		return r;
	}

	@GetMapping("/getVoluntrailyJurisdiction")
	@ApiLog("保存-安全台账配置信息--企业权限")
	@ApiOperation(value = "保存-安全台账配置信息--企业权限", notes = "传入menuIds,deptIds,user", position = 4)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptIds", value = "企业id", required = true),
		@ApiImplicitParam(name = "menuIds", value = "选中id值", required = true)
	})
	public R getVoluntrailyJurisdiction(String menuIds,String deptIds,BladeUser user) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String msg="";
		R rs = new R();
		//根据deptIds字符串分别截取
		String[] deptIds_idsss = deptIds.split(",");
		//去除素组中重复的数组
		List<String> deptIds_listid = new ArrayList<String>();
		for (int i=0; i<deptIds_idsss.length; i++) {
			if(!deptIds_listid.contains(deptIds_idsss[i])) {
				deptIds_listid.add(deptIds_idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] deptIds_idss= deptIds_listid.toArray(new String[1]);
		if(deptIds_idss.length > 0){
			for(int i=0; i< deptIds_idss.length; i++){
				//直属上级id
				Dept dept= iSysClient.selectById(deptIds_idss[i]);
				//根据menuIds字符串分别截取
				String[] menuIds_idsss = menuIds.split(",");
				//去除素组中重复的数组
				List<String> menuIds_listid = new ArrayList<String>();
				for (int s=0; s<menuIds_idsss.length; s++) {
					if(!menuIds_listid.contains(menuIds_idsss[s])) {
						menuIds_listid.add(menuIds_idsss[s]);
					}
				}
				//返回一个包含所有对象的指定类型的数组
				String[] menuIds_idss= menuIds_listid.toArray(new String[1]);
				if(menuIds_idss.length > 0 ){
					List<Richenganpai> list = new ArrayList<>();
					for (int j = 0;j<menuIds_idss.length;j++){
						BaoBiaoSecurityParameterInfoRemark baoBiaoSecurityParameterInfoRemark = new BaoBiaoSecurityParameterInfoRemark();
						//根据安全台账配置项ID获取配置详情
						List<BaoBiaoSecurityParameterInfo> detail = iBaoBiaoSecurityParameterInfoService.selectByIds(Integer.parseInt(menuIds_idss[j]),null);
						//根据企业ID、安全台账名称获取安全台账详情（文档）
						List<SafetyProductionFileVO> safetyProductionFileVOS = iBaoBiaoSecurityParameterInfoService.selectSafetyProductionFileByIds(dept.getId(),detail.get(0).getName());
						if(safetyProductionFileVOS.size() < 1){
							msg += dept.getDeptName()+"该企业未生成该项安全台账,";
							rs.setMsg(msg);
							rs.setCode(500);
							rs.setSuccess(false);
							return rs;
						}
						//添加企业台账记录
						baoBiaoSecurityParameterInfoRemark.setDeptId(dept.getId().toString());
						baoBiaoSecurityParameterInfoRemark.setIsdeleted(0);
						baoBiaoSecurityParameterInfoRemark.setSecurityId(detail.get(0).getId());
						baoBiaoSecurityParameterInfoRemark.setSafetyfileId(safetyProductionFileVOS.get(0).getId());
						baoBiaoSecurityParameterInfoRemark.setStatus(0);

						//根据企业ID或者安全台账配置项ID获取企业安全台账配置相关详情
						List<BaoBiaoSecurityParameterInfo> detail_dept = iBaoBiaoSecurityParameterInfoService.selectBaoBiaoSecurityParameterInfoRemarkByIds(dept.getId(),safetyProductionFileVOS.get(0).getId());
						if(detail_dept.size()>0){
							rs.setCode(500);
							rs.setSuccess(false);
							msg += safetyProductionFileVOS.get(0).getName()+"该安全台账配置,"+dept.getDeptName()+"已生成,";
							rs.setMsg(msg);
						}else{
							boolean safe = iBaoBiaoSecurityParameterInfoService.insertRemarkSelective(baoBiaoSecurityParameterInfoRemark);
							if(safe == false){
								msg += "添加企业台账记录失败,";
								rs.setMsg(msg);
								rs.setCode(500);
								rs.setSuccess(false);
								return rs;
							}else{
								msg += "添加企业台账记录成功,";
								rs.setMsg(msg);
								rs.setCode(200);
								rs.setSuccess(true);
							}
						}
					}
				}else{
					rs.setMsg("安全台账配置信息项不能为空");
					rs.setCode(500);
					rs.setSuccess(false);
					return rs;
				}
			}
		}else{
			rs.setMsg("企业不能为空");
			rs.setCode(500);
			rs.setSuccess(false);
			return rs;
		}
		return rs;
	}

	@GetMapping("/getVoluntrailyBacklog")
	@ApiLog("安全台账配置信息--日程待办生成")
	@ApiOperation(value = "安全台账配置信息--日程待办生成", notes = "传入menuIds,deptIds,user", position = 14)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptIds", value = "企业ID", required = true),
		@ApiImplicitParam(name = "menuIds", value = "选中的安全台账ID", required = true)
	})
	public R getVoluntrailyBacklog(String menuIds,String deptIds,BladeUser user) throws Exception {
		SimpleDateFormat format_month = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String renwuleixing = "安全台账";
		R rs = new R();

		//根据deptIds字符串分别截取
		String[] deptIds_idsss = deptIds.split(",");
		//去除素组中重复的数组
		List<String> deptIds_listid = new ArrayList<String>();
		for (int i=0; i<deptIds_idsss.length; i++) {
			if(!deptIds_listid.contains(deptIds_idsss[i])) {
				deptIds_listid.add(deptIds_idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] deptIds_idss= deptIds_listid.toArray(new String[1]);
		if(deptIds_idss.length > 0){
			for(int i=0; i< deptIds_idss.length; i++){
				//直属上级id
				Dept dept= iSysClient.selectById(deptIds_idss[i]);
				if(dept == null){
					rs.setMsg("获取机构信息失败");
					rs.setCode(500);
					rs.setSuccess(false);
					return rs;
				}
				//根据menuIds字符串分别截取
				String[] menuIds_idsss = menuIds.split(",");
				//去除素组中重复的数组
				List<String> menuIds_listid = new ArrayList<String>();
				for (int s=0; s<menuIds_idsss.length; s++) {
					if(!menuIds_listid.contains(menuIds_idsss[s])) {
						menuIds_listid.add(menuIds_idsss[s]);
					}
				}
				//返回一个包含所有对象的指定类型的数组
				String[] menuIds_idss= menuIds_listid.toArray(new String[1]);
				if(menuIds_idss.length > 0 ){
					List<Richenganpai> list = new ArrayList<>();
					for (int j = 0;j<menuIds_idss.length;j++){
						Richenganpai richenganpai = new Richenganpai();
						BaoBiaoSecurityParameterInfoTime baoBiaoSecurityParameterInfoTime = new BaoBiaoSecurityParameterInfoTime();
						//根据企业ID或者安全台账配置项ID获取企业安全台账配置相关详情
						List<BaoBiaoSecurityParameterInfo> detail = iBaoBiaoSecurityParameterInfoService.selectBaoBiaoSecurityParameterInfoRemarkByIds(dept.getId(),Integer.parseInt(menuIds_idss[j]));
						//添加企业日程待办记录
						richenganpai.setRenwubiaoti(detail.get(0).getName()+"待办");
//						richenganpai.set(menuIds_idss[j]);
						richenganpai.setDeptId(dept.getId());
						richenganpai.setRenwuleixing(renwuleixing);
						if(user == null){
							richenganpai.setCaozuoren("admin");
							richenganpai.setCaozuorenid(1);
						}else{
							richenganpai.setCaozuoren(user.getUserName());
							richenganpai.setCaozuorenid(user.getUserId());
						}
						richenganpai.setCaozuoshijian(DateUtil.now());
						richenganpai.setAnpairen("admin");
						richenganpai.setAnpairenId(1);
						richenganpai.setIsZhongyao(1);
						UserPage<User> userList = iUserClient.selectUserByDeptPage(dept.getId(),0);
						List<User> users = userList.getRecords();
						String userNames = "";
						String userIds = "";
						if(userList != null){
							for(int u= 0;u<users.size();u++){
								userNames += users.get(u).getAccount()+",";
								userIds += users.get(u).getId()+",";
							}
						}
						richenganpai.setZhixingrens(userNames);
						richenganpai.setZhixingrenIds(userIds);
						richenganpai.setSafetyfileId(detail.get(0).getSafetyfileId());
						//周期类型 1：日；2：周；3：月；4：季；5：年
						//日
						if(detail.get(0).getRemindtype() == 1){
							int days = DateUtils.getDaysFullMonth(format_month.format(new Date()));
							int now_days = Integer.parseInt(format.format(new Date()).substring(format.format(new Date()).lastIndexOf("-")+1));
							for (int d = now_days;d<=days;d++){
								richenganpai.setRenwukaishishijian(format_month.format(new Date())+"-"+d);
								richenganpai.setRenwujiezhishijian(format_month.format(new Date())+"-"+d);
								//向安全台账周期时间记录表添加时间记录
								baoBiaoSecurityParameterInfoTime.setDeptId(dept.getId());
								baoBiaoSecurityParameterInfoTime.setSafetyfileId(detail.get(0).getSafetyfileId());
								baoBiaoSecurityParameterInfoTime.setType(1);
								baoBiaoSecurityParameterInfoTime.setTime(richenganpai.getRenwukaishishijian());
								iBaoBiaoSecurityParameterInfoService.insertBaoBiaoSecurityParameterInfoTimeSelective(baoBiaoSecurityParameterInfoTime);

								//根据企业ID、安全台账名称获取安全台账详情（文档）
								List<SafetyProductionFileVO> safetyProductionFileVOS = iBaoBiaoSecurityParameterInfoService.selectSafetyProductionFileByIds(dept.getId(),detail.get(0).getName());
								String oo = safetyProductionFileVOS.get(0).getPath().substring(safetyProductionFileVOS.get(0).getPath().lastIndexOf("\\",safetyProductionFileVOS.get(0).getPath().lastIndexOf("\\")-1)+1);
								//"+1"代表在定位时往后取一位,即去掉"/"
								//"-1"代表以"/"字符定位的位置向前取一位
								//从path.lastIndexOf("/")-1位置开始向前寻找倒数第二个"/"的位置
								oo = oo.substring(0, oo.indexOf("\\"));
								System.out.println(oo);
								richenganpai.setRenwuneirong(oo+"--"+detail.get(0).getName()+"文件上传");
								richenganpai.setIsDeleted(0);
								richenganpai.setLeixing(1);
								list.add(richenganpai);
								boolean ss = richenganpaiService.insertSelective(richenganpai);
								if(ss){
									BaoBiaoSecurityParameterInfoRemark baoBiaoSecurityParameterInfoRemark = new BaoBiaoSecurityParameterInfoRemark();
									baoBiaoSecurityParameterInfoRemark.setSecurityId(detail.get(0).getId());
									baoBiaoSecurityParameterInfoRemark.setStatus(1);
									baoBiaoSecurityParameterInfoRemark.setTime(richenganpai.getRenwukaishishijian());
									iBaoBiaoSecurityParameterInfoService.updateRemarkBind(baoBiaoSecurityParameterInfoRemark);
									rs.setMsg("添加成功");
									rs.setCode(200);
									rs.setSuccess(true);
								}else{
									rs.setMsg("添加失败");
									rs.setCode(500);
									rs.setSuccess(false);
									return rs;
								}
							}
						}
						//周
						if(detail.get(0).getRemindtype() == 2){
							String weekDays = null;
							//根据安全台账指定日期获取是星期几
							Date date = format.parse(detail.get(0).getRemindtime());
							String week = DateUtils.getWeekOfDate(date);
							if("星期日".equals(week)){
								//获取当月所有星期 week
								weekDays = DateUtils.getwendsor(month,1);
							}
							if("星期一".equals(week)){
								//获取当月所有星期 week
								weekDays = DateUtils.getwendsor(month,2);
							}
							if("星期二".equals(week)){
								//获取当月所有星期 week
								weekDays = DateUtils.getwendsor(month,3);
							}
							if("星期三".equals(week)){
								//获取当月所有星期 week
								weekDays = DateUtils.getwendsor(month,4);
							}
							if("星期四".equals(week)){
								//获取当月所有星期 week
								weekDays = DateUtils.getwendsor(month,5);
							}
							if("星期五".equals(week)){
								//获取当月所有星期 week
								weekDays = DateUtils.getwendsor(month,6);
							}
							if("星期六".equals(week)){
								//获取当月所有星期 week
								weekDays = DateUtils.getwendsor(month,7);
							}

							String[] weekDays_idsss = weekDays.split(",");
							//去除素组中重复的数组
							List<String> weekDays_listid = new ArrayList<String>();
							for (int q=0; q<weekDays_idsss.length; q++) {
								if(!weekDays_listid.contains(weekDays_idsss[q])) {
									weekDays_listid.add(weekDays_idsss[q]);
								}
							}
							//返回一个包含所有对象的指定类型的数组
							String[] weekDays_idss= weekDays_listid.toArray(new String[1]);
							if(weekDays_idss.length > 0 ) {
								for (int w = 0; w < weekDays_idss.length; w++) {
									String week_Days = DateUtils.getFirstAndLastOfWeek(weekDays_idss[w]);
									String[] week_Days_idsss = week_Days.split(",");
									//去除素组中重复的数组
									List<String> week_Days_listid = new ArrayList<String>();
									for (int q=0; q<week_Days_idsss.length; q++) {
										if(!week_Days_listid.contains(week_Days_idsss[q])) {
											week_Days_listid.add(week_Days_idsss[q]);
										}
									}
									//返回一个包含所有对象的指定类型的数组
									String[] week_Days_idss= week_Days_listid.toArray(new String[1]);
									String nowdate = format.format(new Date());
									Date nowDate_Date = format.parse(nowdate);
									Date endDate_Date = format.parse(weekDays_idss[w]);
									int compareTo = nowDate_Date.compareTo(endDate_Date);
									if( compareTo == -1 || compareTo == 0){
										richenganpai.setRenwukaishishijian(weekDays_idss[w]);
//									richenganpai.setRenwujiezhishijian(week_Days_idss[0]);
										richenganpai.setRenwujiezhishijian(week_Days_idss[1]);
										//向安全台账周期时间记录表添加时间记录
										baoBiaoSecurityParameterInfoTime.setDeptId(dept.getId());
										baoBiaoSecurityParameterInfoTime.setSafetyfileId(detail.get(0).getSafetyfileId());
										baoBiaoSecurityParameterInfoTime.setType(1);
										baoBiaoSecurityParameterInfoTime.setTime(richenganpai.getRenwukaishishijian());
										iBaoBiaoSecurityParameterInfoService.insertBaoBiaoSecurityParameterInfoTimeSelective(baoBiaoSecurityParameterInfoTime);

										//根据企业ID、安全台账名称获取安全台账详情（文档）
										List<SafetyProductionFileVO> safetyProductionFileVOS = iBaoBiaoSecurityParameterInfoService.selectSafetyProductionFileByIds(dept.getId(),detail.get(0).getName());
										String oo = safetyProductionFileVOS.get(0).getPath().substring(safetyProductionFileVOS.get(0).getPath().lastIndexOf("\\",safetyProductionFileVOS.get(0).getPath().lastIndexOf("\\")-1)+1);
										//"+1"代表在定位时往后取一位,即去掉"/"
										//"-1"代表以"/"字符定位的位置向前取一位
										//从path.lastIndexOf("/")-1位置开始向前寻找倒数第二个"/"的位置
										oo = oo.substring(0, oo.indexOf("\\"));
										System.out.println(oo);
										richenganpai.setRenwuneirong(oo+"--"+detail.get(0).getName()+"文件上传");
										richenganpai.setIsDeleted(0);
										richenganpai.setLeixing(1);
										list.add(richenganpai);
										boolean ss = richenganpaiService.insertSelective(richenganpai);
										if(ss){
											BaoBiaoSecurityParameterInfoRemark baoBiaoSecurityParameterInfoRemark = new BaoBiaoSecurityParameterInfoRemark();
											baoBiaoSecurityParameterInfoRemark.setSecurityId(detail.get(0).getId());
											baoBiaoSecurityParameterInfoRemark.setStatus(1);
											baoBiaoSecurityParameterInfoRemark.setTime(richenganpai.getRenwukaishishijian());
											iBaoBiaoSecurityParameterInfoService.updateRemarkBind(baoBiaoSecurityParameterInfoRemark);
											rs.setMsg("添加成功");
											rs.setCode(200);
											rs.setSuccess(true);
										}else{
											rs.setMsg("添加失败");
											rs.setCode(500);
											rs.setSuccess(false);
											return rs;
										}
									}
								}
							}
						}
						//月
						else if(detail.get(0).getRemindtype() == 3){
							String[] months = DateUtils.getYearFullMonth(String.valueOf(year));
							//去除素组中重复的数组
							List<String> months_listid = new ArrayList<String>();
							for (int q=month-1; q<months.length; q++) {
								if(!months_listid.contains(months[q])) {
									months_listid.add(months[q]);
								}
							}
							//返回一个包含所有对象的指定类型的数组
							String[] months_idss= months_listid.toArray(new String[1]);
							if(months_idss.length > 0 ) {
								for (int y = 0; y < months_idss.length; y++) {
									String nowdate = format_month.format(new Date());
									Date nowDate_Date = format_month.parse(nowdate);
									Date endDate_Date = format_month.parse(months_idss[y]);
									int compareTo = nowDate_Date.compareTo(endDate_Date);
									if( compareTo == -1 || compareTo == 0){
										richenganpai.setRenwukaishishijian(months_idss[y]+"-"+detail.get(0).getRemindtime().substring(detail.get(0).getRemindtime().lastIndexOf("-")+1));
										richenganpai.setRenwujiezhishijian(format.format(DateUtils.getLastDay(year,Integer.parseInt(months_idss[y].substring(months_idss[y].lastIndexOf("-")+1)))));
										//向安全台账周期时间记录表添加时间记录
										baoBiaoSecurityParameterInfoTime.setDeptId(dept.getId());
										baoBiaoSecurityParameterInfoTime.setSafetyfileId(detail.get(0).getSafetyfileId());
										baoBiaoSecurityParameterInfoTime.setType(1);
										baoBiaoSecurityParameterInfoTime.setTime(richenganpai.getRenwukaishishijian());
										iBaoBiaoSecurityParameterInfoService.insertBaoBiaoSecurityParameterInfoTimeSelective(baoBiaoSecurityParameterInfoTime);

										//根据企业ID、安全台账名称获取安全台账详情（文档）
										List<SafetyProductionFileVO> safetyProductionFileVOS = iBaoBiaoSecurityParameterInfoService.selectSafetyProductionFileByIds(dept.getId(),detail.get(0).getName());
										String oo = safetyProductionFileVOS.get(0).getPath().substring(safetyProductionFileVOS.get(0).getPath().lastIndexOf("\\",safetyProductionFileVOS.get(0).getPath().lastIndexOf("\\")-1)+1);
										//"+1"代表在定位时往后取一位,即去掉"/"
										//"-1"代表以"/"字符定位的位置向前取一位
										//从path.lastIndexOf("/")-1位置开始向前寻找倒数第二个"/"的位置
										oo = oo.substring(0, oo.indexOf("\\"));
										System.out.println(oo);
										richenganpai.setRenwuneirong(oo+"--"+detail.get(0).getName()+"文件上传");
										richenganpai.setIsDeleted(0);
										richenganpai.setLeixing(1);
										list.add(richenganpai);
										boolean ss = richenganpaiService.insertSelective(richenganpai);
										if(ss){
											BaoBiaoSecurityParameterInfoRemark baoBiaoSecurityParameterInfoRemark = new BaoBiaoSecurityParameterInfoRemark();
											baoBiaoSecurityParameterInfoRemark.setSecurityId(detail.get(0).getId());
											baoBiaoSecurityParameterInfoRemark.setStatus(1);
											baoBiaoSecurityParameterInfoRemark.setTime(months_idss[y]);
											iBaoBiaoSecurityParameterInfoService.updateRemarkBind(baoBiaoSecurityParameterInfoRemark);
											rs.setMsg("添加成功");
											rs.setCode(200);
											rs.setSuccess(true);
										}else{
											rs.setMsg("添加失败");
											rs.setCode(500);
											rs.setSuccess(false);
											return rs;
										}
									}
								}
							}
						}
						//季
						else if(detail.get(0).getRemindtype() == 4){
							int jidu_nums = DateUtils.getQuarter();
							for(int jd = jidu_nums-1;jd<5;jd++){
								String[] jidu = DateUtils.getCurrQuarter(jd);
								String begin = jidu[0].substring(0,7);
								begin = begin+"-"+detail.get(0).getRemindtime().substring(detail.get(0).getRemindtime().lastIndexOf("-")+1);
								richenganpai.setRenwukaishishijian(begin);
								richenganpai.setRenwujiezhishijian(jidu[1]);
								//向安全台账周期时间记录表添加时间记录
								baoBiaoSecurityParameterInfoTime.setDeptId(dept.getId());
								baoBiaoSecurityParameterInfoTime.setSafetyfileId(detail.get(0).getSafetyfileId());
								baoBiaoSecurityParameterInfoTime.setType(1);
								baoBiaoSecurityParameterInfoTime.setTime(richenganpai.getRenwukaishishijian());
								iBaoBiaoSecurityParameterInfoService.insertBaoBiaoSecurityParameterInfoTimeSelective(baoBiaoSecurityParameterInfoTime);

								//根据企业ID、安全台账名称获取安全台账详情（文档）
								List<SafetyProductionFileVO> safetyProductionFileVOS = iBaoBiaoSecurityParameterInfoService.selectSafetyProductionFileByIds(dept.getId(),detail.get(0).getName());
								String oo = safetyProductionFileVOS.get(0).getPath().substring(safetyProductionFileVOS.get(0).getPath().lastIndexOf("\\",safetyProductionFileVOS.get(0).getPath().lastIndexOf("\\")-1)+1);
								//"+1"代表在定位时往后取一位,即去掉"/"
								//"-1"代表以"/"字符定位的位置向前取一位
								//从path.lastIndexOf("/")-1位置开始向前寻找倒数第二个"/"的位置
								oo = oo.substring(0, oo.indexOf("\\"));
								System.out.println(oo);
								richenganpai.setRenwuneirong(oo+"--"+detail.get(0).getName()+"文件上传");
								richenganpai.setIsDeleted(0);
								richenganpai.setLeixing(1);
								list.add(richenganpai);
								boolean ss = richenganpaiService.insertSelective(richenganpai);
								if(ss){
									BaoBiaoSecurityParameterInfoRemark baoBiaoSecurityParameterInfoRemark = new BaoBiaoSecurityParameterInfoRemark();
									baoBiaoSecurityParameterInfoRemark.setSecurityId(detail.get(0).getId());
									baoBiaoSecurityParameterInfoRemark.setStatus(1);
									baoBiaoSecurityParameterInfoRemark.setTime(richenganpai.getRenwukaishishijian());
									iBaoBiaoSecurityParameterInfoService.updateRemarkBind(baoBiaoSecurityParameterInfoRemark);
									rs.setMsg("添加成功");
									rs.setCode(200);
									rs.setSuccess(true);
								}else{
									rs.setMsg("添加失败");
									rs.setCode(500);
									rs.setSuccess(false);
									return rs;
								}
							}
						}
						//年
						else if(detail.get(0).getRemindtype() == 5){
							richenganpai.setRenwukaishishijian(format_month.format(new Date())+"-"+detail.get(0).getRemindtime().substring(detail.get(0).getRemindtime().lastIndexOf("-")+1));
							richenganpai.setRenwujiezhishijian(format_month.format(new Date())+"-31");
							//向安全台账周期时间记录表添加时间记录
							baoBiaoSecurityParameterInfoTime.setDeptId(dept.getId());
							baoBiaoSecurityParameterInfoTime.setSafetyfileId(detail.get(0).getSafetyfileId());
							baoBiaoSecurityParameterInfoTime.setType(1);
							baoBiaoSecurityParameterInfoTime.setTime(richenganpai.getRenwukaishishijian());
							iBaoBiaoSecurityParameterInfoService.insertBaoBiaoSecurityParameterInfoTimeSelective(baoBiaoSecurityParameterInfoTime);

							//根据企业ID、安全台账名称获取安全台账详情（文档）
							List<SafetyProductionFileVO> safetyProductionFileVOS = iBaoBiaoSecurityParameterInfoService.selectSafetyProductionFileByIds(dept.getId(),detail.get(0).getName());
							String oo = safetyProductionFileVOS.get(0).getPath().substring(safetyProductionFileVOS.get(0).getPath().lastIndexOf("\\",safetyProductionFileVOS.get(0).getPath().lastIndexOf("\\")-1)+1);
							//"+1"代表在定位时往后取一位,即去掉"/"
							//"-1"代表以"/"字符定位的位置向前取一位
							//从path.lastIndexOf("/")-1位置开始向前寻找倒数第二个"/"的位置
							oo = oo.substring(0, oo.indexOf("\\"));
							System.out.println(oo);
							richenganpai.setRenwuneirong(oo+"--"+detail.get(0).getName()+"文件上传");
							richenganpai.setIsDeleted(0);
							richenganpai.setLeixing(1);
							list.add(richenganpai);
							boolean ss = richenganpaiService.insertSelective(richenganpai);
							if(ss){
								BaoBiaoSecurityParameterInfoRemark baoBiaoSecurityParameterInfoRemark = new BaoBiaoSecurityParameterInfoRemark();
								baoBiaoSecurityParameterInfoRemark.setSecurityId(detail.get(0).getId());
								baoBiaoSecurityParameterInfoRemark.setStatus(1);
								baoBiaoSecurityParameterInfoRemark.setTime(format_month.format(new Date()));
								iBaoBiaoSecurityParameterInfoService.updateRemarkBind(baoBiaoSecurityParameterInfoRemark);
								rs.setMsg("添加成功");
								rs.setCode(200);
								rs.setSuccess(true);
							}else{
								rs.setMsg("添加失败");
								rs.setCode(500);
								rs.setSuccess(false);
								return rs;
							}
						}
					}
				}else{
					rs.setMsg("安全台账配置信息项不能为空");
					rs.setCode(500);
					rs.setSuccess(false);
					return rs;
				}
			}
		}else{
			rs.setMsg("企业不能为空");
			rs.setCode(500);
			rs.setSuccess(false);
			return rs;
		}
		return rs;
	}

	@PostMapping(value = "/getAllByDeptSecurityParameterInfo")
	@ApiLog("获取安全台账配置信息列表----企业")
	@ApiOperation(value = "获取安全台账配置信息列表----企业", notes = "传入riChengAnPaiPage", position = 3)
	public R<RiChengAnPaiPage<BaoBiaoSecurityParameterInfo>> getAllByDeptSecurityParameterInfo(@RequestBody RiChengAnPaiPage riChengAnPaiPage) {
		R r = new R();
		RiChengAnPaiPage<BaoBiaoSecurityParameterInfo> pages = iBaoBiaoSecurityParameterInfoService.getAllByDept(riChengAnPaiPage);
		if(pages != null){
			r.setMsg("获取成功");
			r.setData(pages);
			r.setCode(200);
		}else{
			r.setMsg("获取成功,暂无数据");
			r.setCode(200);
		}
		return r;
	}

	/**
	 * 根据ID删除安全台账配置信息
	 */
	@GetMapping("/removeByDept")
	@ApiLog("根据ID删除安全台账配置信息----企业")
	@ApiOperation(value = "根据ID删除安全台账配置信息----企业", notes = "传入安全台账配置信息ID、bladeUser", position = 4)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Id", value = "日程安排ID", required = true)
	})
	public R removeByDept(@RequestParam Integer Id, BladeUser bladeUser) {
		R rs = new R();
		boolean ss = iBaoBiaoSecurityParameterInfoService.deleteRemarkBind(Id);
		if(ss){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("删除安全台账配置信息成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("删除安全台账配置信息失败");
		}
		return rs;
	}

	/**
	 * 根据企业ID、安全台账ID获取安全台账时间周期记录表
	 */
	@GetMapping("/selectBaoBiaoSecurityParameterInfoTime")
	@ApiLog("根据企业ID、安全台账ID获取安全台账时间周期记录表")
	@ApiOperation(value = "根据企业ID、安全台账ID获取安全台账时间周期记录表", notes = "传入deptId、securityId", position = 24)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "日程安排ID", required = true),
		@ApiImplicitParam(name = "securityId", value = "日程安排ID", required = true)
	})
	public R selectBaoBiaoSecurityParameterInfoTime(@RequestParam Integer deptId,Integer securityId) {
		R rs = new R();
		List<BaoBiaoSecurityParameterInfoTime> deail = iBaoBiaoSecurityParameterInfoService.selectBaoBiaoSecurityParameterInfoTime(deptId,securityId);
		if(deail != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(deail);
			rs.setMsg("获取信息成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取信息失败");
		}
		return rs;
	}

}
