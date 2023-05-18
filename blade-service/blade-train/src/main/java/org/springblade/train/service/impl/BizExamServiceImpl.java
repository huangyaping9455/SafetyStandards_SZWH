/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.train.entity.BizExam;
import org.springblade.train.mapper.BizExamMapper;
import org.springblade.train.service.IBizExamService;
import org.springframework.stereotype.Service;

/**
 * @Description: 课程表service
 */
@Service("IBizExamService")
@AllArgsConstructor
public class BizExamServiceImpl extends ServiceImpl<BizExamMapper, BizExam> implements IBizExamService {

}
