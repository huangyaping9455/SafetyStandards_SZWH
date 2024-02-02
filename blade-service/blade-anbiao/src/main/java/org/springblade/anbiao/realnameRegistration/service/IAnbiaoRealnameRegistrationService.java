package org.springblade.anbiao.realnameRegistration.service;

import org.springblade.anbiao.realnameRegistration.entity.AnbiaoRealnameRegistration;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.realnameRegistration.page.AnbiaoRealnameRegistrationPage;

import java.util.List;

/**
 * <p>
 * 乘客信息登记表 服务类
 * </p>
 *
 * @author author
 * @since 2023-11-01
 */
public interface IAnbiaoRealnameRegistrationService extends IService<AnbiaoRealnameRegistration> {

	AnbiaoRealnameRegistrationPage<AnbiaoRealnameRegistration> selectALLPage(AnbiaoRealnameRegistrationPage realnameRegistrationPage);

}
