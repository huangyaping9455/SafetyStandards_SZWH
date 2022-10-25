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
package org.springblade.anbiao.guanlijigouherenyuan.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.configure.entity.Configure;
import org.springblade.anbiao.configure.service.IConfigureService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.AnBiaoLogin;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Personnel;
import org.springblade.anbiao.guanlijigouherenyuan.page.PersonnelPage;
import org.springblade.anbiao.guanlijigouherenyuan.service.IPersonnelService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.PersonnelVO;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Post;
import org.springblade.system.feign.ISysClient;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.feign.IUserFiegn;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 *  控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/personnel")
@Api(value = "人员管理", tags = "人员管理")
public class PersonnelController extends BladeController {

	private IPersonnelService personnelService;

	private IConfigureService mapService;

	private IUserFiegn userClient;

	private ISysClient iSysClient;

	private IFileUploadClient fileUploadClient;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiLog("详情-人员管理")
	@ApiOperation(value = "详情-人员管理", notes = "传入id", position = 1)
	public R detail(String id) {
		return R.data(personnelService.getById(id));
	}

	/**
	 * 分页
	 */
	@PostMapping("/list")
	@ApiLog("分页-人员管理")
	@ApiOperation(value = "分页-人员管理", notes = "传入PersonnelPage", position = 2)
	public R<PersonnelPage<PersonnelVO>> list(@RequestBody PersonnelPage Page) {
		Dept dept = iSysClient.selectByJGBM("机构",Page.getPostId().toString());
		Page.setDeptId(dept.getId());
		PersonnelPage<PersonnelVO> pages = personnelService.selectPageList(Page);
		return R.data(pages);
	}

	/**
	 * 重定义单位id
	 */
	@PostMapping("/cdjg")
	@ApiLog("重定义单位id")
	@ApiOperation(value = "重定义单位id", notes = "传入postId", position = 2)
	public R cdjg(String postId) {
		List<Personnel> list=personnelService.selectJG();
		for (int i = 0; i < list.size(); i++) {
			Dept dept = iSysClient.selectByJGBM("机构",list.get(i).getPostId());
			Personnel personnel=personnelService.selectpostId(list.get(i).getPostId(),list.get(i).getUserid().toString());
			personnel.setDeptId(dept.getId());
			personnelService.saveOrUpdate(personnel);
		}

		return R.data("成功");
	}

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-人员管理")
	@ApiOperation(value = "新增-人员管理", notes = "传入personnel", position = 3)
	public R insert(@RequestBody Personnel personnel,BladeUser bladeUser) {
		R rs = new R();
		User user=new User();
		if (Func.isNotEmpty(personnel.getPassword())) {
			user.setPassword(DigestUtil.encrypt(personnel.getPassword()));
		}
		int code=201;
		Object obj=new Object();
		String msg;
		//获取当前最大id
		int userId=userClient.selectMaxId()+1;
		//岗位id
		String postId=personnel.getPostId();
		Post post=new Post();
		post.setUserId(userId);
		post.setPostId(Integer.parseInt(postId));
		//直属上级id
		Dept dept=iSysClient.selectByJGBM("机构",postId);
		user.setDeptId(dept.getId().toString());
		user.setId(userId);
		user.setRoleId("2");
		user.setAccount(personnel.getAccount());
		user.setName(personnel.getXingming());
		user.setRealName(personnel.getXingming());
		//获取时间
		java.util.Date date = new java.util.Date();
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		if(bladeUser == null){
			user.setCreateUser(1);
			user.setUpdateUser(1);
		}else{
			user.setCreateUser(bladeUser.getUserId());
			user.setUpdateUser(bladeUser.getUserId());
		}
		user.setCreateTime(localDateTime);
		user.setUpdateTime(localDateTime);
		user.setBirthday(personnel.getChushengriqi());
		user.setEmail(personnel.getYouxiang());
		user.setPhone(personnel.getShoujihao());
		//业务状态默认为1
		user.setStatus(1);
		int i=userClient.selectByLoginName(personnel.getAccount());
		if(i>0){
			msg="该登录账号已存在，请重新输入";
			rs.setCode(code);
			rs.setData(obj);
			rs.setMsg(msg);
		}else{
			//执行插入人员基本数据
			boolean flag=userClient.insertPer(user);
			//人员基本信息表存储
			personnel.setUserid(userId);
			if(bladeUser == null){
				personnel.setCaozuoren("管理员");
				personnel.setCaozuorenid(1);
			}else{
				personnel.setCaozuoren(bladeUser.getUserName());
				personnel.setCaozuorenid(bladeUser.getUserId());
			}

			personnel.setCaozuoshijian(DateUtil.now());
			personnel.setCreatetime(DateUtil.now());
			personnel.setDeptId(dept.getId());
			personnel.setPostId(personnel.getPostId());
			personnel.setIsDeleted(0);
			if(StringUtil.isNotBlank(personnel.getFujian())){
				fileUploadClient.updateCorrelation(personnel.getFujian(),"1");
			}
//			personnelService.saveOrUpdate(personnel);
			personnelService.insertSelective(personnel);
			code=R.status(flag).getCode();
			obj=R.status(flag).getData();
			if(flag==true){
				//执行插入人员-岗位数据
				int ii=personnelService.selectByPost(userId+"");
				if(ii==0){
					//第一次新增的人员岗位设置为默认岗位
					post.setIsdefault(1);
				}
				iSysClient.insertPost(post);
			}
			msg="保存成功";
			rs.setCode(code);
			rs.setData(obj);
			rs.setMsg(msg);
		}
		return rs;
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiLog("修改-人员管理")
	@ApiOperation(value = "修改-人员管理", notes = "传入personnel", position = 4)
	public R update(@RequestBody Personnel personnel,BladeUser bladeUser) {
		R rs = new R();
		//获取时间
		User user=new User();
		java.util.Date date = new java.util.Date();
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		if(bladeUser == null){
			user.setUpdateUser(1);
		}else{
			user.setUpdateUser(bladeUser.getUserId());
		}
		user.setUpdateTime(localDateTime);
		user.setId(personnel.getUserid());
		user.setAccount(personnel.getAccount());
		user.setPassword(DigestUtil.encrypt(personnel.getPassword()));
		user.setName(personnel.getXingming());
		user.setRealName(personnel.getXingming());
		user.setBirthday(user.getBirthday());
		user.setEmail(personnel.getYouxiang());
		user.setPhone(personnel.getShoujihao());
		//基本信息
		personnel.setId(personnel.getId());
		if(bladeUser == null){
			personnel.setCaozuoren("admin");
			personnel.setCaozuorenid(1);
		}else{
			personnel.setCaozuoren(bladeUser.getUserName());
			personnel.setCaozuorenid(bladeUser.getUserId());
		}
		personnel.setCaozuoshijian(DateUtil.now());
		personnel.setCreatetime(DateUtil.now());
		if(StringUtil.isNotBlank(personnel.getFujian())){
			fileUploadClient.updateCorrelation(personnel.getFujian(),"1");
		}
		personnel.setDeptId(personnel.getDeptId());
//		personnelService.saveOrUpdate(personnel);
//		personnelService.insertSelective(personnel);
//		int i=userClient.selectByLoginName(personnel.getAccount());
//		if(i>0){
//			rs.setCode(500);
//			rs.setSuccess(false);
//			rs.setMsg("该登录账号已存在，请重新输入");
//		}else {
		personnelService.updateSelective(personnel);
		boolean u = userClient.updatePer(user);
		if(u == true){
			rs.setCode(200);
			rs.setMsg("更新成功");
			rs.setSuccess(true);
		}
//		}
		return rs;
	}

	/**
	 * 删除
	 */
	@PostMapping("/del")
	@ApiLog("删除-人员管理")
	@ApiOperation(value = "删除-人员管理", notes = "传入id", position = 5)
	public R del(@ApiParam(value = "id", required = true) @RequestParam String id) {
		//清理人员信息
		userClient.deletePer(personnelService.getById(id).getUserid().toString());
		return R.status(personnelService.updateDel(id));
	}

	/********************************** 配置表 ***********************/

	/**
	 * 根据单位id获取配置模块数据
	 */
	@GetMapping("/listMap")
	@ApiLog("获取配置-人员管理")
	@ApiOperation(value = "获取配置-人员管理", notes = "传入deptId", position = 6)
	public R<JSONArray> listMap(Integer deptId) {
		List<Configure> list1=mapService.selectMapList("anbiao_personnel_map",deptId);
		String str="";
		for (int i = 0; i <list1.size() ; i++) {
			//转换成json数据并put id
			JSONObject jsonObject = JSONUtil.parseObj(list1.get(i).getBiaodancanshu());
			jsonObject.put("id",list1.get(i).getId());
			if(!str.equals("")){
				str=str+","+jsonObject.toString();
			}else{
				str=jsonObject.toString();
			}
		}
		str="["+str+"]";
		JSONArray json= JSONUtil.parseArray(str);
		return R.data(json);
	}

	/**
	 * 配置表新增
	 */
	@PostMapping("/insertMap")
	@ApiLog("配置表新增-人员管理")
	@ApiOperation(value = "配置表新增-人员管理", notes = "传入biaodancanshu与deptId", position = 7)
	public R insertMap(String biaodancanshu,String deptId) {
		Configure configure=new Configure();
		JSONObject jsonObject = JSONUtil.parseObj(biaodancanshu);
		configure.setLabel(jsonObject.getStr("label"));
		configure.setShujubiaoziduan(jsonObject.getStr("prop"));
		configure.setDeptId(Integer.parseInt(deptId));
		configure.setTableName("anbiao_personnel_map");
		configure.setBiaodancanshu(biaodancanshu);
		return R.status(mapService.insertMap(configure));
	}
	/**
	 * 配置表编辑
	 */
	@PostMapping("/updateMap")
	@ApiLog("配置表编辑-人员管理")
	@ApiOperation(value = "配置表编辑-人员管理", notes = "传入biaodancanshu与id", position = 9)
	public R updateMap(String biaodancanshu,String id) {
		Configure configure=new Configure();
		JSONObject jsonObject = JSONUtil.parseObj(biaodancanshu);
		configure.setId(id);
		configure.setLabel(jsonObject.getStr("label"));
		configure.setShujubiaoziduan(jsonObject.getStr("prop"));
		configure.setTableName("anbiao_personnel_map");
		configure.setBiaodancanshu(biaodancanshu);
		return R.status(mapService.updateMap(configure));
	}

	/**
	 * 配置表删除
	 */
	@PostMapping("/delMap")
	@ApiLog("配置表删除-人员管理")
	@ApiOperation(value = "配置表删除-人员管理", notes = "传入id", position = 8)
	public R delMap(@ApiParam(value = "主键id", required = true) @RequestParam String id) {
		return R.status(mapService.delMap("anbiao_personnel_map",id));
	}

	@PostMapping("/insertAnBiaoLogin")
	@ApiLog("新增-统一入口账号")
	@ApiOperation(value = "新增-统一入口账号", notes = "传入anBiaoLogin", position = 9)
	public R insertAnBiaoLogin(@RequestBody AnBiaoLogin anBiaoLogin) {
		String formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		anBiaoLogin.setCreatetime(formatStr2);
		R rs = new R();
		if(anBiaoLogin.getType().equals("安标")){
			anBiaoLogin.setProject_one(1);
		}else if(anBiaoLogin.getType().equals("物流")){
			anBiaoLogin.setProject_two(2);
		}else if(anBiaoLogin.getType().equals("教育")){
			anBiaoLogin.setProject_three(3);
		}
		AnBiaoLogin anBiaoLogin1 = personnelService.selectAnBiaoLogin(anBiaoLogin.getName(),null);
		if (anBiaoLogin1 != null){
			boolean i = personnelService.updateAnBiaoLogin(anBiaoLogin);
			if(i == true){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("该账号已存在，只做更新");
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("编辑-统一入口账号失败");
			}
		}else{
			boolean i = personnelService.insertAnBiaoLogin(anBiaoLogin);
			if(i == true){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("新增-统一入口账号成功");
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("新增-统一入口账号失败");
			}
		}
		return rs;
	}

	@PostMapping("logintoken")
	@ApiLog("获取登录认证token")
	@ApiOperation(value = "获取登录认证token", notes = "传入账号:account,密码:password")
	public R<AnBiaoLogin> logintoken(@ApiParam(value = "账号", required = true) @RequestParam String account,
									 @ApiParam(value = "密码", required = true) @RequestParam String password) {
		AnBiaoLogin user = personnelService.selectAnBiaoLogin(account, password);
		//验证用户
		if(user==null){
			return R.fail("用户名不存在");
		}
		return R.data(user);
	}
}
