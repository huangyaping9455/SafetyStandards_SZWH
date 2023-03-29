package org.springblade.common.configurationBean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 呵呵哒
 * @description: nacos动态获取文件路径/url前缀
 * @projectName SafetyStandards
 * @date 2019/5/2211:35
 */
@ConfigurationProperties("fileserver")
@Component
@Data
public class FileServer {

	/**
	 * 物理路径通用前缀
	 */
	private String pathPrefix;
	/**
	 * url通用前缀
	 */
	private String urlPrefix;

	/**
	 * 界定是否需要验证码
	 */
	private String falg;

	/**
	 * gps相关数据地址
	 */
	private String gpsVehiclePath;

	/**
	 * 获取logo
	 */
	private String photoLogo;

	/**
	 * 加密字符串
	 */
	private String key;

	/**
	 * 加密字符串
	 */
	private String videoUrl;

	/**
	 * tts指令地址
	 */
	private String ttsUrl;

	/**
	 * 离线时间（判断车辆在线离线阀值）
	 */
	private int maxOfflineTime;

	/**
	 * 教育数据获取地址
	 */
	private String learnRecordUrl;

	/**
	 * 隐患数据获取地址
	 */
	private String hazardRecordUrl;

	/**
	 * 安全达标分数
	 */
	private int markRemindScore;

	/**
	 * 有为数据地址
	 */
	private String youweiUrl;

	/**
	 * 上线率分值
	 */
	private int onlineRateScore;

	/**
	 * 上线率占比
	 */
	private String onlineRatePercentage;

	/**
	 * 定位率分值
	 */
	private int locateRateScore;

	/**
	 * 定位率占比
	 */
	private String locateRatePercentage;

	/**
	 * 数据合格率分值
	 */
	private int qualifiedRateScore;

	/**
	 * 数据合格率占比
	 */
	private String qualifiedRatePercentage;

	/**
	 * 轨迹完整率分值
	 */
	private int trajectoryIntegrityRateScore;

	/**
	 * 轨迹完整率占比
	 */
	private String trajectoryIntegrityRatePercentage;

	/**
	 * 轨迹漂移率分值
	 */
	private int trajectoryDriftRateScore;

	/**
	 * 轨迹漂移率占比
	 */
	private String trajectoryDriftRatePercentage;

	/**
	 * 是否开启生成隐患数据
	 */
	private String hiddenDanger;

}
