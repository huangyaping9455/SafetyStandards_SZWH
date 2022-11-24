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
package org.springblade.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.train.entity.Snapshot;

/**
 * Mapper 接口
 * @author 呵呵哒
 */
public interface SnapshotMapper extends BaseMapper<Snapshot> {

    /**
     * 添加抓拍图片
     * @param snapshot
     * @return
     */
    boolean insertSnapshotSelective(Snapshot snapshot);

}
