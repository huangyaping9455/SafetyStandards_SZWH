package org.springblade.anbiao.weixiu.DTO;

import lombok.Data;

import java.util.Date;

/**
 * @Description : 维修配件明细
 * @Author : long
 * @Date :2022/11/11 18:48
 */
@Data
public class FittingDTO {
	private String id;
	private Long weihuid;
	private String caozuoren;
	private Integer	caozuorenid;
	private String caozuoshijian;

	private String cailiaomingcheng;
	private String xinghao;
	private String shuliang;

	private Long peijianID;
	private String danjia;
	private String 	beizhu;
	private String xiaoji;
	private String 	isdelete;
	private Date 	createtime;
}
