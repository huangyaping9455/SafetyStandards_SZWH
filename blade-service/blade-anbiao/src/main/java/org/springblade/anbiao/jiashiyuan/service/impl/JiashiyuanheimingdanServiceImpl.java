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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.Jiashiyuanheimingdan;
import org.springblade.anbiao.jiashiyuan.mapper.JiashiyuanheimingdanMapper;
import org.springblade.anbiao.jiashiyuan.page.JiashiyuanheimingdanPage;
import org.springblade.anbiao.jiashiyuan.service.IJiashiyuanheimingdanService;
import org.springblade.anbiao.jiashiyuan.vo.JiashiyuanheimingdanVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 * @author 呵呵哒
 */
@Service
@AllArgsConstructor
public class JiashiyuanheimingdanServiceImpl extends ServiceImpl<JiashiyuanheimingdanMapper, Jiashiyuanheimingdan> implements IJiashiyuanheimingdanService {

	private JiashiyuanheimingdanMapper mapper;

	@Override
	public IPage<JiashiyuanheimingdanVO> selectJiashiyuanheimingdanPage(IPage<JiashiyuanheimingdanVO> page, JiashiyuanheimingdanVO jiashiyuanheimingdan) {
		return page.setRecords(baseMapper.selectJiashiyuanheimingdanPage(page, jiashiyuanheimingdan));
	}

	@Override
	public boolean updateDel(String id) {
		return mapper.updateDel(id);
	}

	@Override
	public JiashiyuanheimingdanPage<JiashiyuanheimingdanVO> selectPageList(JiashiyuanheimingdanPage jiashiyuanheimingdanPage) {
		Integer total = mapper.selectTotal(jiashiyuanheimingdanPage);
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%jiashiyuanheimingdanPage.getSize()==0){
				pagetotal = total / jiashiyuanheimingdanPage.getSize();
			}else {
				pagetotal = total / jiashiyuanheimingdanPage.getSize() + 1;
			}
		}
		if (pagetotal < jiashiyuanheimingdanPage.getCurrent()) {
			return jiashiyuanheimingdanPage;
		} else {
			jiashiyuanheimingdanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (jiashiyuanheimingdanPage.getCurrent() > 1) {
				offsetNo = jiashiyuanheimingdanPage.getSize() * (jiashiyuanheimingdanPage.getCurrent() - 1);
			}
			jiashiyuanheimingdanPage.setTotal(total);
			jiashiyuanheimingdanPage.setOffsetNo(offsetNo);
			List<JiashiyuanheimingdanVO> vehlist = mapper.selectPageList(jiashiyuanheimingdanPage);
			return (JiashiyuanheimingdanPage<JiashiyuanheimingdanVO>) jiashiyuanheimingdanPage.setRecords(vehlist);
		}
	}

	@Override
	public JiashiyuanheimingdanVO selectByIds(String id) {
		return mapper.selectByIds(id);
	}

}
