package org.springblade.anbiao.deptUserWecat.mapper;

import org.springblade.anbiao.deptUserWecat.entity.AnbiaoDeptUserWechatInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-06-12
 */
public interface AnbiaoDeptUserWechatInfoMapper extends BaseMapper<AnbiaoDeptUserWechatInfo> {

	AnbiaoDeptUserWechatInfo selectByUser(String yhId);

}
