package org.microprofile.microservice.context;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbTransient;

public final class RequestContext {

	/**
	 * Provide name of next service to be called
	 * 
	 * @param service
	 */

	private String orchestrator;
	private List<String> serviceCalls = new ArrayList<>();

	private List<ServiceResponse> data = new ArrayList<>();
	private int call = -1;
	
	@JsonbTransient
	private JsonObject payload;
	
	private boolean hasNext = false;
	
	public RequestContext() {
	}
	
	public RequestContext(String orchestrator) {
		this.orchestrator = orchestrator;
		serviceCalls.add(orchestrator);
	}

	public List<ServiceResponse> getData() {
		return data;
	}
	
	public void next(String service) {
		serviceCalls.add(service);
		hasNext = true;
	}
	
	public List<String> getServiceCalls() {
		return serviceCalls;
	}
	
	/*public void setServiceCalls(List<String> serviceCalls) {
		this.serviceCalls = serviceCalls;
	}*/

	public String getNext() {
		try {
			return hasNext ? serviceCalls.get(serviceCalls.size()-1) : null;
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public String getOrchestrator() {
		return orchestrator;
	}
	
	public void setOrchestrator(String orchestrator) {
		this.orchestrator = orchestrator;
	}
	
	public JsonObject getPayload() {
		return payload;
	}
	
	public void setPayload(JsonObject payload) {
		this.payload = payload;
	}
	
	public static class ServiceResponse {

		private String service;
		private Object response;

		public ServiceResponse(String service, Object response) {
			this.service = service;
			this.response = response;
		}

		public String getService() {
			return service;
		}

		public void setService(String service) {
			this.service = service;
		}

		public Object getResponse() {
			return response;
		}

		public void setResponse(Object response) {
			this.response = response;
		}

	}

}
