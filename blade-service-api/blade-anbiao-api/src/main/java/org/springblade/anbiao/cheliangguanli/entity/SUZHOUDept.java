package org.springblade.anbiao.cheliangguanli.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SUZHOUDept对象", description = "SUZHOUDept对象")
public class SUZHOUDept implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业Id")
    private String deptId;

    @ApiModelProperty(value = "企业名称")
    private String deptName;

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
}
