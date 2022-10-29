
package org.springblade.anbiao.guanlijigouherenyuan.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.cheliangguanli.entity.VehicleGDSTJ;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.configure.entity.Configure;
import org.springblade.anbiao.configure.service.IConfigureService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.page.OrganizationsPage;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.feign.IUserClient;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/organizations")
@Api(value = "企业资料", tags = "企业资料")
public class OrganizationsController extends BladeController {

	private IOrganizationsService organizationService;
	private IConfigureService mapService;
	private ISysClient iSysClient;
	private IFileUploadClient fileUploadClient;
	private IVehicleService iVehicleService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiLog("详情-企业资料")
	@ApiOperation(value = "详情-企业资料", notes = "传入id", position = 1)
	public R detail(String id) {
		return R.data(organizationService.selectByIds(id));
	}

	/**
	 * 分页
	 */
	@PostMapping("/list")
	@ApiLog("分页-企业资料")
	@ApiOperation(value = "分页-企业资料", notes = "传入OrganizationPage", position = 2)
	public R<OrganizationsPage<OrganizationsVO>> list(@RequestBody OrganizationsPage Page) {
		OrganizationsPage<OrganizationsVO> pages = organizationService.selectPageList(Page);
		return R.data(pages);
	}
	/**
	 * 详情(树形结构详情)
	 */
	@GetMapping("/detailByDeptId")
	@ApiLog("详情(树形结构详情)-企业资料")
	@ApiOperation(value = "详情(树形结构详情)-企业资料", notes = "传入单位id", position = 1)
	public R detailByDeptId(String id ){
		OrganizationsVO organization=organizationService.selectByDeptId(id);
		if("qiye".equals(organization.getJigouleixing())){

			if(!StringUtils.isBlank(organization.getProvince())){
				Organizations sheng = organizationService.selectByDeptId(organization.getProvince());
				organization.setProvince(sheng.getDeptName());
			}

			if(!StringUtils.isBlank(organization.getCity())){
				Organizations shi = organizationService.selectByDeptId(organization.getCity());
				organization.setCity(shi.getDeptName());
			}

			if(!StringUtils.isBlank(organization.getCountry())){
				Organizations xian = organizationService.selectByDeptId(organization.getCountry());
				organization.setCountry(xian.getDeptName());
			}
		}

		//登录页
		if(StrUtil.isNotEmpty(organization.getLoginPhoto())){
			organization.setLoginPhoto(fileUploadClient.getUrl(organization.getLoginPhoto()));
		}
		//首页
		if(StrUtil.isNotEmpty(organization.getHomePhoto())){
			organization.setHomePhoto(fileUploadClient.getUrl(organization.getHomePhoto()));
		}
		//简介页
		if(StrUtil.isNotEmpty(organization.getProfilePhoto())){
			organization.setProfilePhoto(fileUploadClient.getUrl(organization.getProfilePhoto()));
		}
		//logo页面
		if(StrUtil.isNotEmpty(organization.getLogoPhoto())){
			organization.setLogoPhoto(fileUploadClient.getUrl(organization.getLogoPhoto()));
		}
		//logo_rizhi
		if(StrUtil.isNotEmpty(organization.getLogoRizhi())){
			organization.setLogoRizhi(fileUploadClient.getUrl(organization.getLogoRizhi()));
		}
		//app登录
		if(StrUtil.isNotEmpty(organization.getLoginPhotoApp())){
			organization.setLoginPhotoApp(fileUploadClient.getUrl(organization.getLoginPhotoApp()));
		}
		if(StrUtil.isNotEmpty(organization.getHomePhotoApp())){
			organization.setHomePhotoApp(fileUploadClient.getUrl(organization.getHomePhotoApp()));
		}
		if(StrUtil.isNotEmpty(organization.getProfilePhotoApp())){
			organization.setProfilePhotoApp(fileUploadClient.getUrl(organization.getProfilePhotoApp()));
		}
		if(StringUtils.isBlank(organization.getTree_code())){
			organization.setTree_code(organization.getTreecode());
		}
		if(StrUtil.isNotEmpty(organization.getZuzhijigoutu())){
			organization.setZuzhijigoutu(fileUploadClient.getUrl(organization.getZuzhijigoutu()));
		}
		if(StrUtil.isNotEmpty(organization.getDaoluyunshuzhengfujian())){
			organization.setDaoluyunshuzhengfujian(fileUploadClient.getUrl(organization.getDaoluyunshuzhengfujian()));
		}
		if(StrUtil.isNotEmpty(organization.getJingyingxukezhengfujian())){
			organization.setJingyingxukezhengfujian(fileUploadClient.getUrl(organization.getJingyingxukezhengfujian()));
		}
		if(StrUtil.isNotEmpty(organization.getYingyezhizhaofujian())){
			organization.setYingyezhizhaofujian(fileUploadClient.getUrl(organization.getYingyezhizhaofujian()));
		}
		return R.data(organization);
	}
	/**
	 * 根据单位id获取logourl
	 */
	@GetMapping("/selectBylogoRZ")
	@ApiLog("id获取logourl-企业资料")
	@ApiOperation(value = "id获取logourl-企业资料", notes = "传入单位id", position = 1)
	public R<String> selectBylogoRZ(String id ){
		Organizations organization=organizationService.selectByDeptId(id);
		String str="";
		//logo_rizhi
		if(StrUtil.isNotEmpty(organization.getLogoRizhi())){
			str=fileUploadClient.getUrllogin(organization.getLogoRizhi());
		}
		return R.data(str);
	}
	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-企业资料")
	@ApiOperation(value = "新增-企业资料", notes = "传入organization", position = 3)
	public R<Dept> insert(@RequestBody Organizations organization,BladeUser user) {
		int i=iSysClient.selectByName(organization.getDeptName());
		Dept obj=new Dept();
		R r = new R();
		if(i>0){
			r.setMsg("该机构已存在");
			r.setCode(500);
			r.setData("");
		}else{
			String str="1";
			//登录页
			if(StringUtil.isNotBlank(organization.getLoginPhoto())){
				fileUploadClient.updateCorrelation(organization.getLoginPhoto(),str);
			}
			//首页
			if(StringUtil.isNotBlank(organization.getHomePhoto())){
				fileUploadClient.updateCorrelation(organization.getHomePhoto(),str);
			}
			//简介页
			if(StringUtil.isNotBlank(organization.getProfilePhoto())){
				fileUploadClient.updateCorrelation(organization.getProfilePhoto(),str);
			}
			//logo
			if(StringUtil.isNotBlank(organization.getLogoPhoto())){
				fileUploadClient.updateCorrelation(organization.getLogoPhoto(),str);
			}
			//logo
			if(StringUtil.isNotBlank(organization.getLogoRizhi())){
				fileUploadClient.updateCorrelation(organization.getLogoRizhi(),str);
			}
			if(StringUtil.isNotBlank(organization.getLoginPhotoApp())){
				fileUploadClient.updateCorrelation(organization.getLoginPhotoApp(),str);
			}
			if(StringUtil.isNotBlank(organization.getHomePhotoApp())){
				fileUploadClient.updateCorrelation(organization.getHomePhotoApp(),str);
			}
			if(StringUtil.isNotBlank(organization.getProfilePhotoApp())){
				fileUploadClient.updateCorrelation(organization.getProfilePhotoApp(),str);
			}
			if(user == null){
				organization.setCaozuoren("admin");
				organization.setCaozuorenid(1);
			}else{
				organization.setCaozuoren(user.getUserName());
				organization.setCaozuorenid(user.getUserId());
			}
			organization.setCaozuoshijian(DateUtil.now());
			organization.setCreatetime(DateUtil.now());
			//执行机构表新增
			Dept dept=new Dept();
			String treeCode=iSysClient.selectByTreeCode(organization.getParentId()).getTreeCode();
			dept.setTreeCode(treeCode);
			dept.setId(iSysClient.selectMaxId()+1);
			dept.setExtendType("机构");
			dept.setParentId(Integer.parseInt(organization.getParentId()));
			dept.setDeptName(organization.getDeptName());
			dept.setFullName(organization.getDeptName());
			boolean flag=iSysClient.insertDept(dept);
			organization.setDeptId(dept.getId().toString());
			organizationService.insertOrganizationsSelective(organization);
			if(flag==true){
				obj=iSysClient.selectById(dept.getId().toString());
			}
			r.setMsg("操作成功");
			r.setCode(200);
			r.setData(obj);
		}
		return r;
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiLog("修改-企业资料")
	@ApiOperation(value = "修改-企业资料", notes = "传入organization", position = 4)
	public R<Dept> update(@RequestBody Organizations organization,BladeUser user) {
		int i=iSysClient.selectByName(organization.getDeptName());
		Dept obj=new Dept();
		R r = new R();
		if(i>1){
			r.setMsg("该机构已存在");
			r.setCode(500);
			r.setData("");
		}else{
			String str="1";
			//登录页
			if(StringUtil.isNotBlank(organization.getLoginPhoto())){
				fileUploadClient.updateCorrelation(organization.getLoginPhoto(),"1");
			}
			//首页
			if(StringUtil.isNotBlank(organization.getHomePhoto())){
				fileUploadClient.updateCorrelation(organization.getHomePhoto(),"1");
			}
			//简介页
			if(StringUtil.isNotBlank(organization.getProfilePhoto())){
				fileUploadClient.updateCorrelation(organization.getProfilePhoto(),"1");
			}
			//logo
			if(StringUtil.isNotBlank(organization.getLogoPhoto())){
				fileUploadClient.updateCorrelation(organization.getLogoPhoto(),"1");
			}
			//logo
			if(StringUtil.isNotBlank(organization.getLogoRizhi())){
				fileUploadClient.updateCorrelation(organization.getLogoRizhi(),"1");
			}
			if(StringUtil.isNotBlank(organization.getLoginPhotoApp())){
				fileUploadClient.updateCorrelation(organization.getLoginPhotoApp(),"1");
			}
			if(StringUtil.isNotBlank(organization.getHomePhotoApp())){
				fileUploadClient.updateCorrelation(organization.getHomePhotoApp(),"1");
			}
			if(StringUtil.isNotBlank(organization.getProfilePhotoApp())){
				fileUploadClient.updateCorrelation(organization.getProfilePhotoApp(),"1");
			}
			if(user == null){
				organization.setCaozuoren("admin");
				organization.setCaozuorenid(1);
			}else{
				organization.setCaozuoren(user.getUserName());
				organization.setCaozuorenid(user.getUserId());
			}
			organization.setCaozuoshijian(DateUtil.now());
			organizationService.updateById(organization);

			//执行机构表编辑
			Dept dept=iSysClient.selectById(organization.getDeptId());
			dept.setDeptName(organization.getDeptName());
			boolean flag=iSysClient.updateDept(dept);
			if(flag==true){
				obj=iSysClient.selectById(dept.getId().toString());
			}
			r.setMsg("操作成功");
			r.setCode(200);
			r.setData(obj);
		}
		return r;
	}

	/**
	 * 删除
	 */
	@PostMapping("/del")
	@ApiLog("删除-企业资料")
	@ApiOperation(value = "删除-企业资料", notes = "传入id", position = 5)
	public R del(@ApiParam(value = "id", required = true) @RequestParam String id) {
		R r = new R();
		int i=iSysClient.selectCountByparentId(id);
		//根据企业ID查询企业车辆数
		VehicleGDSTJ vehicleGDSTJ = iVehicleService.getVehidNumByDeptId(id);
		Object obj=new Object();
		if(i>0){
			r.setMsg("该组织下还存在下级，不能删除");
			r.setCode(500);
			r.setData("");
		}
		//判断企业车辆数是否大于0，若大于0，提示该企业不能进行删除
		else if(vehicleGDSTJ.getVehicleNum() > 0){
			r.setMsg("该组织还存在绑定车辆，不能删除");
			r.setCode(500);
			r.setData("");
		}else{
			boolean flag=iSysClient.removeById(id);
			//清楚企业基本信息
			organizationService.updateDel(organizationService.selectByDeptId(id).getId());
			obj=R.status(flag).getData();
			r.setMsg("删除成功");
			r.setCode(200);
			r.setData(obj);
		}
		return  r;
	}

/********************************** 配置表 ***********************/

	/**
	 * 根据单位id获取配置模块数据
	 */
	@GetMapping("/listMap")
	@ApiLog("获取配置-企业资料")
	@ApiOperation(value = "获取配置-企业资料", notes = "传入deptId", position = 6)
	public R<JSONArray> listMap(Integer deptId) {
		List<Configure> list1=mapService.selectMapList("anbiao_organization_map",deptId);
		String str="";
		for (int i = 0; i <list1.size() ; i++) {
			//转换成json数据并put id
			JSONObject jsonObject = JSONUtil.parseObj(list1.get(i).getBiaodancanshu());
			jsonObject.put("id",list1.get(i).getId());
			if(!str.equals("")){
				str=str+","+jsonObject.toString();
			}else{
				str=jsonObject.toString();
			}
		}
		str="["+str+"]";
		JSONArray json= JSONUtil.parseArray(str);
		return R.data(json);
	}

	/**
	 * 配置表新增
	 */
	@PostMapping("/insertMap")
	@ApiLog("配置表新增-企业资料")
	@ApiOperation(value = "配置表新增-企业资料", notes = "传入biaodancanshu与deptId", position = 7)
	public R insertMap(String biaodancanshu,String deptId) {
		Configure configure=new Configure();
		JSONObject jsonObject = JSONUtil.parseObj(biaodancanshu);
		configure.setLabel(jsonObject.getStr("label"));
		configure.setShujubiaoziduan(jsonObject.getStr("prop"));
		configure.setDeptId(Integer.parseInt(deptId));
		configure.setTableName("anbiao_organization_map");
		configure.setBiaodancanshu(biaodancanshu);
		return R.status(mapService.insertMap(configure));
	}
	/**
	 * 配置表编辑
	 */
	@PostMapping("/updateMap")
	@ApiLog("配置表编辑-企业资料")
	@ApiOperation(value = "配置表编辑-企业资料", notes = "传入biaodancanshu与id", position = 9)
	public R updateMap(String biaodancanshu,String id) {
		Configure configure=new Configure();
		JSONObject jsonObject = JSONUtil.parseObj(biaodancanshu);
		configure.setId(id);
		configure.setLabel(jsonObject.getStr("label"));
		configure.setShujubiaoziduan(jsonObject.getStr("prop"));
		configure.setTableName("anbiao_organization_map");
		configure.setBiaodancanshu(biaodancanshu);
		return R.status(mapService.updateMap(configure));
	}

	/**
	 * 配置表删除
	 */
	@PostMapping("/delMap")
	@ApiLog("配置表删除-企业资料")
	@ApiOperation(value = "配置表删除-企业资料", notes = "传入id", position = 8)
	public R delMap(@ApiParam(value = "主键id", required = true) @RequestParam String id) {
		return R.status(mapService.delMap("anbiao_organization_map",id));
	}


	/**
	 * 初始化全部模块配置
	 */
	@PostMapping("/insertBy")
	@ApiLog("初始化模块配置-企业资料")
	@ApiOperation(value = "初始化模块配置-企业资料", notes = "", position = 3)
	public R insertBy() {
		String msg="初始化失败";
		//获取当前系统所有存在的模板表名
		List<Configure>  MB=mapService.selectByName();
		for (int i = 0; i <MB.size() ; i++) {
			//进行拼接表名map表
			String tableName=MB.get(i).getTableName()+"_map";
			//获取系统设置默认模板的数据 单位id为2
			List<Configure> normal=	mapService.selectMapList(tableName,2);
			for (int j = 0; j < normal.size(); j++) {
				//获取系统所有企业
				List<Organizations> org=organizationService.selectAll();
				for (int k = 0; k <org.size() ; k++) {
					String deptId=org.get(k).getDeptId();
					String shujubiaoziduan=normal.get(j).getShujubiaoziduan();
					//不能清理默认数据 map表数据 默认数据为哈密众合。单位id2
					if(!deptId.equals("2")){
						//清楚对应模块的数据重新进行初始化
						mapService.delMapByDeptIdDel(tableName,deptId,shujubiaoziduan);
					}
					//清理后进行新增数据
					if(!org.get(k).getDeptId().equals("2")){
						Configure configure=new Configure();
						configure.setLabel(normal.get(j).getLabel());
						configure.setShujubiaoziduan(shujubiaoziduan);
						configure.setDeptId(Integer.parseInt(deptId));
						configure.setTableName(tableName);
						configure.setBiaodancanshu(normal.get(j).getBiaodancanshu());
						mapService.insertMap(configure);
					}
				}
			}
			msg="初始化成功";
		}
		return R.success(msg);
	}
	/**
	 * 初始化指定模块配置
	 */
	@PostMapping("/insertByTabel")
	@ApiLog("初始化指定模块配置-企业资料")
	@ApiOperation(value = "初始化指定模块配置-企业资料", notes = "传入需要初始化的模板表名", position = 3)
	public R insertByTabel(String tableName,String deptId) {
		String msg="初始化失败";
		//获取系统设置默认模板的数据 单位id为2
		List<Configure> normal=	mapService.selectMapList(tableName,Integer.parseInt(deptId));
		for (int j = 0; j < normal.size(); j++) {
			//获取系统所有企业
			List<Organizations> list=organizationService.selectAll();
			//给所有企业对应模板进行初始胡配置
			for (int k = 0; k <list.size() ; k++) {
				String deptIds=list.get(k).getDeptId();
				String shujubiaoziduan=normal.get(j).getShujubiaoziduan();
				//不能清理默认数据 map表数据
				if(!deptIds.equals("3")){
					//清楚对应模块的数据重新进行初始化
					mapService.delMapByDeptIdDel(tableName,deptIds,shujubiaoziduan);
				}
				if(!list.get(k).getDeptId().equals("3")){
					Configure configure=new Configure();
					configure.setLabel(normal.get(j).getLabel());
					configure.setShujubiaoziduan(normal.get(j).getShujubiaoziduan());
					configure.setDeptId(Integer.parseInt(list.get(k).getDeptId()));
					configure.setTableName(tableName);
					configure.setBiaodancanshu(normal.get(j).getBiaodancanshu());
					mapService.insertMap(configure);
				}
			}

			msg="初始化成功";
		}
		return R.success(msg);
	}
}
