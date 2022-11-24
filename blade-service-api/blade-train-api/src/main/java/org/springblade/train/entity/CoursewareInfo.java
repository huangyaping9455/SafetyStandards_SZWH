package org.springblade.train.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel(value = "CoursewareInfo对象", description = "CoursewareInfo对象")
public class CoursewareInfo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程ID")
	private Integer courseId;

	@ApiModelProperty(value = "课件名称")
	private String coursewareName;

	@ApiModelProperty(value = "课件ID")
	private String coursewareId;

	@ApiModelProperty(value = "课件全称")
	private String originalName;

	@ApiModelProperty(value = "课件来源 营运商1，企业2，代理商3，政府部门9")
	private String courseType;

	@ApiModelProperty(value = "课件时长")
	private String duration ;

	@ApiModelProperty(value = "多媒体地址，m3u8的地址")
	private String mediaUrl;

	@ApiModelProperty(value = "源文件格式")
	private String sourceFile;

}
