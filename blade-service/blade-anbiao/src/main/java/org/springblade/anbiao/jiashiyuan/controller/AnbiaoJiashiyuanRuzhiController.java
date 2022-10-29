package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanRuzhi;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanRuzhiService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

	private IAnbiaoJiashiyuanRuzhiService ruzhiService;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-入职登记表")
	@ApiOperation(value = "新增-入职登记表", notes = "传入AnbiaoJiashiyuanRuzhi", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanRuzhi ruzhi, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
		ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrIds, ruzhi.getAjrIds());
		ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
		AnbiaoJiashiyuanRuzhi deail = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
		if(deail == null){
			if(user != null){
				ruzhi.setAjrCreateByName(user.getUserName());
				ruzhi.setAjrCreateByIds(user.getUserId().toString());
			}else{
				ruzhi.setAjrCreateByName(ruzhi.getAjrCreateByName());
				ruzhi.setAjrCreateByIds(ruzhi.getAjrCreateByIds());
			}
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
			return R.status(ruzhiService.updateById(ruzhi));
		}
	}


}
