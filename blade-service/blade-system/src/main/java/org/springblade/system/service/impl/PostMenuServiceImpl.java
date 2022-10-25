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
import org.springblade.system.entity.PostMenu;
import org.springblade.system.mapper.PostMenuMapper;
import org.springblade.system.service.IPostMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author hyp
 */
@Service
@AllArgsConstructor
public class PostMenuServiceImpl extends ServiceImpl<PostMenuMapper, PostMenu> implements IPostMenuService {

	PostMenuMapper postMenuMapper;

	@Override
	public boolean deleteByPostId(Integer postId,Integer type) {
		return postMenuMapper.deleteByPostId(postId,type);
	}

	@Override
	public List<PostMenu> selectListByPostId(Integer postId, Integer type) {
		return postMenuMapper.selectListByPostId(postId,type);
	}

	@Override
	public List<PostMenu> selectListByPostIdBusiness(Integer postId, Integer type) {
		return postMenuMapper.selectListByPostIdBusiness(postId,type);
	}

	@Override
	public List<PostMenu> selectListByPostIdApp(Integer postId, Integer type) {
		return postMenuMapper.selectListByPostIdApp(postId,type);
	}

    @Override
    public void deleteByPostIds(List<Integer> postIds, int type) {
		postMenuMapper.deleteByPostIds(postIds,type);
    }

	@Override
	public List<PostMenu> getByPostIds(List<Integer> postIds, Integer type) {
		return postMenuMapper.getByPostIds(postIds,type);
	}
}


