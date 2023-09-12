package org.springblade.anbiao.qiyeshouye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.qiyeshouye.entity.BaobiaoZhengfuQiye;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2023-08-30
 */
public interface IBaobiaoZhengfuQiyeService extends IService<BaobiaoZhengfuQiye> {

	List<BaobiaoZhengfuQiye> getZFQiYe(@Param("province") String province, @Param("city") String city, @Param("country") String country);


}
