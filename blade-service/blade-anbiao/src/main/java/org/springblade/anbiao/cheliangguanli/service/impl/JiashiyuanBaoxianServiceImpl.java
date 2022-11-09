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
package org.springblade.anbiao.cheliangguanli.service.impl;

import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxian;
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxianInfo;
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxianMingxi;
import org.springblade.anbiao.cheliangguanli.mapper.JiashiyuanBaoxianMingxiMapper;
import org.springblade.anbiao.cheliangguanli.vo.JiashiyuanBaoxianVO;
import org.springblade.anbiao.cheliangguanli.mapper.JiashiyuanBaoxianMapper;
import org.springblade.anbiao.cheliangguanli.service.IJiashiyuanBaoxianService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.mp.support.Condition;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 驾驶员保险信息主表 服务实现类
 *
 * @author Blade
 * @since 2022-10-31
 */
@Service
public class JiashiyuanBaoxianServiceImpl extends BaseServiceImpl<JiashiyuanBaoxianMapper, JiashiyuanBaoxian> implements IJiashiyuanBaoxianService {

	private JiashiyuanBaoxianMingxiMapper mingxiMapper;
	@Override
	public IPage<JiashiyuanBaoxianVO> selectJiashiyuanBaoxianPage(IPage<JiashiyuanBaoxianVO> page, JiashiyuanBaoxianVO jiashiyuanBaoxian) {
		return page.setRecords(baseMapper.selectJiashiyuanBaoxianPage(page, jiashiyuanBaoxian));
	}

	@Override
	public JiashiyuanBaoxianInfo queryDetail(String ajbId) {
		JiashiyuanBaoxianInfo baoxianInfo = new JiashiyuanBaoxianInfo();
		baoxianInfo.setBaoxian(baseMapper.selectById(ajbId));

		JiashiyuanBaoxianMingxi mingxi = new JiashiyuanBaoxianMingxi();
		mingxi.setAjbmAvbIds(ajbId);
		List<JiashiyuanBaoxianMingxi> mingxiList = mingxiMapper.selectList(Condition.getQueryWrapper(mingxi));
		baoxianInfo.setBaoxianMingxis(mingxiList);
		return baoxianInfo;
	}

}
