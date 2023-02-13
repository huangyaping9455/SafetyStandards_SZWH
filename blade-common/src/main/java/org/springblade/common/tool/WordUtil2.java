package org.springblade.common.tool;

import cn.afterturn.easypoi.word.WordExportUtil;
import cn.afterturn.easypoi.word.entity.WordImageEntity;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * word工具类
 * easypoi官方文档:http://easypoi.mydoc.io/#category_49974
 */
public class WordUtil2 {

	 /**
     * 导出word
     * <p>第一步生成替换后的word文件，只支持docx</p>
     * <p>第二步下载生成的文件</p>
     * <p>第三步删除生成的临时文件</p>
     * 模版变量中变量格式：{{foo}}
     * @param templatePath word模板地址
     * @param temDir 生成临时文件存放地址
     * @param fileName 文件名
     * @param params 替换的参数
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    public static void exportWord(String templatePath, String temDir, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(templatePath,"模板路径不能为空");
        Assert.notNull(temDir,"临时文件路径不能为空");
        Assert.notNull(fileName,"导出文件名不能为空");
        Assert.isTrue(fileName.endsWith(".docx"),"word导出请使用docx格式");
        if (!temDir.endsWith("/")){
            temDir = temDir + File.separator;
        }
        File dir = new File(temDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
            }
            XWPFDocument doc = WordExportUtil.exportWord07(templatePath, params);
            String tmpPath = temDir + fileName;
            FileOutputStream fos = new FileOutputStream(tmpPath);
            doc.write(fos);
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            doc.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	delFileWord(temDir,fileName);//这一步看具体需求，要不要删
        }

    }

    /**
     * 删除零时生成的文件
     */
    public static void delFileWord(String filePath, String fileName) {
        File file = new File(filePath + fileName);
        File file1 = new File(filePath);
        file.delete();
        file1.delete();
    }


	/**
	 * 导出word
	 * <p>第一步生成替换后的word文件，只支持docx</p>
	 * <p>第二步下载生成的文件</p>
	 * <p>第三步删除生成的临时文件</p>
	 * 模版变量中变量格式：{{foo}}
	 * @param templatePath word模板地址
	 * @param temDir 生成临时文件存放地址
	 * @param fileName 文件名
	 * @param params 替换的参数
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */
	public static String exportDataWord3(String templatePath, String temDir, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		Assert.notNull(templatePath,"模板路径不能为空");
		Assert.notNull(temDir,"临时文件路径不能为空");
		Assert.notNull(fileName,"导出文件名不能为空");
		Assert.isTrue(fileName.endsWith(".docx"),"word导出请使用docx格式");
		if (!temDir.endsWith("/")){
			temDir = temDir + File.separator;
		}
		File dir = new File(temDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String tmpPath = "";
		try {
			byte[] utf8Bytes = fileName.getBytes("utf-8");
			fileName = new String(utf8Bytes, "utf-8");
			XWPFDocument doc = WordExportUtil.exportWord07(templatePath, params);
			tmpPath = temDir + fileName;
			FileOutputStream fos = new FileOutputStream(tmpPath);
			doc.write(fos);
			fos.close();
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmpPath;
	}

	/**
	 * 简单导出包含图片
	 */
	public static String imageWordExport() {
		Map<String, Object> map = new HashMap<String, Object>();
		WordImageEntity image = new WordImageEntity();
		image.setHeight(200);
		image.setWidth(500);
		image.setUrl("http://www.baidu.com/img/bdlogo.png");
		image.setType(WordImageEntity.URL);
		map.put("a1", "55555");
		map.put("a2", image);
		try {
			XWPFDocument doc = WordExportUtil.exportWord07(
				"D:/BS/static/muban/muban2.docx", map);
			FileOutputStream fos = new FileOutputStream("D:/excel/image.docx");
			doc.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}




}
