package com.oms.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oms.aes.WXBizMsgCrypt;
import com.oms.bean.EventType;
import com.oms.bean.MsgType;
import com.oms.comm.Constants;
import com.oms.service.IWeChatService;
import com.oms.util.DateUtil;
import com.oms.util.SignUtil;
/**
 * 注解方式打开链接
 * 
 * @author 
 *
 */
@Controller
public class CoreController {
	private String token = Constants.TOKEN;
	private String encodingAESKey =Constants.encodingAESKey;
	private String corpId = Constants.CORPID;
	private static final Logger log = LogManager.getLogger(CoreController.class);
	@Autowired
	private IWeChatService weChatService;
	
	
	public IWeChatService getWeChatService() {
		return weChatService;
	}


	public void setWeChatService(IWeChatService weChatService) {
		this.weChatService = weChatService;
	}


	/**
	 * 根据微信很url加入微信，验证
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = { "/coreJoin.do" }, method = RequestMethod.GET)
	public void coreJoinGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 微信加密签名
		String msg_signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		if (SignUtil.checkSignature(token,msg_signature, timestamp, nonce)) {
			write2Reponse(echostr, response);
		}  
	}
	
	
	private void write2Reponse(String str, HttpServletResponse response){
		if(str == null){
			return;
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null){
				out.close();
			}
		}
		
	}
	
	/**
	 * 处理微信服务器发来的各类消息
	 * 返回给微信服务器的文本消息长度限制为不能大于等于2048字节（UTF-8编码），
	 * @param request
	 * @param response  
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = { "/coreJoin.do" }, method = RequestMethod.POST)
	public void coreJoinPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String responseText = null;
		try {
			BufferedReader br = request.getReader();
			StringWriter sw = new StringWriter();
			String temp = null;
			while((temp = br.readLine()) != null){
				sw.append(temp);
			}
			String requestText = sw.toString();
			br.close();
			//log.debug("requestText:"+requestText);
			Document doc = DocumentHelper.parseText(requestText);//获得各种节点
			
			Node encryptNode = doc.selectSingleNode("/xml/Encrypt");//获得其中的Encrypt节点
			String nonce = "xxxxxx";
			WXBizMsgCrypt wc = null;
			if(encryptNode != null){
				wc = new WXBizMsgCrypt(token,encodingAESKey,corpId);//解密的类
				requestText=wc.decrypt(encryptNode.getText());//解密所调的方法
				doc=DocumentHelper.parseText(requestText);
			}
			Node node = doc.selectSingleNode("/xml/MsgType");
			String msgType = node.getText();
			log.info("msgType: "+msgType);
	        //消息类型：文本
			if(MsgType.TEXT.equalsIgnoreCase(msgType)){
				log.info("收到客户端发来的消息: " + requestText);
				responseText = this.weChatService.doTextResponse(requestText);
			}
			//消息类型：图片
			else if (MsgType.IMAGE.equalsIgnoreCase(msgType)){
				responseText = this.weChatService.doImageResponse(requestText);
			}
			//消息类型：音频
			else if(MsgType.VOICE.equalsIgnoreCase(msgType)){
				responseText = this.weChatService.doVoiceResponse(requestText);
			}
			//消息类型：视频
			else if(MsgType.VIDEO.equalsIgnoreCase(msgType)){
				responseText = this.weChatService.doVideoResponse(requestText);
			}
			//消息类型：地理位置
			else if(MsgType.LOCATION.equalsIgnoreCase(msgType)){
				responseText = this.weChatService.doLocationResponse(requestText);
			}
			//消息类型：链接
			else if(MsgType.LINK.equalsIgnoreCase(msgType)){
				responseText = this.weChatService.doLinkResponse(requestText);
			}
			//消息类型：菜单点击事件
			else if(MsgType.EVENT.equalsIgnoreCase(msgType)){
				String eventType = doc.selectSingleNode("/xml/Event").getText();
				log.info("eventType:"+eventType);
				//菜单事件类型：点击型菜单
				if(EventType.CLICK.equalsIgnoreCase(eventType)){
					responseText = this.weChatService.doClickEvent(requestText);
				}else if(EventType.SUBSCRIBE.equals(eventType)){
					responseText = this.weChatService.doSubscribeEvent(requestText);
				}
			}
		//	log.info("responseText: " + responseText);
			//加密
			if(wc != null){
				responseText=wc.encryptMsg(responseText, String.valueOf(DateUtil.now2Int()), nonce);
			}
			//将处理后的消息返回给微信服务器
			write2Reponse(responseText, response);
		} catch (Exception e) {
			log.error("responseText: " + responseText, e);
		} 

	}

}
