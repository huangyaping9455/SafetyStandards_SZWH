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
package org.springblade.anbiao.qiyeshouye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.qiyeshouye.entity.AnbiaoIllegalInfo;
import org.springblade.anbiao.qiyeshouye.entity.AnbiaoIllegalInfoTJ;
import org.springblade.anbiao.qiyeshouye.page.AnbiaoIllegalInfoPage;

/**
 * 服务类
 *
 * @author hyp
 * @since 2018-09-29
 */
public interface IAnbiaoIllegalInfoService extends IService<AnbiaoIllegalInfo> {

	/**
	 * 违法违章列表
	 * @param anbiaoIllegalInfoPage
	 * @return
	 */
	AnbiaoIllegalInfoPage<AnbiaoIllegalInfo> selectGetAll(AnbiaoIllegalInfoPage anbiaoIllegalInfoPage);

	/**
	 * 违章排查登记册
	 * @param anbiaoIllegalInfoPage
	 * @return
	 */
	AnbiaoIllegalInfoPage<AnbiaoIllegalInfoTJ> selectGetAllTJ(AnbiaoIllegalInfoPage anbiaoIllegalInfoPage);

	/**
	 * 根据企业ID、数据ID获取数据
	 * @param deptId
	 * @param Id
	 * @return
	 */
	AnbiaoIllegalInfo selectByIds(Integer deptId,Integer Id);

}
