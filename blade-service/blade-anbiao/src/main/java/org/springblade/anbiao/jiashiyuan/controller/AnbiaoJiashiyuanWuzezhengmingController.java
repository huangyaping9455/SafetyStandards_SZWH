package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanWeihaigaozhishu;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanWuzezhengming;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanWeihaigaozhishuService;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanWuzezhengmingService;
import org.springblade.anbiao.risk.controller.AnbiaoRiskDetailController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * <p>
 * 驾驶员无责证明表 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan/wuzezhengming")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--无责证明书信息", tags = "驾驶员资料管理--无责证明书信息")
public class AnbiaoJiashiyuanWuzezhengmingController {

	private IAnbiaoJiashiyuanWuzezhengmingService wuzezhengmingService;
	@Autowired
	private AnbiaoRiskDetailController riskDetailController;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-无责证明书信息")
	@ApiOperation(value = "新增-无责证明书信息", notes = "传入AnbiaoJiashiyuanWuzezhengming", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanWuzezhengming wuzezhengming, BladeUser user) throws ParseException {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanWuzezhengming> wuzezhengmingQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWuzezhengming>();
		wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwAjIds, wuzezhengming.getAjwAjIds());
		wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwDelete, "0");
		AnbiaoJiashiyuanWuzezhengming deail = wuzezhengmingService.getBaseMapper().selectOne(wuzezhengmingQueryWrapper);
		if (deail == null) {
			if (user != null) {
				wuzezhengming.setAjwCreateByName(user.getUserName());
				wuzezhengming.setAjwCreateByIds(user.getUserId().toString());
			} else {
				wuzezhengming.setAjwCreateByName(wuzezhengming.getAjwCreateByName());
				wuzezhengming.setAjwCreateByIds(wuzezhengming.getAjwCreateByIds());
			}
			wuzezhengming.setAjwDate(DateUtil.now());
			wuzezhengming.setAjwCreateTime(DateUtil.now());
			wuzezhengming.setAjwDelete("0");
			return R.status(wuzezhengmingService.save(wuzezhengming));
		} else {
			if (user != null) {
				wuzezhengming.setAjwUpdateByName(user.getUserName());
				wuzezhengming.setAjwUpdateByIds(user.getUserId().toString());
			} else {
				wuzezhengming.setAjwUpdateByName(wuzezhengming.getAjwUpdateByName());
				wuzezhengming.setAjwUpdateByIds(wuzezhengming.getAjwUpdateByIds());
			}
			wuzezhengming.setAjwUpdateTime(DateUtil.now());
			wuzezhengmingService.updateById(wuzezhengming);

			String jiashiyuanId = wuzezhengming.getAjwAjIds();
			riskDetailController.jiashiyuanWuZeZhengMingRiskinsert(jiashiyuanId,user);

			return R.status(wuzezhengmingService.updateById(wuzezhengming));
		}
	}
}
