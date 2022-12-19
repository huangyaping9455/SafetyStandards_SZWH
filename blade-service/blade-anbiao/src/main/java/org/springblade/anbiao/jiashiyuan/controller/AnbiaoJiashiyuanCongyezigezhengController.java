package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanCongyezigezheng;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanCongyezigezhengService;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetailInfo;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailInfoService;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import org.springblade.common.tool.DateUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 * 驾驶员从业资格证信息 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan/congyezigezheng")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--从业资格证信息", tags = "驾驶员资料管理--从业资格证信息")
public class AnbiaoJiashiyuanCongyezigezhengController {

	private IAnbiaoJiashiyuanCongyezigezhengService congyezigezhengService;
	private IAnbiaoRiskDetailService riskDetailService;
	private IAnbiaoRiskDetailInfoService detailInfoService;
	private IJiaShiYuanService jiaShiYuanService;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-从业资格证信息")
	@ApiOperation(value = "新增-从业资格证信息", notes = "传入AnbiaoJiashiyuanCongyezigezheng", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanCongyezigezheng congyezigezheng, BladeUser user) throws ParseException {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanCongyezigezheng>();
		congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, congyezigezheng.getAjcAjIds());
		congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, "0");
		AnbiaoJiashiyuanCongyezigezheng deail = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);

//		//验证初次发证日期
//		String s = congyezigezheng.getAjcInitialIssuing().substring(0,10);
//		if (StringUtils.isNotBlank(s) && !s.equals("null")){
//			if (DateUtils.isDateString(s,null) == true){
//				congyezigezheng.setAjcInitialIssuing(s);
//			}else {
//				r.setMsg(congyezigezheng.getAjcInitialIssuing()+",该初次发证日期，不是时间格式；");
//				r.setCode(500);
//				r.setSuccess(false);
//				return r;
//			}
//		}

		//验证发证日期
//		if (congyezigezheng.getAjcIssueDate().length()>=10){
//			String s1 = congyezigezheng.getAjcIssueDate().substring(0,10);
//			if (StringUtils.isNotBlank(s1) && !s1.equals("null")){
//				if (DateUtils.isDateString(s1,null) == true){
//					congyezigezheng.setAjcIssueDate(s1);
//				}else {
//					r.setMsg(congyezigezheng.getAjcIssueDate()+",该发证日期，不是时间格式；");
//					r.setCode(500);
//					r.setSuccess(false);
//					return r;
//				}
//			}
//		}


		//验证有效期
		if (congyezigezheng.getAjcValidUntil().length()>=10){
			String s2 = congyezigezheng.getAjcValidUntil().substring(0,10);
			if (StringUtils.isNotBlank(s2) && !s2.equals("null")){
				if (DateUtils.isDateString(s2,null) == true){
					congyezigezheng.setAjcValidUntil(s2);
				}else {
					r.setMsg(congyezigezheng.getAjcValidUntil()+",该有效期，不是时间格式；");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		}

		//验证 发证日期 不能大于 有效期
		if(StringUtils.isNotBlank(congyezigezheng.getAjcIssueDate()) && !congyezigezheng.getAjcIssueDate().equals("null") && StringUtils.isNotBlank(congyezigezheng.getAjcValidUntil()) && !congyezigezheng.getAjcValidUntil().equals("null")){
			int a1 = congyezigezheng.getAjcIssueDate().length();
			int b1 = congyezigezheng.getAjcValidUntil().length();
			if (a1 == b1){
				if (a1 <= 10){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if(DateUtils.belongCalendar(format.parse(congyezigezheng.getAjcIssueDate()),format.parse(congyezigezheng.getAjcValidUntil()))==false){
						r.setMsg("从业资格证发放日期,不能大于有效期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
				if(a1 > 10){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(DateUtils.belongCalendar(format.parse(congyezigezheng.getAjcIssueDate()),format.parse(congyezigezheng.getAjcValidUntil()))==false){
						r.setMsg("从业资格证发放日期,不能大于有效期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}else{
				r.setMsg("从业资格证发放日期与有效期,时间格式不一致;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证下次年审日期
//		if (congyezigezheng.getAjcNextAnnualReview().length()>=10){
//			String s3 = congyezigezheng.getAjcNextAnnualReview().substring(0,10);
//			if (StringUtils.isNotBlank(s3) && !s3.equals("null")){
//				if (DateUtils.isDateString(s3,null) == true){
//					congyezigezheng.setAjcNextAnnualReview(s3);
//				}else {
//					r.setMsg(congyezigezheng.getAjcNextAnnualReview()+",该下次年审日期，不是时间格式；");
//					r.setCode(500);
//					r.setSuccess(false);
//					return r;
//				}
//			}
//		}

		if(deail == null){
			if(user != null){
				congyezigezheng.setAjcCreateByName(user.getUserName());
				congyezigezheng.setAjcCreateByIds(user.getUserId().toString());
			}else{
				congyezigezheng.setAjcCreateByName(congyezigezheng.getAjcCreateByName());
				congyezigezheng.setAjcCreateByIds(congyezigezheng.getAjcCreateByIds());
			}
			congyezigezheng.setAjcInitialIssuing(DateUtil.now());
			congyezigezheng.setAjcCreateTime(DateUtil.now());
			congyezigezheng.setAjcDelete("0");
			congyezigezheng.setAjcAjIds(congyezigezheng.getAjcAjIds());
			return R.status(congyezigezhengService.save(congyezigezheng));
		}else{
			if(user != null){
				congyezigezheng.setAjcUpdateByName(user.getUserName());
				congyezigezheng.setAjcUpdateByIds(user.getUserId().toString());
			}else{
				congyezigezheng.setAjcUpdateByName(congyezigezheng.getAjcUpdateByName());
				congyezigezheng.setAjcUpdateByIds(congyezigezheng.getAjcUpdateByIds());
			}
			congyezigezheng.setAjcUpdateTime(DateUtil.now());

			//从业资格证有效期风险
			AnbiaoRiskDetailInfo anbiaoRiskDetailInfo3 = new AnbiaoRiskDetailInfo();
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper3 = new QueryWrapper<>();
			riskDetailQueryWrapper3.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,congyezigezheng.getAjcAjIds());
			riskDetailQueryWrapper3.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper3.lambda().eq(AnbiaoRiskDetail::getArdTitle,"从业资格证有效截止日期");
			List<AnbiaoRiskDetail> anbiaoRiskDetails = riskDetailService.getBaseMapper().selectList(riskDetailQueryWrapper3);
			for (AnbiaoRiskDetail riskDetail3 : anbiaoRiskDetails) {
				if (riskDetail3 != null && StringUtils.isNotBlank(congyezigezheng.getAjcValidUntil()) && !congyezigezheng.getAjcValidUntil().equals("null")) {
					riskDetail3.setArdIsRectification("1");
					riskDetail3.setArdRectificationByIds(user.getUserId().toString());
					riskDetail3.setArdRectificationByName(user.getUserName());
					riskDetail3.setArdRectificationDate(DateUtil.now());
					riskDetail3.setArdModularName("从业资格证有效截止日期");
					riskDetail3.setArdRectificationField("congyezhengyouxiaoqi");
					riskDetail3.setArdRectificationValue(congyezigezheng.getAjcValidUntil());
					riskDetail3.setArdRectificationFieldType("String");
					riskDetail3.setArdRectificationEnclosure(congyezigezheng.getAjcLicence());
					boolean b = riskDetailService.updateById(riskDetail3);
					if (b == true) {
						//整改内容
						anbiaoRiskDetailInfo3.setArdRiskIds(riskDetail3.getArdIds().toString());
						anbiaoRiskDetailInfo3.setArdRectificationByIds(user.getUserId().toString());
						anbiaoRiskDetailInfo3.setArdRectificationByName(user.getUserName());
						anbiaoRiskDetailInfo3.setArdRectificationDate(DateUtil.now());
						anbiaoRiskDetailInfo3.setArdRectificationField("congyezhengyouxiaoqi");
						anbiaoRiskDetailInfo3.setArdRectificationValue(congyezigezheng.getAjcValidUntil());
						anbiaoRiskDetailInfo3.setArdRectificationFieldType("String");
						detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo3);
					}
				}
			}

			JiaShiYuan jiaShiYuan = new JiaShiYuan();
			jiaShiYuan.setId(congyezigezheng.getAjcAjIds());
			jiaShiYuan.setCongyezhengfujian(congyezigezheng.getAjcLicence());
			jiaShiYuan.setCongyezhengchulingri(congyezigezheng.getAjcInitialIssuing());
			jiaShiYuan.setCongyezhengyouxiaoqi(congyezigezheng.getAjcValidUntil());
			jiaShiYuanService.updateById(jiaShiYuan);

			return R.status(congyezigezhengService.updateById(congyezigezheng));
		}
	}

}
