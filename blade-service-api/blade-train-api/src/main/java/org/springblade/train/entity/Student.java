package org.springblade.train.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 学员
 */
@Data
@TableName("base_student")
public class Student implements Serializable {

    private static final long serialVersionUID = 781714036684042636L;

    /**
     * //主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学员姓名
     */
    @TableField("realname")
    private String realName;

    /**
     * 登录名
     */
    @TableField("username")
    private String userName;

    /**
     * 微信登录OpenID
     */
    @TableField("open_id")
    private String openId;

    /**
     * 性别 0:未知 1:男 2:女
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 正面照
     */
    @TableField("full_face_photo")
    private String fullFacePhoto;

    /**
     * 左侧面照
     */
    @TableField("left_face_photo")
    private String leftFacePhoto;

    /**
     * 右侧面照
     */
    @TableField("right_face_photo")
    private String rightFacePhoto;


    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 手机号码
     */
    @TableField("cellphone")
    private String cellphone;

    /**
     * 身份证号码
     */
    @TableField("identify_number")
    private String identifyNumber;

    /**
     * 所属企业
     */
    @TableField("unit_id")
    private Integer unitId;

    /**
     *  所属企业名称
     */
    @TableField(exist = false)
    private String unitName;

    /**
     * 所属部门
     */
    @TableField("department_name")
    private String departmentName;

    /**
     * 岗位
     */
    @TableField("station")
    private String station;

    /**
     * 车牌号码
     */
    @TableField("plate_number")
    private String plateNumber;

    /**
     * 运输资格证号
     */
    @TableField("trans_permit_code")
    private String transPermitCode;

    /**
     * 区域
     */
    @TableField("area_id")
    private Integer areaId;

    /**
     * 区域名称
     */
    @TableField(exist = false)
    private String areaName;

    /**
     * 服务商
     */
    @TableField("server_id")
    private Integer serverId;

    /**
     * 服务商名称
     */
    @TableField(exist = false)
    private String ServerName;

    /**
     * 状态 未审核-0，审核通过-1，审核不通过-2
     */
    @TableField("status")
    private Integer status;

    /**
     * 删除标识 删除为1，默认为0
     */
    @TableField("deleted")
    private Integer deleted;

    /**
     * 更新人
     */
    @TableField("updated_by")
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField("updated_time")
    private Date updatedTime;

    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;


    /**
     * 微信code
     */
    @TableField(exist = false)
    private String wxCode;

    /**
     * 住址
     */
    @TableField("address")
    private String address;

    /**
     * 民族
     */
    @TableField("nation")
    private String nation;

    /**
     * 准驾类型
     */
    @TableField("drive_class")
    private String driveClass;

    /**
     * 初次领证日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("first_license_date")
    private Date firstLicenseDate;

    /**
     * 身份证照片
     */
    @TableField("identify_photo")
    private String identifyPhoto;

    /**
     * 资格证照片
     */
    @TableField("qualification_photo")
    private String qualificationPhoto;

    /**
     * 学习周期开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("study_period_begin")
    private Date studyPeriodBegin;

    /**
     * 学习周期结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("study_period_end")
    private Date studyPeriodEnd;

    /**
     * 文化程度(1-小学，2-初中，3-中专，4-高中，5-大专，6-本科，7-硕士，8-博士)
     */
    @TableField("education")
    private Integer education;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("birthday")
    private Date birthday;

	/**
	 * 数量
	 */
	@TableField(exist = false)
	private Integer num = 0;
}
