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

import cn.afterturn.easypoi.word.entity.WordImageEntity;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.qiyeshouye.entity.AnbiaoIllegalInfo;
import org.springblade.anbiao.qiyeshouye.entity.AnbiaoIllegalInfoRemark;
import org.springblade.anbiao.qiyeshouye.entity.AnbiaoIllegalInfoTJ;
import org.springblade.anbiao.qiyeshouye.page.AnbiaoIllegalInfoPage;
import org.springblade.anbiao.qiyeshouye.service.IAnbiaoIllegalInfoRemarkService;
import org.springblade.anbiao.qiyeshouye.service.IAnbiaoIllegalInfoService;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.CreateTable;
import org.springblade.common.tool.WordUtil2;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dict;
import org.springblade.system.feign.IDictClient;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;


/**
 * 控制器
 * @author hyp
 * @since 2022-04-24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/anbiaoIllegalInfo")
@Api(value = "企业端-违法违章", tags = "企业端-违法违章")
public class AnbiaoIllegalInfoController {

	private IAnbiaoIllegalInfoService anbiaoIllegalInfoService;

	private IFileUploadClient fileUploadClient;

	private IAnbiaoIllegalInfoRemarkService anbiaoIllegalInfoRemarkService;

	private FileServer fileServer;

	private IDictClient iDictClient;

	@PostMapping(value = "/getAnbiaoIllegalInfoAll")
	@ApiLog("企业端-违法违章列表")
	@ApiOperation(value = "企业端-违法违章列表", notes = "传入AnbiaoIllegalInfoPage",position = 1)
	public R<AnbiaoIllegalInfoPage<AnbiaoIllegalInfo>> getAnbiaoIllegalInfoAll(@RequestBody AnbiaoIllegalInfoPage anbiaoIllegalInfoPage, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		//排序条件
		////默认发起时间降序
		if(anbiaoIllegalInfoPage.getOrderColumns()==null){
			anbiaoIllegalInfoPage.setOrderColumn("date");
		}else{
			anbiaoIllegalInfoPage.setOrderColumn(anbiaoIllegalInfoPage.getOrderColumns());
		}
		AnbiaoIllegalInfoPage<AnbiaoIllegalInfo> pages = anbiaoIllegalInfoService.selectGetAll(anbiaoIllegalInfoPage);
		if(pages!= null){
			rs.setData(pages);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取成功");
		}else{
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取成功，暂无数据");
		}
		return rs;
	}

	@GetMapping("/detail")
	@ApiLog("企业端-违法违章详情")
	@ApiOperation(value = "企业端-违法违章详情", notes = "传入Id", position = 2)
	public R<AnbiaoIllegalInfo> detail(Integer Id) {
		R r = new R();
		AnbiaoIllegalInfo detail = anbiaoIllegalInfoService.getById(Id);
		//违法违章附件
		if(StrUtil.isNotEmpty(detail.getIllegalImg()) && detail.getIllegalImg().contains("http") == false){
			detail.setIllegalImg(fileUploadClient.getUrl(detail.getIllegalImg()));
		}
		//查询是否含有违法违规处理数据
		AnbiaoIllegalInfoRemark anbiaoIllegalInfoRemark = anbiaoIllegalInfoRemarkService.selectByIds(detail.getId());
		if(anbiaoIllegalInfoRemark != null){
			//违法违章处置附件
			if(StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getFujian()) && anbiaoIllegalInfoRemark.getFujian().contains("http") == false){
				anbiaoIllegalInfoRemark.setFujian(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getFujian()));
			}
			//违法违章告诫人签字附件
			if(StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getGaojierenqianzi()) && anbiaoIllegalInfoRemark.getGaojierenqianzi().contains("http") == false){
				anbiaoIllegalInfoRemark.setGaojierenqianzi(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getGaojierenqianzi()));
			}
			//违法违章被告诫人签字附件
			if(StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getBeigaojierenqianzi()) && anbiaoIllegalInfoRemark.getBeigaojierenqianzi().contains("http") == false){
				anbiaoIllegalInfoRemark.setBeigaojierenqianzi(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getBeigaojierenqianzi()));
			}
			//违法违章负责人签字附件
			if(StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getFuzerenqianzi()) && anbiaoIllegalInfoRemark.getFuzerenqianzi().contains("http") == false){
				anbiaoIllegalInfoRemark.setFuzerenqianzi(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getFuzerenqianzi()));
			}
			//违法违章当事人签字附件
			if(StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getDangshirenqianzi()) && anbiaoIllegalInfoRemark.getDangshirenqianzi().contains("http") == false){
				anbiaoIllegalInfoRemark.setDangshirenqianzi(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getDangshirenqianzi()));
			}
			//违法违章检讨书附件
			if(StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getJiantaoshu()) && anbiaoIllegalInfoRemark.getJiantaoshu().contains("http") == false){
				anbiaoIllegalInfoRemark.setJiantaoshu(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getJiantaoshu()));
			}
			//违法违章罚款附件
			if(StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getFakuanfujian()) && anbiaoIllegalInfoRemark.getFakuanfujian().contains("http") == false){
				anbiaoIllegalInfoRemark.setFakuanfujian(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getFakuanfujian()));
			}
			//违法违章12123截图附件
			if(StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getJietu()) && anbiaoIllegalInfoRemark.getJietu().contains("http") == false){
				anbiaoIllegalInfoRemark.setJietu(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getJietu()));
			}
			//违法违章抄告单附件
			if(StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getChaogaodan()) && anbiaoIllegalInfoRemark.getChaogaodan().contains("http") == false){
				anbiaoIllegalInfoRemark.setChaogaodan(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getChaogaodan()));
			}
			detail.setAnbiaoIllegalInfoRemark(anbiaoIllegalInfoRemark);
			r.setMsg("获取成功");
			r.setData(detail);
			r.setCode(200);
		}else{
			if(detail != null){
				r.setMsg("获取成功");
				r.setData(detail);
				r.setCode(200);
			}else{
				r.setMsg("获取成功,暂无数据");
				r.setCode(200);
			}
		}
		return r;
	}

	@PostMapping("/submit")
	@ApiLog("企业端-违法违章新增")
	@ApiOperation(value = "企业端-违法违章新增", notes = "传入AnbiaoIllegalInfo", position = 3)
	public R submit(@RequestBody AnbiaoIllegalInfo anbiaoIllegalInfo, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		anbiaoIllegalInfo.setCreateId(user.getUserId());
		if (StringUtils.isBlank(anbiaoIllegalInfo.getCreateTime())){
			anbiaoIllegalInfo.setCreateTime(DateUtil.now());
		}
		if (anbiaoIllegalInfo.getIsdelete() != 0){
			anbiaoIllegalInfo.setIsdelete(0);
		}
		boolean x = anbiaoIllegalInfoService.save(anbiaoIllegalInfo);
		if(x == true){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("新增违法违章成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("新增违法违章失败");
		}
		return rs;
	}

	@PostMapping("/update")
	@ApiLog("企业端-违法违章修改")
	@ApiOperation(value = "企业端-违法违章修改", notes = "传入AnbiaoIllegalInfo", position = 4)
	public R update(@RequestBody AnbiaoIllegalInfo anbiaoIllegalInfo, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		if (StringUtils.isBlank(anbiaoIllegalInfo.getUpdateTime())){
			anbiaoIllegalInfo.setUpdateTime(DateUtil.now());
		}
		anbiaoIllegalInfo.setUpdateId(user.getUserId());
		boolean x = anbiaoIllegalInfoService.updateById(anbiaoIllegalInfo);
		if(x == true){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("编辑违法违章成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("编辑违法违章失败");
		}
		return rs;
	}

	@GetMapping("/remove")
	@ApiLog("企业端-违法违章删除")
	@ApiOperation(value = "企业端-违法违章删除", notes = "传入数据ID", position = 5)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Id", value = "数据ID", required = true)
	})
	public R remove(@RequestParam Integer Id, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		String updateTime = DateUtil.now();
		AnbiaoIllegalInfo anbiaoIllegalInfo = new AnbiaoIllegalInfo();
		anbiaoIllegalInfo.setUpdateId(user.getUserId());
		anbiaoIllegalInfo.setUpdateTime(updateTime);
		anbiaoIllegalInfo.setIsdelete(1);
		anbiaoIllegalInfo.setId(Id);
		boolean i = anbiaoIllegalInfoService.updateById(anbiaoIllegalInfo);
		//查询是否含有违法违规处理数据
		AnbiaoIllegalInfoRemark anbiaoIllegalInfoRemark = anbiaoIllegalInfoRemarkService.selectByIds(anbiaoIllegalInfo.getId());
		if(anbiaoIllegalInfoRemark != null){
			anbiaoIllegalInfoRemark.setIllegalid(Id);
			anbiaoIllegalInfoRemark.setIsDeleted(1);
			anbiaoIllegalInfoRemark.setUpdateId(user.getUserId());
			anbiaoIllegalInfoRemark.setUpdateTime(updateTime);
			boolean o = anbiaoIllegalInfoRemarkService.updateSelective(anbiaoIllegalInfoRemark);
			if(i == true && o == true){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("删除违法违章成功");
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("删除违法违章失败");
			}
		}else{
			if(i == true){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("删除违法违章成功");
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("删除违法违章失败");
			}
		}
		return rs;
	}

	@PostMapping("/remarkSubmit")
	@ApiLog("企业端-违法违章处理")
	@ApiOperation(value = "企业端-违法违章处理", notes = "传入AnbiaoIllegalInfoRemark", position = 6)
	public R remarkSubmit(@RequestBody AnbiaoIllegalInfoRemark anbiaoIllegalInfoRemark, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		anbiaoIllegalInfoRemark.setCreateId(user.getUserId());
		if (StringUtils.isBlank(anbiaoIllegalInfoRemark.getCreatetime())){
			anbiaoIllegalInfoRemark.setCreatetime(DateUtil.now());
		}
		anbiaoIllegalInfoRemark.setIsDeleted(0);
		boolean x = anbiaoIllegalInfoRemarkService.save(anbiaoIllegalInfoRemark);
		AnbiaoIllegalInfo anbiaoIllegalInfo = new AnbiaoIllegalInfo();
		if (StringUtils.isBlank(anbiaoIllegalInfo.getUpdateTime())){
			anbiaoIllegalInfo.setUpdateTime(DateUtil.now());
		}
		anbiaoIllegalInfo.setUpdateId(user.getUserId());
		anbiaoIllegalInfo.setId(anbiaoIllegalInfoRemark.getIllegalid());
		anbiaoIllegalInfo.setStatus(1);
		boolean s = anbiaoIllegalInfoService.updateById(anbiaoIllegalInfo);
		if(x == true && s == true){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("违法违章处理成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("违法违章处理失败");
		}
		return rs;
	}

	@PostMapping(value = "/getAnbiaoIllegalInfoTJAll")
	@ApiLog("企业端-违章排查登记册")
	@ApiOperation(value = "企业端-违章排查登记册", notes = "传入AnbiaoIllegalInfoPage",position = 7)
	public R<AnbiaoIllegalInfoPage<AnbiaoIllegalInfoTJ>> getAnbiaoIllegalInfoTJAll(@RequestBody AnbiaoIllegalInfoPage anbiaoIllegalInfoPage, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		//排序条件
		////默认发起时间降序
		if(anbiaoIllegalInfoPage.getOrderColumns()==null){
			anbiaoIllegalInfoPage.setOrderColumn("date");
		}else{
			anbiaoIllegalInfoPage.setOrderColumn(anbiaoIllegalInfoPage.getOrderColumns());
		}
		AnbiaoIllegalInfoPage<AnbiaoIllegalInfoTJ> pages = anbiaoIllegalInfoService.selectGetAllTJ(anbiaoIllegalInfoPage);
		if(pages!= null){
			rs.setData(pages);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取成功");
		}else{
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取成功，暂无数据");
		}
		return rs;
	}

	@GetMapping("/getCreateTable")
	@ApiLog("安全台账-违章排查登记册生成")
	@ApiOperation(value = "安全台账-违章排查登记册生成", notes = "传入企业Id", position = 8)
	public String getCreateTable(Integer deptId,String beginTime,String endTime) throws IOException {
		AnbiaoIllegalInfoPage anbiaoIllegalInfoPage = new AnbiaoIllegalInfoPage();
		anbiaoIllegalInfoPage.setDeptId(deptId);
		anbiaoIllegalInfoPage.setBeginTime(beginTime);
		anbiaoIllegalInfoPage.setEndTime(endTime);
		//数据 "车牌颜色",
		String[] header = {"序号", "违章时间", "违章地点", "违章车辆", "违章驾驶员", "违章事由", "查处机关"};
		AnbiaoIllegalInfoPage<AnbiaoIllegalInfoTJ> page = anbiaoIllegalInfoService.selectGetAllTJ(anbiaoIllegalInfoPage);
		List<AnbiaoIllegalInfoTJ> list = page.getRecords();
		List<Dict> unlawfulActList = iDictClient.getDictByCode("unlawfulAct",null);
		List<Dict> dataSourcesList = iDictClient.getDictByCode("dataSources",null);
		list.forEach(item-> {
			unlawfulActList.forEach(dictVOListitem-> {
				boolean ss = dictVOListitem.getDictKey().equals(item.getUnlawfulAct().toString());
				if(ss == true){
					item.setUnlawfulActShow(dictVOListitem.getDictValue());
				}
			});
			dataSourcesList.forEach(dictVOListitem-> {
				boolean ss = dictVOListitem.getDictKey().equals(item.getDataSources().toString());
				if(ss == true){
					item.setDataSourcesShow(dictVOListitem.getDictValue());
				}
			});
		});
		int n = list.size();
		String[][] data=new String[n][7];
		for(int i=0;i<list.size();i++){
			AnbiaoIllegalInfoTJ swdao = list.get(i);
			int xuhao = i+1;
			data[i][0]= String.valueOf(xuhao);
			data[i][1]=swdao.getDate();
			data[i][2]=swdao.getAddress();
			data[i][3]=swdao.getCheliangpaizhao();
			data[i][4]=swdao.getJiashiyuanxingming();
			data[i][5]=swdao.getUnlawfulActShow();
			data[i][6]=swdao.getDataSourcesShow();
		}
		String deptName = list.get(0).getDeptName();
		String [] nyr=DateUtil.today().split("-");
		//附件存放地址(服务器生成地址)
		String filePath = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+deptName+"违章排查登记册.docx";
		//生成数据
		CreateTable.getCreateTable(header,data,filePath);
		//附件url地址
		String urlPath = fileServer.getPathPrefix()+ FilePathConstant.EnclosureUrl+nyr[0]+"\\"+nyr[1]+"\\"+deptName+"违章排查登记册.docx";
		return urlPath;
	}

	@GetMapping("/exportDataWord3")
	@ApiLog("安全台账-违章排查登记详情")
	@ApiOperation(value = "安全台账-违章排查登记详情", notes = "传入数据Id", position = 9)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "企业ID", required = true),
		@ApiImplicitParam(name = "Id", value = "数据ID", required = true)
	})
	public String exportDataWord3(HttpServletRequest request, HttpServletResponse response,Integer deptId,Integer Id) throws IOException{
		Map<String, Object> params = new HashMap<>();
		String temDir = "";
		String fileName = "";
		try {
			// TODO 渲染其他类型的数据请参考官方文档
			DecimalFormat df = new DecimalFormat("######0.00");
			Calendar now = Calendar.getInstance();

			//获取数据
			AnbiaoIllegalInfo detail = anbiaoIllegalInfoService.selectByIds(deptId,Id);
			//违法违章附件
			if(StrUtil.isNotEmpty(detail.getIllegalImg()) && detail.getIllegalImg().contains("http") == false){
				detail.setIllegalImg(fileUploadClient.getUrl(detail.getIllegalImg()));
			}
			//查询是否含有违法违规处理数据
			AnbiaoIllegalInfoRemark anbiaoIllegalInfoRemark = anbiaoIllegalInfoRemarkService.selectByIds(detail.getId());
			if(anbiaoIllegalInfoRemark != null) {
				//违法违章处置附件
				if (StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getFujian()) && anbiaoIllegalInfoRemark.getFujian().contains("http") == false) {
					anbiaoIllegalInfoRemark.setFujian(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getFujian()));
				}
				//违法违章告诫人签字附件
				if (StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getGaojierenqianzi()) && anbiaoIllegalInfoRemark.getGaojierenqianzi().contains("http") == false) {
					anbiaoIllegalInfoRemark.setGaojierenqianzi(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getGaojierenqianzi()));
				}
				//违法违章被告诫人签字附件
				if (StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getBeigaojierenqianzi()) && anbiaoIllegalInfoRemark.getBeigaojierenqianzi().contains("http") == false) {
					anbiaoIllegalInfoRemark.setBeigaojierenqianzi(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getBeigaojierenqianzi()));
				}
				//违法违章负责人签字附件
				if (StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getFuzerenqianzi()) && anbiaoIllegalInfoRemark.getFuzerenqianzi().contains("http") == false) {
					anbiaoIllegalInfoRemark.setFuzerenqianzi(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getFuzerenqianzi()));
				}
				//违法违章当事人签字附件
				if (StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getDangshirenqianzi()) && anbiaoIllegalInfoRemark.getDangshirenqianzi().contains("http") == false) {
					anbiaoIllegalInfoRemark.setDangshirenqianzi(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getDangshirenqianzi()));
				}
				//违法违章检讨书附件
				if (StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getJiantaoshu()) && anbiaoIllegalInfoRemark.getJiantaoshu().contains("http") == false) {
					anbiaoIllegalInfoRemark.setJiantaoshu(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getJiantaoshu()));
				}
				//违法违章罚款附件
				if (StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getFakuanfujian()) && anbiaoIllegalInfoRemark.getFakuanfujian().contains("http") == false) {
					anbiaoIllegalInfoRemark.setFakuanfujian(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getFakuanfujian()));
				}
				//违法违章12123截图附件
				if (StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getJietu()) && anbiaoIllegalInfoRemark.getJietu().contains("http") == false) {
					anbiaoIllegalInfoRemark.setJietu(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getJietu()));
				}
				//违法违章抄告单附件
				if (StrUtil.isNotEmpty(anbiaoIllegalInfoRemark.getChaogaodan()) && anbiaoIllegalInfoRemark.getChaogaodan().contains("http") == false) {
					anbiaoIllegalInfoRemark.setChaogaodan(fileUploadClient.getUrl(anbiaoIllegalInfoRemark.getChaogaodan()));
				}
				detail.setAnbiaoIllegalInfoRemark(anbiaoIllegalInfoRemark);

			}

			String basePath= ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/template/";
//			String templatePath =basePath+"muban1.docx";//word模板地址
			//word模板地址
			String templatePath =fileServer.getPathPrefix()+"muban\\"+"muban1.docx";

			// 渲染文本
			params.put("a1", detail.getAnbiaoIllegalInfoRemark().getYuetanshijian());
			params.put("a2", detail.getAnbiaoIllegalInfoRemark().getAddress());
			params.put("a3", detail.getAnbiaoIllegalInfoRemark().getJiluren());
			params.put("a4", detail.getAnbiaoIllegalInfoRemark().getGaojieren());
			params.put("a5", detail.getAnbiaoIllegalInfoRemark().getBeigaojieren());
			params.put("a6", detail.getAnbiaoIllegalInfoRemark().getCautionTalkReason());
			params.put("a7", detail.getAnbiaoIllegalInfoRemark().getCautionConversationContent());
			if(StringUtils.isNotBlank(detail.getAnbiaoIllegalInfoRemark().getGaojierenqianzi())){
				params.put("a8", detail.getAnbiaoIllegalInfoRemark().getGaojierenqianzi());
			}else{
				params.put("a8", "无");
			}
			if(StringUtils.isNotBlank(detail.getAnbiaoIllegalInfoRemark().getBeigaojierenqianzi())){
				params.put("a9", detail.getAnbiaoIllegalInfoRemark().getBeigaojierenqianzi());
			}else{
				params.put("a9", "无");
			}
			if(StringUtils.isNotBlank(detail.getAnbiaoIllegalInfoRemark().getYuetanbeizhu())){
				params.put("a10", detail.getAnbiaoIllegalInfoRemark().getYuetanbeizhu());
			}else{
				params.put("a10", "无");
			}

			params.put("b1", detail.getAnbiaoIllegalInfoRemark().getDengjishijian());
			if(StringUtils.isNotBlank(detail.getAnbiaoIllegalInfoRemark().getWeizhangshiyou())){
				params.put("b2", detail.getAnbiaoIllegalInfoRemark().getWeizhangshiyou());
			}else{
				params.put("b2", "无");
			}
			params.put("b3", detail.getAnbiaoIllegalInfoRemark().getWeizhangrenyuan());
			params.put("b4", detail.getCheliangpaizhao());
			params.put("b5", detail.getAnbiaoIllegalInfoRemark().getJiluren());
			params.put("b6", detail.getAnbiaoIllegalInfoRemark().getWeizhangjingguo());
			params.put("b7", detail.getAnbiaoIllegalInfoRemark().getChuliqingkuang());
			if(StringUtils.isNotBlank(detail.getAnbiaoIllegalInfoRemark().getFuzerenqianzi())){
				params.put("b8", detail.getAnbiaoIllegalInfoRemark().getFuzerenqianzi());
			}else{
				params.put("b8", "无");
			}
			if(StringUtils.isNotBlank(detail.getAnbiaoIllegalInfoRemark().getDangshirenqianzi())){
				params.put("b9", detail.getAnbiaoIllegalInfoRemark().getDangshirenqianzi());
			}else{
				params.put("b9", "无");
			}
			if(StringUtils.isNotBlank(detail.getAnbiaoIllegalInfoRemark().getBeizhu())){
				params.put("b11", detail.getAnbiaoIllegalInfoRemark().getBeizhu());
			}else{
				params.put("b11", "无");
			}

			// 渲染图片
			WordImageEntity image = new WordImageEntity();
			image.setHeight(240);
			image.setWidth(440);
			String jsonObject = detail.getAnbiaoIllegalInfoRemark().getFujian();
			if(StringUtils.isNotBlank(jsonObject)){
				JSONArray jsonList= JSONUtil.parseArray(jsonObject);
				System.out.println(jsonList);
				String url = "";
				for (int i = 0; i < jsonList.size(); i++) {
					JSONObject jsonObject1 = jsonList.getJSONObject(i);
					url = jsonObject1.get("url").toString();
				}
				//获得第一个点的位置
				int index = url.indexOf("/");
				System.out.println(index);
				//根据第一个点的位置 获得第二个点的位置
				index = url.indexOf("/", index+2);
				//根据第三个点的位置，截取 字符串。得到结果 result
				String result = url.substring(index);
				url = fileServer.getPathPrefix()+result;
				System.out.println(url);
				image.setUrl(url);
				image.setType(WordImageEntity.URL);
				params.put("b10", image);
			}else{
				params.put("b10", "无");
			}
			// TODO 渲染其他类型的数据请参考官方文档
			//=================生成文件保存在本地D盘某目录下=================
//			temDir = "D:/mimi/file/word/"; ;//生成临时文件存放地址
			String [] nyr=DateUtil.today().split("-");
			//附件存放地址(服务器生成地址)
			temDir = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+detail.getDeptName()+"\\";
			//生成文件名
			Long time = new Date().getTime();
			// 生成的word格式
			String formatSuffix = ".docx";
			String value = "";
			if(StringUtils.isNotBlank(detail.getUnlawfulAct().toString()) && !detail.getUnlawfulAct().equals("null")){
				boolean ss = false;
				List<Dict> dictVOList = iDictClient.getDictByCode("unlawfulAct",null);
				for(int i= 0;i<dictVOList.size();i++){
					ss = dictVOList.get(i).getDictKey().equals(detail.getUnlawfulAct().toString());
					value = dictVOList.get(i).getDictValue();
					if(ss == true){
						break;
					}
				}
				if(ss == true){
					dictVOList = iDictClient.getDictByCode("unlawfulAct",value);
					detail.setIllegalCode(dictVOList.get(0).getDictValue());
				}
			}
			String wjName = detail.getCheliangpaizhao()+"_"+detail.getDate()+"_"+detail.getIllegalCode()+"_"+"违法违章详情台账";
			// 拼接后的文件名
			fileName = wjName + formatSuffix;//文件名  带后缀
			//导出word
			WordUtil2.exportDataWord3(templatePath, temDir, fileName, params, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temDir+fileName;
	}

	@PostMapping("/updateStatus")
	@ApiLog("企业端-违法违章生成台账更新状态")
	@ApiOperation(value = "企业端-违法违章生成台账更新状态", notes = "传入AnbiaoIllegalInfo", position = 10)
	public R updateStatus(@RequestBody AnbiaoIllegalInfo anbiaoIllegalInfo, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		if (StringUtils.isBlank(anbiaoIllegalInfo.getUpdateTime())){
			anbiaoIllegalInfo.setUpdateTime(DateUtil.now());
		}
		anbiaoIllegalInfo.setUpdateId(user.getUserId());
		anbiaoIllegalInfo.setStatus(2);
		boolean x = anbiaoIllegalInfoService.updateById(anbiaoIllegalInfo);
		if(x == true){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("更新成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("更新失败");
		}
		return rs;
	}

//	public static void main(String[] args) {
//		String str ="http://125.64.5.2:8204/AttachFiles/2022/08/anbiao_vehicle/1660738840626.jpg";
//		//获得第一个点的位置
//		int index=str.indexOf("/");
//		System.out.println(index);
//		//根据第一个点的位置 获得第二个点的位置
//		index=str.indexOf("/", index+2);
//		//根据第三个点的位置，截取 字符串。得到结果 result
//		String result=str.substring(index);
//		//输出结果
//		System.out.println(result);
//	}


}
