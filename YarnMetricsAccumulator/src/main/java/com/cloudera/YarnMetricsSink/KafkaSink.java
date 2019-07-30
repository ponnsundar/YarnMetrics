package com.cloudera.YarnMetricsSink;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.cloudera.YarnMetricsAccumulatorDTO.AppConfig;

import java.util.*;

public class KafkaSink implements Sink {

	@Override
	public boolean saveData(AppConfig appConfig, String FileContent) {

		Properties props = new Properties();
		props.put("bootstrap.servers", appConfig.getKafkaBootstrapServers());
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 100);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);

		producer.send(new ProducerRecord<String, String>(appConfig
				.getKafkaTopic(), FileContent));

		producer.close();
		return true;

	}

}
