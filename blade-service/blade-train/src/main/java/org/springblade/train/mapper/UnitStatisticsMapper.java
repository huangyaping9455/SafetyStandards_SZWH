package org.springblade.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.train.entity.TrainingListModel;
import org.springblade.train.entity.UnitStatisticsDetailListModel;
import org.springblade.train.entity.UnitStatisticsListModel;
import org.springblade.train.entity.UnitStatisticsSummaryModel;
import org.springblade.train.page.UnitStatisticsPage;

import java.util.List;

/**
 * <p>ClassName: UnitStatisticsMapper
 * @author 呵呵哒
 */
public interface UnitStatisticsMapper extends BaseMapper<UnitStatisticsListModel> {

    /**
     * 汇总企业数量
     * @param unitId
     * @return
     */
    UnitStatisticsSummaryModel summaryStats(String unitId);

    /**
     * 查询企业统计分析列表
     * @param unitStatisticsPage
     * @return
     */
    List<UnitStatisticsListModel> getUnitStatisticsList(UnitStatisticsPage unitStatisticsPage);
    int getUnitStatisticsListTotal(UnitStatisticsPage unitStatisticsPage);

    /**
     * 查询企业统计分析详情列表
     * @param unitStatisticsPage
     * @return
     */
    List<UnitStatisticsDetailListModel> getUnitStatisticsDetailList(UnitStatisticsPage unitStatisticsPage);
    int getUnitStatisticsDetailListTotal(UnitStatisticsPage unitStatisticsPage);


	/**
	 * 学员学习情况统计表
	 * @param unitStatisticsPage
	 * @return
	 */
	List<TrainingListModel> getTrainingList_swh(UnitStatisticsPage unitStatisticsPage);
	int getTrainingList_swhTotal(UnitStatisticsPage unitStatisticsPage);

	/**
	 * 根据企业名称获取课程下拉列表
	 * @param deptName
	 * @return
	 */
	List<TrainingListModel> getDeptCourse(String deptName,Integer type);

}
