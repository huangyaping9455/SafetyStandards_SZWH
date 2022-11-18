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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 车辆保险信息主表实体类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Data
@TableName("anbiao_vehicle_baoxian")
@ApiModel(value = "VehicleBaoxian对象", description = "车辆保险信息主表")
public class VehicleBaoxian implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 车辆保险主表主键
     */
	@TableId(value = "avb_ids", type = IdType.UUID)
    @ApiModelProperty(value = "车辆保险主表主键")
    private String avbIds;
    /**
     * 保单号
     */
    @ApiModelProperty(value = "保单号")
    private String avbPolicyNo;

	/**
	 * 被保险人所属企业
	 */
	@ApiModelProperty(value = "被保险人所属企业")
    private Long avbDeptIds;

	@TableField(exist = false)
	@ApiModelProperty(value = "被保险人所属企业名称")
	private String avbDeptName;
    /**
     * 被保险人主键
     */
    @ApiModelProperty(value = "被保险人主键")
    private String avbInsuredIds;

	/**
	 * 被保险人证件号码
	 */
	@ApiModelProperty(value = "被保险人证件号码")
    private String avbCertificateNumber;
    /**
     * 被保险人姓名
     */
    @ApiModelProperty(value = "被保险人姓名")
    private String avbInsuredName;
    /**
     * 被保联系人
     */
    @ApiModelProperty(value = "被保联系人")
    private String avbInsuredContacts;
    /**
     * 被保联系电话
     */
    @ApiModelProperty(value = "被保联系电话")
    private String avbInsuredContactNumber;
    /**
     * 被保联系地址
     */
    @ApiModelProperty(value = "被保联系地址")
    private String avbInsuredContactAddress;
    /**
     * 投保人主键
     */
    @ApiModelProperty(value = "投保人主键")
    private String avbInsureIds;
    /**
     * 投保人姓名
     */
    @ApiModelProperty(value = "投保人姓名")
    private String avbInsureName;
    /**
     * 投保联系人
     */
    @ApiModelProperty(value = "投保联系人")
    private String avbInsureContacts;
    /**
     * 投保联系电话
     */
    @ApiModelProperty(value = "投保联系电话")
    private String avbInsureContactNumber;
    /**
     * 投保联系地址
     */
    @ApiModelProperty(value = "投保联系地址")
    private String avbInsureContactAddress;
    /**
     * 保险期限(起)
     */
    @ApiModelProperty(value = "保险期限(起)")
    private LocalDate avbInsurancePeriodStart;
    /**
     * 保险期限(止)
     */
    @ApiModelProperty(value = "保险期限(止)")
    private LocalDate avbInsurancePeriodEnd;
    /**
     * 保险天数
     */
    @ApiModelProperty(value = "保险天数")
    private Integer avbInsuranceDays;
    /**
     * 计算方式(0=正常,1=1/300,2=1/365,3=短期;4=月平均,5=公务车)
     */
    @ApiModelProperty(value = "计算方式(0=正常,1=1/300,2=1/365,3=短期;4=月平均,5=公务车)")
    private String avbCalculationMethod;
    /**
     * 车辆主键
     */
    @ApiModelProperty(value = "车辆主键")
    private String avbAvIds;

    @TableField(exist = false)
    @ApiModelProperty(value = "车辆牌照")
    private String cheliangpaizhao;
    /**
     * 当年安全统筹金
     */
    @ApiModelProperty(value = "当年安全统筹金")
    private BigDecimal avbYearSecurityPoolingFund;
    /**
     * 当年保费
     */
    @ApiModelProperty(value = "当年保费")
    private BigDecimal avbYearPremium;
    /**
     * 保险公司
     */
    @ApiModelProperty(value = "保险公司")
    private String avbInsuranceCompany;
    /**
     * 保司保号
     */
    @ApiModelProperty(value = "保司保号")
    private String avbBaoshiBaoNo;
    /**
     * 签单日期
     */
    @ApiModelProperty(value = "签单日期")
    private LocalDate avbSigningDate;
    /**
     * 当年合计
     */
    @ApiModelProperty(value = "当年合计")
    private BigDecimal avbTotal;
    /**
     * 重置价
     */
    @ApiModelProperty(value = "重置价")
    private BigDecimal avbReplacementPrice;
    /**
     * 交保司日期
     */
    @ApiModelProperty(value = "交保司日期")
    private LocalDate avbBailCompanyDate;
    /**
     * 交总公司日期
     */
    @ApiModelProperty(value = "交总公司日期")
    private LocalDate avbBailHeadquartersDate;
    /**
     * 赔付标准(0=新标准,1=旧标准)
     */
    @ApiModelProperty(value = "赔付标准(0=新标准,1=旧标准)")
    private String avbCompensationStandard;
    /**
     * 有无事故(0=无事故,1=有事故)
     */
    @ApiModelProperty(value = "有无事故(0=无事故,1=有事故)")
    private String avbAreAccidents;
    /**
     * 购买方式(0=全款,1=销贷车,2=全部)
     */
    @ApiModelProperty(value = "购买方式(0=全款,1=销贷车,2=全部)")
    private String avbPurchaseMethod;
    /**
     * 投保比例
     */
    @ApiModelProperty(value = "投保比例")
    private BigDecimal avbInsuranceRatio;
    /**
     * 准载座位
     */
    @ApiModelProperty(value = "准载座位")
    private Integer avbPermittedSeat;
    /**
     * 驾驶座位(准载)默认为1
     */
    @ApiModelProperty(value = "驾驶座位(准载)默认为1")
    private Integer avbPermittedSeatDriverSeat;
    /**
     * 前排座位(准载)默认为1
     */
    @ApiModelProperty(value = "前排座位(准载)默认为1")
    private Integer avbPermittedSeatFrontSeats;
    /**
     * 后排座位(准载)
     */
    @ApiModelProperty(value = "后排座位(准载)")
    private Integer avbPermittedSeatBackRowSeats;
    /**
     * 实载座位
     */
    @ApiModelProperty(value = "实载座位")
    private Integer avbLiveSeat;
    /**
     * 驾驶座位(实载)默认为1
     */
    @ApiModelProperty(value = "驾驶座位(实载)默认为1")
    private Integer avbLiveSeatDriverSeat;
    /**
     * 前排座位(实载)默认为1
     */
    @ApiModelProperty(value = "前排座位(实载)默认为1")
    private Integer avbLiveSeatFrontSeats;
    /**
     * 后排座位(实载)
     */
    @ApiModelProperty(value = "后排座位(实载)")
    private Integer avbLiveSeatBackRowSeats;
    /**
     * 座位方式(0=准载,1=实载)
     */
    @ApiModelProperty(value = "座位方式(0=准载,1=实载)")
    private String avbSeatingMode;
    /**
     * 驾驶座费率
     */
    @ApiModelProperty(value = "驾驶座费率")
    private BigDecimal avbDriverSeatRate;
    /**
     * 前排费率
     */
    @ApiModelProperty(value = "前排费率")
    private BigDecimal avbFrontRowRate;
    /**
     * 后排费率
     */
    @ApiModelProperty(value = "后排费率")
    private BigDecimal avbBackRowRate;
    /**
     * 车船税税额
     */
    @ApiModelProperty(value = "车船税税额")
    private BigDecimal avbVehicleAndVesselTax;
    /**
     * 车船税减税
     */
    @ApiModelProperty(value = "车船税减税")
    private BigDecimal avbVehicleAndVesselTaxReduction;
    /**
     * 车船税实收
     */
    @ApiModelProperty(value = "车船税实收")
    private BigDecimal avbVehicleAndVesselTaxPaidIn;
    /**
     * 保费合计
     */
    @ApiModelProperty(value = "保费合计")
    private BigDecimal avbTotalPremium;
    /**
     * 保司保费合计
     */
    @ApiModelProperty(value = "保司保费合计")
    private BigDecimal avbCompanyTotalPremium;
    /**
     * 统筹保费合计
     */
    @ApiModelProperty(value = "统筹保费合计")
    private BigDecimal avbTotalPlannedPremium;
    /**
     * 保费总合计
     */
    @ApiModelProperty(value = "保费总合计")
    private BigDecimal avbTotalizePremium;
    /**
     * 保司保费总合计
     */
    @ApiModelProperty(value = "保司保费总合计")
    private BigDecimal avbCompanyTotalizePremium;
    /**
     * 是否审核(0=待审核,1=已审核)
     */
    @ApiModelProperty(value = "是否审核(0=待审核,1=已审核)")
    private String avbApprove;
    /**
     * 特别约定
     */
    @ApiModelProperty(value = "特别约定")
    private String avbSpecialAgreement;
    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String avbEnclosure;
    /**
     * 逻辑删除(0=正常,1=删除)
     */
    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    private Integer avbDelete;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime avbCreateTime;
    /**
     * 创建人主键
     */
    @ApiModelProperty(value = "创建人主键")
    private String avbCreateByIds;
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String avbCreateByName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime avbUpdateTime;
    /**
     * 更新人主键
     */
    @ApiModelProperty(value = "更新人主键")
    private String avbUpdateByIds;
    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String avbUpdateByName;

	@ApiModelProperty(value = "车辆保险明细信息")
	@TableField(exist = false)
	private List<VehicleBaoxianMingxi> baoxianMingxis;


}
