package org.springblade.anbiao.fullrate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.fullrate.entity.AnbiaoDeptFullRate;
import org.springblade.anbiao.fullrate.page.DeptFullRatePage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2023-05-15
 */
public interface AnbiaoDeptFullRateMapper extends BaseMapper<AnbiaoDeptFullRate> {

	List<AnbiaoDeptFullRate> selectDayTJ(DeptFullRatePage deptFullRatePage);
	int selectDayTJotal(DeptFullRatePage deptFullRatePage);

}
