/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.train.entity.UnitStatisticsListModel;
import org.springblade.train.entity.UnitStatisticsSummaryModel;
import org.springblade.train.page.UnitStatisticsPage;

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

}
