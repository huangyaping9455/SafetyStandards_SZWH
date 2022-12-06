package org.springblade.common.tool;

import org.springframework.lang.Nullable;

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


}
