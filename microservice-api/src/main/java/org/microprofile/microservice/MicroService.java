package org.microprofile.microservice;

import java.io.Serializable;

import org.microprofile.microservice.context.RequestContext;

public interface MicroService<REQUEST extends Serializable, RESPONSE extends Serializable> {
	
	public RESPONSE service(RequestContext<REQUEST> context);
	public Class<REQUEST> getRequestType();
	public Class<RESPONSE> getResponseType();
	
}
