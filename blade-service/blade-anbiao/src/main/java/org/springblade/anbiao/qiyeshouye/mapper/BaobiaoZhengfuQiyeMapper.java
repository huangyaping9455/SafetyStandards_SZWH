package org.springblade.anbiao.qiyeshouye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.qiyeshouye.entity.BaobiaoZhengfuQiye;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-08-30
 */
public interface BaobiaoZhengfuQiyeMapper extends BaseMapper<BaobiaoZhengfuQiye> {

	List<BaobiaoZhengfuQiye> getZFQiYe(@Param("province") String province,@Param("city") String city,@Param("country") String country);

}
