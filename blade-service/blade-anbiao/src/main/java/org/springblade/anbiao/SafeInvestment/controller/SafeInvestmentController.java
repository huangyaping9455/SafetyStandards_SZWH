package org.springblade.anbiao.SafeInvestment.controller;

import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.SafeInvestment.DTO.SafeInvestmentDTO;
import org.springblade.anbiao.SafeInvestment.VO.SafeInvestmentVO;
import org.springblade.anbiao.SafeInvestment.VO.SafetyInvestmentDetailsVO;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInput;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInputDetailed;
import org.springblade.anbiao.SafeInvestment.service.impl.SafeInvestmentServiceImpl;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author long
 * @create 2022-10-28-11:31
 */
@RequestMapping("/anbiao/SafeInvestment/")
public class SafeInvestmentController extends BladeController {

	private SafeInvestmentServiceImpl safeInvestmentService;

	@PostMapping("list")
	public R saList(SafeInvestmentDTO safeInvestmentDTO){
		List<SafeInvestmentVO> safeInvestmentVOS = safeInvestmentService.selectList(safeInvestmentDTO);
		return R.data(safeInvestmentVOS);
	}


	@PostMapping("all")
	public R selectALL(@Param("rid") String rid){
		List<SafetyInvestmentDetailsVO> safetyInvestmentDetailsVOS = safeInvestmentService.selectAll(rid);
		return R.data(safetyInvestmentDetailsVOS);
	}

	@PostMapping("insert")
	public R insert(SafeInvestmentDTO safeInvestmentDTO){
		AnbiaoSafetyInput anbiaoSafetyInput = safeInvestmentDTO.getAnbiaoSafetyInput();
		Boolean aBoolean = safeInvestmentService.insertOne(anbiaoSafetyInput);

		List<AnbiaoSafetyInputDetailed> inputDetailedList = safeInvestmentDTO.getInputDetailedList();

		Boolean insert = safeInvestmentService.insert(inputDetailedList);

		if(aBoolean&&insert){
			return R.status(true);
		}
		return R.status(false);
	}

	@PostMapping("delete")
	public R delete(String asi_dept_ids){
		return R.status(safeInvestmentService.delete(asi_dept_ids));
	}

	@PostMapping("update")
	public R update(AnbiaoSafetyInput anbiaoSafetyInput){
		return R.status(safeInvestmentService.updateSafe(anbiaoSafetyInput));
	}

}
