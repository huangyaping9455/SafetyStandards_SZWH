package org.springblade.anbiao.jiashiyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanEnterprise;
import org.springblade.anbiao.jiashiyuan.mapper.JiaShiYuanEnterpriseMapper;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanEnterpriseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by you on 2019/4/23.
 */
@Service
@AllArgsConstructor
public class JiaShiYuanEnterpriseServiceImpl extends ServiceImpl<JiaShiYuanEnterpriseMapper, JiaShiYuanEnterprise> implements IJiaShiYuanEnterpriseService {

	private JiaShiYuanEnterpriseMapper JiaShiYuanEnterpriseMapper;


	@Override
	public boolean updateDel(String id) {
		return JiaShiYuanEnterpriseMapper.updateDel(id);
	}

	@Override
	public JiaShiYuanPage<JiaShiYuanEnterprise> selectPageList(JiaShiYuanPage jiaShiYuanPage) {
		Integer total = JiaShiYuanEnterpriseMapper.selectTotal(jiaShiYuanPage);
		Integer pagetotal = 0;
		if(jiaShiYuanPage.getSize()==0){
			if(jiaShiYuanPage.getTotal()==0){
				jiaShiYuanPage.setTotal(total);
			}
			if(jiaShiYuanPage.getTotal()==0){
				return jiaShiYuanPage;
			}else {
				List<JiaShiYuanEnterprise> vehlist = JiaShiYuanEnterpriseMapper.selectPageList(jiaShiYuanPage);
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
			List<JiaShiYuanEnterprise> vehlist = JiaShiYuanEnterpriseMapper.selectPageList(jiaShiYuanPage);
			return (JiaShiYuanPage<JiaShiYuanEnterprise>) jiaShiYuanPage.setRecords(vehlist);
		}
	}

	@Override
	public JiaShiYuanEnterprise selectByIds(String id) {
		return JiaShiYuanEnterpriseMapper.selectByIds(id);
	}

	@Override
	public JiaShiYuanEnterprise selectByCardNo(String deptId,String cardNo,String driverNo,String jiashiyuanleixing) {
		return JiaShiYuanEnterpriseMapper.selectByCardNo(deptId,cardNo, driverNo,jiashiyuanleixing);
	}


	@Override
	public List<JiaShiYuanEnterprise> jiaShiYuanList(String deptId) {
		return JiaShiYuanEnterpriseMapper.jiaShiYuanList(deptId);
	}

	@Override
	public boolean insertSelective(JiaShiYuanEnterprise jiaShiYuan) {
		return JiaShiYuanEnterpriseMapper.insertSelective(jiaShiYuan);
	}

	@Override
	public boolean updateSelective(JiaShiYuanEnterprise jiaShiYuan) {
		return JiaShiYuanEnterpriseMapper.updateSelective(jiaShiYuan);
	}

	@Override
	public JiaShiYuanEnterprise getjiaShiYuanByOne(String deptId, String jiashiyuanxingming, String shoujihaoma, String shenfenzhenghao, String jiashiyuanleixing) {
		return JiaShiYuanEnterpriseMapper.getjiaShiYuanByOne(deptId, jiashiyuanxingming, shoujihaoma, shenfenzhenghao, jiashiyuanleixing);
	}

}
