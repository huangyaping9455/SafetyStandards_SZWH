package org.springblade.anbiao.jiashiyuan.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;

/**
 * Created by you on 2019/4/22.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "JiaShiYuanVO对象", description = "JiaShiYuanVO对象")
public class DriverInfoVO extends JiaShiYuan{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "驾驶员ID")
	private String id;

	/**
	 * 驾驶员姓名
	 */
	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	/**
	 * 照片
	 */
	@ApiModelProperty(value = "照片")
	private String zhaopian;

	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	/**
	 * 企业ID
	 */
	@ApiModelProperty(value = "企业ID")
	private String deptId;

	/**
	 * 企业名称
	 */
	@ApiModelProperty(value = "企业名称")
	private String deptName;

	/**
	 * openID
	 */
	@ApiModelProperty(value = "openID")
	private String openid;

}
