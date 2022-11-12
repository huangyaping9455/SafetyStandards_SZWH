package org.springblade.anbiao.labor.cotroller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInputDetailed;
import org.springblade.anbiao.labor.DTO.laborDTO;
import org.springblade.anbiao.labor.entity.Labor;
import org.springblade.anbiao.labor.entity.LaborEntity;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.labor.page.LaborPage;
import org.springblade.anbiao.labor.service.laborService;
import org.springblade.anbiao.weixiu.DTO.FittingDTO;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static cn.hutool.core.date.DateUtil.now;

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
	public R saList(@RequestBody LaborPage laborPage){
		LaborPage laborPage1 = service.selectPage(laborPage);
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
	public R insert(@RequestBody laborDTO laborDTO){
		if(laborDTO!=null){
			String replace = UUID.randomUUID().toString().replace("-", "");
			laborDTO.setAliIds(replace);
			List<Labor> labor = laborDTO.getLabor();
			String str = "";
			String aadApType = "";
			for (Labor list : labor) {
				aadApType+= list.getAadApType()+",";
				String replace1 = UUID.randomUUID().toString().replace("-", "");
				Labor labor1 = new Labor();
				labor1.setAadApName(list.getAadApName());
				labor1.setAadApIds(list.getAadApIds());
				labor1.setAadApType(list.getAadApType());
				labor1.setAlrIds(replace1);
				labor1.setAlrAliIds(replace);
				service.insertA(labor1);
			}
			str = aadApType;

			String[] split = str.split(",");
			List<String> list = new ArrayList<String>();
			for (String i : split) {
				if (!list.contains(i)) {//boolean contains(Object o):
					list.add(i);
				}
			}
			String s = list.toString();
			laborDTO.setAliApplicationScope(s);
		}

		return R.status(service.insertOne(laborDTO));
		}



	@PostMapping("labor")
	@ApiLog("查询-劳保用品信息")
	@ApiOperation(value = "劳保图形", notes = "传入laborDTO", position = 2)
	public R selectlabor(@Param("ali_name") String ali_name){
		return R.data(service.selectGraphics(ali_name));
	}

	@PostMapping("all")
	@ApiLog("查询-劳保用品信息")
	@ApiOperation(value = "劳保图形", notes = "传入laborDTO", position = 2)
	public R selectAll(@RequestBody LaborPage laborPage){
		LaborEntity laborEntities = service.selectAll(laborPage);
		List<Labor> labor = service.selectC(laborPage);
		laborEntities.setLabor(labor);
		return R.data(laborEntities);
	}

	@PostMapping("delete")
	@ApiLog("删除-劳保用品信息")
	@ApiOperation(value = "劳保用品信息", notes = "传入id", position = 3)
	public R delete(@RequestBody laborDTO laborDTO){
		return R.status( service.deleteAccident(laborDTO));
	}

	@PostMapping("update")
	@ApiLog("修改-劳保用品信息")
	@ApiOperation(value = "劳保用品信息", notes = "传入laborDTO", position = 4)
	public R update(@RequestBody LaborEntity laborEntity ){
		Boolean aBoolean = false;
		List<Labor> labor = laborEntity.getLabor();
		for (Labor list : labor) {
			Labor labor1 = new Labor();
			labor1.setAadApName(list.getAadApName());
			labor1.setAadApType(list.getAadApType());
			labor1.setAlrAliIds(laborEntity.getAliIds());
			aBoolean = service.updateA(labor1);
		}
		if(!aBoolean){
			return R.success("修改失败");
		}
		return R.status(service.updateAccident(laborEntity));
	}

	@PostMapping("updatel")
	@ApiLog("修改-劳保用品信息")
	@ApiOperation(value = "劳保用品领取", notes = "传入LaborlingquEntity", position = 5)
	public R update(LaborlingquEntity laborlingqu){
		return R.status(service.updateL(laborlingqu));
	}
}
