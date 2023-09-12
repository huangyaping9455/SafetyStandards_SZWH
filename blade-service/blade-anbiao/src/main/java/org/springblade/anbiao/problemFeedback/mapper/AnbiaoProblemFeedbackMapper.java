package org.springblade.anbiao.problemFeedback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.problemFeedback.entity.AnbiaoProblemFeedback;
import org.springblade.anbiao.problemFeedback.page.AnbiaoProblemFeedbackPage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-08-30
 */
public interface AnbiaoProblemFeedbackMapper extends BaseMapper<AnbiaoProblemFeedback> {

	List<AnbiaoProblemFeedback> selectALLPage(AnbiaoProblemFeedbackPage anbiaoProblemFeedbackPage);
	int selectALLTotal(AnbiaoProblemFeedbackPage anbiaoProblemFeedbackPage);

}
