package org.springblade.anbiao.jiashiyuan.mapper;

import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuanDaily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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

}
