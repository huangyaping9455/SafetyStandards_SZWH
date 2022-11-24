/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.train.entity.CourseTestRecord;
import org.springblade.train.mapper.CourseTestRecordMapper;
import org.springblade.train.service.ICourseTestRecordService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseTestRecordServiceImpl extends ServiceImpl<CourseTestRecordMapper, CourseTestRecord> implements ICourseTestRecordService {

}
