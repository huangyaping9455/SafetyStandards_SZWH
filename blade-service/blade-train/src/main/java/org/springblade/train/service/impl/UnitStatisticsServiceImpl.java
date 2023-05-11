/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.tool.StringUtil;
import org.springblade.common.tool.StringUtils;
import org.springblade.train.entity.*;
import org.springblade.train.mapper.*;
import org.springblade.train.page.UnitStatisticsPage;
import org.springblade.train.service.IUnitStatisticsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class UnitStatisticsServiceImpl extends ServiceImpl<UnitStatisticsMapper, UnitStatisticsListModel> implements IUnitStatisticsService {

    private UnitStatisticsMapper unitStatisticsMapper;

	private CoursewareMapper coursewareMapper;

	private StudyRecordMapper studyRecordMapper;

	private UnitMapper unitMapper;

	private ScholarEducationMapper scholarEducationMapper;

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

	@Override
	public UnitStatisticsPage getTrainingList_swh(UnitStatisticsPage unitStatisticsPage) {
		Integer total = unitStatisticsMapper.getTrainingList_swhTotal(unitStatisticsPage);
		if(unitStatisticsPage.getSize()==0){
			if(unitStatisticsPage.getTotal()==0){
				unitStatisticsPage.setTotal(total);
			}
			if(unitStatisticsPage.getTotal()==0){
				return unitStatisticsPage;
			}else {
				List<TrainingListModel> trainingListModelList = unitStatisticsMapper.getTrainingList_swh(unitStatisticsPage);
				trainingListModelList.forEach(item->{
					Unit unit = unitMapper.selectById(unitStatisticsPage.getUnitId());
					item.setUnitName(unit.getFullName());
					Integer courseId = unitStatisticsPage.getRelUnitCourseId();
					List<Map<String, Date>> courseExt = scholarEducationMapper.getCourseExt(courseId);
					item.setStudyBeginTime(courseExt.get(0).get("beginTime").toString());
					item.setStudyEndTime(courseExt.get(0).get("endTime").toString());
					String courseName = String.valueOf(courseExt.get(0).get("name"));
					item.setCourseName(courseName);
					item.setDuration(Integer.parseInt(String.valueOf(courseExt.get(0).get("duration"))));
					// 课件信息
					HashMap<Integer, Courseware> coursewareMap = new HashMap<>();
					QueryWrapper<Courseware> coursewareWrapper = new QueryWrapper<>();
					List<Courseware> courseList = coursewareMapper.selectList(coursewareWrapper);
					if (courseList != null && courseList.size() > 0) {
						for (Courseware courseware : courseList) {
							coursewareMap.put(courseware.getId(), courseware);
						}
					}
					// 学习记录
					List<StudyRecord> studyRecordList = studyRecordMapper.selectList(new LambdaQueryWrapper<StudyRecord>()
						.eq(StudyRecord::getStudentId, item.getStudentId())
						.in(StudyRecord::getRelUnitCourseId, unitStatisticsPage.getRelUnitCourseId()));

					Integer studyTimeCompletion = 0;
					// 课程列表数据组装
					if (studyRecordList != null && studyRecordList.size() > 0) {
						Long studyTimeTotal = 0L;

						StudyRecord studyRecordFirst = studyRecordList.get(0);

						for (StudyRecord studyRecord : studyRecordList) {
							// 组装课件名称
							Courseware courseware = coursewareMap.get(studyRecord.getCoursewareId());
							if (courseware != null) {
								studyRecord.setCourseware(courseware);
							} else {
								log.info("结业证明-组装课件名称为空");
							}
							studyTimeCompletion += studyRecord.getPlayProgress();
							studyTimeTotal += studyRecord.getDuration();
							studyRecord.setStudyTimeCompletion(studyTimeCompletion);
							Double rate = (studyRecord.getStudyTimeCompletion()*1.0/item.getDuration())*100;
							studyRecord.setStudyProgress(String.format("%.2f",rate)+"%");
						}
					}
//				item.setStudyRecord(studyRecordList);
					item.setStudyTimeCompletion(studyTimeCompletion);
					Double rate = (item.getStudyTimeCompletion()*1.0/item.getDuration())*100;
					if(rate >= 100){
						item.setStudyProgress("100%");
					}else{
						item.setStudyProgress(String.format("%.2f",rate)+"%");
					}
					if(StringUtils.isNotEmpty(item.getSignatrueimg()) || StringUtils.isNotEmpty(item.getStuDuration())){
						item.setIsSignatrue("已签到");
					}else{
						item.setIsSignatrue("未签到");
					}
				});
				unitStatisticsPage.setRecords(trainingListModelList);
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
			List<TrainingListModel> trainingListModelList = unitStatisticsMapper.getTrainingList_swh(unitStatisticsPage);
			trainingListModelList.forEach(item->{
				Unit unit = unitMapper.selectById(unitStatisticsPage.getUnitId());
				item.setUnitName(unit.getFullName());
				Integer courseId = unitStatisticsPage.getRelUnitCourseId();
				List<Map<String, Date>> courseExt = scholarEducationMapper.getCourseExt(courseId);
				item.setStudyBeginTime(courseExt.get(0).get("beginTime").toString());
				item.setStudyEndTime(courseExt.get(0).get("endTime").toString());
				item.setStudyTime(item.getStudyBeginTime().substring(0,10)+"至"+item.getStudyEndTime().substring(0,10));
				String courseName = String.valueOf(courseExt.get(0).get("name"));
				item.setCourseName(courseName);
				item.setDuration(Integer.parseInt(String.valueOf(courseExt.get(0).get("duration"))));
				// 课件信息
				HashMap<Integer, Courseware> coursewareMap = new HashMap<>();
				QueryWrapper<Courseware> coursewareWrapper = new QueryWrapper<>();
				List<Courseware> courseList = coursewareMapper.selectList(coursewareWrapper);
				if (courseList != null && courseList.size() > 0) {
					for (Courseware courseware : courseList) {
						coursewareMap.put(courseware.getId(), courseware);
					}
				}
				// 学习记录
				List<StudyRecord> studyRecordList = studyRecordMapper.selectList(new LambdaQueryWrapper<StudyRecord>()
					.eq(StudyRecord::getStudentId, item.getStudentId())
					.in(StudyRecord::getRelUnitCourseId, unitStatisticsPage.getRelUnitCourseId()));

				Integer studyTimeCompletion = 0;
				// 课程列表数据组装
				if (studyRecordList != null && studyRecordList.size() > 0) {
					Long studyTimeTotal = 0L;

					StudyRecord studyRecordFirst = studyRecordList.get(0);

					for (StudyRecord studyRecord : studyRecordList) {
						// 组装课件名称
						Courseware courseware = coursewareMap.get(studyRecord.getCoursewareId());
						if (courseware != null) {
							studyRecord.setCourseware(courseware);
						} else {
							log.info("结业证明-组装课件名称为空");
						}
						studyTimeCompletion += studyRecord.getPlayProgress();
						studyTimeTotal += studyRecord.getDuration();
						studyRecord.setStudyTimeCompletion(studyTimeCompletion);
						Double rate = (studyRecord.getStudyTimeCompletion()*1.0/item.getDuration())*100;
						studyRecord.setStudyProgress(String.format("%.2f",rate)+"%");
					}
				}
//				item.setStudyRecord(studyRecordList);
				item.setStudyTimeCompletion(studyTimeCompletion);
				Double rate = (item.getStudyTimeCompletion()*1.0/item.getDuration())*100;
				if(rate >= 100){
					item.setStudyProgress("100%");
				}else{
					item.setStudyProgress(String.format("%.2f",rate)+"%");
				}
				if(StringUtils.isNotEmpty(item.getSignatrueimg()) || StringUtils.isNotEmpty(item.getStuDuration())){
					item.setIsSignatrue("已签到");
				}else{
					item.setIsSignatrue("未签到");
				}
			});
			unitStatisticsPage.setRecords(trainingListModelList);
		}
		return unitStatisticsPage;
	}

	@Override
	public List<TrainingListModel> getDeptCourse(String deptName,Integer type) {
		return unitStatisticsMapper.getDeptCourse(deptName,type);
	}


}
