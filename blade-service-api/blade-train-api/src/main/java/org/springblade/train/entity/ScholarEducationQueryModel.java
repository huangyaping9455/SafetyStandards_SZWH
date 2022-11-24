package org.springblade.train.entity;

import lombok.Data;

import java.util.List;

/**
 * <p>ClassName: ScholarEducationQueryModel
 * <p>Description: [学者学历查询实体]
*/
@Data
public class ScholarEducationQueryModel {

    
    /**
     * 学员姓名
     */
    private String realName;
    
    /**
     * 登录名
     */
    private String userName;
    
    /**
     * 身份证号码
     */
    private String identifyNumber;
    
    /**
     * 企业id
     */
    private String unitId;

	/**
	 * 企业名称
	 */
	private String unitName;
    
	/**
	 * 权限企业Id
	 */
	private List<Integer> authUnitId;
    
    /**
     * 学员id
     */
    private String studentId;
    
	/**
	 * 页码
	 */
	private Integer pageNo;
	
	/**
	 * 页大小
	 */
	private Integer pageSize;
}
