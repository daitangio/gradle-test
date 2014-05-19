package com.gioorgi.jdk8.lambda;

public interface EasyFace {

	String myName();
	
	default String dateMe() {
		return "Ok "+myName()+" will come";
	}
}
