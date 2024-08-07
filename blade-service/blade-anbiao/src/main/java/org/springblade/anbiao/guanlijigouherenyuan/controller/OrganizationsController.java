
package org.springblade.anbiao.guanlijigouherenyuan.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.cheliangguanli.entity.VehicleGDSTJ;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.configure.entity.Configure;
import org.springblade.anbiao.configure.service.IConfigureService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Departmentpost;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Personnel;
import org.springblade.anbiao.guanlijigouherenyuan.page.OrganizationsPage;
import org.springblade.anbiao.guanlijigouherenyuan.service.IDepartmentpostService;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.service.IPersonnelService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.common.tool.DateUtils;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Dict;
import org.springblade.system.entity.Post;
import org.springblade.system.feign.IDictClient;
import org.springblade.system.feign.ISysClient;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.feign.IUserFiegn;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	private IDictClient iDictClient;
	private IUserFiegn userClient;
	private IDepartmentpostService departmentpostService;
	private IPersonnelService personnelService;

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
		OrganizationsVO organization = organizationService.selectByDeptId(id);
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

	/**
	 * 机构档案信息--验证
	 */
	@PostMapping("/deptImport")
	@ApiLog("机构档案信息--验证")
	@ApiOperation(value = "机构档案信息--验证", notes = "file", position = 11)
	public R deptImport(@RequestParam(value = "file") MultipartFile file, BladeUser user) throws ParseException {
		R rs = new R();
		if (user == null) {
			rs.setCode(401);
			rs.setMsg("用户权限验证失败");
			rs.setData(null);
			rs.setSuccess(false);
			return rs;
		}
		ExcelReader reader = null;
		try {
			reader = ExcelUtil.getReader(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//时间默认格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//验证数据成功条数
		int aa = 0;
		//验证数据错误条数
		int bb = 0;
		//全局变量，只要一条数据不对，就为false
		boolean isDataValidity = true;
		//错误信息
		String errorStr = "";
		int c=0;

		List<Map<String, Object>> readAll = reader.readAll();
		if (readAll.size() > 2000) {
			errorStr += "导入数据超过2000条，无法导入！";
			rs.setMsg(errorStr);
			rs.setCode(400);
			return rs;
		}

		List<Organizations> organizations = new ArrayList<>();
		for (Map<String, Object> a : readAll) {
			aa++;
			Organizations organization = new Organizations();
			int deptids = iSysClient.selectMaxId()+1;
			organization.setDeptId(Integer.toString(deptids));
			//验证单位名称不能为空
			String deptName = String.valueOf(a.get("所属企业")).trim();
			if (StringUtils.isBlank(deptName) && !deptName.equals("null")) {
				organization.setMsg("所属企业不能为空;");
				organization.setImportUrl("icon_cha.png");
				errorStr += "所属企业不能为空;";
				bb++;
			} else {
				organization.setDeptName(deptName);
				int i = iSysClient.selectByName(organization.getDeptName());
				if(i > 1){
					organization.setMsg(deptName+"所属企业已存在;");
					organization.setImportUrl("icon_cha.png");
					errorStr += deptName+"所属企业已存在;";
					bb++;
				}
				organization.setImportUrl("icon_gou.png");
			}

			//验证职务名称不能为空
			String gangwei = String.valueOf(a.get("职务名称")).trim();
			if (StringUtils.isBlank(gangwei) && !gangwei.equals("null")) {
				organization.setMsg("职务名称不能为空;");
				organization.setImportUrl("icon_cha.png");
				errorStr += "职务名称不能为空;";
				bb++;
			} else {
				organization.setGangweimingcheng(gangwei);
				int num = organizationService.selectByName(organization.getGangweimingcheng(),organization.getDeptId());
				if(num < 1){
					organization.setImportUrl("icon_gou.png");
				}else{
					organization.setMsg(organization.getDeptName()+"该企业,"+organization.getGangweimingcheng()+"职务名称已存在;");
					organization.setImportUrl("icon_cha.png");
					errorStr += organization.getDeptName()+"该企业,"+organization.getGangweimingcheng()+"职务名称已存在;";
					bb++;
				}
			}

			//验证姓名不能为空
			String xingming = String.valueOf(a.get("姓名")).trim();
			if (StringUtils.isBlank(xingming) && !xingming.equals("null")) {
				organization.setMsg("姓名不能为空;");
				organization.setImportUrl("icon_cha.png");
				errorStr += "姓名不能为空;";
				bb++;
			} else {
				organization.setXingming(xingming);
				organization.setImportUrl("icon_gou.png");
			}

			//验证手机号码不能为空
			String shoujihaoma = String.valueOf(a.get("手机号码")).trim();
			if (StringUtils.isBlank(shoujihaoma) && !shoujihaoma.equals("null")) {
				organization.setMsg("手机号码不能为空;");
				organization.setImportUrl("icon_cha.png");
				errorStr += "手机号码不能为空;";
				bb++;
			} else {
				organization.setShoujihaoma(shoujihaoma);
				int i=userClient.selectByLoginName(organization.getShoujihaoma());
				if(i>0){
					organization.setMsg(shoujihaoma+"该账号已存在，请重新输入;");
					organization.setImportUrl("icon_cha.png");
					errorStr += shoujihaoma+"该账号已存在，请重新输入;";
					bb++;
				}else{
					organization.setImportUrl("icon_gou.png");
				}
			}

			//验证统一社会信用代码不能为空
			String jiGouBianMa = String.valueOf(a.get("统一社会信用代码")).trim();
			if (StringUtils.isBlank(jiGouBianMa) && !jiGouBianMa.equals("null")) {
				organization.setMsg("统一社会信用代码不能为空;");
				organization.setImportUrl("icon_cha.png");
				errorStr += "统一社会信用代码不能为空;";
				bb++;
			} else {
				organization.setJigoubianma(jiGouBianMa);
				organization.setImportUrl("icon_gou.png");
			}

			//验证工商营业执照开始时间
			String yyzzksdate = String.valueOf(a.get("统一社会信用代码有效日期始")).trim();
			if(StringUtils.isNotEmpty(yyzzksdate) && !yyzzksdate.equals("null")){
				if (yyzzksdate.length() >= 10){
					yyzzksdate=yyzzksdate.substring(0,10);
					if (StringUtils.isNotBlank(yyzzksdate) && !yyzzksdate.equals("null")) {
						if (DateUtils.isDateString(yyzzksdate, null) == true) {
							organization.setYyzzksdate(yyzzksdate);
							organization.setImportUrl("icon_gou.png");
						} else {
							organization.setMsg(yyzzksdate + ",该统一社会信用代码有效日期始,不是时间格式;");
							errorStr += yyzzksdate + ",该统一社会信用代码有效日期始,不是时间格式;";
							organization.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				}else {
					organization.setMsg(yyzzksdate + ",该统一社会信用代码有效日期始,不是时间格式;");
					errorStr += yyzzksdate + ",该统一社会信用代码有效日期始,不是时间格式;";
					organization.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证工商营业执照结束时间
			String yyzzjzdate = String.valueOf(a.get("统一社会信用代码有效日期止")).trim();
			if(StringUtils.isNotEmpty(yyzzjzdate) && !yyzzjzdate.equals("null")) {
				if (yyzzjzdate.length() >= 10 && !yyzzjzdate.equals("长期")) {
					yyzzjzdate = yyzzjzdate.substring(0, 10);
					if (StringUtils.isNotBlank(yyzzjzdate) && !yyzzjzdate.equals("null")) {
						if (DateUtils.isDateString(yyzzjzdate, null) == true) {
							organization.setYyzzjzdate(yyzzjzdate);
							organization.setImportUrl("icon_gou.png");
						} else {
							organization.setMsg(yyzzjzdate + ",该统一社会信用代码有效日期止,不是时间格式;");
							errorStr += yyzzjzdate + ",该统一社会信用代码有效日期止,不是时间格式;";
							organization.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else if (yyzzjzdate.equals("长期")) {
					organization.setYyzzjzdate(yyzzjzdate);
					organization.setImportUrl("icon_gou.png");
				} else {
					organization.setMsg(yyzzjzdate + ",该统一社会信用代码有效日期止,不是时间格式;");
					errorStr += yyzzjzdate + ",该统一社会信用代码有效日期止,不是时间格式;";
					organization.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证 工商营业执照开始时间 不能大于 工商营业执照结束时间
			if (StringUtils.isNotBlank(yyzzksdate) && !yyzzksdate.equals("null") && StringUtils.isNotBlank(yyzzjzdate) && !yyzzjzdate.equals("null") && !yyzzjzdate.equals("长期")) {
				int a1 = yyzzksdate.length();
				int b1 = yyzzjzdate.length();
				if (a1 == b1) {
					if (a1 <= 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if (DateUtils.belongCalendar(format.parse(yyzzksdate), format.parse(yyzzjzdate))) {
							organization.setImportUrl("icon_gou.png");
						} else {
							organization.setMsg("统一社会信用代码有效日期始时间,不能大于统一社会信用代码有效日期止时间;");
							errorStr += "统一社会信用代码有效日期始时间,不能大于统一社会信用代码有效日期止时间;";
							organization.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if (a1 > 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (DateUtils.belongCalendar(format.parse(yyzzksdate), format.parse(yyzzjzdate))) {
							organization.setImportUrl("icon_gou.png");
						} else {
							organization.setMsg("统一社会信用代码有效日期始时间,不能大于统一社会信用代码有效日期止时间;");
							errorStr += "统一社会信用代码有效日期始时间,不能大于统一社会信用代码有效日期止时间;";
							organization.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					organization.setMsg("统一社会信用代码有效日期始时间与统一社会信用代码有效日期止时间,时间格式不一致;");
					errorStr += "统一社会信用代码有效日期始时间与统一社会信用代码有效日期止时间,时间格式不一致;";
					organization.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证机构类型
			String jigouleixing = String.valueOf(a.get("机构类型")).trim();
			if (StringUtils.isBlank(jigouleixing) || jigouleixing.equals("null")) {
				organization.setImportUrl("icon_gou.png");
				organization.setJigouleixing("qiye");
			} else {
				boolean ss = false;
				List<Dict> dictVOList = iDictClient.getDictByCode("jigouleixing",null);
				for(int i= 0;i<dictVOList.size();i++){
					ss = dictVOList.get(i).getDictValue().equals(jigouleixing);
					if(ss == true){
						break;
					}
				}
				if(ss == true){
					dictVOList = iDictClient.getDictByCode("jigouleixing",jigouleixing);
					organization.setImportUrl("icon_gou.png");
					organization.setJigouleixing(dictVOList.get(0).getDictKey());
				}else{
					organization.setImportUrl("icon_cha.png");
					errorStr += jigouleixing+",机构类型错误,请校验”;";
					organization.setMsg(jigouleixing+",机构类型错误,请校验;");
					bb++;
				}
			}

			//验证道路运输许可证号
			String daoluxukezhenghao = String.valueOf(a.get("道路运输许可证号")).trim();
			if (StringUtils.isBlank(daoluxukezhenghao) || daoluxukezhenghao.equals("null")) {
			} else {
				organization.setDaoluxukezhenghao(daoluxukezhenghao);
				organization.setImportUrl("icon_gou.png");
			}

			//验证道路运输证有效期(起)
			String daoluyunshuzhengkaishiriqi = String.valueOf(a.get("道路运输许可证有效日期始")).trim();
			if(StringUtils.isNotEmpty(daoluyunshuzhengkaishiriqi) && !daoluyunshuzhengkaishiriqi.equals("null")) {
				if (daoluyunshuzhengkaishiriqi.length() >= 10) {
					daoluyunshuzhengkaishiriqi = daoluyunshuzhengkaishiriqi.substring(0, 10);
					if (StringUtils.isNotBlank(daoluyunshuzhengkaishiriqi) && !daoluyunshuzhengkaishiriqi.equals("null")) {
						if (DateUtils.isDateString(daoluyunshuzhengkaishiriqi, null) == true) {
							organization.setDaoluyunshuzhengkaishiriqi(daoluyunshuzhengkaishiriqi);
							organization.setImportUrl("icon_gou.png");
						} else {
							organization.setMsg(daoluyunshuzhengkaishiriqi + ",该道路运输许可证有效日期始,不是时间格式;");
							errorStr += daoluyunshuzhengkaishiriqi + ",该道路运输许可证有效日期始,不是时间格式;";
							organization.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					organization.setMsg(daoluyunshuzhengkaishiriqi + ",该道路运输许可证有效日期始,不是时间格式;");
					errorStr += daoluyunshuzhengkaishiriqi + ",该道路运输许可证有效日期始,不是时间格式;";
					organization.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证道路运输证有效期(止)
			String daoluyunshuzhengjieshuriqi = String.valueOf(a.get("道路运输许可证有效期（止）")).trim();
			if(StringUtils.isNotEmpty(daoluyunshuzhengjieshuriqi) && !daoluyunshuzhengjieshuriqi.equals("null")) {
				if (daoluyunshuzhengjieshuriqi.length() >= 10) {
					daoluyunshuzhengjieshuriqi = daoluyunshuzhengjieshuriqi.substring(0, 10);
					if (StringUtils.isNotBlank(daoluyunshuzhengjieshuriqi) && !daoluyunshuzhengjieshuriqi.equals("null")) {
						if (DateUtils.isDateString(daoluyunshuzhengjieshuriqi, null) == true) {
							organization.setDaoluyunshuzhengjieshuriqi(daoluyunshuzhengjieshuriqi);
							organization.setImportUrl("icon_gou.png");
						} else {
							organization.setMsg(daoluyunshuzhengjieshuriqi + ",该道路运输许可证有效日期止,不是时间格式;");
							errorStr += daoluyunshuzhengjieshuriqi + ",该道路运输许可证有效日期止,不是时间格式;";
							organization.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					organization.setMsg(daoluyunshuzhengjieshuriqi + ",该道路运输许可证有效日期止,不是时间格式;");
					errorStr += daoluyunshuzhengjieshuriqi + ",该道路运输许可证有效日期止,不是时间格式;";
					organization.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证 道路运输许可证有效日期始 不能大于 道路运输许可证有效日期止
			if (StringUtils.isNotBlank(daoluyunshuzhengkaishiriqi) && !daoluyunshuzhengkaishiriqi.equals("null") && StringUtils.isNotBlank(daoluyunshuzhengjieshuriqi) && !daoluyunshuzhengjieshuriqi.equals("null")) {
				int a1 = daoluyunshuzhengkaishiriqi.length();
				int b1 = daoluyunshuzhengjieshuriqi.length();
				if (a1 == b1) {
					if (a1 <= 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if (DateUtils.belongCalendar(format.parse(daoluyunshuzhengkaishiriqi), format.parse(daoluyunshuzhengjieshuriqi))) {
							organization.setImportUrl("icon_gou.png");
						} else {
							organization.setMsg("道路运输许可证有效日期始,不能大于道路运输许可证有效日期止;");
							errorStr += "道路运输许可证有效日期始,不能大于道路运输许可证有效日期止;";
							organization.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if (a1 > 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (DateUtils.belongCalendar(format.parse(daoluyunshuzhengkaishiriqi), format.parse(daoluyunshuzhengjieshuriqi))) {
							organization.setImportUrl("icon_gou.png");
						} else {
							organization.setMsg("道路运输许可证有效日期始,不能大于道路运输许可证有效日期止;");
							errorStr += "道路运输许可证有效日期始,不能大于道路运输许可证有效日期止;";
							organization.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					organization.setMsg("道路运输许可证有效日期始与道路运输许可证有效日期止,时间格式不一致;");
					errorStr += "道路运输许可证有效日期始与道路运输许可证有效日期止,时间格式不一致;";
					organization.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证经营许可证号
			String jingyingxukezhenghao = String.valueOf(a.get("经营许可证号")).trim();
			if (StringUtils.isNotBlank(jingyingxukezhenghao) && !jingyingxukezhenghao.equals("null")) {
				organization.setJingyingxukezhengbianma(jingyingxukezhenghao);
			}

			//验证经营许可证有效期(起)
			String jingyingxukezhengkaishiriqi = String.valueOf(a.get("经营许可证有效日期始")).trim();
			if(StringUtils.isNotEmpty(jingyingxukezhengkaishiriqi) && !jingyingxukezhengkaishiriqi.equals("null")) {
				if (jingyingxukezhengkaishiriqi.length() >= 10) {
					jingyingxukezhengkaishiriqi = jingyingxukezhengkaishiriqi.substring(0, 10);
					if (StringUtils.isNotBlank(jingyingxukezhengkaishiriqi) && !jingyingxukezhengkaishiriqi.equals("null")) {
						if (DateUtils.isDateString(jingyingxukezhengkaishiriqi, null) == true) {
							organization.setJingyingxukezhengchulingriqi(jingyingxukezhengkaishiriqi);
							organization.setImportUrl("icon_gou.png");
						} else {
							organization.setMsg(jingyingxukezhengkaishiriqi + ",该经营许可证有效日期始,不是时间格式;");
							errorStr += jingyingxukezhengkaishiriqi + ",该经营许可证有效日期始,不是时间格式;";
							organization.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					organization.setMsg(jingyingxukezhengkaishiriqi + ",该经营许可证有效日期始,不是时间格式;");
					errorStr += jingyingxukezhengkaishiriqi + ",该经营许可证有效日期始,不是时间格式;";
					organization.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证经营许可证有效期(止)
			String jingyingxukezhengjieshuriqi = String.valueOf(a.get("经营许可证有效日期止")).trim();
			if(StringUtils.isNotEmpty(jingyingxukezhengjieshuriqi) && !jingyingxukezhengjieshuriqi.equals("null")) {
				if (jingyingxukezhengjieshuriqi.length() >= 10) {
					jingyingxukezhengjieshuriqi = jingyingxukezhengjieshuriqi.substring(0, 10);
					if (StringUtils.isNotBlank(jingyingxukezhengjieshuriqi) && !jingyingxukezhengjieshuriqi.equals("null")) {
						if (DateUtils.isDateString(jingyingxukezhengjieshuriqi, null) == true) {
							organization.setJingyingxukezhengyouxiaoqi(jingyingxukezhengjieshuriqi);
							organization.setImportUrl("icon_gou.png");
						} else {
							organization.setMsg(jingyingxukezhengjieshuriqi + ",该经营许可证有效日期止,不是时间格式;");
							errorStr += jingyingxukezhengjieshuriqi + ",该经营许可证有效日期止,不是时间格式;";
							organization.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					organization.setMsg(jingyingxukezhengjieshuriqi + ",该经营许可证有效日期止,不是时间格式;");
					errorStr += jingyingxukezhengjieshuriqi + ",该经营许可证有效日期止,不是时间格式;";
					organization.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证 经营许可证有效期(起) 不能大于 经营许可证有效期(止)
			if (StringUtils.isNotBlank(jingyingxukezhengkaishiriqi) && !jingyingxukezhengkaishiriqi.equals("null") && StringUtils.isNotBlank(jingyingxukezhengjieshuriqi) && !jingyingxukezhengjieshuriqi.equals("null")) {
				int a1 = jingyingxukezhengkaishiriqi.length();
				int b1 = jingyingxukezhengjieshuriqi.length();
				if (a1 == b1) {
					if (a1 <= 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if (DateUtils.belongCalendar(format.parse(jingyingxukezhengkaishiriqi), format.parse(jingyingxukezhengjieshuriqi))) {
							organization.setImportUrl("icon_gou.png");
						} else {
							organization.setMsg("经营许可证有效日期始,不能大于经营许可证有效日期止;");
							errorStr += "经营许可证有效日期始,不能大于经营许可证有效日期止;";
							organization.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if (a1 > 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (DateUtils.belongCalendar(format.parse(jingyingxukezhengkaishiriqi), format.parse(jingyingxukezhengjieshuriqi))) {
							organization.setImportUrl("icon_gou.png");
						} else {
							organization.setMsg("经营许可证有效日期始,不能大于经营许可证有效日期止;");
							errorStr += "经营许可证有效日期始,不能大于经营许可证有效日期止;";
							organization.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					organization.setMsg("经营许可证有效日期始与经营许可证有效日期止,时间格式不一致;");
					errorStr += "经营许可证有效日期始与经营许可证有效日期止,时间格式不一致;";
					organization.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证Excel导入时，是否存在重复数据
			for (Organizations item : organizations) {
				if (item.getDeptName().equals(deptName) && item.getJigoubianma().equals(jiGouBianMa) && item.getDaoluxukezhenghao().equals(daoluxukezhenghao)) {
					organization.setImportUrl("icon_cha.png");
					errorStr += organizations + "机构信息重复；";
					organization.setMsg(organizations + "机构信息重复；");
					bb++;
				}
			}
			organizations.add(organization);
		}

		if (bb > 0) {
			rs.setMsg(errorStr);
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setData(organizations);
			return rs;
		} else {
			rs.setCode(200);
			rs.setMsg("数据验证成功");
			rs.setData(organizations);
			rs.setSuccess(true);
			return rs;
		}
	}

	/**
	 * 机构档案信息--确认导入
	 *
	 * @param
	 */
	@PostMapping("/deptImportOK")
	@ApiLog("机构档案信息--确认导入")
	@ApiOperation(value = "机构档案信息--确认导入", notes = "organizations", position = 10)
	public R driverDeptImportOk(@RequestParam(value = "organizations") String organizations,BladeUser user) {
		JSONArray json = JSONUtil.parseArray(organizations);
		List<Map<String, Object>> lists = (List) json;
		R rs = new R();
		if (user == null) {
			rs.setCode(401);
			rs.setMsg("用户权限验证失败");
			rs.setData(null);
			rs.setSuccess(false);
			return rs;
		}
		//时间默认格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//验证数据成功条数
		int aa = 0;
		//验证数据错误条数
		int bb = 0;
		//全局变量，只要一条数据不对，就为false
		boolean isDataValidity = true;
		//错误信息
		String errorStr = "";

		if (lists.size() > 2000) {
			errorStr += "导入数据超过2000条，无法导入！";
			rs.setMsg(errorStr);
			rs.setCode(400);
			return rs;
		}

		for (Map<String, Object> a : lists) {
			aa++;
			Organizations organization = new Organizations();
			Dept dept = new Dept();
			organization.setDeptId(String.valueOf(a.get("deptId")).trim());
			organization.setDeptName(String.valueOf(a.get("deptName")).trim());
			if (String.valueOf(a.get("parentId")).trim() == null || String.valueOf(a.get("parentId")).trim().equals("") || String.valueOf(a.get("parentId")).trim().equals("null")) {
				organization.setParentId("1");
			}
			String treeCode = iSysClient.selectByTreeCode(organization.getParentId()).getTreeCode();
			dept.setTreeCode(treeCode);
			dept.setId(iSysClient.selectMaxId() + 1);
			dept.setExtendType("机构");
			dept.setParentId(Integer.parseInt(organization.getParentId()));
			dept.setDeptName(organization.getDeptName());
			dept.setFullName(organization.getDeptName());
			isDataValidity = iSysClient.insertDept(dept);
			organization.setDeptId(dept.getId().toString());
			organization.setJigoubianma(String.valueOf(a.get("jigoubianma")).trim());
			organization.setYyzzksdate(String.valueOf(a.get("yyzzksdate")).trim());
			organization.setYyzzjzdate(String.valueOf(a.get("yyzzjzdate")).trim());
			organization.setJigouleixing(String.valueOf(a.get("jigouleixing")).trim());
			organization.setDaoluxukezhenghao(String.valueOf(a.get("daoluxukezhenghao")).trim());
			organization.setDaoluyunshuzhengkaishiriqi(String.valueOf(a.get("daoluyunshuzhengkaishiriqi")).trim());
			organization.setDaoluyunshuzhengjieshuriqi(String.valueOf(a.get("daoluyunshuzhengjieshuriqi")).trim());
			organization.setJingyingxukezhengbianma(String.valueOf(a.get("jingyingxukezhengbianma")).trim());
			organization.setJingyingxukezhengchulingriqi(String.valueOf(a.get("jingyingxukezhengchulingriqi")).trim());
			organization.setJingyingxukezhengyouxiaoqi(String.valueOf(a.get("jingyingxukezhengyouxiaoqi")).trim());
			organization.setGangweimingcheng(String.valueOf(a.get("gangweimingcheng")).trim());
			organization.setXingming(String.valueOf(a.get("xingming")).trim());
			organization.setShoujihaoma(String.valueOf(a.get("shoujihaoma")).trim());

			organization.setCreatetime(DateUtil.now());
			organization.setCaozuoshijian(DateUtil.now());
			if (user != null) {
				organization.setCaozuoren(user.getUserName());
				organization.setCaozuorenid(user.getUserId());
			}
			Organizations organizationsInfo = organizationService.getorganizationByOne(organization.getDeptId(), organization.getDeptName(), organization.getJigoubianma(), organization.getJigouleixing(), organization.getDaoluxukezhenghao());
			if (organizationsInfo != null) {
				organization.setId(organizationsInfo.getId());
				isDataValidity = organizationService.updateById(organization);
			} else {
//				String id = IdUtil.simpleUUID();
//				organization.setId(id);
				isDataValidity = organizationService.insertOrganizationsSelective(organization);
			}
			//添加岗位
			Departmentpost departmentpost = new Departmentpost();
			String type = "岗位";
			departmentpost.setParentId(dept.getId().toString());
			//执行机构表新增
			treeCode = iSysClient.selectByTreeCode(departmentpost.getParentId()).getTreeCode();
			dept.setTreeCode(treeCode);
			dept.setId(iSysClient.selectMaxId() + 1);
			dept.setExtendType(type);
			dept.setDeptName(organization.getGangweimingcheng());
			dept.setFullName(organization.getGangweimingcheng());
			dept.setParentId(Integer.parseInt(departmentpost.getParentId()));
			isDataValidity = iSysClient.insertDept(dept);
			departmentpost.setMingcheng(organization.getGangweimingcheng());
			departmentpost.setCaozuoren(user.getUserName());
			departmentpost.setCaozuorenid(user.getUserId());
			departmentpost.setCaozuoshijian(DateUtil.now());
			departmentpost.setCreatetime(DateUtil.now());
			departmentpost.setDeptId(dept.getId());
			departmentpost.setType(departmentpost.getExtendType());
			departmentpost.setExtendType(type);
			departmentpost.setLeixing(type);
			departmentpost.setType(type);
			if (type.equals(departmentpost.getExtendType())) {
				//新增岗位时默认给岗位赋权
				//默认给企业端赋权
				String menuId = "288,289,291,296,295,292,299";
				iSysClient.ABgrant(dept.getId() + "", menuId, 1);
			}
			if (isDataValidity == true) {
				departmentpostService.insertSelective(departmentpost);
			}

			//添加用户信息
			User userInfo = new User();
			Personnel personnel = new Personnel();
			personnel.setPostId(departmentpost.getDeptId().toString());
			personnel.setPostName(departmentpost.getMingcheng());
			personnel.setAccount(organization.getShoujihaoma());
			personnel.setXingming(organization.getXingming());
			//获取当前最大id
			int userId = userClient.selectMaxId() + 1;
			//岗位id
			String postId = personnel.getPostId();
			Post post = new Post();
			post.setUserId(userId);
			post.setPostId(Integer.parseInt(postId));
			//直属上级id
			dept = iSysClient.selectByJGBM("机构", postId);
			userInfo.setDeptId(dept.getId().toString());
			userInfo.setId(userId);
			userInfo.setRoleId("2");
			userInfo.setAccount(personnel.getAccount());
			userInfo.setName(personnel.getXingming());
			userInfo.setRealName(personnel.getXingming());
			userInfo.setPhone(organization.getShoujihaoma());
			userInfo.setPassword(DigestUtil.encrypt(userInfo.getPhone().substring(userInfo.getPhone().length() - 6)));
			//获取时间
			java.util.Date date = new java.util.Date();
			Instant instant = date.toInstant();
			ZoneId zone = ZoneId.systemDefault();
			LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
			if (user == null) {
				userInfo.setCreateUser(1);
				userInfo.setUpdateUser(1);
			} else {
				userInfo.setCreateUser(user.getUserId());
				userInfo.setUpdateUser(user.getUserId());
			}
			userInfo.setCreateTime(localDateTime);
			userInfo.setUpdateTime(localDateTime);
			userInfo.setBirthday(personnel.getChushengriqi());
			userInfo.setEmail(personnel.getYouxiang());
			//业务状态默认为1
			userInfo.setStatus(1);
			int i = userClient.selectByLoginName(personnel.getAccount());
			if (i > 0) {
				String msg = "该登录账号已存在，请重新输入";
				rs.setCode(500);
				rs.setMsg(msg);
				return rs;
			} else {
				//执行插入人员基本数据
				boolean flag = userClient.insertPer(userInfo);
				//人员基本信息表存储
				personnel.setUserid(userId);
				if (user == null) {
					personnel.setCaozuoren("管理员");
					personnel.setCaozuorenid(1);
				} else {
					personnel.setCaozuoren(user.getUserName());
					personnel.setCaozuorenid(user.getUserId());
				}
				personnel.setCaozuoshijian(DateUtil.now());
				personnel.setCreatetime(DateUtil.now());
				personnel.setDeptId(dept.getId());
				personnel.setPostId(personnel.getPostId());
				personnel.setIsDeleted(0);
				personnel.setShoujihao(userInfo.getPhone());
				if (StringUtil.isNotBlank(personnel.getFujian())) {
					fileUploadClient.updateCorrelation(personnel.getFujian(), "1");
				}
				flag = personnelService.insertSelective(personnel);
				if (flag == true) {
					//执行插入人员-岗位数据
					int ii = personnelService.selectByPost(userId + "");
					if (ii == 0) {
						//第一次新增的人员岗位设置为默认岗位
						post.setIsdefault(1);
					}
					iSysClient.insertPost(post);
				}
			}
			if (isDataValidity == true) {
				rs.setCode(200);
				rs.setMsg("数据导入成功");
				rs.setSuccess(true);
				rs.setData(organizations);
				return rs;
			} else {
				rs.setCode(500);
				rs.setMsg("数据导入失败");
				rs.setData(organizations);
				return rs;
			}
		}
		return rs;
	}
}
