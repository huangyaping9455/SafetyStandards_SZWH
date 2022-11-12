package org.springblade.anbiao.AccidentReports.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.common.BasePage;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/8 18:50
 */
@Data
public class AccidentPage extends BasePage{
	private String id;
	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private String asiYear;

	@ApiModelProperty(value = "单位id",required = true)
	private Integer deptId;

	private Date shigufashengshijian;

	private String shigufashengdidian;

	private String shigufenlei;

	private String shiguxingzhi;

	private String shiguzeren;

	private String chepaihao;

	private String jiashiyuan;

	private String deptName;
}
