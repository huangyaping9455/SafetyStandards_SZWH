package org.springblade.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.train.entity.Student;

/**
 * mapper
 */
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 根据学员ID获取学员信息
     * @param driverId
     * @return
     */
    Student getStudentById(int driverId);

}
