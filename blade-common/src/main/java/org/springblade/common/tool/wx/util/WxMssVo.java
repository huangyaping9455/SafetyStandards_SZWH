package org.springblade.common.tool.wx.util;

import lombok.Data;
import java.util.Map;

/**
 * 小程序推送所需数据
 * @author hyp
 * @create 2023-05-22 10:52
 * */
@Data
public class WxMssVo {
	//用户openid
	private String touser;
	//模版id
	private String template_id;
	//默认跳到小程序首页
	private String page = "pages/risk/index";
	//放大那个推送字段
	//private String emphasis_keyword = "keyword1.DATA";
	//推送文字
	private Map<String, TemplateDataVo> data;
}
