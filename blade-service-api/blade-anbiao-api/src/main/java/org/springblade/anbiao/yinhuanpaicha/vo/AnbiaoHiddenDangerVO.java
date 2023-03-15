package org.springblade.anbiao.yinhuanpaicha.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoHiddenDanger;

import java.net.URL;

/**
 * @author hyp
 * @create 2022-10-31 18:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@HeadRowHeight(25)
@ContentRowHeight(20)
@ApiModel(value = "AnbiaoHiddenDangerVO对象", description = "AnbiaoHiddenDangerVO对象")
public class AnbiaoHiddenDangerVO extends AnbiaoHiddenDanger {

	@ApiModelProperty(value = "企业名称")
	private String deptname;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ColumnWidth(15)
	@ExcelProperty("图片")
	private URL imgUrl;

	@ApiModelProperty(value = "驾驶员id")
	private String jiashiyuanid;


}
