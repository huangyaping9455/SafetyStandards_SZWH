/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.train.entity.TrainingListModel;
import org.springblade.train.entity.UnitStatisticsListModel;
import org.springblade.train.entity.UnitStatisticsSummaryModel;
import org.springblade.train.page.UnitStatisticsPage;

import java.util.List;

public interface IUnitStatisticsService extends IService<UnitStatisticsListModel> {

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
    UnitStatisticsPage getUnitStatisticsList(UnitStatisticsPage unitStatisticsPage);

    /**
     * 查询企业统计分析详情列表
     * @param unitStatisticsPage
     * @return
     */
    UnitStatisticsPage getUnitStatisticsDetailList(UnitStatisticsPage unitStatisticsPage);

	/**
	 * 学员学习情况统计表
	 * @param unitStatisticsPage
	 * @return
	 */
	UnitStatisticsPage getTrainingList_swh(UnitStatisticsPage unitStatisticsPage);

	/**
	 * 根据企业名称获取课程下拉列表
	 * @param deptName
	 * @return
	 */
	List<TrainingListModel> getDeptCourse(String deptName,Integer type);

}
