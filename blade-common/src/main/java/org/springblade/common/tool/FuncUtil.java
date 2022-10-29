package org.springblade.common.tool;


import java.util.Arrays;
import java.util.List;

public class FuncUtil {

	public static List<Integer> toLongList(String str) {
		return Arrays.asList(toLongArray(str));
	}

	public static List<Integer> toLongList(String split, String str) {
		return Arrays.asList(toLongArray(split, str));
	}

	public static Integer[] toLongArray(String str) {
		return toLongArray(",", str);
	}

	public static Integer[] toLongArray(String split, String str) {
		if (StringUtil.isEmpty(str)) {
			return new Integer[0];
		} else {
			String[] arr = str.split(split);
			Integer[] longs = new Integer[arr.length];

			for(int i = 0; i < arr.length; ++i) {
				Integer v = Integer.valueOf(arr[i]);
				longs[i] = v;
			}

			return longs;
		}
	}

	public static long toLong(final Object value, final long defaultValue) {
		return NumberUtil.toLong(String.valueOf(value), defaultValue);
	}
}
