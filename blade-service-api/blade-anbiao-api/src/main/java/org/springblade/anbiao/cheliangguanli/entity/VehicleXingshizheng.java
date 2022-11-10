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
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 车辆行驶证信息实体类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Data
@TableName("anbiao_vehicle_xingshizheng")
@ApiModel(value = "VehicleXingshizheng对象", description = "车辆行驶证信息")
public class VehicleXingshizheng implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 行驶证主键
     */
	@TableId(value = "avx_ids", type = IdType.UUID)
    @ApiModelProperty(value = "行驶证主键")
    private String avxIds;
    /**
     * 车辆主键
     */
    @ApiModelProperty(value = "车辆主键")
    private String avxAvIds;
    /**
     * 号牌号码
     */
    @ApiModelProperty(value = "号牌号码")
    private String avxPlateNo;
    /**
     * 车辆类型
     */
    @ApiModelProperty(value = "车辆类型")
    private String avxVehicleType;
    /**
     * 所有人
     */
    @ApiModelProperty(value = "所有人")
    private String avxOwner;
    /**
     * 住址
     */
    @ApiModelProperty(value = "住址")
    private String avxAddress;
    /**
     * 使用性质
     */
    @ApiModelProperty(value = "使用性质")
    private String avxUseCharter;
    /**
     * 品牌型号
     */
    @ApiModelProperty(value = "品牌型号")
    private String avxModel;
    /**
     * 车辆识别代码
     */
    @ApiModelProperty(value = "车辆识别代码")
    private String avxVin;
    /**
     * 发动机号码
     */
    @ApiModelProperty(value = "发动机号码")
    private String avxEngineNo;
    /**
     * 注册日期
     */
    @ApiModelProperty(value = "注册日期")
    private LocalDate avxRegisterDate;
    /**
     * 发证日期
     */
    @ApiModelProperty(value = "发证日期")
    private LocalDate avxIssueDate;
    /**
     * 正本附件
     */
    @ApiModelProperty(value = "正本附件")
    private String avxOriginalEnclosure;
    /**
     * 档案编号
     */
    @ApiModelProperty(value = "档案编号")
    private String avxFileNo;
    /**
     * 核定载人数
     */
    @ApiModelProperty(value = "核定载人数")
    private Integer avxAuthorizedSeatingCapacity;
    /**
     * 总质量(单位kg)
     */
    @ApiModelProperty(value = "总质量(单位kg)")
    private Integer avxTotalMass;
    /**
     * 整备质量(单位kg)
     */
    @ApiModelProperty(value = "整备质量(单位kg)")
    private Integer avxCurbWeight;
    /**
     * 核定载质量(单位kg)
     */
    @ApiModelProperty(value = "核定载质量(单位kg)")
    private Integer avxApprovedLoadCapacity;
    /**
     * 外廓尺寸
     */
    @ApiModelProperty(value = "外廓尺寸")
    private String avxOverallDimensions;
    /**
     * 准牵引质量
     */
    @ApiModelProperty(value = "准牵引质量")
    private Integer avxQuasiTractiveMass;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String avxRemarks;
    /**
     * 检验记录
     */
    @ApiModelProperty(value = "检验记录")
    private String avxInspectionRecord;
    /**
     * 有效期至
     */
    @ApiModelProperty(value = "有效期至")
    private LocalDate avxValidUntil;
    /**
     * 副本附件
     */
    @ApiModelProperty(value = "副本附件")
    private String avxCopyEnclosure;
    /**
     * 逻辑删除(0=正常,1=删除)
     */
    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    private String avxDelete;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime avxCreateTime;
    /**
     * 创建人主键
     */
    @ApiModelProperty(value = "创建人主键")
    private String avxCreateByIds;
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String avxCreateByName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime avxUpdateTime;
    /**
     * 更新人主键
     */
    @ApiModelProperty(value = "更新人主键")
    private String avxUpdateByIds;
    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String avxUpdateByName;


}
