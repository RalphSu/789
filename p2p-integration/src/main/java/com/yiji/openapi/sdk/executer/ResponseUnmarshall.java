package com.yiji.openapi.sdk.executer;

public interface ResponseUnmarshall<R, T> {

	T unmarshall(String serviceName, R responseMessage);

}
