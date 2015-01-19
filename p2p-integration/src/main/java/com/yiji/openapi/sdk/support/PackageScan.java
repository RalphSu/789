package com.yiji.openapi.sdk.support;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageScan {
	private static final Logger logger = LoggerFactory.getLogger(PackageScan.class);
	
	public static Map<String, Class<?>> scan(String packageName) {
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			String resourceName = packageName.replaceAll("\\.", "/");
			URL url = loader.getResource(resourceName);
			logger.debug("currUrl:{}", url);
			String protocol = url.getProtocol();
			if (StringUtils.equals("file", protocol)) {
				File urlFile = new File(url.toURI());
				File[] files = urlFile.listFiles();
				for (File f : files)
					getClassName(packageName, f, classMap);
			} else if (StringUtils.equals("jar", protocol)) {
				// 如果是jar包文件
				// 定义一个JarFile
				JarFile jar;
				try {
					// 获取jar
					jar = ((JarURLConnection) url.openConnection()).getJarFile();
					// 从此jar包 得到一个枚举类
					Enumeration<JarEntry> entries = jar.entries();
					// 同样的进行循环迭代
					while (entries.hasMoreElements()) {
						// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
						JarEntry entry = entries.nextElement();
						String name = entry.getName();
						// 如果是以/开头的
						if (name.charAt(0) == '/') {
							// 获取后面的字符串
							name = name.substring(1);
						}
						// 如果前半部分和定义的包名相同
						if (name.startsWith(resourceName)) {
							int idx = name.lastIndexOf('/');
							// 如果以"/"结尾 是一个包
							if (idx != -1) {
								// 获取包名 把"/"替换成"."
								packageName = name.substring(0, idx).replace('/', '.');
							}
							// 如果可以迭代下去 并且是一个包
							if ((idx != -1)) {
								// 如果是一个.class文件 而且不是目录
								if (name.endsWith(".class") && !entry.isDirectory()) {
									// 去掉后面的".class" 获取真正的类名
									String className = name.substring(packageName.length() + 1,
										name.length() - 6);
									try {
										Class<?> cl = Thread.currentThread()
											.getContextClassLoader()
											.loadClass(packageName + '.' + className);
										classMap.put(className, cl);
									} catch (ClassNotFoundException e) {
										logger.error("加载类失败", e);
									}
								}
							}
						}
					}
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		logger.debug("classMap:{}", classMap);
		return classMap;
	}
	
	private static void getClassName(String packageName, File packageFile,
										Map<String, Class<?>> classMap) {
		if (packageFile.isFile()) {
			String className = packageFile.getName().replace(".class", "");
			String classFullName = packageName + "." + className;
			classMap.put(className, loadClass(classFullName));
		} else {
			File[] files = packageFile.listFiles();
			String tmPackageName = packageName + "." + packageFile.getName();
			for (File f : files) {
				getClassName(tmPackageName, f, classMap);
			}
		}
	}
	
	private static Class<?> loadClass(String classFullName) {
		try {
			return Class.forName(classFullName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	

}
