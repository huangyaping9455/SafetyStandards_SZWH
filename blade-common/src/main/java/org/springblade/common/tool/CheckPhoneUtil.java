/**
 * Copyright (C), 2015-2020,
 * FileName: CheckPhone
 * Description:
 */
package org.springblade.common.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CheckPhoneUtil {

	/** 座机电话格式验证 **/
	private static final String PHONE_CALL_PATTERN = "^(?:\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(?:-\\d{1,4})?$";

	/**
	 * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700,173,191,199
	 * **/
	private static final String CHINA_TELECOM_PATTERN = "(?:^(?:\\+86)?1(?:33|53|7[37]|8[019]|9[0-9])\\d{8}$)|(?:^(?:\\+86)?1700\\d{7}$)";

	/**
	 * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1707,1708,1709,175,166
	 * **/
	private static final String CHINA_UNICOM_PATTERN = "(?:^(?:\\+86)?1(?:3[0-2]|4[5]|5[56]|66|7[56]|8[56])\\d{8}$)|(?:^(?:\\+86)?170[7-9]\\d{7}$)";
	/**
	 * 简单手机号码校验，校验手机号码的长度和1开头
	 */
	private static final String SIMPLE_PHONE_CHECK = "^(?:\\+86)?1\\d{10}$";
	/**
	 * 中国移动号码格式验证
	 * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184
	 * ,187,188,147,178,1705,189,198
	 *
	 **/
	private static final String CHINA_MOBILE_PATTERN = "(?:^(?:\\+86)?1(?:3[4-9]|4[7]|5[0-27-9]|7[0-9]|8[2-4789]|98)\\d{8}$)|(?:^(?:\\+86)?1705\\d{7}$)";

	/**
	 * 仅手机号格式校验
	 */
	private static final String PHONE_PATTERN = new StringBuilder(300)
		.append(CHINA_MOBILE_PATTERN).append("|")
		.append(CHINA_TELECOM_PATTERN).append("|")
		.append(CHINA_UNICOM_PATTERN).toString();

	/**
	 * 手机和座机号格式校验
	 */
	private static final String PHONE_TEL_PATTERN = new StringBuilder(350)
		.append(PHONE_PATTERN).append("|").append("(")
		.append(PHONE_CALL_PATTERN).append(")").toString();


	/**
	 * 匹配多个号码以,、或空格隔开的格式，如 17750581369
	 * 13306061248、(596)3370653,17750581369,13306061248 (0596)3370653
	 * @param input
	 * @param separator 可以自己指定分隔符，如"、, "表示可以以顿号、逗号和空格分隔
	 * @return
	 */
	public static boolean checkMultiPhone(String input, String separator) {
		separator = escapeMetacharacterOfStr(separator);
		String regex = "^(?!.+["
			+ separator
			+ "]$)(?:(?:(?:(?:\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(?:-\\d{1,4})?)|(?:1\\d{10}))(?:["
			+ separator + "]|$))+$";
		return match(regex, input);
	}

	/**
	 * 转义字符串中的[]-^\+*${元字符
	 * @param input
	 * @return
	 */
	private static String escapeMetacharacterOfStr(String input) {
		String regex = "[-{+*$^\\[\\]\\\\]";
		return input.replaceAll(regex, "\\\\$0");
	}

	/**
	 * 仅手机号码校验
	 * @param input
	 * @return
	 */
	public static boolean isPhone(String input) {
		return match(PHONE_PATTERN, input);
	}

	/**
	 * 手机号或座机号校验
	 * @param input
	 * @return
	 */
	public static boolean isPhoneOrTel(String input) {
		System.out.println(PHONE_TEL_PATTERN);
		return match(PHONE_TEL_PATTERN, input);
	}

	/**
	 * 验证电话号码的格式
	 *
	 * @author LinBilin
	 * @param str
	 *            校验电话字符串
	 * @return 返回true,否则为false
	 */
	public static boolean isPhoneCallNum(String str) {
		return match(PHONE_CALL_PATTERN, str);
	}

	/**
	 * 验证【电信】手机号码的格式
	 *
	 * @author LinBilin
	 * @param str
	 *            校验手机字符串
	 * @return 返回true,否则为false
	 */
	public static boolean isChinaTelecomPhoneNum(String str) {
		return match(CHINA_TELECOM_PATTERN, str);
	}

	/**
	 * 验证【联通】手机号码的格式
	 *
	 * @author LinBilin
	 * @param str
	 *            校验手机字符串
	 * @return 返回true,否则为false
	 */
	public static boolean isChinaUnicomPhoneNum(String str) {
		return match(CHINA_UNICOM_PATTERN, str);
	}

	/**
	 * 验证【移动】手机号码的格式
	 *
	 * @author LinBilin
	 * @param str
	 *            校验手机字符串
	 * @return 返回true,否则为false
	 */
	public static boolean isChinaMobilePhoneNum(String str) {
		return match(CHINA_MOBILE_PATTERN, str);
	}

	/**
	 * 简单手机号码校验，校验手机号码的长度和1开头
	 *
	 * @param str
	 * @return
	 */
	public static boolean isPhoneSimple(String str) {
		return match(SIMPLE_PHONE_CHECK, str);
	}

	/**
	 * 匹配函数
	 *
	 * @param regex
	 * @param input
	 * @return
	 */
	private static boolean match(String regex, String input) {
		return Pattern.matches(regex, input);
	}


	/**
	 * ^ 匹配输入字符串开始的位置
	 * \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
	 * $ 匹配输入字符串结尾的位置
	 */
	private static final Pattern HK_PATTERN = Pattern.compile("^(5|6|8|9)\\d{7}$");
	private static final Pattern CHINA_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");
	private static final Pattern NUM_PATTERN = Pattern.compile("[0-9]+");

	/**
	 * 大陆号码或香港号码均可
	 */
	public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
		return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
	}

	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
	 * 此方法中前三位格式有：
	 * 13+任意数
	 * 145,147,149
	 * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
	 * 166
	 * 17+3,5,6,7,8
	 * 18+任意数
	 * 198,199
	 */
	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		Matcher m = CHINA_PATTERN.matcher(str);
		return m.matches();
	}

	/**
	 * 香港手机号码8位数，5|6|8|9开头+7位任意数
	 */
	public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {

		Matcher m = HK_PATTERN.matcher(str);
		return m.matches();
	}

	/**
	 * 判断是否是正整数的方法
	 */
	public static boolean isNumeric(String string) {
		return NUM_PATTERN.matcher(string).matches();
	}






}
