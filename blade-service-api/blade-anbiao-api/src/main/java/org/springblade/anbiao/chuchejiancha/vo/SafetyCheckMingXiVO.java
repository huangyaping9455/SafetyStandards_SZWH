package org.springblade.anbiao.chuchejiancha.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.net.URL;

@Data
@ApiModel(value = "SafetyCheckMingXiVO对象", description = "SafetyCheckMingXiVO对象")
public class SafetyCheckMingXiVO {

	private String deptName;
	private String jiashiyuanxingming;
	private String cheliangpaizhao;
	private String infoId;
	private String vehid;
	private String deptId;
	private String status;
	private String date;
	private String remarkId;
	private String examid;
	private String xiangid;
	private String jcrsignatrue;
	@ColumnWidth(15)
	@ExcelProperty("签名")
	@TableField(exist = false)
	private URL image;

}
