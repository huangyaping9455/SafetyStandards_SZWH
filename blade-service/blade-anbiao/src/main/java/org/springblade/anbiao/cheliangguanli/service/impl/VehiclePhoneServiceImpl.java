package org.springblade.anbiao.cheliangguanli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.VehiclePhone;
import org.springblade.anbiao.cheliangguanli.mapper.VehiclePhoneMapper;
import org.springblade.anbiao.cheliangguanli.service.IVehiclePhoneService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :hyp
 */
@Service
@AllArgsConstructor
public class VehiclePhoneServiceImpl extends ServiceImpl<VehiclePhoneMapper, VehiclePhone> implements IVehiclePhoneService {

    private VehiclePhoneMapper vehiclePhoneMapper;

    @Override
    public List<VehiclePhone> getlist(String vehId) {
        QueryWrapper<VehiclePhone> vehiclePhoneQueryWrapper = new QueryWrapper<VehiclePhone>();
        vehiclePhoneQueryWrapper.lambda().eq(VehiclePhone::getVehid, vehId);
        List<VehiclePhone> lists = vehiclePhoneMapper.selectList(vehiclePhoneQueryWrapper);
        return lists;
    }
}
