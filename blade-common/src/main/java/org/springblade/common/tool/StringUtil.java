package org.springblade.common.tool;

import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtil {
	@Deprecated
	public static boolean isEmpty(@Nullable Object str) {
		return str == null || "".equals(str);
	}

	/**
	 * 查询字符串中指定字符个数
	 * @param oriStr 原字符串
	 * @param findStr 需要查找的字符
	 * @param count 固定值：0
	 * @return 查找到字符的个数
	 */
	public static int findStrCount(String oriStr, String findStr, int count) {

		if (oriStr.contains(findStr)) {
			count++;
			count = findStrCount(oriStr.substring(oriStr.indexOf(findStr) + findStr.length()), findStr, count);
		}
		return count;
	}

	public static <T> List<T> compare(T[] t1, T[] t2) {
		List<T> list1 = Arrays.asList(t1);
		List<T> list2 = new ArrayList<T>();
		for (T t : t2) {
			if (!list1.contains(t)) {
				list2.add(t);
			}
		}
		return list2;
	}

	public static void main(String[] arg){

		String[] array1 = {"2023-02-01", "2023-02-11", "2023-02-22"};
		String[] array2 = {"2023-02-13", "2023-02-22", "2023-02-12"};

		List<String> list = compare(array1,array2);
		for (String integer : list) {
			System.out.println(integer);
		}
	}


}
