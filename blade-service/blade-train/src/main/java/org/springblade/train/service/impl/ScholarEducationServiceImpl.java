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
import org.springblade.train.entity.*;
import org.springblade.train.mapper.*;
import org.springblade.train.page.ScholarEducationPage;
import org.springblade.train.service.IScholarEducationService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 学员学历分析service
 */
@Service
@AllArgsConstructor
@Slf4j
public class ScholarEducationServiceImpl extends ServiceImpl<ScholarEducationMapper, ScholarEducationModel> implements IScholarEducationService {

    private ScholarEducationMapper scholarEducationMapper;

    private StudentMapper studentMapper;

    private UnitMapper unitMapper;

    private CoursewareMapper coursewareMapper;

    private StudyRecordMapper studyRecordMapper;

    private ExamRecordMapper examRecordMapper;

    private SnapshotMapper snapshotMapper;

    @Override
    public ScholarEducationPage getScholarEducationList(ScholarEducationPage scholarEducationPage) {
        Integer total = scholarEducationMapper.getScholarEducationListTotal(scholarEducationPage);
        if(scholarEducationPage.getSize()==0){
            if(scholarEducationPage.getTotal()==0){
                scholarEducationPage.setTotal(total);
            }
            if(scholarEducationPage.getTotal()==0){
                return scholarEducationPage;
            }else {
                List<ScholarEducationModel> studyRecordAppList = scholarEducationMapper.getScholarEducationList(scholarEducationPage);
                scholarEducationPage.setRecords(studyRecordAppList);
                return scholarEducationPage;
            }
        }
        Integer pagetotal = 0;
        if (total > 0) {
            if(total%scholarEducationPage.getSize()==0){
                pagetotal = total / scholarEducationPage.getSize();
            }else {
                pagetotal = total / scholarEducationPage.getSize() + 1;
            }
        }
        if (pagetotal >= scholarEducationPage.getCurrent()) {
            scholarEducationPage.setPageTotal(pagetotal);
            Integer offsetNo = 0;
            if (scholarEducationPage.getCurrent() > 1) {
                offsetNo = scholarEducationPage.getSize() * (scholarEducationPage.getCurrent() - 1);
            }
            scholarEducationPage.setTotal(total);
            scholarEducationPage.setOffsetNo(offsetNo);
            List<ScholarEducationModel> studyRecordAppList = scholarEducationMapper.getScholarEducationList(scholarEducationPage);
            scholarEducationPage.setRecords(studyRecordAppList);
        }
        return scholarEducationPage;
    }

    @Override
    public CertificateModel getCertificateOfCompletiont(Integer studentId, List<Integer> courseIdList) {
        CertificateModel model = new CertificateModel();
        QueryWrapper<Student> studentWrapper = new QueryWrapper<Student>();
        studentWrapper.lambda().eq(Student::getId, studentId);
        Student student = studentMapper.selectOne(studentWrapper);

        // 组装学员基本信息
        if (student != null) {
            model.setUserName(student.getUserName());
            model.setRealName(student.getRealName());
            model.setSex(student.getSex());
            model.setStation(student.getStation());
            model.setIdentifyNumber(student.getIdentifyNumber());
            model.setFullFacePhoto(student.getFullFacePhoto());
            QueryWrapper<Unit> unitWrapper = new QueryWrapper<Unit>();
            unitWrapper.lambda().eq(Unit::getId, student.getUnitId());
            Unit unit = unitMapper.selectOne(unitWrapper);
            // 组装企业
            if (unit != null) {
                model.setUnitId(unit.getId());
                model.setUnitName(unit.getFullName());
            } else {
                log.info("结业证明-组装企业为空");
            }
        }

        // 课件信息
        // 课件临时HashMap
        HashMap<Integer, Courseware> coursewareMap = new HashMap<Integer, Courseware>();
        QueryWrapper<Courseware> coursewareWrapper = new QueryWrapper<Courseware>();
        List<Courseware> courseList = coursewareMapper.selectList(coursewareWrapper);
        if (courseList != null && courseList.size() > 0) {
            for (Courseware courseware : courseList) {
                coursewareMap.put(courseware.getId(), courseware);
            }
        }

        // 学习记录
        QueryWrapper<StudyRecord> studyRecordWrapper = new QueryWrapper<StudyRecord>();
        studyRecordWrapper.lambda().eq(StudyRecord::getStudentId, studentId);
        studyRecordWrapper.lambda().in(StudyRecord::getRelUnitCourseId, courseIdList);
        List<StudyRecord> studyRecordList = studyRecordMapper.selectList(studyRecordWrapper);

        // 考试记录
        QueryWrapper<ExamRecord> examRecordWrapper = new QueryWrapper<ExamRecord>();
        examRecordWrapper.select("score,begin_time as beginTime,end_time as endTime,result");
        examRecordWrapper.lambda().eq(ExamRecord::getStudentId, studentId)
                .in(ExamRecord::getRelUnitCourseId, courseIdList)
                .eq(ExamRecord::getResult, 1)
                .eq(ExamRecord::getValid, 1);

        examRecordWrapper.lambda().orderByDesc(ExamRecord::getScore);
        examRecordWrapper.last("limit 1");
        ExamRecord examRecord = examRecordMapper.selectOne(examRecordWrapper);
        if (examRecord != null) {
            // 组装考试基本信息
            model.setExamRecord(examRecord);
        } else {
            log.info("结业证明-学员id={},课程id={},无考试记录", studentId, courseIdList);
        }

        // 课程列表数据组装
        if (studyRecordList != null && studyRecordList.size() > 0) {

            for (StudyRecord studyRecord : studyRecordList) {
                // 组装课件名称
                Courseware courseware = coursewareMap.get(studyRecord.getCoursewareId());
                if (courseware != null) {
                    studyRecord.setCourseware(courseware);
                } else {
                    log.info("结业证明-组装课件名称为空");
                }
                try {
                    // 计算总时长
                    model.setTotaltime(model.getTotaltime() + studyRecord.getDuration());
                } catch (Exception ex) {
                    log.error("error:", ex);
                }
            }
            // 课程列表
            model.setStudyRecord(studyRecordList);
            //最大结束时间
            String dateTime = studyRecordList.stream().map(StudyRecord::getEndTime).max(String::compareTo).get();
            model.setDateTime(dateTime);
        }

        // 抓拍记录
        model.setSnapshot(null);
        if (studyRecordList.size() > 0) {
            QueryWrapper<Snapshot> snapshotWrapper = new QueryWrapper<Snapshot>();
            snapshotWrapper.select("DISTINCT create_time,student_id,rel_unit_course_id,photo_url").lambda().eq(Snapshot::getStudentId, studentId)
                    .eq(Snapshot::getRelUnitCourseId, studyRecordList.get(0).getRelUnitCourseId())
                    .orderByAsc(Snapshot::getCreateTime);
            List<Snapshot> snapshotList = snapshotMapper.selectList(snapshotWrapper);
            if (snapshotList != null && snapshotList.size() > 0) {
                model.setSnapshot(snapshotList);
            } else {
                log.info("结业证明-组装抓拍图片记录为空");
            }
        }
        return model;
    }

	@Override
	public List<Map<String, Date>> getCourseExt(Integer courseId) {
		List<Map<String, Date>> courseExt = baseMapper.getCourseExt(courseId);
		return courseExt;
	}

	@Override
	public List<TrainingListModel> getTrainingList_swh(Integer relUnitCourseId) {
		List<TrainingListModel> trainingListModelList = baseMapper.getTrainingList_swh(relUnitCourseId);
		trainingListModelList.forEach(item->{
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
				.in(StudyRecord::getRelUnitCourseId, relUnitCourseId));

			// 课程列表数据组装
			if (studyRecordList != null && studyRecordList.size() > 0) {
				Long studyTimeTotal = 0L;
				Integer studyTimeCompletion = 0;
				StudyRecord studyRecordFirst = studyRecordList.get(0);
				Integer courseId = studyRecordFirst.getRelUnitCourseId();

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
			item.setStudyRecord(studyRecordList);
		});
		return trainingListModelList;
	}

	@Override
	public List<TrainingListModel> getCourseQuestion(Integer courseId) {
		return baseMapper.getCourseQuestion(courseId);
	}

}
