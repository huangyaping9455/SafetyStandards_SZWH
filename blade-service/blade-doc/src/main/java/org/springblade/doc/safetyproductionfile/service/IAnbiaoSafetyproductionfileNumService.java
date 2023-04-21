package org.springblade.doc.safetyproductionfile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.doc.safetyproductionfile.entity.AnbiaoSafetyproductionfileNum;
import org.springblade.doc.safetyproductionfile.page.SafetyproductionfileNumPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyp
 * @since 2023-03-23
 */
public interface IAnbiaoSafetyproductionfileNumService extends IService<AnbiaoSafetyproductionfileNum> {

	SafetyproductionfileNumPage<AnbiaoSafetyproductionfileNum> selectTJ(SafetyproductionfileNumPage safetyproductionfileNumPage);

	List<AnbiaoSafetyproductionfileNum> getDeptTree(Integer deptId);

}
