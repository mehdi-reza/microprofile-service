package org.microprofile.microservice;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessManagedBean;
import javax.enterprise.inject.spi.ProcessSyntheticBean;
import javax.enterprise.inject.spi.WithAnnotations;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.microprofile.microservice.annotations.ServiceDescriptor;
import org.microprofile.microservice.context.RequestContext;
import org.microprofile.microservice.events.OnStart;
import org.microprofile.microservice.executor.ServiceExecutor;
import org.slf4j.Logger;

@Path("/service")
@ApplicationScoped
public class ServiceEndpoint {
	
	@Inject
	private Logger logger;
			
	/*@Inject
	Event<OnStart> startEvent;*/
	
	/*@Inject 
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
	}*/
	
	/*public void startup(@Observes @ServiceDescriptor ProcessBean<MicroService<?  extends Serializable,?  extends Serializable>> bean) {
		logger.info("ProcessManagedBean {}", bean);
		Thread.dumpStack();
	}*/
	

	@Inject
	Instance<ServiceExecutor> instance;
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestContext<?> get() {
		return instance.get().execute(null);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestContext<?> post(JsonObject payload) {
		return instance.get().execute(payload);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestContext<?> put(JsonObject payload) {
		return instance.get().execute(payload);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestContext<?> delete(JsonObject payload) {
		return instance.get().execute(payload);
	}
}
