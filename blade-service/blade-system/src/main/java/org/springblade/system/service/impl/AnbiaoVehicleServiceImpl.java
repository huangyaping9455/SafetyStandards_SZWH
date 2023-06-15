package org.springblade.system.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.VehicleImg;
import org.springblade.common.tool.StringUtils;
import org.springblade.system.entity.AnbiaoVehicle;
import org.springblade.system.entity.AnbiaoVehicleImg;
import org.springblade.system.mapper.AnbiaoVehicleMapper;
import org.springblade.system.service.IAnbiaoVehicleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 车辆信息表 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2023-06-06
 */
@Service
@AllArgsConstructor
public class AnbiaoVehicleServiceImpl extends ServiceImpl<AnbiaoVehicleMapper, AnbiaoVehicle> implements IAnbiaoVehicleService {

	private AnbiaoVehicleMapper vehicleMapper;

	private IFileUploadClient fileUploadClient;

	@Override
	public AnbiaoVehicleImg getByVehImg(String vehId) {
		AnbiaoVehicleImg vehImg = vehicleMapper.getByVehImg(vehId);
		if (vehImg != null){
			int count = 0 ;
			int xszzm = 0 ;
			int xszfm = 0 ;
			if(StringUtils.isNotEmpty(vehImg.getXszzmimg()) && vehImg.getXszzmimg() != null){
				if(!vehImg.getXszzmimg().contains("http")){
					vehImg.setXszzmimg(fileUploadClient.getUrl(vehImg.getXszzmimg()));
				}else{
					vehImg.setXszzmimg(vehImg.getXszzmimg());
				}
				count += 1;
				xszzm += 1;
				vehImg.setXszzmcount(xszzm);
			}
			if(StringUtils.isNotEmpty(vehImg.getXszfmimg()) && vehImg.getXszfmimg() != null){
				if(!vehImg.getXszfmimg().contains("http")){
					vehImg.setXszfmimg(fileUploadClient.getUrl(vehImg.getXszfmimg()));
				}else{
					vehImg.setXszfmimg(vehImg.getXszfmimg());
				}
				count += 1;
				xszfm += 1;
				vehImg.setXszfmcount(xszfm);
			}
			vehImg.setXszcount(count);
			if(StringUtils.isNotEmpty(vehImg.getYszimg()) && vehImg.getYszimg() != null){
				if(!vehImg.getYszimg().contains("http")){
					vehImg.setYszimg(fileUploadClient.getUrl(vehImg.getYszimg()));
				}else{
					vehImg.setYszimg(vehImg.getYszimg());
				}
				count += 1;
				vehImg.setYszimgcount(1);
			}
			if(StringUtils.isNotEmpty(vehImg.getXnbgimg()) && vehImg.getXnbgimg() != null){
				if(!vehImg.getXnbgimg().contains("http")){
					vehImg.setXnbgimg(fileUploadClient.getUrl(vehImg.getXnbgimg()));
				}else{
					vehImg.setXnbgimg(vehImg.getXnbgimg());
				}
				count += 1;
				vehImg.setXnbgimgcount(1);
			}
			if(StringUtils.isNotEmpty(vehImg.getDjzimg()) && vehImg.getDjzimg() != null){
				if(!vehImg.getDjzimg().contains("http")){
					vehImg.setDjzimg(fileUploadClient.getUrl(vehImg.getDjzimg()));
				}else{
					vehImg.setDjzimg(vehImg.getDjzimg());
				}
				count += 1;
				vehImg.setDjzimgcount(1);
			}
			vehImg.setCount(count);
		}
		return vehImg;
	}
}
