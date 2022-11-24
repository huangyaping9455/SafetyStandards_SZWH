package org.springblade.train.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.train.entity.Courseware;
import org.springblade.train.entity.ExerciseAnswer;
import org.springblade.train.entity.Exercises;
import org.springblade.train.entity.WaitCompletedCourse;
import org.springblade.train.mapper.WaitCompletedMapper;
import org.springblade.train.service.IWaitCompletedService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Description: 待完成课程管理实现类
 */
@Service
@AllArgsConstructor
public class WaitCompletedServiceImpl extends ServiceImpl<WaitCompletedMapper, WaitCompletedCourse> implements IWaitCompletedService {

	private WaitCompletedMapper waitCompletedMapper;

	@Override
	public List<WaitCompletedCourse> getCourseList(int studentId, int isPay, int courseType, Integer courseKind, int courseId) {
		return waitCompletedMapper.getCourseList(studentId,isPay,courseType,courseKind,courseId);
	}

	@Override
	public List<WaitCompletedCourse> getCourseLevelList(int studentId, int isPay, int courseType, int level) {
		return waitCompletedMapper.getCourseLevelList(studentId,isPay,courseType,level);
	}

	@Override
	public List<Courseware> getCourseWareList(int studentId, int relUnitCourseId,int courseId) {
		return waitCompletedMapper.getCourseWareList(studentId, relUnitCourseId,courseId);
	}

	@Override
	public List<Exercises> getExercisesList(int coursewareId) {
		List<Exercises> exercisesList = waitCompletedMapper.getExercisesList(coursewareId);
		if(exercisesList != null){
			exercisesList.forEach(item->{
				List<ExerciseAnswer> exerciseAnswerList = waitCompletedMapper.getExerciseAnswerList(item.getId());
				if(exerciseAnswerList != null){
					item.setExerciseAnswerList(exerciseAnswerList);
				}else{
					item.setExerciseAnswerList(null);
				}
			});
		}
		return exercisesList;
	}

	@Override
	public int getBeginStudyTime(int studentId, int relUnitCourseId) {
		return waitCompletedMapper.getStudyDuration(studentId, relUnitCourseId);
	}

	@Override
	@Async
	public void saveEndStudyTime(int studentId, int relUnitCourseId, String endTime) {
		int courseDuration = waitCompletedMapper.getCourseDuration(relUnitCourseId);
		int studyDuration = waitCompletedMapper.getStudyDuration(studentId, relUnitCourseId);

		if(studyDuration >= courseDuration) {
			waitCompletedMapper.saveEndStudyTime(studentId,relUnitCourseId,endTime);
			// 是继续教育平台的，在学习完成后，将数据移到另一个表中
//			if (config.isContinueLearning()){
//				Student student = studentService.getStudentById(studentId);
//
//
//			}
		}
	}

	@Override
	@Async
	public void saveBeginStudyTime(int studentId, int relUnitCourseId, String beginTime) {
		waitCompletedMapper.saveBeginStudyTime(studentId,relUnitCourseId,beginTime);
	}

	@Override
	public HashMap<String, Object> checkOrder(int studentId, int relUnitCourseId) {
		return waitCompletedMapper.checkOrder(studentId, relUnitCourseId);
	}

	@Override
	public void handler(String orderNo) {
		waitCompletedMapper.handler(orderNo);
	}

	@Override
	public List<Courseware> selectCoursewareByTime(String date) {
		return waitCompletedMapper.selectCoursewareByTime(date);
	}

	@Override
	public boolean updateCoursewareById(String sourceFile, int id) {
		return waitCompletedMapper.updateCoursewareById(sourceFile, id);
	}

}
