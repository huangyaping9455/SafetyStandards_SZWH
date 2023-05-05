package org.springblade.anbiao.yunyingshang.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
 *  服务类
 * </p>
 *
 * @author hyp
 * @since 2022-11-18
 */
public interface ITsOperatorInfoService extends IService<TsOperatorInfo> {

	TsOperatorInfoPage<TsOperatorInfo> selectGetAll(TsOperatorInfoPage tsOperatorInfoPage);

	ZhengFuBaoJingTongJiJieSuanPage<ZhengFuRiYunXingTongJi> selectGetYYSRYXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	List<ZhengFuBaoJingTongJi> selectGetZFYYSBaoJingPaiMing(@Param("deptId") String deptId,@Param("xiaJiDeptId") String xiaJiDeptId);

	TsOperatorInfoPage<TsOperatorInfoVo> selectZFYYSPage(TsOperatorInfoPage tsOperatorInfoPage);

	ZhengFuBaoJingTongJiJieSuanPage<ZhengFuBaoJingTongJiJieSuan> selectGetBJTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	List<TsOperatorInfo> selectOperatorInfo(@Param("deptId") String deptId,@Param("opCode") String opCode);
}
