package org.microprofile.microservice.producers;

import javax.enterprise.inject.Produces;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class Producers {

	@Produces
	ZooKeeper zookeper(@ConfigProperty(name = "zookeeper.connect.string") String connectString,
			@ConfigProperty(name = "zookeeper.connect.timeout") int connectTimeout) {
		try {
			return new ZooKeeper(connectString, connectTimeout, new Watcher() {
				public void process(WatchedEvent event) {
					System.out.println("Event received: "+event);
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
