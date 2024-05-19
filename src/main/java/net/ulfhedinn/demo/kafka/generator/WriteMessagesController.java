package net.ulfhedinn.demo.kafka.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.ulfhedinn.demo.kafka.data.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Controller
public class WriteMessagesController {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  private final ObjectMapper mapper;
  private final KafkaProducer producer;

  public WriteMessagesController(ObjectMapper mapper,
                                 KafkaProducer producer) {
    this.mapper = mapper;
    this.producer = producer;
  }

  @Scheduled(fixedDelay = 5_000)
  public void writeMessageToKafka() throws JsonProcessingException {
    Payload payload = new Payload(new Date(), UUID.randomUUID(), "Interesting data");

    producer.sendMessage("jontg", mapper.writeValueAsString(payload));
  }
}
