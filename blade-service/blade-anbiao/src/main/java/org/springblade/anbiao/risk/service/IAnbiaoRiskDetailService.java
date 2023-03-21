package org.springblade.anbiao.risk.service;

import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.vo.BaoYangWeiXiuVO;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTraining;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.risk.page.JiashiyuanRiskAllPage;
import org.springblade.anbiao.risk.page.RiskPage;
import org.springblade.anbiao.risk.page.VehicleRiskAllPage;
import org.springblade.anbiao.risk.vo.*;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoHiddenDanger;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoHiddenDangerVO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 安标风险统计信息 服务类
 * </p>
 *
 * @author hyp
 * @since 2022-12-06
 */
@Resource
public interface IAnbiaoRiskDetailService extends IService<AnbiaoRiskDetail> {

	List<AnbiaoRiskDetailVO> selectByCount(String deptId, String date);

	List<AnbiaoRiskDetailVO> selectByDateCount(String deptId, String date);

	List<AnbiaoRiskDetailVO> selectByCategoryCount(String deptId, String date,String category);

	List<AnbiaoRiskDetailVO> selectByCategoryMXCount(String deptId, String date,String category,String ardContent);

	RiskPage<AnbiaoRiskDetailVO> selectByCategoryMXCountPage(RiskPage riskPage);

	List<AnbiaoSystemRiskVO> selectSystemRisk();

	List<LedgerDetailVO> ledgerDetail(String deptId);

	JiashiyuanRiskAllPage<JiashiyuanRiskAllVO> selectJiashiyuanRiskAll(JiashiyuanRiskAllPage jiashiyuanRiskAllPage);

	List<AnbiaoJiashiyuanRuzhi> selectRuZhiRisk();
	List<AnbiaoJiashiyuanRuzhi> selectRuZhiRisk(String jiashiyuanId);

	List<JiaShiYuan> selectShenFenZhengRisk();
	List<JiaShiYuan> selectShenFenZhengRisk(String jiashiyuanId);

	List<AnbiaoJiashiyuanJiashizheng> selectJiaShiZhengRisk();
	List<AnbiaoJiashiyuanJiashizheng> selectJiaShiZhengRisk(String jiashiyuanId);

	List<AnbiaoJiashiyuanCongyezigezheng> selectCongYeZhengRisk();
	List<AnbiaoJiashiyuanCongyezigezheng> selectCongYeZhengRisk(String jiashiyuanId);

	List<AnbiaoJiashiyuanTijian> selectTiJianRisk();
	List<AnbiaoJiashiyuanTijian> selectTiJianRisk(String jiashiyuanId);

	List<AnbiaoJiashiyuanGangqianpeixun> selectGangQianPeiXunRisk();
	List<AnbiaoJiashiyuanGangqianpeixun> selectGangQianPeiXunRisk(String jiashiyuanId);

	List<AnbiaoJiashiyuanWuzezhengming> selectWuZeZhengMingRisk();
	List<AnbiaoJiashiyuanWuzezhengming> selectWuZeZhengMingRisk(String jiashiyuanId);

	List<AnbiaoJiashiyuanAnquanzerenshu> selectAnQuanZeRenShuRisk();
	List<AnbiaoJiashiyuanAnquanzerenshu> selectAnQuanZeRenShuRisk(String jiashiyuanId);

	List<AnbiaoJiashiyuanWeihaigaozhishu> selectWeiHaiGaoZhiShuRisk();
	List<AnbiaoJiashiyuanWeihaigaozhishu> selectWeiHaiGaoZhiShuRisk(String jiashiyuanId);

	List<AnbiaoJiashiyuanLaodonghetong> selectLaoDongHeTongRisk();
	List<AnbiaoJiashiyuanLaodonghetong> selectLaoDongHeTongRisk(String jiashiyuanId);

	List<AnbiaoAnquanhuiyi> selectAnQuanHuiYiRisk();

	List<AnbiaoSafetyTraining> selectAnQuanPeiXunRisk();

	List<AnbiaoHiddenDangerVO> selectYinHuanPaiChaRisk();

	List<BaoYangWeiXiuVO> selectWeiXiuDengJiRisk();

	List<LaborlingquEntity> selectLaBorRisk();

	List<AnbiaoCheliangJiashiyuanDaily> selectAnQuanJianChaRisk();

	List<VehicleXingshizheng> selectXingShiZhengRisk();

	List<VehicleDaoluyunshuzheng> selectDaoLuYunShuZhengRisk();

	List<VehicleXingnengbaogao> selectXingNengBaoGaoRisk();

	List<VehicleDengjizhengshu> selectDengJiZhengShuRisk();

	VehicleRiskAllPage<VehicleRiskAllVO> selectVehicleRiskAll(VehicleRiskAllPage vehicleRiskAllPage);

}
