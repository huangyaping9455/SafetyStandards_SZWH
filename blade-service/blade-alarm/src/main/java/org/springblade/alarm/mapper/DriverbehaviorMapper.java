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
import org.springblade.alarm.entity.AlarmBaojingTongji;
import org.springblade.alarm.entity.Driverbehavior;
import org.springblade.alarm.page.AlarmTimePage;
import org.springblade.alarm.page.DriverAlarmPage;
import org.springblade.alarm.page.ShishiBaojingTongjiPage;
import org.springblade.alarm.vo.DriverbehaviorVO;

import java.util.List;
import java.util.Map;

/**
 * 驾驶员行为报警 Mapper 接口
 *
 * @author hyp
 * @since 2019-05-12
 */
public interface DriverbehaviorMapper extends BaseMapper<Driverbehavior> {

    /**
     * 自定义分页
     *
     * @param driverAlarmPage
     * @return
     */
    List<DriverbehaviorVO> selectAlarmPage(DriverAlarmPage driverAlarmPage);

    /**
     * 统计
     *
     * @param driverAlarmPage
     * @return
     */
    int selectAlarmTotal(DriverAlarmPage driverAlarmPage);

    /**
     * 查询报警车辆、次数、处理率
     *
     * @param page
     * @return
     */
    AlarmBaojingTongji selectBaojingtongji(DriverAlarmPage page);

    /**
     * 查询报警车辆、次数、处理率
     *
     * @param page
     * @return
     */
    Map<String, Object> selectShishiBaojingTongji(ShishiBaojingTongjiPage page);

    /**
     * 根据报警id 查询报警详情
     */
    DriverbehaviorVO selectAlarmDetail(@Param("id") String id, @Param("AlarmType") String AlarmType);
	/**
	 * 主动安全今日报警
	 */
	List<DriverbehaviorVO> zhudongDay(@Param("company") String company, @Param("AlarmType") String AlarmType, @Param("date")String date);



    /**
     * 查询主动防御是否有报警
     * @param shishiBaojingTongjiPage
     * @return
     */
    List<String> selectShifouBaojing(ShishiBaojingTongjiPage shishiBaojingTongjiPage);
	/**
	 * 查询时间段的主动防御报警
	 */
	List<Driverbehavior> timeZhudong(AlarmTimePage alarmTimePage);
}
