package com.icebreak.util.session;

import java.io.*;

public class ByteObject {
	/**
	 * 字节数组转为对向
	 * @param bytes
	 * @return
	 */
	public static Object bytes2Object(byte[] bytes) {
		if (bytes == null || bytes.length < 1) {
			return null;
		}
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			Object object = objectInputStream.readObject();
			objectInputStream.close();
			byteArrayInputStream.close();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("字节数组转为对象失败。。。", e);
		}
	}
	
	/**
	 * 对象转为字节数组
	 * @param object
	 * @return
	 */
	public static byte[] object2Bytes(Object object) {
		if (object == null) {
			return null;
		}
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			byte[] bytes = byteArrayOutputStream.toByteArray();
			objectOutputStream.close();
			byteArrayOutputStream.close();
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("对象转为字节数组失败。。。", e);
		}
	}
	
}
