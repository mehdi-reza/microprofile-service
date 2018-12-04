package org.microprofile.microservice;

import org.microprofile.microservice.context.RequestContext;
import org.microprofile.microservice.data.RequestData;
import org.microprofile.microservice.data.ResponseData;

public interface MicroService<REQUEST extends RequestData, RESPONSE extends ResponseData> {
	
	public RESPONSE service(RequestContext context);
	public Class<REQUEST> getRequestType();
	public Class<RESPONSE> getResponseType();
	
	public default Object requestDataInstance() throws InstantiationException, IllegalAccessException {
		return getRequestType().newInstance();
	}
	
	public default Object responseDataInstance() throws InstantiationException, IllegalAccessException {
		return getResponseType().newInstance();
	}
}
