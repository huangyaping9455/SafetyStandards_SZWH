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
package org.springblade.train.feign;

import org.springblade.train.entity.*;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * Feign失败配置
 * @author hyp
 */
@Component
public class ITrainClientFallback implements ITrainClient {

    @Override
    public Boolean insertCourseStudentSelective(@Valid CourseStudent courseStudent) {
        return null;
    }

    @Override
    public Unit getUnitByName(String name) {
        return null;
    }

    @Override
    public Student getStudentByName(String driverName, String deptName) {
        return null;
    }


    @Override
    public CourseExt getCourseExt(String alarmType, String deptId) {
        return null;
    }

    @Override
    public List<Course> getCourseALLList(int id) {
        return null;
    }
}
