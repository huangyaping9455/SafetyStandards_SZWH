/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.train.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanTrain;
import org.springblade.anbiao.jiashiyuan.feign.IJiaShiYuanClient;
import org.springblade.anbiao.qiyeshouye.page.QiYeShouYePage;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springblade.train.entity.*;
import org.springblade.train.mapper.*;
import org.springblade.train.page.CourseInfoPage;
import org.springblade.train.page.StudyRecordPage;
import org.springblade.train.service.ITrainService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 服务实现类
 * @author 呵呵哒
 */
@Service
@AllArgsConstructor
public class TrainServiceImpl extends ServiceImpl<TrainMapper, Train> implements ITrainService {

	private TrainMapper trainMapper;

	private SnapshotMapper snapshotMapper;

	private StudyRecordMapper studyRecordMapper;

	private StudentMapper studentMapper;

	private UnitMapper unitMapper;

	private AreaMapper areaMapper;

	private IJiaShiYuanClient jiaShiYuanClient;

	private ExamSignatrueMapper mapper;

	private ISysClient sysClient;

	private PasswordEncoder passwordEncoder;



	@Override
    public List<Train> getQYCourseList(String name, String id) {
        return trainMapper.getQYCourseList(name, id);
    }

    @Override
    public Unit getUnitByName(String name) {
        return trainMapper.getUnitByName(name);
    }

    @Override
    public Student getStudentByName(String driverName, String deptName) {
        return trainMapper.getStudentByName(driverName,deptName);
    }

    @Override
    public CourseExt getCourseExt(String alarmType, String deptId) {
        return trainMapper.getCourseExt(alarmType, deptId);
    }

    @Override
    public List<Course> getCourseALLList(int id) {
        return trainMapper.getCourseALLList(id);
    }

    @Override
    public boolean insertSnapshotSelective(Snapshot snapshot) {
        return snapshotMapper.insertSnapshotSelective(snapshot);
    }

    @Override
    public boolean insertStudyRecordSelective(StudyRecord studyRecord) {
        return studyRecordMapper.insertStudyRecordSelective(studyRecord);
    }

    @Override
    public StudyRecordPage getAppStudyRecordList(StudyRecordPage studyRecordPage) {
        Integer total = studyRecordMapper.getAppStudyRecordListTotal(studyRecordPage);
        if(studyRecordPage.getSize()==0){
            if(studyRecordPage.getTotal()==0){
                studyRecordPage.setTotal(total);
            }
            if(studyRecordPage.getTotal()==0){
                return studyRecordPage;
            }else {
                List<StudyRecordApp> studyRecordAppList = studyRecordMapper.getAppStudyRecordList(studyRecordPage);
                studyRecordPage.setRecords(studyRecordAppList);
                return studyRecordPage;
            }
        }
        Integer pagetotal = 0;
        if (total > 0) {
            if(total%studyRecordPage.getSize()==0){
                pagetotal = total / studyRecordPage.getSize();
            }else {
                pagetotal = total / studyRecordPage.getSize() + 1;
            }
        }
        if (pagetotal >= studyRecordPage.getCurrent()) {
            studyRecordPage.setPageTotal(pagetotal);
            Integer offsetNo = 0;
            if (studyRecordPage.getCurrent() > 1) {
                offsetNo = studyRecordPage.getSize() * (studyRecordPage.getCurrent() - 1);
            }
            studyRecordPage.setTotal(total);
            studyRecordPage.setOffsetNo(offsetNo);
            List<StudyRecordApp> studyRecordAppList = studyRecordMapper.getAppStudyRecordList(studyRecordPage);
            studyRecordPage.setRecords(studyRecordAppList);
        }
        return studyRecordPage;
    }

    @Override
    public StudyRecord getStudyRecordList(int studentId, int courseId, int coursewareId) {
        return studyRecordMapper.getStudyRecordList(studentId, courseId, coursewareId);
    }

	@Override
	public List<Snapshot> getSnapshot(JsonNode node) {
		QueryWrapper<Snapshot> queryWrapper = new QueryWrapper<>();
		int studentId = node.get("studentId").asInt();
		int relUnitCourseId = node.get("relUnitCourseId").asInt();
		queryWrapper.select("DISTINCT create_time,student_id,rel_unit_course_id,photo_url")
			.lambda().eq(Snapshot::getStudentId, studentId)
			.eq(Snapshot::getRelUnitCourseId, relUnitCourseId)
			.orderByAsc(Snapshot::getCreateTime);

		if(node.get("courseWareId") != null) {
			queryWrapper.lambda().eq(Snapshot::getCoursewareId, node.get("courseWareId").asInt());
		}

		if(node.get("startTime") != null && node.get("endTime") != null) {
			String startTime = node.get("startTime").asText();
			String endTime = node.get("endTime").asText();
			queryWrapper.lambda().between(Snapshot::getCreateTime, startTime, endTime);
		}
		if (node.hasNonNull("limit")){
			PageHelper.startPage(1,8);
		}
		return snapshotMapper.selectList(queryWrapper);
	}

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
	public StudyCountInfo getStudyInfo(Integer unitId) {
		return trainMapper.getStudyInfo(unitId);
	}

	@Override
	public CourseInfoPage<CourseInfo> selectCourseInfoPage(CourseInfoPage courseInfoPage) {
		Integer total = trainMapper.selectCourseInfoTotal(courseInfoPage);
		if(courseInfoPage.getSize()==0){
			if(courseInfoPage.getTotal()==0){
				courseInfoPage.setTotal(total);
			}
			if(courseInfoPage.getTotal()==0){
				return courseInfoPage;
			}else {
				List<CourseInfo> courseInfoList = trainMapper.selectCourseInfoPage(courseInfoPage);
				courseInfoList.forEach(item-> {
					CoursewareInfo coursewareInfo = trainMapper.getCoursewareDuration(item.getCourseId());
					if(coursewareInfo != null){
						item.setDuration(coursewareInfo.getDuration());
					}
				});
				courseInfoPage.setRecords(courseInfoList);
				return courseInfoPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%courseInfoPage.getSize()==0){
				pagetotal = total / courseInfoPage.getSize();
			}else {
				pagetotal = total / courseInfoPage.getSize() + 1;
			}
		}
		if (pagetotal >= courseInfoPage.getCurrent()) {
			courseInfoPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (courseInfoPage.getCurrent() > 1) {
				offsetNo = courseInfoPage.getSize() * (courseInfoPage.getCurrent() - 1);
			}
			courseInfoPage.setTotal(total);
			courseInfoPage.setOffsetNo(offsetNo);
			List<CourseInfo> courseInfoList = trainMapper.selectCourseInfoPage(courseInfoPage);
			courseInfoList.forEach(item-> {
				CoursewareInfo coursewareInfo = trainMapper.getCoursewareDuration(item.getCourseId());
				if(coursewareInfo != null){
					item.setDuration(coursewareInfo.getDuration());
				}
			});
			courseInfoPage.setRecords(courseInfoList);
		}
		return courseInfoPage;
	}

	@Override
	public List<CoursewareInfo> getCoursewareInfo(Integer courseId) {
		return trainMapper.getCoursewareInfo(courseId);
	}

	@Override
	public CourseInfoPage<StudentInfo> selectStudentInfoPage(CourseInfoPage courseInfoPage) {
		Integer total = trainMapper.selectStudentInfoTotal(courseInfoPage);
		if(courseInfoPage.getSize()==0){
			if(courseInfoPage.getTotal()==0){
				courseInfoPage.setTotal(total);
			}
			if(courseInfoPage.getTotal()==0){
				return courseInfoPage;
			}else {
				List<StudentInfo> studentInfoList = trainMapper.selectStudentInfoPage(courseInfoPage);
				courseInfoPage.setRecords(studentInfoList);
				return courseInfoPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%courseInfoPage.getSize()==0){
				pagetotal = total / courseInfoPage.getSize();
			}else {
				pagetotal = total / courseInfoPage.getSize() + 1;
			}
		}
		if (pagetotal >= courseInfoPage.getCurrent()) {
			courseInfoPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (courseInfoPage.getCurrent() > 1) {
				offsetNo = courseInfoPage.getSize() * (courseInfoPage.getCurrent() - 1);
			}
			courseInfoPage.setTotal(total);
			courseInfoPage.setOffsetNo(offsetNo);
			List<StudentInfo> studentInfoList = trainMapper.selectStudentInfoPage(courseInfoPage);
			courseInfoPage.setRecords(studentInfoList);
		}
		return courseInfoPage;
	}

	@Override
	public List<StudentProve> getStudentProveInfo(Integer unitId, Integer courseId, Integer studentId,Integer deptId) {
//		return trainMapper.getStudentProveInfo(unitId, courseId, studentId);
		List<StudentProve> studentProveList = trainMapper.getStudentProveInfo(unitId, courseId, studentId);
		studentProveList.forEach(studentitem-> {
			ExamSignatrue examSignatrue = mapper.getSignatrue(studentId, courseId);
			if(examSignatrue != null){
				studentitem.setSignatrue(examSignatrue.getSignatrueimg());
			}
//			List<JiaShiYuanTrain> jiaShiYuanTrainList =jiaShiYuanClient.selectJiaShiYuanTrain(null,studentitem.getRealName());
//			if(jiaShiYuanTrainList != null){
//				if(jiaShiYuanTrainList.size() > 0){
//					jiaShiYuanTrainList.forEach(item-> {
//						if(item.getJiashiyuanxingming().equals(studentitem.getRealName())){
//							studentitem.setSignatrue(item.getSignatrue());
//							if(StringUtils.isNotEmpty(item.getPhoto())){
//								studentitem.setFullFacePhoto(fileUploadClient.getUrl(item.getPhoto()));
//							}
//						}
//					});
//				}
//			}else{
//				ExamSignatrue examSignatrue = mapper.getSignatrue(studentId, courseId);
//				if(examSignatrue != null){
//					studentitem.setSignatrue(examSignatrue.getSignatrueimg());
//				}
//			}
		});
		return studentProveList;
	}

	@Override
	public List<StudentProveDetail> getStudentProveDetailList(int courseId, int studentId) {
		return trainMapper.getStudentProveDetailList(courseId, studentId);
	}

	@Override
	public List<StudentProveDetail> getStudentCoursewareList(int courseId, int studentId) {
		return trainMapper.getStudentCoursewareList(courseId, studentId);
	}

	@Override
	public List<StudentStatistics> getStudentAllList(Integer unitId, Integer courseId,Integer deptId) {
//		return trainMapper.getStudentAllList(unitId,courseId);
		List<StudentStatistics> studentStatisticsList = trainMapper.getStudentAllList(unitId,courseId);
		studentStatisticsList.forEach(jsyitem-> {
			ExamSignatrue examSignatrue = mapper.getSignatrue(jsyitem.getStudentId(), courseId);
			if(examSignatrue != null){
				jsyitem.setSignatrue(examSignatrue.getSignatrueimg());
			}
//			List<JiaShiYuanTrain> jiaShiYuanTrainList =jiaShiYuanClient.selectJiaShiYuanTrain(deptId,null);
//			if(jiaShiYuanTrainList.size() > 0){
//				jiaShiYuanTrainList.forEach(item-> {
//					if(jsyitem.getRealname().equals(item.getJiashiyuanxingming())){
//						jsyitem.setSignatrue(item.getSignatrue());
//					}
//				});
//			}
		});
		return studentStatisticsList;
	}

	@Override
	public List<CourseKind> getCourseKindList(String name) {
		return trainMapper.getCourseKindList(name);
	}

	@Override
	public QiYeShouYePage<ZFCourseInfo> selectZFPersonLearnCoutAll(QiYeShouYePage qiYeShouYePage) {
		Integer total = trainMapper.selectZFPersonLearnCoutAllTotal(qiYeShouYePage);
		if(qiYeShouYePage.getSize()==0){
			if(qiYeShouYePage.getTotal()==0){
				qiYeShouYePage.setTotal(total);
			}
			if(qiYeShouYePage.getTotal()==0){
				return qiYeShouYePage;
			}else{
				List<ZFCourseInfo> ZFCourseInfoList = trainMapper.selectZFPersonLearnCoutAll(qiYeShouYePage);
				qiYeShouYePage.setRecords(ZFCourseInfoList);
				return qiYeShouYePage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeShouYePage.getSize()==0){
				pagetotal = total / qiYeShouYePage.getSize();
			}else {
				pagetotal = total / qiYeShouYePage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeShouYePage.getCurrent()) {
			qiYeShouYePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeShouYePage.getCurrent() > 1) {
				offsetNo = qiYeShouYePage.getSize() * (qiYeShouYePage.getCurrent() - 1);
			}
			qiYeShouYePage.setTotal(total);
			qiYeShouYePage.setOffsetNo(offsetNo);
			List<ZFCourseInfo> ZFCourseInfoList = trainMapper.selectZFPersonLearnCoutAll(qiYeShouYePage);
			qiYeShouYePage.setRecords(ZFCourseInfoList);
		}
		return qiYeShouYePage;
	}

	@Override
	public Boolean getStudentDept(Integer deptId) {
		AtomicBoolean ss = new AtomicBoolean(false);
		List<Dept> deptList = sysClient.getQiYeList(deptId);
		if (deptList != null && deptList.size() > 0){
			deptList.forEach(deptitem-> {
				String time = DateUtil.now();
				//同步更新企业信息
				QueryWrapper<Unit> unitWrapper = new QueryWrapper<>();
				unitWrapper.lambda().eq(Unit::getFullName, deptitem.getDeptName());
				unitWrapper.lambda().eq(Unit::getDeleted, 0);
				Unit unit = unitMapper.selectOne(unitWrapper);
				if(unit != null){
					int id = unit.getId() == null ? 0 : unit.getId();
					// 修改
					if(id > 0) {
						unit.setUpdatedTime(time);
						unit.setUpdatedBy("数据同步管理员");
						int i = unitMapper.updateById(unit);
						if(i>0) {
							ss.set(true);
						}else {
							ss.set(false);
						}
					}else {
						unit.setFullName(deptitem.getDeptName());
						unit.setSimpleName(deptitem.getDeptName());
						unit.setTradeKindId(8);
						unit.setAreaId(2848);
						unit.setServerId(1);
						unit.setGovernmentId(2);
						// 类型 营运商-0，政府-1，代理商-2，企业-3
						unit.setType(3);
						// 状态 正常-1，暂停-2
						unit.setStatus(1);
						// 删除标识 删除为1，默认为0
						unit.setDeleted(0);
						unit.setCreatedTime(time);
						unit.setUpdatedTime(time);
						unit.setCreatedBy("数据同步管理员");
						unit.setUpdatedBy("数据同步管理员");
						// 新增
						int i = unitMapper.insert(unit);
						if(i>0) {
							ss.set(true);
						}else {
							ss.set(false);
						}
					}
				}else{
					unit = new Unit();
					unit.setFullName(deptitem.getDeptName());
					unit.setSimpleName(deptitem.getDeptName());
					unit.setTradeKindId(8);
					unit.setAreaId(2848);
					unit.setServerId(1);
					unit.setGovernmentId(2);
					// 类型 营运商-0，政府-1，代理商-2，企业-3
					unit.setType(3);
					// 状态 正常-1，暂停-2
					unit.setStatus(1);
					// 删除标识 删除为1，默认为0
					unit.setDeleted(0);
					unit.setCreatedTime(time);
					unit.setUpdatedTime(time);
					unit.setCreatedBy("数据同步管理员");
					unit.setUpdatedBy("数据同步管理员");
					// 新增
					int i = unitMapper.insert(unit);
					if(i>0) {
						ss.set(true);
					}else {
						ss.set(false);
					}
				}
				QueryWrapper<Unit> unitWrapper1 = new QueryWrapper<>();
				unitWrapper1.lambda().eq(Unit::getFullName, deptitem.getDeptName());
				unitWrapper1.lambda().eq(Unit::getDeleted, 0);
				Unit unit1 = unitMapper.selectOne(unitWrapper);
				List<JiaShiYuanTrain> jiaShiYuanTrainList = jiaShiYuanClient.selectJiaShiYuanTrain(deptitem.getId(),null);
				if(jiaShiYuanTrainList != null && jiaShiYuanTrainList.size() > 0){
					jiaShiYuanTrainList.forEach(item-> {
						System.out.println(item.getJiashiyuanxingming());
						//同步更新学员信息
						QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
						queryWrapper.eq("realName",item.getJiashiyuanxingming());
						queryWrapper.eq("cellphone",item.getShoujihaoma());
						queryWrapper.eq("deleted",0);
						Student student = studentMapper.selectOne(queryWrapper);
						if(student != null && student.getId() != null){
							student.setRealName(item.getJiashiyuanxingming());
							student.setUserName(item.getJiashiyuanxingming());
							student.setCellphone(item.getShoujihaoma());
							student.setIdentifyNumber(item.getShenfenzhenghao());
							if(StringUtils.isNotEmpty(item.getXingbie())){
								student.setSex(Integer.parseInt(item.getXingbie()));
							}else{
								student.setSex(1);
							}
							student.setPlateNumber(item.getChepaihao());
							student.setId(student.getId());
							student.setUpdatedTime(new Date());
							student.setUpdatedBy("数据同步管理员");
							student.setPassword(null);
							int i = studentMapper.updateById(student);
							if(i>0) {
								ss.set(true);
							}else {
								ss.set(false);
							}
						}else{
							student = new Student();
							student.setUnitId(unit1.getId());
							student.setRealName(item.getJiashiyuanxingming());
							student.setUserName(item.getJiashiyuanxingming());
							student.setCellphone(item.getShoujihaoma());
							student.setIdentifyNumber(item.getShenfenzhenghao());
							if(StringUtils.isEmpty(item.getXingbie())){
								item.setXingbie("1");
							}
							student.setSex(Integer.parseInt(item.getXingbie()));
							student.setPlateNumber(item.getChepaihao());
							student.setStation("驾驶员");
							student.setAreaId(1);
							student.setStatus(1);
							student.setDeleted(0);
							student.setCreatedBy("数据同步管理员");
							student.setCreatedTime(new Date());
							student.setUpdatedBy("数据同步管理员");
							student.setUpdatedTime(new Date());

							String password = "123456";
							String encode = passwordEncoder.encode(password);
							student.setPassword(encode);
							int i = studentMapper.insert(student);
							if(i>0) {
								ss.set(true);
							}else {
								ss.set(false);
							}
						}
					});
				}
				//将已经删除了的驾驶员从教育平台中进行删除
				// 示例学员列表
				List<String> list1 = new ArrayList<>();
				List<String> list2 = new ArrayList<>();
				if(jiaShiYuanTrainList != null && jiaShiYuanTrainList.size() > 0){
					jiaShiYuanTrainList.forEach(item-> {
						list1.add(item.getJiashiyuanxingming());
					});
				}

				QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("unit_id",unit.getId());
				queryWrapper.eq("deleted",0);
				List<Student> student = studentMapper.selectList(queryWrapper);
				if(student != null && student.size() > 0){
					if(student != null && student.size() > 0){
						student.forEach(item-> {
							list2.add(item.getRealName());
						});
					}
				}

//				// 使用Stream API检查list1中的每个学员是否都不在list2中
//				boolean notPresent = list1.stream()
//					.noneMatch(String -> list2.contains(student));
//
//				System.out.println("所有学员都不在list1中: " + notPresent);

				// 使用stream过滤出不在list2中的list1的学生
				List<String> notInStudents2 = list2.stream()
					.filter(String -> !list1.contains(String))
					.collect(Collectors.toList());
				// 打印结果
				System.out.println(notInStudents2); // 输出
				if(notInStudents2 != null){
					Unit finalUnit = unit;
					notInStudents2.forEach(stuitem-> {
						System.out.println(stuitem);
						QueryWrapper queryWrapper1 = new QueryWrapper<>();
						queryWrapper1.eq("realName",stuitem);
						queryWrapper1.eq("unit_id", finalUnit.getId());
						queryWrapper1.eq("deleted",0);
						Student stu = studentMapper.selectOne(queryWrapper1);
						if(stu != null && stu.getId() != null){
							stu.setUpdatedTime(new Date());
							stu.setUpdatedBy("数据同步管理员");
							stu.setDeleted(1);
							int i = studentMapper.updateById(stu);
							if(i>0) {
								ss.set(true);
							}else {
								ss.set(false);
							}
						}
					});
//					for (int i = 0; i < notInStudents2.size(); i++) {
//						System.out.println(notInStudents2.get(i));
//						System.out.println(i);
//						queryWrapper = new QueryWrapper<>();
//						queryWrapper.eq("realName",notInStudents2.get(i));
//						queryWrapper.eq("unit_id",unit.getId());
//						queryWrapper.eq("deleted",0);
//						Student stu = studentMapper.selectOne(queryWrapper);
//						if(stu != null && stu.getId() != null){
//							stu.setUpdatedTime(new Date());
//							stu.setUpdatedBy("数据同步管理员");
//							stu.setDeleted(1);
//							i = studentMapper.updateById(stu);
//							if(i>0) {
//								ss.set(true);
//							}else {
//								ss.set(false);
//							}
//						}
//					}
				}
			});
		}else{
			ss.set(true);
		}
		return ss.get();
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
}
