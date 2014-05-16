package com.gioorgi.microservice.audit;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.context.annotation.ComponentScan;

//@Component(value="auditcontrollertest")
@ComponentScan
public class AuditServiceController {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired(required=true)
	AuditEventRepository auditRepository;
	
	
	@PostConstruct
	public void scanAuditRepository() {
		logger.info("Unknown events so far:"+auditRepository.find("<unknown>", new Date(0)));
	}
}
