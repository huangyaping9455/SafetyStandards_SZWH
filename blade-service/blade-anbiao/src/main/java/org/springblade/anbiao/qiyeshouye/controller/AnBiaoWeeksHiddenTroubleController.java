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
package org.springblade.anbiao.qiyeshouye.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.qiyeshouye.NoticeVO;
import org.springblade.anbiao.qiyeshouye.entity.AnBiaoWeeksHiddenTrouble;
import org.springblade.anbiao.qiyeshouye.page.AnBiaoWeeksHiddenTroublePage;
import org.springblade.anbiao.qiyeshouye.service.IQiYeShouYeService;
import org.springblade.anbiao.richenganpai.entity.Richenganpai;
import org.springblade.anbiao.richenganpai.service.IRichenganpaiService;
import org.springblade.common.tool.DateUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.feign.IUserClient;
import org.springblade.system.user.page.UserPage;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 控制器
 * @author hyp
 * @since 2022-04-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/weeksHiddenTrouble")
@Api(value = "企业端--周隐患排查", tags = "企业端--周隐患排查")
public class AnBiaoWeeksHiddenTroubleController {

	private IQiYeShouYeService qiYeShouYeService;

	private IFileUploadClient fileUploadClient;

	private IRichenganpaiService richenganpaiService;

	private IUserClient iUserClient;

	private IVehicleService vehicleService;

	@PostMapping(value = "/getNoticeAll")
	@ApiLog("企业端--周隐患排查列表")
	@ApiOperation(value = "企业端--周隐患排查列表", notes = "传入AnBiaoWeeksHiddenTroublePage",position = 1)
	public R<AnBiaoWeeksHiddenTroublePage<AnBiaoWeeksHiddenTrouble>> getNoticeAll(@RequestBody AnBiaoWeeksHiddenTroublePage anBiaoWeeksHiddenTroublePage) {
		AnBiaoWeeksHiddenTroublePage<AnBiaoWeeksHiddenTrouble> pages = qiYeShouYeService.selectWeeksHiddenTroubleGetAll(anBiaoWeeksHiddenTroublePage);
		//检查附件
		List<AnBiaoWeeksHiddenTrouble> list = pages.getRecords();
		for (int i = 0; i <list.size() ; i++) {
			if (StrUtil.isNotEmpty(list.get(i).getExamineImg()) && list.get(i).getExamineImg().contains("http") == false) {
				list.get(i).setExamineImg(fileUploadClient.getUrl(list.get(i).getExamineImg()));
			}
		}
		return R.data(pages);
	}

	@GetMapping("/detail")
	@ApiLog("企业端--周隐患排查详情")
	@ApiOperation(value = "企业端--周隐患排查详情", notes = "传入Id", position = 2)
	public R<NoticeVO> detail(Integer Id) {
		R r = new R();
		AnBiaoWeeksHiddenTrouble detail = qiYeShouYeService.selectWeeksHiddenTroubleById(Id,null,null,null,null,null);
		//检查附件
		if(StrUtil.isNotEmpty(detail.getExamineImg()) && detail.getExamineImg().contains("http") == false){
			detail.setExamineImg(fileUploadClient.getUrl(detail.getExamineImg()));
		}
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

	@PostMapping("/submit")
	@ApiLog("企业端--周隐患排查新增")
	@ApiOperation(value = "企业端--周隐患排查新增", notes = "传入AnBiaoWeeksHiddenTrouble", position = 3)
	public R submit(@RequestBody AnBiaoWeeksHiddenTrouble anBiaoWeeksHiddenTrouble) throws ParseException {
		R rs = new R();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isBlank(anBiaoWeeksHiddenTrouble.getCreateUser())){
			anBiaoWeeksHiddenTrouble.setCreateUser(anBiaoWeeksHiddenTrouble.getCreateUser());
		}
		if (StringUtils.isBlank(anBiaoWeeksHiddenTrouble.getCreateTime())){
			anBiaoWeeksHiddenTrouble.setCreateTime(DateUtil.now());
		}
		if (anBiaoWeeksHiddenTrouble.getIsdelete() != 0){
			anBiaoWeeksHiddenTrouble.setIsdelete(0);
		}
		String nowdate = format.format(new Date());
		String week_Days = DateUtils.getFirstAndLastOfWeek(nowdate);
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
		String bignDate = week_Days_idss[0];
		String endDate = week_Days_idss[1];
		AnBiaoWeeksHiddenTrouble detail = new AnBiaoWeeksHiddenTrouble();
		if(anBiaoWeeksHiddenTrouble.getType() == 1){
			detail = qiYeShouYeService.selectWeeksHiddenTroubleById(null,anBiaoWeeksHiddenTrouble.getType(),anBiaoWeeksHiddenTrouble.getDeptId(),null,bignDate,endDate);
		}
		if(anBiaoWeeksHiddenTrouble.getType() == 2){
			detail = qiYeShouYeService.selectWeeksHiddenTroubleById(null,anBiaoWeeksHiddenTrouble.getType(),anBiaoWeeksHiddenTrouble.getDeptId(),anBiaoWeeksHiddenTrouble.getVehId(),bignDate,endDate);
		}
		if(detail != null){
			if(anBiaoWeeksHiddenTrouble.getType() == 1){
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("该企业本周的周排查已完成");
			}
			if(anBiaoWeeksHiddenTrouble.getType() == 2){
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("该企业车辆本周的周排查已完成");
			}
		}else{
			boolean q = qiYeShouYeService.insertWeeksHiddenTroubleSelective(anBiaoWeeksHiddenTrouble);
			if(q == true){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("新增周隐患排查成功");
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("新增周隐患排查失败");
			}
		}
		return rs;
	}

	@PostMapping("/update")
	@ApiLog("企业端--周隐患排查修改")
	@ApiOperation(value = "企业端--周隐患排查修改", notes = "传入AnBiaoWeeksHiddenTrouble", position = 4)
	public R update(@RequestBody AnBiaoWeeksHiddenTrouble anBiaoWeeksHiddenTrouble) throws ParseException {
		R rs = new R();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String nowdate = format.format(new Date());
		String week_Days = DateUtils.getFirstAndLastOfWeek(nowdate);
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
		String bignDate = week_Days_idss[0];
		String endDate = week_Days_idss[1];
		AnBiaoWeeksHiddenTrouble detail = new AnBiaoWeeksHiddenTrouble();
		if(anBiaoWeeksHiddenTrouble.getType() == 1){
			detail = qiYeShouYeService.selectWeeksHiddenTroubleById(null,anBiaoWeeksHiddenTrouble.getType(),anBiaoWeeksHiddenTrouble.getDeptId(),null,bignDate,endDate);
		}
		if(anBiaoWeeksHiddenTrouble.getType() == 2){
			detail = qiYeShouYeService.selectWeeksHiddenTroubleById(null,anBiaoWeeksHiddenTrouble.getType(),anBiaoWeeksHiddenTrouble.getDeptId(),anBiaoWeeksHiddenTrouble.getVehId(),bignDate,endDate);
		}
		if(detail == null){
			if(anBiaoWeeksHiddenTrouble.getType() == 1){
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("该企业本周的周排查无数据");
			}
			if(anBiaoWeeksHiddenTrouble.getType() == 2){
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("该企业车辆本周的周排查无数据");
			}
		}else {
			anBiaoWeeksHiddenTrouble.setId(detail.getId());
			boolean q = qiYeShouYeService.updateWeeksHiddenTroubleSelective(anBiaoWeeksHiddenTrouble);
			if (q == true) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("编辑周隐患排查成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("编辑周隐患排查失败");
			}
		}
		return rs;
	}

	@GetMapping("/pushWeeksHiddenTroubleMessage")
	@ApiLog("企业端--周隐患排查代办提醒")
	@ApiOperation(value = "企业端--周隐患排查代办提醒", notes = "传入deptId", position = 5)
	public R pushWeeksHiddenTroubleMessage(Integer deptId) throws Exception {
		R rs = new R();
		AnBiaoWeeksHiddenTrouble anBiaoWeeksHiddenTrouble = new AnBiaoWeeksHiddenTrouble();
		anBiaoWeeksHiddenTrouble.setType(1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String nowdate = format.format(new Date());
		String week_Days = DateUtils.getFirstAndLastOfWeek(nowdate);
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
		String bignDate = week_Days_idss[0];
		String endDate = week_Days_idss[1];
//		List<Dept> deptList = qiYeShouYeService.QiYeList(deptId);
//		deptList.forEach(item-> {
//			AnBiaoWeeksHiddenTrouble detail = qiYeShouYeService.selectWeeksHiddenTroubleById(null,null,item.getId(),null,bignDate,endDate);
//			if(detail != null){
//				rs.setCode(200);
//				rs.setSuccess(true);
//				rs.setMsg("本周的周隐患排查已完成");
//			}else{
//				rs.setCode(500);
//				rs.setSuccess(false);
//				rs.setMsg("本周的周隐患排查未完成,尽快完善");
//			}
//		});
		AnBiaoWeeksHiddenTrouble detail = qiYeShouYeService.selectWeeksHiddenTroubleById(null,null,deptId,null,bignDate,endDate);
		if(detail != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("本周的周隐患排查已完成");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("本周的周隐患排查未完成,尽快完善");
		}
		return rs;
	}

	@GetMapping("/getVoluntrailyBacklog")
	@ApiLog("企业端--周隐患排查代办提醒生成")
	@ApiOperation(value = "企业端--周隐患排查代办提醒生成", notes = "传入user", position = 6)
	public R getVoluntrailyBacklog(BladeUser user) throws Exception {
		R rs = new R();
		AnBiaoWeeksHiddenTrouble anBiaoWeeksHiddenTrouble = new AnBiaoWeeksHiddenTrouble();
		anBiaoWeeksHiddenTrouble.setType(1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String nowdate = format.format(new Date());
		String week_Days = DateUtils.getFirstAndLastOfWeek(nowdate);
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
		String bignDate = week_Days_idss[0];
		String endDate = week_Days_idss[1];
		List<Dept> deptList = qiYeShouYeService.QiYeList(5884);
		deptList.forEach(item-> {
			AnBiaoWeeksHiddenTrouble detail = qiYeShouYeService.selectWeeksHiddenTroubleById(null,anBiaoWeeksHiddenTrouble.getType(),item.getId(),null,bignDate,endDate);
			if(detail != null){
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("该企业本周的周排查已完成");
			}else{
				Richenganpai richenganpai = new Richenganpai();
				//添加企业日程待办记录
				richenganpai.setRenwubiaoti(item.getDeptName()+"周隐患排查待办");
				richenganpai.setDeptId(item.getId());
				richenganpai.setRenwuleixing("周隐患排查");
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
				UserPage<User> userList = iUserClient.selectUserByDeptPage(item.getId(),0);
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
				richenganpai.setRenwukaishishijian(bignDate);
				richenganpai.setRenwujiezhishijian(endDate);
				richenganpai.setRenwuneirong(item.getDeptName()+"周隐患排查待办");
				richenganpai.setIsDeleted(0);
				richenganpai.setLeixing(1);
				boolean q = richenganpaiService.insertSelective(richenganpai);
				if(q == true){
					rs.setCode(200);
					rs.setSuccess(true);
					rs.setMsg("新增周隐患排查待办成功");
				}else{
					rs.setCode(500);
					rs.setSuccess(false);
					rs.setMsg("新增周隐患排查待办失败");
				}
			}
		});
		return rs;
	}

}
