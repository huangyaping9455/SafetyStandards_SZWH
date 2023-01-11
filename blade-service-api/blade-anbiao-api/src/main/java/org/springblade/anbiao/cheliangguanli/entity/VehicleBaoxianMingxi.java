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
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.annotations.Api;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 车辆保险信息明细实体类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Data
@TableName("anbiao_vehicle_baoxian_mingxi")
@ApiModel(value = "VehicleBaoxianMingxi对象", description = "车辆保险信息明细")
public class VehicleBaoxianMingxi implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 保险明细主键
     */
	@TableId(value = "avbm_ids", type = IdType.UUID)
    @ApiModelProperty(value = "保险明细主键")
    private String avbmIds;
    /**
     * 保险主表主键
     */
    @ApiModelProperty(value = "保险主表主键")
    private String avbmAvbIds;

    @ApiModelProperty(value = "保险险种名称")
    private String avbmName;
    /**
     * 险别(车损险、交强险、乘坐险、商三险、自燃险、货物险、危险品、车船税、承运人险)
     */
    @ApiModelProperty(value = "险别(车损险、交强险、乘坐险、商三险、自燃险、货物险、危险品、车船税、承运人险)")
    private String avbmRisk;
    /**
     * 是否增保(0=否,1=是)
     */
    @ApiModelProperty(value = "是否增保(0=否,1=是)")
    private String avbmIncreaseInsurance;
    /**
     * 批改日期
     */
    @ApiModelProperty(value = "批改日期")
    private LocalDate avbmCorrectionDate;
    /**
     * 保额(±)
     */
    @ApiModelProperty(value = "保额(±)")
    private String avbmInsuredType;
    /**
     * 保险金额
     */
    @ApiModelProperty(value = "保险金额")
    private BigDecimal avbmInsuranceAmount;
    /**
     * 基本保费
     */
    @ApiModelProperty(value = "基本保费")
    private BigDecimal avbmBasicPremium;
    /**
     * 费率
     */
    @ApiModelProperty(value = "费率")
    private BigDecimal avbmRate;
    /**
     * 打折
     */
    @ApiModelProperty(value = "打折")
    private BigDecimal avbmDiscount;
    /**
     * 自主系数
     */
    @ApiModelProperty(value = "自主系数")
    private BigDecimal avbmAutonomyCoefficient;
    /**
     * 合计
     */
    @ApiModelProperty(value = "合计")
    private BigDecimal avbmTotal;
    /**
     * 是否统筹(0=否,1=是)
     */
    @ApiModelProperty(value = "是否统筹(0=否,1=是)")
    private String avbmOverallPlanning;
    /**
     * 统筹金额
     */
    @ApiModelProperty(value = "统筹金额")
    private BigDecimal avbmOverallAmount;
    /**
     * 保司金额
     */
    @ApiModelProperty(value = "保司金额")
    private BigDecimal avbmCompanyAmount;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer avbmSerialNo;


}
