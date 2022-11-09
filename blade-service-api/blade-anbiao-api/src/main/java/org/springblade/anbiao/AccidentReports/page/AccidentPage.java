package org.springblade.anbiao.AccidentReports.page;

import lombok.Data;
import org.springblade.common.BasePage;

import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/8 18:50
 */
@Data
public class AccidentPage {
	private String id;
	private Date startTime;
	private Date endTime;

	private Integer current;
	private Integer size;
}
