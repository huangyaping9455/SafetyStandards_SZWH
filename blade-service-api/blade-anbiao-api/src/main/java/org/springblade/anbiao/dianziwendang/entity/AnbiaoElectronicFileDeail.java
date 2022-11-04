package org.springblade.anbiao.dianziwendang.entity;

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
 * 电子文档签名记录表
 * </p>
 *
 * @author hyp
 * @since 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_electronic_file_deail")
@ApiModel(value="AnbiaoElectronicFileDeail对象", description="电子文档签名记录表")
public class AnbiaoElectronicFileDeail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "add_id", type = IdType.UUID)
    private String addId;

    @ApiModelProperty(value = "电子文档主键")
    private String aadAefIds;

    @ApiModelProperty(value = "签收人主键")
    private String aadApIds;

    @ApiModelProperty(value = "签收人名称")
    private String aadApName;

    @ApiModelProperty(value = "签收人类别(0=安全管理员,1=从业人员)")
    private String aadApType;

    @ApiModelProperty(value = "签收人签名")
    private String addApAutograph;

    @ApiModelProperty(value = "签收时间")
    private LocalDateTime addTime;


}
