package org.springblade.gps.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hyp
 * @create 2023-04-25 16:03
 */
@Data
@ApiModel(value = "TcmdImgUrl", description = "TcmdImgUrl")
public class TcmdImgUrl implements Serializable {

	private String fileUrl;

}
