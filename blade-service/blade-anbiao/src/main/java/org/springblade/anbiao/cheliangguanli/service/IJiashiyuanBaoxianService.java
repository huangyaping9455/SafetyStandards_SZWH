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
package org.springblade.anbiao.cheliangguanli.service;

import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxian;
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxianInfo;
import org.springblade.anbiao.cheliangguanli.vo.JiashiyuanBaoxianVO;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanLedgerPage;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanLedgerVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.system.entity.Dept;
import org.springblade.system.user.entity.User;

import java.util.List;

/**
 * 驾驶员保险信息主表 服务类
 *
 * @author Blade
 * @since 2022-10-31
 */
public interface IJiashiyuanBaoxianService extends BaseService<JiashiyuanBaoxian> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param jiashiyuanBaoxian
	 * @return
	 */
	IPage<JiashiyuanBaoxianVO> selectJiashiyuanBaoxianPage(IPage<JiashiyuanBaoxianVO> page, JiashiyuanBaoxianVO jiashiyuanBaoxian,String ajbInsuredIds);

	/**
	 * 查询驾驶员保险详细信息
	 * @param ajbId
	 * @return
	 */
	JiashiyuanBaoxianInfo queryDetail(String ajbId);

	JiashiyuanBaoxian queryByMax(String driverId);

	List<User> getDeptUser(String deptId);

	/**
	 * 根据企业ID获取下级所有企业（企业、个体）
	 * @param deptId
	 * @return
	 */
	List<Dept> QiYeList(@Param("deptId") Integer deptId);

	JiaShiYuanLedgerPage selectLedgerList(JiaShiYuanLedgerPage jiaShiYuanLedgerPage);

	List<JiaShiYuanLedgerVO> selectVehicleInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);

	JiaShiYuanLedgerVO selectHeavyTrafficInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);
	List<JiaShiYuanLedgerVO> selectHeavyTrafficInsurance2(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);

	JiaShiYuanLedgerVO selectNotHeavyTrafficInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);
	List<JiaShiYuanLedgerVO> selectNotHeavyTrafficInsurance2(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);

	List<JiaShiYuanLedgerVO> selectOtherVehicleInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);

	JiaShiYuanLedgerVO selectsumVehicleInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);

	List<JiaShiYuanLedgerVO> selectOverlossInsuranceInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);

	List<JiaShiYuanLedgerVO> selectDeptInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);

	JiaShiYuanLedgerVO selectAccidentInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);
	List<JiaShiYuanLedgerVO> selectAccidentInsurance2(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);

	List<JiaShiYuanLedgerVO> selectNotAccidentInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);

	List<JiaShiYuanLedgerVO> selectPersonInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);

	JiaShiYuanLedgerVO selectDeptTotalTmountInsurance(JiaShiYuanLedgerVO jiaShiYuanLedgerVO);
}
