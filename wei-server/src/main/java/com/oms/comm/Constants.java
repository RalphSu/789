package com.oms.comm;

public class Constants {
	/**
	 * 常量说明：
	 * 此处定义的常量需要持久化，可以保存在数据库中，在需要的地方读取。
	 * 在多企业号中，最好以每个应用来定义。
	 */
	public static final int AGENTID = 1;
	public static final String TOKEN = "luoqb";
	public static final String CORPID = "wxb49742b14a7ff88d";//对应APPid
	public static final String SECRET = "5672160586b05b79b95a5bd23bc1ab9c";
	public static final String encodingAESKey = "d9a7da07d40adb9f0aceace26084fa6a";
	
	public static final String SYSTEMNAME ="789金融";//项目名称
	
	public static final String firstMenu = "不知道1";//一级菜单
	public static final String secondMenu = "不知道2";//二级菜单
	public static final String thirdMenu = "不知道3";//三级菜单
	
	public static final String[] firstMenuChildren = new String[]{"测试菜单1"};//一级子菜单,最多5个
	public static final String[] secondMenuChildren = new String[]{"测试菜单2"};//二级子菜单
	public static final String[] thirdMenuChildren = new String[]{"测试菜单3"};//三级子菜单
	
	
	public static final String[] firstMenuChildrenURL = new String[]{"http://aluo151.xicp.net/userInfo.do"};//一级子菜单,最多5个
	public static final String[] secondMenuChildrenURL = new String[]{"http://aluo151.xicp.net/appserver/userInfo.do"};//二级子菜单
	public static final String[] thirdMenuChildrenURL = new String[]{"http://aluo151.xicp.net/appserver/userInfo.do"};//三级子菜单
}
