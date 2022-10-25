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
package org.springblade.anbiao.jiashiyuan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 */
@Data
@TableName("anbiao_zhengjianshenyan")
@ApiModel(value = "Zhengjianshenyan对象", description = "Zhengjianshenyan对象")
public class Zhengjianshenyan implements Serializable {

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
    @ApiModelProperty(value = "单位id",required = true)
    private Integer deptId;
    /**
     * 驾驶员id
     */
    @ApiModelProperty(value = "驾驶员id",required = true)
    private String jiashiyuanid;
    /**
     * 驾驶员类型
     */
    @ApiModelProperty(value = "驾驶员类型")
    private String jiashiyuanleixing;
    /**
     * 证件名称
     */
    @ApiModelProperty(value = "证件名称")
    private String zhengjianmingcheng;
    /**
     * 审验有效期
     */
    @ApiModelProperty(value = "审验有效期")
    private String shenyanyouxiaoqi;
    /**
     * 审验类型
     */
    @ApiModelProperty(value = "审验类型")
    private String shenyanleixing;
    /**
     * 审验机构
     */
    @ApiModelProperty(value = "审验机构")
    private String shenyanjigou;
    /**
     * 是否合格
     */
    @ApiModelProperty(value = "是否合格")
    private String shifouhege;
    /**
     * 审验日期
     */
    @ApiModelProperty(value = "审验日期")
    private String shenyanriqi;
    /**
     * 考核等级
     */
    @ApiModelProperty(value = "考核等级")
    private String kaohedengji;
    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String fujian;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String beizhu;
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
