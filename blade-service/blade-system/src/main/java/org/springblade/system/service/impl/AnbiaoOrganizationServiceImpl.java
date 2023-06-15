package org.springblade.system.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.system.entity.AnbiaoOrganizationsFuJian;
import org.springblade.common.tool.StringUtils;
import org.springblade.system.entity.AnbiaoOrganization;
import org.springblade.system.mapper.AnbiaoOrganizationMapper;
import org.springblade.system.service.IAnbiaoOrganizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 企业详细信息 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2023-06-09
 */
@Service
@AllArgsConstructor
public class AnbiaoOrganizationServiceImpl extends ServiceImpl<AnbiaoOrganizationMapper, AnbiaoOrganization> implements IAnbiaoOrganizationService {

	AnbiaoOrganizationMapper mapper;

	private IFileUploadClient fileUploadClient;

	@Override
	public AnbiaoOrganizationsFuJian selectByDeptImg(String deptId) {
		AnbiaoOrganizationsFuJian or = new AnbiaoOrganizationsFuJian();
		AnbiaoOrganizationsFuJian organizationFuJian = mapper.selectByDeptImg(deptId);
		if(organizationFuJian != null){
			int count = 0;
			if(StringUtils.isNotEmpty(organizationFuJian.getDaoluyunshuzhengfujian()) && organizationFuJian.getDaoluyunshuzhengfujian() != null){
				if(!organizationFuJian.getDaoluyunshuzhengfujian().contains("http")){
					or.setDaoluyunshuzhengfujian(fileUploadClient.getUrl(organizationFuJian.getDaoluyunshuzhengfujian()));
				}else{
					or.setDaoluyunshuzhengfujian(organizationFuJian.getDaoluyunshuzhengfujian());
				}
				count += 1;
				or.setDaoluyunshuzhengcount(1);
			}
			if(StringUtils.isNotEmpty(organizationFuJian.getYingyezhizhaofujian()) && organizationFuJian.getYingyezhizhaofujian() != null){
				if(!organizationFuJian.getYingyezhizhaofujian().contains("http")){
					or.setYingyezhizhaofujian(fileUploadClient.getUrl(organizationFuJian.getYingyezhizhaofujian()));
				}else{
					or.setYingyezhizhaofujian(organizationFuJian.getYingyezhizhaofujian());
				}
				count += 1;
				or.setYingyezhizhaocount(1);
			}
			if(StringUtils.isNotEmpty(organizationFuJian.getJingyingxukezhengfujian()) && organizationFuJian.getJingyingxukezhengfujian() != null){
				if(!organizationFuJian.getJingyingxukezhengfujian().contains("http")){
					or.setJingyingxukezhengfujian(fileUploadClient.getUrl(organizationFuJian.getJingyingxukezhengfujian()));
				}else{
					or.setJingyingxukezhengfujian(organizationFuJian.getJingyingxukezhengfujian());
				}
				count += 1;
				or.setJingyingxukezhengcount(1);
			}
			List<AnbiaoOrganizationsFuJian> deptPost = mapper.selectByDeptPost(deptId);
			if(deptPost.size() > 0){
				int postCount = 0;
				for (int p = 0; p < deptPost.size(); p++) {
					postCount = 0;
					deptPost.get(p).setPostId(deptPost.get(p).getPostId());
					deptPost.get(p).setPostName(deptPost.get(p).getPostName());
					List<AnbiaoOrganizationsFuJian> personnelImg = mapper.selectByPersonnelImg(deptId,deptPost.get(p).getPostId());
					if(personnelImg.size() > 0) {
						for (int i = 0; i < personnelImg.size(); i++) {
							int pcount = 0;
							int sfzcount = 0;
							int qtcount = 0;
							personnelImg.get(i).setPersonId(personnelImg.get(i).getPersonId());
							personnelImg.get(i).setPersonName(personnelImg.get(i).getPersonName());
							if (StringUtils.isNotEmpty(personnelImg.get(i).getShenfenzhengfanmianfujian()) && personnelImg.get(i).getShenfenzhengfanmianfujian() != null) {
								if(!personnelImg.get(i).getShenfenzhengfanmianfujian().contains("http")){
									personnelImg.get(i).setShenfenzhengfanmianfujian(fileUploadClient.getUrl(personnelImg.get(i).getShenfenzhengfanmianfujian()));
								}else{
									personnelImg.get(i).setShenfenzhengfanmianfujian(personnelImg.get(i).getShenfenzhengfanmianfujian());
								}
								pcount += 1;
								sfzcount += 1;
							}
							if (StringUtils.isNotEmpty(personnelImg.get(i).getShenfenzhengfujian()) && personnelImg.get(i).getShenfenzhengfujian() != null) {
								if(!personnelImg.get(i).getShenfenzhengfujian().contains("http")){
									personnelImg.get(i).setShenfenzhengfujian(fileUploadClient.getUrl(personnelImg.get(i).getShenfenzhengfujian()));
								}else{
									personnelImg.get(i).setShenfenzhengfujian(personnelImg.get(i).getShenfenzhengfujian());
								}
								pcount += 1;
								sfzcount += 1;
							}
							personnelImg.get(i).setShenfenzhengcount(sfzcount);
							if (StringUtils.isNotEmpty(personnelImg.get(i).getQitafanmianfujian()) && personnelImg.get(i).getQitafanmianfujian() != null) {
								if(!personnelImg.get(i).getQitafanmianfujian().contains("http")){
									personnelImg.get(i).setQitafanmianfujian(fileUploadClient.getUrl(personnelImg.get(i).getQitafanmianfujian()));
								}else{
									personnelImg.get(i).setQitafanmianfujian(personnelImg.get(i).getQitafanmianfujian());
								}
								pcount += 1;
								qtcount +=1;
							}
							if (StringUtils.isNotEmpty(personnelImg.get(i).getQitazhengmianfujian()) && personnelImg.get(i).getQitazhengmianfujian() != null) {
								if(!personnelImg.get(i).getQitazhengmianfujian().contains("http")){
									personnelImg.get(i).setQitazhengmianfujian(fileUploadClient.getUrl(personnelImg.get(i).getQitazhengmianfujian()));
								}
								pcount += 1;
								qtcount +=1;
							}
							personnelImg.get(i).setQitacount(qtcount);
							personnelImg.get(i).setCount(pcount);
							postCount += pcount;
						}
						deptPost.get(p).setPersonnelFuJianList(personnelImg);
					}
					deptPost.get(p).setCount(postCount);
					or.setPostFuJianList(deptPost);
					count += postCount;
				}
			}
			or.setCount(count);
			or.setId(organizationFuJian.getId());
			or.setDeptId(organizationFuJian.getDeptId());
			or.setDeptName(organizationFuJian.getDeptName());
		}
		return or;
	}
}
