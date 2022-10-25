package org.springblade.anbiao.jiashiyuan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanEnterprise;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;

import java.util.List;

/**
 * Created by you on 2019/4/23.
 */
public interface JiaShiYuanEnterpriseMapper extends BaseMapper<JiaShiYuanEnterprise> {

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	boolean updateDel(String id);

	/**
	 * 自定义分页
	 * @param
	 * @return
	 */
	List<JiaShiYuanEnterprise> selectPageList(JiaShiYuanPage jiaShiYuanPage);
	int selectTotal(JiaShiYuanPage jiaShiYuanPage);

	/**
	 * 根据ID获取驾驶员档案信息
	 * @param id
	 * @return
	 */
	JiaShiYuanEnterprise selectByIds(String id);

	/**
	 * @Description: 根据身份证号查询
	 * @Param: cardNo
	 * @return: org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanEnterprise
	 * @Author: elvis
	 * @Date: 2020-06-23
	 */
	JiaShiYuanEnterprise selectByCardNo(@Param("deptId") String deptId, @Param("cardNo") String cardNo, @Param("driverNo") String driverNo, @Param("jiashiyuanleixing") String jiashiyuanleixing);


	/**
	 * 根据企业ID获取驾驶员列表
	 * @param deptId
	 * @return
	 */
	List<JiaShiYuanEnterprise> jiaShiYuanList(String deptId);

	/**
	 * 导入驾驶员档案信息
	 * @param jiaShiYuanEnterprise
	 * @return
	 */
	boolean insertSelective(JiaShiYuanEnterprise jiaShiYuanEnterprise);

	/**
	 * 导入修改驾驶员档案信息
	 * @param jiaShiYuanEnterprise
	 * @return
	 */
	boolean updateSelective(JiaShiYuanEnterprise jiaShiYuanEnterprise);

	/**
	 * 根据驾驶员姓名、联系电话、企业ID查询驾驶员信息
	 * @param deptId
	 * @param jiashiyuanxingming
	 * @param shoujihaoma
	 * @return
	 */
	JiaShiYuanEnterprise getjiaShiYuanByOne(@Param("deptId") String deptId,
                                  @Param("jiashiyuanxingming") String jiashiyuanxingming,
                                  @Param("shoujihaoma") String shoujihaoma,
                                  @Param("shenfenzhenghao") String shenfenzhenghao,
                                  @Param("jiashiyuanleixing") String jiashiyuanleixing
    );

}
