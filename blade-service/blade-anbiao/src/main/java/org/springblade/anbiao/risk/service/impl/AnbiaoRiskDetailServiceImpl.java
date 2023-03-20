package org.springblade.anbiao.risk.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.vo.BaoYangWeiXiuVO;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTraining;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.mapper.AnbiaoRiskDetailMapper;
import org.springblade.anbiao.risk.page.JiashiyuanRiskAllPage;
import org.springblade.anbiao.risk.page.RiskDeptConfigurationPage;
import org.springblade.anbiao.risk.page.RiskPage;
import org.springblade.anbiao.risk.page.VehicleRiskAllPage;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.anbiao.risk.vo.*;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoHiddenDanger;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoHiddenDangerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 安标风险统计信息 服务实现类
 * </p>
 *
 * @author hyp
 * @since 2022-12-06
 */
@Service
@AllArgsConstructor
public class AnbiaoRiskDetailServiceImpl extends ServiceImpl<AnbiaoRiskDetailMapper, AnbiaoRiskDetail> implements IAnbiaoRiskDetailService {

	@Autowired
	private AnbiaoRiskDetailMapper mapper;

	@Override
	public List<AnbiaoRiskDetailVO> selectByCount(String deptId, String date) {
		return mapper.selectByCount(deptId, date);
	}

	@Override
	public List<AnbiaoRiskDetailVO> selectByDateCount(String deptId, String date) {
		return mapper.selectByDateCount(deptId, date);
	}

	@Override
	public List<AnbiaoRiskDetailVO> selectByCategoryCount(String deptId, String date, String category) {
		return mapper.selectByCategoryCount(deptId, date, category);
	}

	@Override
	public List<AnbiaoRiskDetailVO> selectByCategoryMXCount(String deptId, String date, String category,String ardContent) {
		List<AnbiaoRiskDetailVO> riskDetailVOList = mapper.selectByCategoryMXCount(deptId, date, category,ardContent);
		if(riskDetailVOList.size() >= 1) {
			riskDetailVOList.forEach(item-> {
				//驾驶员资料
				if("anbiao_jiashiyuan".contains(item.getArdAssociationTable())){
					List<JiaShiYuan> jiaShiYuanList = mapper.selectMapList("anbiao_jiashiyuan","id",item.getArdAssociationValue());
					if(jiaShiYuanList.size() >= 1){
						jiaShiYuanList.forEach(jsyitem-> {
							item.setJiashiyuanxingming(jsyitem.getJiashiyuanxingming());
							item.setShoujihaoma(jsyitem.getShoujihaoma());
							item.setShenfenzhenghao(jsyitem.getShenfenzhenghao());
						});
					}
				}

				//企业资料
				if("anbiao_organization".contains(item.getArdAssociationTable())) {
					List<Organizations> organizationsList = mapper.selectOrganizationsMapList("anbiao_organization", "id", item.getArdAssociationValue());
					if (organizationsList.size() >= 1) {
						organizationsList.forEach(orgitem -> {
							item.setDeptName(orgitem.getDeptName());
						});
					}
				}

				//车辆资料
				if("anbiao_vehicle".contains(item.getArdAssociationTable())) {
					List<Vehicle> vehicleList = mapper.selectVehicleMapList("anbiao_vehicle", "id", item.getArdAssociationValue());
					if (vehicleList.size() >= 1) {
						vehicleList.forEach(vehitem -> {
							item.setCheliangpaizhao(vehitem.getCheliangpaizhao());
							item.setChepaiyanse(vehitem.getChepaiyanse());
						});
					}
				}
			});
		}
		return riskDetailVOList;
	}

	@Override
	public RiskPage<AnbiaoRiskDetailVO> selectByCategoryMXCountPage(RiskPage riskPage) {
		Integer total = mapper.selectByCategoryMXCountTotal(riskPage);
		if(riskPage.getSize()==0){
			if(riskPage.getTotal()==0){
				riskPage.setTotal(total);
			}

			List<AnbiaoRiskDetailVO> riskDetailVOList = mapper.selectByCategoryMXCountPage(riskPage);
			if(riskDetailVOList.size() >= 1) {
				riskDetailVOList.forEach(item-> {
					//驾驶员资料
					if("anbiao_jiashiyuan".contains(item.getArdAssociationTable())){
						List<JiaShiYuan> jiaShiYuanList = mapper.selectMapList("anbiao_jiashiyuan","id",item.getArdAssociationValue());
						if(jiaShiYuanList.size() >= 1){
							jiaShiYuanList.forEach(jsyitem-> {
								item.setJiashiyuanxingming(jsyitem.getJiashiyuanxingming());
								item.setShoujihaoma(jsyitem.getShoujihaoma());
								item.setShenfenzhenghao(jsyitem.getShenfenzhenghao());
							});
						}
					}

					//企业资料
					if("anbiao_organization".contains(item.getArdAssociationTable())) {
						List<Organizations> organizationsList = mapper.selectOrganizationsMapList("anbiao_organization", "id", item.getArdAssociationValue());
						if (organizationsList.size() >= 1) {
							organizationsList.forEach(orgitem -> {
								item.setDeptName(orgitem.getDeptName());
							});
						}
					}

					//车辆资料
					if("anbiao_vehicle".contains(item.getArdAssociationTable())) {
						List<Vehicle> vehicleList = mapper.selectVehicleMapList("anbiao_vehicle", "id", item.getArdAssociationValue());
						if (vehicleList.size() >= 1) {
							vehicleList.forEach(vehitem -> {
								item.setCheliangpaizhao(vehitem.getCheliangpaizhao());
								item.setChepaiyanse(vehitem.getChepaiyanse());
							});
						}
					}
				});
			}
			riskPage.setRecords(riskDetailVOList);
			return riskPage;

		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%riskPage.getSize()==0){
				pagetotal = total / riskPage.getSize();
			}else {
				pagetotal = total / riskPage.getSize() + 1;
			}
		}
		if (pagetotal >= riskPage.getCurrent()) {
			riskPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (riskPage.getCurrent() > 1) {
				offsetNo = riskPage.getSize() * (riskPage.getCurrent() - 1);
			}
			riskPage.setTotal(total);
			riskPage.setOffsetNo(offsetNo);
			List<AnbiaoRiskDetailVO> riskDetailVOList = mapper.selectByCategoryMXCountPage(riskPage);
			if(riskDetailVOList.size() >= 1) {
				riskDetailVOList.forEach(item-> {
					//驾驶员资料
					if("anbiao_jiashiyuan".contains(item.getArdAssociationTable())){
						List<JiaShiYuan> jiaShiYuanList = mapper.selectMapList("anbiao_jiashiyuan","id",item.getArdAssociationValue());
						if(jiaShiYuanList.size() >= 1){
							jiaShiYuanList.forEach(jsyitem-> {
								item.setJiashiyuanxingming(jsyitem.getJiashiyuanxingming());
								item.setShoujihaoma(jsyitem.getShoujihaoma());
								item.setShenfenzhenghao(jsyitem.getShenfenzhenghao());
							});
						}
					}

					//企业资料
					if("anbiao_organization".contains(item.getArdAssociationTable())) {
						List<Organizations> organizationsList = mapper.selectOrganizationsMapList("anbiao_organization", "id", item.getArdAssociationValue());
						if (organizationsList.size() >= 1) {
							organizationsList.forEach(orgitem -> {
								item.setDeptName(orgitem.getDeptName());
							});
						}
					}

					//车辆资料
					if("anbiao_vehicle".contains(item.getArdAssociationTable())) {
						List<Vehicle> vehicleList = mapper.selectVehicleMapList("anbiao_vehicle", "id", item.getArdAssociationValue());
						if (vehicleList.size() >= 1) {
							vehicleList.forEach(vehitem -> {
								item.setCheliangpaizhao(vehitem.getCheliangpaizhao());
								item.setChepaiyanse(vehitem.getChepaiyanse());
							});
						}
					}
				});
			}
			riskPage.setRecords(riskDetailVOList);
		}
		return riskPage;
	}

	@Override
	public List<AnbiaoSystemRiskVO> selectSystemRisk() {
		return mapper.selectSystemRisk();
	}

	@Override
	public List<LedgerDetailVO> ledgerDetail(String deptId) {
		return mapper.ledgerDetail(deptId);
	}

	@Override
	public JiashiyuanRiskAllPage<JiashiyuanRiskAllVO> selectJiashiyuanRiskAll(JiashiyuanRiskAllPage jiashiyuanRiskAllPage) {
		Integer total = mapper.selectTotal(jiashiyuanRiskAllPage);
		Integer pagetotal = 0;
		if(jiashiyuanRiskAllPage.getSize()==0){
			if(jiashiyuanRiskAllPage.getTotal()==0){
				jiashiyuanRiskAllPage.setTotal(total);
			}
			if(jiashiyuanRiskAllPage.getTotal()==0){
				return jiashiyuanRiskAllPage;
			}else {
				List<JiashiyuanRiskAllVO> jiashiyuanRiskAllVOS = mapper.selectJiashiyuanRiskAll(jiashiyuanRiskAllPage);
				jiashiyuanRiskAllPage.setRecords(jiashiyuanRiskAllVOS);
				return jiashiyuanRiskAllPage;
			}
		}
		if (total > 0) {
			if(total%jiashiyuanRiskAllPage.getSize()==0){
				pagetotal = total / jiashiyuanRiskAllPage.getSize();
			}else {
				pagetotal = total / jiashiyuanRiskAllPage.getSize() + 1;
			}
		}
		if (pagetotal < jiashiyuanRiskAllPage.getCurrent()) {
			return jiashiyuanRiskAllPage;
		} else {
			jiashiyuanRiskAllPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (jiashiyuanRiskAllPage.getCurrent() > 1) {
				offsetNo = jiashiyuanRiskAllPage.getSize() * (jiashiyuanRiskAllPage.getCurrent() - 1);
			}
			jiashiyuanRiskAllPage.setTotal(total);
			jiashiyuanRiskAllPage.setOffsetNo(offsetNo);
			List<JiashiyuanRiskAllVO> jiashiyuanRiskAllVOS = mapper.selectJiashiyuanRiskAll(jiashiyuanRiskAllPage);
			return (JiashiyuanRiskAllPage<JiashiyuanRiskAllVO>) jiashiyuanRiskAllPage.setRecords(jiashiyuanRiskAllVOS);
		}
	}

	@Override
	public List<AnbiaoJiashiyuanRuzhi> selectRuZhiRisk() {
		return mapper.selectRuZhiRisk(null);
	}

	@Override
	public List<AnbiaoJiashiyuanRuzhi> selectRuZhiRisk(String jiashiyuanId) {
		return mapper.selectRuZhiRisk(jiashiyuanId);
	}

	@Override
	public List<JiaShiYuan> selectShenFenZhengRisk() {
		return mapper.selectShenFenZhengRisk();
	}

	@Override
	public List<AnbiaoJiashiyuanJiashizheng> selectJiaShiZhengRisk() {
		return mapper.selectJiaShiZhengRisk();
	}


	@Override
	public List<AnbiaoJiashiyuanCongyezigezheng> selectCongYeZhengRisk() {
		return mapper.selectCongYeZhengRisk();
	}

	@Override
	public List<AnbiaoJiashiyuanTijian> selectTiJianRisk() {
		return mapper.selectTiJianRisk();
	}

	@Override
	public List<AnbiaoJiashiyuanGangqianpeixun> selectGangQianPeiXunRisk() {
		return mapper.selectGangQianPeiXunRisk();
	}

	@Override
	public List<AnbiaoJiashiyuanWuzezhengming> selectWuZeZhengMingRisk() {
		return mapper.selectWuZeZhengMingRisk();
	}

	@Override
	public List<AnbiaoJiashiyuanAnquanzerenshu> selectAnQuanZeRenShuRisk() {
		return mapper.selectAnQuanZeRenShuRisk();
	}

	@Override
	public List<AnbiaoJiashiyuanWeihaigaozhishu> selectWeiHaiGaoZhiShuRisk() {
		return mapper.selectWeiHaiGaoZhiShuRisk();
	}

	@Override
	public List<AnbiaoJiashiyuanLaodonghetong> selectLaoDongHeTongRisk() {
		return mapper.selectLaoDongHeTongRisk();
	}

	@Override
	public List<AnbiaoAnquanhuiyi> selectAnQuanHuiYiRisk() {
		return mapper.selectAnQuanHuiYiRisk();
	}

	@Override
	public List<AnbiaoSafetyTraining> selectAnQuanPeiXunRisk() {
		return mapper.selectAnQuanPeiXunRisk();
	}

	@Override
	public List<AnbiaoHiddenDangerVO> selectYinHuanPaiChaRisk() {
		return mapper.selectYinHuanPaiChaRisk();
	}

	@Override
	public List<BaoYangWeiXiuVO> selectWeiXiuDengJiRisk() {
		return mapper.selectWeiXiuDengJiRisk();
	}

	@Override
	public List<LaborlingquEntity> selectLaBorRisk() {
		return mapper.selectLaBorRisk();
	}

	@Override
	public List<AnbiaoCheliangJiashiyuanDaily> selectAnQuanJianChaRisk() {
		return mapper.selectAnQuanJianChaRisk();
	}

	@Override
	public List<VehicleXingshizheng> selectXingShiZhengRisk() {
		return mapper.selectXingShiZhengRisk();
	}

	@Override
	public List<VehicleDaoluyunshuzheng> selectDaoLuYunShuZhengRisk() {
		return mapper.selectDaoLuYunShuZhengRisk();
	}

	@Override
	public List<VehicleXingnengbaogao> selectXingNengBaoGaoRisk() {
		return mapper.selectXingNengBaoGaoRisk();
	}

	@Override
	public List<VehicleDengjizhengshu> selectDengJiZhengShuRisk() {
		return mapper.selectDengJiZhengShuRisk();
	}

	@Override
	public VehicleRiskAllPage<VehicleRiskAllVO> selectVehicleRiskAll(VehicleRiskAllPage vehicleRiskAllPage) {
		Integer total = mapper.selectVehicleRiskTotal(vehicleRiskAllPage);
		Integer pagetotal = 0;
		if(vehicleRiskAllPage.getSize()==0){
			if(vehicleRiskAllPage.getTotal()==0){
				vehicleRiskAllPage.setTotal(total);
			}
			if(vehicleRiskAllPage.getTotal()==0){
				return vehicleRiskAllPage;
			}else {
				List<VehicleRiskAllVO> vehicleRiskAllVOS = mapper.selectVehicleRiskAll(vehicleRiskAllPage);
				vehicleRiskAllPage.setRecords(vehicleRiskAllVOS);
				return vehicleRiskAllPage;
			}
		}
		if (total > 0) {
			if(total%vehicleRiskAllPage.getSize()==0){
				pagetotal = total / vehicleRiskAllPage.getSize();
			}else {
				pagetotal = total / vehicleRiskAllPage.getSize() + 1;
			}
		}
		if (pagetotal < vehicleRiskAllPage.getCurrent()) {
			return vehicleRiskAllPage;
		} else {
			vehicleRiskAllPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (vehicleRiskAllPage.getCurrent() > 1) {
				offsetNo = vehicleRiskAllPage.getSize() * (vehicleRiskAllPage.getCurrent() - 1);
			}
			vehicleRiskAllPage.setTotal(total);
			vehicleRiskAllPage.setOffsetNo(offsetNo);
			List<VehicleRiskAllVO> vehicleRiskAllVOS = mapper.selectVehicleRiskAll(vehicleRiskAllPage);
			return (VehicleRiskAllPage<VehicleRiskAllVO>) vehicleRiskAllPage.setRecords(vehicleRiskAllVOS);
		}
	}


}
