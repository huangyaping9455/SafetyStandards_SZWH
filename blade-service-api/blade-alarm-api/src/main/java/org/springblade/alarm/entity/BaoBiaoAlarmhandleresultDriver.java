/**
 * Copyright (C), 2015-2021
 * FileName: BaoBiaoAlarmhandleresultDriver
 * Author:   呵呵哒
 * Date:     2021/8/20 14:42
 * Description:
 */
package org.springblade.alarm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2021/8/20
 * @描述
 */
@Data
@ApiModel(value = "BaoBiaoAlarmhandleresultDriver对象", description = "BaoBiaoAlarmhandleresultDriver对象")
@TableName("baobiao_alarmhandleresult_driver")
public class BaoBiaoAlarmhandleresultDriver implements Serializable {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "驾驶员ID")
    private String driver_id;

    @ApiModelProperty(value = "报警ID")
    private String alarm_id;

    @ApiModelProperty(value = "报警类型")
    private String alarm_type;

    @ApiModelProperty(value = "课程ID")
    private String course_id;

    @ApiModelProperty(value = "操作人")
    private String caozuoren;

    @ApiModelProperty(value = "操作人ID")
    private String caozuorenid;

    @ApiModelProperty(value = "操作时间")
    private String caozuoshijian;

    @ApiModelProperty(value = "教育平台驾驶员ID")
    private Integer study_driver_id;

    @ApiModelProperty(value = "学习状态（0：未学习，1：学习中，2：学习完成）")
    private Integer status;

    @ApiModelProperty(value = "待办数")
    private Integer shu;

    @ApiModelProperty(value = "教育平台驾驶员ID(用于展示)")
    private Integer studydriverid;

    @ApiModelProperty(value = "驾驶员ID(用于展示)")
    private String driverid;

    @ApiModelProperty(value = "报警类型(用于展示)")
    private String alarmtype;

    @ApiModelProperty(value = "企业ID")
    private Integer dept_id;

    public Integer getDept_id() {
        return dept_id;
    }

    public void setDept_id(Integer dept_id) {
        this.dept_id = dept_id;
    }

    public Integer getStudydriverid() {
        return studydriverid;
    }

    public void setStudydriverid(Integer studydriverid) {
        this.studydriverid = studydriverid;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getAlarmtype() {
        return alarmtype;
    }

    public void setAlarmtype(String alarmtype) {
        this.alarmtype = alarmtype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(String alarm_id) {
        this.alarm_id = alarm_id;
    }

    public String getAlarm_type() {
        return alarm_type;
    }

    public void setAlarm_type(String alarm_type) {
        this.alarm_type = alarm_type;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCaozuoren() {
        return caozuoren;
    }

    public void setCaozuoren(String caozuoren) {
        this.caozuoren = caozuoren;
    }

    public String getCaozuorenid() {
        return caozuorenid;
    }

    public void setCaozuorenid(String caozuorenid) {
        this.caozuorenid = caozuorenid;
    }

    public String getCaozuoshijian() {
        return caozuoshijian;
    }

    public void setCaozuoshijian(String caozuoshijian) {
        this.caozuoshijian = caozuoshijian;
    }

    public Integer getStudy_driver_id() {
        return study_driver_id;
    }

    public void setStudy_driver_id(Integer study_driver_id) {
        this.study_driver_id = study_driver_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShu() {
        return shu;
    }

    public void setShu(Integer shu) {
        this.shu = shu;
    }
}


