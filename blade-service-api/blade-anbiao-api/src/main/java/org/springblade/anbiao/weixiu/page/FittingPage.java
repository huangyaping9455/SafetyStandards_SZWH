package org.springblade.anbiao.weixiu.page;

import lombok.Data;
import org.springblade.common.BasePage;

import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/8 18:51
 */
@Data
public class FittingPage extends BasePage {
	private String id;

	private Date startTime;

	private Date endTime;



}
