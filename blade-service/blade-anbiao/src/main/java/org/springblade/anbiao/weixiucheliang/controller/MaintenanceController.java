package org.springblade.anbiao.weixiucheliang.controller;

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
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import org.springblade.anbiao.weixiu.VO.MaintenanceEntityV;
import org.springblade.anbiao.weixiu.VO.MaintenanceTZVO;
import org.springblade.anbiao.weixiu.entity.FittingEntity;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;
import org.springblade.anbiao.weixiu.page.MaintenanceTZPage;
import org.springblade.anbiao.weixiucheliang.mapper.MaintenanceMapper;
import org.springblade.anbiao.weixiucheliang.service.FittingService;
import org.springblade.anbiao.weixiucheliang.service.MaintenanceService;
import org.springblade.anbiao.yinhuanpaicha.page.AnbiaoHiddenDangerPage;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoHiddenDangerVO;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.ApacheZipUtils;
import org.springblade.common.tool.ExcelUtils;
import org.springblade.common.tool.StringUtils;
import org.springblade.common.tool.WordUtil2;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import static cn.hutool.core.date.DateUtil.now;

/**
 * @Description :维修车
 * @Author : long
 * @Date :2022/11/4 14:34
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/Repair/")
@Api(value = "维修登记", tags = "维修登记")
public class MaintenanceController {

	private MaintenanceService service;

	private MaintenanceMapper mapper;

	private FittingService fittingService;

	private FileServer fileServer;

	private IFileUploadClient fileUploadClient;

	private IVehicleService vehicleService;

	private IAnbiaoRiskDetailService riskDetailService;

	@PostMapping("list")
	@ApiLog("查询-维修列表")
	@ApiOperation(value = "查询维修列表", notes = "MaintenancePage ", position = 1)
	public R saList(@RequestBody MaintenancePage maintenancePage){
		MaintenancePage maintenancePage1 = service.selectList(maintenancePage);
		return R.data(maintenancePage1);
	}


	@PostMapping("all")
	@ApiLog("查询-配件")
	@ApiOperation(value = "查询维修", notes = "MaintenancePage ", position = 2)
	public R selectALL(@RequestBody MaintenancePage maintenancePage){
		MaintenanceEntity maintenanceEntities = service.selectAll(maintenancePage);
		List<FittingsEntity> fittingsEntity = service.selectC(maintenancePage);
		maintenanceEntities.setFittingDTOS(fittingsEntity);
		return R.data(maintenanceEntities);
	}

	@PostMapping("insert")
	@ApiLog("添加-配件")
	@ApiOperation(value = "添加维修", notes = "MaintenanceEntity ", position = 3)
	public R insert(@RequestBody MaintenanceEntity maintenanceDTO){
		R rs = new R();
		Boolean aBoolean1 = service.insertOne(maintenanceDTO);
		if(maintenanceDTO.getMaintainDictId() ==3){
			List<FittingsEntity> fittingDTOS = maintenanceDTO.getFittingDTOS();
			Boolean aBoolean = false;
			for (FittingsEntity list : fittingDTOS) {
				String replace = UUID.randomUUID().toString().replace("-", "");
				FittingEntity fittingDTO = new FittingEntity();
				Long id = maintenanceDTO.getId();
				fittingDTO.setId(replace);
				fittingDTO.setWeihuid(id);
				fittingDTO.setCaozuorenid(maintenanceDTO.getCaozuorenid());
				fittingDTO.setCailiaomingcheng(list.getAwpName());
				fittingDTO.setXinghao(list.getAwpModel());
				fittingDTO.setCaozuoshijian(now());
				fittingDTO.setBeizhu(list.getAwpRemarks());
				fittingDTO.setPeijianid(list.getAwpIds());
				aBoolean = mapper.insertB(fittingDTO);
			}

			if (aBoolean && aBoolean1 ) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("新增成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("新增失败");
			}
		}else{
			if ( aBoolean1 ) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("新增成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("新增失败");
			}
		}
		return rs;
	}

	@PostMapping("delete")
	@ApiLog("删除-配件")
	@ApiOperation(value = "删除维修", notes = "MaintenancePage ", position = 4)
	public R delete(@RequestBody MaintenancePage maintenancePage){
		return R.status( service.deleteAccident(maintenancePage));
	}

	@PostMapping("update")
	@ApiLog("修改-配件")
	@ApiOperation(value = "修改维修", notes = "MaintenanceEntity ", position = 5)
	public R update(@RequestBody MaintenanceEntity maintenanceEntity){
		if(maintenanceEntity.getMaintainDictId() ==3){
			R r = new R();
			QueryWrapper<MaintenanceEntity> maintenanceEntityQueryWrapper = new QueryWrapper<>();
			maintenanceEntityQueryWrapper.lambda().eq(MaintenanceEntity::getId, maintenanceEntity.getId());
			MaintenanceEntity maintenanceEntity1 = service.getBaseMapper().selectOne(maintenanceEntityQueryWrapper);
			if (maintenanceEntity1 != null) {
				maintenanceEntity1.setId(maintenanceEntity.getId());
				maintenanceEntity1.setDeptId(maintenanceEntity.getDepID());
				maintenanceEntity1.setDriverId(maintenanceEntity.getDriverId());
				maintenanceEntity1.setVehicleId(maintenanceEntity.getVehicleId());
				maintenanceEntity1.setMaintainDictId(maintenanceEntity.getMaintainDictId());
				maintenanceEntity1.setExpectedCompletion(maintenanceEntity.getExpectedCompletion());
				maintenanceEntity1.setSendDate(maintenanceEntity.getSendDate());
				maintenanceEntity1.setAcbCenterMaintenance(maintenanceEntity.getAcbCenterMaintenance());
				maintenanceEntity1.setAcbAfterMaintenance(maintenanceEntity.getAcbAfterMaintenance());
				maintenanceEntity1.setActualCompletionDate(maintenanceEntity.getActualCompletionDate());
				maintenanceEntity1.setInRangeMileage(maintenanceEntity.getInRangeMileage());
				maintenanceEntity1.setInTheOil(maintenanceEntity1.getInTheOil());
				maintenanceEntity1.setNextMaintenanceDate(maintenanceEntity.getNextMaintenanceDate());
				maintenanceEntity1.setNextMaintenanceMileage(maintenanceEntity.getNextMaintenanceMileage());
				maintenanceEntity1.setMaintenanceDeptName(maintenanceEntity.getMaintenanceDeptName());
				maintenanceEntity1.setFujian(maintenanceEntity.getFujian());
				maintenanceEntity1.setSendRepairPersonId(maintenanceEntity.getSendRepairPersonId());
				maintenanceEntity1.setPickUpVehicleDate(maintenanceEntity.getPickUpVehicleDate());
				maintenanceEntity1.setPickUpVehicleDriverId(maintenanceEntity.getPickUpVehicleDriverId());
				maintenanceEntity1.setCaozuoren(maintenanceEntity.getCaozuoren());
				maintenanceEntity1.setCaozuorenid(maintenanceEntity.getCaozuorenid());
				maintenanceEntity1.setCaozuoshijian(now());
				maintenanceEntity1.setRemark(maintenanceEntity.getRemark());
				maintenanceEntity1.setSubtotalOfMaterialQuantity(maintenanceEntity.getSubtotalOfMaterialQuantity());
				maintenanceEntity1.setSubtotalOfWarrantyItems(maintenanceEntity.getSubtotalOfWarrantyItems());
				maintenanceEntity1.setMaterialSubtotal(maintenanceEntity.getMaterialSubtotal());
				maintenanceEntity1.setCreatetime(now());
				maintenanceEntity1.setCreateperson(maintenanceEntity.getCreateperson());
				maintenanceEntity1.setCreateid(maintenanceEntity.getCreateid());
				maintenanceEntity1.setAcbMaintenanceContent(maintenanceEntity.getAcbMaintenanceContent());
				maintenanceEntity1.setAcbRepairReason(maintenanceEntity.getAcbRepairReason());

				if (org.apache.commons.lang.StringUtils.isNotBlank(maintenanceEntity1.getAcbAfterMaintenance()) && !maintenanceEntity1.getAcbAfterMaintenance().equals("null")){
					QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>();
					vehicleQueryWrapper.lambda().eq(Vehicle::getId,maintenanceEntity1.getVehicleId());
					Vehicle vehicle = vehicleService.getBaseMapper().selectOne(vehicleQueryWrapper);

					if (org.apache.commons.lang.StringUtils.isBlank(maintenanceEntity1.getAcbRepairReason()) || maintenanceEntity1.getAcbRepairReason().equals("null")){
						maintenanceEntity1.setAcbRepairReason("");
					}
					String s = vehicle.getCheliangpaizhao() + maintenanceEntity1.getAcbRepairReason();
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"维修登记");
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,maintenanceEntity1.getDriverId());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,s);
					AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail!=null){
						riskDetail.setArdIsRectification("1");
						riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
						riskDetailService.getBaseMapper().updateById(riskDetail);
					}}

				int i = service.getBaseMapper().updateById(maintenanceEntity1);
				if (i > 0) {
					QueryWrapper<FittingEntity> fittingEntityQueryWrapper = new QueryWrapper<>();
					fittingEntityQueryWrapper.lambda().eq(FittingEntity::getWeihuid, maintenanceEntity.getId());
					fittingService.getBaseMapper().delete(fittingEntityQueryWrapper);
					List<FittingsEntity> fittingEntities = maintenanceEntity.getFittingDTOS();
					for (FittingsEntity list : fittingEntities) {
						FittingEntity fitting = new FittingEntity();
						fitting.setWeihuid(maintenanceEntity.getId());
						fitting.setCaozuorenid(maintenanceEntity.getCaozuorenid());
						fitting.setCaozuoren(maintenanceEntity.getCaozuoren());
						fitting.setCailiaomingcheng(list.getAwpName());
						fitting.setPeijianid(list.getPeijianId());
						fitting.setXiaoji(list.getAwpModel());
						fitting.setShuliang(String.valueOf(fittingEntities.size()));
						fitting.setBeizhu(list.getAwpRemarks());
						fitting.setIsdelete(maintenanceEntity.getIsDeleted().toString());
						boolean b = fittingService.save(fitting);
						if (b) {
							r.setMsg("更新成功");
							r.setCode(200);
							r.setSuccess(false);
						} else {
							r.setMsg("更新失败");
							r.setCode(500);
							r.setSuccess(false);
						}

					}
				}
			} else {
				r.setMsg("该数据不存在");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
			return r;
		}
		return R.status(service.updateAccident(maintenanceEntity));
	}

	@GetMapping("getByDateList")
	@ApiLog("维修折线图")
	@ApiOperation(value = "维修折线图", notes = "deptId, date, type ", position = 12)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "企业ID", required = true),
		@ApiImplicitParam(name = "date", value = "日期（传入年度）", required = true),
		@ApiImplicitParam(name = "type", value = "维修类别（0=一级维护,1=二级维护,3=总成维修）", required = true)
	})
	public R getByDateList(String deptId, String date, String type){
		R rs = new R();
		List<MaintenanceEntityV> maintenanceEntities = service.selectByDateList(deptId, date, type);
		if(maintenanceEntities.size() < 1){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(null);
			rs.setMsg("获取成功，暂无数据");
		}else{
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(maintenanceEntities);
			rs.setMsg("获取成功");
		}
		return rs;
	}

	@PostMapping(value = "/getTZTJPage")
	@ApiLog("维修登记-维修隐患整改台账")
	@ApiOperation(value = "维修登记-维修隐患整改台账", notes = "传入MaintenanceTZPage", position = 13)
	public R<MaintenanceTZPage<MaintenanceTZVO>> getTZTJPage(@RequestBody MaintenanceTZPage maintenanceTZPage) {
		R r = new R();
		MaintenanceTZPage<MaintenanceTZVO> pages = service.selectTZTJList(maintenanceTZPage);
		if(pages != null){
			r.setMsg("获取成功");
			r.setData(pages);
			r.setCode(200);
		}else{
			r.setMsg("获取成功,暂无数据");
			r.setCode(200);
		}
		return r;
	}

	@GetMapping("/goExport_HiddenDanger_Excel")
	@ApiLog("维修登记-维修隐患整改台账-导出")
	@ApiOperation(value = "维修登记-维修隐患整改台账-导出", notes = "传入deptId、date", position = 22)
	public R goExport_HiddenDanger_Excel(HttpServletRequest request, HttpServletResponse response, String deptId , String date, BladeUser user) throws IOException {
		R rs = new R();
		List<String> urlList = new ArrayList<>();
		MaintenanceTZPage maintenanceTZPage = new MaintenanceTZPage();
		maintenanceTZPage.setDeptId(deptId);
		maintenanceTZPage.setDate(date);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath =fileServer.getPathPrefix()+"muban\\"+"HiddenDangerRepair.xlsx";
		String [] nyr= DateUtil.today().split("-");
		String[] idsss = maintenanceTZPage.getDeptId().split(",");
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
			maintenanceTZPage.setSize(0);
			maintenanceTZPage.setCurrent(0);
			maintenanceTZPage.setDeptId(idss[j]);
			service.selectTZTJList(maintenanceTZPage);
			List<MaintenanceTZVO> hiddenDangerVOList = maintenanceTZPage.getRecords();
			//Excel中的结果集ListData
			List<AnbiaoHiddenDangerVO> ListData = new ArrayList<>();
			if(hiddenDangerVOList.size()==0){

			}else if(hiddenDangerVOList.size()>3000){
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			}else{
				for( int i = 0 ; i < hiddenDangerVOList.size() ; i++) {
					Map<String, Object> map = new HashMap<>();
					String url = "";
					String templateFile = templatePath;
					// 渲染文本
					MaintenanceTZVO t = hiddenDangerVOList.get(i);
					MaintenanceTZVO hiddenDangerVO = new MaintenanceTZVO();
					map.put("deptName", t.getDeptName());
					hiddenDangerVO.setCheliangpaizhao(t.getCheliangpaizhao());
					map.put("cheliangpaizhao", t.getCheliangpaizhao());
					map.put("driverName", t.getDriverName());
					map.put("sendDate", t.getSendDate());
					if(StringUtils.isEmpty(t.getAfterMaintenance()) && t.getAfterMaintenance() != "null"){
						map.put("zgdriverName", t.getDriverName());
						map.put("zgsendDate", t.getCaozuoshijian());
					}
					if(t.getMaintainDictId().equals("0")){
						hiddenDangerVO.setMaintainDictId("一级维护");
					}
					if(t.getMaintainDictId().equals("1")){
						hiddenDangerVO.setMaintainDictId("二级维护");
					}
					if(t.getMaintainDictId().equals("2")){
						hiddenDangerVO.setMaintainDictId("总成维修");
					}
					map.put("maintainDictId", hiddenDangerVO.getMaintainDictId());
					map.put("repairReason", t.getRepairReason());
					map.put("maintenanceDeptName", t.getMaintenanceDeptName());
					map.put("acbCost", t.getAcbCost());
					//附件
					// 渲染图片
					if (StrUtil.isNotEmpty(t.getBillAttachment()) && t.getBillAttachment().contains("http") == false) {
						t.setBillAttachment(fileUploadClient.getUrl(t.getBillAttachment()));
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getBillAttachment()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("billAttachment", t.getImgUrl());
					}else if(StrUtil.isNotEmpty(t.getBillAttachment())){
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getBillAttachment()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("billAttachment", t.getImgUrl());
					}else{
						map.put("billAttachment", "无");
					}
					//维修前照片
					if (StrUtil.isNotEmpty(t.getBeforeMaintenance()) && t.getBeforeMaintenance().contains("http") == false) {
						t.setBillAttachment(fileUploadClient.getUrl(t.getBeforeMaintenance()));
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getBeforeMaintenance()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("beforeMaintenance", t.getImgUrl());
					}else if(StrUtil.isNotEmpty(t.getBeforeMaintenance())){
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getBeforeMaintenance()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("beforeMaintenance", t.getImgUrl());
					}else{
						map.put("beforeMaintenance", "无");
					}
					//维修中照片
					if (StrUtil.isNotEmpty(t.getCenterMaintenance()) && t.getCenterMaintenance().contains("http") == false) {
						t.setBillAttachment(fileUploadClient.getUrl(t.getCenterMaintenance()));
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getCenterMaintenance()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("centerMaintenance", t.getImgUrl());
					}else if(StrUtil.isNotEmpty(t.getCenterMaintenance())){
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getCenterMaintenance()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("centerMaintenance", t.getImgUrl());
					}else{
						map.put("centerMaintenance", "无");
					}
					//维修前照片
					if (StrUtil.isNotEmpty(t.getAfterMaintenance()) && t.getAfterMaintenance().contains("http") == false) {
						t.setBillAttachment(fileUploadClient.getUrl(t.getAfterMaintenance()));
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getAfterMaintenance()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("afterMaintenance", t.getImgUrl());
					}else if(StrUtil.isNotEmpty(t.getAfterMaintenance())){
						//添加图片到工作表的指定位置
						try {
							t.setImgUrl(new URL(t.getAfterMaintenance()));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						map.put("afterMaintenance", t.getImgUrl());
					}else{
						map.put("afterMaintenance", "无");
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
					fileName = fileName+"/"+t.getDeptName()+"-维修隐患整改台账.xlsx";
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
		}
		String fileName = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+"维修隐患整改台账.zip";
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
