package org.springblade.system.service;

import org.springblade.anbiao.guanlijigouherenyuan.page.PersonnelPage;
import org.springblade.anbiao.guanlijigouherenyuan.vo.PersonnelVO;
import org.springblade.system.entity.AnbiaoPersonnel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lmh
 * @since 2023-06-12
 */
public interface IAnbiaoPersonnelService extends IService<AnbiaoPersonnel> {

	PersonnelPage<PersonnelVO> selectPageList(PersonnelPage Page);

}
