package org.microprofile.microservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.microprofile.microservice.annotations.ServiceDescriptor;
import org.microprofile.microservice.events.OnStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/service")
@ApplicationScoped
public class ServiceEndpoint {
	
	Logger logger=LoggerFactory.getLogger(ServiceEndpoint.class);
			
	private MicroService<?,?> service;
	
	@Inject
	Event<OnStart> startEvent;
	
	@Inject 
	protected void setService(MicroService<?, ?>  service) {
		this.service=service;
		
		final ServiceDescriptor descriptor = service.getClass().getAnnotation(ServiceDescriptor.class);
		Objects.requireNonNull(descriptor, "ServiceDescriptor is not defined on service..");
		
		startEvent.fire(new OnStart() {
			public String getServiceName() {
				return descriptor.name();
			}
			public URL getURL() throws MalformedURLException {
				return new URL(descriptor.url());
			}
		});
	}
	
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
}
