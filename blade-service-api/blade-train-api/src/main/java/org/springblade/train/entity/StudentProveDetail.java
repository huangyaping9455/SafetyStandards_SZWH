package org.springblade.train.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "StudentProveDetail对象", description = "StudentProveDetail对象")
public class StudentProveDetail implements Serializable {

	@ApiModelProperty("课件Id")
	private Integer id;

	@ApiModelProperty("课件名称")
    private String coursewareName;

	@ApiModelProperty("开始学习时间")
    private String beginTime;

	@ApiModelProperty("结束学习时间")
    private String endTime;

	@ApiModelProperty("学习时长")
    private Integer duration;

	@ApiModelProperty("是否有效 1有效，0无效")
    private Integer valid;
}
