/**
 * Copyright (C), 2015-2020,
 * FileName: IXinXiJiaoHuZhuService
 * Author:   呵呵哒
 * Date:     2020/6/20 17:18
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.lawsRegulations.page.AnBiaoLawsRegulationsPage;
import org.springblade.anbiao.qiyeshouye.entity.*;
import org.springblade.anbiao.qiyeshouye.page.AnBiaoDeptWechatInfoPage;
import org.springblade.anbiao.qiyeshouye.page.AnBiaoWeeksHiddenTroublePage;
import org.springblade.anbiao.qiyeshouye.page.QiYeShouYePage;
import org.springblade.anbiao.zhengfu.entity.ZhengFuBaoJingTongJiLv;
import org.springblade.system.entity.Dept;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/7/4
 * @描述
 */
public interface IQiYeShouYeService extends IService<QiYeShouYe> {

	/**
	 * 根据企业ID获取下级所有企业（企业、个体）
	 * @param deptId
	 * @return
	 */
	List<Dept> QiYeList(@Param("deptId") Integer deptId);

	/**
	 * 本月车辆情况
	 *
	 * @param deptId
	 * @return
	 */
	QiYeShouYe selectMonthVehcile(@Param("deptId") String deptId);

	/**
	 * 报警统计（年）
	 *
	 * @param deptId
	 * @return
	 */
	QiYeShouYe selectYearAlarm(@Param("deptId") String deptId, @Param("year") String year);

	/**
	 * 处理趋势图
	 *
	 * @param deptId
	 * @return
	 */
	List<QiYeShouYe> selectYearAlarmTendency(@Param("deptId") String deptId, @Param("year") String year);

	/**
	 * 近七天报警统计
	 *
	 * @param deptId
	 * @return
	 */
	QiYeShouYe selectSevenAlarmStatistics(@Param("deptId") String deptId) throws UnsupportedEncodingException;

	/**
	 * 运维端首页统计数据
	 *
	 * @param deptId
	 * @return
	 */
	List<QiYeYunWeiShouYe> selectOperational(@Param("deptId") String deptId);

	/**
	 * 首页安全达标提醒
	 *
	 * @param deptId
	 * @return
	 */
	QiYeYunWeiShouYe selectMarkRemind(@Param("deptId") String deptId);

	/**
	 * 企业数
	 *
	 * @return
	 */
	QiYeYunWeiShouYe selectQiYeCount();

	/**
	 * 安标企业数
	 *
	 * @return
	 */
	QiYeYunWeiShouYe selectABQiYeCount();

	/**
	 * 待办提醒
	 *
	 * @param deptId
	 * @param dateTime
	 * @return
	 */
	List<QiYeAnBiao> selectScheduleReminders(@Param("deptId") String deptId, @Param("dateTime") String dateTime);

	/**
	 * 安标目录得分对比表
	 *
	 * @return
	 */
	List<QiYeAnBiaoMuLu> selectQiYeAnBiaoMuLu(@Param("deptId") String deptId);

	/**
	 * 安标周期评分达标率
	 *
	 * @param deptId
	 * @return
	 */
	QiYeAnBiaoPeriodRate selectPeriodControlRates(@Param("deptId") String deptId);

	/**
	 * 安全提示
	 *
	 * @param deptId
	 * @return
	 */
	List<QiYeAnBiaoSafetyTips> selectSafetyTips(@Param("deptId") String deptId);

	/**
	 * 根据不达标项ID查询子小项总数
	 *
	 * @param deptId xiangId
	 * @return
	 */
	QiYeAnBiaoSafetyTips selectSafetyTipsZNum(@Param("deptId") String deptId, @Param("xiangId") Integer xiangId);

	/**
	 * 根据不达标项ID查询不达标子小项数
	 *
	 * @param deptId xiangId
	 * @return
	 */
	QiYeAnBiaoSafetyTips selectSafetyTipsNum(@Param("deptId") String deptId, @Param("xiangId") Integer xiangId);

	/**
	 * 查询企业是否有生成安全标准化
	 *
	 * @param deptId
	 * @return
	 */
	int selectGetByIdOnDeptId(@Param("deptId") String deptId);

	/**
	 * 添加学习记录
	 *
	 * @param personLearnInfo
	 * @return
	 */
	int insertSelective(PersonLearnInfo personLearnInfo);

	/**
	 * 根据相关信息查询学习记录
	 *
	 * @param lyear
	 * @param lmonth
	 * @param username
	 * @param deptname
	 * @return
	 */
	PersonLearnInfo getByName(@Param("lyear") Integer lyear,
							  @Param("lmonth") Integer lmonth,
							  @Param("username") String username,
							  @Param("deptname") String deptname);

	/**
	 * 更新学习记录
	 *
	 * @param personLearnInfo
	 * @return
	 */
	int updateSelective(PersonLearnInfo personLearnInfo);

	/**
	 * 企业查询学习列表
	 *
	 * @param qiYeShouYePage
	 * @return
	 */
	QiYeShouYePage<PersonLearnInfo> selectPersonLearnInfoAll(QiYeShouYePage qiYeShouYePage);

	/**
	 * 政府查询学习列表
	 *
	 * @param qiYeShouYePage
	 * @return
	 */
	QiYeShouYePage<PersonLearnInfo> selectZFPersonLearnInfoAll(QiYeShouYePage qiYeShouYePage);

	/**
	 * 政府查询学习统计列表
	 *
	 * @param qiYeShouYePage
	 * @return
	 */
	QiYeShouYePage<PersonLearnInfo> selectZFPersonLearnCoutAll(QiYeShouYePage qiYeShouYePage);

	/**
	 * 添加线下学习记录
	 * @param personLearnRemark
	 * @return
	 */
	int insertOffLearnSelective(PersonLearnRemark personLearnRemark);

	/**
	 * 编辑线下学习记录
	 * @param personLearnRemark
	 * @return
	 */
	int updateOffLearnSelective(PersonLearnRemark personLearnRemark);

	/**
	 * 根据ID查询线下学习详情
	 * @param Id
	 * @return
	 */
	PersonLearnRemark selectByIds(Integer Id);

	/**
	 * 企业查询线下学习记录统计
	 * @param qiYeShouYePage
	 * @return
	 */
	QiYeShouYePage<PersonLearnRemark> selectPersonLearnRemarkAll(QiYeShouYePage qiYeShouYePage);

	/**
	 * 新增微信消息推送配置
	 * @param anBiaoDeptWechatInfo
	 * @return
	 */
	boolean insertDeptWechatSelective(AnBiaoDeptWechatInfo anBiaoDeptWechatInfo);

	/**
	 * 根据数据ID更新微信消息推送配置
	 * @param anBiaoDeptWechatInfo
	 * @return
	 */
	boolean updateDeptWechatSelective(AnBiaoDeptWechatInfo anBiaoDeptWechatInfo);

	/**
	 * 微信消息推送配置列表
	 * @param anBiaoDeptWechatInfoPage
	 * @return
	 */
	AnBiaoDeptWechatInfoPage<AnBiaoDeptWechatInfo> selectDeptWechatGetAll(AnBiaoDeptWechatInfoPage anBiaoDeptWechatInfoPage);

	/**
	 * 根据数据ID删除微信消息推送配置
	 * @param updateTime
	 * @param updateUser
	 * @param Id
	 * @return
	 */
	boolean deleteDeptWechatInfo(@RequestParam("updateTime") String updateTime,
								 @RequestParam("updateUser") Integer updateUser,
								 @RequestParam("Id") Integer Id);

	/**
	 * 根据数据ID查询微信消息推送配置详情
	 * @return
	 */
	AnBiaoDeptWechatInfo selectDeptWechatInfoById(@Param("id") Integer id,
												  @Param("weChatCode") String weChatCode,
												  @Param("deptId") String deptId);

	/**
	 * 获取微信消息推送配置最大数据ID
	 * @return
	 */
	int selectMaxId();

	/**
	 * 更新微信配置详情数据
	 * @param anBiaoDeptWechatRemark
	 * @return
	 */
	boolean insertDeptWechatRemarkSelective(AnBiaoDeptWechatRemark anBiaoDeptWechatRemark);

	/**
	 * 更新微信配置详情数据
	 * @param anBiaoDeptWechatRemark
	 * @return
	 */
	boolean updateDeptWechatRemarkSelective(AnBiaoDeptWechatRemark anBiaoDeptWechatRemark);

	/**
	 * 根据数据ID删除微信消息推送配置详情
	 * @param updateTime
	 * @param updateUser
	 * @param Id
	 * @return
	 */
	boolean deleteDeptWechatRemark(@RequestParam("updateTime") String updateTime,
								   @RequestParam("updateUser") Integer updateUser,
								   @RequestParam("Id") Integer Id);

	/**
	 * 根据微信配置ID查询微信消息推送配置详情
	 * @return
	 */
	List<AnBiaoDeptWechatRemark> selectDeptWechatRemarkById(@Param("Id") Integer Id,
													  @Param("alarmtype") String alarmtype,
													  @Param("reminder") String reminder);

	/**
	 * 添加周隐患数据
	 * @param anBiaoWeeksHiddenTrouble
	 * @return
	 */
	boolean insertWeeksHiddenTroubleSelective(AnBiaoWeeksHiddenTrouble anBiaoWeeksHiddenTrouble);

	/**
	 * 编辑周隐患数据
	 * @param anBiaoWeeksHiddenTrouble
	 * @return
	 */
	boolean updateWeeksHiddenTroubleSelective(AnBiaoWeeksHiddenTrouble anBiaoWeeksHiddenTrouble);

	/**
	 * 根据数据ID获取周隐患数据详情
	 * @param Id
	 * @return
	 */
	AnBiaoWeeksHiddenTrouble selectWeeksHiddenTroubleById(@Param("Id") Integer Id,@Param("type") Integer type,@Param("deptId") Integer deptId,
														  @Param("vehId") String vehId,@Param("bignDate") String bignDate,@Param("endDate") String endDate);

	/**
	 * 周隐患列表
	 * @param anBiaoWeeksHiddenTroublePage
	 * @return
	 */
	AnBiaoWeeksHiddenTroublePage<AnBiaoWeeksHiddenTrouble> selectWeeksHiddenTroubleGetAll(AnBiaoWeeksHiddenTroublePage anBiaoWeeksHiddenTroublePage);

	/**
	 * 违规违章行为分析
	 * @param deptId
	 * @param date
	 * @return
	 */
	ZhengFuBaoJingTongJiLv selectMonthAlarm(Integer deptId, String date);
}
