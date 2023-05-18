/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springblade.train.entity.ExamSignatrue;

/**
 * @Description: 考试签名mapper
 * @Author: stydm
 * @CreateDate: 2019-10-19$ 16:24$
 * @UpdateUser: stydm
 * @UpdateDate: 2019-10-19$ 16:24$
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Mapper
public interface ExamSignatrueMapper extends BaseMapper<ExamSignatrue> {

    ExamSignatrue getSignatrue(Integer studentId, Integer coursewareId);

}
