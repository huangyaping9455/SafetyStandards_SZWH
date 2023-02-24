package org.springblade.anbiao.jiashiyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuanDaily;
import org.springblade.anbiao.jiashiyuan.page.AnbiaoCheliangJiashiyuanDailyPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lmh
 * @since 2022-11-10
 */
public interface IAnbiaoCheliangJiashiyuanDailyService extends IService<AnbiaoCheliangJiashiyuanDaily> {

	AnbiaoCheliangJiashiyuanDaily SelectByID(String shiyongxingzhi,String jiashiyuanid);

	AnbiaoCheliangJiashiyuanDailyPage<AnbiaoCheliangJiashiyuanDaily> selectPageList(AnbiaoCheliangJiashiyuanDailyPage page);

	boolean unbundleVeh(AnbiaoCheliangJiashiyuanDaily anbiaoCheliangJiashiyuanDaily);

}
