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
package org.springblade.anbiao.jinritixing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.jinritixing.entity.Yujingquanxian;
import org.springblade.anbiao.jinritixing.page.YujingquanxianPage;
import org.springblade.anbiao.jinritixing.vo.YujingquanxianVO;

import java.util.List;

/**
 *  Mapper 接口
 */
public interface YujingquanxianMapper extends BaseMapper<Yujingquanxian> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param yujingquanxian
	 * @return
	 */
	List<YujingquanxianVO> selectYujingquanxianPage(IPage page, YujingquanxianVO yujingquanxian);

	/**
	 * 预警项分页
	 * @param yujingquanxianPage
	 * @return
	 */
	List<YujingquanxianVO> selectAllYuJing(YujingquanxianPage yujingquanxianPage);
	int selectAllYuJingTotal(YujingquanxianPage yujingquanxianPage);

	List<YujingquanxianVO> selectYuJingList(YujingquanxianPage page);

	boolean delYuJing(YujingquanxianPage page);

	boolean delYuJingByXiangId(YujingquanxianPage page);

	void yujingjiesuan(YujingquanxianPage page);

	/**
	 * 查询已创建预警项绑定的企业及相关预警项
	 * @param page
	 * @return
	 */
	List<YujingquanxianVO> selectYJDept(YujingquanxianPage page);
	int selectYJDeptTotal(YujingquanxianPage page);


}
