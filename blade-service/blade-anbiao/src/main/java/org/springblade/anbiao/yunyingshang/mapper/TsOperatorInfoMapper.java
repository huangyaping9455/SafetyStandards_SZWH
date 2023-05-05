package org.springblade.anbiao.yunyingshang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.yunyingshang.entity.TsOperatorInfo;
import org.springblade.anbiao.yunyingshang.page.TsOperatorInfoPage;
import org.springblade.anbiao.yunyingshang.vo.TsOperatorInfoVo;
import org.springblade.anbiao.zhengfu.entity.ZhengFuBaoJingTongJi;
import org.springblade.anbiao.zhengfu.entity.ZhengFuBaoJingTongJiJieSuan;
import org.springblade.anbiao.zhengfu.entity.ZhengFuRiYunXingTongJi;
import org.springblade.anbiao.zhengfu.page.ZhengFuBaoJingTongJiJieSuanPage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2022-11-18
 */
public interface TsOperatorInfoMapper extends BaseMapper<TsOperatorInfo> {

	List<TsOperatorInfo> selectGetAll(TsOperatorInfoPage tsOperatorInfoPage);
	int selectGetAllTotal(TsOperatorInfoPage tsOperatorInfoPage);

	List<ZhengFuRiYunXingTongJi> selectGetYYSRYXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectYYSRYXTJTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	List<ZhengFuBaoJingTongJi> selectGetZFYYSBaoJingPaiMing(@Param("deptId") String deptId,@Param("xiaJiDeptId") String xiaJiDeptId);

	List<TsOperatorInfoVo> selectZFYYSPage(TsOperatorInfoPage tsOperatorInfoPage);
	int selectZFYYSTotal(TsOperatorInfoPage tsOperatorInfoPage);

	List<ZhengFuBaoJingTongJiJieSuan> selectGetBJTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectGetBJTJTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
     * 根据企业ID获取运营商列表
	 * @param deptId
     * @return
     */
	List<TsOperatorInfo> selectOperatorInfo(@Param("deptId") String deptId,@Param("opCode") String opCode);
}
