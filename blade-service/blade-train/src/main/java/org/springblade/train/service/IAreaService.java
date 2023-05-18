package org.springblade.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.train.entity.Area;
import org.springblade.train.entity.ZAreaTree;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <p>ClassName: IAreaService
 * <p>Description: [行政区接口]
 * @author Tan
 * @date 2020年02月18日 12:45:54
*/
public interface IAreaService extends IService<Area>{

	/**
	 * 行政区树形查询
	 * @param param
	 * @return List<ZAreaTree>
	 */
	List<ZAreaTree> getAreaZtreeList(HashMap<String,String> param) throws Exception;

	List<Area> getAreaByPid(String pid);

}
