package org.springblade.anbiao.chuchejiancha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface AnbiaoCarExamineMapper extends BaseMapper<AnbiaoCarExamine> {

	List<AnbiaoCarExamineVO> getCarExamineMTree(@Param("deptId") Integer deptId,@Param("Id") Integer Id,@Param("parentId") Integer parentId,@Param("Name") String Name,@Param("type") Integer type);

	List<AnbiaoCarExamineVO> getCarExamineMTreeMuBan(Integer deptId);

	List<AnbiaoCarExamineVO> selectGetQYWD(Integer deptId);

	Integer selectMaxId();

	List<AnbiaoCarExamineVO> selectCarExamineDeptListPage(YinhuanpaichaXiangPage chuchejianchaXiangPage);
	int selectCarExamineDeptListTotal(YinhuanpaichaXiangPage chuchejianchaXiangPage);

	boolean deleteYingjiyanlian(Integer deptId);

}
