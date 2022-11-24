package org.springblade.train.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: 课程表entity
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("biz_test_record")
public class Exam implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 开始时间 */
    @TableField("begin_time")
    private String beginTime ;

    /** 结束时间 */
    @TableField("end_time")
    private String endTime ;

    /** 学员ID */
    @TableField("student_id")
    private int studentId ;

    /** 课程ID */
    @TableField("course_id")
    private int courseId ;

    /** 考试用时(秒) */
    @TableField("duration")
    private int duration ;

    /** 考试分数 */
    @TableField("score")
    private int score ;

    /** 考试结果 1：通过，0：不通过 */
    @TableField("result")
    private int result ;
}
