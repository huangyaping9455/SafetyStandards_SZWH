package org.springblade.anbiao.shixun.mapper;

import org.springblade.anbiao.shixun.entity.AnbiaoBsPolicyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.shixun.page.BsPolicyInfoPage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2022-11-04
 */
public interface AnbiaoBsPolicyInfoMapper extends BaseMapper<AnbiaoBsPolicyInfo> {

	List<AnbiaoBsPolicyInfo> selectGetAll(BsPolicyInfoPage page);
	int selectGetAllTotal(BsPolicyInfoPage page);


}
