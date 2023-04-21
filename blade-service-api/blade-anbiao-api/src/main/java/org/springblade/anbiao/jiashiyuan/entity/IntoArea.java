package org.springblade.anbiao.jiashiyuan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by you on 2019/4/22.
 */
@Data
@ApiModel(value = "IntoArea对象", description = "IntoArea对象")
public class IntoArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID")
	private String qiyeid;

	@ApiModelProperty(value = "企业名称")
	private String qiyemingcheng;

	@ApiModelProperty(value = "区域名称")
	private String area;

	@ApiModelProperty(value = "区域点位")
	private String points;

	@ApiModelProperty(value = "区域中心点经度")
	private BigDecimal centerLat;

	@ApiModelProperty(value = "区域中心点纬度")
	private BigDecimal centerLng;

	@ApiModelProperty(value = "报警时间")
	private LocalDateTime date;

	@ApiModelProperty(value = "报警类型")
	private String note;

	@ApiModelProperty(value = "速度")
	private String velocity;

	@ApiModelProperty(value = "区域ID")
	private String AreaId;

	@ApiModelProperty(value = "持续时间")
	private String times;

	@ApiModelProperty(value = "guId")
	private String guid;

	@ApiModelProperty(value = "经度")
	private BigDecimal longitude;

	@ApiModelProperty(value = "纬度")
	private BigDecimal latitude;

	@ApiModelProperty(value = "报警地点")
	private String RoadName;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "车辆型号")
	private String xinghao;

	@ApiModelProperty(value = "车辆id")
	private String VehicleId;

	@ApiModelProperty(value = "处理状态")
	private String chulizhuangtai;

	@ApiModelProperty(value = "处理形式")
	private String chulixingshi;

	@ApiModelProperty(value = "处理描述")
	private String chulimiaoshu;

	@ApiModelProperty(value = "处理人")
	private String chuliren;

	@ApiModelProperty(value = "处理人id")
	private Integer chulirenid;

	@ApiModelProperty(value = "处理时间")
	private LocalDateTime chulishijian;

	@ApiModelProperty(value = "附件")
	private String fujian;

	@ApiModelProperty(value = "备注")
	private String beizhu;

	@ApiModelProperty(value = "申诉状态")
	private String shensuzhuangtai;

	@ApiModelProperty(value = "申诉形式")
	private String shensuxingshi;

	@ApiModelProperty(value = "申诉描述")
	private String shensumiaoshu;

	@ApiModelProperty(value = "申诉审核标识（0:申诉审核中;1:申诉通过;2:申诉驳回）")
	private Integer shensushenhebiaoshi;

	@ApiModelProperty(value = "申诉审核人")
	private String shensushenheren;

	@ApiModelProperty(value = "申诉审核时间")
	private String shensushenheshijian;

	@ApiModelProperty(value = "申诉审核意见")
	private String shensushenheyijian;

	@ApiModelProperty(value = "二次处理形式")
	private String twicechulixingshi;

	@ApiModelProperty(value = "二次处理描述")
	private String twicechulimiaoshu;

	@ApiModelProperty(value = "二次处理附件")
	private String twicefujian;

	@ApiModelProperty(value = "二次处理人")
	private String twicechuliren;

	@ApiModelProperty(value = "二次处理时间")
	private String twicechulishijian;

	@ApiModelProperty(value = "二次处理人ID")
	private String twicechulirenid;

	@ApiModelProperty(value = "最终处理结果")
	private String endresult;

	@ApiModelProperty(value = "处理类型1为处理0为申诉")
	private Integer remark;

	@ApiModelProperty(value = "报警ID")
	private Integer alarmid;

}
