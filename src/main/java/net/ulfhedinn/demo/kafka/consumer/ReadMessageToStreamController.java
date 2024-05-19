package net.ulfhedinn.demo.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.ulfhedinn.demo.kafka.data.Payload;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class ReadMessageToStreamController {
  private static final Serde<String> STRING_SERDE = Serdes.String();

  private final ObjectMapper mapper;

  public ReadMessageToStreamController(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Autowired
  void buildPipeline(StreamsBuilder streamsBuilder) {
    KStream<String, String> messageStream = streamsBuilder
      .stream("jontg", Consumed.with(STRING_SERDE, STRING_SERDE));

    KTable<String, Long> wordCounts = messageStream
      .mapValues((message) -> {
        try {
          Payload payload = mapper.readValue(message, Payload.class);
          log.info("Mapping value {}", payload);
          return payload.getMessage();
        } catch (Exception e) {
          log.warn("Unprocessable message {}", message);
          return message;
        }
      })
      .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
      .groupBy((key, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE))
      .count();

    wordCounts.toStream().foreach((key, value) -> {
      log.info("Stream consumer with key '{}' and value '{}'", key, value);
    });
  }

}
