package org.springblade.anbiao.realnameRegistration.mapper;

import org.springblade.anbiao.realnameRegistration.entity.AnbiaoRealnameRegistration;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.realnameRegistration.page.AnbiaoRealnameRegistrationPage;

import java.util.List;

/**
 * <p>
 * 乘客信息登记表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-11-01
 */
public interface AnbiaoRealnameRegistrationMapper extends BaseMapper<AnbiaoRealnameRegistration> {

	List<AnbiaoRealnameRegistration> selectALLPage(AnbiaoRealnameRegistrationPage realnameRegistrationPage);
	int selectALLTotal(AnbiaoRealnameRegistrationPage realnameRegistrationPage);

}
