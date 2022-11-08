package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanCongyezigezheng;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanCongyezigezhengService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-从业资格证信息")
	@ApiOperation(value = "新增-从业资格证信息", notes = "传入AnbiaoJiashiyuanCongyezigezheng", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanCongyezigezheng congyezigezheng, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanCongyezigezheng>();
		congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, congyezigezheng.getAjcAjIds());
		congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, "0");
		AnbiaoJiashiyuanCongyezigezheng deail = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
		if(deail == null){
			if(user != null){
				congyezigezheng.setAjcCreateByName(user.getUserName());
				congyezigezheng.setAjcCreateByIds(user.getUserId().toString());
			}else{
				congyezigezheng.setAjcCreateByName(congyezigezheng.getAjcCreateByName());
				congyezigezheng.setAjcCreateByIds(congyezigezheng.getAjcCreateByIds());
			}
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
			return R.status(congyezigezhengService.updateById(congyezigezheng));
		}
	}

}
