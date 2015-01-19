package com.icebreak.p2p.front.controller.trade.download;

import java.util.HashMap;
import java.util.Map;

public class DataMap {
	private static Map<String,Object> objMap;
	public static void setOjbMap(String str,Object obj){
		objMap=new HashMap<String, Object>();
		objMap.put(str, obj);
	}
	public static Map<String,Object> getMap(){
		return objMap;
	}
}
