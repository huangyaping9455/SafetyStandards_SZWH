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
package org.springblade.anbiao.baobiaowenjian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.baobiaowenjian.entity.BaobiaoMulu;
import org.springblade.anbiao.baobiaowenjian.page.BaobiaoWenjianPage;
import org.springblade.anbiao.baobiaowenjian.vo.BaobiaoMuluVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author hyp
 * @since 2019-05-16
 */
public interface BaobiaoMuluMapper extends BaseMapper<BaobiaoMulu> {

	/**
	 * 自定义分页
	 *
	 * @param baobiaoWenjianPage
	 * @return
	 */
	List<BaobiaoMuluVO> selectBaogaoPage(BaobiaoWenjianPage baobiaoWenjianPage);

	/**
	 * 统计
	 *
	 * @param baobiaoWenjianPage
	 * @return
	 */
	int selectBaogaoTotal(BaobiaoWenjianPage baobiaoWenjianPage);


}
