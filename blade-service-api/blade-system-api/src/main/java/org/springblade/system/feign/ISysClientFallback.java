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
package org.springblade.system.feign;

import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Post;
import org.springblade.system.entity.Role;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

/**
 * Feign失败配置
 *
 * @author hyp
 */
@Component
public class ISysClientFallback implements ISysClient {
	@Override
	public String getDeptName(Integer id) {
		return null;
	}

	@Override
	public Dept getDept(Integer id) {
		return null;
	}

	@Override
	public String getRoleName(Integer id) {
		return null;
	}

	@Override
	public String getRoleAlias(Integer id) {
		return null;
	}

	@Override
	public Role getRole(Integer id) {
		return null;
	}

	@Override
	public Dept selectByJGBM(String type, String deptId) {
		return null;
	}

	@Override
	public Boolean grant(String postId,String menuIds) {
		return null;
	}

	@Override
	public Boolean ABgrant(String postId, String menuIds,int type) {
		return null;
	}

	@Override
	public Boolean ABJurisdiction(String postId, String menuIds) {
		return null;
	}

	@Override
	public Boolean insertPost(@Valid Post post) {
		return null;
	}

	@Override
	public Boolean updateIsdefault(@Valid Post post) {
		return null;
	}

	@Override
	public List<Post> selectByUserId(Integer userId) {
		return null;
	}

	@Override
	public Boolean insertDept(@Valid Dept dept) {
		return null;
	}

	@Override
	public Boolean updateDept(@Valid Dept dept) {
		return null;
	}

	@Override
	public Dept selectByTreeCode(String id) {
		return null;
	}

	@Override
	public Dept selectById(String id) {
		return null;
	}

	@Override
	public int selectMaxId() {
		return 0;
	}

	@Override
	public int selectCountByparentId(String id) {
		return 0;
	}

	@Override
	public Dept selectDeptById(Integer id) {
		return null;
	}

	@Override
	public int selectByName(String name) {
		return 0;
	}

	@Override
	public Boolean removeById(String id) {
		return null;
	}

    @Override
    public List<Integer> getDetpIds(Integer deptId) {
        return null;
    }
	@Override
	public Dept getDeptByName(String name){
		return null;
	}

	@Override
	public List<Dept> getByName(String deptname) {
		return null;
	}

}
