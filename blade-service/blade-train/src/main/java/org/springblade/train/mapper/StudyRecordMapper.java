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
import org.springblade.train.entity.StudyRecord;
import org.springblade.train.entity.StudyRecordApp;
import org.springblade.train.page.StudyRecordPage;

import java.util.List;

/**
 * Mapper 接口
 * @author 呵呵哒
 */
public interface StudyRecordMapper extends BaseMapper<StudyRecord> {

    /**
     * 添加学习记录
     * @param studyRecord
     * @return
     */
    boolean insertStudyRecordSelective(StudyRecord studyRecord);

    /**
     * 根据学员ID获取学习记录
     * @param studyRecordPage
     * @return
     */
    List<StudyRecordApp> getAppStudyRecordList(StudyRecordPage studyRecordPage);
    int getAppStudyRecordListTotal(StudyRecordPage studyRecordPage);

    /**
     * 查询上次学习记录
     * @param studentId
     * @param courseId
     * @param coursewareId
     * @return
     */
    StudyRecord getStudyRecordList(int studentId,int courseId,int coursewareId);

}
