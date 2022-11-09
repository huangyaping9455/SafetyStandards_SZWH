package org.springblade.anbiao.labor.VO;

import lombok.Data;

import java.util.Date;

/**
 * @Description : 企管领取详情列表
 * @Author : long
 * @Date :2022/11/4 11:00
 */
@Data
public class laobaolingquVO {
	private String alr_person_name;
	private Integer alr_receipts_number;
	private Date alr_receipt_date;
	private String 	alr_person_autograph;
}
