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
package org.springblade.anbiao.cheliangguanli.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 */
@Data
@TableName("anbiao_guacheziliao")
@ApiModel(value = "Guacheziliao对象", description = "Guacheziliao对象")
public class Guacheziliao implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
	@TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 单位ID
     */
    @ApiModelProperty(value = "单位ID",required = true)
    private Integer deptId;
    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String caozuoren;
    /**
     * 操作人ID
     */
    @ApiModelProperty(value = "操作人ID")
    private Integer caozuorenid;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private String caozuoshijian;
    /**
     * 车辆牌照
     */
    @ApiModelProperty(value = "车辆牌照", required = true)
    private String cheliangpaizhao;
    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车牌颜色", required = true)
    private String chepaiyanse;
    /**
     * 车头牌照
     */
    @ApiModelProperty(value = "车头牌照")
    private String chetoupaizhao;
    /**
     * 照片
     */
    @ApiModelProperty(value = "照片")
    private String zhaopian;
    /**
     * 车辆类型
     */
    @ApiModelProperty(value = "车辆类型")
    private String cheliangleixing;
    /**
     * 使用性质
     */
    @ApiModelProperty(value = "使用性质")
    private String shiyongxingzhi;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private String xinghao;
    /**
     * 车架号
     */
    @ApiModelProperty(value = "车架号")
    private String chejiahao;
    /**
     * 轮胎规格
     */
    @ApiModelProperty(value = "轮胎规格")
    private String luntaiguige;
    /**
     * 车身颜色
     */
    @ApiModelProperty(value = "车身颜色")
    private String cheshenyanse;
    /**
     * 挂车核定吨位
     */
    @ApiModelProperty(value = "挂车核定吨位")
    private String hedingdunwei;
    /**
     * 车辆经营者
     */
    @ApiModelProperty(value = "车辆经营者")
    private String jingyingzhe;
    /**
     * 责任经营者电话
     */
    @ApiModelProperty(value = "责任经营者电话")
    private String jingyingzhedianhua;
    /**
     * 营运年限
     */
    @ApiModelProperty(value = "营运年限")
    private String yingyunnianxian;
    /**
     * 登记证书编号
     */
    @ApiModelProperty(value = "登记证书编号")
    private String dengjizhengshu;
    /**
     * 内部外部车辆
     */
    @ApiModelProperty(value = "内部外部车辆")
    private String neiwaibucheliang;
    /**
     * 车辆来源
     */
    @ApiModelProperty(value = "车辆来源")
    private String chelianglaiyuan;
    /**
     * 注册登记时间
     */
    @ApiModelProperty(value = "注册登记时间")
    private String zhuceshijian;
    /**
     * 车辆入户时间
     */
    @ApiModelProperty(value = "车辆入户时间")
    private String ruhushijian;
    /**
     * 车辆过户时间
     */
    @ApiModelProperty(value = "车辆过户时间")
    private String guohushijian;
    /**
     * 车辆退市时间
     */
    @ApiModelProperty(value = "车辆退市时间", required = true)
    private String tuishishijian;
    /**
     * 接驳运输证号
     */
    @ApiModelProperty(value = "接驳运输证号")
    private String jieboyunshuzheng;
    /**
     * 强制报废日期
     */
    @ApiModelProperty(value = "强制报废日期", required = true)
    private String baofeiriqi;
    /**
     * 原车辆牌照
     */
    @ApiModelProperty(value = "原车辆牌照")
    private String yuancheliangpaizhao;
    /**
     * 车辆状态
     */
    @ApiModelProperty(value = "车辆状态")
    private String cheliangzhuangtai;
    /**
     * GPS安装时间
     */
    @ApiModelProperty(value = "GPS安装时间", required = true)
    @TableField("GPSanzhuangshijian")
  private String GPSanzhuangshijian;
    /**
     * 车辆停放地区
     */
    @ApiModelProperty(value = "车辆停放地区")
    private String cheliangtingfangqu;
    /**
     * 档案号
     */
    @ApiModelProperty(value = "档案号")
    private String danganhao;
    /**
     * 备用车辆
     */
    @ApiModelProperty(value = "备用车辆")
    private String beiyongcheliang;
    /**
     * 厂牌
     */
    @ApiModelProperty(value = "厂牌")
    private String changpai;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String beizhu;
    /**
     * 机动车驾驶证
     */
    @ApiModelProperty(value = "机动车驾驶证")
    private String jiashizheng;
    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String fujian;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Integer isdelete;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间",required = true)
	private String createtime;
}
