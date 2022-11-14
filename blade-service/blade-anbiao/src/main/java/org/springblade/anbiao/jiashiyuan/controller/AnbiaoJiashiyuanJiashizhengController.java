package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanJiashizheng;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanJiashizhengService;
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

/**
 * <p>
 * 驾驶员驾驶证信息 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan/jiashizheng")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--驾驶证信息", tags = "驾驶员资料管理--驾驶证信息")
public class AnbiaoJiashiyuanJiashizhengController {

	private IAnbiaoJiashiyuanJiashizhengService jiashizhengService;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-驾驶证信息")
	@ApiOperation(value = "新增-驾驶证信息", notes = "传入AnbiaoJiashiyuanJiashizheng", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanJiashizheng jiashizheng, BladeUser user) throws ParseException {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
		jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, jiashizheng.getAjjAjIds());
		jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
		AnbiaoJiashiyuanJiashizheng deail = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);

//		//初次领证日期
//		String s = jiashizheng.getAjjFirstIssue().substring(0,10);
//		if (StringUtils.isNotBlank(s) && !s.equals("null")){
//			if (DateUtils.isDateString(s,null) == true){
//				jiashizheng.setAjjFirstIssue(s);
//			}else {
//				r.setMsg(jiashizheng.getAjjFirstIssue()+",该初次领证日期，不是时间格式；");
//				r.setCode(500);
//				r.setSuccess(false);
//				return r;
//			}
//		}

		//有效期开始日期
		if (jiashizheng.getAjjValidPeriodStart().length()>=10){
			String s1 = jiashizheng.getAjjValidPeriodStart().substring(0,10);
			if (StringUtils.isNotBlank(s1) && !s1.equals("null")){
				if (DateUtils.isDateString(s1,null) == true){
					jiashizheng.setAjjValidPeriodStart(s1);
				}else {
					r.setMsg(jiashizheng.getAjjValidPeriodStart()+",该有效期开始日期，不是时间格式；");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		}

		//有效期结束日期
		if (jiashizheng.getAjjValidPeriodEnd().length()>=10){
			String s2 = jiashizheng.getAjjValidPeriodEnd().substring(0,10);
			if (StringUtils.isNotBlank(s2) && !s2.equals("null")){
				if (DateUtils.isDateString(s2,null) == true){
					jiashizheng.setAjjValidPeriodEnd(s2);
				}else {
					r.setMsg(jiashizheng.getAjjValidPeriodEnd()+",该有效期结束日期，不是时间格式；");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		}

		//验证 有效期开始日期 不能大于 有效期结束日期
		if(StringUtils.isNotBlank(jiashizheng.getAjjValidPeriodStart()) && !jiashizheng.getAjjValidPeriodStart().equals("null") && StringUtils.isNotBlank(jiashizheng.getAjjValidPeriodEnd()) && !jiashizheng.getAjjValidPeriodEnd().equals("null")){
			int a1 = jiashizheng.getAjjValidPeriodStart().length();
			int b1 = jiashizheng.getAjjValidPeriodEnd().length();
			if (a1 == b1){
				if (a1 <= 10){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if(DateUtils.belongCalendar(format.parse(jiashizheng.getAjjValidPeriodStart()),format.parse(jiashizheng.getAjjValidPeriodEnd()))==false){
						r.setMsg("驾驶证有效期开始日期,不能大于有效期结束日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
				if(a1 > 10){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(DateUtils.belongCalendar(format.parse(jiashizheng.getAjjValidPeriodStart()),format.parse(jiashizheng.getAjjValidPeriodEnd()))==false){
						r.setMsg("驾驶证有效期开始日期,不能大于有效期结束日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}else{
				r.setMsg("驾驶证有效期开始日期与有效期结束日期,时间格式不一致;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证下次年审日期
		if (jiashizheng.getAjjNextAnnualReview().length() >= 10) {
			String s3 = jiashizheng.getAjjNextAnnualReview().substring(0,10);
			if (StringUtils.isNotBlank(s3) && !s3.equals("null")){
				if (DateUtils.isDateString(s3,null) == true){
					jiashizheng.setAjjNextAnnualReview(s3);
				}else {
					r.setMsg(jiashizheng.getAjjNextAnnualReview()+",该下次年审日期，不是时间格式；");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		}

		if(deail == null){
			if(user != null){
				jiashizheng.setAjjCreateByName(user.getUserName());
				jiashizheng.setAjjCreateByIds(user.getUserId().toString());
			}else{
				jiashizheng.setAjjCreateByName(jiashizheng.getAjjCreateByName());
				jiashizheng.setAjjCreateByIds(jiashizheng.getAjjCreateByIds());
			}
			jiashizheng.setAjjFirstIssue(DateUtil.now());
			jiashizheng.setAjjCreateTime(DateUtil.now());
			jiashizheng.setAjjDelete("0");
			return R.status(jiashizhengService.save(jiashizheng));
		}else{
			if(user != null){
				jiashizheng.setAjjUpdateByName(user.getUserName());
				jiashizheng.setAjjUpdateByIds(user.getUserId().toString());
			}else{
				jiashizheng.setAjjUpdateByName(jiashizheng.getAjjUpdateByName());
				jiashizheng.setAjjUpdateByIds(jiashizheng.getAjjUpdateByIds());
			}
			jiashizheng.setAjjUpdateTime(DateUtil.now());
			return R.status(jiashizhengService.updateById(jiashizheng));
		}
	}


}
