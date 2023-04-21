package org.springblade.anbiao.yinhuanpaicha.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiangDept;
import org.springblade.anbiao.yinhuanpaicha.page.YinhuanpaichaXiangPage;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoYinhuanpaichaXiangDeptInfoService;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoYinhuanpaichaXiangDeptService;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoYinhuanpaichaXiangService;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoYinhuanpaichaXiangDeptVO;
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
@RequestMapping("/anbiao/anbiaoYinhuanpaichaXiangDept")
@Api(value = "隐患排查企业权限", tags = "隐患排查企业权限")
public class AnbiaoYinhuanpaichaXiangDeptController {

	private IAnbiaoYinhuanpaichaXiangService iAnbiaoYinhuanpaichaXiangService;

	private IAnbiaoYinhuanpaichaXiangDeptService iAnbiaoYinhuanpaichaXiangDeptService;

	private IAnbiaoYinhuanpaichaXiangDeptInfoService iAnbiaoYinhuanpaichaXiangDeptInfoService;

	@PostMapping("/saveYinhuanpaichaXiangDept")
	@ApiLog("隐患排查企业权限-新增企业隐患权限")
	@ApiOperation(value = "隐患排查企业权限-新增企业隐患权限", notes = "传入anbiaoYinhuanpaichaXiang", position = 1)
	public R saveYinhuanpaichaXiangDept(@RequestBody AnbiaoYinhuanpaichaXiangDept anbiaoYinhuanpaichaXiangDept, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		anbiaoYinhuanpaichaXiangDept.setCreateid(user.getUserId());
		anbiaoYinhuanpaichaXiangDept.setIsdelete(0);
		anbiaoYinhuanpaichaXiangDept.setCreatetime(DateUtil.now());
		boolean ii = false;
		//企业
		String[] idsss = anbiaoYinhuanpaichaXiangDept.getDeptid().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss = listid.toArray(new String[1]);

		//隐患项
		String[] xiang_idsss = anbiaoYinhuanpaichaXiangDept.getXiangid().split(",");
		//去除素组中重复的数组
		List<String> xiang_listid = new ArrayList<String>();
		for (int p=0; p<xiang_idsss.length; p++) {
			if(!xiang_listid.contains(xiang_idsss[p])) {
				xiang_listid.add(xiang_idsss[p]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] xiang_idss = xiang_listid.toArray(new String[1]);

		for(int i = 0;i< idss.length;i++){
			anbiaoYinhuanpaichaXiangDept.setDeptid(idss[i]);
			for(int q = 0;q< xiang_idss.length;q++){
				anbiaoYinhuanpaichaXiangDept.setXiangid(xiang_idss[q]);

				QueryWrapper<AnbiaoYinhuanpaichaXiangDept> unitWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiangDept>();
				unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDept::getDeptid, anbiaoYinhuanpaichaXiangDept.getDeptid());
				unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDept::getXiangid, anbiaoYinhuanpaichaXiangDept.getXiangid());
				unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDept::getIsdelete, 0);
				AnbiaoYinhuanpaichaXiangDept deail = iAnbiaoYinhuanpaichaXiangDeptService.getBaseMapper().selectOne(unitWrapper);
				if(deail == null){
					ii = iAnbiaoYinhuanpaichaXiangDeptService.save(anbiaoYinhuanpaichaXiangDept);
					if (ii == true) {
						rs.setCode(200);
						rs.setSuccess(true);
						rs.setMsg("新增成功");
					} else {
						rs.setCode(500);
						rs.setSuccess(false);
						rs.setMsg("新增失败");
					}
//					if(ii == true) {
//						QueryWrapper<AnbiaoYinhuanpaichaXiang> queryWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiang>();
//						queryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getId, anbiaoYinhuanpaichaXiangDept.getXiangid());
//						queryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getIsdelete, 0);
//						AnbiaoYinhuanpaichaXiang deailInfo = iAnbiaoYinhuanpaichaXiangService.getBaseMapper().selectOne(queryWrapper);
//						if (deailInfo.getIsdrawupplan() == 1) {
//							AnbiaoYinhuanpaichaXiangDeptInfo anbiaoYinhuanpaichaXiangDeptInfo = new AnbiaoYinhuanpaichaXiangDeptInfo();
//							anbiaoYinhuanpaichaXiangDeptInfo.setCreateid(user.getUserId());
//							anbiaoYinhuanpaichaXiangDeptInfo.setIsdelete(0);
//							anbiaoYinhuanpaichaXiangDeptInfo.setCreatetime(DateUtil.now());
//
//							QueryWrapper<AnbiaoYinhuanpaichaXiangDeptInfo> infoQueryWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiangDeptInfo>();
//							infoQueryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getDeptid, anbiaoYinhuanpaichaXiangDept.getDeptid());
//							infoQueryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getDataid, anbiaoYinhuanpaichaXiangDept.getXiangid());
//							infoQueryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getIsdelete, 0);
//							AnbiaoYinhuanpaichaXiangDeptInfo deptInfo = iAnbiaoYinhuanpaichaXiangDeptInfoService.getBaseMapper().selectOne(infoQueryWrapper);
//							if (deptInfo == null) {
//								anbiaoYinhuanpaichaXiangDeptInfo.setDeptid(anbiaoYinhuanpaichaXiangDept.getDeptid());
//								anbiaoYinhuanpaichaXiangDeptInfo.setDataid(anbiaoYinhuanpaichaXiangDept.getXiangid());
//								ii = iAnbiaoYinhuanpaichaXiangDeptInfoService.save(anbiaoYinhuanpaichaXiangDeptInfo);
//								if (ii == true) {
//									rs.setCode(200);
//									rs.setSuccess(true);
//									rs.setMsg("新增成功");
//								} else {
//									rs.setCode(500);
//									rs.setSuccess(false);
//									rs.setMsg("新增失败");
//								}
//							}
//						}else{
//							rs.setCode(200);
//							rs.setSuccess(true);
//							rs.setMsg("新增成功");
//						}
//					}
				}else{
					anbiaoYinhuanpaichaXiangDept.setId(deail.getId());
					ii = iAnbiaoYinhuanpaichaXiangDeptService.updateById(anbiaoYinhuanpaichaXiangDept);
					if (ii == true) {
						rs.setCode(200);
						rs.setSuccess(true);
						rs.setMsg("新增成功");
					} else {
						rs.setCode(500);
						rs.setSuccess(false);
						rs.setMsg("新增失败");
					}
//					if(ii == true) {
//						QueryWrapper<AnbiaoYinhuanpaichaXiang> queryWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiang>();
//						queryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getId, anbiaoYinhuanpaichaXiangDept.getXiangid());
//						queryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getIsdelete, 0);
//						AnbiaoYinhuanpaichaXiang deailInfo = iAnbiaoYinhuanpaichaXiangService.getBaseMapper().selectOne(queryWrapper);
//						if (deailInfo.getIsdrawupplan() == 1) {
//							AnbiaoYinhuanpaichaXiangDeptInfo anbiaoYinhuanpaichaXiangDeptInfo = new AnbiaoYinhuanpaichaXiangDeptInfo();
//							anbiaoYinhuanpaichaXiangDeptInfo.setCreateid(user.getUserId());
//							anbiaoYinhuanpaichaXiangDeptInfo.setIsdelete(0);
//							anbiaoYinhuanpaichaXiangDeptInfo.setCreatetime(DateUtil.now());
//
//							QueryWrapper<AnbiaoYinhuanpaichaXiangDeptInfo> infoQueryWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiangDeptInfo>();
//							infoQueryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getDeptid, anbiaoYinhuanpaichaXiangDept.getDeptid());
//							infoQueryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getDataid, anbiaoYinhuanpaichaXiangDept.getId().toString());
//							infoQueryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getIsdelete, 0);
//							AnbiaoYinhuanpaichaXiangDeptInfo deptInfo = iAnbiaoYinhuanpaichaXiangDeptInfoService.getBaseMapper().selectOne(infoQueryWrapper);
//							if (deptInfo == null) {
//								anbiaoYinhuanpaichaXiangDeptInfo.setDeptid(anbiaoYinhuanpaichaXiangDept.getDeptid());
//								anbiaoYinhuanpaichaXiangDeptInfo.setDataid(anbiaoYinhuanpaichaXiangDept.getId().toString());
//								ii = iAnbiaoYinhuanpaichaXiangDeptInfoService.save(anbiaoYinhuanpaichaXiangDeptInfo);
//								if (ii == true) {
//									rs.setCode(200);
//									rs.setSuccess(true);
//									rs.setMsg("新增成功");
//								} else {
//									rs.setCode(500);
//									rs.setSuccess(false);
//									rs.setMsg("新增失败");
//								}
//							}
//						}else{
//							rs.setCode(200);
//							rs.setSuccess(true);
//							rs.setMsg("新增成功");
//						}
//					}
				}
			}
		}
		rs.setCode(200);
		rs.setSuccess(true);
		rs.setMsg("新增成功");
		return rs;
	}

	@PostMapping("/updateYinhuanpaichaXiangDept")
	@ApiLog("隐患排查企业权限-编辑企业隐患权限")
	@ApiOperation(value = "隐患排查企业权限-编辑企业隐患权限", notes = "传入anbiaoYinhuanpaichaXiang", position = 2)
	public R updateYinhuanpaichaXiangDept(@RequestBody AnbiaoYinhuanpaichaXiangDept anbiaoYinhuanpaichaXiangDept, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}

		boolean ii = false;
		String[] xiang_idsss = anbiaoYinhuanpaichaXiangDept.getXiangid().split(",");
		//去除素组中重复的数组
		List<String> xiang_listid = new ArrayList<String>();
		for (int p=0; p<xiang_idsss.length; p++) {
			if(!xiang_listid.contains(xiang_idsss[p])) {
				xiang_listid.add(xiang_idsss[p]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] xiang_idss = xiang_listid.toArray(new String[1]);
		for(int q = 0;q< xiang_idss.length;q++){
			anbiaoYinhuanpaichaXiangDept.setXiangid(xiang_idss[q]);

			QueryWrapper<AnbiaoYinhuanpaichaXiangDept> unitWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiangDept>();
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDept::getXiangid, anbiaoYinhuanpaichaXiangDept.getXiangid());
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDept::getDeptid, anbiaoYinhuanpaichaXiangDept.getDeptid());
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDept::getIsdelete, 0);
			AnbiaoYinhuanpaichaXiangDept deail = iAnbiaoYinhuanpaichaXiangDeptService.getBaseMapper().selectOne(unitWrapper);
			if(deail == null){
				anbiaoYinhuanpaichaXiangDept.setCreateid(user.getUserId());
				anbiaoYinhuanpaichaXiangDept.setIsdelete(0);
				anbiaoYinhuanpaichaXiangDept.setCreatetime(DateUtil.now());
				ii = iAnbiaoYinhuanpaichaXiangDeptService.save(anbiaoYinhuanpaichaXiangDept);
			}else{
				anbiaoYinhuanpaichaXiangDept.setUpdateid(user.getUserId());
				anbiaoYinhuanpaichaXiangDept.setIsdelete(0);
				anbiaoYinhuanpaichaXiangDept.setUpdatetime(DateUtil.now());
				ii = iAnbiaoYinhuanpaichaXiangDeptService.updateById(anbiaoYinhuanpaichaXiangDept);
			}
			if(ii == true){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("编辑成功");
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("编辑失败");
			}

		}
		return rs;
	}

	@GetMapping("/removeYinhuanpaichaXiangDept")
	@ApiOperation(value = "隐患排查企业权限-删除企业隐患权限", notes = "传入Id", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true) })
	public R removeYinhuanpaichaXiangDept(Integer Id, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		AnbiaoYinhuanpaichaXiangDept anbiaoYinhuanpaichaXiangDept = new AnbiaoYinhuanpaichaXiangDept();
		anbiaoYinhuanpaichaXiangDept.setUpdateid(user.getUserId());
		anbiaoYinhuanpaichaXiangDept.setIsdelete(1);
		anbiaoYinhuanpaichaXiangDept.setUpdatetime(DateUtil.now());
		anbiaoYinhuanpaichaXiangDept.setId(Id);
		return R.data(iAnbiaoYinhuanpaichaXiangDeptService.updateById(anbiaoYinhuanpaichaXiangDept));
	}

	@PostMapping("/getYinhuanpaichaXiangDeptPage")
	@ApiLog("隐患排查项-分页列表")
	@ApiOperation(value = "隐患排查项-分页列表", notes = "传入yinhuanpaichaXiangPage", position = 4)
	public R<YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptVO>> getYinhuanpaichaXiangDeptPage(@RequestBody YinhuanpaichaXiangPage yinhuanpaichaXiangPage, BladeUser user) {
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
		YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptVO> pages = iAnbiaoYinhuanpaichaXiangDeptService.selectYinhuanpaichaXiangDeptPage(yinhuanpaichaXiangPage);
		return R.data(pages);
	}


}
