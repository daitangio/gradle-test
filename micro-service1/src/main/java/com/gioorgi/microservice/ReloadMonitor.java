package com.gioorgi.microservice;

import org.springsource.loaded.ReloadEventProcessorPlugin;

public class ReloadMonitor implements ReloadEventProcessorPlugin {

	@Override
	public boolean shouldRerunStaticInitializer(String typename,
			Class<?> clazz, String encodedTimestamp) {
		return false;
	}

	@Override
	public void reloadEvent(String typename, Class<?> clazz,
			String encodedTimestamp) {
		System.err.println("\n%%%%%%%%%%%%%%%%%%%%\n\tSpringLoad Reloaded\n%%%%%%%%%%%%\n");
		
	}

}
