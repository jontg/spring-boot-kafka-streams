package net.ulfhedinn.demo.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.ulfhedinn.demo.kafka.data.Payload;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
@Profile("producer")
public class WriteMessagesController {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  private final ObjectMapper mapper;
  private final KafkaProducer producer;

  private final Random random;
  private final List<UUID> randomOrgIds;

  public WriteMessagesController(ObjectMapper mapper,
                                 KafkaProducer producer) {
    this.mapper = mapper;
    this.producer = producer;
    random = new Random();
    randomOrgIds = List.of(
      new UUID(Long.MIN_VALUE, Long.MIN_VALUE),
      new UUID(Long.MIN_VALUE, Long.MAX_VALUE),
      new UUID(Long.MAX_VALUE, Long.MIN_VALUE),
      new UUID(Long.MAX_VALUE, Long.MAX_VALUE));
  }

  @Scheduled(fixedDelay = 100)
  public void writeMessageToKafka() throws JsonProcessingException {
    Payload payload = new Payload(
      new Date(),
      randomOrgIds.get(random.nextInt(0, randomOrgIds.size())),
      "Interesting data");

    producer.sendMessage("jontg", mapper.writeValueAsString(payload));
  }
}
