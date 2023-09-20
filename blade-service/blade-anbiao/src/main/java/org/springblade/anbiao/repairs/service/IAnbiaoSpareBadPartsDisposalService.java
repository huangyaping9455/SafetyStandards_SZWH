package org.springblade.anbiao.repairs.service;

import org.springblade.anbiao.repairs.entity.AnbiaoSpareBadPartsDisposal;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.repairs.page.AnbiaoSpareBadPartsDisposalPage;

import java.util.List;

/**
 * <p>
 * 坏件处理记录 服务类
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
public interface IAnbiaoSpareBadPartsDisposalService extends IService<AnbiaoSpareBadPartsDisposal> {

	AnbiaoSpareBadPartsDisposalPage<AnbiaoSpareBadPartsDisposal> selectAllPage(AnbiaoSpareBadPartsDisposalPage AnbiaoSpareBadPartsDisposalPage);

	AnbiaoSpareBadPartsDisposal selectMaxXuhao(String deptId);

}
