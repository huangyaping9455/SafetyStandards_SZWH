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
package org.springblade.anbiao.qiyeshouye.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 实体类
 * @author hyp
 */
@Data
@TableName("anbiao_dept_wechat_info")
@ApiModel(value = "AnBiaoDeptWechatInfo对象", description = "AnBiaoDeptWechatInfo对象")
public class AnBiaoDeptWechatInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private Integer id;

	@ApiModelProperty(value = "微信群ID")
	private String weChatCode;

	@ApiModelProperty(value = "微信群名称")
	private String weChatName;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "是否删除")
	private int isdelete = 0;

	@ApiModelProperty(value = "创建时间")
	private String createtime;

	@ApiModelProperty(value = "更新时间")
	private String updatetime;

	@ApiModelProperty(value = "更新用户ID")
	private int updateuser;

	@ApiModelProperty(value = "报表推送")
	private String reportforms;

	@ApiModelProperty(value = "报警类型")
	private String alarmtype;

	@ApiModelProperty(value = "提醒语")
	private String reminder;

	@ApiModelProperty(value = "报警等级")
	private String alarmlevel;

	@ApiModelProperty(value = "报警配置list")
	private List<AnBiaoDeptWechatRemark> anBiaoDeptWechatRemarkList;

}
