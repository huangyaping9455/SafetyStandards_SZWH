package org.springblade.anbiao.jiashiyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.entity.VehicleImg;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.vo.DriverInfoVO;
import org.springblade.anbiao.jiashiyuan.vo.DriverTJMingXiVO;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanListVO;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanVO;

import java.util.List;

/**
 * Created by you on 2019/4/22.
 */
public interface IJiaShiYuanService extends IService<JiaShiYuan> {
	boolean insertJSY(JiaShiYuan jiaShiYuan);

	boolean updateDel(String id);

	/**
	 * 自定义 分页
	 * @param
	 * @return
	 */
	JiaShiYuanPage<JiaShiYuanListVO> selectPageList(JiaShiYuanPage jiaShiYuanPage);

	JiaShiYuanVO selectByIds(String id);

	/**
	 * @Description: 根据身份证号查询
	 * @Param: cardNo
	 * @return: org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanVO
	 * @Author: elvis
	 * @Date: 2020-06-23
	 */
	JiaShiYuanVO selectByCardNo(@Param("deptId") String deptId,@Param("cardNo") String cardNo, @Param("driverNo") String driverNo,@Param("jiashiyuanleixing") String jiashiyuanleixing);

	/**
	 * 根据驾驶员ID查询绑定车辆
	 * @param jiashiyuanid
	 * @return
	 */
	List<Vehicle> selectByCar(String jiashiyuanid);

	/**
	 * /初始化登录密码
	 * @param password
	 * @param id
	 * @return
	 */
	boolean updatePassWord(String password,String id);

	/**
	 *查询驾驶员绑定车辆
	 * @param jiaShiYuanPage
	 * @return
	 */
	JiaShiYuanPage<JiaShiYuanVO> selectJVList(JiaShiYuanPage jiaShiYuanPage);

	/**
	 * 根据企业ID获取驾驶员列表
	 * @param deptId
	 * @return
	 */
	List<JiaShiYuan> jiaShiYuanList(@Param("deptId") String deptId,@Param("jiashiyuanleixing") String jiashiyuanleixing);

	/**
	 * 导入驾驶员档案信息
	 * @param jiaShiYuan
	 * @return
	 */
	boolean insertSelective(JiaShiYuan jiaShiYuan);

	/**
	 * 导入修改驾驶员档案信息
	 * @param jiaShiYuan
	 * @return
	 */
	boolean updateSelective(JiaShiYuan jiaShiYuan);

	/**
	 * 根据驾驶员姓名、联系电话、企业ID查询驾驶员信息
	 * @param deptId
	 * @param jiashiyuanxingming
	 * @param shoujihaoma
	 * @return
	 */
	JiaShiYuan getjiaShiYuan(@Param("deptId") String deptId,
							 @Param("jiashiyuanxingming") String jiashiyuanxingming,
							 @Param("shoujihaoma") String shoujihaoma,
							 @Param("jiashiyuanleixing") String jiashiyuanleixing);

	JiaShiYuan getjiaShiYuanByOne(@Param("deptId") String deptId,
								  @Param("jiashiyuanxingming") String jiashiyuanxingming,
								  @Param("shoujihaoma") String shoujihaoma,
								  @Param("shenfenzhenghao") String shenfenzhenghao,
								  @Param("jiashiyuanleixing") String jiashiyuanleixing
	);

	/**
	 * 驾驶员登录
	 * @param account
	 * @param password
	 * @return
	 */
	JiaShiYuan getDriver(String account, String password);

	JiaShiYuan selectDriverByopenId(String openid);

	/**
	 * 绑定openid
	 * @param account
	 * @param openid
	 */
	void bindDriverOpenId(String account, String openid);

	/**
	 * 根据驾驶员ID获取个人详细信息（司机小程序个人中心）
	 * @param jsyId
	 * @return
	 */
	DriverInfoVO selectDriverInfo(String jsyId);

	/**
	 * 根据企业ID获取驾驶员信息
	 * @param deptId
	 * @return
	 */
	List<JiaShiYuan> getJiaShiYuanByDept(Integer deptId);

	/**
	 * 根据企业ID获取驾驶员签名
	 * @param deptId
	 * @return
	 */
	List<JiaShiYuanTrain> selectJiaShiYuanTrain(@Param("deptId") Integer deptId);

	int selectMaxId();

	JiaShiYuanPage<JiaShiYuanTJMX> selectAlarmTJMXPage(JiaShiYuanPage jiaShiYuanPage);

	JiaShiYuanPage<JiaShiYuanTJMX> selectAlarmTJMXPage2(JiaShiYuanPage jiaShiYuanPage);
	JiaShiYuanTJMX selectAlarmTJMXVehicle(JiaShiYuanTJMX jiaShiYuanTJMX);
	JiaShiYuanTJMX selectAlarmTJMXVehicleGUA(JiaShiYuanTJMX jiaShiYuanTJMX);

	List<JiaShiYuanTJMX> selectAlarmTJMXPage3(JiaShiYuanTJMX jiaShiYuanTJMX);

	/**
	 * 获取车辆附件
	 * @param jsyId
	 * @return
	 */
	DriverImg getByDriverImg(@Param("jsyId") String jsyId);

	List<DriverTJMingXiVO> selectDriverTJMingXi( DriverTJMingXiVO driverTJMingXiVO );

	/**
	 * 获取驾驶员档案台账数据
	 * @return
	 */
	List<JiaShiYuanTable> jiaShiYuanTableList(@Param("deptId") Integer deptId);

	/**
	 * 驾驶员异动
	 * @param deptId
	 * @param id
	 * @return
	 */
	boolean updateDeptId(@Param("deptId") String deptId,@Param("id") String id);

}
