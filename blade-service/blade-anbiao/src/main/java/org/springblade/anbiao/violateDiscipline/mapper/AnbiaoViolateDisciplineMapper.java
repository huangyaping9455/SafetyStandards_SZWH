package org.springblade.anbiao.violateDiscipline.mapper;

import org.springblade.anbiao.violateDiscipline.entity.AnbiaoViolateDiscipline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.violateDiscipline.page.AnbiaoViolateDisciplinePage;

import java.util.List;

/**
 * <p>
 * 违规违纪材料上传登记 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-11-01
 */
public interface AnbiaoViolateDisciplineMapper extends BaseMapper<AnbiaoViolateDiscipline> {

	List<AnbiaoViolateDiscipline> selectALLPage(AnbiaoViolateDisciplinePage violateDisciplinePage);
	int selectALLTotal(AnbiaoViolateDisciplinePage violateDisciplinePage);

	List<AnbiaoViolateDiscipline> selectTJALLPage(AnbiaoViolateDisciplinePage violateDisciplinePage);
	int selectTJALLTotal(AnbiaoViolateDisciplinePage violateDisciplinePage);


}
