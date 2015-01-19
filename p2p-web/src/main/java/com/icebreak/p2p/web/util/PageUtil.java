package com.icebreak.p2p.web.util;

import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;

public class PageUtil {
	public static <T> Page<T> getCovertPage(QueryBaseBatchResult<T> batchResult) {
		long start = (batchResult.getPageNumber() - 1) * batchResult.getPageSize();
		Page<T> newPage = new Page<T>(start, batchResult.getTotalCount(),
			(int) batchResult.getPageSize(), batchResult.getPageList());
		return newPage;
	}
}
