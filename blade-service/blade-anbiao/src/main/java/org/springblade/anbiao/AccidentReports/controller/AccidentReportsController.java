package org.springblade.anbiao.AccidentReports.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.tools.zip.ZipOutputStream;
import org.springblade.anbiao.AccidentReports.DTO.AccidentReportsDTO;
import org.springblade.anbiao.AccidentReports.VO.AccidentLedgerReportsVO;
import org.springblade.anbiao.AccidentReports.VO.AccidentReportsVO;
import org.springblade.anbiao.AccidentReports.page.AccidentLedgerReportsPage;
import org.springblade.anbiao.AccidentReports.page.AccidentPage;
import org.springblade.anbiao.AccidentReports.service.AccidentReportsService;
import org.springblade.anbiao.SafeInvestment.VO.SafelInfoledgerVO;
import org.springblade.anbiao.yinhuanpaicha.page.AnbiaoHiddenDangerPage;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoHiddenDangerVO;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.ApacheZipUtils;
import org.springblade.common.tool.ExcelUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/2 14:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/Accident/")
@Api(value = "事故信息", tags = "事故信息")
public class AccidentReportsController {

	@Autowired
	private AccidentReportsService  service;
	private AlarmServer alarmServer;
	private FileServer fileServer;
	private IFileUploadClient fileUploadClient;

	@PostMapping("list")
	@ApiLog("列表-事故")
	@ApiOperation(value = "分页事故信息", notes = "传入AccidentPage", position = 1)
	public R saList(@RequestBody AccidentPage accidentPage){
		return R.data( service.selectList(accidentPage));
	}


	@PostMapping("all")
	@ApiLog("列表-事故详细信息")
	@ApiOperation(value = "事故详细信息列表", notes = "传入id", position = 2)
	public R selectALL(@RequestBody AccidentPage accidentPage){
		AccidentReportsVO incidentHandlingEntities = service.selectAll(accidentPage);
		return R.data(incidentHandlingEntities);
	}

	@PostMapping("insert")
	@ApiLog("新增-事故详细信息")
	@ApiOperation(value = "新增事故详细信息", notes = "传入AccidentReportsDTO", position = 3)
	public R insert(@RequestBody AccidentReportsDTO accidentReportsDTO){
		String replace = UUID.randomUUID().toString().replace("-", "");
		accidentReportsDTO.setId(replace);
		return R.status(service.insertOne(accidentReportsDTO));
	}

	@PostMapping("delete")
	@ApiLog("删除-事故详细信息")
	@ApiOperation(value = "删除事故详细信息", notes = "传入id", position = 4)
	public R delete(@RequestBody AccidentPage accidentPage){
		return R.status( service.deleteAccident(accidentPage));
	}

	@PostMapping("update")
	@ApiLog("修改-事故详细信息")
	@ApiOperation(value = "修改事故详细信息", notes = "传入AccidentReportsDTO", position = 5)
	public R update(@RequestBody AccidentReportsDTO accidentReportsDTO ){
		return R.status(service.updateAccident(accidentReportsDTO));
	}

	@GetMapping("ledgerlist")
	@ApiLog("分页 列表-事故台账")
	@ApiOperation(value = "安全投入台账分页", notes = "传入accidentLedgerReportsVO", position = 1)
	public R<IPage<AccidentLedgerReportsVO>> ledgerList(AccidentLedgerReportsVO accidentLedgerReportsVO, Query query) {
		IPage<AccidentLedgerReportsVO> pages = service.selectLedgerList(Condition.getPage(query), accidentLedgerReportsVO);
		return R.data(pages);
	}

	@PostMapping("/getledgerPage")
	@ApiLog("分页 列表-事故台账")
	@ApiOperation(value = "分页 列表-事故台账", notes = "传入AccidentLedgerReportsPage", position = 2)
	public R getLedgerPage(@RequestBody AccidentLedgerReportsPage accidentLedgerReportsPage, BladeUser us) {
		R rs = new R();
		AccidentLedgerReportsPage list = service.selectLedgerList(accidentLedgerReportsPage);
		return R.data(list);
	}

	@GetMapping("/goExport_Excel")
	@ApiLog("事故信息-导出")
	@ApiOperation(value = "事故信息-导出", notes = "传入AccidentLedgerReportsPage", position = 22)
	public R goExport_HiddenDanger_Excel(HttpServletRequest request, HttpServletResponse response, String id, String date, String deptId, BladeUser user) throws IOException {
		int a=1;
		R rs = new R();
		List<String> urlList = new ArrayList<>();
		AccidentLedgerReportsPage accidentLedgerReportsPage = new AccidentLedgerReportsPage();
		if (StringUtils.isBlank(id) || !id.equals("null")){
			id="077aa074239c46e7b48a38b20b279c94,091e76243137422497acfa4b889606e2,244b3e09a06d45b791e77154e7e34314,29f1026a1f474864b1364770d2deab37,2a5d943f2ae54d85ae372c3a83e6e36a,33c70bb381f24fc29e7c66bdf8f09c05,34dc223d98a94d2bb209ed704b007cdf,3bfbd30e9d58411f86dede8aa0d6c4db,45f67bbd2b124126aca2387dc9047518,4ade247fa27a4e19a95a95c0356512ce,512523246d0649608cdf94536dcee8d5,52c151a382df4a909a71eb8a7f2c61ba,82421ee6a154484885c054628ccbd952,8ff2f6618c904175b40f2d4daa08e8bb,9242f1f77a6c4b3a8d50605d8e196cd9,a492901fbd2b48568347043aa93868aa,b000af9ee7e84d1c9697fbc17b62ca01,b1d59a923faa4d68b1a2cc0bbbbef04c,b47303d15caf49de821f5394cfd74b90,c4983cf066a244bca7ef8924e5aa26f7,d3d9b7d27e3140eaaff94632a8a18916,d990235001204eb79ef8c4ea7e69ce6a,e1a907c1edad498a8f027c4f9a15fb27,e3aa57439ad841adb7eacb8e07a854a3,e5ba5e7f8290401e90ccdc1b2f869af4,f6e238ca73564e23a1d0dd157c187597,f79a59b9fe6d4ea9928532a1ded5ce33,fb9b783576974548a54d5e7c05002433";
		}

		accidentLedgerReportsPage.setAccidentId(id);
//		accidentLedgerReportsPage.setDate(date);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath =fileServer.getPathPrefix()+"muban\\"+"AccidentReports.xlsx";
		String [] nyr= DateUtil.today().split("-");
		String[] idsss = accidentLedgerReportsPage.getAccidentId().split(",");
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
			accidentLedgerReportsPage.setDeptName("");
			accidentLedgerReportsPage.setSize(0);
			accidentLedgerReportsPage.setCurrent(0);
			accidentLedgerReportsPage.setAccidentId(idss[j]);
			service.selectLedgerList(accidentLedgerReportsPage);
			List<AccidentLedgerReportsVO> accidentLedgerReportsVOS = accidentLedgerReportsPage.getRecords();
			//Excel中的结果集ListData
			List<AccidentLedgerReportsVO> ListData = new ArrayList<>();
			if(accidentLedgerReportsVOS.size()==0){

			}else if(accidentLedgerReportsVOS.size()>3000){
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			}else{
				for( int i = 0 ; i < accidentLedgerReportsVOS.size() ; i++) {
					Map<String, Object> map = new HashMap<>();
					String templateFile = templatePath;
					// 渲染文本
					AccidentLedgerReportsVO t = accidentLedgerReportsVOS.get(i);
					AccidentLedgerReportsVO accidentLedgerReportsVO = new AccidentLedgerReportsVO();
					map.put("deptName", t.getDeptName());
					map.put("shigufashengshijian", t.getShigufashengshijian());
					map.put("shigufashengdidian", t.getShigufashengdidian());
					map.put("shigufenlei", t.getShigufenlei());
					map.put("shiguxingzhi", t.getShiguxingzhi());
					map.put("shiguzeren", t.getShiguzeren());
					accidentLedgerReportsVO.setChepaihao(t.getChepaihao());
					map.put("chepaihao", t.getChepaihao());
					map.put("jiashiyuan", t.getJiashiyuan());
					map.put("shigugaikuang", t.getShigugaikuang());
					map.put("caichansunshi", t.getCaichansunshi());
					map.put("jianjiejingjisunshi", t.getJianjiejingjisunshi());

					if (StrUtil.isNotEmpty(t.getShiguzhaopian1()) && t.getShiguzhaopian1().contains("http") == false) {
						t.setShiguzhaopian1(fileUploadClient.getUrl(t.getShiguzhaopian1()));
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getShiguzhaopian1()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("shiguzhaopian1", t.getImgUrl());
					}else if(StrUtil.isNotEmpty(t.getShiguzhaopian1())){
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getShiguzhaopian1()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("shiguzhaopian1", t.getImgUrl());
					}else{
						map.put("shiguzhaopian1", "无");
					}
					if (StrUtil.isNotEmpty(t.getShiguzhaopian2()) && t.getShiguzhaopian2().contains("http") == false) {
						t.setShiguzhaopian2(fileUploadClient.getUrl(t.getShiguzhaopian2()));
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getShiguzhaopian2()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("shiguzhaopian2", t.getImgUrl());
					}else if(StrUtil.isNotEmpty(t.getShiguzhaopian2())){
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getShiguzhaopian2()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("shiguzhaopian2", t.getImgUrl());
					}else{
						map.put("shiguzhaopian2", "无");
					}
					if (StrUtil.isNotEmpty(t.getShiguzhaopian3()) && t.getShiguzhaopian3().contains("http") == false) {
						t.setShiguzhaopian3(fileUploadClient.getUrl(t.getShiguzhaopian3()));
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getShiguzhaopian3()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("shiguzhaopian3", t.getImgUrl());
					}else if(StrUtil.isNotEmpty(t.getShiguzhaopian3())){
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getShiguzhaopian3()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("shiguzhaopian3", t.getImgUrl());
					}else{
						map.put("shiguzhaopian3", "无");
					}
					// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
					// {} 代表普通变量 {.} 代表是list的变量
					// 这里模板 删除了list以后的数据，也就是统计的这一行
					String templateFileName = templateFile;
					//alarmServer.getTemplateUrl()+
//					String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2]+"/"+t.getDeptName()+"-事故报告台账.xlsx";

					String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2];
					File newFile = new File(fileName);
						//判断目标文件所在目录是否存在
					if(!newFile.exists()){
						//如果目标文件所在的目录不存在，则创建父目录
						newFile.mkdirs();
					}
					fileName = fileName+"/"+t.getDeptName()+"-"+a+"-事故报告台账.xlsx";


					ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
					WriteSheet writeSheet = EasyExcel.writerSheet().build();
					// 写入list之前的数据
					excelWriter.fill(map, writeSheet);
					// 直接写入数据
					excelWriter.fill(ListData, writeSheet);
					excelWriter.finish();
					urlList.add(fileName);
					a++;
				}
			}
		}
		String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+"事故报告台账.zip";
		ExcelUtils.deleteFile(fileName);
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
}
