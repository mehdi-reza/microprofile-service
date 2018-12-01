package org.microprofile.microservice.context;

import java.util.ArrayList;
import java.util.List;

public final class RequestContext {

	/**
	 * Provide name of next service to be called
	 * 
	 * @param service
	 */

	private String orchestrator;
	private List<String> serviceCalls = new ArrayList<>();

	private List<ServicePayload> data = new ArrayList<>();
	private int call = -1;
	
	boolean hasNext = false;
	
	public RequestContext() {
	}
	
	public RequestContext(String orchestrator) {
		this.orchestrator = orchestrator;
	}

	public RequestContext(String service, Object payload) {
		this.data.add(new ServicePayload(service, payload));
	}

	public List<ServicePayload> getData() {
		return data;
	}
	
	public void setData(List<ServicePayload> data) {
		this.data = data;
	}
	
	public void next(String service) {
		serviceCalls.add(service);
		hasNext = true;
	}
	
	public List<String> getServiceCalls() {
		return serviceCalls;
	}
	
	public void setServiceCalls(List<String> serviceCalls) {
		this.serviceCalls = serviceCalls;
	}

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
}
