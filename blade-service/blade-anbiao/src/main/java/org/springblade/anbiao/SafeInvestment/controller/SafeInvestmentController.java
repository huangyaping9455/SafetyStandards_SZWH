package org.springblade.anbiao.SafeInvestment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.SafeInvestment.DTO.SafeInvestmentDTO;
import org.springblade.anbiao.SafeInvestment.VO.SafetyInvestmentDetailsVO;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInput;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInputDetailed;
import org.springblade.anbiao.SafeInvestment.page.SafelInfoPage;
import org.springblade.anbiao.SafeInvestment.service.impl.SafeInvestmentServiceImpl;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author long
 * @create 2022-10-28-11:31
 */
@RequestMapping("/anbiao/SafeInvestment/")
@RestController
@AllArgsConstructor
@Api(value = "安全投入", tags = "安全投入")
public class SafeInvestmentController extends BladeController {

	private SafeInvestmentServiceImpl safeInvestmentService;

	@PostMapping("list")
	@ApiLog("分页 列表-安全投入")
	@ApiOperation(value = "安全投入", notes = "传入SafeInvestmentDTO", position = 1)
	public R saList(SafelInfoPage safelInfoPage){
		return R.data(safeInvestmentService.selectPage(safelInfoPage));
	}


	@PostMapping("all")
	@ApiLog("列表-安全投入详情")
	@ApiOperation(value = "安全投入详情", notes = "rid", position = 2)
	public R selectALL(@Param("rid") String rid){
		List<SafetyInvestmentDetailsVO> safetyInvestmentDetailsVOS = safeInvestmentService.selectAll(rid);
		return R.data(safetyInvestmentDetailsVOS);
	}

	@PostMapping("insert")
	@ApiLog("新增-安全投入详情")
	@ApiOperation(value = "安全投入详情", notes = "SafeInvestmentDTO ", position = 3)
	public R insert(SafeInvestmentDTO safeInvestmentDTO){
		R rs = new R();
		AnbiaoSafetyInput anbiaoSafetyInput = safeInvestmentDTO.getAnbiaoSafetyInput();
		Boolean aBoolean = false;
		if(anbiaoSafetyInput!=null){
			 aBoolean = safeInvestmentService.insertOne(anbiaoSafetyInput);
		}

		List<AnbiaoSafetyInputDetailed> inputDetailedList = safeInvestmentDTO.getInputDetailedList();
		Boolean insert = false;
		if(inputDetailedList!=null){
			insert = safeInvestmentService.insert(inputDetailedList);
		}
		if(aBoolean&&insert){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("新增成功");
		}else {
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("新增失败");
		}
		return rs;
	}

	@PostMapping("delete")
	@ApiLog("删除-安全投入详情")
	@ApiOperation(value = "安全投入详情", notes = "asi_dept_ids ", position = 4)
	public R delete(@Param("asi_dept_ids") String asi_dept_ids){
		return R.status(safeInvestmentService.delete(asi_dept_ids));
	}

	@PostMapping("update")
	@ApiLog("修改-安全投入详情")
	@ApiOperation(value = "安全投入详情", notes = "AnbiaoSafetyInput ", position = 5)
	public R update(AnbiaoSafetyInput anbiaoSafetyInput){
		return R.status(safeInvestmentService.updateSafe(anbiaoSafetyInput));
	}

}
