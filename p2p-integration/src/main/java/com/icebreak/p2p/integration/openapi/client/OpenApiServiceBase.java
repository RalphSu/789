package com.icebreak.p2p.integration.openapi.client;

import com.icebreak.p2p.integration.openapi.client.mock.impl.DefaultHttpSender;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.result.TradeResult;
import com.icebreak.p2p.util.Constants;
import com.icebreak.p2p.util.MiscUtil;
import com.icebreak.p2p.util.StringUtil;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.icebreak.util.lang.security.DigestUtil;
import com.yiji.openapi.sdk.APITool;
import com.yiji.openapi.sdk.support.Signatures;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class OpenApiServiceBase {

	public final static String SUCCESS = "EXECUTE_SUCCESS";
	public final static String SUCCESS_VALUE = "success";
	public final static String SUCCESS_STRING = "T";
	public final static String RESULT_MESSAGE = "resultMessage";
	public final static String RESULT_CODE = "resultCode";
	/** 普通日志 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected final Logger loggerAlert = LoggerFactory
			.getLogger("ALERT-APPENDER");
	protected DefaultHttpSender sender = new DefaultHttpSender();
	@Resource
	private APITool apiTool;

	protected Map<String, Object> senderPost(Map<String, String> paramMap,
			OpenApiContext openApiContext) {
		Map<String, String> standardParamsMap = buildStandardParams(openApiContext);
		paramMap.putAll(standardParamsMap);
		clearNullVallue(paramMap);
		Map<String, String> postMap = digestParam(openApiContext, paramMap);
		if (logger.isInfoEnabled())
			logger.info("==>>OpenApi入参数=" + postMap);
		HttpEntity entity = sender.producePostEntity(postMap,
				openApiContext.getInputCharset());
		return doRequestAndSetResult(entity);
	}

	protected Map<String, Object> senderPost(Map<String, String> paramMap) {
		clearNullVallue(paramMap);
		paramMap.put("sign", Signatures.sign(paramMap));
		if (logger.isInfoEnabled())
			logger.info("==>>OpenApi入参数=" + paramMap);
		HttpEntity entity = sender.producePostEntity(paramMap, "utf-8");
		return doRequestAndSetResult(entity);
	}

	protected String send(Map<String, String> paramMap) {
		clearNullVallue(paramMap);
		paramMap.put("sign", Signatures.sign(paramMap));
		if (logger.isInfoEnabled())
			logger.info("==>>OpenApi入参数=" + paramMap);
		HttpEntity entity = sender.producePostEntity(paramMap, "utf-8");
		return getResponseString(entity);
	}

	private Map<String, Object> doRequestAndSetResult(HttpEntity entity) {
		return MiscUtil.parseJSON(getResponseString(entity));
	}

	private String getResponseString(HttpEntity entity) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			HttpPost post = sender.buildHttpPost(com.yiji.openapi.sdk.Constants.OPENAPI_GATEWAY,
				entity);
			HttpResponse response = sender.post(post);
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),
				"UTF-8"));
			String str = br.readLine();
			while (str != null) {
				sb.append(str);
				str = br.readLine();
			}
		} catch (Exception e) {
			logger.error("send openApi error", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("br close exception ", e);
				}
			}
		}
		return sb.toString();
	}

	public String makeResposeUrl(Map<String, String> paramMap,
			OpenApiContext openApiContext) {
		Map<String, String> standardParamsMap = buildStandardParams(openApiContext);
		paramMap.putAll(standardParamsMap);
		clearNullVallue(paramMap);
		Map<String, String> postMap = digestParam(openApiContext, paramMap);
		if (logger.isInfoEnabled())
			logger.info("Open Api 入参数=" + postMap);

		BufferedReader br = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(openApiContext.getOpenApiUrl()).append("?");
			StringBuffer sb1 = new StringBuffer();
			Iterator<Map.Entry<String, String>> iterator = postMap.entrySet()
					.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				if (i == 0)
					sb1.append(entry.getKey()).append("=")
							.append(entry.getValue());
				else
					sb1.append("&").append(entry.getKey()).append("=")
							.append(entry.getValue());
				i++;
			}
			// sb.append(URLEncoder.encode(sb1.toString(),
			// openApiContext.getInputCharset()));
			sb.append(sb1.toString());
			return sb.toString();
		} catch (Exception e) {
			logger.error("send openApi error", e);
		}
		return "";

	}

	/**
	 * @param paramMap
	 */
	private void clearNullVallue(Map<String, String> paramMap) {
		TreeMap<String, String> treeMap = new TreeMap<String, String>(paramMap);
		for (Entry<String, String> entry : treeMap.entrySet()) {
			if (entry.getValue() == null) {
				paramMap.remove(entry.getKey());
			}
		}
	}

	private Map<String, String> buildStandardParams(
			OpenApiContext openApiContext) {
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put(Constants.ORDER_NO_KEY, openApiContext.getOpenApiBizNo());
		paramMap.put(Constants.SERVICE_TYPE_KEY, openApiContext.getService());
		paramMap.put(Constants.PARTNER_ID_KEY, openApiContext.getPartnerId());
		paramMap.put(Constants.CHARSET_KEY, openApiContext.getInputCharset());
		paramMap.put(Constants.SIGN_TYPE, openApiContext.getSignType()
				.getName());
		if (StringUtil.isNotBlank(openApiContext.getNotifyUrl()))
			paramMap.put(Constants.NOTIFY_URL_KEY,
					openApiContext.getNotifyUrl());
		return paramMap;
	}

	/**
	 * @param result
	 * @param dataMap
	 */
	protected void setReturnResult(P2PBaseResult result,
			Map<String, Object> dataMap) {
		result.setSuccess(isSuccess(dataMap));

		if (!result.isSuccess()) {
			result.setResultEnum(ResultEnum.OPENAPI_ACCESS_FAILURE);
		}
		result.setMessage(getResultMessage(dataMap));
	}

	private Map<String, String> digestParam(OpenApiContext openApiContext,
			Map<String, String> paramMap) {
		String sign = DigestUtil.digest(paramMap,
				openApiContext.getSecurityCheckKey(),
				openApiContext.getSignType());
		paramMap.put(DigestUtil.SIGN_KEY, sign);
		return paramMap;
	}

	protected boolean isSuccess(Map<String, Object> dataMap) {
		if (dataMap == null)
			return false;
		String resultCode = (String) dataMap.get(RESULT_CODE);
		if (resultCode != null
				&& (resultCode.indexOf(SUCCESS) > -1 || resultCode
						.equals(SUCCESS_VALUE))) {
			logger.info("call open api success");
			return true;
		}
		return false;
	}

	protected boolean isSuccessStringSuccess(Map<String, Object> dataMap) {
		if (dataMap == null)
			return false;
		String success = String.valueOf(dataMap.get("success"));
		String resultCode = String.valueOf(dataMap.get("resultCode"));
		if (com.icebreak.util.lang.util.StringUtil.equals(success, "T")
			&& com.icebreak.util.lang.util.StringUtil.equals(resultCode, "EXECUTE_SUCCESS")) {
			logger.info("call open api success");
			return true;
		}
		return false;
	}

	protected String getResultMessage(Map<String, Object> dataMap) {
		if (dataMap == null)
			return null;
		return (String) dataMap.get(RESULT_MESSAGE);

	}

	protected String getResultValue(Map<String, Object> dataMap, String key) {
		if (dataMap == null)
			return null;
		return (String) dataMap.get(key);

	}

	@SuppressWarnings("unchecked")
	protected String getResultValue(Map<String, Object> dataMap, String mapKey,
			String key) {
		if (dataMap == null)
			return null;
		Map<String, Object> tempMap = (Map<String, Object>) dataMap.get(mapKey);
		if (tempMap == null)
			return null;
		return (String) tempMap.get(key);

	}

	protected void setBaseResult(TradeResult result, Map<String, Object> dataMap) {
		result.setChannelId(get("channelId", dataMap));
		result.setOrderNo(get("orderNo", dataMap));
		result.setResultCode(get("resultCode", dataMap));
		result.setResultMessage(get("resultMessage", dataMap));
	}

	protected String get(String name, Map<String, Object> dataMap) {
		return (String) dataMap.get(name);
	}
	
	public String makeRequestUrl(Map<String, String> paramMap) {
		return apiTool.makeRequestUrl(paramMap);
	}
}
