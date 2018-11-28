package org.microprofile.microservice;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class JaxRsActivator extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		super.getClasses().add(ServiceEndpoint.class);
		initialize();
		return super.getClasses();
	}
	
	@PostConstruct
	public void initialize() {
		System.out.println("Initialized");
	}
}
