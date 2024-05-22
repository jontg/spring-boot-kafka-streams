package net.ulfhedinn.demo.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.ulfhedinn.demo.kafka.data.Payload;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@Profile("consumer")
public class ReadMessageToStreamController {
  private static final Serde<UUID> UUID_SERDE = Serdes.UUID();
  private static final Serde<String> STRING_SERDE = Serdes.String();

  private final ObjectMapper mapper;

  public ReadMessageToStreamController(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Autowired
  public void buildPipeline(StreamsBuilder streamsBuilder) {
    KStream<String, String> messageStream = streamsBuilder
      .stream("jontg", Consumed.with(STRING_SERDE, STRING_SERDE));

    KTable<Windowed<UUID>, Long> wordCounts = messageStream
      .groupBy((key, message) -> {
        Payload payload = getPayloadFromMessage(message);
        return payload.getOrgId();
      }, Grouped.with(UUID_SERDE, STRING_SERDE))
      .windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofSeconds(30), Duration.ofMillis(500)))
      .count();

    wordCounts.toStream().foreach((key, count) -> {
      log.info("Stream consumer with key '{}' [{} to {}] and value '{}'",
        key.key(),
        key.window().startTime(),
        key.window().endTime(),
        count);
    });
  }

  private Payload getPayloadFromMessage(String message) {
    try {
      return mapper.readValue(message, Payload.class);
    } catch (JsonProcessingException e) {
      return new Payload(new Date(), new UUID(0, 0), "Empty Message");
    }
  }
}
