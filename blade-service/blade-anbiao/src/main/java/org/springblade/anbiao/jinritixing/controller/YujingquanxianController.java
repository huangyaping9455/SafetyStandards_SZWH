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
package org.springblade.anbiao.jinritixing.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jinritixing.entity.Yujingquanxian;
import org.springblade.anbiao.jinritixing.page.YujingquanxianPage;
import org.springblade.anbiao.jinritixing.service.IYujingquanxianService;
import org.springblade.anbiao.jinritixing.vo.YujingquanxianVO;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("anbiao/yujingquanxian")
@Api(value = "预警权限", tags = "预警权限接口")
public class YujingquanxianController extends BladeController {

	private IYujingquanxianService yujingquanxianService;

	private ISysClient iSysClient;

	/**
	* 查询所有预警
	*/
	@PostMapping("/allyujing")
	@ApiLog("查询所有-预警权限")
	@ApiOperation(value = "查询所有-预警权限", notes = "查询所有预警", position = 1)
	public R<YujingquanxianPage<YujingquanxianVO>> allyujing(@RequestBody YujingquanxianPage yujingquanxianPage) {
		YujingquanxianPage<YujingquanxianVO> lists = yujingquanxianService.selectAllYuJing(yujingquanxianPage);
		return R.data(lists);
	}

	/**
	 * 查询已创建预警项绑定的企业及相关预警项
	 */
	@PostMapping("/selectYJDept")
	@ApiLog("查询所有已绑定预警项的企业")
	@ApiOperation(value = "查询所有已绑定预警项的企业", notes = "查询所有已绑定预警项的企业", position = 1)
	public R<YujingquanxianPage<YujingquanxianVO>> selectYJDept(@RequestBody YujingquanxianPage yujingquanxianPage) {
		YujingquanxianPage<YujingquanxianVO> lists = yujingquanxianService.selectYJDept(yujingquanxianPage);
		return R.data(lists);
	}

	/**
	 * 获取当前岗位所拥有权限
	 */
	@GetMapping("/yujingTreeKeys")
	@ApiLog("获取当前岗位-预警权限")
	@ApiOperation(value = "获取当前岗位-预警权限", notes = "传入岗位id", position = 2)
	@ApiImplicitParams({@ApiImplicitParam(name = "postId", value = "岗位id", required = true)})
	public R<List<String>> yujingTreeKeys(String postId) {
		YujingquanxianPage page=new YujingquanxianPage();
		page.setPostId(postId);
		List<YujingquanxianVO> list=yujingquanxianService.selectYuJingList(page);
		List<String> lists=new ArrayList<>();
		for (int i = 0; i <list.size() ; i++) {
			lists.add(list.get(i).getYujingxiangid());
		}
		return R.data(lists);
	}

	/**
	* 查询单位预警
	*/
	@PostMapping("/yujinglist")
	@ApiLog("查询单位-预警权限")
	@ApiOperation(value = "查询单位-预警权限", notes = "传入岗位id", position = 3)
	public R<List<YujingquanxianVO>> yujinglist(String postId) {
		YujingquanxianPage page=new YujingquanxianPage();
		page.setPostId(postId);
		return R.data(yujingquanxianService.selectYuJingList(page));
	}

	/**
	 * 删除单项预警
	 */
	@PostMapping("/delYuJingByXiangId")
	@ApiLog("查询单位-删除单项预警")
	@ApiOperation(value = "查询单位-删除单项预警", notes = "传入企业ID、岗位ID、预警项ID(请求方式form-data)", position = 3)
	public R delYuJingByXiangId(String deptId,String postId,String yujingxiangid) throws Exception{
		R rs = new R();
		YujingquanxianPage page=new YujingquanxianPage();
		page.setDeptId(Integer.parseInt(deptId));
		page.setPostId(postId);
		page.setYujingxiangid(yujingxiangid);
		try {
            boolean i = yujingquanxianService.delYuJingByXiangId(page);
            if(i == true){
                rs.setSuccess(true);
                rs.setCode(200);
                rs.setMsg("删除成功");
            }else{
                rs.setSuccess(false);
                rs.setCode(500);
                rs.setMsg("删除失败");
            }
        }catch (Exception e){
		    rs.setMsg(e.getMessage());
		    rs.setCode(500);
        }
		return rs;
	}


	/**
	* 保存
	*/
	@GetMapping("/submit")
	@ApiLog("保存-预警权限")
	@ApiOperation(value = "保存-预警权限", notes = "传入menuIds,postIds,user", position = 4)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "postIds", value = "岗位id", required = true),
		@ApiImplicitParam(name = "menuIds", value = "选中id值", required = true)
	})
	public R submit(String menuIds,String postIds,BladeUser user) {
		R rs = new R();
		YujingquanxianPage page = new YujingquanxianPage();
		boolean falg;
		//根据postIds字符串分别截取
		String[] postIds_idsss = postIds.split(",");
		//去除素组中重复的数组
		List<String> postIds_listid = new ArrayList<String>();
		for (int i=0; i<postIds_idsss.length; i++) {
			if(!postIds_listid.contains(postIds_idsss[i])) {
				postIds_listid.add(postIds_idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] postIds_idss= postIds_listid.toArray(new String[1]);
		if(postIds_idss.length > 0){
			for(int i=0; i<postIds_idss.length; i++){
				//直属上级id
				Dept dept=iSysClient.selectByJGBM("机构",postIds_idss[i]);
				page.setDeptId(dept.getId());
				page.setPostId(postIds_idss[i]);
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
				String[]  menuIds_idss= menuIds_listid.toArray(new String[1]);

				if(menuIds.length()>0){
					List<Yujingquanxian> list = new ArrayList<>();
					yujingquanxianService.delYuJing(page);
					for (int j = 0;j<menuIds_idss.length;j++){
						Yujingquanxian yujingquanxian = new Yujingquanxian();
						yujingquanxian.setYujingxiangid(menuIds_idss[j]);
						yujingquanxian.setDeptId(dept.getId());
						if(user == null){
							yujingquanxian.setCaozuoren("admin");
							yujingquanxian.setCaozuorenid(1);
						}else{
							yujingquanxian.setCaozuoren(user.getUserName());
							yujingquanxian.setCaozuorenid(user.getUserId());
						}
						yujingquanxian.setCaozuoshijian(DateUtil.now());
						yujingquanxian.setPostid(postIds_idss[i]);
						list.add(yujingquanxian);
						//yujingquanxianService.save(yujingquanxian);
						falg=yujingquanxianService.saveBatch(list);
						if(falg == true){
							rs.setMsg("添加成功");
							rs.setCode(200);
							rs.setSuccess(true);
						}else{
							rs.setMsg("添加失败");
							rs.setCode(500);
							rs.setSuccess(false);
						}
					}
				}else{
					rs.setMsg("预警项不能为空");
					rs.setCode(500);
					rs.setSuccess(false);
				}
			}
		}else{
			rs.setMsg("岗位不能为空");
			rs.setCode(500);
			rs.setSuccess(false);
		}
		return rs;
	}

	/**
	 * 预警结算
	 */
	@PostMapping("/yujingjiesuan")
	@ApiLog("预警结算-预警权限")
	@ApiOperation(value = "预警结算-预警权限", notes = "传入YujingquanxianPage", position = 5)
	public R yujingjiesuan(@RequestBody YujingquanxianPage page) {
		List<YujingquanxianVO> list = yujingquanxianService.selectYuJingList(page);
		if(list.size()>0){
			for (int i = 0;i<list.size();i++){
				YujingquanxianPage pages = new YujingquanxianPage();
				pages.setDeptId(page.getDeptId());
				pages.setYujingxiangid(list.get(i).getYujingxiangid());
				yujingquanxianService.yujingjiesuan(pages);
			}
		}
		return R.status(true);
	}
}
