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
package org.springblade.alarm.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author hyp
 * @since 2019-05-12
 */
@Data
@TableName("baobiao_alarmhandleresult")
@ApiModel(value = "Alarmhandleresult对象", description = "Alarmhandleresult对象")
public class Alarmhandleresult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 报警数据id
     */
    @ApiModelProperty(value = "报警数据id")
    private String baojingid;
    /**
     * 报警类型
     */
    @ApiModelProperty(value = "报警类型")
    private String baojingleixing;
    /**
     * 处理1、申诉0
     */
    @ApiModelProperty(value = "处理1、申诉0")
    private Boolean remark;
    /**
     * 处理状态（1已处理，2超时处理，3已处理未通过）
     */
    @ApiModelProperty(value = "处理状态（1已处理，2超时处理，3已处理未通过）")
    private Integer chulizhuangtai;
    /**
     * 处理形式
     */
    @ApiModelProperty(value = "处理形式")
    private String chulixingshi;
    /**
     * 处理描述
     */
    @ApiModelProperty(value = "处理描述")
    private String chulimiaoshu;
    /**
     * 处理人id
     */
    @ApiModelProperty(value = "处理人id")
    private Integer chulirenid;
    /**
     * 处理人
     */
    @ApiModelProperty(value = "处理人")
    private String chuliren;
    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    private String chulishijian;
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
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    private String qiyemingcheng;
    /**
     * 企业id
     */
    @ApiModelProperty(value = "企业id")
    private Integer qiyeid;
    /**
     * 车辆牌照
     */
    @ApiModelProperty(value = "车辆牌照")
    private String cheliangpaizhao;
    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车牌颜色")
    private String chepaiyanse;
    /**
     * 核定状态
     */
    @ApiModelProperty(value = "核定状态")
    private String hedingzhuangtai;
    /**
     * 核定人员
     */
    @ApiModelProperty(value = "核定人员")
    private String hedingrenyuan;
    /**
     * 核定人员id
     */
    @ApiModelProperty(value = "核定人员id")
    private Integer hedingrenyuanid;
    /**
     * 核定时间
     */
    @ApiModelProperty(value = "核定时间")
    private LocalDateTime hedingshijian;
    /**
     * 申诉审核人
     */
    @ApiModelProperty(value = "申诉审核人")
    private String shensushenheren;
    /**
     * 申诉审核时间
     */
    @ApiModelProperty(value = "申诉审核时间")
    private String shensushenheshijian;
    /**
     * 申诉审核标识
     */
    @ApiModelProperty(value = "申诉审核标识")
    private Integer shensushenhebiaoshi;
    /**
     * 申诉速度
     */
    @ApiModelProperty(value = "申诉速度")
    private Integer shensusudu;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
	@TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "报警id串")
    @TableField(exist = false)
    private String ids;
    @ApiModelProperty(value = "报警id列表")
    @TableField(exist = false)
    private String[] idss;

	/**
	 * 二次处理形式
	 */
	@ApiModelProperty(value = "二次处理形式")
	private String twicechulixingshi;

	/**
	 * 二次处理描述
	 */
	@ApiModelProperty(value = "二次处理描述")
	private String twicechulimiaoshu;

	/**
	 * 二次处理附件
	 */
	@ApiModelProperty(value = "二次处理附件")
	private String twicefujian;

	/**
	 * 二次处理人
	 */
	@ApiModelProperty(value = "二次处理人")
	private String twicechuliren;

	/**
	 * 二次处理时间
	 */
	@ApiModelProperty(value = "二次处理时间")
	private String twicechulishijian;

	/**
	 * 二次处理人ID
	 */
	@ApiModelProperty(value = "二次处理人ID")
	private Integer twicechulirenid;

	/**
	 * 处理最终结果
	 */
	@ApiModelProperty(value = "处理最终结果")
	private Integer endresult;
}
