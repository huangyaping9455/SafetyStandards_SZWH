package org.springblade.train.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 练习题实体
 */
@Data
public class ExercisesModel implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 课件id
     */
    private Integer courseWareId;
    
    /**
     * 题目内容
     */
    private String content;
    
	/**
	 * 题目类型(0单选，1多选)
	 */
	private Integer category;

	/**
	 * 图片地址（可能有多个）
	 */
	private String imageUrls;

	/**
	 * 视频地址（可能有多个）
	 */
	private String videoUrls;
    
    /**
     * 题目解析
     */
    private String analysis;
    
	/**
	 * 练习题答案
	 */
	@TableField(exist = false)
	private List<ExerciseAnswerModel> exerciseAnswerList;
    

}
