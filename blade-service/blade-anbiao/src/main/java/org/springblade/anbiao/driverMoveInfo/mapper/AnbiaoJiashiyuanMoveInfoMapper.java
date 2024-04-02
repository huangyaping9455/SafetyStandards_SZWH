package org.springblade.anbiao.driverMoveInfo.mapper;

import org.springblade.anbiao.driverMoveInfo.entity.AnbiaoJiashiyuanMoveInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanListVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-03-25
 */
public interface AnbiaoJiashiyuanMoveInfoMapper extends BaseMapper<AnbiaoJiashiyuanMoveInfo> {

	List<JiaShiYuanListVO> selectPageList(JiaShiYuanPage jiaShiYuanPage);
	int selectTotal(JiaShiYuanPage jiaShiYuanPage);

	List<JiaShiYuanListVO> selectGHCPageList(JiaShiYuanPage jiaShiYuanPage);
	int selectGHCTotal(JiaShiYuanPage jiaShiYuanPage);

}
