package org.springblade.anbiao.realnameRegistration.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.realnameRegistration.entity.AnbiaoRealnameRegistration;
import org.springblade.anbiao.realnameRegistration.mapper.AnbiaoRealnameRegistrationMapper;
import org.springblade.anbiao.realnameRegistration.page.AnbiaoRealnameRegistrationPage;
import org.springblade.anbiao.realnameRegistration.service.IAnbiaoRealnameRegistrationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 乘客信息登记表 服务实现类
 * </p>
 *
 * @author author
 * @since 2023-11-01
 */
@Service
@AllArgsConstructor
public class AnbiaoRealnameRegistrationServiceImpl extends ServiceImpl<AnbiaoRealnameRegistrationMapper, AnbiaoRealnameRegistration> implements IAnbiaoRealnameRegistrationService {

	private AnbiaoRealnameRegistrationMapper realnameRegistrationMapper;

	@Override
	public AnbiaoRealnameRegistrationPage<AnbiaoRealnameRegistration> selectALLPage(AnbiaoRealnameRegistrationPage realnameRegistrationPage) {
		Integer total = realnameRegistrationMapper.selectALLTotal(realnameRegistrationPage);
		if(realnameRegistrationPage.getSize()==0){
			if(realnameRegistrationPage.getTotal()==0){
				realnameRegistrationPage.setTotal(total);
			}

			if(realnameRegistrationPage.getTotal()==0){
				return realnameRegistrationPage;
			}else{
				List<AnbiaoRealnameRegistration> realnameRegistrations = realnameRegistrationMapper.selectALLPage(realnameRegistrationPage);
				realnameRegistrationPage.setRecords(realnameRegistrations);
				return realnameRegistrationPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%realnameRegistrationPage.getSize()==0){
				pagetotal = total / realnameRegistrationPage.getSize();
			}else {
				pagetotal = total / realnameRegistrationPage.getSize() + 1;
			}
		}
		if (pagetotal >= realnameRegistrationPage.getCurrent()) {
			realnameRegistrationPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (realnameRegistrationPage.getCurrent() > 1) {
				offsetNo = realnameRegistrationPage.getSize() * (realnameRegistrationPage.getCurrent() - 1);
			}
			realnameRegistrationPage.setTotal(total);
			realnameRegistrationPage.setOffsetNo(offsetNo);
			List<AnbiaoRealnameRegistration> realnameRegistrations = realnameRegistrationMapper.selectALLPage(realnameRegistrationPage);
			realnameRegistrationPage.setRecords(realnameRegistrations);
		}
		return realnameRegistrationPage;
	}
}
