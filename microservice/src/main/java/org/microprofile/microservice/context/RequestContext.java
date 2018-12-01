package org.microprofile.microservice.context;

import java.util.ArrayList;
import java.util.List;

public class RequestContext {

	/**
	 * Provide name of next service to be called
	 * 
	 * @param service
	 */

	private int call=-1;
	private List<String> serviceCalls=new ArrayList<>();
	private Object payload;

	public RequestContext() {

	}

	public RequestContext(Object payload) {
		this.payload = payload;
	}

	public void next(String service) {
		serviceCalls.add(service);
	}

	public String getNext() {
		try {
			return serviceCalls.get(call + 1);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
}
