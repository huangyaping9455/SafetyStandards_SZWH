package org.springblade.train.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.train.entity.Area;
import org.springblade.train.entity.ZAreaTree;
import org.springblade.train.mapper.AreaMapper;
import org.springblade.train.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * <p>ClassName: AreaServiceImpl
 * <p>Description: [行政区接口实现]
 * @author Tan
 * @date 2020年02月18日 13:23:18
*/
@Service
@AllArgsConstructor
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

	@Autowired
	private AreaMapper areaMapper;


	/**
	 * 根据父id查询区域
	 */
	@Override
	public List<Area> getAreaByPid(String pid) {
		List<Area> listArea = new ArrayList<Area>();
		QueryWrapper<Area> queryWrapper = new QueryWrapper<Area>();

		// 查询所有的区域
		if(pid.equals("1")) {
			listArea = areaMapper.selectList(queryWrapper);

		}else {
			queryWrapper.lambda().like(Area::getFullid, String.format("%08d", Integer.valueOf(pid)));
		}


		// 根据pid查询区域
		listArea = areaMapper.selectList(queryWrapper);
		if (listArea != null && listArea.size() > 0) {
			return listArea;
		} else {
			// 查询自己
			QueryWrapper<Area> wrapper = new QueryWrapper<Area>();
			wrapper.lambda().eq(Area::getId, pid);
			listArea = areaMapper.selectList(wrapper);
		}
		return listArea;
	}

	@Override
	public List<ZAreaTree> getAreaZtreeList(HashMap<String,String> param) throws Exception {
		List<Area> listArea = new ArrayList<Area>();
		QueryWrapper<Area> queryWrapper = new QueryWrapper<Area>();
		// 查询所有的区域
		if(param.get("areaId").equals("1")) {
			listArea = areaMapper.selectList(queryWrapper);

		}else {
			queryWrapper.lambda().like(StringUtils.isNotEmpty(param.get("areaId")),Area::getFullid, String.format("%08d", Integer.valueOf(param.get("areaId"))));
		}

		// 根据pid查询区域
		listArea = areaMapper.selectList(queryWrapper);
		if (listArea == null) {
			// 查询自己
			QueryWrapper<Area> wrapper = new QueryWrapper<Area>();
			wrapper.lambda().eq(Area::getId, param.get("areaId"));
			listArea = areaMapper.selectList(wrapper);
		}


		List<ZAreaTree> list = new ArrayList<ZAreaTree>();
		if(listArea != null && listArea.size() > 0) {
			for (Area area : listArea) {
				ZAreaTree ztree = new ZAreaTree();
				ztree.setId(area.getId());
				ztree.setPId(area.getPid());
				ztree.setName(area.getSimpleName());
				list.add(ztree);
			}
		}
		return list;
	}

}
