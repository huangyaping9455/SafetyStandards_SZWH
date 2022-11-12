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
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.VehicleInspectionItems;
import org.springblade.anbiao.cheliangguanli.vo.VehicleInspectionItemsVO;
import org.springblade.anbiao.cheliangguanli.mapper.VehicleInspectionItemsMapper;
import org.springblade.anbiao.cheliangguanli.service.IVehicleInspectionItemsService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 车辆安全检查项目 服务实现类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Service
@AllArgsConstructor
public class VehicleInspectionItemsServiceImpl extends ServiceImpl<VehicleInspectionItemsMapper, VehicleInspectionItems> implements IVehicleInspectionItemsService {

	@Override
	public IPage<VehicleInspectionItemsVO> selectVehicleInspectionItemsPage(IPage<VehicleInspectionItemsVO> page, VehicleInspectionItemsVO vehicleInspectionItems) {
		return page.setRecords(baseMapper.selectVehicleInspectionItemsPage(page, vehicleInspectionItems));
	}

	@Override
	public boolean deleteLogic(@NotEmpty List<Integer> ids) {
		return false;
	}
}
