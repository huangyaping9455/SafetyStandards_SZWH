package org.springblade.common.tool;

import com.deepoove.poi.XWPFTemplate;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 呵呵哒
 * @description: Wrod工具
 * @projectName SafetyStandards
 */
public class WrodUtil {
	/**
	 *生产doxc图片转换模板
	 * @author: 呵呵哒
	 * @param kList
	 * @return: java.io.InputStream
	 */
	public static InputStream creatImageTemplate(List<String> kList) throws IOException {
		XWPFDocument doc = new XWPFDocument();

		XWPFParagraph paragraph = doc.createParagraph();
		paragraph.setIndentationLeft(0);
		paragraph.setIndentationHanging(0);
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraph.setWordWrap(true);
		//在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入
		for (int a = 0; a < kList.size(); a++) {
			XWPFRun run = paragraph.insertNewRun(a);
			//设置run内容
			run.setText("{{@image" + a + "}}");
			run.setFontFamily("宋体");
			run.setBold(true);
			run.addBreak();


		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		doc.write(byteArrayOutputStream);
		ByteArrayInputStream swapStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		byteArrayOutputStream.close();
		return swapStream;
	}

	/**
	 * 根据模板填充内容生成word，并下载
	 * @param templatePath word模板文件路径
	 * @param paramMap     替换的参数集合
	 */
	public static void downloadWord(OutputStream out, String templatePath, Map<String, Object> paramMap) {

		Long time = new Date().getTime();
		// 生成的word格式
		String formatSuffix = ".docx";
		// 拼接后的文件名
		String fileName = time + formatSuffix;

		//设置生成的文件存放路径，可以存放在你想要指定的路径里面
		String rootPath="D:/mimi/"+File.separator+"file/word/";

		String filePath = rootPath+fileName;
		File newFile = new File(filePath);
		//判断目标文件所在目录是否存在
		if(!newFile.getParentFile().exists()){
			//如果目标文件所在的目录不存在，则创建父目录
			newFile.getParentFile().mkdirs();
		}

		// 读取模板templatePath并将paramMap的内容填充进模板，即编辑模板(compile)+渲染数据(render)
		XWPFTemplate template = XWPFTemplate.compile(templatePath).render(paramMap);
		try {
			//out = new FileOutputStream(filePath);//输出路径(下载到指定路径)
			// 将填充之后的模板写入filePath
			template.write(out);//将template写到OutputStream中
			out.flush();
			out.close();
			template.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
