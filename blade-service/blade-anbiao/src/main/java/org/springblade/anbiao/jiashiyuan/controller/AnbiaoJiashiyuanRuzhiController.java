package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanRuzhi;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanRuzhiService;
import org.springblade.anbiao.risk.controller.AnbiaoRiskDetailController;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import org.springblade.common.tool.DateUtils;
import org.springblade.common.tool.IdCardUtil;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 驾驶员入职登记表 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan/ruzhi")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--入职登记表", tags = "驾驶员资料管理--入职登记表")
public class AnbiaoJiashiyuanRuzhiController {

	@Autowired
	private IAnbiaoJiashiyuanRuzhiService ruzhiService;

	@Autowired
	private AnbiaoRiskDetailController riskDetailController;


	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-入职登记表")
	@ApiOperation(value = "新增-入职登记表", notes = "传入AnbiaoJiashiyuanRuzhi", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanRuzhi ruzhi, BladeUser user) throws ParseException {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
		ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, ruzhi.getAjrAjIds());
		ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
		AnbiaoJiashiyuanRuzhi deail = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
		SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");

		//验证毕业日期
		if (ruzhi.getAjrGraduationDate() != null){
			if (ruzhi.getAjrGraduationDate().length()>=10){
				String s = ruzhi.getAjrGraduationDate().substring(0,10);
				if (StringUtils.isNotBlank(s) && !s.equals("null")){
					if (DateUtils.isDateString(s,null) == true){
						ruzhi.setAjrGraduationDate(s);
					}else {
						r.setMsg(ruzhi.getAjrGraduationDate()+",该有毕业日期，不是时间格式；");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}
		}

		//验证领取驾照日期
		if (ruzhi.getAjrReceiveDrivingLicense() != null) {
			if (ruzhi.getAjrReceiveDrivingLicense().length() >= 10) {
				String s1 = ruzhi.getAjrReceiveDrivingLicense().substring(0, 10);
				if (StringUtils.isNotBlank(s1) && !s1.equals("null")) {
					if (DateUtils.isDateString(s1, null) == true) {
						ruzhi.setAjrReceiveDrivingLicense(s1);
					} else {
						r.setMsg(ruzhi.getAjrReceiveDrivingLicense() + ",该领取驾照日期，不是时间格式；");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}
		}

		if(deail == null){
			if(user != null){
				ruzhi.setAjrCreateByName(user.getUserName());
				ruzhi.setAjrCreateByIds(user.getUserId().toString());
			}else{
				ruzhi.setAjrCreateByName(ruzhi.getAjrCreateByName());
				ruzhi.setAjrCreateByIds(ruzhi.getAjrCreateByIds());
			}
			//通过身份证获取年龄
			Integer age = IdCardUtil.getAgeByCard(ruzhi.getAjrIdNumber());
			ruzhi.setAjrAge(age);
			//通过身份证获取生日日期
			Date chushengshijian = IdCardUtil.getBirthDate(ruzhi.getAjrIdNumber());
			ruzhi.setAjrBirth(dateFormat2.format(chushengshijian));
			ruzhi.setAjrApproverTime(DateUtil.now());
			ruzhi.setAjrCreateTime(DateUtil.now());
			ruzhi.setAjrDelete("0");
			return R.status(ruzhiService.save(ruzhi));
		}else{
			if(user != null){
				ruzhi.setAjrUpdateByName(user.getUserName());
				ruzhi.setAjrUpdateByIds(user.getUserId().toString());
			}else{
				ruzhi.setAjrUpdateByName(ruzhi.getAjrUpdateByName());
				ruzhi.setAjrUpdateByIds(ruzhi.getAjrUpdateByIds());
			}
			ruzhi.setAjrUpdateTime(DateUtil.now());
			String jiashiyuanId = ruzhi.getAjrAjIds();
			riskDetailController.jiashiyuanRuZhiRiskinsert(jiashiyuanId,user);
			return R.status(ruzhiService.updateById(ruzhi));
		}
	}

	/**
	 * 入职登记信息--审批
	 * @update: 李明昊 添加审批
	 * @param
	 */
	@PostMapping("/driverAudit")
	@ApiLog("入职登记信息--审批")
	@ApiOperation(value = "入职登记信息--审批", notes = "ajrId", position = 10)
	public R driverAudit( String ajrId,BladeUser user ){
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanRuzhi> jiashiyuanRuzhiQueryWrapper = new QueryWrapper<>();
		jiashiyuanRuzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrIds,ajrId);
		jiashiyuanRuzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrApproverStatus,"0");
		AnbiaoJiashiyuanRuzhi deail = ruzhiService.getBaseMapper().selectOne(jiashiyuanRuzhiQueryWrapper);

		if (deail != null){
			if (deail.getAjrApproverAutograph() != null){
				deail.setAjrApproverStatus("1");
				deail.setAjrApproverName(user.getUserName());
				deail.setAjrApproverTime(DateUtil.now());
				return R.status(ruzhiService.updateById(deail));
			}else {
				r.setMsg("审批后请签名");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}else {
			r.setMsg("无入职登记信息可审批");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	/**
	 * 头像更改
	 */
	@PostMapping("/headPortrait")
	@ApiLog("头像更改")
	@ApiOperation(value = "头像更改", notes = "headPortrait", position = 10)
	public R driverAudit( String headPortrait,String ajrId,BladeUser user ){
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanRuzhi> jiashiyuanRuzhiQueryWrapper = new QueryWrapper<>();
		jiashiyuanRuzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds,ajrId);
		jiashiyuanRuzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
		AnbiaoJiashiyuanRuzhi deail = ruzhiService.getBaseMapper().selectOne(jiashiyuanRuzhiQueryWrapper);
		deail.setAjrHeadPortrait(headPortrait);
		deail.setAjrUpdateByName(user.getUserName());
		deail.setAjrUpdateByIds(user.getUserId().toString());
		deail.setAjrUpdateTime(DateUtil.now());
		deail.setAjrIds(deail.getAjrIds());
		return R.status(ruzhiService.updateById(deail));
	}
}
