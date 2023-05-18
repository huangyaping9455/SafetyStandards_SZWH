/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.train.entity.ExamSignatrue;

/**
 * @Description: 课程表service
 * @Author: stydm
 * @CreateDate: 2019-10-19$ 16:24$
 * @UpdateUser: stydm
 * @UpdateDate: 2019-10-19$ 16:24$
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public interface IExamSignatrueService extends IService<ExamSignatrue> {

    ExamSignatrue getSignatrue(Integer studentId, Integer coursewareId);
}
