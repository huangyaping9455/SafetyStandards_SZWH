package org.springblade.train.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>Description: [课件内容实体类]
 */
@Data
@TableName("base_courseware")
public class Courseware implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 初始名称
	 */
	@TableField("original_name")
	private String originalName;

	/**
	 * 课件编号
	 */
	@TableField("code")
	private Long code;

	/**
	 * 源文件
	 */
	@TableField("source_file")
	private String sourceFile;
	
	/**
	 * 多媒体地址，m3u8的地址
	 */
	@TableField("media_url")
	private String mediaUrl;

	/**
	 * 推广图片
	 */
	@TableField("advertise_image")
	private String advertiseImage;

	/**
	 * 课件时长(秒)
	 */
	@TableField("duration")
	private Integer duration;

	/**
	 * 关键词
	 */
	@TableField("keyword")
	private String keyword;

	/**
	 * 课件描述
	 */
	@TableField("description")
	private String description;

	/**
	 * 课件所属ID
	 */
	@TableField("unit_id")
	private Integer unitId;

	/**
	 * 出版(发文单位/讲师)
	 */
	@TableField("publisher")
	private String publisher;

	/**
	 * 课件来源 营运商1，企业2，代理商3，政府部门9
	 */
	@TableField("source_type")
	private Integer sourceType;

	/**
	 * 课件类型 视频-1，文件-2，直播-3
	 */
	@TableField("course_type")
	private Integer courseType = 1;
	
	/**
	 * 状态 待审核-0，不通过-1，通过-2， 上架-3，下架-4，暂停-5
	 */
	@TableField("status")
	private Integer status = 0;

	/**
	 * 备注
	 */
	@TableField("memo")
	private String memo;

	/**
	 * 创建人
	 */
	@TableField("created_by")
	private String createdBy;

	/**
	 * 创建时间
	 */
	@TableField("created_time")
	private String createdTime;

	/**
	 * 更新人
	 */
	@TableField("updated_by")
	private String updatedBy;

	/**
	 * 更新时间
	 */
	@TableField("updated_time")
	private String updatedTime;
	
	// 调用第三方，直播所需配置

	/**
	 * 直播间Id
	 */
	@TableField("room_id")
	private Long roomId;
	
	/**
	 * 直播间标题
	 */
	@TableField("title")
	private String title;
	
	/**
	 * 直播开始时间
	 */
	@TableField("start_time")
	private String startTime;
	
	/**
	 * 直播结束时间
	 */
	@TableField("end_time")
	private String endTime;
	
	/**
	 * 系统当前时间
	 */
	@TableField(exist = false)
	private String nowTime;
	
	/**
	 * 管理员参加码
	 */
	@TableField("admin_code")
	private String adminCode;
	
	/**
	 * 老师参加码
	 */
	@TableField("teacher_code")
	private String teacherCode;
	
	/**
	 * 学生参加码
	 */
	@TableField("student_code")
	private String studentCode;
	
	/**
	 * 老师直播URL
	 */
	@TableField("teacher_url")
	private String teacherUrl;
	
	/**
	 * 学生可提前进入时间
	 */
	@TableField("pre_enter_time")
	private Integer preEnterTime;
	
	/**
	 * 老师姓名
	 */
	@TableField("teacher_name")
	private String teacherName;
	
	/**
	 * 练习题总数
	 */
	@TableField(exist = false)
	private Integer exercisesCount;
	
	/**
	 * 已学习时长
	 */
	@TableField(exist = false)
	private Integer studyDuration;
	
	/**
	 * 课件开始时间
	 */
	@TableField(exist = false)
	private String coursewareBeginTime;
	
	/**
	 * 课件结束时间
	 */
	@TableField(exist = false)
	private String coursewareEndTime;
}
