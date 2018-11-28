package org.microprofile.microservice;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/service")
public class AbstractMicroService<T extends AbstractMicroService<?>> {
	
	Class<T> microServiceClass;
	
	AbstractMicroService(Class<T> microServiceClass) {
		this.microServiceClass=microServiceClass;
	}
	
	@GET
	public void get() {
		System.out.println("get called");
	}
	
	@POST
	public void post() {
		System.out.println("post called");
	}
}
