package org.springblade.anbiao.jiashiyuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanAnquanzerenshu;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanChengxinkaohe;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanAnquanzerenshuService;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanChengxinkaoheService;
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
 * 驾驶员诚信考核记录 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan/chengxinkaohe")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--诚信考核记录信息", tags = "驾驶员资料管理--诚信考核记录信息")
public class AnbiaoJiashiyuanChengxinkaoheController {

	private IAnbiaoJiashiyuanChengxinkaoheService chengxinkaoheService;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-诚信考核记录信息")
	@ApiOperation(value = "新增-诚信考核记录信息", notes = "传入AnbiaoJiashiyuanChengxinkaohe", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanChengxinkaohe chengxinkaohe, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanChengxinkaohe> chengxinkaoheQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanChengxinkaohe>();
		chengxinkaoheQueryWrapper.lambda().eq(AnbiaoJiashiyuanChengxinkaohe::getAjcAjIds, chengxinkaohe.getAjcAjIds());
		chengxinkaoheQueryWrapper.lambda().eq(AnbiaoJiashiyuanChengxinkaohe::getAjcDelete, "0");
		AnbiaoJiashiyuanChengxinkaohe deail = chengxinkaoheService.getBaseMapper().selectOne(chengxinkaoheQueryWrapper);
		if(deail == null){
			if(user != null){
				chengxinkaohe.setAjcCreateByName(user.getUserName());
				chengxinkaohe.setAjcCreateByIds(user.getUserId().toString());
			}else{
				chengxinkaohe.setAjcCreateByName(chengxinkaohe.getAjcCreateByName());
				chengxinkaohe.setAjcCreateByIds(chengxinkaohe.getAjcCreateByIds());
			}
			chengxinkaohe.setAjcCreateTime(LocalDateTime.now());
			chengxinkaohe.setAjcDelete("0");
			return R.status(chengxinkaoheService.save(chengxinkaohe));
		}else{
			if(user != null){
				chengxinkaohe.setAjcUpdateByName(user.getUserName());
				chengxinkaohe.setAjcUpdateByIds(user.getUserId().toString());
			}else{
				chengxinkaohe.setAjcUpdateByName(chengxinkaohe.getAjcUpdateByName());
				chengxinkaohe.setAjcUpdateByIds(chengxinkaohe.getAjcUpdateByIds());
			}
			chengxinkaohe.setAjcUpdateTime(LocalDateTime.now());
			return R.status(chengxinkaoheService.updateById(chengxinkaohe));
		}
	}
}
