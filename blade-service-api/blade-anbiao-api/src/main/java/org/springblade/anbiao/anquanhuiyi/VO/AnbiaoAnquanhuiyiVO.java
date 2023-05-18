package org.springblade.anbiao.anquanhuiyi.VO;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiDetail;

import java.net.URL;
import java.util.List;

/**
 * @author hyp
 * @create 2023-02-15 11:11
 */
@Data
@ApiModel(value = "AnbiaoAnquanhuiyiVO对象", description = "AnbiaoAnquanhuiyiVO对象")
public class AnbiaoAnquanhuiyiVO {

//	@ApiModelProperty(value = "企业名称")
//	private String deptName;
//
//	@ApiModelProperty(value = "会议名称")
//	private String huiyimingcheng;
//
//	@ApiModelProperty(value = "会议时间")
//	private String dateShow;

	@ApiModelProperty(value = "会议提示")
	private String message;

	@ApiModelProperty(value = "安全会议主键")
	private String id;

	@ApiModelProperty(value = "企业主键",required = true)
	private Integer deptId;

	@ApiModelProperty(value = "操作人")
	private String caozuoren;

	@ApiModelProperty(value = "操作人主键")
	private Integer caozuorenid;

	@ApiModelProperty(value = "操作时间")
	private String caozuoshijian;

	@ApiModelProperty(value = "会议名称",required = true)
	private String huiyimingcheng;

	@ApiModelProperty(value = "会议编号")
	private String huiyibianhao;

//    @ApiModelProperty(value = "会议类型")
//    @TableField("huiyileixing")
//    private String huiyileixing;

	@ApiModelProperty(value = "会议形式(0=线上,1=线上)",required = true)
	private String huiyixingshi;

//    @ApiModelProperty(value = "会议日期")
//    @TableField("huiyiriqi")
//    private String huiyiriqi;

	@ApiModelProperty(value = "主持人")
	private String zhuchiren;

	@ApiModelProperty(value = "记录人")
	private String jiluren;

	@ApiModelProperty(value = "会议地点")
	private String huiyididian;

	@ApiModelProperty(value = "会议开始时间",required = true)
	private String huiyikaishishijian;

	@ApiModelProperty(value = "会议结束时间",required = true)
	private String huiyijieshushijian;

	@ApiModelProperty(value = "会议内容")
	private String huiyineirong;

	@ApiModelProperty(value = "备注")
	private String beizhu;

	@ApiModelProperty(value = "会议照片")
	private String huiyizhaopian;

	@ApiModelProperty(value = "是否删除(0=否,1=是)")
	private Integer isDeleted;

	@ApiModelProperty(value = "创建时间")
	private String createtime;

	@ApiModelProperty(value = "参会人数")
	private Integer canhuirenshu = 0;

	@ApiModelProperty(value = "附件")
	private String fujian;

	@ApiModelProperty(value = "会议人员信息")
	private List<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetails;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "参会明细主键")
	private String aadIds;

	@ApiModelProperty(value = "参会人员类别")
	private String type;

	@ApiModelProperty(value = "状态，0：未签到，1：已签到")
	private Integer status;

	@ApiModelProperty(value = "月份")
	private String months;

	@ApiModelProperty(value = "会议日期区间")
	private String dateShow;

	@ApiModelProperty(value = "实际参会人数")
	private Integer shijicanhuirenshu = 0;

	@ApiModelProperty(value = "驾驶员id")
	private String jiashiyuanid;

	@ApiModelProperty(value = "企业ID字符串（多个以英文逗号隔开）")
	private String deptIds;

	@ApiModelProperty(value = "会议ID")
	private String huiYiId;

	@ApiModelProperty(value = "会议ID")
	private String TwoDimensionalCode;

	@ColumnWidth(15)
	@ExcelProperty("二维码")
	private URL TwoDimensionalCodeUrl;
}
