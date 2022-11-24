package org.springblade.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.train.entity.Courseware;
import org.springblade.train.entity.ExerciseAnswer;
import org.springblade.train.entity.Exercises;
import org.springblade.train.entity.WaitCompletedCourse;

import java.util.HashMap;
import java.util.List;

/**
 * @Description: 待完成课程管理接口
 *
 */
public interface IWaitCompletedService extends IService<WaitCompletedCourse> {

	List<WaitCompletedCourse> getCourseList(int studentId, int isPay, int courseType, Integer courseKind,int courseId);

	List<WaitCompletedCourse> getCourseLevelList(int studentId, int isPay, int courseType, int level);

	List<Courseware> getCourseWareList(int studentId, int relUnitCourseId,int courseId);

	/**
	 * 获取练习题
	 * @param coursewareId
	 * @return
	 */
	List<Exercises> getExercisesList(int coursewareId);

	int getBeginStudyTime(int studentId, int relUnitCourseId);

	void saveBeginStudyTime(int studentId, int relUnitCourseId, String beginTime);

	void saveEndStudyTime(int studentId, int relUnitCourseId, String endTime);

	HashMap<String, Object> checkOrder(int studentId, int relUnitCourseId);

	void handler(String out_trade_no);

	List<Courseware> selectCoursewareByTime(String date);

	boolean updateCoursewareById(String sourceFile,int id);

}
