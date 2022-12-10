package org.springblade.anbiao.risk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 安标风险统计信息
 * </p>
 *
 * @author lmh
 * @since 2022-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_risk_detail_info")
@ApiModel(value="AnbiaoRiskDetailInfo对象", description="安标风险统计信息")
public class AnbiaoRiskDetailInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ard_ids", type = IdType.AUTO)
    private Long ardIds;

    @ApiModelProperty(value = "风险主键")
    private String ardRiskIds;

    @ApiModelProperty(value = "整改人主键")
    private String ardRectificationByIds;

    @ApiModelProperty(value = "整改人姓名")
    private String ardRectificationByName;

    @ApiModelProperty(value = "整改时间")
    private String ardRectificationDate;

    @ApiModelProperty(value = "整改字段名称")
    private String ardRectificationField;

    @ApiModelProperty(value = "整改字段值")
    private String ardRectificationValue;

    @ApiModelProperty(value = "整改字段类型")
    private String ardRectificationFieldType;


}
