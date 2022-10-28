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
import java.time.LocalDateTime;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 车辆安全检查项目实体类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Data
@TableName("anbiao_vehicle_inspection_items")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VehicleInspectionItems对象", description = "车辆安全检查项目")
public class VehicleInspectionItems extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 检查项目主键
     */
    @ApiModelProperty(value = "检查项目主键")
    @TableId(value = "ids", type = IdType.AUTO)
  private Long ids;
    /**
     * 检查部位
     */
    @ApiModelProperty(value = "检查部位")
    private String vitItem;
    /**
     * 具体位置
     */
    @ApiModelProperty(value = "具体位置")
    private String vitName;
  private String vitModel;
    /**
     * 技术要求
     */
    @ApiModelProperty(value = "技术要求")
    private String vitDescribe;
    /**
     * 适用类型
     */
    @ApiModelProperty(value = "适用类型")
    private String vitTrialScope;
    /**
     * 适用车型
     */
    @ApiModelProperty(value = "适用车型")
    private String vitTrialModel;
    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    private Integer vitOrderNum;
    /**
     * 逻辑删除(0=正常,1=删除)
     */
    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    private String vitDelete;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime vitCreateTime;
    /**
     * 创建人主键
     */
    @ApiModelProperty(value = "创建人主键")
    private String vitCreateByIds;
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String vitCreateByName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime vitUpdateTime;
    /**
     * 更新人主键
     */
    @ApiModelProperty(value = "更新人主键")
    private String vitUpdateByIds;
    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String vitUpdateByName;


}
