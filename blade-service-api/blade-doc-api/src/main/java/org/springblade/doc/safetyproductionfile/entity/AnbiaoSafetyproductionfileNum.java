package org.springblade.doc.safetyproductionfile.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @author hyp
 * @since 2023-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_safetyproductionfile_num")
@ApiModel(value="AnbiaoSafetyproductionfileNum对象", description="")
public class AnbiaoSafetyproductionfileNum implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "企业ID")
	@TableField("deptId")
    private Integer deptId;

    @ApiModelProperty(value = "应传文件数")
	@TableField("uploadedNum")
    private Integer uploadedNum;

    @ApiModelProperty(value = "完成数")
	@TableField("finshNum")
    private Integer finshNum;

    @ApiModelProperty(value = "完成比例")
	@TableField("finshRatio")
    private String finshRatio;

    @ApiModelProperty(value = "操作时间")
	@TableField("caozuoshijian")
    private String caozuoshijian;

	@ApiModelProperty(value = "统计日期")
	@TableField("date")
	private String date;

	@ApiModelProperty(value = "政府ID")
	@TableField(exist = false)
	private int zhengfuid;

	@ApiModelProperty(value = "政府名称")
	@TableField(exist = false)
	private String zhengfuname;

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String deptName;


}
