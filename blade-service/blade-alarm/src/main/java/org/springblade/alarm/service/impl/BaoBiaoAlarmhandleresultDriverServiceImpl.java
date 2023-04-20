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
package org.springblade.alarm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.alarm.entity.BaoBiaoAlarmhandleresultDriver;
import org.springblade.alarm.mapper.BaoBiaoAlarmhandleresultDriverMapper;
import org.springblade.alarm.service.IBaoBiaoAlarmhandleresultDriverService;
import org.springframework.stereotype.Service;


/**
 * VIEW 服务实现类
 *
 * @author Blade
 * @since 2019-11-13
 */
@Service
@AllArgsConstructor
public class BaoBiaoAlarmhandleresultDriverServiceImpl extends ServiceImpl<BaoBiaoAlarmhandleresultDriverMapper, BaoBiaoAlarmhandleresultDriver> implements IBaoBiaoAlarmhandleresultDriverService {

	private BaoBiaoAlarmhandleresultDriverMapper baoBiaoAlarmhandleresultDriverMapper;


	@Override
	public BaoBiaoAlarmhandleresultDriver getDriverLearnBacklogCount(String driverId,String status) {
		return baoBiaoAlarmhandleresultDriverMapper.getDriverLearnBacklogCount(driverId,status);
	}

	@Override
	public boolean insertSelective(BaoBiaoAlarmhandleresultDriver baoBiaoAlarmhandleresultDriver) {
		return baoBiaoAlarmhandleresultDriverMapper.insertSelective(baoBiaoAlarmhandleresultDriver);
	}

	@Override
	public boolean updateStatus(BaoBiaoAlarmhandleresultDriver baoBiaoAlarmhandleresultDriver) {
		return baoBiaoAlarmhandleresultDriverMapper.updateStatus(baoBiaoAlarmhandleresultDriver);
	}
}
