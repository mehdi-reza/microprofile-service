package org.microprofile.microservice;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.microprofile.microservice.annotations.ServiceDescriptor;
import org.slf4j.Logger;

public class EventsObserver {
	
	@Inject
	private Logger logger;

	@Inject
	JsonBuilderFactory jsonFactory;
	
	public void init(@Observes @Initialized(ApplicationScoped.class) Object init, ZooKeeper zookeeper, MicroService<?,?> service) {
		
		ServiceDescriptor descriptor = service.getClass().getAnnotation(ServiceDescriptor.class);
		Objects.requireNonNull(descriptor, "ServiceDescriptor is not defined on service..");

		String serviceName = descriptor.name();
		String url = descriptor.url();
		
		String nodeName="/services/"+serviceName;
		
		logger.info("Registering service {} in Zookeeper {}", nodeName, zookeeper.getSessionId());
		try {
			Stat node = zookeeper.exists(nodeName, false);
			if(Objects.isNull(node)) {
				logger.trace("Node does not exists already, creating node {}", nodeName);
				ACL acl=new ACL(ZooDefs.Perms.ALL, new Id("world", "anyone"));

				JsonObject nodeData=jsonFactory.createObjectBuilder().add("url", url).build();
				StringWriter writer=new StringWriter();
				Json.createWriter(writer).write(nodeData);				
				zookeeper.create(nodeName, writer.getBuffer().toString().getBytes(), Arrays.asList(new ACL[]{acl}), CreateMode.PERSISTENT);
				writer.close();
			} else {
				logger.trace("Service {} already registered in zookeeper", nodeName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		logger.info("intialized .. {}, service: {}", init, service);
    }

	public void zookeeperEvent(@Observes WatchedEvent event) {
		logger.info("zookeeper event received.. {}", event);
	}
}
