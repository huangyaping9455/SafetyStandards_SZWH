package org.springblade.anbiao.chuchejiancha.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.tools.zip.ZipOutputStream;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamine;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfo;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfoRemark;
import org.springblade.anbiao.chuchejiancha.entity.CarExamineTJMX;
import org.springblade.anbiao.chuchejiancha.page.AnBiaoCheckCarPage;
import org.springblade.anbiao.chuchejiancha.page.AnbiaoCarExamineInfoPage;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineInfoRemarkService;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineInfoService;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineService;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineInfoTZVO;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineInfoVO;
import org.springblade.anbiao.chuchejiancha.vo.CarExamineMessageVO;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiucheliang.service.MaintenanceService;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoHiddenDanger;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoHiddenDangerService;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.*;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
@RequestMapping("/anbiao/carExamineInfo")
@Api(value = "安全检查数据", tags = "安全检查数据")
public class AnbiaoCarExamineInfoController {

	private IAnbiaoCarExamineInfoService iAnbiaoCarExamineInfoService;

	private IAnbiaoCarExamineInfoRemarkService iAnbiaoCarExamineInfoRemarkService;

	private IFileUploadClient fileUploadClient;

	private IAnbiaoCarExamineService iAnbiaoCarExamineService;

	private MaintenanceService maintenanceService;

	private IOrganizationsService organizationsService;

	private IAnbiaoHiddenDangerService hiddenDangerService;

	private FileServer fileServer;

	private IVehicleService vehicleService;

	private IAnbiaoRiskDetailService riskDetailService;

	@PostMapping("/saveCarExamineInfo")
	@ApiLog("安全检查数据-新增")
	@ApiOperation(value = "安全检查数据-新增", notes = "传入AnbiaoCarExamineInfo", position = 1)
	public R saveCarExamineInfo(@RequestBody AnbiaoCarExamineInfo anbiaoCarExamineInfo) {
		R rs = new R();
		boolean ii = false;

		anbiaoCarExamineInfo.setIsdelete(0);
		anbiaoCarExamineInfo.setCreatetime(DateUtil.now());
		anbiaoCarExamineInfo.setDate(DateUtil.now().substring(0,10));

		QueryWrapper<AnbiaoCarExamineInfo> examineQueryWrapper = new QueryWrapper<AnbiaoCarExamineInfo>();
//		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getType, anbiaoCarExamineInfo.getType());
//		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getStatus, 1);
		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getDeptid, anbiaoCarExamineInfo.getDeptid());
		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getIsdelete, 0);
		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getDate, anbiaoCarExamineInfo.getDate());
		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getVehid, anbiaoCarExamineInfo.getVehid());
		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getJsyid, anbiaoCarExamineInfo.getJsyid());
		AnbiaoCarExamineInfo deail = iAnbiaoCarExamineInfoService.getBaseMapper().selectOne(examineQueryWrapper);
		if(deail == null){
			if(anbiaoCarExamineInfo.getStatus() == 0 || anbiaoCarExamineInfo.getStatus() == 6){
				anbiaoCarExamineInfo.setStatus(0);
				anbiaoCarExamineInfo.setIsdelete(0);

				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"未按时进行安全检查");
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,anbiaoCarExamineInfo.getJsyid());
				AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail!=null){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.getBaseMapper().updateById(riskDetail);
				}

				ii = iAnbiaoCarExamineInfoService.save(anbiaoCarExamineInfo);
				if (ii == true) {
					rs.setCode(200);
					rs.setSuccess(true);
					rs.setMsg("编辑成功");
				} else {
					rs.setCode(500);
					rs.setSuccess(false);
					rs.setMsg("编辑失败");
				}
			}else{
				anbiaoCarExamineInfo.setStatus(1);

				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"未按时进行安全检查");
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,anbiaoCarExamineInfo.getJsyid());
				AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail!=null){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.getBaseMapper().updateById(riskDetail);
				}

				ii = iAnbiaoCarExamineInfoService.save(anbiaoCarExamineInfo);
				if(ii == true) {
					deail = iAnbiaoCarExamineInfoService.getBaseMapper().selectOne(examineQueryWrapper);
					if (deail != null) {
						List<AnbiaoCarExamineInfoRemark> examineInfos = anbiaoCarExamineInfo.getAnbiaoCarExamineInfoRemarkList();
						AnbiaoCarExamineInfo finalDeail = deail;
						OrganizationsVO dept = organizationsService.selectByDeptId(deail.getDeptid().toString());
						examineInfos.forEach(item-> {
							AnbiaoCarExamineInfoRemark remark = new AnbiaoCarExamineInfoRemark();
							remark.setExamid(finalDeail.getId());
							remark.setStatus(1);
							remark.setXiangid(item.getXiangid());
							remark.setFlgremark(item.getFlgremark());
							remark.setFlgimg(item.getFlgimg());
							remark.setTrueimg(item.getTrueimg());
							remark.setTrueremark(item.getTrueremark());
							remark.setCreatetime(DateUtil.now());
							remark.setCreateid(finalDeail.getJsyid());
							boolean is = iAnbiaoCarExamineInfoRemarkService.save(remark);
							if (is == true) {
								new Thread(new Runnable() {
									@Override
									public void run() {
										//向隐患登记、维修登记档案信息表中添加数据；
										MaintenanceEntity maintenanceDTO = new MaintenanceEntity();
										maintenanceDTO.setDeptId(finalDeail.getDeptid());
										maintenanceDTO.setDepID(finalDeail.getDeptid());
										maintenanceDTO.setVehicleId(finalDeail.getVehid());
										maintenanceDTO.setDriverId(finalDeail.getJsyid());
										maintenanceDTO.setSendDate(finalDeail.getDate());
										maintenanceDTO.setMaintainDictId(0);
										if(StringUtils.isEmpty(finalDeail.getRemark())){
											maintenanceDTO.setAcbMaintenanceContent("未填写");
										}else{
											maintenanceDTO.setAcbMaintenanceContent(finalDeail.getRemark());
										}
										if(StringUtils.isEmpty(remark.getFlgremark())){
											maintenanceDTO.setAcbRepairReason("未填写");
										}else{
											maintenanceDTO.setAcbRepairReason(remark.getFlgremark());
										}
										maintenanceDTO.setMaintenanceDeptName(dept.getDeptName());
										maintenanceDTO.setAcbBeforeMaintenance(remark.getFlgimg());
										maintenanceDTO.setCreateid(finalDeail.getJsyid());
										maintenanceDTO.setCreatetime(DateUtil.now());
										maintenanceService.insertOne(maintenanceDTO);

										AnbiaoHiddenDanger danger = new AnbiaoHiddenDanger();
										danger.setAhdDelete("0");
										danger.setAhdRectificationSituation("0");
										danger.setAhdCreateTime(DateUtil.now());
										danger.setAhdDeptIds(finalDeail.getDeptid().toString());
										danger.setAhdVehicleIds(finalDeail.getVehid());
										danger.setAhdType("0");
										danger.setAhdDriverIds(finalDeail.getJsyid());
										danger.setAhdDriverName(finalDeail.getCreatename());
										danger.setAhdDiscovererName(finalDeail.getCreatename());
										danger.setAhdDiscoveryTime(finalDeail.getDate());
										danger.setAhdHiddendangerEnclosure(remark.getFlgimg());
										danger.setAhdCreateTime(DateUtil.now());
										danger.setAhdCreateByIds(finalDeail.getJsyid());
										if(StringUtils.isEmpty(remark.getFlgremark())){
											danger.setAhdDescribe("未填写");
										}else{
											danger.setAhdDescribe(remark.getFlgremark());
										}
										danger.setAhdAddress("车辆设备");
										hiddenDangerService.save(danger);
									}
								}).start();
								rs.setCode(200);
								rs.setSuccess(true);
								rs.setMsg("新增成功");
							} else {
								rs.setCode(500);
								rs.setSuccess(false);
								rs.setMsg("新增失败");
							}
						});
					}
				}
			}
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("该数据已添加");
		}
		return rs;
	}

	@PostMapping("/updateCarExamineXiang")
	@ApiLog("安全检查数据-编辑")
	@ApiOperation(value = "安全检查数据-编辑", notes = "传入anbiaoCarExamineInfo", position = 2)
	public R updateCarExamineXiang(@RequestBody AnbiaoCarExamineInfo anbiaoCarExamineInfo) {
		R rs = new R();
		boolean ii = false;

		anbiaoCarExamineInfo.setUpdateid(anbiaoCarExamineInfo.getJsyid());
		anbiaoCarExamineInfo.setIsdelete(0);
		anbiaoCarExamineInfo.setUpdatetime(DateUtil.now());
		if(anbiaoCarExamineInfo.getStatus() == 0){
			anbiaoCarExamineInfo.setStatus(0);
			ii = iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo);
			if (ii == true) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("编辑成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("编辑失败");
			}
		}else{
			anbiaoCarExamineInfo.setStatus(1);
			ii = iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo);
			if(ii == true) {
				List<AnbiaoCarExamineInfoRemark> examineInfos = anbiaoCarExamineInfo.getAnbiaoCarExamineInfoRemarkList();
				examineInfos.forEach(item-> {
					AnbiaoCarExamineInfoRemark remark = new AnbiaoCarExamineInfoRemark();
					remark.setStatus(1);
					remark.setXiangid(item.getXiangid());
					remark.setExamid(item.getExamid());
					remark.setFlgremark(item.getFlgremark());
					remark.setFlgimg(item.getFlgimg());
					remark.setTrueimg(item.getTrueimg());
					remark.setTrueremark(item.getTrueremark());
					remark.setUpdatetime(DateUtil.now());
					remark.setUpdateid(anbiaoCarExamineInfo.getJsyid());
					remark.setId(item.getId());
					boolean is = iAnbiaoCarExamineInfoRemarkService.updateById(remark);
					if (is == true) {
						rs.setCode(200);
						rs.setSuccess(true);
						rs.setMsg("编辑成功");
					} else {
						rs.setCode(500);
						rs.setSuccess(false);
						rs.setMsg("编辑失败");
					}
				});
			}
		}
		return rs;
	}

	@GetMapping("/removeCarExamineXiang")
	@ApiOperation(value = "安全检查数据-删除", notes = "传入Id", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true),
		@ApiImplicitParam(name = "caozuorenid", value = "操作人ID", required = true)
	})
	public R removeCarExamineXiang(Integer Id,String caozuorenid) {
		AnbiaoCarExamineInfo anbiaoCarExamineInfo = new AnbiaoCarExamineInfo();
		anbiaoCarExamineInfo.setUpdateid(caozuorenid);
		anbiaoCarExamineInfo.setUpdatetime(DateUtil.now());
		anbiaoCarExamineInfo.setIsdelete(1);
		anbiaoCarExamineInfo.setId(Id);
		return R.data(iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo));
	}

	@GetMapping("/getById")
	@ApiOperation(value = "安全检查数据-根据ID获取详情", notes = "传入Id", position = 4)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true)})
	public R getById(Integer Id) {
		AnbiaoCarExamineInfo deail = iAnbiaoCarExamineInfoService.getById(Id);
		if(deail != null){
			//车辆照片
			if(StrUtil.isNotEmpty(deail.getVehimg()) && deail.getVehimg().contains("http") == false){
				deail.setVehimg(fileUploadClient.getUrl(deail.getVehimg()));
			}
			//检查人电子签名
			if(StrUtil.isNotEmpty(deail.getJcrsignatrue()) && deail.getJcrsignatrue().contains("http") == false){
				deail.setJcrsignatrue(fileUploadClient.getUrl(deail.getJcrsignatrue()));
			}
			//审核人电子签名
			if(StrUtil.isNotEmpty(deail.getShenhesignatrue()) && deail.getShenhesignatrue().contains("http") == false){
				deail.setShenhesignatrue(fileUploadClient.getUrl(deail.getShenhesignatrue()));
			}
			//整改责任人电子签名
			if(StrUtil.isNotEmpty(deail.getZgzrrsignatrue()) && deail.getZgzrrsignatrue().contains("http") == false){
				deail.setZgzrrsignatrue(fileUploadClient.getUrl(deail.getZgzrrsignatrue()));
			}
			//整改验收人电子签名
			if(StrUtil.isNotEmpty(deail.getZgysrsignatrue()) && deail.getZgysrsignatrue().contains("http") == false){
				deail.setZgysrsignatrue(fileUploadClient.getUrl(deail.getZgysrsignatrue()));
			}
			QueryWrapper<AnbiaoCarExamineInfoRemark> remarkQueryWrapper = new QueryWrapper<AnbiaoCarExamineInfoRemark>();
			remarkQueryWrapper.lambda().eq(AnbiaoCarExamineInfoRemark::getExamid, deail.getId());
			remarkQueryWrapper.lambda().eq(AnbiaoCarExamineInfoRemark::getStatus, 1);
			List<AnbiaoCarExamineInfoRemark> remark = iAnbiaoCarExamineInfoRemarkService.getBaseMapper().selectList(remarkQueryWrapper);
			if(remark != null){
				deail.setAnbiaoCarExamineInfoRemarkList(remark);
				remark.forEach(item-> {
					QueryWrapper<AnbiaoCarExamine> examineQueryWrapper = new QueryWrapper<AnbiaoCarExamine>();
					examineQueryWrapper.lambda().eq(AnbiaoCarExamine::getId, item.getXiangid());
					examineQueryWrapper.lambda().eq(AnbiaoCarExamine::getIsdelete, 0);
					AnbiaoCarExamine xiangdeail = iAnbiaoCarExamineService.getBaseMapper().selectOne(examineQueryWrapper);
					if(xiangdeail != null){
						item.setXiangname(xiangdeail.getName());
					}
					//异常附件
					if(StrUtil.isNotEmpty(item.getFlgimg()) && item.getFlgimg().contains("http") == false){
						item.setFlgimg(fileUploadClient.getUrl(item.getFlgimg()));
					}
					//整改附件
					if(StrUtil.isNotEmpty(item.getTrueimg()) && item.getTrueimg().contains("http") == false){
						item.setTrueimg(fileUploadClient.getUrl(item.getTrueimg()));
					}
				});
			}
		}
		return R.data(deail);
	}

	@PostMapping("/updateCarExamineStatus")
	@ApiLog("安全检查数据-审核、整改、验收")
	@ApiOperation(value = "安全检查数据-审核、整改、验收", notes = "传入anbiaoCarExamineInfo", position = 5)
	public R updateCarExamineStatus(@RequestBody AnbiaoCarExamineInfo anbiaoCarExamineInfo, BladeUser user) {
		R rs = new R();

		boolean ii = false;
		if(anbiaoCarExamineInfo.getStatus() == 2){
			anbiaoCarExamineInfo.setShenherenid(user.getUserId());
			anbiaoCarExamineInfo.setShenhenrenname(user.getUserName());
			anbiaoCarExamineInfo.setShenheshijian(DateUtil.now());
			anbiaoCarExamineInfo.setStatus(2);
			anbiaoCarExamineInfo.setShenhesignatrue(anbiaoCarExamineInfo.getShenhesignatrue());
			anbiaoCarExamineInfo.setId(anbiaoCarExamineInfo.getId());
			ii = iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo);
			if (ii == true) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("审核成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("审核失败");
			}
		}

		if(anbiaoCarExamineInfo.getStatus() == 3){
			anbiaoCarExamineInfo.setShenherenid(user.getUserId());
			anbiaoCarExamineInfo.setShenhenrenname(user.getUserName());
			anbiaoCarExamineInfo.setShenheshijian(DateUtil.now());
			anbiaoCarExamineInfo.setStatus(3);
			anbiaoCarExamineInfo.setShenhesignatrue(anbiaoCarExamineInfo.getShenhesignatrue());
			anbiaoCarExamineInfo.setId(anbiaoCarExamineInfo.getId());
			ii = iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo);
			if (ii == true) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("审核成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("审核失败");
			}
		}

		if(anbiaoCarExamineInfo.getStatus() == 4){
			anbiaoCarExamineInfo.setShenheshijian(DateUtil.now());
			anbiaoCarExamineInfo.setStatus(4);
			anbiaoCarExamineInfo.setZgzrrsignatrue(anbiaoCarExamineInfo.getZgzrrsignatrue());
			anbiaoCarExamineInfo.setId(anbiaoCarExamineInfo.getId());
			ii = iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo);
			if(ii == true) {
				List<AnbiaoCarExamineInfoRemark> examineInfos = anbiaoCarExamineInfo.getAnbiaoCarExamineInfoRemarkList();
				examineInfos.forEach(item-> {
					AnbiaoCarExamineInfoRemark remark = new AnbiaoCarExamineInfoRemark();
					remark.setXiangid(item.getXiangid());
					remark.setTrueremark(item.getTrueremark());
					remark.setTrueimg(item.getTrueimg());
					remark.setUpdatetime(DateUtil.now());
					remark.setUpdateid(anbiaoCarExamineInfo.getJsyid());
					remark.setId(item.getId());
					boolean is = iAnbiaoCarExamineInfoRemarkService.updateById(remark);
					if (is == true) {
						rs.setCode(200);
						rs.setSuccess(true);
						rs.setMsg("整改成功");
					} else {
						rs.setCode(500);
						rs.setSuccess(false);
						rs.setMsg("整改失败");
					}
				});
			}
		}

		if(anbiaoCarExamineInfo.getStatus() == 5){
			anbiaoCarExamineInfo.setYanshourenid(user.getUserId());
			anbiaoCarExamineInfo.setYanshourenname(user.getUserName());
			anbiaoCarExamineInfo.setZgshenheshijian(DateUtil.now());
			anbiaoCarExamineInfo.setZgysrsignatrue(anbiaoCarExamineInfo.getZgysrsignatrue());
			anbiaoCarExamineInfo.setFujianremark(anbiaoCarExamineInfo.getFujianremark());
			anbiaoCarExamineInfo.setStatus(5);
			anbiaoCarExamineInfo.setShenhesignatrue(anbiaoCarExamineInfo.getShenhesignatrue());
			anbiaoCarExamineInfo.setId(anbiaoCarExamineInfo.getId());
			ii = iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo);
			if (ii == true) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("验收归档成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("验收归档失败");
			}
		}

		return rs;
	}

	@PostMapping("/getCarExamineInfoList")
	@ApiLog("安全检查数据-分页列表")
	@ApiOperation(value = "安全检查数据-分页列表", notes = "传入AnbiaoCarExamineInfoPage", position = 6)
	public R<AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfoVO>> getCarExamineInfoList(@RequestBody AnbiaoCarExamineInfoPage carExamineInfoPage, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(401);
			return rs;
		}
		AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfoVO> list= iAnbiaoCarExamineInfoService.selectCarExamineInfoPage(carExamineInfoPage);
		return R.data(list);
	}

	@PostMapping("/saveCarExamineInfoDept")
	@ApiLog("安全检查数据-一键打卡（企管端）")
	@ApiOperation(value = "安全检查数据-一键打卡（企管端）", notes = "传入anbiaoCarExamineInfo", position = 7)
	public R saveCarExamineInfoDept(@RequestBody AnbiaoCarExamineInfo anbiaoCarExamineInfo, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(401);
			return rs;
		}

		boolean ii = false;

		String[] idsss = anbiaoCarExamineInfo.getVehid().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			anbiaoCarExamineInfo.setIsdelete(0);
			anbiaoCarExamineInfo.setCreatetime(DateUtil.now());

			QueryWrapper<AnbiaoCarExamineInfo> examineQueryWrapper = new QueryWrapper<AnbiaoCarExamineInfo>();
			examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getDeptid, anbiaoCarExamineInfo.getDeptid());
			examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getIsdelete, 0);
			examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getDate, anbiaoCarExamineInfo.getDate());
			examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getVehid, idsss[i]);
			AnbiaoCarExamineInfo deail = iAnbiaoCarExamineInfoService.getBaseMapper().selectOne(examineQueryWrapper);
			if(deail == null){
				anbiaoCarExamineInfo.setVehid(idsss[i]);
				anbiaoCarExamineInfo.setJsyid(user.getUserId().toString());
				if(anbiaoCarExamineInfo.getStatus() == 0){
					anbiaoCarExamineInfo.setStatus(0);
					anbiaoCarExamineInfo.setIsdelete(0);
					ii = iAnbiaoCarExamineInfoService.save(anbiaoCarExamineInfo);
					if (ii == true) {
						rs.setCode(200);
						rs.setSuccess(true);
						rs.setMsg("编辑成功");
					} else {
						rs.setCode(500);
						rs.setSuccess(false);
						rs.setMsg("编辑失败");
					}
				}else{
					anbiaoCarExamineInfo.setStatus(1);
					ii = iAnbiaoCarExamineInfoService.save(anbiaoCarExamineInfo);
					if(ii == true) {
						deail = iAnbiaoCarExamineInfoService.getBaseMapper().selectOne(examineQueryWrapper);
						if (deail != null) {
							List<AnbiaoCarExamineInfoRemark> examineInfos = anbiaoCarExamineInfo.getAnbiaoCarExamineInfoRemarkList();
							AnbiaoCarExamineInfo finalDeail = deail;
							examineInfos.forEach(item-> {
								AnbiaoCarExamineInfoRemark remark = new AnbiaoCarExamineInfoRemark();
								remark.setExamid(finalDeail.getId());
								remark.setStatus(1);
								remark.setXiangid(item.getXiangid());
								remark.setFlgremark(item.getFlgremark());
								remark.setFlgimg(item.getFlgimg());
								remark.setTrueimg(item.getTrueimg());
								remark.setTrueremark(item.getTrueremark());
								remark.setCreatetime(DateUtil.now());
								remark.setCreateid(finalDeail.getJsyid());
								boolean is = iAnbiaoCarExamineInfoRemarkService.save(remark);
								if (is == true) {
									rs.setCode(200);
									rs.setSuccess(true);
									rs.setMsg("新增成功");
								} else {
									rs.setCode(500);
									rs.setSuccess(false);
									rs.setMsg("新增失败");
								}
							});
						}
					}
				}
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("该数据已添加");
			}
		}
		return rs;
	}

	@PostMapping("/getCarExamineInfoDateList")
	@ApiLog("安全检查数据-日历统计表")
	@ApiOperation(value = "安全检查数据-日历统计表", notes = "传入AnBiaoCheckCarPage", position = 8)
	public R getCarExamineInfoDateList(@RequestBody AnBiaoCheckCarPage carExamineInfoPage, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(401);
			return rs;
		}
		List<AnbiaoCarExamineInfoVO> list= iAnbiaoCarExamineInfoService.selectAnBiaoCheckCarALLPage(carExamineInfoPage);
		return R.data(list);
	}

	@PostMapping("/getCarExamineInfoTZList")
	@ApiLog("安全检查数据-台账统计表")
	@ApiOperation(value = "安全检查数据-台账统计表", notes = "传入AnbiaoCarExamineInfoPage", position = 9)
	public R getCarExamineInfoTZList(@RequestBody AnbiaoCarExamineInfoPage carExamineInfoPage, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(401);
			return rs;
		}
		AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfoTZVO> list= iAnbiaoCarExamineInfoService.selectCarExamineInfoTZPage(carExamineInfoPage);
		return R.data(list);
	}

	@GetMapping("/goExport_HiddenDanger_Excel")
	@ApiLog("安全检查数据-台账统计表-导出")
	@ApiOperation(value = "安全检查数据-台账统计表-导出", notes = "传入deptId、vehId、beginTime、endTime", position = 22)
	public R goExport_HiddenDanger_Excel(HttpServletRequest request, HttpServletResponse response, String deptId, String vehId , String beginTime , String endTime , BladeUser user) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
		R rs = new R();
		List<String> urlList = new ArrayList<>();
		AnBiaoCheckCarPage anBiaoCheckCarPage = new AnBiaoCheckCarPage();
		anBiaoCheckCarPage.setDeptId(deptId);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath =fileServer.getPathPrefix()+"muban\\"+"DayliExamine.xlsx";
		String [] nyr= DateUtil.today().split("-");
		String[] idsss = anBiaoCheckCarPage.getDeptId().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss= listid.toArray(new String[1]);
		for(int j = 0;j< idss.length;j++){
			anBiaoCheckCarPage.setSize(0);
			anBiaoCheckCarPage.setCurrent(0);
			anBiaoCheckCarPage.setBeginTime(beginTime);
			anBiaoCheckCarPage.setEndTime(endTime);
			anBiaoCheckCarPage.setDeptId(idss[j]);
			anBiaoCheckCarPage.setVehId(vehId);
			List<AnbiaoCarExamineInfoVO> examineInfoList = iAnbiaoCarExamineInfoService.selectAnBiaoCheckCarALLPage(anBiaoCheckCarPage);
			if(examineInfoList.size()==0){

			}else if(examineInfoList.size()>3000){
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			}else{
				for( int i = 0 ; i < examineInfoList.size() ; i++) {
					//Excel中的结果集ListData
					List<CarExamineTJMX> ListData1 = new ArrayList<>();
					List<CarExamineTJMX> ListData2 = new ArrayList<>();
					List<CarExamineTJMX> ListData3 = new ArrayList<>();
					Map<String, Object> map = new HashMap<>();
					String url = "";
					String templateFile = templatePath;
					// 渲染文本
					AnbiaoCarExamineInfoVO t = examineInfoList.get(i);
					CarExamineTJMX one = new CarExamineTJMX();
					map.put("driverName", t.getJiashiyuanxingming());
					map.put("vehNo", t.getCheliangpaizhao());
					map.put("sendDate", anBiaoCheckCarPage.getBeginTime()+"至"+anBiaoCheckCarPage.getEndTime());
					String str = "";
					String dates = "";
					for( int p = 0 ; p < 31 ; p++) {
						String pp = String.valueOf(p);
						if(pp.length() > 1){
							System.out.println(t.getDate().substring(t.getDate().length() - 2));
							dates = t.getDate().substring(t.getDate().length() - 2);
						}else{
							dates = t.getDate().substring(t.getDate().length() - 1);
						}
						if(dates.equals(pp)){
							if(t.getStatus().equals(0)){
								str +="a"+p+":√,";
							}else if(t.getStatus().equals(6)){
								str +="a"+p+":√,";
							}else{
								str +="a"+p+":×,";
							}
						}
					}
					System.out.println(str);
					if(StringUtils.isNotEmpty(str)){
						Class c1 = Class.forName("org.springblade.anbiao.chuchejiancha.entity.CarExamineTJMX");
						CarExamineTJMX ca = (CarExamineTJMX) c1.newInstance();
						String[] arr = str.split(",");
						for(String arrStr : arr){
							Field item = c1.getDeclaredField(arrStr.split(":")[0]);
							item.setAccessible(true);
							item.set(ca,arrStr.split(":")[1]);
						}
					}
					ListData1.add(one);
//					//维修前照片
//					if (StrUtil.isNotEmpty(t.getAfterMaintenance()) && t.getAfterMaintenance().contains("http") == false) {
//						t.setBillAttachment(fileUploadClient.getUrl(t.getAfterMaintenance()));
//						//添加图片到工作表的指定位置
//						try {
//							t.setImgUrl(new URL(t.getAfterMaintenance()));
//						} catch (MalformedURLException e) {
//							e.printStackTrace();
//						}
//						map.put("afterMaintenance", t.getImgUrl());
//					}else if(StrUtil.isNotEmpty(t.getAfterMaintenance())){
//						//添加图片到工作表的指定位置
//						try {
//							t.setImgUrl(new URL(t.getAfterMaintenance()));
//						} catch (MalformedURLException e) {
//							e.printStackTrace();
//						}
//						map.put("afterMaintenance", t.getImgUrl());
//					}else{
//						map.put("afterMaintenance", "无");
//					}
					// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
					// {} 代表普通变量 {.} 代表是list的变量
					// 这里模板 删除了list以后的数据，也就是统计的这一行
					String templateFileName = templateFile;
					//alarmServer.getTemplateUrl()+
					String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2]+"/"+t.getDeptName()+"-车辆日常检查表.xlsx";
					ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
					WriteSheet writeSheet = EasyExcel.writerSheet().build();
					// 写入list之前的数据
					excelWriter.fill(map, writeSheet);
					// 直接写入数据
					excelWriter.fill(ListData1, writeSheet);
					excelWriter.fill(ListData2, writeSheet);
					excelWriter.fill(ListData3, writeSheet);
					excelWriter.finish();
					urlList.add(fileName);
				}
			}
		}
		String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+"车辆日常检查表.zip";
		ExcelUtils.deleteFile(fileName);
		ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream(fileName));
		ApacheZipUtils.doCompress1(urlList, bizOut);
		//不要忘记调用
		bizOut.close();

		rs.setMsg("下载成功");
		rs.setCode(200);
		rs.setData(fileName);
		rs.setSuccess(true);
		return rs;
	}

//	@GetMapping("/goExport_ExamineInfo_Excel")
//	@ApiLog("安全检查数据-车辆安全检查台账统计表-导出")
//	@ApiOperation(value = "安全检查数据-车辆安全检查台账统计表-导出", notes = "传入deptId、vehId、beginTime、endTime", position = 22)
//	public R goExport_ExamineInfo_Excel(HttpServletRequest request, HttpServletResponse response, String deptId, String vehId ,String deptname, String beginTime , String endTime , BladeUser user) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
//		R rs = new R();
//		List<String> urlList = new ArrayList<>();
//		AnBiaoCheckCarPage anBiaoCheckCarPage = new AnBiaoCheckCarPage();
//		anBiaoCheckCarPage.setDeptId(deptId);
//		// TODO 渲染其他类型的数据请参考官方文档
//		DecimalFormat df = new DecimalFormat("######0.00");
//		Calendar now = Calendar.getInstance();
//		//word模板地址
//		String templatePath =fileServer.getPathPrefix()+"muban\\"+"DayliExamineInfo.xlsx";
//		String [] nyr= DateUtil.today().split("-");
//		String[] idsss = anBiaoCheckCarPage.getDeptId().split(",");
//		//去除素组中重复的数组
//		List<String> listid = new ArrayList<String>();
//		for (int i=0; i<idsss.length; i++) {
//			if(!listid.contains(idsss[i])) {
//				listid.add(idsss[i]);
//			}
//		}
//		//返回一个包含所有对象的指定类型的数组
//		String[] idss= listid.toArray(new String[1]);
//		for(int j = 0;j< idss.length;j++){
//			String templateFile = templatePath;
//			//Excel中的结果集ListData
//			List<CarExamineTJMX> ListData1 = new ArrayList<>();
//			Map<String, Object> map = new HashMap<>();
//			anBiaoCheckCarPage.setSize(0);
//			anBiaoCheckCarPage.setCurrent(0);
//			anBiaoCheckCarPage.setBeginTime(beginTime);
//			anBiaoCheckCarPage.setEndTime(endTime);
//			anBiaoCheckCarPage.setDeptId(idss[j]);
//			anBiaoCheckCarPage.setVehId(vehId);
//			if(StringUtils.isNotBlank(anBiaoCheckCarPage.getVehId())){
//				String[] vehIdidsss = anBiaoCheckCarPage.getVehId().split(",");
//				//去除素组中重复的数组
//				List<String> vehIdlistid = new ArrayList<String>();
//				for (int i=0; i<vehIdidsss.length; i++) {
//					if(!vehIdlistid.contains(vehIdidsss[i])) {
//						vehIdlistid.add(vehIdidsss[i]);
//					}
//				}
//				//返回一个包含所有对象的指定类型的数组
//				String[] vehIdidss = vehIdlistid.toArray(new String[1]);
//				anBiaoCheckCarPage.setVehIdidss(vehIdidss);
//			}
//			List<AnbiaoCarExamineInfoVO> examineInfoList = iAnbiaoCarExamineInfoService.selectAnBiaoCheckCarALLPage(anBiaoCheckCarPage);
//			if(examineInfoList.size()==0){
//
//			}else if(examineInfoList.size()>3000){
//				rs.setMsg("数据超过30000条无法下载");
//				rs.setCode(500);
//				return rs;
//			}else{
//				for( int i = 0 ; i < examineInfoList.size() ; i++) {
//					// 渲染文本
//					AnbiaoCarExamineInfoVO t = examineInfoList.get(i);
//					Class c1 = Class.forName("org.springblade.anbiao.chuchejiancha.entity.CarExamineTJMX");
//					CarExamineTJMX ca = (CarExamineTJMX) c1.newInstance();
//					String str = "";
//					String dates = "";
//					ca.setXuhao(i+1);
//					ca.setVehNo(t.getCheliangpaizhao());
//					for( int p = 1 ; p < 32 ; p++) {
//						String pp = String.valueOf(p);
//						if(pp.length() > 1){
//							System.out.println(t.getDate().substring(t.getDate().length() - 2));
//							dates = t.getDate().substring(t.getDate().length() - 2);
//						}else{
//							dates = t.getDate().substring(t.getDate().length() - 1);
//						}
//						if(dates.equals(pp)){
//							if(t.getStatus().equals(0)){
//								str +="a"+p+":√,";
//							}else if(t.getStatus().equals(6)){
//								str +="a"+p+":√,";
//							}else{
//								str +="a"+p+":√,";
//							}
//						}
//					}
//					System.out.println(str);
//					if(StringUtils.isNotEmpty(str)){
//						String[] arr = str.split(",");
//						for(String arrStr : arr){
//							Field item = c1.getDeclaredField(arrStr.split(":")[0]);
//							item.setAccessible(true);
//							item.set(ca,arrStr.split(":")[1]);
//						}
//						ListData1.add(ca);
//					}
//				}
//				// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
//				// {} 代表普通变量 {.} 代表是list的变量
//				// 这里模板 删除了list以后的数据，也就是统计的这一行
//				String templateFileName = templateFile;
//				//alarmServer.getTemplateUrl()+
//				String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2];
//				File newFile = new File(fileName);
//				//判断目标文件所在目录是否存在
//				if(!newFile.exists()){
//					//如果目标文件所在的目录不存在，则创建父目录
//					newFile.mkdirs();
//				}
//				fileName = fileName+"/"+examineInfoList.get(0).getDeptName()+"-车辆安全检查台账.xlsx";
//				ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
//				WriteSheet writeSheet = EasyExcel.writerSheet().build();
//				// 写入list之前的数据
//				excelWriter.fill(map, writeSheet);
//				// 直接写入数据
//				excelWriter.fill(ListData1, writeSheet);
//				excelWriter.finish();
//				urlList.add(fileName);
//			}
//		}
//		String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+"车辆安全检查台账.zip";
//		ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream(fileName));
//		ApacheZipUtils.doCompress1(urlList, bizOut);
//		//不要忘记调用
//		bizOut.close();
//
//		rs.setMsg("下载成功");
//		rs.setCode(200);
//		rs.setData(fileName);
//		rs.setSuccess(true);
//		return rs;
//	}

	@GetMapping("/goExport_ExamineInfo_Excel")
	@ApiLog("安全检查数据-车辆安全检查台账统计表-导出")
	@ApiOperation(value = "安全检查数据-车辆安全检查台账统计表-导出", notes = "传入deptId、vehId、beginTime、endTime", position = 22)
	public R goExport_ExamineInfo_Excel(HttpServletRequest request, HttpServletResponse response, String deptId, String vehId ,String deptname, String beginTime , String endTime , BladeUser user) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
		R rs = new R();
		List<String> urlList = new ArrayList<>();
		AnBiaoCheckCarPage anBiaoCheckCarPage = new AnBiaoCheckCarPage();
		anBiaoCheckCarPage.setDeptId(deptId);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath =fileServer.getPathPrefix()+"muban\\"+"DayliExamineInfo.xlsx";
		String [] nyr= DateUtil.today().split("-");
		String[] idsss = anBiaoCheckCarPage.getDeptId().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss= listid.toArray(new String[1]);
		for(int j = 0;j< idss.length;j++){
			String templateFile = templatePath;
			//Excel中的结果集ListData
			List<CarExamineTJMX> ListData1 = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			anBiaoCheckCarPage.setSize(0);
			anBiaoCheckCarPage.setCurrent(0);
			anBiaoCheckCarPage.setBeginTime(beginTime);
			anBiaoCheckCarPage.setEndTime(endTime);
			anBiaoCheckCarPage.setDeptId(idss[j]);
			anBiaoCheckCarPage.setVehId(vehId);
			if(StringUtils.isNotBlank(anBiaoCheckCarPage.getVehId())){
				String[] vehIdidsss = anBiaoCheckCarPage.getVehId().split(",");
				//去除素组中重复的数组
				List<String> vehIdlistid = new ArrayList<String>();
				for (int i=0; i<vehIdidsss.length; i++) {
					if(!vehIdlistid.contains(vehIdidsss[i])) {
						vehIdlistid.add(vehIdidsss[i]);
					}
				}
				//返回一个包含所有对象的指定类型的数组
				String[] vehIdidss = vehIdlistid.toArray(new String[1]);
				anBiaoCheckCarPage.setVehIdidss(vehIdidss);
			}
			List<AnbiaoCarExamineInfoTZVO> examineInfoList = iAnbiaoCarExamineInfoService.selectDeptVeh(anBiaoCheckCarPage);
			if(examineInfoList.size()==0){

			}else if(examineInfoList.size()>3000){
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			}else{
				for( int i = 0 ; i < examineInfoList.size() ; i++) {
					// 渲染文本
					AnbiaoCarExamineInfoTZVO t = examineInfoList.get(i);
					List<AnbiaoCarExamineInfoTZVO> tt = examineInfoList.get(i).getExamineInfoTZVOList();
					Class c1 = Class.forName("org.springblade.anbiao.chuchejiancha.entity.CarExamineTJMX");
					CarExamineTJMX ca = (CarExamineTJMX) c1.newInstance();
					String str = "";
					String dates = "";
					ca.setXuhao(i+1);
					ca.setVehNo(t.getCheliangpaizhao());
//					if(tt.size() > 0 && tt != null){
						for(int ts = 0 ; ts < tt.size() ; ts++) {
							for( int p = 1 ; p < 32 ; p++) {
								String pp = String.valueOf(p);
								if(pp.length() > 1){
									System.out.println(tt.get(ts).getDate().substring(tt.get(ts).getDate().length() - 2));
									dates = tt.get(ts).getDate().substring(tt.get(ts).getDate().length() - 2);
								}else{
									dates = tt.get(ts).getDate().substring(tt.get(ts).getDate().length() - 1);
								}
								if(dates.equals(pp)){
									if(tt.get(ts).getStatus().equals(0)){
										str +="a"+p+":√,";
									}else if(tt.get(ts).getStatus().equals(6)){
										str +="a"+p+":√,";
									}else if(tt.get(ts).getStatus().equals(1)){
										str +="a"+p+":x,";
									}else{
										str +="a"+p+":--,";
									}
								}
							}
						}
//					}

					System.out.println(str);
					if(StringUtils.isNotEmpty(str)){
						String[] arr = str.split(",");
						for(String arrStr : arr){
							Field item = c1.getDeclaredField(arrStr.split(":")[0]);
							item.setAccessible(true);
							item.set(ca,arrStr.split(":")[1]);
						}
					}
					ListData1.add(ca);
				}
				// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
				// {} 代表普通变量 {.} 代表是list的变量
				// 这里模板 删除了list以后的数据，也就是统计的这一行
				String templateFileName = templateFile;
				//alarmServer.getTemplateUrl()+
				String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2];
				File newFile = new File(fileName);
				//判断目标文件所在目录是否存在
				if(!newFile.exists()){
					//如果目标文件所在的目录不存在，则创建父目录
					newFile.mkdirs();
				}
				fileName = fileName+"/"+examineInfoList.get(0).getDeptName()+"-车辆安全检查台账.xlsx";
				ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
				WriteSheet writeSheet = EasyExcel.writerSheet().build();
				// 写入list之前的数据
				excelWriter.fill(map, writeSheet);
				// 直接写入数据
				excelWriter.fill(ListData1, writeSheet);
				excelWriter.finish();
				urlList.add(fileName);
			}
		}
		String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+"车辆安全检查台账.zip";
		ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream(fileName));
		ApacheZipUtils.doCompress1(urlList, bizOut);
		//不要忘记调用
		bizOut.close();

		rs.setMsg("下载成功");
		rs.setCode(200);
		rs.setData(fileName);
		rs.setSuccess(true);
		return rs;
	}


//	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
////		AnbiaoCarExamineInfoVO t = new AnbiaoCarExamineInfoVO();
////		t.setDate("2023-02-08");
////		t.setStatus(0);
////		String str = "";
////		String dates = "";
////		for( int p = 0 ; p < 31 ; p++) {
////			String pp = String.valueOf(p);
////			if(pp.length() > 1){
////				System.out.println(t.getDate().substring(t.getDate().length() - 2));
////				dates = t.getDate().substring(t.getDate().length() - 2);
////			}else{
////				dates = t.getDate().substring(t.getDate().length() - 1);
////			}
////			if(dates.equals(pp)){
////				if(t.getStatus().equals(0)){
////					str +="a"+p+":√,";
////				}
////				if(t.getStatus().equals(6)){
////					str +="a"+p+":√,";
////				}
////			}
////		}
////		System.out.println(str);
//		String str = "a8:√";
//		Class c1 = Class.forName("org.springblade.anbiao.chuchejiancha.entity.CarExamineTJMX");
//		CarExamineTJMX ca = (CarExamineTJMX) c1.newInstance();
//		String[] arr = str.split(",");
//		for(String arrStr : arr){
//			Field item = c1.getDeclaredField(arrStr.split(":")[0]);
//			item.setAccessible(true);
//			item.set(ca,arrStr.split(":")[1]);
//		}
//		System.out.println(JSON.toJSONString(ca));
//	}

	@PostMapping("/standingBookDetail")
	@ApiLog("安全检查数据-详情")
	@ApiOperation(value = "安全检查数据-详情", notes = "传入AnBiaoCheckCarPage", position = 18)
	public R standingBookDetail(@RequestBody AnBiaoCheckCarPage carExamineInfoPage, BladeUser user) throws ParseException {
		R r = new R();
		if(user == null){
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		List<AnbiaoCarExamineInfoVO> list= iAnbiaoCarExamineInfoService.selectAnBiaoCheckCarALLPage(carExamineInfoPage);
		if(list != null && list.size() >0){
			CarExamineMessageVO messageVO = new CarExamineMessageVO();
			messageVO.setDateShow(carExamineInfoPage.getBeginTime()+"至"+carExamineInfoPage.getEndTime());
			String message = "";
			String days = "";
			int count = 0;
			List<String> stringList = DateUtils.getDays(carExamineInfoPage.getBeginTime(),carExamineInfoPage.getEndTime());
			String[] array2 = stringList.toArray(new String[stringList.size()]);
			int size = list.size();
			String[] array1 = new String[size];
			for (int i = 0; i < list.size(); i++) {
				messageVO.setVehNo(list.get(i).getCheliangpaizhao());
				messageVO.setDriverName(list.get(i).getJiashiyuanxingming());
				array1[i] = list.get(i).getDate();
			}
//			for (AnbiaoCarExamineInfoVO detail:list) {
//				messageVO.setVehNo(detail.getCheliangpaizhao());
//				messageVO.setDriverName(detail.getJiashiyuanxingming());

//				List<String> stringList = DateUtils.getDays(carExamineInfoPage.getBeginTime(),carExamineInfoPage.getEndTime());
//				for (int i = 0; i < stringList.size(); i++) {
//					System.out.println(stringList.get(i)+"------------------"+detail.getDate());
//					if (!stringList.get(i).equals(detail.getDate())) {
//						count += 1;
//						days += stringList.get(i).substring(stringList.get(i).length()-2)+"/";
//					}
//				}
//				SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
//				Date startTime = ft.parse(carExamineInfoPage.getBeginTime());
//				Date endTime = ft.parse(carExamineInfoPage.getEndTime());
//				Date nowTime = ft.parse(detail.getDate());
//				boolean effectiveDate = DateUtils.isEffectiveDate(nowTime, startTime, endTime);
//				if (effectiveDate) {
//					System.out.println("当前时间在范围内");
//				}else {
//					count += 1;
//					days += detail.getDate().substring(detail.getDate().length()-2)+"/";
//				}

//			}
			System.out.println(array1);
			System.out.println("++++++++++++++++++++++++++++++++++");
			System.out.println(array2);
			List<String> stringlist = StringUtil.compare(array1,array2);
			for (String integer : stringlist) {
				count += 1;
				days += integer.substring(integer.length()-2)+"/";
			}
			messageVO.setCount(count);
			message += count+"次"+"未做日常检查（"+days+"）";
			messageVO.setMessage(message);
			r.setData(messageVO);
			r.setCode(200);
			r.setMsg("获取成功");
			return r;
		}else {
			r.setCode(200);
			r.setMsg("暂无数据");
			return r;
		}
	}


}
