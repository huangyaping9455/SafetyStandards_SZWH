package org.springblade.train.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 培训列表
 */
@Data
public class TrainingListModelVo implements Serializable {

	/**
	 * 序号
	 */
	private Integer rowIndex;

	/**
	 * 姓名
	 */
	private String realName;

	/**
	 * 性别
	 */
	private String sex = "女";

	/**
	 * 职位
	 */
	private String station;

	/**
	 * 手机号码
	 */
	private String cellphone;

	/**
	 * 培训起止时间
	 */
	private String studyTime;

	/**
	 * 总学时(小时)
	 */
	private String zduration;

	/**
	 * 累计完成学时(小时)
	 */
	private String stuDuration;

	/**
	 * 学习进度
	 */
	private String studyProgress = "0.00%";

	/**
	 * 是否签到
	 */
	private String isSignatrue = "未签到";

	/**
	 * 考试得分
	 */
	private Integer score = 0;

}
