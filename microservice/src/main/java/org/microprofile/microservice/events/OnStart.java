package org.microprofile.microservice.events;

import java.net.MalformedURLException;
import java.net.URL;

public interface OnStart {
	public String getServiceName();
	public URL getURL() throws MalformedURLException;
}
