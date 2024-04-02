package org.springblade.anbiao.jiashiyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.entity.VehicleImg;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.jiashiyuan.mapper.JiaShiYuanMapper;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.jiashiyuan.vo.DriverInfoVO;
import org.springblade.anbiao.jiashiyuan.vo.DriverTJMingXiVO;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanListVO;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanVO;
import org.springblade.common.tool.StringUtils;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by you on 2019/4/23.
 */
@Service
@AllArgsConstructor
public class JiaShiYuanServiceImpl extends ServiceImpl<JiaShiYuanMapper, JiaShiYuan> implements IJiaShiYuanService {

	private JiaShiYuanMapper jiaShiYuanMapper;

	private IFileUploadClient fileUploadClient;

	@Override
	public boolean insertJSY(JiaShiYuan jiaShiYuan) {
		return jiaShiYuanMapper.insertJSY(jiaShiYuan);
	}

	@Override
	public boolean updateDel(String id) {
		return jiaShiYuanMapper.updateDel(id);
	}

	@Override
	public JiaShiYuanPage<JiaShiYuanListVO> selectPageList(JiaShiYuanPage jiaShiYuanPage) {
		Integer total = jiaShiYuanMapper.selectTotal(jiaShiYuanPage);
		Integer pagetotal = 0;
		if(jiaShiYuanPage.getSize()==0){
			if(jiaShiYuanPage.getTotal()==0){
				jiaShiYuanPage.setTotal(total);
			}
			if(jiaShiYuanPage.getTotal()==0){
				return jiaShiYuanPage;
			}else {
				List<JiaShiYuanListVO> vehlist = jiaShiYuanMapper.selectPageList(jiaShiYuanPage);
				jiaShiYuanPage.setRecords(vehlist);
				return jiaShiYuanPage;
			}
		}
		if (total > 0) {
			if(total%jiaShiYuanPage.getSize()==0){
				pagetotal = total / jiaShiYuanPage.getSize();
			}else {
				pagetotal = total / jiaShiYuanPage.getSize() + 1;
			}
		}
		if (pagetotal < jiaShiYuanPage.getCurrent()) {
			return jiaShiYuanPage;
		} else {
			jiaShiYuanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (jiaShiYuanPage.getCurrent() > 1) {
				offsetNo = jiaShiYuanPage.getSize() * (jiaShiYuanPage.getCurrent() - 1);
			}
			jiaShiYuanPage.setTotal(total);
			jiaShiYuanPage.setOffsetNo(offsetNo);
			List<JiaShiYuanListVO> vehlist = jiaShiYuanMapper.selectPageList(jiaShiYuanPage);
			return (JiaShiYuanPage<JiaShiYuanListVO>) jiaShiYuanPage.setRecords(vehlist);
		}
	}

	@Override
	public JiaShiYuanVO selectByIds(String id) {
		return jiaShiYuanMapper.selectByIds(id);
	}

	@Override
	public JiaShiYuanVO selectByCardNo(String deptId,String cardNo,String driverNo,String jiashiyuanleixing) {
		return jiaShiYuanMapper.selectByCardNo(deptId,cardNo, driverNo,jiashiyuanleixing);
	}

	@Override
	public List<Vehicle> selectByCar(String jiashiyuanid) {
		return jiaShiYuanMapper.selectByCar(jiashiyuanid);
	}

	@Override
	public boolean updatePassWord(String password,String id) {
		return jiaShiYuanMapper.updatePassWord(password, id);
	}

	@Override
	public JiaShiYuanPage<JiaShiYuanVO> selectJVList(JiaShiYuanPage jiaShiYuanPage) {
		Integer total = jiaShiYuanMapper.selectJVTotal(jiaShiYuanPage);
		if(jiaShiYuanPage.getSize()==0){
			if(jiaShiYuanPage.getTotal()==0){
				jiaShiYuanPage.setTotal(total);
			}

			List<JiaShiYuanVO> vehlist = jiaShiYuanMapper.selectJVList(jiaShiYuanPage);
			jiaShiYuanPage.setRecords(vehlist);
			return jiaShiYuanPage;

		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%jiaShiYuanPage.getSize()==0){
				pagetotal = total / jiaShiYuanPage.getSize();
			}else {
				pagetotal = total / jiaShiYuanPage.getSize() + 1;
			}
		}
		if (pagetotal >= jiaShiYuanPage.getCurrent()) {
			jiaShiYuanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (jiaShiYuanPage.getCurrent() > 1) {
				offsetNo = jiaShiYuanPage.getSize() * (jiaShiYuanPage.getCurrent() - 1);
			}
			jiaShiYuanPage.setTotal(total);
			jiaShiYuanPage.setOffsetNo(offsetNo);
			List<JiaShiYuanVO> vehlist = jiaShiYuanMapper.selectJVList(jiaShiYuanPage);
			jiaShiYuanPage.setRecords(vehlist);
		}
		return jiaShiYuanPage;
	}

	@Override
	public List<JiaShiYuan> jiaShiYuanList(String deptId,String jiashiyuanleixing) {
		return jiaShiYuanMapper.jiaShiYuanList(deptId,jiashiyuanleixing);
	}

	@Override
	public boolean insertSelective(JiaShiYuan jiaShiYuan) {
		return jiaShiYuanMapper.insertSelective(jiaShiYuan);
	}

	@Override
	public boolean updateSelective(JiaShiYuan jiaShiYuan) {
		return jiaShiYuanMapper.updateSelective(jiaShiYuan);
	}

	@Override
	public JiaShiYuan getjiaShiYuan(String deptId, String jiashiyuanxingming, String shoujihaoma,String jiashiyuanleixing) {
		return jiaShiYuanMapper.getjiaShiYuan(deptId, jiashiyuanxingming, shoujihaoma, jiashiyuanleixing);
	}

	@Override
	public JiaShiYuan getjiaShiYuanByOne(String deptId, String jiashiyuanxingming, String shoujihaoma, String shenfenzhenghao, String jiashiyuanleixing) {
		return jiaShiYuanMapper.getjiaShiYuanByOne(deptId, jiashiyuanxingming, shoujihaoma, shenfenzhenghao, jiashiyuanleixing);
	}

	@Override
	public JiaShiYuan getDriver(String account, String password) {
		return jiaShiYuanMapper.getDriver(account, password);
	}

	@Override
	public JiaShiYuan selectDriverByopenId(String openid) {
		return jiaShiYuanMapper.selectDriverByopenId(openid);
	}

	@Override
	public void bindDriverOpenId(String account, String openid) {
		jiaShiYuanMapper.bindDriverOpenId(account, openid);
	}

	@Override
	public DriverInfoVO selectDriverInfo(String jsyId) {
		return jiaShiYuanMapper.selectDriverInfo(jsyId);
	}

	@Override
	public List<JiaShiYuan> getJiaShiYuanByDept(Integer deptId) {
		return jiaShiYuanMapper.getJiaShiYuanByDept(deptId);
	}

	@Override
	public List<JiaShiYuanTrain> selectJiaShiYuanTrain(Integer deptId) {
		return jiaShiYuanMapper.selectJiaShiYuanTrain(deptId);
	}

	@Override
	public int selectMaxId() {
		return jiaShiYuanMapper.selectMaxId();
	}

	@Override
	public JiaShiYuanPage<JiaShiYuanTJMX> selectAlarmTJMXPage(JiaShiYuanPage jiaShiYuanPage) {
		Integer total = jiaShiYuanMapper.selectAlarmTJMXTotal(jiaShiYuanPage);
		if(jiaShiYuanPage.getSize()==0){
			if(jiaShiYuanPage.getTotal()==0){
				jiaShiYuanPage.setTotal(total);
			}

			List<JiaShiYuanTJMX> jiaShiYuanTJMXES = jiaShiYuanMapper.selectAlarmTJMXPage(jiaShiYuanPage);
			jiaShiYuanPage.setRecords(jiaShiYuanTJMXES);
			return jiaShiYuanPage;

		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%jiaShiYuanPage.getSize()==0){
				pagetotal = total / jiaShiYuanPage.getSize();
			}else {
				pagetotal = total / jiaShiYuanPage.getSize() + 1;
			}
		}
		if (pagetotal >= jiaShiYuanPage.getCurrent()) {
			jiaShiYuanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (jiaShiYuanPage.getCurrent() > 1) {
				offsetNo = jiaShiYuanPage.getSize() * (jiaShiYuanPage.getCurrent() - 1);
			}
			jiaShiYuanPage.setTotal(total);
			jiaShiYuanPage.setOffsetNo(offsetNo);
			List<JiaShiYuanTJMX> jiaShiYuanTJMXES = jiaShiYuanMapper.selectAlarmTJMXPage(jiaShiYuanPage);
			jiaShiYuanPage.setRecords(jiaShiYuanTJMXES);
		}
		return jiaShiYuanPage;
	}

	@Override
	public JiaShiYuanPage<JiaShiYuanTJMX> selectAlarmTJMXPage2(JiaShiYuanPage jiaShiYuanPage) {
		Integer total = jiaShiYuanMapper.selectAlarmTJMXTotal2(jiaShiYuanPage);
		if(jiaShiYuanPage.getSize()==0){
			if(jiaShiYuanPage.getTotal()==0){
				jiaShiYuanPage.setTotal(total);
			}

			List<JiaShiYuanTJMX> jiaShiYuanTJMXES = jiaShiYuanMapper.selectAlarmTJMXPage2(jiaShiYuanPage);
			jiaShiYuanPage.setRecords(jiaShiYuanTJMXES);
			return jiaShiYuanPage;

		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%jiaShiYuanPage.getSize()==0){
				pagetotal = total / jiaShiYuanPage.getSize();
			}else {
				pagetotal = total / jiaShiYuanPage.getSize() + 1;
			}
		}
		if (pagetotal >= jiaShiYuanPage.getCurrent()) {
			jiaShiYuanPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (jiaShiYuanPage.getCurrent() > 1) {
				offsetNo = jiaShiYuanPage.getSize() * (jiaShiYuanPage.getCurrent() - 1);
			}
			jiaShiYuanPage.setTotal(total);
			jiaShiYuanPage.setOffsetNo(offsetNo);
			List<JiaShiYuanTJMX> jiaShiYuanTJMXES = jiaShiYuanMapper.selectAlarmTJMXPage2(jiaShiYuanPage);
			jiaShiYuanPage.setRecords(jiaShiYuanTJMXES);
		}
		return jiaShiYuanPage;
	}

	@Override
	public JiaShiYuanTJMX selectAlarmTJMXVehicle(JiaShiYuanTJMX jiaShiYuanTJMX) {
		return jiaShiYuanMapper.selectAlarmTJMXVehicle(jiaShiYuanTJMX);
	}

	@Override
	public JiaShiYuanTJMX selectAlarmTJMXVehicleGUA(JiaShiYuanTJMX jiaShiYuanTJMX) {
		return jiaShiYuanMapper.selectAlarmTJMXVehicleGUA(jiaShiYuanTJMX);
	}

	@Override
	public List<JiaShiYuanTJMX> selectAlarmTJMXPage3(JiaShiYuanTJMX jiaShiYuanTJMX) {
		return jiaShiYuanMapper.selectAlarmTJMXPage3(jiaShiYuanTJMX);
	}

	@Override
	public DriverImg getByDriverImg(String jsyId) {
		DriverImg driverImg = jiaShiYuanMapper.getByDriverImg(jsyId);
		if (driverImg != null){
			int count = 0 ;
			if(StringUtils.isNotEmpty(driverImg.getSfzzmimg()) && driverImg.getSfzzmimg() != null){
				if(!driverImg.getSfzzmimg().contains("http")){
					driverImg.setSfzzmimg(fileUploadClient.getUrl(driverImg.getSfzzmimg()));
				}else{
					driverImg.setSfzzmimg(driverImg.getSfzzmimg());
				}
				count += 1;
			}
			if(StringUtils.isNotEmpty(driverImg.getSfzfmimg()) && driverImg.getSfzfmimg() != null){
				if(!driverImg.getSfzfmimg().contains("http")){
					driverImg.setSfzfmimg(fileUploadClient.getUrl(driverImg.getSfzfmimg()));
				}else{
					driverImg.setSfzfmimg(driverImg.getSfzfmimg());
				}
				count += 1;
			}
			driverImg.setSfzimgzcount(count);
			if(StringUtils.isNotEmpty(driverImg.getRuzhiimg()) && driverImg.getRuzhiimg() != null){
				if(!driverImg.getRuzhiimg().contains("http")){
					driverImg.setRuzhiimg(fileUploadClient.getUrl(driverImg.getRuzhiimg()));
				}else{
					driverImg.setRuzhiimg(driverImg.getRuzhiimg());
				}
				count += 1;
				driverImg.setRuzhiimgzcount(1);
			}
			int jszcount = 0;
			if(StringUtils.isNotEmpty(driverImg.getJszzmimg()) && driverImg.getJszzmimg() != null){
				if(!driverImg.getJszzmimg().contains("http")){
					driverImg.setJszzmimg(fileUploadClient.getUrl(driverImg.getJszzmimg()));
				}else{
					driverImg.setJszzmimg(driverImg.getJszzmimg());
				}
				count += 1;
				jszcount += 1;
			}
			if(StringUtils.isNotEmpty(driverImg.getJszfmimg()) && driverImg.getJszfmimg() != null){
				if(!driverImg.getJszfmimg().contains("http")){
					driverImg.setJszfmimg(fileUploadClient.getUrl(driverImg.getJszfmimg()));
				}else{
					driverImg.setJszfmimg(driverImg.getJszfmimg());
				}
				count += 1;
				jszcount += 1;
			}
			driverImg.setJszimgcount(jszcount);
			if(StringUtils.isNotEmpty(driverImg.getCyzimg()) && driverImg.getCyzimg() != null){
				if(!driverImg.getCyzimg().contains("http")){
					driverImg.setCyzimg(fileUploadClient.getUrl(driverImg.getCyzimg()));
				}else{
					driverImg.setCyzimg(driverImg.getCyzimg());
				}
				count += 1;
				driverImg.setCyzcount(1);
			}
			if(StringUtils.isNotEmpty(driverImg.getTjimg()) && driverImg.getTjimg() != null){
				if(!driverImg.getTjimg().contains("http")){
					driverImg.setTjimg(fileUploadClient.getUrl(driverImg.getTjimg()));
				}else{
					driverImg.setTjimg(driverImg.getTjimg());
				}
				count += 1;
				driverImg.setTjcount(1);
			}
			if(StringUtils.isNotEmpty(driverImg.getGqimg()) && driverImg.getGqimg() != null){
				if(!driverImg.getGqimg().contains("http")){
					driverImg.setGqimg(fileUploadClient.getUrl(driverImg.getGqimg()));
				}else{
					driverImg.setGqimg(driverImg.getGqimg());
				}
				count += 1;
				driverImg.setGqimgcount(1);
			}
			if(StringUtils.isNotEmpty(driverImg.getWzzmimg()) && driverImg.getWzzmimg() != null){
				if(!driverImg.getWzzmimg().contains("http")){
					driverImg.setWzzmimg(fileUploadClient.getUrl(driverImg.getWzzmimg()));
				}else{
					driverImg.setWzzmimg(driverImg.getWzzmimg());
				}
				count += 1;
				driverImg.setWzzmimgcount(1);
			}
			if(StringUtils.isNotEmpty(driverImg.getQtimg()) && driverImg.getQtimg() != null){
				if(!driverImg.getQtimg().contains("http")){
					driverImg.setQtimg(fileUploadClient.getUrl(driverImg.getQtimg()));
				}else{
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
	public List<DriverTJMingXiVO> selectDriverTJMingXi(DriverTJMingXiVO driverTJMingXiVO) {
		return jiaShiYuanMapper.getDriverTJMingXi(driverTJMingXiVO);
	}

	@Override
	public List<JiaShiYuanTable> jiaShiYuanTableList(Integer deptId) {
		return jiaShiYuanMapper.jiaShiYuanTableList(deptId);
	}

	@Override
	public boolean updateDeptId(String deptId, String id) {
		return jiaShiYuanMapper.updateDeptId(deptId, id);
	}


}
