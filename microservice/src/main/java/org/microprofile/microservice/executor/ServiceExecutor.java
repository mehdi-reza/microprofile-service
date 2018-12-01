package org.microprofile.microservice.executor;

import javax.inject.Inject;

import org.microprofile.microservice.MicroService;
import org.microprofile.microservice.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceExecutor {

	@Inject
	private MicroService service;

	private Logger logger=LoggerFactory.getLogger(ServiceExecutor.class);
	
	public void execute(Object payload) {

		RequestContext context=new RequestContext();		
		service.service(context);
		logger.info("Next service to be called {}", context.getNext());
	}
}
