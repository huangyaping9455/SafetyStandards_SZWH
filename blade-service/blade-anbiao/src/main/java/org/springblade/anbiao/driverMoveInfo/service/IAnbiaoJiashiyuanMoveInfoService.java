package org.springblade.anbiao.driverMoveInfo.service;

import org.springblade.anbiao.driverMoveInfo.entity.AnbiaoJiashiyuanMoveInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-03-25
 */
public interface IAnbiaoJiashiyuanMoveInfoService extends IService<AnbiaoJiashiyuanMoveInfo> {

	JiaShiYuanPage<JiaShiYuanListVO> selectPageList(JiaShiYuanPage jiaShiYuanPage);

	JiaShiYuanPage<JiaShiYuanListVO> selectGHCPageList(JiaShiYuanPage jiaShiYuanPage);

}
