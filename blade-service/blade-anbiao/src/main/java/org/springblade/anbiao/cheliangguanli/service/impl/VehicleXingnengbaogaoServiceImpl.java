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
package org.springblade.anbiao.cheliangguanli.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.anbiao.cheliangguanli.entity.VehicleXingnengbaogao;
import org.springblade.anbiao.cheliangguanli.vo.VehicleXingnengbaogaoVO;
import org.springblade.anbiao.cheliangguanli.mapper.VehicleXingnengbaogaoMapper;
import org.springblade.anbiao.cheliangguanli.service.IVehicleXingnengbaogaoService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 车辆综合性能检测报告 服务实现类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Service
public class VehicleXingnengbaogaoServiceImpl extends ServiceImpl<VehicleXingnengbaogaoMapper, VehicleXingnengbaogao> implements IVehicleXingnengbaogaoService {

	VehicleXingnengbaogaoMapper xingnengbaogaoMapper;

	@Override
	public IPage<VehicleXingnengbaogaoVO> selectVehicleXingnengbaogaoPage(IPage<VehicleXingnengbaogaoVO> page, VehicleXingnengbaogaoVO vehicleXingnengbaogao) {
		return page.setRecords(baseMapper.selectVehicleXingnengbaogaoPage(page, vehicleXingnengbaogao));
	}

	@Override
	public VehicleXingnengbaogaoVO selectVehicleXingnengbaogaoByVehicleIds(String avxAvIds) {
		return xingnengbaogaoMapper.selectVehicleXingnengbaogaoByVehicleIds(avxAvIds);
	}

	@Override
	public boolean deleteLogic(@NotEmpty List<Integer> ids) {
		return false;
	}
}
