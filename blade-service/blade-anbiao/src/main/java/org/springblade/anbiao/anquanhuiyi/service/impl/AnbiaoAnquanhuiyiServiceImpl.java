package org.springblade.anbiao.anquanhuiyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.anquanhuiyi.VO.AnquanhuiyiledgerVO;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiDetail;
import org.springblade.anbiao.anquanhuiyi.mapper.AnbiaoAnquanhuiyiDetailMapper;
import org.springblade.anbiao.anquanhuiyi.mapper.AnbiaoAnquanhuiyiMapper;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 安全会议记录表 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2022-11-01
 */
@Service
@AllArgsConstructor
public class AnbiaoAnquanhuiyiServiceImpl extends ServiceImpl<AnbiaoAnquanhuiyiMapper, AnbiaoAnquanhuiyi> implements IAnbiaoAnquanhuiyiService {

	AnbiaoAnquanhuiyiMapper mapper;

	AnbiaoAnquanhuiyiDetailMapper detailMapper;

	@Override
	public AnQuanHuiYiPage<AnbiaoAnquanhuiyi> selectGetAll(AnQuanHuiYiPage anQuanHuiYiPage) {
		Integer total = mapper.selectGetAllTotal(anQuanHuiYiPage);
		Integer anQuanHuiYiPagetotal = 0;
		if (anQuanHuiYiPage.getSize() == 0) {
			if (anQuanHuiYiPage.getTotal() == 0) {
				anQuanHuiYiPage.setTotal(total);
			}
			if (anQuanHuiYiPage.getTotal() == 0) {
				return anQuanHuiYiPage;
			} else {
				List<AnbiaoAnquanhuiyi> bsPolicyInfoList = mapper.selectGetAll(anQuanHuiYiPage);
				if(bsPolicyInfoList.size() >= 1) {
					bsPolicyInfoList.forEach(item-> {
						QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
						anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds,item.getId());
						anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAddApBeingJoined,"1");
						List<AnbiaoAnquanhuiyiDetail> detail = detailMapper.selectList(anquanhuiyiDetailQueryWrapper);
						if (detail != null && detail.size() > 0){
							item.setShijicanhuirenshu(detail.size()+1);
						}
					});
				}
				anQuanHuiYiPage.setRecords(bsPolicyInfoList);
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
			List<AnbiaoAnquanhuiyi> bsPolicyInfoList = mapper.selectGetAll(anQuanHuiYiPage);
			if(bsPolicyInfoList.size() >= 1) {
				bsPolicyInfoList.forEach(item-> {
					QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
					anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds,item.getId());
					anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAddApBeingJoined,"1");
					List<AnbiaoAnquanhuiyiDetail> detail = detailMapper.selectList(anquanhuiyiDetailQueryWrapper);
					if (detail != null && detail.size() > 0){
						item.setShijicanhuirenshu(detail.size()+1);
					}
				});
			}
			anQuanHuiYiPage.setRecords(bsPolicyInfoList);
			return anQuanHuiYiPage;
		}
	}

	@Override
	public List<AnbiaoAnquanhuiyi> selectAnquanHuiYiMonth(int year,String deptId) {
		return mapper.selectAnquanHuiYiMonth(year,deptId);
	}

	@Override
	public IPage<AnquanhuiyiledgerVO> selectLedgerList(IPage<AnquanhuiyiledgerVO> page, AnquanhuiyiledgerVO anquanhuiyiledgerVO) {
		return page.setRecords(mapper.selectLedgerList(page,anquanhuiyiledgerVO));
	}
}
