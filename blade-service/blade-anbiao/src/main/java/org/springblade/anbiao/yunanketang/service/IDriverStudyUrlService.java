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
package org.springblade.anbiao.yunanketang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.qiyeshouye.entity.DriverStudyHours;
import org.springblade.anbiao.qiyeshouye.entity.DriverStudyUrl;

/**
 * 服务类
 *
 * @author hyp
 * @since 2020-12-29
 */
public interface IDriverStudyUrlService extends IService<DriverStudyUrl> {

	/**
	 * 根据ID查询详情
	 * @param Id
	 * @return
	 */
	DriverStudyUrl selectByIds(String Id);

	/**
	 * 新增
	 * @param DriverStudyUrl
	 * @return
	 */
	boolean insertSelective(DriverStudyUrl DriverStudyUrl);

	/**
	 * 根据ID修改相应信息
	 * @param DriverStudyUrl
	 * @return
	 */
	boolean updateSelective(DriverStudyUrl DriverStudyUrl);

	/**
	 * 添加学习记录
	 * @param driverStudyHours
	 * @return
	 */
	boolean insertStudyHoursSelective(DriverStudyHours driverStudyHours);

	/**
	 * 编辑学习记录
	 * @param driverStudyHours
	 * @return
	 */
	boolean updateStudyHoursSelective(DriverStudyHours driverStudyHours);

	/**
	 * 根据驾驶员ID获取学习记录
	 * @param Id,date
	 * @return
	 */
	DriverStudyHours selectStudyHoursByIds(@Param("Id") String Id, @Param("date") String date);

}
