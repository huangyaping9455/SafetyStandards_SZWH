package org.springblade.anbiao.risk.service;

import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.risk.page.JiashiyuanRiskAllPage;
import org.springblade.anbiao.risk.page.RiskPage;
import org.springblade.anbiao.risk.vo.AnbiaoRiskDetailVO;
import org.springblade.anbiao.risk.vo.AnbiaoSystemRiskVO;
import org.springblade.anbiao.risk.vo.JiashiyuanRiskAllVO;
import org.springblade.anbiao.risk.vo.LedgerDetailVO;

import java.util.List;

/**
 * <p>
 * 安标风险统计信息 服务类
 * </p>
 *
 * @author hyp
 * @since 2022-12-06
 */
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

	List<JiaShiYuan> selectShenFenZhengRisk();

	List<AnbiaoJiashiyuanJiashizheng> selectJiaShiZhengRisk();

	List<AnbiaoJiashiyuanCongyezigezheng> selectCongYeZhengRisk();

	List<AnbiaoJiashiyuanTijian> selectTiJianRisk();

	List<AnbiaoJiashiyuanGangqianpeixun> selectGangQianPeiXunRisk();

	List<AnbiaoJiashiyuanWuzezhengming> selectWuZeZhengMingRisk();

	List<AnbiaoJiashiyuanAnquanzerenshu> selectAnQuanZeRenShuRisk();

	List<AnbiaoJiashiyuanWeihaigaozhishu> selectWeiHaiGaoZhiShuRisk();

	List<AnbiaoJiashiyuanLaodonghetong> selectLaoDongHeTongRisk();
}
