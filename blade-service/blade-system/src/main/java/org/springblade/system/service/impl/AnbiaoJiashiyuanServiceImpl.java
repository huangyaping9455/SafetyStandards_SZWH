package org.springblade.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.common.tool.StringUtils;
import org.springblade.system.entity.AnbiaoDriverImg;
import org.springblade.system.entity.AnbiaoJiashiyuan;
import org.springblade.system.mapper.AnbiaoJiashiyuanMapper;
import org.springblade.system.service.IAnbiaoJiashiyuanService;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 驾驶员信息表 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2023-06-06
 */
@Service
@AllArgsConstructor
public class AnbiaoJiashiyuanServiceImpl extends ServiceImpl<AnbiaoJiashiyuanMapper, AnbiaoJiashiyuan> implements IAnbiaoJiashiyuanService {

	private AnbiaoJiashiyuanMapper jiaShiYuanMapper;

	private IFileUploadClient fileUploadClient;

	@Override
	public AnbiaoDriverImg getByDriverImg(String jsyId) {
		AnbiaoDriverImg driverImg = jiaShiYuanMapper.getByDriverImg(jsyId);
		if (driverImg != null) {
			int count = 0;
			int sfzzmcount = 0;
			int sfzfmcount = 0;
			if (StringUtils.isNotEmpty(driverImg.getSfzzmimg()) && driverImg.getSfzzmimg() != null) {
				if (!driverImg.getSfzzmimg().contains("http")) {
					driverImg.setSfzzmimg(fileUploadClient.getUrl(driverImg.getSfzzmimg()));
				} else {
					driverImg.setSfzzmimg(driverImg.getSfzzmimg());
				}
				count += 1;
				sfzzmcount += 1;
				driverImg.setSfzzmimgzcount(sfzzmcount);
			}
			if (StringUtils.isNotEmpty(driverImg.getSfzfmimg()) && driverImg.getSfzfmimg() != null) {
				if (!driverImg.getSfzfmimg().contains("http")) {
					driverImg.setSfzfmimg(fileUploadClient.getUrl(driverImg.getSfzfmimg()));
				} else {
					driverImg.setSfzfmimg(driverImg.getSfzfmimg());
				}
				count += 1;
				sfzfmcount += 1;
				driverImg.setSfzfmimgzcount(sfzfmcount);
			}
			driverImg.setSfzimgzcount(count);
			if (StringUtils.isNotEmpty(driverImg.getRuzhiimg()) && driverImg.getRuzhiimg() != null) {
				if (!driverImg.getRuzhiimg().contains("http")) {
					driverImg.setRuzhiimg(fileUploadClient.getUrl(driverImg.getRuzhiimg()));
				} else {
					driverImg.setRuzhiimg(driverImg.getRuzhiimg());
				}
				count += 1;
				driverImg.setRuzhiimgzcount(1);
			}
			int jszcount = 0;
			int jszzmcount = 0;
			int jszfmcount = 0;
			if (StringUtils.isNotEmpty(driverImg.getJszzmimg()) && driverImg.getJszzmimg() != null) {
				if (!driverImg.getJszzmimg().contains("http")) {
					driverImg.setJszzmimg(fileUploadClient.getUrl(driverImg.getJszzmimg()));
				} else {
					driverImg.setJszzmimg(driverImg.getJszzmimg());
				}
				count += 1;
				jszcount += 1;
				jszzmcount += 1;
				driverImg.setJszzmimgcount(jszzmcount);
			}
			if (StringUtils.isNotEmpty(driverImg.getJszfmimg()) && driverImg.getJszfmimg() != null) {
				if (!driverImg.getJszfmimg().contains("http")) {
					driverImg.setJszfmimg(fileUploadClient.getUrl(driverImg.getJszfmimg()));
				} else {
					driverImg.setJszfmimg(driverImg.getJszfmimg());
				}
				count += 1;
				jszcount += 1;
				jszfmcount += 1;
				driverImg.setJszfmimgcount(jszfmcount);
			}
			driverImg.setJszimgcount(jszcount);
			if (StringUtils.isNotEmpty(driverImg.getCyzimg()) && driverImg.getCyzimg() != null) {
				if (!driverImg.getCyzimg().contains("http")) {
					driverImg.setCyzimg(fileUploadClient.getUrl(driverImg.getCyzimg()));
				} else {
					driverImg.setCyzimg(driverImg.getCyzimg());
				}
				count += 1;
//				driverImg.setCyzcount(1);
			}
			if (StringUtils.isNotEmpty(driverImg.getTjimg()) && driverImg.getTjimg() != null) {
				if (!driverImg.getTjimg().contains("http")) {
					driverImg.setTjimg(fileUploadClient.getUrl(driverImg.getTjimg()));
				} else {
					driverImg.setTjimg(driverImg.getTjimg());
				}
				count += 1;
//				driverImg.setTjcount(1);
			}
			if (StringUtils.isNotEmpty(driverImg.getGqimg()) && driverImg.getGqimg() != null) {
				if (!driverImg.getGqimg().contains("http")) {
					driverImg.setGqimg(fileUploadClient.getUrl(driverImg.getGqimg()));
				} else {
					driverImg.setGqimg(driverImg.getGqimg());
				}
				count += 1;
				driverImg.setGqimgcount(1);
			}
			if (StringUtils.isNotEmpty(driverImg.getWzzmimg()) && driverImg.getWzzmimg() != null) {
				if (!driverImg.getWzzmimg().contains("http")) {
					driverImg.setWzzmimg(fileUploadClient.getUrl(driverImg.getWzzmimg()));
				} else {
					driverImg.setWzzmimg(driverImg.getWzzmimg());
				}
				count += 1;
				driverImg.setWzzmimgcount(1);
			}
			if (StringUtils.isNotEmpty(driverImg.getQtimg()) && driverImg.getQtimg() != null) {
				if (!driverImg.getQtimg().contains("http")) {
					driverImg.setQtimg(fileUploadClient.getUrl(driverImg.getQtimg()));
				} else {
					driverImg.setQtimg(driverImg.getQtimg());
				}
				count += 1;
				driverImg.setQtimgcount(1);
			}
			driverImg.setCount(count);
		}
		return driverImg;
	}

	@Override
	public List<AnbiaoDriverImg> getByDriverImgAll(String jiashiyuanxingming,String deptId) {
		return baseMapper.getByDriverImgAll(jiashiyuanxingming,deptId);
	}

}
