package org.springblade.train.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author hyp
 * @create 2022-09-22 17:11
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("base_exam_signatrue")
public class ExamSignatrue implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 考试ID */
    @TableField("examid")
    private Integer examid ;

    /** 电子签名 */
    @TableField("signatrueimg")
    private String signatrueimg ;

    /** 创建时间 */
    @TableField("createtime")
    private String createtime ;

    /** 学员ID */
    @TableField("studentid")
    private Integer studentid;


}
