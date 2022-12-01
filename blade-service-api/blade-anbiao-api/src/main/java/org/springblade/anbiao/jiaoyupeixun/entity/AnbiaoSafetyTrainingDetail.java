package org.springblade.anbiao.jiaoyupeixun.entity;

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
 * 安全会议参会记录
 * </p>
 *
 * @author hyp
 * @since 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_safety_training_detail")
@ApiModel(value="AnbiaoSafetyTrainingDetail对象", description="安全会议参会记录")
public class AnbiaoSafetyTrainingDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参培明细主键")
    @TableId(value = "aad_ids", type = IdType.UUID)
    private String aadIds;

    @ApiModelProperty(value = "安全培训主键")
    private String aadAstIds;

    @ApiModelProperty(value = "参培人主键",required = true)
    private String aadApIds;

    @ApiModelProperty(value = "参培人名称",required = true)
    private String aadApName;

    @ApiModelProperty(value = "参培人员类别(0=安全管理员,1=从业人员)",required = true)
    private String aadApType;

    @ApiModelProperty(value = "参培人头像",required = true)
    private String addApHeadPortrait;

    @ApiModelProperty(value = "参培人签名",required = true)
    private String addApAutograph;

    @ApiModelProperty(value = "是否参培(0=否,1=是)")
    private String addApBeingJoined;

    @ApiModelProperty(value = "参培时间",required = true)
    private String addTime;


}
