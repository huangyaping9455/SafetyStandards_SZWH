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
package org.springblade.anbiao.jinritixing.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jinritixing.page.BaojingtongjiPage;
import org.springblade.anbiao.jinritixing.service.IBaojingtongjiService;
import org.springblade.anbiao.jinritixing.vo.BaojingtongjiVO;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/baojingtongji")
@Api(value = "报警统计", tags = "报警统计")
public class BaojingtongjiController extends BladeController {

	private IBaojingtongjiService baojingtongjiService;

	/**
	* 统计每月的每一天报警数量
	*/
	@PostMapping("/days")
	@ApiLog("每月的每一天-报警统计")
	@ApiOperation(value = "每月的每一天-报警统计", notes = "传入page", position = 1)
	public R<List<BaojingtongjiVO>> days(@RequestBody BaojingtongjiPage baojingtongjiPage) {
		if(baojingtongjiPage.getTongjiriqi() ==null || "".equals(baojingtongjiPage.getTongjiriqi())){
			baojingtongjiPage.setTongjiriqi(DateUtil.yesterday().toString());
		}
		String tongjiriqi = baojingtongjiPage.getTongjiriqi().substring(0,7)+"-01";
		baojingtongjiPage.setTongjiriqi(tongjiriqi);
		System.out.print(baojingtongjiPage);

		List<BaojingtongjiVO> list = baojingtongjiService.selectdays(baojingtongjiPage);

		return R.data(list);
	}

	/**
	 * 统计每月的报警数量
	 */
	@PostMapping("/yues")
	@ApiLog("统计每月-报警统计")
	@ApiOperation(value = "统计每月-报警统计", notes = "传入page", position = 2)
	public R<Map<String, String>> yues(@RequestBody BaojingtongjiPage page) {
		HashMap map = new HashMap();
		if(page.getTongjiriqi() ==null || "".equals(page.getTongjiriqi())){
			page.setTongjiriqi(DateUtil.yesterday().toString());
		}
		List<BaojingtongjiVO> list = baojingtongjiService.selectyues(page);
		int jidu1 =0;
		int jidu2 =0;
		int jidu3 =0;
		int jidu4 =0;
		int shangbannian = 0;
		int xiabannian = 0;
		for(int i = 0;i<list.size();i++){
			if(list.get(i).getYue()<=3){
				jidu1 += list.get(i).getCounts();
			}
			if(list.get(i).getYue()<=6 && list.get(i).getYue()>3){
				jidu2 += list.get(i).getCounts();
			}
			if(list.get(i).getYue()<=9 && list.get(i).getYue()>6){
				jidu3 += list.get(i).getCounts();
			}
			if(list.get(i).getYue()<=12 && list.get(i).getYue()>9){
				jidu4 += list.get(i).getCounts();
			}
			if(list.get(i).getYue()<=6){
				shangbannian += list.get(i).getCounts();
			}
			if(list.get(i).getYue()>6){
				xiabannian += list.get(i).getCounts();
			}
		}
		map.put("jidu1",jidu1);
		map.put("jidu2",jidu2);
		map.put("jidu3",jidu3);
		map.put("jidu4",jidu4);
		map.put("shangbannian",shangbannian);
		map.put("xiabannian",xiabannian);
		map.put("list",list);
		return R.data(map);
	}


}
