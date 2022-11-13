package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanGangqianpeixun;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanGangqianpeixunService;
import org.springblade.common.tool.DateUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 驾驶员岗前培训表 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan/gangqianpeixun")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--岗前培训信息", tags = "驾驶员资料管理--岗前培训信息")
public class AnbiaoJiashiyuanGangqianpeixunController {

	private IAnbiaoJiashiyuanGangqianpeixunService gangqianpeixunService;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-岗前培训信息")
	@ApiOperation(value = "新增-岗前培训信息", notes = "传入AnbiaoJiashiyuanGangqianpeixun", position = 1)
	public R insert(@RequestBody AnbiaoJiashiyuanGangqianpeixun gangqianpeixun, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanGangqianpeixun>();
		gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, gangqianpeixun.getAjgAjIds());
		gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgDelete, "0");
		AnbiaoJiashiyuanGangqianpeixun deail = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);

		//验证培训日期
		if (gangqianpeixun.getAjgTrainingDate().length()>=10){
			String s = gangqianpeixun.getAjgTrainingDate().substring(0,10);
			if (StringUtils.isNotBlank(s) && !s.equals("null")){
				if (DateUtils.isDateString(s,null) == true){
					gangqianpeixun.setAjgTrainingDate(s);
				}else {
					r.setMsg(gangqianpeixun.getAjgTrainingDate()+",该岗前培训日期，不是时间格式；");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		}else {
			r.setMsg(gangqianpeixun.getAjgTrainingDate()+",该岗前培训日期，不是时间格式；");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}


		if(deail == null){
			if(user != null){
				gangqianpeixun.setAjgCreateByName(user.getUserName());
				gangqianpeixun.setAjgCreateByIds(user.getUserId().toString());
			}else{
				gangqianpeixun.setAjgCreateByName(gangqianpeixun.getAjgCreateByName());
				gangqianpeixun.setAjgCreateByIds(gangqianpeixun.getAjgCreateByIds());
			}
			gangqianpeixun.setAjgCreateTime(DateUtil.now());
			gangqianpeixun.setAjgDelete("0");
			return R.status(gangqianpeixunService.save(gangqianpeixun));
		}else{
			if(user != null){
				gangqianpeixun.setAjgUpdateByName(user.getUserName());
				gangqianpeixun.setAjgUpdateByIds(user.getUserId().toString());
			}else{
				gangqianpeixun.setAjgUpdateByName(gangqianpeixun.getAjgUpdateByName());
				gangqianpeixun.setAjgUpdateByIds(gangqianpeixun.getAjgUpdateByIds());
			}
			gangqianpeixun.setAjgUpdateTime(DateUtil.now());
			return R.status(gangqianpeixunService.updateById(gangqianpeixun));
		}
	}

}
