package org.microprofile.microservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.microprofile.microservice.annotations.ServiceDescriptor;
import org.microprofile.microservice.context.RequestContext;
import org.microprofile.microservice.events.OnStart;
import org.microprofile.microservice.executor.ServiceExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/service")
@ApplicationScoped
public class ServiceEndpoint {
	
	Logger logger=LoggerFactory.getLogger(ServiceEndpoint.class);
			
	@Inject
	Event<OnStart> startEvent;
	
	@Inject 
	protected void setService(MicroService service) {
		
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
	

	@Inject
	Instance<ServiceExecutor> instance;
	
	@GET
	public void get(Object payload) {
		instance.get().execute(payload);
	}

	@POST
	public void post(Object payload) {
		instance.get().execute(payload);
	}
	
	@PUT
	public void put(Object payload) {
		instance.get().execute(payload);
	}
	
	@DELETE
	public void delete(Object payload) {
		instance.get().execute(payload);
	}
}
