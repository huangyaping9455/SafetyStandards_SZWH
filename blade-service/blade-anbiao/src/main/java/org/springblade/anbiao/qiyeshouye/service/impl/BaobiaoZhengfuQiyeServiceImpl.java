package org.springblade.anbiao.qiyeshouye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.qiyeshouye.entity.BaobiaoZhengfuQiye;
import org.springblade.anbiao.qiyeshouye.mapper.BaobiaoZhengfuQiyeMapper;
import org.springblade.anbiao.qiyeshouye.service.IBaobiaoZhengfuQiyeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2023-08-30
 */
@Service
@AllArgsConstructor
public class BaobiaoZhengfuQiyeServiceImpl extends ServiceImpl<BaobiaoZhengfuQiyeMapper, BaobiaoZhengfuQiye> implements IBaobiaoZhengfuQiyeService {

	private BaobiaoZhengfuQiyeMapper mapper;

	@Override
	public List<BaobiaoZhengfuQiye> getZFQiYe(String province, String city, String country) {
		return mapper.getZFQiYe(province, city, country);
	}
}
