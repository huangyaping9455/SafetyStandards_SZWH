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

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.cheliangguanli.entity.Cheliangbaoxian;
import org.springblade.anbiao.cheliangguanli.page.CheliangbaoxianPage;
import org.springblade.anbiao.cheliangguanli.vo.CheliangbaoxianVO;

import java.util.List;

/**
 *  Mapper 接口
 * @author 呵呵哒
 */
public interface CheliangbaoxianMapper extends BaseMapper<Cheliangbaoxian> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cheliangbaoxian
	 * @return
	 */
	List<CheliangbaoxianVO> selectCheliangbaoxianPage(IPage page, CheliangbaoxianVO cheliangbaoxian);

	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	boolean updateDel(String id);

	/**
	 * 更新数据状态
	 * @param id
	 * @return
	 */
	boolean updateStatus(String id);

	/**
	 * 自定义分页
	 * @param
	 * @return
	 */
	List<CheliangbaoxianVO> selectPageList(CheliangbaoxianPage Page);
	/**
	 * 统计
	 * @param
	 * @return
	 */
	int selectTotal(CheliangbaoxianPage Page);

	/**
	 * 根据保险ID、投保类型、车辆ID获取保险信息
	 * @param id
	 * @param toubaoleixing
	 * @return
	 */
	CheliangbaoxianVO selectByIds(String id,String toubaoleixing,String cheliangid);

	/**
	 * 新增保险信息
	 * @param cheliangbaoxian
	 * @return
	 */
	boolean insertSelective(Cheliangbaoxian cheliangbaoxian);




}
