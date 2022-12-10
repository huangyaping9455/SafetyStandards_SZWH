package org.springblade.anbiao.guanlijigouherenyuan.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springblade.system.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 企业信息表 Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2022-11-20
 */
@Mapper
public interface BladeDeptMapper extends BaseMapper<Dept> {
	int MaxId();
}
