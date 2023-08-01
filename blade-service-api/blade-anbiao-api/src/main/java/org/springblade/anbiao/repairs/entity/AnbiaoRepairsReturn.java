package org.springblade.anbiao.repairs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author author
 * @since 2023-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_repairs_return")
@ApiModel(value="AnbiaoRepairsReturn对象", description="")
public class AnbiaoRepairsReturn implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ret_id", type = IdType.UUID)
    private String retId;

    @ApiModelProperty(value = "报修单ID")
    private String retRpId;

    @ApiModelProperty(value = "问题处理，1:已解决，2：未解决")
    private Integer retIsDispose;

    @ApiModelProperty(value = "收费情况，1:收费一致，2：有出入")
    private Integer retChargingSituation;

    @ApiModelProperty(value = "满意程度，数字几代表几颗星")
    private Integer retSatisfactionDegree;

    @ApiModelProperty(value = "回访概述")
    private String retRemark;

    @ApiModelProperty(value = "客户建议")
    private String retSuggest;

    @ApiModelProperty(value = "创建时间")
    private String retCreatetime;

    @ApiModelProperty(value = "创建人ID")
    private Integer retCreateid;

    @ApiModelProperty(value = "创建人")
    private String retCreatename;

    @ApiModelProperty(value = "回访时间")
    private String retDate;


}
