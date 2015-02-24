package com.oms.service;

//对用户的操作进行处理	包括消息、事件等
public interface IWeChatService{

	/**
	 * 对菜单点击事件进行处理
	 * @param requestXml 微信服务器发来的xml字符串
	 * @return 返回给微信服务器的xml字符串
	 * @throws Exception
	 */
	public String doClickEvent(String requestXml) throws Exception;

	/**
	 * 对文本消息进行处理
	 * @param requestXml 微信服务器发来的xml字符串
	 * @return 返回给微信服务器的xml字符串
	 * @throws Exception
	 */
	public String doTextResponse(String requestXml) throws Exception;

	/**
	 * 对图片消息进行处理
	 * @param requestXml 微信服务器发来的xml字符串
	 * @return 返回给微信服务器的xml字符串
	 * @throws Exception
	 */
	public String doImageResponse(String requestText) throws Exception;

	/**
	 * 对语音消息进行处理
	 * @param requestXml 微信服务器发来的xml字符串
	 * @return 返回给微信服务器的xml字符串
	 * @throws Exception
	 */
	public String doVoiceResponse(String requestText) throws Exception;

	/**
	 * 对视频消息进行处理
	 * @param requestXml 微信服务器发来的xml字符串
	 * @return 返回给微信服务器的xml字符串
	 * @throws Exception
	 */
	public String doVideoResponse(String requestText) throws Exception;

	/**
	 * 对地理位置进行处理
	 * @param requestXml 微信服务器发来的xml字符串
	 * @return 返回给微信服务器的xml字符串
	 * @throws Exception
	 */
	public String doLocationResponse(String requestText) throws Exception;

	/**
	 * 对链接进行处理
	  * @param requestXml 微信服务器发来的xml字符串
	 * @return 返回给微信服务器的xml字符串
	 * @throws Exception
	 */
	public String doLinkResponse(String requestText) throws Exception;

	/**
	 * 对关注事件进行处理
	 * @param requestText 微信服务器发来的xml字符串
	 * @return 返回给微信服务器的xml字符串
	 */
	public String doSubscribeEvent(String requestText) throws Exception;

}
