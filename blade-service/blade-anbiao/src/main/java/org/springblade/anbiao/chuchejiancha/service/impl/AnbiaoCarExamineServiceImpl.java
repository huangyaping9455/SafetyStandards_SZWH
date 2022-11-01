package org.springblade.anbiao.chuchejiancha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamine;
import org.springblade.anbiao.chuchejiancha.mapper.AnbiaoCarExamineMapper;
import org.springblade.anbiao.chuchejiancha.page.YinhuanpaichaXiangPage;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineService;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineVO;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@Service
@AllArgsConstructor
public class AnbiaoCarExamineServiceImpl extends ServiceImpl<AnbiaoCarExamineMapper, AnbiaoCarExamine> implements IAnbiaoCarExamineService {

	private AnbiaoCarExamineMapper mapper;

	@Override
	public List<AnbiaoCarExamineVO> getCarExamineMTree(Integer deptId,Integer Id, Integer parentId, String Name,Integer type) {
		List<AnbiaoCarExamineVO> anbiaoCarExamineVOS = mapper.getCarExamineMTree(deptId,Id,parentId,Name,type);
		return ForestNodeMerger.merge(anbiaoCarExamineVOS);
	}

	@Override
	public List<AnbiaoCarExamineVO> getCarExamineMTreeMuBan(Integer deptId) {
		return mapper.getCarExamineMTreeMuBan(deptId);
	}

	@Override
	public List<AnbiaoCarExamineVO> selectGetQYWD(Integer deptId) {
		return mapper.selectGetQYWD(deptId);
	}

	@Override
	public Integer selectMaxId() {
		return mapper.selectMaxId();
	}

	@Override
	public YinhuanpaichaXiangPage<AnbiaoCarExamineVO> selectCarExamineDeptListPage(YinhuanpaichaXiangPage chuchejianchaXiangPage) {
		Integer total = mapper.selectCarExamineDeptListTotal(chuchejianchaXiangPage);
		if(chuchejianchaXiangPage.getSize()==0){
			if(chuchejianchaXiangPage.getTotal()==0){
				chuchejianchaXiangPage.setTotal(total);
			}
			List<AnbiaoCarExamineVO> anbiaoCarExamineVOList = mapper.selectCarExamineDeptListPage(chuchejianchaXiangPage);
			chuchejianchaXiangPage.setRecords(anbiaoCarExamineVOList);
			return chuchejianchaXiangPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%chuchejianchaXiangPage.getSize()==0){
				pagetotal = total / chuchejianchaXiangPage.getSize();
			}else {
				pagetotal = total / chuchejianchaXiangPage.getSize() + 1;
			}
		}
		if (pagetotal >= chuchejianchaXiangPage.getCurrent()) {
			chuchejianchaXiangPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (chuchejianchaXiangPage.getCurrent() > 1) {
				offsetNo = chuchejianchaXiangPage.getSize() * (chuchejianchaXiangPage.getCurrent() - 1);
			}
			chuchejianchaXiangPage.setTotal(total);
			chuchejianchaXiangPage.setOffsetNo(offsetNo);
			List<AnbiaoCarExamineVO> anbiaoCarExamineVOList = mapper.selectCarExamineDeptListPage(chuchejianchaXiangPage);
			chuchejianchaXiangPage.setRecords(anbiaoCarExamineVOList);
		}
		return chuchejianchaXiangPage;
	}

	@Override
	public boolean deleteYingjiyanlian(Integer deptId) {
		return mapper.deleteYingjiyanlian(deptId);
	}

}
