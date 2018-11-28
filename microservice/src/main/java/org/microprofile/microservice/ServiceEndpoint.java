package org.microprofile.microservice;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/service")
@ApplicationScoped
public class ServiceEndpoint {
	
	Logger logger=LoggerFactory.getLogger(ServiceEndpoint.class);
			
	@Inject MicroService<?> service;
	
	@GET
	public void get() {
		service.service(new RequestContext());
	}

	@POST
	public void post() {
		System.out.println("post called");
	}
	
	@PUT
	public void put() {
		
	}
	
	@PostConstruct
	public void initialize() {
		logger.info("Registering service in Zookeeper");
	}
}
