package com.yiji.openapi.sdk.servlet;

import com.yiji.openapi.sdk.notify.NotifyHandlerDispatcher;
import com.yiji.openapi.sdk.util.Servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @date 2014年6月16日
 */
public class OpenApiClientSpringServlet extends AbstractSpringServlet {
	/** UID */
	private static final long serialVersionUID = 6169228472722574634L;

	private NotifyHandlerDispatcher notifyHandlerDispatcher;

	@Override
	protected void doInit() {
		super.doInit();
		notifyHandlerDispatcher = getBean("notifyHandlerDispatcher", NotifyHandlerDispatcher.class);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		try {
			Map<String, Object> received = Servlets.getParametersStartingWith(request, null);
			Map<String, String> notifyData = new HashMap<String, String>(received.size());
			for (Map.Entry<String, Object> entry : received.entrySet()) {
				notifyData.put(entry.getKey(), (String) entry.getValue());
			}
			String notifyUrl = Servlets.getRequestPath(request);
			notifyHandlerDispatcher.dispatch(notifyUrl, notifyData);
		} catch (Exception e) {
			Servlets.writeResponse(response, "failure");
		}
		Servlets.writeResponse(response, "success");
	}

}
