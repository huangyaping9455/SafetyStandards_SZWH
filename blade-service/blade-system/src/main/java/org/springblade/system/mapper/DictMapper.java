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
package org.springblade.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.system.entity.Dict;
import org.springblade.system.page.DictPage;
import org.springblade.system.vo.DictVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author hyp
 */
public interface DictMapper extends BaseMapper<Dict> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dict
	 * @return
	 */
	List<DictVO> selectDictPage(IPage page, DictVO dict);

	/**
	 * 获取字典表对应中文
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return
	 */
	String getValue(String code, Integer dictKey);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return
	 */
	List<Dict> getList(String code);

	/**
	 * 获取字典表(分页)
	 * @param dictPage
	 * @return
	 */
	List<Dict> selectALLPage(DictPage dictPage);
	int selectAllTotal(DictPage dictPage);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return
	 */
	List<Dict> selectByCode(String code);


	/**
	 * 获取树形节点
	 *
	 * @return
	 */
	List<DictVO> tree(DictPage dictPage);

	/**
	 * 其他字典
	 * @return
	 */
	List<DictVO> OtherTree(DictPage dictPage);

	/**
	 * 根据上级ID获取下级字典
	 * @return
	 */
	List<DictVO> RegionalismTree(Integer id);

	/**
	 * 根据code查询相关数据
	 * @param code
	 * @return
	 */
	List<Dict> getDictByCode(@Param("code") String code,@Param("dictvalue") String dictvalue);


}
