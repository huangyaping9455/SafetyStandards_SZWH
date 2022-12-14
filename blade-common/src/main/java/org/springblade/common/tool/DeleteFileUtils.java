package org.springblade.common.tool;

import java.io.File;

/**
 * @author hyp
 * @create 2022-04-28 16:15
 */
public class DeleteFileUtils {

	//删除指定文件夹下的所有文件，包括文件夹本身
	public static void removeDir(File dir) {
		File[] files = dir.listFiles();
		if (files.length>0) {
			for (File file : files) {
				if (file.isDirectory()) {
					removeDir(file);
					file.delete();
				} else {
					file.delete();
				}
			}
		}
		//删除文件夹本身的方法
		dir.delete();
	}

	//删除指定文件夹下的所有文件，不包括文件夹本身
	public static void delAllFiles(File file) {
		if (!file.exists()) {
			System.out.println("不存在该路径: " + file);
			return;
		}
		File[] files = file.listFiles();
		if (files.length > 0) {
			for (File f : files) {
				if (f.isDirectory()) {
					//如果是目录
					delAllFiles(f);
					//递归
					f.delete();
					//删除该文件夹
				} else {
					f.delete();
				}
			}
		}
		else {
			System.out.println("***该目录中无任何文件***");
		}
	}

	/**
	 * 删除文件或文件夹
	 */
		public static void main(String[] args) {
			boolean result = deleteFileOrDirectory("D:/media/transcoding");
			System.out.println(result);


		}

		/**
		 * 删除文件或文件夹
		 *
		 * @param fileName 文件名
		 * @return 删除成功返回true,失败返回false
		 */
		public static boolean deleteFileOrDirectory(String fileName) {
			File file = new File(fileName);  // fileName是路径或者file.getPath()获取的文件路径
			if (file.exists()) {
				if (file.isFile()) {
					return deleteFile(fileName);  // 是文件，调用删除文件的方法
				} else {
					return deleteDirectory(fileName);  // 是文件夹，调用删除文件夹的方法
				}
			} else {
				System.out.println("文件或文件夹删除失败：" + fileName);
				return false;
			}
		}

		/**
		 * 删除文件
		 *
		 * @param fileName 文件名
		 * @return 删除成功返回true,失败返回false
		 */
		public static boolean deleteFile(String fileName) {
			File file = new File(fileName);
			if (file.isFile() && file.exists()) {
				file.delete();
				System.out.println("删除文件成功：" + fileName);
				return true;
			} else {
				System.out.println("删除文件失败：" + fileName);
				return false;
			}
		}

		/**
		 * 删除文件夹
		 * 删除文件夹需要把包含的文件及文件夹先删除，才能成功
		 *
		 * @param directory 文件夹名
		 * @return 删除成功返回true,失败返回false
		 */
		public static boolean deleteDirectory(String directory) {
			// directory不以文件分隔符（/或\）结尾时，自动添加文件分隔符，不同系统下File.separator方法会自动添加相应的分隔符
			if (!directory.endsWith(File.separator)) {
				directory = directory + File.separator;
			}
			File directoryFile = new File(directory);
			// 判断directory对应的文件是否存在，或者是否是一个文件夹
			if (!directoryFile.exists() || !directoryFile.isDirectory()) {
				System.out.println("文件夹删除失败，文件夹不存在" + directory);
				return false;
			}
			boolean flag = true;
			// 删除文件夹下的所有文件和文件夹
			File[] files = directoryFile.listFiles();
			for (int i = 0; i < files.length; i++) {  // 循环删除所有的子文件及子文件夹
				// 删除子文件
				if (files[i].isFile()) {
					flag = deleteFile(files[i].getAbsolutePath());
					if (!flag) {
						break;
					}
				} else {  // 删除子文件夹
					flag = deleteDirectory(files[i].getAbsolutePath());
					if (!flag) {
						break;
					}
				}
			}

			if (!flag) {
				System.out.println("删除失败");
				return false;
			}
			// 最后删除当前文件夹
			if (directoryFile.delete()) {
				System.out.println("删除成功：" + directory);
				return true;
			} else {
				System.out.println("删除失败：" + directory);
				return false;
			}
		}


}
