package org.springblade.anbiao.yunyingshang.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.yunyingshang.entity.TsOperatorInfo;
import org.springblade.anbiao.yunyingshang.page.TsOperatorInfoPage;
import org.springblade.anbiao.yunyingshang.service.ITsOperatorInfoService;
import org.springblade.anbiao.yunyingshang.vo.TsOperatorInfoVo;
import org.springblade.anbiao.zhengfu.entity.Organization;
import org.springblade.anbiao.zhengfu.entity.ZhengFuBaoJingTongJi;
import org.springblade.anbiao.zhengfu.entity.ZhengFuBaoJingTongJiJieSuan;
import org.springblade.anbiao.zhengfu.entity.ZhengFuRiYunXingTongJi;
import org.springblade.anbiao.zhengfu.page.ZhengFuBaoJingTongJiJieSuanPage;
import org.springblade.anbiao.zhengfu.service.IOrganizationService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-11-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/operatorInfo")
@Api(value = "运营商档案信息", tags = "运营商档案信息")
public class TsOperatorInfoController {

	private ITsOperatorInfoService service;

	private IOrganizationService iOrganizationService;

	/**
	 *新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增")
	@ApiOperation(value = "新增", notes = "传入TsOperatorInfo")
	public R insert(@RequestBody TsOperatorInfo tsOperatorInfo, BladeUser user) throws ParseException {
		R r = new R();
		QueryWrapper<TsOperatorInfo> tsOperatorInfoQueryWrapper = new QueryWrapper<>();
		tsOperatorInfoQueryWrapper.lambda().eq(TsOperatorInfo::getOpName, tsOperatorInfo.getOpName());
		tsOperatorInfoQueryWrapper.lambda().eq(TsOperatorInfo::getOpCode,tsOperatorInfo.getOpCode());
		tsOperatorInfoQueryWrapper.lambda().eq(TsOperatorInfo::getIsDeleted,0);
		TsOperatorInfo deail = service.getBaseMapper().selectOne(tsOperatorInfoQueryWrapper);
		if (deail == null){
			if(tsOperatorInfo.getCountry() != null){
				tsOperatorInfo.setYunguanid(tsOperatorInfo.getCountry());
			}

			if(tsOperatorInfo.getCountry() == null && tsOperatorInfo.getCity() != null){
				tsOperatorInfo.setYunguanid(tsOperatorInfo.getCity());
			}

			if(tsOperatorInfo.getProvince() != null && tsOperatorInfo.getCity() == null){
				tsOperatorInfo.setYunguanid(tsOperatorInfo.getProvince());
			}

			tsOperatorInfo.setCreateName(user.getUserName());
			tsOperatorInfo.setCreateId(user.getUserId());
			tsOperatorInfo.setCreateTime(DateUtil.now());
			tsOperatorInfo.setIsDeleted(0);
			boolean i = service.save(tsOperatorInfo);
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
			r.setMsg("该运营商信息已存在");
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
	@ApiOperation(value = "编辑",notes = "传入TsOperatorInfo")
	public R update(@RequestBody TsOperatorInfo tsOperatorInfo,BladeUser user){
		R r = new R();
		QueryWrapper<TsOperatorInfo> tsOperatorInfoQueryWrapper = new QueryWrapper<>();
		tsOperatorInfoQueryWrapper.lambda().eq(TsOperatorInfo::getOpId,tsOperatorInfo.getOpId());
		TsOperatorInfo deail = service.getBaseMapper().selectOne(tsOperatorInfoQueryWrapper);
		if (deail != null){
			if(tsOperatorInfo.getCountry() != null){
				tsOperatorInfo.setYunguanid(tsOperatorInfo.getCountry());
			}

			if(tsOperatorInfo.getCountry() == null && tsOperatorInfo.getCity() != null){
				tsOperatorInfo.setYunguanid(tsOperatorInfo.getCity());
			}

			if(tsOperatorInfo.getProvince() != null && tsOperatorInfo.getCity() == null){
				tsOperatorInfo.setYunguanid(tsOperatorInfo.getProvince());
			}

			tsOperatorInfo.setUpdateName(user.getUserName());
			tsOperatorInfo.setUpdateId(user.getUserId());
			tsOperatorInfo.setUpdateTime(DateUtil.now());
			boolean i = service.updateById(tsOperatorInfo);
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
		QueryWrapper<TsOperatorInfo> tsOperatorInfoQueryWrapper = new QueryWrapper<>();
		tsOperatorInfoQueryWrapper.lambda().eq(TsOperatorInfo::getOpId,Id);
		TsOperatorInfo deail = service.getBaseMapper().selectOne(tsOperatorInfoQueryWrapper);
		if (deail != null){
			TsOperatorInfo tsOperatorInfo = new TsOperatorInfo();
			tsOperatorInfo.setUpdateName(user.getUserName());
			tsOperatorInfo.setUpdateId(user.getUserId());
			tsOperatorInfo.setUpdateTime(DateUtil.now());
			tsOperatorInfo.setIsDeleted(1);
			tsOperatorInfo.setOpId(Id);
			boolean i = service.updateById(tsOperatorInfo);
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
		}else {
			r.setMsg("该数据不存在");
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
		TsOperatorInfo tsOperatorInfoInfo = service.getById(Id);
		if(tsOperatorInfoInfo != null){
			r.setData(tsOperatorInfoInfo);
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
	@PostMapping("/getOperatorInfoPage")
	@ApiLog("分页-列表")
	@ApiOperation(value = "分页-列表",notes = "传入TsOperatorInfoPage")
	public R<TsOperatorInfoPage<TsOperatorInfo>> getOperatorInfoPage(@RequestBody TsOperatorInfoPage tsOperatorInfoPage) {
		R r = new R();
		TsOperatorInfoPage<TsOperatorInfo> list = service.selectGetAll(tsOperatorInfoPage);
		return R.data(list);
	}

	@PostMapping(value = "/getYYSRYXTJ")
	@ApiLog("政府报警统计-运营商日运行情况统计")
	@ApiOperation(value = "政府报警统计-运营商日运行情况统计", notes = "传入zhengFuBaoJingTongJiJieSuanPage(deptId、begintime、endtime)",position = 16)
	public R<ZhengFuBaoJingTongJiJieSuanPage<ZhengFuRiYunXingTongJi>> getYYSRYXTJ(@RequestBody ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {

		Organization jb = iOrganizationService.selectGetZFJB(zhengFuBaoJingTongJiJieSuanPage.getDeptId());
		if(jb == null){
			return R.data(null);
		}
		if( !StringUtils.isBlank(jb.getProvince()) && StringUtils.isBlank(jb.getCity()) ){
			zhengFuBaoJingTongJiJieSuanPage.setDeptId(zhengFuBaoJingTongJiJieSuanPage.getDeptId());
		}

		if(!StringUtils.isBlank(jb.getCity()) && StringUtils.isBlank(jb.getCountry())){
			zhengFuBaoJingTongJiJieSuanPage.setDeptId(zhengFuBaoJingTongJiJieSuanPage.getDeptId());
		}

		if(!StringUtils.isBlank(jb.getCountry())) {
			zhengFuBaoJingTongJiJieSuanPage.setXiaJiDeptId(zhengFuBaoJingTongJiJieSuanPage.getDeptId());
		}

		//排序条件
		////默认处理牌照降序
		if(zhengFuBaoJingTongJiJieSuanPage.getOrderColumns()==null){
			zhengFuBaoJingTongJiJieSuanPage.setOrderColumn("");
		}else{
			zhengFuBaoJingTongJiJieSuanPage.setOrderColumn(zhengFuBaoJingTongJiJieSuanPage.getOrderColumns());
		}
		ZhengFuBaoJingTongJiJieSuanPage<ZhengFuRiYunXingTongJi> pages = service.selectGetYYSRYXTJ(zhengFuBaoJingTongJiJieSuanPage);
		return R.data(pages);
	}

	@GetMapping(value = "/getZFQYBJPMList")
	@ApiLog("政府报警统计-运营商报警排名（数据分析）")
	@ApiOperation(value = "政府报警统计-运营商报警排名（数据分析）", notes = "传入deptId",position = 7)
	public R getZFQYBJPMList(String deptId) throws IOException {
		R rs = new R();
		if(StringUtils.isBlank(deptId)){
			rs.setCode(500);
			rs.setMsg("请传人参数deptId");
			return rs;
		}
		List<ZhengFuBaoJingTongJi> zhengFuBaoJingTongJis = null;
		Organization jb = iOrganizationService.selectGetZFJB(deptId);
		if(!StringUtils.isBlank(jb.getProvince()) && StringUtils.isBlank(jb.getCity())){
			zhengFuBaoJingTongJis = service.selectGetZFYYSBaoJingPaiMing(deptId,null);
		}

		if(!StringUtils.isBlank(jb.getCity()) && StringUtils.isBlank(jb.getCountry())){
			zhengFuBaoJingTongJis = service.selectGetZFYYSBaoJingPaiMing(deptId,null);
		}

		if(!StringUtils.isBlank(jb.getCountry())) {
			zhengFuBaoJingTongJis = service.selectGetZFYYSBaoJingPaiMing(null,deptId);
		}

		if (zhengFuBaoJingTongJis != null) {
			rs.setCode(200);
			rs.setData(zhengFuBaoJingTongJis);
			rs.setMsg("获取成功");
		} else {
			rs.setCode(500);
			rs.setMsg("获取失败");
		}
		return rs;
	}

	@PostMapping(value = "/getZFBJTJJS")
	@ApiLog("政府报警统计-运营商报警排名（报警排名）")
	@ApiOperation(value = "政府报警统计-运营商报警排名（报警排名）", notes = "传入deptId或者xiaJiDeptId",position = 8)
	public R<ZhengFuBaoJingTongJiJieSuanPage<ZhengFuBaoJingTongJiJieSuan>> getZFBJTJJS(@RequestBody ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage) {
		//排序条件
		////默认报警总数降序
		if(zhengFuBaoJingTongJiJieSuanPage.getOrderColumns()==null){
			zhengFuBaoJingTongJiJieSuanPage.setOrderColumn("danchebaojingbi");
		}else{
			zhengFuBaoJingTongJiJieSuanPage.setOrderColumn(zhengFuBaoJingTongJiJieSuanPage.getOrderColumns());
		}
		Organization jb = iOrganizationService.selectGetZFJB(zhengFuBaoJingTongJiJieSuanPage.getDeptId());
		if(!StringUtils.isBlank(jb.getProvince()) && StringUtils.isBlank(jb.getCity())){
			zhengFuBaoJingTongJiJieSuanPage.setProvince(zhengFuBaoJingTongJiJieSuanPage.getDeptId());
		}

		if(!StringUtils.isBlank(jb.getCity()) && StringUtils.isBlank(jb.getCountry())){
			zhengFuBaoJingTongJiJieSuanPage.setCity(zhengFuBaoJingTongJiJieSuanPage.getDeptId());
		}

		if(!StringUtils.isBlank(jb.getCountry())) {
			zhengFuBaoJingTongJiJieSuanPage.setCountry(zhengFuBaoJingTongJiJieSuanPage.getDeptId());
		}

		ZhengFuBaoJingTongJiJieSuanPage<ZhengFuBaoJingTongJiJieSuan> pages = service.selectGetBJTJ(zhengFuBaoJingTongJiJieSuanPage);
		return R.data(pages);
	}

	@PostMapping("/getZFVehiclePage")
	@ApiLog("分页-政府-运营商档案")
	@ApiOperation(value = "分页-政府-运营商档案", notes = "传入TsOperatorInfoPage", position = 9)
	public R<TsOperatorInfoPage<TsOperatorInfoVo>> getZFVehiclePage(@RequestBody TsOperatorInfoPage tsOperatorInfoPage) {
		Organization jb = iOrganizationService.selectGetZFJB(tsOperatorInfoPage.getDeptId());
		if(!StringUtils.isBlank(jb.getProvince()) && StringUtils.isBlank(jb.getCity())){
			tsOperatorInfoPage.setProvince(jb.getProvince());
		}

		if(!StringUtils.isBlank(jb.getCity()) && StringUtils.isBlank(jb.getCountry())){
			tsOperatorInfoPage.setCity(jb.getCity());
		}

		if(!StringUtils.isBlank(jb.getCountry())){
			tsOperatorInfoPage.setCountry(jb.getCountry());
		}
		TsOperatorInfoPage<TsOperatorInfoVo> pages = service.selectZFYYSPage(tsOperatorInfoPage);
		return R.data(pages);
	}

	@GetMapping("/getOperatorInfo")
	@ApiLog("根据企业ID获取运营商下拉列表")
	@ApiOperation(value = "根据企业ID获取运营商下拉列表", notes = "传入deptId", position = 10)
	public R<List<TsOperatorInfo>> getOperatorInfo(String deptId) {
		List<TsOperatorInfo> operatorInfoList = service.selectOperatorInfo(deptId, null);
		return R.data(operatorInfoList);
	}

}
