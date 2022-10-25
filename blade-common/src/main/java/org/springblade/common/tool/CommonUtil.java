/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.common.tool;

import cn.hutool.core.codec.Base64;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 通用工具类
 *
 * @author 呵呵哒
 */
public class CommonUtil {

	/**
	 * PDF 格式
	 */
	public static final int PDF_FMT = 17;

	/**
	 * doc格式
	 */
	public static final int DOC_FMT = 0;
	/**
	 * docx格式
	 */
	public static final int DOCX_FMT = 12;


	/**
	 * <p>将文件转成base64 字符串</p>
	 * @param path 文件路径
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		if(file.exists()){
			return "data:image/png;base64,"+Base64.encode(file).replaceAll("\r|\n", "");
		}
		return "";

	}

	/**
	 * world 转 pdf
	 * @param startFile world地址
	 * @param overFile pdf地址
	 */
	public static void world2pdf(String startFile,String overFile){
		ActiveXComponent app = null;
		Dispatch doc = null;
		try {
			app = new ActiveXComponent("Word.Application");
			app.setProperty("Visible", new Variant(false));
			Dispatch docs = app.getProperty("Documents").toDispatch();

			doc = Dispatch.call(docs,  "Open" , startFile).toDispatch();
			File tofile = new File(overFile);
			if (tofile.exists()) {
				tofile.delete();
			}
			Dispatch.call(doc,"SaveAs", overFile, PDF_FMT);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			Dispatch.call(doc,"Close",false);
			if (app != null) {
				app.invoke("Quit", new Variant[] {});
			}
		}
	}

	/***
	 * PDF文件转PNG图片
	 * @param PdfFilePath pdf完整路径
	 * @param dstImgFolder 图片存放的文件夹
	 * @param dpi dpi越大转换后越清晰，相对转换速度越慢
	 * @param flag 页数 为0则转换全部页数
	 * @return 图片地址
	 */
	public static String pdf2Image(String PdfFilePath, String dstImgFolder, int dpi,int flag) {
		File file = new File(PdfFilePath);
		PDDocument pdDocument=null;
		String urlpath = "";
		try {
			String imgPDFPath = file.getParent();
			int dot = file.getName().lastIndexOf('.');
			String imagePDFName = file.getName().substring(0, dot);
			String imgFolderPath = null;
			if ("".equals(dstImgFolder)) {
				imgFolderPath = imgPDFPath ;
			} else {
				imgFolderPath = dstImgFolder;
			}
			if (createDirectory(imgFolderPath)) {
				pdDocument = PDDocument.load(file);
				PDFRenderer renderer = new PDFRenderer(pdDocument);
				int pages = pdDocument.getNumberOfPages();
				if(flag > 0) {//大于0则打印具体页数
					if(flag<pages) {
						pages = flag;
					}
				}
				StringBuffer imgFilePath = null;
				for (int i = 0; i < pages; i++) {
					String imgFilePathPrefix = imgFolderPath+File.separator + imagePDFName;
					imgFilePath = new StringBuffer();
					imgFilePath.append(imgFilePathPrefix);
					imgFilePath.append("-");
					imgFilePath.append(String.valueOf(i + 1));
					imgFilePath.append(".png");
					File dstFile = new File(imgFilePath.toString());
					BufferedImage image = renderer.renderImageWithDPI(i, dpi);
					ImageIO.write(image, "png", dstFile);
					//拼接保存名字
					urlpath=urlpath+imagePDFName+"-"+String.valueOf(i + 1)+".png|";
				}
				pdDocument.close();
			} else {
				System.out.println("error:" + "creat" + imgFolderPath + "wrong");
			}

		} catch (Exception e) {
			System.out.println("Exception");
			e.printStackTrace();
		}
		//返回保存名字
		return urlpath;
	}

	private static boolean createDirectory(String folder) {
		File dir = new File(folder);
		if (dir.exists()) {
			return true;
		} else {
			return dir.mkdirs();
		}
	}



	/**
	 * 根据格式类型转换doc文件
	 *
	 * @param descPath the docx path 目标文件
	 * @param fmt 所转格式    DOC_FMT doc格式     DOCX_FMT docx格式
	 * @return the file
	 * @throws Exception the exception
	 *
	 * 、
	 */
	public static File convertDocFmt(String srcPath, String descPath, int fmt) throws Exception {
		// 实例化ComThread线程与ActiveXComponent
		ComThread.InitSTA();
		ActiveXComponent app = new ActiveXComponent("Word.Application");
		try {
			// 文档隐藏时进行应用操作
			app.setProperty("Visible", new Variant(false));
			// 实例化模板Document对象
			Dispatch document = app.getProperty("Documents").toDispatch();
			// 打开Document进行另存为操作
			Dispatch doc = Dispatch.invoke(document, "Open", Dispatch.Method,
				new Object[]{srcPath, new Variant(true), new Variant(true)}, new int[1]).toDispatch();
			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[]{descPath, new Variant(fmt)}, new int[1]);
			Dispatch.call(doc, "Close", new Variant(false));
			return new File(descPath);
		} catch (Exception e) {
			throw e;
		} finally {
			// 释放线程与ActiveXComponent
			app.invoke("Quit", new Variant[]{});
			ComThread.Release();
		}
	}

}
