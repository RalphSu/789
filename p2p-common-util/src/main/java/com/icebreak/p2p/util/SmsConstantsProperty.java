package com.icebreak.p2p.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.icebreak.util.lang.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SmsConstantsProperty extends  PropertyPlaceholderConfigurer {
    protected  static final   Logger logger	= LoggerFactory.getLogger(SmsConstantsProperty.class);
	private static Map<String, Object> ctxPropertiesMap;  
	
	 @Override  
	    protected void processProperties(  
	            ConfigurableListableBeanFactory beanFactoryToProcess,  
	            Properties props) throws BeansException {  
	        super.processProperties(beanFactoryToProcess, props);  
	        ctxPropertiesMap = new HashMap<String, Object>();  
	        for (Object key : props.keySet()) {  
	            String keyStr = key.toString();  
	            String value = props.getProperty(keyStr);  
	            ctxPropertiesMap.put(keyStr, value);  
	        }  
	    }  
	  
	    public static String getContent(String key) {
            try{
                if(ctxPropertiesMap.get(key) == null){
                    return "";
                }
                String  content =  (String)ctxPropertiesMap.get(key);
                if(StringUtil.isEmpty(content)){
                    return "";
                }
                String productName = AppConstantsUtil.getProductName();
                return content.replaceAll("#ProductName#", productName) ;
            }catch (Exception e){
                logger.error("短信配置文件读取未知异常:e={}", e.getMessage(), e);
            }
            return "";
	    }  

}
