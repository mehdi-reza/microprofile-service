package org.microprofile.microservice;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.zookeeper.ZooKeeper;
import org.microprofile.microservice.events.OnStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceStarter {
	
	private Logger logger=LoggerFactory.getLogger(ServiceStarter.class);

	@Inject
	ZooKeeper zookeeper;

	public void startService(@Observes OnStart event) {
		logger.info("Registering service {} in Zookeeper {}", event.getServiceName(), zookeeper.getSessionId());		
	}
}
