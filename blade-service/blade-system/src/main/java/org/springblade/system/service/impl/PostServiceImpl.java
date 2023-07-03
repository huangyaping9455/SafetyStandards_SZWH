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
package org.springblade.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.system.entity.Post;
import org.springblade.system.entity.PostMenu;
import org.springblade.system.mapper.PostMapper;
import org.springblade.system.service.IPostMenuService;
import org.springblade.system.service.IPostService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 服务实现类
 */
@Service
@AllArgsConstructor
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

	private PostMapper postMapper;

	IPostMenuService postMenuService;

	@Override
	public List<Post> selectByUserId(Integer userId) {
		return postMapper.selectByUserId(userId);
	}

	@Override
	public Post selectByUserIdAndIsdefault(Integer userId) {
		return postMapper.selectByUserIdAndIsdefault(userId);
	}

	@Override
	public boolean deleteByUserId(Integer userId) {
		return postMapper.deleteByUserId(userId);
	}

	@Override
	public boolean updateIsdefault(Post post) {
		return postMapper.updateIsdefault(post);
	}

	@Override
	public boolean insertPost(Post post) {
		return postMapper.insertPost(post);
	}

	@Override
	public boolean grant(@NotEmpty  List<String> postIds, @NotEmpty List<String> menuIds,String type) {
		// 删除岗位配置的菜单集合
		postMenuService.deleteByPostId(Integer.parseInt(postIds.get(0)),Integer.parseInt(type));
		// 组装配置
		List<PostMenu> postMenus = new ArrayList<>();
		postIds.forEach(postId -> menuIds.forEach(menuId -> {
			PostMenu postMenu = new PostMenu();
			postMenu.setPostId(Integer.parseInt(postId));
			postMenu.setMenuId(Integer.parseInt(menuId));
			postMenu.setType(Integer.parseInt(type));
			postMenus.add(postMenu);
		}));
		// 新增配置
		return postMenuService.saveBatch(postMenus);
	}
	@Override
	public boolean ABgrant(@NotEmpty  List<String> postIds, @NotEmpty List<String> menuIds,int type) {
		// 删除岗位配置的菜单集合
		postMenuService.deleteByPostId(Integer.parseInt(postIds.get(0)),type);
		// 组装配置
		List<PostMenu> postMenus = new ArrayList<>();
		postIds.forEach(postId -> menuIds.forEach(menuId -> {
			PostMenu postMenu = new PostMenu();
			postMenu.setPostId(Integer.parseInt(postId));
			postMenu.setMenuId(Integer.parseInt(menuId));
			postMenu.setType(type);
			postMenus.add(postMenu);
		}));
		// 新增配置
		return postMenuService.saveBatch(postMenus);
	}

	@Override
	public boolean Businessgrant(@NotEmpty List<Integer> postIds, @NotEmpty List<Integer> menuIds) {
		// 删除岗位配置的菜单集合
		postMenuService.deleteByPostId(postIds.get(0).intValue(),3);
		// 组装配置
		List<PostMenu> postMenus = new ArrayList<>();
		postIds.forEach(postId -> menuIds.forEach(menuId -> {
			PostMenu postMenu = new PostMenu();
			postMenu.setPostId(postId);
			postMenu.setMenuId(menuId);
			postMenu.setType(3);
			postMenus.add(postMenu);
		}));
		// 新增配置
		return postMenuService.saveBatch(postMenus);
	}

	@Override
	public boolean ABJurisdiction(@NotEmpty List<String> postIds, @NotEmpty List<String> menuIds) {
		// 删除岗位配置的菜单集合
		postMenuService.deleteByPostId(Integer.parseInt(postIds.get(0)),2);
		// 组装配置 安标16项
		List<PostMenu> postMenus = new ArrayList<>();
		postIds.forEach(postId -> menuIds.forEach(menuId -> {
			PostMenu postMenu = new PostMenu();
			postMenu.setPostId(Integer.parseInt(postId));
			postMenu.setMenuId(Integer.parseInt(menuId));
			postMenu.setType(2);
			postMenus.add(postMenu);
		}));
		// 新增配置
		return postMenuService.saveBatch(postMenus);
	}

	@Override
	public boolean Appgrant(@NotEmpty List<String> postIds, @NotEmpty List<String> menuIds) {
		// 删除岗位配置的菜单集合
		postMenuService.deleteByPostId(Integer.parseInt(postIds.get(0)),4);
		// 组装配置
		List<PostMenu> postMenus = new ArrayList<>();
		postIds.forEach(postId -> menuIds.forEach(menuId -> {
			PostMenu postMenu = new PostMenu();
			postMenu.setPostId(Integer.parseInt(postId));
			postMenu.setMenuId(Integer.parseInt(menuId));
			postMenu.setType(4);
			postMenus.add(postMenu);
		}));
		// 新增配置
		return postMenuService.saveBatch(postMenus);
	}

    @Override
    public boolean grantAdd(String deptId, List<String> menuIds,Integer type) {
		//获取机构下的所有岗位
		List<Integer> postIds = postMapper.getPostIdsByDeptId(deptId);
		List<PostMenu> oldPostMenus = postMenuService.getByPostIds(postIds,type);
		// 组装配置
		List<PostMenu> newPostMenus = new ArrayList<>();
		postIds.forEach(postId -> menuIds.forEach(menuId -> {
			PostMenu postMenu = new PostMenu();
			postMenu.setPostId(postId);
			postMenu.setMenuId(Integer.parseInt(menuId));
			postMenu.setType(type);
			newPostMenus.add(postMenu);
		}));

		//如果原postmenu包含追加menu，则移出
		Iterator<PostMenu> iterator = newPostMenus.iterator();
		while (iterator.hasNext()) {
			PostMenu p = iterator.next();
			oldPostMenus.forEach(old->{
				//已重写equals
				if(p.equals(old)){
					iterator.remove();
				}
			});
		}

         return postMenuService.saveBatch(newPostMenus);
    }

	@Override
	public boolean grantSyn(String deptId, List<String> menuIds, Integer type) {
		//获取机构下的所有岗位
		List<Integer> postIds = postMapper.getPostIdsByDeptId(deptId);
		// 删除岗位配置的菜单集合
		postMenuService.deleteByPostIds(postIds,type);
		// 组装配置
		List<PostMenu> postMenus = new ArrayList<>();
		postIds.forEach(postId -> menuIds.forEach(menuId -> {
			PostMenu postMenu = new PostMenu();
			postMenu.setPostId(postId);
			postMenu.setMenuId(Integer.parseInt(menuId));
			postMenu.setType(type);
			postMenus.add(postMenu);
		}));
		return postMenuService.saveBatch(postMenus);
	}
}
