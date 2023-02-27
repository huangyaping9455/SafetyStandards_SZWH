package org.springblade.common.tool;

import org.apache.tools.zip.ZipEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class PackageToZIp {

	public static boolean toZip(String source, String destination) {
		try {
			FileOutputStream out = new FileOutputStream(destination);
			ZipOutputStream zipOutputStream = new ZipOutputStream(out);
			File sourceFile = new File(source);
			// 递归压缩文件夹
			compress(sourceFile, zipOutputStream, sourceFile.getName());
			zipOutputStream.flush();
			zipOutputStream.close();
//			//不删除源文件 就不用调用这个  要删除就调用
//			deleteFile(sourceFile);
		} catch (IOException e) {
			System.out.println("failed to zip compress, exception");
			return false;
		}
		return true;
	}

	public static Boolean deleteFile(File file) {
		//判断文件不为null或文件目录存在
		if (file == null || !file.exists()) {
			System.out.println("文件删除失败,请检查文件是否存在以及文件路径是否正确");
			return false;
		}
		//获取目录下子文件
		File[] files = file.listFiles();
		//遍历该目录下的文件对象
		for (File f : files) {
			//判断子目录是否存在子目录,如果是文件则删除
			if (f.isDirectory()) {
				//递归删除目录下的文件
				deleteFile(f);
			} else {
				//文件删除
				f.delete();
			}
		}
		//文件夹删除
		file.delete();
		return true;
	}

	private static void compress(File sourceFile, ZipOutputStream zos, String name) throws IOException {
		byte[] buf = new byte[1024];
		if (sourceFile.isFile()) {
			// 压缩单个文件，压缩后文件名为当前文件名
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1) {
				zos.write(buf, 0, len);
			}
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				// 空文件夹的处理(创建一个空ZipEntry)
				zos.putNextEntry(new ZipEntry(name + "/"));
				zos.closeEntry();
			} else {
				// 递归压缩文件夹下的文件
				for (File file : listFiles) {
					compress(file, zos, name + "/" + file.getName());
				}
			}
		}
	}
}
