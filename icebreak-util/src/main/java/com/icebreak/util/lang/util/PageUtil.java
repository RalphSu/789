package com.icebreak.util.lang.util;

public class PageUtil {
	
	private int listbegin;
	
	private int listend;
	
	private int currentPage;
	
	private int liststep = 8;
	
	private int totalPage;
	
	/**
	 * 构建一个<code>PageUtil.java</code>
	 */
	public PageUtil() {
		super();
	}
	
	/**
	 * 构建分页DIV
	 */
	public static String buildPager(int currentPage, int totalPages, int totalItems, String url) {
		
		String xml = "<div>共 " + totalItems + " 条记录, 当前第 " + currentPage + " 页 , 共" + totalPages
						+ " 页  ";
		
		PageUtil pageUtil = new PageUtil();
		pageUtil.setCurrentPage(currentPage);
		pageUtil.setTotalPage(totalPages);
		
		StringBuffer sb = new StringBuffer(xml);
		int i = pageUtil.getListbegin();
		int end = pageUtil.getListend();
		
		// 当步长大于总共页数的时候需要处理的
		if (pageUtil.getListend() > pageUtil.getTotalPage()) {
			i = pageUtil.getTotalPage() - pageUtil.getListstep() + 1;
			end = pageUtil.getTotalPage();
			if (pageUtil.getTotalPage() < pageUtil.getListstep()) {
				i = 1;
			}
		}
		
		// 第一页的时候应该等于步长
		if (i == 1 && end != pageUtil.getTotalPage()) {
			end = pageUtil.getListstep();
			// 第一页显示
			if (pageUtil.getListstep() > pageUtil.getTotalPage()) {
				end = pageUtil.getTotalPage();
			}
		}
		
		// 首页
		if (pageUtil.getListbegin() != 1) {
			
			String temp = url + ((url.indexOf("?") == -1) ? "?pi=1" : "&pi=1");
			sb.append("<a href='" + temp + "'>首页</a>&nbsp;&nbsp;");
		}
		
		// 上一页
		if (pageUtil.getCurrentPage() != 1) {
			String temp = url
							+ ((url.indexOf("?") == -1) ? "?pi=" + (pageUtil.getCurrentPage() - 1)
								: "&pi=" + (pageUtil.getCurrentPage() - 1));
			
			sb.append("<a href='" + temp + "'>上一页</a>&nbsp;&nbsp;");
		}
		
		// 循环分页数据
		for (; i <= end; i++) {
			String temp = url + ((url.indexOf("?") == -1) ? "?pi=" + i : "&pi=" + i);
			if (pageUtil.getCurrentPage() == i) {
				sb.append("<b><a href='" + temp + "'>" + i + "</a></b>&nbsp;&nbsp;");
			} else {
				sb.append("<a href='" + temp + "'/>" + i + "</a>&nbsp;&nbsp;");
			}
		}
		
		// 下一页
		if (pageUtil.getCurrentPage() < pageUtil.getTotalPage()) {
			String temp = url
							+ ((url.indexOf("?") == -1) ? "?pi=" + (pageUtil.getCurrentPage() + 1)
								: "&pi=" + (pageUtil.getCurrentPage() + 1));
			sb.append("<a href='" + temp + "'>下一页&nbsp;&nbsp;</a>");
		}
		
		// 已经是最后页了
		if (end != pageUtil.getTotalPage()) {
			
			String temp = url
							+ ((url.indexOf("?") == -1) ? "?pi=" + pageUtil.getTotalPage()
								: "&pi=" + pageUtil.getTotalPage());
			sb.append("<a href='" + temp + "'>尾页</a>");
		}
		
		sb.append("</div>");
		
		return sb.toString();
	}
	
	public int getListbegin() {
		int a = (int) Math.ceil((double) liststep / 2);
		if (currentPage <= a) {
			listbegin = 1;
		} else {
			listbegin = currentPage - (a - 1);
		}
		return listbegin;
	}
	
	public int getListend() {
		int a = (int) Math.ceil((double) liststep / 2);
		listend = currentPage + a;
		return listend;
	}
	
	/**
	 * @return Returns the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	
	/**
	 * @param currentPage The currentPage to set.
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	/**
	 * @return Returns the liststep
	 */
	public int getListstep() {
		return liststep;
	}
	
	/**
	 * @param liststep The liststep to set.
	 */
	public void setListstep(int liststep) {
		this.liststep = liststep;
	}
	
	/**
	 * @return Returns the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}
	
	/**
	 * @param totalPage The totalPage to set.
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	/**
	 * @param listbegin The listbegin to set.
	 */
	public void setListbegin(int listbegin) {
		this.listbegin = listbegin;
	}
	
	/**
	 * @param listend The listend to set.
	 */
	public void setListend(int listend) {
		this.listend = listend;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
			"PageUtil [listbegin=%s, listend=%s, currentPage=%s, liststep=%s, totalPage=%s]",
			listbegin, listend, currentPage, liststep, totalPage);
	}
}
