package org.springblade.auth.controller;

/**
 * @author hyp
 * @create 2022-10-24 16:12
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.guanlijigouherenyuan.feign.IPersonnelClient;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.common.configurationBean.WechatServer;
import org.springblade.common.tool.StringUtils;
import org.springblade.common.tool.wx.util.WxgzhHttpUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.feign.IUserClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth/wechatUser")
@Api(value = "微信小程序登录验证", tags = "微信小程序登录验证")
public class WechatUserController {

	private IUserClient client;

	private IPersonnelClient personnelClient;

	private ISysClient sysClient;

	private WechatServer wechatServer;

	@GetMapping("/getOpenId")
	@ApiLog("获取openID-微信")
	@ApiOperation(value = "获取openID-微信", notes = "传入code", position = 1)
	public R getOpenId(String code) throws Exception {
		R rs = new R();
		String openid = WxgzhHttpUtils.getOpenID(code,wechatServer.getWxgzhAppId(),wechatServer.getWxgzhSecret());
		rs.setData(openid);
		rs.setCode(200);
		rs.setSuccess(true);
		return rs;
	}

	@GetMapping("/updateDeptWechatUser")
	@ApiLog("确认绑定-微信")
	@ApiOperation(value = "确认绑定-微信", notes = "传入openid，账号、角色类型（1：驾驶员，2：两类人员）", position = 1)
	public R updateDeptWechatUser(String openid, String loginName,String password,Integer type) throws Exception {
		R rs = new R();
		if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password) || type == null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("参数不能为空！");
			return rs;
		}
		String errMsg = "";
		if(type == 1){
			password = DigestUtil.encrypt(password);
			JiaShiYuan us = personnelClient.driverLogin(loginName,password);
			if (us != null) {
				personnelClient.bindWechatOpenId(us.getId(),openid,1,1);
				Dept dept = sysClient.selectByJGBM("机构",us.getDeptId().toString());
				if(dept != null){
					us.setDeptName(dept.getDeptName());
				}
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("绑定成功");
				rs.setData(us);
				return rs;
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				errMsg = "账号密码错误，请校验后重新输入!";
			}
		}else{
			password = DigestUtil.encrypt(password);
			R<UserInfo> res = client.userInfo(loginName,password);
			User user = res.getData().getUser();
			if (user != null && user.getId() != null) {
				personnelClient.bindWechatOpenId(user.getId().toString(),openid,1,2);
				Dept dept = sysClient.selectByJGBM("机构",user.getDeptId().toString());
				if(dept != null){
					user.setDeptName(dept.getDeptName());
				}
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("绑定成功");
				rs.setData(user);
				return rs;
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				errMsg = "账号密码错误，请校验后重新输入!";
			}
		}
		try {
			errMsg= URLEncoder.encode(errMsg, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		rs.setMsg(errMsg);
		return rs;
	}

	@GetMapping("/removeBD")
	@ApiLog("注销解绑-微信（微信公众号）")
	@ApiOperation(value = "注销解绑-微信（微信公众号）", notes = "传入用户ID", position = 2)
	public R removeDriverBD(String yhId) {
		R rs = new R();
		personnelClient.bindWechatOpenId(yhId,"",0,null);
		rs.setCode(200);
		rs.setSuccess(true);
		rs.setMsg("注销解绑成功");
		return rs;
	}


}
