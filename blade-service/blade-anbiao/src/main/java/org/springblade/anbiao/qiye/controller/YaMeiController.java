/**
 * Copyright (C), 2015-2021
 * FileName: YaMeiController
 * Author:   呵呵哒
 * Date:     2021/4/23 20:58
 * Description:
 */
package org.springblade.anbiao.qiye.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.qiye.springTemplate.basicJsonBean.JsonBean;
import org.springblade.anbiao.qiye.springTemplate.basicJsonBean.JsonProducer;
import org.springblade.anbiao.qiye.springTemplate.basicString.StringProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 hyp
 * @创建时间 2021/4/23
 * @描述
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/yaMeiApi")
@Api(value = "亚美接口调用", tags = "亚美接口调用")
public class YaMeiController {

	@Autowired
	private StringProducer producer;

	@Autowired
	private JsonProducer jsonProducer;

	@GetMapping("/string")
	public String string() {
		for (int i = 0; i < 5; i++) {
			producer.send("Message【" + i + "】：Hello,My name is CJWDDSG ZL");
		}
		return "success";
	}

	@GetMapping("/json")
	public String json() {
		JsonBean json = new JsonBean();
		json.setMessageId("1");
		json.setMessageContent("this is message");
		jsonProducer.send(json);

		return "success";
	}
}
