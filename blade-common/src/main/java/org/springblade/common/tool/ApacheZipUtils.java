package org.springblade.common.tool;

/**
 * @author hyp
 *  ZIP导出工具类
 * @create 2023-02-08 17:39
 */

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApacheZipUtils {

	public static void main(String[] args) throws Exception{
//        doCompress("D:\\port","D:\\文件夹1.zip");
		ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream("D:\\ExcelTest\\文件夹2.zip"));
		doCompress1(new ArrayList<String>(){{
			add("D:\\ExcelTest\\导入车辆资料更新模板.xlsx");
			add("D:\\ExcelTest\\导入车辆资料模板.xlsx");
		}},   bizOut);
		//不要忘记调用
		bizOut.close();
	}

	public static void doCompress(String srcFile, String zipFile) throws Exception {
		doCompress(new File(srcFile), new File(zipFile));
	}

	/**
	 * 文件压缩
	 * @param srcFile  目录或者单个文件
	 * @param destFile 压缩后的ZIP文件
	 */
	public static void doCompress(File srcFile, File destFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(destFile));

		if(srcFile.isDirectory()){
			File[] files = srcFile.listFiles();
			for(File file : files){
				doCompress(file, out);
			}
		}else {
			doCompress(srcFile, out);
		}

		out.close();
	}

	public static void doCompress1(List<String> pathnames, ZipOutputStream out) throws IOException {
		for(String path:pathnames){
			doCompress(new File(path), out);
		}
	}

	public static void doCompress(String pathname, ZipOutputStream out) throws IOException{
		doCompress(new File(pathname), out);
	}

	public static void doCompress(File file, ZipOutputStream zipOut) throws IOException{
		if( file.exists() ){
			//读取相关的文件
			FileInputStream fis = new FileInputStream(file);
			zipOut.putNextEntry(new ZipEntry(file.getName()));

			int len = 0 ;
			// 读取文件的内容,打包到zip文件
			byte[] buffer = new byte[1024];
			while ((len = fis.read(buffer)) > 0) {
				zipOut.write(buffer, 0, len);
			}
			zipOut.setEncoding("gbk");//如果不加此句，压缩文件依然可以生成，只是在打开和解压的时候，会显示乱码，但是还是会解压出来
			zipOut.flush();
			zipOut.closeEntry();
			fis.close();
		}
	}
}
