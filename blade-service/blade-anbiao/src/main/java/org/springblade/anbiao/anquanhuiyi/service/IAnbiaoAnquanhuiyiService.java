package org.springblade.anbiao.anquanhuiyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.anquanhuiyi.VO.AnquanhuiyiledgerVO;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;

import java.util.List;

/**
 * <p>
 * 安全会议记录表 服务类
 * </p>
 *
 * @author lmh
 * @since 2022-11-01
 */
public interface IAnbiaoAnquanhuiyiService extends IService<AnbiaoAnquanhuiyi> {

	AnQuanHuiYiPage<AnbiaoAnquanhuiyi> selectGetAll(AnQuanHuiYiPage anQuanHuiYiPage);

	List<AnbiaoAnquanhuiyi> selectAnquanHuiYiMonth(int year,String deptId);

	IPage<AnquanhuiyiledgerVO> selectLedgerList(IPage<AnquanhuiyiledgerVO> page, AnquanhuiyiledgerVO anquanhuiyiledgerVO);

}
