package com.icebreak.p2p.ws.result;

import com.icebreak.p2p.ws.service.ResultEnum;
import com.icebreak.util.lang.result.ResultBase;
import com.icebreak.util.lang.util.StringUtil;

public class P2PBaseResult extends ResultBase {
	
	private static final long serialVersionUID = 5156892170604621621L;
	ResultEnum resultEnum = ResultEnum.UN_KNOWN_EXCEPTION;
	
	private String url;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public boolean isExecuted() {
		
		return ResultEnum.EXECUTE_SUCCESS == resultEnum ? true : false;
	}
	
	public ResultEnum getResultEnum() {
		return resultEnum;
	}
	
	public void setResultEnum(ResultEnum resultEnum) {
		this.resultEnum = resultEnum;
		if (this.resultEnum != null) {
			if (StringUtil.isEmpty(this.getMessage())) {
				this.setMessage(this.resultEnum.getMessage());
			}
			
		}
	}
	
	@Override
	public void setSuccess(boolean success) {
		super.setSuccess(success);
		if (success)
			resultEnum = ResultEnum.EXECUTE_SUCCESS;
	}
	
	@Override
	public String toString() {
		return "P2PBaseResult [resultEnum=" + resultEnum + ", url=" + url + "]";
	}
	
}
