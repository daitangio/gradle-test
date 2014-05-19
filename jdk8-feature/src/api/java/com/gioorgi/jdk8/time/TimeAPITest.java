package com.gioorgi.jdk8.time;
import java.time.Clock;


public class TimeAPITest {

	public static void main(String[] args) {
		Clock clock = Clock.systemDefaultZone();
		System.err.println("clock="+clock);
	}

}
