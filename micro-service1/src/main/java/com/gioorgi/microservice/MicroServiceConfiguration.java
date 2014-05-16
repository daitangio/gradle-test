package com.gioorgi.microservice;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "mc", ignoreUnknownFields = true)
public class MicroServiceConfiguration {


	
	private boolean autodiscovery=false;
	private boolean refreshSystemOnReload=false;
	public boolean isAutodiscovery() {
		return autodiscovery;
	}
	public void setAutodiscovery(boolean autodiscovery) {
		this.autodiscovery = autodiscovery;
	}
	public boolean isRefreshSystemOnReload() {
		return refreshSystemOnReload;
	}
	public void setRefreshSystemOnReload(boolean refreshSystemOnReload) {
		this.refreshSystemOnReload = refreshSystemOnReload;
	}
	
}
