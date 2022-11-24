/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.train.entity.*;
import org.springblade.train.page.CourseTestRecordPage;

import java.util.HashMap;
import java.util.List;

/**
 * @Description: 课程表service
 */
public interface IExamService extends IService<Exam> {
    /**
     * 获取待考试列表
     * @param param
     * @return
     */
    List<WaitExamModel> getWaitExamList(HashMap<String, Object> param);

    /**
     * 获取考题列表
     * @param param
     * @return
     */
    List<ExamQuestionModel> getQuestionList(HashMap<String, Object> param);

    /**
     * 获取考试列表
     * @param courseTestRecordPage
     * @return
     */
    CourseTestRecordPage getCourseTestRecordList(CourseTestRecordPage courseTestRecordPage);

    /**
     * 线下考试管理信息列表
     * @param param
     * @return
     */
    List<StudentExamModel> getStudentExamList(HashMap<String, Object> param);

    /**
     * 学员课程信息
     * @param param
     * @return
     */
    List<CourseStudent> getCourseStudentMapList(HashMap<String, Object> param);

    /**
     * 可以申请考核学生
     * @param param
     * @return
     */
    List<StudentApplyModel> getApplyStudentList(HashMap<String, Object> param);

    /**
     * 设置考试
     * @param param
     * @return
     */
    void setStudentExamStatus(HashMap<String, Object> param);

    /**
     * 设置考试
     * @param param
     * @return
     */
    void setStudentExamCancel(HashMap<String, Object> param);

    /**
     * 设置分数
     * @param param
     * @return
     */
    void setStudentExamScore(HashMap<String, Object> param);

    /**
     * 获取企业课程关联ID
     * @param studentId
     * @param courseId
     * @return
     */
    Integer getRelUnitCourseId(Integer studentId, Integer courseId);
}
