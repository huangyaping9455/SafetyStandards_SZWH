package org.springblade.auth.controller;

/**
 * @author hyp
 * @create 2022-10-24 16:12
 */

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.guanlijigouherenyuan.feign.IPersonnelClient;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.common.configurationBean.WechatServer;
import org.springblade.common.tool.WeChatUtil;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springblade.system.user.entity.EnterpriseUser;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.feign.IUserClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth/wechat")
@Api(value = "微信小程序登录验证", tags = "微信小程序登录验证")
public class UserController {

	private IUserClient client;

	private IPersonnelClient personnelClient;

	private ISysClient sysClient;

	private WechatServer wechatServer;

	@PostMapping("/manageLogin")
	@ApiLog("小程序--登陆（企管端）")
	@ApiOperation(value = "小程序--登录（企管端）", notes = "传入code，账号", position = 1)
	public R manageLogin(EnterpriseUser enterpriseUser) {
		R rs = new R();
		//返回accessToken
		AuthInfoConfig info = new AuthInfoConfig();
		String cellphone = "";
		String openId = "";

		if(enterpriseUser != null && !enterpriseUser.equals("")) {
			if(enterpriseUser.getLoginType() != null && !enterpriseUser.getLoginType().equals("")) {
				if(enterpriseUser.getLoginType().equals("0")) {
					if(enterpriseUser.getName() != null && !enterpriseUser.getName().equals("") && enterpriseUser.getName().length() > 0
					&& enterpriseUser.getPassword() != null && !enterpriseUser.getPassword().equals("") && enterpriseUser.getPassword().length() > 0) {
						User user = client.login(enterpriseUser.getName(),DigestUtil.encrypt(enterpriseUser.getPassword()));
						if(user != null){
							//设置jwt参数
							Map<String, String> param = new HashMap<>(16);
							param.put(SecureUtil.USER_ID, Func.toStr(user.getId()));
							param.put(SecureUtil.ACCOUNT, user.getAccount());
							param.put(SecureUtil.USER_NAME, user.getName());
							//拼装accessToken
							String accessToken = SecureUtil.createJWT(param, "audience", "issuser", true);

							info.setAccount(user.getAccount());
							info.setUserName(user.getName());
							info.setOpenid(user.getOpenid());
							info.setAuthority("administrator");
							info.setAccessToken(accessToken);
							info.setTokenType(SecureUtil.BEARER);
							Dept dept=sysClient.selectByJGBM("机构",user.getDeptId().toString());
							if(dept == null){
								rs.setMsg("该账号岗位机构不存在!");
								rs.setCode(500);
								rs.setSuccess(false);
							}
							info.setDeptId(dept.getId().toString());
							info.setDeptName(dept.getDeptName());
							info.setUserId(user.getId().toString());
							//设置token过期时间
							info.setExpiresIn(SecureUtil.getExpire());

							rs.setMsg("登录成功!");
							rs.setCode(200);
							rs.setData(info);
							rs.setSuccess(true);
						}else{
							rs.setMsg("账号、密码错误!");
							rs.setCode(500);
							rs.setSuccess(false);
						}
					} else {
						rs.setMsg("参数不能为空！");
						rs.setCode(500);
						rs.setSuccess(false);
						return rs;
					}
				} else if(enterpriseUser.getLoginType().equals("1")) {
					if(enterpriseUser.getCode() != null && !enterpriseUser.getCode().equals("") && enterpriseUser.getCode().length() > 0) {
						WeChatUtil weChatUtil = new WeChatUtil();
						String jsonId = weChatUtil.getopenid(wechatServer.getQyAppId(),enterpriseUser.getCode(),wechatServer.getQySecret());
						JSONObject jsonObject = JSONObject.parseObject(jsonId);
						String openid = (String) jsonObject.get("openid");
						if(StringUtils.isNotBlank(openid)) {
							User user = client.getopenid(openid);
							if(user != null) {
								user = client.login(user.getAccount(),user.getPassword());
								//设置jwt参数
								Map<String, String> param = new HashMap<>(16);
								param.put(SecureUtil.USER_ID, Func.toStr(user.getId()));
								param.put(SecureUtil.ACCOUNT, user.getAccount());
								param.put(SecureUtil.USER_NAME, user.getName());
								//拼装accessToken
								String accessToken = SecureUtil.createJWT(param, "audience", "issuser", true);
								info.setAccount(user.getAccount());
								info.setUserName(user.getName());
								info.setAuthority("administrator");
								info.setAccessToken(accessToken);
								info.setTokenType(SecureUtil.BEARER);
								Dept dept=sysClient.selectByJGBM("机构",user.getDeptId().toString());
								if(dept == null){
									rs.setMsg("该账号岗位机构不存在!");
									rs.setCode(500);
									rs.setSuccess(false);
								}
								info.setDeptId(dept.getId().toString());
								info.setDeptName(dept.getDeptName());
								info.setUserId(user.getId().toString());
								info.setOpenid(openid);
								//设置token过期时间
								info.setExpiresIn(SecureUtil.getExpire());

								rs.setMsg("登录成功!");
								rs.setCode(200);
								rs.setSuccess(true);
								rs.setData(info);
								return rs;
							} else {
								rs.setMsg(org.springframework.util.StringUtils.isEmpty(cellphone) ? "请授权手机号登录" :  "账号不存在，请前往注册或联系管理员!");
								rs.setCode(404);
								rs.setSuccess(false);
								return rs;
							}
						} else {
							rs.setMsg("微信登录失败，请重新登录!");
							rs.setCode(500);
							rs.setSuccess(false);
							return rs;
						}
					} else {
						rs.setMsg("参数不能为空！");
						rs.setCode(500);
						rs.setSuccess(false);
						return rs;
					}

				} else {
					rs.setMsg("登陆方式错误！");
					rs.setCode(500);
					rs.setSuccess(false);
					return rs;
				}
			} else {
				rs.setMsg("参数不能为空！");
				rs.setCode(500);
				rs.setSuccess(false);
				return rs;
			}
		} else {
			rs.setMsg("参数不能为空！");
			rs.setCode(500);
			rs.setSuccess(false);
			return rs;
		}
		return rs;
	}

	@PostMapping("/driverLogin")
	@ApiLog("小程序--登录（司机端）")
	@ApiOperation(value = "小程序--登录（司机端）", notes = "传入code，账号", position = 1)
	public R driverLogin(@ApiParam(value = "账号", required = false) @RequestParam String name,
		@ApiParam(value = "密码", required = false) @RequestParam String password,
		@ApiParam(value = "微信code", required = false) @RequestParam String code,
		@ApiParam(value = "encryptedData", required = false) @RequestParam String encryptedData,
		@ApiParam(value = "iv", required = false) @RequestParam String iv){
		R rs = new R();
		//返回accessToken
		AuthInfoConfig info=new AuthInfoConfig();
		String cellphone = "";
		String openId = "";

		if(StringUtils.isBlank(name) && StringUtils.isBlank(password) && StringUtils.isBlank(code)){
			rs.setMsg("参数不能为空!");
			rs.setCode(500);
			rs.setSuccess(false);
			return rs;
		}else if(StringUtils.isNotBlank(code)){
			WeChatUtil weChatUtil = new WeChatUtil();
			String jsonId = weChatUtil.getopenid(wechatServer.getAppId(),code,wechatServer.getSecret());
			JSONObject jsonObject = JSONObject.parseObject(jsonId);
//			JsonNode openJsonNode = weChatUtil.requestOpenId(WECHATURL,wechatServer.getAppId(),wechatServer.getSecret(),code);
//			System.out.println(openJsonNode);
			//可将返回值类型改为String，然后直接return jsonObject
			String openid = (String) jsonObject.get("openid");
			if(openid != null && !"".equals(openid)){
//				if (StringUtils.isNotBlank("encryptedData") && StringUtils.isNotBlank("iv")) {
//					String json = WeChatUtil.decrypt(jsonObject.get("session_key").toString(), iv, encryptedData);
//					if (null == json) {
//						rs.setMsg("授权微信手机号码登录失败，请重新登录!");
//						rs.setCode(500);
//						rs.setSuccess(false);
//						return rs;
//					}
//					JsonNode infoJsonNode = JSONUtils.string2JsonNode(json);
//					if (infoJsonNode.hasNonNull("purePhoneNumber")) {
//						cellphone = infoJsonNode.get("purePhoneNumber").asText();
////						userService.updateOpenId(cellphone, openId);
//					}
//				}
				JiaShiYuan us = null;
				if (null != (us = personnelClient.getDriverOpenId(openId))) {
					//设置jwt参数
					Map<String, String> param = new HashMap<>(16);
					param.put(SecureUtil.USER_ID, Func.toStr(us.getId()));
					param.put(SecureUtil.ACCOUNT, us.getJiashiyuanxingming());
					param.put(SecureUtil.USER_NAME, us.getShoujihaoma());
					//拼装accessToken
					String accessToken = SecureUtil.createJWT(param, "audience", "issuser", true);
					info.setAccount(us.getShoujihaoma());
					info.setUserName(us.getJiashiyuanxingming());
					info.setAuthority("administrator");
					info.setAccessToken(accessToken);
					info.setTokenType(SecureUtil.BEARER);
					Dept dept=sysClient.selectByJGBM("机构",us.getDeptId().toString());
					if(dept == null){
						rs.setMsg("该账号岗位机构不存在!");
						rs.setCode(500);
						rs.setSuccess(false);
					}
					info.setDeptId(dept.getId().toString());
					info.setDeptName(dept.getDeptName());
					info.setUserId(us.getId().toString());
					//设置token过期时间
					info.setExpiresIn(SecureUtil.getExpire());

					rs.setMsg("登录成功!");
					rs.setCode(200);
					rs.setSuccess(true);
					rs.setData(info);
					return rs;
				}else {
					rs.setMsg(org.springframework.util.StringUtils.isEmpty(cellphone) ? "请授权手机号登录" :  "账号不存在，请前往注册或联系管理员!");
					rs.setCode(404);
					rs.setSuccess(false);
					return rs;
				}
			}else{
				rs.setMsg("微信登录失败，请重新登录!");
				rs.setCode(500);
				rs.setSuccess(false);
				return rs;
			}
		}else {
			password = DigestUtil.encrypt(password);
			JiaShiYuan us = personnelClient.driverLogin(name,password);
			if(us != null){
				//设置jwt参数
				Map<String, String> param = new HashMap<>(16);
				param.put(SecureUtil.USER_ID, Func.toStr(us.getId()));
				param.put(SecureUtil.ACCOUNT, us.getShoujihaoma());
				param.put(SecureUtil.USER_NAME, us.getJiashiyuanxingming());
				//拼装accessToken
				String accessToken = SecureUtil.createJWT(param, "audience", "issuser", true);

				info.setAccount(us.getShoujihaoma());
				info.setUserName(us.getJiashiyuanxingming());
				info.setAuthority("administrator");
				info.setAccessToken(accessToken);
				info.setTokenType(SecureUtil.BEARER);
				Dept dept=sysClient.selectByJGBM("机构",us.getDeptId().toString());
				if(dept == null){
					rs.setMsg("该账号岗位机构不存在!");
					rs.setCode(500);
					rs.setSuccess(false);
				}
				info.setDeptId(dept.getId().toString());
				info.setDeptName(dept.getDeptName());
				info.setUserId(us.getId().toString());
				//设置token过期时间
				info.setExpiresIn(SecureUtil.getExpire());

				rs.setMsg("登录成功!");
				rs.setCode(200);
				rs.setData(info);
				rs.setSuccess(true);
			}else{
				rs.setMsg("账号、密码错误!");
				rs.setCode(500);
				rs.setSuccess(false);
			}
		}
		return rs;
	}

	@GetMapping("/updateDriverBD")
	@ApiLog("确认绑定-微信（司机端）")
	@ApiOperation(value = "确认绑定-微信（司机端）", notes = "传入code，账号", position = 2)
	public R updateDriverBD(String code, String loginName) {
		R rs = new R();
		String errMsg = "";
		JiaShiYuan user = personnelClient.driverLogin(loginName,null);
		if (user != null) {
			if (StringUtils.isNotEmpty(user.getOpenid())) {
				rs.setCode(500);
				rs.setSuccess(false);
				errMsg = "该账户已经绑定";
			} else {
				WeChatUtil weChatUtil = new WeChatUtil();
				String jsonId = weChatUtil.getopenid(wechatServer.getAppId(),code,wechatServer.getSecret());
				JSONObject jsonObject = JSONObject.parseObject(jsonId);
				//可将返回值类型改为String，然后直接return jsonObject
				String openid = (String) jsonObject.get("openid");
				if(openid != null && !"".equals(openid)){
					personnelClient.bindDriverOpenId(loginName,openid);
					rs.setCode(200);
					rs.setSuccess(true);
					rs.setMsg("绑定成功");
					return rs;
				}else{
					rs.setMsg("微信登录失败，请重新登录!");
					rs.setCode(500);
					rs.setSuccess(false);
					return rs;
				}
			}
		} else {
			rs.setCode(404);
			rs.setSuccess(false);
			errMsg = "该账号不存在";
		}
		try {
			errMsg= URLEncoder.encode(errMsg, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rs;
	}

	@GetMapping("/removeDriverBD")
	@ApiLog("注销解绑-微信（司机端）")
	@ApiOperation(value = "注销解绑-微信（司机端）", notes = "传入账号", position = 2)
	public R removeDriverBD(String loginName) {
		R rs = new R();
		personnelClient.bindDriverOpenId(loginName,"");
		rs.setCode(200);
		rs.setSuccess(true);
		rs.setMsg("注销解绑成功");
		return rs;
	}


	@GetMapping("/updateUserDeptBD")
	@ApiLog("确认绑定-微信（企业端）")
	@ApiOperation(value = "确认绑定-微信（企业端）", notes = "传入code，账号", position = 2)
	public R updateUserDeptBD(String code, String loginName) {
		R rs = new R();
		String errMsg = "";
		R<UserInfo> res = client.userInfo(loginName,null);
		User user = res.getData().getUser();
		if (user != null) {
			if (StringUtils.isNotEmpty(user.getOpenid())) {
				rs.setCode(500);
				rs.setSuccess(false);
				errMsg = "该账户已经绑定";
			} else {
				WeChatUtil weChatUtil = new WeChatUtil();
				String jsonId = weChatUtil.getopenid(wechatServer.getQyAppId(),code,wechatServer.getQySecret());
				JSONObject jsonObject = JSONObject.parseObject(jsonId);
				System.err.println(jsonObject);
				//可将返回值类型改为String，然后直接return jsonObject
				String openid = (String) jsonObject.get("openid");
				if(openid != null && !"".equals(openid)){
					client.bindDriverOpenId(loginName,openid);
					rs.setData(openid);
					rs.setCode(200);
					rs.setSuccess(true);
					errMsg = "绑定成功";
				}else{
					rs.setMsg("微信登录失败，请重新登录!");
					rs.setCode(500);
					rs.setSuccess(false);
					return rs;
				}
			}
		} else {
			rs.setCode(404);
			rs.setSuccess(false);
			errMsg = "该账号不存在";
		}
		try {
			errMsg= URLEncoder.encode(errMsg, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		rs.setMsg(errMsg);
		return rs;
	}

}
