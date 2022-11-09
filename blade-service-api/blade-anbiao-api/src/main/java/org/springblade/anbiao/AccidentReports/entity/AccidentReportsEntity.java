package org.springblade.anbiao.AccidentReports.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/2 14:20
 */
@Data
@TableName("anbiao_shigubaogao")
public class AccidentReportsEntity {
	private String id;
	private Integer dept_id;
	private String  caozuoren;
	private Integer caozuorenid;
	private Date caozuoshijian;
	private String  cheliangid;
	private String chepaihao;
	private String chexing;
	private BigDecimal hezaishu;
	private BigDecimal shizaishu;
	private String weihuapinming;
	private String jiashiyuan;
	private String congyezigeleibie;
	private String congyezigezhenghao;
	private String shigufenlei;
	private String shiguzeren;
	private String shiguxingzhi;
	private Date shigufashengshijian;
	private String shigufashengdidian;
	private String tianqiqingkuang;
	private String gonglujishudengji;
	private String xianxingzhuangkuang;
	private String lumianzhuangkuang;
	private String shiguzhijieyuanyin;
	private String yunxingxianlu;
 	private String xianluleibie;
	private String shifazhan;
	private String chezhandengji;
	private Integer siwang;
	private Integer	shizong;
	private Integer	shoushang;
	private Integer	waijisiwang;
	private Integer waijishizong;
	private Integer	waijishoushang;
	private BigDecimal caichansunshi;
	private BigDecimal jianjiejingjisunshi;
	private String shigugaikuang;
	private String zerenfenxi;
	private String shiguzhaopian;
	private String fujian;
	private Integer	is_deleted;
	private Date createtime;
}
