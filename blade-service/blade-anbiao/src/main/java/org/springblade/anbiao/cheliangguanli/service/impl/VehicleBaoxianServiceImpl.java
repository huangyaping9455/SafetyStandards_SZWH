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
import org.springblade.anbiao.cheliangguanli.entity.VehicleBaoxian;
import org.springblade.anbiao.cheliangguanli.entity.VehicleBaoxianInfo;
import org.springblade.anbiao.cheliangguanli.entity.VehicleBaoxianMingxi;
import org.springblade.anbiao.cheliangguanli.mapper.VehicleBaoxianMingxiMapper;
import org.springblade.anbiao.cheliangguanli.vo.VehicleBaoxianVO;
import org.springblade.anbiao.cheliangguanli.mapper.VehicleBaoxianMapper;
import org.springblade.anbiao.cheliangguanli.service.IVehicleBaoxianService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.mp.support.Condition;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 车辆保险信息主表 服务实现类
 *
 * @author Blade
 * @since 2022-10-28
 */
@AllArgsConstructor
@Service
public class VehicleBaoxianServiceImpl extends ServiceImpl<VehicleBaoxianMapper, VehicleBaoxian> implements IVehicleBaoxianService {

	private VehicleBaoxianMingxiMapper baoxianMingxiMapper;
	private VehicleBaoxianMapper baoxianMapper;

	@Override
	public IPage<VehicleBaoxianVO> selectVehicleBaoxianPage(IPage<VehicleBaoxianVO> page, VehicleBaoxianVO vehicleBaoxian,String avbAvIds,String deptName,String cheliangpaizhao) {
		return page.setRecords(baseMapper.selectVehicleBaoxianPage(page, vehicleBaoxian,avbAvIds,deptName,cheliangpaizhao));
	}

	@Override
	public VehicleBaoxianInfo queryDetail(String avbId) {
		VehicleBaoxianInfo baoxianInfo = new VehicleBaoxianInfo();
		baoxianInfo.setBaoxian(baoxianMapper.queryById(avbId));

		VehicleBaoxianMingxi mingxi = new VehicleBaoxianMingxi();
		mingxi.setAvbmAvbIds(avbId);
		List<VehicleBaoxianMingxi> mingxiList = baoxianMingxiMapper.selectList(Condition.getQueryWrapper(mingxi));
		baoxianInfo.setBaoxianMingxis(mingxiList);
		return baoxianInfo;
	}

	public VehicleBaoxian queryByMax(String avbId) {
		return baoxianMapper.queryByMax(avbId);
	}

	@Override
	public VehicleBaoxian queryById(String avbId) {
		return baoxianMapper.queryById(avbId);
	}

	@Override
	public boolean deleteLogic(@NotEmpty List<Integer> ids) {
		return false;
	}
}
