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
package org.springblade.anbiao.richenganpai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.richenganpai.entity.BaoBiaoSecurityParameterInfo;
import org.springblade.anbiao.richenganpai.entity.BaoBiaoSecurityParameterInfoRemark;
import org.springblade.anbiao.richenganpai.entity.BaoBiaoSecurityParameterInfoTime;
import org.springblade.anbiao.richenganpai.mapper.BaoBiaoSecurityParameterInfoMapper;
import org.springblade.anbiao.richenganpai.page.RiChengAnPaiPage;
import org.springblade.anbiao.richenganpai.service.IBaoBiaoSecurityParameterInfoService;
import org.springblade.doc.safetyproductionfile.vo.SafetyProductionFileVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2019-06-06
 */
@Service
@AllArgsConstructor
public class BaoBiaoSecurityParameterInfoServiceImpl extends ServiceImpl<BaoBiaoSecurityParameterInfoMapper, BaoBiaoSecurityParameterInfo> implements IBaoBiaoSecurityParameterInfoService {

	private BaoBiaoSecurityParameterInfoMapper mapper;

	@Override
	public boolean insertSelective(BaoBiaoSecurityParameterInfo baoBiaoSecurityParameterInfo) {
		return mapper.insertSelective(baoBiaoSecurityParameterInfo);
	}

	@Override
	public boolean updateSelective(BaoBiaoSecurityParameterInfo baoBiaoSecurityParameterInfo) {
		return mapper.updateSelective(baoBiaoSecurityParameterInfo);
	}

	@Override
	public List<BaoBiaoSecurityParameterInfo> selectByIds(Integer Id,String name) {
		return mapper.selectByIds(Id,name);
	}

	@Override
	public boolean deleteBind(String updateTime, Integer updateUserId,Integer Id) {
		return mapper.deleteBind(updateTime, updateUserId, Id);
	}

	@Override
	public RiChengAnPaiPage<BaoBiaoSecurityParameterInfo> getAll(RiChengAnPaiPage riChengAnPaiPage) {
		Integer total = mapper.getAllTotal(riChengAnPaiPage);
		if(riChengAnPaiPage.getSize()==0){
			if(riChengAnPaiPage.getTotal()==0){
				riChengAnPaiPage.setTotal(total);
			}

			if(riChengAnPaiPage.getTotal()==0){
				return riChengAnPaiPage;
			}else{
				List<BaoBiaoSecurityParameterInfo> richenganpaiVOList = mapper.getAll(riChengAnPaiPage);
				riChengAnPaiPage.setRecords(richenganpaiVOList);
				return riChengAnPaiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%riChengAnPaiPage.getSize()==0){
				pagetotal = total / riChengAnPaiPage.getSize();
			}else {
				pagetotal = total / riChengAnPaiPage.getSize() + 1;
			}
		}
		if (pagetotal >= riChengAnPaiPage.getCurrent()) {
			riChengAnPaiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (riChengAnPaiPage.getCurrent() > 1) {
				offsetNo = riChengAnPaiPage.getSize() * (riChengAnPaiPage.getCurrent() - 1);
			}
			riChengAnPaiPage.setTotal(total);
			riChengAnPaiPage.setOffsetNo(offsetNo);
			List<BaoBiaoSecurityParameterInfo> richenganpaiVOList = mapper.getAll(riChengAnPaiPage);
			riChengAnPaiPage.setRecords(richenganpaiVOList);
		}
		return riChengAnPaiPage;
	}

	@Override
	public boolean insertRemarkSelective(BaoBiaoSecurityParameterInfoRemark baoBiaoSecurityParameterInfoRemark) {
		return mapper.insertRemarkSelective(baoBiaoSecurityParameterInfoRemark);
	}

	@Override
	public boolean updateRemarkBind(BaoBiaoSecurityParameterInfoRemark baoBiaoSecurityParameterInfoRemark) {
		return mapper.updateRemarkBind(baoBiaoSecurityParameterInfoRemark);
	}

	@Override
	public boolean deleteRemarkBind(Integer Id) {
		return mapper.deleteRemarkBind(Id);
	}

	@Override
	public List<BaoBiaoSecurityParameterInfo> selectBaoBiaoSecurityParameterInfoRemarkByIds(Integer deptId, Integer securityId) {
		return mapper.selectBaoBiaoSecurityParameterInfoRemarkByIds(deptId, securityId);
	}

	@Override
	public List<SafetyProductionFileVO> selectSafetyProductionFileByIds(Integer deptId, String name) {
		return mapper.selectSafetyProductionFileByIds(deptId, name);
	}

	@Override
	public RiChengAnPaiPage<BaoBiaoSecurityParameterInfo> getAllByDept(RiChengAnPaiPage riChengAnPaiPage) {
		Integer total = mapper.getAllByDeptTotal(riChengAnPaiPage);
		if(riChengAnPaiPage.getSize()==0){
			if(riChengAnPaiPage.getTotal()==0){
				riChengAnPaiPage.setTotal(total);
			}

			if(riChengAnPaiPage.getTotal()==0){
				return riChengAnPaiPage;
			}else{
				List<BaoBiaoSecurityParameterInfo> richenganpaiVOList = mapper.getAllByDept(riChengAnPaiPage);
				riChengAnPaiPage.setRecords(richenganpaiVOList);
				return riChengAnPaiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%riChengAnPaiPage.getSize()==0){
				pagetotal = total / riChengAnPaiPage.getSize();
			}else {
				pagetotal = total / riChengAnPaiPage.getSize() + 1;
			}
		}
		if (pagetotal >= riChengAnPaiPage.getCurrent()) {
			riChengAnPaiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (riChengAnPaiPage.getCurrent() > 1) {
				offsetNo = riChengAnPaiPage.getSize() * (riChengAnPaiPage.getCurrent() - 1);
			}
			riChengAnPaiPage.setTotal(total);
			riChengAnPaiPage.setOffsetNo(offsetNo);
			List<BaoBiaoSecurityParameterInfo> richenganpaiVOList = mapper.getAllByDept(riChengAnPaiPage);
			riChengAnPaiPage.setRecords(richenganpaiVOList);
		}
		return riChengAnPaiPage;
	}

	@Override
	public boolean insertBaoBiaoSecurityParameterInfoTimeSelective(BaoBiaoSecurityParameterInfoTime baoBiaoSecurityParameterInfoTime) {
		return mapper.insertBaoBiaoSecurityParameterInfoTimeSelective(baoBiaoSecurityParameterInfoTime);
	}

	@Override
	public List<BaoBiaoSecurityParameterInfoTime> selectBaoBiaoSecurityParameterInfoTime(Integer deptId, Integer securityId) {
		return mapper.selectBaoBiaoSecurityParameterInfoTime(deptId, securityId);
	}

}
