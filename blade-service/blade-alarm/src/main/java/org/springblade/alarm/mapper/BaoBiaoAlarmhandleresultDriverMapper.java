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
package org.springblade.alarm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springblade.alarm.entity.BaoBiaoAlarmhandleresultDriver;

/**
 *  Mapper 接口
 *
 * @author Blade
 * @since 2019-12-04
 */
public interface BaoBiaoAlarmhandleresultDriverMapper extends BaseMapper<BaoBiaoAlarmhandleresultDriver> {

	/**
	 * 根据驾驶员ID、学习状态获取驾驶员学习待办数
	 * @return
	 */
	BaoBiaoAlarmhandleresultDriver getDriverLearnBacklogCount(@Param("driverId") String driverId,@Param("status") String status);

	/**
	 * 添加处罚驾驶员、报警关联记录
	 * @param baoBiaoAlarmhandleresultDriver
	 * @return
	 */
	boolean insertSelective(BaoBiaoAlarmhandleresultDriver baoBiaoAlarmhandleresultDriver);

	/**
	 * 编辑处罚驾驶员、报警关联记录
	 * @return
	 */
	boolean updateStatus(BaoBiaoAlarmhandleresultDriver baoBiaoAlarmhandleresultDriver);

}
