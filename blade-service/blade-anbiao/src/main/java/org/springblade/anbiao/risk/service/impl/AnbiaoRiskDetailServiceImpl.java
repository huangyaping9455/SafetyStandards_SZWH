package org.springblade.anbiao.risk.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.mapper.AnbiaoRiskDetailMapper;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.anbiao.risk.vo.AnbiaoRiskDetailVO;
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
	public List<AnbiaoRiskDetailVO> selectByCategoryMXCount(String deptId, String date, String category) {
		List<AnbiaoRiskDetailVO> riskDetailVOList = mapper.selectByCategoryMXCount(deptId, date, category);
		if(riskDetailVOList.size() >= 1) {
			riskDetailVOList.forEach(item-> {
				//驾驶员资料
				List<JiaShiYuan> jiaShiYuanList = mapper.selectMapList(item.getArdAssociationTable(),item.getArdAssociationField(),item.getArdAssociationValue());
				if(jiaShiYuanList.size() >= 1){
					jiaShiYuanList.forEach(jsyitem-> {
						item.setJiashiyuanxingming(jsyitem.getJiashiyuanxingming());
						item.setShoujihaoma(jsyitem.getShoujihaoma());
						item.setShenfenzhenghao(jsyitem.getShenfenzhenghao());
					});
				}
				//企业资料
				List<Organizations> organizationsList = mapper.selectOrganizationsMapList(item.getArdAssociationTable(),item.getArdAssociationField(),item.getArdAssociationValue());
				if(organizationsList.size() >= 1){
					organizationsList.forEach(orgitem-> {
						item.setDeptName(orgitem.getDeptName());
					});
				}
				//车辆资料
				List<Vehicle> vehicleList = mapper.selectVehicleMapList(item.getArdAssociationTable(),item.getArdAssociationField(),item.getArdAssociationValue());
				if(vehicleList.size() >= 1){
					vehicleList.forEach(vehitem-> {
						item.setCheliangpaizhao(vehitem.getCheliangpaizhao());
						item.setChepaiyanse(vehitem.getChepaiyanse());
					});
				}
			});
		}
		return riskDetailVOList;
	}
}
