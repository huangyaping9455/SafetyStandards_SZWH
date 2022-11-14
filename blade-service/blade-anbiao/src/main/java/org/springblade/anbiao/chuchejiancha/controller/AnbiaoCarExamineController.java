package org.springblade.anbiao.chuchejiancha.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamine;
import org.springblade.anbiao.chuchejiancha.entity.FileCarExamineParse;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineService;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineVO;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/carExamine")
@Api(value = "隐患排查-出车检查项配置", tags = "隐患排查-出车检查项配置")
public class AnbiaoCarExamineController extends BladeController {

	private IAnbiaoCarExamineService iAnbiaoCarExamineService;

	private FileCarExamineParse fileParse;

	@PostMapping("/saveYinhuanpaichaXiang")
	@ApiLog("隐患排查-出车检查项配置-新增")
	@ApiOperation(value = "隐患排查-出车检查项配置-新增", notes = "传入anbiaoCarExamine", position = 1)
	public R saveCarExamineXiang(@RequestBody AnbiaoCarExamine anbiaoCarExamine, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(401);
			return rs;
		}
		anbiaoCarExamine.setCreateid(user.getUserId().toString());
		anbiaoCarExamine.setIsdelete(0);
		anbiaoCarExamine.setCreatetime(DateUtil.now());
		anbiaoCarExamine.setStatus(1);

		QueryWrapper<AnbiaoCarExamine> examineQueryWrapper = new QueryWrapper<AnbiaoCarExamine>();
		examineQueryWrapper.lambda().eq(AnbiaoCarExamine::getName, anbiaoCarExamine.getName());
		examineQueryWrapper.lambda().eq(AnbiaoCarExamine::getType, anbiaoCarExamine.getType());
		examineQueryWrapper.lambda().eq(AnbiaoCarExamine::getStatus, 1);
		examineQueryWrapper.lambda().eq(AnbiaoCarExamine::getDeptid, anbiaoCarExamine.getDeptid());
		examineQueryWrapper.lambda().eq(AnbiaoCarExamine::getIsdelete, 0);
		AnbiaoCarExamine deail = iAnbiaoCarExamineService.getBaseMapper().selectOne(examineQueryWrapper);
		if(deail == null){
			//查询最大id
			Integer newId = iAnbiaoCarExamineService.selectMaxId()+1;
			anbiaoCarExamine.setId(newId);
			QueryWrapper<AnbiaoCarExamine> carExamineQueryWrapper = new QueryWrapper<AnbiaoCarExamine>();
			carExamineQueryWrapper.lambda().eq(AnbiaoCarExamine::getId, anbiaoCarExamine.getParentid());
			AnbiaoCarExamine carExamine = iAnbiaoCarExamineService.getBaseMapper().selectOne(carExamineQueryWrapper);
			if(carExamine != null){
				anbiaoCarExamine.setTreecode(carExamine.getTreecode()+"-"+newId);
			}else{
				if(anbiaoCarExamine.getParentid() == 0){
					anbiaoCarExamine.setTreecode("0-"+newId);
				}
			}

			boolean ii = iAnbiaoCarExamineService.save(anbiaoCarExamine);
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
			rs.setMsg("该项名称已存在");
		}
		return rs;
	}

	@PostMapping("/updateCarExamineXiang")
	@ApiLog("隐患排查-出车检查项配置-编辑")
	@ApiOperation(value = "隐患排查-出车检查项配置-编辑", notes = "传入anbiaoCarExamine", position = 2)
	public R updateCarExamineXiang(@RequestBody AnbiaoCarExamine anbiaoCarExamine, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(401);
			return rs;
		}
		anbiaoCarExamine.setUpdateid(user.getUserId().toString());
		anbiaoCarExamine.setIsdelete(0);
		anbiaoCarExamine.setUpdatetime(DateUtil.now());
		QueryWrapper<AnbiaoCarExamine> carExamineQueryWrapper = new QueryWrapper<AnbiaoCarExamine>();
		carExamineQueryWrapper.lambda().eq(AnbiaoCarExamine::getId, anbiaoCarExamine.getParentid());
		AnbiaoCarExamine carExamine = iAnbiaoCarExamineService.getBaseMapper().selectOne(carExamineQueryWrapper);
		if(carExamine != null){
			anbiaoCarExamine.setTreecode(carExamine.getTreecode()+"-"+anbiaoCarExamine.getId());
		}else{
			if(anbiaoCarExamine.getParentid() == 0){
				anbiaoCarExamine.setTreecode("0-"+anbiaoCarExamine.getId());
			}
		}
		return R.data(iAnbiaoCarExamineService.updateById(anbiaoCarExamine));
	}

	@GetMapping("/removeCarExamineXiang")
	@ApiOperation(value = "隐患排查-出车检查项配置-删除", notes = "传入Id", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true) })
	public R removeCarExamineXiang(Integer Id, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(401);
			return rs;
		}
		AnbiaoCarExamine anbiaoCarExamine = new AnbiaoCarExamine();
		anbiaoCarExamine.setUpdateid(user.getUserId().toString());
		anbiaoCarExamine.setUpdatetime(DateUtil.now());
		anbiaoCarExamine.setIsdelete(1);
		anbiaoCarExamine.setId(Id);
		return R.data(iAnbiaoCarExamineService.updateById(anbiaoCarExamine));
	}

	@GetMapping("/updateCarExamineXiangStatus")
	@ApiOperation(value = "隐患排查-出车检查项配置-禁用", notes = "传入Id", position = 4)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true) })
	public R updateCarExamineXiangStatus(Integer Id, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(401);
			return rs;
		}
		AnbiaoCarExamine anbiaoCarExamine = new AnbiaoCarExamine();
		anbiaoCarExamine.setUpdateid(user.getUserId().toString());
		anbiaoCarExamine.setUpdatetime(DateUtil.now());
		anbiaoCarExamine.setStatus(2);
		anbiaoCarExamine.setId(Id);
		return R.data(iAnbiaoCarExamineService.updateById(anbiaoCarExamine));
	}

	@GetMapping("/getCarExamineMTree")
	@ApiLog("隐患排查-出车检查项配置-获取目录树")
	@ApiOperation(value = "隐患排查-出车检查项配置-获取目录树", notes = "传入deptId", position = 5)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "机构id", required = true),
		@ApiImplicitParam(name = "parentId", value = "父级ID", required = false),
		@ApiImplicitParam(name = "name", value = "名称", required = false)
	})
	public R<List<AnbiaoCarExamineVO>> getCarExamineMTree(@RequestParam Integer deptId, Integer parentId, String name, BladeUser user) {
		R rs = new R();
//		if(user == null){
//			rs.setMsg("未授权");
//			rs.setCode(500);
//			return rs;
//		}
		List<AnbiaoCarExamineVO> list= iAnbiaoCarExamineService.getCarExamineMTree(1,null,parentId,name,null);
		return R.data(list);
	}

//	@GetMapping("/getCarExamineMTreeMuBan")
//	@ApiLog("隐患排查-出车检查项配置-获取模板数据")
//	@ApiOperation(value = "隐患排查-出车检查项配置-获取模板数据", notes = "传入deptId", position = 6)
//	@ApiImplicitParam(name = "deptId", value = "单位id", required = true)
//	public R<List<AnbiaoCarExamineVO>> getCarExamineMTreeMuBan(@RequestParam Integer deptId, BladeUser user) {
//		R rs = new R();
//		if(user == null){
//			rs.setMsg("未授权");
//			rs.setCode(500);
//			return rs;
//		}
//		List<AnbiaoCarExamineVO> list= iAnbiaoCarExamineService.getCarExamineMTreeMuBan(deptId);
//		return R.data(list);
//	}
//
//	@PostMapping(value = "/getQYWDList")
//	@ApiLog("隐患排查-出车检查项配置-未生成的企业")
//	@ApiOperation(value = "隐患排查-出车检查项配置-未生成的企业", notes = "传入deptId",position = 7)
//	@ApiImplicitParam(name = "deptId", value = "单位id", required = true)
//	public R getQYWDList(@RequestParam Integer deptId, BladeUser user) throws IOException {
//		R rs = new R();
//		if(user == null){
//			rs.setMsg("未授权");
//			rs.setCode(500);
//			return rs;
//		}
//		return R.data(iAnbiaoCarExamineService.selectGetQYWD(deptId));
//	}
//
//	@PostMapping("/aKeyGeneration")
//	@ApiLog("隐患排查-出车检查项配置-一键生成")
//	@ApiOperation(value = "隐患排查-出车检查项配置-一键生成", notes = "传入单位id", position = 8)
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "deptIds", value = "机构id", required = true)
//	})
//	public R aKeyGeneration(String deptIds,String leixingid, BladeUser user) {
//		R rs = new R();
//		if(user == null){
//			rs.setMsg("未授权");
//			rs.setCode(500);
//			return rs;
//		}
//		List<AnbiaoCarExamineVO> mubanList;
//		mubanList = iAnbiaoCarExamineService.getCarExamineMTree(1,Integer.parseInt(leixingid),null,null,null);
//
//		String[] idsss = deptIds.split(",");
//		//去除素组中重复的数组
//		List<String> listid = new ArrayList<String>();
//		for (int i=0; i<idsss.length; i++) {
//			if(!listid.contains(idsss[i])) {
//				listid.add(idsss[i]);
//			}
//		}
//		//返回一个包含所有对象的指定类型的数组
//		String[] idss = listid.toArray(new String[1]);
//		for(int i = 0;i< idss.length;i++){
//			QueryWrapper<AnbiaoCarExamine> examineQueryWrapper = new QueryWrapper<AnbiaoCarExamine>();
//			examineQueryWrapper.lambda().eq(AnbiaoCarExamine::getStatus, 1);
//			examineQueryWrapper.lambda().eq(AnbiaoCarExamine::getDeptid, idss[i]);
//			examineQueryWrapper.lambda().eq(AnbiaoCarExamine::getIsdelete, 0);
//			AnbiaoCarExamine deail = iAnbiaoCarExamineService.getBaseMapper().selectOne(examineQueryWrapper);
//			//如果改机构已有文件，则跳过
//			if(deail != null){
//				return R.success("该机构已有文件");
//			}
//
//			int maxId = iAnbiaoCarExamineService.selectMaxId()+1;
//			fileParse.setId(maxId);
//			fileParse.setDeptId(Integer.parseInt(idss[i]));
//			fileParse.setCreateid(user.getUserId().toString());
//			String tier = "0";
//			fileParse.parseAbcdMubanList(mubanList, Integer.parseInt(leixingid),tier);
//			List<AnbiaoCarExamine> list = fileParse.getAbcdList();
//			fileParse.close();
//
//			boolean ii = iAnbiaoCarExamineService.saveBatch(list);
//			if(ii == true){
//				rs.setCode(200);
//				rs.setSuccess(true);
//				rs.setMsg("生成成功");
//			}else{
//				rs.setCode(500);
//				rs.setSuccess(false);
//				rs.setMsg("生成失败");
//			}
//		}
//		return rs;
//	}
//
//	@PostMapping("/getCarExamineDeptList")
//	@ApiLog("隐患排查-出车检查项配置-已生成企业列表")
//	@ApiOperation(value = "隐患排查-出车检查项配置-已生成企业列表", notes = "传入传入chuchejianchaXiangPage", position = 9)
//	public R<YinhuanpaichaXiangPage<AnbiaoCarExamineVO>> getCarExamineDeptList(@RequestBody YinhuanpaichaXiangPage chuchejianchaXiangPage, BladeUser user) {
//		R rs = new R();
//		if(user == null){
//			rs.setMsg("未授权");
//			rs.setCode(500);
//			return rs;
//		}
//		YinhuanpaichaXiangPage<AnbiaoCarExamineVO> list= iAnbiaoCarExamineService.selectCarExamineDeptListPage(chuchejianchaXiangPage);
//		return R.data(list);
//	}
//
//	@GetMapping("/removeCarExamineDept")
//	@ApiOperation(value = "隐患排查-出车检查项配置-已生成企业删除", notes = "传入Id", position = 3)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "deptId", value = "企业Id", required = true) })
//	public R removeCarExamineDept(Integer deptId, BladeUser user) {
//		R rs = new R();
//		if(user == null){
//			rs.setMsg("未授权");
//			rs.setCode(500);
//			return rs;
//		}
////		AnbiaoCarExamine anbiaoCarExamine = new AnbiaoCarExamine();
////		anbiaoCarExamine.setUpdateid(user.getUserId().toString());
////		anbiaoCarExamine.setUpdatetime(DateUtil.now());
////		anbiaoCarExamine.setIsdelete(1);
////		anbiaoCarExamine.setDeptid(deptId);
//		return R.data(iAnbiaoCarExamineService.deleteYingjiyanlian(deptId));
//	}



}
