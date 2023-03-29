/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.anbiao.qiyeshouye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.qiyeshouye.entity.AnbiaoIllegalInfo;
import org.springblade.anbiao.qiyeshouye.entity.AnbiaoIllegalInfo;
import org.springblade.anbiao.qiyeshouye.entity.AnbiaoIllegalInfoTJ;
import org.springblade.anbiao.qiyeshouye.mapper.AnbiaoIllegalInfoMapper;
import org.springblade.anbiao.qiyeshouye.page.AnbiaoIllegalInfoPage;
import org.springblade.anbiao.qiyeshouye.service.IAnbiaoIllegalInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author hyp
 * @since 2020-12-29
 */
@Service
@AllArgsConstructor
public class AnbiaoIllegalInfoServiceImpl extends ServiceImpl<AnbiaoIllegalInfoMapper, AnbiaoIllegalInfo> implements IAnbiaoIllegalInfoService {

	private AnbiaoIllegalInfoMapper anbiaoIllegalInfoMapper;


	@Override
	public AnbiaoIllegalInfoPage<AnbiaoIllegalInfo> selectGetAll(AnbiaoIllegalInfoPage anbiaoIllegalInfoPage) {
		Integer total = anbiaoIllegalInfoMapper.selectGetAllTotal(anbiaoIllegalInfoPage);
		if(anbiaoIllegalInfoPage.getSize()==0){
			if(anbiaoIllegalInfoPage.getTotal()==0){
				anbiaoIllegalInfoPage.setTotal(total);
			}

			if(anbiaoIllegalInfoPage.getTotal()==0){
				return anbiaoIllegalInfoPage;
			}else{
				List<AnbiaoIllegalInfo> anbiaoIllegalInfoList = anbiaoIllegalInfoMapper.selectGetAll(anbiaoIllegalInfoPage);
				anbiaoIllegalInfoPage.setRecords(anbiaoIllegalInfoList);
				return anbiaoIllegalInfoPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoIllegalInfoPage.getSize()==0){
				pagetotal = total / anbiaoIllegalInfoPage.getSize();
			}else {
				pagetotal = total / anbiaoIllegalInfoPage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoIllegalInfoPage.getCurrent()) {
			anbiaoIllegalInfoPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoIllegalInfoPage.getCurrent() > 1) {
				offsetNo = anbiaoIllegalInfoPage.getSize() * (anbiaoIllegalInfoPage.getCurrent() - 1);
			}
			anbiaoIllegalInfoPage.setTotal(total);
			anbiaoIllegalInfoPage.setOffsetNo(offsetNo);
			List<AnbiaoIllegalInfo> anbiaoIllegalInfoList = anbiaoIllegalInfoMapper.selectGetAll(anbiaoIllegalInfoPage);
			anbiaoIllegalInfoPage.setRecords(anbiaoIllegalInfoList);
		}
		return anbiaoIllegalInfoPage;
	}

	@Override
	public AnbiaoIllegalInfoPage<AnbiaoIllegalInfoTJ> selectGetAllTJ(AnbiaoIllegalInfoPage anbiaoIllegalInfoPage) {
		Integer total = anbiaoIllegalInfoMapper.selectGetAllTotalTJ(anbiaoIllegalInfoPage);
		if(anbiaoIllegalInfoPage.getSize()==0){
			if(anbiaoIllegalInfoPage.getTotal()==0){
				anbiaoIllegalInfoPage.setTotal(total);
			}

			if(anbiaoIllegalInfoPage.getTotal()==0){
				return anbiaoIllegalInfoPage;
			}else{
				List<AnbiaoIllegalInfoTJ> anbiaoIllegalInfoTJList = anbiaoIllegalInfoMapper.selectGetAllTJ(anbiaoIllegalInfoPage);
				anbiaoIllegalInfoPage.setRecords(anbiaoIllegalInfoTJList);
				return anbiaoIllegalInfoPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoIllegalInfoPage.getSize()==0){
				pagetotal = total / anbiaoIllegalInfoPage.getSize();
			}else {
				pagetotal = total / anbiaoIllegalInfoPage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoIllegalInfoPage.getCurrent()) {
			anbiaoIllegalInfoPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoIllegalInfoPage.getCurrent() > 1) {
				offsetNo = anbiaoIllegalInfoPage.getSize() * (anbiaoIllegalInfoPage.getCurrent() - 1);
			}
			anbiaoIllegalInfoPage.setTotal(total);
			anbiaoIllegalInfoPage.setOffsetNo(offsetNo);
			List<AnbiaoIllegalInfoTJ> anbiaoIllegalInfoTJList = anbiaoIllegalInfoMapper.selectGetAllTJ(anbiaoIllegalInfoPage);
			anbiaoIllegalInfoPage.setRecords(anbiaoIllegalInfoTJList);
		}
		return anbiaoIllegalInfoPage;
	}

	@Override
	public AnbiaoIllegalInfo selectByIds(Integer deptId, Integer Id) {
		return anbiaoIllegalInfoMapper.selectByIds(deptId, Id);
	}
}
