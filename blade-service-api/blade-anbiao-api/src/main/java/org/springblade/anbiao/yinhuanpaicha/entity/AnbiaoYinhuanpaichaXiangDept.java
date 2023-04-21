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
@TableName("anbiao_yinhuanpaicha_xiang_dept")
@ApiModel(value="AnbiaoYinhuanpaichaXiangDept对象", description="AnbiaoYinhuanpaichaXiangDept对象")
public class AnbiaoYinhuanpaichaXiangDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "上级ID")
    private Integer parentid;

    @ApiModelProperty(value = "企业ID")
    private String deptid;

    @ApiModelProperty(value = "隐患项ID")
    private String xiangid;

    private Integer isdelete;

    @ApiModelProperty(value = "创建者ID")
    private Integer createid;

    @ApiModelProperty(value = "创建时间")
    private String createtime;

    @ApiModelProperty(value = "更新者ID")
    private Integer updateid;

    @ApiModelProperty(value = "更新时间")
    private String updatetime;

}
