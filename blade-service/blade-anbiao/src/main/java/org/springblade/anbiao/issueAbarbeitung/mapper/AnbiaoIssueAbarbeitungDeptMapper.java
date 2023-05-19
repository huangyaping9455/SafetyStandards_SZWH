package org.springblade.anbiao.issueAbarbeitung.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.issueAbarbeitung.VO.AnbiaoIssueAbarbeitungDeptVo;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitungDept;
import org.springblade.anbiao.issueAbarbeitung.page.AnbiaoIssueAbarbeitungPage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2023-04-03
 */
public interface AnbiaoIssueAbarbeitungDeptMapper extends BaseMapper<AnbiaoIssueAbarbeitungDept> {

	List<AnbiaoIssueAbarbeitungDeptVo> selectGetAll(AnbiaoIssueAbarbeitungPage page);
	int selectGetAllTotal(AnbiaoIssueAbarbeitungPage page);


}
