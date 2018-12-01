package org.microprofile.microservice;

import org.microprofile.microservice.context.RequestContext;

public interface MicroService {
	
	public Object service(RequestContext context);

}
