package org.springblade.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.train.entity.Courseware;
import org.springblade.train.entity.ExerciseAnswer;
import org.springblade.train.entity.Exercises;
import org.springblade.train.entity.WaitCompletedCourse;

import java.util.HashMap;
import java.util.List;

/**
 * @Description: 待完成课程
 */
public interface WaitCompletedMapper extends BaseMapper<WaitCompletedCourse> {

	/**
	 * 根据学员ID、支付状态、课程类型、行业id获取待完成学习列表
	 * @param studentId
	 * @param isPay
	 * @param courseType
	 * @param courseKind
	 * @return
	 */
	List<WaitCompletedCourse> getCourseList(int studentId, int isPay, int courseType, Integer courseKind,int courseId);

	List<WaitCompletedCourse> getCourseLevelList(int studentId, int isPay, int courseType, int level);

	/**
	 * 获取课程相关课件
	 * @param studentId
	 * @param relUnitCourseId
	 * @return
	 */
	List<Courseware> getCourseWareList(int studentId, int relUnitCourseId,int courseId);

	/**
	 * 获取练习题
	 * @param coursewareId
	 * @return
	 */
	List<Exercises> getExercisesList(int coursewareId);

	/**
	 * 获取练习题答案
	 * @param coursewareId
	 * @return
	 */
	List<ExerciseAnswer> getExerciseAnswerList(int coursewareId);

	void saveBeginStudyTime(int studentId, int relUnitCourseId, String beginTime);

	void saveEndStudyTime(int studentId, int relUnitCourseId, String endTime);

	int getCourseDuration(int relUnitCourseId);

	int getStudyDuration(int studentId, int relUnitCourseId);

	HashMap<String, Object> checkOrder(int studentId, int relUnitCourseId);

	void handler(String orderNo);

	List<Courseware> selectCoursewareByTime(String date);

	boolean updateCoursewareById(String sourceFile,int id);

}
