/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.train.entity.ExamSignatrue;
import org.springblade.train.mapper.ExamSignatrueMapper;
import org.springblade.train.service.IExamSignatrueService;
import org.springframework.stereotype.Service;

/**
 * @Description: 课程表service
 * @Author: stydm
 * @CreateDate: 2019-10-19$ 16:24$
 * @UpdateUser: stydm
 * @UpdateDate: 2019-10-19$ 16:24$
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Service("IExamSignatrueService")
@AllArgsConstructor
public class ExamSignatrueServiceImpl extends ServiceImpl<ExamSignatrueMapper, ExamSignatrue> implements IExamSignatrueService {

    private ExamSignatrueMapper mapper;

    @Override
    public ExamSignatrue getSignatrue(Integer studentId, Integer coursewareId) {
        return mapper.getSignatrue(studentId, coursewareId);
    }
}
