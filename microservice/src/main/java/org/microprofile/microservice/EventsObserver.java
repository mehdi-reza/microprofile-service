package org.microprofile.microservice;

import java.util.Arrays;
import java.util.Objects;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.microprofile.microservice.events.OnStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventsObserver {
	
	private Logger logger=LoggerFactory.getLogger(EventsObserver.class);

	@Inject
	ZooKeeper zookeeper;

	public void startService(@Observes OnStart event) {
		String nodeName="/services/"+event.getServiceName();

		logger.info("Registering service {} in Zookeeper {}", nodeName, zookeeper.getSessionId());
		try {
			Stat node = zookeeper.exists(nodeName, false);
			if(Objects.isNull(node)) {
				logger.trace("Node does not exists already, creating node {}", nodeName);
				ACL acl=new ACL(ZooDefs.Perms.ALL, new Id("world", "anyone"));
				
				zookeeper.create(nodeName, new String("uri:"+event.getURL()).getBytes(), Arrays.asList(new ACL[]{acl}), CreateMode.PERSISTENT);
			} else {
				logger.trace("Service {} already registered in zookeeper", nodeName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void zookeeperEvent(@Observes WatchedEvent event) {
		logger.info("zookeeper event received.. {}", event);
	}
}
