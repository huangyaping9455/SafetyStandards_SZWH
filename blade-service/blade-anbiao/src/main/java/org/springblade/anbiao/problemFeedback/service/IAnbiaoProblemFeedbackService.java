package org.springblade.anbiao.problemFeedback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.problemFeedback.entity.AnbiaoProblemFeedback;
import org.springblade.anbiao.problemFeedback.page.AnbiaoProblemFeedbackPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2023-08-30
 */
public interface IAnbiaoProblemFeedbackService extends IService<AnbiaoProblemFeedback> {

	AnbiaoProblemFeedbackPage<AnbiaoProblemFeedback> selectALLPage(AnbiaoProblemFeedbackPage anbiaoProblemFeedbackPage);

}
