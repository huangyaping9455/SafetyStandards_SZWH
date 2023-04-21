package org.springblade.common.tool;

import java.io.*;

/**
 * 文件读取工具类
 */
public class FileUtil {

    /**
     * 读取文件内容，作为字符串返回
     */
    public static String readFileAsString(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        if (file.length() > 1024 * 1024 * 1024) {
            throw new IOException("File is too large");
        }

        StringBuilder sb = new StringBuilder((int) (file.length()));
        // 创建字节输入流
        FileInputStream fis = new FileInputStream(filePath);
        // 创建一个长度为10240的Buffer
        byte[] bbuf = new byte[10240];
        // 用于保存实际读取的字节数
        int hasRead = 0;
        while ( (hasRead = fis.read(bbuf)) > 0 ) {
            sb.append(new String(bbuf, 0, hasRead));
        }
        fis.close();
        return sb.toString();
    }

    /**
     * 根据文件路径读取byte[] 数组
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                bos.close();
            }
        }
    }

	/**
	 * 根据文件路径、文件名称删除文件
	 * @param dirPath 文件所在的路径
	 * @param fileName 文件名称
	 */
	private static void circleMethod(String dirPath,String fileName) {
		File file = new File(dirPath);
		if (file.isDirectory()) {//if(file.isDirectory)；如果path表示的是一个目录则返回true。
			String[] dirPathList = file.list();
			for (int i = 0; i < dirPathList.length; i++) {
				String filePath = dirPath + File.separator + dirPathList[i];
				File fileDelete = new File(filePath);
				if (fileDelete.getName().equals(fileName)) {
					fileDelete.delete();
				}
				//circleMethod(filePath);
			}
		}
	}


	/**
	 * @param filePath  文件路径
	 * @param fileNames  文件名称(多个)
	 */
	public static void delMethod(String filePath,String fileNames) {
		String[] name = fileNames.split(",");//多个文件，逗号分割
		for (String fileName : name) {
			File file = new File(filePath+"\\"+fileName);
			if(file.exists() && file.isFile()) {
				file.delete();
			}
		}
	}

	public static void main(String[] args) {
//		String dir = "D:\\mimi\\file\\word";
//		String name = "";
//		circleMethod(dir,name);

		//删除指定文件
		// 路径
		String dir = "D:\\BS\\static\\SafetyStandards\\安全生产档案\\图片文件\\清远市清城区丰烨物流有限公司\\监督检查\\安全学习及培训教育记录\\10月份安全培训教育记录";
		delMethod(dir,"10月份安全培训教育记录-1.png,10月份安全培训教育记录-12.png");
	}

}
