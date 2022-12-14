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
import org.springblade.anbiao.cheliangguanli.entity.DeptBaoxian;
import org.springblade.anbiao.cheliangguanli.entity.DeptBaoxianInfo;
import org.springblade.anbiao.cheliangguanli.entity.DeptBaoxianMingxi;
import org.springblade.anbiao.cheliangguanli.mapper.DeptBaoxianMingxiMapper;
import org.springblade.anbiao.cheliangguanli.vo.DeptBaoxianVO;
import org.springblade.anbiao.cheliangguanli.mapper.DeptBaoxianMapper;
import org.springblade.anbiao.cheliangguanli.service.IDeptBaoxianService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.mp.support.Condition;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 车辆保险信息主表 服务实现类
 *
 * @author Blade
 * @since 2022-10-31
 */
@AllArgsConstructor
@Service
public class DeptBaoxianServiceImpl extends ServiceImpl<DeptBaoxianMapper, DeptBaoxian> implements IDeptBaoxianService {

	private DeptBaoxianMapper baoxianMapper;
	private DeptBaoxianMingxiMapper mingxiMapper;

	@Override
	public IPage<DeptBaoxianVO> selectDeptBaoxianPage(IPage<DeptBaoxianVO> page, DeptBaoxianVO deptBaoxian) {
		return page.setRecords(baseMapper.selectDeptBaoxianPage(page, deptBaoxian));
	}

	@Override
	public DeptBaoxianInfo queryDetail(String avbId) {
		DeptBaoxianInfo baoxianInfo = new DeptBaoxianInfo();
		baoxianInfo.setBaoxian(baseMapper.selectById(avbId));

		DeptBaoxianMingxi mingxi = new DeptBaoxianMingxi();
		mingxi.setAvbmAvbIds(avbId);

		List<DeptBaoxianMingxi> mingxiList = mingxiMapper.selectList(Condition.getQueryWrapper(mingxi));
		baoxianInfo.setMingxiList(mingxiList);
		return baoxianInfo;
	}

	@Override
	public DeptBaoxian queryByMax(String avbInsuredIds) {
		return baoxianMapper.queryByMax(avbInsuredIds);
	}

	@Override
	public boolean deleteLogic(@NotEmpty List<Integer> ids) {
		return false;
	}
}
