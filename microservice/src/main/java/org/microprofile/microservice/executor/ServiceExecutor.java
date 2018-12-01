package org.microprofile.microservice.executor;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import javax.inject.Inject;
import javax.json.JsonReaderFactory;
import javax.json.bind.Jsonb;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.microprofile.microservice.MicroService;
import org.microprofile.microservice.annotations.ServiceDescriptor;
import org.microprofile.microservice.context.RequestContext;
import org.microprofile.microservice.context.ServicePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceExecutor {

	@Inject
	private MicroService service;

	private Logger logger=LoggerFactory.getLogger(ServiceExecutor.class);
	
	@Inject JsonReaderFactory readerFactory;
	@Inject Jsonb jsonb;
	
	@Context HttpServletRequest request;
	
	public RequestContext execute(RequestContext context) {
		
		String serviceName = service.getClass().getAnnotation(ServiceDescriptor.class).name();

		// record this service call
		context.getServiceCalls().add(serviceName);
		
		// make this service as orchestrator
		if(Objects.isNull(context.getOrchestrator())) {
			context.setOrchestrator(serviceName);
		}

		Object response = service.service(context);
		
		context.getData().add(new ServicePayload(serviceName, response));
		
		if(Objects.nonNull(context.getNext())) {
			// call next service		
		}
		
		trace(context);
		
		logger.info("Next service to be called {}", context.getNext());
		
		return context;
	}
	
	private void trace(Object context) {
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		try {
			jsonb.toJson(context, outputStream);
			logger.info(outputStream.toString());
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}