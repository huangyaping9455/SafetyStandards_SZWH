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

import org.springblade.anbiao.cheliangguanli.entity.VehicleDaoluyunshuzheng;
import org.springblade.anbiao.cheliangguanli.vo.VehicleDaoluyunshuzhengVO;
import org.springblade.anbiao.cheliangguanli.mapper.VehicleDaoluyunshuzhengMapper;
import org.springblade.anbiao.cheliangguanli.service.IVehicleDaoluyunshuzhengService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 车辆道路运输证 服务实现类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Service
public class VehicleDaoluyunshuzhengServiceImpl extends BaseServiceImpl<VehicleDaoluyunshuzhengMapper, VehicleDaoluyunshuzheng> implements IVehicleDaoluyunshuzhengService {

	@Override
	public IPage<VehicleDaoluyunshuzhengVO> selectVehicleDaoluyunshuzhengPage(IPage<VehicleDaoluyunshuzhengVO> page, VehicleDaoluyunshuzhengVO vehicleDaoluyunshuzheng) {
		return page.setRecords(baseMapper.selectVehicleDaoluyunshuzhengPage(page, vehicleDaoluyunshuzheng));
	}

}
