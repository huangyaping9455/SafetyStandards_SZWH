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
package org.springblade.anbiao.muban.service;

import com.baomidou.mybatisplus.extension.service.IService;
import feign.Param;
import org.springblade.anbiao.muban.entity.Muban;
import org.springblade.anbiao.muban.entity.MubanMap;
import org.springblade.anbiao.muban.vo.MubanVO;
import org.springblade.core.tool.api.R;

import java.util.List;

/**
 *  服务类
 * @author 呵呵哒
 */
public interface IMubanService extends IService<Muban> {

	/**
	 * 获取模板数据
	 *
	 * @return
	 */
	List<MubanVO> selectMubanPage(Integer id);

	/**
	 * 获取数据
	 * @param token
	 * @return
	 */
	Muban selectByToken(String token);


	/**
	 * 获取token个数
	 * @param token
	 * @return
	 */
	int CountToken(String token);

	/**
	 * 根据名称查询数据
	 * @param muban
	 * @return
	 */
	Muban selectByName(String muban);

	/**
	 * 获取名称数量
	 * @param muban
	 * @return
	 */
	int CountMuban(String muban);

	/**
	* @Description: 根据表名查询默认模板配置字段
	* @Param: [tableName]
	* @return: java.util.List<org.springblade.anbiao.muban.entity.MubanMap>
	* @Author: 呵呵哒
	*/
	List<MubanMap> selectMapList(@Param("tableName") String tableName);


	/**
	 *初始化安标基础资料模板配置同步
	 * @author: hyp
	 * @date: 2019/7/20 17:18
	 * @param deptId
	 * @return: org.springblade.core.tool.api.R
	 */
	R initConf(Integer deptId,String [] tables);

	/**
	 * 获取一键初始化表名
	 * @param id
	 * @return
	 */
	List<MubanVO> selectBiaoMing(Integer id);
}
