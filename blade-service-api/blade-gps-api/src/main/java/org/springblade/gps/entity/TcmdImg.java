package org.springblade.gps.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hyp
 * @create 2023-04-25 16:03
 */
@Data
@ApiModel(value = "TcmdImg", description = "TcmdImg")
public class TcmdImg implements Serializable {

	private String fileUrl;

	private String alarmNumber;

	private String fileType;

}
