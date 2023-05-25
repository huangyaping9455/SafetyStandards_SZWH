package org.springblade.common.tool.wx.util;

/**
 * @author hyp
 * @create 2023-05-22 11:28
 */
public class TemplateDataVo {

	String value;


	public TemplateDataVo(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "TemplateDataVo{" +
			"value='" + value + '\'' +
			'}';
	}
}
