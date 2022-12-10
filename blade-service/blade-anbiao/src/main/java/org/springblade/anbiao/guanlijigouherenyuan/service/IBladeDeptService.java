package org.springblade.anbiao.guanlijigouherenyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.system.entity.Dept;

/**
 * <p>
 * 企业信息表 服务类
 * </p>
 *
 * @author lmh
 * @since 2022-11-20
 */
public interface IBladeDeptService extends IService<Dept> {
	int MaxId();
}
