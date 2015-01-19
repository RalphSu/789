package com.icebreak.p2p.rs.service.customer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.viewObject.InvestorInfoVO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.valueobject.QueryConditions;

public class AppInvestorRecordService extends ServiceBase implements
		AppService {

	@Override
	public JSONObject execute(Map<String, Object> params,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		if(SessionLocalManager.getSessionLocal() == null){
			json.put("code", 0);
			json.put("message", "未登录");
			return json;
		}
		try{
			if(params.containsKey("pageSize") && params.containsKey("pageNo")){
				String size = params.get("pageSize").toString();
				String pageNo = params.get("pageNo").toString();
				QueryConditions queryConditions = new QueryConditions();
				queryConditions.setUserId(SessionLocalManager.getSessionLocal().getUserId());
				PageParam pageParam = new PageParam();
				pageParam.setPageNo(Integer.parseInt(pageNo));
				pageParam.setPageSize(Integer.parseInt(size));
				Page<InvestorInfoVO>  customers=personalInfoManager.pageChildrenInvestorVO(queryConditions, pageParam);
				if(customers.getResult().size()>0 && customers.getResult()!=null){
					JSONArray arry = new JSONArray();
					for(InvestorInfoVO info : customers.getResult()){
						JSONObject temp = new JSONObject();
						temp.put("userBaseId", info.getUserBaseId());
						temp.put("userName", info.getUserName());
						temp.put("realName", info.getRealName());
						temp.put("createTime", info.getRowAddTime());
						temp.put("state", info.getState());
						temp.put("memoNo", info.getMemberNo());
						temp.put("distributionQuota", info.getDistributionQuota());
						arry.add(temp);
					}
					json.put("code", 1);
					json.put("message", "查询成功");
					json.put("infos", arry);
				}else{
					json.put("code", 2);
					json.put("message", "暂无查询记录");
				}
			}else{
				json.put("code", 0);
				json.put("message", "参数size或page不完整");
			}
			
		}catch(Exception e){
			json.put("code", 0);
			json.put("message", "参数或网络异常");
			logger.error("查询投资记录时,参数或网络异常");
		}
		return json;
	}

	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.appInvestorRecordService;
	}

}
