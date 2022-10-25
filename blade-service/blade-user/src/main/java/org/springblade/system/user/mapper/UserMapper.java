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
package org.springblade.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.page.UserPage;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author hyp
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @return
	 */
	List<User> selectUserPage(IPage page, User user);

	/**
	 * 自定义分页根据机构
	 *
	 * @param userPage
	 * @return
	 */
	List<User> selectUserByPage(UserPage userPage);

	/**
	 * 统计
	 *
	 * @param userPage
	 * @return
	 */
	int selectUsersTotal(UserPage userPage);
	/**
	 * 获取用户
	 *
	 * @param account
	 * @param password
	 * @return
	 */
	User getUser( String account, String password);

	/**
	 * 根据用户id获取数据
	 * @param id
	 * @return
	 */
	User getUserById(Integer id);

	/**
	 * 获取角色名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleName(String[] ids);

	/**
	 * 获取角色别名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleAlias(String[] ids);

	/**
	 * 获取部门名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getDeptName(String[] ids);

	/**
	 * 获取当前最大的id
	 * @return
	 */
	int selectMaxId();

	int selectByLoginName(String account);

	/**
	 * 自定义新增
	 * @param user
	 * @return
	 */
	boolean insertPer(User user);

	/**
	 * 根据岗位id获取岗位下级人员信息
	 * @param postId
	 * @return
	 */
	List<User> selectByPostId(String postId);
	/**
	 * 根据单位id获取下级人员信息
	 * @param deptId
	 * @return
	 */
	List<User> selectByDeptId(String deptId);

	/**
	 * 自定义编辑
	 * @param user
	 * @return
	 */
	boolean updatePer(User user);

	/**
	 * 自定义删除
	 * @param id
	 * @return
	 */
	boolean deletePer(String id);

	/**
	 * 查询openid是否存在
	 * @param openid
	 * @return
	 */
	User selectByopenId(String openid);

	/**
	 *绑定openid
	 * @param account
	 * @param openid
	 */
	void bindDriverOpenId(String account, String openid);

	/**
	 * 通过微信openid获取用户信息
	 * @param openid
	 * @return
	 */
	User getWeixinUser(String openid);

	/**
	 * 获取政府用户
	 * @param account
	 * @param password
	 * @return
	 */
	User getZFUser(String account, String password);

	/**
	 * 根据用户id获取用户所属岗位、机构信息
	 * @param userId
	 * @return
	 */
	User getZFUserInfo(String type,String userId);

	/**
	 * 自定义锁定
	 * @return
	 */
	boolean updateLocked(@RequestParam("isLocked") Integer isLocked, @RequestParam("loginErrorCount") Integer loginErrorCount
		, @RequestParam("lastLoginErrorTime") String lastLoginErrorTime, @RequestParam("id") String id);

	/**
	 * 根据登录名称查询用户
	 * @param account
	 * @return
	 */
	User selectByName(String account);

	/**
	 * 禁用、启用、删除用户账号信息
	 * @param updateUser
	 * @param updateTime
	 * @param isDeleted
	 * @param idss
	 * @return
	 */
	boolean updateUserStatus(@RequestParam("updateUser") Integer updateUser,
							 @RequestParam("updateTime") String updateTime,
							 @RequestParam("isDeleted") Integer isDeleted,
							 @RequestParam("idss") String[] idss);

	/**
	 * 根据企业ID查询所属下级所有的账号
	 * @param deptId
	 * @return
	 */
	List<User> selectNameById(String deptId);

	/**
	 * 根据postId查询岗位下是否存在人员
 	 * @param postId
	 * @return
	 */
	User selectPostIdByUser(String postId);




}
