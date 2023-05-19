package org.springblade.anbiao.issueAbarbeitung.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitung;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitungDept;
import org.springblade.anbiao.issueAbarbeitung.mapper.AnbiaoIssueAbarbeitungDeptMapper;
import org.springblade.anbiao.issueAbarbeitung.mapper.AnbiaoIssueAbarbeitungMapper;
import org.springblade.anbiao.issueAbarbeitung.page.AnbiaoIssueAbarbeitungPage;
import org.springblade.anbiao.issueAbarbeitung.service.IAnbiaoIssueAbarbeitungService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyp
 * @since 2023-04-03
 */
@Service
@AllArgsConstructor
public class AnbiaoIssueAbarbeitungServiceImpl extends ServiceImpl<AnbiaoIssueAbarbeitungMapper, AnbiaoIssueAbarbeitung> implements IAnbiaoIssueAbarbeitungService {

	private AnbiaoIssueAbarbeitungMapper abarbeitungMapper;

	private AnbiaoIssueAbarbeitungDeptMapper deptMapper;

	@Override
	public AnbiaoIssueAbarbeitungPage<AnbiaoIssueAbarbeitung> selectGetAll(AnbiaoIssueAbarbeitungPage page) {
		Integer total = abarbeitungMapper.selectGetAllTotal(page);
		Integer pagetotal = 0;
		if (page.getSize() == 0) {
			if (page.getTotal() == 0) {
				page.setTotal(total);
			}
			if (page.getTotal() == 0) {
				return page;
			} else {
				List<AnbiaoIssueAbarbeitung> issueAbarbeitungList = abarbeitungMapper.selectGetAll(page);
				issueAbarbeitungList.forEach(item->{
					int ydcount = 0;
					int dcl = 0;
					int dsh = 0;
					int shwtg = 0;
					int shtg = 0;
					QueryWrapper<AnbiaoIssueAbarbeitungDept> anbiaoIssueAbarbeitungDeptQueryWrapper = new QueryWrapper<AnbiaoIssueAbarbeitungDept>();
					anbiaoIssueAbarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getIssueId,item.getId());
					List<AnbiaoIssueAbarbeitungDept> abarbeitungDeptList = deptMapper.selectList(anbiaoIssueAbarbeitungDeptQueryWrapper);
					if (abarbeitungDeptList != null && abarbeitungDeptList.size() > 0){
						for(int q=0;q<abarbeitungDeptList.size();q++){
							if(abarbeitungDeptList.get(q).getIsRead() == 1){
								ydcount += 1;
							}
							if(abarbeitungDeptList.get(q).getStatus() == 0){
								dcl += 1;
							}
							if(abarbeitungDeptList.get(q).getStatus() == 1){
								dsh += 1;
							}
							if(abarbeitungDeptList.get(q).getStatus() == 3){
								shwtg += 1;
							}
							if(abarbeitungDeptList.get(q).getStatus() == 2){
								shtg += 1;
							}
						}
					}
					//审核状态，0：待处理，1：待审核，2：审核通过，3：审核不通过
					if(shtg > 0){
						item.setStatus(2);
					}
					if(shwtg > 0){
						item.setStatus(3);
					}
					if(dsh > 0){
						item.setStatus(1);
					}
					if(dcl > 0){
						item.setStatus(0);
					}
					item.setCount(abarbeitungDeptList.size());
					item.setYdcount(ydcount);
					int wdcount = abarbeitungDeptList.size() - ydcount;
					item.setWdcount(wdcount);
				});
				if(page.getStatus() != null){
					for(int i = 0;i<issueAbarbeitungList.size();i++){
						if(!page.getStatus().equals(issueAbarbeitungList.get(i).getStatus())){
							issueAbarbeitungList.remove(i);
						}
					}
					issueAbarbeitungList.removeIf(element -> !element.getStatus().equals(page.getStatus()));
				}
				page.setRecords(issueAbarbeitungList);
				return page;
			}
		}
		if (total > 0) {
			if (total % page.getSize() == 0) {
				pagetotal = total / page.getSize();
			} else {
				pagetotal = total / page.getSize() + 1;
			}
		}
		if (pagetotal < page.getCurrent()) {
			return page;
		} else {
			page.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (page.getCurrent() > 1) {
				offsetNo = page.getSize() * (page.getCurrent() - 1);
			}
			page.setTotal(total);
			page.setOffsetNo(offsetNo);
			List<AnbiaoIssueAbarbeitung> issueAbarbeitungList = abarbeitungMapper.selectGetAll(page);
			issueAbarbeitungList.forEach(item->{
				int ydcount = 0;
				int dcl = 0;
				int dsh = 0;
				int shwtg = 0;
				int shtg = 0;
				QueryWrapper<AnbiaoIssueAbarbeitungDept> anbiaoIssueAbarbeitungDeptQueryWrapper = new QueryWrapper<AnbiaoIssueAbarbeitungDept>();
				anbiaoIssueAbarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getIssueId,item.getId());
				List<AnbiaoIssueAbarbeitungDept> abarbeitungDeptList = deptMapper.selectList(anbiaoIssueAbarbeitungDeptQueryWrapper);
				if (abarbeitungDeptList != null && abarbeitungDeptList.size() > 0){
					for(int q=0;q<abarbeitungDeptList.size();q++){
						if(abarbeitungDeptList.get(q).getIsRead() == 1){
							ydcount += 1;
						}
						if(abarbeitungDeptList.get(q).getStatus() == 0){
							dcl += 1;
						}
						if(abarbeitungDeptList.get(q).getStatus() == 1){
							dsh += 1;
						}
						if(abarbeitungDeptList.get(q).getStatus() == 3){
							shwtg += 1;
						}
						if(abarbeitungDeptList.get(q).getStatus() == 2){
							shtg += 1;
						}
					}
				}
				//审核状态，0：待处理，1：待审核，2：审核通过，3：审核不通过
				if(shtg > 0){
					item.setStatus(2);
				}
				if(shwtg > 0){
					item.setStatus(3);
				}
				if(dsh > 0){
					item.setStatus(1);
				}
				if(dcl > 0){
					item.setStatus(0);
				}
				item.setCount(abarbeitungDeptList.size());
				item.setYdcount(ydcount);
				int wdcount = abarbeitungDeptList.size() - ydcount;
				item.setWdcount(wdcount);
			});

			if(page.getStatus() != null){
				for(int i = 0;i<issueAbarbeitungList.size();i++){
					if(!page.getStatus().equals(issueAbarbeitungList.get(i).getStatus())){
						issueAbarbeitungList.remove(i);
					}
				}
				issueAbarbeitungList.removeIf(element -> !element.getStatus().equals(page.getStatus()));
			}
			page.setRecords(issueAbarbeitungList);
			return page;
		}
	}
}
