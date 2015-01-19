package com.icebreak.p2p.base.data.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebreak.p2p.base.data.BaseDataLoader;
import com.icebreak.p2p.service.openingbank.api.OpeningBankService;

@Service("baseDataLoader")
public class BaseDataLoaderImp implements BaseDataLoader {
	
	@Autowired
	OpeningBankService							openingBankService;
	
	private static Map<String, Object>			entityCache		= new HashMap<String, Object>();
	
	private static Map<String, Collection<?>>	entityCacheList	= new HashMap<String, Collection<?>>();
	
	private static final String					CQ_DISTRICT_KEY	= "CQ_DISTRICT_KEY";
	
	/**
	 * @param mapList
	 */
	protected void addChongqingCounties(List<Map<String, Object>> mapList) {
		Map<String, Object> itemMap = new HashMap<String, Object>();
		itemMap.put("id", "江北");
		itemMap.put("name", "江北");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "大渡口");
		itemMap.put("name", "大渡口");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "沙坪坝");
		itemMap.put("name", "沙坪坝");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "小龙坎");
		itemMap.put("name", "小龙坎");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "九龙坡");
		itemMap.put("name", "九龙坡");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "南岸");
		itemMap.put("name", "南岸");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "北碚");
		itemMap.put("name", "北碚");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "渝北");
		itemMap.put("name", "渝北");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "巴南");
		itemMap.put("name", "巴南");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "经开区");
		itemMap.put("name", "经开区");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "北部新区");
		itemMap.put("name", "北部新区");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "高新");
		itemMap.put("name", "高新");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "万州");
		itemMap.put("name", "万州");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "涪陵");
		itemMap.put("name", "涪陵");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "长寿");
		itemMap.put("name", "长寿");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "黔江");
		itemMap.put("name", "黔江");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "永川");
		itemMap.put("name", "永川");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "江津");
		itemMap.put("name", "江津");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "合川");
		itemMap.put("name", "合川");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "大足");
		itemMap.put("name", "大足");
		mapList.add(itemMap);
		itemMap = new HashMap<String, Object>();
		itemMap.put("id", "綦江");
		itemMap.put("name", "綦江");
		mapList.add(itemMap);
	}
	
	@Override
	public synchronized void clearCache() {
		synchronized (entityCache) {
			synchronized (entityCacheList) {
				entityCache.clear();
				entityCacheList.clear();
			}
		}
	}
}
