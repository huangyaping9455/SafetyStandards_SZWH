package org.springblade.anbiao.deptUserWecat.service;

import io.swagger.models.auth.In;
import org.springblade.anbiao.deptUserWecat.entity.AnbiaoDeptUserWechatInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-06-12
 */
public interface IAnbiaoDeptUserWechatInfoService extends IService<AnbiaoDeptUserWechatInfo> {

	void bindWechatOpenId(String yhId, String openid, Integer status, Integer type);

	AnbiaoDeptUserWechatInfo selectByUser(String yhId);


}
