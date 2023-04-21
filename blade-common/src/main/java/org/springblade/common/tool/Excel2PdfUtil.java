package org.springblade.common.tool;

import com.aspose.cells.License;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;

import java.io.*;

/**
 * @author hyp
 * @create 2022-12-21 15:58
 */
public class Excel2PdfUtil {
	/**
	 * excel文件转换为PDF文件
	 * @param Address 需要转化的Excel文件地址，
	 * @param outputPath 转换后的文件地址
	 */
	public static String excel2pdf(String Address,String outputPath) {
		//获取文件名称
		String fileName = Address.substring(Address.lastIndexOf("\\")+1);
		fileName = fileName.substring(0,fileName.lastIndexOf(".")) + ".pdf";
		// 验证License 若不验证则转化出的pdf文档会有水印产生
		if (!getLicense()) {
			return null;
		}
		try {
			FileWriter writer = new FileWriter(outputPath);
			writer.close();
			// 输出路径
			File pdfFile = new File(outputPath);
			FileInputStream excelstream = new FileInputStream(Address);
			// excel路径，这里是先把数据放进缓存表里，然后把缓存表转化成PDF
			Workbook wb = new Workbook(excelstream);
			FileOutputStream fileOS = new FileOutputStream(pdfFile);
			PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
			//参数true把内容放在一张PDF页面上；
			pdfSaveOptions.setOnePagePerSheet(true);
			wb.save(fileOS, pdfSaveOptions);
			fileOS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	//获取认证，去除水印
	public static boolean getLicense() {
		boolean result = false;
		try {
			//这个文件应该是类似于密码验证(证书？)，用于获得去除水印的权限
			InputStream is = Excel2PdfUtil.class.getClassLoader().getResourceAsStream("resources/license.xml");
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {

		excel2pdf("D:\\BS\\static\\SafetyStandards\\安全生产档案\\正式文件\\测试企业\\企业资质档案\\企业基本信息\\更新学员人脸照片.xls",
			"D:\\BS\\static\\SafetyStandards\\安全生产档案\\pdf文件\\测试企业\\企业资质档案\\企业基本信息\\\\更新学员人脸照片.pdf");

	}

}
