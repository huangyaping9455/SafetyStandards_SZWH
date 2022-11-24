package org.springblade.train.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.train.entity.*;
import org.springblade.train.mapper.StudyRecordAnalysisMapper;
import org.springblade.train.mapper.WaitCompletedMapper;
import org.springblade.train.service.IStudyRecordAnalysisService;
import org.springblade.train.service.IWaitCompletedService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class StudyRecordAnalysisServiceImpl extends ServiceImpl<StudyRecordAnalysisMapper, StudyRecordAnalysis> implements IStudyRecordAnalysisService {

	private StudyRecordAnalysisMapper studyRecordAnalysisMapper;

	@Override
	public List<HashMap<String, Object>> getStudyRecordAnalysis(HashMap<String, String> param) {
		List<HashMap<String, Object>> resultlist = studyRecordAnalysisMapper.getStudyStatForDay(param);
		return resultlist;
	}

	@Override
	public StudyStatHoursModel getStudyRecordAnalysisHour(HashMap<String, String> param) {
		StudyStatHoursModel result = studyRecordAnalysisMapper.getStudyStatForHour(param);
		if (null == result) {
			result = new StudyStatHoursModel();
		}
		return result;
	}

	@Override
	public List<CourseSelectModel> getCourseSelectList(HashMap<String, Object> param) {
		return studyRecordAnalysisMapper.getCourseSelectList(param);
	}
}
