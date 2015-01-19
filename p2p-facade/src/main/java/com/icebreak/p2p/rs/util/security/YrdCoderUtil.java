package com.icebreak.p2p.rs.util.security;

import java.util.HashMap;
import java.util.Map;

import com.icebreak.p2p.util.MD5Util;
import com.icebreak.p2p.util.security.AES;
import com.icebreak.p2p.util.security.Coder;
import com.icebreak.p2p.util.security.RSACoder;
import com.icebreak.p2p.util.security.YrdRSACoder;

public class YrdCoderUtil extends Coder{
	private static String privateKey;
	private static String splitChar= "&";
	static{
		try {
			privateKey = YrdRSACoder.getDefaultPrivateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取AES加密过的数据
	 * @param encriptData
	 * @param secKey
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getAESDecriptedParams(String encriptData, String secKey) throws Exception {
		String decodeStr  = null;
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			AES aes = new AES(secKey);
			decodeStr = aes.getDecrypt(encriptData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] decodeAry = decodeStr.split(splitChar);
		for(String arrayStr : decodeAry){
			String[] param = arrayStr.split("=");
			if(param != null){
				if(param.length == 2 ){
					params.put(param[0], param[1]);
				}else if(param.length>2){
					params.put(param[0], arrayStr.substring(arrayStr.indexOf("=")+1));
				}else{
					params.put(param[0], null);
				}
			}
		}
		params.put("secKey", secKey);
		return params;
	}
	/**
	 * 私钥解密公钥加密的AES加密key
	 * @param securityKey
	 * @return
	 * @throws Exception
	 */
	
	public static String getRSADecriptedKey(String securityKey) throws Exception {
		byte[] data = decryptBASE64(securityKey);
		byte[] decodedData = RSACoder
				.decryptByPrivateKey(data, privateKey);
		return new String(decodedData);
	}
	
	/**
	 * rsa私钥加密key
	 * @param secKey
	 * @return
	 * @throws Exception
	 */
	public static String getRSAEncriptedData(String secKey) throws Exception {
		byte[] data = secKey.getBytes();
		byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);
		String encodedString =  encryptBASE64(encodedData);
		return encodedString;
	}
	/**
	 * 私钥签名加密过的key
	 * @param resSecKey
	 * @return
	 * @throws Exception
	 */
	public static String signRSAData(String resSecKey) throws Exception {
		byte[] encodedData = decryptBASE64(resSecKey);
		String sign = RSACoder.sign(encodedData, privateKey);
		return sign;
	}
	public static Map<String, Object> getKeyPairMap() throws Exception {
		return RSACoder.generateKeyMap();
	}
	public static String getAppKey(String userName) {
		String appKey = MD5Util.getMD5_16(userName);
		return appKey;
	}
	
}
