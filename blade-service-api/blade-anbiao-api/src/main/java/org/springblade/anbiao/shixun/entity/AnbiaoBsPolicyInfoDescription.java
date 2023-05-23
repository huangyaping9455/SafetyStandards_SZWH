package org.springblade.anbiao.shixun.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
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
 * @since 2023-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_bs_policy_info_description")
@ApiModel(value="AnbiaoBsPolicyInfoDescription对象", description="")
public class AnbiaoBsPolicyInfoDescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "说明主键")
    @TableId(value = "abpid_id", type = IdType.UUID)
    private String abpidId;

    @ApiModelProperty(value = "时讯主键")
    private String abpiId;

    @ApiModelProperty(value = "签名")
    private String abpidAutograph;

    @ApiModelProperty(value = "签名人主键")
    private String abpidIds;

    @ApiModelProperty(value = "签名人姓名")
    private String abpidName;

    @ApiModelProperty(value = "签字时间")
    private String abpidTime;


}
