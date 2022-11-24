package org.springblade.train.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.configurationBean.TrainServer;
import org.springblade.train.config.FacePostProcessor;
import org.springblade.train.config.JSONUtils;
import org.springblade.train.config.RestUtils;
import org.springblade.train.entity.Area;
import org.springblade.train.entity.Student;
import org.springblade.train.entity.Unit;
import org.springblade.train.mapper.AreaMapper;
import org.springblade.train.mapper.StudentMapper;
import org.springblade.train.mapper.UnitMapper;
import org.springblade.train.service.IStudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 学员service
 */
@Slf4j
@Service
@AllArgsConstructor
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    private StudentMapper studentMapper;

    private UnitMapper unitMapper;

    private AreaMapper areaMapper;

    private TrainServer trainServer;

    private FacePostProcessor facePostProcessor;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Student getStudentById(Integer id) {
        Student student = studentMapper.selectById(id);
        if(student == null){
            return student;
        }
        List<Student> students = new ArrayList<>();
        students.add(student);
        formatStudent(students);
        return student;
    }

    @Override
    public void manageFace(Student orginalStudent, Student modifyStudent) {
        if (!trainServer.isEnable()) {
            return;
        }
        //人脸识别
        HashMap<String, String> map = new HashMap<>();
        map.put("group_id",trainServer.getFacePlatformId());
        map.put("user_id",modifyStudent.getId()+"");

        map.put("image_type",trainServer.getImageType());
        String params =JSONUtils.obj2String(map);

        String res = facePostProcessor.postMsg(trainServer.getUserGetUrl()+"?"+facePostProcessor.getAccessToken(),params);

        JsonNode node = JSONUtils.string2JsonNode(res);

        Boolean notExists = false;
        notExists = node.get("error_code").asInt(-1) != 0;

        // 不存在时，是新增
        if (notExists){
            if (modifyStudent.getFullFacePhoto() != null ){
                map.put("user_info", modifyStudent.getUserName() + "-F");
                map.put("image", RestUtils.get(trainServer.getFileserver() + modifyStudent.getFullFacePhoto()));
                params = JSONUtils.obj2String(map);
                log.info("上传用户[ {} ]的正脸照片到人脸库", modifyStudent.getRealName());
                facePostProcessor.asyncMgrFace(trainServer.getUserAddUrl() + "?" + facePostProcessor.getAccessToken(), params);
            }
            if (modifyStudent.getLeftFacePhoto() != null) {
                map.put("image", RestUtils.get(trainServer.getFileserver() + modifyStudent.getLeftFacePhoto()));
                map.put("user_info", modifyStudent.getUserName() + "-L");
                params = JSONUtils.obj2String(map);
                log.info("上传用户[ {} ]的左侧脸照片到人脸库", modifyStudent.getRealName());
                facePostProcessor.asyncMgrFace(trainServer.getUserAddUrl() + "?" + facePostProcessor.getAccessToken(), params);
            }
            if (modifyStudent.getRightFacePhoto() != null ) {
                map.put("image", RestUtils.get(trainServer.getFileserver() + modifyStudent.getRightFacePhoto()));
                map.put("user_info", modifyStudent.getUserName() + "-R");
                params = JSONUtils.obj2String(map);
                log.info("上传用户[ {} ]的右侧脸照片到人脸库", modifyStudent.getRealName());
                facePostProcessor.asyncMgrFace(trainServer.getUserAddUrl() + "?" + facePostProcessor.getAccessToken(), params);
            }
        }else {
            if ((modifyStudent.getFullFacePhoto() != null && orginalStudent.getFullFacePhoto() == null) ||
                    ((modifyStudent.getFullFacePhoto() != null && orginalStudent.getFullFacePhoto() != null) &&
                            !orginalStudent.getFullFacePhoto().trim().equals(modifyStudent.getFullFacePhoto().trim()))) {
                map.put("image", RestUtils.get(trainServer.getFileserver() + modifyStudent.getFullFacePhoto()));
                map.put("user_info", modifyStudent.getUserName() + "-F");
                params = JSONUtils.obj2String(map);
                log.info("上传用户[ {} ]的正脸照片到人脸库", modifyStudent.getRealName());
                facePostProcessor.asyncMgrFace(trainServer.getUserAddUrl() + "?" + facePostProcessor.getAccessToken(), params);
            }
            if ((modifyStudent.getLeftFacePhoto() != null && orginalStudent.getLeftFacePhoto() == null) ||
                    ((modifyStudent.getLeftFacePhoto() != null && orginalStudent.getLeftFacePhoto() != null) &&
                            !orginalStudent.getLeftFacePhoto().trim().equals(modifyStudent.getLeftFacePhoto().trim()))) {
                map.put("image", RestUtils.get(trainServer.getFileserver() + modifyStudent.getLeftFacePhoto()));
                map.put("user_info", modifyStudent.getUserName() + "-L");
                params = JSONUtils.obj2String(map);
                log.info("上传用户[ {} ]的左侧脸照片到人脸库", modifyStudent.getRealName());
                facePostProcessor.asyncMgrFace(trainServer.getUserAddUrl() + "?" + facePostProcessor.getAccessToken(), params);
            }
            if ((modifyStudent.getRightFacePhoto() != null && orginalStudent.getRightFacePhoto() == null) ||
                    ((modifyStudent.getRightFacePhoto() != null && orginalStudent.getRightFacePhoto() != null) &&
                            !orginalStudent.getRightFacePhoto().trim().equals(modifyStudent.getRightFacePhoto().trim()))) {
                map.put("image", RestUtils.get(trainServer.getFileserver() + modifyStudent.getRightFacePhoto()));
                map.put("user_info", modifyStudent.getUserName() + "-R");
                params = JSONUtils.obj2String(map);
                log.info("上传用户[ {} ]的右侧脸照片到人脸库", modifyStudent.getRealName());
                facePostProcessor.asyncMgrFace(trainServer.getUserAddUrl() + "?" + facePostProcessor.getAccessToken(), params);
            }
        }
    }

    @Override
    public Student getStudentById(int driverId) {
        return studentMapper.getStudentById(driverId);
    }

    private void formatStudent(List<Student> students){
        // 查询服务商列表存入临时HashMap
        HashMap<Integer, Unit> serviceMap = new HashMap<Integer, Unit>();
        QueryWrapper<Unit> serviceWrapper = new QueryWrapper<Unit>();

        // 类型 营运商-0，政府-1，代理商-2，企业-3[0,2 都表示服务商]
      /*  List<Integer> list = new ArrayList<Integer>();
        list.add(0);
        list.add(2);
        list.add(3);
        serviceWrapper.lambda().in(Unit::getType, list);*/
        serviceWrapper.lambda().eq(Unit::getDeleted,0);
        serviceWrapper.lambda().eq(Unit::getStatus,1);
        List<Unit> serviceList = unitMapper.selectList(serviceWrapper);
        if (serviceList != null && serviceList.size() > 0) {
            for (Unit unit : serviceList) {
                serviceMap.put(unit.getId(), unit);
            }
        }

        // 区域临时HashMap
        HashMap<Integer, Area> areaMap = new HashMap<Integer, Area>();
        QueryWrapper<Area> wrapper = new QueryWrapper<Area>();
        List<Area> areaList = areaMapper.selectList(wrapper);
        if (areaList != null && areaList.size() > 0) {
            for (Area area : areaList) {
                areaMap.put(area.getId(), area);
            }
        }

        for (Student student: students
        ) {
            Area area = areaMap.get(student.getAreaId());
            student.setAreaName(area == null ? "" : area.getSimpleName());
            Unit unit = serviceMap.get(student.getUnitId());
            student.setUnitName(unit==null?"":unit.getSimpleName());
            Unit server = serviceMap.get(student.getServerId());
            student.setServerName(server == null ? "":server.getSimpleName());
            student.setPassword(null);
        }
    }

	@Override
	public Student getStudentByUserName(String userName, String identifyNumber, Integer unitId) {

		QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("realName",userName);
		queryWrapper.eq("identify_number",identifyNumber);
		queryWrapper.eq("unit_id",unitId);
		queryWrapper.eq("deleted",0);
		return studentMapper.selectOne(queryWrapper);
	}

	/**
	 * 修改
	 * @param Student
	 */
	@Override
	public void update(Student Student){
		studentMapper.updateById(Student);
	}



	/**l
	 * 新增
	 * @param student
	 */
	@Override
	public void insert(Student student) {
		studentMapper.insert(student);
	}

	/**
	 * 删除
	 * @param ids
	 */
	@Override
	public void delete(List<Integer> ids) {
		List<Student> students = new ArrayList<>();
		for (Integer id:ids) {
			Student student = new Student();
			student.setId(id);
			student.setDeleted(1);
			students.add(student);
		}
		this.updateBatchById(students);
	}

}
