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
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 车辆综合性能检测报告实体类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Data
@TableName("anbiao_vehicle_xingnengbaogao")
@ApiModel(value = "VehicleXingnengbaogao对象", description = "车辆综合性能检测报告")
public class VehicleXingnengbaogao implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 检测报告主键
     */
	@TableId(value = "avx_ids", type = IdType.UUID)
    @ApiModelProperty(value = "检测报告主键")
    private String avxIds;
    /**
     * 车辆主键
     */
    @ApiModelProperty(value = "车辆主键")
    private String avxAvIds;
    /**
     * 检测机构
     */
    @ApiModelProperty(value = "检测机构")
    private String avxInspectionOrganization;
    /**
     * 检测日期
     */
    @ApiModelProperty(value = "检测日期")
    private LocalDate avxInspectionDate;
    /**
     * 检测报告附件
     */
    @ApiModelProperty(value = "检测报告附件")
    private String avxEnclosure;
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

	@ApiModelProperty(value = "车辆牌照")
	@TableField(exist = false)
	private String avxFileNo;


}
