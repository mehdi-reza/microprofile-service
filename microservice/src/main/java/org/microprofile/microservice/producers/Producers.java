package org.microprofile.microservice.producers;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class Producers {
	
	@Inject
	Event<WatchedEvent> zookeeperEvent;
	
	@Produces @Dependent
	ZooKeeper zookeper(@ConfigProperty(name = "zookeeper.connect.string") String connectString,
			@ConfigProperty(name = "zookeeper.connect.timeout") int connectTimeout) {
		try {
			ZooKeeper zookeeper = new ZooKeeper(connectString, connectTimeout, new Watcher() {
				public void process(WatchedEvent event) {
					zookeeperEvent.fireAsync(event);
				}
			});
			//zookeeper.addAuthInfo("digest", "system:system".getBytes());
			return zookeeper;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
