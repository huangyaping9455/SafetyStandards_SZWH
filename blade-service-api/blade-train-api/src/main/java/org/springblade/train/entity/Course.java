package org.springblade.train.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 课程表entity
 */
@Data
@TableName("base_course")
public class Course implements Serializable {

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

    /** 课程名称 */
    @TableField("name")
    private String name ;

    /** 区域id */
    @TableField(exist = false)
    private int areaId ;

    /** 推广图片 */
    @TableField("advertise_image")
    private String advertiseImage ;

    /** 关键词 */
    @TableField("keyword")
    private String keyword ;

    /** 课程描述 */
    @TableField("description")
    private String description ;

    /** 备注 */
    @TableField("memo")
    private String memo ;

    /** 删除标识 删除为1，默认为0 */
    @TableField(exist = false)
    private int deleted ;

    /** 状态  上架-1，下架-2 */
    @TableField(exist = false)
    private int status ;

    /** 课程来源 营运商1，企业2，政府部门3 */
    @TableField("source_type")
    private int sourceType ;

    /** 课程类型:0-直播，1-视频文件 */
    @TableField("course_type")
    private int courseType ;

    /** 来源id 原始课程的id */
    @TableField(exist = false)
    private int sourceId ;

    /** 代理商id */
    @TableField(exist = false)
    private int unitId ;

    /** 行业id */
    @TableField("course_kind_id")
    private int courseKindId ;

    /** 行业名称 */
    @TableField(exist = false)
    private String courseKindName ;

    /** 行业id */
    @TableField(exist = false)
    private int tradeKindId ;

    /** 行业名称 */
    @TableField(exist = false)
    private String tradeKindName ;

    /** 服务商id */
    @TableField(exist = false)
    private int serverId ;

    /** 服务商名称 */
    @TableField(exist = false)
    private String serverName ;

    /** 区域名称 */
    @TableField(exist = false)
    private String areaName ;

    /** 原始课程的名称 */
    @TableField(exist = false)
    private String sourceName ;

    /** 代理商名称 */
    @TableField(exist = false)
    private String simpleName ;

    /** 代理商名称 */
    @TableField(exist = false)
    private String fullName ;

    /** 课程付费 免费-0，企业付款-1，学员付款-2 */
    @TableField(exist = false)
    private int courseFee ;

    /** 上课方式 远程课程-1，现场课程-2，现场直播-3，远程直播-4 */
    @TableField(exist = false)
    private int studyType ;

    /** 考试分数 0为不考试，大于0的数据为及格线 */
    @TableField(exist = false)
    private int score ;

    /** 验证方式 不验证-0，人脸验证-1，位置验证-2，人脸位置-3 */
    @TableField(exist = false)
    private int verification ;

    /** 开始时间 */
    @TableField(exist = false)
    private String beginTime ;

    /** 结束时间 */
    @TableField(exist = false)
    private String endTime ;

    /** 考试时间 */
    @TableField(exist = false)
    private int examDuration ;

    /** 是否可操作 */
    @TableField(exist = false)
    private int isOperation ;

    /** 考试总分 */
    @TableField(exist = false)
    private int totalScores ;

    /** 课程价格 */
    @TableField(exist = false)
    private double price ;

    /** 考题数量 */
    @TableField(exist = false)
    private int testCount ;

    /** 题库数量 */
    @TableField(exist = false)
    private int questionCount ;

    /** 文件课件 */
    @TableField(exist = false)
    private List<CourseWareDetailModel> courseWareFileList;

    /** 视频课件 */
    @TableField(exist = false)
    private List<CourseWareDetailModel> courseWareVideoList;

    /** 课程数量 */
    @TableField(exist = false)
    private int courseCount ;
}
