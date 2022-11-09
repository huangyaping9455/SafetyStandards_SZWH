package org.springblade.anbiao.weixiu.page;

import lombok.Data;
import org.springblade.common.BasePage;

import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/8 18:52
 */
@Data
public class MaintenancePage  {
	private String id;

	private Date startTime;

	private Date endTime;

	private Integer current;

	private Integer size;
}
