package org.microprofile.microservice.executor;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.microprofile.microservice.context.RequestContext;

@Path("/service")
public interface RestClientApi {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RequestContext post(RequestContext context);
}
