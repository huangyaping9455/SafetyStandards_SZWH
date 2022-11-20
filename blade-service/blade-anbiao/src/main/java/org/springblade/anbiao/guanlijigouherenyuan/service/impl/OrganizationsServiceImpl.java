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
package org.springblade.anbiao.guanlijigouherenyuan.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.mapper.OrganizationsMapper;
import org.springblade.anbiao.guanlijigouherenyuan.page.OrganizationsPage;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 *
 * @author hyp
 * @since 2019-04-30
 */
@Service
@AllArgsConstructor
public class OrganizationsServiceImpl extends ServiceImpl<OrganizationsMapper, Organizations> implements IOrganizationsService {

	OrganizationsMapper mapper;

	@Override
	public boolean updateDel(String id) {
		return mapper.updateDel(id);
	}

	@Override
	public OrganizationsPage<OrganizationsVO> selectPageList(OrganizationsPage Page) {
		Integer total = mapper.selectTotal(Page);
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%Page.getSize()==0){
				pagetotal = total / Page.getSize();
			}else {
				pagetotal = total / Page.getSize() + 1;
			}
		}
		if (pagetotal < Page.getCurrent()) {
			return Page;
		} else {
			Page.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (Page.getCurrent() > 1) {
				offsetNo = Page.getSize() * (Page.getCurrent() - 1);
			}
			Page.setTotal(total);
			Page.setOffsetNo(offsetNo);
			List<OrganizationsVO> org = mapper.selectPageList(Page);
			return (OrganizationsPage<OrganizationsVO>) Page.setRecords(org);
		}
	}

	@Override
	public int selectTotal(OrganizationsPage Page) {
		return mapper.selectTotal(Page);
	}

	@Override
	public OrganizationsVO selectByDeptId(String deptId) {
		return mapper.selectByDeptId(deptId);
	}

	@Override
	public boolean delByDeptId(String deptId) {
		return mapper.delByDeptId(deptId);
	}

//	@Override
//	public Organization selectZFRenyuan(String id) {
//		return mapper.selectZFRenyuan(id);
//	}

	@Override
	public List<OrganizationsVO> getZFQY() {
		return mapper.getZFQY();
	}

	@Override
	public List<OrganizationsVO> getZFQYOrderById() {
		return mapper.getZFQYOrderById();
	}

	@Override
	public boolean insertSelective(OrganizationsVO organizationsVO) {
		return mapper.insertSelective(organizationsVO);
	}


	@Override
	public List<Organizations> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public OrganizationsVO selectByIds(String id) {
		return mapper.selectByIds(id);
	}

	@Override
	public boolean insertOrganizationsSelective(Organizations organizations) {
		return mapper.insertOrganizationsSelective(organizations);
	}

	@Override
	public OrganizationsVO selectParentDeptById(String deptId) {
		return mapper.selectParentDeptById(deptId);
	}

	@Override
	public Organizations getorganizationByOne(String deptId, String deptName, String jigoubianma, String jigouleixing, String daoluxukezhenghao) {
		return mapper.getorganizationByOne(deptId,deptName,jigoubianma,jigouleixing,daoluxukezhenghao);
	}

	@Override
	public int selectMaxId() {
		return mapper.selectMaxId();
	}

}
