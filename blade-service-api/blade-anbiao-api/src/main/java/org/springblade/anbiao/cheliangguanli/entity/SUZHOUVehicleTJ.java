package org.springblade.anbiao.cheliangguanli.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SUZHOUVehicleTJ对象", description = "SUZHOUVehicleTJ对象")
public class SUZHOUVehicleTJ implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业Id",required = true)
    private String deptId;

    @ApiModelProperty(value = "企业名称")
    private String deptName;

    @ApiModelProperty(value = "总车辆数",required = true)
    private String sumCount;

    @ApiModelProperty(value = "货运车辆在线数",required = true)
    private String generalCargoCount;

    @ApiModelProperty(value = "危货车辆在线数",required = true)
    private String dangerousGoodsCount;

    @ApiModelProperty(value = "客运车辆在线数",required = true)
    private String passengerCount;

    @ApiModelProperty(value = "其他类型在线数")
    private String otherCount;

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

    public String getSumCount() {
        return sumCount;
    }

    public void setSumCount(String sumCount) {
        this.sumCount = sumCount;
    }

    public String getGeneralCargoCount() {
        return generalCargoCount;
    }

    public void setGeneralCargoCount(String generalCargoCount) {
        this.generalCargoCount = generalCargoCount;
    }

    public String getDangerousGoodsCount() {
        return dangerousGoodsCount;
    }

    public void setDangerousGoodsCount(String dangerousGoodsCount) {
        this.dangerousGoodsCount = dangerousGoodsCount;
    }

    public String getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(String passengerCount) {
        this.passengerCount = passengerCount;
    }

    public String getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(String otherCount) {
        this.otherCount = otherCount;
    }
}
