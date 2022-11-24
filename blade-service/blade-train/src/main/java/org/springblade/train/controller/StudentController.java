package org.springblade.train.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.train.config.FacePostProcessor;
import org.springblade.train.config.FaceProperties;
import org.springblade.train.config.JSONUtils;
import org.springblade.train.entity.Student;
import org.springblade.train.entity.Unit;
import org.springblade.train.entity.WaitCompletedCourse;
import org.springblade.train.service.IStudentService;
import org.springblade.train.service.ITrainService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 学员管理
 * @author TLFBOY
 * @create 2020-02-24 16:33
 */
@RestController
@AllArgsConstructor
@RequestMapping("/train/student")
@Api(value = "学员信息", tags = "学员信息")
public class StudentController extends BladeController {

    private IStudentService studentService;

    private FaceProperties faceProperties;

    private FacePostProcessor facePostProcessor;

	private ITrainService trainService;


	@GetMapping("/getStudent")
	@ApiOperation(value = "教育--根据企业名称、学员姓名、身份证号获取学员信息", notes = "教育--根据企业名称、学员姓名、身份证号获取学员信息", position = 13)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptName", value = "企业名称", required = true),
		@ApiImplicitParam(name = "driverName", value = "学员姓名", required = true),
		@ApiImplicitParam(name = "identifyNumber", value = "学员身份证号", required = true)
	})
	public R getStudent(String driverName,String identifyNumber, String deptName) {
		R rs = new R();
		//根据企业名称查询教育平台是否包含该企业
		Unit unit = trainService.getUnitByName(deptName);
		if(unit == null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("当前所属单位未在教育系统中");
			return rs;
		}
		Student findStudent = studentService.getStudentByUserName(driverName,identifyNumber,unit.getId());
		if(findStudent != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(findStudent);
			rs.setMsg("学员已存在");
			return rs;
		}else{
			rs.setCode(200);
			rs.setSuccess(false);
			rs.setData("");
			rs.setMsg("学员不存在，请先注册");
			return rs;
		}
	}

    /**
     * 添加学员
     * @param student
     * @return
     */
	@PostMapping("/addStudent")
	@ApiOperation(value = "学员信息--添加学员", notes = "学员信息--添加学员", position = 3)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "student", value = "数据对象", required = true)
	})
    public R addStudent(@RequestBody Student student){
		R rs = new R();
		//根据企业名称查询教育平台是否包含该企业
		Unit unit = trainService.getUnitByName(student.getUnitName());
		if(unit == null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("当前所属单位未在教育系统中");
			return rs;
		}
        Student findStudent = studentService.getStudentByUserName(student.getUserName(),student.getIdentifyNumber(),unit.getId());
        if(findStudent != null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("学员已经存在");
			return rs;
        }
        Date date = new Date();
		student.setUnitId(unit.getId());
        student.setCreatedBy("admin-third");
        student.setCreatedTime(date);
        student.setUpdatedBy("admin-third");
        student.setUpdatedTime(date);
        student.setStatus(0);
        student.setDeleted(0);
		student.setAreaId(1);
        student.setPassword(DigestUtil.encrypt(student.getPassword()));
        studentService.insert(student);
        studentService.manageFace(new Student(),student);
		rs.setCode(200);
		rs.setSuccess(true);
		rs.setMsg("添加成功");
		return rs;
    }

    /**
     * 更新学员
     * @param student
     * @return
     */
    @PostMapping(value ="/updateStudent")
	@ApiOperation(value = "学员信息--更新学员信息", notes = "学员信息--更新学员信息", position = 3)
    public R updateStudent(@RequestBody Student student){
		R rs = new R();
        student.setUpdatedTime(new Date());
        student.setUpdatedBy("admin-third");
        student.setPassword(null);
        // 更新人脸
        Student original = studentService.getStudentById(student.getId());
        studentService.manageFace(original,student);
        studentService.update(student);
		rs.setCode(200);
		rs.setMsg("更新成功");
		rs.setData(null);
        return rs;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteStudent/{id}")
	@ApiOperation(value = "学员信息--删除学员", notes = "学员信息--删除学员", position = 3)
    public R deleteStudent(@PathVariable Integer id){
		R rs = new R();
        try {
            List<Integer> idList = new ArrayList<>();
            idList.add(id);
            studentService.delete(idList);
            //同时删除人脸库的用户
            HashMap<String, String> map = new HashMap<>();
            map.put("group_id",faceProperties.getFacePlatformId());
            map.put("user_id",id.toString());
            String params = JSONUtils.obj2String(map);
            facePostProcessor.asyncMgrFace(faceProperties.getUserDeleteUrl()+"?"+facePostProcessor.getAccessToken(),params);
			rs.setCode(200);
			rs.setMsg("学员删除成功");
			rs.setData(null);
			return rs;
        } catch (Exception e) {
            e.printStackTrace();
			rs.setCode(500);
			rs.setMsg("学员删除失败");
			rs.setData(null);
			return rs;
        }
    }

    /**
     * 根据id查询
     * @return
     */
    @PostMapping(value = "/getStudentById")
	@ApiOperation(value = "学员信息--根据id查询详情", notes = "学员信息- -根据id查询详情", position = 3)
    public R getStudentById(@RequestBody String json){
		R rs = new R();
        JsonNode node = JSONUtils.string2JsonNode(json);
        Integer id = node.get("id").asInt();
        Student student = studentService.getStudentById(id);
		rs.setCode(200);
		rs.setMsg("查询成功");
		rs.setData(student);
		return rs;
    }

    /**
     * 重置密码
     * @param id 学员ID
     * @return 返回操作结果
     */
    @GetMapping("/password/{id}")
	@ApiOperation(value = "学员信息--重置密码", notes = "学员信息- -重置密码", position = 3)
    public R updatePassword(@PathVariable int id){
		R rs = new R();
        Student student = new Student();
        student.setId(id);
//        String encode = passwordEncoder.encode("123456");
//        student.setPassword(encode);
		student.setPassword(DigestUtil.encrypt(student.getPassword()));
        student.setUpdatedBy("admin-third");
        studentService.update(student);
		rs.setCode(200);
		rs.setMsg("密码重置成功");
		rs.setData(null);
		return rs;
    }

}
