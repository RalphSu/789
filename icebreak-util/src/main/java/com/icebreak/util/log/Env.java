package com.icebreak.util.log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Env {
	
	private final static String regex = "\\$\\{[^\\$]*\\}";
	
	private final static Pattern pattern = Pattern.compile(regex);
	
	public static String analyze(String s){
		for(Matcher matcher = pattern.matcher(s); matcher.find(); matcher = pattern.matcher(s)){
			String t = matcher.group();
			t = t.substring(2, t.length() - 1);
			s = matcher.replaceFirst(System.getProperty(t, ""));
		}
		return s;
	}

}
