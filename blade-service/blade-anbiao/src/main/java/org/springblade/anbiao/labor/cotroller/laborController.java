package org.springblade.anbiao.labor.cotroller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.labor.DTO.laborDTO;
import org.springblade.anbiao.labor.entity.Labor;
import org.springblade.anbiao.labor.entity.LaborEntity;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.labor.page.LaborPage;
import org.springblade.anbiao.labor.service.laborLingquService;
import org.springblade.anbiao.labor.service.laborService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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

	private laborService service;

	private laborLingquService lingquService;

	@PostMapping("list")
	@ApiLog("列表-劳保用品信息")
	@ApiOperation(value = "分页劳保用品信息", notes = "传入LaborPage ", position = 1)
	public R saList(@RequestBody LaborPage laborPage) {
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
	@ApiOperation(value = "添加劳保用品信息", notes = "传入laborDTO", position = 2)
	public R insert(@RequestBody laborDTO laborDTO, BladeUser user) {
		if (laborDTO != null) {
			String replace = UUID.randomUUID().toString().replace("-", "");
			laborDTO.setAliIds(replace);
			List<Labor> labor = laborDTO.getLabor();
			String str = "";
			String aadApType = "";
			for (Labor list : labor) {
				aadApType += list.getAadApType() + ",";
				String replace1 = UUID.randomUUID().toString().replace("-", "");
				Labor labor1 = new Labor();
				labor1.setAadApName(list.getAadApName());
				labor1.setAadApIds(list.getAadApIds());
				labor1.setAadApType(list.getAadApType());
				labor1.setAlrIds(replace1);
				labor1.setAlrAliIds(replace);
				labor1.setAlrCreateByIds(user.getUserId().toString());
				labor1.setAlrCreateByName(user.getUserName());
				labor1.setAlrCreateTime(DateUtil.now());
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
		laborDTO.setAliCreateByIds(user.getUserId().toString());
		laborDTO.setAliCreateByName(user.getUserName());
		laborDTO.setAliCreateTime(DateUtil.now());
		return R.status(service.insertOne(laborDTO));
	}


	@GetMapping("labor")
	@ApiLog("查询-劳保用品信息")
	@ApiOperation(value = "查询劳保图形", notes = "传入laborDTO", position = 2)
	public R selectlabor(String ali_name,String aliIssueDate,String aliDeptIds) {
		return R.data(service.selectGraphics(ali_name,aliIssueDate,aliDeptIds));
	}

	@PostMapping("all")
	@ApiLog("查询-劳保用品信息")
	@ApiOperation(value = "查询用品信息", notes = "传入laborDTO", position = 2)
	public R selectAll(@RequestBody LaborPage laborPage) {
		LaborEntity laborEntities = service.selectAll(laborPage);
		List<Labor> labor = service.selectC(laborPage);
		laborEntities.setLabor(labor);
		return R.data(laborEntities);
	}

	@PostMapping("delete")
	@ApiLog("删除-劳保用品信息")
	@ApiOperation(value = "删除劳保用品信息", notes = "传入id", position = 3)
	public R delete(@RequestBody laborDTO laborDTO) {
		return R.status(service.deleteAccident(laborDTO));
	}

	@PostMapping("update")
	@ApiLog("修改-劳保用品信息")
	@ApiOperation(value = "修改劳保用品信息", notes = "传入laborDTO", position = 4)
	public R update(@RequestBody LaborEntity laborEntity) {
		R r = new R();
		QueryWrapper<LaborEntity> laborQueryWrapper = new QueryWrapper<>();
		laborQueryWrapper.lambda().eq(LaborEntity::getAliIds, laborEntity.getAliIds());
		LaborEntity laborEntity1 = service.getBaseMapper().selectOne(laborQueryWrapper);
		if (laborEntity1 != null) {
			laborEntity1.setAliUpdateByIds(laborEntity.getDeptId());
			laborEntity1.setAliIssuePeopleNumber(laborEntity.getAliIssuePeopleNumber());
			laborEntity1.setAliName(laborEntity.getAliName());
			laborEntity1.setAliDeptIds(laborEntity.getAliDeptIds());
			laborEntity1.setAliIssueQuantity(laborEntity.getAliIssueQuantity());
			laborEntity1.setAliIssueDate(laborEntity.getAliIssueDate());
			laborEntity1.setAliStatus(laborEntity.getAliStatus());
			laborEntity1.setAliDelete(laborEntity.getAliDelete());
			int i = service.getBaseMapper().updateById(laborEntity1);
			if (i > 0) {
				QueryWrapper<LaborlingquEntity> laborlingquEntityQueryWrapper = new QueryWrapper<>();
				laborlingquEntityQueryWrapper.lambda().eq(LaborlingquEntity::getAlrAliIds, laborEntity.getAliIds());
				lingquService.getBaseMapper().delete(laborlingquEntityQueryWrapper);
				List<Labor> labor2 = laborEntity.getLabor();
				for (Labor list : labor2) {
					LaborlingquEntity labor1 = new LaborlingquEntity();
					labor1.setAlrPersonName(list.getAadApName());
					labor1.setAlrPersonIds(list.getAadApIds());
					labor1.setAlrIds(list.getAlrIds());
					labor1.setAlrAliIds(laborEntity.getAliIds());
					labor1.setAliApplicationScope(list.getAadApType());
					boolean b = lingquService.save(labor1);
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

	@PostMapping("updatel")
	@ApiLog("修改-劳保用品信息")
	@ApiOperation(value = "劳保用品领取", notes = "传入LaborlingquEntity", position = 5)
	public R update(@RequestBody LaborlingquEntity laborlingqu) {
		QueryWrapper<LaborlingquEntity> laborQueryWrapper = new QueryWrapper<LaborlingquEntity>();
		laborQueryWrapper.lambda().eq(LaborlingquEntity::getAlrAliIds, laborlingqu.getAlrAliIds());
		laborQueryWrapper.lambda().eq(LaborlingquEntity::getAlrPersonIds, laborlingqu.getAlrPersonIds());
		LaborlingquEntity laborEntity1 = lingquService.getBaseMapper().selectOne(laborQueryWrapper);
		if(laborEntity1 != null){
			laborlingqu.setAlrIds(laborEntity1.getAlrIds());
			laborlingqu.setAlrUpdateByIds(laborlingqu.getAlrPersonIds());
			laborlingqu.setAlrUpdateTime(DateUtil.now());
			return R.status(service.updateL(laborlingqu));
		}else{
			R rs = new R();
			rs.setCode(200);
			rs.setMsg("暂无数据");
			rs.setSuccess(false);
			return rs;
		}
	}
}
