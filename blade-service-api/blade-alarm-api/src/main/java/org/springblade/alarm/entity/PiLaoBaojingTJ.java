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

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 实体类
 * @author Blade
 */
@Data
@ApiModel(value = "PiLaoBaojingTJ对象", description = "PiLaoBaojingTJ对象")
public class PiLaoBaojingTJ implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    @TableField("plateNumber")
  private String plateNumber;
    /**
     * 车辆等级
     */
    @ApiModelProperty(value = "车辆等级")
    @TableField("OperatType")
  private String OperatType;
    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车牌颜色")
    private String color;
    /**
     * 报警类型
     */
    @ApiModelProperty(value = "报警类型")
    @TableField("AlarmType")
  private String AlarmType;
    /**
     * 累计持续时间
     */
    @ApiModelProperty(value = "累计持续时间")
    private String keeptime;
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 单次报警位置
     */
    @ApiModelProperty(value = "单次报警位置")
    private String weizhi;

	/**
	 * 单次开始时间
	 */
	@ApiModelProperty(value = "单次开始时间")
	private LocalDateTime beginTime;
	/**
	 * 单次结束时间
	 */
	@ApiModelProperty(value = "单次结束时间")
	private  LocalDateTime endTime;

	/**
	 * 单车持续时间
	 */
	@ApiModelProperty(value = "单车持续时间")
	private  String  keeptimeone;
	/**
	 * 累计时间转换
	 */
	@ApiModelProperty(value = "累计持续时间转换")
	private  String keeptimeShow;

	/**
	 * 疲劳次数
	 */
	@ApiModelProperty(value = "疲劳次数")
	private  Integer pilaocisu;

	/**
	 * 统计日期
	 */
	@ApiModelProperty(value = "统计日期")
	private String createDate;
}
