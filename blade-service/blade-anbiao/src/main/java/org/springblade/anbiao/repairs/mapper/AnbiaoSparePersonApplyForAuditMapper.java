package org.springblade.anbiao.repairs.mapper;

import org.springblade.anbiao.repairs.entity.AnbiaoSparePersonApplyForAudit;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePersonApplyForAudit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.repairs.page.AnbiaoSparePersonApplyForAuditPage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
public interface AnbiaoSparePersonApplyForAuditMapper extends BaseMapper<AnbiaoSparePersonApplyForAudit> {

	List<AnbiaoSparePersonApplyForAudit> selectAllPage(AnbiaoSparePersonApplyForAuditPage anbiaoSparePersonApplyForAuditPage);
	int selectAllTotal(AnbiaoSparePersonApplyForAuditPage anbiaoSparePersonApplyForAuditPage);

	AnbiaoSparePersonApplyForAudit selectMaxXuhao(String deptId);

}
