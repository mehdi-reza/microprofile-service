package org.microprofile.microservice;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/service")
public class ServiceEndpoint {
	
	@Inject MicroService service;
	
	@GET
	public void get() {
		service.service();
	}

	@POST
	public void post() {
		System.out.println("post called");
	}
}
