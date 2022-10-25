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
package org.springblade.anbiao.guanlijigouherenyuan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Departmentpost;
import org.springblade.anbiao.guanlijigouherenyuan.page.DepartmentpostPage;
import org.springblade.anbiao.guanlijigouherenyuan.vo.DepartmentpostVO;

import java.util.List;

/**
 *  Mapper 接口
 */
public interface DepartmentpostMapper extends BaseMapper<Departmentpost> {

	/**
	 * 自定义分页
	 * @param departmentpostPage
	 * @return
	 */
	List<DepartmentpostVO> selectPageQuery(DepartmentpostPage departmentpostPage);
	/**
	 * 统计
	 * @param departmentpostPage
	 * @return
	 */
	int selectTotal(DepartmentpostPage  departmentpostPage);
	/**
	* @Description: 逻辑删除
	* @Param: [id]
	*/
	boolean deletepost(String id);

	/**
	 * 根据岗位id获取岗位信息
	 * @param deptId
	 * @return
	 */
	Departmentpost selectByPostId(String deptId);

	/**
	 * 根据岗位id修改信息
	 * @param Departmentpost
	 * @return
	 */
	boolean updateByPostId(Departmentpost Departmentpost);

	/**
	 * 新增岗位信息
	 * @param departmentpost
	 * @return
	 */
	boolean insertSelective(Departmentpost departmentpost);

}
