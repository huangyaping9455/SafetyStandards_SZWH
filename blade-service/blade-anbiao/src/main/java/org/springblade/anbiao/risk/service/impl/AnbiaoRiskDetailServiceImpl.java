package org.springblade.anbiao.risk.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanTJMX;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.mapper.AnbiaoRiskDetailMapper;
import org.springblade.anbiao.risk.page.RiskPage;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.anbiao.risk.vo.AnbiaoRiskDetailVO;
import org.springblade.anbiao.risk.vo.AnbiaoSystemRiskVO;
import org.springblade.anbiao.risk.vo.LedgerDetailVO;
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
}
