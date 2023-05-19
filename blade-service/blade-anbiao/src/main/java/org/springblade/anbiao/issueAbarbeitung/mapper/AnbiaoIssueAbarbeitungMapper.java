package org.springblade.anbiao.issueAbarbeitung.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitung;
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
public interface AnbiaoIssueAbarbeitungMapper extends BaseMapper<AnbiaoIssueAbarbeitung> {

	List<AnbiaoIssueAbarbeitung> selectGetAll(AnbiaoIssueAbarbeitungPage page);
	int selectGetAllTotal(AnbiaoIssueAbarbeitungPage page);

}
