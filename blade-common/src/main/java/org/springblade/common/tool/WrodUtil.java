package org.springblade.common.tool;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
}
