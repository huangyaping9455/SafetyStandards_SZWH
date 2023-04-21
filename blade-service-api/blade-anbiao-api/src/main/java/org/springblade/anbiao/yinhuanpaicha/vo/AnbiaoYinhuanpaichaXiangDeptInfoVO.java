package org.springblade.anbiao.yinhuanpaicha.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AnbiaoYinhuanpaichaXiangDeptInfoVO对象", description="AnbiaoYinhuanpaichaXiangDeptInfoVO对象")
public class AnbiaoYinhuanpaichaXiangDeptInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "企业ID")
    private String deptid;

    @ApiModelProperty(value = "排查年、月份")
    private String pcyf;

    @ApiModelProperty(value = "数据ID")
    private String dataid;

    @ApiModelProperty(value = "车辆ID")
    private String vehid;

    @ApiModelProperty(value = "驾驶员ID")
    private String jsyid;

    @ApiModelProperty(value = "状态，默认0，未填写；1：待审查；1：已审查")
    private Integer status = 0;

    @ApiModelProperty(value = "派发时间")
    private String pfdate;

    @ApiModelProperty(value = "审查时间")
    private String scdate;

    @ApiModelProperty(value = "派发人ID")
    private Integer createid;

    @ApiModelProperty(value = "创建时间")
    private String createtime;

    @ApiModelProperty(value = "审核人ID")
    private Integer updateid;

    @ApiModelProperty(value = "更新时间")
    private String updatetime;

    private Integer isdelete = 0;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	@ApiModelProperty(value = "企业名称")
	private String deptname;

	@ApiModelProperty(value = "项目名称")
	private String name;

	@ApiModelProperty(value = "状态展示")
	private String statusShow;

	@ApiModelProperty(value = "详情ID")
	private String shujuid;

}
