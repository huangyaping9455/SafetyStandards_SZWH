package org.springblade.system.service;

import org.apache.ibatis.annotations.Param;
import org.springblade.system.entity.AnbiaoDriverImg;
import org.springblade.system.entity.AnbiaoJiashiyuan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 驾驶员信息表 服务类
 * </p>
 *
 * @author lmh
 * @since 2023-06-06
 */
public interface IAnbiaoJiashiyuanService extends IService<AnbiaoJiashiyuan> {

	AnbiaoDriverImg getByDriverImg(@Param("jsyId") String jsyId);

	List<AnbiaoDriverImg> getByDriverImgAll(String jiashiyuanxingming,String deptId);

}
