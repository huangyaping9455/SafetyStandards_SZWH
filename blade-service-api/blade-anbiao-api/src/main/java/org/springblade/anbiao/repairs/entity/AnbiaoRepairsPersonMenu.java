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
 * @since 2023-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_repairs_person_menu")
@ApiModel(value="AnbiaoRepairsPersonMenu对象", description="维修人员菜单关联表")
public class AnbiaoRepairsPersonMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "维修人员ID")
    private String rpPersonId;

    @ApiModelProperty(value = "小程序菜单名称")
    private String rpMenuName;

    @ApiModelProperty(value = "创建时间")
    private String rpCreatetime;

    @ApiModelProperty(value = "创建人ID")
    private Integer rpCreateid;

    @ApiModelProperty(value = "创建人")
    private String rpCreatename;


}
