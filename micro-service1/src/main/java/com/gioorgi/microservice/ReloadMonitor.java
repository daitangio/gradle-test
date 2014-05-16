package com.gioorgi.microservice;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springsource.loaded.ReloadEventProcessorPlugin;

@Component
@ComponentScan
@EnableConfigurationProperties(MicroServiceConfiguration.class)
public class ReloadMonitor implements ReloadEventProcessorPlugin{
	
	
	@Autowired(required=true)
	MicroServiceConfiguration config;
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public boolean shouldRerunStaticInitializer(String typename,
			Class<?> clazz, String encodedTimestamp) {
		return false;
	}

	@PostConstruct
	public void printYourStatusBaby() {
		
		// org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext
		logger.info("*** ApplicationContext="+applicationContext);
		
	
	}
	
	@Override
	public void reloadEvent(String typename, Class<?> clazz,
			String encodedTimestamp) {
		System.err.println("\n%%%%%%%%%%%%%%%%%%%%\n\tSpringLoad Reloaded Taking Action \n%%%%%%%%%%%%%%%%%%%%\n");
		// Two ways: Force a context reinit in some respect: 
		// 
		// OR
		if(config.isRefreshSystemOnReload()) {
		try {
			logger.info("Asking system to do a fast reinit");
			applicationContext.getBean(HelloWorld.class).sayHelloAndBeNice();
			logger.info("Warm reboot successful");
		}catch(BeansException cce) {
			logger.warn("Unable to reinit application beans. Consider Cold Reboot",cce);
		}
		}else {
			logger.warn("Micro Service refresh not enabled. Only Servlet will be updated");
		}
		
	}

	@Autowired(required=true)
	private ApplicationContext applicationContext ;
	

}
