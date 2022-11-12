package org.springblade.anbiao.labor.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/4 10:57
 */
@Data
public class laobaolingquDTO {
	private String alr_person_autograph;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date alr_receipt_date;

	private Integer alr_receipts_number;
}
