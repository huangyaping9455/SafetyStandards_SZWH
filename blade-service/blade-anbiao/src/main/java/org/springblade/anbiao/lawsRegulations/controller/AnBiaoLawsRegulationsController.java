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
package org.springblade.anbiao.lawsRegulations.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.lawsRegulations.entity.AnbiaoLawsRegulations;
import org.springblade.anbiao.lawsRegulations.page.AnBiaoLawsRegulationsPage;
import org.springblade.anbiao.lawsRegulations.service.IAnbiaoLawsRegulationsService;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * 控制器
 * @author hyp
 * @since 2022-04-24
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/lawsRegulations")
@Api(value = "法律法规管理", tags = "法律法规管理")
public class AnBiaoLawsRegulationsController {

	private IAnbiaoLawsRegulationsService iAnbiaoLawsRegulationsService;

	private FileServer fileServer;

	@PostMapping(value = "/getNoticeAll")
	@ApiLog("法律法规管理--法律法规列表")
	@ApiOperation(value = "法律法规管理--法律法规列表", notes = "传入AnBiaoLawsRegulationsPage",position = 1)
	public R<AnBiaoLawsRegulationsPage<AnbiaoLawsRegulations>> getNoticeAll(@RequestBody AnBiaoLawsRegulationsPage anBiaoLawsRegulationsPage) {
		if(anBiaoLawsRegulationsPage.getOrderColumns()==null){
			anBiaoLawsRegulationsPage.setOrderColumn("releaseDate");
		}else{
			anBiaoLawsRegulationsPage.setOrderColumn(anBiaoLawsRegulationsPage.getOrderColumns());
		}
		AnBiaoLawsRegulationsPage<AnbiaoLawsRegulations> pages = iAnbiaoLawsRegulationsService.selectlawsRegulationsGetAll(anBiaoLawsRegulationsPage);
		return R.data(pages);
	}

	@GetMapping("/detail")
	@ApiLog("法律法规管理--法律法规详情")
	@ApiOperation(value = "法律法规管理--法律法规详情", notes = "传入Id", position = 2)
	public R<AnbiaoLawsRegulations> detail(Integer Id) {
		R r = new R();
		AnbiaoLawsRegulations detail = iAnbiaoLawsRegulationsService.selectlawsRegulationsById(Id,null);
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
	@ApiLog("法律法规管理--法律法规新增")
	@ApiOperation(value = "法律法规管理--法律法规新增", notes = "传入AnBiaoLawsRegulations", position = 2)
	public R submit(@RequestBody AnbiaoLawsRegulations anBiaoLawsRegulations, BladeUser user) throws UnsupportedEncodingException {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		String pathPdf = fileServer.getPathPrefix()+FilePathConstant.QYWJ_PDF_PATH;
		File dirs = new File(pathPdf);
		if(!dirs.exists()){
			dirs.mkdirs();
		}
		if (StringUtils.isBlank(anBiaoLawsRegulations.getCreateTime())){
			anBiaoLawsRegulations.setCreateTime(DateUtil.now());
		}
		anBiaoLawsRegulations.setCreateUser(user.getUserId());
		AnbiaoLawsRegulations deail = iAnbiaoLawsRegulationsService.selectlawsRegulationsById(null,anBiaoLawsRegulations.getName());
		if(deail == null){
			boolean x = iAnbiaoLawsRegulationsService.insertLawsRegulationsSelective(anBiaoLawsRegulations);
			if(x == true){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("新增成功");
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("新增失败");
			}
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("该文件名称已存在");
		}
		return rs;
	}

	@PostMapping("/update")
	@ApiLog("法律法规管理--法律法规修改")
	@ApiOperation(value = "法律法规管理--法律法规修改", notes = "传入AnBiaoLawsRegulations", position = 4)
	public R update(@RequestBody AnbiaoLawsRegulations anBiaoLawsRegulations, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		if (StringUtils.isBlank(anBiaoLawsRegulations.getUpdatetime())){
			anBiaoLawsRegulations.setUpdatetime(DateUtil.now());
		}
		anBiaoLawsRegulations.setUpdateuser(user.getUserId());
		anBiaoLawsRegulations.setIsdelete(0);
//		AnBiaoLawsRegulations deail = iAnbiaoLawsRegulationsService.selectlawsRegulationsById(null,anBiaoLawsRegulations.getName());
//		if(deail == null){
		boolean x = iAnbiaoLawsRegulationsService.updateLawsRegulationsSelective(anBiaoLawsRegulations);
		if(x == true){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("编辑法律法规成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("编辑法律法规失败");
		}
//		}else{
//			rs.setCode(500);
//			rs.setSuccess(false);
//			rs.setMsg("该法律法规已存在");
//		}
		return rs;
	}

	@GetMapping("/remove")
	@ApiLog("法律法规管理--法律法规删除")
	@ApiOperation(value = "法律法规管理--法律法规删除", notes = "传入数据ID、用户ID", position = 5)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Id", value = "数据ID", required = true),
		@ApiImplicitParam(name = "userId", value = "用户ID", required = true)
	})
	public R remove(@RequestParam Integer Id,Integer userId) {
		R rs = new R();
		String updateTime = DateUtil.now();
		AnbiaoLawsRegulations anBiaoLawsRegulations = new AnbiaoLawsRegulations();
		anBiaoLawsRegulations.setIsdelete(1);
		anBiaoLawsRegulations.setUpdatetime(updateTime);
		anBiaoLawsRegulations.setId(Id);
		anBiaoLawsRegulations.setUpdateuser(userId);
		boolean i = iAnbiaoLawsRegulationsService.updateLawsRegulationsSelective(anBiaoLawsRegulations);
		if(i == true){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("删除法律法规成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("删除法律法规失败");
		}
		return rs;
	}

	private String extractString(String s){
		for(int i = 0; i < 3; i++){
			s = s.substring(s.indexOf("/")+1 );
		}
		return s;
	}

}
