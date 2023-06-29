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
package org.springblade.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.system.dto.MenuDTO;
import org.springblade.system.entity.Menu;
import org.springblade.system.vo.MenuVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author hyp
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param menu
	 * @return
	 */
	List<MenuVO> selectMenuPage(IPage page, MenuVO menu);

	List<Menu> selectAllList(Menu menu);

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> tree();

	/**
	 * 授权树形结构
	 *
	 * @return
	 */
	List<MenuVO> grantTree(String type);

	/**
	 * 授权树形结构
	 * @param roleId
	 * @return
	 */
	List<MenuVO> grantTreeByPost(@Param("type") String type,@Param("list") List<Integer> roleId);

	/**
	 * 所有菜单
	 *
	 * @return
	 */
	List<Menu> allMenu(String type);

	/**
	 * 权限配置菜单
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> roleMenu(@Param("type") String type,@Param("list") List<Integer> list);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> routes(List<Integer> roleId);

	/**
	 * 按钮树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> buttons(List<Integer> roleId);

	/**
	 * 获取配置的角色权限
	 * @param roleIds
	 * @return
	 */
	List<MenuDTO> authRoutes(List<Integer> roleIds,String type);


	//***************************安标*********************************//

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param menu
	 * @return
	 */
	List<MenuVO> selectABMenuPage(IPage page, MenuVO menu);

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> ABtree(int type);

	/**
	 * 授权树形结构
	 *
	 * @return
	 */
	List<MenuVO> ABgrantTree(int type);

	/**
	 * 授权树形结构
	 * @param type
	 * @param roleId
	 * @return
	 */
	List<MenuVO> ABgrantTreeByPost(@Param("type") int type,@Param("list") List<Integer> roleId);

	/**
	 * 所有菜单
	 *
	 * @return
	 */
	List<Menu> ABallMenu(int type);

	/**
	 * 权限配置菜单
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> ABroleMenu(@Param("type") int type,@Param("list") List<Integer> roleId);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> ABroutes(@Param("type") int type,@Param("list") List<Integer> roleId);

	/**
	 * 按钮树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> ABbuttons(List<Integer> roleId);

	/**
	 * 获取配置的角色权限
	 * @param roleIds
	 * @return
	 */
	List<MenuDTO> ABauthRoutes(List<Integer> roleIds);
	//*********************业务模块
	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> Businesstree();

	/**
	 * 授权树形结构
	 * @param postId
	 * @return
	 */
	List<MenuVO> BusinessgrantTreeByPost(List<Integer> postId);

	//*********************手机app
	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> Apptree();

	/**
	 * 授权树形结构
	 * @param postId
	 * @return
	 */
	List<MenuVO> AppgrantTreeByPost(List<Integer> postId);



}
