package com.gioorgi.jdk8.lambda;

public interface NeverDatePartner {

	String myName();
	
	default String dateMe() {
		return ""+myName()+" will never date with you";
	}
}
