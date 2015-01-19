package com.icebreak.util.util;

import java.io.IOException;

public class ServiceConfigurationError extends Error {

	/** 版本号 */
	private static final long serialVersionUID = 1560080319357392104L;

	public ServiceConfigurationError(String msg) {
		super(msg);
	}

	public ServiceConfigurationError(String msg, Throwable cause) {
		super(msg, cause);
	}
}
