/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.train.entity.UnitStatisticsDetailListModel;
import org.springblade.train.entity.UnitStatisticsListModel;
import org.springblade.train.entity.UnitStatisticsSummaryModel;
import org.springblade.train.mapper.UnitStatisticsMapper;
import org.springblade.train.page.UnitStatisticsPage;
import org.springblade.train.service.IUnitStatisticsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UnitStatisticsServiceImpl extends ServiceImpl<UnitStatisticsMapper, UnitStatisticsListModel> implements IUnitStatisticsService {

    private UnitStatisticsMapper unitStatisticsMapper;

    @Override
    public UnitStatisticsSummaryModel summaryStats(String unitId) {
        return unitStatisticsMapper.summaryStats(unitId);
    }

    @Override
    public UnitStatisticsPage getUnitStatisticsList(UnitStatisticsPage unitStatisticsPage) {
        Integer total = unitStatisticsMapper.getUnitStatisticsListTotal(unitStatisticsPage);
        if(unitStatisticsPage.getSize()==0){
            if(unitStatisticsPage.getTotal()==0){
                unitStatisticsPage.setTotal(total);
            }
            if(unitStatisticsPage.getTotal()==0){
                return unitStatisticsPage;
            }else {
                List<UnitStatisticsListModel> studyRecordAppList = unitStatisticsMapper.getUnitStatisticsList(unitStatisticsPage);
                unitStatisticsPage.setRecords(studyRecordAppList);
                return unitStatisticsPage;
            }
        }
        Integer pagetotal = 0;
        if (total > 0) {
            if(total%unitStatisticsPage.getSize()==0){
                pagetotal = total / unitStatisticsPage.getSize();
            }else {
                pagetotal = total / unitStatisticsPage.getSize() + 1;
            }
        }
        if (pagetotal >= unitStatisticsPage.getCurrent()) {
            unitStatisticsPage.setPageTotal(pagetotal);
            Integer offsetNo = 0;
            if (unitStatisticsPage.getCurrent() > 1) {
                offsetNo = unitStatisticsPage.getSize() * (unitStatisticsPage.getCurrent() - 1);
            }
            unitStatisticsPage.setTotal(total);
            unitStatisticsPage.setOffsetNo(offsetNo);
            List<UnitStatisticsListModel> studyRecordAppList = unitStatisticsMapper.getUnitStatisticsList(unitStatisticsPage);
            unitStatisticsPage.setRecords(studyRecordAppList);
        }
        return unitStatisticsPage;
    }

    @Override
    public UnitStatisticsPage getUnitStatisticsDetailList(UnitStatisticsPage unitStatisticsPage) {
        Integer total = unitStatisticsMapper.getUnitStatisticsDetailListTotal(unitStatisticsPage);
        if(unitStatisticsPage.getSize()==0){
            if(unitStatisticsPage.getTotal()==0){
                unitStatisticsPage.setTotal(total);
            }
            if(unitStatisticsPage.getTotal()==0){
                return unitStatisticsPage;
            }else {
                List<UnitStatisticsDetailListModel> studyRecordAppList = unitStatisticsMapper.getUnitStatisticsDetailList(unitStatisticsPage);
                unitStatisticsPage.setRecords(studyRecordAppList);
                return unitStatisticsPage;
            }
        }
        Integer pagetotal = 0;
        if (total > 0) {
            if(total%unitStatisticsPage.getSize()==0){
                pagetotal = total / unitStatisticsPage.getSize();
            }else {
                pagetotal = total / unitStatisticsPage.getSize() + 1;
            }
            List<UnitStatisticsDetailListModel> studyRecordAppList = unitStatisticsMapper.getUnitStatisticsDetailList(unitStatisticsPage);
            unitStatisticsPage.setRecords(studyRecordAppList);
        }
        if (pagetotal >= unitStatisticsPage.getCurrent()) {
            unitStatisticsPage.setPageTotal(pagetotal);
            Integer offsetNo = 0;
            if (unitStatisticsPage.getCurrent() > 1) {
                offsetNo = unitStatisticsPage.getSize() * (unitStatisticsPage.getCurrent() - 1);
            }
            unitStatisticsPage.setTotal(total);
            unitStatisticsPage.setOffsetNo(offsetNo);
            List<UnitStatisticsDetailListModel> studyRecordAppList = unitStatisticsMapper.getUnitStatisticsDetailList(unitStatisticsPage);
            unitStatisticsPage.setRecords(studyRecordAppList);
        }
        return unitStatisticsPage;
    }
}
