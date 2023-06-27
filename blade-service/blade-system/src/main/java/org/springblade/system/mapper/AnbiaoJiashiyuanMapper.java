package org.springblade.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.springblade.system.entity.AnbiaoDriverImg;
import org.springblade.system.entity.AnbiaoJiashiyuan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 驾驶员信息表 Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2023-06-06
 */
public interface AnbiaoJiashiyuanMapper extends BaseMapper<AnbiaoJiashiyuan> {


	AnbiaoDriverImg getByDriverImg(@Param("jsyId") String jsyId);

	List<AnbiaoDriverImg> getByDriverImgAll(String jiashiyuanxingming,String deptId);

}
