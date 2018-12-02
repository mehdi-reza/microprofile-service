package org.microprofile.microservice;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public final class JaxRsActivator extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		
		Set<Class<?>> classes=new HashSet<Class<?>>();
		
		classes.add(ServiceEndpoint.class);
		return classes;
	}
}
