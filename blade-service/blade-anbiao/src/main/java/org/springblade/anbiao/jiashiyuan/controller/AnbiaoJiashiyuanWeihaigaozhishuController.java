package org.springblade.anbiao.jiashiyuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanTijian;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanWeihaigaozhishu;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanTijianService;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanWeihaigaozhishuService;
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
 * 驾驶员危害告知书 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan/weihaigaozhi")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--危害告知书信息", tags = "驾驶员资料管理--危害告知书信息")
public class AnbiaoJiashiyuanWeihaigaozhishuController {

	private IAnbiaoJiashiyuanWeihaigaozhishuService weihaigaozhishuService;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-危害告知书信息")
	@ApiOperation(value = "新增-危害告知书信息", notes = "传入AnbiaoJiashiyuanWeihaigaozhishu", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishu, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu> weihaigaozhishuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu>();
		weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwAjIds, weihaigaozhishu.getAjwAjIds());
		weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwDelete, "0");
		AnbiaoJiashiyuanWeihaigaozhishu deail =weihaigaozhishuService.getBaseMapper().selectOne(weihaigaozhishuQueryWrapper);
		if(deail == null){
			if(user != null){
				weihaigaozhishu.setAjwCreateByName(user.getUserName());
				weihaigaozhishu.setAjwCreateByIds(user.getUserId().toString());
			}else{
				weihaigaozhishu.setAjwCreateByName(weihaigaozhishu.getAjwCreateByName());
				weihaigaozhishu.setAjwCreateByIds(weihaigaozhishu.getAjwCreateByIds());
			}
			weihaigaozhishu.setAjwCreateTime(LocalDateTime.now());
			weihaigaozhishu.setAjwDelete("0");
			return R.status(weihaigaozhishuService.save(weihaigaozhishu));
		}else{
			if(user != null){
				weihaigaozhishu.setAjwUpdateByName(user.getUserName());
				weihaigaozhishu.setAjwUpdateByIds(user.getUserId().toString());
			}else{
				weihaigaozhishu.setAjwUpdateByName(weihaigaozhishu.getAjwUpdateByName());
				weihaigaozhishu.setAjwUpdateByIds(weihaigaozhishu.getAjwUpdateByIds());
			}
			weihaigaozhishu.setAjwUpdateTime(LocalDateTime.now());
			return R.status(weihaigaozhishuService.updateById(weihaigaozhishu));
		}
	}
}
