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
package org.springblade.anbiao.guanlijigouherenyuan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 实体类
 */
@Data
@ApiModel(value = "OrganizationsFuJian对象", description = "OrganizationsFuJian对象")
public class OrganizationsFuJian implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "单位ID",required = true)
    private String deptId;

	@ApiModelProperty(value = "单位名称")
    private String deptName;

	@ApiModelProperty(value = "道路运输证附件")
	private String daoluyunshuzhengfujian;

	@ApiModelProperty(value = "道路运输证影像附件数")
	private Integer daoluyunshuzhengcount = 0;

	@ApiModelProperty(value = "经营许可证附件")
	private String jingyingxukezhengfujian;

	@ApiModelProperty(value = "经营许可证影像附件数")
	private Integer jingyingxukezhengcount = 0;

	@ApiModelProperty(value = "工商营业执照附件")
	private String yingyezhizhaofujian;

	@ApiModelProperty(value = "工商营业执照影像附件数")
	private Integer yingyezhizhaocount = 0;

	@ApiModelProperty(value = "岗位id")
	private String postId;

	@ApiModelProperty(value = "岗位名称")
	private String postName;

	@ApiModelProperty(value = "人员ID")
	private String personId;

	@ApiModelProperty(value = "人员姓名")
	private String personName;

	@ApiModelProperty(value = "身份证附件正面")
	private String shenfenzhengfujian;

	@ApiModelProperty(value = "身份证附件反面")
	private String shenfenzhengfanmianfujian;

	@ApiModelProperty(value = "身份证影像附件数")
	private Integer shenfenzhengcount = 0;

	@ApiModelProperty(value = "其他附件证明")
	private String qitazhengmianfujian;

	@ApiModelProperty(value = "其他附件反面")
	private String qitafanmianfujian;

	@ApiModelProperty(value = "其他影像附件数")
	private Integer qitacount = 0;

	@ApiModelProperty(value = "影像附件数")
	private Integer count = 0;

	@ApiModelProperty(value = "岗位影像附件数")
	private List<OrganizationsFuJian> postFuJianList;

	@ApiModelProperty(value = "人员影像附件数")
	private List<OrganizationsFuJian> personnelFuJianList;

}
