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
@TableName("biz_exam")
public class BizExam implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "student_id", type = IdType.INPUT)
    private Integer studentId;

    /** 申请时间 */
    @TableField("apply_time")
    private String applyTime ;

    /** 考试开始时间 */
    @TableField("exam_begin_time")
    private String examBeginTime ;

    /** 考试结束时间 */
    @TableField("exam_end_time")
    private String examEndTime ;

    /** 状态 (0 已申请 1已安排考试 2 考核未通过 3 考核通过) */
    @TableField("status")
    private int status ;

    /** 分数 */
    @TableField("score")
    private int score ;

}
