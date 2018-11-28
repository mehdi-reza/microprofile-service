package org.microprofile.microservice;

public interface MicroService<T> {
	
	public T service(RequestContext context);
	public Class<T> getResponseType();
	
}
