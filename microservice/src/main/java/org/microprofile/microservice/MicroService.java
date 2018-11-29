package org.microprofile.microservice;

public interface MicroService<REQUEST, RESPONSE> {
	
	public RESPONSE service(RequestContext context);
	public Class<REQUEST> getRequestType();
	public Class<RESPONSE> getResponseType();
	
}
