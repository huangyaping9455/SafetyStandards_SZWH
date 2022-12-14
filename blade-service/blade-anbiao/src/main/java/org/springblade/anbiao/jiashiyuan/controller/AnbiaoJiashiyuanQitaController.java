package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanLaodonghetong;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanQita;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanLaodonghetongService;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanQitaService;
import org.springblade.common.tool.DateUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 * 驾驶员其他信息表 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan/qita")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--其他信息", tags = "驾驶员资料管理--其他信息")
public class AnbiaoJiashiyuanQitaController {

	private IAnbiaoJiashiyuanQitaService qitaService;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-其他信息")
	@ApiOperation(value = "新增-其他信息", notes = "传入AnbiaoJiashiyuanQita", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanQita qita, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanQita>();
		qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtAjIds, qita.getAjtAjIds());
		qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtDelete, "0");
		AnbiaoJiashiyuanQita deail =qitaService.getBaseMapper().selectOne(qitaQueryWrapper);

//
//		//验证上传日期
//		String s = qita.getAjtPhysicalExaminationDate().substring(0,10);
//		if (StringUtils.isNotBlank(s) && !s.equals("null")){
//			if (DateUtils.isDateString(s,null) == true){
//				qita.setAjtPhysicalExaminationDate(s);
//			}else {
//				r.setMsg(qita.getAjtPhysicalExaminationDate()+",该上传日期，不是时间格式；");
//				r.setCode(500);
//				r.setSuccess(false);
//				return r;
//			}
//		}

		if(deail == null){
			if(user != null){
				qita.setAjtCreateByName(user.getUserName());
				qita.setAjtCreateByIds(user.getUserId().toString());
			}else{
				qita.setAjtCreateByName(qita.getAjtCreateByName());
				qita.setAjtCreateByIds(qita.getAjtCreateByIds());
			}
			qita.setAjtPhysicalExaminationDate(DateUtil.now());
			qita.setAjtCreateTime(DateUtil.now());
			qita.setAjtDelete("0");
			return R.status(qitaService.save(qita));
		}else{
			if(user != null){
				qita.setAjtUpdateByName(user.getUserName());
				qita.setAjtUpdateByIds(user.getUserId().toString());
			}else{
				qita.setAjtUpdateByName(qita.getAjtUpdateByName());
				qita.setAjtUpdateByIds(qita.getAjtUpdateByIds());
			}
			qita.setAjtUpdateTime(DateUtil.now());
			return R.status(qitaService.updateById(qita));
		}
	}
}
