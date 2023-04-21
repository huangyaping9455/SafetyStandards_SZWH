package org.springblade.doc.safetyproductionfile.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.doc.safetyproductionfile.entity.AnbiaoSafetyproductionfileNum;
import org.springblade.doc.safetyproductionfile.mapper.AnbiaoSafetyproductionfileNumMapper;
import org.springblade.doc.safetyproductionfile.page.SafetyproductionfileNumPage;
import org.springblade.doc.safetyproductionfile.service.IAnbiaoSafetyproductionfileNumService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyp
 * @since 2023-03-23
 */
@Service
@AllArgsConstructor
public class AnbiaoSafetyproductionfileNumServiceImpl extends ServiceImpl<AnbiaoSafetyproductionfileNumMapper, AnbiaoSafetyproductionfileNum> implements IAnbiaoSafetyproductionfileNumService {

	private AnbiaoSafetyproductionfileNumMapper mapper;

	@Override
	public SafetyproductionfileNumPage<AnbiaoSafetyproductionfileNum> selectTJ(SafetyproductionfileNumPage safetyproductionfileNumPage) {
		Integer total = mapper.selectTotal(safetyproductionfileNumPage);
		if(safetyproductionfileNumPage.getSize()==0){
			if(safetyproductionfileNumPage.getTotal()==0){
				safetyproductionfileNumPage.setTotal(total);
			}
			List<AnbiaoSafetyproductionfileNum> safetyproductionfileNumList = mapper.selectTJ(safetyproductionfileNumPage);
			safetyproductionfileNumPage.setRecords(safetyproductionfileNumList);
			return safetyproductionfileNumPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%safetyproductionfileNumPage.getSize()==0){
				pagetotal = total / safetyproductionfileNumPage.getSize();
			}else {
				pagetotal = total / safetyproductionfileNumPage.getSize() + 1;
			}
		}
		if (pagetotal >= safetyproductionfileNumPage.getCurrent()) {
			safetyproductionfileNumPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (safetyproductionfileNumPage.getCurrent() > 1) {
				offsetNo = safetyproductionfileNumPage.getSize() * (safetyproductionfileNumPage.getCurrent() - 1);
			}
			safetyproductionfileNumPage.setTotal(total);
			safetyproductionfileNumPage.setOffsetNo(offsetNo);
			List<AnbiaoSafetyproductionfileNum> safetyproductionfileNumList = mapper.selectTJ(safetyproductionfileNumPage);
			safetyproductionfileNumPage.setRecords(safetyproductionfileNumList);
		}
		return safetyproductionfileNumPage;
	}

	@Override
	public List<AnbiaoSafetyproductionfileNum> getDeptTree(Integer deptId) {
		return mapper.getDeptTree(deptId);
	}
}
