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
package org.springblade.anbiao.qiyeshouye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.qiyeshouye.entity.QiYeTongJi;
import org.springblade.anbiao.qiyeshouye.entity.ZQHT;
import org.springblade.anbiao.qiyeshouye.page.QiYeTongJiPage;
import org.springblade.anbiao.qiyeshouye.page.ZQHTPage;

import java.util.List;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/6/18
 * @描述
 */
public interface ZQHTMapper extends BaseMapper<ZQHT> {

	/**
	 * 政企互通-列表
	 * @param zqhtPage
	 * @return
	 */
	List<ZQHT> selectGetTJ(ZQHTPage zqhtPage);
	int selectGetTJTotal(ZQHTPage zqhtPage);


}
