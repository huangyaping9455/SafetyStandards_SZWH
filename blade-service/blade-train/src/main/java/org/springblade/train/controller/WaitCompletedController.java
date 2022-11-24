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
package org.springblade.train.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.configurationBean.TrainServer;
import org.springblade.core.tool.api.R;
import org.springblade.train.config.BaseController;
import org.springblade.train.config.FacePostProcessor;
import org.springblade.train.config.JSONUtils;
import org.springblade.train.config.RestUtils;
import org.springblade.train.entity.*;
import org.springblade.train.page.StudyRecordPage;
import org.springblade.train.service.IStudentService;
import org.springblade.train.service.ITrainService;
import org.springblade.train.service.IWaitCompletedService;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/train/waitCompleted")
@Api(value = "处罚教育学习--待完成学习", tags = "处罚教育学习--待完成学习")
public class WaitCompletedController extends BaseController {

    private IWaitCompletedService waitCompletedService;

    private ITrainService trainService;

    private TrainServer trainServer;

    private FacePostProcessor facePostProcessor;

    private IStudentService studentService;

//    private IAlarmClientBack iAlarmClientBack;

    @GetMapping("/getFileServer")
    @ApiOperation(value = "教育--获取文件地址", notes = "教育--获取文件地址", position = 0)
    public R getFileServer() throws Exception {
        R rs = new R();
        String fileServer = trainServer.getFileserver();
        rs.setData(fileServer);
        rs.setCode(200);
        rs.setSuccess(true);
        rs.setMsg("获取地址成功");
        return rs;
    }

    @GetMapping("/getCourseList")
    @ApiOperation(value = "教育--获取待完成课程", notes = "教育--获取待完成课程", position = 1)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "studentId", value = "学员ID", required = true),
        @ApiImplicitParam(name = "courseId", value = "课程ID", required = false)
    })
    public R getCourseList(int studentId,int courseId) throws Exception {
        R rs = new R();
        int isPay = 3;
        int courseType = 1;
        Integer courseKind = null;
        try {
            //查询信息
            List<WaitCompletedCourse> courseList = waitCompletedService.getCourseList(studentId, isPay, courseType, courseKind,courseId);
            if (courseList != null) {
                rs.setCode(200);
                rs.setMsg("查询待完成课程成功");
                rs.setSuccess(true);
                rs.setData(courseList);
            } else {
                rs.setCode(200);
                rs.setMsg("暂无数据");
                rs.setSuccess(true);
                rs.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setMsg("查询待完成课程失败");
            rs.setSuccess(false);
        }
        return rs;
    }


//    /**
//     * 查询待完成课程级别
//     *
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "getCourseLevelList", method = RequestMethod.GET)
//    public String getCourseLevelList(HttpServletRequest request, @RequestParam int isPay, @RequestParam int courseType, @RequestParam int level) {
//        try {
//            AuthenticatedUser user = this.getCurrentUser();
//            int studentId = user.getUserId();
//            //查询信息
//            List<WaitCompletedCourse> courseList = waitCompletedService.getCourseLevelList(studentId, isPay, courseType, level);
//            return this.returnSuccessResult(courseList, "查询待完成课程成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return this.returnErrorResult("查询待完成课程失败");
//        }
//    }
//

    @GetMapping("/getCourseWareList")
    @ApiOperation(value = "教育--获取课程相关课件", notes = "教育--获取课程相关课件", position = 2)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "studentId", value = "学员ID", required = true),
        @ApiImplicitParam(name = "relUnitCourseId", value = "课程ID", required = true),
        @ApiImplicitParam(name = "courseId", value = "课件ID", required = false)
    })
    public R getCourseWareList(int studentId, int relUnitCourseId,int courseId) throws Exception{
        R rs = new R();
        try {
            //查询信息
            List<Courseware> coursewareList = waitCompletedService.getCourseWareList(studentId, relUnitCourseId,courseId);
            if (coursewareList != null) {
//            List<CoursewareModel> resultList = this.modelMapper.map(coursewareList, new TypeToken<List<CoursewareModel>>() {
//            }.getType());
                // 用 m3u8 地址
                List<CoursewareModel> resultList = new ArrayList<>();
                coursewareList.forEach((item) -> {
                    CoursewareModel cm = new CoursewareModel();
                    BeanUtils.copyProperties(item, cm);
//                    if (StringUtils.hasText(item.getMediaUrl())){
//                        cm.setSourceFile(item.getMediaUrl());
//                    }
                    resultList.add(cm);
                });
                rs.setCode(200);
                rs.setMsg("查询课件成功");
                rs.setSuccess(true);
                rs.setData(resultList);
            } else {
                rs.setCode(200);
                rs.setMsg("暂无数据");
                rs.setSuccess(true);
                rs.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setMsg("查询课件失败");
            rs.setSuccess(false);
        }
        return rs;
    }

    @GetMapping("/getExercisesList")
    @ApiOperation(value = "教育--获取课件下的练习题", notes = "教育- -获取课件下的练习题", position = 3)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "coursewareId", value = "课件ID", required = true)
    })
    public R getExercisesList(int coursewareId) throws Exception{
        R rs = new R();
        try {
            //查询信息
            List<Exercises> exercisesList = waitCompletedService.getExercisesList(coursewareId);
//            List<ExercisesModel> resultList = this.modelMapper.map(exercisesList, new TypeToken<List<ExercisesModel>>() {
//            }.getType());
            if(exercisesList != null){
                rs.setCode(200);
                rs.setMsg("获取课件下的练习题成功");
                rs.setSuccess(true);
                rs.setData(exercisesList);
            }else{
                rs.setCode(200);
                rs.setMsg("获取课件下的练习题成功,暂无数据");
                rs.setSuccess(true);
                rs.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setMsg("获取课件下的练习题失败");
            rs.setSuccess(false);
        }
        return rs;
    }

    @PostMapping("/saveSnapshot")
    @ApiOperation(value = "教育--保存抓拍图片", notes = "教育- -保存抓拍图片", position = 3)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "snapshot", value = "数据对象", required = true)
    })
    public R saveSnapshot(@RequestBody Snapshot snapshot) {
        R rs = new R();
        try {
            //保存信息
            boolean flag = trainService.insertSnapshotSelective(snapshot);
            if (flag) {
                rs.setCode(200);
                rs.setMsg("保存抓拍图片成功");
                rs.setSuccess(true);
            } else {
                rs.setCode(500);
                rs.setMsg("保存抓拍图片失败");
                rs.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setMsg("保存抓拍图片失败");
            rs.setSuccess(false);
        }
        return rs;
    }

    @PostMapping("/saveStudyRecord")
    @ApiOperation(value = "教育--保存学习记录", notes = "教育- -保存学习记录", position = 4)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "studyRecord", value = "数据对象", required = true)
    })
    public R saveStudyRecord(@RequestBody StudyRecord studyRecord) {
        R rs = new R();
        try {
            //获取用户信息
            if (StringUtils.isEmpty(studyRecord.getBeginTime())) {
                log.info("学习时间异常");
                rs.setCode(500);
                rs.setMsg("学习时间异常，请重新学习");
                rs.setSuccess(false);
            }
            if (StringUtils.isEmpty(studyRecord.getPlayProgress()) && studyRecord.getPlayProgress() == 0) {
                log.info("学习进度异常");
                rs.setCode(500);
                rs.setMsg("学习时间异常，请重新学习");
                rs.setSuccess(false);
            }
            int studentId = studyRecord.getStudentId();
            log.info("学员ID："+studentId);
            int relUnitCourseId = studyRecord.getRelUnitCourseId();
            int i = waitCompletedService.getBeginStudyTime(studentId, relUnitCourseId);
            if (i <= 0) {
                String beginTime = studyRecord.getBeginTime();
                waitCompletedService.saveBeginStudyTime(studentId, relUnitCourseId, beginTime);
            }
            //保存信息
            studyRecord.setStudentId(studentId);

            // 当开始结束时间运算后和前端传过来的学习时长不匹配时，以计算后的值为准，并更新学时进度
//            LocalDateTime start = LocalDateTime.parse(studyRecord.getBeginTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            LocalDateTime end = LocalDateTime.parse(studyRecord.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            Long duration = ChronoUnit.SECONDS.between(start, end);
//            if ((duration + 61) < studyRecord.getDuration()) {
//                studyRecord.setDuration(duration.intValue());
//                studyRecord.setPlayProgress(studyRecord.getPlayProgress() - studyRecord.getDuration() + duration.intValue());
//            }
            boolean flag = trainService.insertStudyRecordSelective(studyRecord);
            if (flag) {
                String endTime = studyRecord.getEndTime();
                waitCompletedService.saveEndStudyTime(studentId, relUnitCourseId, endTime);
                //修改安标主动安全学习记录表学习状态，改为“1”
//                BaoBiaoAlarmhandleresultDriver bb = new BaoBiaoAlarmhandleresultDriver();
//                bb.setStatus(1);
//                bb.setStudy_driver_id(studentId);
//                bb.setCourse_id(Integer.toString(relUnitCourseId));
////                bb.setAlarm_id();
//                iAlarmClientBack.updateStatus(bb);
                log.info("保存学习记录成功");
                rs.setCode(200);
                rs.setMsg("保存学习记录成功");
                rs.setSuccess(true);
            } else {
                log.info("保存学习记录失败");
                rs.setCode(500);
                rs.setMsg("保存学习记录失败");
                rs.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setMsg("保存学习记录失败");
            rs.setSuccess(false);
        }
        return rs;
    }

    @PostMapping("/getStudyRecord")
    @ApiOperation(value = "教育--查询学习记录", notes = "教育- -查询学习记录", position = 5)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "studyRecordPage", value = "数据对象", required = true)
    })
    public R getStudyRecord(@RequestBody StudyRecordPage studyRecordPage) {
        R rs = new R();
        try {
            StudyRecordPage studyRecordList = trainService.getAppStudyRecordList(studyRecordPage);
            if(studyRecordList != null){
                rs.setCode(200);
                rs.setMsg("查询学习记录成功");
                rs.setSuccess(true);
                rs.setData(studyRecordList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setMsg("查询学习记录失败");
            rs.setSuccess(false);
        }
        return rs;
    }

    @GetMapping("/getLastDuration")
    @ApiOperation(value = "教育--查询上一次课件学习时长", notes = "教育--查询上一次课件学习时长", position = 6)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "studentId", value = "学员ID", required = true),
        @ApiImplicitParam(name = "relUnitCourseId", value = "课件ID", required = true),
        @ApiImplicitParam(name = "coursewareId", value = "课件ID", required = true)
    })
    public R getLastDuration(int studentId,int relUnitCourseId,int coursewareId) {
        R rs = new R();
        try {
            StudyRecord studyRecord = trainService.getStudyRecordList(studentId, relUnitCourseId, coursewareId);
            if(studyRecord != null){
                int duration = studyRecord.getDuration();
                rs.setCode(200);
                rs.setMsg("查询上一次视频播放进度成功");
                rs.setSuccess(true);
                rs.setData(duration);
            }else{
                rs.setCode(500);
                rs.setMsg("查询上一次视频播放进度失败");
                rs.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setMsg("查询上一次视频播放进度失败");
            rs.setSuccess(false);
        }
        return rs;
    }

    @PostMapping("/searchUser")
    @ApiOperation(value = "教育--人脸验证", notes = "教育- -人脸验证", position = 7)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "studentId", value = "学员ID", required = true),
        @ApiImplicitParam(name = "json", value = "数据对象", required = true)
    })
    public R searchUser(@RequestBody String json) throws Exception{
        R rs = new R();

        //查询过滤条件
        JsonNode node = JSONUtils.string2JsonNode(json);
        String studentId = node.get("studentId").asText();
        Student user = studentService.getStudentById(Integer.parseInt(studentId));
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id",user.getId().toString());
        map.put("group_id_list",trainServer.getFacePlatformId());
        // map.put("image",config.getFileServer()+node.get("image").asText());
        map.put("image_type",trainServer.getImageType());
//        map.put("image", RestUtils.get(trainServer.getFileserver()+node.get("image").asText()));
        System.out.println("图片路径");
        System.out.println(node.get("image").asText());
        map.put("image", RestUtils.get(node.get("image").asText()));
        String params = JSONUtils.obj2String(map);
        try {
            String responseMsg = facePostProcessor.postMsg(trainServer.getSearchFaceUrl()+"?"+facePostProcessor.getAccessToken(),params);
            JsonNode res = JSONUtils.string2JsonNode(responseMsg);

            if(res.get("error_code").asInt()==0){
                JsonNode resList = res.get("result").get("user_list");
                if(resList.size()>0){
                    double score = resList.get(0).get("score").asDouble();
                    if(score>Integer.parseInt(trainServer.getSearchScore())){
                        rs.setCode(200);
                        rs.setMsg("认证成功");
                        rs.setSuccess(true);
                        rs.setData(resList.get(0));
                        return rs;
                    }
                }
            }else if(res.get("error_code").asInt() == 222207){
                // 人脸不存在，异步重传人脸
                log.warn("用户 {}/{} 人脸不存在，重传人脸",user.getRealName(),user.getUserName());
                Student student = studentService.getStudentById(user.getId());
                studentService.manageFace(student,student);
            }
            log.info("人脸验证失败：\r\n "+responseMsg);
            rs.setCode(500);
            rs.setMsg("认证失败");
            rs.setSuccess(true);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setMsg("认证失败");
            rs.setSuccess(true);
            return rs;
        }
    }

}
