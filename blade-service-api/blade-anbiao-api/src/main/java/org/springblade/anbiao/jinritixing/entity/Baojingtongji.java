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
package org.springblade.anbiao.jinritixing.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 * @author 呵呵哒
 */
@Data
@ApiModel(value = "Baojingtongji对象", description = "Baojingtongji对象")
public class Baojingtongji implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 统计日期
     */
    @ApiModelProperty(value = "统计日期",required = true)
    private String tongjiriqi;
    /**
     * 单位名称
     */
    @ApiModelProperty(value = "单位名称",required = true)
    private String company;
    /**
     * 总数量
     */
    @ApiModelProperty(value = "总数量")
    private Integer counts;
    /**
     * 超速
     */
    @ApiModelProperty(value = "超速")
    private Integer chaosu;
    /**
     * 疲劳
     */
    @ApiModelProperty(value = "疲劳")
    private Integer pilao;
    /**
     * 夜间
     */
    @ApiModelProperty(value = "夜间")
    private Integer yejian;
    /**
     * 异常
     */
    @ApiModelProperty(value = "异常")
    private Integer yichang;

	/**
	 * 无数据
	 */
	@ApiModelProperty(value = "无数据")
	private Integer wushuju;
	/**
	 * 不定位
	 */
	@ApiModelProperty(value = "不定位")
	private Integer budingwei;
	/**
	 * 其他
	 */
	@ApiModelProperty(value = "其他")
	private Integer qita;


}
