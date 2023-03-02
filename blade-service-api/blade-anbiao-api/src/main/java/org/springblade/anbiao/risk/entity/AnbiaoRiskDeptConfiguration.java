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
@TableName("anbiao_risk_dept_configuration")
@ApiModel(value="AnbiaoRiskDeptConfiguration对象", description="")
public class AnbiaoRiskDeptConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "风险配置表id")
    private String rcId;

    @ApiModelProperty(value = "企业id")
    private String deptId;

    @ApiModelProperty(value = "是否删除，0默认正常")
    private String isDeleted;

    @ApiModelProperty(value = "0为未启用，1为启用")
    private String status;

	@ApiModelProperty(value = "更新时间")
	private String updatetime;

	@ApiModelProperty(value = "操作人")
	private String caozuoren;

	@ApiModelProperty(value = "创建时间")
	private String creattime;

	@ApiModelProperty(value = "创建人")
	private String chuangjianren;
}
