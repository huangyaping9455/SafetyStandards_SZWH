package org.springblade.anbiao.jiashiyuan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.vo.DriverInfoVO;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanVO;

import java.util.List;

/**
 * Created by you on 2019/4/23.
 */
public interface JiaShiYuanMapper extends BaseMapper<JiaShiYuan> {

	boolean insertJSY(JiaShiYuan jiaShiYuan);

	boolean updateDel(String id);

	/**
	 * 自定义分页
	 * @param
	 * @return
	 */
	List<JiaShiYuanVO> selectPageList(JiaShiYuanPage jiaShiYuanPage);

	/**
	 * 统计
	 * @param
	 * @return
	 */
	int selectTotal(JiaShiYuanPage jiaShiYuanPage);

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
	List<JiaShiYuanVO> selectJVList(JiaShiYuanPage jiaShiYuanPage);
	int selectJVTotal(JiaShiYuanPage jiaShiYuanPage);

	/**
	 * 根据企业ID获取驾驶员列表
	 * @param deptId
	 * @return
	 */
	List<JiaShiYuan> jiaShiYuanList(String deptId);

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
	 *绑定openid
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

}
