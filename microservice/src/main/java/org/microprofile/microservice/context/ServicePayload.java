package org.microprofile.microservice.context;

public class ServicePayload {

	private String service;
	private Object payload;

	public ServicePayload(String service, Object payload) {
		this.service = service;
		this.payload = payload;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

}
