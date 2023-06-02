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
import org.springblade.anbiao.yingjijiuyuan.entity.YingjiduiwuChengyuan;
import org.springblade.anbiao.yingjijiuyuan.mapper.YingjiduiwuChengyuanMapper;
import org.springblade.anbiao.yingjijiuyuan.service.IYingjiduiwuChengyuanService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author hyp
 * @since 2023-06-01
 */
@Service
@AllArgsConstructor
public class YingjiduiwuChengyuanServiceImpl extends ServiceImpl<YingjiduiwuChengyuanMapper, YingjiduiwuChengyuan> implements IYingjiduiwuChengyuanService {

    private YingjiduiwuChengyuanMapper yingjiduiwuChengyuanMapper;

    @Override
    public List<YingjiduiwuChengyuan> selectByDuiwuId(String id) {
        return yingjiduiwuChengyuanMapper.selectByDuiwuId(id);
    }

    @Override
    public boolean deleleChengyuan(String id) {
        return yingjiduiwuChengyuanMapper.deleteChengyuan(id);
    }

}
