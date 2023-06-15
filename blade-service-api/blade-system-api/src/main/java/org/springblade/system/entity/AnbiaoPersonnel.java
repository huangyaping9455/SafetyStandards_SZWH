package org.springblade.system.entity;

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
 * @since 2023-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_personnel")
@ApiModel(value="AnbiaoPersonnel对象", description="")
public class AnbiaoPersonnel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private Integer userid;

    private String caozuoren;

    private Integer caozuorenid;

    private LocalDateTime caozuoshijian;

    private String xingming;

    private String shenfenzheng;

    private String shoujihao;

    private String chushengriqi;

    private String qitalianxifangshi;

    private String youxiang;

    private String jiatingdizhi;

    private String gonghao;

    private String ruzhiriqi;

    private String gongzuojingli;

    private String beizhu;

    private String fujian;

    private Integer isDeleted;

    private LocalDateTime createtime;

    private Integer deptId;

    private Integer postId;

    @ApiModelProperty(value = "身份证附件正面")
    private String shenfenzhengfujian;

    @ApiModelProperty(value = "身份证附件反面")
    private String shenfenzhengfanmianfujian;

    @ApiModelProperty(value = "其他附件证明")
    private String qitazhengmianfujian;

    @ApiModelProperty(value = "其他附件反面")
    private String qitafanmianfujian;


}
