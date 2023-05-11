package org.springblade.train.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 培训列表
 */
@Data
public class TrainingListModel implements Serializable {

	private Integer rowIndex;

	/**
	 * 姓名
	 */
	private String realName;

	/**
	 * 身份证号
	 */
	private String identifyNumber;

	/**
	 * 手机号码
	 */
	private String cellphone;

	private String station;

	private String studyCount;

	/**
	 * 考试得分
	 */
	private Integer score = 0;

	/**
	 * 学员ID
	 */
	private Integer studentId;

	/**
	 * 培训结果
	 */
	private String result;

	/**
	 *抓拍头像
	 */
	private String photoUrl;

	private Integer id;

	/**
	 *车牌号
	 */
	private String plateNumber;

	private List<TrainingListModel> listModelList;

	/**
	 * 已学时长
	 */
	private String stuDuration;

	/**
	 * 总学时(小时)
	 */
	private String zduration;

	/**
	 * 总学时(秒)
	 */
	private Integer duration = 0;

	/**
	 * 试卷总分
	 */
	private Integer totalScores = 0;

	/**
	 * 电子签名
	 */
	private String signatrueimg;

	/**
	 *地址
	 */
	private String address;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 题目
	 */
	private String content;

	/**
	 * 参考答案
	 */
	private String answercontent;

	/**
	 *  学习记录对象
	 */
	private List<StudyRecord> studyRecord;

	/**
	 * 考试结果
	 */
	private String examResult;

	/**
	 * 企业ID
	 */
	private String unitId;

	/**
	 * 企业名称
	 */
	private String unitName;

	/**
	 * 课程关联ID
	 */
	private String relUnitCourseId;

	/**
	 * 课程名称
	 */
	private String courseName;

	/**
	 * 培训开始时间
	 */
	private String studyBeginTime;

	/**
	 * 培训结束时间
	 */
	private String studyEndTime;

	@ApiModelProperty(value = "累计完成学时")
	@TableField(exist = false)
	private Integer studyTimeCompletion = 0;

	@ApiModelProperty(value = "学习进度")
	@TableField(exist = false)
	private String studyProgress = "0.00%";

	@ApiModelProperty(value = "是否签到")
	@TableField(exist = false)
	private String isSignatrue = "未签到";

	/**
	 * 培训起止时间
	 */
	private String studyTime;

}
