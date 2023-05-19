package org.springblade.anbiao.issueAbarbeitung.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitung;
import org.springblade.anbiao.issueAbarbeitung.page.AnbiaoIssueAbarbeitungPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyp
 * @since 2023-04-03
 */
public interface IAnbiaoIssueAbarbeitungService extends IService<AnbiaoIssueAbarbeitung> {

	AnbiaoIssueAbarbeitungPage<AnbiaoIssueAbarbeitung> selectGetAll(AnbiaoIssueAbarbeitungPage page);

}
