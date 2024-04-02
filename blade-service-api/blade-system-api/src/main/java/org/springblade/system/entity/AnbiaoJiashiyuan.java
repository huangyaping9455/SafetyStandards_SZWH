package org.springblade.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 驾驶员信息表
 * </p>
 *
 * @author lmh
 * @since 2023-06-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_jiashiyuan")
@ApiModel(value="AnbiaoJiashiyuan对象", description="驾驶员信息表")
public class AnbiaoJiashiyuan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "驾驶员姓名")
    private String jiashiyuanxingming;

    @ApiModelProperty(value = "照片")
    private String zhaopian;

    @ApiModelProperty(value = "性别")
    private String xingbie;

    @ApiModelProperty(value = "身份证号")
    private String shenfenzhenghao;

    @ApiModelProperty(value = "出生时间")
    private String chushengshijian;

    @ApiModelProperty(value = "年龄")
    private String nianling;

    @ApiModelProperty(value = "手机号码")
    private String shoujihaoma;

    @ApiModelProperty(value = "从业人员类型")
    private String congyerenyuanleixing;

    @ApiModelProperty(value = "身份证初领日期")
    private String shenfenzhengchulingriqi;

    @ApiModelProperty(value = "身份证有效期")
    private String shenfenzhengyouxiaoqi;

    @ApiModelProperty(value = "文化程度")
    private String wenhuachengdu;

    @ApiModelProperty(value = "聘用日期")
    private String pingyongriqi;

    @ApiModelProperty(value = "机动驾驶员")
    private String jidongjiashiyuan;

    @ApiModelProperty(value = "驾驶员类型")
    private String jiashiyuanleixing;

    @ApiModelProperty(value = "企业ID")
    private String deptId;

    @ApiModelProperty(value = "身份证附件正面")
    private String shenfenzhengfujian;

    @ApiModelProperty(value = "身份证附件反面")
    private String shenfenzhengfanmianfujian;

    @ApiModelProperty(value = "从业证附件")
    private String congyezhengfujian;

    @ApiModelProperty(value = "驾驶证附件")
    private String jiashizhengfujian;

    @ApiModelProperty(value = "家庭住址")
    private String jiatingzhuzhi;

    @ApiModelProperty(value = "档案标号")
    private String danganbianhao;

    @ApiModelProperty(value = "驾驶证号")
    private String jiashizhenghao;

    @ApiModelProperty(value = "准驾车型")
    private String zhunjiachexing;

    @ApiModelProperty(value = "驾龄")
    private String jialing;

    @ApiModelProperty(value = "违法积分")
    private String weifajifen;

    @ApiModelProperty(value = "驾驶证初领日期")
    private String jiashizhengchulingriqi;

    @ApiModelProperty(value = "驾驶证有效期")
    private String jiashizhengyouxiaoqi;

    @ApiModelProperty(value = "体检有效期")
    private String tijianyouxiaoqi;

    @ApiModelProperty(value = "从业资格证")
    private String congyezigezheng;

    @ApiModelProperty(value = "从业类别")
    private String congyeleibie;

    @ApiModelProperty(value = "从业证有效期")
    private String congyezhengyouxiaoqi;

    @ApiModelProperty(value = "从业证初领日")
    private String congyezhengchulingri;

    @ApiModelProperty(value = "证件核发机关")
    private String zhengjianhefajiguan;

    @ApiModelProperty(value = "发证机关")
    private String fazhengjiguan;

    @ApiModelProperty(value = "诚信考核时间")
    private String chengxinkaoheshijian;

    @ApiModelProperty(value = "下次诚信考核时间")
    private String xiacichengxinkaoheshijian;

    @ApiModelProperty(value = "继续教育时间")
    private String jixujiaoyushijian;

    @ApiModelProperty(value = "下次继续教育时间")
    private String xiacijixujiaoyushijian;

    @ApiModelProperty(value = "从业资格类别")
    private String congyezigeleibie;

    @ApiModelProperty(value = "证件状态")
    private String zhengjianzhuangtai;

    @ApiModelProperty(value = "护照号码")
    private String huzhaohaoma;

    @ApiModelProperty(value = "护照类别")
    private String huzhaoleibie;

    @ApiModelProperty(value = "国家码")
    private String guojiama;

    @ApiModelProperty(value = "护照有效期")
    private String huzhaoyouxiaoqi;

    @ApiModelProperty(value = "准假证号")
    private String zhunjiazhenghao;

    @ApiModelProperty(value = "准驾类型")
    private String zhunjialeixing;

    @ApiModelProperty(value = "准运线")
    private String zhunyunxian;

    @ApiModelProperty(value = "准驾证有效期")
    private String zhunjiazhengyouxiaoqi;

    @ApiModelProperty(value = "缴纳标准")
    private String jiaonabiaozhun;

    @ApiModelProperty(value = "缴纳金额")
    private String jiaonajine;

    @ApiModelProperty(value = "是否缴纳")
    private String shifoujiaona;

    @ApiModelProperty(value = "超速违法记录")
    private String chaosuweifajilu;

    @ApiModelProperty(value = "交通违法记录")
    private String jiaotongweifajilu;

    @ApiModelProperty(value = "致人死亡责任")
    private String zhirensiwangzeren;

    @ApiModelProperty(value = "违规类型")
    private String weiguileixing;

    @ApiModelProperty(value = "驾车经历")
    private String jiachejingli;

    @ApiModelProperty(value = "备注")
    private String beizhu;

    @ApiModelProperty(value = "复印件")
    private String fuyinjian;

    @ApiModelProperty(value = "操作人id")
    private Integer caozuorenid;

    @ApiModelProperty(value = "操作时间")
    private String caozuoshijian;

    @ApiModelProperty(value = "操作人")
    private String caozuoren;

    @ApiModelProperty(value = "部门")
    private String bumen;

    @ApiModelProperty(value = "离职时间")
    private String lizhishijian;

    @ApiModelProperty(value = "登录密码")
    private String denglumima;

    private Integer isdelete;

    private String createtime;

    @ApiModelProperty(value = "体检日期")
    private String tijianriqi;

    private String openid;

    @ApiModelProperty(value = "状态，默认0：在职，1：离职")
    private Integer status;

	@TableField(exist = false)
    private Integer sfzCompeleted;

	@ApiModelProperty(value = "deptName")
	@TableField(exist = false)
	private String deptName;


	@ApiModelProperty(value = "count")
	@TableField(exist = false)
	private Integer count = 0;

	@ApiModelProperty(value = "入职ID")
	@TableField(exist = false)
	private String ruzhiid;

	@ApiModelProperty(value = "入职头像附件")
	@TableField(exist = false)
	private String ruzhiimg;

	@ApiModelProperty(value = "身份证正面附件")
	@TableField(exist = false)
	private String sfzzmimg;

	@ApiModelProperty(value = "身份证反面附件")
	@TableField(exist = false)
	private String sfzfmimg;

	@ApiModelProperty(value = "驾驶证ID")
	@TableField(exist = false)
	private String jszid;

	@ApiModelProperty(value = "驾驶证正面附件")
	@TableField(exist = false)
	private String jszzmimg;

	@ApiModelProperty(value = "驾驶证反面附件")
	@TableField(exist = false)
	private String jszfmimg;

	@ApiModelProperty(value = "从业资格证ID")
	@TableField(exist = false)
	private String cyzid;

	@ApiModelProperty(value = "从业资格证附件")
	@TableField(exist = false)
	private String cyzimg;

	@ApiModelProperty(value = "体检表ID")
	@TableField(exist = false)
	private String tjid;

	@ApiModelProperty(value = "体检表附件")
	@TableField(exist = false)
	private String tjimg;

	@ApiModelProperty(value = "岗前培训ID")
	@TableField(exist = false)
	private String gqid;

	@ApiModelProperty(value = "岗前培训附件")
	@TableField(exist = false)
	private String gqimg;

	@ApiModelProperty(value = "无责证明ID")
	@TableField(exist = false)
	private String wzzmid;

	@ApiModelProperty(value = "无责证明附件")
	@TableField(exist = false)
	private String wzzmimg;

	@ApiModelProperty(value = "其他ID")
	@TableField(exist = false)
	private String qtid;

	@ApiModelProperty(value = "其他附件")
	@TableField(exist = false)
	private String qtimg;

	@ApiModelProperty(value = "入职影像附件数")
	@TableField(exist = false)
	private Integer ruzhiimgzcount = 0;

	@ApiModelProperty(value = "身份证影像附件数")
	@TableField(exist = false)
	private Integer sfzimgzcount = 0;

	@ApiModelProperty(value = "驾驶证影像附件数")
	@TableField(exist = false)
	private Integer jszimgcount = 0;

	@ApiModelProperty(value = "从业资格证影像附件数")
	@TableField(exist = false)
	private Integer cyzcount = 0;

	@ApiModelProperty(value = "车辆登记证书影像附件数")
	@TableField(exist = false)
	private Integer djzimgcount = 0;

	@ApiModelProperty(value = "体检表影像附件数")
	@TableField(exist = false)
	private Integer tjcount = 0;

	@ApiModelProperty(value = "岗前培训影像附件数")
	@TableField(exist = false)
	private Integer gqimgcount = 0;

	@ApiModelProperty(value = "无责证明影像附件数")
	@TableField(exist = false)
	private Integer wzzmimgcount = 0;

	@ApiModelProperty(value = "其他影像附件数")
	@TableField(exist = false)
	private Integer qtimgcount = 0;

	@ApiModelProperty(value = "驾驶员id")
	@TableField(exist = false)
	private String jiashiyuanId;

	@ApiModelProperty(value = "表id")
	@TableField(exist = false)
	private String tableId;

	@ApiModelProperty(value = "附件")
	@TableField(exist = false)
	private String attachments;

	@ApiModelProperty(value = "是否统计")
	@TableField(exist = false)
	private Integer isCount=0;

	@ApiModelProperty(value = "是否分配")
	@TableField(exist = false)
	private Integer isDistribution=0;

	@ApiModelProperty(value = "showBotton")
	@TableField(exist = false)
	private Boolean showBotton;

	@ApiModelProperty(value = "节点id")
	@TableField(exist = false)
	private String nodeId;

	@ApiModelProperty(value = "身份证数量")
	@TableField(exist = false)
	private String SFZcount;

	@ApiModelProperty(value = "入职表数量")
	@TableField(exist = false)
	private String RZcount;

	@ApiModelProperty(value = "驾驶证数量")
	@TableField(exist = false)
	private String JSZcount;

	@ApiModelProperty(value = "从业证数量")
	@TableField(exist = false)
	private String CYZcount;

	@ApiModelProperty(value = "体检数量")
	@TableField(exist = false)
	private String TJcount;

	@ApiModelProperty(value = "岗前培训数量")
	@TableField(exist = false)
	private String GQPXcount;

	@ApiModelProperty(value = "无责证明数量")
	@TableField(exist = false)
	private String WZZMcount;

	@ApiModelProperty(value = "其他数量")
	@TableField(exist = false)
	private String QTcount;



}
