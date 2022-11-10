package org.springblade.anbiao.SafeInvestment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.math.NumberUtils;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
		SafelInfoPage safelInfoPage1 = safeInvestmentService.selectPage(safelInfoPage);
		return R.data(safelInfoPage1);
	}


	@PostMapping("all")
	@ApiLog("列表-安全投入详情")
	@ApiOperation(value = "安全投入详情", notes = "rid", position = 2)
	public R selectALL(String asid_asi_ids){
		List<SafetyInvestmentDetailsVO> safetyInvestmentDetailsVOS = safeInvestmentService.selectAll(asid_asi_ids);
		return R.data(safetyInvestmentDetailsVOS);
	}

	@PostMapping("insert")
	@ApiLog("新增-安全投入详情")
	@ApiOperation(value = "新增安全投入", notes = "SafeInvestmentDTO ", position = 3)
	public R insert(@RequestBody SafeInvestmentDTO safeInvestmentDTO){
		R rs = new R();
		AnbiaoSafetyInput anbiaoSafetyInput = new AnbiaoSafetyInput();
		String s = UUID.randomUUID().toString().replace("-", "");
		anbiaoSafetyInput.setAsi_ids(s);
		anbiaoSafetyInput.setAsi_update_by_ids(safeInvestmentDTO.getDeptId());
		anbiaoSafetyInput.setAsi_year(safeInvestmentDTO.getAsi_year());
		anbiaoSafetyInput.setAsi_accrued_amount(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsi_accrued_amount()));
		anbiaoSafetyInput.setAsi_withdrawal_amount(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsi_withdrawal_amount()));
		anbiaoSafetyInput.setAsi_extraction_proportion((safeInvestmentDTO.getAsi_extraction_proportion()));
		anbiaoSafetyInput.setAsi_amount_used(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsi_amount_used()));
		anbiaoSafetyInput.setAsi_remaining_amount(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsi_remaining_amount()));
		anbiaoSafetyInput.setAsi_dept_ids(safeInvestmentDTO.getAsi_dept_ids());

		Boolean aBoolean = false;
		if(anbiaoSafetyInput!=null){
			 aBoolean = safeInvestmentService.insertOne(anbiaoSafetyInput);
		}

		List<AnbiaoSafetyInputDetailed> inputDetailedList = safeInvestmentDTO.getInputDetailedList();
		Boolean insert = false;
		if(inputDetailedList!=null){
			for (AnbiaoSafetyInputDetailed list : inputDetailedList) {
				String uuid1 = UUID.randomUUID().toString().replace("-", "");
				AnbiaoSafetyInputDetailed anbiaoSafetyInputDetailed = new AnbiaoSafetyInputDetailed();
				anbiaoSafetyInputDetailed.setAsid_ids(uuid1);
				anbiaoSafetyInputDetailed.setAsid_entry_name(list.getAsid_entry_name());
				anbiaoSafetyInputDetailed.setAsid_handled_by_name(list.getAsid_handled_by_name());
				anbiaoSafetyInputDetailed.setAsid_investment_scope(list.getAsid_investment_scope());
				anbiaoSafetyInputDetailed.setAsid_investment_dare(list.getAsid_investment_dare());
				anbiaoSafetyInputDetailed.setAsid_amount_used(list.getAsid_amount_used());
				insert = safeInvestmentService.insert(anbiaoSafetyInputDetailed);
			}
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
	@ApiOperation(value = "删除安全投入", notes = "asi_dept_ids ", position = 4)
	public R delete(@Param("asi_dept_ids") String asi_dept_ids){
		return R.status(safeInvestmentService.delete(asi_dept_ids));
	}

	@PostMapping("update")
	@ApiLog("修改-安全投入详情")
	@ApiOperation(value = "修改安全投入", notes = "AnbiaoSafetyInput ", position = 5)
	public R update(@RequestBody AnbiaoSafetyInput anbiaoSafetyInput){
		return R.status(safeInvestmentService.updateSafe(anbiaoSafetyInput));
	}

}
