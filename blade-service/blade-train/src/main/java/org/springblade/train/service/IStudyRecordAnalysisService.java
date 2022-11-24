package org.springblade.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.train.entity.CourseSelectModel;
import org.springblade.train.entity.StudyRecordAnalysis;
import org.springblade.train.entity.StudyStatHoursModel;

import java.util.HashMap;
import java.util.List;

public interface IStudyRecordAnalysisService extends IService<StudyRecordAnalysis> {

	/**
	 * 学习记录分析-日学习时长分布
	 * @param param	    查询条件包含如下
	 * <p>type		   查询类型  1=天 ,2=周,3=月,4=年</p>
	 * <p>unitIds    政府/企业/服务商Id</p>
	 * <p>courseId	   课程id</p>
	 * <p>startTime  开始时间</p>
	 * <p>endTime	   结束时间</p>
	 * @return
	 */
	List<HashMap<String, Object>> getStudyRecordAnalysis(HashMap<String, String> param);


	/**
	 * 学习记录分析-小时学习时长分布图
	 * @param param	    查询条件包含如下
	 * <p>type		   查询类型  1=天 ,2=周,3=月,4=年</p>
	 * <p>unitIds    政府/企业/服务商Id</p>
	 * <p>courseId	   课程id</p>
	 * <p>startTime  开始时间</p>
	 * <p>endTime	   结束时间</p>
	 * @return
	 */
	StudyStatHoursModel getStudyRecordAnalysisHour(HashMap<String, String> param);

	/**
	 * 获取课程下拉框
	 * @param param
	 * @return
	 */
	List<CourseSelectModel> getCourseSelectList(HashMap<String, Object> param);

}
