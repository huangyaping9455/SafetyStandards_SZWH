package org.springblade.anbiao.lawsRegulations.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @author hyp
 * @since 2023-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_laws_regulations")
@ApiModel(value="AnbiaoLawsRegulations对象", description="AnbiaoLawsRegulations对象")
public class AnbiaoLawsRegulations implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文件名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "分类")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "文号")
    @TableField("number")
    private String number;

    @ApiModelProperty(value = "发布机关")
    @TableField("authority")
    private String authority;

    @ApiModelProperty(value = "文件状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "发布日期")
    @TableField("releaseDate")
    private String releaseDate;

    @ApiModelProperty(value = "实施日期")
    @TableField("materialDate")
    private String materialDate;

    @ApiModelProperty(value = "文件地址")
    @TableField("fileUrl")
    private String fileUrl;

    @ApiModelProperty(value = "创建时间")
    @TableField("createTime")
    private String createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("createUser")
    private Integer createUser;

    @ApiModelProperty(value = "是否删除")
    @TableField("isdelete")
    private Integer isdelete;

    @ApiModelProperty(value = "更新时间")
    @TableField("updatetime")
    private String updatetime;

    @ApiModelProperty(value = "更新用户")
    @TableField("updateuser")
    private Integer updateuser;

    @ApiModelProperty(value = "pdf地址")
    @TableField("filePdfUrl")
    private String filePdfUrl;


}
