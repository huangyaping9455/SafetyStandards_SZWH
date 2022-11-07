package org.springblade.anbiao.shixun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.shixun.entity.AnbiaoBsPolicyInfo;
import org.springblade.anbiao.shixun.mapper.AnbiaoBsPolicyInfoMapper;
import org.springblade.anbiao.shixun.page.BsPolicyInfoPage;
import org.springblade.anbiao.shixun.service.IAnbiaoBsPolicyInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyp
 * @since 2022-11-04
 */
@Service
@AllArgsConstructor
public class AnbiaoBsPolicyInfoServiceImpl extends ServiceImpl<AnbiaoBsPolicyInfoMapper, AnbiaoBsPolicyInfo> implements IAnbiaoBsPolicyInfoService {

	private AnbiaoBsPolicyInfoMapper mapper;

	@Override
	public BsPolicyInfoPage<AnbiaoBsPolicyInfo> selectGetAll(BsPolicyInfoPage page) {
		Integer total = mapper.selectGetAllTotal(page);
		Integer pagetotal = 0;
		if (page.getSize() == 0) {
			if (page.getTotal() == 0) {
				page.setTotal(total);
			}
			if (page.getTotal() == 0) {
				return page;
			} else {
				List<AnbiaoBsPolicyInfo> bsPolicyInfoList = mapper.selectGetAll(page);
				page.setRecords(bsPolicyInfoList);
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
			List<AnbiaoBsPolicyInfo> bsPolicyInfoList = mapper.selectGetAll(page);
			page.setRecords(bsPolicyInfoList);
			return page;
		}
	}
}
