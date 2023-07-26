package org.springblade.anbiao.deptrisk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.deptrisk.entity.DeptRisk;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyp
 * @since 2023-05-15
 */
public interface IDeptRiskService extends IService<DeptRisk> {

	DeptRisk selectDeptNum(Integer deptId);

	DeptRisk selectDeptRiskNum(Integer deptId);

	List<DeptRisk> selectDeptListRiskNum(Integer deptId);

	List<DeptRisk> selectDeptAvg(Integer deptId);

	DeptRisk selectJsyRiskCount(Integer deptId);

	DeptRisk selectClRiskCount(Integer deptId);

	List<DeptRisk> selectRiskTendency(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptId") Integer deptId);


}
