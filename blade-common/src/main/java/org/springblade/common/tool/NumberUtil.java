package org.springblade.common.tool;

import org.springframework.lang.Nullable;

public class NumberUtil {

	public static long toLong(@Nullable final String str, final long defaultValue) {
		if (str == null) {
			return defaultValue;
		} else {
			try {
				return Integer.valueOf(str);
			} catch (NumberFormatException var4) {
				return defaultValue;
			}
		}
	}
}
