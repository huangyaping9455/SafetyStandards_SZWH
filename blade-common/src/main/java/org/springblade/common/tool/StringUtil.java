package org.springblade.common.tool;

import org.springframework.lang.Nullable;

public class StringUtil {
	@Deprecated
	public static boolean isEmpty(@Nullable Object str) {
		return str == null || "".equals(str);
	}
}
