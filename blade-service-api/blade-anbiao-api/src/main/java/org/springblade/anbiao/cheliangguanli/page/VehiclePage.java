package org.springblade.anbiao.cheliangguanli.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * 车辆 分页实体类
 * Program: SafetyStandards
 * @description: VehiclePage
 * @author: hyp
 * @create2021-04-25 11:00
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VehiclePage对象", description = "VehiclePage对象")
public class VehiclePage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业Id", required = true)
    private Integer deptId;

    @ApiModelProperty(value = "企业名称")
    private String deptName;

    @ApiModelProperty(value = "车辆牌照")
    private String cheliangpaizhao;

    @ApiModelProperty(value = "使用性质")
    private String shiyongxingzhi;

	@ApiModelProperty(value = "车辆类型(1，2)",required = true)
	private String cheliangleixing;

	@ApiModelProperty(value = "ids值为‘1’时，表示从预警提醒钻取数据")
	private String ids;

	@ApiModelProperty(value = "统计日期")
	private String tongjiriqi;

	@ApiModelProperty(value = "操作时间")
	private String caozuoshijian;

	@ApiModelProperty(value = "终端ID")
	private String zongduanid;

	@ApiModelProperty(value = "预警项id")
	private String tixingxiangqingid;

	@ApiModelProperty(value = "预警类型")
	private String tixingleixing;

	@ApiModelProperty(value = "车辆状态")
	private String cheliangzhuangtai;

	@ApiModelProperty(value = "开始时间")
	private String begintime;

	@ApiModelProperty(value = "结束时间")
	private String endtime;

	@ApiModelProperty(value = "所属地市")
	private String areaName;

	@ApiModelProperty(value = "道路运输日期状态")
	private String daoluyunshuzhengchulingriqistatus;

	@ApiModelProperty(value = "道路运输日期状态")
	private String daoluyunshuzhengyouxiaoqistatus;

	@ApiModelProperty(value = "道路运输证初领日期")
	private String daoluyunshuzhengchulingriqi;

	@ApiModelProperty(value = "道路运输证有效期")
	private String daoluyunshuzhengyouxiaoqi;

	@ApiModelProperty(value = "本次年检日期状态")
	private String bencinianjianriqistatus;

	@ApiModelProperty(value = "下次年检日期状态")
	private String xiacinianjianriqistatus;

	@ApiModelProperty(value = "本次年检日期")
	private String bencinianjianriqi;

	@ApiModelProperty(value = "下次年检日期")
	private String xiacinianjianriqi;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "车主")
	private String chezhu;
}
