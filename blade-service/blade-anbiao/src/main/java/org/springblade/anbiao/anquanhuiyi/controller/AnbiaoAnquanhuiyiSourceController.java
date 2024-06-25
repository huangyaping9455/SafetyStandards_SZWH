package org.springblade.anbiao.anquanhuiyi.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiDetail;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiSource;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiService;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiSourceService;
import org.springblade.common.tool.DateUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-11-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/anquanhuiyiSource")
@Api(value = "安全会议-数据源配置", tags = "安全会议-数据源配置")
public class AnbiaoAnquanhuiyiSourceController {

	private IAnbiaoAnquanhuiyiSourceService sourceService;

	private IAnbiaoAnquanhuiyiService anquanhuiyiService;

	/**
	 *新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增")
	@ApiOperation(value = "新增", notes = "传入AnbiaoAnquanhuiyiSource")
	public R insert(@RequestBody AnbiaoAnquanhuiyiSource anquanhuiyiSource, BladeUser user) throws ParseException {
		R r = new R();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		QueryWrapper<AnbiaoAnquanhuiyiSource> anquanhuiyiSourceQueryWrapper = new QueryWrapper<>();
		anquanhuiyiSourceQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiSource::getDeptid, anquanhuiyiSource.getDeptid());
		anquanhuiyiSourceQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiSource::getIsdeleted,0);
		anquanhuiyiSourceQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiSource::getZhengwen,anquanhuiyiSource.getZhengwen());
		anquanhuiyiSourceQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiSource::getZhuti,anquanhuiyiSource.getZhuti());
		if (StringUtils.isNotBlank(anquanhuiyiSource.getHuiyijiyao()) && !anquanhuiyiSource.getHuiyijiyao().equals("null")){
			anquanhuiyiSourceQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiSource::getHuiyijiyao,anquanhuiyiSource.getHuiyijiyao());
		}
		AnbiaoAnquanhuiyiSource deail = sourceService.getBaseMapper().selectOne(anquanhuiyiSourceQueryWrapper);
		if (deail == null){
			anquanhuiyiSource.setCreatename(user.getUserName());
			anquanhuiyiSource.setCreateid(user.getUserId());
			anquanhuiyiSource.setCreatetime(DateUtil.now());
			anquanhuiyiSource.setIsdeleted(0);
			boolean i = sourceService.save(anquanhuiyiSource);
			if (i) {
				r.setMsg("新增成功");
				r.setCode(200);
				r.setSuccess(false);
			} else {
				r.setMsg("新增失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}else {
			r.setMsg("该会议已存在");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		return r;
	}

	/**
	 * 编辑
	 */
	@PostMapping("/update")
	@ApiLog("编辑")
	@ApiOperation(value = "编辑",notes = "传入AnbiaoAnquanhuiyiSource")
	public R update(@RequestBody AnbiaoAnquanhuiyiSource anquanhuiyiSource,BladeUser user){
		R r = new R();
		QueryWrapper<AnbiaoAnquanhuiyiSource> anquanhuiyiSourceQueryWrapper = new QueryWrapper<>();
		anquanhuiyiSourceQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiSource::getId,anquanhuiyiSource.getId());
		AnbiaoAnquanhuiyiSource deail = sourceService.getBaseMapper().selectOne(anquanhuiyiSourceQueryWrapper);
		if (deail != null){
			anquanhuiyiSource.setId(anquanhuiyiSource.getId());
			anquanhuiyiSource.setUpdatename(user.getUserName());
			anquanhuiyiSource.setUpdateid(user.getUserId());
			anquanhuiyiSource.setUpdatetime(DateUtil.now());
			boolean i = sourceService.updateById(anquanhuiyiSource);
			if (i) {
				r.setMsg("更新成功");
				r.setCode(200);
				r.setSuccess(false);
			} else {
				r.setMsg("更新失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}else {
			r.setMsg("该数据不存在");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		return r;
	}



	/**
	 * 删除
	 */
	@GetMapping("/deleteById")
	@ApiLog("删除")
	@ApiOperation(value = "删除", notes = "传入Id", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true),
	})
	public R deleteById( String Id, BladeUser user){
		R r = new R();
		AnbiaoAnquanhuiyiSource anquanhuiyiSource = new AnbiaoAnquanhuiyiSource();
		anquanhuiyiSource.setId(Id);
		anquanhuiyiSource.setUpdatename(user.getUserName());
		anquanhuiyiSource.setUpdateid(user.getUserId());
		anquanhuiyiSource.setUpdatetime(DateUtil.now());
		anquanhuiyiSource.setIsdeleted(1);
		boolean i = sourceService.updateById(anquanhuiyiSource);
		if (i){
			r.setMsg("删除成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}else {
			r.setMsg("删除失败");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	/**
	 * 查询详情
	 */
	@GetMapping("/detail")
	@ApiLog("详情")
	@ApiOperation(value = "详情",notes = "传入Id")
	@ApiImplicitParam(name = "Id",value = "数据ID",required = true)
	public R detail(String Id){
		R r = new R();
		AnbiaoAnquanhuiyiSource anquanhuiyiSourceInfo = sourceService.getById(Id);
		if(anquanhuiyiSourceInfo != null){
			r.setData(anquanhuiyiSourceInfo);
			r.setCode(200);
			r.setMsg("获取成功");
			return r;
		}else {
			r.setCode(500);
			r.setMsg("暂无数据");
			return r;
		}
	}

	/**
	 * 分页
	 */
	@PostMapping("/getAnquanhuiyiSourcePage")
	@ApiLog("分页-列表")
	@ApiOperation(value = "分页-列表",notes = "传入AnQuanHuiYiPage")
	public R<AnQuanHuiYiPage<AnbiaoAnquanhuiyiSource>> getAnquanhuiyiSourcePage(@RequestBody AnQuanHuiYiPage anQuanHuiYiPage) {
		R r = new R();
		AnQuanHuiYiPage<AnbiaoAnquanhuiyiSource> list = sourceService.selectGetAll(anQuanHuiYiPage);
		return R.data(list);
	}


}
