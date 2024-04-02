package org.springblade.anbiao.driverMoveInfo.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.driverMoveInfo.entity.AnbiaoJiashiyuanMoveInfo;
import org.springblade.anbiao.driverMoveInfo.mapper.AnbiaoJiashiyuanMoveInfoMapper;
import org.springblade.anbiao.driverMoveInfo.service.IAnbiaoJiashiyuanMoveInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.anbiao.jiashiyuan.mapper.JiaShiYuanMapper;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanListVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-03-25
 */
@Service
@AllArgsConstructor
public class AnbiaoJiashiyuanMoveInfoServiceImpl extends ServiceImpl<AnbiaoJiashiyuanMoveInfoMapper, AnbiaoJiashiyuanMoveInfo> implements IAnbiaoJiashiyuanMoveInfoService {

	private AnbiaoJiashiyuanMoveInfoMapper jiaShiYuanMapper;

	@Override
	public JiaShiYuanPage<JiaShiYuanListVO> selectPageList(JiaShiYuanPage jiaShiYuanPage) {
		Integer total = jiaShiYuanMapper.selectTotal(jiaShiYuanPage);
		Integer pagetotal = 0;
		if(jiaShiYuanPage.getSize()==0){
			if(jiaShiYuanPage.getTotal()==0){
				jiaShiYuanPage.setTotal(total);
			}
			if(jiaShiYuanPage.getTotal()==0){
				return jiaShiYuanPage;
			}else {
				List<JiaShiYuanListVO> vehlist = jiaShiYuanMapper.selectPageList(jiaShiYuanPage);
				jiaShiYuanPage.setRecords(vehlist);
				return jiaShiYuanPage;
			}
		}
		if (total > 0) {
			if(total%jiaShiYuanPage.getSize()==0){
				pagetotal = total / jiaShiYuanPage.getSize();
			}else {
				pagetotal = total / jiaShiYuanPage.getSize() + 1;
			}
		}
		if (pagetotal < jiaShiYuanPage.getCurrent()) {
			return jiaShiYuanPage;
		} else {
			jiaShiYuanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (jiaShiYuanPage.getCurrent() > 1) {
				offsetNo = jiaShiYuanPage.getSize() * (jiaShiYuanPage.getCurrent() - 1);
			}
			jiaShiYuanPage.setTotal(total);
			jiaShiYuanPage.setOffsetNo(offsetNo);
			List<JiaShiYuanListVO> vehlist = jiaShiYuanMapper.selectPageList(jiaShiYuanPage);
			return (JiaShiYuanPage<JiaShiYuanListVO>) jiaShiYuanPage.setRecords(vehlist);
		}
	}

	@Override
	public JiaShiYuanPage<JiaShiYuanListVO> selectGHCPageList(JiaShiYuanPage jiaShiYuanPage) {
		Integer total = jiaShiYuanMapper.selectGHCTotal(jiaShiYuanPage);
		Integer pagetotal = 0;
		if(jiaShiYuanPage.getSize()==0){
			if(jiaShiYuanPage.getTotal()==0){
				jiaShiYuanPage.setTotal(total);
			}
			if(jiaShiYuanPage.getTotal()==0){
				return jiaShiYuanPage;
			}else {
				List<JiaShiYuanListVO> vehlist = jiaShiYuanMapper.selectGHCPageList(jiaShiYuanPage);
				jiaShiYuanPage.setRecords(vehlist);
				return jiaShiYuanPage;
			}
		}
		if (total > 0) {
			if(total%jiaShiYuanPage.getSize()==0){
				pagetotal = total / jiaShiYuanPage.getSize();
			}else {
				pagetotal = total / jiaShiYuanPage.getSize() + 1;
			}
		}
		if (pagetotal < jiaShiYuanPage.getCurrent()) {
			return jiaShiYuanPage;
		} else {
			jiaShiYuanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (jiaShiYuanPage.getCurrent() > 1) {
				offsetNo = jiaShiYuanPage.getSize() * (jiaShiYuanPage.getCurrent() - 1);
			}
			jiaShiYuanPage.setTotal(total);
			jiaShiYuanPage.setOffsetNo(offsetNo);
			List<JiaShiYuanListVO> vehlist = jiaShiYuanMapper.selectGHCPageList(jiaShiYuanPage);
			return (JiaShiYuanPage<JiaShiYuanListVO>) jiaShiYuanPage.setRecords(vehlist);
		}
	}
}
