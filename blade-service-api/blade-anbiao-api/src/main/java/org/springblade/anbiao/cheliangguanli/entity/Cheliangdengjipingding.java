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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 * @author 呵呵哒
 */
@Data
@TableName("anbiao_cheliangdengjipingding")
@ApiModel(value = "Cheliangdengjipingding对象", description = "Cheliangdengjipingding对象")
public class Cheliangdengjipingding implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
	@TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 单位ID
     */
    @ApiModelProperty(value = "单位ID",required = true)
    private Integer deptId;
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
     * 车辆id
     */
    @ApiModelProperty(value = "车辆id",required = true)
    private String cheliangid;
    /**
     * 类型等级
     */
    @ApiModelProperty(value = "类型等级")
    private String leixingdengji;
    /**
     * 技术等级
     */
    @ApiModelProperty(value = "技术等级")
    private String jishudengji;
    /**
     * 评定单号
     */
    @ApiModelProperty(value = "评定单号")
    private String pingdingdanhao;
    /**
     * 评定单位
     */
    @ApiModelProperty(value = "评定单位")
    private String pingdingdanwei;
    /**
     * 评定日期
     */
    @ApiModelProperty(value = "评定日期")
    private String pingdingriqi;
    /**
     * 评定有效期
     */
    @ApiModelProperty(value = "评定有效期",required = true)
    private String pingdingyouxiaoqi;
    /**
     * 检测单位
     */
    @ApiModelProperty(value = "检测单位")
    private String jiancedanwei;
    /**
     * 检测日期
     */
    @ApiModelProperty(value = "检测日期")
    private String jianceriqi;
    /**
     * 客车等级
     */
    @ApiModelProperty(value = "客车等级")
    private String kechedengji;
    /**
     * 记录人员
     */
    @ApiModelProperty(value = "记录人员")
    private String jilurenyuan;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String beizhu;
    /**
     * 有效证明
     */
    @ApiModelProperty(value = "有效证明")
    private String youxiaozhengming;
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
