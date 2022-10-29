package org.springblade.anbiao.jiashiyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * 驾驶员岗前培训表
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_jiashiyuan_gangqianpeixun")
@ApiModel(value="AnbiaoJiashiyuanGangqianpeixun对象", description="驾驶员岗前培训表")
public class AnbiaoJiashiyuanGangqianpeixun implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗前培训主键")
    @TableId(value = "ajg_ids", type = IdType.AUTO)
    private String ajgIds;

    @ApiModelProperty(value = "驾驶员信息主键")
    @TableField("ajg_aj_ids")
    private String ajgAjIds;

    @ApiModelProperty(value = "培训日期")
    @TableField("ajg_training_date")
    private LocalDate ajgTrainingDate;

    @ApiModelProperty(value = "培训附件")
    @TableField("ajg_training_enclosure")
    private String ajgTrainingEnclosure;

    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    @TableField("ajg_delete")
    private String ajgDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField("ajg_create_time")
    private LocalDateTime ajgCreateTime;

    @ApiModelProperty(value = "创建人主键")
    @TableField("ajg_create_by_ids")
    private String ajgCreateByIds;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("ajg_create_by_name")
    private String ajgCreateByName;

    @ApiModelProperty(value = "更新时间")
    @TableField("ajg_update_time")
    private LocalDateTime ajgUpdateTime;

    @ApiModelProperty(value = "更新人主键")
    @TableField("ajg_update_by_ids")
    private String ajgUpdateByIds;

    @ApiModelProperty(value = "更新人姓名")
    @TableField("ajg_update_by_name")
    private String ajgUpdateByName;


}
