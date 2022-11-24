package org.springblade.train.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel(value = "StudyCountInfo对象", description = "StudyCountInfo对象")
public class StudyCountInfo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID")
	@TableField(exist = false)
    private Integer unitId;

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
    private String unitName;

	@ApiModelProperty(value = "累计注册人员")
	@TableField(exist = false)
    private int registerNumber = 0 ;

	@ApiModelProperty(value = "应学人数")
	@TableField(exist = false)
    private int shouldLearnNumber = 0 ;

	@ApiModelProperty(value = "在学人数")
	@TableField(exist = false)
    private int inStudyNumber = 0 ;

	@ApiModelProperty(value = "应学课程数")
	@TableField(exist = false)
    private int courseNumber = 0 ;

	@ApiModelProperty(value = "今日学习人数")
	@TableField(exist = false)
    private int todayNumber = 0 ;

}
