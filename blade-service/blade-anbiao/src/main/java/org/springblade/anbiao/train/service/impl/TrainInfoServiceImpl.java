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
package org.springblade.anbiao.train.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.train.entity.TrainInfo;
import org.springblade.anbiao.train.mapper.TrainInfoMapper;
import org.springblade.anbiao.train.service.ITrainInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 * @author 呵呵哒
 */
@Service
@AllArgsConstructor
public class TrainInfoServiceImpl extends ServiceImpl<TrainInfoMapper, TrainInfo> implements ITrainInfoService {

	private TrainInfoMapper trainMapper;

	@Override
	public List<TrainInfo> getDeptWait(String deptId) {
		return trainMapper.getDeptWait(deptId);
	}

	@Override
	public List<TrainInfo> getDeptWaitCount(String deptId) {
		return trainMapper.getDeptWaitCount(deptId);
	}
}
