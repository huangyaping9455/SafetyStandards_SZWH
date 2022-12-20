package org.springblade.anbiao.jiashiyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuan;
import org.springblade.anbiao.jiashiyuan.mapper.AnbiaoCheliangJiashiyuanMapper;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanVehiclePage;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoCheliangJiashiyuanService;
import org.springblade.anbiao.jiashiyuan.vo.CheliangJiashiyuanVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lmh
 * @since 2022-11-08
 */
@Service
@AllArgsConstructor
public class AnbiaoCheliangJiashiyuanServiceImpl extends ServiceImpl<AnbiaoCheliangJiashiyuanMapper, AnbiaoCheliangJiashiyuan> implements IAnbiaoCheliangJiashiyuanService {

	private AnbiaoCheliangJiashiyuanMapper anbiaoCheliangJiashiyuanMapper;

	@Override
	public List<CheliangJiashiyuanVO> SelectByJiashiyuanID(String shiyongxingzhi,int deptId) {
		return anbiaoCheliangJiashiyuanMapper.SelectByJiashiyuanID(shiyongxingzhi,deptId);
	}

	@Override
	public JiaShiYuanVehiclePage<CheliangJiashiyuanVO> selectPageList(JiaShiYuanVehiclePage jiaShiYuanVehiclePage) {
		Integer total = anbiaoCheliangJiashiyuanMapper.selectTotal(jiaShiYuanVehiclePage);
		Integer pagetotal = 0;
		if (jiaShiYuanVehiclePage.getSize() == 0) {
			if (jiaShiYuanVehiclePage.getTotal() == 0) {
				jiaShiYuanVehiclePage.setTotal(total);
			}
			if (jiaShiYuanVehiclePage.getTotal() == 0) {
				return jiaShiYuanVehiclePage;
			} else {
				List<CheliangJiashiyuanVO> vehlist = anbiaoCheliangJiashiyuanMapper.selectPageList(jiaShiYuanVehiclePage);
				jiaShiYuanVehiclePage.setRecords(vehlist);
				return jiaShiYuanVehiclePage;
			}
		}
		if (total > 0) {
			if (total % jiaShiYuanVehiclePage.getSize() == 0) {
				pagetotal = total / jiaShiYuanVehiclePage.getSize();
			} else {
				pagetotal = total / jiaShiYuanVehiclePage.getSize() + 1;
			}
		}
		if (pagetotal < jiaShiYuanVehiclePage.getCurrent()) {
			return jiaShiYuanVehiclePage;
		} else {
			jiaShiYuanVehiclePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (jiaShiYuanVehiclePage.getCurrent() > 1) {
				offsetNo = jiaShiYuanVehiclePage.getSize() * (jiaShiYuanVehiclePage.getCurrent() - 1);
			}
			jiaShiYuanVehiclePage.setTotal(total);
			jiaShiYuanVehiclePage.setOffsetNo(offsetNo);
			List<CheliangJiashiyuanVO> vehlist = anbiaoCheliangJiashiyuanMapper.selectPageList(jiaShiYuanVehiclePage);
			return (JiaShiYuanVehiclePage<CheliangJiashiyuanVO>) jiaShiYuanVehiclePage.setRecords(vehlist);
		}
	}

}
