package org.microprofile.microservice.executor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonReaderFactory;
import javax.json.bind.Jsonb;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.microprofile.microservice.MicroService;
import org.microprofile.microservice.annotations.ServiceDescriptor;
import org.microprofile.microservice.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceExecutor {

	@Inject
	private MicroService service;

	private Logger logger=LoggerFactory.getLogger(ServiceExecutor.class);
	
	@Inject JsonReaderFactory readerFactory;
	@Inject Jsonb jsonb;
	
	@Context HttpServletRequest request;
	
	public RequestContext execute(JsonObject payload) {
		
		String serviceName = service.getClass().getAnnotation(ServiceDescriptor.class).name();
		RequestContext context=null;

		// can be null if a get request
		if(Objects.nonNull(payload) && Objects.nonNull(payload.get("orchestrator"))) { 
			context = jsonb.fromJson(payload.toString(), RequestContext.class);
		}

		// make this service as orchestrator
		if(Objects.isNull(context)) {
			context=new RequestContext(serviceName);
			context.setPayload(payload);
		}

		Object response = service.service(context);
		
		// record service response in list
		context.getData().add(new RequestContext.ServiceResponse(serviceName, response));
		
		if(Objects.nonNull(context.getNext())) {
			// call next service		
		}
		
		
		logger.info("Next service to call - {}", context.getNext());
		
		return context;
	}
}
