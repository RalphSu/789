package com.icebreak.p2p.util.security;

import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import java.io.*;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class YrdRSACoder extends Coder{
	public static final String KEY_ALGORITHM = "RSA";
	//"-----BEGIN PRIVATE KEY-----"
	private static String privateKey2 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPPNt40uxmDcB3BS"
		+ "KlsFtfkGTG0Be3Z5moY6V2/ceI4Bg+UpN80BXl1LIK70HvfWsvgO3fU52+AmGK2g"
		+ "2QzgMCrqQOYldE4FWRP9J+hRYg6Neb+Oame4hLRWPSpeB4qr+XD6NWoUXmF5yItE"
		+ "A2fmvZWdpFoiSdZkH6Lhga1sl1BPAgMBAAECgYBhzg4rii81Hi+xxJSPTVwyGW/B"
		+ "bw3Len8eB/uZuXV3am4yGXX0PO9RN9lVtkNSI4RKdRRf0yU856uQw+0u+CT52eI5"
		+ "fbJZba1g4oBDLBovMyZI0dUJZMAV2zJZNDxXAX2j/u3ZVSzkeeNTKbQ/NyXrKcaq"
		+ "xc7+yA7uFQfoy4qFQQJBAP6pYOzp+WSzr2Sr1U1mtPmYX1nCN0Teu/omahbDT+7w"
		+ "R5UfAWrH6CkfMmmo0Utgbh/Y+ouX6deXJ7Ml5Be/xCECQQD1FbrfmkJmNxW0zIam"
		+ "kdXAqCuTPEyYIPvSyvF7/2yaqEMp4xTnLtW2l5iBKseSP6NK8tUzBw9jVoS0VRbG"
		+ "yIZvAkEAhjnio50DXYe0B7zmVcCv3OrqPxY2KW+45rW+rzbM9+Tf5gKMraqmfJ47"
		+ "5SMdBbzS7qhgDpnIoGDEhRGQss/Z4QJAF+hTv2Yz3fa3plhhNjR5rn55KbazHg/x"
		+ "oMFtRxRGitupGZfuPRMDg/lLxiXfK/QLQM9pXr3skVsqPNEkFKYQ/wJAEtixUzoO"
		+ "1iQzRSj6s95XYAtHwEPkrwe9reOjDNZ3X3Wsx9ryXgNuXakxfVR9eOENnjp9nLuH"
		+ "WtiPrpbz/jL4gw==";
//		+ "-----END PRIVATE KEY-----"
	//"-----BEGIN PUBLIC KEY-----"
	private static String publicKey3 = "MIGfMA0GCSqGSIb3DQEBAQUB";
//		+ "-----END PUBLIC KEY-----";
	private static final String RSA_PUBKEY_FILENAME = "/security/rsa_public_key.pem";
	private static final String RSA_PRIKEY_FILENAME = "/security/private_key.pem";
	private static RSAPrivateKey privateKey = null;
	private static RSAPublicKey publicKey= null;
    private static File publicKeyFile2 = null;
    private static File privateKeyFile2 = null;
    static final int KEY_SIZE = 1024;
    
	static {
		publicKeyFile2 = new File(getRSAPairFilePath(RSA_PUBKEY_FILENAME));
		privateKeyFile2 = new File(getRSAPairFilePath(RSA_PRIKEY_FILENAME));
		try {
			readKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	// 同步读出保存的密钥对
    private static Object readKeyPair() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
        	 BufferedReader br = new BufferedReader(new FileReader(privateKeyFile2));
             String s = br.readLine();  
             StringBuffer privatekey = new StringBuffer();  
             s = br.readLine();  
             while (s.charAt(0) != '-') {  
                 privatekey.append(s + "\r");  
                 s = br.readLine();  
             }  
             privateKey2 = privatekey.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(fis);
        }
        try {
       	 BufferedReader br = new BufferedReader(new FileReader(publicKeyFile2));
            String s = br.readLine();  
            StringBuffer publicKey = new StringBuffer();  
            s = br.readLine();  
            while (s.charAt(0) != '-') {  
            	publicKey.append(s + "\r");  
                s = br.readLine();  
            }  
            publicKey3 = publicKey.toString();
       } catch (Exception ex) {
           ex.printStackTrace();
       } finally {
           IOUtils.closeQuietly(ois);
           IOUtils.closeQuietly(fis);
       }
        return null;
    }
	  /**
     * 返回生成/读取的密钥对文件的路径。
     */
    private static String getRSAPairFilePath(String fileName) {
        String urlPath = RSACoder.class.getResource("/").getPath();
        return (new File(urlPath).getParent() + fileName);
    }
    
    public static String getDefaultPrivateKey() throws Exception{
	        return privateKey2;
	 }
    public static String getDefaultPublicKey() throws Exception{
	        return publicKey3;
	 }
	 public static String getPrivateKey() throws Exception{
		 byte[] keybyte = decryptBASE64(privateKey2);
         
         KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);  
           
         PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keybyte);  
           
         Key privateKey = kf.generatePrivate(keySpec);  
	        return encryptBASE64(privateKey.getEncoded());     
	 }
	 
	 public static String getPublicKey() throws Exception{
		 byte[] keybyte = decryptBASE64(publicKey3);
         
         KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);  
           
         PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keybyte);  
           
         Key publicKey = kf.generatePublic(keySpec);  
	        return encryptBASE64(publicKey.getEncoded());     
	 }
	 
	 /**
		 * 解密<br>
		 * 用私钥解密
		 * 
		 * @param data
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public static byte[] decryptByPrivateKey(byte[] data, String key)
				throws Exception {
			// 对密钥解密
			byte[] keyBytes = decryptBASE64(key);

			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			return cipher.doFinal(data);
		}

		/**
		 * 解密<br>
		 * 用公钥解密
		 * 
		 * @param data
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public static byte[] decryptByPublicKey(byte[] data, String key)
				throws Exception {
			// 对密钥解密
			byte[] keyBytes = decryptBASE64(key);

			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);

			return cipher.doFinal(data);
		}

		/**
		 * 加密<br>
		 * 用公钥加密
		 * 
		 * @param data
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public static byte[] encryptByPublicKey(byte[] data, String key)
				throws Exception {
			// 对公钥解密
			byte[] keyBytes = decryptBASE64(key);

			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			return cipher.doFinal(data);
		}
	 /**
		 * 加密<br>
		 * 用私钥加密
		 * 
		 * @param data
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public static byte[] encryptByPrivateKey(byte[] data, String key)
				throws Exception {
			// 对密钥解密
			byte[] keyBytes = decryptBASE64(key);

			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);

			return cipher.doFinal(data);
		}
		public static void main(String[] args) {
			System.out.println(RSACoder.class.getResource("/").getPath());
		}
}
