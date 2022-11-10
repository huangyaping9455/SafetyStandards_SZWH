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
 * @since 2022-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_cheliang_jiashiyuan")
@ApiModel(value="AnbiaoCheliangJiashiyuan对象", description="")
public class AnbiaoCheliangJiashiyuan implements Serializable {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "车辆ID")
    private String vehid;

    @ApiModelProperty(value = "驾驶员ID")
    private String jiashiyuanid;

    @ApiModelProperty(value = "操作时间")
    private String createtime;

    @ApiModelProperty(value = "1为ios 2 为安卓")
    private String type;


}
