package org.springblade.anbiao.issueAbarbeitung.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.anbiao.issueAbarbeitung.VO.AnbiaoIssueAbarbeitungDeptVo;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitung;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitungDept;
import org.springblade.anbiao.issueAbarbeitung.mapper.AnbiaoIssueAbarbeitungDeptMapper;
import org.springblade.anbiao.issueAbarbeitung.mapper.AnbiaoIssueAbarbeitungMapper;
import org.springblade.anbiao.issueAbarbeitung.page.AnbiaoIssueAbarbeitungPage;
import org.springblade.anbiao.issueAbarbeitung.service.IAnbiaoIssueAbarbeitungDeptService;
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
public class AnbiaoIssueAbarbeitungDeptServiceImpl extends ServiceImpl<AnbiaoIssueAbarbeitungDeptMapper, AnbiaoIssueAbarbeitungDept> implements IAnbiaoIssueAbarbeitungDeptService {

	private AnbiaoIssueAbarbeitungDeptMapper deptMapper;

	private AnbiaoIssueAbarbeitungMapper abarbeitungMapper;

	private IOrganizationsService iOrganizationsService;

	@Override
	public AnbiaoIssueAbarbeitungPage<AnbiaoIssueAbarbeitungDeptVo> selectGetAll(AnbiaoIssueAbarbeitungPage page) {
		Integer total = deptMapper.selectGetAllTotal(page);
		Integer pagetotal = 0;
		if (page.getSize() == 0) {
			if (page.getTotal() == 0) {
				page.setTotal(total);
			}
			if (page.getTotal() == 0) {
				return page;
			} else {
				List<AnbiaoIssueAbarbeitungDeptVo> deptList = deptMapper.selectGetAll(page);
				page.setRecords(deptList);
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
			List<AnbiaoIssueAbarbeitungDeptVo> deptList = deptMapper.selectGetAll(page);
			deptList.forEach(item->{
				QueryWrapper<AnbiaoIssueAbarbeitung> anbiaoIssueAbarbeitungQueryWrapper = new QueryWrapper<AnbiaoIssueAbarbeitung>();
				anbiaoIssueAbarbeitungQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitung::getId,item.getIssueId());
				AnbiaoIssueAbarbeitung abarbeitung = abarbeitungMapper.selectOne(anbiaoIssueAbarbeitungQueryWrapper);
				if(abarbeitung != null){
					OrganizationsVO organizations = iOrganizationsService.selectByDeptId(abarbeitung.getFasongdanweiid().toString());
					if(organizations != null){
						item.setFasongdanwei(organizations.getDeptName());
					}
				}
			});
			page.setRecords(deptList);
			return page;
		}
	}
}
