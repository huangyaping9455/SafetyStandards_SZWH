package org.springblade.anbiao.guanlijigouherenyuan.feign;

import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 呵呵哒
 * @program: SafetyStandards
 * @description:
 **/
@Component
public class IOrganizationsClientBack implements IOrganizationsClient {
	@Override
	public Organizations selectByDeptId(String deptId) {
		return null;
	}

	@Override
	public Organizations selectByDeptIdInfo(String deptId) {
		return null;
	}

	@Override
	public Boolean delByDeptId(String deptId) {
		return null;
	}

//	@Override
//	public Organization selectZFRenyuan(String id) {
//		return null;
//	}

	@Override
	public List<OrganizationsVO> getZFQY() {
		return null;
	}

	@Override
	public List<OrganizationsVO> getZFQYOrderById() {
		return null;
	}

	@Override
	public Boolean insertSelective(OrganizationsVO organizationsVO) {
		return null;
	}

	@Override
	public Organizations selectParentDeptById(String deptId) {
		return null;
	}


}
