package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanQita;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanTijian;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanQitaService;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanTijianService;
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
import java.time.LocalDateTime;

/**
 * <p>
 * 驾驶员体检信息表 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan/tijian")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--体检信息", tags = "驾驶员资料管理--体检信息")
public class AnbiaoJiashiyuanTijianController {

	private IAnbiaoJiashiyuanTijianService tijianService;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-体检信息")
	@ApiOperation(value = "新增-体检信息", notes = "传入AnbiaoJiashiyuanTijian", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanTijian tijian, BladeUser user) throws ParseException {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanTijian>();
		tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, tijian.getAjtAjIds());
		tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, "0");
		AnbiaoJiashiyuanTijian deail = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);

		//验证体检日期
		if (tijian.getAjtPhysicalExaminationDate().length() >= 10) {
			String s = tijian.getAjtPhysicalExaminationDate().substring(0, 10);
			if (StringUtils.isNotBlank(s) && !s.equals("null")) {
				if (DateUtils.isDateString(s, null) == true) {
					tijian.setAjtPhysicalExaminationDate(s);
				} else {
					r.setMsg(tijian.getAjtPhysicalExaminationDate() + ",该体检日期，不是时间格式；");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		} else {
			r.setMsg(tijian.getAjtPhysicalExaminationDate() + ",该体检日期，不是时间格式；");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}

		//验证体检有效期
		if (tijian.getAjtTermValidity().length() >= 10) {
			String s1 = tijian.getAjtTermValidity().substring(0, 10);
			if (StringUtils.isNotBlank(s1) && !s1.equals("null")) {
				if (DateUtils.isDateString(s1, null) == true) {
					tijian.setAjtTermValidity(s1);
				} else {
					r.setMsg(tijian.getAjtTermValidity() + ",该体检有效期，不是时间格式；");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		} else {
			r.setMsg(tijian.getAjtTermValidity() + ",该体检有效期，不是时间格式；");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}

		//验证 验证体检日期 不能大于 验证体检有效期
		if (StringUtils.isNotBlank(tijian.getAjtPhysicalExaminationDate()) && !tijian.getAjtPhysicalExaminationDate().equals("null") && StringUtils.isNotBlank(tijian.getAjtTermValidity()) && !tijian.getAjtTermValidity().equals("null")) {
			int a1 = tijian.getAjtPhysicalExaminationDate().length();
			int b1 = tijian.getAjtTermValidity().length();
			if (a1 == b1) {
				if (a1 <= 10) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if (DateUtils.belongCalendar(format.parse(tijian.getAjtPhysicalExaminationDate()), format.parse(tijian.getAjtTermValidity())) == false) {
						r.setMsg("体检日期,不能大于体检有效期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
					if (a1 > 10) {
						SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (DateUtils.belongCalendar(format2.parse(tijian.getAjtPhysicalExaminationDate()), format2.parse(tijian.getAjtTermValidity())) == false) {
							r.setMsg("体检日期,不能大于体检有效期;");
							r.setCode(500);
							r.setSuccess(false);
							return r;
						}
					} else {
						r.setMsg("体检日期与体检有效期,时间格式不一致;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}

		}

				if (deail == null) {
					if (user != null) {
						tijian.setAjtCreateByName(user.getUserName());
						tijian.setAjtCreateByIds(user.getUserId().toString());
					} else {
						tijian.setAjtCreateByName(tijian.getAjtCreateByName());
						tijian.setAjtCreateByIds(tijian.getAjtCreateByIds());
					}
					tijian.setAjtCreateTime(DateUtil.now());
					tijian.setAjtDelete("0");
					return R.status(tijianService.save(tijian));
				} else {
					if (user != null) {
						tijian.setAjtUpdateByName(user.getUserName());
						tijian.setAjtUpdateByIds(user.getUserId().toString());
					} else {
						tijian.setAjtUpdateByName(tijian.getAjtUpdateByName());
						tijian.setAjtUpdateByIds(tijian.getAjtUpdateByIds());
					}
					tijian.setAjtUpdateTime(DateUtil.now());
					return R.status(tijianService.updateById(tijian));

				}
			}
		}

