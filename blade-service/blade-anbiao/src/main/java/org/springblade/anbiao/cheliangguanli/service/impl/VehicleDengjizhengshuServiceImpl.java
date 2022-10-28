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

import org.springblade.anbiao.cheliangguanli.entity.VehicleDengjizhengshu;
import org.springblade.anbiao.cheliangguanli.vo.VehicleDengjizhengshuVO;
import org.springblade.anbiao.cheliangguanli.mapper.VehicleDengjizhengshuMapper;
import org.springblade.anbiao.cheliangguanli.service.IVehicleDengjizhengshuService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 车辆登记证书 服务实现类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Service
public class VehicleDengjizhengshuServiceImpl extends BaseServiceImpl<VehicleDengjizhengshuMapper, VehicleDengjizhengshu> implements IVehicleDengjizhengshuService {

	@Override
	public IPage<VehicleDengjizhengshuVO> selectVehicleDengjizhengshuPage(IPage<VehicleDengjizhengshuVO> page, VehicleDengjizhengshuVO vehicleDengjizhengshu) {
		return page.setRecords(baseMapper.selectVehicleDengjizhengshuPage(page, vehicleDengjizhengshu));
	}

}
