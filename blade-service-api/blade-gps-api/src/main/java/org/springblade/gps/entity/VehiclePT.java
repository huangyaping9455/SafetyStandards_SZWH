package org.springblade.gps.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author th
 * @description: 车辆实时点位信息
 * @projectName SafetyStandards
 * @date 2019/10/912:22
 */
@Data
@ApiModel(value = "VehiclePT对象", description = "车辆实时点位信息")
public class VehiclePT {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private  String cph;
    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车牌颜色")
    private String platecolor;
    /**
     * gps时间
     */
    @ApiModelProperty(value = "gps时间")
    private String gpstime;
    /**
     * 单位名称
     */
    @ApiModelProperty(value = "单位名称")
    private String company;
    /**
     * 速度
     */
    @ApiModelProperty(value = "速度")
    private Integer velocity;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;
    /**
     * 车辆状态
     */
    @ApiModelProperty(value = "车辆状态")
    private String status;
	/**
	 * acc状态
	 */

	@ApiModelProperty("acc状态")
	private Integer acc;
	/**
	 * accshow
	 */
	@ApiModelProperty("accShow")
	private String accShow;
	/**
	 * 地理位置
	 */
	@ApiModelProperty("地理位置")
	private String  locationName;
	/**
	 * 车辆信息
	 */
	@ApiModelProperty("车辆信息")
	private Object vehicledata;

	@ApiModelProperty("车辆信息list")
	private List<VehiclePT> ptList;
}
