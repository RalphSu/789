package com.icebreak.p2p.authority.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.icebreak.p2p.dataobject.Permission;

public class PermissionSorter {
	/**
	 * 比较器
	 */
	private static Comparator<Permission> comparator = new Comparator<Permission>(){
		@Override
		public int compare(Permission o1, Permission o2) {
			String op1 = o1.getOperate();
			String op2 = o2.getOperate();
			int len1 = 0;
			int len2 = 0;
			if(op1 != null){
				len1 = op1.length();
			}
			if(op2 != null){
				len2 = op2.length();
			}
			return len2 - len1;
		}
		
	};
	
	/**
	 * 排序
	 * @return
	 */
	public static List<Permission> sort(List<Permission> permissions){
		return sort(permissions, comparator);
	}
	
	/**
	 * 排序
	 * @param permissions
	 * @param comparator
	 */
	public static List<Permission> sort(List<Permission> permissions, Comparator<Permission> comparator){
		Permission[] ps = new Permission[permissions.size()];
		permissions.toArray(ps);
		Arrays.sort(ps, comparator);
		return Arrays.asList(ps);
	}

}
