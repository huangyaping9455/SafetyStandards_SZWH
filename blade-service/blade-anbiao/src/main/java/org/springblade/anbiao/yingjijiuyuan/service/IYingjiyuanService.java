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
package org.springblade.anbiao.yingjijiuyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.yingjijiuyuan.entity.Yingjiyuan;
import org.springblade.anbiao.yingjijiuyuan.page.YingjiyuanPage;
import org.springblade.anbiao.yingjijiuyuan.vo.YingjiyuanVO;

/**
 * 应急预案 服务类
 * @author hyp
 * @since 2023-06-01
 */
public interface IYingjiyuanService extends IService<Yingjiyuan> {

    /**
     * 自定义分页
     *
     * @param yingjiyuanPage
     * @return
     */
    YingjiyuanPage<YingjiyuanVO> selectYingjiyuanPage(YingjiyuanPage yingjiyuanPage);

    /**
     * 根据id获取数据
     * *@param id
     *
     * @return
     */
    YingjiyuanVO selectByKey(String id);

    /**
     * 自定义 假删除
     * @param id
     * @author :呵呵哒
     */
    boolean deleleYingjiyuan(String id);

}
