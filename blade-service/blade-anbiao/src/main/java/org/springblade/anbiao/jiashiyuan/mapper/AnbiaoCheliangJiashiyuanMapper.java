package org.springblade.anbiao.jiashiyuan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuan;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanVehiclePage;
import org.springblade.anbiao.jiashiyuan.vo.CheliangJiashiyuanVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2022-11-08
 */
@Mapper
public interface AnbiaoCheliangJiashiyuanMapper extends BaseMapper<AnbiaoCheliangJiashiyuan> {
	public List<CheliangJiashiyuanVO> SelectByJiashiyuanID(String shiyongxingzhi,int deptId);

	List<CheliangJiashiyuanVO> selectPageList(JiaShiYuanVehiclePage jiaShiYuanVehiclePage);
	int selectTotal(JiaShiYuanVehiclePage jiaShiYuanVehiclePage);

	/**
	 * 根据企业ID获取集团性企业信息
	 * @param deptId
	 * @return
	 */
	List<CheliangJiashiyuanVO> SelectByDept(int deptId);

}
