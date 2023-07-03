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
package org.springblade.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.support.Kv;
import org.springblade.system.entity.Menu;
import org.springblade.system.vo.MenuVO;

import java.util.List;

/**
 * 服务类
 *
 * @author hyp
 */
public interface IMenuService extends IService<Menu> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param menu
	 * @return
	 */
	IPage<MenuVO> selectMenuPage(IPage<MenuVO> page, MenuVO menu);

	/**
	 * 根据
	 * @param menu
	 * @return
	 */
	List<Menu> selectAllList(Menu menu);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> routes(String roleId,String type);

	/**
	 * 按钮树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> buttons(String roleId);

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> tree(String type);

	/**
	 * 授权树形结构
	 *
	 * @param user
	 * @return
	 */
	List<MenuVO> grantTree(BladeUser user,String type);

	/**
	 * 默认选中节点
	 *
	 * @param postId
	 * @return
	 */
	List<String> postTreeKeys(String postId,String type);
	/**
	 * 获取配置的角色权限
	 *
	 * @param user
	 * @return
	 */
	List<Kv> authRoutes(BladeUser user,String type);



	//**********************安标*******************************
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param menu
	 * @return
	 */
	IPage<MenuVO> selectABMenuPage(IPage<MenuVO> page, MenuVO menu);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> ABroutes(int type,String roleId);

	/**
	 * 按钮树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> ABbuttons(String roleId);

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> ABtree(int type);

	/**
	 * 授权树形结构
	 *
	 * @param user
	 * @return
	 */
	List<MenuVO> ABgrantTree(BladeUser user,int type);

	/**
	 * 默认选中节点
	 *
	 * @param postId
	 * @return
	 */
	List<String> ABpostTreeKeys(String postId,int type);
	/**
	 * 获取配置的角色权限
	 *
	 * @param user
	 * @return
	 */
	List<Kv> ABauthRoutes(BladeUser user);

	//*********************业务模块
	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> Businesstree(BladeUser user);

	/**
	 * 授权树形结构
	 * @param postId
	 * @return
	 */
	List<String> BusinessgrantTreeByPost(String postId);

	//*********************业务模块
	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> Apptree(BladeUser user);

	/**
	 * 授权树形结构
	 * @param postId
	 * @return
	 */
	List<String> AppgrantTreeByPost(String postId);

}
