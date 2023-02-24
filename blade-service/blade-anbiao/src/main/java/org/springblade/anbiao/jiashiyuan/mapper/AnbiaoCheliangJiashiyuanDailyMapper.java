package org.springblade.anbiao.jiashiyuan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuanDaily;
import org.springblade.anbiao.jiashiyuan.page.AnbiaoCheliangJiashiyuanDailyPage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2022-11-10
 */
public interface AnbiaoCheliangJiashiyuanDailyMapper extends BaseMapper<AnbiaoCheliangJiashiyuanDaily> {


	AnbiaoCheliangJiashiyuanDaily SelectByID(String shiyongxingzhi,String jiashiyuanid);

	List<AnbiaoCheliangJiashiyuanDaily> selectPageList(AnbiaoCheliangJiashiyuanDailyPage page);
	int selectTotal(AnbiaoCheliangJiashiyuanDailyPage page);

	boolean unbundleVeh(AnbiaoCheliangJiashiyuanDaily anbiaoCheliangJiashiyuanDaily);

}
