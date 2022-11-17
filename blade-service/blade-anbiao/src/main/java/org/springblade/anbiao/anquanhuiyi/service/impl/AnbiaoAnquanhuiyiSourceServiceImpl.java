package org.springblade.anbiao.anquanhuiyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiSource;
import org.springblade.anbiao.anquanhuiyi.mapper.AnbiaoAnquanhuiyiSourceMapper;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiSourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyp
 * @since 2022-11-17
 */
@Service
@AllArgsConstructor
public class AnbiaoAnquanhuiyiSourceServiceImpl extends ServiceImpl<AnbiaoAnquanhuiyiSourceMapper, AnbiaoAnquanhuiyiSource> implements IAnbiaoAnquanhuiyiSourceService {

	private AnbiaoAnquanhuiyiSourceMapper mapper;

	@Override
	public AnQuanHuiYiPage<AnbiaoAnquanhuiyiSource> selectGetAll(AnQuanHuiYiPage anQuanHuiYiPage) {
		Integer total = mapper.selectGetAllTotal(anQuanHuiYiPage);
		Integer anQuanHuiYiPagetotal = 0;
		if (anQuanHuiYiPage.getSize() == 0) {
			if (anQuanHuiYiPage.getTotal() == 0) {
				anQuanHuiYiPage.setTotal(total);
			}
			if (anQuanHuiYiPage.getTotal() == 0) {
				return anQuanHuiYiPage;
			} else {
				List<AnbiaoAnquanhuiyiSource> anquanhuiyiSourceList = mapper.selectGetAll(anQuanHuiYiPage);
				anQuanHuiYiPage.setRecords(anquanhuiyiSourceList);
				return anQuanHuiYiPage;
			}
		}
		if (total > 0) {
			if (total % anQuanHuiYiPage.getSize() == 0) {
				anQuanHuiYiPagetotal = total / anQuanHuiYiPage.getSize();
			} else {
				anQuanHuiYiPagetotal = total / anQuanHuiYiPage.getSize() + 1;
			}
		}
		if (anQuanHuiYiPagetotal < anQuanHuiYiPage.getCurrent()) {
			return anQuanHuiYiPage;
		} else {
			anQuanHuiYiPage.setPageTotal(anQuanHuiYiPagetotal);
			Integer offsetNo = 0;
			if (anQuanHuiYiPage.getCurrent() > 1) {
				offsetNo = anQuanHuiYiPage.getSize() * (anQuanHuiYiPage.getCurrent() - 1);
			}
			anQuanHuiYiPage.setTotal(total);
			anQuanHuiYiPage.setOffsetNo(offsetNo);
			List<AnbiaoAnquanhuiyiSource> anquanhuiyiSourceList = mapper.selectGetAll(anQuanHuiYiPage);
			anQuanHuiYiPage.setRecords(anquanhuiyiSourceList);
			return anQuanHuiYiPage;
		}
	}
}
