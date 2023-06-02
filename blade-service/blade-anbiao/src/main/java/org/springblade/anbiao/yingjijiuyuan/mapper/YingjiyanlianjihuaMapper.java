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
package org.springblade.anbiao.yingjijiuyuan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.yingjijiuyuan.entity.Yingjiyanlianjihua;
import org.springblade.anbiao.yingjijiuyuan.page.YingjiyanlianjihuaPage;
import org.springblade.anbiao.yingjijiuyuan.vo.YingjiyanlianjihuaVO;

import java.util.List;

/**
 *  Mapper 接口
 * @author hyp
 * @since 2023-06-01
 */
public interface YingjiyanlianjihuaMapper extends BaseMapper<Yingjiyanlianjihua> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param yingjiyanlianjihua
	 * @return
	 */
	List<YingjiyanlianjihuaVO> selectYingjiyanlianjihuaPage(IPage page, YingjiyanlianjihuaVO yingjiyanlianjihua);

	/**
	 * 自定义分页
	 * @param
	 * @return
	 */
	List<YingjiyanlianjihuaVO> selectPageList(YingjiyanlianjihuaPage yingjiyanlianjihuaPage);
	/**
	 * 统计
	 * @param
	 * @return
	 */
	int selectTotal(YingjiyanlianjihuaPage yingjiyanlianjihuaPage);

	boolean insertYingJiYanLian(Yingjiyanlianjihua yingjiyanlianjihua);

	boolean updateDel(String id);

	YingjiyanlianjihuaVO selectByIds(String id);
}
