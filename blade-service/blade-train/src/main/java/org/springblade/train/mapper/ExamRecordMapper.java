package org.springblade.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.train.entity.ExamRecord;

import java.util.List;

/**
 * @Description: 考试记录
 */
public interface ExamRecordMapper extends BaseMapper<ExamRecord> {

	List<ExamRecord> getExamRecordList(Integer studyType, Integer courseId, String realName, List<Integer> unitIdList);

}
