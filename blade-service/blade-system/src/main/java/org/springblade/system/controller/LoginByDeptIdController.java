package org.springblade.system.controller;


import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.feign.IOrganizationsClient;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: SafetyStandards
 * @description: LoginByDeptIdController
 **/
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Api(value = "登录页数据", tags = "登录页数据")
public class LoginByDeptIdController {

	private IOrganizationsClient orrganizationsClient;

	private IFileUploadClient fileUploadClient;

	/**
	 * 根据login后缀单位id获取图片url
	 */
	@GetMapping("/loginByDeptId")
	@ApiOperation(value = "根据deptid获取url", notes = "根据deptid获取url", position = 1)
	public R<Map<String, String>> loginByDeptId(String  deptId) {
		Map<String,String> map = new HashMap<String,String>();
		Organizations organization=orrganizationsClient.selectByDeptId(deptId);
		if(organization!=null){
			//登录页
			if(StrUtil.isNotEmpty(organization.getLoginPhoto())){
				organization.setLoginPhoto(fileUploadClient.getUrlUrl(organization.getLoginPhoto()));
			}
			//首页
			if(StrUtil.isNotEmpty(organization.getHomePhoto())){
				organization.setHomePhoto(fileUploadClient.getUrlUrl(organization.getHomePhoto()));
			}
			//简介页
			if(StrUtil.isNotEmpty(organization.getProfilePhoto())){
				organization.setProfilePhoto(fileUploadClient.getUrlUrl(organization.getProfilePhoto()));
			}
			//简介页
			if(StrUtil.isNotEmpty(organization.getLogoPhoto())){
				organization.setLogoPhoto(fileUploadClient.getUrlUrl(organization.getLogoPhoto()));
			}
			//app登录
			if(StrUtil.isNotEmpty(organization.getLoginPhotoApp())){
				organization.setLoginPhotoApp(fileUploadClient.getUrlUrl(organization.getLoginPhotoApp()));
			}
			if(StrUtil.isNotEmpty(organization.getHomePhotoApp())){
				organization.setLoginPhotoApp(fileUploadClient.getUrlUrl(organization.getHomePhotoApp()));
			}
			if(StrUtil.isNotEmpty(organization.getProfilePhotoApp())){
				organization.setLoginPhotoApp(fileUploadClient.getUrlUrl(organization.getProfilePhotoApp()));
			}
			map.put("loginPhoto",organization.getLoginPhoto());
			map.put("homePhoto",organization.getHomePhoto());
			map.put("profilePhoto",organization.getProfilePhoto());
			map.put("logoPhoto",organization.getLogoPhoto());
			map.put("loginPhotoApp",organization.getLoginPhotoApp());
			map.put("homePhotoApp",organization.getHomePhotoApp());
			map.put("profilePhotoApp",organization.getHomePhotoApp());
		}else{
			map.put("loginPhoto","");
			map.put("homePhoto","");
			map.put("profilePhoto","");
			map.put("logoPhoto","");
			map.put("loginPhotoApp","");
			map.put("homePhotoApp","");
			map.put("profilePhotoApp","");
		}
		return R.data(map);
	}
}
