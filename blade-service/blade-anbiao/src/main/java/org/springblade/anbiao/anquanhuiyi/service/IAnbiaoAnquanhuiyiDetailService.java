package org.springblade.anbiao.anquanhuiyi.service;

import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 安全会议参会记录 服务类
 * </p>
 *
 * @author lmh
 * @since 2022-11-01
 */
public interface IAnbiaoAnquanhuiyiDetailService extends IService<AnbiaoAnquanhuiyiDetail> {

	List<AnbiaoAnquanhuiyiDetail> selectPersonnelType (AnbiaoAnquanhuiyiDetail anquanhuiyiDetail);

}
