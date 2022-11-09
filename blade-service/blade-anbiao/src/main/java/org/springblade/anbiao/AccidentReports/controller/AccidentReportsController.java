package org.springblade.anbiao.AccidentReports.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.AccidentReports.DTO.AccidentReportsDTO;
import org.springblade.anbiao.AccidentReports.VO.AccidentReportsVO;
import org.springblade.anbiao.AccidentReports.page.AccidentPage;
import org.springblade.anbiao.AccidentReports.service.AccidentReportsService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/2 14:10
 */
//@RestController
//@AllArgsConstructor
@RequestMapping("/anbiao/Accident/")
@Api(value = "事故信息", tags = "事故信息")
public class AccidentReportsController {

	@Autowired
	private AccidentReportsService  service;

	@PostMapping("list")
	@ApiLog("列表-事故")
	@ApiOperation(value = "事故信息", notes = "传入AccidentPage", position = 1)
	public R saList(AccidentPage accidentPage){
		List<AccidentReportsVO> accidentReportsEntities = service.selectList(accidentPage);
		return R.data(accidentReportsEntities);
	}


	@PostMapping("all")
	@ApiLog("列表-事故详细信息")
	@ApiOperation(value = "事故详细信息", notes = "传入rid", position = 2)
	public R selectALL(@Param("rid") String rid){
		List<AccidentReportsVO> incidentHandlingEntities = service.selectAll(rid);
		return R.data(incidentHandlingEntities);
	}

	@PostMapping("insert")
	@ApiLog("新增-事故详细信息")
	@ApiOperation(value = "事故详细信息", notes = "传入AccidentReportsDTO", position = 3)
	public R insert(AccidentReportsDTO accidentReportsDTO){

		return R.status(service.insertOne(accidentReportsDTO));
	}

	@PostMapping("delete")
	@ApiLog("删除-事故详细信息")
	@ApiOperation(value = "事故详细信息", notes = "传入id", position = 4)
	public R delete(@Param("id") String id){
		return R.status( service.deleteAccident(id));
	}

	@PostMapping("update")
	@ApiLog("修改-事故详细信息")
	@ApiOperation(value = "事故详细信息", notes = "传入AccidentReportsDTO", position = 5)
	public R update(AccidentReportsDTO accidentReportsDTO ){
		return R.status(service.updateAccident(accidentReportsDTO));
	}
}
