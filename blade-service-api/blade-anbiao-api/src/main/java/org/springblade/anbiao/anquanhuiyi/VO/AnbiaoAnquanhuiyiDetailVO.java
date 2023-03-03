package org.springblade.anbiao.anquanhuiyi.VO;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiDetail;

import java.net.URL;

/**
 * @author hyp
 * @create 2023-02-15 11:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AnbiaoAnquanhuiyiDetailVO对象", description = "AnbiaoAnquanhuiyiDetailVO对象")
public class AnbiaoAnquanhuiyiDetailVO extends AnbiaoAnquanhuiyiDetail {

	@ColumnWidth(15)
	@ExcelProperty("人脸照片")
	@TableField(exist = false)
	private URL addApHeadPortraitImgUrl;

	@ColumnWidth(15)
	@ExcelProperty("签名")
	@TableField(exist = false)
	private URL addApAutographImgUrl;
}
