package org.springblade.anbiao.jiashiyuan.feign;

import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanTrain;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: SafetyStandards
 **/
@Component
public class IJiaShiYuanClientBack implements IJiaShiYuanClient {

	@Override
	public List<JiaShiYuanTrain> selectJiaShiYuanTrain(Integer deptId, String driverId) {
		return null;
	}
}
