package org.springblade.anbiao.issueAbarbeitung.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.issueAbarbeitung.VO.AnbiaoIssueAbarbeitungDeptVo;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitungDept;
import org.springblade.anbiao.issueAbarbeitung.page.AnbiaoIssueAbarbeitungPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyp
 * @since 2023-04-03
 */
public interface IAnbiaoIssueAbarbeitungDeptService extends IService<AnbiaoIssueAbarbeitungDept> {

	AnbiaoIssueAbarbeitungPage<AnbiaoIssueAbarbeitungDeptVo> selectGetAll(AnbiaoIssueAbarbeitungPage page);

}
