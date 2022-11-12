package org.springblade.anbiao.SafeInvestment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.SafeInvestment.DTO.SafeInvestmentDTO;
import org.springblade.anbiao.SafeInvestment.VO.SafeAllVO;
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
	public R saList(@RequestBody SafelInfoPage safelInfoPage) {
		SafelInfoPage safelInfoPage1 = safeInvestmentService.selectPage(safelInfoPage);
		return R.data(safelInfoPage1);
	}


	@PostMapping("all")
	@ApiLog("列表-安全投入详情")
	@ApiOperation(value = "安全投入详情", notes = "SafeInvestmentDTO", position = 2)
	public R selectALL(@RequestBody SafeInvestmentDTO safeInvestmentDTO) {
		SafeAllVO safeAllVO = safeInvestmentService.selectAll(safeInvestmentDTO);
		return R.data(safeAllVO);
	}

	@PostMapping("insert")
	@ApiLog("新增-安全投入详情")
	@ApiOperation(value = "新增安全投入", notes = "SafeInvestmentDTO ", position = 3)
	public R insert(@RequestBody SafeInvestmentDTO safeInvestmentDTO) {
		R rs = new R();
		AnbiaoSafetyInput anbiaoSafetyInput = new AnbiaoSafetyInput();
		String s = UUID.randomUUID().toString().replace("-", "");
		anbiaoSafetyInput.setAsi_ids(s);
		anbiaoSafetyInput.setAsi_update_by_ids(safeInvestmentDTO.getDeptId());
		anbiaoSafetyInput.setAsi_year(safeInvestmentDTO.getAsiYear());
		anbiaoSafetyInput.setAsi_accrued_amount(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsiAccruedAmount()));
		anbiaoSafetyInput.setAsi_withdrawal_amount(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsiWithdrawalAmount()));
		String replace = safeInvestmentDTO.getAsiExtractionProportion().replace("%", "");
		anbiaoSafetyInput.setAsi_extraction_proportion(NumberUtils.createBigDecimal(replace));
		anbiaoSafetyInput.setAsi_amount_used(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsiAmountUsed()));
		anbiaoSafetyInput.setAsi_remaining_amount(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsiRemainingAmount()));
		anbiaoSafetyInput.setAsi_dept_ids(safeInvestmentDTO.getAsiDeptIds().toString());
		anbiaoSafetyInput.setAsi_last_years_turnover(NumberUtils.createBigDecimal(safeInvestmentDTO.getAsiLastYearsTurnover()));
		Boolean aBoolean = false;
		if (anbiaoSafetyInput != null) {
			aBoolean = safeInvestmentService.insertOne(anbiaoSafetyInput);
		}

		List<SafetyInvestmentDetailsVO> detailsVOList = safeInvestmentDTO.getSafetyInvestmentDetailsVOS();
		Boolean insert = true;
		if (detailsVOList != null) {
			for (SafetyInvestmentDetailsVO list : detailsVOList) {
				String uuid1 = UUID.randomUUID().toString().replace("-", "");
				AnbiaoSafetyInputDetailed anbiaoSafetyInputDetailed = new AnbiaoSafetyInputDetailed();
				anbiaoSafetyInputDetailed.setAsidIds(uuid1);
				anbiaoSafetyInputDetailed.setAsidAsiIds(s);
				anbiaoSafetyInputDetailed.setAsidEntryName(list.getAsidEntryName());
				anbiaoSafetyInputDetailed.setAsidHandledByName(list.getAsidHandledByName());
				anbiaoSafetyInputDetailed.setAsidInvestmentScope(list.getAsidInvestmentScope());
				anbiaoSafetyInputDetailed.setAsidInvestmentDare(list.getAsidInvestmentDare());
				anbiaoSafetyInputDetailed.setAsidAmountUsed(list.getAsidAmountUsed());
				anbiaoSafetyInputDetailed.setAsidHandledByIds(safeInvestmentDTO.getDeptId());
				insert = safeInvestmentService.insert(anbiaoSafetyInputDetailed);
			}
		}
		if (aBoolean && insert) {
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("新增成功");
		} else {
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("新增失败");
		}
		return rs;
	}

	@PostMapping("delete")
	@ApiLog("删除-安全投入详情")
	@ApiOperation(value = "删除安全投入", notes = "SafeInvestmentDTO ", position = 4)
	public R delete(@RequestBody SafeInvestmentDTO safeInvestmentDTO) {
		return R.status(safeInvestmentService.delete(safeInvestmentDTO));
	}

	@PostMapping("update")
	@ApiLog("修改-安全投入详情")
	@ApiOperation(value = "修改安全投入", notes = "AnbiaoSafetyInput ", position = 5)
	public R update(@RequestBody SafeInvestmentDTO safeInvestmentDTO) {
		R rs = new R();
		List<SafetyInvestmentDetailsVO> detailsVOList = safeInvestmentDTO.getSafetyInvestmentDetailsVOS();
		Boolean update = true;
		Boolean insert = true;
		if (detailsVOList != null) {
			List<AnbiaoSafetyInputDetailed> selectd = safeInvestmentService.selectd(safeInvestmentDTO);
			if (selectd != null&&selectd.size()!=0) {
				for (SafetyInvestmentDetailsVO list : detailsVOList) {
					SafetyInvestmentDetailsVO safetyInvestmentDetailsVO = new SafetyInvestmentDetailsVO();
					safetyInvestmentDetailsVO.setAsidEntryName(list.getAsidEntryName());
					safetyInvestmentDetailsVO.setAsidHandledByName(list.getAsidHandledByName());
					safetyInvestmentDetailsVO.setAsidInvestmentScope(list.getAsidInvestmentScope());
					safetyInvestmentDetailsVO.setAsidInvestmentDare(list.getAsidInvestmentDare());
					safetyInvestmentDetailsVO.setAsidAmountUsed(list.getAsidAmountUsed());
					safetyInvestmentDetailsVO.setAsidHandledByIds(safeInvestmentDTO.getDeptId());
					safetyInvestmentDetailsVO.setAsidIds(safeInvestmentDTO.getAsiIds());
					update = safeInvestmentService.updateSafede(safetyInvestmentDetailsVO);
				}
			} else {
				for (SafetyInvestmentDetailsVO list : detailsVOList) {
					String uuid1 = UUID.randomUUID().toString().replace("-", "");
					AnbiaoSafetyInputDetailed anbiaoSafetyInputDetailed = new AnbiaoSafetyInputDetailed();
					anbiaoSafetyInputDetailed.setAsidIds(uuid1);
					anbiaoSafetyInputDetailed.setAsidAsiIds(safeInvestmentDTO.getAsiIds());
					anbiaoSafetyInputDetailed.setAsidEntryName(list.getAsidEntryName());
					anbiaoSafetyInputDetailed.setAsidHandledByName(list.getAsidHandledByName());
					anbiaoSafetyInputDetailed.setAsidInvestmentScope(list.getAsidInvestmentScope());
					anbiaoSafetyInputDetailed.setAsidInvestmentDare(list.getAsidInvestmentDare());
					anbiaoSafetyInputDetailed.setAsidAmountUsed(list.getAsidAmountUsed());
					anbiaoSafetyInputDetailed.setAsidHandledByIds(safeInvestmentDTO.getDeptId());
					insert = safeInvestmentService.insert(anbiaoSafetyInputDetailed);
				}
			}
			Boolean aBoolean = safeInvestmentService.updateSafe(safeInvestmentDTO);
			if (aBoolean && update && insert) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("修改成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("修改失败");
			}
		}
		return rs;
	}
}
