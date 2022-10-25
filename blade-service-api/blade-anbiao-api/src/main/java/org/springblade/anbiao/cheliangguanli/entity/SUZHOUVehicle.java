package org.springblade.anbiao.cheliangguanli.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SUZHOUVehicle对象", description = "SUZHOUVehicle对象")
public class SUZHOUVehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业Id")
    private String deptId;

    @ApiModelProperty(value = "企业名称")
    private String deptName;

    @ApiModelProperty(value = "车辆ID")
    private String cheliangid;

    @ApiModelProperty(value = "车辆牌照")
    private String cheliangpaizhao;

    @ApiModelProperty(value = "公司地址")
    private String jingyingdizhi;

    @ApiModelProperty(value = "公司电话")
    private String lianxidianhua;

    @ApiModelProperty(value = "车牌颜色")
    private String chepaiyanse;

    @ApiModelProperty(value = "使用性质")
    private String shiyongxingzhi;

    @ApiModelProperty(value = "驾驶员id")
    private String jiashiyuanid;

    @ApiModelProperty(value = "运输证号")
    private String jieboyunshuzhenghao;

    @ApiModelProperty(value = "驾驶员名称")
    private String jiashiyuanxingming = "";

    @ApiModelProperty(value = "驾驶员电话")
    private String jiashiyuandianhua = "";

    @ApiModelProperty(value = "年龄")
    private Integer age = 0;

    @ApiModelProperty(value = "身份证号")
    private String shenfenzhenghao;

    @ApiModelProperty(value = "驾驶证号")
    private String jiashizhenghao;

    public String getCheliangid() {
        return cheliangid;
    }

    public void setCheliangid(String cheliangid) {
        this.cheliangid = cheliangid;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCheliangpaizhao() {
        return cheliangpaizhao;
    }

    public void setCheliangpaizhao(String cheliangpaizhao) {
        this.cheliangpaizhao = cheliangpaizhao;
    }

    public String getJingyingdizhi() {
        return jingyingdizhi;
    }

    public void setJingyingdizhi(String jingyingdizhi) {
        this.jingyingdizhi = jingyingdizhi;
    }

    public String getLianxidianhua() {
        return lianxidianhua;
    }

    public void setLianxidianhua(String lianxidianhua) {
        this.lianxidianhua = lianxidianhua;
    }

    public String getChepaiyanse() {
        return chepaiyanse;
    }

    public void setChepaiyanse(String chepaiyanse) {
        this.chepaiyanse = chepaiyanse;
    }

    public String getShiyongxingzhi() {
        return shiyongxingzhi;
    }

    public void setShiyongxingzhi(String shiyongxingzhi) {
        this.shiyongxingzhi = shiyongxingzhi;
    }

    public String getJiashiyuanid() {
        return jiashiyuanid;
    }

    public void setJiashiyuanid(String jiashiyuanid) {
        this.jiashiyuanid = jiashiyuanid;
    }

    public String getJieboyunshuzhenghao() {
        return jieboyunshuzhenghao;
    }

    public void setJieboyunshuzhenghao(String jieboyunshuzhenghao) {
        this.jieboyunshuzhenghao = jieboyunshuzhenghao;
    }

    public String getJiashiyuanxingming() {
        return jiashiyuanxingming;
    }

    public void setJiashiyuanxingming(String jiashiyuanxingming) {
        this.jiashiyuanxingming = jiashiyuanxingming;
    }

    public String getJiashiyuandianhua() {
        return jiashiyuandianhua;
    }

    public void setJiashiyuandianhua(String jiashiyuandianhua) {
        this.jiashiyuandianhua = jiashiyuandianhua;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getShenfenzhenghao() {
        return shenfenzhenghao;
    }

    public void setShenfenzhenghao(String shenfenzhenghao) {
        this.shenfenzhenghao = shenfenzhenghao;
    }

    public String getJiashizhenghao() {
        return jiashizhenghao;
    }

    public void setJiashizhenghao(String jiashizhenghao) {
        this.jiashizhenghao = jiashizhenghao;
    }
}
