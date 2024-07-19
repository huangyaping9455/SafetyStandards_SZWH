package org.springblade.anbiao.anquanhuiyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.anquanhuiyi.VO.AnquanhuiyiledgerVO;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;

import java.util.List;

/**
 * <p>
 * 安全会议记录表 Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2022-11-01
 */
public interface AnbiaoAnquanhuiyiMapper extends BaseMapper<AnbiaoAnquanhuiyi> {

	List<AnbiaoAnquanhuiyi> selectGetAll(AnQuanHuiYiPage anQuanHuiYiPage);
	int selectGetAllTotal(AnQuanHuiYiPage anQuanHuiYiPage);

	List<AnbiaoAnquanhuiyi> selectAnquanHuiYiMonth(int year,String deptId);

	List<AnquanhuiyiledgerVO> selectLedgerList(IPage page, AnquanhuiyiledgerVO anquanhuiyiledgerVO);

	List<AnquanhuiyiledgerVO> selectscore( AnquanhuiyiledgerVO anquanhuiyiledgerVO);

	List<AnquanhuiyiledgerVO> selectAllMeetingsInMonth( AnquanhuiyiledgerVO anquanhuiyiledgerVO);

	List<AnbiaoAnquanhuiyi> selectHuiYiMonth(String deptId,String date);

}
