package org.microprofile.microservice.executor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

public class ServiceExecutor {

	@Inject
	private Logger logger;
	
	@Inject JsonReaderFactory readerFactory;
	@Inject Jsonb jsonb;
	
	@Context HttpServletRequest request;

	private MicroService<?, ?> service;

	public void setService(MicroService<?,?> service) {
		this.service=service;
	}

	public RequestContext<?> execute(JsonObject payload) {
		
		String serviceName = service.getClass().getAnnotation(ServiceDescriptor.class).name();
		
		RequestContext<?> context=null;

		// can be null if a get request
		if(Objects.nonNull(payload) && Objects.nonNull(payload.get("orchestrator"))) { 
			context = jsonb.fromJson(payload.toString(), RequestContext.class);
		}

		// make this service as orchestrator
		if(Objects.isNull(context)) {
			context=new RequestContext<>(serviceName);
			context.setPayload(jsonb.fromJson(payload.toString(), service.getRequestType()));
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
