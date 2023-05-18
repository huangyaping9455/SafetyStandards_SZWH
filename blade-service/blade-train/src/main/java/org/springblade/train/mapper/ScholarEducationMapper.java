package org.springblade.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springblade.train.entity.ScholarEducationModel;
import org.springblade.train.entity.ScholarEducationQueryModel;
import org.springblade.train.entity.TrainingListModel;
import org.springblade.train.page.ScholarEducationPage;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface ScholarEducationMapper extends BaseMapper<ScholarEducationModel> {

	/**
	 * <p>@Description: [学者学历分析列表数据查询]
	 */
	List<ScholarEducationModel> getScholarEducationList(ScholarEducationPage scholarEducationPage);
	int getScholarEducationListTotal(ScholarEducationPage scholarEducationPage);

	List<TrainingListModel> getTrainingList(Integer relUnitCourseId);

	List<Map<String, Date>> getCourseExt(Integer courseId);

	List<TrainingListModel> getTrainingList_swh(Integer relUnitCourseId);

	List<TrainingListModel> getCourseQuestion(Integer courseId);


}
