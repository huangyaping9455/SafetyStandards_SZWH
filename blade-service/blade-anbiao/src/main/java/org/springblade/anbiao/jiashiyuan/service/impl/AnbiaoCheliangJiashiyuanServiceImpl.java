package org.springblade.anbiao.jiashiyuan.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuan;
import org.springblade.anbiao.jiashiyuan.mapper.AnbiaoCheliangJiashiyuanMapper;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoCheliangJiashiyuanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.anbiao.jiashiyuan.vo.CheliangJiashiyuanVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lmh
 * @since 2022-11-08
 */
@Service
@AllArgsConstructor
public class AnbiaoCheliangJiashiyuanServiceImpl extends ServiceImpl<AnbiaoCheliangJiashiyuanMapper, AnbiaoCheliangJiashiyuan> implements IAnbiaoCheliangJiashiyuanService {

	private AnbiaoCheliangJiashiyuanMapper anbiaoCheliangJiashiyuanMapper;

	@Override
	public List<CheliangJiashiyuanVO> SelectByJiashiyuanID(String jiashiyuanid,String shiyongxingzhi) {
		return anbiaoCheliangJiashiyuanMapper.SelectByJiashiyuanID(jiashiyuanid,shiyongxingzhi);
	}

}
