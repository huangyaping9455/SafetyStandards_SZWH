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
package org.springblade.anbiao.yingjijiuyuan.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.yingjijiuyuan.entity.Yingjiyanlianjihua;
import org.springblade.anbiao.yingjijiuyuan.mapper.YingjiyanlianjihuaMapper;
import org.springblade.anbiao.yingjijiuyuan.page.YingjiyanlianjihuaPage;
import org.springblade.anbiao.yingjijiuyuan.service.IYingjiyanlianjihuaService;
import org.springblade.anbiao.yingjijiuyuan.vo.YingjiyanlianjihuaVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 * @author hyp
 * @since 2023-06-01
 */
@Service
@AllArgsConstructor
public class YingjiyanlianjihuaServiceImpl extends ServiceImpl<YingjiyanlianjihuaMapper, Yingjiyanlianjihua> implements IYingjiyanlianjihuaService {

	private YingjiyanlianjihuaMapper yingjiyanlianjihuaMapper;

	@Override
	public IPage<YingjiyanlianjihuaVO> selectYingjiyanlianjihuaPage(IPage<YingjiyanlianjihuaVO> page, YingjiyanlianjihuaVO yingjiyanlianjihua) {
		return page.setRecords(baseMapper.selectYingjiyanlianjihuaPage(page, yingjiyanlianjihua));
	}

	@Override
	public YingjiyanlianjihuaPage<YingjiyanlianjihuaVO> selectPageList(YingjiyanlianjihuaPage yingjiyanlianjihuaPage) {
		Integer total = yingjiyanlianjihuaMapper.selectTotal(yingjiyanlianjihuaPage);
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%yingjiyanlianjihuaPage.getSize()==0){
				pagetotal = total / yingjiyanlianjihuaPage.getSize();
			}else {
				pagetotal = total / yingjiyanlianjihuaPage.getSize() + 1;
			}
		}
		if (pagetotal < yingjiyanlianjihuaPage.getCurrent()) {
			return yingjiyanlianjihuaPage;
		} else {
			yingjiyanlianjihuaPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (yingjiyanlianjihuaPage.getCurrent() > 1) {
				offsetNo = yingjiyanlianjihuaPage.getSize() * (yingjiyanlianjihuaPage.getCurrent() - 1);
			}
			yingjiyanlianjihuaPage.setTotal(total);
			yingjiyanlianjihuaPage.setOffsetNo(offsetNo);
			List<YingjiyanlianjihuaVO> vehlist = yingjiyanlianjihuaMapper.selectPageList(yingjiyanlianjihuaPage);
			return (YingjiyanlianjihuaPage<YingjiyanlianjihuaVO>) yingjiyanlianjihuaPage.setRecords(vehlist);
		}
	}

	@Override
	public boolean insertYingJiYanLian(Yingjiyanlianjihua yingjiyanlianjihua) {
		return yingjiyanlianjihuaMapper.insertYingJiYanLian(yingjiyanlianjihua);
	}

	@Override
	public boolean updateDel(String id) {
		return yingjiyanlianjihuaMapper.updateDel(id);
	}

	@Override
	public YingjiyanlianjihuaVO selectByIds(String id) {
		return yingjiyanlianjihuaMapper.selectByIds(id);
	}

}
