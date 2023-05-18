package org.springblade.train.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.train.entity.*;
import org.springblade.train.mapper.*;
import org.springblade.train.service.IAreaService;
import org.springblade.train.service.IUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * <p>
 * ClassName: UnitServiceImpl
 * <p>
 * Description: [服务商政府企业接口实现]
 *
 * @author Tan
 * @date 2020年02月18日 13:23:18
 */
@Service
@AllArgsConstructor
public class UnitServiceImpl extends ServiceImpl<UnitMapper, Unit> implements IUnitService {

	@Autowired
	private UnitMapper unitMapper;

	@Autowired
	private AreaMapper areaMapper;

	@Autowired
	private IAreaService areaService;

	@Autowired
	private TradeKindMapper tradeKindMapper;

	@Autowired
	private CourseUnitMapper courseUnitMapper;

	@Autowired
	private StudentMapper studentMapper;



	/**
	 * 政府列表查询
	 *
	 * @param simpleName 政府名称
	 * @param type       营运商-0，政府-1，代理商-2，企业-3
	 * @return
	 */
	@Override
	public List<Unit> getGovernmentList(HashMap<String, String> param) throws Exception {


		// 政府列表查询
		QueryWrapper<Unit> queryWrapper = new QueryWrapper<Unit>();
		queryWrapper.lambda().like(StringUtils.isNotEmpty(param.get("simpleName")), Unit::getSimpleName, param.get("simpleName"));
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("type")), Unit::getType, param.get("type"));
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("deleted")), Unit::getDeleted, param.get("deleted"));
		String areaIds = param.get("areaIds");
		Set<Integer> ids = new HashSet<Integer>();
		if(areaIds != null) {
			String[] strings = areaIds.split(",");
			for (String string : strings) {
				ids.add(Integer.valueOf(string));
			}
		    queryWrapper.lambda().in(Unit::getAreaId, ids);
		}
		List<Unit> selectList = unitMapper.selectList(queryWrapper);

		// 区域临时HashMap
		HashMap<Integer, Area> areaMap = new HashMap<Integer, Area>();
		QueryWrapper<Area> wrapper = new QueryWrapper<Area>();
		wrapper.lambda().in(ids.size() > 0, Area::getId, ids);
		List<Area> areaList = areaMapper.selectList(wrapper);
		if (areaList != null && areaList.size() > 0) {
			for (Area area : areaList) {
				areaMap.put(area.getId(), area);
			}
		}

		// 区域父Id对应的区域id集合
		HashMap<Integer, List<Integer>> areaPidmap = new HashMap<Integer, List<Integer>>();

		// 区域按照Pid分组
		Map<Integer, List<Area>> collect = areaList.stream().collect(Collectors.groupingBy(Area::getPid));
		Iterator<Entry<Integer, List<Area>>> iterator = collect.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, List<Area>> entry = iterator.next();
			Integer key = entry.getKey();
			List<Integer> list = new ArrayList<Integer>();
			if (entry.getValue() != null && entry.getValue().size() > 0) {
				list.add(key);
				for (Area area : entry.getValue()) {
					list.add(area.getId());
				}
			}
			areaPidmap.put(key, list);
		}

		// 企业数统计HashMap<governmentId,count>
		HashMap<Integer, Integer> companymap = new HashMap<Integer, Integer>();
		QueryWrapper<Unit> companyWrapper = new QueryWrapper<Unit>();
		// 营运商-0，政府-1，代理商-2，企业-3
		companyWrapper.lambda().eq(Unit::getType, 3);
		companyWrapper.lambda().eq(Unit::getDeleted, 0);
		companyWrapper.lambda().ne(Unit::getGovernmentId, "");
		List<Unit> companyList = unitMapper.selectList(companyWrapper);
		// 按照所属政府id分组
		Map<Integer, List<Unit>> companyGroup = companyList.stream().collect(Collectors.groupingBy(Unit::getGovernmentId));
		Iterator<Entry<Integer, List<Unit>>> iterator2 = companyGroup.entrySet().iterator();
		while (iterator2.hasNext()) {
			Entry<Integer, List<Unit>> entry = iterator2.next();
			Integer governmentId = entry.getKey();
			Integer count = entry.getValue().size();
			companymap.put(governmentId, count);
		}

		// 组装所在区域名称
		if (selectList != null && selectList.size() > 0) {
			for (Unit unit : selectList) {
				// 组装所在区域名称
				Area area = areaMap.get(unit.getAreaId());
				if (area != null) {
					unit.setAreaName(area.getSimpleName());
				}

				// 组装企业数
				Integer companyCount = companymap.get(unit.getId());
				if(companyCount != null) {
					unit.setCompanyCount(companyCount);
				}
			}
		}

		return selectList;
	}

	/**
	 * 服务商列表查询
	 *
	 * @param simpleName 服务商名称
	 * @param type       营运商-0，政府-1，代理商-2，企业-3
	 * @return
	 */
	@Override
	public List<Unit> getServiceProviderList(HashMap<String, String> param) {
		// 服务商列表查询
		QueryWrapper<Unit> queryWrapper = new QueryWrapper<Unit>();
		queryWrapper.lambda().like(StringUtils.isNotEmpty(param.get("simpleName")), Unit::getSimpleName, param.get("simpleName"));
		String [] type = param.get("type").split(",");
		List<Integer> typeList = new ArrayList<Integer>();
		for (String s : type) {
			typeList.add(Integer.valueOf(s));
		}
		queryWrapper.lambda().in(Unit::getType, typeList);
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("deleted")), Unit::getDeleted, param.get("deleted"));

		String areaIds = param.get("areaIds");
		Set<Integer> ids = new HashSet<Integer>();
		if(areaIds != null) {
			String[] strings = areaIds.split(",");
			for (String string : strings) {
				ids.add(Integer.valueOf(string));
			}
		    queryWrapper.lambda().in(Unit::getAreaId, ids);
		}

		List<Unit> selectList = unitMapper.selectList(queryWrapper);

		// 区域临时HashMap
		HashMap<Integer, Area> areaMap = new HashMap<Integer, Area>();
		QueryWrapper<Area> wrapper = new QueryWrapper<Area>();
		wrapper.lambda().in(ids.size() > 0, Area::getId, ids);
		List<Area> areaList = areaMapper.selectList(wrapper);
		if (areaList != null && areaList.size() > 0) {
			for (Area area : areaList) {
				areaMap.put(area.getId(), area);
			}
		}

		// 区域父Id对应的区域id集合
		HashMap<Integer, List<Integer>> areaPidmap = new HashMap<Integer, List<Integer>>();

		// 区域按照Pid分组
		Map<Integer, List<Area>> collect = areaList.stream().collect(Collectors.groupingBy(Area::getPid));
		Iterator<Entry<Integer, List<Area>>> iterator = collect.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, List<Area>> entry = iterator.next();
			Integer key = entry.getKey();
			List<Integer> list = new ArrayList<Integer>();
			if (entry.getValue() != null && entry.getValue().size() > 0) {
				list.add(key);
				for (Area area : entry.getValue()) {
					list.add(area.getId());
				}
			}
			areaPidmap.put(key, list);
		}


		// 企业数统计HashMap<serverId,count>
		HashMap<Integer, Integer> companymap = new HashMap<Integer, Integer>();
		QueryWrapper<Unit> companyWrapper = new QueryWrapper<Unit>();
		// 营运商-0，政府-1，代理商-2，企业-3
		companyWrapper.lambda().eq(Unit::getType, 3);
		companyWrapper.lambda().eq(Unit::getDeleted, 0);
		companyWrapper.lambda().ne(Unit::getServerId, 0);
		List<Unit> companyList = unitMapper.selectList(companyWrapper);
		// 按照所属服务商id分组
		Map<Integer, List<Unit>> companyGroup = companyList.stream().collect(Collectors.groupingBy(Unit::getServerId));
		Iterator<Entry<Integer, List<Unit>>> iterator2 = companyGroup.entrySet().iterator();
		while (iterator2.hasNext()) {
			Entry<Integer, List<Unit>> entry = iterator2.next();
			Integer serverId = entry.getKey();
			Integer count = entry.getValue().size();
			companymap.put(serverId, count);
		}

		// 课程数统计HashMap<unitId,count> 查询课程表拓展表
		HashMap<Integer, Integer> courseUnitMap = new HashMap<Integer, Integer>();
		QueryWrapper<CourseUnit> courseUnitWrapper = new QueryWrapper<CourseUnit>();

		courseUnitWrapper.lambda().eq(CourseUnit::getDeleted, 0); // 删除标识 删除为1，默认为0
		courseUnitWrapper.lambda().eq(CourseUnit::getStatus, 1);  // 状态  上架-1，下架-2
		List<CourseUnit> courseUnitList = courseUnitMapper.selectList(courseUnitWrapper);
		// 按照企业id分组
		Map<Integer, List<CourseUnit>> courseUnitGroup = courseUnitList.stream().collect(Collectors.groupingBy(CourseUnit::getUnitId));
		Iterator<Entry<Integer, List<CourseUnit>>> iterator3 = courseUnitGroup.entrySet().iterator();
		while (iterator3.hasNext()) {
			Entry<Integer, List<CourseUnit>> entry = iterator3.next();
			Integer unitId = entry.getKey();
			Integer count = entry.getValue().size();
			courseUnitMap.put(unitId, count);
		}
		// 组装所在区域名称
		if (selectList != null && selectList.size() > 0) {
			for (Unit unit : selectList) {

				// 组装所在区域名称
				Area area = areaMap.get(unit.getAreaId());
				if (area != null) {
					unit.setAreaName(area.getSimpleName());
				}

				// 组装企业数
				Integer companyCount = companymap.get(unit.getId());
				if(companyCount != null) {
					unit.setCompanyCount(companyCount);
				}

				// 组装课程数
				Integer courseCount = courseUnitMap.get(unit.getId());
				if(courseCount != null) {
					unit.setCourseCount(courseCount);
				}

			}
		}

		return selectList;
	}

	/**
	 * 政府/服务商/企业管理-修改
	 *
	 * @param unit
	 * @return
	 */
	@Override
	public int updateUnit(Unit unit) {
		return unitMapper.updateById(unit);
	}

	/**
	 * 政府/服务商/企业管理-新增
	 *
	 * @param unit
	 * @return
	 */
	@Override
	public int insertUnit(Unit unit) {
		return unitMapper.insert(unit);
	}

	/**
	 * 政府/服务商/企业管理-删除
	 *
	 * @param ids = 1,2,3
	 * @return
	 */
	@Override
	public int deleteUnit(String ids) {
		List<Integer> idList = new ArrayList<Integer>();
		String[] strings = ids.split(",");
		for (String s : strings) {
			idList.add(Integer.valueOf(s));
		}
		UpdateWrapper<Unit> updateWrapper = new UpdateWrapper<Unit>();
		updateWrapper.lambda().in(Unit::getId, idList);
		Unit unit = new Unit();
		unit.setDeleted(1);
		return unitMapper.update(unit, updateWrapper);
	}

	/**
	 * 企业管理列表查询
	 * @param governmentId 政府id
	 * @param serviceId    服务商id
	 * @param type         营运商-0，政府-1，代理商-2，企业-3
	 * @param simpleName   企业名称
	 * @param tradeKindId  行业类型
	 * @return
	 */
	@Override
	public List<Unit> getCompanyList(HashMap<String, String> param) {
		// 企业管理列表查询
		QueryWrapper<Unit> queryWrapper = new QueryWrapper<Unit>();
		queryWrapper.lambda().like(StringUtils.isNotEmpty(param.get("simpleName")), Unit::getSimpleName, param.get("simpleName"));
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("serverId")), Unit::getServerId, param.get("serverId"));
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("governmentId")), Unit::getGovernmentId, param.get("governmentId"));
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("tradeKindId")), Unit::getTradeKindId, param.get("tradeKindId"));
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("type")), Unit::getType, param.get("type"));
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("deleted")), Unit::getDeleted, param.get("deleted"));
		String areaIds = param.get("areaIds");
		Set<Integer> ids = new HashSet<Integer>();
		if(areaIds != null && !"".equals(areaIds)) {
			String[] strings = areaIds.split(",");
			for (String string : strings) {
				ids.add(Integer.valueOf(string));
			}
		    queryWrapper.lambda().in(Unit::getAreaId, ids);
		}

		List<Unit> selectList = unitMapper.selectList(queryWrapper);


		// 查询政府列表存入临时HashMap
		HashMap<Integer, Unit> governmentMap = new HashMap<Integer, Unit>();
		QueryWrapper<Unit> governmentWrapper = new QueryWrapper<Unit>();
		// 类型 营运商-0，政府-1，代理商-2，企业-3
		governmentWrapper.lambda().eq(Unit::getType, 1);
		List<Unit> governmentList = unitMapper.selectList(governmentWrapper);
		if (governmentList != null && governmentList.size() > 0) {
			for (Unit unit : governmentList) {
				governmentMap.put(unit.getId(), unit);
			}
		}
		// 查询服务商列表存入临时HashMap
		HashMap<Integer, Unit> serviceMap = new HashMap<Integer, Unit>();
		QueryWrapper<Unit> serviceWrapper = new QueryWrapper<Unit>();

		// 类型 营运商-0，政府-1，代理商-2，企业-3[0,2 都表示服务商]
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(2);
		serviceWrapper.lambda().in(Unit::getType, list);
		List<Unit> serviceList = unitMapper.selectList(serviceWrapper);
		if (serviceList != null && serviceList.size() > 0) {
			for (Unit unit : serviceList) {
				serviceMap.put(unit.getId(), unit);
			}
		}

		// 查询学员数
		HashMap<Integer, Integer> studentMap = new HashMap<Integer, Integer>();
		QueryWrapper<Student> studentWrapper = new QueryWrapper<Student>();
		studentWrapper.lambda().ne(Student::getUnitId, "");
		studentWrapper.lambda().eq(Student::getDeleted, 0);
		List<Student> studentList = studentMapper.selectList(studentWrapper);
		// 学员按照UnitId分组 统计学员数量
		Map<Integer, List<Student>> collect = studentList.stream().collect(Collectors.groupingBy(Student::getUnitId));
		Iterator<Entry<Integer, List<Student>>> iterator = collect.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, List<Student>> entry = iterator.next();
			Integer key = entry.getKey();
			studentMap.put(key, entry.getValue().size());
		}


		// 区域临时HashMap
		HashMap<Integer, Area> areaMap = new HashMap<Integer, Area>();
		QueryWrapper<Area> wrapper = new QueryWrapper<Area>();
		List<Area> areaList = areaMapper.selectList(wrapper);
		if (areaList != null && areaList.size() > 0) {
			for (Area area : areaList) {
				areaMap.put(area.getId(), area);
			}
		}

		// 行业临时HashMap
		HashMap<Integer, TradeKind> traceKindMap = new HashMap<Integer, TradeKind>();
		QueryWrapper<TradeKind> tradeKindWrapper = new QueryWrapper<TradeKind>();
		List<TradeKind> tradeKindList = tradeKindMapper.selectList(tradeKindWrapper);
		if (tradeKindList != null && tradeKindList.size() > 0) {
			for (TradeKind tradeKind : tradeKindList) {
				traceKindMap.put(tradeKind.getId(), tradeKind);
			}
		}

		// 企业管理列表展示数据组装
		if (selectList != null && selectList.size() > 0) {
			for (Unit unit : selectList) {
				// 组装行业
				TradeKind tradeKind = traceKindMap.get(unit.getTradeKindId());
				if(tradeKind != null) {
					unit.setTradeKindName(tradeKind.getName());
				}

				// 组装所属区域
				Area area = areaMap.get(unit.getAreaId());
				if(area != null) {
					unit.setAreaName(area.getSimpleName());
				}

				// 组装学员数
				Integer studentCount = studentMap.get(unit.getId());
				if(studentCount != null) {
					unit.setStudentCount(studentCount);
				}

				// 组装服务商
				Unit server = serviceMap.get(unit.getServerId());
				if(server != null) {
					unit.setServerName(server.getSimpleName());
				}

				// 组装政府
				Unit goverment = governmentMap.get(unit.getGovernmentId());
				if(goverment != null) {
					unit.setGovernmentName(goverment.getSimpleName());
				}
			}
		}
		return selectList;
	}

	/**
	 * 根据所属服务商id查询
	 * @param serverId 所属服务商Id
	 * @param status 	状态 正常-1，暂停-2
	 * @param deleted 	删除标识 删除为1，默认为0
	 * @return
	 */
	@Override
	public List<Unit> getUnitListByServerId(String serverId, String status, String deleted) throws Exception {
		QueryWrapper<Unit> queryWrapper = new QueryWrapper<Unit>();
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(serverId), Unit::getServerId, serverId);
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(status), Unit::getStatus, status);
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(deleted), Unit::getDeleted, deleted);
		return unitMapper.selectList(queryWrapper);
	}

	/**
	 * 政府/服务商/企业详情
	 * @param id   主键Id
	 * @return
	 */
	@Override
	public Unit getUnitById(String id) {
		return unitMapper.selectById(id);
	}

	/**
	 *  政府/服务商/企业下拉框
	 */
	@Override
	public List<Unit> selectUnitList(HashMap<String, String> param) {
		QueryWrapper<Unit> queryWrapper = new QueryWrapper<Unit>();
		queryWrapper.lambda().like(StringUtils.isNotEmpty(param.get("simpleName")), Unit::getSimpleName, param.get("simpleName"));
		String [] type = param.get("type").split(",");
		List<Integer> typeList = new ArrayList<Integer>();
		for (String s : type) {
			typeList.add(Integer.valueOf(s));
		}
		queryWrapper.lambda().in(Unit::getType, typeList);
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("deleted")), Unit::getDeleted, param.get("deleted"));
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("status")), Unit::getStatus, param.get("status"));
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("serverId")), Unit::getServerId, param.get("serverId"));
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("tradeKindId")), Unit::getTradeKindId, param.get("tradeKindId"));
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("govId")), Unit::getGovernmentId, param.get("govId"));
		queryWrapper.lambda().eq(StringUtils.isNotEmpty(param.get("unitId")), Unit::getId, param.get("unitId"));

//		String areaIds = param.get("areaIds");
//		Set<Integer> ids = new HashSet<Integer>();
//		if(areaIds != null) {
//			String[] strings = areaIds.split(",");
//			for (String string : strings) {
//				ids.add(Integer.valueOf(string));
//			}
//		    queryWrapper.lambda().in(Unit::getAreaId, ids);
//		}

		return unitMapper.selectList(queryWrapper);
	}

	/**
	 * 当前登录用户下所属企业
	 */
	@Override
	public List<Unit> getCommCompanyList(AuthenticatedUser user, Integer type) {
		List<Unit> result = new ArrayList<Unit>();
		QueryWrapper<Unit> queryWrapper = new QueryWrapper<Unit>();
		queryWrapper.lambda().eq(Unit::getType, type); // 类型 营运商-0，政府-1，代理商-2，企业-3
		queryWrapper.lambda().eq(Unit::getDeleted, 0); // 删除标识 删除为1，默认为0
		queryWrapper.lambda().eq(Unit::getStatus, 1); // 状态 正常-1，暂停-2
		UserTypeEnum userType = user.getUserType();
		Set<Integer> ids = new HashSet<Integer>();
		// 未知
		if (userType == UserTypeEnum.UNKNOW) {
			return result;
		}
		// 运营商
		if (userType == UserTypeEnum.OPERATOR) {

			ids = this.areaIds(user);
			if(ids != null && ids.size() > 0) {
				queryWrapper.lambda().in(Unit::getAreaId, ids);
			}
			result = unitMapper.selectList(queryWrapper);
			return result;
		}

		// 政府
		if (userType == UserTypeEnum.GOVERNMENT) {
			ids = this.areaIds(user);
			if(ids != null && ids.size() > 0) {
				queryWrapper.lambda().in(Unit::getAreaId, ids);
			}
			queryWrapper.lambda().eq(Unit::getGovernmentId, user.getUnitId()); // 政府下面的企业
			result = unitMapper.selectList(queryWrapper);
			return result;
		}
		// 代理商
		if (userType == UserTypeEnum.AGENT) {
			ids = this.areaIds(user);
			if(ids != null && ids.size() > 0) {
				queryWrapper.lambda().in(Unit::getAreaId, ids);
			}
			queryWrapper.lambda().eq(Unit::getServerId, user.getUnitId()); // 代理商下面的企业
			result = unitMapper.selectList(queryWrapper);
			return result;
		}
		// 企业
		if (userType == UserTypeEnum.ENTERPRISE) {
			ids = this.areaIds(user);
			if(ids != null && ids.size() > 0) {
				queryWrapper.lambda().in(Unit::getAreaId, ids);
			}
			queryWrapper.lambda().eq(Unit::getId, user.getUnitId());
			result = unitMapper.selectList(queryWrapper);
			return result;
		}
		return result;
	}

	@Override
	public List<Unit> getUnit() {
		List<Unit> result = new ArrayList<Unit>();
		QueryWrapper<Unit> queryWrapper = new QueryWrapper<Unit>();
		queryWrapper.lambda().eq(Unit::getType, 3);
		queryWrapper.lambda().eq(Unit::getDeleted, 0); // 删除标识 删除为1，默认为0
		queryWrapper.lambda().eq(Unit::getStatus, 1); // 状态 正常-1，暂停-2
		result = unitMapper.selectList(queryWrapper);
		return result;
	}

	@Override
	public List<Unit> getUnit(Integer serverId) {
		List<Unit> result = new ArrayList<Unit>();
		QueryWrapper<Unit> queryWrapper = new QueryWrapper<Unit>();
		queryWrapper.lambda().eq(Unit::getType, 3);
		queryWrapper.lambda().eq(Unit::getServerId, serverId); // 类型 营运商-0，政府-1，代理商-2，企业-3
		queryWrapper.lambda().eq(Unit::getDeleted, 0); // 删除标识 删除为1，默认为0
		queryWrapper.lambda().eq(Unit::getStatus, 1); // 状态 正常-1，暂停-2
		result = unitMapper.selectList(queryWrapper);
		return result;
	}


	/**
	 * 获取当前登录用户数据权限区域id
	 * @param user
	 * @return
	 */
	private Set<Integer> areaIds(AuthenticatedUser user){
		List<String> areaIdList = new ArrayList<String>();
		String areaId = "";
		if(user.getAreaId()==0) {
			areaId = "1";
		}else {
			areaId = String.valueOf(user.getAreaId());
		}
		List<Area> listArea = areaService.getAreaByPid(areaId);
		if(listArea != null && listArea.size() > 0) {
			for (Area area : listArea) {
				areaIdList.add(String.valueOf(area.getId()));
			}
		}

		String areaIds = areaIdList.stream().collect(Collectors.joining(","));

		Set<Integer> ids = new HashSet<Integer>();
		if(areaIds != null) {
			String[] strings = areaIds.split(",");
			for (String string : strings) {
				ids.add(Integer.valueOf(string));
			}
		}

		return ids;
	}

	/**
	 * 根据Id获取政府/服务商/企业
	 * @return
	 */
	@Override
	public List<Unit> getUnitById(Integer id) {
		List<Unit> listUnit = new ArrayList<Unit>();
		Unit unit = unitMapper.selectById(id);
		if(unit != null) {
			listUnit.add(unit);
		}
		return listUnit;
	}

}
