package org.microprofile.microservice;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
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

import org.microprofile.microservice.context.RequestContext;
import org.microprofile.microservice.executor.ServiceExecutor;
import org.slf4j.Logger;

@Path("/service")
@ApplicationScoped
public class ServiceEndpoint {
	
	@Inject
	private Logger logger;

	@Inject
	Instance<ServiceExecutor> instance;
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestContext get() {
		logger.trace("TRACE: /service/get ..");
		return instance.get().execute(null);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestContext post(JsonObject payload) {
		logger.trace("TRACE: /service/post .. {}", payload);
		return instance.get().execute(payload);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestContext put(JsonObject payload) {
		logger.trace("TRACE: /service/put .. {}", payload);
		return instance.get().execute(payload);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestContext delete(JsonObject payload) {
		logger.trace("TRACE: /service/delete .. {}", payload);
		return instance.get().execute(payload);
	}
}
