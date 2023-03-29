package org.springblade.alarm.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.alarm.entity.AlarmBaojingTongji;
import org.springblade.common.BasePage;

/**
 * 报警 分页实体类
 * Program: SafetyStandards
 * @description: AlarmPage
 * @author: 呵呵哒
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AlarmPage对象", description = "AlarmPage对象")
public class AlarmPage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业 id")
    private Integer deptId;

    @ApiModelProperty(value = "企业名称", required = true)
    private String deptName;

    @ApiModelProperty(value = "报警类型", required = true)
    private String alarmType;

    @ApiModelProperty(value = "开始时间（yyyy-MM-dd）", required = true)
    private String beginTime;

    @ApiModelProperty(value = "结束时间（yyyy-MM-dd）", required = true)
    private String endTime;

    /*新加筛选严重等级*/
	@ApiModelProperty(value = "严重报警等级 1 严重一级  2 严重二级  3 严重三级  0 为普通  “” 为全部 ",required = true)
	private Integer status;

	/*新增筛选道路名称*/
	@ApiModelProperty(value = "道路名称")
	private String  roadName;

    @ApiModelProperty(value = "车辆牌照")
    private String cheliangpaizhao;

    @ApiModelProperty(value = "是否处理('',未处理，已处理)")
    private String shifouchuli;

    @ApiModelProperty(value = "是否申诉")
    private String shifoushenshu;

    @ApiModelProperty(value = "超速比")
    private String chaosubi;

    @ApiModelProperty(value = "超速比最大值")
    private String chaosubiMax;

    @ApiModelProperty(value = "营运类型")
    private String operattype;

    @ApiModelProperty(value = "核定状态")
    private String passed;

    @ApiModelProperty(value = "申诉审核标识")
    private String shensushenhebiaoshi;

    @ApiModelProperty(value = "持续时间")
    private Integer keeptime;

    @ApiModelProperty(value = "最大速度")
    private Integer maxspeed;

    @ApiModelProperty(value = "道路限速")
    private Integer roadLimited = 0;

    @ApiModelProperty(value = "速度")
    private String velocity;

    @ApiModelProperty(value = "核定人")
    private String verifyname;

    private String[] idss;

    @ApiModelProperty(value = "不定位时长")
    private Integer budingweishichang;

    @ApiModelProperty(value = "不在线时长")
    private Integer buzaixianshichang;

    @ApiModelProperty(value = "平台、终端报警")
    private String analyzemode;

    @ApiModelProperty(value = "1、查看日；2、查看月")
    private String mark;

    @ApiModelProperty(value = "是否查看严重违规报警(1是0否)")
    private Integer yanzhongweiguibaojing ;

    @ApiModelProperty(value = "区域验证")
    private Integer quyuyanzheng;

    @ApiModelProperty(value = "统计车辆、报警数、处理率")
    private AlarmBaojingTongji baojingTongji;

	@ApiModelProperty("机构名称")
	private String jigouName;

}
