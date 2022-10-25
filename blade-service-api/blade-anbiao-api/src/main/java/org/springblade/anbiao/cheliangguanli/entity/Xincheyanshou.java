/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.anbiao.cheliangguanli.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 */
@Data
@TableName("anbiao_xincheyanshou")
@ApiModel(value = "Xincheyanshou对象", description = "Xincheyanshou对象")
public class Xincheyanshou implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
	@TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 单位id
     */
    @ApiModelProperty(value = "单位id", required = true)
    private Integer deptId;
    /**
     * 车属单位
     */
    @ApiModelProperty(value = "车属单位")
    private String cheshudanwei;
    /**
     * 车辆类型
     */
    @ApiModelProperty(value = "车辆类型")
    private String cheliangleixing;
    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String caozuoren;
    /**
     * 操作人id
     */
    @ApiModelProperty(value = "操作人id")
    private Integer caozuorenid;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private String caozuoshijian;
    /**
     * 送车单位
     */
    @ApiModelProperty(value = "送车单位")
    private String songchedanwei;
    /**
     * 送车人
     */
    @ApiModelProperty(value = "送车人")
    private String songcheren;
    /**
     * 验车人
     */
    @ApiModelProperty(value = "验车人")
    private String yancheren;
    /**
     * 验车时间
     */
    @ApiModelProperty(value = "验车时间")
    private String yancheshijian;
    /**
     * 改进验车人
     */
    @ApiModelProperty(value = "改进验车人")
    private String gaijinyancheren;
    /**
     * 改进时间
     */
    @ApiModelProperty(value = "改进时间")
    private String gaijinshijian;
    /**
     * 复检验车人
     */
    @ApiModelProperty(value = "复检验车人")
    private String fujianyancheren;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String beizhu;
    /**
     * 补充记录
     */
    @ApiModelProperty(value = "补充记录")
    private String buchongjilu;
    /**
     * 改进事项
     */
    @ApiModelProperty(value = "改进事项")
    private String gaijinshixiang;
    /**
     * 复检情况
     */
    @ApiModelProperty(value = "复检情况")
    private String fujianqingkuang;
    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String fujian;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Integer isdelete;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间",required = true)
	private String createtime;
}
