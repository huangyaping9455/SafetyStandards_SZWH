package org.springblade.anbiao.weixiu.VO;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.net.URL;

/**
 * @author hyp
 * @create 2023-02-13 17:32
 */
@Data
@HeadRowHeight(25)
@ContentRowHeight(20)
@ApiModel(value = "MaintenanceTZVO对象", description = "MaintenanceTZVO对象")
public class MaintenanceTZVO {

	@ApiModelProperty(value = "数据ID")
	private String Id;
	@ApiModelProperty(value = "企业名称")
	private String deptName;
	@ApiModelProperty(value = "企业ID")
	private Integer	deptId;
	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;
	@ApiModelProperty(value = "驾驶员姓名")
	private String driverName;
	@ApiModelProperty(value = "维修日期")
	private String sendDate;
	@ApiModelProperty(value = "维修类别ID(0=一级维护,1=二级维护,3=总成维修)")
	private String maintainDictId;
	@ApiModelProperty(value = "维修费用")
	private String acbCost = "0.00";
	@ApiModelProperty(value = "驾驶员姓名")
	private String zgdriverName;
	@ApiModelProperty(value = "维修日期")
	private String zgsendDate;
	@ApiModelProperty(value = "维修内容")
	private String maintenanceContent;
	@ApiModelProperty(value = "维修原因")
	private String repairReason;
	@ApiModelProperty(value = "维修单位")
	private String maintenanceDeptName;
	@ApiModelProperty(value = "维修票据")
	private String billAttachment;
	@ApiModelProperty(value = "维修前照片")
	private String beforeMaintenance;
	@ApiModelProperty(value = "维修后照片")
	private String afterMaintenance;
	@ApiModelProperty(value = "维修中照片")
	private String centerMaintenance;
	@ApiModelProperty(value = "整改后日期")
	private String caozuoshijian;
	@ColumnWidth(15)
	@ExcelProperty("图片")
	private URL imgUrl;
}
