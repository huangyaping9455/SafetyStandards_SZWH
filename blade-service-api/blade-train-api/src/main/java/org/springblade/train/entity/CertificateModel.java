package org.springblade.train.entity;

import lombok.Data;

import java.util.List;

@Data
public class CertificateModel {

	/**
	 * 登录名
	 */
	private String userName;
	
	/**
	 * 学员姓名
	 */
	private String realName;
	
	/**
	 * 性别 0:未知 1:男 2:女
	 */
	private Integer sex;
	
	/**
	 * 正面照
	 */
	private String fullFacePhoto;
	
	/**
	 * 身份证号码
	 */
	private String identifyNumber;

	/**
	 * 所属企业
	 */
	private Integer unitId;

	/**
	 * 所属企业名称
	 */
	private String unitName;
	
	/**
	 * 岗位
	 */
	private String station;
	
	/**
	 *  学习记录对象
	 */
	private List<StudyRecord> studyRecord;
	
	/**
	 * 抓拍图片对象
	 */
	private List<Snapshot> snapshot;
	
	/**
	 * 学习时长
	 */
	private Integer totaltime=0;
	
	/**
	 * 学完时间
	 */
	private String dateTime;
	
	/**
	 * 考试记录对象
	 */
	private ExamRecord examRecord;
	
	

}
