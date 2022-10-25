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
package org.springblade.anbiao.jiashiyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.Zhengjianshenyan;
import org.springblade.anbiao.jiashiyuan.mapper.ZhengjianshenyanMapper;
import org.springblade.anbiao.jiashiyuan.page.ZhengjianshenyanPage;
import org.springblade.anbiao.jiashiyuan.service.IZhengjianshenyanService;
import org.springblade.anbiao.jiashiyuan.vo.ZhengjianshenyanVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  服务实现类
 * @author 呵呵哒
 */
@Service
@AllArgsConstructor
public class ZhengjianshenyanServiceImpl extends ServiceImpl<ZhengjianshenyanMapper, Zhengjianshenyan> implements IZhengjianshenyanService {

	private ZhengjianshenyanMapper mapper;

	@Override
	public IPage<ZhengjianshenyanVO> selectZhengjianshenyanPage(IPage<ZhengjianshenyanVO> page, ZhengjianshenyanVO zhengjianshenyan) {
		return page.setRecords(baseMapper.selectZhengjianshenyanPage(page, zhengjianshenyan));
	}

	@Override
	public boolean updateDel(String id) {
		return mapper.updateDel(id);
	}

	@Override
	public ZhengjianshenyanPage<ZhengjianshenyanVO> selectPageList(ZhengjianshenyanPage zhengjianshenyanPage) {
		Integer total = mapper.selectTotal(zhengjianshenyanPage);
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%zhengjianshenyanPage.getSize()==0){
				pagetotal = total / zhengjianshenyanPage.getSize();
			}else {
				pagetotal = total / zhengjianshenyanPage.getSize() + 1;
			}
		}
		if (pagetotal < zhengjianshenyanPage.getCurrent()) {
			return zhengjianshenyanPage;
		} else {
			zhengjianshenyanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (zhengjianshenyanPage.getCurrent() > 1) {
				offsetNo = zhengjianshenyanPage.getSize() * (zhengjianshenyanPage.getCurrent() - 1);
			}
			zhengjianshenyanPage.setTotal(total);
			zhengjianshenyanPage.setOffsetNo(offsetNo);
			List<ZhengjianshenyanVO> vehlist = mapper.selectPageList(zhengjianshenyanPage);
			return (ZhengjianshenyanPage<ZhengjianshenyanVO>) zhengjianshenyanPage.setRecords(vehlist);
		}
	}

	@Override
	public ZhengjianshenyanVO selectByIds(String id) {
		return mapper.selectByIds(id);
	}

}
