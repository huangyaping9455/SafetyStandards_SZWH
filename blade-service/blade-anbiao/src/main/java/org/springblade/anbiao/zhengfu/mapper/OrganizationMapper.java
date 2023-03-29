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
package org.springblade.anbiao.zhengfu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.zhengfu.entity.Organization;
import org.springblade.anbiao.zhengfu.entity.XinXiJiaoHuZhuTiVo;
import org.springblade.anbiao.zhengfu.entity.ZhengFuOrganization;
import org.springblade.anbiao.zhengfu.page.OrganizationPage;

import java.util.List;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/6/18
 * @描述
 */
public interface OrganizationMapper extends BaseMapper<Organization> {

	/**
	 * 获取省运管局
	 * @return
	 */
	Organization selectGetSheng(String deptId);

	/**
	 * 获取市、县运管局
	 * @param Id
	 * @return
	 */
	List<Organization> selectGetShi(String Id);

	List<Organization> selectGetXian(String Id);

	/**
	 * 根据人员ID判读是否是政府账号
	 * @param Id
	 * @return
	 */
	Organization selectGetRenyuan(String Id);

	/**
	 * 根据人员岗位上级ID获取运管信息
	 * @param Id
	 * @return
	 */
	Organization selectGetGangWei(String Id);

	/**
	 * 根据账号政府id获取下级企业
	 * province city country
	 * @return
	 */
	List<ZhengFuOrganization> selectGetZF(@Param("province") String province,@Param("city") String city,@Param("country") String country,@Param("deptId") String deptId);

	/**
	 * 查询政府ID
	 * @param deptId
	 * @return
	 */
	Organization selectGetZFJB(@Param("deptId") String deptId);

	/**
	 * 查询政府企业明细
	 * @param organizationPage
	 * @return
	 */
	List<Organization> selectALLPage(OrganizationPage organizationPage);
	int selectAllTotal(OrganizationPage organizationPage);

	/**
	 *获取运管下的企业
	 * @param deptId
	 * @return
	 */
	List<XinXiJiaoHuZhuTiVo> selectGetZFQIYE(@Param("deptId") String deptId);


}
