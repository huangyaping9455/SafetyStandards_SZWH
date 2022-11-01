package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanJiashizheng;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanJiashizhengService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
	public R insert(@RequestBody AnbiaoJiashiyuanJiashizheng jiashizheng, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
		jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, jiashizheng.getAjjAjIds());
		jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
		AnbiaoJiashiyuanJiashizheng deail = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
		if(deail == null){
			if(user != null){
				jiashizheng.setAjjCreateByName(user.getUserName());
				jiashizheng.setAjjCreateByIds(user.getUserId().toString());
			}else{
				jiashizheng.setAjjCreateByName(jiashizheng.getAjjCreateByName());
				jiashizheng.setAjjCreateByIds(jiashizheng.getAjjCreateByIds());
			}
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
