package org.springblade.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 企业详细信息
 * </p>
 *
 * @author lmh
 * @since 2023-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_organization")
@ApiModel(value="AnbiaoOrganization对象", description="企业详细信息")
public class AnbiaoOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "单位ID")
    private String deptId;

    @ApiModelProperty(value = "单位名称")
    private String deptName;

    @ApiModelProperty(value = "机构负责人")
    private String jigoufuzeren;

    @ApiModelProperty(value = "机构编码")
    private String jigoubianma;

    @ApiModelProperty(value = "机构类型")
    private String jigouleixing;

    @ApiModelProperty(value = "机构资质")
    private String jigouzizhi;

    @ApiModelProperty(value = "法人代表")
    private String farendaibiao;

    @ApiModelProperty(value = "法人电话")
    private String farendianhua;

    @ApiModelProperty(value = "法人身份证")
    private String farenshenfenzheng;

    @ApiModelProperty(value = "联系电话")
    private String lianxidianhua;

    @ApiModelProperty(value = "注册资本")
    private String zhuceziben;

    @ApiModelProperty(value = "投资总额")
    private String touzizonge;

    @ApiModelProperty(value = "车辆规模")
    private String cheliangguimo;

    @ApiModelProperty(value = "从业人员数量")
    private String congyerenshuliang;

    @ApiModelProperty(value = "成立时间")
    private String chenglishijian;

    @ApiModelProperty(value = "停车场面积")
    private String tingchechangmianji;

    @ApiModelProperty(value = "停车场位置")
    private String tingchechangweizhi;

    @ApiModelProperty(value = "驾驶员数量")
    private String jiashiyuanshuliang;

    @ApiModelProperty(value = "押运员数量")
    private String yayunyuanshuliang;

    @ApiModelProperty(value = "装卸员数量")
    private String zhuangxieyuanshuliang;

    @ApiModelProperty(value = "序号")
    private String xuhao;

    @ApiModelProperty(value = "行政区代码")
    private String xingzhengqudaima;

    @ApiModelProperty(value = "经营许可证编码")
    private String jingyingxukezhengbianma;

    @ApiModelProperty(value = "经营许可证初初领日期")
    private String jingyingxukezhengchulingriqi;

    @ApiModelProperty(value = "经营许可证有效期")
    private String jingyingxukezhengyouxiaoqi;

    @ApiModelProperty(value = "法人代表附件")
    private String fujian;

    @ApiModelProperty(value = "安全管理图")
    private String anquanguanlitu;

    @ApiModelProperty(value = "组织机构图")
    private String zuzhijigoutu;

    @ApiModelProperty(value = "经营范围")
    private String jingyingfanwei;

    @ApiModelProperty(value = "经营地址")
    private String jingyingdizhi;

    @ApiModelProperty(value = "详细地址")
    private String xiangxidizhi;

    @ApiModelProperty(value = "机构描述")
    private String jigoumiaoshu;

    @ApiModelProperty(value = "LOGO")
    private String loginPhoto;

    private String homePhoto;

    private String profilePhoto;

    private String logoPhoto;

    private String logoRizhi;

    private String caozuoren;

    private Integer caozuorenid;

    private LocalDateTime caozuoshijian;

    private LocalDateTime createtime;

    private String loginPhotoApp;

    private String homePhotoApp;

    private String profilePhotoApp;

    private Integer isdelete;

    @ApiModelProperty(value = "运营类型(1危货，2普货，3客运)")
    private String yunyingleixing;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区县")
    private String country;

    @ApiModelProperty(value = "所属地市")
    private String area;

    @ApiModelProperty(value = "企业安全负责人电话")
    private String securityofficertelephone;

    @ApiModelProperty(value = "平台名称")
    private String platformname;

    @ApiModelProperty(value = "版权所有")
    private String copyrighter;

    @ApiModelProperty(value = "技术支持")
    private String technicalsupport;

    @ApiModelProperty(value = "道路许可证号")
    private String daoluxukezhenghao;

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

    @ApiModelProperty(value = "工商营业执照开始时间")
    private String yyzzksdate;

    @ApiModelProperty(value = "工商营业执照截止时间")
    private String yyzzjzdate;

    @ApiModelProperty(value = "合同期限开始时间")
    private String htbegindate;

    @ApiModelProperty(value = "合同期限结束时间")
    private String htlastdate;

    @ApiModelProperty(value = "经济类型")
    private String jingjileixing;

    @ApiModelProperty(value = "企业分级")
    private String qiyefenji;


}
