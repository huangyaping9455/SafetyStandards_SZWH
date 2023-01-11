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
package org.springblade.anbiao.cheliangguanli.mapper;

import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxian;
import org.springblade.anbiao.cheliangguanli.vo.JiashiyuanBaoxianVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.system.entity.Dept;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.page.UserPage;

import java.util.List;

/**
 * 驾驶员保险信息主表 Mapper 接口
 *
 * @author Blade
 * @since 2022-10-31
 */
public interface JiashiyuanBaoxianMapper extends BaseMapper<JiashiyuanBaoxian> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param jiashiyuanBaoxian
	 * @return
	 */
	List<JiashiyuanBaoxianVO> selectJiashiyuanBaoxianPage(IPage page, JiashiyuanBaoxianVO jiashiyuanBaoxian,String ajbInsuredIds);

	/**
	 * 自定义根据ID查询明细
	 * @param ajbIds
	 * @return
	 */
	JiashiyuanBaoxian selectById(String ajbIds);

	JiashiyuanBaoxian queryByMax(String driverId);

	List<User> getDeptUser(String deptId);

	/**
	 * 根据企业ID获取下级所有企业（企业、个体）
	 * @param deptId
	 * @return
	 */
	List<Dept> QiYeList(@Param("deptId") Integer deptId);

}
