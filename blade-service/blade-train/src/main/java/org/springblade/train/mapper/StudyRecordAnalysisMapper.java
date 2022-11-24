package org.springblade.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springblade.train.entity.CourseSelectModel;
import org.springblade.train.entity.StudyRecordAnalysis;
import org.springblade.train.entity.StudyStatHoursModel;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface StudyRecordAnalysisMapper extends BaseMapper<StudyRecordAnalysis> {
	
	List<HashMap<String, Object>> getStudyStatForDay(HashMap<String, String> param);
	
	StudyStatHoursModel getStudyStatForHour(HashMap<String, String> param);

	/**
	 * 获取课程下拉框
	 * @param param
	 * @return
	 */
	List<CourseSelectModel> getCourseSelectList(HashMap<String, Object> param);
}
