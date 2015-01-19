package com.icebreak.p2p.ext.domain;

import java.io.Serializable;

public interface Result extends Serializable {
	
	public boolean isSuccess();
	
	public boolean isExecuted();
	
	public String getMessage();
	
}
