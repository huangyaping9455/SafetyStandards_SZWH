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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.alarm.entity.Alarmsummary;
import org.springblade.alarm.mapper.AlarmsummaryMapper;
import org.springblade.alarm.service.IAlarmsummaryService;
import org.springblade.alarm.vo.AlarmsummaryVO;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author hyp
 * @since 2019-05-12
 */
@Service
public class AlarmsummaryServiceImpl extends ServiceImpl<AlarmsummaryMapper, Alarmsummary> implements IAlarmsummaryService {

	@Override
	public IPage<AlarmsummaryVO> selectAlarmsummaryPage(IPage<AlarmsummaryVO> page, AlarmsummaryVO alarmsummary) {
		return page.setRecords(baseMapper.selectAlarmsummaryPage(page, alarmsummary));
	}

}
