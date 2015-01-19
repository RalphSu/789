package com.icebreak.util.id;

import com.alibaba.fastjson.JSON;
import com.icebreak.util.env.Env;
import com.icebreak.util.lang.exception.Exceptions;
import com.icebreak.util.lang.ip.IPUtil;
import com.icebreak.util.lang.security.DigestUtil;
import com.icebreak.util.lang.security.DigestUtil.DigestALGEnum;
import com.icebreak.util.net.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ID {
	
	private static final Logger logger = LoggerFactory.getLogger(ID.class.getName());
	
	/**
	 * 业务编码长度
	 */
	public static final int BUSSINESS_CODE_LEN = 8;
	
	/**
	 * serverId 长度
	 */
	public static final int SERVER_ID_LEN = 5;
	
	/**
	 * 时间字符串长度
	 */
	public static final int TIME_FLIED_LEN = 20;
	
	/**
     *
     */
	public static final int SHARDING_FLIED_LEN = 2;
	
	/**
	 * id长度
	 */
	public static final int ID_LEN = BUSSINESS_CODE_LEN + SERVER_ID_LEN + TIME_FLIED_LEN
										+ SHARDING_FLIED_LEN;
	
	/**
	 * 每毫秒内生成id最大数
	 */
	private static final int COUNT_IN_MILL_SECOND = 1000;
	
	/**
	 * 17位的时间格式
	 */
	private static final String TIME_FORMAT = "yyyyMMddHHmmssSSS";
	
	private static AtomicLong lastTime = new AtomicLong(0);
	
	/**
	 * 时间字符串缓存
	 */
	private static volatile String lastTimeStrCache = null;
	
	/**
	 * 时间缓存
	 */
	private static volatile long lastTimeCache = 0l;
	
	/**
	 * 服务地址
	 */
	private static String serverRegisterUrl = getServerRegisterUrl();
	
	private static Random rand = new Random();
	
	private static final char PADDING_CHAR = '0';
	
	/**
	 * 默认物理地址，如果获取mac失败，用此物理地址
	 */
	private static final String DEFAULT_MAC = "00:00:00:00:00:00";
	
	private static String port = null;
	
	/**
	 * 获取服务id
	 */
	private static String serverId = null;
	
	private static final String DEFAULT_SHARDING = "00";
	
	/**
	 * 从cs服务器注册服务时的安全码
	 */
	public static final String SECURITY_KEY = "id";
	
	static {
		logger.info("ID生成器启动...");
		genServerId();
	}
	
	private static String getServerRegisterUrl() {
		Properties prop = new Properties();
		try {
			prop.load(ID.class.getClassLoader().getResourceAsStream("common_util_prop.properties"));
		} catch (IOException e) {
			logger.warn("注册服务失败{}", e);
		}
		return (String) prop.get("ID.serverRegisterUrl");
	}
	
	private static void genServerId() {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("ip", getIp());
		dataMap.put("port", getPort());
		dataMap.put("mac", getMAC());
		//请求签名
		dataMap.put(DigestUtil.SIGN_KEY,
			DigestUtil.digest(dataMap, SECURITY_KEY, DigestALGEnum.MD5));
		
		//1.通过cs提供的服务器注册服务获取serverId
		try {
			logger.info("注册server，获取服务id..");
			String data = HttpRequest.post(serverRegisterUrl, dataMap, false).readTimeout(5_000)
				.connectTimeout(5_000).body();
			
			logger.info("注册server,返回数据:{}", data);
			ServerRegisterResult serverRegisterResult = (ServerRegisterResult) JSON.parse(data);
			if (!serverRegisterResult.isSuccess()) {
				logger.error("注册server失败,原因：{}", serverRegisterResult.getMessage());
			} else {
				serverId = Integer.toString(serverRegisterResult.getServerId());
				serverId = padding(serverId, SERVER_ID_LEN, PADDING_CHAR);
				logger.info("注册server成功，serverId：{}", serverId);
			}
		} catch (Exception e) {
			logger.warn("请求服务注册失败:{}", e.getMessage());
		}
		//2.获取失败，随机生成长度为5的serverId
		if (serverId == null) {
			serverId = padding(Integer.toString(rand.nextInt(100000)), SERVER_ID_LEN, PADDING_CHAR);
			logger.warn("随机生成{}位serverid:{}", SERVER_ID_LEN, serverId);
		}
	}
	
	/**
	 * 填充字符串
	 * 
	 * @param str 待填充字符串
	 * @param len 填充后的位数
	 * @param padding 填充字符
	 */
	private static String padding(String str, int len, char padding) {
		if (str.length() < len) {
			StringBuilder sb = new StringBuilder(len);
			int toPadLen = len - str.length();
			for (int i = 1; i <= toPadLen; i++) {
				sb.append(padding);
			}
			sb.append(str);
			return sb.toString();
		} else {
			return str;
		}
	}
	
	/**
	 * 生成长度为35的业务id
	 * 
	 * @param code 业务id长度为8位，不能为null，不要包含空格(不便于以后处理),
	 * @param sharding 分区标识,长度为2为,不能为空
	 * @return 业务id
	 */
	public static String newID(String code, String sharding) {
		if ((code == null) || (code.length() != BUSSINESS_CODE_LEN)) {
			throw Exceptions.newRuntimeExceptionWithoutStackTrace("ID业务编码长度不正确,期望"
																	+ BUSSINESS_CODE_LEN + "位:"
																	+ code);
		}
		if (sharding == null || sharding.length() != SHARDING_FLIED_LEN) {
			throw Exceptions.newRuntimeExceptionWithoutStackTrace("ID分区长度不正确,期望"
																	+ SHARDING_FLIED_LEN + "位:"
																	+ sharding);
		}
		StringBuilder idBuilder = new StringBuilder(ID_LEN);
		idBuilder.append(code).append(serverId);
		appendTime(idBuilder);
		idBuilder.append(sharding);
		return idBuilder.toString();
	}
	
	/**
	 * 生成长度为35的业务id,数据存储分区标识为用'00'
	 * 
	 * @param code 业务id长度为8位，不能为null，不要包含空格(不便于以后处理),
	 * @return 业务id
	 */
	public static String newID(String code) {
		return newID(code, DEFAULT_SHARDING);
	}
	
	private static String getMAC() {
		String mac;
		try {
			mac = IPUtil.getMACAddress();
			mac = mac.replace("-", ":");
		} catch (Exception e) {
			mac = DEFAULT_MAC;
			logger.info("获取mac失败，使用默认物理地址:{}", mac);
		}
		return mac;
	}
	
	/**
	 * @return 返回当前端口
	 */
	private static String getPort() {
		logger.info("获取服务端口...");
		initPort();
		if (!Env.isErrorPort(port)) {
			return port;
		}
		port = "0" + rand.nextInt(10000);
		logger.info("使用随机端口:{}", port);
		return port;
	}
	
	/**
	 * tomcat 比较蛋疼，embed版和普通版的ObjectName不一样
	 * 通过service来获取所有的connector，然后通过协议判断http服务端口
	 */
	private static void initPort() {
		if (port == null) {
			port = Env.getPort();
		}
	}
	
	private static String getIp() {
		return IPUtil.getFirstNoLoopbackIPV4Address();
	}
	
	/**
	 * 添加20位时间
	 */
	private static void appendTime(StringBuilder idBuilder) {
		Date now = new Date();
		//1.获取时间标识
		long time = createTime(now.getTime());
		String timeStr;
		//2.长度17位的当前时间
		if (lastTimeCache == now.getTime()) {
			timeStr = lastTimeStrCache;
		} else {
			timeStr = new SimpleDateFormat(TIME_FORMAT)
				.format(new Date(time / COUNT_IN_MILL_SECOND));
			lastTimeStrCache = timeStr;
			lastTimeCache = now.getTime();
		}
		idBuilder.append(timeStr);
		//3.添加毫秒后的3位精确数字,保证在并发下不会生成重复的
		String dt = Long.toString(time);
		idBuilder.append(dt.substring(dt.length() - 3));
	}
	
	/**
	 * 通过id获取业务编码
	 * 
	 * @param id id
	 * @return 获取业务编码
	 */
	public static String getBussinessCode(String id) {
		checkId(id);
		return id.substring(0, BUSSINESS_CODE_LEN);
	}
	
	private static void checkId(String id) {
		if (id == null || id.length() != ID_LEN) {
			throw Exceptions.newRuntimeExceptionWithoutStackTrace("id格式不正确：" + id);
		}
	}
	
	public static String getTime(String id) {
		checkId(id);
		return id.substring(BUSSINESS_CODE_LEN + SERVER_ID_LEN, BUSSINESS_CODE_LEN + SERVER_ID_LEN
																+ TIME_FLIED_LEN - 3);
	}
	
	/**
	 * 通过id获取分区标识
	 * 
	 * @param id id
	 * @return 分区标识
	 */
	public static String getSharding(String id) {
		checkId(id);
		return id.substring(BUSSINESS_CODE_LEN + SERVER_ID_LEN + TIME_FLIED_LEN);
	}
	
	/**
	 * 通过id获取serverId
	 * 
	 * @param id id
	 * @return serverId
	 */
	public static String getServerId(String id) {
		checkId(id);
		return id.substring(BUSSINESS_CODE_LEN, BUSSINESS_CODE_LEN + SERVER_ID_LEN);
	}
	
	/**
	 * 根据当前时间(毫秒)获取到唯一的时间标识
	 * 
	 * @param currentTimeMillis 当前毫秒时间
	 * @return 时间标识
	 */
	private static long createTime(long currentTimeMillis) {
		long timeMillis = currentTimeMillis * COUNT_IN_MILL_SECOND;
		while (true) {
			long last = lastTime.get();
			if (timeMillis > last) {
				if (lastTime.compareAndSet(last, timeMillis)) {
					break;
				}
			} else {
				if (lastTime.compareAndSet(last, last + 1)) {
					timeMillis = last + 1;
					break;
				}
			}
		}
		return timeMillis;
	}
	
}
