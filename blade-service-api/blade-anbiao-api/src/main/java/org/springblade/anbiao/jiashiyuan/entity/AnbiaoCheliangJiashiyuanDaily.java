package org.springblade.anbiao.jiashiyuan.entity;

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
 * @since 2022-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_cheliang_jiashiyuan_daily")
@ApiModel(value="AnbiaoCheliangJiashiyuanDaily对象", description="")
public class AnbiaoCheliangJiashiyuanDaily implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "车辆ID")
    private String vehid;

    @ApiModelProperty(value = "驾驶员ID")
    private String jiashiyuanid;

    @ApiModelProperty(value = "操作时间")
    private String createtime;

	@ApiModelProperty(value = "挂车车辆ID")
	private String gvehid;

	@ApiModelProperty(value = "车辆使用状态")
	private int vstatus = 0;

	@ApiModelProperty(value = "挂车使用状态")
	private int gstatus = 0;

	@ApiModelProperty(value = "更新时间")
	private String updatetime;

}


