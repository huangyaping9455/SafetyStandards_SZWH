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
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 驾驶员保险信息主表实体类
 *
 * @author Blade
 * @since 2022-10-31
 */
@Data
@TableName("anbiao_jiashiyuan_baoxian")
@ApiModel(value = "JiashiyuanBaoxian对象", description = "驾驶员保险信息主表")
public class JiashiyuanBaoxian implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 车辆保险主表主键
     */
	@TableId(value = "ajb_ids", type = IdType.UUID)
    @ApiModelProperty(value = "车辆保险主表主键")
    private String ajbIds;
    /**
     * 保单号
     */
    @ApiModelProperty(value = "保单号")
    private String ajbPolicyNo;

	/**
	 * 被保险人所属企业
	 */
	@ApiModelProperty(value = "被保险人所属企业")
    private Long ajbDeptIds;

	@TableField(exist = false)
	@ApiModelProperty(value = "被保险人所属企业名称")
	private String ajbDeptName;
    /**
     * 被保险人主键
     */
    @ApiModelProperty(value = "被保险人主键")
    private String ajbInsuredIds;


	/**
	 * 被保险人证件号码
	 */
	@ApiModelProperty(value = "被保险人证件号码")
	private String ajbCertificateNumber;
    /**
     * 被保险人姓名
     */
    @ApiModelProperty(value = "被保险人姓名")
    private String ajbInsuredName;
    /**
     * 被保联系人
     */
    @ApiModelProperty(value = "被保联系人")
    private String ajbInsuredContacts;
    /**
     * 被保联系电话
     */
    @ApiModelProperty(value = "被保联系电话")
    private String ajbInsuredContactNumber;
    /**
     * 被保联系地址
     */
    @ApiModelProperty(value = "被保联系地址")
    private String ajbInsuredContactAddress;
    /**
     * 投保人主键
     */
    @ApiModelProperty(value = "投保人主键")
    private String ajbInsureIds;
    /**
     * 投保人姓名
     */
    @ApiModelProperty(value = "投保人姓名")
    private String ajbInsureName;
    /**
     * 投保联系人
     */
    @ApiModelProperty(value = "投保联系人")
    private String ajbInsureContacts;
    /**
     * 投保联系电话
     */
    @ApiModelProperty(value = "投保联系电话")
    private String ajbInsureContactNumber;
    /**
     * 投保联系地址
     */
    @ApiModelProperty(value = "投保联系地址")
    private String ajbInsureContactAddress;
    /**
     * 保险期限(起)
     */
    @ApiModelProperty(value = "保险期限(起)")
    private LocalDate ajbInsurancePeriodStart;
    /**
     * 保险期限(止)
     */
    @ApiModelProperty(value = "保险期限(止)")
    private LocalDate ajbInsurancePeriodEnd;
    /**
     * 保险天数
     */
    @ApiModelProperty(value = "保险天数")
    private Integer ajbInsuranceDays;
    /**
     * 计算方式(0=正常,1=1/300,2=1/365,3=短期;4=月平均,5=公务车)
     */
    @ApiModelProperty(value = "计算方式(0=正常,1=1/300,2=1/365,3=短期;4=月平均,5=公务车)")
    private String ajbCalculationMethod;
    /**
     * 人员主键
     */
    @ApiModelProperty(value = "人员主键")
    private String ajbAjIds;
    /**
     * 当年安全统筹金
     */
    @ApiModelProperty(value = "当年安全统筹金")
    private BigDecimal ajbYearSecurityPoolingFund;
    /**
     * 当年保费
     */
    @ApiModelProperty(value = "当年保费")
    private BigDecimal ajbYearPremium;
    /**
     * 保险公司
     */
    @ApiModelProperty(value = "保险公司")
    private String ajbInsuranceCompany;
    /**
     * 保司保号
     */
    @ApiModelProperty(value = "保司保号")
    private String ajbBaoshiBaoNo;
    /**
     * 签单日期
     */
    @ApiModelProperty(value = "签单日期")
    private LocalDate ajbSigningDate;
    /**
     * 当年合计
     */
    @ApiModelProperty(value = "当年合计")
    private BigDecimal ajbTotal;
    /**
     * 重置价
     */
    @ApiModelProperty(value = "重置价")
    private BigDecimal ajbReplacementPrice;
    /**
     * 交保司日期
     */
    @ApiModelProperty(value = "交保司日期")
    private LocalDate ajbBailCompanyDate;
    /**
     * 交总公司日期
     */
    @ApiModelProperty(value = "交总公司日期")
    private LocalDate ajbBailHeadquartersDate;
    /**
     * 赔付标准(0=新标准,1=旧标准)
     */
    @ApiModelProperty(value = "赔付标准(0=新标准,1=旧标准)")
    private String ajbCompensationStandard;
    /**
     * 有无事故(0=无事故,1=有事故)
     */
    @ApiModelProperty(value = "有无事故(0=无事故,1=有事故)")
    private String ajbAreAccidents;
    /**
     * 购买方式(0=全款,1=销贷车,2=全部)
     */
    @ApiModelProperty(value = "购买方式(0=全款,1=销贷车,2=全部)")
    private String ajbPurchaseMethod;
    /**
     * 投保比例
     */
    @ApiModelProperty(value = "投保比例")
    private BigDecimal ajbInsuranceRatio;
    /**
     * 准载座位
     */
    @ApiModelProperty(value = "准载座位")
    private Integer ajbPermittedSeat;
    /**
     * 驾驶座位(准载)默认为1
     */
    @ApiModelProperty(value = "驾驶座位(准载)默认为1")
    private Integer ajbPermittedSeatDriverSeat;
    /**
     * 前排座位(准载)默认为1
     */
    @ApiModelProperty(value = "前排座位(准载)默认为1")
    private Integer ajbPermittedSeatFrontSeats;
    /**
     * 后排座位(准载)
     */
    @ApiModelProperty(value = "后排座位(准载)")
    private Integer ajbPermittedSeatBackRowSeats;
    /**
     * 实载座位
     */
    @ApiModelProperty(value = "实载座位")
    private Integer ajbLiveSeat;
    /**
     * 驾驶座位(实载)默认为1
     */
    @ApiModelProperty(value = "驾驶座位(实载)默认为1")
    private Integer ajbLiveSeatDriverSeat;
    /**
     * 前排座位(实载)默认为1
     */
    @ApiModelProperty(value = "前排座位(实载)默认为1")
    private Integer ajbLiveSeatFrontSeats;
    /**
     * 后排座位(实载)
     */
    @ApiModelProperty(value = "后排座位(实载)")
    private Integer ajbLiveSeatBackRowSeats;
    /**
     * 座位方式(0=准载,1=实载)
     */
    @ApiModelProperty(value = "座位方式(0=准载,1=实载)")
    private String ajbSeatingMode;
    /**
     * 驾驶座费率
     */
    @ApiModelProperty(value = "驾驶座费率")
    private BigDecimal ajbDriverSeatRate;
    /**
     * 前排费率
     */
    @ApiModelProperty(value = "前排费率")
    private BigDecimal ajbFrontRowRate;
    /**
     * 后排费率
     */
    @ApiModelProperty(value = "后排费率")
    private BigDecimal ajbBackRowRate;
    /**
     * 车船税税额
     */
    @ApiModelProperty(value = "车船税税额")
    private BigDecimal ajbVehicleAndVesselTax;
    /**
     * 车船税减税
     */
    @ApiModelProperty(value = "车船税减税")
    private BigDecimal ajbVehicleAndVesselTaxReduction;
    /**
     * 车船税实收
     */
    @ApiModelProperty(value = "车船税实收")
    private BigDecimal ajbVehicleAndVesselTaxPaidIn;
    /**
     * 保费合计
     */
    @ApiModelProperty(value = "保费合计")
    private BigDecimal ajbTotalPremium;
    /**
     * 保司保费合计
     */
    @ApiModelProperty(value = "保司保费合计")
    private BigDecimal ajbCompanyTotalPremium;
    /**
     * 统筹保费合计
     */
    @ApiModelProperty(value = "统筹保费合计")
    private BigDecimal ajbTotalPlannedPremium;
    /**
     * 保费总合计
     */
    @ApiModelProperty(value = "保费总合计")
    private BigDecimal ajbTotalizePremium;
    /**
     * 保司保费总合计
     */
    @ApiModelProperty(value = "保司保费总合计")
    private BigDecimal ajbCompanyTotalizePremium;
    /**
     * 是否审核(0=待审核,1=已审核)
     */
    @ApiModelProperty(value = "是否审核(0=待审核,1=已审核)")
    private String ajbApprove;
    /**
     * 特别约定
     */
    @ApiModelProperty(value = "特别约定")
    private String ajbSpecialAgreement;
    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String ajbEnclosure;
    /**
     * 逻辑删除(0=正常,1=删除)
     */
    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    private String ajbDelete;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime ajbCreateTime;
    /**
     * 创建人主键
     */
    @ApiModelProperty(value = "创建人主键")
    private String ajbCreateByIds;
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String ajbCreateByName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime ajbUpdateTime;
    /**
     * 更新人主键
     */
    @ApiModelProperty(value = "更新人主键")
    private String ajbUpdateByIds;
    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String ajbUpdateByName;

	@ApiModelProperty(value = "投保人企业主键")
	private String ajbInsureDeptid;

	/**
	 * 保险金额
	 */
	@ApiModelProperty(value = "保险金额")
	@TableField(exist = false)
	private BigDecimal ajbmInsuranceAmount;
	/**
	 * 基本保费
	 */
	@ApiModelProperty(value = "基本保费")
	@TableField(exist = false)
	private BigDecimal ajbmBasicPremium;

	@ApiModelProperty(value = "企业ID")
	@TableField(exist = false)
	private Integer deptId;


}
