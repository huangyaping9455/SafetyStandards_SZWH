package org.springblade.train.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 课件内容实体类
 */
@Data
public class CoursewareModel implements Serializable {
	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 初始名称
	 */
	private String originalName;

	/**
	 * m3u8地址
	 */
	private String mediaUrl;

	/**
	 * 源文件
	 */
	private String sourceFile;

	/**
	 * 推广图片
	 */
	private String advertiseImage;

	/**
	 * 课件时长(秒)
	 */
	private Integer duration;

	/**
	 * 课件描述
	 */
	private String description;

	/**
	 * 课件类型 视频-1，文件-2，直播-3
	 */
	private Integer courseType;
	
	/**
	 * 已学习的时长(秒)
	 */
	private Integer studyDuration;
	
	/**
	 * 直播开始时间
	 */
	private String startTime;
	
	/**
	 * 直播结束时间
	 */
	private String endTime;
	
	/**
	 * 课件开始时间
	 */
	private String coursewareBeginTime;
	
	/**
	 * 课件结束时间
	 */
	private String coursewareEndTime;
	
	/**
	 * 练习题总数
	 */
	private Integer exercisesCount;
	
	/**
	 * 系统当前时间
	 */
	private String nowTime;
	
}
