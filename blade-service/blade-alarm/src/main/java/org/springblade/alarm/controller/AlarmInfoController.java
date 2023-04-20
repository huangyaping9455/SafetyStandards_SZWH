/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.alarm.controller;

import cn.cqmxcx.www.util.CommonTool;
import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.alarm.entity.*;
import org.springblade.alarm.page.*;
import org.springblade.alarm.service.*;
import org.springblade.alarm.vo.AlarmsummaryCutofftimeVO;
import org.springblade.alarm.vo.DriverbehaviorVO;
import org.springblade.alarm.vo.NotlocatedetailVO;
import org.springblade.alarm.vo.OfflinedetailVO;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.tool.DateUtils;
import org.springblade.common.tool.GpsToBaiduUtil;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springblade.train.entity.*;
import org.springblade.train.feign.ITrainClient;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * 报警信息 控制器
 * @author 呵呵哒
 */
@RestController
@AllArgsConstructor
@RequestMapping("/alarm/alarminfo")
@Api(value = "报警信息接口", tags = "报警信息接口")
public class AlarmInfoController {

    private IAlarmsummaryCutofftimeService cutofftimeService;
    private IOfflinedetailService offlinedetailService;
    private INotlocatedetailService notlocatedetailService;
    private IAlarmhandleresultService alarmhandleresultService;
    private ISysClient sysClient;
    private IDriverbehaviorService driverbehaviorService;
    private IAlarmvehdailyreportService iAlarmvehdailyreportService;
    private IVehdailyreportService iVehdailyreportService;
    private IFileUploadClient fileUploadClient;
    private AlarmServer alarmServer;
	private ITrainClient trainClient;
	private IBaoBiaoAlarmhandleresultDriverService iBaoBiaoAlarmhandleresultDriverService;

	@PostMapping("/OperatType")
	@ApiLog("类型-获取企业车辆类型")
	@ApiOperation(value = "类型-获取企业车辆类型", notes = "deptId", position = 1)
	public R OperatType(String deptId){
			return  R.data(cutofftimeService.findoperattype(deptId));
	}

    @PostMapping("/list")
	@ApiLog("分页查询-北斗报警")
    @ApiOperation(value = "分页查询-北斗报警", notes = "传入AlarmPage", position = 2)
    public R<AlarmPage<AlarmsummaryCutofftimeVO>> list(@RequestBody AlarmPage alarmPage) {
		if("chaoSuBiShow".equals(alarmPage.getOrderColumn())){
			alarmPage.setOrderColumn("chaoSuBi");
		}
		if("keeptimeShow".equals(alarmPage.getOrderColumn())){
			alarmPage.setOrderColumn("keepTime");
		}
        AlarmPage pages = cutofftimeService.selectAlarmPage(alarmPage);
        return R.data(pages);
    }

    @PostMapping("/detail")
	@ApiLog("详情-北斗报警")
    @ApiOperation(value = "详情-北斗报警", notes = "传入报警id", position = 3)
    public R<AlarmsummaryCutofftimeVO> detail(@RequestParam String id, @RequestParam String AlarmType) {
        AlarmsummaryCutofftimeVO vo = cutofftimeService.selectAlarmDetail(id,AlarmType);
		if(vo != null) {
			if ("".equals(vo.getFujian()) && vo.getFujian() != null) {
				vo.setFujian(fileUploadClient.getUrl(vo.getFujian()));
			}
		}
        return R.data(vo);
    }

    @PostMapping("/driverAlarmList")
	@ApiLog("分页查询-驾驶员行为报警")
    @ApiOperation(value = "分页查询-驾驶员行为报警", notes = "传入DriverAlarmPage", position = 4)
    public R<DriverAlarmPage<DriverbehaviorVO>> driverAlarmList(@RequestBody DriverAlarmPage driverAlarmPage) {
		if("chaoSuBiShow".equals(driverAlarmPage.getOrderColumn())){

			driverAlarmPage.setOrderColumn("");
		}
        if (driverAlarmPage.getHedingzhuangtai() == null) {
            driverAlarmPage.setHedingzhuangtai("核定报警");
        }
        DriverAlarmPage pages = driverbehaviorService.selectAlarmPage(driverAlarmPage);
        return R.data(pages);
    }

    @PostMapping("/driverAlarmDetail")
	@ApiLog("详情-驾驶员行为报警")
    @ApiOperation(value = "详情-驾驶员行为报警", notes = "传入报警id", position = 5)
    public R<DriverbehaviorVO> driverAlarmDetail(@RequestParam String id,@RequestParam String AlarmType) {
        DriverbehaviorVO vo = driverbehaviorService.selectAlarmDetail(id,AlarmType);
        if(vo != null) {
			if ("".equals(vo.getFujian()) && vo.getFujian() != null) {
				vo.setFujian(fileUploadClient.getUrl(vo.getFujian()));
			}
		}
        return R.data(vo);
    }

    @PostMapping("/buzaixianlist")
	@ApiLog("分页查询-24小时不在线")
    @ApiOperation(value = "分页查询-24小时不在线", notes = "传入offlinePage", position = 6)
    public R<OfflinePage<OfflinedetailVO>> buzaixianlist(@RequestBody OfflinePage offlinePage) {
        OfflinePage pages = offlinedetailService.selectAlarmPage(offlinePage);
        return R.data(pages);
    }

    @PostMapping("/budingweilist")
	@ApiLog("分页查询-24小时不定位")
    @ApiOperation(value = "分页查询-24小时不定位", notes = "传入notlocatePage", position = 7)
    public R<NotlocatePage<NotlocatedetailVO>> budingweilist(@RequestBody NotlocatePage notlocatePage) {
        NotlocatePage pages = notlocatedetailService.selectAlarmPage(notlocatePage);
        return R.data(pages);
    }

    /**
     * 添加处理记录
     *
     * @param ids
     * @param alarmType
     * @param chulixingshi
     * @param chulimiaoshu
     * @param fujian
     */
//    @PostMapping("/insertChuli")
//	@ApiLog("新增-处理记录")
//    @ApiOperation(value = "Add-新增处理记录", notes = "传入alarmResult", position = 7)
//    public R insertChuli(@ApiParam(value = "ids串", required = true) String ids, @ApiParam(value = "报警类型", required = true) String alarmType, @ApiParam(value = "处理形式", required = true) String chulixingshi, @ApiParam(value = "处理描述") String chulimiaoshu, @ApiParam(value = "附件") String fujian,
//                         BladeUser bladeUser,String deptId) {
////        Dept dept = sysClient.selectByJGBM("机构", bladeUser.getRoleId());
//        Dept dept = sysClient.selectById(deptId);
////        String deptId = dept.getId().toString();
//        String[] idsss = ids.split(",");
//        //去除素组中重复的数组
//		List<String> listid = new ArrayList<String>();
//		for (int i=0; i<idsss.length; i++) {
//			if(!listid.contains(idsss[i])) {
//				listid.add(idsss[i]);
//			}
//		}
//		//返回一个包含所有对象的指定类型的数组
//		String[]  idss= listid.toArray(new String[1]);
//
//        int count = alarmhandleresultService.selectAlarmCountByIdsAndDetpId(idss,dept.getDeptName());
//        if(count<idss.length){
//            return R.fail("当前单位与报警所属单位不符，不能进行处理");
//        }
//        Alarmhandleresult result = new Alarmhandleresult();
//        result.setIdss(idss);
//        result.setBaojingleixing(alarmType);
//		result.setRemark(true);
//        //根据报警id 报警类型
//        List<String> resultids = alarmhandleresultService.selectIdList(result);
//        List<Alarmhandleresult> list = new ArrayList<>();
//        int code = 200;
//        String msg = "";
//        boolean delnum = false;
//        boolean addnum = false;
//
//        for (int i = 0; i < idss.length; i++) {
//            Alarmhandleresult alarmResult = new Alarmhandleresult();
//            alarmResult.setBaojingid(idss[i]);
//            alarmResult.setChulizhuangtai(1);
//            alarmResult.setChuliren(bladeUser.getUserName());
//            alarmResult.setChulishijian(DateUtil.now());
//            alarmResult.setQiyemingcheng(dept.getDeptName());
//            alarmResult.setQiyeid(Integer.parseInt(deptId));
//            alarmResult.setChulixingshi(chulixingshi);
//            alarmResult.setBaojingleixing(alarmType);
//            //登录页
//            if (StringUtil.isNotBlank(fujian)) {
//                fileUploadClient.updateCorrelation(fujian, "1");
//            }
//            alarmResult.setFujian(fujian);
//            String str = CommonTool.HtmlToText(chulimiaoshu);
//            alarmResult.setChulimiaoshu(str);
//			alarmResult.setRemark(true);
//			//根据报警id查询是否出处理过 处理过就不添加
//			List<Alarmhandleresult> alarmhandleresult = alarmhandleresultService.selectBybaojin(idss[i]);
//			if(alarmhandleresult.size()==0){
//				list.add(alarmResult);
//			}
//        }
//        //保存之前，先删除要保存的报警已处理记录
//        if (resultids.size() > 0) {
//            System.out.println(" resultids: " + resultids.size());
//            delnum = alarmhandleresultService.removeByIds(resultids);
//        }
//        //添加记录
//        addnum = alarmhandleresultService.saveBatch(list);
//        //更新日报百分比
////		String company=dept.getDeptName();
////		DateTime yesterday = DateUtil.yesterday();
////		String cutoffTime=yesterday.toString();
//		Integer updateribao = alarmhandleresultService.updateribao(cutoffTime, company, deptId);
//
//		if (delnum && addnum) {
//            msg = "更新成功";
//        } else if (addnum) {
//            msg = "保存成功";
//        } else {
//            code = 500;
//            msg = "保存失败";
//        }
//        return R.fail(code, msg);
//    }

	/**
	 * 添加处理记录
	 */
	@PostMapping("/insertChuli")
	@ApiLog("新增-报警处理记录")
	@ApiOperation(value = "新增-报警处理记录", notes = "传入alarmResult", position = 8)
	public R insertChuli(
		@ApiParam(value = "报警id（传入多个报警ID需以英文逗号隔开）", required = true) String ids,
		@ApiParam(value = "报警类型", required = true) String alarmType,
		@ApiParam(value = "处理形式", required = true) String chulixingshi,
		@ApiParam(value = "处理描述") String chulimiaoshu,
		@ApiParam(value = "附件") String fujian,
		@ApiParam(value = "用户对象", required = true) BladeUser bladeUser,
		@ApiParam(value = "企业ID", required = true) String deptId,
		@ApiParam(value = "处理类型,如超期处理、正常处理") String type) {

		Dept dept = sysClient.selectById(deptId);
		String[] idsss = ids.split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[]  idss= listid.toArray(new String[1]);

		int count = alarmhandleresultService.selectAlarmCountByIdsAndDetpId(idss,dept.getDeptName());
		System.out.println(count);
		if(count<idss.length){
			return R.fail("当前所属单位与报警单位不符，不能进行报警处理");
		}
		Alarmhandleresult result = new Alarmhandleresult();
		result.setIdss(idss);
		result.setBaojingleixing(alarmType);
		result.setRemark(null);
		//根据报警id 报警类型

		List<Alarmhandleresult> resultids = alarmhandleresultService.selectIdList(result);
		System.out.println(resultids);
		int code = 200;
		String msg = "";
		Integer delnum = 0;
		boolean addnum = false;
		Integer countnum = 0;
		for (int i = 0; i < idss.length; i++) {
			List<Alarmhandleresult> list = new ArrayList<>();
			if (resultids.size() > 0 && resultids.size() > countnum) {
				for(int j=0;j<= i;j++){
					// || resultids.get(j).getShensushenhebiaoshi()  == null
					if(countnum >= resultids.size()){
						return R.fail(code, msg);
					}
					if(resultids.get(j).getShensushenhebiaoshi() == null){
						Alarmhandleresult alarmResult = new Alarmhandleresult();
						alarmResult.setBaojingid(idss[i]);
						//根据 type 区分是否是正常处警
						//若 type 为 超期处理，则赋值为2
						if(StringUtils.isNotBlank(type) && "超期处理".equals(type)){
							alarmResult.setChulizhuangtai(2);
						}else{
							alarmResult.setChulizhuangtai(1);
						}
						if (bladeUser == null){
							alarmResult.setChuliren("admin");
							alarmResult.setChulirenid(1);
						}else{
							alarmResult.setChuliren(bladeUser.getUserName());
							alarmResult.setChulirenid(bladeUser.getUserId());
						}
						alarmResult.setChulishijian(DateUtil.now());
						alarmResult.setQiyemingcheng(dept.getDeptName());
						alarmResult.setQiyeid(Integer.parseInt(deptId));
						alarmResult.setChulixingshi(chulixingshi);
						alarmResult.setBaojingleixing(alarmType);
						//登录页
						if (StringUtil.isNotBlank(fujian)) {
							fileUploadClient.updateCorrelation(fujian, "1");
						}
						alarmResult.setFujian(fujian);
						String str = CommonTool.HtmlToText(chulimiaoshu);
						alarmResult.setChulimiaoshu(str);
						alarmResult.setRemark(true);
						alarmResult.setEndresult(1);
						//根据报警id查询是否出处理过 处理过就不添加
						List<Alarmhandleresult> alarmhandleresult = alarmhandleresultService.selectBybaojin(idss[i],alarmType);
						if(alarmhandleresult.size()==0){
							list.add(alarmResult);
						}
						//添加记录
						addnum = alarmhandleresultService.saveBatch(list);

						if (delnum>0 && addnum) {
							msg = "更新成功";
						} else if (addnum) {
							msg = "保存成功";
						} else {
							code = 500;
							msg = "保存失败";
						}
					}else{
						System.out.println(resultids.get(j).getShensushenhebiaoshi()+"++++++++++++++"+resultids.get(j).getBaojingid()+"++++++++++"+idss[i]);
						if(resultids.get(j).getShensushenhebiaoshi() == 2 && resultids.get(j).getBaojingid().equals(idss[i])){
							if (StringUtil.isNotBlank(fujian)) {
								fileUploadClient.updateCorrelation(fujian, "1");
							}
							String str = CommonTool.HtmlToText(chulimiaoshu);
							Integer userId ;
							String userName;
							if (bladeUser == null){
								userId = 1;
								userName = "admin";
							}else{
								userId = bladeUser.getUserId();
								userName = bladeUser.getUserName();
							}
							String baojingid = resultids.get(j).getBaojingid();
							addnum = alarmhandleresultService.updateAftertreatment(chulixingshi,str,userName,DateUtil.now(),userId,fujian,baojingid);
							if (delnum>0 && addnum) {
								msg = "更新成功";
							} else if (addnum) {
								msg = "保存成功";
							} else {
								code = 500;
								msg = "保存失败";
							}
							countnum +=1;
						}else{
							Alarmhandleresult alarmResult = new Alarmhandleresult();
							alarmResult.setBaojingid(idss[i]);
							//根据 type 区分是否是正常处警
							//若 type 为 超期处理，则赋值为2
							if(StringUtils.isNotBlank(type) && "超期处理".equals(type)){
								alarmResult.setChulizhuangtai(2);
							}else{
								alarmResult.setChulizhuangtai(1);
							}
							if (bladeUser == null){
								alarmResult.setChuliren("admin");
								alarmResult.setChulirenid(1);
							}else{
								alarmResult.setChuliren(bladeUser.getUserName());
								alarmResult.setChulirenid(bladeUser.getUserId());
							}
							alarmResult.setChulishijian(DateUtil.now());
							alarmResult.setQiyemingcheng(dept.getDeptName());
							alarmResult.setQiyeid(Integer.parseInt(deptId));
							alarmResult.setChulixingshi(chulixingshi);
							alarmResult.setBaojingleixing(alarmType);
							//登录页
							if (StringUtil.isNotBlank(fujian)) {
								fileUploadClient.updateCorrelation(fujian, "1");
							}
							alarmResult.setFujian(fujian);
							String str = CommonTool.HtmlToText(chulimiaoshu);
							alarmResult.setChulimiaoshu(str);
							alarmResult.setRemark(true);
							alarmResult.setEndresult(1);
							//根据报警id查询是否出处理过 处理过就不添加
							List<Alarmhandleresult> alarmhandleresult = alarmhandleresultService.selectBybaojin(idss[i],alarmType);
							if(alarmhandleresult.size()==0){
								list.add(alarmResult);
							}
							//添加记录
							addnum = alarmhandleresultService.saveBatch(list);

							if (delnum>0 && addnum) {
								msg = "更新成功";
							} else if (addnum) {
								msg = "保存成功";
							} else {
								code = 500;
								msg = "保存失败";
							}
							countnum = j;
						}
					}
				}
			} else{
				Alarmhandleresult alarmResult = new Alarmhandleresult();
				alarmResult.setBaojingid(idss[i]);
				//根据 type 区分是否是正常处警
				//若 type 为 超期处理，则赋值为2
				if(StringUtils.isNotBlank(type) && "超期处理".equals(type)){
					alarmResult.setChulizhuangtai(2);
				}else{
					alarmResult.setChulizhuangtai(1);
				}
				if (bladeUser == null){
					alarmResult.setChuliren("admin");
					alarmResult.setChulirenid(1);
				}else{
					alarmResult.setChuliren(bladeUser.getUserName());
					alarmResult.setChulirenid(bladeUser.getUserId());
				}
				alarmResult.setChulishijian(DateUtil.now());
				alarmResult.setQiyemingcheng(dept.getDeptName());
				alarmResult.setQiyeid(Integer.parseInt(deptId));
				alarmResult.setChulixingshi(chulixingshi);
				alarmResult.setBaojingleixing(alarmType);
				//登录页
				if (StringUtil.isNotBlank(fujian)) {
					fileUploadClient.updateCorrelation(fujian, "1");
				}
				alarmResult.setFujian(fujian);
				String str = CommonTool.HtmlToText(chulimiaoshu);
				alarmResult.setChulimiaoshu(str);
				alarmResult.setRemark(true);
				alarmResult.setEndresult(1);
				//根据报警id查询是否出处理过 处理过就不添加
				List<Alarmhandleresult> alarmhandleresult = alarmhandleresultService.selectBybaojin(idss[i],alarmType);
				if(alarmhandleresult.size()==0){
					list.add(alarmResult);
				}
				//添加记录
				addnum = alarmhandleresultService.saveBatch(list);

				if (delnum>0 && addnum) {
					msg = "更新成功";
				} else if (addnum) {
					msg = "保存成功";
				} else {
					code = 500;
					msg = "保存失败";
				}
			}
		}
		return R.fail(code, msg);
	}

	/**
	 * 添加处理记录
	 */
	@PostMapping("/insertChuli_Study")
	@ApiLog("新增-报警处理记录")
	@ApiOperation(value = "新增-报警处理记录", notes = "传入alarmResult", position = 8)
	public R insertChuli_Study(
			@ApiParam(value = "报警id（传入多个报警ID需以英文逗号隔开）", required = true) String ids,
			@ApiParam(value = "报警类型", required = true) String alarmType,
			@ApiParam(value = "处理形式", required = true) String chulixingshi,
			@ApiParam(value = "处理描述") String chulimiaoshu,
			@ApiParam(value = "附件") String fujian,
			@ApiParam(value = "用户对象", required = true) BladeUser bladeUser,
			@ApiParam(value = "企业ID", required = true) String deptId,
			@ApiParam(value = "处理类型,如超期处理、正常处理") String type,
			@ApiParam(value = "驾驶员姓名") String driverName,
			@ApiParam(value = "驾驶员ID") String driverId,
			@ApiParam(value = "课程ID") String courseId
			) {
		if(bladeUser == null){
			int code = 500;
			String msg = "请重新登录";
			return R.fail(code, msg);
		}
		Unit unit = null;
		Student student = null;
		Dept dept = sysClient.selectById(deptId);
		if(chulixingshi.contains("处罚性学习")){
			//根据企业名称查询教育平台是否包含该企业
			unit = trainClient.getUnitByName(dept.getDeptName());
			if(unit == null){
				return R.fail("当前所属单位未在教育系统中，不能进行报警处理");
			}

			//根据驾驶员姓名查询教育平台是否存在该驾驶员
			student = trainClient.getStudentByName(driverName,dept.getDeptName());
			if(student == null){
				return R.fail("当前驾驶员未在教育系统中，不能进行报警处理");
			}
		}

		String[] idsss = ids.split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[]  idss= listid.toArray(new String[1]);

//		int count = alarmhandleresultService.selectAlarmCountByIdsAndDetpId(idss,dept.getDeptName());
//		System.out.println(count);
//		if(count<idss.length){
//			return R.fail("当前所属单位与报警单位不符，不能进行报警处理");
//		}
		Alarmhandleresult result = new Alarmhandleresult();
		result.setIdss(idss);
		result.setBaojingleixing(alarmType);
		result.setRemark(null);
		//根据报警id 报警类型

		List<Alarmhandleresult> resultids = alarmhandleresultService.selectIdList(result);
		System.out.println(resultids);
		int code = 200;
		String msg = "";
		Integer delnum = 0;
		boolean addnum = false;
		Integer countnum = 0;
		for (int i = 0; i < idss.length; i++) {
			List<Alarmhandleresult> list = new ArrayList<>();
			if (resultids.size() > 0 && resultids.size() > countnum) {
				for(int j=0;j<= i;j++){
					// || resultids.get(j).getShensushenhebiaoshi()  == null
					if(countnum >= resultids.size()){
						return R.fail(code, msg);
					}
					if(resultids.get(j).getShensushenhebiaoshi() == null) {
						Alarmhandleresult alarmResult = new Alarmhandleresult();
						alarmResult.setBaojingid(idss[i]);
						//根据 type 区分是否是正常处警
						//若 type 为 超期处理，则赋值为2
						if (StringUtils.isNotBlank(type) && "超期处理".equals(type)) {
							alarmResult.setChulizhuangtai(2);
						} else {
							alarmResult.setChulizhuangtai(1);
						}
						if (bladeUser == null) {
							alarmResult.setChuliren("admin");
							alarmResult.setChulirenid(1);
						} else {
							alarmResult.setChuliren(bladeUser.getUserName());
							alarmResult.setChulirenid(bladeUser.getUserId());
						}
						alarmResult.setChulishijian(DateUtil.now());
						alarmResult.setQiyemingcheng(dept.getDeptName());
						alarmResult.setQiyeid(Integer.parseInt(deptId));
						alarmResult.setChulixingshi(chulixingshi);
						alarmResult.setBaojingleixing(alarmType);
						//登录页
						if (StringUtil.isNotBlank(fujian)) {
							fileUploadClient.updateCorrelation(fujian, "1");
						}
						alarmResult.setFujian(fujian);
						String str = CommonTool.HtmlToText(chulimiaoshu);
						alarmResult.setChulimiaoshu(str);
						alarmResult.setRemark(true);
						alarmResult.setEndresult(1);
						//根据报警id查询是否出处理过 处理过就不添加
						List<Alarmhandleresult> alarmhandleresult = alarmhandleresultService.selectBybaojin(idss[i], alarmType);
						if (alarmhandleresult.size() == 0) {
							list.add(alarmResult);
						}
						//添加记录
						addnum = alarmhandleresultService.saveBatch(list);
						if (chulixingshi.contains("处罚性学习")) {
							//向教育平台添加待学习记录
							List<Course> courseList = trainClient.getCourseALLList(Integer.parseInt(courseId));
							CourseStudent courseStudent = new CourseStudent();
							CourseExt courseExt = trainClient.getCourseExt(alarmType, unit.getId().toString());
							if (courseList.size() > 0) {
								int payFlag = 0;
								if (courseList.get(0).getCourseFee() == 2) {
									payFlag = 1;
								}
								courseStudent.setCourseId(courseExt.getRelUnitCourseId());
								courseStudent.setStudentId(student.getId());
								courseStudent.setPayFlag(payFlag);
								courseStudent.setCourseStatus(0);
								courseStudent.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
//					courseStudentList.add(courseStudent);
//					studentIds.add(courseStudentModel.getStudentId());
							}
							boolean addjy = trainClient.insertCourseStudentSelective(courseStudent);
							if (addjy == false) {
								code = 500;
								msg = "添加教育平台学习记录失败";
								return R.fail(code, msg);
							}

							//向第三方平台处罚驾驶员关联表添加记录
							BaoBiaoAlarmhandleresultDriver bb = new BaoBiaoAlarmhandleresultDriver();
							bb.setAlarm_id(idss[i]);
							bb.setAlarm_type(alarmType);
							bb.setDriver_id(driverId);
							if (bladeUser == null) {
								bb.setCaozuoren("admin");
								bb.setCaozuorenid("1");
							} else {
								bb.setCaozuoren(bladeUser.getUserName());
								bb.setCaozuorenid(bladeUser.getUserId().toString());
							}
							bb.setCaozuoshijian(DateUtil.now());
							bb.setCourse_id(courseExt.getRelUnitCourseId().toString());
							bb.setStudy_driver_id(student.getId());
							bb.setStatus(0);
							bb.setDept_id(Integer.parseInt(deptId));
							boolean addjsyalarm = iBaoBiaoAlarmhandleresultDriverService.insertSelective(bb);
							if (addjsyalarm == false) {
								code = 500;
								msg = "添加处罚教育学习记录失败";
								return R.fail(code, msg);
							}
						}else{
							if(StringUtils.isNotBlank(driverId)){
								//向第三方平台处罚驾驶员关联表添加记录
								BaoBiaoAlarmhandleresultDriver bb = new BaoBiaoAlarmhandleresultDriver();
								bb.setAlarm_id(idss[i]);
								bb.setAlarm_type(alarmType);
								bb.setDriver_id(driverId);
								bb.setCaozuoren(bladeUser.getUserName());
								bb.setCaozuorenid(bladeUser.getUserId().toString());
								bb.setCaozuoshijian(DateUtil.now());
								bb.setDept_id(Integer.parseInt(deptId));
								boolean addjsyalarm = iBaoBiaoAlarmhandleresultDriverService.insertSelective(bb);
								if (addjsyalarm == false) {
									code = 500;
									msg = "添加处罚教育学习记录失败";
									return R.fail(code, msg);
								}
							}
							if (delnum>0 && addnum) {
								msg = "更新成功";
							} else if (addnum) {
								msg = "保存成功";
							} else {
								code = 500;
								msg = "保存失败";
							}
						}
					}else{
						System.out.println(resultids.get(j).getShensushenhebiaoshi()+"++++++++++++++"+resultids.get(j).getBaojingid()+"++++++++++"+idss[i]);
						if(resultids.get(j).getShensushenhebiaoshi() == 2 && resultids.get(j).getBaojingid().equals(idss[i])){
							if (StringUtil.isNotBlank(fujian)) {
								fileUploadClient.updateCorrelation(fujian, "1");
							}
							String str = CommonTool.HtmlToText(chulimiaoshu);
							Integer userId ;
							String userName;
							if (bladeUser == null){
								userId = 1;
								userName = "admin";
							}else{
								userId = bladeUser.getUserId();
								userName = bladeUser.getUserName();
							}
							String baojingid = resultids.get(j).getBaojingid();
							addnum = alarmhandleresultService.updateAftertreatment(chulixingshi,str,userName,DateUtil.now(),userId,fujian,baojingid);
							if (delnum>0 && addnum) {
								msg = "更新成功";
							} else if (addnum) {
								msg = "保存成功";
							} else {
								code = 500;
								msg = "保存失败";
							}
							countnum +=1;
						}else{
							Alarmhandleresult alarmResult = new Alarmhandleresult();
							alarmResult.setBaojingid(idss[i]);
							//根据 type 区分是否是正常处警
							//若 type 为 超期处理，则赋值为2
							if(StringUtils.isNotBlank(type) && "超期处理".equals(type)){
								alarmResult.setChulizhuangtai(2);
							}else{
								alarmResult.setChulizhuangtai(1);
							}
							if (bladeUser == null){
								alarmResult.setChuliren("admin");
								alarmResult.setChulirenid(1);
							}else{
								alarmResult.setChuliren(bladeUser.getUserName());
								alarmResult.setChulirenid(bladeUser.getUserId());
							}
							alarmResult.setChulishijian(DateUtil.now());
							alarmResult.setQiyemingcheng(dept.getDeptName());
							alarmResult.setQiyeid(Integer.parseInt(deptId));
							alarmResult.setChulixingshi(chulixingshi);
							alarmResult.setBaojingleixing(alarmType);
							//登录页
							if (StringUtil.isNotBlank(fujian)) {
								fileUploadClient.updateCorrelation(fujian, "1");
							}
							alarmResult.setFujian(fujian);
							String str = CommonTool.HtmlToText(chulimiaoshu);
							alarmResult.setChulimiaoshu(str);
							alarmResult.setRemark(true);
							alarmResult.setEndresult(1);
							//根据报警id查询是否出处理过 处理过就不添加
							List<Alarmhandleresult> alarmhandleresult = alarmhandleresultService.selectBybaojin(idss[i],alarmType);
							if(alarmhandleresult.size()==0){
								list.add(alarmResult);
							}
							//添加记录
//							addnum = alarmhandleresultService.saveBatch(list);

							//添加记录
							addnum = alarmhandleresultService.saveBatch(list);

							if(chulixingshi.contains("处罚性学习")) {
								//向教育平台添加待学习记录
								List<Course> courseList = trainClient.getCourseALLList(Integer.parseInt(courseId));
								CourseStudent courseStudent = new CourseStudent();
								CourseExt courseExt = trainClient.getCourseExt(alarmType, unit.getId().toString());
								if (courseList.size() > 0) {
									int payFlag = 0;
									if (courseList.get(0).getCourseFee() == 2) {
										payFlag = 1;
									}
									courseStudent.setCourseId(courseExt.getRelUnitCourseId());
									courseStudent.setStudentId(student.getId());
									courseStudent.setPayFlag(payFlag);
									courseStudent.setCourseStatus(0);
									courseStudent.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
//					courseStudentList.add(courseStudent);
//					studentIds.add(courseStudentModel.getStudentId());
								}
								boolean addjy = trainClient.insertCourseStudentSelective(courseStudent);
								if (addjy == false) {
									code = 500;
									msg = "添加教育平台学习记录失败";
									return R.fail(code, msg);
								}

								//向第三方平台处罚驾驶员关联表添加记录
								BaoBiaoAlarmhandleresultDriver bb = new BaoBiaoAlarmhandleresultDriver();
								bb.setAlarm_id(idss[i]);
								bb.setAlarm_type(alarmType);
								bb.setDriver_id(driverId);
								if (bladeUser == null) {
									bb.setCaozuoren("admin");
									bb.setCaozuorenid("1");
								} else {
									bb.setCaozuoren(bladeUser.getUserName());
									bb.setCaozuorenid(bladeUser.getUserId().toString());
								}
								bb.setCaozuoshijian(DateUtil.now());
								bb.setCourse_id(courseExt.getRelUnitCourseId().toString());
								bb.setStudy_driver_id(student.getId());
								bb.setStatus(0);
								bb.setDept_id(Integer.parseInt(deptId));
								boolean addjsyalarm = iBaoBiaoAlarmhandleresultDriverService.insertSelective(bb);
								if (addjsyalarm == false) {
									code = 500;
									msg = "添加处罚教育学习记录失败";
									return R.fail(code, msg);
								}
							}else{
								if(StringUtils.isNotBlank(driverId)){
									//向第三方平台处罚驾驶员关联表添加记录
									BaoBiaoAlarmhandleresultDriver bb = new BaoBiaoAlarmhandleresultDriver();
									bb.setAlarm_id(idss[i]);
									bb.setAlarm_type(alarmType);
									bb.setDriver_id(driverId);
									bb.setCaozuoren(bladeUser.getUserName());
									bb.setCaozuorenid(bladeUser.getUserId().toString());
									bb.setCaozuoshijian(DateUtil.now());
									bb.setDept_id(Integer.parseInt(deptId));
									boolean addjsyalarm = iBaoBiaoAlarmhandleresultDriverService.insertSelective(bb);
									if (addjsyalarm == false) {
										code = 500;
										msg = "添加处罚教育学习记录失败";
										return R.fail(code, msg);
									}
								}
							}
							if (delnum>0 && addnum) {
								msg = "更新成功";
							} else if (addnum) {
								msg = "保存成功";
							} else {
								code = 500;
								msg = "保存失败";
							}
							countnum = j;
						}
					}
				}
			} else{
				Alarmhandleresult alarmResult = new Alarmhandleresult();
				alarmResult.setBaojingid(idss[i]);
				//根据 type 区分是否是正常处警
				//若 type 为 超期处理，则赋值为2
				if(StringUtils.isNotBlank(type) && "超期处理".equals(type)){
					alarmResult.setChulizhuangtai(2);
				}else{
					alarmResult.setChulizhuangtai(1);
				}
				if (bladeUser == null){
					alarmResult.setChuliren("admin");
					alarmResult.setChulirenid(1);
				}else{
					alarmResult.setChuliren(bladeUser.getUserName());
					alarmResult.setChulirenid(bladeUser.getUserId());
				}
				alarmResult.setChulishijian(DateUtil.now());
				alarmResult.setQiyemingcheng(dept.getDeptName());
				alarmResult.setQiyeid(Integer.parseInt(deptId));
				alarmResult.setChulixingshi(chulixingshi);
				alarmResult.setBaojingleixing(alarmType);
				//登录页
				if (StringUtil.isNotBlank(fujian)) {
					fileUploadClient.updateCorrelation(fujian, "1");
				}
				alarmResult.setFujian(fujian);
				String str = CommonTool.HtmlToText(chulimiaoshu);
				alarmResult.setChulimiaoshu(str);
				alarmResult.setRemark(true);
				alarmResult.setEndresult(1);
				//根据报警id查询是否出处理过 处理过就不添加
				List<Alarmhandleresult> alarmhandleresult = alarmhandleresultService.selectBybaojin(idss[i],alarmType);
				if(alarmhandleresult.size()==0){
					list.add(alarmResult);
				}
				//添加记录
				addnum = alarmhandleresultService.saveBatch(list);

				if(chulixingshi.contains("处罚性学习")) {
					//向教育平台添加待学习记录
					List<Course> courseList = trainClient.getCourseALLList(Integer.parseInt(courseId));
					CourseStudent courseStudent = new CourseStudent();
					CourseExt courseExt = trainClient.getCourseExt(alarmType, unit.getId().toString());
					if (courseList.size() > 0) {
						int payFlag = 0;
						if (courseList.get(0).getCourseFee() == 2) {
							payFlag = 1;
						}
						courseStudent.setCourseId(courseExt.getRelUnitCourseId());
						courseStudent.setStudentId(student.getId());
						courseStudent.setPayFlag(payFlag);
						courseStudent.setCourseStatus(0);
						courseStudent.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
	//					courseStudentList.add(courseStudent);
	//					studentIds.add(courseStudentModel.getStudentId());
					}
					boolean addjy = trainClient.insertCourseStudentSelective(courseStudent);
					if (addjy == false) {
						code = 500;
						msg = "添加教育平台学习记录失败";
						return R.fail(code, msg);
					}

					//向第三方平台处罚驾驶员关联表添加记录
					BaoBiaoAlarmhandleresultDriver bb = new BaoBiaoAlarmhandleresultDriver();
					bb.setAlarm_id(idss[i]);
					bb.setAlarm_type(alarmType);
					bb.setDriver_id(driverId);
					if (bladeUser == null) {
						bb.setCaozuoren("admin");
						bb.setCaozuorenid("1");
					} else {
						bb.setCaozuoren(bladeUser.getUserName());
						bb.setCaozuorenid(bladeUser.getUserId().toString());
					}
					bb.setCaozuoshijian(DateUtil.now());
					bb.setCourse_id(courseExt.getRelUnitCourseId().toString());
					bb.setStudy_driver_id(student.getId());
					bb.setStatus(0);
					bb.setDept_id(Integer.parseInt(deptId));
					boolean addjsyalarm = iBaoBiaoAlarmhandleresultDriverService.insertSelective(bb);
					if (addjsyalarm == false) {
						code = 500;
						msg = "添加处罚教育学习记录失败";
						return R.fail(code, msg);
					}
				}else{
					if(StringUtils.isNotBlank(driverId)){
						//向第三方平台处罚驾驶员关联表添加记录
						BaoBiaoAlarmhandleresultDriver bb = new BaoBiaoAlarmhandleresultDriver();
						bb.setAlarm_id(idss[i]);
						bb.setAlarm_type(alarmType);
						bb.setDriver_id(driverId);
						bb.setCaozuoren(bladeUser.getUserName());
						bb.setCaozuorenid(bladeUser.getUserId().toString());
						bb.setCaozuoshijian(DateUtil.now());
						bb.setDept_id(Integer.parseInt(deptId));
						boolean addjsyalarm = iBaoBiaoAlarmhandleresultDriverService.insertSelective(bb);
						if (addjsyalarm == false) {
							code = 500;
							msg = "添加处罚教育学习记录失败";
							return R.fail(code, msg);
						}
					}
				}
				if (delnum>0 && addnum) {
					msg = "更新成功";
				} else if (addnum) {
					msg = "保存成功";
				} else {
					code = 500;
					msg = "保存失败";
				}
			}
		}
		return R.fail(code, msg);
	}

    @PostMapping("/selectBJDetail")
    @ApiLog("报警详情")
    @ApiOperation(value="报警详情",notes="传入报警id",position=9)
    public R selectBJDetail(String baojingid,String type,String AlarmType){
        if("1".equals(type)){
			AlarmsummaryCutofftimeVO page=cutofftimeService.selectAlarmDetail(baojingid,AlarmType);
            return R.data(page);
        }
        DriverbehaviorVO vo = driverbehaviorService.selectAlarmDetail(baojingid,AlarmType);
        return R.data(vo);
    }

	@PostMapping("/selectAlarmsummaryRecord")
	@ApiLog("报警详情-生命周期")
	@ApiOperation(value="报警详情-生命周期",notes="传入报警id",position=9)
	public R selectAlarmsummaryRecord(String baojingid,String type,String AlarmType){
		BaobiaoAlarmsummaryRecord page=cutofftimeService.selectAlarmsummaryRecord(baojingid,AlarmType,type);
		return R.data(page);
	}


    @PostMapping("/shishiBaojingTongji")
	@ApiLog("统计-实时报警")
    @ApiOperation(value = "统计-实时报警", notes = "传入shishiBaojingTongjiPage", position = 10)
    public R shishiBaojingTongji(@RequestBody ShishiBaojingTongjiPage shishiBaojingTongjiPage) {
        Map<String, Object> baojingtongji = new HashMap<>();

//        Map<String, Object> tongji = cutofftimeService.selectShishiBaojingTongji(shishiBaojingTongjiPage);
//        baojingtongji.put("gps", tongji);
//        Map<String, Object> tongjidr = driverbehaviorService.selectShishiBaojingTongji(shishiBaojingTongjiPage);
//        baojingtongji.put("driver", tongjidr);
        Map<String, Object> gps = cutofftimeService.selectShifouBaojing(shishiBaojingTongjiPage);
        baojingtongji.put("gps", gps);
        Map<String, Object> driver = driverbehaviorService.selectShifouBaojing(shishiBaojingTongjiPage);
        baojingtongji.put("driver", driver);
        return R.data(baojingtongji);
    }

    @PostMapping("/chaosu")
	@ApiLog("超速报警-处理统计")
    @ApiOperation(value = "超速报警-处理统计", notes = "传入AlarmvehPage ", position = 11)
    public R chaosu(@RequestBody AlarmvehPage alarmvehPage) {
        AlarmvehPage alarmvehPage1 = iAlarmvehdailyreportService.chaosu(alarmvehPage);
        return R.data(alarmvehPage1);
    }

    @PostMapping("/pilao")
	@ApiLog("疲劳报警-处理统计")
    @ApiOperation(value = "疲劳报警-处理统计", notes = "传入AlarmvehPage", position = 12)
    public R pilao(@RequestBody AlarmvehPage alarmvehPage) {
        return R.data(iAlarmvehdailyreportService.pilao(alarmvehPage));
    }

    @PostMapping("/zhudonganquan")
	@ApiLog("主动安全-处理统计")
    @ApiOperation(value = "主动安全-处理统计", notes = "传入AlarmvehPage ", position = 13)
    public R zhudonganquan(@RequestBody AlarmvehPage alarmvehPage) {
        return R.data(iAlarmvehdailyreportService.zhudonganquan(alarmvehPage));
    }

    @PostMapping("/licheng")
	@ApiLog("里程-日行驶")
    @ApiOperation(value = "里程-日行驶", notes = "传入AlarmvehPage ", position = 14)
    public R licheng(@RequestBody VehdailyreportPage vehdailyreportPage) {
        return R.data(iVehdailyreportService.selectall(vehdailyreportPage));
    }

	/**
	 * 添加申述记录
	 *
	 * @param ids
	 * @param alarmType
	 * @param chulixingshi
	 * @param chulimiaoshu
	 * @param fujian
	 */
	@PostMapping("/insertShenshu")
	@ApiLog("新增-申述记录")
	@ApiOperation(value = "Add-新增申述记录", notes = "传入alarmResult", position = 15)
	public R insertShenshu(@ApiParam(value = "ids串", required = true) String ids, @ApiParam(value = "报警类型", required = true) String alarmType, @ApiParam(value = "处理形式", required = true) String chulixingshi, @ApiParam(value = "处理描述") String chulimiaoshu, @ApiParam(value = "附件") String fujian,
						 BladeUser bladeUser,String deptId) {
//		Dept dept = sysClient.selectByJGBM("机构", bladeUser.getRoleId());
//		String deptId = dept.getId().toString();
        Dept dept = sysClient.selectById(deptId);
		String[] idss = ids.split(",");
//        int count = alarmhandleresultService.selectAlarmCountByIdsAndDetpId(idss,dept.getDeptName());
//        if(count<idss.length){
//            return R.fail("当前单位与报警所属单位不符，不能进行申述");
//        }
		Alarmhandleresult result = new Alarmhandleresult();
		result.setIdss(idss);
		result.setBaojingleixing(alarmType);
		result.setRemark(false);
		//根据报警id 报警类型
		List<Alarmhandleresult> resultids = alarmhandleresultService.selectIdList(result);
		List<Alarmhandleresult> list = new ArrayList<>();
		int code = 200;
		String msg = "";
		boolean delnum = false;
		boolean addnum = false;
		for (int i = 0; i < idss.length; i++) {
			Alarmhandleresult alarmResult = new Alarmhandleresult();
			alarmResult.setBaojingid(idss[i]);
			alarmResult.setChulizhuangtai(1);
			alarmResult.setChulirenid(bladeUser.getUserId());
			alarmResult.setChuliren(bladeUser.getUserName());
			alarmResult.setChulishijian(DateUtil.now());
			alarmResult.setQiyemingcheng(dept.getDeptName());
			alarmResult.setQiyeid(Integer.parseInt(deptId));
			alarmResult.setChulixingshi(chulixingshi);
			alarmResult.setBaojingleixing(alarmType);
			alarmResult.setShensushenhebiaoshi(0);
			//登录页
			if (StringUtil.isNotBlank(fujian)) {
				fileUploadClient.updateCorrelation(fujian, "1");
			}
			alarmResult.setFujian(fujian);
			String str = CommonTool.HtmlToText(chulimiaoshu);
			alarmResult.setChulimiaoshu(str);
			alarmResult.setRemark(false);
			//根据报警id查询是否出处理过 处理过就不添加
			List<Alarmhandleresult> alarmhandleresults = alarmhandleresultService.selectBybaojin(idss[i],alarmType);
			if(alarmhandleresults.size()==0){
				list.add(alarmResult);
			}
		}
		//保存之前，先删除要保存的报警已处理记录
		if (resultids.size() > 0) {
			delnum = alarmhandleresultService.removeByIds(resultids);
		}
		//添加记录
		addnum = alarmhandleresultService.saveBatch(list);
		if (delnum && addnum) {
			msg = "更新成功";
		} else if (addnum) {
			msg = "保存成功";
		} else {
			code = 500;
			msg = "保存失败";
		}
		return R.fail(code, msg);
	}

	@GetMapping("/alarmDay")
	@ApiLog("gps今日报警")
	@ApiOperation(value = "gps-今日报警", notes = "传入 company 企业名称 AlarmType 报警类型", position = 16)
	public R licheng(@ApiParam(value = "企业名称", required = true)  @RequestParam String company,@ApiParam(value = "报警类型", required = true)  @RequestParam String AlarmType) {
		List<AlarmsummaryCutofftimeVO> alarmDays = cutofftimeService.alarmDay(company, AlarmType);
		return R.data(alarmDays);
	}

	@GetMapping("/zhudongDay")
	@ApiLog("主动防御报警")
	@ApiOperation(value = "主动防御-报警", notes = "传入 company 企业名称 AlarmType 报警类型", position = 17)
	public R zhudongDay(@ApiParam(value = "企业名称", required = true)  @RequestParam String company,@ApiParam(value = "报警类型", required = true)  @RequestParam String AlarmType){
		List<DriverbehaviorVO> zhudongDay = driverbehaviorService.zhudongDay(company, AlarmType);
		return  R.data(zhudongDay);
	}

	@GetMapping("/alarmweichuli")
	@ApiLog("未处理报警")
	@ApiOperation(value = "未处理-报警", notes = "传入 company 企业名称", position = 18)
	public R alarmweichuli(@ApiParam(value = "企业名称", required = true)  @RequestParam Integer deptId){
		String date = DateUtils.getPastDate(alarmServer.getRealtime());
		List<AlarmWeichuliType> weichulitongji = alarmhandleresultService.weichulitongji(deptId,date);
		if(weichulitongji==null || weichulitongji.size()==0){
			return  R.data("");
		}
		return  R.data(weichulitongji);
	}

	@GetMapping("/theExtendedAlarmDay")
	@ApiLog("即将超期未处理报警")
	@ApiOperation(value = "即将超期未处理报警", notes = "传入 deptId 企业ID", position = 23)
	public R theExtendedAlarmDay(@ApiParam(value = "企业ID", required = true)  @RequestParam Integer deptId){
		String date = DateUtils.getPastDate(alarmServer.getRunoutthetime());
		List<AlarmWeichuliType> weichulitongji = alarmhandleresultService.cqweichulitongji(deptId,date,"false");
		if(weichulitongji==null || weichulitongji.size()==0){
			return  R.data("");
		}
		return  R.data(weichulitongji);
	}

	@GetMapping("/overdueAlarmDay")
	@ApiLog("超期未处理报警")
	@ApiOperation(value = "超期未处理报警", notes = "传入 deptId 企业ID", position = 24)
	public R overdueAlarmDay(@ApiParam(value = "企业ID", required = true)  @RequestParam Integer deptId){
		String date = DateUtils.getPastDate(alarmServer.getBeyondthetime());
		List<AlarmWeichuliType> weichulitongji = alarmhandleresultService.cqweichulitongji(deptId,date,"true");
		if(weichulitongji==null || weichulitongji.size()==0){
			return  R.data("");
		}
		return  R.data(weichulitongji);
	}

    @PostMapping("/listMG")
    @ApiLog("分页查询-北斗报警倒查MG")
    @ApiOperation(value = "分页查询-北斗报警MG", notes = "传入AlarmPage", position = 19)
    public R<AlarmPage<AlarmsummaryCutofftimeMG>> listMG(@RequestBody AlarmPage alarmPage) {
        AlarmPage pages = cutofftimeService.selectAlarmMGPage(alarmPage);
        return R.data(pages);
    }

	@PostMapping("/driverAlarmListMG")
	@ApiLog("分页查询-驾驶员行为报警倒查MG")
	@ApiOperation(value = "分页查询-驾驶员行为报警MG", notes = "传入DriverAlarmPage", position = 20)
	public R<DriverAlarmPage<DriverbehaviorMG>> driverAlarmLisMG(@RequestBody DriverAlarmPage driverAlarmPage) {
		DriverAlarmPage pages = driverbehaviorService.selectdriverbehaviorPage(driverAlarmPage);
		return R.data(pages);
	}

	@PostMapping("/AlarmTimeData")
	@ApiLog("报警信息接口-时间段报警信息")
	@ApiOperation(value = "报警信息-时间段报警信息", notes = "传入DriverAlarmPage", position = 21)
	public R alarmTimeData(@RequestBody AlarmTimePage alarmTimePage) {
		Map<String,Object> map=new HashMap<>();
		//车辆报警
		List<AlarmsummaryCutofftime> alarm = cutofftimeService.timeAlarm(alarmTimePage);
		//主动防御报警
		List<Driverbehavior> zhudong = driverbehaviorService.timeZhudong(alarmTimePage);
		for(AlarmsummaryCutofftime data:alarm){
			double lat=Double.valueOf(data.getLatitude().toString());
			double lon=Double.valueOf(data.getLongitude().toString());
			double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
			data.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
			data.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
		}
		for(Driverbehavior data:zhudong){
			double lat=Double.valueOf(data.getLatitude().toString());
			double lon=Double.valueOf(data.getLongitude().toString());
			double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
			data.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
			data.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
		}
		map.put("gps",alarm);
		map.put("driver",zhudong);
		return  R.data(map);
	}

	@PostMapping(value = "/UpdateStatementProcessRate")
	@ApiLog("企业报告-更新报告处理率")
	@ApiOperation(value = "企业报告-更新报告处理率", notes = "传入相关参数（beginTime,endTime,company,property）",position = 22)
	public R UpdateStatementProcessRate(String beginTime,String endTime,String company,String property) {
		R rs = new R();

//		DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
//		beginTime = DATE_FORMAT.format(beginTime);
//		endTime = DATE_FORMAT.format(endTime);
		List<AlarmsummaryCutofftimeVO> alarmsummaryVO = cutofftimeService.selectAlarmLv(beginTime, endTime, company);
		if(alarmsummaryVO.size() > 0) {
			for (int i = 0;i < alarmsummaryVO.size();i++){
				String processRate = alarmsummaryVO.get(i).getChulilv();
				if(alarmsummaryVO.get(i).getBaojingcishu() == 0){
					processRate = "100%";
				}
				String deptId = alarmsummaryVO.get(i).getDeptId();
//				String countdate = alarmsummaryVO.get(i).getDateShow();
				boolean is = cutofftimeService.updateBaoBiaoMuLu(processRate,deptId,property,beginTime,endTime);
				if (is == true){
					rs.setSuccess(true);
					rs.setCode(200);
					rs.setMsg("更新"+beginTime+"至"+endTime+"处理率成功");
				}
			}
		}else{
			rs.setMsg("无该企业数据");
			return rs;
		}
		return rs;
	}

	@GetMapping("/getDriverLearnBacklogCount")
	@ApiLog("教育--根据驾驶员ID、学习状态获取驾驶员学习待办数")
	@ApiOperation(value = "教育--根据驾驶员ID、学习状态获取驾驶员学习待办数", notes = "传入driverId,status", position = 30)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "driverId", value = "驾驶员ID", required = true),
		@ApiImplicitParam(name = "status", value = "状态（0待学习）", required = true)
	})
	public R getDriverLearnBacklogCount(String driverId,String status) {
		BaoBiaoAlarmhandleresultDriver detail = iBaoBiaoAlarmhandleresultDriverService.getDriverLearnBacklogCount( driverId ,status);
		return R.data(detail);
	}

//	@GetMapping("/selectAlarmsummaryRecord")
//	@ApiLog("详情-根据报警ID查询报警生命周期记录")
//	@ApiOperation(value = "详情-根据报警ID查询报警生命周期记录", notes = "传入报警ID", position = 35)
//	public R selectAlarmsummaryRecord(Integer AlarmReportID) {
//		List<BaobiaoAlarmsummaryRecord> vo = cutofftimeService.selectAlarmsummaryRecord(AlarmReportID);
//		return R.data(vo);
//	}

	@GetMapping("/updateAlarmhandleresult")
	@ApiLog("报警处理--报警附件补传")
	@ApiOperation(value = "报警处理--报警附件补传", notes = "传入数据ID、用户ID", position = 37)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Id", value = "报警ID", required = true),
		@ApiImplicitParam(name = "alarmType", value = "报警类型", required = true),
		@ApiImplicitParam(name = "userId", value = "用户ID", required = true),
		@ApiImplicitParam(name = "fujian", value = "附件路径", required = true)
	})
	public R updateAlarmhandleresult(@RequestParam String Id,String alarmType,Integer userId,String fujian) {
		R rs = new R();
		//根据报警id查询是否有处理记录
		List<Alarmhandleresult> alarmhandleresult = alarmhandleresultService.selectBybaojin(Id,alarmType);
		if(alarmhandleresult.size()==0){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("该条报警还未进行报警处理");
			return rs;
		}else{
			Alarmhandleresult alarmhandleresult1 = alarmhandleresult.get(0);
			alarmhandleresult1.setBaojingid(alarmhandleresult1.getBaojingid());
			alarmhandleresult1.setBaojingleixing(alarmhandleresult1.getBaojingleixing());
			String remark = "操作用户ID："+userId+"操作时间："+DateUtil.now()+"，补传了报警附件，附件路径："+fujian;
			alarmhandleresult1.setBeizhu(remark);
			fujian = alarmhandleresult1.getFujian()+fujian;
			alarmhandleresult1.setFujian(fujian);
			boolean i = alarmhandleresultService.updateAlarmhandleresult(alarmhandleresult1);
			if(i == true){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("报警附件补传成功");
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("报警附件补传失败");
			}
			return rs;
		}
	}

	@PostMapping("/getStairAlarmList")
	@ApiLog("北斗报警-一级报警查询（passed = 99）")
	@ApiOperation(value = "北斗报警-一级报警查询（passed = 99）", notes = "传入AlarmPage", position = 38)
	public R<AlarmPage<AlarmsummaryCutofftimeVO>> getStairAlarmList(@RequestBody AlarmPage alarmPage) {
		AlarmPage pages = cutofftimeService.selectStairAlarmPage(alarmPage);
		return R.data(pages);
	}

}
