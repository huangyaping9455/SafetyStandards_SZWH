package org.springblade.anbiao.jiashiyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanTrain;
import org.springblade.anbiao.jiashiyuan.mapper.JiaShiYuanMapper;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.jiashiyuan.vo.DriverInfoVO;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by you on 2019/4/23.
 */
@Service
@AllArgsConstructor
public class JiaShiYuanServiceImpl extends ServiceImpl<JiaShiYuanMapper, JiaShiYuan> implements IJiaShiYuanService {

	private JiaShiYuanMapper jiaShiYuanMapper;

	@Override
	public boolean insertJSY(JiaShiYuan jiaShiYuan) {
		return jiaShiYuanMapper.insertJSY(jiaShiYuan);
	}

	@Override
	public boolean updateDel(String id) {
		return jiaShiYuanMapper.updateDel(id);
	}

	@Override
	public JiaShiYuanPage<JiaShiYuanVO> selectPageList(JiaShiYuanPage jiaShiYuanPage) {
		Integer total = jiaShiYuanMapper.selectTotal(jiaShiYuanPage);
		Integer pagetotal = 0;
		if(jiaShiYuanPage.getSize()==0){
			if(jiaShiYuanPage.getTotal()==0){
				jiaShiYuanPage.setTotal(total);
			}
			if(jiaShiYuanPage.getTotal()==0){
				return jiaShiYuanPage;
			}else {
				List<JiaShiYuanVO> vehlist = jiaShiYuanMapper.selectPageList(jiaShiYuanPage);
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
			List<JiaShiYuanVO> vehlist = jiaShiYuanMapper.selectPageList(jiaShiYuanPage);
			return (JiaShiYuanPage<JiaShiYuanVO>) jiaShiYuanPage.setRecords(vehlist);
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
	public List<JiaShiYuan> jiaShiYuanList(String deptId) {
		return jiaShiYuanMapper.jiaShiYuanList(deptId);
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


}
