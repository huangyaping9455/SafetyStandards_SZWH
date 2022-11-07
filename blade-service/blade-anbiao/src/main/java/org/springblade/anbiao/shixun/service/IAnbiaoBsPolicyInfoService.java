package org.springblade.anbiao.shixun.service;

import org.springblade.anbiao.shixun.entity.AnbiaoBsPolicyInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.shixun.page.BsPolicyInfoPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyp
 * @since 2022-11-04
 */
public interface IAnbiaoBsPolicyInfoService extends IService<AnbiaoBsPolicyInfo> {

	BsPolicyInfoPage<AnbiaoBsPolicyInfo> selectGetAll(BsPolicyInfoPage page);

}
