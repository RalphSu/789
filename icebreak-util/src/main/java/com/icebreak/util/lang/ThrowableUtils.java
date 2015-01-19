package com.icebreak.util.lang;

public abstract class ThrowableUtils {

	/**
	 * 构建嵌套可投掷信息的 message 信息。
	 * 
	 * @param message message 内容。
	 * @param cause 可嵌套的可投掷信息。
	 * @return 构建的结果。
	 */
	public static String buildMessage(String message, Throwable cause) {
		if (cause != null) {
			StringBuilder buf = new StringBuilder();
			if (message != null) {
				buf.append(message).append("; ");
			}
			buf.append("嵌套的异常是 ").append(cause);
			return buf.toString();
		} else {
			return message;
		}
	}

	/**
	 * 包装转换一个检查异常为不检查异常。
	 * <p>
	 * 如果 e 是一个不检查异常，则直接抛出，如果 e 是一个检查异常，则使用 {@link RuntimeExceptionWrapper} 包装。
	 * 
	 * @param e 需要包装的异常。
	 * @throws RuntimeExceptionWrapper 如果 e 是一个检查异常。
	 * @throws RuntimeException 如果 e 是不检查异常。
	 */
	public static void wrapExceptionToRunTimeException(Exception e) {
		if (e instanceof RuntimeException) {
			throw (RuntimeException) e;
		}
		throw new RuntimeExceptionWrapper(e);
	}

	/**
	 * 包装转换一个 {@link Throwable} 为不检查异常。
	 * <p>
	 * 如果 t 是一个 不检查异常 或者 {@link Error} ，则直接抛出，如果 t 是一个检查异常，则使用
	 * {@link RuntimeExceptionWrapper} 包装。
	 * 
	 * @param t 需要包装的 Throwable 。
	 * @throws RuntimeExceptionWrapper 如果 e 是一个检查异常。
	 * @throws RuntimeException 如果 e 是不检查异常。
	 * @throws Error 如果 e 是 Error。
	 */
	public static void wrapExceptionToRunTimeException(Throwable t) {
		if (t instanceof RuntimeException) {
			throw (RuntimeException) t;
		}
		if (t instanceof Error) {
			throw (Error) t;
		}
		throw new RuntimeExceptionWrapper(t);
	}

	/**
	 * 返回 t 的 cause；如果 cause 不存在返回 t ，否则返回 null。
	 * 
	 * @param t 一个 Throwable。
	 * @return t 的 cause；如果 cause 不存在返回 t ，否则返回 null。
	 */
	public static Throwable getCause(Throwable t) {
		if (t == null) {
			return null;
		}
		Throwable cause = t.getCause();
		if (cause != null) {
			return cause;
		}
		return t;
	}

}
