package org.springblade.common.tool.doc.util;


import java.io.File;

/**
 * 文件操作工具类
 * @author hyp
 * @create 2022-08-20 14:52
 */
public class FileUtils {
	public static boolean isFile(String filepath) {
		File f = new File(filepath);
		return f.exists() && f.isFile();
	}
	public static boolean isDir(String dirPath) {
		File f = new File(dirPath);
		return f.exists() && f.isDirectory();
	}
	/**
	 * 创建多级目录
	 * @param path
	 */
	public static void makeDirs(String path) {
		File file = new File(path);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}else {
			System.out.println("创建目录失败："+path);
		}
	}
}
