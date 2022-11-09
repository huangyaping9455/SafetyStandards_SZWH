package org.springblade.anbiao.labor.cotroller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.AccidentReports.DTO.AccidentReportsDTO;
import org.springblade.anbiao.AccidentReports.entity.AccidentReportsEntity;
import org.springblade.anbiao.AccidentReports.entity.IncidentHandlingEntity;
import org.springblade.anbiao.AccidentReports.service.AccidentReportsService;
import org.springblade.anbiao.labor.DTO.laborDTO;
import org.springblade.anbiao.labor.VO.LaborVO;
import org.springblade.anbiao.labor.page.LaborPage;
import org.springblade.anbiao.labor.service.laborService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Description : 劳保用品
 * @Author : long
 * @Date :2022/11/3 21:41
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/labor/")
@Api(value = "劳保用品", tags = "劳保用品信息")
public class laborController {
	@Autowired
	private laborService service;

	@PostMapping("list")
	@ApiLog("列表-劳保用品信息")
	@ApiOperation(value = "劳保用品信息", notes = "传入LaborPage ", position = 1)
	public R saList(LaborPage laborPage,String id,Date startTime,Date endTime){
		LaborPage laborPage1 = service.selectList(laborPage, id, startTime, endTime);
		return R.data(laborPage1);
	}

//
//	@PostMapping("all")
//	public R selectALL(@Param("rid") String rid){
//		List<LaborVO> laborVOS = service.selectAll();
//		return R.data(laborVOS);
//	}

	@PostMapping("insert")
	@ApiLog("添加-劳保用品信息")
	@ApiOperation(value = "劳保用品信息", notes = "传入laborDTO", position = 2)
	public R insert(laborDTO laborDTO){
		if(laborDTO!=null){
			return R.status(service.insertOne(laborDTO));
		}
		return R.success("数据为空");
	}

	@PostMapping("labor")
	@ApiLog("查询-劳保用品信息")
	@ApiOperation(value = "劳保用品信息", notes = "传入laborDTO", position = 2)
	public R selectlabor(@Param("ali_name") String ali_name){
		return R.data(service.selectGraphics(ali_name));
	}

	@PostMapping("delete")
	@ApiLog("删除-劳保用品信息")
	@ApiOperation(value = "劳保用品信息", notes = "传入id", position = 3)
	public R delete(@Param("id") String id){
		return R.status( service.deleteAccident(id));
	}

	@PostMapping("update")
	@ApiLog("修改-劳保用品信息")
	@ApiOperation(value = "劳保用品信息", notes = "传入laborDTO", position = 4)
	public R update(laborDTO laborDTO ){
		return R.status(service.updateAccident(laborDTO));
	}
}
