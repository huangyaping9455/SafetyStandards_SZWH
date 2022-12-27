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
package org.springblade.anbiao.guanlijigouherenyuan.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 */
@Data
@TableName("anbiao_organization")
@ApiModel(value = "Organization对象", description = "Organization对象")
public class Organizations implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
	@TableId(value = "id",type = IdType.UUID)
    private String id;
    /**
     * 单位ID
     */
    @ApiModelProperty(value = "单位ID",required = true)
    private String deptId;
	/**
	 * 单位名称
	 */
	@ApiModelProperty(value = "单位名称")
    private String deptName;
    /**
     * 机构负责人
     */
    @ApiModelProperty(value = "机构负责人")
    private String jigoufuzeren;
    /**
     * 机构编码
     */
    @ApiModelProperty(value = "机构编码")
    private String jigoubianma;
    /**
     * 机构类型
     */
    @ApiModelProperty(value = "机构类型")
    private String jigouleixing;
    /**
     * 机构资质
     */
    @ApiModelProperty(value = "机构资质")
    private String jigouzizhi;
    /**
     * 法人代表
     */
    @ApiModelProperty(value = "法人代表")
    private String farendaibiao;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String lianxidianhua;
    /**
     * 注册资本
     */
    @ApiModelProperty(value = "注册资本")
    private String zhuceziben;
    /**
     * 投资总额
     */
    @ApiModelProperty(value = "投资总额")
    private String touzizonge;
    /**
     * 车辆规模
     */
    @ApiModelProperty(value = "车辆规模")
    private String cheliangguimo;
    /**
     * 从业人数量
     */
    @ApiModelProperty(value = "从业人数量")
    private String congyerenshuliang;
    /**
     * 成立时间
     */
    @ApiModelProperty(value = "成立时间")
    private String chenglishijian;
    /**
     * 停车场面积
     */
    @ApiModelProperty(value = "停车场面积")
    private String tingchechangmianji;
    /**
     * 停车场位置
     */
    @ApiModelProperty(value = "停车场位置")
    private String tingchechangweizhi;
    /**
     * 驾驶员数量
     */
    @ApiModelProperty(value = "驾驶员数量")
    private String jiashiyuanshuliang;
    /**
     * 押运员数量
     */
    @ApiModelProperty(value = "押运员数量")
    private String yayunyuanshuliang;
    /**
     * 装卸员数量
     */
    @ApiModelProperty(value = "装卸员数量")
    private String zhuangxieyuanshuliang;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private String xuhao;
    /**
     * 行政区代码
     */
    @ApiModelProperty(value = "行政区代码")
    private String xingzhengqudaima;
    /**
     * 经营许可证编码
     */
    @ApiModelProperty(value = "经营许可证编码")
    private String jingyingxukezhengbianma;
    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String fujian;
    /**
     * 安全管理图
     */
    @ApiModelProperty(value = "安全管理图")
    private String anquanguanlitu;
    /**
     * 组织机构图
     */
    @ApiModelProperty(value = "组织机构图")
    private String zuzhijigoutu;
    /**
     * 经营范围
     */
    @ApiModelProperty(value = "经营范围")
    private String jingyingfanwei;
    /**
     * 经营地址
     */
    @ApiModelProperty(value = "经营地址")
    private String jingyingdizhi;
    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String xiangxidizhi;
    /**
     * 机构描述
     */
    @ApiModelProperty(value = "机构描述")
    private String jigoumiaoshu;
    /**
     * 登录图片
     */
    @ApiModelProperty(value = "登录图片")
  	private String loginPhoto;
    /**
     * home图片
     */
    @ApiModelProperty(value = "home图片")
  	private String homePhoto;
    /**
     * profilePhoto
     */
    @ApiModelProperty(value = "profilePhoto")
  	private String profilePhoto;
	/**
	 * logoPhoto
	 */
	@ApiModelProperty(value = "logoPhoto")
	private String logoPhoto;
	/**
	 * logoRizhi
	 */
	@ApiModelProperty(value = "logoRizhi")
	private String logoRizhi;


    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
	@TableLogic
    private Integer isdelete;
	/**
	 * 操作人
	 */
	@ApiModelProperty(value = "操作人")
	private String caozuoren;
	/**
	 * 操作人id
	 */
	@ApiModelProperty(value = "操作人id")
	private Integer caozuorenid;
	/**
	 * 操作时间
	 */
	@ApiModelProperty(value = "操作时间")
	private String caozuoshijian;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间",required = true)
	private String createtime;

	/**
	 * 登录页附件(App)
	 */
	@ApiModelProperty(value = "login页附件(App)")
	private String loginPhotoApp;
	/**
	 * home页附件(App)
	 */
	@ApiModelProperty(value = "home页附件(App)")
	private String homePhotoApp;
	/**
	 * 简介页附件(App)
	 */
	@ApiModelProperty(value = "简介页附件(App)")
	private String profilePhotoApp;


	@ApiModelProperty(value = "类型")
	@TableField(exist = false)
	private String extendType;

	@ApiModelProperty(value = "上级id")
	@TableField(exist = false)
	private String parentId;

	@ApiModelProperty(value = "上级名称")
	@TableField(exist = false)
	private String parentName;

	@ApiModelProperty(value = "运营类型")
	private String yunyingleixing;

	@ApiModelProperty(value = "省")
	private String province;

	@ApiModelProperty(value = "市")
	private String city;

	@ApiModelProperty(value = "区县")
	private String country;

	@ApiModelProperty(value = "所属地市")
	private String area;

//	@ApiModelProperty(value = "企业ID")
//	private String qiyeid;
//
//	@ApiModelProperty(value = "企业名称")
//	private String qiyemingcheng;

    @ApiModelProperty(value = "法人电话")
    private String farendianhua;

    @ApiModelProperty(value = "法人身份证")
    private String farenshenfenzheng;

    @ApiModelProperty(value = "初次发证日期")
    private String jingyingxukezhengchulingriqi;

    @ApiModelProperty(value = "有效截至日期")
    private String jingyingxukezhengyouxiaoqi;

    @ApiModelProperty(value = "企业安全负责人电话")
    private String securityofficertelephone;

	@ApiModelProperty(value = "道路运输证附件")
	private String daoluyunshuzhengfujian;

	@ApiModelProperty(value = "道路运输证有效期(起)")
	private String daoluyunshuzhengkaishiriqi;

	@ApiModelProperty(value = "道路运输证有效期(止)")
	private String daoluyunshuzhengjieshuriqi;

	@ApiModelProperty(value = "经营许可证附件")
	private String jingyingxukezhengfujian;

	@ApiModelProperty(value = "工商营业执照附件")
	private String yingyezhizhaofujian;

	@ApiModelProperty(value = "道路许可证号")
	private String daoluxukezhenghao;

	@ApiModelProperty(value = "工商营业执照开始时间")
	private String yyzzksdate;

	@ApiModelProperty(value = "工商营业执照截止时间")
	private String yyzzjzdate;

	/**
	 * 导入错误信息
	 */
	@ApiModelProperty(value = "导入错误信息")
	@TableField(exist = false)
	private String Msg;

	@ApiModelProperty(value = "导入错误信息图标")
	@TableField(exist = false)
	private String importUrl;

	/**
	 * 岗位id
	 */
	@ApiModelProperty(value = "岗位id")
	@TableField(exist = false)
	private String postId;

	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名", required = true)
	@TableField(exist = false)
	private String xingming;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号", required = true)
	@TableField(exist = false)
	private String shoujihao;

	/**
	 * 聘用日期
	 */
	@ApiModelProperty(value = "聘用日期")
	@TableField(exist = false)
	private String pingyongriqi;

	/**
	 * 劳动合同开始日期
	 */
	@ApiModelProperty(value = "劳动合同开始日期")
	@TableField(exist = false)
	private String laodonghetongkaishiriqi;

	/**
	 * 劳动合同结束日期
	 */
	@ApiModelProperty(value = "劳动合同结束日期")
	@TableField(exist = false)
	private String laodonghetongjieshuriqi;

	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号", required = true)
	@TableField(exist = false)
	private String shenfenzhenghao;

	@ApiModelProperty(value = "身份证初领日期",required = true)
	@TableField(exist = false)
	private String shenfenzhengchulingriqi;
	@ApiModelProperty(value = "手机号码")
	@TableField(exist = false)
	private String shoujihaoma;

	@ApiModelProperty(value = "岗位名称")
	@TableField(exist = false)
	private String gangweimingcheng;

	/**
	 * 身份证有效期
	 */
	@ApiModelProperty(value = "身份证有效期",required = true)
	@TableField(exist = false)
	private String shenfenzhengyouxiaoqi;

	/**
	 * 从业资格证
	 */
	@ApiModelProperty(value = "从业资格证",required = true)
	@TableField(exist = false)
	private String congyezigezheng;
}
