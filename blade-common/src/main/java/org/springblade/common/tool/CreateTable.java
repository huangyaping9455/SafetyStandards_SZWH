package org.springblade.common.tool;

import com.spire.doc.*;
import com.spire.doc.documents.*;
import com.spire.doc.fields.TextRange;

import java.awt.*;
import java.io.IOException;

/**
 * @author hyp
 * @create 2022-04-20 11:18
 */
public class CreateTable {

	public static String getCreateTable(String[] header, String[][] data,String outUrl) throws IOException {
		//创建Word文档
		Document document = new Document();
		//添加一个section
		Section section = document.addSection();

		//添加表格
		Table table = section.addTable(true);
		//设置表格的行数和列数
		table.resetCells(data.length + 1, header.length);

		//设置第一行作为表格的表头并添加数据
		TableRow row = table.getRows().get(0);
		row.isHeader(true);
		row.setHeight(30);
		row.setHeightType(TableRowHeightType.Exactly);
		row.getRowFormat().setBackColor(Color.gray);

		for (int i = 0; i < header.length; i++) {
			row.getCells().get(i).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
			Paragraph p = row.getCells().get(i).addParagraph();
			//设置文字居中
			p.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
			TextRange range1 = p.appendText(header[i]);
			range1.getCharacterFormat().setFontName("楷体");
			range1.getCharacterFormat().setFontSize(12f);
			range1.getCharacterFormat().setBold(true);
		}

		//添加数据到剩余行
		for (int r = 0; r < data.length; r++) {
			TableRow dataRow = table.getRows().get(r + 1);
			dataRow.setHeight(25);
			dataRow.setHeightType(TableRowHeightType.Exactly);
			dataRow.getRowFormat().setBackColor(Color.white);
			for (int c = 0; c < data[r].length; c++) {
				Paragraph p = dataRow.getCells().get(c).addParagraph();
				p.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
				dataRow.getCells().get(c).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
				TextRange range2 = p.appendText(data[r][c]);
				range2.getCharacterFormat().setFontName("楷体");
				range2.getCharacterFormat().setFontSize(12f);
			}
		}

		//设置单元格背景颜色
		for (int j = 1; j < table.getRows().getCount(); j++) {
			if (j % 2 == 0) {
				TableRow row2 = table.getRows().get(j);
				for (int f = 0; f < row2.getCells().getCount(); f++) {
					row2.getCells().get(f).getCellFormat().setBackColor(new Color(173, 216, 230));
				}
			}
		}

		//列宽自动适应内容
		table.autoFit(AutoFitBehaviorType.Auto_Fit_To_Contents);
		//表格自适应页面宽度
		table.autoFit(AutoFitBehaviorType.Auto_Fit_To_Window);
		//应用表格样式
		table.applyStyle(DefaultTableStyle.Table_Grid_5);
		//设置页面大小为A3
		section.getPageSetup().setPageSize(PageSize.A3);
		//设置页面方向为Landscape横向
		section.getPageSetup().setOrientation(PageOrientation.Landscape);
		//section.getPageSetup().setOrientation(PageOrientation.Portrait);//纵向
		//保存文档 "E:\\CreateTable.docx"
		document.saveToFile(outUrl, FileFormat.Docx_2013);
//		//重新读取生成的文档
//		InputStream is = new FileInputStream(outUrl);
//		XWPFDocument document1 = new XWPFDocument(is);
//		//以上Spire.Doc 生成的文件会自带警告信息，这里来删除Spire.Doc 的警告
//		document1.removeBodyElement(0);
//		//输出word内容文件流，新输出路径位置 "E:\\demo1.docx"
//		OutputStream os=new FileOutputStream("E:\\demo1.docx");
		try {
//			document1.write(os);
			System.out.println("生成docx文档成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outUrl;
	}


	public static void main(String[] args) throws IOException {
		//数据
		String[] header = {"姓名", "性别", "部门", "工号"};
		String[][] data =
			{
				new String[]{"Winny", "女", "综合", "0109"},
				new String[]{"Lois", "女", "综合", "0111"},
				new String[]{"Jois", "男", "技术", "0110"},
				new String[]{"Moon", "女", "销售", "0112"},
				new String[]{"Vinit", "女", "后勤", "0113"},
			};
		CreateTable.getCreateTable(header,data,"E:\\CreateTable.docx");


			// 浮点数的打印
//			System.out.println(new BigDecimal("10000000000").toString());
//
//			// 普通的数字字符串
//			System.out.println(new BigDecimal("100.000").toString());
//
//			// 去除末尾多余的0
//			System.out.println(new BigDecimal("100.000").stripTrailingZeros().toString());
//
//			// 避免输出科学计数法
//			System.out.println(new BigDecimal("100.000").stripTrailingZeros().toPlainString());

	}
}
