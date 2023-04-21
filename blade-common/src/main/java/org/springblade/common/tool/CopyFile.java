package org.springblade.common.tool;

import java.io.*;

/**
 * @author hyp
 * //复制一个文件到另一个目录
 * @create 2022-08-05 14:17
 */
public class CopyFile {

	public static void main(String[] args) {
		//需要复制的文件
		File f=new File("D:\\BS\\static\\SafetyStandards\\标准化相关文件\\子模板文件\\清远市清城区丰烨物流有限公司\\1.安全目标\\1.1安全工作方针与目标\\1.1.1.安全工作方针与目标\\关于安全教育对接研讨会议_20220505172757.pdf");
		//复制的文件
		File f2=new File("D:\\BS\\static\\SafetyStandards\\标准化相关文件\\pdf文件\\清远市清城区丰烨物流有限公司\\1.安全目标\\1.1安全工作方针与目标\\1.1.1.安全工作方针与目标\\关于安全教育对接研讨会议_20220505172757.pdf");
		method1(f,f2);
//		method2(f,f2);
	}

	//方法一，以字节流方式
	//如果输出输入流的创建不在try()里，记得使用close()方法来关闭，否则会造成资源的浪费
	public static void method1(File f,File f2){
		try (
			FileInputStream fis=new FileInputStream(f);//创建字节输入流
			FileOutputStream fos=new FileOutputStream(f2);//创建字节输出流
		){
			byte[] all=new byte[(int)f.length()];
			fis.read(all);//读取文件数据
			fos.write(all);//写入文件数据
			System.out.println("复制完成");
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	//方法二，以字符流方式
	public static void method2(File f,File f2){
		try (
			FileReader fr=new FileReader(f);//创建字符输入流
			FileWriter fw=new FileWriter(f2)//创建字符输出流
		){
			char[] all= new char[(int) f.length()];
			fr.read(all);//读取文件数据
			fw.write(all);//写入文件数据
			System.out.println("复制完成");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
