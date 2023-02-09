package org.springblade.anbiao.yinhuanpaicha.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.word.entity.WordImageEntity;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.data.ImageData;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tools.zip.ZipOutputStream;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanTJMX;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoHiddenDanger;
import org.springblade.anbiao.yinhuanpaicha.page.AnbiaoHiddenDangerPage;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoHiddenDangerService;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoHiddenDangerVO;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.ApacheZipUtils;
import org.springblade.common.tool.StringUtils;
import org.springblade.common.tool.WordUtil2;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.hutool.core.util.ImageUtil.toBufferedImage;

/**
 * <p>
 * 隐患排查信息 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/anbiao/hiddendanger")
@AllArgsConstructor
@Api(value = "隐患排查信息", tags = "隐患排查信息")
public class AnbiaoHiddenDangerController {

	private IAnbiaoHiddenDangerService service;

	private IFileUploadClient fileUploadClient;

	private AlarmServer alarmServer;

	private FileServer fileServer;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("隐患排查信息-新增、编辑")
	@ApiOperation(value = "隐患排查信息-新增", notes = "传入AnbiaoHiddenDanger", position = 1)
	public R insert(@RequestBody AnbiaoHiddenDanger danger, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoHiddenDanger> dangerQueryWrapper = new QueryWrapper<AnbiaoHiddenDanger>();
		dangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdDeptIds, danger.getAhdDeptIds());
		dangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdVehicleIds, danger.getAhdVehicleIds());
		dangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdType, danger.getAhdType());
		dangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdDelete, "0");
		dangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdDiscoveryTime, danger.getAhdDiscoveryTime());
		dangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdDescribe, danger.getAhdDescribe());
		AnbiaoHiddenDanger deail = service.getBaseMapper().selectOne(dangerQueryWrapper);
		if(deail == null){
			danger.setAhdDelete("0");
			if(user != null){
				danger.setAhdCreateByName(user.getUserName());
				danger.setAhdCreateByIds(user.getUserId().toString());
			}
			if (danger.getAhdAddress() == null){
				danger.setAhdAddress("车辆设备");
			}
			danger.setAhdCreateTime(DateUtil.now());
			boolean i = service.save(danger);
			if(i){
				r.setMsg("新增成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			}else{
				r.setMsg("新增失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}else{
			r.setMsg("该数据已存在");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	/**
	 * 编辑
	 */
	@PostMapping("/update")
	@ApiLog("隐患排查信息-编辑")
	@ApiOperation(value = "隐患排查信息-编辑", notes = "传入AnbiaoHiddenDanger", position = 1)
	public R update(@RequestBody AnbiaoHiddenDanger danger, BladeUser user) {
		R r = new R();
		if(user != null){
			danger.setAhdUpdateByName(user.getUserName());
			danger.setAhdUpdateByIds(user.getUserId().toString());
		}
		danger.setAhdUpdateTime(DateUtil.now());
		danger.setAhdIds(danger.getAhdIds());
		boolean i = service.updateById(danger);
		if(i){
			r.setMsg("编辑成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}else{
			r.setMsg("编辑失败");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	/**
	 * 编辑
	 */
	@GetMapping("/audit")
	@ApiLog("隐患排查信息-审核")
	@ApiOperation(value = "隐患排查信息-审核", notes = "传入数据Id、是否整改((0.否,1.是))", position = 1)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true),
		@ApiImplicitParam(name = "status", value = "是否整改((0.否,1.是)", required = true)
	})
	public R audit( String Id,Integer status, BladeUser user) {
		R r = new R();
		AnbiaoHiddenDanger danger = new AnbiaoHiddenDanger();
		if(user != null){
			danger.setAhdAuditName(user.getUserName());
			danger.setAhdAuditId(user.getUserId());
		}
		danger.setAhdAuditTime(DateUtil.now());
		danger.setAhdRectificationSituation(status.toString());
		danger.setAhdIds(Id);
		boolean i = service.updateById(danger);
		if(i){
			r.setMsg("审核成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}else{
			r.setMsg("审核失败");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	@PostMapping("/getHiddenDangerPage")
	@ApiLog("隐患排查信息-分页列表")
	@ApiOperation(value = "隐患排查信息-分页列表", notes = "传入AnbiaoHiddenDangerPage", position = 2)
	public R<AnbiaoHiddenDangerPage<AnbiaoHiddenDangerVO>> getHiddenDangerPage(@RequestBody AnbiaoHiddenDangerPage anbiaoHiddenDangerPage, BladeUser user) {
		R rs = new R();
		AnbiaoHiddenDangerPage<AnbiaoHiddenDangerVO> list= service.selectPage(anbiaoHiddenDangerPage);
		return R.data(list);
	}

	@GetMapping("/remove")
	@ApiOperation(value = "隐患排查信息-删除", notes = "传入Id", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true),
	})
	public R remove(String Id,BladeUser user) {
		R r = new R();
		AnbiaoHiddenDanger danger = new AnbiaoHiddenDanger();
		if(user != null) {
			danger.setAhdUpdateByName(user.getUserName());
			danger.setAhdUpdateByIds(user.getUserId().toString());
		}
		danger.setAhdUpdateTime(DateUtil.now());
		danger.setAhdDelete("1");
		danger.setAhdIds(Id);
		boolean i = service.updateById(danger);
		if(i){
			r.setMsg("删除成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}else{
			r.setMsg("删除失败");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	@GetMapping("/getById")
	@ApiOperation(value = "隐患排查信息-根据ID获取详情", notes = "传入Id", position = 4)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true)})
	public R getById(String Id) {
		AnbiaoHiddenDanger deail = service.getById(Id);
		if(deail != null) {
			//隐患附件(以英文分号分割)
			if (StrUtil.isNotEmpty(deail.getAhdHiddendangerEnclosure()) && deail.getAhdHiddendangerEnclosure().contains("http") == false) {
				deail.setAhdHiddendangerEnclosure(fileUploadClient.getUrl(deail.getAhdHiddendangerEnclosure()));
			}
			//整改附件(以英文分号分割)
			if (StrUtil.isNotEmpty(deail.getAhdRectificationEnclosure()) && deail.getAhdRectificationEnclosure().contains("http") == false) {
				deail.setAhdRectificationEnclosure(fileUploadClient.getUrl(deail.getAhdRectificationEnclosure()));
			}
		}
		return R.data(deail);
	}

	@GetMapping("/goExport_HiddenDanger_Excel")
	@ApiLog("隐患排查信息-导出")
	@ApiOperation(value = "隐患排查信息-导出", notes = "传入AnbiaoHiddenDangerPage", position = 22)
	public R goExport_HiddenDanger_Excel(HttpServletRequest request, HttpServletResponse response, String deptId , String date, BladeUser user) throws IOException {
		R rs = new R();
		List<String> urlList = new ArrayList<>();
		AnbiaoHiddenDangerPage anbiaoHiddenDangerPage = new AnbiaoHiddenDangerPage();
		anbiaoHiddenDangerPage.setDeptId(deptId);
		anbiaoHiddenDangerPage.setDate(date);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath =fileServer.getPathPrefix()+"muban\\"+"HiddenDanger.xlsx";

		String[] idsss = anbiaoHiddenDangerPage.getDeptId().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss= listid.toArray(new String[1]);
		for(int j = 0;j< idss.length;j++){
			anbiaoHiddenDangerPage.setDeptname("");
			anbiaoHiddenDangerPage.setSize(0);
			anbiaoHiddenDangerPage.setCurrent(0);
			anbiaoHiddenDangerPage.setDeptId(idss[j]);
			service.selectPage(anbiaoHiddenDangerPage);
			List<AnbiaoHiddenDangerVO> hiddenDangerVOList = anbiaoHiddenDangerPage.getRecords();
			//Excel中的结果集ListData
			List<AnbiaoHiddenDangerVO> ListData = new ArrayList<>();
			if(hiddenDangerVOList.size()==0){

			}else if(hiddenDangerVOList.size()>3000){
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			}else{
				for( int i = 0 ; i < hiddenDangerVOList.size() ; i++) {
					Map<String, Object> map = new HashMap<>();
					String templateFile = templatePath;
					// 渲染文本
					AnbiaoHiddenDangerVO t = hiddenDangerVOList.get(i);
					AnbiaoHiddenDangerVO hiddenDangerVO = new AnbiaoHiddenDangerVO();
					map.put("deptname", t.getDeptname());
					hiddenDangerVO.setCheliangpaizhao(t.getCheliangpaizhao());
					map.put("cheliangpaizhao", t.getCheliangpaizhao());
					if(t.getAhdType().equals("0")){
						hiddenDangerVO.setAhdType("人的不安全行为");
					}
					if(t.getAhdType().equals("1")){
						hiddenDangerVO.setAhdType("物的不安全状态");
					}
					if(t.getAhdType().equals("2")){
						hiddenDangerVO.setAhdType("管理上的缺陷");
					}
					if(t.getAhdType().equals("3")){
						hiddenDangerVO.setAhdType("环境因素");
					}
					map.put("ahdType", hiddenDangerVO.getAhdType());
					if(StringUtils.isEmpty(t.getAhdDescribe())){
						map.put("ahdDescribe","无");
					}else{
						map.put("ahdDescribe", t.getAhdDescribe());
					}
					map.put("ahdDriverName", t.getAhdDriverName());
					map.put("ahdDiscoveryTime", t.getAhdDiscoveryTime().substring(0,10));
					map.put("ahdDiscovererName", t.getAhdDiscovererName());

					if (StrUtil.isNotEmpty(t.getAhdHiddendangerEnclosure()) && t.getAhdHiddendangerEnclosure().contains("http") == false) {
						t.setAhdHiddendangerEnclosure(fileUploadClient.getUrl(t.getAhdHiddendangerEnclosure()));
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getAhdHiddendangerEnclosure()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("ahdHiddendangerEnclosure", t.getImgUrl());
					}else if(StrUtil.isNotEmpty(t.getAhdHiddendangerEnclosure())){
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getAhdHiddendangerEnclosure()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("ahdHiddendangerEnclosure", t.getImgUrl());
					}else{
						map.put("ahdHiddendangerEnclosure", "无");
					}
					// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
					// {} 代表普通变量 {.} 代表是list的变量
					// 这里模板 删除了list以后的数据，也就是统计的这一行
					String templateFileName = templateFile;
					//alarmServer.getTemplateUrl()+
					String fileName = "D:\\ExcelTest\\"+t.getDeptname()+"-隐患登记台账.xlsx";
					ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
					WriteSheet writeSheet = EasyExcel.writerSheet().build();
					// 写入list之前的数据
					excelWriter.fill(map, writeSheet);
					// 直接写入数据
					excelWriter.fill(ListData, writeSheet);
					excelWriter.finish();
					urlList.add(fileName);
				}
			}
		}
		String [] nyr=DateUtil.today().split("-");
		String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+"隐患登记台账.zip";
		ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream(fileName));
		ApacheZipUtils.doCompress1(urlList, bizOut);
		//不要忘记调用
		bizOut.close();

		rs.setMsg("下载成功");
		rs.setCode(200);
		rs.setData(fileName);
		rs.setSuccess(true);
		return rs;
	}

	@GetMapping("/goExport_HiddenDanger")
	@ApiLog("隐患排查信息-导出")
	@ApiOperation(value = "隐患排查信息-导出", notes = "传入AnbiaoHiddenDangerPage", position = 22)
	public R goExport_HiddenDanger(HttpServletRequest request, HttpServletResponse response, String deptId , String date, BladeUser user) throws IOException {
		R rs = new R();
		List<String> urlList = new ArrayList<>();
		AnbiaoHiddenDangerPage anbiaoHiddenDangerPage = new AnbiaoHiddenDangerPage();
		anbiaoHiddenDangerPage.setDeptId(deptId);
		anbiaoHiddenDangerPage.setDate(date);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath =fileServer.getPathPrefix()+"muban\\"+"HiddenDanger.docx";

		String[] idsss = anbiaoHiddenDangerPage.getDeptId().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss= listid.toArray(new String[1]);
		for(int j = 0;j< idss.length;j++){
			anbiaoHiddenDangerPage.setDeptname("");
			anbiaoHiddenDangerPage.setSize(0);
			anbiaoHiddenDangerPage.setCurrent(0);
			anbiaoHiddenDangerPage.setDeptId(idss[j]);
			service.selectPage(anbiaoHiddenDangerPage);
			List<AnbiaoHiddenDangerVO> hiddenDangerVOList = anbiaoHiddenDangerPage.getRecords();

			if(hiddenDangerVOList.size()==0){

			}else if(hiddenDangerVOList.size()>3000){
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			}else{
				for( int i = 0 ; i < hiddenDangerVOList.size() ; i++) {
					Map<String, Object> map = new HashMap<>();
					String temDir = "";
					String fileName = "";

					// 渲染文本
					AnbiaoHiddenDangerVO t = hiddenDangerVOList.get(i);
					AnbiaoHiddenDangerVO hiddenDangerVO = new AnbiaoHiddenDangerVO();
					map.put("deptname", t.getDeptname());
					hiddenDangerVO.setCheliangpaizhao(t.getCheliangpaizhao());
					map.put("cheliangpaizhao", t.getCheliangpaizhao());
					if(t.getAhdType().equals("0")){
						hiddenDangerVO.setAhdType("人的不安全行为");
					}
					if(t.getAhdType().equals("1")){
						hiddenDangerVO.setAhdType("物的不安全状态");
					}
					if(t.getAhdType().equals("2")){
						hiddenDangerVO.setAhdType("管理上的缺陷");
					}
					if(t.getAhdType().equals("3")){
						hiddenDangerVO.setAhdType("环境因素");
					}
					map.put("ahdType", hiddenDangerVO.getAhdType());
					if(StringUtils.isEmpty(t.getAhdDescribe())){
						map.put("ahdDescribe","无");
					}else{
						map.put("ahdDescribe", t.getAhdDescribe());
					}
					map.put("ahdDriverName", t.getAhdDriverName());
					map.put("ahdDiscoveryTime", t.getAhdDiscoveryTime());
					map.put("ahdDiscovererName", t.getAhdDiscovererName());

					//附件
					// 渲染图片
					WordImageEntity image = new WordImageEntity();
					image.setHeight(240);
					image.setWidth(440);
					if (StrUtil.isNotEmpty(t.getAhdHiddendangerEnclosure()) && t.getAhdHiddendangerEnclosure().contains("http") == false) {
						t.setAhdHiddendangerEnclosure(fileUploadClient.getUrl(t.getAhdHiddendangerEnclosure()));

						String jsonObject = t.getAhdHiddendangerEnclosure();
						if(org.apache.commons.lang.StringUtils.isNotBlank(jsonObject)){
							JSONArray jsonList= JSONUtil.parseArray(jsonObject);
							System.out.println(jsonList);
							String url = "";
							for (int p = 0; p < jsonList.size(); p++) {
								JSONObject jsonObject1 = jsonList.getJSONObject(p);
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
							map.put("ahdHiddendangerEnclosure", image);
						}else{
							map.put("ahdHiddendangerEnclosure", "无");
						}
					}else if(StrUtil.isNotEmpty(t.getAhdHiddendangerEnclosure())){
						image.setUrl(t.getAhdHiddendangerEnclosure());
						image.setType(WordImageEntity.URL);
						map.put("ahdHiddendangerEnclosure", image);
					}else{
						map.put("ahdHiddendangerEnclosure", "无");
					}

					// TODO 渲染其他类型的数据请参考官方文档
					//=================生成文件保存在本地D盘某目录下=================
					//			temDir = "D:/mimi/file/word/"; ;//生成临时文件存放地址
					String [] nyr=DateUtil.today().split("-");
					//附件存放地址(服务器生成地址)
					temDir = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+t.getDeptname()+"\\";
					//生成文件名
					// 生成的word格式
					String formatSuffix = ".docx";
					String wjName = t.getDeptname()+"_"+t.getAhdDiscoveryTime()+"_"+"隐患登记台账";
					// 拼接后的文件名
					fileName = wjName + formatSuffix;//文件名  带后缀
					//导出word
					WordUtil2.exportDataWord3(templatePath, temDir, fileName, map, request, response);
				}
			}
		}
		String fileName = alarmServer.getTemplateUrl()+date+"隐患登记台账.xlsx";
		ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream("D:\\ExcelTest\\文件夹34.zip"));
		ApacheZipUtils.doCompress1(urlList, bizOut);
		//不要忘记调用
		bizOut.close();

		rs.setMsg("下载成功");
		rs.setCode(200);
		rs.setSuccess(true);
		return rs;
	}


}
