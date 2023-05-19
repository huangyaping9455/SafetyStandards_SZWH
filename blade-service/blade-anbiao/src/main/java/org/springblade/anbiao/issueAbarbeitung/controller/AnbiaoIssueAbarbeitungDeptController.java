package org.springblade.anbiao.issueAbarbeitung.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.anbiao.issueAbarbeitung.VO.AnbiaoIssueAbarbeitungDeptVo;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitung;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitungDept;
import org.springblade.anbiao.issueAbarbeitung.page.AnbiaoIssueAbarbeitungPage;
import org.springblade.anbiao.issueAbarbeitung.service.IAnbiaoIssueAbarbeitungDeptService;
import org.springblade.anbiao.issueAbarbeitung.service.IAnbiaoIssueAbarbeitungService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyp
 * @since 2023-04-03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/issueAbarbeitungDept")
@Api(value = "企业端--下发整改", tags = "企业端—-下发整改")
public class AnbiaoIssueAbarbeitungDeptController extends BladeController {

	private IAnbiaoIssueAbarbeitungDeptService issueAbarbeitungDeptService;

	private IFileUploadClient fileUploadClient;

	private IOrganizationsService iOrganizationsService;

	private IAnbiaoIssueAbarbeitungService issueAbarbeitungService;

	@PostMapping("/getAnquanhuiyiPage")
	@ApiLog("企业端--下发整改--分页")
	@ApiOperation(value = "企业端--下发整改--分页",notes = "传入AnbiaoIssueAbarbeitungPage")
	public R<AnbiaoIssueAbarbeitungPage<AnbiaoIssueAbarbeitungDeptVo>> getAnquanhuiyiPage(@RequestBody AnbiaoIssueAbarbeitungPage issueAbarbeitungPage, BladeUser user) {
		R r = new R();
		if (user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			r.setData(null);
			r.setSuccess(false);
			return r;
		}
		AnbiaoIssueAbarbeitungPage<AnbiaoIssueAbarbeitungDeptVo> list = issueAbarbeitungDeptService.selectGetAll(issueAbarbeitungPage);
		return R.data(list);
	}

	@PostMapping("/signIn")
	@ApiLog("企业端--下发整改-阅读")
	@ApiOperation(value = "企业端--下发整改-阅读",notes = "传入AnbiaoIssueAbarbeitungDept")
	public R SignIn(@RequestBody AnbiaoIssueAbarbeitungDept issueAbarbeitungDept, BladeUser user){
		R r = new R();
		if (user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			r.setData(null);
			r.setSuccess(false);
			return r;
		}
		QueryWrapper<AnbiaoIssueAbarbeitungDept> abarbeitungDeptQueryWrapper = new QueryWrapper<>();
		abarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getId,issueAbarbeitungDept.getId());
		abarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getIsRead,0);
		AnbiaoIssueAbarbeitungDept detail = issueAbarbeitungDeptService.getBaseMapper().selectOne(abarbeitungDeptQueryWrapper);
		if (detail != null){
			issueAbarbeitungDept.setIsRead(1);
			issueAbarbeitungDept.setReadTime(DateUtil.now());
			issueAbarbeitungDept.setReadName(user.getUserName());
			issueAbarbeitungDept.setReadId(user.getUserId());
			issueAbarbeitungDept.setId(issueAbarbeitungDept.getId());
			int is = issueAbarbeitungDeptService.getBaseMapper().updateById(issueAbarbeitungDept);
			if (is > 0){
				r.setMsg("保存成功");
				r.setCode(200);
				r.setSuccess(true);
			}else {
				r.setMsg("保存失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}else {
			r.setMsg("保存成功");
			r.setCode(200);
			r.setSuccess(true);
		}
		return r;
	}

	@PostMapping("/insert")
	@ApiLog("企业端--下发整改-整改处理")
	@ApiOperation(value = "企业端--下发整改-整改处理",notes = "传入AnbiaoIssueAbarbeitungDept")
	public R insert(@RequestBody AnbiaoIssueAbarbeitungDept issueAbarbeitungDept, BladeUser user){
		R r = new R();
		if (user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			r.setData(null);
			r.setSuccess(false);
			return r;
		}
		QueryWrapper<AnbiaoIssueAbarbeitungDept> abarbeitungDeptQueryWrapper = new QueryWrapper<>();
		abarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getId,issueAbarbeitungDept.getId());
//		abarbeitungDeptQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitungDept::getStatus,0);
		AnbiaoIssueAbarbeitungDept detail = issueAbarbeitungDeptService.getBaseMapper().selectOne(abarbeitungDeptQueryWrapper);
		if (detail != null){
			issueAbarbeitungDept.setStatus(issueAbarbeitungDept.getStatus());
			issueAbarbeitungDept.setIsRead(1);
			issueAbarbeitungDept.setResponseTime(DateUtil.now().substring(0,10));
			issueAbarbeitungDept.setCaozuoshijian(DateUtil.now());
			issueAbarbeitungDept.setCaozuoren(user.getUserName());
			issueAbarbeitungDept.setCaozuorenid(user.getUserId());
			issueAbarbeitungDept.setReadTime(DateUtil.now());
			issueAbarbeitungDept.setReadName(user.getUserName());
			issueAbarbeitungDept.setReadId(user.getUserId());
			issueAbarbeitungDept.setId(issueAbarbeitungDept.getId());
			int is = issueAbarbeitungDeptService.getBaseMapper().updateById(issueAbarbeitungDept);
			if (is > 0){
				r.setMsg("处理成功");
				r.setCode(200);
				r.setSuccess(true);
			}else {
				r.setMsg("处理失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}else{
			r.setMsg("处理失败，请校验数据");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		return r;
	}

	@GetMapping("/detail")
	@ApiLog("企业端--下发整改-整改处理--详情")
	@ApiOperation(value = "企业端--下发整改-整改处理--详情",notes = "传入数据Id")
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true)})
	public R detail(String Id){
		R r = new R();
		AnbiaoIssueAbarbeitungDept deail = issueAbarbeitungDeptService.getById(Id);
		if (deail != null){
			//附件
			if(StrUtil.isNotEmpty(deail.getFujian()) && deail.getFujian().contains("http") == false){
				deail.setFujian(fileUploadClient.getUrl(deail.getFujian()));
			}
			QueryWrapper<AnbiaoIssueAbarbeitung> anbiaoIssueAbarbeitungQueryWrapper = new QueryWrapper<AnbiaoIssueAbarbeitung>();
			anbiaoIssueAbarbeitungQueryWrapper.lambda().eq(AnbiaoIssueAbarbeitung::getId,deail.getIssueId());
			AnbiaoIssueAbarbeitung abarbeitung = issueAbarbeitungService.getBaseMapper().selectOne(anbiaoIssueAbarbeitungQueryWrapper);
			if(abarbeitung != null){
				OrganizationsVO organizations = iOrganizationsService.selectByDeptId(abarbeitung.getFasongdanweiid().toString());
				if(organizations != null){
					deail.setFasongdanwei(organizations.getDeptName());
				}
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




}
