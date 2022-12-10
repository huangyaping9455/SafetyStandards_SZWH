package org.springblade.anbiao.guanlijigouherenyuan.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.system.entity.Dept;
import org.springblade.anbiao.guanlijigouherenyuan.mapper.BladeDeptMapper;
import org.springblade.anbiao.guanlijigouherenyuan.service.IBladeDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业信息表 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2022-11-20
 */
@Service
@AllArgsConstructor
public class BladeDeptServiceImpl extends ServiceImpl<BladeDeptMapper, Dept> implements IBladeDeptService {

	private BladeDeptMapper bladeDeptMapper;

	@Override
	public int MaxId() {
		return bladeDeptMapper.MaxId();
	}
}
