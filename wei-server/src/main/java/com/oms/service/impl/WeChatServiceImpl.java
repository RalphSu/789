package com.oms.service.impl;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Service;

import com.oms.bean.EventKey;
import com.oms.bean.Message;
import com.oms.bean.MsgType;
import com.oms.bean.event.ClickMenuEventMessage;
import com.oms.bean.event.SubscribeEventMessage;
import com.oms.bean.req.ImageRequestMessage;
import com.oms.bean.req.LinkRequestMessage;
import com.oms.bean.req.LocationRequestMessage;
import com.oms.bean.req.TextRequestMessage;
import com.oms.bean.req.VideoRequestMessage;
import com.oms.bean.req.VoiceRequestMessage;
import com.oms.bean.resp.ImageResponseMessage;
import com.oms.bean.resp.ImageResponseMessage.Image;
import com.oms.bean.resp.NewsResponseMessage;
import com.oms.bean.resp.NewsResponseMessage.Articles;
import com.oms.bean.resp.NewsResponseMessage.Articles.Item;
import com.oms.bean.resp.TextResponseMessage;
import com.oms.bean.resp.VideoResponseMessage;
import com.oms.bean.resp.VideoResponseMessage.Video;
import com.oms.bean.resp.VoiceResponseMessage;
import com.oms.bean.resp.VoiceResponseMessage.Voice;
import com.oms.comm.Constants;
import com.oms.service.IWeChatService;
import com.oms.util.DateUtil;
import com.oms.util.JAXBContextUtil;
import com.oms.util.ResponseUtil;

@Service
public class WeChatServiceImpl implements IWeChatService{

	/**
	 * 将xml字符串转换为Message类的实例
	 * @param clazz 继承于Message的子类
	 * @param xml 被转换的xml字符串
	 * @return 转换后的Message类的实例
	 * @throws JAXBException
	 */
	private static Message xmlStr2Message(Class<? extends Message> clazz, String xml) throws JAXBException{
		JAXBContext jaxbContext = JAXBContextUtil.get(clazz);
		Message msg = (Message)jaxbContext.createUnmarshaller().unmarshal(new StringReader(xml));
		return msg;
	}
	
	/**
	 * 将Message类的实例转换为xml字符串
	 * @param msg 被转换的Message类的实例
	 * @return 转换后的xml字符串
	 * @throws JAXBException
	 */
	private static String message2XmlStr(Message msg) throws JAXBException{
		Class<? extends Message> clazz = msg.getClass();
		JAXBContext jabxContext = JAXBContextUtil.get(clazz);
		StringWriter sw1 = new StringWriter();
		Marshaller marshal = jabxContext.createMarshaller();
//		marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshal.marshal(msg, sw1);
		return sw1.toString();
	}
	
	@Override
	public String doClickEvent(String requestXml) throws Exception{
		ClickMenuEventMessage clickEvent = (ClickMenuEventMessage)xmlStr2Message(ClickMenuEventMessage.class, requestXml);
		//图文处理	已成功
		if(EventKey.NEWS_INFO.equalsIgnoreCase(clickEvent.getEventKey())){
			NewsResponseMessage newsResponse = new NewsResponseMessage();
				newsResponse.setFromUserName(clickEvent.getToUserName());
				newsResponse.setToUserName(clickEvent.getFromUserName());
				newsResponse.setCreateTime(DateUtil.now2Int());
				newsResponse.setMsgType(MsgType.NEWS);
			Articles articles = new Articles();
					List<Item> itemList = articles.getItems();
						Item item = new Item();
						item.setTitle("预约挂号");
						//item.setPicUrl(WeChatInit.getProperty("baseUrl")+"/include/images/appoint.jpg");
						item.setDescription("点击立即开始预约");
						//item.setUrl(WeChatInit.getProperty("baseUrl")+"/appointment.do?verbId=queryHspList&fromUser="+clickEvent.getFromUserName());
					itemList.add(item);
			newsResponse.setArticleCount(itemList.size());
			newsResponse.setArticles(articles);
			
			return message2XmlStr(newsResponse);
		}
		//图片处理	已成功
		else if(EventKey.IMAGE_INFO.equalsIgnoreCase(clickEvent.getEventKey())){
			ImageResponseMessage imageResponse=new ImageResponseMessage();
				imageResponse.setFromUserName(clickEvent.getToUserName());
				imageResponse.setToUserName(clickEvent.getFromUserName());
				imageResponse.setCreateTime(clickEvent.getCreateTime());
				imageResponse.setMsgType(MsgType.IMAGE);
			Image image=new Image();
				image.setMediaId("nulB2UnxHUN6kCycNAKdBocDChU5X47jMrTmydtrMYf2aj9uO_ypHKRMl2GpytyA");
			imageResponse.setImage(image);
			
			return message2XmlStr(imageResponse);
		}
		//文本处理	已成功
		else if(EventKey.TEXT_INFO.equalsIgnoreCase(clickEvent.getEventKey())){
			TextResponseMessage textResponse=new TextResponseMessage();
				textResponse.setFromUserName(clickEvent.getToUserName());
				textResponse.setToUserName(clickEvent.getFromUserName());
				textResponse.setCreateTime(clickEvent.getCreateTime());
				textResponse.setMsgType(MsgType.TEXT);
				textResponse.setContent("你发的是文本点击事件");
			
			return message2XmlStr(textResponse);
		}
		//声音消息处理
		else if(EventKey.VIDEO_INFO.equalsIgnoreCase(clickEvent.getEventKey())){
			VoiceResponseMessage voiceResponse=new VoiceResponseMessage();
				voiceResponse.setFromUserName(clickEvent.getToUserName());
				voiceResponse.setToUserName(clickEvent.getFromUserName());
				voiceResponse.setCreateTime(clickEvent.getCreateTime());
				voiceResponse.setMsgType(MsgType.VOICE);
			Voice voice=new Voice();
				voice.setMediaId("");
				
				voiceResponse.setVoice(voice);
			return message2XmlStr(voiceResponse);
		}
		//视频消息处理
		else if(EventKey.VIDEO_INFO.equalsIgnoreCase(clickEvent.getEventKey())){
			VideoResponseMessage videoResponse=new VideoResponseMessage();
				videoResponse.setFromUserName(clickEvent.getToUserName());
				videoResponse.setToUserName(clickEvent.getFromUserName());
				videoResponse.setCreateTime(clickEvent.getCreateTime());
				videoResponse.setMsgType(MsgType.VIDEO);
			Video video=new Video();
				video.setDescription("");
				video.setMediaId("");
				video.setTitle("");
				
			return message2XmlStr(videoResponse);
		}
		

		else{
			TextResponseMessage textResponse = ResponseUtil.createTextResponse(clickEvent.getFromUserName()
					, clickEvent.getToUserName(), "功能开发中，敬请关注！");
			return message2XmlStr(textResponse);
		}
	}
	
	public String doTextResponse(String requestText) throws Exception{
		TextRequestMessage textRequest = 
				(TextRequestMessage)xmlStr2Message(TextRequestMessage.class, requestText);
		TextResponseMessage textResponse = ResponseUtil.createTextResponse(textRequest.getFromUserName()
											, textRequest.getToUserName(), "<u>iiii</u>");
		return message2XmlStr(textResponse);
	}
	
	@Override
	public String doImageResponse(String requestText) throws Exception {
		ImageRequestMessage imageRequest = (ImageRequestMessage)xmlStr2Message(ImageRequestMessage.class, requestText);
		TextResponseMessage textResponse = ResponseUtil.createTextResponse(imageRequest.getFromUserName()
											, imageRequest.getToUserName(), "您发送了一条图片消息！");
		return message2XmlStr(textResponse);
	}

	@Override
	public String doVoiceResponse(String requestText) throws Exception {
		VoiceRequestMessage voiceRequest = (VoiceRequestMessage)xmlStr2Message(VoiceRequestMessage.class, requestText);
		TextResponseMessage textResponse = ResponseUtil.createTextResponse(voiceRequest.getFromUserName()
											, voiceRequest.getToUserName(), "您发送了一条声音消息！");
		return message2XmlStr(textResponse);
	}

	@Override
	public String doVideoResponse(String requestText) throws Exception {
		VideoRequestMessage videoRequest = (VideoRequestMessage)xmlStr2Message(VideoRequestMessage.class, requestText);
		TextResponseMessage textResponse = ResponseUtil.createTextResponse(videoRequest.getFromUserName()
											, videoRequest.getToUserName(), "您发送了一条视频消息！");
		return message2XmlStr(textResponse);
	}

	@Override
	public String doLocationResponse(String requestText) throws Exception {
		LocationRequestMessage locationRequest = (LocationRequestMessage)xmlStr2Message(LocationRequestMessage.class, requestText);
		TextResponseMessage textResponse = ResponseUtil.createTextResponse(locationRequest.getFromUserName()
											, locationRequest.getToUserName(), "您发送了地理位置！");
		return message2XmlStr(textResponse);
	}

	@Override
	public String doLinkResponse(String requestText) throws Exception {
		LinkRequestMessage linkRequest = (LinkRequestMessage)xmlStr2Message(LinkRequestMessage.class, requestText);
		TextResponseMessage textResponse = ResponseUtil.createTextResponse(linkRequest.getFromUserName()
											, linkRequest.getToUserName(), "您发送了一条链接消息！");
		return message2XmlStr(textResponse);
	}

	@Override
	public String doSubscribeEvent(String requestText) throws Exception {
		SubscribeEventMessage subscribeEventMessage = (SubscribeEventMessage)xmlStr2Message(SubscribeEventMessage.class, requestText);
		TextResponseMessage textResponse = ResponseUtil.createTextResponse(subscribeEventMessage.getFromUserName()
				, subscribeEventMessage.getToUserName(), "欢迎使用"+Constants.SYSTEMNAME+"，请点击下方菜单进行使用！");
		return message2XmlStr(textResponse);
	}
	
}
