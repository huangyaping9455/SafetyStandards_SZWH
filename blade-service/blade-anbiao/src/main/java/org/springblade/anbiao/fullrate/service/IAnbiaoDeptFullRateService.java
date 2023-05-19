package org.springblade.anbiao.fullrate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.fullrate.entity.AnbiaoDeptFullRate;
import org.springblade.anbiao.fullrate.page.DeptFullRatePage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyp
 * @since 2023-05-15
 */
public interface IAnbiaoDeptFullRateService extends IService<AnbiaoDeptFullRate> {

	DeptFullRatePage<AnbiaoDeptFullRate> selectDayTJ(DeptFullRatePage deptFullRatePage);

}
