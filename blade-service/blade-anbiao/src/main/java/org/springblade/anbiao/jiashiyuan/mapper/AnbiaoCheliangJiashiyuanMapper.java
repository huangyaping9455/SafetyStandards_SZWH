package org.springblade.anbiao.jiashiyuan.mapper;

import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.jiashiyuan.vo.CheliangJiashiyuanVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2022-11-08
 */
@Mapper
public interface AnbiaoCheliangJiashiyuanMapper extends BaseMapper<AnbiaoCheliangJiashiyuan> {
	public List<CheliangJiashiyuanVO> SelectByJiashiyuanID(String jiashiyuanid,String shiyongxingzhi);
}
