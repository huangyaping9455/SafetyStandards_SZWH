package org.springblade.anbiao.risk.entity;

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
 * @author lmh
 * @since 2023-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_risk_configuration")
@ApiModel(value="AnbiaoRiskConfiguration对象", description="")
public class AnbiaoRiskConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "预警项")
    private String yujingxiang;

    @ApiModelProperty(value = "说明")
    private String shuoming;

    @ApiModelProperty(value = "是否删除，0默认正常")
    private String isDeleted;

    @ApiModelProperty(value = "更新时间")
    private String updatetime;

	@ApiModelProperty(value = "操作人")
	private String caozuoren;

	@ApiModelProperty(value = "创建时间")
	private String creattime;

	@ApiModelProperty(value = "创建人")
	private String chuangjianren;
}
