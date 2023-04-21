package org.springblade.anbiao.jiashiyuan.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.jiashiyuan.entity.DeptVehIntoArea;
import org.springblade.anbiao.jiashiyuan.entity.EpidemicPreventionAndControl;
import org.springblade.anbiao.jiashiyuan.entity.IntoArea;
import org.springblade.anbiao.jiashiyuan.page.EpidemicPreventionAndControlPage;
import org.springblade.anbiao.jiashiyuan.page.IntoAreaPage;
import org.springblade.anbiao.jiashiyuan.service.IEpidemicPreventionAndControlService;
import org.springblade.common.tool.GeneralBasic;
import org.springblade.common.tool.JSONUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * Created by you on 2022/4/06.
 */
@Slf4j
@RestController
@RequestMapping("/anbiao/epidemicPreventionAndControl")
@AllArgsConstructor
@Api(value = "疫情防控管理", tags = "疫情防控管理")
public class EpidemicPreventionAndControlController {

	private IEpidemicPreventionAndControlService iEpidemicPreventionAndControlService;

	private IFileUploadClient fileUploadClient;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-司机端疫情防控信息")
	@ApiOperation(value = "新增-司机端疫情防控信息", notes = "传入epidemicPreventionAndControl", position = 1)
	public R insert(@RequestBody EpidemicPreventionAndControl epidemicPreventionAndControl, BladeUser user) throws UnsupportedEncodingException {
		R r = new R();
//		if(user == null){
//			r.setMsg("未授权");
//			r.setCode(500);
//			return r;
//		}
		if(StringUtils.isNotBlank(epidemicPreventionAndControl.getTravelitineraryimg()) && epidemicPreventionAndControl.getTravelitineraryimg() != null){
			String msg = "";
			String msg1 = "";
			//行程卡
			String pathUrl = epidemicPreventionAndControl.getTravelitineraryimg();
			String url = fileUploadClient.getUrl(pathUrl);
			if(url != null){
				JsonNode urlres = JSONUtils.string2JsonNode(url);
				Iterator<JsonNode> urlelements = urlres.elements();
				while(urlelements.hasNext()) {
					JsonNode node = urlelements.next();
					String words = node.get("url").asText();
					System.out.println(words);
					pathUrl = words;
				}
			}
			String ss = GeneralBasic.generalBasic(pathUrl);
			if(ss != null){
				JsonNode res = JSONUtils.string2JsonNode(ss);
				log.info(ss);
				JsonNode lists = res.get("words_result");
				JsonNode words_result_num = res.get("words_result_num");
				if("0".equals(words_result_num.asText())){
					r.setMsg("请上传正确通信大数据行程卡");
					r.setCode(500);
					return r;
				}else{
					log.info("通信大数据行程卡");
					log.info(lists.toString());
					Iterator<JsonNode> elements = lists.elements();
					String remark = "";
					while (elements.hasNext()) {
						JsonNode node = elements.next();
						String words = node.get("words").asText();
						if (words.contains("行程卡") || words.contains("通信行程卡") || words.contains("绿色行程卡")) {
							msg1 = "通信大数据行程卡";
						} else {
							msg = "debug";
						}
						if (words.contains("途经：")) {
							remark += node.get("words").asText();
							String str1 = remark.substring(0, remark.indexOf("："));
							String str2 = remark.substring(str1.length() + 1, remark.length());
							epidemicPreventionAndControl.setAfterregion(str2);
						}
						if (words.contains("（注：*")) {
							if (words.contains("（注：*")) {
								remark += node.get("words").asText();
								remark = remark.substring(0, remark.indexOf("（"));
							}
							System.out.println(remark);
							System.out.println("最近14天经过风险区域");
							String str1 = remark.substring(0, remark.indexOf("："));
							String str2 = remark.substring(str1.length() + 1, remark.length());
							epidemicPreventionAndControl.setRiskyarea(str2);
						}
						if (words.contains("绿色行程卡")) {
							System.out.println("无风险");
							epidemicPreventionAndControl.setIsriskyarea(0);
							break;
						}
						if (words.contains("黄色行程卡")) {
							System.out.println("中风险");
							epidemicPreventionAndControl.setIsriskyarea(1);
							break;
						}
						if (words.contains("红色行程卡")) {
							System.out.println("高风险");
							epidemicPreventionAndControl.setIsriskyarea(1);
							break;
						}
					}

					if (msg.equals("debug") && msg1.equals("")) {
						r.setMsg("请上传正确通信大数据行程卡");
						r.setCode(500);
						return r;
					}
				}
			}else{
				r.setMsg("请上传正确通信大数据行程卡");
				r.setCode(500);
				return r;
			}
		}

		if(StringUtils.isNotBlank(epidemicPreventionAndControl.getHealthcodeimg()) && epidemicPreventionAndControl.getHealthcodeimg() != null){
			String msg = "";
			String msg1 = "";
			//健康码
			String pathUrl = epidemicPreventionAndControl.getHealthcodeimg();
			String url = fileUploadClient.getUrl(pathUrl);
			if(url != null){
				JsonNode urlres = JSONUtils.string2JsonNode(url);
				Iterator<JsonNode> urlelements = urlres.elements();
				while(urlelements.hasNext()) {
					JsonNode node = urlelements.next();
					String words = node.get("url").asText();
					System.out.println(words);
					pathUrl = words;
				}
			}
			String ss = GeneralBasic.generalBasic(pathUrl);
			if(ss != null) {
				System.out.println(ss);
				JsonNode res = JSONUtils.string2JsonNode(ss);
				JsonNode lists = res.get("words_result");
				JsonNode words_result_num = res.get("words_result_num");
				if("0".equals(words_result_num.asText())){
					r.setMsg("请上传正确健康码");
					r.setCode(500);
					return r;
				}else {
					Iterator<JsonNode> elements = lists.elements();
					String remark = "";
					while (elements.hasNext()) {
						JsonNode node = elements.next();
						String words = node.get("words").asText();
						if (words.contains("码")) {
							msg1 = "码";
						} else {
							msg = "debug";
						}
						if (words.contains("绿码")) {
							remark = node.get("words").asText();
							epidemicPreventionAndControl.setHealthcodestatus(0);
							break;
						}
						if (words.contains("黄码")) {
							remark = node.get("words").asText();
							epidemicPreventionAndControl.setHealthcodestatus(1);
							break;
						}
						if (words.contains("红码")) {
							remark = node.get("words").asText();
							epidemicPreventionAndControl.setHealthcodestatus(2);
							break;
						}
					}
					if (msg.equals("debug") && msg1.equals("")) {
						r.setMsg("请上传正确健康码");
						r.setCode(500);
						return r;
					}
				}
			}else{
				r.setMsg("请上传正确健康码");
				r.setCode(500);
				return r;
			}
		}
		epidemicPreventionAndControl.setCreatetime(DateUtil.now());
		if(StringUtils.isNotBlank(epidemicPreventionAndControl.getAfterregion()) && !epidemicPreventionAndControl.getAfterregion().equals("null")){
			epidemicPreventionAndControl.setAfterregion(epidemicPreventionAndControl.getAfterregion());
		}
		if(StringUtils.isNotBlank(epidemicPreventionAndControl.getRiskyarea()) && !epidemicPreventionAndControl.getRiskyarea().equals("null")){
			epidemicPreventionAndControl.setRiskyarea(epidemicPreventionAndControl.getRiskyarea());
		}
		epidemicPreventionAndControl.setUpdateuser(epidemicPreventionAndControl.getJiashiyuanid());
		EpidemicPreventionAndControl detal = iEpidemicPreventionAndControlService.selectById(epidemicPreventionAndControl.getJiashiyuanid(), epidemicPreventionAndControl.getNattime());
		boolean i;
		if(detal == null){
			i = iEpidemicPreventionAndControlService.insertSelective(epidemicPreventionAndControl);
		}else{
			i = iEpidemicPreventionAndControlService.updateSelective(epidemicPreventionAndControl);
		}
		if(i){
			r.setMsg("保存成功");
			r.setCode(200);
		}else{
			r.setMsg("保存失败");
			r.setCode(500);
		}
		return r;
	}

	/**
	 * 编辑
	 */
	@PostMapping("/update")
	@ApiLog("编辑-司机端疫情防控信息")
	@ApiOperation(value = "编辑-司机端疫情防控信息", notes = "编辑epidemicPreventionAndControl", position = 2)
	public R update(@RequestBody EpidemicPreventionAndControl epidemicPreventionAndControl, BladeUser user) {
		R r = new R();
//		if(user == null){
//			r.setMsg("未授权");
//			r.setCode(500);
//			return r;
//		}
		if(StringUtils.isNotBlank(epidemicPreventionAndControl.getTravelitineraryimg()) && epidemicPreventionAndControl.getTravelitineraryimg() != null){
			String msg = "";
			String msg1 = "";
			//行程卡
			String pathUrl = epidemicPreventionAndControl.getTravelitineraryimg();
			String url = fileUploadClient.getUrl(pathUrl);
			if(url != null){
				JsonNode urlres = JSONUtils.string2JsonNode(url);
				Iterator<JsonNode> urlelements = urlres.elements();
				while(urlelements.hasNext()) {
					JsonNode node = urlelements.next();
					String words = node.get("url").asText();
					System.out.println(words);
					pathUrl = words;
				}
			}
			String ss = GeneralBasic.generalBasic(pathUrl);
			if(ss != null){
				JsonNode res = JSONUtils.string2JsonNode(ss);
				log.info(ss);
				JsonNode lists = res.get("words_result");
				JsonNode words_result_num = res.get("words_result_num");
				if("0".equals(words_result_num.asText())){
					r.setMsg("请上传正确通信大数据行程卡");
					r.setCode(500);
					return r;
				}else{
					log.info("通信大数据行程卡");
					log.info(lists.toString());
					Iterator<JsonNode> elements = lists.elements();
					String remark = "";
					while (elements.hasNext()) {
						JsonNode node = elements.next();
						String words = node.get("words").asText();
						if (words.contains("行程卡") || words.contains("通信行程卡") || words.contains("绿色行程卡")) {
							msg1 = "通信大数据行程卡";
						} else {
							msg = "debug";
						}
						if (words.contains("途经：")) {
							remark += node.get("words").asText();
							String str1 = remark.substring(0, remark.indexOf("："));
							String str2 = remark.substring(str1.length() + 1, remark.length());
							epidemicPreventionAndControl.setAfterregion(str2);
						}
						if (words.contains("（注：*")) {
							if (words.contains("（注：*")) {
								remark += node.get("words").asText();
								remark = remark.substring(0, remark.indexOf("（"));
							}
							System.out.println(remark);
							System.out.println("最近14天经过风险区域");
							String str1 = remark.substring(0, remark.indexOf("："));
							String str2 = remark.substring(str1.length() + 1, remark.length());
							epidemicPreventionAndControl.setRiskyarea(str2);
						}
						if (words.contains("绿色行程卡")) {
							System.out.println("无风险");
							epidemicPreventionAndControl.setIsriskyarea(0);
							break;
						}
						if (words.contains("黄色行程卡")) {
							System.out.println("中风险");
							epidemicPreventionAndControl.setIsriskyarea(1);
							break;
						}
						if (words.contains("红色行程卡")) {
							System.out.println("高风险");
							epidemicPreventionAndControl.setIsriskyarea(1);
							break;
						}
					}

					if (msg.equals("debug") && msg1.equals("")) {
						r.setMsg("请上传正确通信大数据行程卡");
						r.setCode(500);
						return r;
					}
				}
			}else{
				r.setMsg("请上传正确通信大数据行程卡");
				r.setCode(500);
				return r;
			}
		}

		if(StringUtils.isNotBlank(epidemicPreventionAndControl.getHealthcodeimg()) && epidemicPreventionAndControl.getHealthcodeimg() != null){
			String msg = "";
			String msg1 = "";
			//健康码
			String pathUrl = epidemicPreventionAndControl.getHealthcodeimg();
			String url = fileUploadClient.getUrl(pathUrl);
			if(url != null){
				JsonNode urlres = JSONUtils.string2JsonNode(url);
				Iterator<JsonNode> urlelements = urlres.elements();
				while(urlelements.hasNext()) {
					JsonNode node = urlelements.next();
					String words = node.get("url").asText();
					System.out.println(words);
					pathUrl = words;
				}
			}
			String ss = GeneralBasic.generalBasic(pathUrl);
			if(ss != null) {
				System.out.println(ss);
				JsonNode res = JSONUtils.string2JsonNode(ss);
				JsonNode lists = res.get("words_result");
				JsonNode words_result_num = res.get("words_result_num");
				if("0".equals(words_result_num.asText())){
					r.setMsg("请上传正确健康码");
					r.setCode(500);
					return r;
				}else {
					Iterator<JsonNode> elements = lists.elements();
					String remark = "";
					while (elements.hasNext()) {
						JsonNode node = elements.next();
						String words = node.get("words").asText();
						if (words.contains("码")) {
							msg1 = "码";
						} else {
							msg = "debug";
						}
						if (words.contains("绿码")) {
							remark = node.get("words").asText();
							epidemicPreventionAndControl.setHealthcodestatus(0);
							break;
						}
						if (words.contains("黄码")) {
							remark = node.get("words").asText();
							epidemicPreventionAndControl.setHealthcodestatus(1);
							break;
						}
						if (words.contains("红码")) {
							remark = node.get("words").asText();
							epidemicPreventionAndControl.setHealthcodestatus(2);
							break;
						}
					}
					if (msg.equals("debug") && msg1.equals("")) {
						r.setMsg("请上传正确健康码");
						r.setData(msg);
						r.setCode(500);
						return r;
					}
				}
			}else{
				r.setMsg("请上传正确健康码");
				r.setCode(500);
				return r;
			}
		}
		epidemicPreventionAndControl.setUpdatetime(DateUtil.now());
		epidemicPreventionAndControl.setUpdateuser(epidemicPreventionAndControl.getJiashiyuanid());
		if(StringUtils.isNotBlank(epidemicPreventionAndControl.getAfterregion()) && !epidemicPreventionAndControl.getAfterregion().equals("null")){
			epidemicPreventionAndControl.setAfterregion(epidemicPreventionAndControl.getAfterregion());
		}
		if(StringUtils.isNotBlank(epidemicPreventionAndControl.getRiskyarea()) && !epidemicPreventionAndControl.getRiskyarea().equals("null")){
			epidemicPreventionAndControl.setRiskyarea(epidemicPreventionAndControl.getRiskyarea());
		}
//		epidemicPreventionAndControl.setUpdateuser(epidemicPreventionAndControl.getJiashiyuanid());
		boolean i = iEpidemicPreventionAndControlService.updateSelective(epidemicPreventionAndControl);
		if(i){
			r.setMsg("编辑成功");
			r.setCode(200);
		}else{
			r.setMsg("编辑失败");
			r.setCode(500);
		}
		return r;
	}

	/**
	 *  查询详情
	 */
	@GetMapping("/detail")
	@ApiLog("详情-司机端疫情防控信息")
	@ApiOperation(value = "详情-司机端疫情防控信息", notes = "传入id", position = 3)
	public R detail(String id,BladeUser user) {
		R r = new R();
//		if(user == null){
//			r.setMsg("未授权");
//			r.setCode(500);
//			return r;
//		}
		EpidemicPreventionAndControl detal = iEpidemicPreventionAndControlService.selectById(id,null);
		if(detal != null){
			//健康码附件
			if(StrUtil.isNotEmpty(detal.getHealthcodeimg()) && detal.getHealthcodeimg().contains("http") == false){
				detal.setHealthcodeimg(fileUploadClient.getUrl(detal.getHealthcodeimg()));
			}
			//行程卡附件
			if(StrUtil.isNotEmpty(detal.getTravelitineraryimg()) && detal.getTravelitineraryimg().contains("http") == false){
				detal.setTravelitineraryimg(fileUploadClient.getUrl(detal.getTravelitineraryimg()));
			}
			//核酸检测记录附件
			if(StrUtil.isNotEmpty(detal.getNatimg()) && detal.getNatimg().contains("http") == false){
				detal.setNatimg(fileUploadClient.getUrl(detal.getNatimg()));
			}
			return R.data(detal);
		}else{
			return R.data(null);
		}
	}

	/**
	 * 分页
	 */
	@PostMapping("/list")
	@ApiLog("分页-疫情防控信息")
	@ApiOperation(value = "分页-疫情防控信息", notes = "传入epidemicPreventionAndControlServicePage", position = 4)
	public R<EpidemicPreventionAndControlPage<EpidemicPreventionAndControl>> list(@RequestBody EpidemicPreventionAndControlPage epidemicPreventionAndControlServicePage,BladeUser user) {
		R r = new R();
		if(user == null){
			r.setMsg("未授权");
			r.setCode(500);
			return r;
		}
		EpidemicPreventionAndControlPage<EpidemicPreventionAndControl> pages = iEpidemicPreventionAndControlService.selectPageList(epidemicPreventionAndControlServicePage);
		return R.data(pages);
	}

	/**
	 * 分页
	 */
	@PostMapping("/getDeptMXIntoAreaPMTJ")
	@ApiLog("分页-进区域报警明细报表")
	@ApiOperation(value = "分页-进区域报警明细报表", notes = "传入intoAreaPage", position = 5)
	public R<IntoAreaPage<IntoArea>> getDeptMXIntoAreaPMTJ(@RequestBody IntoAreaPage intoAreaPage, BladeUser user) {
		R r = new R();
		if(user == null){
			r.setMsg("未授权");
			r.setCode(500);
			return r;
		}
		IntoAreaPage<IntoArea> pages = iEpidemicPreventionAndControlService.selectDeptMXIntoAreaPMTJ(intoAreaPage);
		return R.data(pages);
	}

	@PostMapping("/getDeptAreaalarmPageList")
	@ApiLog("分页-企业车辆进区域报警及处理情况统计")
	@ApiOperation(value = "分页-企业车辆进区域报警及处理情况统计", notes = "传入intoAreaPage", position = 6)
	public R<IntoAreaPage<DeptVehIntoArea>> getDeptAreaalarmPageList(@RequestBody IntoAreaPage intoAreaPage) {
		R r = new R();
		IntoAreaPage<DeptVehIntoArea> pages = iEpidemicPreventionAndControlService.selectDeptAreaalarmPageList(intoAreaPage);
		return R.data(pages);
	}


}
