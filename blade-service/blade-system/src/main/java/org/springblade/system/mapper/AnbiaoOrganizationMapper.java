package org.springblade.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.springblade.system.entity.AnbiaoOrganizationsFuJian;
import org.springblade.system.entity.AnbiaoOrganization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 企业详细信息 Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2023-06-09
 */
public interface AnbiaoOrganizationMapper extends BaseMapper<AnbiaoOrganization> {

	AnbiaoOrganizationsFuJian selectByDeptImg(@Param("deptId") String deptId);

	List<AnbiaoOrganizationsFuJian> selectByDeptPost(@Param("deptId") String deptId);

	List<AnbiaoOrganizationsFuJian> selectByPersonnelImg(@Param("deptId") String deptId, @Param("postId") String postId);

}
