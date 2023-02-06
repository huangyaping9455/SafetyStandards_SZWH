package org.springblade.anbiao.labor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;


/**
 * @Description :
 * @Author : long
 * @Date :2022/11/12 11:10
 */
@Mapper
public interface LaborlingquMapper extends BaseMapper<LaborlingquEntity> {
	LaborlingquEntity selectSumReceive(String alrAliIds);
}
