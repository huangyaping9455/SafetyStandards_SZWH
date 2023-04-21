package org.springblade.anbiao.yinhuanpaicha.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 *
 * </p>
 *
 * @author hyp
 * @since 2022-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_yinhuanpaicha_xiang_dept_info")
@ApiModel(value="AnbiaoYinhuanpaichaXiangDeptInfo对象", description="AnbiaoYinhuanpaichaXiangDeptInfo对象")
public class AnbiaoYinhuanpaichaXiangDeptInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "企业ID")
    private String deptid;

    @ApiModelProperty(value = "排查年、月份")
    private String pcyf;

    @ApiModelProperty(value = "数据ID")
    private String dataid;

    @ApiModelProperty(value = "派发人ID")
    private Integer createid;

    @ApiModelProperty(value = "创建时间")
    private String createtime;

    @ApiModelProperty(value = "审核人ID")
    private Integer updateid;

    @ApiModelProperty(value = "更新时间")
    private String updatetime;

    private Integer isdelete;

	@ApiModelProperty(value = "状态，默认0，已派发")
	private Integer status;

}
