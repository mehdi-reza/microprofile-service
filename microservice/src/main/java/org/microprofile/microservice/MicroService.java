package org.microprofile.microservice;

import org.microprofile.microservice.context.RequestContext;

public interface MicroService<REQUEST, RESPONSE> {
	
	public RESPONSE service(RequestContext context);
	public Class<REQUEST> getRequestType();
	public Class<RESPONSE> getResponseType();
	
}
