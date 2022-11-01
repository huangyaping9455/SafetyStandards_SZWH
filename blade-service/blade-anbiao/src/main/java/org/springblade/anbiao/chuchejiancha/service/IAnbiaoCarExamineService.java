package org.springblade.anbiao.chuchejiancha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import feign.Param;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamine;
import org.springblade.anbiao.chuchejiancha.page.YinhuanpaichaXiangPage;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
public interface IAnbiaoCarExamineService extends IService<AnbiaoCarExamine> {

	List<AnbiaoCarExamineVO> getCarExamineMTree(Integer deptId, @Param("Id") Integer Id, Integer parentId, String Name, Integer type);

	List<AnbiaoCarExamineVO> getCarExamineMTreeMuBan(Integer deptId);

	List<AnbiaoCarExamineVO> selectGetQYWD(Integer deptId);

	Integer selectMaxId();

	YinhuanpaichaXiangPage<AnbiaoCarExamineVO> selectCarExamineDeptListPage(YinhuanpaichaXiangPage chuchejianchaXiangPage);

	boolean deleteYingjiyanlian(Integer deptId);

}
