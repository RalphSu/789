package com.icebreak.util.lang.security;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

public class EncryptionUtil {
	
	public enum DigestALGEnum {
		SHA,
		MD5;
	}
	
	public static byte[] digestData(String data, DigestALGEnum alg) {
		
		byte[] cData = new byte[] {};
		try {
			MessageDigest digest = MessageDigest.getInstance(alg.toString());
			
			String firstLetter = data.substring(0, 1);
			String lastLetter = data.substring(data.length() - 1);
			String tmpData = firstLetter + "<|" + data + "|>" + lastLetter;
			
			tmpData = data + tmpData + data;
			digest.update(tmpData.getBytes("UTF8"));
			
			cData = digest.digest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cData;
	}
	
	public static String digestDataStr(String data, DigestALGEnum alg) {
		return new String(digestData(data, alg));
	}
	
	public static char[] encodeWithHex(byte[] data) {
		return Hex.encodeHex(data);
	}
	
	public static String encodeWithHexStr(String data, DigestALGEnum alg) {
		return new String(encodeWithHex(digestData(data, alg)));
	}
	
	public static String encodeWithHexStrNoDigest(String data) {
		byte[] pData = new byte[] {};
		try {
			pData = data.getBytes("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(Hex.encodeHex(pData));
	}
	
	public static String decodeWithHexStrNoDigest(String data) {
		char[] cData;
		byte[] pData = new byte[] {};
		try {
			cData = data.toCharArray();
			pData = Hex.decodeHex(cData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(pData);
	}
	
}
