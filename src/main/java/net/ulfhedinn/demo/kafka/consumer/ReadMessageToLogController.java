package net.ulfhedinn.demo.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.ulfhedinn.demo.kafka.data.Payload;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("producer")
public class ReadMessageToLogController {
  private final ObjectMapper mapper;

  public ReadMessageToLogController(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @KafkaListener(topics = "jontg", groupId = "read-message-to-logger")
  public void consumeMessageForLogger(String message) throws JsonProcessingException {
    Payload payload = mapper.readValue(message, Payload.class);
//    log.info("Received message '{}'", payload);
  }
}
