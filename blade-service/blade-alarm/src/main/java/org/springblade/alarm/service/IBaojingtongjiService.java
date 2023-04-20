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
package org.springblade.alarm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.alarm.entity.AlarmCountDay;
import org.springblade.alarm.entity.BaojingTJ;
import org.springblade.alarm.entity.BaojingTJMX;
import org.springblade.alarm.page.AlarmPage;
import org.springblade.alarm.page.BaojingTJPage;
import org.springblade.alarm.vo.BaojingtongjiVO;

/**
 *  服务类
 *
 * @author Blade
 * @since 2019-07-25
 */
public interface IBaojingtongjiService extends IService<BaojingTJ> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param baojingtongji
	 * @return
	 */
	IPage<BaojingtongjiVO> selectBaojingtongjiPage(IPage<BaojingtongjiVO> page, BaojingtongjiVO baojingtongji);
	int findtoto(BaojingTJPage baojingTJPage);
	BaojingTJPage selectAll(BaojingTJPage baojingTJPage);

	BaojingTJPage  PilaoAll(BaojingTJPage baojingTJPage);
	/**
	 * 当日报警统计
	 */
	AlarmCountDay alarmCount(String company);
	/**
	 * 24小时不定位统计
	 */
	BaojingTJPage selectbudingwei(BaojingTJPage baojingTJPage);
	/**
	 * 24小时不在线统计
	 */
	BaojingTJPage selectbuzaixian(BaojingTJPage baojingTJPage);

	/**
	 * 报警事后违规处理统计
	 * @param alarmPage
	 * @return
	 */
	AlarmPage<BaojingTJMX> selectAlarmTJMXPage(AlarmPage alarmPage);
}
