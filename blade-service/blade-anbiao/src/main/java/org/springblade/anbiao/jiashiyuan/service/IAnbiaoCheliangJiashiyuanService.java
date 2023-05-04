package org.springblade.anbiao.jiashiyuan.service;

import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuan;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.jiashiyuan.mapper.AnbiaoCheliangJiashiyuanMapper;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanVehiclePage;
import org.springblade.anbiao.jiashiyuan.vo.CheliangJiashiyuanVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lmh
 * @since 2022-11-08
 */
@Service
public interface IAnbiaoCheliangJiashiyuanService extends IService<AnbiaoCheliangJiashiyuan>  {
	public List<CheliangJiashiyuanVO> SelectByJiashiyuanID(String shiyongxingzhi,int deptId);

	JiaShiYuanVehiclePage<CheliangJiashiyuanVO> selectPageList(JiaShiYuanVehiclePage jiaShiYuanVehiclePage);

	/**
	 * 根据企业ID获取集团性企业信息
	 * @param deptId
	 * @return
	 */
	List<CheliangJiashiyuanVO> SelectByDept(int deptId);

}
