
package com.icebreak.util.util;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.app.event.InvalidReferenceEventHandler;
import org.apache.velocity.context.Context;
import org.apache.velocity.util.introspection.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Map;

public abstract class Velocitys {
    private static Logger logger = LoggerFactory.getLogger(Velocitys.class);
    private static VelocityEngine velocity;

    private static InvalidReferenceEventHandler invalidReferenceEventHandler = new InvalidReferenceEventHandler() {


        @Override
        public Object invalidGetMethod(Context context, String reference, Object object, String property, Info info) {
            logger.warn("vm模版[{}]参数[{}]为空", info.getTemplateName(), reference);
            return "";
        }

        @Override
        public boolean invalidSetMethod(Context context, String leftreference, String rightreference, Info info) {
            return false;
        }

        @Override
        public Object invalidMethod(Context context, String reference, Object object, String method, Info info) {
            return null;
        }
    };
    private static EventCartridge eventCartridge;

    static {
        try {
            velocity = new VelocityEngine();
            //模板内引用解析失败时不抛出异常
            velocity.setProperty(
                    "runtime.references.strict", "false");
            velocity.init();
            eventCartridge = new EventCartridge();
            eventCartridge.addEventHandler(invalidReferenceEventHandler);
        } catch (Exception e) {
            logger.error("Velocitys初始化失败", e);
        }
    }


    public static String evaluate(String templateContent, Map<String, String> param) {
        return evaluate(templateContent, param, false);
    }


    public static String evaluate(String templateContent, Map<String, String> param, boolean ignoreInvalidRef) {

        return evaluate("Velocitys", templateContent, param, ignoreInvalidRef);
    }


    public static String evaluate(String templateName, String templateContent, Map<String, String> param, boolean ignoreInvalidRef) {
        VelocityContext context = new VelocityContext(param);
        if (ignoreInvalidRef) {
            eventCartridge.attachToContext(context);
        }
        StringWriter w = new StringWriter();
        velocity.evaluate(context, w, templateName, templateContent);
        return w.toString();
    }
}
