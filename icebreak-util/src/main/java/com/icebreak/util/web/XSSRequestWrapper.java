package com.icebreak.util.web;

import com.google.common.collect.Lists;
import com.icebreak.util.lang.exception.Exceptions;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.reference.DefaultEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

public class XSSRequestWrapper extends HttpServletRequestWrapper {
	
	private static Pattern patterns = Pattern.compile("<script>(.*?)</script>"
														+ "|src[\r\n]*=[\r\n]*\\\'(.*?)\\\'"
														+ "|src[\r\n]*=[\r\n]*\\\"(.*?)\\\""
														+ "|</script>" + "|<script(.*?)>"
														+ "|eval\\((.*?)\\)"
														+ "|expression\\((.*?)\\)" + "|javascript:"
														+ "|vbscript:" + "|onload(.*?)=",
		Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	
	private static Pattern patterns2 = Pattern.compile("(<.*>.*</.*>)|(<.*/?>)",
		Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	
	private static Pattern comment = Pattern.compile("/\\*.*\\*/", Pattern.CASE_INSENSITIVE
																	| Pattern.MULTILINE
																	| Pattern.DOTALL);
	private static Encoder encoder = new DefaultEncoder(Lists.newArrayList("HTMLEntityCodec",
		"PercentCodec"));
	/**
	 * 对象是否被检测
	 */
	private boolean checked = false;
	private boolean autoCheck = false;
	
	public XSSRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
	public XSSRequestWrapper(HttpServletRequest request, boolean autoCheck) {
		super(request);
		this.autoCheck = autoCheck;
	}
	
	public void autoCheck() {
		if (autoCheck && !checked) {
			checkXSS();
		}
	}
	
	/**
	 * 检查是否被xss
	 * @return
	 */
	public void checkXSS() {
		Set<Map.Entry<String, String[]>> set = super.getParameterMap().entrySet();
		for (Entry<String, String[]> entry : set) {
			String[] values = entry.getValue();
			if (values != null) {
				for (String value : values) {
					try {
						value = canonicalize(value);
					} catch (Exception e) {
						throw Exceptions.newRuntimeExceptionWithoutStackTrace(String.format(
							"参数:[%s] 值:[%s]发现XSS注入", entry.getKey(), value));
					}
					if (patterns.matcher(value).find() || patterns2.matcher(value).find()) {
						throw Exceptions.newRuntimeExceptionWithoutStackTrace(String.format(
							"参数:[%s] 值:[%s]发现XSS注入", entry.getKey(), value));
					}
				}
			}
			
		}
		checked = true;
	}
	
	private static String canonicalize(String value) {
		value = encoder.canonicalize(value, true, false);
		return value;
	}
	
	@Override
	public String[] getParameterValues(String parameter) {
		autoCheck();
		String[] values = super.getParameterValues(parameter);
		if (checked) {
			return values;
		}
		
		if (values == null) {
			return null;
		}
		
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = stripXSS(values[i]);
		}
		
		return encodedValues;
	}
	
	/**
	 * 获取参数，如果此对象已经做过xss检查，则直接返回值
	 * @param parameter
	 * @return
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String parameter) {
		autoCheck();
		String value = super.getParameter(parameter);
		if (checked) {
			return value;
		}
		return stripXSS(value);
	}
	
	@Override
	public String getHeader(String name) {
		autoCheck();
		String value = super.getHeader(name);
		return stripXSS(value);
	}
	
	public static String stripXSS(String value) {
		if (value != null) {
			// 防止编码攻击,获得解码后的字符串
			value = canonicalize(value);
			
			// 避免 null
			value = value.replaceAll("\0", "");
			//避免注释
			value = comment.matcher(value).replaceAll("");
			
			// Remove all sections that match a pattern
			value = patterns.matcher(value).replaceAll("");
			
			//替换掉<>
			value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			//替换掉()
			value = value.replace('(', '（').replace(')', '）');
		}
		return value;
	}
	
	//	public static void main(String[] args) {
	//
	//		String str = "收藏+宝贝分";
	//
	//		System.out.println(stripXSS(str));
	//	}
}
