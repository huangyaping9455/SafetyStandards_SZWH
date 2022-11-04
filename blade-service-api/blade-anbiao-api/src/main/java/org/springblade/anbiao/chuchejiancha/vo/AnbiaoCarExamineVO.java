package org.springblade.anbiao.chuchejiancha.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamine;
import org.springblade.core.tool.node.INode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-09-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AnbiaoCarExamineVO对象", description = "AnbiaoCarExamineVO对象")
public class AnbiaoCarExamineVO extends AnbiaoCarExamine implements INode {

	/**
	 * 主键ID
	 */
//	private Integer id;

	/**
	 * 父节点ID
	 */
	private Integer parentId;

	/**
	 * 子孙节点
	 */
	private List<INode> children;

	@Override
	public List<INode> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}

	private String deptName;

	private String deptId;

	private String createname;

	private String statusShow;

}
