package org.springblade.anbiao.qiyeshouye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author author
 * @since 2023-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("baobiao_zhengfu_qiye")
@ApiModel(value="BaobiaoZhengfuQiye对象", description="")
public class BaobiaoZhengfuQiye implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "qiyeid", type = IdType.AUTO)
    private Integer qiyeid;

    private String qiyemingcheng;

    private String jigouleixing;

    private String province;

    private String city;

    private String country;

    private String yunguanmingcheng;

    private String yunguanid;

    private String areaname;


}
