package com.icebreak.util.util;

import org.springframework.util.Assert;

import java.util.*;
import java.util.regex.PatternSyntaxException;

public abstract class StringUtils {

	/** 空的 {@link String} 数组 */
	public static final String[] EMPTY_STRINGS = {};

	/**
	 * 省略字符。
	 * 
	 * @param source 要省略的字符。
	 * @param len 字符长度。
	 * @param delim 代替省略字符的字符串。
	 * @return 省略后的字符。
	 */
	public static String strTruncate(String source, int len, String delim) {
		if (source == null) {
			return null;
		}
		int start, stop, byteLen;
		int alen = source.getBytes().length;
		if (len > 0) {
			if (alen <= len) {
				return source;
			}
			start = stop = byteLen = 0;
			while (byteLen <= len) {
				if (source.substring(stop, stop + 1).getBytes().length == 1) {
					byteLen += 1;
				} else {
					byteLen += 2;
				}
				stop++;
			}
			if (alen > len) {
				StringBuilder sb = new StringBuilder(source.substring(start, stop - 1));
				sb.append(delim);
			}
			return source.substring(start, stop - 1);
		}
		return source;
	}

	/**
	 * 将一个字符串指定位置的字符变为大写。
	 * 
	 * @param str 字符串。
	 * @param index 指定位置,该数必须大于等于1,小于等于字符串长度。
	 * @return 改变后的字符串。
	 * @throws IndexOutOfBoundsException 如果 index 大于 <code>str.length()</code>。
	 */
	public static String toUpperCase(String str, int index) {
		StringBuilder sb = new StringBuilder(str);
		char s = Character.toUpperCase(sb.charAt(index - 1));
		sb.setCharAt(index - 1, s);
		return sb.toString();
	}

	/**
	 * 将一个字符串指定位置的字符变为小写。
	 * 
	 * @param str 字符串。
	 * @param index 指定位置,该数必须大于等于1,小于等于字符串长度。
	 * @return 改变后的字符串。
	 * @throws IndexOutOfBoundsException 如果 index 大于 <code>str.length()</code>。
	 */
	public static String toLowerCase(String str, int index) {
		StringBuilder sb = new StringBuilder(str);
		char s = Character.toLowerCase(sb.charAt(index - 1));
		sb.setCharAt(index - 1, s);
		return sb.toString();
	}

	/**
	 * 将参数转换为 {@link String} 。 如果 param 为 null 则返回 null 。如果参数为 {@link String}
	 * 类型则直接转换为 {@link String} ，否则调用参数的 {@link Object#toString()} 方法转换为
	 * {@link String} 。
	 * 
	 * @param param 要转换的参数。
	 * @return 转换后的参数。
	 */
	public static String toString(Object param) {
		if (param == null) {
			return null;
		}
		return param.toString();
	}

	/**
	 * 判断string是否为空。
	 * 
	 * @param string 要判断的String。
	 * @return 如果String为 null 或者 {@link String#trim()} 为 "" ，则返回 true。
	 */
	public static boolean isEmpty(String string) {
		return (string == null) || ("".equals(string.trim()));
	}

	/**
	 * 判断string是否不为空。
	 * 
	 * @param string 要判断的String。
	 * @return 如果String不为 null 且 {@link String#trim()} 不为 "" ，则返回 true。
	 */
	public static boolean isNotEmpty(String string) {
		return (string != null) && (!"".equals(string.trim()));
	}

	/**
	 * 检查 string 是否为空，如果为空则使用 defaultValue 作为返回值。
	 * 
	 * @param string 要检查的 String。
	 * @param defaultValue 如果 string 为空时的默认值。
	 * @return string 不为空返回 string ，否则返回 defaultValue。
	 */
	public static String checkEmptyString(String string, String defaultValue) {
		if (isEmpty(string)) {
			return defaultValue;
		}
		return string;
	}

	/**
	 * 判断 CharSequence 是否有内容。
	 * 
	 * @param charSequence 要判断的CharSequence。
	 * @return 当 charSequence 不为 null 与 {@link CharSequence#length()} > 0 时返回
	 *         true。
	 */
	public static boolean hasLength(CharSequence charSequence) {
		return (charSequence != null && charSequence.length() > 0);
	}

	/**
	 * 判断 charSequence 是否有 text。
	 * 
	 * @param charSequence 要判断的CharSequence。
	 * @return 当 charSequence 不为 null 与 {@link CharSequence#length()} > 0 并且该
	 *         charSequence 的至少一个字符在 {@link Character#isWhitespace(char)} 返回
	 *         false 时返回 true。
	 */
	public static boolean hasText(CharSequence charSequence) {
		if (!hasLength(charSequence)) {
			return false;
		}
		int strLen = charSequence.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(charSequence.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 得到字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1 处结束出现的次数。
	 * 
	 * @param string 一个字符串。
	 * @param s 需要检测在 string 中出现次数的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @param endIndex - 结束处的索引（不包括） 。
	 * @return 字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1 处结束出现的次数，如果
	 *         string 为 null 或者 s 为 空字符串 则返回 0。
	 * @throws IllegalArgumentException 当 beginIndex 小于 0 或者 endIndex 大于 string
	 *             的长度时。
	 */
	public static int occurrence(String string, String s, int beginIndex, int endIndex) {
		if (string == null || isEmpty(s)) {
			return 0;
		}
		return occurrence0(string, s, beginIndex, endIndex);
	}

	/**
	 * 得到字符串 s 在 字符串 string 中从 beginIndex 处开始到末尾出现的次数。
	 * 
	 * @param string 一个字符串。
	 * @param s 需要检测在 string 中出现次数的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @return 字符串 s 在 字符串 string 中从 beginIndex 处开始到末尾出现的次数，如果 string 为 null 或者
	 *         s 为 空字符串 则返回 0。
	 * @throws IllegalArgumentException 当 beginIndex 小于 0 时。
	 */
	public static int occurrence(String string, String s, int beginIndex) {
		if (string == null || isEmpty(s)) {
			return 0;
		}
		return occurrence0(string, s, beginIndex, string.length());
	}

	/**
	 * 得到字符串 s 在 字符串 string 中出现的次数。
	 * 
	 * @param string 一个字符串。
	 * @param s 需要检测在 string 中出现次数的字符串。
	 * @return 字符串 s 在 字符串 string 中出现的次数，如果 string 为 null 或者 s 为 空字符串 则返回 0，如果
	 *         string 为 空 或者 s 为 空字符串 则返回 0。
	 */
	public static int occurrence(String string, String s) {
		if (string == null || isEmpty(s)) {
			return 0;
		}
		return occurrence0(string, s, 0, string.length());
	}

	/**
	 * 得到字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1 处结束出现的次数。
	 * 
	 * @param string 一个字符串。
	 * @param s 需要检测在 string 中出现次数的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @param endIndex - 结束处的索引（不包括） 。
	 * @return 字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1 处结束出现的次数。
	 * @throws IllegalArgumentException 当 beginIndex 小于 0 或者 大于等于endIndex 或者
	 *             endIndex 大于 string 的长度时。
	 */
	private static int occurrence0(String string, String s, int beginIndex, int endIndex) {
		if (endIndex == 0 && beginIndex == 0) {
			return 0;
		}
		Assert.isTrue(beginIndex >= 0 && beginIndex < endIndex);
		Assert.isTrue(endIndex <= string.length());
		int i = 0;
		while (true) {
			beginIndex = string.indexOf(s, beginIndex);
			if (beginIndex == -1 || beginIndex >= endIndex) {
				break;
			}
			i++;
			beginIndex = beginIndex + s.length();
		}
		return i;
	}

	/**
	 * 得到字符串 s 在 字符串 string 中第一次开始连续出现的次数。
	 * 
	 * @param string 一个字符串。
	 * @param s 需要检测在 string 中第一次开始连续出现次数的字符串。
	 * @return 字符串 s 在 字符串 string 中第一次开始连续出现的次数，如果 string 为 空 或者 s 为 空 则返回 0。
	 */
	public static int consecutive(String string, String s) {
		if (string == null || isEmpty(s)) {
			return 0;
		}
		return consecutive0(string, s, 0, string.length());
	}

	/**
	 * 得到字符串 s 在 字符串 string 中从 beginIndex 处开始到末尾第一次开始连续出现的次数。
	 * 
	 * @param string 一个字符串。
	 * @param s 需要检测在 string 中第一次开始连续出现次数的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @return 字符串 s 在 字符串 string 中从 beginIndex 处开始到末尾第一次开始连续出现的次数，如果 string 为 空
	 *         或者 s 为 空字符串 则返回 0。
	 * @throws IllegalArgumentException 当 beginIndex 小于 0 时。
	 */
	public static int consecutive(String string, String s, int beginIndex) {
		if (string == null || isEmpty(s)) {
			return 0;
		}
		return consecutive0(string, s, beginIndex, string.length());
	}

	/**
	 * 得到字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1 处结束第一次开始连续出现的次数。
	 * 
	 * @param string 一个字符串。
	 * @param s 需要检测在 string 中第一次开始连续出现次数的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @param endIndex - 结束处的索引（不包括） 。
	 * @return 字符串 s 在 字符串 string 中从 beginIndex 处开始到末尾第一次开始连续出现的次数，如果 string 为 空
	 *         或者 s 为 空字符串 则返回 0。
	 * @throws IllegalArgumentException 当 beginIndex 小于 0 或者 大于等于endIndex 或者
	 *             endIndex 大于 string 的长度时。
	 */
	public static int consecutive(String string, String s, int beginIndex, int endIndex) {
		if (string == null || isEmpty(s)) {
			return 0;
		}
		return consecutive0(string, s, beginIndex, endIndex);
	}

	/**
	 * 得到字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1 处结束第一次开始连续出现的次数。
	 * 
	 * @param string 一个字符串。
	 * @param s 需要检测在 string 中第一次开始连续出现次数的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @param endIndex - 结束处的索引（不包括） 。
	 * @return 字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1
	 *         处结束第一次开始连续出现的次数。
	 * @throws IllegalArgumentException 当 beginIndex 小于 0 或者 大于等于endIndex 或者
	 *             endIndex 大于 string 的长度时。
	 */
	private static int consecutive0(String string, String s, int beginIndex, int endIndex) {
		if (endIndex == 0 && beginIndex == 0) {
			return 0;
		}
		Assert.isTrue(beginIndex >= 0 && beginIndex < endIndex);
		Assert.isTrue(endIndex <= string.length());
		int i = 0;
		while (true) {
			beginIndex = string.indexOf(s, beginIndex);
			if (beginIndex == -1 || beginIndex >= endIndex) {
				break;
			} else if ((beginIndex != endIndex - 1) && (s.charAt(0) != string.charAt(beginIndex + 1))) {
				// 如果 s 的第一个字符与下次比较的第一个字符都不匹配，则为不连续
				i++;
				break;
			}
			i++;
			beginIndex = beginIndex + s.length();
		}
		return i;
	}

	/**
	 * 字符串 s 在 字符串 string 中从后往前第一次开始连续出现的次数。
	 * 
	 * @param string 一个字符串。
	 * @param s 需要检测在 string 中从后往前第一次开始连续出现次数的字符串。
	 * @return 字符串 s 在 字符串 string 中从后往前第一次开始连续出现的次数，如果 string 为 空 或者 s 为 空字符串
	 *         则返回 0。
	 */
	public static int lastConsecutive(String string, String s) {
		if (string == null || isEmpty(s)) {
			return 0;
		}
		return lastConsecutive0(string, s, string.length(), 0);
	}

	/**
	 * 字符串 s 在 字符串 string 中从后往前 lastBeginIndex - 1 处开始到 0 处结束第一次开始连续出现的次数。
	 * 
	 * @param string 一个字符串。
	 * @param s 需要检测在 string 中从后往前第一次开始连续出现次数的字符串。
	 * @param lastBeginIndex 从后往前开始处的索引（不包括）。
	 * @return 字符串 s 在 字符串 string 中从后往前 lastBeginIndex - 1 处开始到 0
	 *         处结束第一次开始连续出现的次数，如果 string 为 空 或者 s 为 空字符串 则返回 0。
	 * @throws IllegalArgumentException 当 lastBeginIndex 大于 string 的长度时。
	 */
	public static int lastConsecutive(String string, String s, int lastBeginIndex) {
		if (string == null || isEmpty(s)) {
			return 0;
		}
		return lastConsecutive0(string, s, lastBeginIndex, 0);
	}

	/**
	 * 字符串 s 在 字符串 string 中从后往前 lastBeginIndex - 1 处开始到 lastEndIndex
	 * 处结束第一次开始连续出现的次数。
	 * 
	 * @param string 一个字符串。
	 * @param s 需要检测在 string 中从后往前第一次开始连续出现次数的字符串。
	 * @param lastBeginIndex 从后往前开始处的索引（不包括）。
	 * @param lastEndIndex - 从后往前结束处的索引（包括） 。
	 * @return 字符串 s 在 字符串 string 中从后往前 lastBeginIndex - 1 处开始到 lastEndIndex
	 *         处结束第一次开始连续出现的次数，如果 string 为 空 或者 s 为 空字符串 则返回 0。
	 * @throws IllegalArgumentException 当 lastBeginIndex 大于 string 的长度 或者 小于等于
	 *             lastEndIndex 或者 lastEndIndex 小于0 时。
	 */
	public static int lastConsecutive(String string, String s, int lastBeginIndex, int lastEndIndex) {
		if (string == null || isEmpty(s)) {
			return 0;
		}
		return lastConsecutive0(string, s, lastBeginIndex, lastEndIndex);
	}

	/**
	 * 字符串 s 在 字符串 string 中从后往前 lastBeginIndex - 1 处开始到 lastEndIndex
	 * 处结束第一次开始连续出现的次数。
	 * 
	 * @param string 一个字符串。
	 * @param s 需要检测在 string 中从后往前第一次开始连续出现次数的字符串。
	 * @param lastBeginIndex 从后往前开始处的索引（不包括）。
	 * @param lastEndIndex - 从后往前结束处的索引（包括） 。
	 * @return 字符串 s 在 字符串 string 中从后往前 lastBeginIndex - 1 处开始到 lastEndIndex
	 *         处结束第一次开始连续出现的次数。
	 * @throws IllegalArgumentException 当 lastBeginIndex 大于 string 的长度 或者 小于等于
	 *             lastEndIndex 或者 lastEndIndex 小于0 时。
	 */
	private static int lastConsecutive0(String string, String s, int lastBeginIndex, int lastEndIndex) {
		if (lastBeginIndex == 0 && lastEndIndex == 0) {
			return 0;
		}
		Assert.isTrue(lastBeginIndex <= string.length() && lastBeginIndex > lastEndIndex);
		Assert.isTrue(lastEndIndex >= 0);
		int i = 0;
		StringBuilder builder = new StringBuilder(string);
		builder.delete(0, lastEndIndex);
		char sEndChar = s.charAt(s.length() - 1);
		while (true) {
			lastBeginIndex = builder.lastIndexOf(s, --lastBeginIndex);
			if (lastBeginIndex == -1) {
				break;
			} else if (lastBeginIndex == 0) {
				i++;
				break;
			} else if (sEndChar != builder.charAt(lastBeginIndex - 1)) {
				// 如果 s 的最后个字符与下次比较的反向第一个字符都不匹配，则为不连续
				i++;
				break;
			}
			i++;
			builder.delete(lastBeginIndex - s.length(), lastBeginIndex);
		}
		return i;
	}

	/**
	 * 得到字符串 s 在 string 中出现第 count 次时的索引。
	 * <p>
	 * 如果 string 为 null 返回 -1 。如果 s 在 string 中出现次数不到 count 次，则返回 -1。
	 * 
	 * @param string 任意字符串。
	 * @param s 任意字符串。
	 * @param count 指定 s 在 string 中出现的次数。
	 * @return 字符串 s 在 string 中出现第 count 次时的索引。
	 * @throws IllegalArgumentException 如果 count 小于等于 0。
	 */
	public static int indexOf(String string, String s, int count) {
		if (string == null) {
			return -1;
		}
		Assert.isTrue(count > 0);
		int index = -s.length();
		for (int i = 0; i < count; i++) {
			index = string.indexOf(s, index + s.length());
			if (index == -1) {
				return -1;
			}
		}
		return index;
	}

	/**
	 * 得到字符串 s 在 string 中从后往前出现第 count 次时的索引。
	 * <p>
	 * 如果 string 为 null 返回 -1 。如果 s 在 string 中出现次数不到 count 次，则返回 -1。
	 * 
	 * @param string 任意字符串。
	 * @param s 任意字符串。
	 * @param count 指定 s 在 string 中出现的次数。
	 * @return 字符串 s 在 string 中出现第 count 次时的索引。
	 * @throws IllegalArgumentException 如果 count 小于等于 0。
	 */
	public static int lastIndexOf(String string, String s, int count) {
		if (string == null) {
			return -1;
		}
		Assert.isTrue(count > 0);
		int index = string.length();
		for (int i = 0; i < count; i++) {
			index = string.lastIndexOf(s, index - 1);
			if (index == -1) {
				return -1;
			}
		}
		return index;
	}

	/**
	 * 返回一个新的字符串，它是此字符串的一个子字符串。该子字符串始于指定索引处的字符，一直到此字符串末尾。
	 * <p>
	 * 该方法与 {@link String#substring(int)} 不同的是，该方法生成的 {@link String} 内部的 char[]
	 * 与原字符串无关。
	 * 
	 * @param string 要执行取子的字符串。
	 * @param beginIndex beginIndex 开始处的索引（包括）。
	 * @return 指定的子字符串，如果 string 为 null 则返回 null 。
	 * @throws IndexOutOfBoundsException 如果 beginIndex 为负或大于此 String 对象的长度。
	 * @see String#substring(int)
	 */
	public static String substring(String string, int beginIndex) {
		if (string == null) {
			return null;
		}
		return new String(string.substring(beginIndex));
	}

	/**
	 * 返回一个新字符串，它是此字符串的一个子字符串。该子字符串从指定的 beginIndex 处开始，一直到索引 endIndex - 1
	 * 处的字符。因此，该子字符串的长度为 endIndex-beginIndex。
	 * <p>
	 * 该方法与 {@link String#substring(int, int)} 不同的是，该方法生成的 {@link String} 内部的
	 * char[] 与原字符串无关。
	 * 
	 * @param string 要执行取子的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @param endIndex 结束处的索引（不包括）。
	 * @return 指定的子字符串，如果 string 为 null 则返回 null 。
	 * @throws IndexOutOfBoundsException 如果 beginIndex 为负，或 endIndex 大于此 String
	 *             对象的长度，或 beginIndex 大于 endIndex。
	 * @see String#substring(int, int)
	 */
	public static String substring(String string, int beginIndex, int endIndex) {
		if (string == null) {
			return null;
		}
		return new String(string.substring(beginIndex, endIndex));
	}

	/**
	 * 根据给定的正则表达式的匹配来拆分 value 为一个 {@link Set}。
	 * <p>
	 * 该方法调用 <code>stringSplitToSet(value, regex, true)</code> 完成。
	 * <p>
	 * 该方法返回的 Set 为 {@link LinkedHashSet} 。
	 * 
	 * @param value 需要被拆分的字符串。
	 * @param regex 定界正则表达式 。
	 * @return 字符串 Set ，根据给定正则表达式的匹配来拆分 value ，从而生成此 Set 。
	 * @throws PatternSyntaxException 如果 regex 表示的正则表达式无效。
	 * @see LinkedHashSet
	 * @see #stringSplitToSet(String, String, boolean)
	 */
	public static Set<String> stringSplitToSet(String value, String regex) {
		return stringSplitToSet(value, regex, true);
	}

	/**
	 * 根据给定的正则表达式的匹配来拆分 value 为一个 {@link Set}。
	 * <p>
	 * 该方法返回的 Set 为 {@link LinkedHashSet} 。
	 * 
	 * @param value 需要被拆分的字符串。
	 * @param regex 定界正则表达式 。
	 * @param ignoreEmptyTokens 如果为 true 则忽略 长度为零 的字符串 。
	 * @return 字符串 Set ，根据给定正则表达式的匹配来拆分 value ，从而生成此 Set 。
	 * @throws PatternSyntaxException 如果 regex 表示的正则表达式无效。
	 * @see LinkedHashSet
	 */
	public static Set<String> stringSplitToSet(String value, String regex, boolean ignoreEmptyTokens) {
		if (value == null) {
			return new LinkedHashSet<String>(6);
		}
		Set<String> set = new LinkedHashSet<String>();
		stringSplitToCollection(value, regex, set, ignoreEmptyTokens);
		return set;
	}

	/**
	 * 根据给定的正则表达式的匹配来拆分 value 为一个 {@link List}。
	 * <p>
	 * 该方法调用 <code>stringSplitToList(value, regex, true)</code> 完成。
	 * <p>
	 * 该方法返回的 List 为 {@link ArrayList} 。
	 * 
	 * @param value 需要被拆分的字符串。
	 * @param regex 定界正则表达式 。
	 * @return 字符串 List ，根据给定正则表达式的匹配来拆分 value ，从而生成此 List 。
	 * @throws PatternSyntaxException 如果 regex 表示的正则表达式无效。
	 * @see ArrayList
	 * @see #stringSplitToList(String, String, boolean)
	 */
	public static List<String> stringSplitToList(String value, String regex) {
		return stringSplitToList(value, regex, true);
	}

	/**
	 * 根据给定的正则表达式的匹配来拆分 value 为一个 {@link List}。
	 * <p>
	 * 该方法返回的 List 为 {@link ArrayList} 。
	 * 
	 * @param value 需要被拆分的字符串。
	 * @param regex 定界正则表达式 。
	 * @param ignoreEmptyTokens 如果为 true 则忽略 长度为零 的字符串 。
	 * @return 字符串 List ，根据给定正则表达式的匹配来拆分 value ，从而生成此 List 。
	 * @throws PatternSyntaxException 如果 regex 表示的正则表达式无效。
	 * @see ArrayList
	 */
	public static List<String> stringSplitToList(String value, String regex, boolean ignoreEmptyTokens) {
		if (value == null) {
			return new ArrayList<String>(5);
		}
		List<String> list = new ArrayList<String>();
		stringSplitToCollection(value, regex, list, ignoreEmptyTokens);
		return list;
	}

	private static void stringSplitToCollection(String value, String regex, Collection<String> set, boolean ignoreEmptyTokens) {
		String[] split = value.split(regex);
		for (String aSplit : split) {
			if (!ignoreEmptyTokens || aSplit.length() > 0) {
				set.add(aSplit);
			}
		}
	}

	/**
	 * 根据给定的正则表达式的匹配来拆分 value 为一个 String[]。
	 * <p>
	 * 该方法调用 <code>stringSplitToArray(value, regex, true)</code> 完成。
	 * 
	 * @param value 需要被拆分的字符串。
	 * @param regex 定界正则表达式 。
	 * @return 字符串数组 ，根据给定正则表达式的匹配来拆分 value ，从而生成此 数组 。
	 * @throws PatternSyntaxException 如果 regex 表示的正则表达式无效。
	 */
	public static String[] stringSplitToArray(String value, String regex) {
		return stringSplitToArray(value, regex, true);
	}

	/**
	 * 根据给定的正则表达式的匹配来拆分 value 为一个 String[]。
	 * 
	 * @param value 需要被拆分的字符串。
	 * @param regex 定界正则表达式 。
	 * @param ignoreEmptyTokens 如果为 true 则忽略 长度为零 的字符串 。
	 * @return 字符串数组 ，根据给定正则表达式的匹配来拆分 value ，从而生成此 数组 。
	 * @throws PatternSyntaxException 如果 regex 表示的正则表达式无效。
	 */
	public static String[] stringSplitToArray(String value, String regex, boolean ignoreEmptyTokens) {
		if (value == null) {
			return EMPTY_STRINGS;
		}
		List<String> list = stringSplitToList(value, regex, ignoreEmptyTokens);
		if (list.isEmpty()) {
			return EMPTY_STRINGS;
		}
		return list.toArray(new String[list.size()]);
	}

}
