package org.springblade.anbiao.cheliangguanli.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.mapper.VehicleMapper;
import org.springblade.anbiao.cheliangguanli.page.VehiclePage;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.vo.JiShuDangAnVO;
import org.springblade.anbiao.cheliangguanli.vo.VehicleListVO;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanVO;
import org.springblade.common.tool.StringUtils;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 车辆自定义接口实现
 *
 * @author :hyp
 */
@Service
@AllArgsConstructor
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements IVehicleService {

    private VehicleMapper vehicleMapper;

	private IFileUploadClient fileUploadClient;

    @Override
    public VehiclePage<VehicleListVO> selectVehiclePage(VehiclePage vehiclePage) {
        Integer total = vehicleMapper.selectVehicleTotal(vehiclePage);
		if(vehiclePage.getSize()==0){
			if(vehiclePage.getTotal()==0){
				vehiclePage.setTotal(total);
			}
			if(vehiclePage.getTotal()==0){
				return vehiclePage;
			}else {
				List<VehicleListVO> vehlist = vehicleMapper.selectVehiclePage(vehiclePage);
				vehiclePage.setRecords(vehlist);
				return vehiclePage;
			}
		}
        Integer pagetotal = 0;
        if (total > 0) {
        	if(total%vehiclePage.getSize()==0){
				pagetotal = total / vehiclePage.getSize();
			}else {
				pagetotal = total / vehiclePage.getSize() + 1;
			}
			List<VehicleListVO> vehlist = vehicleMapper.selectVehiclePage(vehiclePage);
			vehiclePage.setRecords(vehlist);
        }
        if (pagetotal >= vehiclePage.getCurrent()) {
            vehiclePage.setPageTotal(pagetotal);
            Integer offsetNo = 0;
            if (vehiclePage.getCurrent() > 1) {
                offsetNo = vehiclePage.getSize() * (vehiclePage.getCurrent() - 1);
            }
            vehiclePage.setTotal(total);
            vehiclePage.setOffsetNo(offsetNo);
            List<VehicleListVO> vehlist = vehicleMapper.selectVehiclePage(vehiclePage);
            vehiclePage.setRecords(vehlist);
        }
        return vehiclePage;
    }

    @Override
    public VehicleVO selectByKey(String id) {
        return vehicleMapper.selectByKey(id);
    }

	@Override
	public VehicleVO selectByCPYS(String cheliangpaizhao, String chepaiyanse) {
		return vehicleMapper.selectByCPYS(cheliangpaizhao,chepaiyanse);
	}

	@Override
	public VehicleVO selectCPYS(String cheliangpaizhao, String chepaiyanse) {
		return vehicleMapper.selectCPYS(cheliangpaizhao, chepaiyanse);
	}

	@Override
	public VehicleVO selectDeptCPYS(String cheliangpaizhao, String chepaiyanse, String deptId) {
		return vehicleMapper.selectDeptCPYS(cheliangpaizhao, chepaiyanse, deptId);
	}

	@Override
    public boolean deleleVehicle(String id) {
        return vehicleMapper.deleteVehicle(id);
    }

	@Override
	public boolean updateVehicleOutStatus(String id) {
		return vehicleMapper.updateVehicleOutStatus(id);
	}

	@Override
	public boolean updateVehicleSignStatus(String id) {
		return vehicleMapper.updateVehicleSignStatus(id);
	}

	@Override
	public boolean updateVehicleScrapStatus(String id) {
		return vehicleMapper.updateVehicleScrapStatus(id);
	}

	@Override
	public List<VehicleCP> selectCL(String deptId, String cheliangpaizhao) {
		return vehicleMapper.selectCL(deptId,cheliangpaizhao);
	}

	@Override
	public int vehicleDayCount(Integer deptId) {
		return vehicleMapper.vehicleDayCount(deptId);
	}

	@Override
	public int xianzhiVecleCount(Integer deptId) {
		return vehicleMapper.xianzhiVehcleCount(deptId);
	}

	@Override
	public Vehicle vehileOne(String cheliangpaizhao, String chepaiyanse,Integer deptId) {
		return vehicleMapper.vehileOne(cheliangpaizhao,chepaiyanse,deptId);
	}

    @Override
    public List<VehicleVO> selectVehicleAll_FY(String caozuoshijian, String deptId) {
        return vehicleMapper.selectVehicleAll_FY(caozuoshijian,deptId);
    }

	@Override
	public List<VehicleVO> selectVehicleAll_NFY(String caozuoshijian, String deptId) {
		return vehicleMapper.selectVehicleAll_NFY(caozuoshijian,deptId);
	}

	@Override
	public List<VehicleVO> selectVehicleAll(String caozuoshijian) {
		return vehicleMapper.selectVehicleAll(caozuoshijian);
	}

	@Override
	public VehicleVO selectByYYS(String id) {
		return vehicleMapper.selectByYYS(id);
	}

	@Override
	public boolean updateDeptId(String deptId, String id) {
		return vehicleMapper.updateDeptId(deptId,id);
	}

	@Override
	public VehicleVO selectVehicleColor(String color) {
		return vehicleMapper.selectVehicleColor(color);
	}

	@Override
	public boolean insertSelective(Vehicle vehicle) {
		return vehicleMapper.insertSelective(vehicle);
	}

	@Override
	public boolean updateSelective(Vehicle vehicle) {
		return vehicleMapper.updateSelective(vehicle);
	}

	@Override
	public VehicleVO selectByZongDuan(String id) {
		return vehicleMapper.selectByZongDuan(id);
	}

	@Override
	public VehiclePage<VehicleGDSTJ> selectGDSVehiclePage(VehiclePage vehiclePage) {
		Integer total = vehicleMapper.selectGDSVehicleTotal(vehiclePage);
		if(vehiclePage.getSize()==0){
			if(vehiclePage.getTotal()==0){
				vehiclePage.setTotal(total);
			}
			List<VehicleGDSTJ> vehlist = vehicleMapper.selectGDSVehiclePage(vehiclePage);
			vehiclePage.setRecords(vehlist);
			return vehiclePage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%vehiclePage.getSize()==0){
				pagetotal = total / vehiclePage.getSize();
			}else {
				pagetotal = total / vehiclePage.getSize() + 1;
			}
			List<VehicleGDSTJ> vehlist = vehicleMapper.selectGDSVehiclePage(vehiclePage);
			vehiclePage.setRecords(vehlist);
		}
		if (pagetotal >= vehiclePage.getCurrent()) {
			vehiclePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (vehiclePage.getCurrent() > 1) {
				offsetNo = vehiclePage.getSize() * (vehiclePage.getCurrent() - 1);
			}
			vehiclePage.setTotal(total);
			vehiclePage.setOffsetNo(offsetNo);
			List<VehicleGDSTJ> vehlist = vehicleMapper.selectGDSVehiclePage(vehiclePage);
			vehiclePage.setRecords(vehlist);
		}
		return vehiclePage;
	}

	@Override
	public VehiclePage<VehicleGDSTJ> selectGDSMXVehiclePage(VehiclePage vehiclePage) {
		Integer total = vehicleMapper.selectGDSMXVehicleTotal(vehiclePage);
		if(vehiclePage.getSize()==0){
			if(vehiclePage.getTotal()==0){
				vehiclePage.setTotal(total);
			}
			List<VehicleGDSTJ> vehlist = vehicleMapper.selectGDSMXVehiclePage(vehiclePage);
			vehiclePage.setRecords(vehlist);
			return vehiclePage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%vehiclePage.getSize()==0){
				pagetotal = total / vehiclePage.getSize();
			}else {
				pagetotal = total / vehiclePage.getSize() + 1;
			}
			List<VehicleGDSTJ> vehlist = vehicleMapper.selectGDSMXVehiclePage(vehiclePage);
			vehiclePage.setRecords(vehlist);
		}
		if (pagetotal >= vehiclePage.getCurrent()) {
			vehiclePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (vehiclePage.getCurrent() > 1) {
				offsetNo = vehiclePage.getSize() * (vehiclePage.getCurrent() - 1);
			}
			vehiclePage.setTotal(total);
			vehiclePage.setOffsetNo(offsetNo);
			List<VehicleGDSTJ> vehlist = vehicleMapper.selectGDSMXVehiclePage(vehiclePage);
			vehiclePage.setRecords(vehlist);
		}
		return vehiclePage;
	}

	@Override
	public List<Vehicle> vehileList(Integer deptId) {
		return vehicleMapper.vehileList(deptId);
	}

	@Override
	public boolean updateVehicleZongDuanId(String zongduanid, String caozuoren, String caozuorenid, String id) {
		return vehicleMapper.updateVehicleZongDuanId(zongduanid, caozuoren, caozuorenid, id);
	}

	@Override
	public CheliangJiashiyuan getVehidAndJiashiyuanId(String vehid, String jiashiyuanid) {
		return vehicleMapper.getVehidAndJiashiyuanId(vehid, jiashiyuanid);
	}

	@Override
	public boolean insertVehidAndJiashiyuan(CheliangJiashiyuan cheliangJiashiyuan) {
		return vehicleMapper.insertVehidAndJiashiyuan(cheliangJiashiyuan);
	}

	@Override
	public VehicleGDSTJ getVehidNumByDeptId(String deptId) {
		return vehicleMapper.getVehidNumByDeptId(deptId);
	}

	@Override
	public List<SUZHOUDept> getDeptList() {
		return vehicleMapper.getDeptList();
	}

	@Override
	public List<SUZHOUVehicle> getSUZHOUVehicleList(String deptId) {
		return vehicleMapper.getSUZHOUVehicleList(deptId);
	}

	@Override
	public List<SUZHOUVehicleTJ> getSUZHOUVehicleTJList(String deptId) {
		return vehicleMapper.getSUZHOUVehicleTJList(deptId);
	}

	@Override
	public VehicleDriver getVehicleDriver(String deptId,String cheliangpaizhao,String chepaiyanse,String type) {
		return vehicleMapper.getVehicleDriver(deptId,cheliangpaizhao,chepaiyanse,type);
	}

	@Override
	public List<VehicleDriver> getDriverByDeptIdList(String deptId,String type) {
		return vehicleMapper.getDriverByDeptIdList(deptId,type);
	}

	@Override
	public VehicleImg getByVehImg(String vehId) {
		VehicleImg vehImg = vehicleMapper.getByVehImg(vehId);
		if (vehImg != null){
			int count = 0 ;
			if(StringUtils.isNotEmpty(vehImg.getXszzmimg()) && vehImg.getXszzmimg() != null){
				if(!vehImg.getXszzmimg().contains("http")){
					vehImg.setXszzmimg(fileUploadClient.getUrl(vehImg.getXszzmimg()));
				}else{
					vehImg.setXszzmimg(vehImg.getXszzmimg());
				}
				count += 1;
			}
			if(StringUtils.isNotEmpty(vehImg.getXszfmimg()) && vehImg.getXszfmimg() != null){
				if(!vehImg.getXszfmimg().contains("http")){
					vehImg.setXszfmimg(fileUploadClient.getUrl(vehImg.getXszfmimg()));
				}else{
					vehImg.setXszfmimg(vehImg.getXszfmimg());
				}
				count += 1;
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

	@Override
	public List<VehicleVO> selectDriverVehicle(String jsyId) {
		return vehicleMapper.selectDriverVehicle(jsyId);
	}

	@Override
	public List<JiShuDangAnVO> selectJiShuDangAn(JiShuDangAnVO jiShuDangAnVO) {
		return vehicleMapper.selectJiShuDangAn(jiShuDangAnVO);
	}

	@Override
	public List<JiaShiYuanVO> selectCheLiangJiaShiYuan(JiaShiYuanVO jiaShiYuanVO) {
		return vehicleMapper.selectCheLiangJiaShiYuan(jiaShiYuanVO);
	}

	@Override
	public boolean deleteVehicle(Integer isDeleted, String caozuoren, String caozuorenid, String id, String shijian) {
		return vehicleMapper.deleteVehicle(isDeleted.toString(), caozuoren, caozuorenid, id, shijian);
	}


}
