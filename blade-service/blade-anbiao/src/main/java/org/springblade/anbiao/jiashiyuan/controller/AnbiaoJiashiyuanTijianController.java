package org.springblade.anbiao.jiashiyuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanQita;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanTijian;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanQitaService;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanTijianService;
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
	public R insert(@RequestBody AnbiaoJiashiyuanTijian tijian, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanTijian>();
		tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, tijian.getAjtAjIds());
		tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, "0");
		AnbiaoJiashiyuanTijian deail =tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
		if(deail == null){
			if(user != null){
				tijian.setAjtCreateByName(user.getUserName());
				tijian.setAjtCreateByIds(user.getUserId().toString());
			}else{
				tijian.setAjtCreateByName(tijian.getAjtCreateByName());
				tijian.setAjtCreateByIds(tijian.getAjtCreateByIds());
			}
			tijian.setAjtCreateTime(LocalDateTime.now());
			tijian.setAjtDelete("0");
			return R.status(tijianService.save(tijian));
		}else{
			if(user != null){
				tijian.setAjtUpdateByName(user.getUserName());
				tijian.setAjtUpdateByIds(user.getUserId().toString());
			}else{
				tijian.setAjtUpdateByName(tijian.getAjtUpdateByName());
				tijian.setAjtUpdateByIds(tijian.getAjtUpdateByIds());
			}
			tijian.setAjtUpdateTime(LocalDateTime.now());
			return R.status(tijianService.updateById(tijian));
		}
	}
}
