package org.springblade.anbiao.issueAbarbeitung.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitung;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitungDept;
import org.springblade.anbiao.issueAbarbeitung.page.AnbiaoIssueAbarbeitungPage;
import org.springblade.anbiao.issueAbarbeitung.service.IAnbiaoIssueAbarbeitungDeptService;
import org.springblade.anbiao.issueAbarbeitung.service.IAnbiaoIssueAbarbeitungService;
import org.springblade.anbiao.zhengfu.entity.Organization;
import org.springblade.anbiao.zhengfu.service.IOrganizationService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyp
 * @since 2023-04-03l
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/issueAbarbeitung")
@Api(value = "行管端--下发整改", tags = "行管端—-下发整改")
public class AnbiaoIssueAbarbeitungController extends BladeController {

	private IAnbiaoIssueAbarbeitungService issueAbarbeitungService;

	private IAnbiaoIssueAbarbeitungDeptService issueAbarbeitungDeptService;

	private ISysClient iSysClient;

	private IFileUploadClient fileUploadClient;

	private IOrganizationService iOrganizationService;

	private IOrganizationsService iOrganizationsService;


	@PostMapping("/getAnquanhuiyiPage")
	@ApiLog("行管端--下发整改--分页")
	@ApiOperation(value = "行管端--下发整改--分页",notes = "传入AnbiaoIssueAbarbeitungPage")
	public R<AnbiaoIssueAbarbeitungPage<AnbiaoIssueAbarbeitung>> getAnquanhuiyiPage(@RequestBody AnbiaoIssueAbarbeitungPage issueAbarbeitungPage, BladeUser user) {
		R r = new R();
		if (user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			r.setData(null);
			r.setSuccess(false);
			return r;
		}
		Organization jb = iOrganizationService.selectGetZFJB(issueAbarbeitungPage.getDeptId());
		if(!StringUtils.isBlank(jb.getProvince()) && StringUtils.isBlank(jb.getCity())){
			issueAbarbeitungPage.setProvince(issueAbarbeitungPage.getDeptId());
		}

		if(!StringUtils.isBlank(jb.getCity()) && StringUtils.isBlank(jb.getCountry())){
			issueAbarbeitungPage.setCity(issueAbarbeitungPage.getDeptId());
		}

		if(!StringUtils.isBlank(jb.getCountry())) {
			issueAbarbeitungPage.setCountry(issueAbarbeitungPage.getDeptId());
		}
		AnbiaoIssueAbarbeitungPage<AnbiaoIssueAbarbeitung> list = issueAbarbeitungService.selectGetAll(issueAbarbeitungPage);
		return R.data(list);
	}

	@PostMapping("/insert")
	@ApiLog("行管端—-下发整改-新增")
	@ApiOperation(value = "行管端—-下发整改-新增", notes = "传入AnbiaoIssueAbarbeitung")
	public R insert(@RequestBody AnbiaoIssueAbarbeitung issueAbarbeitung, BladeUser user) throws ParseException {
		R r = new R();
		if (user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			r.setData(null);
			r.setSuccess(false);
			return r;
		}
		String[] idsss = issueAbarbeitung.getDeptId().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss= listid.toArray(new String[1]);

		QueryWrapper<AnbiaoIssueAbarbeitung> anbiaoIssueAbarbeitungQueryWrapper = new QueryWrapper<AnbiaoIssueAbarbeitung>();
		issueAbarbeitung.setDeptId(issueAbarbeitung.getDeptId());
		anbiaoIssueAbarbeitungQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitung::getDeptId,issueAbarbeitung.getDeptId());
		anbiaoIssueAbarbeitungQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitung::getFasongdanweiid,issueAbarbeitung.getFasongdanweiid());
		anbiaoIssueAbarbeitungQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitung::getTitle,issueAbarbeitung.getTitle());
		anbiaoIssueAbarbeitungQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitung::getRectificationTime,issueAbarbeitung.getRectificationTime());
		AnbiaoIssueAbarbeitung deail = issueAbarbeitungService.getBaseMapper().selectOne(anbiaoIssueAbarbeitungQueryWrapper);
		boolean is = false;
		if (deail == null){
			String uuid = UUID.randomUUID().toString().replace("-", "");
			issueAbarbeitung.setId(uuid);
			issueAbarbeitung.setDeptName(issueAbarbeitung.getDeptName());
			issueAbarbeitung.setCreateTime(DateUtil.now());
			issueAbarbeitung.setCareateId(user.getUserId());
			issueAbarbeitung.setCreateName(user.getAccount());
			is = issueAbarbeitungService.save(issueAbarbeitung);
			if (is){
				for(int q = 0;q< idss.length;q++){
					QueryWrapper<AnbiaoIssueAbarbeitungDept> anbiaoIssueAbarbeitungDeptQueryWrapper = new QueryWrapper<AnbiaoIssueAbarbeitungDept>();
					anbiaoIssueAbarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getDeptId,Integer.parseInt(idss[q]));
					anbiaoIssueAbarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getIssueId,issueAbarbeitung.getId());
					AnbiaoIssueAbarbeitungDept abarbeitungDept = issueAbarbeitungDeptService.getBaseMapper().selectOne(anbiaoIssueAbarbeitungDeptQueryWrapper);
					if (abarbeitungDept == null){
						AnbiaoIssueAbarbeitungDept issueAbarbeitungDept = new AnbiaoIssueAbarbeitungDept();
						issueAbarbeitungDept.setDeptId(Integer.parseInt(idss[q]));
						Dept dept = iSysClient.selectDeptById(Integer.parseInt(idss[q]));
						if(dept != null){
							String deptName = dept.getDeptName();
							issueAbarbeitungDept.setDeptName(deptName);
						}
						issueAbarbeitungDept.setIssueId(issueAbarbeitung.getId());
						issueAbarbeitungDept.setCaozuoshijian(DateUtil.now());
						issueAbarbeitungDept.setCaozuorenid(user.getUserId());
						issueAbarbeitungDept.setCaozuoren(user.getAccount());
						is = issueAbarbeitungDeptService.save(issueAbarbeitungDept);
					}
				}
			}
		}else{
			r.setMsg("该数据已存在");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		if (is){
			r.setMsg("新增成功");
			r.setCode(200);
			r.setSuccess(true);
		}else {
			r.setMsg("新增失败");
			r.setCode(500);
			r.setSuccess(false);
		}
		return r;
	}

	@GetMapping("/detail")
	@ApiLog("行管端—-下发整改--整改通知--详情")
	@ApiOperation(value = "行管端—-下发整改--整改通知--详情",notes = "传入数据Id")
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true)})
	public R detail(String Id){
		R r = new R();
		AnbiaoIssueAbarbeitung deail = issueAbarbeitungService.getById(Id);
		if (deail != null){
			//附件
			if(StrUtil.isNotEmpty(deail.getFujian()) && deail.getFujian().contains("http") == false){
				deail.setFujian(fileUploadClient.getUrl(deail.getFujian()));
			}
			OrganizationsVO organizations = iOrganizationsService.selectByDeptId(deail.getFasongdanweiid().toString());
			if(organizations != null){
				deail.setFasongdanwei(organizations.getDeptName());
			}
			r.setData(deail);
			r.setCode(200);
			r.setMsg("获取成功");
			return r;
		}else {
			r.setCode(500);
			r.setMsg("暂无数据");
			return r;
		}
	}

	@GetMapping("/detailDeptList")
	@ApiLog("行管端—-下发整改--整改审核--详情")
	@ApiOperation(value = "行管端—-下发整改--整改审核--详情",notes = "传入数据Id")
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true)})
	public R detailDeptList(String Id){
		R r = new R();
		QueryWrapper<AnbiaoIssueAbarbeitungDept> anbiaoIssueAbarbeitungDeptQueryWrapper = new QueryWrapper<AnbiaoIssueAbarbeitungDept>();
		anbiaoIssueAbarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getIssueId,Id);
		List<AnbiaoIssueAbarbeitungDept> abarbeitungDeptList = issueAbarbeitungDeptService.getBaseMapper().selectList(anbiaoIssueAbarbeitungDeptQueryWrapper);
		if (abarbeitungDeptList != null && abarbeitungDeptList.size() > 0){
			abarbeitungDeptList.forEach(item-> {
				//附件
				if (StrUtil.isNotEmpty(item.getFujian()) && item.getFujian().contains("http") == false) {
					item.setFujian(fileUploadClient.getUrl(item.getFujian()));
				}
			});
			r.setData(abarbeitungDeptList);
			r.setCode(200);
			r.setMsg("获取成功");
			return r;
		}else {
			r.setCode(500);
			r.setMsg("暂无数据");
			return r;
		}
	}

	@GetMapping("/audit")
	@ApiLog("行管端—-下发整改--整改审核--审核")
	@ApiOperation(value = "行管端—-下发整改--整改审核--审核",notes = "传入企业数据Id、审核状态、审核不通过理由")
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "企业数据Id", required = true),
		@ApiImplicitParam(name = "status", value = "审核状态（2：审核通过，3：审核不通过）", required = true),
		@ApiImplicitParam(name = "message", value = "审核不通过理由")
	})
	public R audit(Integer Id,Integer status,String message, BladeUser user){
		R r = new R();
		if (user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			r.setData(null);
			r.setSuccess(false);
			return r;
		}
		QueryWrapper<AnbiaoIssueAbarbeitungDept> abarbeitungDeptQueryWrapper = new QueryWrapper<>();
		abarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getId,Id);
		abarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getStatus,1);
		AnbiaoIssueAbarbeitungDept detail = issueAbarbeitungDeptService.getBaseMapper().selectOne(abarbeitungDeptQueryWrapper);
		if (detail != null){
			AnbiaoIssueAbarbeitungDept issueAbarbeitungDept = new AnbiaoIssueAbarbeitungDept();
			issueAbarbeitungDept.setStatus(status);
			if(StringUtils.isNotEmpty(message) && message != "null"){
				issueAbarbeitungDept.setResponseSituation(message);
			}
			issueAbarbeitungDept.setShenheTime(DateUtil.now());
			issueAbarbeitungDept.setShenheName(user.getAccount());
			issueAbarbeitungDept.setShenheId(user.getUserId());
			issueAbarbeitungDept.setId(Id);
			issueAbarbeitungDept.setIsRead(detail.getIsRead());
			int is = issueAbarbeitungDeptService.getBaseMapper().updateById(issueAbarbeitungDept);
			if (is > 0){
				r.setMsg("审核成功");
				r.setCode(200);
				r.setSuccess(true);
			}else {
				r.setMsg("审核失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}else {
			r.setMsg("该数据已审核");
			r.setCode(200);
			r.setSuccess(true);
		}
		return r;
	}

	@GetMapping("/batchAudit")
	@ApiLog("行管端—-下发整改--整改审核--批量审核")
	@ApiOperation(value = "行管端—-下发整改--整改审核--批量审核",notes = "传入政府数据Id")
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "企业数据Id", required = true)})
	public R batchAudit(String Id, BladeUser user){
		R r = new R();
		if (user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			r.setData(null);
			r.setSuccess(false);
			return r;
		}
		QueryWrapper<AnbiaoIssueAbarbeitungDept> anbiaoIssueAbarbeitungDeptQueryWrapper = new QueryWrapper<AnbiaoIssueAbarbeitungDept>();
		anbiaoIssueAbarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getIssueId,Id);
		anbiaoIssueAbarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getStatus,1);
		List<AnbiaoIssueAbarbeitungDept> abarbeitungDeptList = issueAbarbeitungDeptService.getBaseMapper().selectList(anbiaoIssueAbarbeitungDeptQueryWrapper);
		if (abarbeitungDeptList != null && abarbeitungDeptList.size() > 0){
			abarbeitungDeptList.forEach(item-> {
				AnbiaoIssueAbarbeitungDept issueAbarbeitungDept = new AnbiaoIssueAbarbeitungDept();
				issueAbarbeitungDept.setStatus(2);
				issueAbarbeitungDept.setIsRead(item.getIsRead());
				issueAbarbeitungDept.setShenheTime(DateUtil.now());
				issueAbarbeitungDept.setShenheName(user.getAccount());
				issueAbarbeitungDept.setShenheId(user.getUserId());
				issueAbarbeitungDept.setId(item.getId());
				int is = issueAbarbeitungDeptService.getBaseMapper().updateById(issueAbarbeitungDept);
				if (is > 0){
					r.setMsg("审核成功");
					r.setCode(200);
					r.setSuccess(true);
				}else {
					r.setMsg("审核失败");
					r.setCode(500);
					r.setSuccess(false);
				}
			});
		}else{
			r.setMsg("请校验数据");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		return r;
	}



}
