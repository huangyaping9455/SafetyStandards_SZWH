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
package org.springblade.anbiao.jinritixing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.jinritixing.entity.*;
import org.springblade.anbiao.jinritixing.page.JinritixingPage;
import org.springblade.anbiao.jinritixing.vo.FyyhTiXingInfoVO;
import org.springblade.anbiao.jinritixing.vo.FyyhTiXingVO;
import org.springblade.anbiao.jinritixing.vo.JinritixingVO;

import java.util.List;

/**
 *  服务类
 */
public interface IJinritixingService extends IService<Jinritixing> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param jinritixing
	 * @return
	 */
	IPage<JinritixingVO> selectJinritixingPage(IPage<JinritixingVO> page, JinritixingVO jinritixing);

	boolean updateDel(String id);

	/**
	 * 自定义分页
	 * @param
	 * @return
	 */
	JinritixingPage<JinritixingVO> selectPageList(JinritixingPage Page);

	JinritixingVO selectByIds(String id);

	JinritixingPage<JinritixingVO> selectLists(JinritixingPage Page);

	int selectNum(JinritixingPage Page);

	JinritixingPage<FyyhTiXingVO> selectFYYHPageList(JinritixingPage jinritixingPage);

	List<FyyhTiXingVO> selectCountScore(Integer deptId);

	List<FyyhTiXingVO> selectFinshCountScore(Integer deptId);

	JinritixingPage<FyyhTiXingInfoVO> selectYHFLPageList(JinritixingPage jinritixingPage);

	List<FyyhTiXingInfoVO> selectJinritixingByDeptId(Integer deptId, Integer tixingxiangqingid);

	List<YinHuanVehicle> selectYHXDept();

	List<YinHuanVehicle> selectVehQZBFSJ(Integer deptId,String type);

	List<YinHuanVehicle> selectVehXCNSSJ(Integer deptId,String type);

	List<YinHuanVehicle> selectVehXCNJSJ(Integer deptId,String type);

	List<YinHuanVehicle> selectVehDLYSZYXQSJ(Integer deptId,String type);

	List<YinHuanVehicle> selectVehXCJSPDQSJ(Integer deptId,String type);

	List<YinHuanVehicle> selectVehBXSJ(Integer deptId,Integer toubaoleixing,String type);

	List<YinHuanDriver> selectDriverSJ(Integer deptId, String leixing, String type);

	List<YinHuanDept> selectDeptSJ(Integer deptId, String leixing, String type);

	YinHuanRate selectDeptRate(Integer deptId);


}
