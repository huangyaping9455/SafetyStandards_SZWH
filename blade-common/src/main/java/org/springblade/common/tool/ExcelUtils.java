package org.springblade.common.tool;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hyp
 * @create 2023-02-14 11:24
 */
public class ExcelUtils {

//	public static void main(String[] args) {
//
//		//表格头
//		ArrayList<List<String>> head = new ArrayList<>();
//		List<String> head1 = new ArrayList<>();
//		head1.add("序号");
//		head.add(head1);
//		List<String> head2 = new ArrayList<>();
//		head2.add("姓名");
//		head.add(head2);
//		List<String> head3 = new ArrayList<>();
//		head3.add("年龄");
//		head.add(head3);
//		List<String> head4 = new ArrayList<>();
//		head4.add("地址");
//		head.add(head4);
//		List<String> head5 = new ArrayList<>();
//		head5.add("时间");
//		head.add(head5);
//
//		//内容
//		List<List<String>> bodyList = new ArrayList<>();
//		List<String> body = new ArrayList<>();
//		body.add("1");
//		body.add("张三");
//		body.add("15");
//		body.add("北京");
//		body.add("2022-09-02");
//		bodyList.add(body);
//
//		String file = "D:\\BS\\static\\muban";
//		File dir = new File(file);
//		dir.mkdirs();
//		String fileName = file+"/DayliExamine.xlsx";
//		//创建EXCEL对象
//		ExcelWriterBuilder builder = EasyExcel.write(fileName);
//		//设置处理器，合并单元格，列宽处理器等
//		builder.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy());
//		//获取writer对象
//		ExcelWriter writer= builder.build();
//		WriteSheet sheet = EasyExcel.writerSheet(0, "个人信息").build();
//		//创建两个表格并写入表头
//		WriteTable tableOne = EasyExcel.writerTable(0).needHead(Boolean.TRUE).build();
//		tableOne.setHead(head);
//
//		WriteTable tableTwo = EasyExcel.writerTable(1).needHead(Boolean.TRUE).build();
//		tableTwo.setHead(head);
//
//		//写入两次数据，每次会自动生成一个表头
//		writer.write(bodyList,sheet,tableOne);
//		writer.finish();
//
//	}


	/**
	 * 删除 文件或者文件夹
	 * @param filePath
	 */
	public static void deleteFile(String filePath){
		File file = new File(filePath);
		if (!file.exists()) {
			return;
		}
		if (file.isDirectory() ) {
			File[] list = file.listFiles();

			for (File f : list) {
				deleteFile(f.getAbsolutePath()) ;
			}
		}
		file.delete();
	}


	public static void main(String[] args) {
		deleteFile("D:\\BS\\static\\AttachFiles\\2023\\02\\隐患登记台账.zip");
	}



}
