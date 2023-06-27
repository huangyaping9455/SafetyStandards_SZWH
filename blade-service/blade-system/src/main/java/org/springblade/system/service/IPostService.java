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

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.system.entity.Post;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 服务类
 *
 * @hyp
 */
public interface IPostService extends IService<Post> {

	/**
	 *根据人员id获取岗位
	 * @param userId
	 * @return
	 */
	List<Post> selectByUserId(Integer userId);
	/**
	 * 根据人员id获取默认岗位信息
	 * @param userId
	 * @return
	 */
	Post selectByUserIdAndIsdefault(Integer userId);

	/**
	 * 根据人员id删除岗位
	 * @param userId
	 * @return
	 */
	boolean deleteByUserId(Integer userId);

	/**
	 * @Description: 根据用户id与岗位id设置默认岗位
	 * @Param: [post]
	 * @return: boolean
	 * @Author: hyp
	 * @Date2021-07-25
	 */
	boolean  updateIsdefault(Post post);

	/**
	 * 新增
	 * @param post
	 * @return
	 */
	boolean insertPost(Post post);

	/**
	 * 权限配置(运维)
	 *
	 * @param postId 岗位id
	 * @param menuIds 菜单id集合
	 * @return 是否成功
	 */
	boolean grant(@NotEmpty List<String> postId, @NotEmpty List<String> menuIds,String type);

	/**
	 * 权限配置(安标)
	 *
	 * @param postId 岗位id
	 * @param menuIds 菜单id集合
	 * @return 是否成功
	 */
	boolean ABgrant(@NotEmpty List<String> postId, @NotEmpty List<String> menuIds,int type);

	/**
	 * 权限配置(业务)
	 *
	 * @param postId 岗位id
	 * @param menuIds 菜单id集合
	 * @return 是否成功
	 */
	boolean Businessgrant(@NotEmpty List<Integer> postId, @NotEmpty List<Integer> menuIds);

	/**
	 * 权限配置(安标16项)
	 *
	 * @param postId 岗位id
	 * @param menuIds 菜单id集合
	 * @return 是否成功
	 */
	boolean ABJurisdiction(@NotEmpty List<String> postId, @NotEmpty List<String> menuIds);
	/**
	 * 权限配置(app)
	 *
	 * @param postId 岗位id
	 * @param menuIds 菜单id集合
	 * @return 是否成功
	 */
	boolean Appgrant(@NotEmpty List<String> postId, @NotEmpty List<String> menuIds);

	/**
	 *根据单位id追加子级所有岗位权限
	 * @author: hyp
	 * @date: 2019/10/24 15:08
	 * @param deptId
	 * @param split
	 * @return: boolean
	 */
	boolean grantAdd(String deptId, List<String> split,Integer type);

	/**
	 *根据单位id同步子级所有权限
	 * @author: hyp
	 * @date: 2019/10/24 16:17
	 * @param deptId
	 * @param split
	 * @param type
	 * @return: boolean
	 */
	boolean grantSyn(String deptId, List<String> split, Integer type);
}
