package org.springblade.gps.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springblade.gps.entity.TcmdImgUrl;

import java.util.List;

/**
 * @author hyp
 * @create 2023-04-25 17:00
 */
@Data
@ApiModel(value = "TcmdImgVO", description = "TcmdImgVO")
public class TcmdImgVO {

	private List<TcmdImgUrl> Picture;

	private List<TcmdImgUrl> Video;
}
