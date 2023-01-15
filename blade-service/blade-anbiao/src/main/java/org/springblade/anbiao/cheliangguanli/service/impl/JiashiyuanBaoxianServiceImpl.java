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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxian;
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxianInfo;
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxianMingxi;
import org.springblade.anbiao.cheliangguanli.mapper.JiashiyuanBaoxianMingxiMapper;
import org.springblade.anbiao.cheliangguanli.vo.JiashiyuanBaoxianVO;
import org.springblade.anbiao.cheliangguanli.mapper.JiashiyuanBaoxianMapper;
import org.springblade.anbiao.cheliangguanli.service.IJiashiyuanBaoxianService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.mp.support.Condition;
import org.springblade.system.entity.Dept;
import org.springblade.system.user.entity.User;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 驾驶员保险信息主表 服务实现类
 *
 * @author Blade
 * @since 2022-10-31
 */
@AllArgsConstructor
@Service
public class JiashiyuanBaoxianServiceImpl extends ServiceImpl<JiashiyuanBaoxianMapper, JiashiyuanBaoxian> implements IJiashiyuanBaoxianService {

	private JiashiyuanBaoxianMingxiMapper mingxiMapper;
	private JiashiyuanBaoxianMapper baoxianMapper;
	@Override
	public IPage<JiashiyuanBaoxianVO> selectJiashiyuanBaoxianPage(IPage<JiashiyuanBaoxianVO> page, JiashiyuanBaoxianVO jiashiyuanBaoxian,String ajbInsuredIds) {
		return page.setRecords(baseMapper.selectJiashiyuanBaoxianPage(page, jiashiyuanBaoxian,ajbInsuredIds));
	}

	@Override
	public JiashiyuanBaoxianInfo queryDetail(String ajbId) {
		JiashiyuanBaoxianInfo baoxianInfo = new JiashiyuanBaoxianInfo();
		baoxianInfo.setBaoxian(baoxianMapper.selectId(ajbId));

		JiashiyuanBaoxianMingxi mingxi = new JiashiyuanBaoxianMingxi();
		mingxi.setAjbmAvbIds(ajbId);
		List<JiashiyuanBaoxianMingxi> mingxiList = mingxiMapper.selectList(Condition.getQueryWrapper(mingxi));
		baoxianInfo.setBaoxianMingxis(mingxiList);
		return baoxianInfo;
	}

	@Override
	public JiashiyuanBaoxian queryByMax(String driverId){
		return baoxianMapper.queryByMax(driverId);
	}

	@Override
	public List<User> getDeptUser(String deptId) {
		return baoxianMapper.getDeptUser(deptId);
	}

	@Override
	public List<Dept> QiYeList(Integer deptId) {
		return baoxianMapper.QiYeList(deptId);
	}

	@Override
	public boolean deleteLogic(@NotEmpty List<Integer> ids) {
		return false;
	}
}
