package org.springblade.system.service;

import org.apache.ibatis.annotations.Param;
import org.springblade.system.entity.AnbiaoOrganizationsFuJian;
import org.springblade.system.entity.AnbiaoOrganization;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 企业详细信息 服务类
 * </p>
 *
 * @author lmh
 * @since 2023-06-09
 */
public interface IAnbiaoOrganizationService extends IService<AnbiaoOrganization> {

	AnbiaoOrganizationsFuJian selectByDeptImg(@Param("deptId") String deptId);

}
