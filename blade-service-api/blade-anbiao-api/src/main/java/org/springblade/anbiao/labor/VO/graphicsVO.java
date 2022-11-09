package org.springblade.anbiao.labor.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description :劳保图形
 * @Author : long
 * @Date :2022/11/4 22:22
 */
@Data
public class graphicsVO {

	private Integer ali_issue_quantity;

	private String ali_application_scope;

	private Integer ali_issue_people_number;

	private String jiashiyuan;

	private String guanli;

	private String qita;
}
