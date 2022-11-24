/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springblade.train.entity.CourseStudent;

/**
 * @Description: 课程表mapper
 */
@Mapper
public interface CourseStudentMapper extends BaseMapper<CourseStudent> {

    /**
     * 添加学习记录
     * @param courseStudent
     * @return
     */
    boolean insertCourseStudentSelective(CourseStudent courseStudent);

}
