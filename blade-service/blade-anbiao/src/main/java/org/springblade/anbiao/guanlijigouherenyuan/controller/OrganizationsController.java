
package org.springblade.anbiao.guanlijigouherenyuan.controller;

import cn.afterturn.easypoi.word.entity.WordImageEntity;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiDetail;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.service.*;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfo;
import org.springblade.anbiao.configure.entity.Configure;
import org.springblade.anbiao.configure.service.IConfigureService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.*;
import org.springblade.anbiao.guanlijigouherenyuan.page.OrganizationsPage;
import org.springblade.anbiao.guanlijigouherenyuan.service.IBladeDeptService;
import org.springblade.anbiao.guanlijigouherenyuan.service.IDepartmentpostService;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.service.IPersonnelService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.jiashiyuan.service.*;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.ApacheZipUtils;
import org.springblade.common.tool.DateUtils;
import org.springblade.common.tool.WordUtil2;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.entity.AnbiaoJiashiyuan;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
	private FileServer fileServer;
	private IBladeDeptService iBladeDeptService;
	private IAnbiaoJiashiyuanRuzhiService ruzhiService;
	private IAnbiaoJiashiyuanJiashizhengService jiashizhengService;
	private IAnbiaoJiashiyuanCongyezigezhengService congyezigezhengService;
	private IAnbiaoJiashiyuanTijianService tijianService;
	private IAnbiaoJiashiyuanGangqianpeixunService gangqianpeixunService;
	private IAnbiaoJiashiyuanWuzezhengmingService wuzezhengmingService;
	private IAnbiaoJiashiyuanQitaService qitaService;
	private IJiaShiYuanService jiaShiYuanService;
	private IVehicleDaoluyunshuzhengService daoluyunshuzhengService;
	private IVehicleXingshizhengService xingshizhengService;
	private IVehicleXingnengbaogaoService xingnengbaogaoService;
	private IVehicleDengjizhengshuService dengjizhengshuService;

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
		organization.setDeptName(organization.getDeptName().trim());
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
			organization.setDeptId(dept.getId().toString());
			OrganizationsVO organizations = organizationService.selectByDeptId(dept.getId().toString());
			if(organizations == null){
				boolean flag=iSysClient.insertDept(dept);
				organizationService.insertOrganizationsSelective(organization);
				if(flag==true){
					//添加岗位
					if(organization.getJigouleixing().equals("qiye") || organization.getJigouleixing().equals("geti")) {
						String[] myArray = {"主要负责人", "安全管理员", "车队长", "调度经理", "财务主任", "动态监控经理"};
						for (String strs : myArray) {
							System.out.println(strs);
							Departmentpost departmentpost = new Departmentpost();
							String type = "岗位";
							departmentpost.setParentId(dept.getId().toString());
							//执行机构表新增
							treeCode = iSysClient.selectByTreeCode(departmentpost.getParentId()).getTreeCode();
							dept.setTreeCode(treeCode);
							dept.setId(iSysClient.selectMaxId() + 1);
							dept.setExtendType(type);
							dept.setDeptName(strs);
							dept.setFullName(strs);
							dept.setParentId(Integer.parseInt(organization.getDeptId()));
							QueryWrapper<Dept> deptQueryWrapper = new QueryWrapper<Dept>();
							deptQueryWrapper.lambda().eq(Dept::getDeptName, strs);
							deptQueryWrapper.lambda().eq(Dept::getParentId, organization.getDeptId());
							deptQueryWrapper.lambda().eq(Dept::getIsDeleted, 0);
							Dept deail = iBladeDeptService.getBaseMapper().selectOne(deptQueryWrapper);
							if (deail == null) {
								flag = iSysClient.insertDept(dept);
								departmentpost.setMingcheng(strs);
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
								if (flag == true) {
									departmentpostService.insertSelective(departmentpost);
								}
							}
						}
						if (flag == true) {
							obj = iSysClient.selectById(dept.getId().toString());
						}
					}
				}
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
		organization.setDeptName(organization.getDeptName().trim());
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
			String sjdeptName = String.valueOf(a.get("上级企业名称")).trim();
			if (StringUtils.isNotBlank(sjdeptName) && !sjdeptName.equals("null")) {
				int i = iSysClient.selectByName(sjdeptName);
				if(i > 0){
					organization.setSjdeptName(sjdeptName);
					Dept dept = iSysClient.getDeptByName(sjdeptName);
					organization.setParentId(dept.getId().toString());
					organization.setImportUrl("icon_gou.png");
				}else{
					organization.setMsg(sjdeptName+"上级企业名称不存在;");
					organization.setImportUrl("icon_cha.png");
					errorStr += sjdeptName+"上级企业名称不存在;";
					bb++;
				}
			}

			//验证单位名称不能为空
			String deptName = String.valueOf(a.get("所属企业")).trim();
			if (StringUtils.isBlank(deptName) || deptName.equals("null")) {
				organization.setMsg("所属企业不能为空;");
				organization.setImportUrl("icon_cha.png");
				errorStr += "所属企业不能为空;";
				bb++;
			} else {
				organization.setDeptName(deptName);
				int i = iSysClient.selectByName(organization.getDeptName());
				if(i > 0){
					organization.setMsg(deptName+"所属企业已存在;");
					organization.setImportUrl("icon_cha.png");
					errorStr += deptName+"所属企业已存在;";
					bb++;
				}
				organization.setImportUrl("icon_gou.png");
			}

			//验证职务名称不能为空
			String gangwei = String.valueOf(a.get("职务名称")).trim();
			if (StringUtils.isBlank(gangwei) || gangwei.equals("null")) {
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
			if (StringUtils.isBlank(xingming) || xingming.equals("null")) {
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
			if (StringUtils.isBlank(shoujihaoma) || shoujihaoma.equals("null")) {
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
			if (StringUtils.isBlank(jiGouBianMa) || jiGouBianMa.equals("null")) {
//				organization.setMsg("统一社会信用代码不能为空;");
//				organization.setImportUrl("icon_cha.png");
//				errorStr += "统一社会信用代码不能为空;";
//				bb++;
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
				if (item.getDeptName().equals(deptName)  && item.getGangweimingcheng().equals(gangwei) && item.getXingming().equals(xingming) && item.getShoujihaoma().equals(shoujihaoma)) {
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
			if (String.valueOf(a.get("parentId")).trim() == null || String.valueOf(a.get("parentId")).trim().equals("") || String.valueOf(a.get("parentId")).trim().equals("null")) {
				organization.setParentId("1");
			}else{
				organization.setParentId(String.valueOf(a.get("parentId")).trim());
			}
			String treeCode = iSysClient.selectByTreeCode(organization.getParentId()).getTreeCode();
			organization.setDeptId(String.valueOf(a.get("deptId")).trim());
			organization.setDeptName(String.valueOf(a.get("deptName")).trim());
			int q = iSysClient.selectByName(organization.getDeptName());
			if(q > 0){
				Organizations organizationsInfo = organizationService.getorganizationByOne(null, organization.getDeptName(),null, organization.getJigouleixing(),null);
				dept.setTreeCode(treeCode);
				dept.setId(Integer.parseInt(organizationsInfo.getDeptId()));
				dept.setExtendType("机构");
				dept.setParentId(Integer.parseInt(organization.getParentId()));
				dept.setDeptName(organization.getDeptName());
				dept.setFullName(organization.getDeptName());
				organization.setDeptId(organizationsInfo.getDeptId());
			}else{

				dept.setTreeCode(treeCode);
				dept.setId(iSysClient.selectMaxId() + 1);
				dept.setExtendType("机构");
				dept.setParentId(Integer.parseInt(organization.getParentId()));
				dept.setDeptName(organization.getDeptName());
				dept.setFullName(organization.getDeptName());
				isDataValidity = iSysClient.insertDept(dept);
				organization.setDeptId(dept.getId().toString());
			}
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
			Organizations organizationsInfo = organizationService.getorganizationByOne(organization.getDeptId(), organization.getDeptName(),null, organization.getJigouleixing(),null);
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
			QueryWrapper<Dept> deptQueryWrapper = new QueryWrapper<Dept>();
			deptQueryWrapper.lambda().eq(Dept::getDeptName, organization.getGangweimingcheng());
			deptQueryWrapper.lambda().eq(Dept::getParentId, organization.getDeptId());
			deptQueryWrapper.lambda().eq(Dept::getIsDeleted, 0);
			Dept deail = iBladeDeptService.getBaseMapper().selectOne(deptQueryWrapper);
			if(deail == null){
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
			}else{
				QueryWrapper<Departmentpost> departmentpostQueryWrapper = new QueryWrapper<Departmentpost>();
				departmentpostQueryWrapper.lambda().eq(Departmentpost::getDeptId, deail.getId());
				departmentpostQueryWrapper.lambda().eq(Departmentpost::getMingcheng, organization.getGangweimingcheng());
				departmentpostQueryWrapper.lambda().eq(Departmentpost::getIsDeleted, 0);
				departmentpost = departmentpostService.getBaseMapper().selectOne(departmentpostQueryWrapper);
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
				personnel.setDeptId(dept.getId().toString());
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

	@GetMapping("/getDeptImg")
	@ApiLog("企业-影像资料数据统计")
	@ApiOperation(value = "企业-影像资料数据统计", notes = "传入deptId", position = 31)
	public R<OrganizationsFuJian> getDeptImg(String deptId) {
		R rs = new R();
		OrganizationsFuJian or = organizationService.selectByDeptImg(deptId);
		if(or != null){
			rs.setCode(200);
			rs.setData(or);
			rs.setMsg("获取成功");
		}else{
			rs.setCode(200);
			rs.setData(null);
			rs.setMsg("获取成功,暂无数据");
		}
		return rs;
	}

	@PostMapping("/uploadInsert")
	@ApiLog("企业-影像资料数据-导入")
	@ApiOperation(value = "企业-影像资料数据-导入", notes = "传入OrganizationsFuJian")
	public R uploadInsert(@RequestBody OrganizationsFuJian organizationsFuJian, BladeUser user) throws ParseException {
		R r = new R();
		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}

		Organizations organization = new Organizations();
		organization.setCaozuoshijian(DateUtil.now());
		organization.setId(organizationsFuJian.getId());
		organization.setDaoluyunshuzhengfujian(organizationsFuJian.getDaoluyunshuzhengfujian());
		organization.setJingyingxukezhengfujian(organizationsFuJian.getJingyingxukezhengfujian());
		organization.setYingyezhizhaofujian(organizationsFuJian.getYingyezhizhaofujian());
		boolean ii = organizationService.updateById(organization);
		if (ii){
			List<OrganizationsFuJian> organizationsFuJianList = organizationsFuJian.getPersonnelFuJianList();
			for (int j = 0; j <= organizationsFuJianList.size()-1; j++) {
				Personnel personnel = new Personnel();
				personnel.setCaozuoshijian(DateUtil.now());
				personnel.setId(organizationsFuJianList.get(j).getPersonId());
				if (user == null) {
					personnel.setCaozuoren("admin");
					personnel.setCaozuorenid(1);
				} else {
					personnel.setCaozuoren(user.getUserName());
					personnel.setCaozuorenid(user.getUserId());
				}
				personnel.setQitafanmianfujian(organizationsFuJianList.get(j).getQitafanmianfujian());
				personnel.setQitazhengmianfujian(organizationsFuJianList.get(j).getQitazhengmianfujian());
				personnel.setShenfenzhengfanmianfujian(organizationsFuJianList.get(j).getShenfenzhengfanmianfujian());
				personnel.setShenfenzhengfujian(organizationsFuJianList.get(j).getShenfenzhengfujian());
				ii = personnelService.updateSelective(personnel);
				if (ii) {
					r.setMsg("导入成功");
					r.setCode(200);
					r.setSuccess(false);
				} else {
					r.setMsg("导入失败");
					r.setCode(500);
					r.setSuccess(false);
				}
			}
		}else {
				r.setMsg("导入失败");
				r.setCode(500);
				r.setSuccess(false);
		}
		return r;
	}

	@GetMapping("/exportDataWord")
	@ApiLog("企业-影像资料数据-导出")
	@ApiOperation(value = "企业-影像资料数据-导出", notes = "传入企业ID", position = 29)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "企业ID（多个以英文逗号隔开）", required = true),
	})
	public R exportDataWord(HttpServletRequest request, HttpServletResponse response, String deptId, BladeUser user) throws IOException{
		R r = new R();
		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		Map<String, Object> params = new HashMap<>();
		String temDir = "";
		String fileName = "";
		String folder = "";
		String [] nyr=DateUtil.today().split("-");
		List<String> urlList = new ArrayList<>();
		try {
			// TODO 渲染其他类型的数据请参考官方文档
			DecimalFormat df = new DecimalFormat("######0.00");
			Calendar now = Calendar.getInstance();
			String[] idsss = deptId.split(",");
			//去除素组中重复的数组
			List<String> listid = new ArrayList<String>();
			for (int i=0; i<idsss.length; i++) {
				if(!listid.contains(idsss[i])) {
					listid.add(idsss[i]);
				}
			}
			//返回一个包含所有对象的指定类型的数组
			String[] idss= listid.toArray(new String[1]);
			for(int j = 0;j< idss.length;j++){
				String url = "";
				//获取数据
				OrganizationsVO detail = organizationService.selectByDeptId(idss[j]);
				if(detail == null){
					r.setMsg("导出失败，请校验资料数据");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
				//word模板地址
				String templatePath =fileServer.getPathPrefix()+"muban\\"+"deptFile.docx";
				// 渲染文本
				params.put("deptName", detail.getDeptName());
				// 渲染图片
				//经营许可证
				WordImageEntity image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				if(StrUtil.isNotEmpty(detail.getJingyingxukezhengfujian()) && !detail.getJingyingxukezhengfujian().contains("http")){
					detail.setJingyingxukezhengfujian(fileUploadClient.getUrl(detail.getJingyingxukezhengfujian()));
					url = detail.getJingyingxukezhengfujian();
					url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
					System.out.println(url);
					image.setUrl(url);
					image.setType(WordImageEntity.URL);
					params.put("a1", image);
				}else if(StringUtils.isNotEmpty(detail.getJingyingxukezhengfujian())){
					url = detail.getJingyingxukezhengfujian();
					url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
					System.out.println(url);
					image.setUrl(url);
					image.setType(WordImageEntity.URL);
					params.put("a1", image);
				}else{
					params.put("a1", "未上传");
				}
				//企业营业执照
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				if(StrUtil.isNotEmpty(detail.getYingyezhizhaofujian()) && !detail.getYingyezhizhaofujian().contains("http")){
					detail.setYingyezhizhaofujian(fileUploadClient.getUrl(detail.getYingyezhizhaofujian()));
					url = detail.getYingyezhizhaofujian();
					url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
					System.out.println(url);
					image.setUrl(url);
					image.setType(WordImageEntity.URL);
					params.put("a2", image);
				}else if(StringUtils.isNotEmpty(detail.getYingyezhizhaofujian())){
					url = detail.getYingyezhizhaofujian();
					url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
					System.out.println(url);
					image.setUrl(url);
					image.setType(WordImageEntity.URL);
					params.put("a2", image);
				}else{
					params.put("a2", "未上传");
				}
				//企业道路运输许可证
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				if(StrUtil.isNotEmpty(detail.getDaoluyunshuzhengfujian()) && !detail.getDaoluyunshuzhengfujian().contains("http")){
					detail.setDaoluyunshuzhengfujian(fileUploadClient.getUrl(detail.getDaoluyunshuzhengfujian()));
					url = detail.getDaoluyunshuzhengfujian();
					url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
					System.out.println(url);
					image.setUrl(url);
					image.setType(WordImageEntity.URL);
					params.put("a3", image);
				}else if(StringUtils.isNotEmpty(detail.getDaoluyunshuzhengfujian())){
					url = detail.getDaoluyunshuzhengfujian();
					url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
					System.out.println(url);
					image.setUrl(url);
					image.setType(WordImageEntity.URL);
					params.put("a3", image);
				}else{
					params.put("a3", "未上传");
				}
				// TODO 渲染其他类型的数据请参考官方文档
				//=================生成文件保存在本地D盘某目录下=================
//			temDir = "D:/mimi/file/word/"; ;//生成临时文件存放地址
				nyr=DateUtil.today().split("-");
				//附件存放地址(服务器生成地址)
				temDir = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2]+"/";
				//生成文件名
				Long time = new Date().getTime();
				// 生成的word格式
				String formatSuffix = ".docx";
				String wjName = detail.getDeptName()+"_"+"影像附件";
				// 拼接后的文件名
				fileName = wjName + formatSuffix;//文件名  带后缀
				//导出word
				String tmpPath = WordUtil2.exportDataWord3(templatePath, temDir, fileName, params, request, response);
				urlList.add(tmpPath);
			}
			folder = fileServer.getPathPrefix()+FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2]+"/"+"企业影像.zip";
			ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream(folder));
			ApacheZipUtils.doCompress1(urlList, bizOut);
			//不要忘记调用
			bizOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		r.setMsg("导出成功");
		r.setCode(200);
		r.setData(folder);
		r.setSuccess(true);
		return r;
	}

	@PostMapping("/imagesUploadInsert")
	@ApiLog("影像资料数据-批量导入")
	@ApiOperation(value = "影像资料数据-批量导入", notes = "传入ImageFuJian")
	public R imagesUploadInsert(BladeUser user,@RequestBody List<ImageFuJian> imageFuJians) throws ParseException {
		R r = new R();
		if (user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		boolean ii = false;
		for (ImageFuJian imageFuJian : imageFuJians) {
			//-------驾驶员影像资料-------
			//入职
			if (imageFuJian.getDeptName().equals("入职登记表")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<AnbiaoJiashiyuanRuzhi> anbiaoJiashiyuanRuzhiQueryWrapper = new QueryWrapper<>();
					anbiaoJiashiyuanRuzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, imageFuJian.getTableId());
					anbiaoJiashiyuanRuzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
					AnbiaoJiashiyuanRuzhi anbiaoJiashiyuanRuzhi = ruzhiService.getBaseMapper().selectOne(anbiaoJiashiyuanRuzhiQueryWrapper);
					anbiaoJiashiyuanRuzhi.setAjrUpdateTime(DateUtil.now());
					anbiaoJiashiyuanRuzhi.setAjrUpdateByIds(user.getUserId().toString());
					anbiaoJiashiyuanRuzhi.setAjrUpdateByName(user.getUserName());
					anbiaoJiashiyuanRuzhi.setAjrHeadPortrait(imageFuJian.getAttachments());

					ii = ruzhiService.updateById(anbiaoJiashiyuanRuzhi);
				}
			}
			//驾驶员身份证正面
			if (imageFuJian.getDeptName().equals("驾驶员身份证正面")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					JiaShiYuan jsy = new JiaShiYuan();
					jsy.setCaozuoshijian(DateUtil.now());
					jsy.setCaozuoren(user.getUserName());
					jsy.setCaozuorenid(user.getUserId());
					jsy.setId(imageFuJian.getTableId());
					jsy.setShenfenzhengfujian(imageFuJian.getAttachments());

					ii = jiaShiYuanService.updateById(jsy);
				}
			}
			//驾驶员身份证反面
			if (imageFuJian.getDeptName().equals("驾驶员身份证反面")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					JiaShiYuan jsy = new JiaShiYuan();
					jsy.setCaozuoshijian(DateUtil.now());
					jsy.setCaozuoren(user.getUserName());
					jsy.setCaozuorenid(user.getUserId());
					jsy.setId(imageFuJian.getTableId());
					jsy.setShenfenzhengfanmianfujian(imageFuJian.getAttachments());

					ii = jiaShiYuanService.updateById(jsy);
				}
			}
			//驾驶证正面
			if (imageFuJian.getDeptName().equals("驾驶证正面")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<>();
					jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, imageFuJian.getTableId());
					jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
					AnbiaoJiashiyuanJiashizheng jiashizheng = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
					jiashizheng.setAjjUpdateTime(DateUtil.now());
					jiashizheng.setAjjUpdateByName(user.getUserName());
					jiashizheng.setAjjUpdateByIds(user.getUserId().toString());
					jiashizheng.setAjjFrontPhotoAddress(imageFuJian.getAttachments());

					ii = jiashizhengService.updateById(jiashizheng);
				}
			}
			//驾驶证反面
			if (imageFuJian.getDeptName().equals("驾驶证反面")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<>();
					jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, imageFuJian.getTableId());
					jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
					AnbiaoJiashiyuanJiashizheng jiashizheng = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
					jiashizheng.setAjjUpdateTime(DateUtil.now());
					jiashizheng.setAjjUpdateByName(user.getUserName());
					jiashizheng.setAjjUpdateByIds(user.getUserId().toString());
					jiashizheng.setAjjAttachedPhotos(imageFuJian.getAttachments());

					ii = jiashizhengService.updateById(jiashizheng);
				}
			}
			//从业资格证
			if (imageFuJian.getDeptName().equals("从业资格证")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<>();
					congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, imageFuJian.getTableId());
					congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, "0");
					AnbiaoJiashiyuanCongyezigezheng congyezigezheng = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
					congyezigezheng.setAjcUpdateTime(DateUtil.now());
					congyezigezheng.setAjcUpdateByName(user.getUserName());
					congyezigezheng.setAjcUpdateByIds(user.getUserId().toString());
					congyezigezheng.setAjcLicence(imageFuJian.getAttachments());

					ii = congyezigezhengService.updateById(congyezigezheng);
				}
			}
			//体检表
			if (imageFuJian.getDeptName().equals("体检表")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<>();
					tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, imageFuJian.getTableId());
					tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, "0");
					AnbiaoJiashiyuanTijian tijian = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
					tijian.setAjtUpdateTime(DateUtil.now());
					tijian.setAjtUpdateByName(user.getUserName());
					tijian.setAjtUpdateByIds(user.getUserId().toString());
					tijian.setAjtEnclosure(imageFuJian.getAttachments());

					ii = tijianService.updateById(tijian);
				}
			}
			//岗卡培训
			if (imageFuJian.getDeptName().equals("岗前培训")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<>();
					gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, imageFuJian.getTableId());
					gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgDelete, "0");
					AnbiaoJiashiyuanGangqianpeixun gangqianpeixun = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
					gangqianpeixun.setAjgUpdateTime(DateUtil.now());
					gangqianpeixun.setAjgUpdateByName(user.getUserName());
					gangqianpeixun.setAjgUpdateByIds(user.getUserId().toString());
					gangqianpeixun.setAjgTrainingEnclosure(imageFuJian.getAttachments());

					ii = gangqianpeixunService.updateById(gangqianpeixun);
				}
			}
			//三年无重大责任证明
			if (imageFuJian.getDeptName().equals("三年无重大责任证明")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<AnbiaoJiashiyuanWuzezhengming> anbiaoJiashiyuanWuzezhengmingQueryWrapper = new QueryWrapper<>();
					anbiaoJiashiyuanWuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwAjIds, imageFuJian.getTableId());
					anbiaoJiashiyuanWuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwDelete, "0");
					AnbiaoJiashiyuanWuzezhengming wuzezhengming = wuzezhengmingService.getBaseMapper().selectOne(anbiaoJiashiyuanWuzezhengmingQueryWrapper);
					wuzezhengming.setAjwUpdateTime(DateUtil.now());
					wuzezhengming.setAjwUpdateByName(user.getUserName());
					wuzezhengming.setAjwUpdateByIds(user.getUserId().toString());
					wuzezhengming.setAjwEnclosure(imageFuJian.getAttachments());

					ii = wuzezhengmingService.updateById(wuzezhengming);
				}
			}
			//其他
			if (imageFuJian.getDeptName().equals("其他")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<>();
					qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtAjIds, imageFuJian.getTableId());
					qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtDelete, "0");
					AnbiaoJiashiyuanQita qita = qitaService.getBaseMapper().selectOne(qitaQueryWrapper);
					qita.setAjtUpdateTime(DateUtil.now());
					qita.setAjtUpdateByName(user.getUserName());
					qita.setAjtUpdateByIds(user.getUserId().toString());
					qita.setAjtEnclosure(imageFuJian.getAttachments());

					ii = qitaService.updateById(qita);
				}
			}


			//-------车辆影像资料-------
			//行驶证正面
			if (imageFuJian.getDeptName().equals("行驶证正面")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<VehicleXingshizheng> xingshizhengQueryWrapper = new QueryWrapper<>();
					xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxAvIds, imageFuJian.getTableId());
					xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxDelete, "0");
					VehicleXingshizheng vxs = xingshizhengService.getBaseMapper().selectOne(xingshizhengQueryWrapper);
					vxs.setAvxUpdateTime(LocalDateTime.now());
					vxs.setAvxUpdateByName(user.getUserName());
					vxs.setAvxUpdateByIds(user.getUserId().toString());
					vxs.setAvxOriginalEnclosure(imageFuJian.getAttachments());

					ii = xingshizhengService.updateById(vxs);
				}
			}
			//行驶证反面
			if (imageFuJian.getDeptName().equals("行驶证反面")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<VehicleXingshizheng> xingshizhengQueryWrapper = new QueryWrapper<>();
					xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxAvIds, imageFuJian.getTableId());
					xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxDelete, "0");
					VehicleXingshizheng vxs = xingshizhengService.getBaseMapper().selectOne(xingshizhengQueryWrapper);
					vxs.setAvxUpdateTime(LocalDateTime.now());
					vxs.setAvxUpdateByName(user.getUserName());
					vxs.setAvxUpdateByIds(user.getUserId().toString());
					vxs.setAvxCopyEnclosure(imageFuJian.getAttachments());

					ii = xingshizhengService.updateById(vxs);
				}
			}
			//道路运输证
			if (imageFuJian.getDeptName().equals("道路运输证")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<VehicleDaoluyunshuzheng> daoluyunshuzhengQueryWrapper = new QueryWrapper<>();
					daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdAvIds, imageFuJian.getTableId());
					daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdDelete, "0");
					VehicleDaoluyunshuzheng vdlyxs = daoluyunshuzhengService.getBaseMapper().selectOne(daoluyunshuzhengQueryWrapper);
					vdlyxs.setAvdUpdateTime(DateUtil.now());
					vdlyxs.setAvdUpdateByName(user.getUserName());
					vdlyxs.setAvdUpdateByIds(user.getUserId().toString());
					vdlyxs.setAvdEnclosure(imageFuJian.getAttachments());

					ii = daoluyunshuzhengService.updateById(vdlyxs);
				}
			}
			//车辆综合性能检测报告
			if (imageFuJian.getDeptName().equals("车辆综合性能检测报告")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<VehicleXingnengbaogao> xingnengbaogaoQueryWrapper = new QueryWrapper<>();
					xingnengbaogaoQueryWrapper.lambda().eq(VehicleXingnengbaogao::getAvxAvIds, imageFuJian.getTableId());
					xingnengbaogaoQueryWrapper.lambda().eq(VehicleXingnengbaogao::getAvxDelete, "0");
					VehicleXingnengbaogao vxnbg = xingnengbaogaoService.getBaseMapper().selectOne(xingnengbaogaoQueryWrapper);
					vxnbg.setAvxUpdateTime(LocalDateTime.now());
					vxnbg.setAvxUpdateByName(user.getUserName());
					vxnbg.setAvxUpdateByIds(user.getUserId().toString());
					vxnbg.setAvxEnclosure(imageFuJian.getAttachments());

					ii = xingnengbaogaoService.updateById(vxnbg);
				}
			}
			//车辆登记证书
			if (imageFuJian.getDeptName().equals("车辆登记证书")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<VehicleDengjizhengshu> dengjizhengshuQueryWrapper = new QueryWrapper<>();
					dengjizhengshuQueryWrapper.lambda().eq(VehicleDengjizhengshu::getAvdVehicleIds, imageFuJian.getTableId());
					dengjizhengshuQueryWrapper.lambda().eq(VehicleDengjizhengshu::getAvdDelete, "0");
					VehicleDengjizhengshu vdjz = dengjizhengshuService.getBaseMapper().selectOne(dengjizhengshuQueryWrapper);
					vdjz.setAvdUpdateTime(DateUtil.now());
					vdjz.setAvdUpdateByName(user.getUserName());
					vdjz.setAvdUpdateByIds(user.getUserId().toString());
					vdjz.setAvdEnclosure(imageFuJian.getAttachments());

					ii = dengjizhengshuService.updateById(vdjz);
				}
			}


			//-------企业影像资料-------
			//经营许可证
			if (imageFuJian.getDeptName().equals("经营许可证")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
					organizationsQueryWrapper.lambda().eq(Organizations::getDeptId, imageFuJian.getTableId());
					organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, "0");
					Organizations organization = organizationService.getBaseMapper().selectOne(organizationsQueryWrapper);
					organization.setCaozuoshijian(DateUtil.now());
					organization.setDaoluyunshuzhengfujian(imageFuJian.getAttachments());

					ii = organizationService.updateById(organization);
				}
			}
			//企业营业执照
			if (imageFuJian.getDeptName().equals("企业营业执照")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
					organizationsQueryWrapper.lambda().eq(Organizations::getDeptId, imageFuJian.getTableId());
					organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, "0");
					Organizations organization = organizationService.getBaseMapper().selectOne(organizationsQueryWrapper);
					organization.setCaozuoshijian(DateUtil.now());
					organization.setJingyingxukezhengfujian(imageFuJian.getAttachments());

					ii = organizationService.updateById(organization);
				}
			}
			//企业道路运输许可证
			if (imageFuJian.getDeptName().equals("企业道路运输许可证")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
					organizationsQueryWrapper.lambda().eq(Organizations::getDeptId, imageFuJian.getTableId());
					organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, "0");
					Organizations organization = organizationService.getBaseMapper().selectOne(organizationsQueryWrapper);
					organization.setCaozuoshijian(DateUtil.now());
					organization.setYingyezhizhaofujian(imageFuJian.getAttachments());

					ii = organizationService.updateById(organization);
				}
			}


			//-------管理人员影像资料-------
			//身份证正面
			if (imageFuJian.getDeptName().equals("身份证正面")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<Personnel> personnelQueryWrapper = new QueryWrapper<>();
					personnelQueryWrapper.lambda().eq(Personnel::getId, imageFuJian.getTableId());
					personnelQueryWrapper.lambda().eq(Personnel::getIsDeleted, "0");
					Personnel personnel = personnelService.getBaseMapper().selectOne(personnelQueryWrapper);
					personnel.setCaozuoshijian(DateUtil.now());
					if (user == null) {
						personnel.setCaozuoren("admin");
						personnel.setCaozuorenid(1);
					} else {
						personnel.setCaozuoren(user.getUserName());
						personnel.setCaozuorenid(user.getUserId());
					}
					personnel.setShenfenzheng(imageFuJian.getAttachments());

					ii = personnelService.updateSelective(personnel);
				}
			}
			//身份证反面
			if (imageFuJian.getDeptName().equals("身份证反面")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<Personnel> personnelQueryWrapper = new QueryWrapper<>();
					personnelQueryWrapper.lambda().eq(Personnel::getId, imageFuJian.getTableId());
					personnelQueryWrapper.lambda().eq(Personnel::getIsDeleted, "0");
					Personnel personnel = personnelService.getBaseMapper().selectOne(personnelQueryWrapper);
					personnel.setCaozuoshijian(DateUtil.now());
					if (user == null) {
						personnel.setCaozuoren("admin");
						personnel.setCaozuorenid(1);
					} else {
						personnel.setCaozuoren(user.getUserName());
						personnel.setCaozuorenid(user.getUserId());
					}
					personnel.setShenfenzhengfanmianfujian(imageFuJian.getAttachments());

					ii = personnelService.updateSelective(personnel);
				}
			}
			//其他正面
			if (imageFuJian.getDeptName().equals("其他正面")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<Personnel> personnelQueryWrapper = new QueryWrapper<>();
					personnelQueryWrapper.lambda().eq(Personnel::getId, imageFuJian.getTableId());
					personnelQueryWrapper.lambda().eq(Personnel::getIsDeleted, "0");
					Personnel personnel = personnelService.getBaseMapper().selectOne(personnelQueryWrapper);
					personnel.setCaozuoshijian(DateUtil.now());
					if (user == null) {
						personnel.setCaozuoren("admin");
						personnel.setCaozuorenid(1);
					} else {
						personnel.setCaozuoren(user.getUserName());
						personnel.setCaozuorenid(user.getUserId());
					}
					personnel.setQitazhengmianfujian(imageFuJian.getAttachments());

					ii = personnelService.updateSelective(personnel);
				}
			}
			//其他反面
			if (imageFuJian.getDeptName().equals("其他反面")) {
				if (StringUtils.isNotEmpty(imageFuJian.getAttachments()) && imageFuJian.getAttachments() != "null") {
					QueryWrapper<Personnel> personnelQueryWrapper = new QueryWrapper<>();
					personnelQueryWrapper.lambda().eq(Personnel::getId, imageFuJian.getTableId());
					personnelQueryWrapper.lambda().eq(Personnel::getIsDeleted, "0");
					Personnel personnel = personnelService.getBaseMapper().selectOne(personnelQueryWrapper);
					personnel.setCaozuoshijian(DateUtil.now());
					if (user == null) {
						personnel.setCaozuoren("admin");
						personnel.setCaozuorenid(1);
					} else {
						personnel.setCaozuoren(user.getUserName());
						personnel.setCaozuorenid(user.getUserId());
					}
					personnel.setQitafanmianfujian(imageFuJian.getAttachments());

					ii = personnelService.updateSelective(personnel);
				}
			}

			if (ii) {
				r.setMsg("导入成功");
				r.setCode(200);
				r.setSuccess(false);
			} else {
				r.setMsg("导入失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}
		return r;
	}

}
