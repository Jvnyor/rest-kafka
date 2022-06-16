package com.Jvnyor.kafka.producer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.Jvnyor.kafka.model.dto.CarDTO;

@Service
public class CarProducer {

	private static final Logger logger = LoggerFactory.getLogger(CarProducer.class);
	private final String topic;
	private final KafkaTemplate<String, CarDTO> kafkaTemplate;

	public CarProducer(@Value("${topic.name}") String topic, KafkaTemplate<String, CarDTO> kafkaTemplate) {
		this.topic = topic;
		this.kafkaTemplate = kafkaTemplate;
	}

	public void send(CarDTO carDTO) {
		kafkaTemplate.send(topic, carDTO).addCallback(
				success -> logger.info("Message send" + success.getProducerRecord().value()),
				failure -> logger.info("Message failure" + failure.getMessage()));
	}
}
