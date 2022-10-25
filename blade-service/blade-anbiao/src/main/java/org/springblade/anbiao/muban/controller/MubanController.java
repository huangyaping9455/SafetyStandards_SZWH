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
package org.springblade.anbiao.muban.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.configure.entity.Configure;
import org.springblade.anbiao.configure.service.IConfigureService;
import org.springblade.anbiao.muban.entity.Muban;
import org.springblade.anbiao.muban.entity.MubanMap;
import org.springblade.anbiao.muban.service.IMubanService;
import org.springblade.anbiao.muban.vo.MubanVO;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *  控制器
 * @author 呵呵哒
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/muban")
@Api(value = "模板选择", tags = "模板选择")
public class MubanController extends BladeController {

	private IMubanService mubanService;

	private IConfigureService mapService;
	/**
	* 获取所有模板
	*/
	@GetMapping("/list")
	@ApiLog("获取所有-模板选择")
	@ApiOperation(value = "获取所有-模板选择", notes = "传入id", position = 2)
	public R<List<MubanVO>> list(Integer id) {
		List<MubanVO> list= mubanService.selectMubanPage(id);
		return R.data(list);
	}

	/**
	 * 初始化模板数据
	 */
	@GetMapping("/InsertMubanMap")
	@ApiLog("初始化模板-模板选择")
	@ApiOperation(value = "初始化模板-模板选择", notes = "传入tableName,deptId", position = 9)
	public R<List<MubanMap>> InsertMubanMap(String biaoming,String deptId) {
		//根据表名获取默认模板数据
		List<MubanMap> list=  mubanService.selectMapList(biaoming);
		//初始化是清除该表已存在数据
		mapService.delMapByDeptId(biaoming+"_map",deptId);
		for (int i = 0; i <list.size() ; i++) {
			Configure configure=new Configure();
			String biaodancanshu=list.get(i).getBiaodancanshu();
			JSONObject jsonObject = JSONUtil.parseObj(biaodancanshu);
			configure.setLabel(jsonObject.getStr("label"));
			configure.setShujubiaoziduan(jsonObject.getStr("prop"));
			configure.setDeptId(Integer.parseInt(deptId));
			configure.setTableName(biaoming+"_map");
			configure.setBiaodancanshu(biaodancanshu);
			mapService.insertMap(configure);
		}
		return R.data(list);
	}
	/**
	 * 一键初始化模板数据
	 */
	@GetMapping("/InsertAllMubanMap")
	@ApiLog("一键初始化模板-模板选择")
	@ApiOperation(value = "一键初始化模板-模板选择", notes = "deptId", position = 10)
	public R<List<MubanMap>> InsertAllMubanMap(String deptId){
		//获取所有表名
		List<MubanVO> list = mubanService.selectBiaoMing(1);
		List<MubanMap> listMaps = new ArrayList<MubanMap>();
		for(MubanVO mubanVO:list){
			//根据表名获取默认模板数据
			List<MubanMap> listMap = mubanService.selectMapList(mubanVO.getBiaoming());
			listMaps.addAll(listMap);
			//初始化是清除该表已存在数据
			mapService.delMapByDeptId(mubanVO.getBiaoming()+"_map",deptId);
			for (int i = 0; i <listMap.size() ; i++) {
				Configure configure=new Configure();
				String biaodancanshu=listMap.get(i).getBiaodancanshu();
				JSONObject jsonObject = JSONUtil.parseObj(biaodancanshu);
				configure.setLabel(jsonObject.getStr("label"));
				configure.setShujubiaoziduan(jsonObject.getStr("prop"));
				configure.setDeptId(Integer.parseInt(deptId));
				configure.setTableName(mubanVO.getBiaoming()+"_map");
				configure.setBiaodancanshu(biaodancanshu);
				mapService.insertMap(configure);
			}
		}
		return R.data(listMaps);
	}


	/**
	* 新增
	*/
	@PostMapping("/insert")
	@ApiLog("新增-模板选择")
	@ApiOperation(value = "新增-模板选择", notes = "传入muban", position = 4)
	public R<Muban> save(@Valid @RequestBody Muban muban) {
		Muban obj=new Muban();
		String msg;
		int i=mubanService.CountToken(muban.getToken());
		int j=mubanService.CountMuban(muban.getMuban());
		if(i>0){
			msg="系统已存在相同令牌，请重新输入";
		}else if(j>1){
			msg="系统已存在相同名称模板，请重新输入";
		}else{
			boolean flag=mubanService.save(muban);
			if(flag==true){
				obj=mubanService.selectByName(muban.getMuban());
			}
			msg="操作成功";
		}
		return R.data(obj,msg);
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
	@ApiLog("修改-模板选择")
	@ApiOperation(value = "修改-模板选择", notes = "传入muban", position = 5)
	public R<Muban> update(@Valid @RequestBody Muban muban) {
		muban.setIs_deleted(0);
		Muban obj=new Muban();
		String msg;
		int i=mubanService.CountToken(muban.getToken());
		int j=mubanService.CountMuban(muban.getMuban());
		if(i>1){
			msg="系统已存在相同令牌，请重新输入";
		}else if(j>1){
			msg="系统已存在相同名称模板，请重新输入";
		}else{
			boolean flag=mubanService.saveOrUpdate(muban);
			if(flag==true){
				obj=mubanService.selectByName(muban.getMuban());
			}
			msg="操作成功";
		}
		return R.data(obj,msg);
	}

	/**
	* 删除
	*/
	@PostMapping("/del")
	@ApiLog("删除-模板选择")
	@ApiOperation(value = "删除-模板选择", notes = "传入ids", position = 7)
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(mubanService.removeByIds(Func.toIntList(ids)));
	}

	/**
	 * 根据token获取数据
	 */
	@PostMapping("/selectByToken")
	@ApiLog("根据token-模板选择-模板选择")
	@ApiOperation(value = "根据token-模板选择-模板选择", notes = "传入token", position = 8)
	public R<Muban> selectByToken(@ApiParam(value = "token", required = true) @RequestParam String token) {
		return R.data(mubanService.selectByToken(token));
	}


}
