package org.springblade.system.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.guanlijigouherenyuan.page.PersonnelPage;
import org.springblade.anbiao.guanlijigouherenyuan.vo.PersonnelVO;
import org.springblade.system.entity.AnbiaoPersonnel;
import org.springblade.system.mapper.AnbiaoPersonnelMapper;
import org.springblade.system.service.IAnbiaoPersonnelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lmh
 * @since 2023-06-12
 */
@Service
@AllArgsConstructor
public class AnbiaoPersonnelServiceImpl extends ServiceImpl<AnbiaoPersonnelMapper, AnbiaoPersonnel> implements IAnbiaoPersonnelService {

	private AnbiaoPersonnelMapper mapper;

	@Override
	public PersonnelPage<PersonnelVO> selectPageList(PersonnelPage Page) {
		Integer total = mapper.selectTotal(Page);
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%Page.getSize()==0){
				pagetotal = total / Page.getSize();
			}else {
				pagetotal = total / Page.getSize() + 1;
			}
		}
		if (pagetotal < Page.getCurrent()) {
			return Page;
		} else {
			Page.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (Page.getCurrent() > 1) {
				offsetNo = Page.getSize() * (Page.getCurrent() - 1);
			}
			Page.setTotal(total);
			Page.setOffsetNo(offsetNo);
			List<PersonnelVO> vehlist = mapper.selectPageList(Page);
			return (PersonnelPage<PersonnelVO>) Page.setRecords(vehlist);
		}
	}

}
