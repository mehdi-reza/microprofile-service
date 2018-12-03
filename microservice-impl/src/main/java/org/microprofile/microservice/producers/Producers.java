package org.microprofile.microservice.producers;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonReaderFactory;
import javax.json.JsonString;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.spi.JsonbProvider;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	Logger logger(InjectionPoint ip) {
		return LoggerFactory.getLogger(ip.getMember().getClass().getName());
	}
	
	@Produces
	Jsonb jsonb() {		
		return JsonbBuilder.create(new JsonbConfig());
	}
	
	@Produces
	JsonBuilderFactory jsonBuilderFactory() {
		Map<String, Object> config=new HashMap<String, Object>();
		return Json.createBuilderFactory(config);
	}
	
	@Produces
	JsonReaderFactory jsonReaderFactory() {
		Map<String, Object> config=new HashMap<String, Object>();
		return Json.createReaderFactory(config);		
	}
}
