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
import org.springblade.anbiao.cheliangguanli.entity.VehicleBaoxianMingxi;
import org.springblade.anbiao.cheliangguanli.vo.VehicleBaoxianMingxiVO;
import org.springblade.anbiao.cheliangguanli.mapper.VehicleBaoxianMingxiMapper;
import org.springblade.anbiao.cheliangguanli.service.IVehicleBaoxianMingxiService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 车辆保险信息明细 服务实现类
 *
 * @author Blade
 * @since 2022-10-28
 */
@AllArgsConstructor
@Service
public class VehicleBaoxianMingxiServiceImpl extends ServiceImpl<VehicleBaoxianMingxiMapper, VehicleBaoxianMingxi> implements IVehicleBaoxianMingxiService {

	@Override
	public IPage<VehicleBaoxianMingxiVO> selectVehicleBaoxianMingxiPage(IPage<VehicleBaoxianMingxiVO> page, VehicleBaoxianMingxiVO vehicleBaoxianMingxi) {
		return page.setRecords(baseMapper.selectVehicleBaoxianMingxiPage(page, vehicleBaoxianMingxi));
	}

	@Override
	public boolean deleteLogic(@NotEmpty List<Integer> ids) {
		return false;
	}
}
