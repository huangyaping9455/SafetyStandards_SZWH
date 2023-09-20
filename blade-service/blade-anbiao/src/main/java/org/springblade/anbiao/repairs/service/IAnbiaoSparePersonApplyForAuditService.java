package org.springblade.anbiao.repairs.service;

import org.springblade.anbiao.repairs.entity.AnbiaoSparePersonApplyForAudit;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.repairs.page.AnbiaoSparePersonApplyForAuditPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
public interface IAnbiaoSparePersonApplyForAuditService extends IService<AnbiaoSparePersonApplyForAudit> {

	AnbiaoSparePersonApplyForAuditPage<AnbiaoSparePersonApplyForAudit> selectAllPage(AnbiaoSparePersonApplyForAuditPage anbiaoSparePersonApplyForAuditPage);

	AnbiaoSparePersonApplyForAudit selectMaxXuhao(String deptId);

}
