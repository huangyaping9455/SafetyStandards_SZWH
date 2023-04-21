package org.springblade.doc.safetyproductionfile.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author hyp
 * @create 2022-09-22 20:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProgressEntity {

	/**
	 * 读取的文件的比特数
	 */
	private long bytesRead = 0L;

	/**
	 * 文件的总大小
	 */
	private long contentLength = 0L;

	/**
	 * 目前正在读取第几个文件
	 */
	private int items;

	/**
	 *  开始上传时间，用于计算上传速率
	 */
	private long startTime = System.currentTimeMillis();

}

