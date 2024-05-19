package net.ulfhedinn.demo.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReadMessageToLogController {
  @KafkaListener(topics = "jontg", groupId = "read-message-to-logger")
  public void consumeMessageForLogger(String message) {
    log.info("Received message '{}'", message);
  }
}
