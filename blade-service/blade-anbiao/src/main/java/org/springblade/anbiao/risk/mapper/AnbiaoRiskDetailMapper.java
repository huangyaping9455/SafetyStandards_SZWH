package org.springblade.anbiao.risk.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.vo.BaoYangWeiXiuVO;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTraining;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.page.JiashiyuanRiskAllPage;
import org.springblade.anbiao.risk.page.RiskPage;
import org.springblade.anbiao.risk.page.VehicleRiskAllPage;
import org.springblade.anbiao.risk.vo.*;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoHiddenDangerVO;

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

	List<AnbiaoSystemRiskVO> selectSystemRisk();

	List<LedgerDetailVO> ledgerDetail(@Param("deptId") String deptId);

	List<JiashiyuanRiskAllVO> selectJiashiyuanRiskAll(JiashiyuanRiskAllPage jiashiyuanRiskAllPage);
	int selectTotal(JiashiyuanRiskAllPage jiashiyuanRiskAllPage);

//	List<AnbiaoJiashiyuanRuzhi> selectRuZhiRisk();

	List<AnbiaoJiashiyuanRuzhi> selectRuZhiRisk(@Param("jiashiyuanId") String jiashiyuanId);

	List<JiaShiYuan> selectShenFenZhengRisk(@Param("jiashiyuanId") String jiashiyuanId);

	List<AnbiaoJiashiyuanJiashizheng> selectJiaShiZhengRisk(@Param("jiashiyuanId") String jiashiyuanId);

	List<AnbiaoJiashiyuanCongyezigezheng> selectCongYeZhengRisk(@Param("jiashiyuanId") String jiashiyuanId);

	List<AnbiaoJiashiyuanTijian> selectTiJianRisk(@Param("jiashiyuanId") String jiashiyuanId);

	List<AnbiaoJiashiyuanGangqianpeixun> selectGangQianPeiXunRisk(@Param("jiashiyuanId") String jiashiyuanId);

	List<AnbiaoJiashiyuanWuzezhengming> selectWuZeZhengMingRisk(@Param("jiashiyuanId") String jiashiyuanId);

	List<AnbiaoJiashiyuanAnquanzerenshu> selectAnQuanZeRenShuRisk(@Param("jiashiyuanId") String jiashiyuanId);

	List<AnbiaoJiashiyuanWeihaigaozhishu> selectWeiHaiGaoZhiShuRisk(@Param("jiashiyuanId") String jiashiyuanId);

	List<AnbiaoJiashiyuanLaodonghetong> selectLaoDongHeTongRisk(@Param("jiashiyuanId") String jiashiyuanId);

	List<AnbiaoAnquanhuiyi> selectAnQuanHuiYiRisk();

	List<AnbiaoSafetyTraining> selectAnQuanPeiXunRisk();

	List<AnbiaoHiddenDangerVO> selectYinHuanPaiChaRisk();

	@SqlParser(filter = true)
	List<BaoYangWeiXiuVO> selectWeiXiuDengJiRisk();

	@SqlParser(filter = true)
	List<LaborlingquEntity> selectLaBorRisk();

	List<AnbiaoCheliangJiashiyuanDaily> selectAnQuanJianChaRisk();

	List<VehicleXingshizheng> selectXingShiZhengRisk(@Param("vehicleId") String vehicleId);

	List<VehicleDaoluyunshuzheng> selectDaoLuYunShuZhengRisk(@Param("vehicleId") String vehicleId);

	List<VehicleXingnengbaogao> selectXingNengBaoGaoRisk(@Param("vehicleId") String vehicleId);

	List<VehicleDengjizhengshu> selectDengJiZhengShuRisk(@Param("vehicleId") String vehicleId);

	List<VehicleRiskAllVO> selectVehicleRiskAll(VehicleRiskAllPage vehicleRiskAllPage);
	int selectVehicleRiskTotal(VehicleRiskAllPage vehicleRiskAllPage);

	List<JiaShiYuan> selectJiaShiYuanBaoXianRisk(@Param("jiashiyuanId") String jiashiyuanId);

	List<Vehicle> selectVehicleBaoXianRisk(@Param("vehicleId") String vehicleId);

}
