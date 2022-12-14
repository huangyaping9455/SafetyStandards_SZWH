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
package org.springblade.system.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Personnel;
import org.springblade.anbiao.guanlijigouherenyuan.feign.IOrganizationsClient;
import org.springblade.anbiao.guanlijigouherenyuan.feign.IPersonnelClient;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.INode;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Post;
import org.springblade.system.entity.PostMenu;
import org.springblade.system.service.IDeptService;
import org.springblade.system.service.IMenuService;
import org.springblade.system.service.IPostMenuService;
import org.springblade.system.service.IPostService;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.feign.IUserFiegn;
import org.springblade.system.vo.DeptSubVO;
import org.springblade.system.vo.DeptVO;
import org.springblade.system.vo.MenuVO;
import org.springblade.system.wrapper.DeptWrapper;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.*;

/**
 * 控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
@Api(value = "机构", tags = "机构")
public class DeptController extends BladeController {

	private IDeptService deptService;

	private IPostService postService;

	private IMenuService menuService;

	private IPostMenuService postMenuService;

	private IUserFiegn userClient;

	private IPersonnelClient  personnelClient;

	private IOrganizationsClient orrganizationsClient;

	private IFileUploadClient fileUploadClient;

	private FileServer fileServer;

	private AlarmServer alarmServer;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "fullName", value = "部门全称", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "列表", notes = "传入dept", position = 1)
	public R<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> dept, BladeUser bladeUser) {
		Iterator<String> iterator = dept.keySet().iterator();
		System.out.println(iterator);
		while (iterator.hasNext()){
			if(iterator.next().equals("deptId")){
				iterator.remove();
			}
		}
		QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
		List<Dept> list = deptService.list((!bladeUser.getTenantCode().equals(BladeConstant.ADMIN_TENANT_CODE)) ? queryWrapper.lambda().eq(Dept::getTenantCode, bladeUser.getTenantCode()) : queryWrapper);
		DeptWrapper deptWrapper = new DeptWrapper();
		return R.data(deptWrapper.listNodeVO(list));
	}

	/**
	 * 组织机构-详情
	 */
	@GetMapping("/viewInfo")
	@ApiOperation(value = "组织机构-详情", notes = "传入id", position = 2)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "组织机构id", required = true) })
	public R<Dept> viewInfo(Integer id)  {
		Dept dept=deptService.selectByDeptId(id);
		return R.data(dept);
	}
	/**
	 * 组织机构-新增
	 */
	@PostMapping("/insert")
	@ApiOperation(value = "组织机构-新增", notes = "传入dept", position = 3)
	public R insert(@Valid @RequestBody Dept dept,BladeUser user) {
		String treeCode=deptService.selectByTreeCode(dept.getParentId()).getTreeCode();
		dept.setTreeCode(treeCode);
		dept.setId(deptService.selectMaxId()+1);
		dept.setDeptName(dept.getDeptName().trim());
		int i=deptService.selectByName(dept.getFullName().trim());
		int code=201;
		Object obj=new Object();
		String msg;
		if(i>0){
			msg="系统已存在相同名称";
		}else{
			//新增组织基本信息
			boolean flag=deptService.insertDept(dept);
			code=R.status(flag).getCode();
			obj=R.status(flag).getData();
			msg="操作成功";
		}
		return R.data(code,obj,msg);

	}
	/**
	 * 组织机构-编辑
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "组织机构-编辑", notes = "传入dept", position = 4)
	public R submit(@Valid @RequestBody Dept dept, BladeUser user) {
		if (Func.isEmpty(dept.getId())) {
			dept.setTenantCode(user.getTenantCode());
		}
		//返回组织机构数据 进行拼接
		dept.setFullName(dept.getDeptName());
		boolean flag=deptService.saveOrUpdate(dept);
		int code=R.status(flag).getCode();
		Object obj=R.status(flag).getData();
		String msg="操作成功";
		return R.data(code,obj,msg);
	}

	/**
	 * 组织机构-删除
	 */
	@GetMapping("/remove")
	@ApiOperation(value = "删除组织机构", notes = "传入id", position = 5)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "组织机构id", required = true) })
	public R remove(Integer id) {
		int i=deptService.selectCountByparentId(id);
		int code=201;
		Object obj=new Object();
		String msg;
		if(i>0){
			msg="该组织下还存在下级，不能删除";
		}else{
			boolean flag=deptService.removeById(id);
			code=R.status(flag).getCode();
			obj=R.status(flag).getData();
			msg="删除成功";
		}
		return R.data(code,obj,msg);
	}

	/**
	 * 组织机构-tree
	 *
	 * @return
	 */
	@GetMapping("/tree")
	@ApiOperation(value = "组织机构-tree", notes = "组织机构-tree", position = 6)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postId", value = "岗位id", required = false),
		@ApiImplicitParam(name = "deptId", value = "单位id", required = false)
	})
	public R<List<DeptVO>> tree(String postId,String deptId) {
		Dept dept;
		List<DeptVO> tree;
		//默认加载
		if(StrUtil.isNotEmpty(postId)){
			dept=deptService.selectByJGBM("机构",postId);
			tree = deptService.tree(dept.getId().toString(),"1");
		}else{
			//根据所选择树形加载下级数据
			tree = deptService.tree(deptId,"2");
		}
		return R.data(tree);
	}

	/**
	 * 组织机构-多岗tree
	 *
	 * @return
	 */
	@GetMapping("/treeDG")
	@ApiOperation(value = "组织机构-多岗tree", notes = "组织机构-多岗tree", position = 7)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postId", value = "岗位id", required = true)})
	public R<List<DeptVO>> treeDG(String postId) {
		Dept dept=deptService.selectByJGBM("机构",postId);
		List<DeptVO>tree = deptService.treeDG(dept.getId().toString());
		return R.data(tree);
	}
	/**
	 * 组织机构-机构异动tree
	 *
	 * @return
	 */
	@GetMapping("/YDtree")
	@ApiOperation(value = "组织机构-机构异动tree", notes = "组织机构-机构异动", position = 7)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postId", value = "岗位id", required = true)})
	public R<List<DeptVO>> YDtree(String postId) {
		Dept dept=deptService.selectByJGBM("机构",postId);
		List<DeptVO>tree = deptService.YDtree(dept.getId().toString());
		return R.data(tree);
	}
	//****************************机构异动***********************
	/**
	 * 组织机构-机构异动-save
	 */
	@PostMapping("/YDsave")
	@ApiOperation(value = "组织机构-企业异动-save", notes = "传入需要移动机构id与移动位置机构id", position = 8)
	@ApiImplicitParams({ @ApiImplicitParam(name = "newdeptId", value = "移动位置机构id", required = true),
		@ApiImplicitParam(name = "deptId", value = "移动机构id", required = true)
	})
	public R YDsave(String deptId,String newdeptId) {
		R rs = new R();
		int code=201;
		Object obj=new Object();
		String msg = null;
		//获取移动位置机构id treecode
		Dept deptd=deptService.selectByDeptId(Integer.parseInt(newdeptId));
		//默认移动机构treecode 移动后机构treecode为移动位置treecode+移动单位id 部门岗位则为移动后岗位treecode+自身id
		String treecode=deptd.getTreeCode();
		//获取当前企业下级 包括企业、部门、岗位
		List<DeptVO> depts=deptService.treeList(deptId);
		for (int i = 0; i <depts.size() ; i++) {
			//根据id查询信息并重新赋值
			Dept dept=deptService.selectByDeptId(depts.get(i).getId());
			String id=dept.getId()+"";
			if(id.length()==1){
				id= "00000"+dept.getId();
			}else if(id.length()==2){
				id= "0000"+dept.getId();
			}else if(id.length()==3){
				id= "000"+dept.getId();
			}else if(id.length()==4){
				id= "00"+dept.getId();
			}else if(id.length()==5){
				id= "0"+dept.getId();
			}
			//为机构时设置上级id 根据排序规则 第一个必是机构 也只需要更改第一个数据的upperid
			if(i == 0){
				dept.setParentId(Integer.parseInt(newdeptId));
			}else{
				//为部门岗位时进入此方法 根据自身上级id获取上级的treecode信息
				Dept dept1=deptService.selectByDeptId(dept.getParentId());
				//进行赋值
				treecode=dept1.getTreeCode();
			}
			dept.setTreeCode(treecode+id);
			boolean flag=deptService.saveOrUpdate(dept);
			code=R.status(flag).getCode();
			obj=R.status(flag).getData();
			msg="操作成功";
		}
		rs.setCode(code);
		rs.setData(obj);
		rs.setMsg(msg);
		return rs;
	}




	//**************************权限 start************************
	/**
	 * 权限-运维-save
	 *
	 * @param postIds
	 * @param menuIds
	 * @return
	 */
	@GetMapping("/grant")
	@ApiOperation(value = "权限-运维-save", notes = "传入postId以及menuId集合", position = 8)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postIds", value = "岗位id", required = true),
		@ApiImplicitParam(name = "menuIds", value = "menuIds集合", required = true)
	})
	public R grant( String postIds, String menuIds) {
		boolean temp = postService.grant(StrUtil.split(postIds,','), StrUtil.split(menuIds,','));
		return R.status(temp);
	}

	/**
	 * 权限-Add
	 *
	 * @param deptId
	 * @param menuIds
	 * @return
	 */
	@GetMapping("/grantAdd")
	@ApiOperation(value = "权限-追加", notes = "传入deptId以及menuId集合", position = 8)
	@ApiImplicitParams({ @ApiImplicitParam(name = "deptId", value = "机构id", required = true),
		@ApiImplicitParam(name = "menuIds", value = "menuIds集合", required = true),
		@ApiImplicitParam(name = "type", value = "权限类型（0代表运维 1代表安标 2代表安标16项 3代表展板 4代表app）", required = true)
	})
	public R grantAdd( String deptId, String menuIds,Integer type) {
		boolean temp = postService.grantAdd(deptId, StrUtil.split(menuIds,','),type);
		return R.status(temp);
	}


	/**
	 * 权限-syn
	 *
	 * @param deptId
	 * @param menuIds
	 * @return
	 */
	@GetMapping("/grantSyn")
	@ApiOperation(value = "权限-同步", notes = "传入deptId以及menuId集合", position = 8)
	@ApiImplicitParams({ @ApiImplicitParam(name = "deptId", value = "机构id", required = true),
		@ApiImplicitParam(name = "menuIds", value = "menuIds集合", required = true),
		@ApiImplicitParam(name = "type", value = "权限类型（0代表运维 1代表安标 2代表安标16项 3代表展板 4代表app）", required = true)
	})
	public R grantSyn( String deptId, String menuIds,Integer type) {
		boolean temp = postService.grantSyn(deptId, StrUtil.split(menuIds,','),type);
		return R.status(temp);
	}
	/**
	 * 权限-安标-save
	 *
	 * @param postIds
	 * @param menuIds
	 * @return
	 */
	@GetMapping("/ABgrant")
	@ApiOperation(value = "权限-安标-save", notes = "传入postId以及menuId集合", position = 9)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postIds", value = "岗位id", required = true),
		@ApiImplicitParam(name = "menuIds", value = "menuIds集合", required = true)
	})
	public R ABgrant( String postIds, String menuIds,Integer type) {
		if(type == null){
			type = 1;
		}
		boolean temp = postService.ABgrant(StrUtil.split(postIds,','), StrUtil.split(menuIds,','),type);
		return R.status(temp);
	}
	/**
	 * 权限-功能-save
	 *
	 * @param postIds
	 * @param menuIds
	 * @return
	 */
	@GetMapping("/Businessgrant")
	@ApiOperation(value = "权限-功能-save", notes = "传入postId以及menuId集合", position = 10)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postIds", value = "岗位id", required = true),
		@ApiImplicitParam(name = "menuIds", value = "menuIds集合", required = true)
	})
	public R Businessgrant( String postIds, String menuIds) {
		boolean temp = postService.Businessgrant(Func.toIntList(postIds), Func.toIntList(menuIds));
		return R.status(temp);
	}
	/**
	 * 权限-安标16项-save
	 *
	 * @param postIds
	 * @param menuIds
	 * @return
	 */
	@GetMapping("/Jurgrant")
	@ApiOperation(value = "权限-安标16项-save", notes = "传入postId以及menuId集合", position = 11)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postIds", value = "岗位id", required = true),
		@ApiImplicitParam(name = "menuIds", value = "menuIds集合", required = true)
	})
	public R Jurgrant( String postIds, String menuIds) {
		boolean temp = postService.ABJurisdiction(StrUtil.split(postIds,','), StrUtil.split(menuIds,','));
		return R.status(temp);
	}
	/**
	 * 权限-app-save
	 *
	 * @param postIds
	 * @param menuIds
	 * @return
	 */
	@GetMapping("/Appgrant")
	@ApiOperation(value = "权限-app-save", notes = "传入postId以及menuId集合", position = 12)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postIds", value = "岗位id", required = true),
		@ApiImplicitParam(name = "menuIds", value = "menuIds集合", required = true)
	})
	public R Appgrant( String postIds, String menuIds) {
		boolean temp = postService.Appgrant(StrUtil.split(postIds,','), StrUtil.split(menuIds,','));
		return R.status(temp);
	}

	//*******************************************运维****************************************
	/**
	 * 权限-运维-tree
	 */
	@GetMapping("/grant-tree")
	@ApiOperation(value = "权限-运维-tree", notes = "权限-运维-tree", position = 13)
	public R<List<MenuVO>> grantTree(BladeUser user) {
		return R.data(menuService.grantTree(user));
	}

	/**
	 * 权限-运维-菜单
	 */
	@GetMapping("/post-tree-keys")
	@ApiOperation(value = "权限-运维-菜单", notes = "传入postId", position = 14)
	public R<List<String>> postTreeKeys(String postId) {
		return R.data(menuService.postTreeKeys(postId));
	}
	//*******************************************安标**************************************
	/**
	 * 权限-安标-tree
	 */
	@GetMapping("/ABgrant-tree")
	@ApiOperation(value = "权限-安标-tree", notes = "权限-安标-tree", position = 15)
	public R<List<MenuVO>> ABgrantTree(BladeUser user,Integer type) {
		if(type == null){
			type = 1;
		}
		return R.data(menuService.ABgrantTree(user,type));
	}

	/**
	 * 权限-安标-菜单
	 */
	@GetMapping("/ABpostTreeKeys")
	@ApiOperation(value = "权限-安标-菜单", notes = "传入postId", position = 16)
	public R<List<String>> ABpostTreeKeys(String postId,Integer type) {
		if(type == null){
			type = 1;
		}
		return R.data(menuService.ABpostTreeKeys(postId,type));
	}
	//**************************app*************************
	/**
	 * 权限-app-tree
	 */
	@GetMapping("/Appgrant-tree")
	@ApiOperation(value = "权限-app-tree", notes = "权限-app-tree", position = 17)
	public R<List<MenuVO>> Apptree(BladeUser user) {
		return R.data(menuService.Apptree(user));
	}
	/**
	 * 权限-app-菜单
	 */
	@GetMapping("/AppgrantTreeByPost")
	@ApiOperation(value = "权限-app-菜单", notes = "传入postId", position = 18)
	public R<List<String>> AppgrantTreeByPost(String postId) {
		return R.data(menuService.AppgrantTreeByPost(postId));
	}

	//********************************************安标16项***************************************

	//********************************************业务权限**************************************
	/**
	 * 权限-功能-tree
	 */
	@GetMapping("/Businesstree-tree")
	@ApiOperation(value = "权限-功能-tree", notes = "权限-功能-tree", position = 19)
	public R<List<MenuVO>> Businesstree(BladeUser user) {
		return R.data(menuService.Businesstree(user));
	}

	/**
	 * 权限-功能-菜单
	 */
	@GetMapping("/BusinessgrantTreeByPost")
	@ApiOperation(value = "权限-功能-菜单", notes = "传入postId", position = 20)
	public R<List<String>> BusinessgrantTreeByPost(String postId) {
		return R.data(menuService.BusinessgrantTreeByPost(postId));
	}

	/**
	 * 业务权限
	 */
	@GetMapping("/permission")
	@ApiOperation(value = "业务权限", notes = "传入postId", position = 21)
	public R<Map<String,Boolean>> permission(String postId) {
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		List<PostMenu> list=postMenuService.selectListByPostIdBusiness(Integer.parseInt(postId),3);
		for (int i = 0; i <list.size() ; i++) {
			//代表展板
			if(list.get(i).getMenuId()==1&&list.get(i).getType()==3){
				map.put("enterprise_panel",true);
			}else{
				map.put("enterprise_panel",false);
			}
		}
		return R.data(map);
	}
	//********************************************业务权限end*******************************
	//放在biaozhunhuamuban里面

	//*****************************************权限 end  ********************************************

	//***************************************人员信息 start**********************************************

	/**
	 * 组织机构-人员-根据岗位id获取人员
	 * @param postId
	 * @return
	 */
	@GetMapping("/selectByPostId")
	@ApiOperation(value = "组织机构-人员-根据岗位id获取人员", notes = "传入postId", position = 22)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postId", value = "岗位id", required = true) })
	public R<List<User>> selectByPostId(String postId) {
		List<User> user=userClient.selectByPostId(postId);
		return R.data(user);
	}

	/**
	 * 组织机构-人员-初始化密码
	 * @param userId
	 * @return
	 */
	@GetMapping("/reset-password")
	@ApiOperation(value = "组织机构-人员-初始化密码", notes = "传入userId集合", position = 23)
	public R resetPassword(@ApiParam(value = "userId", required = true) String userId) {
		boolean temp = userClient.resetPassword(userId);
		return R.status(temp);
	}

	/**
	 * 组织机构-人员-多岗设置
	 */
	@PostMapping("/saveMultiple")
	@ApiOperation(value = "组织机构-人员-多岗设置", notes = "传入岗位id集合与人员id", position = 24)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postIds", value = "岗位id集合", required = true),
		@ApiImplicitParam(name = "userId", value = "人员id", required = true)
	})
	public R saveMultiple(String postIds,Integer userId,BladeUser user) {
		R rs = new R();
		int code=201;
		Object obj=new Object();
		String msg = null;
		List<String> postId=StrUtil.split(postIds,',');

		if(postId.size()>0){
			Post post1=postService.selectByUserIdAndIsdefault(userId);
			boolean status=postIds.contains(post1.getPostId().toString());
			if(status){
				msg="包含默认岗位";
			}else{
				String deptName=deptService.selectByDeptId(post1.getPostId()).getDeptName();
				Dept dept=deptService.selectByJGBM("机构",post1.getPostId().toString());
				msg="请勾选"+dept.getDeptName()+"企业下默认岗位"+deptName;
			}
			String strSub = StrUtil.sub(msg, 0, 3);
			if(!strSub.equals("请勾选")){
				//清理人员相关岗位
				postService.deleteByUserId(userId);
				Personnel personnel=new Personnel();
				for (int i = 0; i < postId.size() ; i++) {
					//根据用户ID、岗位ID获取人员相关详细信息
					Post post=new Post();
					post.setUserId(userId);
					post.setPostId(Integer.parseInt(postId.get(i)));
					boolean flag=postService.saveOrUpdate(post);
					if(i == 0){
						//设置当前切换岗位为默认岗位
						postService.updateIsdefault(post);
					}
					//判断多岗设置时人员基础信息是否存在
					Dept dept=deptService.selectByJGBM("机构",postId.get(i));
					//根据人员id单位id查询人员基础信息是否存在
					Personnel per=personnelClient.selectByUserIdAdnByDeptId(userId.toString(),dept.getId().toString());
					//人员信息不为空的时候进行赋值
					if(per!=null){
						personnel=per;
					}
					code=R.status(flag).getCode();
					obj=R.status(flag).getData();
				}
				//有且只有一个岗位信息时设置为默认岗位
				if(postId.size()==1){
					//设置当前切换岗位为默认岗位
					Post post=new Post();
					post.setUserId(userId);
					post.setPostId(Integer.parseInt(postId.get(0)));
					postService.updateIsdefault(post);
				}
				//清理人员数据
				personnelClient.updateDelByUserId(userId.toString());
				for (int i = 0; i < postId.size() ; i++) {
					//判断多岗设置时人员基础信息是否存在
					Dept dept=deptService.selectByJGBM("机构",postId.get(i));
					//执行新增
					personnel.setDeptId(dept.getId());
					personnel.setUserid(userId);
					if(user == null){
						personnel.setCaozuoren("admin");
						personnel.setCaozuorenid(1);
					}else{
						personnel.setCaozuoren(user.getUserName());
						personnel.setCaozuorenid(user.getUserId());
					}
					personnel.setCaozuoshijian(DateUtil.now());
					personnel.setCreatetime(DateUtil.now());
					personnel.setXingming(personnel.getXingming());
					personnel.setPostId(postId.get(i));
					personnel.setIsDeleted(0);
					//根据人员id单位id查询人员基础信息是否存在
					Personnel per=personnelClient.selectByUserIdAdnByDeptId(userId.toString(),dept.getId().toString());
					//人员信息为空时进行新增
					if(per==null){
						personnel.setId(null);
						System.out.println("personnel");
						System.out.println(personnel);
						boolean ss = personnelClient.insertPersonnelSelective(personnel);
						if(ss == true){
							msg="保存成功";
							rs.setCode(200);
							rs.setMsg(msg);
						}else{
							msg="保存失败";
							rs.setCode(500);
							rs.setMsg(msg);
						}
					}
				}
			}
		}else{
			msg="请选择一个岗位";
			rs.setCode(500);
			rs.setMsg(msg);
		}
		return rs;
	}
	//***************************************人员信息 end**********************************************

	//**********************************岗位切换*********************************
	/**
	 *  复选拥有岗位
	 */
	@GetMapping("/checkHavePost")
	@ApiOperation(value = "复选拥有岗位", notes = "复选拥有岗位", position = 25)
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "人员id", required = true)})
	public R<List<String>> checkHavePost(Integer userId) {
		List<String> list = new ArrayList<String>();
		//根据用户id查询所拥有岗位id
		List<Post> lists=postService.selectByUserId(userId);
		Map<Integer,String> map = new HashMap<Integer,String>();
		for (int i = 0; i < lists.size(); i++) {
			String postId=lists.get(i).getPostId().toString();
			list.add(postId);
		}
		return R.data(list);
	}

	/**
	 * 所拥有岗位
	 */
	@GetMapping("/checkPost")
	@ApiOperation(value = "人员所拥有岗位", notes = "人员所拥有岗位", position = 26)
	public R<List<Map<String, String>>> checkPost(BladeUser user,String type) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if("运维端".equals(type)){
			//根据用户id查询所拥有岗位id
			List<Post> lists=postService.selectByUserId(user.getUserId());
			for (int i = 0; i < lists.size(); i++) {
				Map<String,String> map = new HashMap<String,String>();
				String postId=lists.get(i).getPostId().toString();
				//直属上级id
				Dept dept=deptService.selectByJGBM("机构",postId);
				String postName=deptService.selectByDeptId(Integer.parseInt(postId)).getDeptName();
				map.put("postName",dept.getDeptName()+"|"+postName);
				map.put("postId",postId);
				map.put("deptId",dept.getId().toString());
				list.add(map);
			}
		}else{
			//根据用户id查询所拥有岗位id
			List<Post> lists=postService.selectByUserId(user.getUserId());
			for (int i = 0; i < lists.size(); i++) {
				Map<String,String> map = new HashMap<String,String>();
				String postId=lists.get(i).getPostId().toString();
				//直属上级id
				Dept dept=deptService.selectByJGBM("机构",postId);
				Organizations organizations = orrganizationsClient.selectByDeptId(dept.getId().toString());
				if(organizations != null){
					if(organizations.getJigouleixing().equals("qiye") || organizations.getJigouleixing().equals("geti") || organizations.getDeptId().equals("1")){
						System.out.println(postId);
						System.out.println(i);
						String postName=deptService.selectByDeptId(Integer.parseInt(postId)).getDeptName();
						System.out.println(postName);
						map.put("postName",dept.getDeptName()+"|"+postName);
						map.put("postId",postId);
						map.put("deptId",dept.getId().toString());
						list.add(map);
					}
				}
			}
		}
		return R.data(list);
	}
	/**
	 * 岗位切换
	 */
	@GetMapping("/getUserInfo")
	@ApiOperation(value = "获取人员信息-岗位切换", notes = "获取人员信息-岗位切换", position = 27)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postId", value = "岗位id", required = true)})
	public R<AuthInfoConfig>  getUserInfo(BladeUser user, String postId) {
		//设置jwt参数
		Map<String, String> param = new HashMap<>(16);
		param.put(SecureUtil.USER_ID, Func.toStr(user.getUserId()));
		param.put(SecureUtil.ROLE_ID, user.getRoleId());
		param.put(SecureUtil.TENANT_CODE, user.getTenantCode());
		param.put(SecureUtil.ACCOUNT, user.getAccount());
		param.put(SecureUtil.USER_NAME, user.getUserName());
		param.put(SecureUtil.ROLE_NAME, postId);
		//拼装accessToken
		String accessToken = SecureUtil.createJWT(param, "audience", "issuser", true);
		//返回accessToken
		AuthInfoConfig info=new AuthInfoConfig();
		info.setUrlPrefix(fileServer.getUrlPrefix());
		info.setAccount(user.getAccount());
		info.setUserName(user.getUserName());
		info.setAuthority("administrator");
		info.setAccessToken(accessToken);
		info.setTokenType(SecureUtil.BEARER);
		info.setPostId(postId);
		Dept dept=deptService.selectByJGBM("机构",postId);
		info.setDeptId(dept.getId().toString());
		info.setDeptName(dept.getDeptName());
		info.setUserId(user.getUserId().toString());
		info.setPostName(deptService.getById(postId).getDeptName());
		//设置当前切换岗位为默认岗位
		Post post=new Post();
		post.setUserId(user.getUserId());
		post.setPostId(Integer.parseInt(postId));
		postService.updateIsdefault(post);
		//根据单位id获取企业基本信息
		Organizations organization=orrganizationsClient.selectByDeptId(dept.getId().toString());
		if(organization!=null){
			//根据企业ID获取上级组织信息
			Organizations organizationsVO = orrganizationsClient.selectParentDeptById(dept.getId().toString());
			if(organizationsVO != null) {
				//获取配置logo
				if (StringUtils.isNotBlank(organization.getLogoPhoto())) {
					info.setLogoPhoto(fileUploadClient.getUrlUrl(organization.getLogoPhoto()));
				} else {
					if (StringUtils.isNotBlank(organizationsVO.getLogoPhoto())) {
						info.setLogoPhoto(fileUploadClient.getUrlUrl(organizationsVO.getLogoPhoto()));
					} else {
						if (!StringUtils.isBlank(alarmServer.getAddressPath())) {
							info.setLogoPhoto(fileServer.getUrlPrefix() + fileServer.getPhotoLogo() + "/index_" + alarmServer.getAddressPath() + ".png");
						} else {
							info.setLogoPhoto(fileServer.getUrlPrefix() + fileServer.getPhotoLogo() + "/index.png");
						}
					}
				}
			}else{
				if (!StringUtils.isBlank(alarmServer.getAddressPath())){
					info.setLogoPhoto(fileServer.getUrlPrefix()+fileServer.getPhotoLogo()+"/index_"+alarmServer.getAddressPath()+".png");
				}else{
					info.setLogoPhoto(fileServer.getUrlPrefix()+fileServer.getPhotoLogo()+"/index.png");
				}
			}
		}else{
			if (!StringUtils.isBlank(alarmServer.getAddressPath())){
				info.setLogoPhoto(fileServer.getUrlPrefix()+fileServer.getPhotoLogo()+"/index_"+alarmServer.getAddressPath()+".png");
			}else{
				info.setLogoPhoto(fileServer.getUrlPrefix()+fileServer.getPhotoLogo()+"/index.png");
			}
		}
		//设置token过期时间
		info.setExpiresIn(SecureUtil.getExpire());
		return R.data(info);
	}

	/**
	 * 设置默认岗位
	 */
	@GetMapping("/IsDefault")
	@ApiOperation(value = "默认岗位设置", notes = "默认岗位设置", position = 28)
	public R IsDefault(Integer userId,Integer postId) {
		Post post=new Post();
		post.setUserId(userId);
		post.setPostId(postId);
		boolean flag=postService.updateIsdefault(post);
		int code=201;
		Object obj=new Object();
		String msg;
		code=R.status(flag).getCode();
		obj=R.status(flag).getData();
		if(flag==true){
			msg="设置成功";
		}else{
			msg="设置失败";
		}
		return R.data(code,obj,msg);
	}

	@GetMapping("/getDeptSubTree")
	@ApiOperation(value = "获取机构子集", notes = "传deptId", position = 23)
	public R<List<DeptSubVO>> getDeptSubTree(@ApiParam(value = "deptId", required = true) Integer deptId) {
		List<DeptSubVO> list = deptService.getDeptSubTree(deptId);
		return R.data(list);
	}

	@GetMapping("/getDeptById")
	@ApiOperation(value = "获取省市县运管", notes = "传deptId", position = 24)
	public R<List<DeptSubVO>> getDeptById(@ApiParam(value = "deptId(市/县父级id)") @RequestParam Integer deptId,@ApiParam(value = "type(0省1市2县)", required = true) @RequestParam Integer type,
										  @ApiParam(value = "remark(0省市县1运管，默认0)") @RequestParam Integer remark) {
		List<DeptSubVO> list = deptService.getDeptById(deptId,type,remark);
		return R.data(list);
	}

	@GetMapping("/getByIdDeptList")
	@ApiOperation(value = "根据企业ID获取下级所有企业信息", notes = "传deptId", position = 25)
	public R<List<Dept>> getByIdDeptList(@ApiParam(value = "deptId") @RequestParam Integer deptId) {
		List<Dept> list = deptService.QiYeList(deptId);
		return R.data(list);
	}

}
