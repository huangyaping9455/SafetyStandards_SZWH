package org.springblade.anbiao.jiashiyuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanGangqianpeixun;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanLaodonghetong;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanGangqianpeixunService;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanLaodonghetongService;
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
 * 驾驶员劳动合同 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan/laodonghetong")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--劳动合同信息", tags = "驾驶员资料管理--劳动合同信息")
public class AnbiaoJiashiyuanLaodonghetongController {

	private IAnbiaoJiashiyuanLaodonghetongService laodonghetongService;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-劳动合同信息")
	@ApiOperation(value = "新增-劳动合同信息", notes = "传入AnbiaoJiashiyuanLaodonghetong", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanLaodonghetong laodonghetong, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanLaodonghetong> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanLaodonghetong>();
		gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwAjIds, laodonghetong.getAjwAjIds());
		gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwDelete, "0");
		AnbiaoJiashiyuanLaodonghetong deail = laodonghetongService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
		if(deail == null){
			if(user != null){
				laodonghetong.setAjwCreateByName(user.getUserName());
				laodonghetong.setAjwCreateByIds(user.getUserId().toString());
			}else{
				laodonghetong.setAjwCreateByName(laodonghetong.getAjwCreateByName());
				laodonghetong.setAjwCreateByIds(laodonghetong.getAjwCreateByIds());
			}
			laodonghetong.setAjwCreateTime(LocalDateTime.now());
			laodonghetong.setAjwDelete("0");
			return R.status(laodonghetongService.save(laodonghetong));
		}else{
			if(user != null){
				laodonghetong.setAjwUpdateByName(user.getUserName());
				laodonghetong.setAjwUpdateByIds(user.getUserId().toString());
			}else{
				laodonghetong.setAjwUpdateByName(laodonghetong.getAjwUpdateByName());
				laodonghetong.setAjwUpdateByIds(laodonghetong.getAjwUpdateByIds());
			}
			laodonghetong.setAjwUpdateTime(LocalDateTime.now());
			return R.status(laodonghetongService.updateById(laodonghetong));
		}
	}

}
