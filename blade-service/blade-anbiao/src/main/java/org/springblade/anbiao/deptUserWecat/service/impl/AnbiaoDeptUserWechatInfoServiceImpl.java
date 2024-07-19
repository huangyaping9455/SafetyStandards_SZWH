package org.springblade.anbiao.deptUserWecat.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.deptUserWecat.entity.AnbiaoDeptUserWechatInfo;
import org.springblade.anbiao.deptUserWecat.mapper.AnbiaoDeptUserWechatInfoMapper;
import org.springblade.anbiao.deptUserWecat.service.IAnbiaoDeptUserWechatInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-06-12
 */
@Service
@AllArgsConstructor
public class AnbiaoDeptUserWechatInfoServiceImpl extends ServiceImpl<AnbiaoDeptUserWechatInfoMapper, AnbiaoDeptUserWechatInfo> implements IAnbiaoDeptUserWechatInfoService {

	private AnbiaoDeptUserWechatInfoMapper deptUserWechatInfoMapper;

	@Override
	public void bindWechatOpenId(String yhId, String openid, Integer status, Integer type) {
		if(status == 0){
			QueryWrapper<AnbiaoDeptUserWechatInfo> anquanhuiyiSourceQueryWrapper = new QueryWrapper<>();
			anquanhuiyiSourceQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getYhId, yhId);
			AnbiaoDeptUserWechatInfo deail = deptUserWechatInfoMapper.selectOne(anquanhuiyiSourceQueryWrapper);
			if (deail != null) {
				deail.setStatus(0);
				deail.setCreateTime(DateUtil.now());
				deptUserWechatInfoMapper.updateById(deail);
			}
		}else{
			QueryWrapper<AnbiaoDeptUserWechatInfo> anquanhuiyiSourceQueryWrapper = new QueryWrapper<>();
			anquanhuiyiSourceQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getYhId, yhId);
			AnbiaoDeptUserWechatInfo deail = deptUserWechatInfoMapper.selectOne(anquanhuiyiSourceQueryWrapper);
			if (deail == null) {
				AnbiaoDeptUserWechatInfo deptUserWechatInfo = new AnbiaoDeptUserWechatInfo();
				deptUserWechatInfo.setYhId(yhId);
				deptUserWechatInfo.setCreateTime(DateUtil.now());
				deptUserWechatInfo.setIsDeleted(0);
				deptUserWechatInfo.setYhGzhOpenid(openid);
				deptUserWechatInfo.setStatus(1);
				deptUserWechatInfo.setType(type);
				deptUserWechatInfoMapper.insert(deptUserWechatInfo);
			}else {
				deail.setCreateTime(DateUtil.now());
				deail.setYhGzhOpenid(openid);
				deail.setStatus(1);
				deptUserWechatInfoMapper.updateById(deail);
			}
		}
	}

	@Override
	public AnbiaoDeptUserWechatInfo selectByUser(String yhId) {
		return deptUserWechatInfoMapper.selectByUser(yhId);
	}
}
