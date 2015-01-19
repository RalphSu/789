package com.icebreak.util.lang.result;

import java.io.Serializable;

public interface Result extends Serializable {
	
	public boolean isSuccess();
	
	public boolean isExecuted();
	
	public String getMessage();
	
}
