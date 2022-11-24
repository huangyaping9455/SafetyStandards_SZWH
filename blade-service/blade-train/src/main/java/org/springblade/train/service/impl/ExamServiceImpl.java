/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.train.entity.*;
import org.springblade.train.mapper.ExamMapper;
import org.springblade.train.page.CourseTestRecordPage;
import org.springblade.train.service.IExamService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Description: 课程表service
 */
@Service("IExamService")
@AllArgsConstructor
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements IExamService {
    
    private ExamMapper examMapper;
    
    @Override
    public List<WaitExamModel> getWaitExamList(HashMap<String, Object> param) {
        return examMapper.getWaitExamList(param);
    }

    @Override
    public List<ExamQuestionModel> getQuestionList(HashMap<String, Object> param) {
        return examMapper.getQuestionList(param);
    }

    @Override
    public CourseTestRecordPage getCourseTestRecordList(CourseTestRecordPage courseTestRecordPage) {
        Integer total = examMapper.getCourseTestRecordTotal(courseTestRecordPage);
        if(courseTestRecordPage.getSize()==0){
            if(courseTestRecordPage.getTotal()==0){
                courseTestRecordPage.setTotal(total);
            }
            if(courseTestRecordPage.getTotal()==0){
                return courseTestRecordPage;
            }else {
                List<CourseTestRecord> studyRecordAppList = examMapper.getCourseTestRecordList(courseTestRecordPage);
                courseTestRecordPage.setRecords(studyRecordAppList);
                return courseTestRecordPage;
            }
        }
        Integer pagetotal = 0;
        if (total > 0) {
            if(total%courseTestRecordPage.getSize()==0){
                pagetotal = total / courseTestRecordPage.getSize();
            }else {
                pagetotal = total / courseTestRecordPage.getSize() + 1;
            }
            List<CourseTestRecord> studyRecordAppList = examMapper.getCourseTestRecordList(courseTestRecordPage);
            courseTestRecordPage.setRecords(studyRecordAppList);
        }
        if (pagetotal >= courseTestRecordPage.getCurrent()) {
            courseTestRecordPage.setPageTotal(pagetotal);
            Integer offsetNo = 0;
            if (courseTestRecordPage.getCurrent() > 1) {
                offsetNo = courseTestRecordPage.getSize() * (courseTestRecordPage.getCurrent() - 1);
            }
            courseTestRecordPage.setTotal(total);
            courseTestRecordPage.setOffsetNo(offsetNo);
            List<CourseTestRecord> studyRecordAppList = examMapper.getCourseTestRecordList(courseTestRecordPage);
            courseTestRecordPage.setRecords(studyRecordAppList);
        }
        return courseTestRecordPage;
    }

    @Override
    public List<StudentExamModel> getStudentExamList(HashMap<String, Object> param) {
        return examMapper.getStudentExamList(param);
    }

    @Override
    public List<CourseStudent> getCourseStudentMapList(HashMap<String, Object> param) {
        return examMapper.getCourseStudentMapList(param);
    }

    @Override
    public List<StudentApplyModel> getApplyStudentList(HashMap<String, Object> param) {
        return examMapper.getApplyStudentList(param);
    }

    @Override
    public void setStudentExamStatus(HashMap<String, Object> param) {
        examMapper.setStudentExamStatus(param);
    }

    @Override
    public void setStudentExamCancel(HashMap<String, Object> param) {
        examMapper.setStudentExamCancel(param);
    }

    @Override
    public void setStudentExamScore(HashMap<String, Object> param) {
        examMapper.setStudentExamScore(param);
    }

    @Override
    public Integer getRelUnitCourseId(Integer studentId, Integer courseId){
        return examMapper.getRelUnitCourseId(studentId, courseId);
    }
}
