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
package org.springblade.alarm.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.alarm.entity.BaojingTJMX;
import org.springblade.alarm.page.AlarmPage;
import org.springblade.alarm.page.BaojingTJPage;
import org.springblade.alarm.service.IBaojingtongjiService;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *  控制器
 * luo
 * @author Blade
 * @since 2019-07-25
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/alarm/alarmTongji")
@Api(value = "报警统计接口", tags = "报警统计接口")
public class BaojingtongjiController extends BladeController {

		private IBaojingtongjiService iBaojingtongjiService;

		private AlarmServer alarmServer;

		@PostMapping("ChosuList")
		@ApiLog("超速-统计")
		@ApiOperation(value = "超速-统计-分页", notes = "传入offlinePage", position = 5)
		public  R findAllChaosuPage(@RequestBody BaojingTJPage baojingTJPage){
			BaojingTJPage list=iBaojingtongjiService.selectAll(baojingTJPage);
			return  R.data(list);
		}
		@PostMapping("PilaoList")
		@ApiLog("疲劳-统计-分页")
		@ApiOperation(value = "疲劳-统计-分页", notes = "传入offlinePage", position = 5)
		public R findAllPilaoPage(@RequestBody BaojingTJPage baojingTJPage){
			BaojingTJPage list=iBaojingtongjiService.PilaoAll(baojingTJPage);
			return  R.data(list);

		}
		@PostMapping("alarmCountDay")
		@ApiLog("当日报警统计")
		@ApiOperation(value = "当日报警统计", notes = "传入company 企业名称", position = 5)
	   public R alarmCountDay(@ApiParam(value = "企业名称", required = true)  @RequestParam String company){

			return R.data(iBaojingtongjiService.alarmCount(company));

		}
	@PostMapping("BudingweiList")
	@ApiLog("不定位-统计")
	@ApiOperation(value = "不定位-统计-分页", notes = "BaojingTJPage", position = 5)
	public R budingwei(@RequestBody BaojingTJPage baojingTJPage){
		BaojingTJPage selectbudingwei = iBaojingtongjiService.selectbudingwei(baojingTJPage);
		return R.data(selectbudingwei);
	}
	@PostMapping("BuzaixianList")
	@ApiLog("不在线-统计")
	@ApiOperation(value = "不在线-统计-分页", notes = "BaojingTJPage", position = 5)
	public R buzaixian(@RequestBody BaojingTJPage baojingTJPage){
		BaojingTJPage selectbudingwei = iBaojingtongjiService.selectbuzaixian(baojingTJPage);

		return R.data(selectbudingwei);
	}

	@PostMapping("selectAlarmTJMXPage")
	@ApiLog("违规后续处理情况台账")
	@ApiOperation(value = "违规后续处理情况台账", notes = "传入alarmPage", position = 6)
	public R selectAlarmTJMXPage(@RequestBody AlarmPage alarmPage){
		AlarmPage list = iBaojingtongjiService.selectAlarmTJMXPage(alarmPage);
		return  R.data(list);
	}

	@ApiLog("违规后续处理情况台账--导出")
	@ApiOperation(value = "违规后续处理情况台账--导出", notes = "传入alarmPage", position = 7)
	@PostMapping(value="/goExport")
	public R goExport(@RequestBody AlarmPage alarmPage,HttpServletResponse response) throws IOException {
		R rs = new R();
		alarmPage.setSize(0);
		alarmPage.setCurrent(0);
		iBaojingtongjiService.selectAlarmTJMXPage(alarmPage);
		List<BaojingTJMX> baojingTJMXList = alarmPage.getRecords();

		String templateFile = alarmServer.getTemplateUrl()+"模板\\违规后续处理情况台账.xls";
		Map<String, Object> context = new HashMap<String, Object>();
		//Excel中的结果集ListData
		List<Map> ListData = new ArrayList<Map>();
		Map innerMap = new HashMap();
		if(baojingTJMXList.size()==0){
			innerMap = new HashMap();
			innerMap.put("cheliangpaizhao","");
			innerMap.put("alarmtype","");
			innerMap.put("wgtime","");
			innerMap.put("jiashiyuanxingming","");
			innerMap.put("chulixingshi","");
			innerMap.put("qianming","");
			innerMap.put("cutoffTime","");
			ListData.add(innerMap);
		}else if(baojingTJMXList.size()>30000){
			rs.setMsg("数据超过30000条无法下载");
			rs.setCode(500);
			return rs;
		}else{
			int index = 0;
			DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			for( int i = 0 ; i < baojingTJMXList.size() ; i++) {
				innerMap = new HashMap();
				BaojingTJMX t = baojingTJMXList.get(i);
				innerMap.put("xuhao",index);
				innerMap.put("cheliangpaizhao",t.getCheliangpaizhao());
				innerMap.put("alarmtype",t.getAlarmtype());
				if(t.getAlarmtype().equals("超速报警") || t.getAlarmtype().equals("疲劳驾驶报警") || t.getAlarmtype().equals("夜间行驶报警")
				|| t.getAlarmtype().equals("异常车辆报警") || t.getAlarmtype().equals("24小时不在线报警") || t.getAlarmtype().equals("高速禁行报警")){
					innerMap.put("wgtime",t.getBeginTime()+"-"+t.getEndTime());
				}else{
					innerMap.put("wgtime",t.getBeginTime());
				}
				innerMap.put("jiashiyuanxingming",t.getJiashiyuanxingming());
				innerMap.put("chulixingshi",t.getChulixingshi());
				innerMap.put("qianming",t.getQianming());
				innerMap.put("cutoffTime",dtf3.format(t.getCutoffTime()));
				ListData.add(innerMap);
				index ++;
			}
		}
		String title = alarmPage.getBeginTime()+"至"+alarmPage.getEndTime()+"湛江市"+baojingTJMXList.get(0).getDeptName()+"违规后续处理情况台账";
		// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
		// {} 代表普通变量 {.} 代表是list的变量
		// 这里模板 删除了list以后的数据，也就是统计的这一行
		String templateFileName = templateFile;
		Calendar cal = Calendar.getInstance();
		//日期
		int day = cal.get(Calendar.DATE);
		//月份
		int month = cal.get(Calendar.MONTH) + 1;
		//年份
		int year = cal.get(Calendar.YEAR);
		String fileName = alarmServer.getTemplateUrl()+title + ".xls";
		ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
		WriteSheet writeSheet = EasyExcel.writerSheet().build();
		// 写入list之前的数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		excelWriter.fill(map, writeSheet);

		// 直接写入数据
		excelWriter.fill(ListData, writeSheet);
		excelWriter.finish();

//		ExportExcel ee=new ExportExcel();
//		ServletWebRequest servletContainer = null;
//		String msg=ee.exportExcel(templateFile,fileName, context,servletContainer);

//		String fileName = "indexWrite" + System.currentTimeMillis() + ".xlsx";
		// 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//		EasyExcel.write(fileName, BaojingTJMX.class).sheet("模板").doWrite(ListData);

		rs.setData(fileName);
		rs.setMsg("下载成功");
		rs.setCode(200);
		return rs;
	}

	@ApiLog("违规后续处理情况台账--导出")
	@ApiOperation(value = "违规后续处理情况台账--导出", notes = "传入alarmPage", position = 7)
	@GetMapping(value="/goExport_Get")
	public R goExport_Get(AlarmPage alarmPage,HttpServletResponse response) throws IOException {
		R rs = new R();
		alarmPage.setSize(0);
		alarmPage.setCurrent(0);
		iBaojingtongjiService.selectAlarmTJMXPage(alarmPage);
		List<BaojingTJMX> baojingTJMXList = alarmPage.getRecords();

		String templateFile = alarmServer.getTemplateUrl()+"模板\\违规后续处理情况台账.xls";
		Map<String, Object> context = new HashMap<String, Object>();
		//Excel中的结果集ListData
		List<BaojingTJMX> ListData = new ArrayList<>();
		if(baojingTJMXList.size()==0){

		}else if(baojingTJMXList.size()>30000){
			rs.setMsg("数据超过30000条无法下载");
			rs.setCode(500);
			return rs;
		}else{
			int index = 0;
			DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			for( int i = 0 ; i < baojingTJMXList.size() ; i++) {
				BaojingTJMX t = baojingTJMXList.get(i);
				BaojingTJMX baojingTJMX = new BaojingTJMX();
				baojingTJMX.setXuhao(index);
				baojingTJMX.setCheliangpaizhao(t.getCheliangpaizhao());
				baojingTJMX.setAlarmtype(t.getAlarmtype());
				if(t.getAlarmtype().equals("超速报警") || t.getAlarmtype().equals("疲劳驾驶报警") || t.getAlarmtype().equals("夜间行驶报警")
						|| t.getAlarmtype().equals("异常车辆报警") || t.getAlarmtype().equals("24小时不在线报警") || t.getAlarmtype().equals("高速禁行报警")){
					baojingTJMX.setWgtime(t.getBeginTime()+"-"+t.getEndTime());
				}else{
					baojingTJMX.setWgtime(t.getBeginTime());
				}
				baojingTJMX.setJiashiyuanxingming(t.getJiashiyuanxingming());
				baojingTJMX.setChulixingshi(t.getChulixingshi());
				baojingTJMX.setQianming(t.getQianming());
				baojingTJMX.setCutoffTime(t.getCutoffTime());
				ListData.add(baojingTJMX);
				index ++;
			}
		}
		String title = alarmPage.getBeginTime()+"至"+alarmPage.getEndTime()+"湛江市"+baojingTJMXList.get(0).getDeptName()+"违规后续处理情况台账";
		// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
		// {} 代表普通变量 {.} 代表是list的变量
		// 这里模板 删除了list以后的数据，也就是统计的这一行
		String templateFileName = templateFile;
		Calendar cal = Calendar.getInstance();
		//日期
		int day = cal.get(Calendar.DATE);
		//月份
		int month = cal.get(Calendar.MONTH) + 1;
		//年份
		int year = cal.get(Calendar.YEAR);
		String fileName = alarmServer.getTemplateUrl()+title + ".xls";
		ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
		WriteSheet writeSheet = EasyExcel.writerSheet().build();
		// 写入list之前的数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		excelWriter.fill(map, writeSheet);

		// 直接写入数据
		excelWriter.fill(ListData, writeSheet);
		excelWriter.finish();

//		ExportExcel ee=new ExportExcel();
//		ServletWebRequest servletContainer = null;
//		String msg=ee.exportExcel(templateFile,fileName, context,servletContainer);

		ClassPathResource classPathResource = new ClassPathResource("templates/aaa.xls");
		InputStream inputStream = classPathResource.getInputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		// 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
		response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
		// 如果不用模板的方式导出的话，是doWrite
		EasyExcel.write(response.getOutputStream(),BaojingTJMX.class).withTemplate(inputStream).sheet("Sheet1").doFill(ListData);

		rs.setData(fileName);
		rs.setMsg("下载成功");
		rs.setCode(200);
		return rs;
	}

}
