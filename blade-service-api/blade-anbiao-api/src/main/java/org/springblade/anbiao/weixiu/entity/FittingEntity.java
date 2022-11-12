package org.springblade.anbiao.weixiu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description : 维修材料明细
 * @Author : long
 * @Date :2022/11/11 18:48
 */
@Data
@TableName("anbiao_weixiucailiaomingxi")
public class FittingEntity {
	@TableId(value = "id",type = IdType.UUID)
	private String id;
	private Long weihuid;
	private String caozuoren;
	private Integer	caozuorenid;
	private String caozuoshijian;

	private String cailiaomingcheng;
	private String xinghao;
	private String shuliang;

	@TableField(exist = false)
	private Long peijianID;

	private Long peijianid;
	private String danjia;
	private String 	beizhu;
	private String xiaoji;
	private String 	isdelete;
	private Date 	createtime;

	public void setPeijianid(Long peijianid) {
		this.peijianid = peijianid;
	}
}
