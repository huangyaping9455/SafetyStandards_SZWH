package org.springblade.train.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 练习题答案实体
 */
@Data
public class ExerciseAnswerModel implements Serializable {

    /**
     * 主键id
     */
    private Integer id;
    
    /**
     * 选项编号
     */
    private Integer orderNumber;
    
    /**
     * 选项内容
     */
    private String content;
    
    /**
     * 正确答案 答案选项 1，否则0
     */
    private Integer checked;
    
}
