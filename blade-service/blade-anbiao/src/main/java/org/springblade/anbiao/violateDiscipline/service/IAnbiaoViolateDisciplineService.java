package org.springblade.anbiao.violateDiscipline.service;

import org.springblade.anbiao.violateDiscipline.entity.AnbiaoViolateDiscipline;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.violateDiscipline.page.AnbiaoViolateDisciplinePage;

import java.util.List;

/**
 * <p>
 * 违规违纪材料上传登记 服务类
 * </p>
 *
 * @author author
 * @since 2023-11-01
 */
public interface IAnbiaoViolateDisciplineService extends IService<AnbiaoViolateDiscipline> {

	AnbiaoViolateDisciplinePage<AnbiaoViolateDiscipline> selectALLPage(AnbiaoViolateDisciplinePage violateDisciplinePage);

	AnbiaoViolateDisciplinePage<AnbiaoViolateDiscipline> selectTJALLPage(AnbiaoViolateDisciplinePage violateDisciplinePage);

}
