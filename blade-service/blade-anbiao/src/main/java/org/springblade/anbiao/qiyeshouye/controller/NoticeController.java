/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.anbiao.qiyeshouye.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.qiyeshouye.NoticeVO;
import org.springblade.anbiao.qiyeshouye.entity.Notice;
import org.springblade.anbiao.qiyeshouye.page.NoticePage;
import org.springblade.anbiao.qiyeshouye.service.INoticeService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 控制器
 *
 * @author hyp
 * @since 2021-03-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/notice")
@Api(value = "运维端-通知公告", tags = "运维端-通知公告")
public class NoticeController {

	private INoticeService noticeService;

	@PostMapping(value = "/getNoticeAll")
	@ApiLog("运维端-通知公告列表")
	@ApiOperation(value = "运维端-通知公告列表", notes = "传入noticePage",position = 6)
	public R<NoticePage<Notice>> getNoticeAll(@RequestBody NoticePage noticePage) {
		//排序条件
		////默认车辆牌照降序update-password
		if(noticePage.getOrderColumns()==null){
			noticePage.setOrderColumn("releaseTime");
		}else{
			noticePage.setOrderColumn(noticePage.getOrderColumns());
		}
		NoticePage<Notice> pages = noticeService.selectGetAll(noticePage);
		return R.data(pages);
	}

	/**
	 * 获取当天的通知公告
	 */
	@GetMapping("/getNowNotice")
	@ApiOperation(value = "获取当天的通知公告", notes = "", position = 2)
	public R<NoticeVO> getNowNotice(Integer deptId) {
		R r = new R();
		List<Notice> detail = noticeService.selectNoticePage(deptId);
		if(detail != null){
			r.setMsg("获取成功");
			r.setData(detail);
			r.setCode(200);
		}else{
			r.setMsg("获取成功,暂无数据");
			r.setCode(200);
		}
		return r;
	}

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入notice", position = 2)
	public R<NoticeVO> detail(Integer Id) {
		R r = new R();
		Notice detail = noticeService.selectByIds(Id);
		if(detail != null){
			r.setMsg("获取成功");
			r.setData(detail);
			r.setCode(200);
		}else{
			r.setMsg("获取成功,暂无数据");
			r.setCode(200);
		}
		return r;
	}

	/**
	 * 新增
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入notice", position = 6)
	public R submit(@RequestBody Notice notice) {
		R rs = new R();
		if (StringUtils.isBlank(notice.getCreateTime())){
			notice.setCreateTime(DateUtil.now());
		}

		if (notice.getIs_deleted() != 0){
			notice.setIs_deleted(0);
		}

		if(notice.getStatus() == 2){
			notice.setStatus(2);
		}else if(notice.getStatus() == 1){
			notice.setStatus(1);
		}else{
			notice.setStatus(0);
		}

		boolean i = noticeService.insertSelective(notice);
		if(i == true){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("新增通知公告信息成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("新增通知公告信息失败");
		}
		return rs;
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入notice", position = 6)
	public R update(@RequestBody Notice notice) {
		R rs = new R();
		if (StringUtils.isBlank(notice.getUpdateTime())){
			notice.setUpdateTime(DateUtil.now());
		}
		boolean i = noticeService.updateSelective(notice);
		if(i == true){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("编辑通知公告信息成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("编辑通知公告信息失败");
		}
		return rs;
	}

	/**
	 * 删除
	 */
	@GetMapping("/remove")
	@ApiLog("删除通知公告")
	@ApiOperation(value = "删除通知公告", notes = "传入通知公告ID、用户ID", position = 7)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Id", value = "通知公告ID", required = true),
		@ApiImplicitParam(name = "userId", value = "用户ID", required = true)
	})
	public R remove(@RequestParam Integer Id,Integer userId) {
		String formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean temp = noticeService.deleteBind(formatStr2,userId,Id);
		return R.status(temp);
	}

	/**
	 * 获取消息
	 *
	 * @return
	 */
	@GetMapping("/notices")
	@ApiOperation(value = "消息", notes = "消息", position = 8)
	public R notices() {
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map1 = new HashMap<>(16);
		map1.put("logo", "https://spring.io/img/homepage/icon-spring-framework.svg");
		map1.put("title", "SpringBoot");
		map1.put("description", "现在的web项目几乎都会用到spring框架，而要使用spring难免需要配置大量的xml配置文件，而 springboot的出现解   决了这一问题，一个项目甚至不用部署到服务器上直接开跑，真像springboot所说：“just run”。");
		map1.put("member", "Chill");
		map1.put("href", "http://spring.io/projects/spring-boot");
		list.add(map1);

		Map<String, String> map2 = new HashMap<>(16);
		map2.put("logo", "https://spring.io/img/homepage/icon-spring-cloud.svg");
		map2.put("title", "SpringCloud");
		map2.put("description", "SpringCloud是基于SpringBoot的一整套实现微服务的框架。他提供了微服务开发所需的配置管理、服务发现、断路器、智能路由、微代理、控制总线、全局锁、决策竞选、分布式会话和集群状态管理等组件。");
		map2.put("member", "Chill");
		map2.put("href", "http://spring.io/projects/spring-cloud");
		list.add(map2);

		Map<String, String> map3 = new HashMap<>(16);
		map3.put("logo", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546359961068&di=05ff9406e6675ca9a58a525a7e7950b9&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D575314515%2C4268715674%26fm%3D214%26gp%3D0.jpg");
		map3.put("title", "Mybatis");
		map3.put("description", "MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生信息，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。");
		map3.put("member", "Chill");
		map3.put("href", "http://www.mybatis.org/mybatis-3/getting-started.html");
		list.add(map3);

		Map<String, String> map4 = new HashMap<>(16);
		map4.put("logo", "https://gw.alipayobjects.com/zos/rmsportal/kZzEzemZyKLKFsojXItE.png");
		map4.put("title", "React");
		map4.put("description", "React 起源于 Facebook 的内部项目，因为该公司对市场上所有 JavaScript MVC 框架，都不满意，就决定自己写一套，用来架设Instagram 的网站。做出来以后，发现这套东西很好用，就在2013年5月开源了。");
		map4.put("member", "Chill");
		map4.put("href", "https://reactjs.org/");
		list.add(map4);

		Map<String, String> map5 = new HashMap<>(16);
		map5.put("logo", "https://gw.alipayobjects.com/zos/rmsportal/dURIMkkrRFpPgTuzkwnB.png");
		map5.put("title", "Ant Design");
		map5.put("description", "蚂蚁金服体验技术部经过大量的项目实践和总结，沉淀出设计语言 Ant Design，这可不单纯只是设计原则、控件规范和视觉尺寸，还配套有前端代码实现方案。也就是说采用Ant Design后，UI设计和前端界面研发可同步完成，效率大大提升。");
		map5.put("member", "Chill");
		map5.put("href", "https://ant.design/docs/spec/introduce-cn");
		list.add(map5);

		Map<String, String> map6 = new HashMap<>(16);
		map6.put("logo", "https://gw.alipayobjects.com/zos/rmsportal/sfjbOqnsXXJgNCjCzDBL.png");
		map6.put("title", "Ant Design Pro");
		map6.put("description", "Ant Design Pro 是一个企业级开箱即用的中后台前端/设计解决方案。符合阿里追求的'敏捷的前端+强大的中台'的思想。");
		map6.put("member", "Chill");
		map6.put("href", "https://pro.ant.design");
		list.add(map6);

		return R.data(list);
	}

	/**
	 * 获取我的消息
	 *
	 * @return
	 */
	@GetMapping("/my-notices")
	@ApiOperation(value = "消息", notes = "消息", position = 9)
	public R myNotices() {
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map1 = new HashMap<>(16);
		map1.put("id", "000000001");
		map1.put("avatar", "https://gw.alipayobjects.com/zos/rmsportal/ThXAXghbEsBCCSDihZxY.png");
		map1.put("title", "你收到了 14 份新周报");
		map1.put("datetime", "2018-08-09");
		map1.put("type", "notification");
		list.add(map1);

		Map<String, String> map2 = new HashMap<>(16);
		map2.put("id", "000000002");
		map2.put("avatar", "https://gw.alipayobjects.com/zos/rmsportal/OKJXDXrmkNshAMvwtvhu.png");
		map2.put("title", "你推荐的 曲妮妮 已通过第三轮面试");
		map2.put("datetime", "2018-08-08");
		map2.put("type", "notification");
		list.add(map2);


		Map<String, String> map3 = new HashMap<>(16);
		map3.put("id", "000000003");
		map3.put("avatar", "https://gw.alipayobjects.com/zos/rmsportal/fcHMVNCjPOsbUGdEduuv.jpeg");
		map3.put("title", "曲丽丽 评论了你");
		map3.put("description", "描述信息描述信息描述信息");
		map3.put("datetime", "2018-08-07");
		map3.put("type", "message");
		map3.put("clickClose", "true");
		list.add(map3);


		Map<String, String> map4 = new HashMap<>(16);
		map4.put("id", "000000004");
		map4.put("avatar", "https://gw.alipayobjects.com/zos/rmsportal/fcHMVNCjPOsbUGdEduuv.jpeg");
		map4.put("title", "朱偏右 回复了你");
		map4.put("description", "这种模板用于提醒谁与你发生了互动，左侧放『谁』的头像");
		map4.put("type", "message");
		map4.put("datetime", "2018-08-07");
		map4.put("clickClose", "true");
		list.add(map4);


		Map<String, String> map5 = new HashMap<>(16);
		map5.put("id", "000000005");
		map5.put("title", "任务名称");
		map5.put("description", "任务需要在 2018-01-12 20:00 前启动");
		map5.put("extra", "未开始");
		map5.put("status", "todo");
		map5.put("type", "event");
		list.add(map5);

		return R.data(list);
	}

}
