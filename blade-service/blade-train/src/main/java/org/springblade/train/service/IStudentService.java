package org.springblade.train.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.train.config.FacePostProcessor;
import org.springblade.train.entity.Student;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * 学员业务层接口
 * @author TLFBOY
 * @create 2020-02-21 21:12
 */
public interface IStudentService extends IService<Student> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Student getStudentById(Integer id);

    @Async
    void manageFace(Student orginalStudent,Student modifyStudent);

    /**
     * 根据学员ID获取学员信息
     * @param driverId
     * @return
     */
    Student getStudentById(int driverId);

	/**
	 * 根据名称查询
	 * @param userName
	 * @return
	 */
	Student getStudentByUserName(String userName, String identifyNumber, Integer unitId);

	/***
	 * 修改
	 * @param student
	 */
	void update(Student student);

	/**
	 * 添加
	 * @param student
	 */
	void insert(Student student);

	/**
	 * 删除
	 * @param ids
	 */
	void delete(List<Integer> ids);


}


