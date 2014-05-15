package com.gioorgi.microservice;


import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springsource.loaded.agent.SpringLoadedPreProcessor;

/**
 *  Happily run with
 * java -jar build/libs/micro-service1.jar
 * Look at
 *  http://localhost:8080/configprops
 * for configured properties 
 * # --debug  --spring.config.location=src/main/java/config/
 * @author jj
 *
 */
@Controller
@EnableAutoConfiguration()
@ComponentScan
public class HelloWorld {

	private Log logger = LogFactory.getLog(this.getClass());
	
	@Value("${app.version:UNKNOWN}")
	String version;
	
	@RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

	@RequestMapping("/about")
    @ResponseBody
    String about() {
        return "TEST The Gradle+SpringBoot+SpringLoaded_2014 JJ's_Demo_is here. Demo_Version:"+version;
    }

	
	@PostConstruct
	public void setupSpringReloadMonitor() {
		logger.info("\n\n\t setupSpringReloadMonitor "+about().replace(" ", "\n\t")+"\n\n");
		SpringLoadedPreProcessor.registerGlobalPlugin(new ReloadMonitor());
	}
	
    public void setVersion(String version) {
		this.version = version;
	}

	public static void main(String[] args) throws Exception {   
//		ApplicationListener<ApplicationPreparedEvent> sayHello = new ApplicationListener<ApplicationPreparedEvent>() {
//
//			@Override
//			public void onApplicationEvent(ApplicationPreparedEvent event) {
//				System.err.println("\n\n\t DEMO READY\n\n");				
//			}
//		};
		SpringApplicationBuilder ab=new SpringApplicationBuilder(HelloWorld.class);
//		ab.application().addListeners(sayHello);
		
		
		
    	ab.run(args);
    
    }
}
