package org.springblade.doc.biaozhunhuamuban.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @program: SafetyStandards
 * @description: 标准模板对象
 * @author: jx
 * @create2021-04-25 19:18
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "标准模板对象", description = "标准模板对象")
public class BiaozhunhuamubanPage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;
	/**
	 * 单位id
	 */
	@ApiModelProperty(value = "单位id",required=true)
	private Integer deptId;

	@ApiModelProperty(value="文件性质(法律法规,规则制度,操作规程....)",required=true)
	private String fileProperty;

	@ApiModelProperty(value = "文件类型")
	private String leixing;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

}
