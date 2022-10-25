package org.springblade.anbiao.cheliangguanli.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.cheliangguanli.entity.VehiclePhone;

import java.util.List;

/**
 * 车辆自定义 接口
 * @author :hyp
 * */
public interface IVehiclePhoneService extends IService<VehiclePhone> {

    List<VehiclePhone> getlist(String vehId);
}
