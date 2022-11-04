package org.springblade.anbiao.jiaoyupeixun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 安全生产培训(线下)
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_safety_training")
@ApiModel(value="AnbiaoSafetyTraining对象", description="安全生产培训(线下)")
public class AnbiaoSafetyTraining implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "安全培训主键")
    @TableId(value = "ast_ids", type = IdType.UUID)
    private String astIds;

    @ApiModelProperty(value = "企业主键")
    private String astDeptIds;

    @ApiModelProperty(value = "培训主题")
    private String astTrainingTopic;

    @ApiModelProperty(value = "培训类别(0=岗前培训,1=日常培训)")
    private String astTrainingCategory;

    @ApiModelProperty(value = "培训形式(0=线上,1=线下)默认1")
    private String astTrainingForm;

    @ApiModelProperty(value = "培训开始时间")
    private String astTrainingStartTime;

    @ApiModelProperty(value = "培训结束时间")
    private String astTrainingEndTime;

    @ApiModelProperty(value = "培训时长(单位：小时)")
    private String astTrainingDuration;

    @ApiModelProperty(value = "应参加人数")
    private Integer astShouldAttendNumber;

    @ApiModelProperty(value = "实际参加人数")
    private Integer astActualParticipation;

    @ApiModelProperty(value = "培训内容")
    private String astTrainingContent;

    @ApiModelProperty(value = "参会人员")
    private String astParticipants;

    @ApiModelProperty(value = "现场照片")
    private String astSitePhotos;

    @ApiModelProperty(value = "附件")
    private String astEnclosure;

    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    private String astDelete;

    @ApiModelProperty(value = "创建时间")
    private String astCreateTime;

    @ApiModelProperty(value = "创建人主键")
    private String astCreateByIds;

    @ApiModelProperty(value = "创建人姓名")
    private String astCreateByName;

    @ApiModelProperty(value = "更新时间")
    private String astUpdateTime;

    @ApiModelProperty(value = "更新人主键")
    private String astUpdateByIds;

    @ApiModelProperty(value = "更新人姓名")
    private String astUpdateByName;

	@ApiModelProperty(value = "参加培训人员明细List")
	@TableField(exist = false)
	private List<AnbiaoSafetyTrainingDetail> safetyTrainingDetailList;


}
