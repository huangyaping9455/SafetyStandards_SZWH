package org.springblade.anbiao.risk.controller;


import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.sql.visitor.functions.Substring;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanCongyezigezheng;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanJiashizheng;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanTijian;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanCongyezigezhengService;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanJiashizhengService;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanTijianService;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanListVO;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetailInfo;
import org.springblade.anbiao.risk.page.RiskPage;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailInfoService;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import org.springblade.anbiao.risk.vo.AnbiaoRiskDetailVO;
import org.springblade.anbiao.risk.vo.AnbiaoSystemRiskVO;
import org.springblade.common.tool.JSONUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * ???????????????????????? ???????????????
 * </p>
 *
 * @author hyp
 * @since 2022-12-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/risk")
@Api(value = "??????????????????", tags = "??????????????????")
public class AnbiaoRiskDetailController {

	private IAnbiaoRiskDetailService riskDetailService;
	private IJiaShiYuanService jiaShiYuanService;
	private IOrganizationsService organizationsService;
	private IAnbiaoRiskDetailInfoService detailInfoService;
	private IAnbiaoJiashiyuanJiashizhengService jiashizhengService;
	private IAnbiaoJiashiyuanCongyezigezhengService congyezigezhengService;
	private IAnbiaoJiashiyuanTijianService tijianService;
	private IAnbiaoAnquanhuiyiService anquanhuiyiService;

	@PostMapping("/insert")
	@ApiLog("??????-??????????????????")
	@ApiOperation(value = "??????-??????????????????", position = 1)
	public R insert(BladeUser user) throws ParseException {
		R r = new R();
		QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<Organizations>();
		organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete,0);
		organizationsQueryWrapper.lambda().eq(Organizations::getJigouleixing,"qiye");
		List<Organizations> organizationsList = organizationsService.getBaseMapper().selectList(organizationsQueryWrapper);
		for (Organizations organizations:organizationsList) {
			Integer deptId = Integer.parseInt(organizations.getDeptId());
			QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper1 = new QueryWrapper<>();
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getDeptId,deptId);
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getIsdelete,0);
			List<JiaShiYuan> jiaShiYuans = jiaShiYuanService.getBaseMapper().selectList(jiaShiYuanQueryWrapper1);
			for (JiaShiYuan jiaShiYuan: jiaShiYuans) {
				String jiaShiYuanId = jiaShiYuan.getId();
				int aa=0;
				int timeDifference=0;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId,jiaShiYuanId);
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete,0);
				JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);

				//?????????????????????
				AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
				riskDetail.setArdDeptIds(deail.getDeptId().toString());
				riskDetail.setArdMajorCategories("0");
				riskDetail.setArdSubCategory("003");
				riskDetail.setArdTitle("???????????????????????????");
				riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail.setArdIsRectification("0");
				riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail.setArdAssociationField("id");
				riskDetail.setArdAssociationValue(deail.getId());
				if (StringUtils.isBlank(deail.getShenfenzhengyouxiaoqi()) || deail.getShenfenzhengyouxiaoqi().equals("null")){
					riskDetail.setArdContent("????????????????????????");
					riskDetail.setArdType("??????");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null) {
						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
						if (insert > 0) {
							aa++;
						}
					}
				}else if (!deail.getShenfenzhengyouxiaoqi().equals("??????")){
					Date shenfenzhengyouxiaoqi = formatter.parse(deail.getShenfenzhengyouxiaoqi());
					Date now = formatter.parse(DateUtil.now());
					Calendar calendar1 = Calendar.getInstance();
					calendar1.setTime(now);
					Calendar calendar2 = Calendar.getInstance();
					calendar2.setTime(shenfenzhengyouxiaoqi);
					int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
					int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
					int year1 = calendar1.get(Calendar.YEAR);
					int year2 = calendar2.get(Calendar.YEAR);
					if (year1 != year2) {//?????????
						int timeDistance = 0;
						if (year2<year1){
							timeDifference = -1;
						}else {
							for (int x = year1; x < year2; x++) { //??????
								if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
									timeDistance += 366;
								} else { // ????????????
									timeDistance += 365;
								}
							}
							timeDifference = timeDistance + (day2 - day1);
						}
					} else {
						timeDifference = day2 - day1;
					}
					if (timeDifference <= 30 && timeDifference > 0) {
						riskDetail.setArdContent("????????????????????????");
						riskDetail.setArdType("??????");
					} else if (timeDifference < 0) {
						riskDetail.setArdContent("????????????????????????");
						riskDetail.setArdType("??????");
					}else {
						riskDetail.setArdType("??????");
					}
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null){
						if (riskDetail.getArdType().equals("??????") || riskDetail.getArdType().equals("??????")) {
							int insert = riskDetailService.getBaseMapper().insert(riskDetail);
							if (insert > 0) {
								aa++;
							}
						}
					}
				}

				//?????????????????????
				AnbiaoRiskDetail riskDetail2 = new AnbiaoRiskDetail();
				riskDetail2.setArdDeptIds(deail.getDeptId().toString());
				riskDetail2.setArdMajorCategories("0");
				riskDetail2.setArdSubCategory("003");
				riskDetail2.setArdTitle("???????????????????????????");
				riskDetail2.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail2.setArdIsRectification("0");
				riskDetail2.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail2.setArdAssociationField("id");
				riskDetail2.setArdAssociationValue(deail.getId());
				if (StringUtils.isBlank(deail.getJiashizhengyouxiaoqi()) || deail.getJiashizhengyouxiaoqi().equals("null")){
					riskDetail2.setArdContent("?????????????????????????????????");
					riskDetail2.setArdType("??????");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail2.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail2.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail2.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if(riskDetail1==null){
						int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
						if (insert2 > 0) {
							aa++;
						}
					}
				}else if (!deail.getJiashizhengyouxiaoqi().equals("??????")){
					Date jiashizhengyouxiaoqi = formatter.parse(deail.getJiashizhengyouxiaoqi());
					Date now2 = formatter.parse(DateUtil.now());
					Calendar calendar3 = Calendar.getInstance();
					calendar3.setTime(now2);
					Calendar calendar4 = Calendar.getInstance();
					calendar4.setTime(jiashizhengyouxiaoqi);
					int day3 = calendar3.get(Calendar.DAY_OF_YEAR);
					int day4 = calendar4.get(Calendar.DAY_OF_YEAR);
					int year3 = calendar3.get(Calendar.YEAR);
					int year4 = calendar4.get(Calendar.YEAR);
					if (year3 != year4) {//?????????
						int timeDistance = 0;
						if (year4<year3){
							timeDifference = -1;
						}else {
							for (int x = year3; x < year4; x++) { //??????
								if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
									timeDistance += 366;
								} else { // ????????????
									timeDistance += 365;
								}
							}
							timeDifference = timeDistance + (day4 - day3);
						}
					} else {
						timeDifference = day4 - day3;
					}
					if (timeDifference <= 30 && timeDifference > 0) {
						riskDetail2.setArdContent("?????????????????????????????????");
						riskDetail2.setArdType("??????");
					} else if (timeDifference < 0) {
						riskDetail2.setArdContent("?????????????????????????????????");
						riskDetail2.setArdType("??????");
					}else {
						riskDetail2.setArdType("??????");
					}
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail2.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail2.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail2.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null){
						if (riskDetail2.getArdType().equals("??????") || riskDetail2.getArdType().equals("??????")) {
							int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
							if (insert2 > 0) {
								aa++;
							}
						}
					}
				}

				//???????????????????????????
				AnbiaoRiskDetail riskDetail3 = new AnbiaoRiskDetail();
				riskDetail3.setArdDeptIds(deail.getDeptId().toString());
				riskDetail3.setArdMajorCategories("0");
				riskDetail3.setArdSubCategory("003");
				riskDetail3.setArdTitle("?????????????????????????????????");
				riskDetail3.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail3.setArdIsRectification("0");
				riskDetail3.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail3.setArdAssociationField("id");
				riskDetail3.setArdAssociationValue(deail.getId());
				if (StringUtils.isBlank(deail.getCongyezhengyouxiaoqi()) || deail.getCongyezhengyouxiaoqi().equals("null")){
					riskDetail3.setArdContent("??????????????????????????????");
					riskDetail3.setArdType("??????");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail3.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail3.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail3.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null){
						int insert3 = riskDetailService.getBaseMapper().insert(riskDetail3);
						if (insert3 > 0) {
							aa++;
						}
					}
				}else {
					Date congyezhengyouxiaoqi = formatter.parse(deail.getCongyezhengyouxiaoqi());
					Date now3 = formatter.parse(DateUtil.now());
					Calendar calendar5 = Calendar.getInstance();
					calendar5.setTime(now3);
					Calendar calendar6 = Calendar.getInstance();
					calendar6.setTime(congyezhengyouxiaoqi);
					int day5 = calendar5.get(Calendar.DAY_OF_YEAR);
					int day6 = calendar6.get(Calendar.DAY_OF_YEAR);
					int year5 = calendar5.get(Calendar.YEAR);
					int year6 = calendar6.get(Calendar.YEAR);
					if (year5 != year6) {//?????????
						int timeDistance = 0;
						if (year6<year5){
							timeDifference = -1;
						}else {
							for (int x = year5; x < year6; x++) { //??????
								if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
									timeDistance += 366;
								} else { // ????????????
									timeDistance += 365;
								}
							}
							timeDifference = timeDistance + (day6 - day5);
						}
					} else {
						timeDifference = day6 - day5;
					}
					if (timeDifference <= 30 && timeDifference > 0) {
						riskDetail3.setArdContent("??????????????????????????????");
						riskDetail3.setArdType("??????");
					} else if (timeDifference < 0) {
						riskDetail3.setArdContent("??????????????????????????????");
						riskDetail3.setArdType("??????");
					}else {
						riskDetail3.setArdType("??????");
					}
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail3.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail3.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail3.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null){
						if (riskDetail3.getArdType().equals("??????") || riskDetail3.getArdType().equals("??????")) {
							int insert3 = riskDetailService.getBaseMapper().insert(riskDetail3);
							if (insert3 > 0) {
								aa++;
							}
						}
					}
				}

				//??????????????????
				AnbiaoRiskDetail riskDetail4 = new AnbiaoRiskDetail();
				riskDetail4.setArdDeptIds(deail.getDeptId().toString());
				riskDetail4.setArdMajorCategories("0");
				riskDetail4.setArdSubCategory("003");
				riskDetail4.setArdTitle("????????????????????????");
				riskDetail4.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail4.setArdIsRectification("0");
				riskDetail4.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail4.setArdAssociationField("id");
				riskDetail4.setArdAssociationValue(deail.getId());
				if (StringUtils.isBlank(deail.getTijianriqi()) || deail.getTijianriqi().equals("null")){
					riskDetail4.setArdContent("??????????????????");
					riskDetail4.setArdType("??????");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail4.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail4.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail4.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null){
						int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
						if (insert4 > 0) {
							aa++;
						}
					}
				}else {
					Date tijianyouxiaoqi = formatter.parse(deail.getTijianyouxiaoqi());
					Date now4 = formatter.parse(DateUtil.now());
					Calendar calendar7 = Calendar.getInstance();
					calendar7.setTime(now4);
					Calendar calendar8 = Calendar.getInstance();
					calendar8.setTime(tijianyouxiaoqi);
					int day7 = calendar7.get(Calendar.DAY_OF_YEAR);
					int day8 = calendar8.get(Calendar.DAY_OF_YEAR);
					int year7 = calendar7.get(Calendar.YEAR);
					int year8 = calendar8.get(Calendar.YEAR);
					if (year7 != year8) {//?????????
						int timeDistance = 0;
						if (year8<year7){
							timeDifference = -1;
						}else {
							for (int x = year7; x < year8; x++) { //??????
								if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
									timeDistance += 366;
								} else { // ????????????
									timeDistance += 365;
								}
							}
							timeDifference = timeDistance + (day8 - day7);
						}
					} else {
						timeDifference = day8 - day7;
					}
					riskDetail4.setArdDeptIds(deail.getDeptId().toString());
					if (timeDifference <= 30 && timeDifference > 0) {
						riskDetail4.setArdContent("?????????????????????");
						riskDetail4.setArdType("??????");
					} else if (timeDifference < 0) {
						riskDetail4.setArdContent("?????????????????????");
						riskDetail4.setArdType("??????");
					}else {
						riskDetail4.setArdType("??????");
					}
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail4.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail4.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail4.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null){
						if (riskDetail4.getArdType().equals("??????") || riskDetail4.getArdType().equals("??????")) {
							int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
							if (insert4 > 0) {
								aa++;
							}
						}
					}
				}

				if (aa>0) {
					r.setMsg("??????????????????");
					r.setCode(200);
					r.setSuccess(true);
				}else {
					r.setMsg("???????????????");
					r.setCode(200);
					r.setSuccess(true);
				}
			}
		}
		return r;
	}

	@PostMapping("/detail")
	@ApiLog("??????-??????????????????")
	@ApiOperation(value = "??????-??????????????????", notes = "??????jiaShiYuan", position = 1)
	public R detail(String id,String deptId,BladeUser user) {
		R r=new R();
		QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId,id);
		JiaShiYuan jiaShiYuan = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
		String jiashiyuanxingming = jiaShiYuan.getJiashiyuanxingming();
		QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,id);
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,deptId);
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,0);
		List<AnbiaoRiskDetail> anbiaoRiskDetails = riskDetailService.getBaseMapper().selectList(riskDetailQueryWrapper);
		for (AnbiaoRiskDetail anbiaoRiskDetail:
			anbiaoRiskDetails) {
			anbiaoRiskDetail.setJiashiyuanxingming(jiashiyuanxingming);
		}
		if (anbiaoRiskDetails.size()!=0){
			r.setMsg("????????????");
			r.setCode(200);
			r.setSuccess(true);
			r.setData(anbiaoRiskDetails);
		}else {
			r.setMsg("???????????????");
			r.setCode(200);
			r.setSuccess(true);
		}

		return r;
	}

	@PostMapping("/update")
	@ApiLog("??????-??????????????????")
	@ApiOperation(value = "??????-??????????????????", notes = "??????ardIds", position = 1)
	public R deal(@RequestBody String json,BladeUser user){
		R r=new R();
		int aa=0;
		//????????????
		JsonNode node = JSONUtils.string2JsonNode(json);
		String ardIds = node.get("ardIds").asText();
		String date = node.get("date").asText();
		String fujian = node.get("fujian").asText();
		QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIds,ardIds);
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
		AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
		if (riskDetail!=null) {

			String substring = riskDetail.getArdTitle().substring(0, 2);
			AnbiaoRiskDetailInfo anbiaoRiskDetailInfo = new AnbiaoRiskDetailInfo();

			//?????????????????????
			if (substring.equals("??????")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("???????????????????????????");
				riskDetail.setArdRectificationField("shenfenzhengyouxiaoqi");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//????????????
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("shenfenzhengyouxiaoqi");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//????????????
						QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, riskDetail.getArdAssociationValue());
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
						JiaShiYuan deal = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
						deal.setShenfenzhengyouxiaoqi(date);
						deal.setShenfenzhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = jiaShiYuanService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//?????????????????????
			if (substring.equals("??????")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("???????????????????????????");
				riskDetail.setArdRectificationField("jiashizhengyouxiaoqi");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//????????????
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("jiashizhengyouxiaoqi");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//????????????
						QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, riskDetail.getArdAssociationValue());
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
						JiaShiYuan deal = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
						deal.setJiashizhengyouxiaoqi(date);
						deal.setJiashizhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = jiaShiYuanService.updateById(deal);
						if (b1 == true) {
							//????????????
							QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<>();
							jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, riskDetail.getArdAssociationValue());
							jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, 0);
							AnbiaoJiashiyuanJiashizheng jiashizheng = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
							jiashizheng.setAjjValidPeriodEnd(date);
							jiashizheng.setAjjFrontPhotoAddress(fujian);
							jiashizheng.setAjjUpdateTime(DateUtil.now());
							jiashizheng.setAjjUpdateByIds(user.getUserId().toString());
							jiashizheng.setAjjUpdateByName(user.getUserName());
							boolean b2 = jiashizhengService.updateById(jiashizheng);
							if (b2 == true) {
								aa++;
							}
						}
					}
				}
			}

			//???????????????????????????
			if (substring.equals("??????")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("?????????????????????????????????");
				riskDetail.setArdRectificationField("congyezhengyouxiaoqi");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//????????????
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("congyezhengyouxiaoqi");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//????????????
						QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, riskDetail.getArdAssociationValue());
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
						JiaShiYuan deal = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
						deal.setCongyezhengyouxiaoqi(date);
						deal.setCongyezhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = jiaShiYuanService.updateById(deal);
						if (b1 == true) {
							//??????????????????
							QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<>();
							congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, riskDetail.getArdAssociationValue());
							congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, 0);
							AnbiaoJiashiyuanCongyezigezheng congyezigezheng = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
							congyezigezheng.setAjcValidUntil(date);
							congyezigezheng.setAjcLicence(fujian);
							congyezigezheng.setAjcUpdateTime(DateUtil.now());
							congyezigezheng.setAjcUpdateByIds(user.getUserId().toString());
							congyezigezheng.setAjcUpdateByName(user.getUserName());
							boolean b2 = congyezigezhengService.updateById(congyezigezheng);
							if (b2 == true) {
								aa++;
							}
						}
					}
				}
			}

			//?????????????????????
			if (substring.equals("??????")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("????????????????????????");
				riskDetail.setArdRectificationField("tijianriqi");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//????????????
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("tijianriqi");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//????????????
						QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, riskDetail.getArdAssociationValue());
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
						JiaShiYuan deal = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
						int tijianyouxiaoqi = Integer.parseInt(date.substring(0, 4))+ 1;
						String tijianyouxiaoqis =String.valueOf(tijianyouxiaoqi);
						String tijianyouxiaoqiss =tijianyouxiaoqis+date.substring(4, 10);
						deal.setTijianriqi(date);
						deal.setTijianyouxiaoqi(tijianyouxiaoqiss);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = jiaShiYuanService.updateById(deal);
						if (b1 == true) {
							//?????????
							QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<>();
							tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, riskDetail.getArdAssociationValue());
							tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, 0);
							AnbiaoJiashiyuanTijian tijian = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
							int tijianyouxiaoqi2 = Integer.parseInt(date.substring(0, 4))+ 1;
							String tijianyouxiaoqi22 =String.valueOf(tijianyouxiaoqi2);
							String tijianyouxiaoqi222 =tijianyouxiaoqi22+date.substring(4, 10);
							tijian.setAjtPhysicalExaminationDate(date);
							tijian.setAjtTermValidity(tijianyouxiaoqi222);
							tijian.setAjtTermValidity(date);
							tijian.setAjtEnclosure(fujian);
							tijian.setAjtUpdateTime(DateUtil.now());
							tijian.setAjtUpdateByIds(user.getUserId().toString());
							tijian.setAjtUpdateByName(user.getUserName());
							boolean b2 = tijianService.updateById(tijian);
							if (b2 == true) {
								aa++;
							}
						}
					}
				}
			}
		}
		if (aa>0) {
			r.setMsg("??????????????????");
			r.setCode(200);
			r.setSuccess(true);
		}else {
			r.setMsg("??????????????????");
			r.setCode(500);
			r.setSuccess(false);
		}
		return r;
	}

	@PostMapping("/organizationInsert")
	@ApiLog("??????-????????????????????????")
	@ApiOperation(value = "??????-????????????????????????", position = 1)
	public R organizationInsert(BladeUser user) throws ParseException{
		R r = new R();
		QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<Organizations>();
		organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete,0);
		organizationsQueryWrapper.lambda().eq(Organizations::getJigouleixing,"qiye");
		List<Organizations> organizationsList = organizationsService.getBaseMapper().selectList(organizationsQueryWrapper);
		int aa=0;
		int timeDifference=0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (Organizations organization:organizationsList){

			//??????????????????????????????
			AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
			riskDetail.setArdDeptIds(organization.getDeptId());
			riskDetail.setArdMajorCategories("0");
			riskDetail.setArdSubCategory("000");
			riskDetail.setArdTitle("????????????????????????");
			riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail.setArdIsRectification("0");
			riskDetail.setArdAssociationTable("anbiao_organization");
			riskDetail.setArdAssociationField("id");
			riskDetail.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getJigoubianma()) || organization.getJigoubianma().equals("null")){
				riskDetail.setArdContent("??????????????????????????????");
				riskDetail.setArdType("??????");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null) {
					int insert = riskDetailService.getBaseMapper().insert(riskDetail);
					if (insert > 0) {
						aa++;
					}
				}
			}

			//????????????????????????????????????
			AnbiaoRiskDetail riskDetail2 = new AnbiaoRiskDetail();
			riskDetail2.setArdDeptIds(organization.getDeptId());
			riskDetail2.setArdMajorCategories("0");
			riskDetail2.setArdSubCategory("000");
			riskDetail2.setArdTitle("??????????????????????????????????????????");
			riskDetail2.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail2.setArdIsRectification("0");
			riskDetail2.setArdAssociationTable("anbiao_organization");
			riskDetail2.setArdAssociationField("id");
			riskDetail2.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getYyzzjzdate()) || organization.getYyzzjzdate().equals("null")){
				riskDetail2.setArdContent("????????????????????????????????????????????????");
				riskDetail2.setArdType("??????");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail2.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail2.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail2.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if(riskDetail1==null){
					int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
					if (insert2 > 0) {
						aa++;
					}
				}
			}else if (!organization.getYyzzjzdate().equals("??????")){
				Date yyzzjzdate= formatter.parse(organization.getYyzzjzdate());
				Date now2 = formatter.parse(DateUtil.now());
				Calendar calendar3 = Calendar.getInstance();
				calendar3.setTime(now2);
				Calendar calendar4 = Calendar.getInstance();
				calendar4.setTime(yyzzjzdate);
				int day3 = calendar3.get(Calendar.DAY_OF_YEAR);
				int day4 = calendar4.get(Calendar.DAY_OF_YEAR);
				int year3 = calendar3.get(Calendar.YEAR);
				int year4 = calendar4.get(Calendar.YEAR);
				if (year3 != year4) {//?????????
					int timeDistance = 0;
					if (year4<year3){
						timeDifference = -1;
					}else {
						for (int x = year3; x < year4; x++) { //??????
							if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
								timeDistance += 366;
							} else { // ????????????
								timeDistance += 365;
							}
						}
						timeDifference = timeDistance + (day4 - day3);
					}
				} else {
					timeDifference = day4 - day3;
				}
				if (timeDifference <= 30 && timeDifference > 0) {
					riskDetail2.setArdContent("????????????????????????????????????????????????");
					riskDetail2.setArdType("??????");
				} else if (timeDifference < 0) {
					riskDetail2.setArdContent("????????????????????????????????????????????????");
					riskDetail2.setArdType("??????");
				}else {
					riskDetail2.setArdType("??????");
				}
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail2.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail2.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail2.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
					if (riskDetail2.getArdType().equals("??????") || riskDetail2.getArdType().equals("??????")) {
						int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
						if (insert2 > 0) {
							aa++;
						}
					}
				}
			}

			//??????????????????????????????
			AnbiaoRiskDetail riskDetail3 = new AnbiaoRiskDetail();
			riskDetail3.setArdDeptIds(organization.getDeptId());
			riskDetail3.setArdMajorCategories("0");
			riskDetail3.setArdSubCategory("000");
			riskDetail3.setArdTitle("????????????????????????");
			riskDetail3.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail3.setArdIsRectification("0");
			riskDetail3.setArdAssociationTable("anbiao_organization");
			riskDetail3.setArdAssociationField("id");
			riskDetail3.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getDaoluxukezhenghao()) || organization.getDaoluxukezhenghao().equals("null")){
				riskDetail3.setArdContent("??????????????????????????????");
				riskDetail3.setArdType("??????");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail3.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail3.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail3.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null) {
					int insert = riskDetailService.getBaseMapper().insert(riskDetail3);
					if (insert > 0) {
						aa++;
					}
				}
			}

			//?????????????????????????????????
			AnbiaoRiskDetail riskDetail4 = new AnbiaoRiskDetail();
			riskDetail4.setArdDeptIds(organization.getDeptId());
			riskDetail4.setArdMajorCategories("0");
			riskDetail4.setArdSubCategory("000");
			riskDetail4.setArdTitle("???????????????????????????????????????");
			riskDetail4.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail4.setArdIsRectification("0");
			riskDetail4.setArdAssociationTable("anbiao_organization");
			riskDetail4.setArdAssociationField("id");
			riskDetail4.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getDaoluyunshuzhengjieshuriqi()) || organization.getDaoluyunshuzhengjieshuriqi().equals("null")){
				riskDetail4.setArdContent("?????????????????????????????????????????????");
				riskDetail4.setArdType("??????");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail4.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail4.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail4.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
					int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
					if (insert4 > 0) {
						aa++;
					}
				}
			}else {
				Date daoluyunshuzhengjieshuriqi = formatter.parse(organization.getDaoluyunshuzhengjieshuriqi());
				Date now4 = formatter.parse(DateUtil.now());
				Calendar calendar7 = Calendar.getInstance();
				calendar7.setTime(now4);
				Calendar calendar8 = Calendar.getInstance();
				calendar8.setTime(daoluyunshuzhengjieshuriqi);
				int day7 = calendar7.get(Calendar.DAY_OF_YEAR);
				int day8 = calendar8.get(Calendar.DAY_OF_YEAR);
				int year7 = calendar7.get(Calendar.YEAR);
				int year8 = calendar8.get(Calendar.YEAR);
				if (year7 != year8) {//?????????
					int timeDistance = 0;
					if (year8<year7){
						timeDifference = -1;
					}else {
						for (int x = year7; x < year8; x++) { //??????
							if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
								timeDistance += 366;
							} else { // ????????????
								timeDistance += 365;
							}
						}
						timeDifference = timeDistance + (day8 - day7);
					}
				} else {
					timeDifference = day8 - day7;
				}
				if (timeDifference <= 30 && timeDifference > 0) {
					riskDetail4.setArdContent("?????????????????????????????????????????????");
					riskDetail4.setArdType("??????");
				} else if (timeDifference < 0) {
					riskDetail4.setArdContent("?????????????????????????????????????????????");
					riskDetail4.setArdType("??????");
				}else {
					riskDetail4.setArdType("??????");
				}
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail4.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail4.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail4.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
					if (riskDetail4.getArdType().equals("??????") || riskDetail4.getArdType().equals("??????")) {
						int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
						if (insert4 > 0) {
							aa++;
						}
					}
				}
			}

			//????????????????????????
			AnbiaoRiskDetail riskDetail5 = new AnbiaoRiskDetail();
			riskDetail5.setArdDeptIds(organization.getDeptId());
			riskDetail5.setArdMajorCategories("0");
			riskDetail5.setArdSubCategory("000");
			riskDetail5.setArdTitle("??????????????????");
			riskDetail5.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail5.setArdIsRectification("0");
			riskDetail5.setArdAssociationTable("anbiao_organization");
			riskDetail5.setArdAssociationField("id");
			riskDetail5.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getJingyingxukezhengbianma()) || organization.getJingyingxukezhengbianma().equals("null")){
				riskDetail5.setArdContent("????????????????????????");
				riskDetail5.setArdType("??????");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail5.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail5.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail5.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null) {
					int insert = riskDetailService.getBaseMapper().insert(riskDetail5);
					if (insert > 0) {
						aa++;
					}
				}
			}

			//???????????????????????????
			AnbiaoRiskDetail riskDetail6 = new AnbiaoRiskDetail();
			riskDetail6.setArdDeptIds(organization.getDeptId());
			riskDetail6.setArdMajorCategories("0");
			riskDetail6.setArdSubCategory("000");
			riskDetail6.setArdTitle("?????????????????????????????????");
			riskDetail6.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail6.setArdIsRectification("0");
			riskDetail6.setArdAssociationTable("anbiao_organization");
			riskDetail6.setArdAssociationField("id");
			riskDetail6.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getJingyingxukezhengyouxiaoqi()) || organization.getJingyingxukezhengyouxiaoqi().equals("null")){
				riskDetail6.setArdContent("???????????????????????????????????????");
				riskDetail6.setArdType("??????");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail6.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail6.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail6.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
					int insert6 = riskDetailService.getBaseMapper().insert(riskDetail6);
					if (insert6 > 0) {
						aa++;
					}
				}
			}else {
				Date jingyingxukezhengyouxiaoqi = formatter.parse(organization.getJingyingxukezhengyouxiaoqi());
				Date now4 = formatter.parse(DateUtil.now());
				Calendar calendar7 = Calendar.getInstance();
				calendar7.setTime(now4);
				Calendar calendar8 = Calendar.getInstance();
				calendar8.setTime(jingyingxukezhengyouxiaoqi);
				int day7 = calendar7.get(Calendar.DAY_OF_YEAR);
				int day8 = calendar8.get(Calendar.DAY_OF_YEAR);
				int year7 = calendar7.get(Calendar.YEAR);
				int year8 = calendar8.get(Calendar.YEAR);
				if (year7 != year8) {//?????????
					int timeDistance = 0;
					if (year8<year7){
						timeDifference = -1;
					}else {
						for (int x = year7; x < year8; x++) { //??????
							if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
								timeDistance += 366;
							} else { // ????????????
								timeDistance += 365;
							}
						}
						timeDifference = timeDistance + (day8 - day7);
					}
				} else {
					timeDifference = day8 - day7;
				}
				if (timeDifference <= 30 && timeDifference > 0) {
					riskDetail6.setArdContent("???????????????????????????????????????");
					riskDetail6.setArdType("??????");
				} else if (timeDifference < 0) {
					riskDetail6.setArdContent("???????????????????????????????????????");
					riskDetail6.setArdType("??????");
				}else {
					riskDetail6.setArdType("??????");
				}
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail6.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail6.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail6.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
					if (riskDetail6.getArdType().equals("??????") || riskDetail6.getArdType().equals("??????")) {
						int insert6 = riskDetailService.getBaseMapper().insert(riskDetail6);
						if (insert6 > 0) {
							aa++;
						}
					}
				}
			}

			//??????????????????
			AnbiaoRiskDetail riskDetail7 = new AnbiaoRiskDetail();
			riskDetail7.setArdDeptIds(organization.getDeptId());
			riskDetail7.setArdMajorCategories("0");
			riskDetail7.setArdSubCategory("000");
			riskDetail7.setArdTitle("????????????????????????");
			riskDetail7.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail7.setArdIsRectification("0");
			riskDetail7.setArdAssociationTable("anbiao_organization");
			riskDetail7.setArdAssociationField("id");
			riskDetail7.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getLaodonghetongjieshuriqi()) || organization.getLaodonghetongjieshuriqi().equals("null")){
				riskDetail7.setArdContent("??????????????????????????????");
				riskDetail7.setArdType("??????");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail7.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail7.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail7.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
					int insert7 = riskDetailService.getBaseMapper().insert(riskDetail7);
					if (insert7 > 0) {
						aa++;
					}
				}
			}else {
				Date laodonghetongjieshuriqi = formatter.parse(organization.getLaodonghetongjieshuriqi());
				Date now4 = formatter.parse(DateUtil.now());
				Calendar calendar7 = Calendar.getInstance();
				calendar7.setTime(now4);
				Calendar calendar8 = Calendar.getInstance();
				calendar8.setTime(laodonghetongjieshuriqi);
				int day7 = calendar7.get(Calendar.DAY_OF_YEAR);
				int day8 = calendar8.get(Calendar.DAY_OF_YEAR);
				int year7 = calendar7.get(Calendar.YEAR);
				int year8 = calendar8.get(Calendar.YEAR);
				if (year7 != year8) {//?????????
					int timeDistance = 0;
					if (year8<year7){
						timeDifference = -1;
					}else {
						for (int x = year7; x < year8; x++) { //??????
							if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
								timeDistance += 366;
							} else { // ????????????
								timeDistance += 365;
							}
						}
						timeDifference = timeDistance + (day8 - day7);
					}
				} else {
					timeDifference = day8 - day7;
				}
				if (timeDifference <= 30 && timeDifference > 0) {
					riskDetail7.setArdContent("??????????????????????????????");
					riskDetail7.setArdType("??????");
				} else if (timeDifference < 0) {
					riskDetail7.setArdContent("??????????????????????????????");
					riskDetail7.setArdType("??????");
				}else {
					riskDetail7.setArdType("??????");
				}
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail7.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail7.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail7.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
					if (riskDetail7.getArdType().equals("??????") || riskDetail7.getArdType().equals("??????")) {
						int insert7 = riskDetailService.getBaseMapper().insert(riskDetail7);
						if (insert7 > 0) {
							aa++;
						}
					}
				}
			}

			if (aa>0) {
				r.setMsg("??????????????????");
				r.setCode(200);
				r.setSuccess(true);
			}else {
				r.setMsg("???????????????");
				r.setCode(200);
				r.setSuccess(true);
			}
		}
		return r;
	}

	@PostMapping("/organizationDetail")
	@ApiLog("??????-????????????????????????")
	@ApiOperation(value = "??????-????????????????????????", notes = "??????Organizations", position = 1)
	public R organizationDetail(String id,String deptId,BladeUser user) {
		R r=new R();
		QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
		organizationsQueryWrapper.lambda().eq(Organizations::getId,id);
		Organizations organization = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
		String qiyemingcheng = organization.getDeptName();
		QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,id);
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,deptId);
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,0);
		List<AnbiaoRiskDetail> anbiaoRiskDetails = riskDetailService.getBaseMapper().selectList(riskDetailQueryWrapper);
		for (AnbiaoRiskDetail anbiaoRiskDetail:
			anbiaoRiskDetails) {
			anbiaoRiskDetail.setQiyemingcheng(qiyemingcheng);
		}
		if (anbiaoRiskDetails.size()!=0){
			r.setMsg("????????????");
			r.setCode(200);
			r.setSuccess(true);
			r.setData(anbiaoRiskDetails);
		}else {
			r.setMsg("???????????????");
			r.setCode(200);
			r.setSuccess(true);
		}

		return r;
	}

	@PostMapping("/organizationUpdate")
	@ApiLog("??????-????????????????????????")
	@ApiOperation(value = "??????-????????????????????????", notes = "??????ardIds", position = 1)
	public R organizationDeal(@RequestBody String json,BladeUser user){
		R r=new R();
		int aa=0;
		//????????????
		JsonNode node = JSONUtils.string2JsonNode(json);
		String ardIds = node.get("ardIds").asText();
		String date = node.get("date").asText();
		String fujian = node.get("fujian").asText();
		QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIds,ardIds);
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
		AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
		if (riskDetail!=null){
			String ardTitle = riskDetail.getArdTitle();
			AnbiaoRiskDetailInfo anbiaoRiskDetailInfo = new AnbiaoRiskDetailInfo();

			//????????????????????????
			if (ardTitle.equals("????????????????????????")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("????????????????????????");
				riskDetail.setArdRectificationField("jigoubianma");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//????????????
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("jigoubianma");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//?????????
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setJigoubianma(date);
						deal.setYingyezhizhaofujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//??????????????????????????????????????????
			if (ardTitle.equals("??????????????????????????????????????????")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("??????????????????????????????????????????");
				riskDetail.setArdRectificationField("yyzzjzdate");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//????????????
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("yyzzjzdate");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//?????????
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setYyzzjzdate(date);
						deal.setYingyezhizhaofujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//????????????????????????
			if (ardTitle.equals("????????????????????????")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("????????????????????????");
				riskDetail.setArdRectificationField("daoluxukezhenghao");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//????????????
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("daoluxukezhenghao");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//?????????
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setDaoluxukezhenghao(date);
						deal.setDaoluyunshuzhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//???????????????????????????????????????
			if (ardTitle.equals("???????????????????????????????????????")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("???????????????????????????????????????");
				riskDetail.setArdRectificationField("daoluyunshuzhengjieshuriqi");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//????????????
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("daoluyunshuzhengjieshuriqi");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//?????????
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setDaoluyunshuzhengjieshuriqi(date);
						deal.setDaoluyunshuzhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//??????????????????
			if (ardTitle.equals("??????????????????")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("??????????????????");
				riskDetail.setArdRectificationField("jingyingxukezhengbianma");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//????????????
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("jingyingxukezhengbianma");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//?????????
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setJingyingxukezhengbianma(date);
						deal.setJingyingxukezhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//????????????????????????
			if (ardTitle.equals("????????????????????????")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("????????????????????????");
				riskDetail.setArdRectificationField("htlastdate");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//????????????
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("htlastdate");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//?????????
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setLaodonghetongjieshuriqi(date);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//?????????????????????????????????
			if (ardTitle.equals("?????????????????????????????????")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("?????????????????????????????????");
				riskDetail.setArdRectificationField("jingyingxukezhengyouxiaoqi");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//????????????
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("jingyingxukezhengyouxiaoqi");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//?????????
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setJingyingxukezhengyouxiaoqi(date);
						deal.setJingyingxukezhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

		}
		if (aa>0) {
			r.setMsg("??????????????????");
			r.setCode(200);
			r.setSuccess(true);
		}else {
			r.setMsg("??????????????????");
			r.setCode(500);
			r.setSuccess(false);
		}
		return r;
	}

	@GetMapping("/getByCount")
	@ApiLog("??????--??????????????????")
	@ApiOperation(value = "??????--??????????????????", notes = "????????????ID,??????", position = 4)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "??????ID", required = true),
		@ApiImplicitParam(name = "date", value = "??????(yyyy-MM)", required = true)
	})
	public R<List<AnbiaoRiskDetailVO>> getByCount(String deptId, String date, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setCode(401);
			rs.setMsg("?????????");
			rs.setSuccess(false);
			return rs;
		}
		List<AnbiaoRiskDetailVO> list= riskDetailService.selectByCount(deptId,date);
		if(list.size() < 1){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("???????????????????????????");
		}else{
			rs.setData(list);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("????????????");
		}
		return rs;
	}

	@GetMapping("/getByDateCount")
	@ApiLog("??????--????????????--?????????")
	@ApiOperation(value = "??????--????????????--?????????", notes = "????????????ID,??????", position = 5)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "??????ID", required = true),
		@ApiImplicitParam(name = "date", value = "??????(yyyy-MM)", required = true)
	})
	public R<List<AnbiaoRiskDetailVO>> getByDateCount(String deptId, String date, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setCode(401);
			rs.setMsg("?????????");
			rs.setSuccess(false);
			return rs;
		}
		List<AnbiaoRiskDetailVO> list= riskDetailService.selectByDateCount(deptId,date);
		if(list.size() < 1){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("???????????????????????????");
		}else{
			rs.setData(list);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("????????????");
		}
		return rs;
	}

	@GetMapping("/getByCategoryCount")
	@ApiLog("??????--????????????--????????????")
	@ApiOperation(value = "??????--????????????--????????????", notes = "????????????ID,??????,????????????", position = 6)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "??????ID", required = true),
		@ApiImplicitParam(name = "date", value = "??????(yyyy-MM)", required = true),
		@ApiImplicitParam(name = "category", value = "????????????", required = true)
	})
	public R<List<AnbiaoRiskDetailVO>> getByCategoryCount(String deptId, String date, String category, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setCode(401);
			rs.setMsg("?????????");
			rs.setSuccess(false);
			return rs;
		}
		List<AnbiaoRiskDetailVO> list= riskDetailService.selectByCategoryCount(deptId,date,category);
		if(list.size() < 1){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("???????????????????????????");
		}else{
			rs.setData(list);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("????????????");
		}
		return rs;
	}

	@GetMapping("/selectByCategoryMXList")
	@ApiLog("??????--????????????--??????????????????")
	@ApiOperation(value = "??????--????????????--??????????????????", notes = "????????????ID,??????,????????????", position = 7)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "??????ID", required = true),
		@ApiImplicitParam(name = "date", value = "??????(yyyy-MM)", required = true),
		@ApiImplicitParam(name = "category", value = "????????????", required = true),
		@ApiImplicitParam(name = "ardContent", value = "????????????", required = true)
	})
	public R<List<AnbiaoRiskDetailVO>> selectByCategoryMXList(String deptId, String date, String category,String ardContent, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setCode(401);
			rs.setMsg("?????????");
			rs.setSuccess(false);
			return rs;
		}
		List<AnbiaoRiskDetailVO> list= riskDetailService.selectByCategoryMXCount(deptId,date,category,ardContent);
		if(list.size() < 1){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("???????????????????????????");
		}else{
			rs.setData(list);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("????????????");
		}
		return rs;
	}

	@PostMapping("/getByCategoryMXList")
	@ApiLog("??????--????????????--??????????????????")
	@ApiOperation(value = "??????--????????????--??????????????????", notes = "??????RiskPage", position = 8)
	public R<RiskPage<AnbiaoRiskDetailVO>> getByCategoryMXList(@RequestBody RiskPage riskPage) {
		RiskPage<AnbiaoRiskDetailVO> pages = riskDetailService.selectByCategoryMXCountPage(riskPage);
		return R.data(pages);
	}


	@PostMapping("/systemInsert")
	@ApiLog("??????-????????????????????????")
	@ApiOperation(value = "??????-????????????????????????", position = 1)
	public R systemInsert(BladeUser user) throws ParseException{
		R r = new R();
		int aa=0;
		List<AnbiaoSystemRiskVO> riskVOList = riskDetailService.selectSystemRisk();
		if (riskVOList.size()>0){
			for (AnbiaoSystemRiskVO riskVO: riskVOList) {

				//???????????????
				if (Double.doubleToLongBits(Double.parseDouble(riskVO.getAnquanzerenshu())) < Double.doubleToLongBits(100.00) ){
					AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
					riskDetail.setArdDeptIds(riskVO.getDeptId());
					riskDetail.setArdMajorCategories("1");
					riskDetail.setArdSubCategory("101");
					riskDetail.setArdTitle("?????????????????????");
					riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
					riskDetail.setArdIsRectification("0");
					riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
					riskDetail.setArdAssociationField("id");
					riskDetail.setArdAssociationValue(riskVO.getJiaShiYuanId());
					riskDetail.setArdContent("??????????????????????????????");
					riskDetail.setArdType("?????????");
					riskDetail.setArdPercentage(riskVO.getAnquanzerenshu()+"%");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null) {
						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
						if (insert>0){
							aa++;
						}
					}else{
						int update = riskDetailService.getBaseMapper().updateById(riskDetail);
						if (update>0){
							aa++;
						}
					}
				}

				//?????????
				if (Double.doubleToLongBits(Double.parseDouble(riskVO.getRuzhi())) < Double.doubleToLongBits(100.00)){
					AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
					riskDetail.setArdDeptIds(riskVO.getDeptId());
					riskDetail.setArdMajorCategories("1");
					riskDetail.setArdSubCategory("103");
					riskDetail.setArdTitle("????????????");
					riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
					riskDetail.setArdIsRectification("0");
					riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
					riskDetail.setArdAssociationField("id");
					riskDetail.setArdAssociationValue(riskVO.getJiaShiYuanId());
					riskDetail.setArdContent("?????????????????????");
					riskDetail.setArdType("?????????");
					riskDetail.setArdPercentage(riskVO.getRuzhi()+"%");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null) {
						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
						if (insert>0){
							aa++;
						}
					}else{
						int update = riskDetailService.getBaseMapper().updateById(riskDetail);
						if (update>0){
							aa++;
						}
					}
				}

				//???????????????
				if (Double.doubleToLongBits(Double.parseDouble(riskVO.getWeihaigaozhishu())) < Double.doubleToLongBits(100.00)){
					AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
					riskDetail.setArdDeptIds(riskVO.getDeptId());
					riskDetail.setArdMajorCategories("1");
					riskDetail.setArdSubCategory("102");
					riskDetail.setArdTitle("?????????????????????");
					riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
					riskDetail.setArdIsRectification("0");
					riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
					riskDetail.setArdAssociationField("id");
					riskDetail.setArdAssociationValue(riskVO.getJiaShiYuanId());
					riskDetail.setArdContent("??????????????????????????????");
					riskDetail.setArdType("?????????");
					riskDetail.setArdPercentage(riskVO.getWeihaigaozhishu()+"%");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null) {
						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
						if (insert>0){
							aa++;
						}
					}else{
						int update = riskDetailService.getBaseMapper().updateById(riskDetail);
						if (update>0){
							aa++;
						}
					}
				}

				//????????????
				if ( Double.doubleToLongBits(Double.parseDouble(riskVO.getLaodonghetong())) < Double.doubleToLongBits(100.00)){
					AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
					riskDetail.setArdDeptIds(riskVO.getDeptId());
					riskDetail.setArdMajorCategories("1");
					riskDetail.setArdSubCategory("104");
					riskDetail.setArdTitle("??????????????????");
					riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
					riskDetail.setArdIsRectification("0");
					riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
					riskDetail.setArdAssociationField("id");
					riskDetail.setArdAssociationValue(riskVO.getJiaShiYuanId());
					riskDetail.setArdContent("???????????????????????????");
					riskDetail.setArdType("?????????");
					riskDetail.setArdPercentage(riskVO.getLaodonghetong()+"%");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null) {
						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
						if (insert>0){
							aa++;
						}
					}else{
						int update = riskDetailService.getBaseMapper().updateById(riskDetail);
						if (update>0){
							aa++;
						}
					}
				}

			}
		}
		if (aa>0){
			r.setSuccess(true);
			r.setCode(200);
			r.setMsg("????????????");
		}else {
			r.setSuccess(true);
			r.setCode(200);
			r.setMsg("???????????????");
		}
		return r;
	}


}


















