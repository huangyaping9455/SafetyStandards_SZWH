package org.springblade.doc.safetyproductionfile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.doc.safetyproductionfile.entity.AnbiaoSafetyproductionfileNum;
import org.springblade.doc.safetyproductionfile.page.SafetyproductionfileNumPage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2023-03-23
 */
public interface AnbiaoSafetyproductionfileNumMapper extends BaseMapper<AnbiaoSafetyproductionfileNum> {

	List<AnbiaoSafetyproductionfileNum> selectTJ(SafetyproductionfileNumPage safetyproductionfileNumPage);
	int selectTotal(SafetyproductionfileNumPage safetyproductionfileNumPage);

	List<AnbiaoSafetyproductionfileNum> getDeptTree(Integer deptId);

}
