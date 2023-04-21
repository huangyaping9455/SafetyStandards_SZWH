package org.springblade.anbiao.yinhuanpaicha.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiang;
import org.springblade.anbiao.yinhuanpaicha.page.YinhuanpaichaXiangPage;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoYinhuanpaichaXiangService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-09-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/anbiaoYinhuanpaichaXiang")
@Api(value = "隐患排查项", tags = "隐患排查项")
public class AnbiaoYinhuanpaichaXiangController {

	private IAnbiaoYinhuanpaichaXiangService iAnbiaoYinhuanpaichaXiangService;

	@PostMapping("/saveYinhuanpaichaXiang")
	@ApiLog("隐患排查项-新增隐患项")
	@ApiOperation(value = "隐患排查项-新增隐患项", notes = "传入anbiaoYinhuanpaichaXiang", position = 1)
	public R saveYinhuanpaichaXiang(@RequestBody AnbiaoYinhuanpaichaXiang anbiaoYinhuanpaichaXiang, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		anbiaoYinhuanpaichaXiang.setCreateid(user.getUserId());
		anbiaoYinhuanpaichaXiang.setIsdelete(0);
		anbiaoYinhuanpaichaXiang.setCreatetime(DateUtil.now());

		//隐患规则
		String[] idsss = anbiaoYinhuanpaichaXiang.getJudgerules().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss = listid.toArray(new String[1]);

		//分数
		String[] score_idsss = anbiaoYinhuanpaichaXiang.getScore().split(",");
		//去除素组中重复的数组
		List<String> score_listid = new ArrayList<String>();
		for (int i=0; i<score_idsss.length; i++) {
			if(!score_listid.contains(score_idsss[i])) {
				score_listid.add(score_idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] score_idss = score_listid.toArray(new String[1]);

		for(int i = 0;i< idss.length;i++){
			QueryWrapper<AnbiaoYinhuanpaichaXiang> unitWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiang>();
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getName, anbiaoYinhuanpaichaXiang.getName());
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getType, anbiaoYinhuanpaichaXiang.getType());
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getJudgerules, idss[i]);
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getIsdelete, 0);
			AnbiaoYinhuanpaichaXiang deail = iAnbiaoYinhuanpaichaXiangService.getBaseMapper().selectOne(unitWrapper);
			if(deail == null){
				anbiaoYinhuanpaichaXiang.setJudgerules(idss[i]);
				for(int p = 0;p< score_idss.length;p++){
					anbiaoYinhuanpaichaXiang.setScore(score_idss[i]);
				}
				boolean ii = iAnbiaoYinhuanpaichaXiangService.save(anbiaoYinhuanpaichaXiang);
				if(ii == true){
					rs.setCode(200);
					rs.setSuccess(true);
					rs.setMsg("新增成功");
				}else{
					rs.setCode(500);
					rs.setSuccess(false);
					rs.setMsg("新增失败");
				}
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("该类型隐患已存在");
			}
		}
		return rs;
	}

	@PostMapping("/updateYinhuanpaichaXiang")
	@ApiLog("隐患排查项-编辑隐患项")
	@ApiOperation(value = "隐患排查项-编辑隐患项", notes = "传入anbiaoYinhuanpaichaXiang", position = 2)
	public R updateYinhuanpaichaXiang(@RequestBody AnbiaoYinhuanpaichaXiang anbiaoYinhuanpaichaXiang, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		anbiaoYinhuanpaichaXiang.setUpdateid(user.getUserId());
		anbiaoYinhuanpaichaXiang.setIsdelete(0);
		anbiaoYinhuanpaichaXiang.setUpdatetime(DateUtil.now());
		return R.data(iAnbiaoYinhuanpaichaXiangService.updateById(anbiaoYinhuanpaichaXiang));
	}

	@GetMapping("/removeYinhuanpaichaXiang")
	@ApiOperation(value = "隐患排查项-删除隐患项", notes = "传入Id", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true) })
	public R remove(Integer Id, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		AnbiaoYinhuanpaichaXiang anbiaoYinhuanpaichaXiang = new AnbiaoYinhuanpaichaXiang();
		anbiaoYinhuanpaichaXiang.setUpdateid(user.getUserId());
		anbiaoYinhuanpaichaXiang.setIsdelete(1);
		anbiaoYinhuanpaichaXiang.setUpdatetime(DateUtil.now());
		anbiaoYinhuanpaichaXiang.setId(Id);
		return R.data(iAnbiaoYinhuanpaichaXiangService.updateById(anbiaoYinhuanpaichaXiang));
	}

	@PostMapping("/getYinhuanpaichaXiangPage")
	@ApiLog("隐患排查项-分页列表")
	@ApiOperation(value = "隐患排查项-分页列表", notes = "传入yinhuanpaichaXiangPage", position = 4)
	public R<YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiang>> getYinhuanpaichaXiangPage(@RequestBody YinhuanpaichaXiangPage yinhuanpaichaXiangPage, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		//排序条件
		////默认发起时间降序
		if(yinhuanpaichaXiangPage.getOrderColumns()==null){
			yinhuanpaichaXiangPage.setOrderColumn("createtime");
		}else{
			yinhuanpaichaXiangPage.setOrderColumn(yinhuanpaichaXiangPage.getOrderColumns());
		}
		YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiang> pages = iAnbiaoYinhuanpaichaXiangService.selectYinhuanpaichaXiangPage(yinhuanpaichaXiangPage);
		return R.data(pages);
	}



}
