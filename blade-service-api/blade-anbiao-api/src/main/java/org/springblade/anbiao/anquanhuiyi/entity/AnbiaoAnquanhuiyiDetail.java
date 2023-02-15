package org.springblade.anbiao.anquanhuiyi.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.net.URL;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 安全会议参会记录
 * </p>
 *
 * @author lmh
 * @since 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_anquanhuiyi_detail")
@ApiModel(value="AnbiaoAnquanhuiyiDetail对象", description="安全会议参会记录")
public class AnbiaoAnquanhuiyiDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参会明细主键")
    @TableId(value = "aad_ids", type = IdType.UUID)
    private String aadIds;

    @ApiModelProperty(value = "会议主键")
    @TableField("aad_aa_ids")
    private String aadAaIds;

    @ApiModelProperty(value = "参会人主键",required = true)
    @TableField("aad_ap_ids")
    private String aadApIds;

    @ApiModelProperty(value = "参会人名称",required = true)
    @TableField("aad_ap_name")
    private String aadApName;

    @ApiModelProperty(value = "参会人员类别(0=安全管理员,1=从业人员)",required = true)
    @TableField("aad_ap_type")
    private String aadApType;

    @ApiModelProperty(value = "参会人头像",required = true)
    @TableField("add_ap_head_portrait")
    private String addApHeadPortrait;

    @ApiModelProperty(value = "参会人签名",required = true)
    @TableField("add_ap_autograph")
    private String addApAutograph;

    @ApiModelProperty(value = "是否参会(0=否,1=是)")
    @TableField("add_ap_being_joined")
    private String addApBeingJoined = "0";

    @ApiModelProperty(value = "参会时间",required = true)
    @TableField("add_time")
    private String addTime;

	@ColumnWidth(15)
	@ExcelProperty("图片")
	@TableField(exist = false)
	private URL imgUrl;


}
