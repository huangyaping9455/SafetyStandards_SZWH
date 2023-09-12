package org.springblade.anbiao.problemFeedback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.problemFeedback.entity.AnbiaoProblemFeedback;
import org.springblade.anbiao.problemFeedback.mapper.AnbiaoProblemFeedbackMapper;
import org.springblade.anbiao.problemFeedback.page.AnbiaoProblemFeedbackPage;
import org.springblade.anbiao.problemFeedback.service.IAnbiaoProblemFeedbackService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2023-08-30
 */
@Service
@AllArgsConstructor
public class AnbiaoProblemFeedbackServiceImpl extends ServiceImpl<AnbiaoProblemFeedbackMapper, AnbiaoProblemFeedback> implements IAnbiaoProblemFeedbackService {

	private AnbiaoProblemFeedbackMapper mapper;

	@Override
	public AnbiaoProblemFeedbackPage<AnbiaoProblemFeedback> selectALLPage(AnbiaoProblemFeedbackPage anbiaoProblemFeedbackPage) {
		Integer total = mapper.selectALLTotal(anbiaoProblemFeedbackPage);
		if(anbiaoProblemFeedbackPage.getSize()==0){
			if(anbiaoProblemFeedbackPage.getTotal()==0){
				anbiaoProblemFeedbackPage.setTotal(total);
			}

			if(anbiaoProblemFeedbackPage.getTotal()==0){
				return anbiaoProblemFeedbackPage;
			}else{
				List<AnbiaoProblemFeedback> repairsInfos = mapper.selectALLPage(anbiaoProblemFeedbackPage);
				anbiaoProblemFeedbackPage.setRecords(repairsInfos);
				return anbiaoProblemFeedbackPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoProblemFeedbackPage.getSize()==0){
				pagetotal = total / anbiaoProblemFeedbackPage.getSize();
			}else {
				pagetotal = total / anbiaoProblemFeedbackPage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoProblemFeedbackPage.getCurrent()) {
			anbiaoProblemFeedbackPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoProblemFeedbackPage.getCurrent() > 1) {
				offsetNo = anbiaoProblemFeedbackPage.getSize() * (anbiaoProblemFeedbackPage.getCurrent() - 1);
			}
			anbiaoProblemFeedbackPage.setTotal(total);
			anbiaoProblemFeedbackPage.setOffsetNo(offsetNo);
			List<AnbiaoProblemFeedback> repairsInfos = mapper.selectALLPage(anbiaoProblemFeedbackPage);
			anbiaoProblemFeedbackPage.setRecords(repairsInfos);
		}
		return anbiaoProblemFeedbackPage;
	}
}
