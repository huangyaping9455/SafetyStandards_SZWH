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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.qiyeshouye.NoticeVO;
import org.springblade.anbiao.qiyeshouye.entity.AnBiaoDeptWechatInfo;
import org.springblade.anbiao.qiyeshouye.entity.AnBiaoDeptWechatRemark;
import org.springblade.anbiao.qiyeshouye.page.AnBiaoDeptWechatInfoPage;
import org.springblade.anbiao.qiyeshouye.service.IQiYeShouYeService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制器
 * @author hyp
 * @since 2022-04-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/deptWechat")
@Api(value = "运维端-微信推送配置", tags = "运维端-微信推送配置")
public class AnBiaoDeptWechatInfoController {

	private IQiYeShouYeService qiYeShouYeService;

	@PostMapping(value = "/getNoticeAll")
	@ApiLog("运维端-微信推送配置列表")
	@ApiOperation(value = "运维端-微信推送配置列表", notes = "传入AnBiaoDeptWechatInfoPage",position = 1)
	public R<AnBiaoDeptWechatInfoPage<AnBiaoDeptWechatInfo>> getNoticeAll(@RequestBody AnBiaoDeptWechatInfoPage anBiaoDeptWechatInfoPage) {
		AnBiaoDeptWechatInfoPage<AnBiaoDeptWechatInfo> pages = qiYeShouYeService.selectDeptWechatGetAll(anBiaoDeptWechatInfoPage);
		return R.data(pages);
	}

	@GetMapping("/detail")
	@ApiLog("运维端-微信推送配置详情")
	@ApiOperation(value = "运维端-微信推送配置详情", notes = "传入Id", position = 2)
	public R<NoticeVO> detail(Integer Id) {
		R r = new R();
		AnBiaoDeptWechatInfo detail = qiYeShouYeService.selectDeptWechatInfoById(Id,null,null);
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

	@GetMapping("/wechatRemarkDetail")
	@ApiLog("运维端-微信推送配置详情(报警类型、提醒语)")
	@ApiOperation(value = "运维端-微信推送配置详情(报警类型、提醒语)", notes = "传入Id", position = 2)
	public R<NoticeVO> wechatRemarkDetail(Integer Id) {
		R r = new R();
		List<AnBiaoDeptWechatRemark> detail = qiYeShouYeService.selectDeptWechatRemarkById(Id,null,null);
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
	@ApiLog("运维端-微信推送配置新增")
	@ApiOperation(value = "运维端-微信推送配置新增", notes = "传入AnBiaoDeptWechatInfo", position = 3)
	public R submit(@RequestBody AnBiaoDeptWechatInfo anBiaoDeptWechatInfo, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		if (StringUtils.isBlank(anBiaoDeptWechatInfo.getCreatetime())){
			anBiaoDeptWechatInfo.setCreatetime(DateUtil.now());
		}
		if (anBiaoDeptWechatInfo.getIsdelete() != 0){
			anBiaoDeptWechatInfo.setIsdelete(0);
		}

		anBiaoDeptWechatInfo.setUpdateuser(user.getUserId());
		AnBiaoDeptWechatInfo anBiaoDeptWechatInfo1 = qiYeShouYeService.selectDeptWechatInfoById(null,anBiaoDeptWechatInfo.getWeChatCode(), anBiaoDeptWechatInfo.getDeptId());
		if(anBiaoDeptWechatInfo1 == null){
			boolean x = false;
			//获取企业数据
			String[] deptId = anBiaoDeptWechatInfo.getDeptId().split(",");
			//去除素组中重复的数组
			List<String> deptId_listid = new ArrayList<String>();
			for (int i=0; i<deptId.length; i++) {
				if(!deptId_listid.contains(deptId[i])) {
					deptId_listid.add(deptId[i]);
				}
			}
			//返回一个包含所有对象的指定类型的数组
			String[] deptId_idss= deptId_listid.toArray(new String[1]);
			System.out.println(deptId_idss.length);
			System.out.println(deptId_idss[0]);
			if(deptId_idss.length > 0 && deptId_idss != null && !"".equals(deptId_idss[0])) {
				for (int ii = 0; ii < deptId_idss.length; ii++) {
					anBiaoDeptWechatInfo.setDeptId(deptId_idss[ii]);
					int id = qiYeShouYeService.selectMaxId()+1;
					anBiaoDeptWechatInfo.setId(id);
					x = qiYeShouYeService.insertDeptWechatSelective(anBiaoDeptWechatInfo);
					if(x == true){
						List<AnBiaoDeptWechatRemark> deail = qiYeShouYeService.selectDeptWechatRemarkById(anBiaoDeptWechatInfo.getId(),null,null);
						if(deail != null){
							qiYeShouYeService.deleteDeptWechatRemark(anBiaoDeptWechatInfo.getUpdatetime(),user.getUserId(),anBiaoDeptWechatInfo.getId());
						}
						//循环报警配置list
						System.out.println(anBiaoDeptWechatInfo.getAnBiaoDeptWechatRemarkList());
						if(anBiaoDeptWechatInfo.getAnBiaoDeptWechatRemarkList() != null){
							List<AnBiaoDeptWechatRemark> anBiaoDeptWechatRemarkList = anBiaoDeptWechatInfo.getAnBiaoDeptWechatRemarkList();
							System.out.println(anBiaoDeptWechatRemarkList);
							AnBiaoDeptWechatRemark anBiaoDeptWechatRemark = new AnBiaoDeptWechatRemark();
							anBiaoDeptWechatRemarkList.forEach(item->{
								anBiaoDeptWechatRemark.setAlarmlevel(item.getAlarmlevel());
								anBiaoDeptWechatRemark.setAlarmtype(item.getAlarmtype());
								anBiaoDeptWechatRemark.setReminder(item.getReminder());
								anBiaoDeptWechatRemark.setCreatetime(anBiaoDeptWechatInfo.getCreatetime());
								anBiaoDeptWechatRemark.setIsdelete(0);
								anBiaoDeptWechatRemark.setWechatid(anBiaoDeptWechatInfo.getId());
								boolean q = qiYeShouYeService.insertDeptWechatRemarkSelective(anBiaoDeptWechatRemark);
								if(q == true){
									rs.setCode(200);
									rs.setSuccess(true);
									rs.setMsg("新增微信推送配置成功");
								}else{
									rs.setCode(500);
									rs.setSuccess(false);
									rs.setMsg("新增微信推送配置失败");
								}
							});
						}

//						//向详情表添加数据记录
//						AnBiaoDeptWechatRemark anBiaoDeptWechatRemark = new AnBiaoDeptWechatRemark();
//						//获取报警类型数据
//						String[] alarmtype = anBiaoDeptWechatInfo.getAlarmtype().split(",");
//						//去除素组中重复的数组
//						List<String> alarmtype_listid = new ArrayList<String>();
//						for (int i=0; i<alarmtype.length; i++) {
//							if(!alarmtype_listid.contains(alarmtype[i])) {
//								alarmtype_listid.add(alarmtype[i]);
//							}
//						}
//						//返回一个包含所有对象的指定类型的数组
//						String[] alarmtype_idss= alarmtype_listid.toArray(new String[1]);
//						System.out.println(alarmtype_idss.length);
//						System.out.println(alarmtype_idss[0]);
//						if(alarmtype_idss.length > 0 && alarmtype_idss != null && !"".equals(alarmtype_idss[0])) {
//							for (int i = 0; i < alarmtype_idss.length; i++) {
//								anBiaoDeptWechatRemark.setAlarmtype(alarmtype_idss[i]);
//								//获取提醒语数据
//								String[] reminder = anBiaoDeptWechatInfo.getReminder().split(",");
//								//去除素组中重复的数组
//								List<String> reminder_listid = new ArrayList<String>();
//								for (int j=0; j<reminder.length; j++) {
//									if(!reminder_listid.contains(reminder[i])) {
//										reminder_listid.add(reminder[i]);
//									}
//								}
//								//返回一个包含所有对象的指定类型的数组
//								String[] reminder_idss= reminder_listid.toArray(new String[1]);
//								for (int j = 0; j < reminder_idss.length; j++) {
//									anBiaoDeptWechatRemark.setReminder(reminder_idss[j]);
//								}
//								anBiaoDeptWechatRemark.setCreatetime(anBiaoDeptWechatInfo.getCreatetime());
//								anBiaoDeptWechatRemark.setIsdelete(0);
//								anBiaoDeptWechatRemark.setWechatid(anBiaoDeptWechatInfo.getId());
//								boolean q = qiYeShouYeService.insertDeptWechatRemarkSelective(anBiaoDeptWechatRemark);
//								if(x == true && q == true){
//									rs.setCode(200);
//									rs.setSuccess(true);
//									rs.setMsg("新增微信推送配置成功");
//								}else{
//									rs.setCode(500);
//									rs.setSuccess(false);
//									rs.setMsg("新增微信推送配置失败");
//								}
//							}
//						}
						rs.setCode(200);
						rs.setSuccess(true);
						rs.setMsg("新增微信推送配置成功");
					}else{
						rs.setCode(500);
						rs.setSuccess(false);
						rs.setMsg("新增微信推送配置失败");
					}
				}
			}
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("该企业微信code推送配置已存在");
		}
		return rs;
	}

	@PostMapping("/update")
	@ApiLog("运维端-微信推送配置修改")
	@ApiOperation(value = "运维端-微信推送配置修改", notes = "传入AnBiaoDeptWechatInfo", position = 4)
	public R update(@RequestBody AnBiaoDeptWechatInfo anBiaoDeptWechatInfo, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		if (StringUtils.isBlank(anBiaoDeptWechatInfo.getUpdatetime())){
			anBiaoDeptWechatInfo.setUpdatetime(DateUtil.now());
		}
		anBiaoDeptWechatInfo.setUpdateuser(user.getUserId());
		AnBiaoDeptWechatInfo anBiaoDeptWechatInfo1 = qiYeShouYeService.selectDeptWechatInfoById(anBiaoDeptWechatInfo.getId(),null, anBiaoDeptWechatInfo.getDeptId());
		if(anBiaoDeptWechatInfo1 != null){
			boolean x = qiYeShouYeService.updateDeptWechatSelective(anBiaoDeptWechatInfo);
			List<AnBiaoDeptWechatRemark> deail = qiYeShouYeService.selectDeptWechatRemarkById(anBiaoDeptWechatInfo.getId(),null,null);
			if(deail != null){
				qiYeShouYeService.deleteDeptWechatRemark(anBiaoDeptWechatInfo.getUpdatetime(),user.getUserId(),anBiaoDeptWechatInfo.getId());
			}
			//向详情表添加数据记录
			//循环报警配置list
			System.out.println(anBiaoDeptWechatInfo.getAnBiaoDeptWechatRemarkList());
			if(anBiaoDeptWechatInfo.getAnBiaoDeptWechatRemarkList() != null){
				List<AnBiaoDeptWechatRemark> anBiaoDeptWechatRemarkList = anBiaoDeptWechatInfo.getAnBiaoDeptWechatRemarkList();
				System.out.println(anBiaoDeptWechatRemarkList);
				AnBiaoDeptWechatRemark anBiaoDeptWechatRemark = new AnBiaoDeptWechatRemark();
				anBiaoDeptWechatRemarkList.forEach(item->{
					anBiaoDeptWechatRemark.setAlarmlevel(item.getAlarmlevel());
					anBiaoDeptWechatRemark.setAlarmtype(item.getAlarmtype());
					anBiaoDeptWechatRemark.setReminder(item.getReminder());
					anBiaoDeptWechatRemark.setCreatetime(anBiaoDeptWechatInfo.getCreatetime());
					anBiaoDeptWechatRemark.setIsdelete(0);
					anBiaoDeptWechatRemark.setWechatid(anBiaoDeptWechatInfo.getId());
					boolean q = qiYeShouYeService.insertDeptWechatRemarkSelective(anBiaoDeptWechatRemark);
					if(q == true){
						rs.setCode(200);
						rs.setSuccess(true);
						rs.setMsg("编辑微信推送配置成功");
					}else{
						rs.setCode(500);
						rs.setSuccess(false);
						rs.setMsg("编辑微信推送配置失败");
					}
				});
			}

//			AnBiaoDeptWechatRemark anBiaoDeptWechatRemark = new AnBiaoDeptWechatRemark();
//			anBiaoDeptWechatRemark.setWechatid(anBiaoDeptWechatInfo.getId());
//			//获取报警类型数据
//			String[] alarmtype = anBiaoDeptWechatInfo.getAlarmtype().split(",");
//			//去除素组中重复的数组
//			List<String> alarmtype_listid = new ArrayList<String>();
//			for (int i=0; i<alarmtype.length; i++) {
//				if(!alarmtype_listid.contains(alarmtype[i])) {
//					alarmtype_listid.add(alarmtype[i]);
//				}
//			}
//			//返回一个包含所有对象的指定类型的数组
//			String[] alarmtype_idss= alarmtype_listid.toArray(new String[1]);
//			if(alarmtype_idss.length > 0 && alarmtype_idss != null && !"".equals(alarmtype_idss[0])) {
//				for (int i = 0; i < alarmtype_idss.length; i++) {
//					anBiaoDeptWechatRemark.setAlarmtype(alarmtype_idss[i]);
//					anBiaoDeptWechatRemark.setUpdatetime(anBiaoDeptWechatInfo.getUpdatetime());
//					anBiaoDeptWechatRemark.setUpdateuser(anBiaoDeptWechatInfo.getUpdateuser());
//					boolean q = false;
//					//获取提醒语数据
//					String[] reminder = anBiaoDeptWechatInfo.getReminder().split(",");
//					//去除素组中重复的数组
//					List<String> reminder_listid = new ArrayList<String>();
//					for (int j=0; j<reminder.length; j++) {
//						if(!reminder_listid.contains(reminder[i])) {
//							reminder_listid.add(reminder[i]);
//						}
//					}
//					//返回一个包含所有对象的指定类型的数组
//					String[] reminder_idss= reminder_listid.toArray(new String[1]);
//					for (int j = 0; j < reminder_idss.length; j++) {
//						anBiaoDeptWechatRemark.setReminder(reminder_idss[j]);
//						anBiaoDeptWechatRemark.setCreatetime(anBiaoDeptWechatInfo.getUpdatetime());
//						anBiaoDeptWechatRemark.setIsdelete(0);
//						anBiaoDeptWechatRemark.setUpdatetime("");
//						anBiaoDeptWechatRemark.setUpdateuser(0);
//						q = qiYeShouYeService.insertDeptWechatRemarkSelective(anBiaoDeptWechatRemark);
//					}
//					if(x == true && q == true){
//						rs.setCode(200);
//						rs.setSuccess(true);
//						rs.setMsg("编辑微信推送配置成功");
//					}else{
//						rs.setCode(500);
//						rs.setSuccess(false);
//						rs.setMsg("编辑微信推送配置失败");
//					}
//				}
//			}
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("该企业微信推送配置不存在");
		}
		return rs;
	}

	@GetMapping("/remove")
	@ApiLog("运维端-微信推送配置删除")
	@ApiOperation(value = "运维端-微信推送配置删除", notes = "传入数据ID、用户ID", position = 5)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Id", value = "数据ID", required = true),
		@ApiImplicitParam(name = "userId", value = "用户ID", required = true)
	})
	public R remove(@RequestParam Integer Id,Integer userId) {
		R rs = new R();
		String updateTime = DateUtil.now();
		boolean i = qiYeShouYeService.deleteDeptWechatInfo(updateTime,userId,Id);
		boolean q = qiYeShouYeService.deleteDeptWechatRemark(updateTime,userId,Id);
		if(i == true){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("删除微信推送配置成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("删除微信推送配置失败");
		}
		return rs;
	}

}
