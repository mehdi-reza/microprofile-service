package org.microprofile.microservice.producers;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonString;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.spi.JsonbProvider;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class Producers {

	@Inject
	Event<WatchedEvent> zookeeperEvent;

	@Produces
	ZooKeeper zookeper(@ConfigProperty(name = "zookeeper.connect.string") String connectString,
			@ConfigProperty(name = "zookeeper.connect.timeout") int connectTimeout) {
		try {
			ZooKeeper zookeeper = new ZooKeeper(connectString, connectTimeout, new Watcher() {
				public void process(WatchedEvent event) {
					zookeeperEvent.fireAsync(event);
				}
			});

			return zookeeper;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Produces
	Jsonb jsonb() {		
		return JsonbProvider.provider().create().build();
	}
	
	@Produces
	JsonBuilderFactory jsonBuilderFactory() {
		Map<String, Object> config=new HashMap<String, Object>();
		return Json.createBuilderFactory(config);
	}
}
