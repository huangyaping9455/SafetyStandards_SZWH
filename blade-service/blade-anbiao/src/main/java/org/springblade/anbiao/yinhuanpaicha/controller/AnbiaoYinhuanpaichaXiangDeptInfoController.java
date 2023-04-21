package org.springblade.anbiao.yinhuanpaicha.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.Baoxianxinxi;
import org.springblade.anbiao.cheliangguanli.entity.Cheliangbaoxian;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.service.IBaoxianxinxiService;
import org.springblade.anbiao.cheliangguanli.service.ICheliangbaoxianService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.jinritixing.entity.Jinritixing;
import org.springblade.anbiao.jinritixing.entity.YinHuanDept;
import org.springblade.anbiao.jinritixing.entity.YinHuanDriver;
import org.springblade.anbiao.jinritixing.entity.YinHuanVehicle;
import org.springblade.anbiao.jinritixing.page.JinritixingPage;
import org.springblade.anbiao.jinritixing.service.IJinritixingService;
import org.springblade.anbiao.jinritixing.vo.FyyhTiXingInfoVO;
import org.springblade.anbiao.jinritixing.vo.FyyhTiXingVO;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiang;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiangDept;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiangDeptInfo;
import org.springblade.anbiao.yinhuanpaicha.page.YinhuanpaichaXiangPage;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoYinhuanpaichaXiangDeptInfoService;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoYinhuanpaichaXiangDeptService;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoYinhuanpaichaXiangService;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoYinhuanpaichaXiangDeptInfoVO;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoYinhuanpaichaXiangDeptVO;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
@RequestMapping("/anbiao/anbiaoYinhuanpaichaXiangDeptInfoDeptInfo")
@Api(value = "隐患排查企业计划", tags = "隐患排查企业计划")
public class AnbiaoYinhuanpaichaXiangDeptInfoController {

	private IAnbiaoYinhuanpaichaXiangDeptInfoService iAnbiaoYinhuanpaichaXiangDeptInfoService;

	private IAnbiaoYinhuanpaichaXiangDeptService iAnbiaoYinhuanpaichaXiangDeptService;

	private IAnbiaoYinhuanpaichaXiangService iAnbiaoYinhuanpaichaXiangService;

	private IJinritixingService jinritixingService;

	private IVehicleService iVehicleService;

	private ICheliangbaoxianService iCheliangbaoxianService;

	private IBaoxianxinxiService iBaoxianxinxiService;

	private IJiaShiYuanService iJiaShiYuanService;

	private IOrganizationsService iOrganizationService;

	@PostMapping("/saveYinhuanpaichaXiangDeptInfo")
	@ApiLog("隐患排查企业计划-新增企业隐患计划(设置月份)")
	@ApiOperation(value = "隐患排查企业计划-新增企业隐患计划(设置月份)", notes = "传入anbiaoYinhuanpaichaXiangDeptInfo", position = 1)
	public R saveYinhuanpaichaXiangDeptInfo(@RequestBody AnbiaoYinhuanpaichaXiangDeptInfo anbiaoYinhuanpaichaXiangDeptInfo, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		anbiaoYinhuanpaichaXiangDeptInfo.setCreateid(user.getUserId());
		anbiaoYinhuanpaichaXiangDeptInfo.setIsdelete(0);
		anbiaoYinhuanpaichaXiangDeptInfo.setCreatetime(DateUtil.now());

		//月份
		String[] idsss = anbiaoYinhuanpaichaXiangDeptInfo.getPcyf().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss = listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			QueryWrapper<AnbiaoYinhuanpaichaXiangDeptInfo> unitWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiangDeptInfo>();
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getDeptid, anbiaoYinhuanpaichaXiangDeptInfo.getDeptid());
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getDataid, anbiaoYinhuanpaichaXiangDeptInfo.getDataid());
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getPcyf, idss[i]);
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getIsdelete, 0);
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getStatus, 0);
			AnbiaoYinhuanpaichaXiangDeptInfo deail = iAnbiaoYinhuanpaichaXiangDeptInfoService.getBaseMapper().selectOne(unitWrapper);
			if(deail == null){
				anbiaoYinhuanpaichaXiangDeptInfo.setPcyf(idss[i]);
				anbiaoYinhuanpaichaXiangDeptInfo.setStatus(0);
				boolean ii = iAnbiaoYinhuanpaichaXiangDeptInfoService.save(anbiaoYinhuanpaichaXiangDeptInfo);
				if(ii == true){
					rs.setCode(200);
					rs.setSuccess(true);
					rs.setMsg("新增成功");
				}else{
					rs.setCode(500);
					rs.setSuccess(false);
					rs.setMsg("新增失败");
				}
			}
		}
		return rs;
	}

	@PostMapping("/updateYinhuanpaichaXiangDeptInfo")
	@ApiLog("隐患排查企业计划-编辑企业隐患计划")
	@ApiOperation(value = "隐患排查企业计划-编辑企业隐患计划", notes = "传入anbiaoYinhuanpaichaXiangDeptInfo", position = 2)
	public R updateYinhuanpaichaXiangDeptInfo(@RequestBody AnbiaoYinhuanpaichaXiangDeptInfo anbiaoYinhuanpaichaXiangDeptInfo, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		boolean ii = false;

		anbiaoYinhuanpaichaXiangDeptInfo.setUpdateid(user.getUserId());
		anbiaoYinhuanpaichaXiangDeptInfo.setIsdelete(0);
		anbiaoYinhuanpaichaXiangDeptInfo.setUpdatetime(DateUtil.now());

		//月份
		String[] idsss = anbiaoYinhuanpaichaXiangDeptInfo.getPcyf().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss = listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			QueryWrapper<AnbiaoYinhuanpaichaXiangDeptInfo> unitWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiangDeptInfo>();
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getDeptid, anbiaoYinhuanpaichaXiangDeptInfo.getDeptid());
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getDataid, anbiaoYinhuanpaichaXiangDeptInfo.getDataid());
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getPcyf, idss[i]);
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getIsdelete, 0);
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getStatus, 0);
			AnbiaoYinhuanpaichaXiangDeptInfo deail = iAnbiaoYinhuanpaichaXiangDeptInfoService.getBaseMapper().selectOne(unitWrapper);
			if(deail == null) {
				anbiaoYinhuanpaichaXiangDeptInfo.setPcyf(idss[i]);
				anbiaoYinhuanpaichaXiangDeptInfo.setStatus(0);
				ii = iAnbiaoYinhuanpaichaXiangDeptInfoService.save(anbiaoYinhuanpaichaXiangDeptInfo);
			}else{
				ii = iAnbiaoYinhuanpaichaXiangDeptInfoService.updateById(anbiaoYinhuanpaichaXiangDeptInfo);
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

	@GetMapping("/removeYinhuanpaichaXiangDeptInfo")
	@ApiLog("隐患排查企业计划-删除企业隐患计划")
	@ApiOperation(value = "隐患排查企业计划-删除企业隐患计划", notes = "传入Id", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true) })
	public R removeYinhuanpaichaXiangDeptInfo(Integer Id, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		AnbiaoYinhuanpaichaXiangDeptInfo anbiaoYinhuanpaichaXiangDeptInfo = new AnbiaoYinhuanpaichaXiangDeptInfo();
		anbiaoYinhuanpaichaXiangDeptInfo.setUpdateid(user.getUserId());
		anbiaoYinhuanpaichaXiangDeptInfo.setIsdelete(1);
		anbiaoYinhuanpaichaXiangDeptInfo.setUpdatetime(DateUtil.now());
		anbiaoYinhuanpaichaXiangDeptInfo.setId(Id);

		QueryWrapper<AnbiaoYinhuanpaichaXiangDeptInfo> unitWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiangDeptInfo>();
		unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfo::getId, Id);
		AnbiaoYinhuanpaichaXiangDeptInfo deail = iAnbiaoYinhuanpaichaXiangDeptInfoService.getBaseMapper().selectOne(unitWrapper);
		if(deail != null) {
			if(deail.getStatus() == 1){
				rs.setMsg("该数据已派发车辆，不能删除");
				rs.setCode(500);
				return rs;
			}
		}
		return R.data(iAnbiaoYinhuanpaichaXiangDeptInfoService.updateById(anbiaoYinhuanpaichaXiangDeptInfo));
	}

	@PostMapping("/getDeptYHPage")
	@ApiLog("隐患排查项-企业隐患列表")
	@ApiOperation(value = "隐患排查项-企业隐患列表", notes = "传入yinhuanpaichaXiangPage", position = 4)
	public R<YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptVO>> getDeptYHPage(@RequestBody YinhuanpaichaXiangPage yinhuanpaichaXiangPage, BladeUser user) {
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
		YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptVO> pages = iAnbiaoYinhuanpaichaXiangDeptInfoService.selectDeptYHPage(yinhuanpaichaXiangPage);
		return R.data(pages);
	}

	@PostMapping("/getYinhuanpaichaXiangPage")
	@ApiLog("隐患排查项-企业隐患计划列表")
	@ApiOperation(value = "隐患排查项-企业隐患计划列表", notes = "传入yinhuanpaichaXiangPage", position = 4)
	public R<YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptVO>> getYinhuanpaichaXiangPage(@RequestBody YinhuanpaichaXiangPage yinhuanpaichaXiangPage, BladeUser user) {
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
		YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptVO> pages = iAnbiaoYinhuanpaichaXiangDeptInfoService.selectYinhuanpaichaDeptPlanPage(yinhuanpaichaXiangPage);
		return R.data(pages);
	}

	@PostMapping("/getYinhuanpaichaDeptPlanRemarkPage")
	@ApiLog("隐患排查项-分发隐患列表")
	@ApiOperation(value = "隐患排查项-分发隐患列表", notes = "传入yinhuanpaichaXiangPage", position = 4)
	public R<YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptInfoVO>> getYinhuanpaichaDeptPlanRemarkPage(@RequestBody YinhuanpaichaXiangPage yinhuanpaichaXiangPage, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		//排序条件
		////默认发起时间降序
		if(yinhuanpaichaXiangPage.getOrderColumns()==null){
			yinhuanpaichaXiangPage.setOrderColumn("pcyf");
		}else{
			yinhuanpaichaXiangPage.setOrderColumn(yinhuanpaichaXiangPage.getOrderColumns());
		}
		YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptInfoVO> pages = iAnbiaoYinhuanpaichaXiangDeptInfoService.selectYinhuanpaichaDeptPlanRemarkPage(yinhuanpaichaXiangPage);
		return R.data(pages);
	}

	@GetMapping("/getDeptDriverPlan")
	@ApiOperation(value = "隐患排查项-企业隐患计划列表-驾驶员获取计划", notes = "传入deptId、jsyId、vehId", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "deptId", value = "企业Id", required = true),
		@ApiImplicitParam(name = "jsyId", value = "驾驶员Id"),
		@ApiImplicitParam(name = "vehId", value = "车辆Id")
	})
	public R getDeptDriverPlan(Integer deptId,String jsyId,String vehId, BladeUser user) {
		R rs = new R();
		List<AnbiaoYinhuanpaichaXiangDeptInfoVO> xiangDeptInfoVO = iAnbiaoYinhuanpaichaXiangDeptInfoService.selectDeptDriverPlan(deptId,jsyId,vehId);
		if(xiangDeptInfoVO != null){
			rs.setCode(200);
			rs.setData(xiangDeptInfoVO);
			rs.setMsg("获取成功");
		}else{
			rs.setCode(200);
			rs.setData("");
			rs.setMsg("获取成功，暂无数据");
		}
		return rs;
	}


	@PostMapping("/getFYYHPageList")
	@ApiLog("隐患排查项-隐患列表（分类总数统计）")
	@ApiOperation(value = "隐患排查项-隐患列表（分类总数统计）", notes = "传入jinritixingPage", position = 5)
	public R<JinritixingPage<FyyhTiXingVO>> getFYYHPageList(@RequestBody JinritixingPage jinritixingPage, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		//排序条件
		////默认发起时间降序
		if(jinritixingPage.getOrderColumns()==null){
			jinritixingPage.setOrderColumn("counts");
		}else{
			jinritixingPage.setOrderColumn(jinritixingPage.getOrderColumns());
		}
		JinritixingPage<FyyhTiXingVO> pages = jinritixingService.selectFYYHPageList(jinritixingPage);
		return R.data(pages);
	}

	@GetMapping("/getCountScore")
	@ApiLog("隐患排查企业计划-首页分值")
	@ApiOperation(value = "隐患排查企业计划-首页分值", notes = "传入deptId", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "deptId", value = "企业Id", required = true) })
	public R getCountScore(Integer deptId, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		List<FyyhTiXingVO> deail = jinritixingService.selectCountScore(deptId);
		List<FyyhTiXingVO> finshdeail = jinritixingService.selectFinshCountScore(deptId);
		if(deail != null){
			deail.forEach(item->{
				finshdeail.forEach(finshdeailitem->{
					if(finshdeail != null) {
						if (item.getType().equals(finshdeailitem.getType())) {
							item.setFinshcountscore(finshdeailitem.getFinshcountscore());
						}
					}
				});
			});
		}
		return R.data(deail);
	}

	@GetMapping("/updateYHInfo")
	@ApiLog("隐患排查企业计划-更新隐患字段")
	@ApiOperation(value = "隐患排查企业计划-更新隐患字段", notes = "传入数据ID、字段值", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true),
		@ApiImplicitParam(name = "date", value = "更新时间"),
		@ApiImplicitParam(name = "value", value = "数据值（如从业资格证号）"),
		@ApiImplicitParam(name = "fujian", value = "附件")
	})
	public R updateYHInfo(String Id,String date,String value,String fujian, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		boolean ii = false;
		QueryWrapper<Jinritixing> queryWrapper = new QueryWrapper<Jinritixing>();
		queryWrapper.lambda().eq(Jinritixing::getId, Id);
		queryWrapper.lambda().eq(Jinritixing::getStatus, 0);
		queryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
		Jinritixing deailInfo = jinritixingService.getBaseMapper().selectOne(queryWrapper);
		if(deailInfo != null){
			QueryWrapper<AnbiaoYinhuanpaichaXiang> xiangQueryWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiang>();
			xiangQueryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getId, deailInfo.getTixingxiangqingid());
			xiangQueryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getIsdelete, 0);
			AnbiaoYinhuanpaichaXiang yinhuanpaichaXiang = iAnbiaoYinhuanpaichaXiangService.getBaseMapper().selectOne(xiangQueryWrapper);
			Jinritixing jinritixing = new Jinritixing();
			Vehicle vv = new Vehicle();
			Baoxianxinxi bx = new Baoxianxinxi();
			Cheliangbaoxian cb = new Cheliangbaoxian();
			JiaShiYuan js = new JiaShiYuan();
			Organizations or = new Organizations();
			if(deailInfo.getCheliangid() != null) {
				//车辆报废日期
				if (yinhuanpaichaXiang.getName().equals("车辆报废日期")) {
					vv.setId(deailInfo.getCheliangid());
					vv.setQiangzhibaofeishijian(date);
					vv.setCaozuoshijian(LocalDateTime.now());
					ii = iVehicleService.updateById(vv);
//					if(ii == true){
//						jinritixing.setId(deailInfo.getId());
//						jinritixing.setStatus(1);
//						jinritixing.setCaozuoshijian(DateUtil.format(new Date(),"yyyy-MM-dd"));
//						jinritixing.setCaozuoren(user.getUserName());
//						jinritixing.setCaozuorenid(user.getUserId());
//						ii = jinritixingService.updateById(jinritixing);
//					}
				}
				//车辆下次年审日期
				if(yinhuanpaichaXiang.getName().equals("车辆下次年审日期")){
					vv.setId(deailInfo.getCheliangid());
					vv.setXiacinianshenriqi(date);
					vv.setCaozuoshijian(LocalDateTime.now());
					ii = iVehicleService.updateById(vv);
				}
				//车辆下次年检日期
				if(yinhuanpaichaXiang.getName().equals("车辆下次年检日期")){
					vv.setId(deailInfo.getCheliangid());
					vv.setXiacinianjianriqi(date);
					vv.setCaozuoshijian(LocalDateTime.now());
					ii = iVehicleService.updateById(vv);
				}
				//车辆道路运输证有效截止日期
				if(yinhuanpaichaXiang.getName().equals("车辆道路运输证有效截止日期")){
					vv.setId(deailInfo.getCheliangid());
					vv.setDaoluyunshuzhengyouxiaoqi(date);
					vv.setCaozuoshijian(LocalDateTime.now());
					ii = iVehicleService.updateById(vv);
				}
				//车辆下次技术评定日期
				if (yinhuanpaichaXiang.getName().equals("车辆下次技术评定日期")) {
					vv.setId(deailInfo.getCheliangid());
					vv.setXiacijipingriqi(date);
					vv.setCaozuoshijian(LocalDateTime.now());
					ii = iVehicleService.updateById(vv);
				}
				//车辆交强险到期日期
				if(yinhuanpaichaXiang.getName().equals("车辆交强险到期日期")){
					cb.setCheliangid(deailInfo.getCheliangid());
					cb.setDeptId(deailInfo.getDeptId());
					QueryWrapper<Cheliangbaoxian> cheliangbaoxianQueryWrapper = new QueryWrapper<Cheliangbaoxian>();
					cheliangbaoxianQueryWrapper.lambda().eq(Cheliangbaoxian::getId, deailInfo.getTixingxiangqingid());
					cheliangbaoxianQueryWrapper.lambda().eq(Cheliangbaoxian::getToubaoleixing, 1);
					cheliangbaoxianQueryWrapper.lambda().eq(Cheliangbaoxian::getIsdelete, 0);
					Cheliangbaoxian cheliangbaoxian = iCheliangbaoxianService.getBaseMapper().selectOne(cheliangbaoxianQueryWrapper);
					if(cheliangbaoxian != null){
						bx.setBaoxianid(cheliangbaoxian.getId());
						QueryWrapper<Baoxianxinxi> baoxianxinxiQueryWrapper = new QueryWrapper<Baoxianxinxi>();
						baoxianxinxiQueryWrapper.lambda().eq(Baoxianxinxi::getId, deailInfo.getTixingxiangqingid());
						baoxianxinxiQueryWrapper.lambda().eq(Baoxianxinxi::getIsdelete, 0);
						Baoxianxinxi baoxianxinxi = iBaoxianxinxiService.getBaseMapper().selectOne(baoxianxinxiQueryWrapper);
						if(baoxianxinxi != null){
							bx.setZhongbaoshijian(date);
							bx.setBaoxianid(baoxianxinxi.getBaoxianid());
							bx.setCaozuoshijian(DateUtil.format(new Date(),"yyyy-MM-dd"));
							ii = iBaoxianxinxiService.updateById(bx);
						}
					}
				}
				//车辆商业险到期日期
				if(yinhuanpaichaXiang.getName().equals("车辆商业险到期日期")){
					cb.setCheliangid(deailInfo.getCheliangid());
					cb.setDeptId(deailInfo.getDeptId());
					QueryWrapper<Cheliangbaoxian> cheliangbaoxianQueryWrapper = new QueryWrapper<Cheliangbaoxian>();
					cheliangbaoxianQueryWrapper.lambda().eq(Cheliangbaoxian::getId, deailInfo.getTixingxiangqingid());
					cheliangbaoxianQueryWrapper.lambda().eq(Cheliangbaoxian::getToubaoleixing, 2);
					cheliangbaoxianQueryWrapper.lambda().eq(Cheliangbaoxian::getIsdelete, 0);
					Cheliangbaoxian cheliangbaoxian = iCheliangbaoxianService.getBaseMapper().selectOne(cheliangbaoxianQueryWrapper);
					if(cheliangbaoxian != null){
						bx.setBaoxianid(cheliangbaoxian.getId());
						QueryWrapper<Baoxianxinxi> baoxianxinxiQueryWrapper = new QueryWrapper<Baoxianxinxi>();
						baoxianxinxiQueryWrapper.lambda().eq(Baoxianxinxi::getId, deailInfo.getTixingxiangqingid());
						baoxianxinxiQueryWrapper.lambda().eq(Baoxianxinxi::getIsdelete, 0);
						Baoxianxinxi baoxianxinxi = iBaoxianxinxiService.getBaseMapper().selectOne(baoxianxinxiQueryWrapper);
						if(baoxianxinxi != null){
							bx.setZhongbaoshijian(date);
							bx.setBaoxianid(baoxianxinxi.getBaoxianid());
							bx.setCaozuoshijian(DateUtil.format(new Date(),"yyyy-MM-dd"));
							ii = iBaoxianxinxiService.updateById(bx);
						}
					}
				}
				//车辆超赔险到期日期
				if(yinhuanpaichaXiang.getName().equals("车辆超赔险到期日期")){
					cb.setCheliangid(deailInfo.getCheliangid());
					cb.setDeptId(deailInfo.getDeptId());
					QueryWrapper<Cheliangbaoxian> cheliangbaoxianQueryWrapper = new QueryWrapper<Cheliangbaoxian>();
					cheliangbaoxianQueryWrapper.lambda().eq(Cheliangbaoxian::getId, deailInfo.getTixingxiangqingid());
					cheliangbaoxianQueryWrapper.lambda().eq(Cheliangbaoxian::getToubaoleixing, 3);
					cheliangbaoxianQueryWrapper.lambda().eq(Cheliangbaoxian::getIsdelete, 0);
					Cheliangbaoxian cheliangbaoxian = iCheliangbaoxianService.getBaseMapper().selectOne(cheliangbaoxianQueryWrapper);
					if(cheliangbaoxian != null){
						bx.setBaoxianid(cheliangbaoxian.getId());
						QueryWrapper<Baoxianxinxi> baoxianxinxiQueryWrapper = new QueryWrapper<Baoxianxinxi>();
						baoxianxinxiQueryWrapper.lambda().eq(Baoxianxinxi::getId, deailInfo.getTixingxiangqingid());
						baoxianxinxiQueryWrapper.lambda().eq(Baoxianxinxi::getIsdelete, 0);
						Baoxianxinxi baoxianxinxi = iBaoxianxinxiService.getBaseMapper().selectOne(baoxianxinxiQueryWrapper);
						if(baoxianxinxi != null){
							bx.setZhongbaoshijian(date);
							bx.setBaoxianid(baoxianxinxi.getBaoxianid());
							bx.setCaozuoshijian(DateUtil.format(new Date(),"yyyy-MM-dd"));
							ii = iBaoxianxinxiService.updateById(bx);
						}
					}
				}
				//车辆雇主险到期日期
				if(yinhuanpaichaXiang.getName().equals("车辆雇主险到期日期")){
					cb.setCheliangid(deailInfo.getCheliangid());
					cb.setDeptId(deailInfo.getDeptId());
					QueryWrapper<Cheliangbaoxian> cheliangbaoxianQueryWrapper = new QueryWrapper<Cheliangbaoxian>();
					cheliangbaoxianQueryWrapper.lambda().eq(Cheliangbaoxian::getId, deailInfo.getTixingxiangqingid());
					cheliangbaoxianQueryWrapper.lambda().eq(Cheliangbaoxian::getToubaoleixing, 4);
					cheliangbaoxianQueryWrapper.lambda().eq(Cheliangbaoxian::getIsdelete, 0);
					Cheliangbaoxian cheliangbaoxian = iCheliangbaoxianService.getBaseMapper().selectOne(cheliangbaoxianQueryWrapper);
					if(cheliangbaoxian != null){
						bx.setBaoxianid(cheliangbaoxian.getId());
						QueryWrapper<Baoxianxinxi> baoxianxinxiQueryWrapper = new QueryWrapper<Baoxianxinxi>();
						baoxianxinxiQueryWrapper.lambda().eq(Baoxianxinxi::getId, deailInfo.getTixingxiangqingid());
						baoxianxinxiQueryWrapper.lambda().eq(Baoxianxinxi::getIsdelete, 0);
						Baoxianxinxi baoxianxinxi = iBaoxianxinxiService.getBaseMapper().selectOne(baoxianxinxiQueryWrapper);
						if(baoxianxinxi != null){
							bx.setZhongbaoshijian(date);
							bx.setBaoxianid(baoxianxinxi.getBaoxianid());
							bx.setCaozuoshijian(DateUtil.format(new Date(),"yyyy-MM-dd"));
							ii = iBaoxianxinxiService.updateById(bx);
						}
					}
				}
			}

			if(deailInfo.getJiashiyuanid() != null){
				//驾驶证有效截止日期
				if(yinhuanpaichaXiang.getName().equals("驾驶证有效截止日期")){
					js.setId(deailInfo.getJiashiyuanid());
					js.setDeptId(deailInfo.getDeptId());
					js.setJiashizhengyouxiaoqi(date);
					js.setCaozuoshijian(DateUtil.format(new Date(),"yyyy-MM-dd"));
					ii = iJiaShiYuanService.updateById(js);
				}
				//从业资格证有效截止日期
				if(yinhuanpaichaXiang.getName().equals("从业资格证有效截止日期")){
					js.setId(deailInfo.getJiashiyuanid());
					js.setDeptId(deailInfo.getDeptId());
					js.setCongyezhengyouxiaoqi(date);
					vv.setCaozuoshijian(LocalDateTime.now());
					ii = iJiaShiYuanService.updateById(js);
				}
				//下次诚信考核日期
				if(yinhuanpaichaXiang.getName().equals("下次诚信考核日期")){
					js.setId(deailInfo.getJiashiyuanid());
					js.setDeptId(deailInfo.getDeptId());
					js.setXiacichengxinkaoheshijian(date);
					js.setCaozuoshijian(DateUtil.format(new Date(),"yyyy-MM-dd"));
					ii = iJiaShiYuanService.updateById(js);
				}
				//体检有效截止日期
				if(yinhuanpaichaXiang.getName().equals("体检有效截止日期")){
					js.setId(deailInfo.getJiashiyuanid());
					js.setDeptId(deailInfo.getDeptId());
					js.setTijianyouxiaoqi(date);
					js.setCaozuoshijian(DateUtil.format(new Date(),"yyyy-MM-dd"));
					ii = iJiaShiYuanService.updateById(js);
				}
				//身份证有效期截止时间
				if(yinhuanpaichaXiang.getName().equals("身份证有效期截止时间")){
					js.setId(deailInfo.getJiashiyuanid());
					js.setDeptId(deailInfo.getDeptId());
					js.setShenfenzhengyouxiaoqi(date);
					js.setCaozuoshijian(DateUtil.format(new Date(),"yyyy-MM-dd"));
					ii = iJiaShiYuanService.updateById(js);
				}
				//从业资格证号
				if(yinhuanpaichaXiang.getName().equals("从业资格证号")){
					js.setId(deailInfo.getJiashiyuanid());
					js.setDeptId(deailInfo.getDeptId());
					js.setCongyezigezheng(value);
					js.setCaozuoshijian(DateUtil.format(new Date(),"yyyy-MM-dd"));
					ii = iJiaShiYuanService.updateById(js);
				}
			}

			if(deailInfo.getShujuid() != null) {
				QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<Organizations>();
				organizationsQueryWrapper.lambda().eq(Organizations::getDeptId, deailInfo.getShujuid());
				organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
				Organizations organizations = iOrganizationService.getBaseMapper().selectOne(organizationsQueryWrapper);
				//企业法人身份证
				if (yinhuanpaichaXiang.getName().equals("企业法人身份证")) {
					or.setId(organizations.getId());
					or.setFujian(fujian);
					or.setCaozuoshijian(DateUtil.format(new Date(), "yyyy-MM-dd"));
					ii = iOrganizationService.updateById(or);
				}
				//企业工商营业执照
				if (yinhuanpaichaXiang.getName().equals("企业工商营业执照")) {
					or.setId(organizations.getId());
					or.setLoginPhotoApp(fujian);
					or.setCaozuoshijian(DateUtil.format(new Date(), "yyyy-MM-dd"));
					ii = iOrganizationService.updateById(or);
				}
				//企业经营许可证
				if (yinhuanpaichaXiang.getName().equals("企业经营许可证")) {
					or.setId(organizations.getId());
					or.setHomePhotoApp(fujian);
					or.setCaozuoshijian(DateUtil.format(new Date(), "yyyy-MM-dd"));
					ii = iOrganizationService.updateById(or);
				}
				//企业安全负责人
				if (yinhuanpaichaXiang.getName().equals("企业安全负责人")) {
					or.setId(organizations.getId());
					or.setLogoRizhi(fujian);
					or.setCaozuoshijian(DateUtil.format(new Date(), "yyyy-MM-dd"));
					ii = iOrganizationService.updateById(or);
				}
				//企业营业许可证
				if (yinhuanpaichaXiang.getName().equals("企业营业许可证")) {
					or.setId(organizations.getId());
					or.setLogoPhoto(fujian);
					or.setCaozuoshijian(DateUtil.format(new Date(), "yyyy-MM-dd"));
					ii = iOrganizationService.updateById(or);
				}
				//道路运输许可证
				if (yinhuanpaichaXiang.getName().equals("企业法人身份证")) {
					or.setId(organizations.getId());
					or.setProfilePhoto(fujian);
					or.setCaozuoshijian(DateUtil.format(new Date(), "yyyy-MM-dd"));
					ii = iOrganizationService.updateById(or);
				}
			}

			if(ii == true){
				jinritixing.setId(deailInfo.getId());
				jinritixing.setStatus(1);
				jinritixing.setCaozuoshijian(DateUtil.format(new Date(),"yyyy-MM-dd"));
				jinritixing.setCaozuoren(user.getUserName());
				jinritixing.setCaozuorenid(user.getUserId());
				ii = jinritixingService.updateById(jinritixing);
			}
		}
		if(ii == true){
			rs.setMsg("保存成功");
			rs.setCode(200);
			rs.setSuccess(true);
		}else{
			rs.setMsg("保存失败");
			rs.setCode(500);
			rs.setSuccess(false);
		}
		return rs;
	}


	@PostMapping("/getYHFLPageList")
	@ApiLog("隐患排查企业计划-隐患明细")
	@ApiOperation(value = "隐患排查企业计划-隐患明细", notes = "传入deptId", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "deptId", value = "企业ID", required = true),
		@ApiImplicitParam(name = "tixingxiangqingid", value = "隐患项ID", required = true)
	})
	public R<JinritixingPage<FyyhTiXingInfoVO>> getYHFLPageList(@RequestBody JinritixingPage jinritixingPage, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		//排序条件
		////默认发起时间降序
		if(jinritixingPage.getOrderColumns()==null){
			jinritixingPage.setOrderColumn("tixingxiangqingid");
		}else{
			jinritixingPage.setOrderColumn(jinritixingPage.getOrderColumns());
		}
		JinritixingPage<FyyhTiXingInfoVO> pages = jinritixingService.selectYHFLPageList(jinritixingPage);
		return R.data(pages);
	}


	@GetMapping("/getDeptYH")
	@ApiLog("生成隐患")
	@ApiOperation(value = "生成隐患", notes = "传入企业id", position = 7)
	public R getDeptYH(Integer deptId,String types) {
		R rs = new R();

		String[] idsss = types.split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss = listid.toArray(new String[1]);
		for(int p = 0;p< idss.length;p++){
			String type = idss[p];

			QueryWrapper<AnbiaoYinhuanpaichaXiangDept> unitWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiangDept>();
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDept::getDeptid, deptId);
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDept::getIsdelete, 0);
			List<AnbiaoYinhuanpaichaXiangDept> xiangDepts = iAnbiaoYinhuanpaichaXiangDeptService.getBaseMapper().selectList(unitWrapper);
			if(xiangDepts != null){
				xiangDepts.forEach(item->{
					QueryWrapper<AnbiaoYinhuanpaichaXiang> queryWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiang>();
					queryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getId, item.getXiangid());
					queryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getJudgerules, type);
					queryWrapper.lambda().eq(AnbiaoYinhuanpaichaXiang::getIsdelete, 0);
					AnbiaoYinhuanpaichaXiang deailInfo = iAnbiaoYinhuanpaichaXiangService.getBaseMapper().selectOne(queryWrapper);
					if (deailInfo != null) {
						/////////////////驾驶员相关信息/////////////////
						//驾驶证有效截止日期
						if(deailInfo.getName().equals("驾驶证有效截止日期")){
							List<YinHuanDriver> driverSJ = jinritixingService.selectDriverSJ(deptId,"驾驶证有效截止日期",type);
							if(driverSJ != null){
								for(int i = 0;i<driverSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setJiashiyuanid(driverSJ.get(i).getJiashiyuanid());
									jinritixing.setDeptId(driverSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("驾驶证有效截止日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("驾驶证有效截止日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("驾驶证有效截止日期预警");
									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("jiashizhengyouxiaoqi");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getJiashiyuanid, driverSJ.get(i).getJiashiyuanid());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}
						//从业资格证有效截止日期
						if(deailInfo.getName().equals("从业资格证有效截止日期")){
							List<YinHuanDriver> driverSJ = jinritixingService.selectDriverSJ(deptId,"从业资格证有效截止日期",type);
							if(driverSJ != null){
								for(int i = 0;i<driverSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setJiashiyuanid(driverSJ.get(i).getJiashiyuanid());
									jinritixing.setDeptId(driverSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("从业资格证有效截止日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("从业资格证有效截止日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("从业资格证有效截止日期预警");
									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("congyezhengyouxiaoqi");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getJiashiyuanid, driverSJ.get(i).getJiashiyuanid());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}
						//下次诚信考核日期
						if(deailInfo.getName().equals("下次诚信考核日期")){
							List<YinHuanDriver> driverSJ = jinritixingService.selectDriverSJ(deptId,"下次诚信考核日期",type);
							if(driverSJ != null){
								for(int i = 0;i<driverSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setJiashiyuanid(driverSJ.get(i).getJiashiyuanid());
									jinritixing.setDeptId(driverSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("下次诚信考核日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("下次诚信考核日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("下次诚信考核日期预警");
									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("xiacichengxinkaoheshijian");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getJiashiyuanid, driverSJ.get(i).getJiashiyuanid());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}
						//体检有效截止日期
						if(deailInfo.getName().equals("体检有效截止日期")){
							List<YinHuanDriver> driverSJ = jinritixingService.selectDriverSJ(deptId,"体检有效截止日期",type);
							if(driverSJ != null){
								for(int i = 0;i<driverSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setJiashiyuanid(driverSJ.get(i).getJiashiyuanid());
									jinritixing.setDeptId(driverSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("体检有效截止日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("体检有效截止日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("体检有效截止日期预警");
									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("tijianyouxiaoqi");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getJiashiyuanid, driverSJ.get(i).getJiashiyuanid());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}
						//身份证有效期截止时间
						if(deailInfo.getName().equals("身份证有效期截止时间")){
							List<YinHuanDriver> driverSJ = jinritixingService.selectDriverSJ(deptId,"身份证有效期截止时间",type);
							if(driverSJ != null){
								for(int i = 0;i<driverSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setJiashiyuanid(driverSJ.get(i).getJiashiyuanid());
									jinritixing.setDeptId(driverSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("身份证有效期截止时间缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("身份证有效期截止时间逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("身份证有效期截止时间预警");
									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("shenfenzhengyouxiaoqi");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getJiashiyuanid, driverSJ.get(i).getJiashiyuanid());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}
						//从业资格证号
						if(deailInfo.getName().equals("从业资格证号")){
							List<YinHuanDriver> driverSJ = jinritixingService.selectDriverSJ(deptId,"从业资格证号",type);
							if(driverSJ != null){
								for(int i = 0;i<driverSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setJiashiyuanid(driverSJ.get(i).getJiashiyuanid());
									jinritixing.setDeptId(driverSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("从业资格证号缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("从业资格证号逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("从业资格证号预警");
									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("congyezigezheng");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getJiashiyuanid, driverSJ.get(i).getJiashiyuanid());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}

						/////////////////企业相关信息/////////////////
						//企业法人身份证
						if(deailInfo.getName().equals("企业法人身份证")){
							List<YinHuanDept> driverSJ = jinritixingService.selectDeptSJ(deptId,"企业法人身份证",type);
							if(driverSJ != null){
								for(int i = 0;i<driverSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setShujuid(driverSJ.get(i).getDeptId().toString());
									jinritixing.setDeptId(driverSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("企业法人身份证附件缺项");
									}

//									if(type.equals("2")){
//										jinritixing.setTixingleixing("逾期");
//										jinritixing.setTixingxiangqing("企业法人身份证附件逾期");
//									}
//
//									if(type.equals("1")){
//										jinritixing.setTixingleixing("预警");
//										jinritixing.setTixingxiangqing("企业法人身份证附件预警");
//									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("fujian");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getShujuid, driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}
						//企业工商营业执照
						if(deailInfo.getName().equals("企业工商营业执照")){
							List<YinHuanDept> driverSJ = jinritixingService.selectDeptSJ(deptId,"企业工商营业执照",type);
							if(driverSJ != null){
								for(int i = 0;i<driverSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setShujuid(driverSJ.get(i).getDeptId().toString());
									jinritixing.setDeptId(driverSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("企业工商营业执照附件缺项");
									}

//									if(type.equals("2")){
//										jinritixing.setTixingleixing("逾期");
//										jinritixing.setTixingxiangqing("企业法人身份证附件逾期");
//									}
//
//									if(type.equals("1")){
//										jinritixing.setTixingleixing("预警");
//										jinritixing.setTixingxiangqing("企业法人身份证附件预警");
//									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("loginPhotoApp");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getShujuid, driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}
						//企业经营许可证
						if(deailInfo.getName().equals("企业经营许可证")){
							List<YinHuanDept> driverSJ = jinritixingService.selectDeptSJ(deptId,"企业经营许可证",type);
							if(driverSJ != null){
								for(int i = 0;i<driverSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setShujuid(driverSJ.get(i).getDeptId().toString());
									jinritixing.setDeptId(driverSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("企业经营许可证附件缺项");
									}

//									if(type.equals("2")){
//										jinritixing.setTixingleixing("逾期");
//										jinritixing.setTixingxiangqing("企业法人身份证附件逾期");
//									}
//
//									if(type.equals("1")){
//										jinritixing.setTixingleixing("预警");
//										jinritixing.setTixingxiangqing("企业法人身份证附件预警");
//									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("homePhotoApp");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getShujuid, driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}
						//企业安全负责人
						if(deailInfo.getName().equals("企业安全负责人")){
							List<YinHuanDept> driverSJ = jinritixingService.selectDeptSJ(deptId,"企业安全负责人",type);
							if(driverSJ != null){
								for(int i = 0;i<driverSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setShujuid(driverSJ.get(i).getDeptId().toString());
									jinritixing.setDeptId(driverSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("企业安全负责人附件缺项");
									}

//									if(type.equals("2")){
//										jinritixing.setTixingleixing("逾期");
//										jinritixing.setTixingxiangqing("企业法人身份证附件逾期");
//									}
//
//									if(type.equals("1")){
//										jinritixing.setTixingleixing("预警");
//										jinritixing.setTixingxiangqing("企业法人身份证附件预警");
//									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("logoRizhi");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getShujuid, driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}
						//企业营业许可证
						if(deailInfo.getName().equals("企业营业许可证")){
							List<YinHuanDept> driverSJ = jinritixingService.selectDeptSJ(deptId,"企业营业许可证",type);
							if(driverSJ != null){
								for(int i = 0;i<driverSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setShujuid(driverSJ.get(i).getDeptId().toString());
									jinritixing.setDeptId(driverSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("企业营业许可证附件缺项");
									}

//									if(type.equals("2")){
//										jinritixing.setTixingleixing("逾期");
//										jinritixing.setTixingxiangqing("企业法人身份证附件逾期");
//									}
//
//									if(type.equals("1")){
//										jinritixing.setTixingleixing("预警");
//										jinritixing.setTixingxiangqing("企业法人身份证附件预警");
//									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("logoPhoto");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getShujuid, driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}
						//道路运输许可证
						if(deailInfo.getName().equals("道路运输许可证")){
							List<YinHuanDept> driverSJ = jinritixingService.selectDeptSJ(deptId,"道路运输许可证",type);
							if(driverSJ != null){
								for(int i = 0;i<driverSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setShujuid(driverSJ.get(i).getDeptId().toString());
									jinritixing.setDeptId(driverSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("道路运输许可证附件缺项");
									}

//									if(type.equals("2")){
//										jinritixing.setTixingleixing("逾期");
//										jinritixing.setTixingxiangqing("企业法人身份证附件逾期");
//									}
//
//									if(type.equals("1")){
//										jinritixing.setTixingleixing("预警");
//										jinritixing.setTixingxiangqing("企业法人身份证附件预警");
//									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("profilePhoto");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getShujuid, driverSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}

						/////////////////车辆相关信息/////////////////
						//车辆报废日期
						if(deailInfo.getName().equals("车辆报废日期")){
							List<YinHuanVehicle> vehQZBFSJ = jinritixingService.selectVehQZBFSJ(deptId,type);
							if(vehQZBFSJ != null){
								for(int i = 0;i<vehQZBFSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setCheliangid(vehQZBFSJ.get(i).getId());
									jinritixing.setDeptId(vehQZBFSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("车辆报废日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("车辆报废日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("车辆报废日期预警");
									}
									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("qiangzhibaofeishijian");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,vehQZBFSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getCheliangid, vehQZBFSJ.get(i).getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if(ii == true){
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}

						}

						//车辆下次年审日期
						if(deailInfo.getName().equals("车辆下次年审日期")){
							List<YinHuanVehicle> vehXCNSSJ = jinritixingService.selectVehXCNSSJ(deptId,type);
							if(vehXCNSSJ != null){
								for(int i = 0;i<vehXCNSSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setCheliangid(vehXCNSSJ.get(i).getId());
									jinritixing.setDeptId(vehXCNSSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("车辆下次年审日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("车辆下次年审日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("车辆下次年审日期预警");
									}

									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("xiacinianshenriqi");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,vehXCNSSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getCheliangid, vehXCNSSJ.get(i).getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if (ii == true) {
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}

						//车辆下次年检日期
						if(deailInfo.getName().equals("车辆下次年检日期")){
							List<YinHuanVehicle> vehXCNJSJ = jinritixingService.selectVehXCNJSJ(deptId,type);
							if(vehXCNJSJ != null){
								for(int i = 0;i<vehXCNJSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setCheliangid(vehXCNJSJ.get(i).getId());
									jinritixing.setDeptId(vehXCNJSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("车辆下次年检日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("车辆下次年检日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("车辆下次年检日期预警");
									}

									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("xiacinianjianriqi");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,vehXCNJSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getCheliangid, vehXCNJSJ.get(i).getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if (ii == true) {
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}

						//车辆道路运输证有效截止日期
						if(deailInfo.getName().equals("车辆道路运输证有效截止日期")){
							List<YinHuanVehicle> vehDLYSZYXQSJ = jinritixingService.selectVehDLYSZYXQSJ(deptId,type);
							if(vehDLYSZYXQSJ != null){
								for(int i = 0;i<vehDLYSZYXQSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setCheliangid(vehDLYSZYXQSJ.get(i).getId());
									jinritixing.setDeptId(vehDLYSZYXQSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("车辆道路运输证有效截止日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("车辆道路运输证有效截止日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("车辆道路运输证有效截止日期预警");
									}

									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("daoluyunshuzhengyouxiaoqi");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,vehDLYSZYXQSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getCheliangid, vehDLYSZYXQSJ.get(i).getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if (ii == true) {
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}

						//车辆下次技术评定日期
						if(deailInfo.getName().equals("车辆下次技术评定日期")){
							List<YinHuanVehicle> vehXCJSPDQSJ = jinritixingService.selectVehXCJSPDQSJ(deptId,type);
							if(vehXCJSPDQSJ != null){
								for(int i = 0;i<vehXCJSPDQSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setCheliangid(vehXCJSPDQSJ.get(i).getId());
									jinritixing.setDeptId(vehXCJSPDQSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("车辆下次技术评定日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("车辆下次技术评定日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("车辆下次技术评定日期预警");
									}

									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("xiacijipingriqi");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,vehXCJSPDQSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getCheliangid, vehXCJSPDQSJ.get(i).getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if (ii == true) {
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}

						//交强险到期日期、商业险到期日期、超赔险到期日期、雇主险到期日期 1,2,3,4
						if(deailInfo.getName().equals("车辆交强险到期日期")){
							List<YinHuanVehicle> vehBXSJ = null;
							vehBXSJ = jinritixingService.selectVehBXSJ(deptId,1,type);
							if(vehBXSJ != null){
								for(int i = 0;i<vehBXSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setCheliangid(vehBXSJ.get(i).getId());
									jinritixing.setDeptId(vehBXSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("车辆交强险到期日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("车辆交强险到期日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("车辆交强险到期日期预警");
									}

									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("zhongbaoshijian");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,vehBXSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getCheliangid, vehBXSJ.get(i).getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if (ii == true) {
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}

						if(deailInfo.getName().equals("车辆商业险到期日期")){
							List<YinHuanVehicle> vehBXSJ = null;
							vehBXSJ = jinritixingService.selectVehBXSJ(deptId,2,type);
							if(vehBXSJ != null){
								for(int i = 0;i<vehBXSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setCheliangid(vehBXSJ.get(i).getId());
									jinritixing.setDeptId(vehBXSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("车辆商业险到期日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("车辆商业险到期日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("车辆商业险到期日期预警");
									}

									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("zhongbaoshijian");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,vehBXSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getCheliangid, vehBXSJ.get(i).getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if (ii == true) {
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}

						if(deailInfo.getName().equals("车辆超赔险到期日期")){
							List<YinHuanVehicle> vehBXSJ = null;
							vehBXSJ = jinritixingService.selectVehBXSJ(deptId,3,type);
							if(vehBXSJ != null){
								for(int i = 0;i<vehBXSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setCheliangid(vehBXSJ.get(i).getId());
									jinritixing.setDeptId(vehBXSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("车辆超赔险到期日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("车辆超赔险到期日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("车辆超赔险到期日期预警");
									}

									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("zhongbaoshijian");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,vehBXSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getCheliangid, vehBXSJ.get(i).getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if (ii == true) {
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}

						if(deailInfo.getName().equals("车辆雇主险到期日期")){
							List<YinHuanVehicle> vehBXSJ = null;
							vehBXSJ = jinritixingService.selectVehBXSJ(deptId,4,type);
							if(vehBXSJ != null){
								for(int i = 0;i<vehBXSJ.size();i++){
									Jinritixing jinritixing = new Jinritixing();
									jinritixing.setCheliangid(vehBXSJ.get(i).getId());
									jinritixing.setDeptId(vehBXSJ.get(i).getDeptId());

									if(type.equals("3")){
										jinritixing.setTixingleixing("缺项");
										jinritixing.setTixingxiangqing("车辆雇主险到期日期缺项");
									}

									if(type.equals("2")){
										jinritixing.setTixingleixing("逾期");
										jinritixing.setTixingxiangqing("车辆雇主险到期日期逾期");
									}

									if(type.equals("1")){
										jinritixing.setTixingleixing("预警");
										jinritixing.setTixingxiangqing("车辆雇主险到期日期预警");
									}

									jinritixing.setTixingxiangqingid(deailInfo.getId().toString());
									jinritixing.setTongjiriqi(DateUtil.format(new Date(),"yyyy-MM-dd"));
									jinritixing.setBiaoid("zhongbaoshijian");
									jinritixing.setCreatetime(DateUtil.now());
									String uuid = UUID.randomUUID().toString().replace("-", "");
									jinritixing.setId(uuid);
									QueryWrapper<Jinritixing> jinritixingQueryWrapper = new QueryWrapper<Jinritixing>();
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getDeptId,vehBXSJ.get(i).getDeptId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getTixingxiangqingid, deailInfo.getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getCheliangid, vehBXSJ.get(i).getId());
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getStatus, 0);
									jinritixingQueryWrapper.lambda().eq(Jinritixing::getIsdelete, 0);
									Jinritixing jrtx = jinritixingService.getBaseMapper().selectOne(jinritixingQueryWrapper);
									if (jrtx == null) {
										boolean ii = jinritixingService.save(jinritixing);
										if (ii == true) {
											rs.setCode(200);
											rs.setMsg("添加成功");
										}
									}
								}
							}else{
								rs.setCode(200);
								rs.setMsg("该企业未分配隐患排查项");
							}
						}

					}
				});
			}else{
				rs.setCode(200);
				rs.setMsg("该企业未分配隐患排查项");
			}
		}
		return rs;
	}

}
