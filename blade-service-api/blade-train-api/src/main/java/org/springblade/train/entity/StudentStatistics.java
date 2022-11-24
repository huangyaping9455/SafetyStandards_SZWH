package org.springblade.train.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 *
 * @Description: 学员统计
 * @author: jw.chen
 * @Date: 2020-02-19
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class StudentStatistics extends Model<StudentStatistics> implements Serializable{

	private static final long serialVersionUID = 1L;

	//id
	private Integer id;
	//id
	private Integer studentId;
	//学员姓名
	private String realname;
	//学员岗位
	private String station;
	//性别 0:未知 1:男 2:女
	private Integer sex;
	//课程id
	private Integer courseId;
	//课程名称
	private String courseName;
	//服务商id
	private Integer serviceId;
	//服务商名称
	private String serviceName;
	//企业id
	private Integer unitId;
	//企业名称
	private String unitName;
	//课程学习-开始时间
	private String courseBeginTime;
	//课程学习-结束时间
	private String courseEndTime;
	//上课方式：远程课程-1，现场课程-2，现场直播-3，远程直播-4
	private Integer studyType;
	//考试分数：0为不考试，大于0的数据为及格线
	private Integer score;
	//验证方式 不验证-0，人脸验证-1，位置验证-2，人脸位置-3
	private Integer verification;
	//总学习时长
	private Integer duration;
	//已学习时长
	private Integer studyDuration;
	//是否完成考试：0否 大于0是
	private Integer finishExam;
	// 车牌号
	private String plateNumber;
	// 头像图片
	private String fullFacePhoto;
	// 考核分数
	private Integer finishscore = 0;
	// 身份证号
	private String identifyNumber;
	// 培训内容
	private String description;
	//电子签名
	private String signatrue;
	//抓拍图片（默认创建时间最小的一张）
	private String photoUrl;
}
