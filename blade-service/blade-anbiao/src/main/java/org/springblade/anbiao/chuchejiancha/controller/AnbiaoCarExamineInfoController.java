package org.springblade.anbiao.chuchejiancha.controller;


import cn.afterturn.easypoi.word.entity.WordImageEntity;
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
import org.springblade.anbiao.anquanhuiyi.VO.AnbiaoAnquanhuiyiDetailVO;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiDetail;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;
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
import org.springblade.anbiao.chuchejiancha.vo.SafetyCheckMingXiVO;
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
import java.net.MalformedURLException;
import java.net.URL;
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


	@GetMapping("/AnQuanJianChaMiXi_goExport_Excel")
	@ApiLog("安全检查明细-导出")
	@ApiOperation(value = "安全检查明细-导出", notes = "传入deptId、date", position = 22)
	public R goExport_HiddenDanger_Excel(HttpServletRequest request, HttpServletResponse response, String vehid, String deptId, String date, BladeUser user) throws IOException, ParseException {

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String[] words = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "aa", "bb", "cc", "dd","ee"};
		String aaa="未检查";
		String bbb="未检查";
		String ccc="未检查";
		String ddd="未检查";
		String eee="未检查";
		String fff="未检查";
		String ggg="未检查";
		String hhh="未检查";
		String iii="未检查";
		String jjj="未检查";
		String kkk="未检查";
		String lll="未检查";
		String mmm="未检查";
		String nnn="未检查";
		String ooo="未检查";
		String ppp="未检查";
		String qqq="未检查";
		String rrr="未检查";
		String sss="未检查";
		String ttt="未检查";
		String uuu="未检查";
		String vvv="未检查";
		String www="未检查";
		String xxx="未检查";
		String yyy="未检查";
		String zzz="未检查";
		String aaaa="未检查";
		String bbbb="未检查";
		String cccc="未检查";
		String dddd="未检查";
		String eeee="未检查";

		String uuid = UUID.randomUUID().toString().replace("-", "");

		R rs = new R();
		List<String> urlList = new ArrayList<>();
		SafetyCheckMingXiVO safetyCheckMingXiVO = new SafetyCheckMingXiVO();
		safetyCheckMingXiVO.setDeptId("1");
		safetyCheckMingXiVO.setDate(date);
		safetyCheckMingXiVO.setVehid(vehid);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath = fileServer.getPathPrefix() + "muban\\" + "AnQuanJianChaMiXi3.xlsx";
		String[] nyr = DateUtil.today().split("-");
		String[] idsss = safetyCheckMingXiVO.getVehid().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i = 0; i < idsss.length; i++) {
			if (!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss = listid.toArray(new String[1]);
		for (int j = 0; j < idss.length; j++) {
			safetyCheckMingXiVO.setVehid(idss[j]);
			Map<String, Object> map = new HashMap<>();
			List<SafetyCheckMingXiVO> safetyCheckMingXiVOS = iAnbiaoCarExamineInfoService.selectSafetyCheckMingXi(safetyCheckMingXiVO);
			for (String word : words) {
				for (int k = 0; k <= 35; k++) {
					String kk = word + k;
					map.put(kk, "×");
				}
			}

			//Excel中的结果集ListData
			if (safetyCheckMingXiVOS.size() == 0) {

			} else if (safetyCheckMingXiVOS.size() > 3000) {
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			} else {
				for (int i = 0; i < safetyCheckMingXiVOS.size(); i++) {

					// 渲染文本
					SafetyCheckMingXiVO t = safetyCheckMingXiVOS.get(i);
					map.put("deptName",t.getDeptName());
					map.put("jiashiyuanxingming", t.getJiashiyuanxingming());
					map.put("cheliangpaizhao", t.getCheliangpaizhao());

					if (StrUtil.isNotEmpty(t.getJcrsignatrue()) && t.getJcrsignatrue().contains("http") == false) {
						t.setJcrsignatrue(fileUploadClient.getUrl(t.getJcrsignatrue()));
						//添加图片到工作表的指定位置
						try {
							t.setImage(new URL(t.getJcrsignatrue()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						t.setImage(t.getImage());
					}else if(StrUtil.isNotEmpty(t.getJcrsignatrue())){
						//添加图片到工作表的指定位置
						try {
							t.setImage(new URL(t.getJcrsignatrue()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						t.setImage(t.getImage());
					}else{
						t.setImage(null);
					}
					URL image = t.getImage();

					Date parse = sf.parse(t.getDate());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(parse);
					String year = String.valueOf(calendar.get(Calendar.YEAR));
					map.put("year", year);
					String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
					map.put("month", month);
					String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
					if (day.equals("1")){
						if (t.getStatus().equals("0")) {
							for (int k = 0; k <= 35; k++) {
								String kk = "a" + k;
								map.put(kk, "√");
							}
							map.put("a34",t.getJiashiyuanxingming());
							map.put("a35", image);
						}
						if (t.getStatus().equals("6")) {
							for (int k = 0; k <= 35; k++) {
								String kk = "a" + k;
								map.put(kk, "#");
							}
							map.put("a34",t.getJiashiyuanxingming());
							map.put("a35", image);
						}
						if (t.getStatus().equals("1")) {
							if (aaa.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "a" + k;
									map.put(kk, "√");
								}
								aaa="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("a1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("a2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("a3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("a4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("a5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("a6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("a7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("a8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("a9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("a10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("a11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("a12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("a13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("a14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("a15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("a16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("a17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("a18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("a19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("a20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("a21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("a22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("a23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("a24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("a25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("a26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("a27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("a28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("a29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("a30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("a31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("a32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("a33","*");
							}
							map.put("a34",t.getJiashiyuanxingming());
							map.put("a35", image);

						}
					}
					if (day.equals("2")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "b" + k;
								map.put(kk, "√");
							}
							map.put("b34",t.getJiashiyuanxingming());
							map.put("b35", image);
						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "b" + k;
								map.put(kk, "#");
							}
							map.put("b34",t.getJiashiyuanxingming());
							map.put("b35", image);
						}
						if (t.getStatus().equals("1")) {
							if (bbb.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "b" + k;
									map.put(kk, "√");
								}
								bbb="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("b1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("b2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("b3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("b4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("b5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("b6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("b7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("b8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("b9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("b10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("b11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("b12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("b13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("b14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("b15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("b16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("b17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("b18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("b19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("b20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("b21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("b22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("b23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("b24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("b25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("b26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("b27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("b28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("b29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("b30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("b31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("b32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("b33","*");
							}
							map.put("b34",t.getJiashiyuanxingming());
							map.put("b35", image);

						}
					}
					if (day.equals("3")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "c" + k;
								map.put(kk, "√");
							}
							map.put("c34",t.getJiashiyuanxingming());
							map.put("c35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "c" + k;
								map.put(kk, "#");
							}
							map.put("c34",t.getJiashiyuanxingming());
							map.put("c35", image);

						}
						if (t.getStatus().equals("1")) {
							if (ccc.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "c" + k;
									map.put(kk, "√");
								}
								ccc="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("c1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("c2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("c3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("c4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("c5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("c6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("c7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("c8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("c9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("c10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("c11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("c12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("c13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("c14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("c15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("c16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("c17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("c18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("c19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("c20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("c21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("c22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("c23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("c24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("c25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("c26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("c27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("c28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("c29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("c30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("c31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("c32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("c33","*");
							}
							map.put("c34",t.getJiashiyuanxingming());
							map.put("c35", image);

						}
					}
					if (day.equals("4")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "d" + k;
								map.put(kk, "√");
							}
							map.put("d34",t.getJiashiyuanxingming());
							map.put("d35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "d" + k;
								map.put(kk, "#");
							}
							map.put("d34",t.getJiashiyuanxingming());
							map.put("d35", image);

						}
						if (t.getStatus().equals("1")) {
							if (ddd.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "d" + k;
									map.put(kk, "√");
								}
								ddd="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("d1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("d2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("d3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("d4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("d5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("d6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("d7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("d8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("d9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("d10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("d11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("d12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("d13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("d14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("d15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("d16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("d17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("d18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("d19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("d20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("d21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("d22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("d23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("d24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("d25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("d26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("d27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("d28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("d29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("d30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("d31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("d32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("d33","*");
							}
							map.put("d34",t.getJiashiyuanxingming());
							map.put("d35", image);

						}
					}
					if (day.equals("5")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "e" + k;
								map.put(kk, "√");
							}
							map.put("e34",t.getJiashiyuanxingming());
							map.put("e35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "e" + k;
								map.put(kk, "#");
							}
							map.put("e34",t.getJiashiyuanxingming());
							map.put("e35", image);

						}
						if (t.getStatus().equals("1")) {
							if (eee.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "e" + k;
									map.put(kk, "√");
								}
								eee="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("e1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("e2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("e3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("e4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("e5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("e6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("e7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("e8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("e9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("e10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("e11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("e12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("e13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("e14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("e15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("e16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("e17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("e18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("e19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("e20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("e21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("e22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("e23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("e24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("e25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("e26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("e27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("e28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("e29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("e30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("e31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("e32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("e33","*");
							}
							map.put("e34",t.getJiashiyuanxingming());
							map.put("e35", image);

						}
					}
					if (day.equals("6")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "f" + k;
								map.put(kk, "√");
							}
							map.put("f34",t.getJiashiyuanxingming());
							map.put("f35",image);
						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "f" + k;
								map.put(kk, "#");
							}
							map.put("f34",t.getJiashiyuanxingming());
							map.put("f35",image);
						}
						if (t.getStatus().equals("1")) {
							if (fff.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "f" + k;
									map.put(kk, "√");
								}
								fff="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("f1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("f2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("f3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("f4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("f5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("f6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("f7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("f8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("f9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("f10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("f11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("f12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("f13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("f14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("f15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("f16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("f17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("f18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("f19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("f20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("f21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("f22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("f23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("f24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("f25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("f26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("f27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("f28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("f29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("f30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("f31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("f32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("f33","*");
							}
							map.put("f34",t.getJiashiyuanxingming());
							map.put("f35",image);
						}
					}
					if (day.equals("7")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "g" + k;
								map.put(kk, "√");
							}
							map.put("g34",t.getJiashiyuanxingming());
							map.put("g35", image);
						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "g" + k;
								map.put(kk, "#");
							}
							map.put("g34",t.getJiashiyuanxingming());
							map.put("g35", image);
						}
						if (t.getStatus().equals("1")) {
							if (ggg.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "g" + k;
									map.put(kk, "√");
								}
								ggg="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("g1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("g2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("g3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("g4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("g5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("g6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("g7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("g8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("g9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("g10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("g11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("g12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("g13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("g14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("g15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("g16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("g17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("g18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("g19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("g20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("g21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("g22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("g23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("g24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("g25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("g26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("g27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("g28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("g29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("g30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("g31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("g32","*");
							}
							if (t.getXiangid().equals("71")) {
								map.put("g33", "*");
							}
							map.put("g34",t.getJiashiyuanxingming());
							map.put("g35", image);

						}
					}
					if (day.equals("8")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "h" + k;
								map.put(kk, "√");
							}
							map.put("h34",t.getJiashiyuanxingming());
							map.put("h35", image);
						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "h" + k;
								map.put(kk, "#");
							}
							map.put("h34",t.getJiashiyuanxingming());
							map.put("h35", image);
						}
						if (t.getStatus().equals("1")) {
							if (hhh.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "h" + k;
									map.put(kk, "√");
								}
								hhh="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("h1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("h2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("h3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("h4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("h5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("h6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("h7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("h8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("h9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("h10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("h11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("h12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("h13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("h14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("h15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("h16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("h17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("h18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("h19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("h20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("h21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("h22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("h23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("h24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("h25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("h26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("h27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("h28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("h29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("h30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("h31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("h32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("h33","*");
							}
							map.put("h34",t.getJiashiyuanxingming());
							map.put("h35", image);

						}
					}
					if (day.equals("9")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "i" + k;
								map.put(kk, "√");
							}
							map.put("i34",t.getJiashiyuanxingming());
							map.put("i35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "i" + k;
								map.put(kk, "#");
							}
							map.put("i34",t.getJiashiyuanxingming());
							map.put("i35", image);

						}
						if (t.getStatus().equals("1")) {
							if (iii.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "i" + k;
									map.put(kk, "√");
								}
								iii="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("i1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("i2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("i3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("i4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("i5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("i6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("i7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("i8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("i9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("i10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("i11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("i12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("i13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("i14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("i15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("i16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("i17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("i18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("i19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("i20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("i21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("i22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("i23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("i24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("i25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("i26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("i27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("i28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("i29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("i30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("i31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("i32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("i33","*");
							}
							map.put("i34",t.getJiashiyuanxingming());
							map.put("i35", image);

						}
					}
					if (day.equals("10")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "j" + k;
								map.put(kk, "√");
							}
							map.put("j34",t.getJiashiyuanxingming());
							map.put("j35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "j" + k;
								map.put(kk, "#");
							}
							map.put("j34",t.getJiashiyuanxingming());
							map.put("j35", image);

						}
						if (t.getStatus().equals("1")) {
							if (jjj.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "j" + k;
									map.put(kk, "√");
								}
								jjj="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("j1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("j2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("j3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("j4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("j5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("j6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("j7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("j8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("j9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("j10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("j11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("j12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("j13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("j14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("j15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("j16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("j17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("j18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("j19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("j20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("j21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("j22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("j23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("j24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("j25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("j26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("j27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("j28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("j29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("j30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("j31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("j32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("j33","*");
							}
							map.put("j34",t.getJiashiyuanxingming());
							map.put("j35", image);

						}
					}
					if (day.equals("11")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "k" + k;
								map.put(kk, "√");
							}
							map.put("k34",t.getJiashiyuanxingming());
							map.put("k35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "k" + k;
								map.put(kk, "#");
							}
							map.put("k34",t.getJiashiyuanxingming());
							map.put("k35", image);

						}
						if (t.getStatus().equals("1")) {
							if (kkk.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "k" + k;
									map.put(kk, "√");
								}
								kkk="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("k1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("k2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("k3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("k4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("k5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("k6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("k7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("k8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("k9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("k10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("k11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("k12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("k13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("k14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("k15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("k16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("k17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("k18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("k19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("k20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("k21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("k22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("k23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("k24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("k25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("k26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("k27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("k28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("k29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("k30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("k31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("k32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("k33","*");
							}
							map.put("k34",t.getJiashiyuanxingming());
							map.put("k35", image);

						}
					}
					if (day.equals("12")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "l" + k;
								map.put(kk, "√");
							}
							map.put("l34",t.getJiashiyuanxingming());
							map.put("l35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "l" + k;
								map.put(kk, "#");
							}
							map.put("l34",t.getJiashiyuanxingming());
							map.put("l35", image);

						}
						if (t.getStatus().equals("1")) {
							if (lll.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "l" + k;
									map.put(kk, "√");
								}
								lll="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("l1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("l2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("l3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("l4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("l5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("l6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("l7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("l8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("l9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("l10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("l11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("l12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("l13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("l14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("l15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("l16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("l17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("l18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("l19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("l20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("l21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("l22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("l23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("l24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("l25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("l26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("l27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("l28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("l29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("l30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("l31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("l32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("l33","*");
							}
							map.put("l34",t.getJiashiyuanxingming());
							map.put("l35", image);

						}
					}
					if (day.equals("13")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "m" + k;
								map.put(kk, "√");
							}
							map.put("m34",t.getJiashiyuanxingming());
							map.put("m35",image);
						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "m" + k;
								map.put(kk, "#");
							}
							map.put("m34",t.getJiashiyuanxingming());
							map.put("m35",image);
						}
						if (t.getStatus().equals("1")) {
							if (mmm.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "m" + k;
									map.put(kk, "√");
								}
								mmm="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("m1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("m2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("m3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("m4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("m5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("m6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("m7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("m8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("m9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("m10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("m11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("m12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("m13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("m14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("m15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("m16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("m17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("m18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("m19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("m20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("m21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("m22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("m23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("m24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("m25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("m26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("m27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("m28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("m29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("m30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("m31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("m32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("m33","*");
							}
							map.put("m34",t.getJiashiyuanxingming());
							map.put("m35",image);
						}
					}
					if (day.equals("14")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "n" + k;
								map.put(kk, "√");
							}
							map.put("n34",t.getJiashiyuanxingming());
							map.put("n35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "n" + k;
								map.put(kk, "#");
							}
							map.put("n34",t.getJiashiyuanxingming());
							map.put("n35", image);

						}
						if (t.getStatus().equals("1")) {
							if (nnn.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "n" + k;
									map.put(kk, "√");
								}
								nnn="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("n1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("n2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("n3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("n4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("n5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("n6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("n7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("n8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("n9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("n10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("n11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("n12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("n13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("n14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("n15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("n16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("n17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("n18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("n19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("n20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("n21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("n22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("n23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("n24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("n25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("n26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("n27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("n28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("n29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("n30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("n31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("n32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("n33","*");
							}
							map.put("n34",t.getJiashiyuanxingming());
							map.put("n35", image);

						}
					}
					if (day.equals("15")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "o" + k;
								map.put(kk, "√");
							}
							map.put("o34",t.getJiashiyuanxingming());
							map.put("o35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "o" + k;
								map.put(kk, "#");
							}
							map.put("o34",t.getJiashiyuanxingming());
							map.put("o35", image);

						}
						if (t.getStatus().equals("1")) {
							if (ooo.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "o" + k;
									map.put(kk, "√");
								}
								ooo="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("o1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("o2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("o3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("o4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("o5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("o6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("o7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("o8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("o9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("o10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("o11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("o12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("o13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("o14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("o15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("o16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("o17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("o18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("o19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("o20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("o21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("o22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("o23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("o24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("o25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("o26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("o27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("o28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("o29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("o30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("o31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("o32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("o33","*");
							}
							map.put("o34",t.getJiashiyuanxingming());
							map.put("o35", image);

						}
					}
					if (day.equals("16")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "p" + k;
								map.put(kk, "√");
							}
							map.put("p34",t.getJiashiyuanxingming());
							map.put("p35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "p" + k;
								map.put(kk, "#");
							}
							map.put("p34",t.getJiashiyuanxingming());
							map.put("p35", image);

						}
						if (t.getStatus().equals("1")) {
							if (ppp.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "p" + k;
									map.put(kk, "√");
								}
								ppp="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("p1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("p2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("p3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("p4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("p5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("p6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("p7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("p8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("p9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("p10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("p11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("p12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("p13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("p14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("p15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("p16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("p17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("p18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("p19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("p20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("p21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("p22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("p23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("p24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("p25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("p26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("p27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("p28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("p29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("p30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("p31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("a32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("a33","*");
							}
							map.put("p34",t.getJiashiyuanxingming());
							map.put("p35", image);

						}
					}
					if (day.equals("17")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "q" + k;
								map.put(kk, "√");
							}
							map.put("q34",t.getJiashiyuanxingming());
							map.put("q35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "q" + k;
								map.put(kk, "#");
							}
							map.put("q34",t.getJiashiyuanxingming());
							map.put("q35", image);

						}
						if (t.getStatus().equals("1")) {
							if (qqq.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "q" + k;
									map.put(kk, "√");
								}
								qqq="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("q1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("q2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("q3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("q4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("q5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("q6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("q7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("q8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("q9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("q10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("q11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("q12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("q13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("q14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("q15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("q16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("q17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("q18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("q19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("q20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("q21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("q22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("q23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("q24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("q25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("q26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("q27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("q28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("q29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("q30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("q31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("q32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("q33","*");
							}
							map.put("q34",t.getJiashiyuanxingming());
							map.put("q35", image);

						}
					}
					if (day.equals("18")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "r" + k;
								map.put(kk, "√");
							}
							map.put("r34",t.getJiashiyuanxingming());
							map.put("r35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "r" + k;
								map.put(kk, "#");
							}
							map.put("r34",t.getJiashiyuanxingming());
							map.put("r35", image);

						}
						if (t.getStatus().equals("1")) {
							if (rrr.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "r" + k;
									map.put(kk, "√");
								}
								rrr="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("r1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("r2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("r3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("r4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("r5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("r6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("r7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("r8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("r9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("r10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("r11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("r12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("r13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("r14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("r15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("r16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("r17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("r18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("r19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("r20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("r21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("r22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("r23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("r24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("r25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("r26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("r27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("r28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("r29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("r30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("r31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("r32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("r33","*");
							}
							map.put("r34",t.getJiashiyuanxingming());
							map.put("r35", image);

						}
					}
					if (day.equals("19")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "s" + k;
								map.put(kk, "√");
							}
							map.put("s34",t.getJiashiyuanxingming());
							map.put("s35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "s" + k;
								map.put(kk, "#");
							}
							map.put("s34",t.getJiashiyuanxingming());
							map.put("s35", image);

						}
						if (t.getStatus().equals("1")) {
							if (sss.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "s" + k;
									map.put(kk, "√");
								}
								sss="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("s1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("s2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("s3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("s4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("s5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("s6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("s7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("s8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("s9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("s10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("s11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("s12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("s13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("s14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("s15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("s16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("s17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("s18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("s19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("s20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("s21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("s22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("s23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("s24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("s25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("s26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("s27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("s28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("s29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("s30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("s31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("s32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("s33","*");
							}
							map.put("s34",t.getJiashiyuanxingming());
							map.put("s35", image);

						}
					}
					if (day.equals("20")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "t" + k;
								map.put(kk, "√");
							}
							map.put("t34",t.getJiashiyuanxingming());
							map.put("t35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "t" + k;
								map.put(kk, "#");
							}
							map.put("t34",t.getJiashiyuanxingming());
							map.put("t35", image);

						}
						if (t.getStatus().equals("1")) {
							if (ttt.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "t" + k;
									map.put(kk, "√");
								}
								ttt="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("t1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("t2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("t3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("t4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("t5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("t6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("t7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("t8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("t9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("t10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("t11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("t12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("t13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("t14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("t15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("t16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("t17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("t18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("t19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("t20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("t21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("t22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("t23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("t24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("t25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("t26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("t27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("t28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("t29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("t30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("t31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("t32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("t33","*");
							}
							map.put("t34",t.getJiashiyuanxingming());
							map.put("t35", image);

						}
					}
					if (day.equals("21")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "u" + k;
								map.put(kk, "√");
							}
							map.put("u34",t.getJiashiyuanxingming());
							map.put("u35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "u" + k;
								map.put(kk, "#");
							}
							map.put("u34",t.getJiashiyuanxingming());
							map.put("u35", image);

						}
						if (t.getStatus().equals("1")) {
							if (uuu.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "u" + k;
									map.put(kk, "√");
								}
								uuu="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("u1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("u2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("u3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("u4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("u5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("u6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("u7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("u8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("u9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("u10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("u11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("u12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("u13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("u14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("u15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("u16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("u17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("u18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("u19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("u20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("u21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("u22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("u23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("u24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("u25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("u26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("u27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("u28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("u29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("u30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("u31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("u32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("u33","*");
							}
							map.put("u34",t.getJiashiyuanxingming());
							map.put("u35", image);

						}
					}
					if (day.equals("22")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "v" + k;
								map.put(kk, "√");
							}
							map.put("v34",t.getJiashiyuanxingming());
							map.put("v35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "v" + k;
								map.put(kk, "#");
							}
							map.put("v34",t.getJiashiyuanxingming());
							map.put("v35", image);

						}
						if (t.getStatus().equals("1")) {
							if (vvv.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "v" + k;
									map.put(kk, "√");
								}
								vvv="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("v1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("v2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("v3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("v4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("v5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("v6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("v7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("v8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("v9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("v10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("v11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("v12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("v13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("v14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("v15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("v16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("v17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("v18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("v19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("v20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("v21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("v22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("v23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("v24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("v25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("v26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("v27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("v28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("v29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("v30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("v31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("v32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("v33","*");
							}
							map.put("v34",t.getJiashiyuanxingming());
							map.put("v35", image);

						}
					}
					if (day.equals("23")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "w" + k;
								map.put(kk, "√");
							}
							map.put("w34",t.getJiashiyuanxingming());
							map.put("w35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "w" + k;
								map.put(kk, "#");
							}
							map.put("w34",t.getJiashiyuanxingming());
							map.put("w35", image);

						}
						if (t.getStatus().equals("1")) {
							if (www.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "w" + k;
									map.put(kk, "√");
								}
								www="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("w1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("w2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("w3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("w4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("w5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("w6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("w7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("w8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("w9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("w10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("w11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("w12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("w13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("w14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("w15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("w16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("w17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("w18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("w19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("w20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("w21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("w22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("w23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("w24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("w25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("w26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("w27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("w28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("w29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("w30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("w31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("w32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("w33","*");
							}
							map.put("w34",t.getJiashiyuanxingming());
							map.put("w35", image);

						}
					}
					if (day.equals("24")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "x" + k;
								map.put(kk, "√");
							}
							map.put("x34",t.getJiashiyuanxingming());
							map.put("x35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "x" + k;
								map.put(kk, "#");
							}
							map.put("x34",t.getJiashiyuanxingming());
							map.put("x35", image);

						}
						if (t.getStatus().equals("1")) {
							if (xxx.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "x" + k;
									map.put(kk, "√");
								}
								xxx="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("x1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("x2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("x3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("x4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("x5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("x6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("x7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("x8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("x9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("x10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("x11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("x12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("x13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("x14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("x15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("x16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("x17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("x18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("x19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("x20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("x21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("x22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("x23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("x24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("x25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("x26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("x27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("x28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("x29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("x30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("x31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("x32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("x33","*");
							}
							map.put("x34",t.getJiashiyuanxingming());
							map.put("x35", image);

						}
					}
					if (day.equals("25")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "y" + k;
								map.put(kk, "√");
							}
							map.put("y34",t.getJiashiyuanxingming());
							map.put("y35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "y" + k;
								map.put(kk, "#");
							}
							map.put("y34",t.getJiashiyuanxingming());
							map.put("y35", image);

						}
						if (t.getStatus().equals("1")) {
							if (yyy.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "y" + k;
									map.put(kk, "√");
								}
								yyy="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("y1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("y2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("y3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("y4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("y5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("y6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("y7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("y8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("y9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("y10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("y11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("y12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("y13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("y14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("y15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("y16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("y17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("y18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("y19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("y20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("y21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("y22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("y23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("y24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("y25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("y26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("y27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("y28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("y29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("y30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("y31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("y32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("y33","*");
							}
							map.put("y34",t.getJiashiyuanxingming());
							map.put("y35", image);

						}
					}
					if (day.equals("26")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "z" + k;
								map.put(kk, "√");
							}
							map.put("z34",t.getJiashiyuanxingming());
							map.put("z35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "z" + k;
								map.put(kk, "#");
							}
							map.put("z34",t.getJiashiyuanxingming());
							map.put("z35", image);

						}
						if (t.getStatus().equals("1")) {
							if (zzz.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "z" + k;
									map.put(kk, "√");
								}
								zzz="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("z1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("z2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("z3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("z4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("z5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("z6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("z7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("z8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("z9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("z10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("z11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("z12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("z13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("z14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("z15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("z16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("z17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("z18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("z19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("z20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("z21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("z22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("z23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("z24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("z25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("z26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("z27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("z28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("z29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("z30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("z31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("z32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("z33","*");
							}
							map.put("z34",t.getJiashiyuanxingming());
							map.put("z35", image);

						}
					}
					if (day.equals("27")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "aa" + k;
								map.put(kk, "√");
							}
							map.put("aa34",t.getJiashiyuanxingming());
							map.put("aa35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "aa" + k;
								map.put(kk, "#");
							}
							map.put("aa34",t.getJiashiyuanxingming());
							map.put("aa35", image);

						}
						if (t.getStatus().equals("1")) {
							if (aaaa.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "aa" + k;
									map.put(kk, "√");
								}
								aaaa="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("aa1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("aa2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("aa3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("aa4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("aa5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("aa6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("aa7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("aa8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("aa9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("aa10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("aa11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("aa12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("aa13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("aa14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("aa15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("aa16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("aa17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("aa18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("aa19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("aa20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("aa21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("aa22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("aa23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("aa24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("aa25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("aa26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("aa27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("aa28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("aa29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("aa30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("aa31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("aa32","*");
							}
							if (t.getXiangid().equals("71")) {
								map.put("aa33", "*");
							}
							map.put("aa34",t.getJiashiyuanxingming());
							map.put("aa35", image);

						}
					}
					if (day.equals("28")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "bb" + k;
								map.put(kk, "√");
							}
							map.put("bb34",t.getJiashiyuanxingming());
							map.put("bb35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "bb" + k;
								map.put(kk, "#");
							}
							map.put("bb34",t.getJiashiyuanxingming());
							map.put("bb35", image);

						}
						if (t.getStatus().equals("1")) {
							if (bbbb.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "bb" + k;
									map.put(kk, "√");
								}
								bbbb="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("bb1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("bb2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("bb3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("bb4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("bb5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("bb6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("bb7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("bb8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("bb9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("bb10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("bb11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("bb12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("bb13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("bb14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("bb15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("bb16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("bb17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("bb18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("bb19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("bb20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("bb21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("bb22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("bb23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("bb24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("bb25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("bb26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("bb27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("bb28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("bb29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("bb30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("bb31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("bb32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("bb33","*");
							}
							map.put("bb34",t.getJiashiyuanxingming());
							map.put("bb35", image);

						}
					}
					if (day.equals("29")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "cc" + k;
								map.put(kk, "√");
							}
							map.put("cc34",t.getJiashiyuanxingming());
							map.put("cc35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "cc" + k;
								map.put(kk, "#");
							}
							map.put("cc34",t.getJiashiyuanxingming());
							map.put("cc35", image);

						}
						if (t.getStatus().equals("1")) {
							if (cccc.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "cc" + k;
									map.put(kk, "√");
								}
								cccc="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("cc1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("cc2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("cc3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("cc4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("cc5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("cc6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("cc7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("cc8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("cc9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("cc10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("cc11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("cc12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("cc13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("cc14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("cc15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("cc16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("cc17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("cc18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("cc19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("cc20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("cc21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("cc22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("cc23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("cc24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("cc25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("cc26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("cc27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("cc28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("cc29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("cc30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("cc31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("cc32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("cc33","*");
							}
							map.put("cc34",t.getJiashiyuanxingming());
							map.put("cc35", image);

						}
					}
					if (day.equals("30")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "dd" + k;
								map.put(kk, "√");
							}
							map.put("dd34",t.getJiashiyuanxingming());
							map.put("dd35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "dd" + k;
								map.put(kk, "#");
							}
							map.put("dd34",t.getJiashiyuanxingming());
							map.put("dd35", image);

						}
						if (t.getStatus().equals("1")) {
							if (dddd.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "dd" + k;
									map.put(kk, "√");
								}
								dddd="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("dd1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("dd2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("dd3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("dd4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("dd5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("dd6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("dd7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("dd8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("dd9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("dd10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("dd11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("dd12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("dd13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("dd14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("dd15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("dd16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("dd17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("dd18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("dd19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("dd20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("dd21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("dd22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("dd23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("dd24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("dd25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("dd26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("dd27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("dd28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("dd29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("dd30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("dd31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("dd32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("dd33","*");
							}
							map.put("dd34",t.getJiashiyuanxingming());
							map.put("dd35", image);

						}
					}
					if (day.equals("31")){
						if (t.getStatus().equals("0")){
							for (int k = 0; k <= 35; k++) {
								String kk = "ee" + k;
								map.put(kk, "√");
							}
							map.put("ee34",t.getJiashiyuanxingming());
							map.put("ee35", image);

						}
						if (t.getStatus().equals("6")){
							for (int k = 0; k <= 35; k++) {
								String kk = "ee" + k;
								map.put(kk, "#");
							}
							map.put("ee34",t.getJiashiyuanxingming());
							map.put("ee35", image);

						}

						if (t.getStatus().equals("1")) {
							if (eeee.equals("未检查")){
								for (int k = 0; k <= 35; k++) {
									String kk = "ee" + k;
									map.put(kk, "√");
								}
								eeee="已检查";
							}
							if (t.getXiangid().equals("6")){
								map.put("ee1","*");
							}
							if (t.getXiangid().equals("7")){
								map.put("ee2","*");
							}
							if (t.getXiangid().equals("8")){
								map.put("ee3","*");
							}
							if (t.getXiangid().equals("42")){
								map.put("ee4","*");
							}
							if (t.getXiangid().equals("43")){
								map.put("ee5","*");
							}
							if (t.getXiangid().equals("44")){
								map.put("ee6","*");
							}
							if (t.getXiangid().equals("45")){
								map.put("ee7","*");
							}
							if (t.getXiangid().equals("46")){
								map.put("ee8","*");
							}
							if (t.getXiangid().equals("47")){
								map.put("ee9","*");
							}
							if (t.getXiangid().equals("48")){
								map.put("ee10","*");
							}
							if (t.getXiangid().equals("49")){
								map.put("ee11","*");
							}
							if (t.getXiangid().equals("50")){
								map.put("ee12","*");
							}
							if (t.getXiangid().equals("51")){
								map.put("ee13","*");
							}
							if (t.getXiangid().equals("52")){
								map.put("ee14","*");
							}
							if (t.getXiangid().equals("53")){
								map.put("ee15","*");
							}
							if (t.getXiangid().equals("54")){
								map.put("ee16","*");
							}
							if (t.getXiangid().equals("55")){
								map.put("ee17","*");
							}
							if (t.getXiangid().equals("56")){
								map.put("ee18","*");
							}
							if (t.getXiangid().equals("57")){
								map.put("ee19","*");
							}
							if (t.getXiangid().equals("58")){
								map.put("ee20","*");
							}
							if (t.getXiangid().equals("59")){
								map.put("ee21","*");
							}
							if (t.getXiangid().equals("60")){
								map.put("ee22","*");
							}
							if (t.getXiangid().equals("61")){
								map.put("ee23","*");
							}
							if (t.getXiangid().equals("62")){
								map.put("ee24","*");
							}
							if (t.getXiangid().equals("63")){
								map.put("ee25","*");
							}
							if (t.getXiangid().equals("64")){
								map.put("ee26","*");
							}
							if (t.getXiangid().equals("65")){
								map.put("ee27","*");
							}
							if (t.getXiangid().equals("66")){
								map.put("ee28","*");
							}
							if (t.getXiangid().equals("67")){
								map.put("ee29","*");
							}
							if (t.getXiangid().equals("68")){
								map.put("ee30","*");
							}
							if (t.getXiangid().equals("69")){
								map.put("ee31","*");
							}
							if (t.getXiangid().equals("70")){
								map.put("ee32","*");
							}
							if (t.getXiangid().equals("71")){
								map.put("ee33","*");
							}
							map.put("ee34",t.getJiashiyuanxingming());
							map.put("ee35", image);

						}
					}
					map.put("Cheliangpaizhao",t.getCheliangpaizhao());

				}
				List<AnbiaoAnquanhuiyiDetail> ListData = new ArrayList<>();

				// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
				// {} 代表普通变量 {.} 代表是list的变量
				// 这里模板 删除了list以后的数据，也就是统计的这一行
				String templateFileName = templatePath;
				//alarmServer.getTemplateUrl()+
				String fileName = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "/" + nyr[1] + "/" + nyr[2] + "/" + uuid + "安全检查明细";
				File newFile = new File(fileName);
				//判断目标文件所在目录是否存在
				if (!newFile.exists()) {
					//如果目标文件所在的目录不存在，则创建父目录
					newFile.mkdirs();
				}
				fileName = fileName + "/" + map.get("Cheliangpaizhao") + "-安全检查明细.xlsx";
				ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
				WriteSheet writeSheet = EasyExcel.writerSheet().build();
				// 写入list之前的数据
				excelWriter.fill(map, writeSheet);
				// 直接写入数据
				excelWriter.fill(ListData, writeSheet);
				excelWriter.finish();
				urlList.add(fileName);
			}
		}
		String fileName = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "\\" + nyr[1] + "\\" + "安全检查明细.zip";
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


}
