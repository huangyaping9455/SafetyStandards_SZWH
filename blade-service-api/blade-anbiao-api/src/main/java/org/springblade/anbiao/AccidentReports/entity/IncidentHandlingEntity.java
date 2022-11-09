package org.springblade.anbiao.AccidentReports.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description : 事故处理
 * @Author : long
 * @Date :2022/11/3 20:26
 */
@Data
@TableName("anbiao_shiguchuli")
public class IncidentHandlingEntity {
	private String  id;
	private Integer	dept_id;
	private String caozuoren;
	private Integer	caozuorenid;
	private Date caozuoshijian;
	private String shigubaogaoid;
	private String	shangwangcaichansunshi;
	private String	shiguyuanyin;
	private String	shigushangbaoqingkuang;
	private String	zerendaochaqingkuang;
	private String	zerenrenchufaqingkuang;
	private String	chulicuoshiluoshiqingkuang;
	private String	zerenrenjiaoyuqingkuang;
	private Integer	is_deleted;
	private Date createtime;
}
