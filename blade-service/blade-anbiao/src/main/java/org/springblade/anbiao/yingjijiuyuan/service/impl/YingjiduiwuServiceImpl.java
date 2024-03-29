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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.yingjijiuyuan.entity.Yingjiduiwu;
import org.springblade.anbiao.yingjijiuyuan.mapper.YingjiduiwuMapper;
import org.springblade.anbiao.yingjijiuyuan.page.YingjiduiwuPage;
import org.springblade.anbiao.yingjijiuyuan.service.IYingjiduiwuService;
import org.springblade.anbiao.yingjijiuyuan.vo.YingjiduiwuVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 * @author hyp
 * @since 2023-06-01
 */
@Service
@AllArgsConstructor
public class YingjiduiwuServiceImpl extends ServiceImpl<YingjiduiwuMapper, Yingjiduiwu> implements IYingjiduiwuService {

    private YingjiduiwuMapper yingjiduiwuMapper;

    @Override
    public YingjiduiwuPage<YingjiduiwuVO> selectYingjiduiwuPage(YingjiduiwuPage yingjiduiwuPage) {
        Integer total = yingjiduiwuMapper.selectYingjiduiwuTotal(yingjiduiwuPage);
        Integer pagetotal = 0;
        if (total > 0) {
			if(total%yingjiduiwuPage.getSize()==0){
				pagetotal = total / yingjiduiwuPage.getSize();
			}else {
				pagetotal = total / yingjiduiwuPage.getSize() + 1;
			}
        }
        if (pagetotal >= yingjiduiwuPage.getCurrent() ) {
            yingjiduiwuPage.setPageTotal(pagetotal);
            Integer offsetNo = 0;
            if (yingjiduiwuPage.getCurrent() > 1) {
                offsetNo = yingjiduiwuPage.getSize() * (yingjiduiwuPage.getCurrent() - 1);
            }
            yingjiduiwuPage.setTotal(total);
            yingjiduiwuPage.setOffsetNo(offsetNo);
            List<YingjiduiwuVO> vehlist = yingjiduiwuMapper.selectYingjiduiwuPage(yingjiduiwuPage);
            yingjiduiwuPage.setRecords(vehlist);
        }
        return yingjiduiwuPage;
    }


    @Override
    public YingjiduiwuVO selectByKey(String id) {
        return yingjiduiwuMapper.selectByKey(id);
    }

    @Override
    public boolean deleleYingjiduiwu(String id) {
        return yingjiduiwuMapper.deleteYingjiduiwu(id);
    }

}
