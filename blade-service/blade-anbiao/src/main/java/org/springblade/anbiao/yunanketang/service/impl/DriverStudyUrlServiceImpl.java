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
package org.springblade.anbiao.yunanketang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.qiyeshouye.entity.DriverStudyHours;
import org.springblade.anbiao.qiyeshouye.entity.DriverStudyUrl;
import org.springblade.anbiao.yunanketang.mapper.DriverStudyUrlMapper;
import org.springblade.anbiao.yunanketang.service.IDriverStudyUrlService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author hyp
 * @since 2020-12-29
 */
@Service
@AllArgsConstructor
public class DriverStudyUrlServiceImpl extends ServiceImpl<DriverStudyUrlMapper, DriverStudyUrl> implements IDriverStudyUrlService {

	private org.springblade.anbiao.yunanketang.mapper.DriverStudyUrlMapper DriverStudyUrlMapper;

	@Override
	public DriverStudyUrl selectByIds(String Id) {
		return DriverStudyUrlMapper.selectByIds(Id);
	}

	@Override
	public boolean insertSelective(DriverStudyUrl DriverStudyUrl) {
		return DriverStudyUrlMapper.insertSelective(DriverStudyUrl);
	}

	@Override
	public boolean updateSelective(DriverStudyUrl DriverStudyUrl) {
		return DriverStudyUrlMapper.updateSelective(DriverStudyUrl);
	}

	@Override
	public boolean insertStudyHoursSelective(DriverStudyHours driverStudyHours) {
		return DriverStudyUrlMapper.insertStudyHoursSelective(driverStudyHours);
	}

	@Override
	public boolean updateStudyHoursSelective(DriverStudyHours driverStudyHours) {
		return DriverStudyUrlMapper.updateStudyHoursSelective(driverStudyHours);
	}

	@Override
	public DriverStudyHours selectStudyHoursByIds(String Id,String date) {
		return DriverStudyUrlMapper.selectStudyHoursByIds(Id,date);
	}


}
