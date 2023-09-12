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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanTrain;
import org.springblade.anbiao.jiashiyuan.feign.IJiaShiYuanClient;
import org.springblade.anbiao.qiyeshouye.page.QiYeShouYePage;
import org.springblade.train.entity.*;
import org.springblade.train.mapper.*;
import org.springblade.train.page.CourseInfoPage;
import org.springblade.train.page.StudyRecordPage;
import org.springblade.train.service.ITrainService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
					CoursewareInfo coursewareInfo = trainMapper.getCoursewareDuration(item.getId());
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
				CoursewareInfo coursewareInfo = trainMapper.getCoursewareDuration(item.getId());
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
			List<JiaShiYuanTrain> jiaShiYuanTrainList =jiaShiYuanClient.selectJiaShiYuanTrain(deptId);
			if(jiaShiYuanTrainList.size() > 0){
				jiaShiYuanTrainList.forEach(item-> {
					if(studentitem.getRealName().equals(item.getJiashiyuanxingming())){
						studentitem.setSignatrue(item.getSignatrue());
					}
				});
			}else{
				ExamSignatrue examSignatrue = mapper.getSignatrue(studentId, courseId);
				studentitem.setSignatrue(examSignatrue.getSignatrueimg());
			}
		});
		return studentProveList;
	}

	@Override
	public List<StudentProveDetail> getStudentProveDetailList(int courseId, int studentId) {
		return trainMapper.getStudentProveDetailList(courseId, studentId);
	}

	@Override
	public List<StudentStatistics> getStudentAllList(Integer unitId, Integer courseId,Integer deptId) {
//		return trainMapper.getStudentAllList(unitId,courseId);
		List<StudentStatistics> studentStatisticsList = trainMapper.getStudentAllList(unitId,courseId);
		studentStatisticsList.forEach(jsyitem-> {
			List<JiaShiYuanTrain> jiaShiYuanTrainList =jiaShiYuanClient.selectJiaShiYuanTrain(deptId);
			if(jiaShiYuanTrainList.size() > 0){
				jiaShiYuanTrainList.forEach(item-> {
					if(jsyitem.getRealname().equals(item.getJiashiyuanxingming())){
						jsyitem.setSignatrue(item.getSignatrue());
					}
				});
			}
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
