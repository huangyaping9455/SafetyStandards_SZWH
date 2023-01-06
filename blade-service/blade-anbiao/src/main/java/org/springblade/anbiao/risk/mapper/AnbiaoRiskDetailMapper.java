package org.springblade.anbiao.risk.mapper;

import feign.Param;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.configure.entity.Configure;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.risk.page.RiskPage;
import org.springblade.anbiao.risk.vo.AnbiaoRiskDetailVO;

import java.util.List;

/**
 * <p>
 * 安标风险统计信息 Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2022-12-06
 */
public interface AnbiaoRiskDetailMapper extends BaseMapper<AnbiaoRiskDetail> {

	List<AnbiaoRiskDetailVO> selectByCount(String deptId, String date);

	List<AnbiaoRiskDetailVO> selectByDateCount(String deptId, String date);

	List<AnbiaoRiskDetailVO> selectByCategoryCount(String deptId, String date,String category);

	List<AnbiaoRiskDetailVO> selectByCategoryMXCount(String deptId, String date,String category,String ardContent);

	List<JiaShiYuan> selectMapList(@Param("ardAssociationTable") String ardAssociationTable
		, @Param("ardAssociationField") String ardAssociationField
		, @Param("ardAssociationValue") String ardAssociationValue);

	List<Organizations> selectOrganizationsMapList(@Param("ardAssociationTable") String ardAssociationTable
		, @Param("ardAssociationField") String ardAssociationField
		, @Param("ardAssociationValue") String ardAssociationValue);

	List<Vehicle> selectVehicleMapList(@Param("ardAssociationTable") String ardAssociationTable
		, @Param("ardAssociationField") String ardAssociationField
		, @Param("ardAssociationValue") String ardAssociationValue);

	List<AnbiaoRiskDetailVO> selectByCategoryMXCountPage(RiskPage riskPage);
	int selectByCategoryMXCountTotal(RiskPage riskPage);

}
