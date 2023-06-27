package org.springblade.system.mapper;

import org.springblade.anbiao.guanlijigouherenyuan.page.PersonnelPage;
import org.springblade.anbiao.guanlijigouherenyuan.vo.PersonnelVO;
import org.springblade.system.entity.AnbiaoPersonnel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.system.entity.AnbiaoVehicleImg;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2023-06-12
 */
public interface AnbiaoPersonnelMapper extends BaseMapper<AnbiaoPersonnel> {

	List<PersonnelVO> selectPageList(PersonnelPage Page);

	int selectTotal(PersonnelPage Page);

	List<AnbiaoPersonnel> getByPostImgAll(String postId, String xingming);

}
