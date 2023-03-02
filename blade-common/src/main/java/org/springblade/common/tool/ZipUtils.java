package org.springblade.common.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author hyp
 * @create 2023-02-24 12:04
 */
public class ZipUtils {
	/**
	 * 压缩文件或文件夹（包括所有子目录文件）
	 *
	 * @param sourceFile 源文件
	 * @param format 格式（zip或rar）
	 * @throws IOException 异常信息
	 */
	public static void zipFileTree(File sourceFile, String format) throws IOException {
		ZipOutputStream zipOutputStream = null;
		try {
			String zipFileName;
			if (sourceFile.isDirectory()) { // 目录
				zipFileName = sourceFile.getParent() + File.separator + sourceFile.getName() + "."
					+ format;
			} else { // 单个文件
				zipFileName = sourceFile.getParent()
					+ sourceFile.getName().substring(0, sourceFile.getName().lastIndexOf("."))
					+ "." + format;
			}
			// 压缩输出流
			zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
			zip(sourceFile, zipOutputStream, "");
		} finally {
			if (null != zipOutputStream) {
				// 关闭流
				try {
					zipOutputStream.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 递归压缩文件
	 *
	 * @param file 当前文件
	 * @param zipOutputStream 压缩输出流
	 * @param relativePath 相对路径
	 * @throws IOException IO异常
	 */
	private static void zip(File file, ZipOutputStream zipOutputStream, String relativePath)
		throws IOException {

		FileInputStream fileInputStream = null;
		try {
			if (file.isDirectory()) { // 当前为文件夹
				// 当前文件夹下的所有文件
				File[] list = file.listFiles();
				if (null != list) {
					// 计算当前的相对路径
					relativePath += (relativePath.length() == 0 ? "" : "/") + file.getName();
					// 递归压缩每个文件
					for (File f : list) {
						zip(f, zipOutputStream, relativePath);
					}
				}
			} else { // 压缩文件
				// 计算文件的相对路径
				relativePath += (relativePath.length() == 0 ? "" : "/") + file.getName();
				// 写入单个文件
				zipOutputStream.putNextEntry(new ZipEntry(relativePath));
				fileInputStream = new FileInputStream(file);
				int readLen;
				byte[] buffer = new byte[1024];
				while ((readLen = fileInputStream.read(buffer)) != -1) {
					zipOutputStream.write(buffer, 0, readLen);
				}
				zipOutputStream.closeEntry();
			}
		} finally {
			// 关闭流
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}


	public static void main(String[] args) throws Exception {
		String path = "D:/ExcelTest";
		String format = "zip";
		zipFileTree(new File(path), format);
	}


}
