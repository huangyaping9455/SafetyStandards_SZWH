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
package org.springblade.system.user.feign;


import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.page.UserPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;

/**
 * User Feign接口类
 *
 * @author hyp
 */
@FeignClient(
	value = AppConstant.APPLICATION_USER_NAME,
	fallback = IUserClientBack.class
)
public interface IUserClient {

	String API_PREFIX = "/user";

	/**
	 * 获取用户信息
	 *
	 * @param account  账号
	 * @param password 密码
	 * @return
	 */
	@GetMapping(API_PREFIX + "/user-info")
	R<UserInfo> userInfo(@RequestParam("account") String account, @RequestParam("password") String password);

	@GetMapping(API_PREFIX + "/login")
	User login(@RequestParam("account") String account, @RequestParam("password") String password);

	/**
	 * 查询openid是否存在
	 * @param openid
	 * @return
	 */
	@GetMapping(API_PREFIX+"/getOpenId")
	User getopenid(@RequestParam("openid") String openid);

	/**
	 * 用户绑定openid
	 * @param account
	 * @param openid
	 */
	@GetMapping(API_PREFIX+"/bindDriverOpenId")
	void bindDriverOpenId(@RequestParam("account") String account,@RequestParam("openid") String openid);

	/**
	 * 通过openid获取用户信息
	 * @param openid
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getWeixinUser")
	R<UserInfo> getWeixinUser(@RequestParam("openid") String openid);

	@GetMapping(API_PREFIX + "/ZFUser")
	User ZFUser(@RequestParam("type") String type,@RequestParam("userId") String userId);

	@GetMapping(API_PREFIX + "/ZFlogin")
	User ZFlogin(@RequestParam("account") String account, @RequestParam("password") String password);

	@GetMapping(API_PREFIX + "/updateLocked")
	boolean updateLocked(@RequestParam("isLocked") Integer isLocked, @RequestParam("loginErrorcount") Integer loginErrorcount, @RequestParam("lastLoginErrorTime") String lastLoginErrorTime, @RequestParam("id") String id);

	@GetMapping(API_PREFIX + "/selectByName")
	User selectByName(@RequestParam("account") String account);

	/**
	 * 根据postId查询岗位下是否存在人员
	 * @param postId
	 * @return
	 */
	@GetMapping(API_PREFIX + "/selectPostIdByUser")
	User selectPostIdByUser(@RequestParam("postId") String postId);

	@GetMapping(API_PREFIX + "/selectUserByDeptPage")
	UserPage<User> selectUserByDeptPage(@RequestParam("deptId") Integer deptId,@RequestParam("size") int size);
}
