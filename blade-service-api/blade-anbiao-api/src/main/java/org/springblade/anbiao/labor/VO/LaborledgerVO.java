package org.springblade.anbiao.labor.VO;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.net.URL;

/**
 * @author hyp
 * @create 2023-02-13 22:01
 */
@Data
public class LaborledgerVO {
	@ApiModelProperty(value = "主键id")
	private String aliIds;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "发放日期")
	private String aliIssueDate;

	@ApiModelProperty(value = "用品名称")
	private String aliName;

	@ApiModelProperty(value = "总人数")
	private String aliIssuePeopleNumber;

	@ApiModelProperty(value = "总数量")
	private String aliIssueQuantity;

	@ApiModelProperty(value = "姓名")
	private String alrPersonName;

	@ApiModelProperty(value = "领取数量")
	private Integer alrReceiptsNumber;

	@ApiModelProperty(value = "领取日期")
	private String alrReceiptDate;

	@ApiModelProperty(value = "签名")
	private String alrPersonAutograph;

	@ColumnWidth(15)
	@ExcelProperty("图片")
	private URL imgUrl;
}
