package org.springblade.anbiao.yinhuanpaicha.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 隐患排查信息
 * </p>
 *
 * @author hyp
 * @since 2022-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_hidden_danger")
@ApiModel(value="AnbiaoHiddenDanger对象", description="隐患排查信息")
public class AnbiaoHiddenDanger implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "隐患主键")
    @TableId(value = "ahd_ids", type = IdType.UUID)
    private String ahdIds;

    @ApiModelProperty(value = "企业主键")
    private String ahdDeptIds;

    @ApiModelProperty(value = "车辆主键")
    private String ahdVehicleIds;

    @ApiModelProperty(value = "隐患分类(0=人的不安全行为,1=物的不安全状态,2=管理上的缺陷,3=环境因素)")
    private String ahdType;

    @ApiModelProperty(value = "驾驶员主键")
    private String ahdDriverIds;

    @ApiModelProperty(value = "驾驶员姓名")
    private String ahdDriverName;

    @ApiModelProperty(value = "隐患发现人主键")
    private String ahdDiscovererIds;

    @ApiModelProperty(value = "隐患发现人姓名")
    private String ahdDiscovererName;

    @ApiModelProperty(value = "隐患发现时间")
    private String ahdDiscoveryTime;

    @ApiModelProperty(value = "隐患描述")
    private String ahdDescribe;

    @ApiModelProperty(value = "隐患附件(以英文分号分割)")
    private String ahdHiddendangerEnclosure;

    @ApiModelProperty(value = "是否整改((0.否,1.是))")
    private String ahdRectificationSituation;

    @ApiModelProperty(value = "整改人主键")
    private String ahdRectificationPersionIds;

    @ApiModelProperty(value = "整改时间")
    private String ahdRectificationTime;

    @ApiModelProperty(value = "整改人姓名")
    private String ahdRectificationPersionName;

    @ApiModelProperty(value = "整改附件")
    private String ahdRectificationEnclosure;

    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    private String ahdDelete;

    @ApiModelProperty(value = "创建时间")
    private String ahdCreateTime;

    @ApiModelProperty(value = "创建人主键")
    private String ahdCreateByIds;

    @ApiModelProperty(value = "创建人姓名")
    private String ahdCreateByName;

    @ApiModelProperty(value = "更新时间")
    private String ahdUpdateTime;

    @ApiModelProperty(value = "更新人主键")
    private String ahdUpdateByIds;

    @ApiModelProperty(value = "更新人姓名")
    private String ahdUpdateByName;

    @ApiModelProperty(value = "排查现场")
    private String ahdAddress;

    @ApiModelProperty(value = "审核人ID")
    private Integer ahdAuditId;

    @ApiModelProperty(value = "审核人姓名")
    private String ahdAuditName;

    @ApiModelProperty(value = "审核时间")
    private String ahdAuditTime;

	@ApiModelProperty(value = "排查地点")
	private String ahdPlace;

}
