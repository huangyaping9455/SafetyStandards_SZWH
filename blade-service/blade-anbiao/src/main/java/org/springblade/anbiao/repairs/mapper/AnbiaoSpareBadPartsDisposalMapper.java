package org.springblade.anbiao.repairs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.repairs.entity.AnbiaoSpareBadPartsDisposal;
import org.springblade.anbiao.repairs.page.AnbiaoSpareBadPartsDisposalPage;

import java.util.List;

/**
 * <p>
 * 坏件处理记录 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
public interface AnbiaoSpareBadPartsDisposalMapper extends BaseMapper<AnbiaoSpareBadPartsDisposal> {

	List<AnbiaoSpareBadPartsDisposal> selectAllPage(AnbiaoSpareBadPartsDisposalPage AnbiaoSpareBadPartsDisposalPage);
	int selectAllTotal(AnbiaoSpareBadPartsDisposalPage AnbiaoSpareBadPartsDisposalPage);

	AnbiaoSpareBadPartsDisposal selectMaxXuhao(String deptId);

}
