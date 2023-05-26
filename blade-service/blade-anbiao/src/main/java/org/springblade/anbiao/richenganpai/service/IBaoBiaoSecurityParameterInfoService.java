/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.anbiao.richenganpai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.richenganpai.entity.BaoBiaoSecurityParameterInfo;
import org.springblade.anbiao.richenganpai.entity.BaoBiaoSecurityParameterInfoRemark;
import org.springblade.anbiao.richenganpai.entity.BaoBiaoSecurityParameterInfoTime;
import org.springblade.anbiao.richenganpai.page.RiChengAnPaiPage;
import org.springblade.doc.safetyproductionfile.vo.SafetyProductionFileVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *  服务类
 *
 * @author Blade
 * @since 2019-06-06
 */
public interface IBaoBiaoSecurityParameterInfoService extends IService<BaoBiaoSecurityParameterInfo> {

	/**
	 * 新增安全台账配置
	 * @param baoBiaoSecurityParameterInfo
	 * @return
	 */
	boolean insertSelective(BaoBiaoSecurityParameterInfo baoBiaoSecurityParameterInfo);

	/**
	 * 根据ID修改安全台账配置相应信息
	 * @param baoBiaoSecurityParameterInfo
	 * @return
	 */
	boolean updateSelective(BaoBiaoSecurityParameterInfo baoBiaoSecurityParameterInfo);

	/**
	 * 根据ID查询安全台账配置详情
	 * @param Id
	 * @return
	 */
	List<BaoBiaoSecurityParameterInfo> selectByIds(Integer Id,String name);

	/**
	 * 根据ID删除安全台账配置
	 * @param Id
	 * @return
	 */
	boolean deleteBind(@RequestParam("updateTime") String updateTime,
					   @RequestParam("updateUserId") Integer updateUserId,
					   @RequestParam("Id") Integer Id);

	/**
	 * 获取列表
	 * @param riChengAnPaiPage
	 * @return
	 */
	RiChengAnPaiPage<BaoBiaoSecurityParameterInfo> getAll(RiChengAnPaiPage riChengAnPaiPage);

	/**
	 * 新增安全台账配置
	 * @param baoBiaoSecurityParameterInfoRemark
	 * @return
	 */
	boolean insertRemarkSelective(BaoBiaoSecurityParameterInfoRemark baoBiaoSecurityParameterInfoRemark);

	/***
	 * 根据ID编辑企业安全台账配置
	 * @param baoBiaoSecurityParameterInfoRemark
	 * @return
	 */
	boolean updateRemarkBind(BaoBiaoSecurityParameterInfoRemark baoBiaoSecurityParameterInfoRemark);

	/**
	 * 根据ID删除企业安全台账配置
	 * @param Id
	 * @return
	 */
	boolean deleteRemarkBind(@RequestParam("Id") Integer Id);

	/**
	 * 根据企业ID或者台账配置项ID获取企业安全台账相关详情
	 * @param deptId
	 * @param securityId
	 * @return
	 */
	List<BaoBiaoSecurityParameterInfo> selectBaoBiaoSecurityParameterInfoRemarkByIds(Integer deptId,Integer securityId);

	/**
	 * 根据企业ID或者台账名称获取安全台账相关信息
	 * @param deptId
	 * @param name
	 * @return
	 */
	List<SafetyProductionFileVO> selectSafetyProductionFileByIds(Integer deptId, String name);

	/**
	 * 获取企业安全台账配置列表
	 * @param riChengAnPaiPage
	 * @return
	 */
	RiChengAnPaiPage<BaoBiaoSecurityParameterInfo> getAllByDept(RiChengAnPaiPage riChengAnPaiPage);

	/**
	 * 添加安全台账时间周期记录表
	 * @return
	 */
	boolean insertBaoBiaoSecurityParameterInfoTimeSelective(BaoBiaoSecurityParameterInfoTime baoBiaoSecurityParameterInfoTime);

	/**
	 * 根据企业ID、安全台账ID获取安全台账时间周期记录表
	 * @param deptId
	 * @param securityId
	 * @return
	 */
	List<BaoBiaoSecurityParameterInfoTime> selectBaoBiaoSecurityParameterInfoTime(Integer deptId,Integer securityId);

}
