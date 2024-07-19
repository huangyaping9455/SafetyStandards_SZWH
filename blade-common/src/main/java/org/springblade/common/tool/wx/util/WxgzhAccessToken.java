package org.springblade.common.tool.wx.util;

/**
 * 微信通用接口凭证
 * @author Administrator
 * @create 2023-10-07 10:52
 */
public class WxgzhAccessToken implements java.io.Serializable{


	private static final long serialVersionUID = -4240357901925120079L;

	/** 获取到的凭证 */
	private String token;

	/** 凭证有效时间，单位：秒 */
	private int expiresIn;

	public WxgzhAccessToken() {
	}

	public WxgzhAccessToken(String token, int expiresIn) {
		this.token = token;
		this.expiresIn = expiresIn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
