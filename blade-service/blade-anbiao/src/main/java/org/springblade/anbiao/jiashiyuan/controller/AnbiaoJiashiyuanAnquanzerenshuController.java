package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanAnquanzerenshu;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanCongyezigezheng;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanAnquanzerenshuService;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanCongyezigezhengService;
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
 * 驾驶员安全责任书 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan/anquanzerenshu")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--安全责任书信息", tags = "驾驶员资料管理--安全责任书信息")
public class AnbiaoJiashiyuanAnquanzerenshuController {

	private IAnbiaoJiashiyuanAnquanzerenshuService anquanzerenshuService;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-安全责任书信息")
	@ApiOperation(value = "新增-安全责任书信息", notes = "传入AnbiaoJiashiyuanAnquanzerenshu", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanAnquanzerenshu anquanzerenshu, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu>();
		anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaAjIds, anquanzerenshu.getAjaAjIds());
		anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaDelete, "0");
		AnbiaoJiashiyuanAnquanzerenshu deail = anquanzerenshuService.getBaseMapper().selectOne(anquanzerenshuQueryWrapper);
		if(deail == null){
			if(user != null){
				anquanzerenshu.setAjaCreateByName(user.getUserName());
				anquanzerenshu.setAjaCreateByIds(user.getUserId().toString());
			}else{
				anquanzerenshu.setAjaCreateByName(anquanzerenshu.getAjaCreateByName());
				anquanzerenshu.setAjaCreateByIds(anquanzerenshu.getAjaCreateByIds());
			}
			anquanzerenshu.setAjaAutographTime(DateUtil.now());
			anquanzerenshu.setAjaCreateTime(DateUtil.now());
			anquanzerenshu.setAjaDelete("0");
			return R.status(anquanzerenshuService.save(anquanzerenshu));
		}else{
			if(user != null){
				anquanzerenshu.setAjaUpdateByName(user.getUserName());
				anquanzerenshu.setAjaUpdateByIds(user.getUserId().toString());
			}else{
				anquanzerenshu.setAjaUpdateByName(anquanzerenshu.getAjaUpdateByName());
				anquanzerenshu.setAjaUpdateByIds(anquanzerenshu.getAjaUpdateByIds());
			}
			anquanzerenshu.setAjaUpdateTime(DateUtil.now());
			return R.status(anquanzerenshuService.updateById(anquanzerenshu));
		}
	}

}
