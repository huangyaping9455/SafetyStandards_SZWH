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
package org.springblade.anbiao.qiyeshouye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.qiyeshouye.entity.AnbiaoIllegalInfoRemark;
import org.springblade.anbiao.qiyeshouye.mapper.AnbiaoIllegalInfoRemarkMapper;
import org.springblade.anbiao.qiyeshouye.page.AnbiaoIllegalInfoPage;
import org.springblade.anbiao.qiyeshouye.service.IAnbiaoIllegalInfoRemarkService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author hyp
 * @since 2020-12-29
 */
@Service
@AllArgsConstructor
class AnbiaoIllegalInfoRemarkRemarkServiceImpl extends ServiceImpl<AnbiaoIllegalInfoRemarkMapper, AnbiaoIllegalInfoRemark> implements IAnbiaoIllegalInfoRemarkService {

	private AnbiaoIllegalInfoRemarkMapper anbiaoIllegalInfoRemarkMapper;


	@Override
	public boolean updateSelective(AnbiaoIllegalInfoRemark anbiaoIllegalInfoRemark) {
		return anbiaoIllegalInfoRemarkMapper.updateSelective(anbiaoIllegalInfoRemark);
	}

	@Override
	public AnbiaoIllegalInfoRemark selectByIds(Integer illegalid) {
		return anbiaoIllegalInfoRemarkMapper.selectByIds(illegalid);
	}
}
