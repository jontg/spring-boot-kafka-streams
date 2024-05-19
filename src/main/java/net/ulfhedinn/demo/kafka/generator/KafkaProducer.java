package net.ulfhedinn.demo.kafka.generator;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
    this.kafkaTemplate.setObservationEnabled(true);
  }

  public void sendMessage(String topic, String message) {
    kafkaTemplate.send(topic, message);
  }
}
