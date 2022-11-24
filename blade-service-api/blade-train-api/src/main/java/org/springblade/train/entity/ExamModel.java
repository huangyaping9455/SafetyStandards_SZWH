package org.springblade.train.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 练习题实体
 */
@Data
public class ExamModel implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 课程id
     */
    private Integer courseId;
    
    /**
     * 题目内容
     */
    private String content;
    
	/**
	 * 题目类型(0单选，1多选，2判断，3填空)
	 */
	private Integer category;
    
    /**
     * 题目解析
     */
    private String analysis;
    
	/**
	 * 练习题答案
	 */
	@TableField(exist = false)
	private List<ExamAnswerModel> exerciseAnswerList;
    

}
