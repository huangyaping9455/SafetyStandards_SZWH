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
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanLedgerPage;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanLedgerVO;
import org.springblade.anbiao.labor.VO.LaborledgerVO;
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
		baoxianInfo.setBaoxian(baoxianMapper.selectById(ajbId));

		JiashiyuanBaoxianMingxi mingxi = new JiashiyuanBaoxianMingxi();
		mingxi.setAjbmAvbIds(ajbId);
		List<JiashiyuanBaoxianMingxi> mingxiList = mingxiMapper.selectList(Condition.getQueryWrapper(mingxi));
		baoxianInfo.setBaoxianMingxis(mingxiList);
		return baoxianInfo;
	}

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
	public JiaShiYuanLedgerPage selectLedgerList(JiaShiYuanLedgerPage jiaShiYuanLedgerPage) {
		int total = baoxianMapper.selectLedgerTotal(jiaShiYuanLedgerPage);
		Integer AccPagetotal = 0;
		if (jiaShiYuanLedgerPage.getSize() == 0) {
			if (jiaShiYuanLedgerPage.getTotal() == 0) {
				jiaShiYuanLedgerPage.setTotal(total);
			}
			if (jiaShiYuanLedgerPage.getTotal() == 0) {
				return jiaShiYuanLedgerPage;
			} else {
				List<JiaShiYuanLedgerVO> jiaShiYuanLedgerVOS = baoxianMapper.selectLedgerPage(jiaShiYuanLedgerPage);
				jiaShiYuanLedgerPage.setRecords(jiaShiYuanLedgerVOS);
				return jiaShiYuanLedgerPage;
			}
		}
		if (total > 0) {
			if (total % jiaShiYuanLedgerPage.getSize() == 0) {
				AccPagetotal = total / jiaShiYuanLedgerPage.getSize();
			} else {
				AccPagetotal = total / jiaShiYuanLedgerPage.getSize() + 1;
			}
		}
		if (AccPagetotal < jiaShiYuanLedgerPage.getCurrent()) {
			return jiaShiYuanLedgerPage;
		} else {
			jiaShiYuanLedgerPage.setPageTotal(AccPagetotal);
			Integer offsetNo = 0;
			if (jiaShiYuanLedgerPage.getCurrent() > 1) {
				offsetNo = jiaShiYuanLedgerPage.getSize() * (jiaShiYuanLedgerPage.getCurrent() - 1);
			}
			jiaShiYuanLedgerPage.setTotal(total);
			jiaShiYuanLedgerPage.setOffsetNo(offsetNo);
			List<JiaShiYuanLedgerVO> jiaShiYuanLedgerVOS = baoxianMapper.selectLedgerPage(jiaShiYuanLedgerPage);
			jiaShiYuanLedgerPage.setRecords(jiaShiYuanLedgerVOS);
			return jiaShiYuanLedgerPage;
		}
	}


	@Override
	public boolean deleteLogic(@NotEmpty List<Integer> ids) {
		return false;
	}

	@Override
	public List<JiaShiYuanLedgerVO> selectVehicleInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectVehicleInsurance( jiaShiYuanLedgerVO);
	}

	@Override
	public JiaShiYuanLedgerVO selectHeavyTrafficInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectHeavyTrafficInsurance(jiaShiYuanLedgerVO);
	}

	@Override
	public List<JiaShiYuanLedgerVO> selectHeavyTrafficInsurance2(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectHeavyTrafficInsurance2(jiaShiYuanLedgerVO);
	}

	@Override
	public JiaShiYuanLedgerVO selectNotHeavyTrafficInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectNotHeavyTrafficInsurance(jiaShiYuanLedgerVO);
	}

	@Override
	public List<JiaShiYuanLedgerVO> selectNotHeavyTrafficInsurance2(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectNotHeavyTrafficInsurance2(jiaShiYuanLedgerVO);
	}

	@Override
	public List<JiaShiYuanLedgerVO> selectOtherVehicleInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectOtherVehicleInsurance(jiaShiYuanLedgerVO);
	}

	@Override
	public JiaShiYuanLedgerVO selectsumVehicleInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectsumVehicleInsurance(jiaShiYuanLedgerVO);
	}

	@Override
	public List<JiaShiYuanLedgerVO> selectOverlossInsuranceInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectOverlossInsuranceInsurance(jiaShiYuanLedgerVO);
	}

	@Override
	public List<JiaShiYuanLedgerVO> selectDeptInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectDeptInsurance( jiaShiYuanLedgerVO);
	}

	@Override
	public JiaShiYuanLedgerVO selectAccidentInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectAccidentInsurance(jiaShiYuanLedgerVO);
	}

	@Override
	public List<JiaShiYuanLedgerVO> selectAccidentInsurance2(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectAccidentInsurance2(jiaShiYuanLedgerVO);
	}

	@Override
	public List<JiaShiYuanLedgerVO> selectNotAccidentInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectNotAccidentInsurance(jiaShiYuanLedgerVO);
	}

	@Override
	public List<JiaShiYuanLedgerVO> selectPersonInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectPersonInsurance(jiaShiYuanLedgerVO);
	}

	@Override
	public JiaShiYuanLedgerVO selectDeptTotalTmountInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO) {
		return baoxianMapper.selectDeptTotalTmountInsurance(jiaShiYuanLedgerVO);
	}


}
