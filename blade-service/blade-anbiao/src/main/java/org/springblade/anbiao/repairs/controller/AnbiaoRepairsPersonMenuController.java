package org.springblade.anbiao.repairs.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsInfo;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsPerson;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsPersonMenu;
import org.springblade.anbiao.repairs.page.AnbiaoRepairsDeptPage;
import org.springblade.anbiao.repairs.service.IAnbiaoRepairsPersonMenuService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2023-08-03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/repairsPersonMenu")
@Api(value = "报修-维修人员菜单管理", tags = "报修-维修人员菜单管理")
public class AnbiaoRepairsPersonMenuController {

	@Autowired
	private IAnbiaoRepairsPersonMenuService personMenuService;

	@PostMapping("/insert")
	@ApiLog("维修人员菜单管理-授权")
	@ApiOperation(value = "维修人员菜单管理-授权", notes = "传入AnbiaoRepairsPersonMenu", position = 1)
	public R insert(@RequestBody AnbiaoRepairsPersonMenu repairsPersonMenu, BladeUser user) {
		R r = new R();
		boolean ii = false;
		//根据菜单名称字符串分别截取
		String[] menu_idsss = repairsPersonMenu.getRpMenuName().split(",");
		//去除素组中重复的数组
		List<String> menus_listid = new ArrayList<String>();
		for (int i=0; i<menu_idsss.length; i++) {
			if(!menus_listid.contains(menu_idsss[i])) {
				menus_listid.add(menu_idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] menus_idss= menus_listid.toArray(new String[1]);
		if(menus_idss.length > 0) {
			for (int i = 0; i < menus_idss.length; i++) {
				QueryWrapper<AnbiaoRepairsPersonMenu> dangerQueryWrapper = new QueryWrapper<AnbiaoRepairsPersonMenu>();
				dangerQueryWrapper.lambda().eq(AnbiaoRepairsPersonMenu::getRpPersonId, repairsPersonMenu.getRpPersonId());
				dangerQueryWrapper.lambda().eq(AnbiaoRepairsPersonMenu::getRpMenuName, menus_idss[i]);
				personMenuService.getBaseMapper().delete(dangerQueryWrapper);
				AnbiaoRepairsPersonMenu deail = personMenuService.getBaseMapper().selectOne(dangerQueryWrapper);
				if(deail == null) {
					repairsPersonMenu.setRpMenuName( menus_idss[i]);
					if (user != null) {
						repairsPersonMenu.setRpCreatename(user.getUserName());
						repairsPersonMenu.setRpCreateid(user.getUserId());
					}
					repairsPersonMenu.setRpCreatetime(DateUtil.now());
					ii = personMenuService.save(repairsPersonMenu);
					if (ii) {
						r.setMsg("授权成功");
						r.setCode(200);
						r.setSuccess(true);
					} else {
						r.setMsg("授权失败");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}
		}
		return r;
	}

	@GetMapping("/getPersonMenu")
	@ApiLog("维修人员菜单管理-维修人员获取权限菜单")
	@ApiOperation(value = "维修人员菜单管理-维修人员获取权限菜单", notes = "维修人员ID", position = 2)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "driverId", value = "维修人员ID", required = true),
	})
	public R<List<AnbiaoRepairsPersonMenu>> getPersonMenu(String driverId) {
		List<AnbiaoRepairsPersonMenu> menuList = personMenuService.selectRepairsPersonMenu(driverId);
		return R.data(menuList);
	}


}
