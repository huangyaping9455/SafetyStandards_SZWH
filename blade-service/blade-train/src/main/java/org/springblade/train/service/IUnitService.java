package org.springblade.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.train.entity.AuthenticatedUser;
import org.springblade.train.entity.Unit;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <p>ClassName: IUnitService
 * <p>Description: [服务商政府企业接口]
 * @author Tan
 * @date 2020年02月18日 12:45:54
*/
public interface IUnitService extends IService<Unit>{


	/**
	    *   政府管理列表查询
	 * @param param 查询条件
	 * @return
	 */
	List<Unit> getGovernmentList(HashMap<String, String> param) throws Exception;

	/**
	 * 服务商管理列表查询
	 * @param simpleName 服务商名称
	 * @param type       营运商-0，政府-1，代理商-2，企业-3
	 * @return
	 */
	List<Unit> getServiceProviderList(HashMap<String, String> param);

	/**
	 * 政府/服务商/企业管理-修改
	 * @param unit
	 * @return
	 */
	int updateUnit(Unit unit);

	/**
	 * 政府/服务商/企业管理-新增
	 * @param unit
	 * @return
	 */
	int insertUnit(Unit unit);

	/**
	 * 政府/服务商/企业管理-删除
	 * @param ids = 1,2,3
	 * @return
	 */
	int deleteUnit(String ids);

	/**
	   * 企业管理列表查询
	 * @param param 查询条件
	 * @return
	 */
	List<Unit> getCompanyList(HashMap<String, String> param);


	/**
	 * 根据所属服务商id查询
	 * @param serverId 所属服务商Id
	 * @param status 	状态 正常-1，暂停-2
	 * @param deleted 	删除标识 删除为1，默认为0
	 * @return
	 */
	List<Unit> getUnitListByServerId(String serverId,String status, String deleted) throws Exception;

	/**
	  * 政府/服务商/企业详情
	 * @param id
	 * @return
	 */
	Unit getUnitById(String id);

	/**
	   * 政府/服务商/企业下拉框
	 * @param param
	 * @return
	 */
	List<Unit> selectUnitList(HashMap<String, String> param);


	List<Unit> getCommCompanyList(AuthenticatedUser user, Integer type);

	/**
	 * 获取所有企业
	 * @return
	 */
	List<Unit> getUnit();
	List<Unit> getUnit(Integer serverId);

	/**
	 * 根据Id获取政府/服务商/企业
	 * @return
	 */
	List<Unit> getUnitById(Integer id);

}
